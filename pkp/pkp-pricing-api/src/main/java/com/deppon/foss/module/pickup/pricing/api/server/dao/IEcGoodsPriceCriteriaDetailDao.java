package com.deppon.foss.module.pickup.pricing.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceCriteriaDetailEntity;
/**
 * @Description:首续重价格条目明细DAO接口
 * @author 348757-cc
 * @date 2016-07-04
 * @version 1.0
 */
public interface IEcGoodsPriceCriteriaDetailDao {
	
	/**
	 * 根据计费过则ID查询计价方式明细列表
	 */
	List<PriceCriteriaDetailEntity> selectByValuationId(String valuationId);
	
	/**
	 * 根据计费规则ID激活计价规则明细
	 */
	void activeCriteriaDetails(List<String> valuationIds);
	
	/**
	 * 批量删除条件信息
	 */
	void batchDeleteCriteria(List<String> valuationIds);
}