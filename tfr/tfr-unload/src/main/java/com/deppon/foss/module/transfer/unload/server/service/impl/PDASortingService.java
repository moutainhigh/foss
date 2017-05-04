/*
 * PROJECT NAME: tfr-unload
 * PACKAGE NAME: com.deppon.foss.module.transfer.unload.server.service.impl
 * FILE    NAME: SortingScanService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.unload.server.service.impl;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IReturnGoodsRequestEntityService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ReturnGoodsRequestEntity;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.dao.IPDAExpressPackageDao;
import com.deppon.foss.module.transfer.load.api.server.service.IPDACommonService;
import com.deppon.foss.module.transfer.load.api.shared.domain.ExpressPackageDetailEntity;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDASortingService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.SortingScanDto;
import com.deppon.foss.module.transfer.unload.api.server.dao.IPDASortingDao;
import com.deppon.foss.module.transfer.unload.api.shared.define.UnloadConstants;
import com.deppon.foss.module.transfer.unload.api.shared.domain.SortingScanEntity;
import com.deppon.foss.util.UUIDUtils;
/**
 * 分拣扫描接口
 * @author dp-duyi
 * @date 2013-7-26 上午11:24:01
 */
public class PDASortingService implements IPDASortingService{
	static final Logger LOGGER = LoggerFactory.getLogger(PDASortingService.class);
	private IEmployeeService employeeService;	
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	private IPDASortingDao pdaSortingDao;
	private IPDACommonService pdaCommonService;
	/**
	 * 七天返货: 调用综合接口 200968 2015-07-11
	 * */
	private IReturnGoodsRequestEntityService returnGoodsRequestEntityService;
	
	public void setReturnGoodsRequestEntityService(
			IReturnGoodsRequestEntityService returnGoodsRequestEntityService) {
		this.returnGoodsRequestEntityService = returnGoodsRequestEntityService;
	}

	/**
	 * pda包服务，用于获取包内流水号
	 */
	private IPDAExpressPackageDao pdaExpressPackageDao;
	
