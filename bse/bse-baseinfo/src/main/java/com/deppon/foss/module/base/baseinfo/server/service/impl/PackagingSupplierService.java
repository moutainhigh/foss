package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.cxf.common.util.StringUtils;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IEmployeeDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IPackagingSupplierDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPackagingSupplierService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISendPackagingSupplierToCUBCService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PackagingSupplierEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * TODO(实现IPackagingSupplierService接口，对包装提供商信息实现增删改查)
 * @author 187862-杜军辉
 * @date 2014-5-8 下午6:33:33
 * @since
 * @version
 */
public class PackagingSupplierService implements IPackagingSupplierService {

	private IPackagingSupplierDao packagingSupplierDao;
	
	private ISendPackagingSupplierToCUBCService sendPackagingSupplierToCUBCService;
	/**
	 * @param packagingSupplierDao the packagingSupplierDao to set
	 */
	public void setPackagingSupplierDao(IPackagingSupplierDao packagingSupplierDao) {
		this.packagingSupplierDao = packagingSupplierDao;
	}

	public void setSendPackagingSupplierToCUBCService(
			ISendPackagingSupplierToCUBCService sendPackagingSupplierToCUBCService) {
		this.sendPackagingSupplierToCUBCService = sendPackagingSupplierToCUBCService;
	}


	/** 
	 * <p>TODO(根据包装供应商List查询包装供应商详细信息)</p> 
	 * @author 187862
	 * @date 2014-5-8 下午6:33:33
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPackagingSupplierService#queryPackagingSupplierByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.PackagingSupplierEntity)
	 */
	@Override
	public List<PackagingSupplierEntity> queryPackagingSupplierByEntityList(
			String deptCode,List<String> packagingSupplierCodeList) {
		// TODO Auto-generated method stub
		return packagingSupplierDao.queryPackagingSupplierByEntityList(deptCode, packagingSupplierCodeList);
	}
	
	/** 
	 * <p>TODO(精确查询包装提供商基础信息)</p> 
	 * @author 187862
	 * @date 2014-5-8 下午6:33:33
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPackagingSupplierService#queryPackagingSupplierByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.PackagingSupplierEntity)
	 */
	@Override
	public PackagingSupplierEntity queryPackagingSupplierByEntity(
			String deptCode,String supplierCode) {
		// TODO Auto-generated method stub
		return packagingSupplierDao.queryPackagingSupplierByEntity(deptCode, supplierCode);
	}

	/** 
	 * <p>TODO(添加包装提供商信息)</p> 
	 * @author 187862
	 * @date 2014-5-8 下午6:33:33
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPackagingSupplierService#addPackagingSupplier(com.deppon.foss.module.base.baseinfo.api.shared.domain.PackagingSupplierEntity)
	 */
	@Override
	public int addPackagingSupplier(PackagingSupplierEntity entity) {
		// TODO Auto-generated method stub
		if (null == entity) {
		    return FossConstants.FAILURE;
		}
		entity.setId(UUIDUtils.getUUID());
		BigDecimal breakageRate=new BigDecimal(entity.getBreakageRate()).multiply(new BigDecimal("0.01"));
//		DecimalFormat format=new DecimalFormat("0.000000");
		entity.setBreakageRate(breakageRate.toString());
		int result = packagingSupplierDao.addPackagingSupplier(entity); 
		
		//同步数据到结算中心
		List<PackagingSupplierEntity> packagingSupplier = new ArrayList<PackagingSupplierEntity>();
		packagingSupplier.add(entity);
		syncPackagingSupplierToCUBC(packagingSupplier,NumberConstants.ONE);
		
		return result;
	}

    /**
     *<p>同步发车标准给网点规划</p>
     *@author 269231 -qirongsheng
     *@date 2016-1-27 下午2:48:41
     *@param lineEntity
     *@param type
     */
    private void syncPackagingSupplierToCUBC(List<PackagingSupplierEntity> packagingSupplier, Integer type) {
    	if(null != packagingSupplier){
        	//同步接口
        	sendPackagingSupplierToCUBCService.SyncPackagingSupplier(packagingSupplier, type);
    	}
	}
    
	/** 
	 * <p>TODO(批量删除包装提供商信息)</p> 
	 * @author 187862
	 * @date 2014-5-8 下午6:33:33
	 * @param codeList
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPackagingSupplierService#deletePackagingSupplier(java.lang.String[])
	 */
	@Override
	public int deletePackagingSupplier(String[] codeList) {
		// TODO Auto-generated method stub	
		int result = packagingSupplierDao.deletePackagingSupplier(codeList);
		
		//同步删除数据到结算中心
		List<PackagingSupplierEntity> packagingSupplier = new ArrayList<PackagingSupplierEntity>();	
		packagingSupplier = packagingSupplierDao.queryByCodeList(codeList);				
		syncPackagingSupplierToCUBC(packagingSupplier,NumberConstants.THREE);
		
		return result;
	}

