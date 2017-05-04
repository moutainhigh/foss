package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.pricing.api.server.dao.ICityMarketPlanDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.ICityMarketPlanService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.CityMarketPlanEntity;
import com.deppon.foss.util.define.FossConstants;

public class CityMarketPlanService implements ICityMarketPlanService {
    
	private ICityMarketPlanDao cityMarketPlanDao;
	private IOrgAdministrativeInfoService OrgAdministrativeInfoService;
	
	/**
	 * 通过部门编码\业务时间,查询大礼包名称(List<entity>) 
	 * @return
	 */
	@Override
	public List<CityMarketPlanEntity> searchCityMarketPlanEntityList(
			String orgCode, Date billDate) {
		CityMarketPlanEntity cityMarketPlanEntity = new CityMarketPlanEntity();
		cityMarketPlanEntity.setActive(FossConstants.YES);
		cityMarketPlanEntity.setBeginTime(billDate);
		cityMarketPlanEntity.setEndTime(billDate);
		OrgAdministrativeInfoEntity org = OrgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(orgCode);
		if(null==org){
			return null;
		}
		String cityCode = org.getCityCode();//通过营业部转换为城市code
		if(StringUtil.isEmpty(cityCode)){
			return null;
		}
		cityMarketPlanEntity.setCityCode(cityCode);
		List<CityMarketPlanEntity> list = cityMarketPlanDao.selectByCondition(cityMarketPlanEntity);
		return list;
	}
	/**
	 * 通过大礼包编码、业务时间、查询大礼包信息entity
	 * @param cityMarketPlanCode
	 * @param billDate
	 * @return
	 */
	@Override
	public CityMarketPlanEntity getCityMarketPlanEntityCode(
			String code,String orgCode, Date billDate) {
		CityMarketPlanEntity cityMarketPlanEntity = new CityMarketPlanEntity();
		cityMarketPlanEntity.setActive(FossConstants.YES);
		cityMarketPlanEntity.setBeginTime(billDate);
		cityMarketPlanEntity.setEndTime(billDate);
		OrgAdministrativeInfoEntity org = OrgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(orgCode);
		if(StringUtil.isEmpty(code)){
			return null; 
		}
		if(null==org){
			return null;
		}
		String cityCode = org.getCityCode();//通过营业部转换为城市code
		if(StringUtil.isEmpty(cityCode)){
			return null;
		}
		cityMarketPlanEntity.setCityCode(cityCode);
		cityMarketPlanEntity.setCode(code);
		List<CityMarketPlanEntity> list = cityMarketPlanDao.selectByCondition(cityMarketPlanEntity);
		if(list.size()==1){
			return list.get(0);
		}
		return null;
	}
	/**
	 * 查询数量(笔记本，电脑等活动)，通过收录部门和业务时间
	 * @param orgCode
	 * @param billDate
	 * @return
	 */
	@Override
	public Long countMarketPlanEntity(String orgCode, Date billDate) {
		CityMarketPlanEntity cityMarketPlanEntity = new CityMarketPlanEntity();
		cityMarketPlanEntity.setActive(FossConstants.YES);
		cityMarketPlanEntity.setBeginTime(billDate);
		cityMarketPlanEntity.setEndTime(billDate);
		OrgAdministrativeInfoEntity org = OrgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(orgCode);
		if(null==org){
			return 0L;
		}
		String cityCode = org.getCityCode();//通过营业部转换为城市code
		if(StringUtil.isEmpty(cityCode)){//如果行政区域没有配置城市，返回0
			return 0L;
		}
		cityMarketPlanEntity.setCityCode(cityCode);
		Long count = cityMarketPlanDao.getCountByCityCode(cityMarketPlanEntity);
		return count;
	}

	public void setCityMarketPlanDao(ICityMarketPlanDao cityMarketPlanDao) {
		this.cityMarketPlanDao = cityMarketPlanDao;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		OrgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	
	/**
	 * 获取配置参数
	 * @author WangQianJin
	 * @param entity
	 * @return
	 */
	public List<ConfigurationParamsEntity> queryConfigurationParamsByEntity(ConfigurationParamsEntity entity){		
		return cityMarketPlanDao.queryConfigurationParamsByEntity(entity);
	}
	
}