	public IEmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public IOrgAdministrativeInfoService getOrgAdministrativeInfoService() {
		return orgAdministrativeInfoService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public IPDASortingDao getPdaSortingDao() {
		return pdaSortingDao;
	}

	public void setPdaSortingDao(IPDASortingDao pdaSortingDao) {
		this.pdaSortingDao = pdaSortingDao;
	}
	public void setPdaCommonService(IPDACommonService pdaCommonService) {
		this.pdaCommonService = pdaCommonService;
	}

	public final void setPdaExpressPackageDao(
			IPDAExpressPackageDao pdaExpressPackageDao) {
		this.pdaExpressPackageDao = pdaExpressPackageDao;
	}

	/** 
	 * 分拣扫描
	 * @author dp-duyi
	 * @date 2013-7-26 上午11:29:03
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDASortingService#scan(com.deppon.foss.module.transfer.pda.api.shared.dto.SortingScanDto)
	 */
	@Override
	public List<SortingScanDto> scan(SortingScanDto record) {
		LOGGER.error("分拣扫描开始"+record.getWayBillNo()+record.getSerialNo()+"-"+record.getScanType()+"-"+record.getOrgCode());
		SortingScanEntity sortingScanEntity = new SortingScanEntity();
		sortingScanEntity.setDeviceNo(record.getDeviceNo());
		sortingScanEntity.setOperatorCode(record.getOperatorCode());
		EmployeeEntity employee = employeeService.queryEmployeeByEmpCode(record.getOperatorCode());
		if(employee != null){
			sortingScanEntity.setOperatorName(employee.getEmpName());
		}
		OrgAdministrativeInfoEntity org = pdaCommonService.getTopCurrentOutfieldOrSalesDept(record.getOrgCode());
		if(org != null){
			sortingScanEntity.setOrgName(org.getName());
			sortingScanEntity.setOrgCode(org.getCode());
		}else{
			throw new TfrBusinessException("无效部门");
		}
		sortingScanEntity.setScanType(record.getScanType());
		sortingScanEntity.setScanTime(record.getScanTime());
		sortingScanEntity.setSerialNo(record.getSerialNo());
		sortingScanEntity.setWayBillNo(record.getWayBillNo());
		sortingScanEntity.setCreateTime(new Date());
		sortingScanEntity.setId(UUIDUtils.getUUID());
		
		sortingScanEntity.setScanMode(UnloadConstants.SORT_SCAN_MODE_PDA);
		pdaSortingDao.insertSortingScan(sortingScanEntity);
		
		/**
		 * 调用七天返货接口,判断运单的是否为 7天返货 类型  zwd 200968 2015-07-11
		 */
		//因为零担没有七天返货的类型，所以注释
//		List<SortingScanDto> returns = new ArrayList<SortingScanDto>();
//		ReturnGoodsRequestEntity rge = returnGoodsRequestEntityService.queryReturnGoodsRequestEntityByWayBillNo(record.getWayBillNo());
//		if(rge != null){
//			if(rge.getReturnType().equals("SEVEN_DAYS_RETURN")){
//				SortingScanDto sortingScanDto = new SortingScanDto();
//				sortingScanDto.setWayBillNo(record.getWayBillNo());
//				returns.add(sortingScanDto);
//			}
//		}
		LOGGER.error("分拣扫描结束"+record.getWayBillNo()+record.getSerialNo()+"-"+record.getScanType()+"-"+record.getOrgCode());
		return null;
	}

	/**
	 * 
	 * <p>包分拣扫描</p> 
	 * @author alfred
	 * @date 2014-10-28 上午10:28:12
	 * @param record 
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDASortingService#scanPackage(com.deppon.foss.module.transfer.pda.api.shared.dto.SortingScanDto)
	 */
	@Override
	public List<SortingScanDto> scanPackage(SortingScanDto record) {
		LOGGER.error("分拣包扫描开始"+record.getWayBillNo()+"-"+record.getScanType()+"-"+record.getOrgCode());
		List<SortingScanEntity> sortingScanEntitys = new ArrayList<SortingScanEntity>();
		List<ExpressPackageDetailEntity> detailList = pdaExpressPackageDao.queryScanPackageDetails(record.getWayBillNo());
		/**
		 * 调用七天返货接口,判断运单的是否为 7天返货 类型  zwd 200968 2015-07-11
		 */
		List<SortingScanDto> returns = new ArrayList<SortingScanDto>();
		
		if(CollectionUtils.isNotEmpty(detailList)){
			for(ExpressPackageDetailEntity detail:detailList){
				SortingScanEntity sortingScanEntity = new SortingScanEntity();
				sortingScanEntity.setDeviceNo(record.getDeviceNo());
				sortingScanEntity.setOperatorCode(record.getOperatorCode());
				EmployeeEntity employee = employeeService.queryEmployeeByEmpCode(record.getOperatorCode());
				if(employee != null){
					sortingScanEntity.setOperatorName(employee.getEmpName());
				}
				OrgAdministrativeInfoEntity org = pdaCommonService.getTopCurrentOutfieldOrSalesDept(record.getOrgCode());
				if(org != null){
					sortingScanEntity.setOrgName(org.getName());
					sortingScanEntity.setOrgCode(org.getCode());
				}else{
					throw new TfrBusinessException("无效部门");
				}
				sortingScanEntity.setScanType(record.getScanType());
				sortingScanEntity.setScanTime(record.getScanTime());
				sortingScanEntity.setWayBillNo(detail.getWayBillNo());
				
				/**
				 * 判断是否七天返货 zwd 200968 2015-07-11
				 */
				ReturnGoodsRequestEntity rge = returnGoodsRequestEntityService.queryReturnGoodsRequestEntityByWayBillNo(detail.getWayBillNo());
				if(rge != null){
					if(rge.getReturnType().equals("SEVEN_DAYS_RETURN")){
						SortingScanDto sortingScanDto = new SortingScanDto();
						sortingScanDto.setWayBillNo(detail.getWayBillNo());
						returns.add(sortingScanDto);
					}
				}
				sortingScanEntity.setSerialNo(detail.getSerialNo());
				sortingScanEntity.setCreateTime(new Date());
				sortingScanEntity.setId(UUIDUtils.getUUID());
				sortingScanEntity.setScanMode(UnloadConstants.SORT_SCAN_MODE_PDA);
				sortingScanEntitys.add(sortingScanEntity);
			}
			pdaSortingDao.insertSortingScans(sortingScanEntitys);
		}
		LOGGER.error("分拣包扫描结束"+record.getWayBillNo()+"-"+record.getScanType()+"-"+record.getOrgCode());
		return returns;
	}
	
	
}