	/** 
	 * <p>TODO(更新包装提供商信息)</p> 
	 * @author 187862
	 * @date 2014-5-8 下午6:33:33
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPackagingSupplierService#updatePackagingSupplier(com.deppon.foss.module.base.baseinfo.api.shared.domain.PackagingSupplierEntity)
	 */
	@Override
	public int updatePackagingSupplier(PackagingSupplierEntity entity) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(entity.getId())){
			return FossConstants.FAILURE;
		}else{
			BigDecimal breakageRate=new BigDecimal(entity.getBreakageRate()).multiply(new BigDecimal("0.01"));
//			DecimalFormat format=new DecimalFormat("0.000000");
			entity.setBreakageRate(breakageRate.toString());
			int result = packagingSupplierDao.updatePackagingSupplier(entity);
			
			//同步更新数据到结算中心
			List<PackagingSupplierEntity> packagingSupplier = new ArrayList<PackagingSupplierEntity>();
			List<String> idList = new ArrayList<String>(); 
			idList.add(entity.getId());
			PackagingSupplierEntity updateEntity = packagingSupplierDao.queryByCodeList(idList).get(0);
			packagingSupplier.add(updateEntity);
			syncPackagingSupplierToCUBC(packagingSupplier,NumberConstants.TWO);
			
			return result;
		}
	}
	
	/** 
	 * <p>TODO(根据部门名和包装供应商名查询包装提供商信息唯一性)</p> 
	 * @author 187862
	 * @date 2014-5-29 上午8:45:46
	 * @param entity
	 * @param limit
	 * @param start
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPackagingSupplierService#queryPackagingSupplierUnique(com.deppon.foss.module.base.baseinfo.api.shared.domain.PackagingSupplierEntity, int, int)
	 */
	@Override
	public List<PackagingSupplierEntity> queryPackagingSupplierUnique(
			PackagingSupplierEntity entity, int limit, int start) {
		// TODO Auto-generated method stub
		if (null == entity) {
			entity=new PackagingSupplierEntity();
		} 
			return packagingSupplierDao.queryPackagingSupplierUnique(entity, limit, start);
	}

	/** 
	 * <p>TODO(根据部门查询包装提供商信息)</p> 
	 * @author 187862
	 * @date 2014-5-8 下午6:33:33
	 * @param entity
	 * @param limit
	 * @param start
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPackagingSupplierService#queryPackagingSupplierListByOrgCode(com.deppon.foss.module.base.baseinfo.api.shared.domain.PackagingSupplierEntity, int, int)
	 */
	@Override
	public List<PackagingSupplierEntity> queryPackagingSupplierListByOrgCode(
			PackagingSupplierEntity entity, int limit, int start) {
		// TODO Auto-generated method stub
		if (null == entity) {
			entity=new PackagingSupplierEntity();
		}
			return packagingSupplierDao.queryPackagingSupplierListByOrgCode(entity, limit, start);
	}

	/** 
	 * <p>TODO(根据部门查询包装提供商信息条数)</p> 
	 * @author 187862
	 * @date 2014-5-8 下午6:33:33
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPackagingSupplierService#queryOrgCodeCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.PackagingSupplierEntity)
	 */
	@Override
	public Long queryOrgCodeCount(PackagingSupplierEntity entity) {
		// TODO Auto-generated method stub
		return this.packagingSupplierDao.queryOrgCodeCount(entity);
	}

	/** 
	 * <p>TODO(查询包装供应商信息总条数)</p> 
	 * @author 187862
	 * @date 2014-5-8 下午6:33:33
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPackagingSupplierService#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.PackagingSupplierEntity)
	 */
	@Override
	public Long queryRecordCount(PackagingSupplierEntity entity) {
		// TODO Auto-generated method stub
		if (null == entity) {
			entity=new PackagingSupplierEntity();
		}
			return packagingSupplierDao.queryRecordCount(entity);
	}
	
	/** 
	 * <p>TODO(根据员工工号查询对应外场部门的包装供应商)</p> 
	 * @author 187862
	 * @date 2014-6-25 下午4:35:45
	 * @param empCode
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPackagingSupplierService#queryPackagingSupplierListByEmpCode(String empCode)
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	/**
	 * @param orgAdministrativeInfoComplexService the orgAdministrativeInfoComplexService to set
	 */
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	private IEmployeeDao employeeDao;
	
	/**
	 * @param employeeDao the employeeDao to set
	 */
	public void setEmployeeDao(IEmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}
	@Override
	public List<PackagingSupplierEntity> queryPackagingSupplierListByEmpCode(String empCode) {
		if(StringUtil.isNotEmpty(empCode)){
			EmployeeEntity empEntity=employeeDao.queryEmployeeByEmpCode(empCode);
			if(null==empEntity){
				return null;
			}
			List<String> list=new ArrayList<String>();
			list.add(BizTypeConstants.ORG_TRANSFER_CENTER);
			OrgAdministrativeInfoEntity entity = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoByCode(empEntity.getOrgCode(),list);
			if(null==entity){
				return null;
			}
			PackagingSupplierEntity entity2=new PackagingSupplierEntity();
			entity2.setOrgCodeCode(entity.getCode());
            entity2.setActive(FossConstants.ACTIVE);
			return packagingSupplierDao.queryPackagingSupplierListByOrgCode(entity2, Integer.MAX_VALUE,0);
		}
		return null;
	}
	
	/** 
	 * <p>TODO(根据部4个字段查询包装提供商信息)</p> 
	 * @author 269231
	 * @date 2016-10-13 下午5:38:22
	 * @param entity
	 * @return 
	 */
	@Override
	public PackagingSupplierEntity queryPackagingSupplierByCodes(
			String deptCode, String deptName, String supplierCode,
			String supplierName) {
		// TODO Auto-generated method stub
		return packagingSupplierDao.queryPackagingSupplierByCodes(deptCode, deptName, supplierCode, supplierName);
	}

}
