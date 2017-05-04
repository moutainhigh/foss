/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 BSE TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-pricing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/dao/impl/PriceCriteriaDetailDao.java
 * 
 * FILE NAME        	: PriceCriteriaDetailDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPriceCriteriaDetailDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceCriteriaDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceFibelPaperPackingEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceValuationEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.pop.PopPriceDetailSectionEntity;

public class PriceCriteriaDetailDao extends SqlSessionDaoSupport implements
		IPriceCriteriaDetailDao {
	// 计价方式明细ibatis命名空间
	private static final String PRICING_ENTITY_CRITERIADETAIL = "foss.pkp.pkp-pricing.priceCriteriaDetailEntityMapper.";
	// 插入计价方式明细
	private static final String INSERTSELECTIVE = "insertSelective";
	// 按照计费规则ID查询计价方式明细
	private static final String SELECTBYVALUATIONID = "selectByValuationId";
	// 修改计价方式明细 
	private static final String UPDATECRITERIADETAIL = "updateCriteriaDetailByPrimaryKey";
	// 根据计费规则ID修改计价方式明细 
	private static final String UPDATECRITERIADETAILBYPVID = "updateCriteriaDetailByPricingValuationId";
	
	// 根据计费规则ID激活计价规则明细
	private static final String ACTIVECRITERIADETAIL = "activeCriteriaDetail";
	// 根据计费规则ID激活计价规则明细
	private static final String DELETECRITERIADETAIL = "deleteCriteriaDetail";
	// 根据计费规则ID删除计价规则明细
	private static final String DELETEBYPRICEVALUATIONID = "deleteByPriceValuationId";
	// 根据主键ID删除计价规则明细
	private static final String DELETEBYPK = "deleteByPrimaryKey";
	// 根据主键ID查询计价规则明细
	private static final String SELECTBYPK = "selectByPrimaryKey";
	//查询计价明细
	private static final String SELECT_CRITERIA_DETAIL_ = "findPriceCriteriaDetailByCondition";
	
	/**
	 * 按照计费规则ID查询纸纤包装计价方式明细
	 * @author:218371-foss-zhaoyanjun
	 * @date:2014-11-17下午18:49
	 */
	private static final String SELECTBYVALUATIONIDANDCODE = "selectByValuationIdAndCode";

	/**
	 * 
	 * <p>删除单个计价明细</p> 
	 * @author DP-Foss-YueHongJie
	 * @date 2012-12-24 下午7:36:27
	 * @param id
	 * @return
	 * @see
	 */
	@Override
	public int deleteByPrimaryKey(String id) {
		return getSqlSession().delete(PRICING_ENTITY_CRITERIADETAIL + DELETEBYPK, id);
	}
	/**
	 * 
	 * <p>
	 * (插入一条计价方式明细)
	 * </p>
	 * 
	 * @author 张斌
	 * @date 2012-10-20 上午11:15:04
	 * @param record
	 * @see
	 */
	@Override
	public int insertSelective(PriceCriteriaDetailEntity record) {
		//创建时间
		record.setCreateDate(new Date());
		//修改时间
		record.setModifyDate(record.getCreateDate());
		return getSqlSession().insert(
				PRICING_ENTITY_CRITERIADETAIL + INSERTSELECTIVE, record);
	}

	/**
	 * 
	 * <p>
	 * 复制方案 插入计价方式明细
	 * </p>
	 * 
	 * @author Rosanu
	 * @date 2013-09-18 上午11:15:04
	 * @param record
	 * @see
	 */
	public int insertPricingCriteriaDetail(PriceCriteriaDetailEntity record) {
		//创建时间
		record.setCreateDate(new Date());
		//修改时间
		record.setModifyDate(record.getCreateDate());
		return getSqlSession().insert(
				PRICING_ENTITY_CRITERIADETAIL + "insertPricingCriteriaDetail", record);
	}
	
	/**
	 * 
	 * <p>
	 * (原装插入)
	 * </p>
	 * 
	 * @author 张斌
	 * @date 2012-10-20 上午11:15:04
	 * @param record
	 * @see
	 */
	@Override
	public void copyOriginalSelective(PriceCriteriaDetailEntity record) {
		//创建时间
		record.setCreateDate(record.getCreateDate()==null ? new Date():record.getCreateDate());
		//修改时间
		record.setModifyDate(new Date());
		getSqlSession().insert(PRICING_ENTITY_CRITERIADETAIL + "copyOriginalSelective", record);
	}

	/**
	 * 
	 * <p>
	 * (根据计费过则ID查询计价方式明细列表)
	 * </p>
	 * 
	 * @author 张斌
	 * @date 2012-10-20 上午11:15:04
	 * @param record
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceCriteriaDetailEntity> selectByValuationId(
			String valuationId) {
		return getSqlSession().selectList(
				PRICING_ENTITY_CRITERIADETAIL + SELECTBYVALUATIONID,
				valuationId);
	}
	/**
	 * 
	 * @Description: 根据主键查询
	 * @author FOSSDP-sz
	 * @date 2013-3-17 上午11:46:09
	 * @param id
	 * @return
	 * @version V1.0
	 */
	@Override
	public PriceCriteriaDetailEntity selectByPrimaryKey(String id) {
		return (PriceCriteriaDetailEntity)getSqlSession().selectOne(PRICING_ENTITY_CRITERIADETAIL + SELECTBYPK, id);
	}
	/**
	 * 
	 * <p>
	 * 修改计价方式明细，查询条件是ID
	 * </p>
	 * 
	 * @author 张斌
	 * @date 2012-10-22 上午11:15:04
	 * @param record
	 * @see
	 */
	@Override
	public int updateCriteriaDetailByPrimaryKey(PriceCriteriaDetailEntity record) {
		//修改时间
		record.setModifyDate(new Date());
		return getSqlSession().update(
				PRICING_ENTITY_CRITERIADETAIL + UPDATECRITERIADETAIL, record);
	}
	/**
	 * 
	 * <p>
	 * 根据计费规则ID修改计价方式明细，查询条件是ID
	 * </p>
	 * 
	 * @author Rosanu
	 * @date 2012-10-22 上午11:15:04
	 * @param record
	 * @see
	 */
	public int updateCriteriaDetailByPricingValuationId(PriceCriteriaDetailEntity record) {
		//修改时间
		record.setModifyDate(record.getCreateDate());
		return getSqlSession().update(
				PRICING_ENTITY_CRITERIADETAIL + UPDATECRITERIADETAILBYPVID, record);
	}
	
	/**
	 * 
	 * <p>
	 * 根据计费规则ID激活计价规则明细
	 * </p>
	 * 
	 * @author 张斌
	 * @date 2012-10-24 上午11:15:04
	 * @param record
	 * @see
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void activeCriteriaDetailByValuationIds(List<String> valuationIds) {
		Map map  = new HashMap();
		map.put("valuationIds", valuationIds);
		map.put("versionNo", new Date().getTime());
		 getSqlSession().update(
				PRICING_ENTITY_CRITERIADETAIL + ACTIVECRITERIADETAIL, map);
	}
	/**
	 * 
	 * <p>
	 * 根据计费规则ID删除计价规则明细
	 * </p>
	 * 
	 * @author 张斌
	 * @date 2012-10-25 上午11:15:04
	 * @param record
	 * @see
	 */
	@Override
	public void deleteCriteriaDetailByValuationIds(List<String> valuationIds) {
		Map<String,List<String>> map  = new HashMap<String,List<String>>();
		map.put("valuationIds", valuationIds);
		getSqlSession().delete(
				PRICING_ENTITY_CRITERIADETAIL + DELETECRITERIADETAIL, map);
	}
	/**
	 * 
	 * <p>
	 * (查询各种费用明细)
	 * </p>
	 * 
	 * @author 岳洪杰
	 * @date 2012-10-13 上午11:15:04
	 * @param entity 计价方式明细实体
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceCriteriaDetailEntity> findPriceCriteriaDetailByCondition(
		PriceCriteriaDetailEntity entity) {
		return getSqlSession().selectList(PRICING_ENTITY_CRITERIADETAIL + SELECT_CRITERIA_DETAIL_, entity);
	}
	/**
	 * 根据计费规则ID删除计价规则明细
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author sz
	 * @date 2012-12-7 下午5:25:25
	 * @param valuationId
	 * @see
	 */
	@Override
	public void deleteCriteriaDetailByValuationId(String valuationId) {
		getSqlSession().delete(PRICING_ENTITY_CRITERIADETAIL + DELETEBYPRICEVALUATIONID, valuationId);
	}
	/**
	 * 
	 * <p>(根据主键集合ID查询相关计价明细实体信息)</p> 
	 * @author DP-Foss-YueHongJie
	 * @date 2012-12-14 下午5:49:17
	 * @param keys
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceCriteriaDetailEntity> selectByPrimaryKeyList(List<String> keys) {
	    @SuppressWarnings("rawtypes")
	    Map map = new HashMap();
	    map.put("keys", keys);
	    return getSqlSession().selectList(PRICING_ENTITY_CRITERIADETAIL + "selectByPrimaryKeyList", map);
	}
	/**
	 * 
	 * @Description: 数据下载专用
	 * @author FOSSDP-sz
	 * @date 2013-4-10 下午6:09:44
	 * @param entity
	 * @return
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceCriteriaDetailEntity> downLoadPriceCriteriaDetailByCondition(PriceValuationEntity entity) {
		return getSqlSession().selectList(PRICING_ENTITY_CRITERIADETAIL + "downLoadPriceCriteriaDetailByCondition", entity);
	}
	
	/**
	 * 根据Id查询出CriteriaDetailEntity信息和纸纤包装信息
	 * @author:218371-foss-zhaoyanjun
	 * @date:2014-11-24上午11:21
	 */
	@Override
	public List<PriceFibelPaperPackingEntity> selectByValuationIdAndCode(
			String valuationId) {
		return getSqlSession().selectList(
				PRICING_ENTITY_CRITERIADETAIL + SELECTBYVALUATIONIDANDCODE,
				valuationId);
	}
	
	/**
	 * 根据Id查询出CriteriaDetailEntity信息和纸纤包装信息
	 * @author:218371-foss-zhaoyanjun
	 * @date:2014-11-24上午11:21
	 */
	@Override
	public List<PriceFibelPaperPackingEntity> selectByValuationIdAndCodeTrue(
			String valuationId) {
		return getSqlSession().selectList(
				PRICING_ENTITY_CRITERIADETAIL + SELECTBYVALUATIONIDANDCODE+"True",
				valuationId);
	}
	/**
	 * 批量插入规则明细分段
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void insertDetailSectionList(List<PopPriceDetailSectionEntity> list){
		int i = getSqlSession().insert(PRICING_ENTITY_CRITERIADETAIL + "insertDetailSectionList", list);
		System.out.println(i);
	}
}