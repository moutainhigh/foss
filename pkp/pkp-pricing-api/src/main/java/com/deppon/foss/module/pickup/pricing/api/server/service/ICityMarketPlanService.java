package com.deppon.foss.module.pickup.pricing.api.server.service;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.CityMarketPlanEntity;

public interface ICityMarketPlanService {
/**
 * 通过部门编码\业务时间,查询大礼包名称(List<entity>) 
 * @return
 */
	public List<CityMarketPlanEntity> searchCityMarketPlanEntityList(String deptcode,Date billDate);
/**
 * 通过大礼包编码、业务时间、查询大礼包信息entity
 * @param cityMarketPlanCode
 * @param billDate
 * @return
 */
	public CityMarketPlanEntity getCityMarketPlanEntityCode(String code,String deptcode,Date billDate);
/**
 * 查询数量(笔记本，电脑等活动)
 * @param deptcode
 * @param billDate
 * @return
 */
	public Long countMarketPlanEntity(String deptcode,Date billDate);
	/**
	 * 获取配置参数
	 * @author WangQianJin
	 * @param entity
	 * @return
	 */
	public List<ConfigurationParamsEntity> queryConfigurationParamsByEntity(ConfigurationParamsEntity entity);	
	
}
