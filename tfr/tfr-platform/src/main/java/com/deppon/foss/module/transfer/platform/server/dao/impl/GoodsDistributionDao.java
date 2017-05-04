package com.deppon.foss.module.transfer.platform.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.platform.api.server.dao.IGoodsDistributionDao;
import com.deppon.foss.module.transfer.platform.api.shared.domain.GoodsDistributionEntity;

/***
 * 货量流动分布
 * @author 200978
 * 2015-3-10
 */ 
public class GoodsDistributionDao extends iBatis3DaoImpl implements
		IGoodsDistributionDao {
	
	private static final String NAMESPACE = "Foss.platform.goodsDistributionJob.";

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<GoodsDistributionEntity> statisticGoodsDistribution(
			Date startDate, Date statisticDate) {
		Map map = new HashMap();
		map.put("startDate", startDate);
		map.put("statisticDate", statisticDate);
		return this.getSqlSession().selectList(NAMESPACE+"statisticGoodsDistribution", map);
	}

	/**
	 * 根据当前部门查询 他对应的经营本部
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-24
	 * @param code
	 * @return
	 */
	@Override
	public GoodsDistributionEntity queryOperationDeptCodeByCurrentCode(String code){
		return (GoodsDistributionEntity) this.getSqlSession().selectOne(NAMESPACE + "queryOperationDeptCodeByCurrentCode", code);
	}

	@Override
	public void saveGoodsDistribution(
			GoodsDistributionEntity goodsDistributionEntity) {
		this.getSqlSession().insert(NAMESPACE+"saveGoodsDistribution", goodsDistributionEntity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GoodsDistributionEntity> queryGoodsDistributionByDay(
			GoodsDistributionEntity entity) {
		return this.getSqlSession().selectList(NAMESPACE+"queryGoodsDistributionByDay", entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GoodsDistributionEntity> queryGoodsDistributionByMonth(
			GoodsDistributionEntity entity) {
		return this.getSqlSession().selectList(NAMESPACE+"queryGoodsDistributionByMonth", entity);
	}
	
}
