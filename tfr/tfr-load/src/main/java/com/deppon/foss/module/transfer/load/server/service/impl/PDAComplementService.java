/*
 * PROJECT NAME: tfr-load
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.server.service.impl
 * FILE    NAME: PDAComplementService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.dao.IPDAComlementDao;
import com.deppon.foss.module.transfer.load.api.server.service.IPDACommonService;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAComplementService;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDASortingService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDAComplementDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.SortingScanDto;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ICalculateTransportPathService;

/**
 * PDA补码接口
 * @author dp-duyi
 * @date 2013-7-23 下午4:38:35
 */
public class PDAComplementService implements IPDAComplementService{
	static final Logger LOGGER = LoggerFactory.getLogger(PDAComplementService.class);
	private IPDAComlementDao pdaComlementDao;
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	private IPDASortingService pdaSortingService;
	
	private ICalculateTransportPathService calculateTransportPathService;
	
	
	/**
	 * @param calculateTransportPathService the calculateTransportPathService to set
	 */
	public void setCalculateTransportPathService(ICalculateTransportPathService calculateTransportPathService) {
		this.calculateTransportPathService = calculateTransportPathService;
	}
	public void setPdaComlementDao(IPDAComlementDao pdaComlementDao) {
		this.pdaComlementDao = pdaComlementDao;
	}
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public void setPdaSortingService(IPDASortingService pdaSortingService) {
		this.pdaSortingService = pdaSortingService;
	}
	/** 
	 * 查询补码结果
	 * @author dp-duyi
	 * @date 2013-7-23 下午4:39:21
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDAComplementService#queryComplement(java.util.Date, java.lang.String, boolean)
	 */
	@Override
	public List<PDAComplementDto> queryComplement(Date queryTimeBegin,
			String wayBillNo, boolean beLocal,String orgCode) {
		LOGGER.error("下拉补码数据开始"+wayBillNo+queryTimeBegin+beLocal);
		List<PDAComplementDto> result = null;
		if(StringUtils.isNotBlank(wayBillNo)){
			result = pdaComlementDao.queryComplement(queryTimeBegin, wayBillNo, beLocal,orgCode);
			
		}else{
			List<String> bizTypes = new ArrayList<String>();
			bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
			OrgAdministrativeInfoEntity org = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoByCode(orgCode,bizTypes);
			LOGGER.error("下拉补码部门"+orgCode);
			if(org != null){
				LOGGER.error("下拉补码部门所属外场"+org.getCode());
				result = pdaComlementDao.queryComplement(queryTimeBegin, wayBillNo, beLocal,org.getCode());
			}
		}
		if(CollectionUtils.isNotEmpty(result)){
			for(PDAComplementDto r : result){
				LOGGER.error(r.getWayBillNo()+" 目的站:"+r.getTargetOrgCode()+" 到达部门"+r.getReachOrgCode()+r.getReachOrgName()+" 地址:"+r.getReceiveCustomerAddress());
				String simpleName = pdaComlementDao.queryLastOrgNameByWaybillNo(r.getWayBillNo());
				r.setSimpleOrgName(simpleName);
			}
		}
		LOGGER.error("下拉补码数据结束"+wayBillNo+queryTimeBegin+beLocal);
		return result;
	}
	/**
	* @description 查询补码结果+上分拣扫描
	* @param queryTimeBegin
	* @param wayBillNo
	* @param beLocal
	* @param orgCode
	* @param isPackage
	* @param record
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年8月20日 上午10:11:47
	*/
	@Override
	public Map<String,Object> queryComplement(Date queryTimeBegin,
			String wayBillNo, boolean beLocal, String orgCode,
			String isPackage, SortingScanDto record) {
		Map<String,Object> backMap = new HashMap<String,Object>();
		List<PDAComplementDto> dtoList = queryComplement(queryTimeBegin,wayBillNo,beLocal,orgCode, record);
		List<SortingScanDto> sortingSanDtoList = null;
		if(StringUtils.isNotBlank(isPackage) && "Y".equals(isPackage)){
			throw new TfrBusinessException("零担不支持快递包扫描");
		}else{
			sortingSanDtoList = pdaSortingService.scan(record);
		}
		backMap.put("ComplementList", dtoList);
		backMap.put("SevenReturnList", sortingSanDtoList);
		return backMap;
	}
	
	
	/** 
	 * 查询补码结果
	 * @author dp-ruilibao
	 * @date 2013-7-23 下午4:39:21
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDAComplementService#queryComplement(java.util.Date, java.lang.String, boolean)
	 */
	private List<PDAComplementDto> queryComplement(Date queryTimeBegin,
			String wayBillNo, boolean beLocal,String orgCode, SortingScanDto record) {
		LOGGER.error("下拉补码数据开始"+wayBillNo+queryTimeBegin+beLocal);
		List<PDAComplementDto> result = new ArrayList<PDAComplementDto>();
		if (StringUtils.isEmpty(record.getSerialNo())) {
			throw new TfrBusinessException("流水号不能为空");
		}
		if(StringUtils.isNotBlank(wayBillNo)){
			
			result = pdaComlementDao.queryComplement(queryTimeBegin, wayBillNo, beLocal,orgCode);
			if (CollectionUtils.isEmpty(result)) {
				result = new ArrayList<PDAComplementDto>();
				PDAComplementDto dto = new PDAComplementDto();
				dto.setReachOrgCode("未开单");
				result.add(dto);
				return result;
			}
			
			if (CollectionUtils.isNotEmpty(result)) {
				if (StringUtils.isEmpty(result.get(0).getReachOrgCode())){
					result.get(0).setReachOrgCode(result.get(0).getReachOrgName());
				}
			}
			
			List<String> bizTypes = new ArrayList<String>();
			bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
			OrgAdministrativeInfoEntity org = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoByCode(record.getOrgCode(),bizTypes);
			LOGGER.error("下拉补码部门"+record.getOrgCode());
			if(org != null){
				LOGGER.error("下拉补码部门所属外场"+org.getCode());
			}
			
			//获取运单的下一部门名称,传入运单号,流水号和当前操作人部门获取下一部门名称
			String nextOrgCode = null;
			nextOrgCode = calculateTransportPathService.queryPathByWgoService(wayBillNo, record.getSerialNo(),org.getCode());
			result.get(0).setNextOrgCode(nextOrgCode);
		}else{
			throw new TfrBusinessException("运单号不能为空");
		}
		if(CollectionUtils.isNotEmpty(result)){
			for(PDAComplementDto r : result){
				LOGGER.error(r.getWayBillNo()+" 目的站:"+r.getTargetOrgCode()+" 到达部门"+r.getReachOrgCode()+r.getReachOrgName()+" 地址:"+r.getReceiveCustomerAddress());
				String simpleName = pdaComlementDao.queryLastOrgNameByWaybillNo(r.getWayBillNo());
				r.setSimpleOrgName(simpleName);
			}
		}
		LOGGER.error("下拉补码数据结束"+wayBillNo+queryTimeBegin+beLocal);
		return result;
	}
	

}
