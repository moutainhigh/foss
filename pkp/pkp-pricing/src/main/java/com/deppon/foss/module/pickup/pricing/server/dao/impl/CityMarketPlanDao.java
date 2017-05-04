package com.deppon.foss.module.pickup.pricing.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.pricing.api.server.dao.ICityMarketPlanDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.CityMarketPlanEntity;

public class CityMarketPlanDao  extends SqlSessionDaoSupport implements ICityMarketPlanDao {

	private static final String NAME_SPASE = "com.deppon.foss.module.pickup.pricing.server.dao.CityMarketPlanEntityMapper.";
	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(CityMarketPlanEntity record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(CityMarketPlanEntity record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public CityMarketPlanEntity selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(CityMarketPlanEntity record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(CityMarketPlanEntity record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CityMarketPlanEntity> selectByCondition(
			CityMarketPlanEntity record) {
		return getSqlSession().selectList(NAME_SPASE+"selectByCondition", record);
	}

	@Override
	public Long getCountByCityCode(CityMarketPlanEntity cityMarketPlanEntity) {
		return (Long)getSqlSession().selectOne(NAME_SPASE+"countCityMarketPlanByCondition", cityMarketPlanEntity);
	}
	
	/**
	 * 获取配置参数
	 * @author WangQianJin
	 * @param entity
	 * @return
	 */
	public List<ConfigurationParamsEntity> queryConfigurationParamsByEntity(ConfigurationParamsEntity entity){		
		return this.getSqlSession().selectList(NAME_SPASE + "queryConfigurationParamsByEntity", entity);
	}

}
