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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/dao/impl/PricePlanDao.java
 * 
 * FILE NAME        	: PricePlanDao.java
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

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IBigGoodsPricePlanDao;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PricePlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryExistPricePlanDetailBean;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryPricePlanDetailBean;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ResultPricePlanDetailBean;

/**
 * 
 * @author DP-Foss-YueHongJie
 * @date 2012-12-12 下午3:05:57
 * @version 1.0
 */
public class BigGoodsPricePlanDao extends SqlSessionDaoSupport implements  IBigGoodsPricePlanDao{
	/**
	 * ibatis mapper namespace
	 */
    private static final String NAME_SPACE = "foss.pkp.pkp-pricing.BigGoodsPricePlanEntityMapper.";
    /**
     * 
     * <p>(删除价格方案)</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-12 下午3:06:29
     * @param id
     * @return
     * @see
     */
    @Override
    public int deleteByPrimaryKey(String id) {
	return getSqlSession().delete(NAME_SPACE + "deleteByPrimaryKey",id);	
    }
    /**
     * 
     * <p>(新增价格方案,所有列)</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-12 下午3:07:13
     * @param record
     * @return
     * @see
     */
    @Override
    public int insert(PricePlanEntity entity) {
	OrgAdministrativeInfoEntity currentDept  = FossUserContext.getCurrentDept();
	UserEntity currentUser = FossUserContext.getCurrentUser();
	entity.setCreateUser(currentUser.getEmployee().getEmpCode());
	entity.setCreateOrgCode(currentDept.getCode());
	entity.setModifyUser(currentUser.getEmployee().getEmpCode());
	return getSqlSession().insert(NAME_SPACE + "insert",entity);	
    }
    /**
     * 
     * <p>(新增价格方案,根据属性值是否为NULL决定是否插入)</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-12 下午3:07:29
     * @param record
     * @return
     * @see
     */
    @Override
    public int insertSelective(PricePlanEntity record) {
	OrgAdministrativeInfoEntity currentDept  = FossUserContext.getCurrentDept();
	UserEntity currentUser = FossUserContext.getCurrentUser();
	record.setCreateUser(currentUser.getEmployee().getEmpCode());
	record.setCreateOrgCode(currentDept.getCode());
	record.setModifyUser(currentUser.getEmployee().getEmpCode());
	record.setVersionNo(new Date().getTime());
    	return getSqlSession().insert(NAME_SPACE + "insertSelective",record);	
    }
    /**
     * 
     * <p>(查询价格方案主信息)</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-12 下午3:09:24
     * @param id
     * @return
     * @see
     */
    @Override
    public PricePlanEntity selectByPrimaryKey(String id) {
		return (PricePlanEntity)getSqlSession().selectOne(NAME_SPACE + "selectByPrimaryKey", id);
    }
    /**
     * 
     * <p>(修改价格方案)</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-12 下午3:09:53
     * @param record
     * @return
     * @see
     */
    @Override
    public int updateByPrimaryKeySelective(PricePlanEntity record) {
    	return getSqlSession().update(NAME_SPACE + "updateByPrimaryKeySelective", record);
    }
    /**
     * 
     * <p>(时间，始发区域查询最新价格版本信息)</p> 
     * @author 岳洪杰
     * @date 2012-10-13 上午9:37:32
     * @param beginTime 激活时间
     * @param deptRegionId 始发区域
     * @param active
     * @return
     * @see
     */
    @SuppressWarnings("unchecked")
    public PricePlanEntity findPricePlanByDeptRegionId(Date beginTime, String deptRegionId,String active) {
	@SuppressWarnings("rawtypes")
	Map p = new HashMap();
	p.put("deptRegionId", deptRegionId);
	p.put("billDate", beginTime);
	p.put("active", active);
	return (PricePlanEntity) getSqlSession().selectOne(NAME_SPACE + "findPricePlanByDownload",p);
    }
    /**
     * <p>
     * Description:根据区域ID且当前日期大于生效日期小于结束日期查询价格方案列表<br />
     * </p>
     * @author zhangwei
     * @version 0.1 2012-11-6
     * @param cuurentTime  当前时间
     * @param deptRegionId 始发区域ID 
     * @param active  激活状态
     * @return
     * List<PricePlanEntity>
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<PricePlanEntity> findPricePlanByDownload(String deptRegionId,Date billDate, String active) {
	@SuppressWarnings("rawtypes")
	Map p = new HashMap();
	p.put("deptRegionId", deptRegionId);
	p.put("billDate", billDate);
	p.put("active", active);
	return getSqlSession().selectList(NAME_SPACE + "findPricePlanByDownload",p);
    }
    /**
     * 
     * <p>(查询价格方案批次信息)</p> 
     * @author 岳洪杰
     * @date 2012-11-21 上午9:37:32
     * @param cuurentTime
     * @param deptRegionId
     * @return
     * @see
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<PricePlanEntity> queryPricePlanBatchInfo(
	    PricePlanEntity pricePlanEntity, int start, int limit) {
	RowBounds rowBounds = new RowBounds(start,limit);
	return getSqlSession().selectList(NAME_SPACE + "queryPricePlanBatchInfo", pricePlanEntity, rowBounds);
    }
    /**
     * 
     *
     * @Function: com.deppon.foss.module.pickup.pricing.server.dao.impl.PricePlanDao.queryComparePricePlanBatchInfo
     * @Description:根据名称和始发区域查询价格比对信息
     *
     * @param pricePlanEntity
     * @param start
     * @param limit
     * @return
     *
     * @version:v1.0
     * @author:130376-YANGKANG
     * @date:2014-5-20 下午3:46:21
     *
     * Modification History:
     * Date         Author      Version     Description
     * -----------------------------------------------------------------
     * 2014-5-20    130376-YANGKANG      v1.0.0         create
     */
    @SuppressWarnings("unchecked")
    @Override
	public List<PricePlanEntity> queryComparePricePlanBatchInfo(
	    PricePlanEntity pricePlanEntity, int start, int limit) {
	RowBounds rowBounds = new RowBounds(start,limit);
	return getSqlSession().selectList(NAME_SPACE + "queryComparePricePlanBatchInfo", pricePlanEntity, rowBounds);
    }
    /**
     * 
     * <p>(查询价格方案批次信息)</p> 
     * @author 岳洪杰
     * @date 2012-11-21 上午9:37:32
     * @param cuurentTime
     * @param deptRegionId
     * @return
     * @see
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<PricePlanEntity> queryPricePlanBatchInfo(PricePlanEntity pricePlanEntity) {
    return getSqlSession().selectList(NAME_SPACE + "queryPricePlanBatchInfo", pricePlanEntity);
    }
    /**
     * 
     * queryPricePlanBatchInfoCount(查询分页总记录数)
     * @author DP-Foss-YueHongJie
     * @param record
     * @return 
     *  Long 总记录数
     * @exception 
     * @since  1.0.0
     */
    @Override
    public Long queryPricePlanBatchInfoCount(PricePlanEntity pricePlanEntity) {
	return (Long) getSqlSession().selectOne(NAME_SPACE + "queryPricePlanBatchInfoCount", pricePlanEntity);
    }
    /**
     * 
     * queryPricePlanDetailInfo(查询价格方案明细)
     * @param queryPricePlanDetailBean
     * @author DP-Foss-YueHongJie
     * @return 
     * List<ResultPricePlanDetailBean>
     * @exception 
     * @since  1.0.0
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ResultPricePlanDetailBean> queryPricePlanDetailInfo(
	    QueryPricePlanDetailBean queryPricePlanDetailBean, int start,
	    int limit) {
	RowBounds row = new RowBounds(start,limit);
	return getSqlSession().selectList(NAME_SPACE + "queryPricePlanDetailInfo", queryPricePlanDetailBean,row);
    }
    /**
     * 
     * queryPricePlanDetailInfoCount(查询价格方案明细总记录数)
     * @param queryPricePlanDetailInfoCount
     * @author DP-Foss-YueHongJie
     * @return 
     * Long
     * @exception 
     * @since  1.0.0
     */
    @Override
    public Long queryPricePlanDetailInfoCount(
	    QueryPricePlanDetailBean queryPricePlanDetailBean) {
	return (Long) getSqlSession().selectOne(NAME_SPACE + "queryPricePlanDetailInfoCount", queryPricePlanDetailBean);
    }
    /**
     * 
     * <p>(查询价格方案明细-不分页)</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-12 下午5:02:24
     * @param queryPricePlanDetailBean
     * @return
     * @see
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ResultPricePlanDetailBean> queryPricePlanDetailInfo(
	    QueryPricePlanDetailBean queryPricePlanDetailBean) {
	return getSqlSession().selectList(NAME_SPACE + "queryPricePlanDetailInfo", queryPricePlanDetailBean);
    }
    /**
     * 
     * <p>(查询同一个出发地方案下已经出现的重复目的地信息)</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-17 上午8:23:00
     * @param queryExistPricePlanDetailBean
     * @return
     * @see
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public List<ResultPricePlanDetailBean> isExistRpeatPricePlanDetailData(QueryExistPricePlanDetailBean queryExistPriceBean) {
	Map  parameterMap = new HashMap();
	parameterMap.put("beginTime", queryExistPriceBean.getBeginTime());
	parameterMap.put("active", queryExistPriceBean.getActive());
	parameterMap.put("priceRegionId",queryExistPriceBean.getPriceRegionId());
	parameterMap.put("arrvRegionId", queryExistPriceBean.getArrvRegionId());
	parameterMap.put("centralizePickup", queryExistPriceBean.getIsCentralizePickup());
	parameterMap.put("productCode", queryExistPriceBean.getProductCode());
	parameterMap.put("goodsTypeCode", queryExistPriceBean.getGoodsTypeCode());
	parameterMap.put("valuationType", PricingConstants.VALUATION_TYPE_PRICING);
	parameterMap.put("caculateType", queryExistPriceBean.getCaculateType());
	parameterMap.put("leftRange", queryExistPriceBean.getLeftRange());
	parameterMap.put("rightRange", queryExistPriceBean.getRightRange());
	return getSqlSession().selectList(NAME_SPACE +"isExistRpeatPricePlanDetailData",parameterMap);
    }
    
    /**
     * 
     * <p>(查询同一个出发地方案下已经出现的重复目的地空运价格信息)</p> 
     * @param queryExistPricePlanDetailBean
     * @return
     * @see
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public List<ResultPricePlanDetailBean> isExistRpeatAirLinePricePlanDetailData(QueryExistPricePlanDetailBean queryExistPriceBean) {
    	Map  parameterMap = new HashMap();
    	parameterMap.put("beginTime", queryExistPriceBean.getBeginTime());
    	parameterMap.put("endTime", queryExistPriceBean.getEndTime());
    	parameterMap.put("active", queryExistPriceBean.getActive());
    	parameterMap.put("priceRegionId",queryExistPriceBean.getPriceRegionId());
    	parameterMap.put("arrvRegionId", queryExistPriceBean.getArrvRegionId());
    	parameterMap.put("centralizePickup", queryExistPriceBean.getIsCentralizePickup());
    	parameterMap.put("productCode", queryExistPriceBean.getProductCode());
    	parameterMap.put("goodsTypeCode", queryExistPriceBean.getGoodsTypeCode());
    	parameterMap.put("valuationType", PricingConstants.VALUATION_TYPE_PRICING);
    	return getSqlSession().selectList(NAME_SPACE +"isExistRpeatAirLinePricePlanDetailData",parameterMap);
    }
    
    /**
     * 
     * <p>(新增方案查询同一个出发地方案下已经出现的重复目的地信息)</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-19 下午7:45:09
     * @param queryExistPricePlanDetailBean
     * @return
     * @see
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<ResultPricePlanDetailBean> isExistRpeatPricePlanDetailForEdit(QueryExistPricePlanDetailBean queryExistPriceBean) {
  	Map  parameterMap = new HashMap();
  	parameterMap.put("pricePlanId",queryExistPriceBean.getPricePlanId());
  	parameterMap.put("arrvRegionId", queryExistPriceBean.getArrvRegionId());
  	parameterMap.put("centralizePickup", queryExistPriceBean.getIsCentralizePickup());
  	parameterMap.put("productCode", queryExistPriceBean.getProductCode());
  	parameterMap.put("goodsTypeCode", queryExistPriceBean.getGoodsTypeCode());
  	parameterMap.put("flightTypeCode", queryExistPriceBean.getFlightShift());
  	parameterMap.put("valuationId", queryExistPriceBean.getValuationId());
  	parameterMap.put("combBillTypeCode", queryExistPriceBean.getCombBillTypeCode());
  	//新增开始范围  结束范围  计费类别   作为判断重复的维度
  	parameterMap.put("leftRange", queryExistPriceBean.getLeftRange());
  	parameterMap.put("rightRange",queryExistPriceBean.getRightRange());
  	parameterMap.put("caculateType",queryExistPriceBean.getCaculateType());
  	return getSqlSession().selectList(NAME_SPACE +"isExistRpeatPricePlanDetailForEdit",parameterMap);
      }
    /**
     * 
     * <p>(激活价格方案)</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-17 下午3:09:24
     * @param pricePlanIds
     * @see
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void activePricePlan(List<String> pricePlanIds) {
	Map map = new HashMap();
	map.put("pricePlanIds", pricePlanIds);
	map.put("versionNo", new Date().getTime());
	getSqlSession().selectList(NAME_SPACE +"activePricePlan",map);
    }
}