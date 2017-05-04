package com.deppon.foss.module.pickup.pricing.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.CityMarketPlanEntity;

public interface ICityMarketPlanDao {
    int deleteByPrimaryKey(String id);

    int insert(CityMarketPlanEntity record);

    int insertSelective(CityMarketPlanEntity record);

    CityMarketPlanEntity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CityMarketPlanEntity record);

    int updateByPrimaryKey(CityMarketPlanEntity record);
    
    List<CityMarketPlanEntity> selectByCondition(CityMarketPlanEntity record);

	Long getCountByCityCode(CityMarketPlanEntity cityMarketPlanEntity);
	
	/**
	 * 获取配置参数
	 * @author WangQianJin
	 * @param entity
	 * @return
	 */
	List<ConfigurationParamsEntity> queryConfigurationParamsByEntity(ConfigurationParamsEntity entity);
}