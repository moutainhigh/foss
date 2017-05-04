package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IAdministrativeRegionsDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IBillInspectionRemindService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BillInspectionRemindEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.BillInspectionRemindException;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.BillInspectionRemindDao;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class BillInspectionRemindService implements IBillInspectionRemindService{

	/**
	 * 
	 */
	private BillInspectionRemindDao billInspectionRemindDao;
	
	public void setBillInspectionRemindDao(
			BillInspectionRemindDao billInspectionRemindDao) {
		this.billInspectionRemindDao = billInspectionRemindDao;
	}
	

	/**根据条件进行分页查询
	 * 
	 * @param entity
	 * @param start
	 * @param limit
	 * @return
	 */
	@Override
	public List<BillInspectionRemindEntity> queryAll(
			BillInspectionRemindEntity entity, int start, int limit) {
		entity.setActive(FossConstants.ACTIVE);
		return billInspectionRemindDao.queryAll(entity, start, limit);
	}

	/**
	 * 根据条件查询记录数
	 * @param entity
	 * @return
	 */
	@Override
	public Long queryCount(BillInspectionRemindEntity entity) {
		 
		return billInspectionRemindDao.queryCount(entity);
	}

	
	private IAdministrativeRegionsDao administrativeRegionsDao;
	
	public void setAdministrativeRegionsDao(
			IAdministrativeRegionsDao administrativeRegionsDao) {
		this.administrativeRegionsDao = administrativeRegionsDao;
	}
	/**
	 * 新增记录
	 * @param entity
	 * @return
	 */
	@Override
	public long addBillInspectionRemind(BillInspectionRemindEntity entity) {
		
		BillInspectionRemindEntity billInspection=billInspectionRemindDao.queryBillInspectionRemindByRegionCode(entity);
		if(null!=billInspection){
			
			throw new BillInspectionRemindException("数据已经存在！","数据已经存在！"); 
		}

		AdministrativeRegionsEntity regionsEntity=null;
		if(!StringUtils.isEmpty(entity.getProvinceCode())){
			 regionsEntity=administrativeRegionsDao.queryAdministrativeRegionsByCode(entity.getProvinceCode());
			 entity.setProvinceName(regionsEntity.getName());
		}
		if(!StringUtils.isEmpty(entity.getCityCode())){
			regionsEntity=administrativeRegionsDao.queryAdministrativeRegionsByCode(entity.getCityCode());
			 entity.setCityName(regionsEntity.getName());
		}
		if(!StringUtils.isEmpty(entity.getCountyCode())){
			regionsEntity=administrativeRegionsDao.queryAdministrativeRegionsByCode(entity.getCountyCode());
			 entity.setCountyName(regionsEntity.getName());
		}
		if(StringUtils.equals(entity.getRegionLevCode(), "DISTRICT_COUNTY")){
			entity.setRegionName(entity.getProvinceName()+"-"+entity.getCityName()+"-"+entity.getCountyName());
		}else if(StringUtils.equals(entity.getRegionLevCode(), "DISTRICT_CITY")){
			entity.setRegionName(entity.getProvinceName()+"-"+entity.getCityName());
			entity.setCountyName(null);
			entity.setCountyCode(null);
		}else if(StringUtils.equals(entity.getRegionLevCode(), "DISTRICT_PROVINCE")){
			entity.setCityCode(null);
			entity.setCityName(null);
			entity.setCountyName(null);
			entity.setCountyCode(null);
			entity.setRegionName(entity.getProvinceName());
			
		}
		entity.setId(UUIDUtils.getUUID());
		entity.setCreateDate(new Date());
		entity.setCreateUser(FossUserContext.getCurrentInfo().getEmpCode());
		entity.setCreateUserName(FossUserContext.getCurrentInfo().getEmpName());
		entity.setVersionNo(new Date().getTime());
		entity.setActive(FossConstants.ACTIVE);
		entity.setModifyDate(new Date(NumberConstants.ENDTIME));
		return billInspectionRemindDao.addBillInspectionRemind(entity);
	}

	/**
	 * 根据主键删除数据
	 * @param ids
	 * @return
	 */
	@Override
	public long deleteBillInspectionRemind(String[] ids) {
		for(String id:ids){
			BillInspectionRemindEntity entity=billInspectionRemindDao.queryBillInspectionRemindById(id);
			entity.setId(id);	
			entity.setModifyUser(FossUserContext.getCurrentUser().getEmpCode());
			entity.setVersionNo(entity.getVersionNo()+1);
			entity.setActive(FossConstants.INACTIVE);
			entity.setModifyDate(new Date());
			billInspectionRemindDao.deleteBillInspectionRemind(entity);
		}
		return 0;
	}

	/**
	 * 更新数据
	 * @param entity
	 * @return
	 */
	@Override
	public long updateBillInspectionRemind(BillInspectionRemindEntity entity) {
		BillInspectionRemindEntity billInspection=billInspectionRemindDao.queryBillInspectionRemindByRegionCode(entity);
		if(null!=billInspection){
			
			throw new BillInspectionRemindException("数据已经存在！","数据已经存在！"); 
		}
		
		long l=this.deleteBillInspectionRemind(new String[]{entity.getId()});
		if(l<0){
			throw new BillInspectionRemindException("作废失败！","作废失败！"); 
		}
		return this.addBillInspectionRemind(entity);
	}

	/**
	 * 根据省份 城市区县 区域级别 
	 * @param entity
	 * @param regionCode
	 * @param regionLevCode
	 * @return
	 */
	@Override
	public BillInspectionRemindEntity queryBillInspectionRemindByRegionCode(
			OrgAdministrativeInfoEntity entity, String regionCode,
			String regionLevCode) {
		BillInspectionRemindEntity remindEntity=new  BillInspectionRemindEntity();
		if(regionLevCode.equals(FossConstants.DISTRICT_COUNTY_CODE)){
			remindEntity.setProvinceCode(entity.getProvCode());
			remindEntity.setCityCode(entity.getCityCode());
			remindEntity.setCountyCode(entity.getCountyCode());
			remindEntity.setRegionCode(regionCode);
			remindEntity.setRegionLevCode(regionLevCode);
		}else if(regionLevCode.equals(FossConstants.DISTRICT_CITY_CODE)){
			remindEntity.setProvinceCode(entity.getProvCode());
			remindEntity.setCityCode(entity.getCityCode());
			remindEntity.setRegionCode(regionCode);
			remindEntity.setRegionLevCode(regionLevCode);
		}else if(regionLevCode.equals(FossConstants.DISTRICT_PROVINCE_CODE)){
			remindEntity.setProvinceCode(entity.getProvCode());
			remindEntity.setRegionCode(regionCode);
			remindEntity.setRegionLevCode(regionLevCode);
		}
		return billInspectionRemindDao.queryBillInspectionRemindByRegionCode(remindEntity);
	}

}
