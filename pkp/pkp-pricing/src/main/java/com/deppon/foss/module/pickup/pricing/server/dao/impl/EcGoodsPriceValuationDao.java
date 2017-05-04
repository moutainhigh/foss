package com.deppon.foss.module.pickup.pricing.server.dao.impl;

import java.util.*;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IEcGoodsPriceValuationDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceValuationEntity;
/**
 * @Description:首续重价格规则DAO实现
 * @author 348757-cc
 * @date 2016-07-04
 * @version 1.0
 */
public class EcGoodsPriceValuationDao extends SqlSessionDaoSupport implements IEcGoodsPriceValuationDao {
	// 计费规则ibatis命名空间
	private static final String EC_PRICING_ENTITY_VALUATION = "foss.pkp.pkp-pricing.ecGoodsPriceValuationEntityMapper.";
	// 根据条件查询计费规则
	private static final String SELECTBYCONDITION = "selectByCodition";
	// 激活增值服务
	private static final String ACTIVEVALUATIONS = "activeValuations";
	// 修改计费规则
	private static final String UPDATEVALUATION = "updateValuation";
	// 修改计费规则
	private static final String BATCHDLELETEVALUATION = "batchDleleteValuation";

	/**
	 * 根据id条件查询计费规则
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceValuationEntity> selectByCodition(PriceValuationEntity record) {
		return getSqlSession().selectList(EC_PRICING_ENTITY_VALUATION + SELECTBYCONDITION,record);
	}

	/**
	 * 激活规则
	 */
	@Override
	public int activeValuations(String pricePlanId ,Date beginTime) {
		Map<String , Object> map = new HashMap<String , Object>();
		map.put("pricePlanId", pricePlanId);
		map.put("versionNo", new Date().getTime());
		map.put("beginTime", beginTime);
		//添加修改人工号
		addModifyUser(map);
		return getSqlSession().update(EC_PRICING_ENTITY_VALUATION + ACTIVEVALUATIONS,map);
	}

	/**
	 * 中止计费规则
	 */
	@Override
	public int updateValuation(String pricePlanId ,Date endTime) {
		Map<String , Object> map = new HashMap<String , Object>();
		map.put("pricePlanId", pricePlanId);
		map.put("endTime", endTime);
		map.put("versionNo", new Date().getTime());
		//添加修改人工号
		addModifyUser(map);
		return getSqlSession().update(EC_PRICING_ENTITY_VALUATION + UPDATEVALUATION, map);
	}

	/**
	 * 根据价格方案ID批量删除记录
	 */
	@Override
	public int batchDleleteValuation(List<String> pricePlanIds) {
		Map<String ,List<String>> map = new HashMap<String ,List<String>>();
		map.put("pricePlanIds", pricePlanIds);
		return getSqlSession().delete(EC_PRICING_ENTITY_VALUATION + BATCHDLELETEVALUATION,map);
	}

	private void addModifyUser(Map<String,Object> param){
		if(param==null){
			return;
		}
		UserEntity user = FossUserContext.getCurrentUser();
		param.put("modifyUser", user==null?null:user.getUserName());//userName为工号
	}

}