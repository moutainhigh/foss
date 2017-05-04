package com.deppon.foss.module.pickup.pricing.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IEcGoodsPriceCriteriaDetailDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceCriteriaDetailEntity;
/**
 * @Description:首续重价格条目明细DAO实现
 * @author 348757-cc
 * @date 2016-07-04
 * @version 1.0
 */
public class EcGoodsPriceCriteriaDetailDao extends SqlSessionDaoSupport implements IEcGoodsPriceCriteriaDetailDao {
	// mapper配置文件计价方式明细ibatis命名空间
	private static final String EC_PRICING_ENTITY_CRITERIADETAIL = "foss.pkp.pkp-pricing.ecGoodsPriceCriteriaDetailEntityMapper.";
	// 根据计费规则ID查询计价明细列表
	private static final String SELECTBYVALUATIONID = "selectByValuationId";
	// 根据计费规则ID批量激活计价明细
	private static final String ACTIVECRITERIADETAILS = "activeCriteriaDetails";
	// 根据计费规则ID批量删除计价明细
	private static final String BATCHDELETECRITERIA = "batchDeleteCriteria";
	
	/**
	 * 根据计费规则ID查询计价明细列表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceCriteriaDetailEntity> selectByValuationId(String valuationId) {
		return getSqlSession().selectList(EC_PRICING_ENTITY_CRITERIADETAIL + SELECTBYVALUATIONID,valuationId);
	}

	/**
	 * 根据计费规则ID批量激活计价明细
	 */
	@Override
	public void activeCriteriaDetails(List<String> valuationIds) {
		if (CollectionUtils.isNotEmpty(valuationIds)) {
			List<List<String>> list = com.deppon.foss.util.CollectionUtils.splitListBySize(valuationIds, NumberConstants.NUMBER_500);
			for (List<String> ids : list) {
				Map<String , Object> map  = new HashMap<String , Object>();
				map.put("valuationIds", ids);
				map.put("versionNo", new Date().getTime());
				getSqlSession().update(EC_PRICING_ENTITY_CRITERIADETAIL + ACTIVECRITERIADETAILS, map);
			}
		}
	}

	/**
	 * 根据计费规则ID批量删除计价明细
	 */
	@Override
	public void batchDeleteCriteria(List<String> valuationIds) {
		//批量500条删一次
		if (CollectionUtils.isNotEmpty(valuationIds)) {
			List<List<String>> list = com.deppon.foss.util.CollectionUtils.splitListBySize(valuationIds, NumberConstants.NUMBER_500);
			for (List<String> ids : list) {
				Map<String ,List<String>> map = new HashMap<String, List<String>>();
				map.put("priceValuationIds", ids);
				getSqlSession().delete(EC_PRICING_ENTITY_CRITERIADETAIL + BATCHDELETECRITERIA, map);
			}
		}
	}

}