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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/dao/impl/PriceValuationDao.java
 * 
 * FILE NAME        	: PriceValuationDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.server.dao.impl;
/**
 * 
 * 计费规则DAO(配合定价项目降价发券方案引用PKP)
 * @author 187862-dujunhui
 * @date 2014-11-11 下午1:46:08
 */
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IPriceValuationDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PriceValuationEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;

public class PriceValuationDao extends SqlSessionDaoSupport implements
		IPriceValuationDao {
	// 计费规则ibatis命名空间
	private static final String PRICING_ENTITY_VALUATION = "foss.bse.bse-baseinfo.priceValuationEntityMapper.";
	// 插入计费规则
	private static final String INSERTSELECTIVE = "insertSelective";
	// 根据条件查询计费规则
	private static final String SELECTBYCONDITION = "selectByCodition";
	// 根据条件查询计费规则
	private static final String QUERYBYCONDITION = "queryByCodition";
	// 根据条件查询计费规则
	private static final String QUERYBYCONDITIONFORDOWNLOAD = "queryByCoditionForDownload";
	// 根据条件查询计费规则
	private static final String SELECTBYCONDITIONSQ = "selectByCoditionSq";
	// 根据条件统计计费规则(特殊)
	private static final String COUNTBYCONDITION = "countByCodition";
	// 激活增值服务
	private static final String ACTIVEVALUEADDED = "activeValueAdded";
	// 修改计费规则
	private static final String UPDATEVALUATION = "updateValuation";
	// 删除未增值服务
	private static final String DELETEVALUEADDED = "deleteValueAdded";
	// 根据主键ID删除计费规则
	private static final String DELETEBYPK = "deleteByPrimaryKey";
	//根据价格计划删除
	private static final String DELETEBYPRICEPLANID = "deleteByPricePlanId";
	// 根据主键ID查询计费规则
	private static final String SELECTBYPK = "selectByPrimaryKey";
	// 根据名称查询
	private static final String SELECTBYNAME = "selectByName";
	/**开单查询增值服务费用计算**/
	//private static final String SEARCH_VALUE_ADDED_PRICING_VALUATION ="searchValueAddedPricingValuationByCondition";
	
	/**开单查询运价服务费用计算**/
	//private static final String FIND_PRICE_VALUATION_BY_CODITION= "findPriceValuationByCodition";
	/**
	 * 
	 * @Description: 根据主键删除
	 * @author FOSSDP-sz
	 * @date 2013-3-17 下午12:00:21
	 * @param id
	 * @return
	 * @version V1.0
	 */
	@Override
	public int deleteByPrimaryKey(String id) {
		return getSqlSession().delete(PRICING_ENTITY_VALUATION + DELETEBYPK, id);
	}
	/**
	 * 
	 * <p>
	 * (插入一条计费规则)
	 * </p>
	 * 
	 * @author 张斌
	 * @date 2012-10-20 上午11:15:04
	 * @param record
	 * @see
	 */
	@Override
	public int insertSelective(PriceValuationEntity record) {
		OrgAdministrativeInfoEntity currentDept  = FossUserContext.getCurrentDept();
		UserEntity currentUser = FossUserContext.getCurrentUser();
	    	record.setCreateUser(currentUser.getEmployee().getEmpCode());//创建人
	    	record.setCreateOrgCode(currentDept.getCode());//创建机构
		return getSqlSession().insert(
				PRICING_ENTITY_VALUATION + INSERTSELECTIVE, record);
	}

	/**
	 * 
	 * <p>
	 * 复制方案 插入计费规则
	 * </p>
	 * 
	 * @author Rosanu
	 * @date 2013-09-18 上午11:15:04
	 * @param record
	 * @see
	 */
	public int insertPricingValuation(PriceValuationEntity record) {
		OrgAdministrativeInfoEntity currentDept  = FossUserContext.getCurrentDept();
		UserEntity currentUser = FossUserContext.getCurrentUser();
	    	record.setCreateUser(currentUser.getEmployee().getEmpCode());//创建人
	    	record.setCreateOrgCode(currentDept.getCode());//创建机构
		return getSqlSession().insert(
				PRICING_ENTITY_VALUATION + "insertPricingValuation", record);
	}
	/**
	 * 
	 * <p>
	 * 根据查询条件查询计费规则(分页)
	 * </p>
	 * 
	 * @author 张斌
	 * @date 2012-10-20 上午11:15:04
	 * @param record
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceValuationEntity> selectByCodition(
			PriceValuationEntity record, int start, int limit) {
		// 设置分页条件
		RowBounds rowBounds = new RowBounds(start, limit);
		return getSqlSession()
				.selectList(PRICING_ENTITY_VALUATION + SELECTBYCONDITION,
						record, rowBounds);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceValuationEntity> selectByCoditionNew(
			PriceValuationEntity record, int start, int limit) {
		// 设置分页条件
		RowBounds rowBounds = new RowBounds(start, limit);
		return getSqlSession()
				.selectList(PRICING_ENTITY_VALUATION + "selectByCoditionNew",
						record, rowBounds);
	}
	/**
	 * 
	 * <p>
	 * 根据id条件查询计费规则
	 * </p>
	 * 
	 * @author 张斌
	 * @date 2012-10-20 上午11:15:04
	 * @param record
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceValuationEntity> selectByCodition(
			PriceValuationEntity record) {
		return getSqlSession()
				.selectList(PRICING_ENTITY_VALUATION + SELECTBYCONDITION,
						record);
	}
	/**
	 * 
	 * <p>
	 * 根据查询条件查询计费规则(分页)
	 * </p>
	 * 
	 * @author 张斌
	 * @date 2012-10-20 上午11:15:04
	 * @param record
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceValuationEntity> selectByCoditionSq(
			PriceValuationEntity record) {
		return getSqlSession()
				.selectList(PRICING_ENTITY_VALUATION + SELECTBYCONDITIONSQ,
						record);
	}
	/**
	 * 
	 * <p>
	 * 根据查询条件统计计费规则
	 * </p>
	 * 
	 * @author 张斌
	 * @date 2012-10-20 上午11:15:04
	 * @param record
	 * @see
	 */
	@Override
	public Long countByCodition(PriceValuationEntity record) {
		return (Long) getSqlSession().selectOne(
				PRICING_ENTITY_VALUATION + COUNTBYCONDITION, record);
	}
	/**
	 * 
	 * <p>
	 * (激活增值服务)
	 * </p>
	 * 
	 * @author 张斌
	 * @date 2012-10-22 上午11:15:04
	 * @param record
	 * @see
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void activeValueAdded(List<String> valuationIds) {
		Map map = new HashMap();
		map.put("valuationIds", valuationIds);
		map.put("versionNo", new Date().getTime());
		getSqlSession().update(PRICING_ENTITY_VALUATION + ACTIVEVALUEADDED,
				map);
	}
	/**
	 * 
	 * <p>
	 * (删除未激活增值服务)
	 * </p>
	 * 
	 * @author 张斌
	 * @date 2012-10-25 上午11:15:04
	 * @param record
	 * @see
	 */
	@Override
	public void deleteValueAdded(List<String> valuationIds) {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("valuationIds", valuationIds);
		getSqlSession().delete(PRICING_ENTITY_VALUATION + DELETEVALUEADDED,
				map);
	}
	/**
	 * 
	 * <p>
	 * 根据id条件查询计费规则
	 * </p>
	 * 
	 * @author 张斌
	 * @date 2012-10-20 上午11:15:04
	 * @param record
	 * @see
	 */
	@Override
	public PriceValuationEntity selectByPrimaryKey(String id) {
		return (PriceValuationEntity) getSqlSession().selectOne(
				PRICING_ENTITY_VALUATION + SELECTBYPK, id);
	}
	/**
	 * 
	 * <p>
	 * 修改计费规则
	 * </p>
	 * 
	 * @author 张斌
	 * @date 2012-10-22 上午11:15:04
	 * @param record
	 * @see
	 */
	@Override
	public int updateValuation(PriceValuationEntity record) {
		return getSqlSession().update(
				PRICING_ENTITY_VALUATION + UPDATEVALUATION, record);
	}
	/**
	 * 
	 * <p>
	 * Description:根据价格方案ID删除记录<br />
	 * </p>
	 * @author 
	 * @version 0.1 2012-11-14
	 * @param pricePlanId
	 * @return
	 * int
	 */
	@Override
	public int deltePriceValuationByPricePlanId(String pricePlanId) {
		return getSqlSession().delete(PRICING_ENTITY_VALUATION + DELETEBYPRICEPLANID, pricePlanId);
	}
	/**
	 * 
	 * <p>根据查询条件查询计费规则(不分页)</p> 
	 * @author sz
	 * @date 2012-12-11 下午4:27:48
	 * @param record
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceValuationEntity> queryByCodition(PriceValuationEntity record) {
		return getSqlSession().selectList(PRICING_ENTITY_VALUATION + QUERYBYCONDITION,	record);
	}
	
	/**
	 * 
	 * <p>根据查询条件查询计费规则(不分页)</p> 
	 * @author sz
	 * @date 2012-12-11 下午4:27:48
	 * @param record
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceValuationEntity> queryByCoditionForDownload(PriceValuationEntity record) {
		return getSqlSession().selectList(PRICING_ENTITY_VALUATION + QUERYBYCONDITIONFORDOWNLOAD,	record);
	}
	
	
	/**
	 * 
	 * <p>分页根据查询条件查询计费规则(不分页)</p> 
	 * @author sz
	 * @date 2012-12-11 下午4:27:48
	 * @param record
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceValuationEntity> queryByCoditionForDownloadByPage(PriceValuationEntity record
			,int start, int limited) {
		RowBounds rowBounds = new RowBounds(start, limited);
		return getSqlSession().selectList(PRICING_ENTITY_VALUATION + QUERYBYCONDITIONFORDOWNLOAD,
				record, rowBounds);
	}
	
	/**
	 * 
	 * <p>
	 * Description:根据名称查询增值服务方案<br />
	 * </p>
	 * @author 
	 * @version 0.1 2013-1-9
	 * @param record
	 * @return
	 * List<PriceValuationEntity>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceValuationEntity> selectByName(PriceValuationEntity record) {
		return getSqlSession().selectList(PRICING_ENTITY_VALUATION + SELECTBYNAME,	record);
	}
	/**
	 * 
	 * <p>下载查询条件查询汽运计费规则(不分页)</p> 
	 * @author sz
	 * @date 2012-12-11 下午4:27:48
	 * @param record
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceValuationEntity> downPricingValuationPricingAutoServerData(PriceValuationEntity record) {
		return getSqlSession().selectList(PRICING_ENTITY_VALUATION + "downPricingValuationPricingAutoServerData",record);
	}
	
	/**
	 * 
	 * <p>下载查询条件查询汽运计费规则(分页)</p> 
	 * @author sz
	 * @date 2012-12-11 下午4:27:48
	 * @param record
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceValuationEntity> downPricingValuationPricingAutoServerDataByPage(
			PriceValuationEntity record, int started, int limited) {
		RowBounds rowBounds = new RowBounds(started, limited);
		return getSqlSession().selectList(PRICING_ENTITY_VALUATION + "downPricingValuationPricingAutoServerData",
				record, rowBounds);
	}
	
	
	
	/**
	 * 
	 * <p>下载查询空运计费规则(不分页)</p> 
	 * @author sz
	 * @date 2012-12-11 下午4:27:48
	 * @param record
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceValuationEntity> downPricingValuationPricingAirServerData(PriceValuationEntity record) {
		return getSqlSession().selectList(PRICING_ENTITY_VALUATION + "downPricingValuationPricingAirServerData",record);
	}
	
	
	/**
	 * 
	 * <p>下载查询空运计费规则(分页)</p> 
	 * @author sz
	 * @date 2012-12-11 下午4:27:48
	 * @param record
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceValuationEntity> 
		downPricingValuationPricingAirServerDataByPage(PriceValuationEntity record, int started, int limited) {
		RowBounds rowBounds = new RowBounds(started, limited);
		return getSqlSession().selectList(PRICING_ENTITY_VALUATION + "downPricingValuationPricingAirServerData",
				record,rowBounds );
	}
	
	
	
}