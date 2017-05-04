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

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPricePlanDao;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceCriteriaDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PricePlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceValuationEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.pop.PopPriceDetailSectionEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryExistPricePlanDetailBean;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryPricePlanDetailBean;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ResultPricePlanDetailBean;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.PriceBusinessException;
import com.deppon.foss.util.NumberUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 *  价格方案DAO
 * @author DP-Foss-YueHongJie
 * @date 2012-12-12 下午3:05:57
 * @version 1.0
 */
public class PricePlanDao extends SqlSessionDaoSupport implements  IPricePlanDao{
	/**
	 * ibatis mapper namespace
	 */
    private static final String NAME_SPACE = "foss.pkp.pkp-pricing.PricePlanEntityMapper.";
    
	/**记录日志*/
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
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
	@SuppressWarnings("unchecked")
	@Override
	public List<ResultPricePlanDetailBean> isExistRpeatPricePlanDetailDataForCustomer(
			QueryExistPricePlanDetailBean queryExistPriceBean) {
		Map<String , Object>  parameterMap = new HashMap<String , Object>();
		parameterMap.put("pricePlanId",queryExistPriceBean.getPricePlanId());
		parameterMap.put("beginTime", queryExistPriceBean.getBeginTime());
		parameterMap.put("active", queryExistPriceBean.getActive());
		parameterMap.put("priceRegionId",queryExistPriceBean.getPriceRegionId());
		parameterMap.put("arrvRegionId", queryExistPriceBean.getArrvRegionId());
		parameterMap.put("centralizePickup", queryExistPriceBean.getIsCentralizePickup());
		parameterMap.put("productCode", queryExistPriceBean.getProductCode());
		parameterMap.put("goodsTypeCode", queryExistPriceBean.getGoodsTypeCode());
		parameterMap.put("valuationType", PricingConstants.VALUATION_TYPE_PRICING);
		return getSqlSession().selectList(NAME_SPACE +"isExistRpeatPricePlanDetailDataForCustomer",parameterMap);
	}
	@Override
	public Long queryPricePlanForCustomer(PricePlanEntity pricePlanEntity) {
		return (Long) getSqlSession().selectOne(NAME_SPACE + "queryPricePlanForCustomer", pricePlanEntity);
	}
	
	@Override
	public int insertPricePlanAllBatch(List<PricePlanEntity> pricePlanBatch, List<PriceValuationEntity> priceValuationBatch, 
			List<PriceCriteriaDetailEntity> priceCriteriaDetailEntityBatch, List<PopPriceDetailSectionEntity> popPriceDetailSectionBatch) {
		if(CollectionUtils.isNotEmpty(pricePlanBatch)){
			Connection con = null;
			PreparedStatement ps = null;
			try {
				//获取连接
				con = getSqlSession().getConfiguration().getEnvironment().getDataSource().getConnection();
				con.setAutoCommit(false);
				
				this.insertPricePlanBatchList(con, ps, pricePlanBatch);
				
				pricePlanBatch.clear();//释放内存
				
				this.insertPriceValuationBatchList(con, ps, priceValuationBatch);
				
				priceValuationBatch.clear();//释放内存
				
				this.insertPriceCriteriaDetailBatchList(con, ps, priceCriteriaDetailEntityBatch);
				
				priceCriteriaDetailEntityBatch.clear();//释放内存
				
				this.insertPopPriceDetailSectionBatchList(con, ps, popPriceDetailSectionBatch);
				
				popPriceDetailSectionBatch.clear();//释放内存
				
			} catch (Exception e) {
				//记录异常日志
				LOGGER.error("插入价格方案表时发生异常：" + e.getMessage());
				try {
					//回滚
					if(con != null){
						con.rollback();
					}
				} catch (SQLException e1) {
					//记录异常日志
					LOGGER.error("事务回滚发生异常：" + e1.getMessage());
					throw new PriceBusinessException(e1.getMessage());
				}
				throw new PriceBusinessException(e.getMessage());
			}finally{
				//关闭ps
				try {
					if(ps != null){
						ps.close();
					}
				} catch (SQLException e) {
					try {
						//回滚
						if(con != null){
							con.rollback();
						}
					} catch (SQLException e1) {
						//记录异常日志
						LOGGER.error("事务回滚发生异常：" + e1.getMessage());
						throw new PriceBusinessException(e1.getMessage());
					}
					//记录ps关闭异常信息
					LOGGER.error("关闭prepareStatement异常：" + e.getMessage());
				}finally{
					if(con != null){
						try {
							//关闭connection
							con.close();
						} catch (SQLException e) {
							try {
								//回滚
								con.rollback();
							} catch (SQLException e1) {
								//记录异常日志
								LOGGER.error("事务回滚发生异常：" + e1.getMessage());
								throw new PriceBusinessException(e1.getMessage());
							}
							//记录关闭con异常日志
							LOGGER.error("关闭connection异常：" + e.getMessage());
						}
					}
				}
			}
		}
		return FossConstants.SUCCESS;
	}
	/**
	 * 
	 * 批量导入价格方案-方案表
	 * @param pricePlanBatch
	 * @return huangwei 2015-12-05
	 * @throws SQLException   
	 */
	public void insertPricePlanBatchList(Connection con, PreparedStatement ps, List<PricePlanEntity> pricePlanBatch) throws SQLException  {
		if(CollectionUtils.isNotEmpty(pricePlanBatch)){
			int count = 0;
			ps = con.prepareStatement("insert into PKP.T_SRV_PRICE_PLAN(ID, T_SRV_PRICE_REGION_ID, T_SRV_PRICE_REGION_CODE," +
					"NAME, BEGIN_TIME, END_TIME, ACTIVE, DESCRIPTION, VERSION_INFO, VERSION_NO, REF_ID, CREATE_TIME, " +
					"MODIFY_TIME, CREATE_USER_CODE, MODIFY_USER_CODE, CREATE_ORG_CODE, MODIFY_ORG_CODE, CURRENCY_CODE, TRANSPORT_FLAG,CUSTOMER_NAME,CUSTOMER_CODE) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			OrgAdministrativeInfoEntity currentDept  = FossUserContext.getCurrentDept();
			UserEntity currentUser = FossUserContext.getCurrentUser();

			for(PricePlanEntity pricePlan : pricePlanBatch){
				
				pricePlan.setCreateUser(currentUser.getEmployee().getEmpCode());
				pricePlan.setCreateOrgCode(currentDept.getCode());
				pricePlan.setModifyUser(currentUser.getEmployee().getEmpCode());
				
				
				ps.setString(1, pricePlan.getId());
				ps.setString(2, pricePlan.getPriceRegionId());
				ps.setString(NumberConstants.NUMBER_3, pricePlan.getPriceRegionCode());
				ps.setString(NumberConstants.NUMBER_4, pricePlan.getName());
				if(pricePlan.getBeginTime()!= null){
					ps.setTimestamp(NumberConstants.NUMBER_5, new Timestamp(pricePlan.getBeginTime().getTime()));
				}else{
					ps.setTimestamp(NumberConstants.NUMBER_5, null);
				}
				if(pricePlan.getEndTime()!= null){
					ps.setTimestamp(NumberConstants.NUMBER_6, new Timestamp(pricePlan.getEndTime().getTime()));
				}else{
					ps.setTimestamp(NumberConstants.NUMBER_6, null);
				}
				ps.setString(NumberConstants.NUMBER_7, pricePlan.getActive());
				ps.setString(NumberConstants.NUMBER_8, pricePlan.getDescription());
				ps.setString(NumberConstants.NUMBER_9, pricePlan.getVersionInfo());
				ps.setLong(NumberConstants.NUMBER_10, pricePlan.getVersionNo());
				ps.setString(NumberConstants.NUMBER_11, pricePlan.getRefId());
				ps.setTimestamp(NumberConstants.NUMBER_12, new Timestamp(new Date().getTime()));
				ps.setTimestamp(NumberConstants.NUMBER_13, new Timestamp(new Date().getTime()));
				ps.setString(NumberConstants.NUMBER_14, pricePlan.getCreateUser());
				ps.setString(NumberConstants.NUMBER_15, pricePlan.getModifyUser());
				ps.setString(NumberConstants.NUMBER_16, pricePlan.getCreateOrgCode());
				ps.setString(NumberConstants.NUMBER_17, pricePlan.getModifyOrgCode());
				ps.setString(NumberConstants.NUMBER_18, pricePlan.getCurrencyCode());
				ps.setString(NumberConstants.NUMBER_19, pricePlan.getTransportFlag());
				ps.setString(NumberConstants.NUMBER_20, pricePlan.getCustomerName());
				ps.setString(NumberConstants.NUMBER_21, pricePlan.getCustomerCode());

				//批处理
				ps.addBatch();
				
				count++;//计数器
				
				if(count == NumberConstants.NUMBER_500){
					//批量插入
					ps.executeBatch();
					//事务提交
					con.commit();
					
					ps.clearBatch();
					
					count = 0;
				}
			}
			
			if(count > 0){
				//批量插入
				ps.executeBatch();
				//事务提交
				con.commit();
				
				ps.clearBatch();
			}
		}
	}
	/**
	 * 批量导入价格方案-价格规则表
	 * @param pricePlanBatch
	 * @return huangwei 2015-12-05
	 * @throws SQLException 
	 */
	public void insertPriceValuationBatchList(Connection con, PreparedStatement ps, List<PriceValuationEntity> priceValuationBatch) throws SQLException  {
		if(CollectionUtils.isNotEmpty(priceValuationBatch)){
			int count = 0;
			ps = con.prepareStatement("insert into PKP.T_SRV_PRICING_VALUATION(id, goods_type_id, goods_type_code, product_id, product_code, dept_region_id, arrv_region_id, sales_channel_code, sales_channel_id, pricing_entry_id, pricing_entry_code, price_plan_id, price_plan_code, marketing_event_id, marketing_event_code, description, version_no, active, begin_time, end_time, long_or_short, type, currency_code, code, name, centralize_pickup, flight_shift, create_time, modify_time, create_user_code, modify_user_code, create_org_code, modify_org_code, pricing_industry_id, pricing_industry_code, business_begin_time, business_end_time, customer_code, customer_name) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		    
			OrgAdministrativeInfoEntity currentDept  = FossUserContext.getCurrentDept();
		    UserEntity currentUser = FossUserContext.getCurrentUser();

			for(PriceValuationEntity priceValuation : priceValuationBatch){
				
				priceValuation.setCreateUser(currentUser.getEmployee().getEmpCode());//创建人
				priceValuation.setCreateOrgCode(currentDept.getCode());//创建机构
				
				ps.setString(1, priceValuation.getId());
				ps.setString(2, priceValuation.getGoodsTypeId());
				ps.setString(NumberConstants.NUMBER_3, priceValuation.getGoodsTypeCode());
				ps.setString(NumberConstants.NUMBER_4, priceValuation.getProductId());
				ps.setString(NumberConstants.NUMBER_5, priceValuation.getProductCode());
				ps.setString(NumberConstants.NUMBER_6, priceValuation.getDeptRegionId());
				ps.setString(NumberConstants.NUMBER_7, priceValuation.getArrvRegionId());
				ps.setString(NumberConstants.NUMBER_8, priceValuation.getSalesChannelCode());
				ps.setString(NumberConstants.NUMBER_9, priceValuation.getSalesChannelId());
				ps.setString(NumberConstants.NUMBER_10, priceValuation.getPricingEntryId());
				ps.setString(NumberConstants.NUMBER_11, priceValuation.getPricingEntryCode());
				ps.setString(NumberConstants.NUMBER_12, priceValuation.getPricePlanId());
				ps.setString(NumberConstants.NUMBER_13, priceValuation.getPricePlanCode());
				ps.setString(NumberConstants.NUMBER_14, priceValuation.getMarketingEventId());
				ps.setString(NumberConstants.NUMBER_15, priceValuation.getMarketingEventCode());
				ps.setString(NumberConstants.NUMBER_16, priceValuation.getRemarks());
				ps.setLong(NumberConstants.NUMBER_17, priceValuation.getVersionNo());
				ps.setString(NumberConstants.NUMBER_18, priceValuation.getActive());
				if(priceValuation.getBeginTime()!= null){
					ps.setTimestamp(NumberConstants.NUMBER_19, new Timestamp(priceValuation.getBeginTime().getTime()));
				}else{
					ps.setTimestamp(NumberConstants.NUMBER_19, null);
				}
				if(priceValuation.getEndTime()!= null){
					ps.setTimestamp(NumberConstants.NUMBER_20, new Timestamp(priceValuation.getEndTime().getTime()));
				}else{
					ps.setTimestamp(NumberConstants.NUMBER_20, null);
				}
				ps.setString(NumberConstants.NUMBER_21, priceValuation.getLongOrShort());
				ps.setString(NumberConstants.NUMBER_22, priceValuation.getType());
				ps.setString(NumberConstants.NUMBER_23, priceValuation.getCurrencyCode());
				ps.setString(NumberConstants.NUMBER_24, priceValuation.getCode());
				ps.setString(NumberConstants.NUMBER_25, priceValuation.getName());
				ps.setString(NumberConstants.NUMBER_26, priceValuation.getCentralizePickup());
				ps.setString(NumberConstants.NUMBER_27, priceValuation.getLightShift());
				ps.setTimestamp(NumberConstants.NUMBER_28, new Timestamp(new Date().getTime()));
				ps.setTimestamp(NumberConstants.NUMBER_29, new Timestamp(new Date().getTime()));
				ps.setString(NumberConstants.NUMBER_30, priceValuation.getCreateUser());
				ps.setString(NumberConstants.NUMBER_31, priceValuation.getModifyUser());
				ps.setString(NumberConstants.NUMBER_32, priceValuation.getCreateOrgCode());
				ps.setString(NumberConstants.NUMBER_33, priceValuation.getModifyOrgCode());
				ps.setString(NumberConstants.NUMBER_34, priceValuation.getPricingIndustryId());
				ps.setString(NumberConstants.NUMBER_35, priceValuation.getPricingIndustryCode());
				ps.setString(NumberConstants.NUMBER_36, priceValuation.getBusinessBeginTime());
				ps.setString(NumberConstants.NUMBER_37, priceValuation.getBusinessEndTime());
				ps.setString(NumberConstants.NUMBER_38, priceValuation.getCustomerCode());
				ps.setString(NumberConstants.NUMBER_39, priceValuation.getCustomerName());

				//批处理
				ps.addBatch();
				
				count++;//计数器
				
				if(count == NumberConstants.NUMBER_500){
					//批量插入
					ps.executeBatch();
					//事务提交
					con.commit();
					
					ps.clearBatch();
					
					count = 0;
				}
			}
			
			if(count > 0){
				//批量插入
				ps.executeBatch();
				//事务提交
				con.commit();
				
				ps.clearBatch();
			}
		}
	}
	/**
	 * 批量导入价格方案-价格明细表
	 * @param pricePlanBatch
	 * @return huangwei 2015-12-05
	 * @throws SQLException 
	 */
	public void insertPriceCriteriaDetailBatchList(Connection con, PreparedStatement ps, List<PriceCriteriaDetailEntity> priceCriteriaDetailEntityBatch) throws SQLException {
		if(CollectionUtils.isNotEmpty(priceCriteriaDetailEntityBatch)){
			int count = 0;
			ps = con.prepareStatement("insert into pkp.t_srv_pricing_criteria_detail(Id, Name, caculate_type, fee, fee_rate, leftrange, rightrange, min_fee, max_fee, sub_type, canmodify, Description, process_program, process_parm_val, pricing_criteria_id, parm2, parm1, t_srv_price_rule_id, parm3, parm4, parm5, discount_rate, active, version_no, dept_region_id, pricing_valuation_id, candelete, min_fee_rate, max_fee_rate, create_time, modify_time, comb_bill_type_code, valueadd_caculate_type, valueadd_leftrange, valueadd_rightrange, min_vote, max_vote, package_type, refund_type, returnbill_type, min_insurance_fee, togeter_category, can_not_charge) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

			for(PriceCriteriaDetailEntity priceCriteriaDetail : priceCriteriaDetailEntityBatch){
				
				//创建时间
				priceCriteriaDetail.setCreateDate(new Date());
				//修改时间
				priceCriteriaDetail.setModifyDate(priceCriteriaDetail.getCreateDate());
				
				ps.setString(1, priceCriteriaDetail.getId());
				ps.setString(2, priceCriteriaDetail.getName());
				ps.setString(NumberConstants.NUMBER_3, priceCriteriaDetail.getCaculateType());
				if(priceCriteriaDetail.getFee() != null){
					ps.setLong(NumberConstants.NUMBER_4, priceCriteriaDetail.getFee() != null ? priceCriteriaDetail.getFee()*NumberConstants.NUMBER_100 : 0);
				}else{
					ps.setNull(NumberConstants.NUMBER_4, Types.LONGVARCHAR);
				}
				ps.setBigDecimal(NumberConstants.NUMBER_5, priceCriteriaDetail.getFeeRate());
				ps.setBigDecimal(NumberConstants.NUMBER_6, priceCriteriaDetail.getLeftrange());
				ps.setBigDecimal(NumberConstants.NUMBER_7, priceCriteriaDetail.getRightrange());
				if(priceCriteriaDetail.getMinFee() != null){
					ps.setLong(NumberConstants.NUMBER_8, priceCriteriaDetail.getMinFee() != null ? priceCriteriaDetail.getMinFee()*NumberConstants.NUMBER_100 : 0);
				}else{
					ps.setNull(NumberConstants.NUMBER_8, Types.LONGVARCHAR);
				}
				
				if(priceCriteriaDetail.getMaxFee() != null){
					ps.setLong(NumberConstants.NUMBER_9, priceCriteriaDetail.getMaxFee() != null ? priceCriteriaDetail.getMaxFee()*NumberConstants.NUMBER_100 : 0);
				}else{
					ps.setNull(NumberConstants.NUMBER_9, Types.LONGVARCHAR);
				}
				
				ps.setString(NumberConstants.NUMBER_10, priceCriteriaDetail.getSubType());
				ps.setString(NumberConstants.NUMBER_11, priceCriteriaDetail.getCanmodify());
				ps.setString(NumberConstants.NUMBER_12, priceCriteriaDetail.getDescription());
				ps.setString(NumberConstants.NUMBER_13, priceCriteriaDetail.getProcessProgram());
				ps.setString(NumberConstants.NUMBER_14, priceCriteriaDetail.getProcessParmVal());
				ps.setString(NumberConstants.NUMBER_15, priceCriteriaDetail.getPricingCriteriaId());
				
				if(priceCriteriaDetail.getParm2() != null){
					ps.setLong(NumberConstants.NUMBER_16, priceCriteriaDetail.getParm2() != null ? priceCriteriaDetail.getParm2() : 0);
				}else{
					ps.setNull(NumberConstants.NUMBER_16, Types.LONGVARCHAR);
				}
				
				if(priceCriteriaDetail.getParm1() != null){
					ps.setLong(NumberConstants.NUMBER_17, priceCriteriaDetail.getParm1() != null ? priceCriteriaDetail.getParm1() : 0);
				}else{
					ps.setNull(NumberConstants.NUMBER_17, Types.LONGVARCHAR);
				}
				ps.setString(NumberConstants.NUMBER_18, priceCriteriaDetail.gettSrvPriceRuleId());
				
				if(priceCriteriaDetail.getParm3() != null){
					ps.setLong(NumberConstants.NUMBER_19, priceCriteriaDetail.getParm3() != null ? priceCriteriaDetail.getParm3() : 0);
				}else{
					ps.setNull(NumberConstants.NUMBER_19, Types.LONGVARCHAR);
				}
				
				if(priceCriteriaDetail.getParm4() != null){
					ps.setLong(NumberConstants.NUMBER_20, priceCriteriaDetail.getParm4() != null ? priceCriteriaDetail.getParm4() : 0);
				}else{
					ps.setNull(NumberConstants.NUMBER_20, Types.LONGVARCHAR);
				}
				
				if(priceCriteriaDetail.getParm5() != null){
					ps.setLong(NumberConstants.NUMBER_21, priceCriteriaDetail.getParm5() != null ? priceCriteriaDetail.getParm5() : 0);
				}else{
					ps.setNull(NumberConstants.NUMBER_21, Types.LONGVARCHAR);
				}
				
				ps.setBigDecimal(NumberConstants.NUMBER_22, priceCriteriaDetail.getDiscountRate());
				ps.setString(NumberConstants.NUMBER_23, priceCriteriaDetail.getActive());
				ps.setLong(NumberConstants.NUMBER_24, priceCriteriaDetail.getVersionNo());
				ps.setString(NumberConstants.NUMBER_25, priceCriteriaDetail.getDeptRegionId());
				ps.setString(NumberConstants.NUMBER_26, priceCriteriaDetail.getPricingValuationId());
				ps.setString(NumberConstants.NUMBER_27, priceCriteriaDetail.getCandelete());
				ps.setBigDecimal(NumberConstants.NUMBER_28, priceCriteriaDetail.getMinFeeRate());
				ps.setBigDecimal(NumberConstants.NUMBER_29, priceCriteriaDetail.getMaxFeeRate());
				if(priceCriteriaDetail.getCreateDate()!= null){
					ps.setTimestamp(NumberConstants.NUMBER_30, new Timestamp(priceCriteriaDetail.getCreateDate().getTime()));
				}else{
					ps.setTimestamp(NumberConstants.NUMBER_30, null);
				}
				if(priceCriteriaDetail.getModifyDate()!= null){
					ps.setTimestamp(NumberConstants.NUMBER_31, new Timestamp(priceCriteriaDetail.getModifyDate().getTime()));
				}else{
					ps.setTimestamp(NumberConstants.NUMBER_31, null);
				}
				ps.setString(NumberConstants.NUMBER_32, priceCriteriaDetail.getCombBillTypeCode());
				ps.setString(NumberConstants.NUMBER_33, priceCriteriaDetail.getValueaddCaculateType());
				ps.setBigDecimal(NumberConstants.NUMBER_34, priceCriteriaDetail.getValueaddLeftrange());
				ps.setBigDecimal(NumberConstants.NUMBER_35, priceCriteriaDetail.getValueaddRightrange());
				
				if(priceCriteriaDetail.getMinVote() != null){
					ps.setDouble(NumberConstants.NUMBER_36, priceCriteriaDetail.getMinVote() != null ? priceCriteriaDetail.getMinVote() : 0);
				}else{
					ps.setNull(NumberConstants.NUMBER_36, Types.LONGVARCHAR);
				}
				
				if(priceCriteriaDetail.getMaxVote() != null){
					ps.setDouble(NumberConstants.NUMBER_37, priceCriteriaDetail.getMaxVote() != null ? priceCriteriaDetail.getMaxVote() : 0);
				}else{
					ps.setNull(NumberConstants.NUMBER_37, Types.LONGVARCHAR);
				}
				
				ps.setString(NumberConstants.NUMBER_38, priceCriteriaDetail.getPackageType());
				ps.setString(NumberConstants.NUMBER_39, priceCriteriaDetail.getRefundType());
				ps.setString(NumberConstants.NUMBER_40, priceCriteriaDetail.getReturnbillType());
				
				if(priceCriteriaDetail.getMinInsuranceFee() != null){
					ps.setDouble(NumberConstants.NUMBER_41, priceCriteriaDetail.getMinInsuranceFee() != null ? priceCriteriaDetail.getMinInsuranceFee() : 0);
				}else{
					ps.setNull(NumberConstants.NUMBER_41, Types.LONGVARCHAR);
				}
				
				ps.setString(NumberConstants.NUMBER_42, priceCriteriaDetail.getTogeterCategory());
				ps.setString(NumberConstants.NUMBER_43, priceCriteriaDetail.getCanNotCharge());

				//批处理
				ps.addBatch();
				
				count++;//计数器
				
				if(count == NumberConstants.NUMBER_500){
					//批量插入
					ps.executeBatch();
					//事务提交
					con.commit();
					
					ps.clearBatch();
					
					count = 0;
				}
			}
			
			if(count > 0){
				//批量插入
				ps.executeBatch();
				//事务提交
				con.commit();
				
				ps.clearBatch();
			}
		}
	}
	/**
	 * 批量导入价格方案-分段价格表
	 * @param pricePlanBatch
	 * @return huangwei 2015-12-05
	 * @throws SQLException 
	 */
	public void insertPopPriceDetailSectionBatchList(Connection con, PreparedStatement ps, List<PopPriceDetailSectionEntity> popPriceDetailSectionBatch) throws SQLException {
		if(CollectionUtils.isNotEmpty(popPriceDetailSectionBatch)){
			int count = 0;
			ps = con.prepareStatement("insert into PKP.t_pop_pricing_detail_section(id,section_id,caculate_type,fee,critical_value,criteria_detail_id,description,pricing_valuation_id) values (?,?,?,?,?,?,?,?)");
			for(PopPriceDetailSectionEntity popPriceDetailSection : popPriceDetailSectionBatch){
				
				ps.setString(1, popPriceDetailSection.getId());
				ps.setString(2, popPriceDetailSection.getSectionID());
				ps.setString(NumberConstants.NUMBER_3, popPriceDetailSection.getCaculateType());
				if(popPriceDetailSection.getFee() != null){
					ps.setBigDecimal(NumberConstants.NUMBER_4, popPriceDetailSection.getFee() != null ? NumberUtils.multiply(popPriceDetailSection.getFee(),NumberConstants.NUMBER_100) : new BigDecimal(0));
				}else{
					ps.setNull(NumberConstants.NUMBER_4, Types.DECIMAL);
				}
				
				if(popPriceDetailSection.getCriticalValue() != null){
					ps.setBigDecimal(NumberConstants.NUMBER_5, popPriceDetailSection.getCriticalValue() != null ? NumberUtils.multiply(popPriceDetailSection.getCriticalValue(),NumberConstants.NUMBER_100) : new BigDecimal(0));
				}else{
					ps.setNull(NumberConstants.NUMBER_5, Types.DECIMAL);
				}
				
				ps.setString(NumberConstants.NUMBER_6, popPriceDetailSection.getCriteriaDetailID());
				ps.setString(NumberConstants.NUMBER_7, popPriceDetailSection.getDescription());
				ps.setString(NumberConstants.NUMBER_8, popPriceDetailSection.getValuationId());

				//批处理
				ps.addBatch();
				
				count++;//计数器
				
				if(count == NumberConstants.NUMBER_500){
					//批量插入
					ps.executeBatch();
					//事务提交
					con.commit();
					
					ps.clearBatch();
					
					count = 0;
				}
			}
			
			if(count > 0){
				//批量插入
				ps.executeBatch();
				//事务提交
				con.commit();
				
				ps.clearBatch();
			}
		}
	}
	/**
	 * 删除价格方案批处理
	 * @param pricePlanBatch
	 * @return huangwei 2015-12-05
	 */
	@Override
	public int deletePricePlanBatch(List<String> priceValuationIdsList, List<String> pricePlanIdsList) {
		if(CollectionUtils.isNotEmpty(pricePlanIdsList)){
			Connection con = null;
			PreparedStatement ps = null;
			int count = 0;
			try {
				//获取连接
				con = getSqlSession().getConfiguration().getEnvironment().getDataSource().getConnection();
				con.setAutoCommit(false);
				
				if(CollectionUtils.isNotEmpty(priceValuationIdsList)){
					//删除价格分段表
					ps = con.prepareStatement("DELETE FROM PKP.T_POP_PRICING_DETAIL_SECTION SECTION WHERE SECTION.PRICING_VALUATION_ID = ?");
					for(String priceValuationId : priceValuationIdsList){
						
						ps.setString(1, priceValuationId);
						
						//批处理
						ps.addBatch();
	
						count++;//计数器
						
						if(count == NumberConstants.NUMBER_200){
							//批量插入
							ps.executeBatch();
							//事务提交
							con.commit();
							
							ps.clearBatch();
							
							count = 0;
						}
					}
					if(count > 0){
						//批量插入
						ps.executeBatch();
						//事务提交
						con.commit();
						
						ps.clearBatch();
						
						count = 0;
					}
					//删除价格明细表
					ps = con.prepareStatement("DELETE FROM pkp.T_SRV_PRICING_CRITERIA_DETAIL T WHERE T.PRICING_VALUATION_ID = ?");
					for(String priceValuationId : priceValuationIdsList){
						
						ps.setString(1, priceValuationId);
						
						//批处理
						ps.addBatch();
	
						count++;//计数器
						
						if(count == NumberConstants.NUMBER_200){
							//批量插入
							ps.executeBatch();
							//事务提交
							con.commit();
							
							ps.clearBatch();
							
							count = 0;
						}
					}
					if(count > 0){
						//批量插入
						ps.executeBatch();
						//事务提交
						con.commit();
						
						ps.clearBatch();
						
						count = 0;
					}
					
					priceValuationIdsList.clear();//释放内存
				}
				
				//删除价格规则表
				ps = con.prepareStatement("DELETE FROM PKP.T_SRV_PRICING_VALUATION T WHERE T.PRICE_PLAN_ID = ?");
				for(String pricePlanId : pricePlanIdsList){
					
					ps.setString(1, pricePlanId);
					
					//批处理
					ps.addBatch();

					//批量插入
					ps.executeBatch();
					//事务提交
					con.commit();
					
					ps.clearBatch();
						
				}
				
				
				//删除方案表
				ps = con.prepareStatement("DELETE FROM PKP.T_SRV_PRICE_PLAN T WHERE T.ID = ?");
				for(String pricePlanId : pricePlanIdsList){
					
					ps.setString(1, pricePlanId);
					
					//批处理
					ps.addBatch();

					count++;//计数器
					
					if(count == NumberConstants.NUMBER_500){
						//批量插入
						ps.executeBatch();
						//事务提交
						con.commit();
						
						ps.clearBatch();
						
						count = 0;
					}
				}
				if(count > 0){
					//批量插入
					ps.executeBatch();
					//事务提交
					con.commit();
					
					ps.clearBatch();
					
					count = 0;
				}
				pricePlanIdsList.clear();//释放内存
			} catch (Exception e) {
				//记录异常日志
				LOGGER.error("插入T_SRV_PRICING_CRITERIA_DETAIL表时发生异常：" + e.getMessage());
				try {
					//回滚
					if(con != null){
						con.rollback();
					}
				} catch (SQLException e1) {
					//记录异常日志
					LOGGER.error("事务回滚发生异常：" + e1.getMessage());
					throw new PriceBusinessException(e1.getMessage());
				}
				throw new PriceBusinessException(e.getMessage());
			}finally{
				//关闭ps
				try {
					if(ps != null){
						ps.close();
					}
				} catch (SQLException e) {
					try {
						//回滚
						if(con != null){
							con.rollback();
						}
					} catch (SQLException e1) {
						//记录异常日志
						LOGGER.error("事务回滚发生异常：" + e1.getMessage());
						throw new PriceBusinessException(e1.getMessage());
					}
					//记录ps关闭异常信息
					LOGGER.error("关闭prepareStatement异常：" + e.getMessage());
				}finally{
					if(con != null){
						try {
							//关闭connection
							con.close();
						} catch (SQLException e) {
							try {
								//回滚
								con.rollback();
							} catch (SQLException e1) {
								//记录异常日志
								LOGGER.error("事务回滚发生异常：" + e1.getMessage());
								throw new PriceBusinessException(e1.getMessage());
							}
							//记录关闭con异常日志
							LOGGER.error("关闭connection异常：" + e.getMessage());
						}
					}
				}
			}
		}
		//返回处理成功
		return FossConstants.SUCCESS;
	}
	/**
	 * 激活价格方案批处理
	 * @param pricePlanBatch
	 * @return huangwei 2015-12-05
	 */
	@Override
	public int activePricePlanBatch(List<PricePlanEntity> pricePlanEntityList, List<String> priceValuationIdsList) {
		if(CollectionUtils.isNotEmpty(pricePlanEntityList)){
			Connection con = null;
			PreparedStatement ps = null;
			int count = 0;
			try {
				//获取连接
				con = getSqlSession().getConfiguration().getEnvironment().getDataSource().getConnection();
				con.setAutoCommit(false);
				
				Long versionNo = new Date().getTime();
				
				if(CollectionUtils.isNotEmpty(priceValuationIdsList)){
					//激活规则表
					ps = con.prepareStatement("UPDATE pkp.T_SRV_PRICING_VALUATION T SET T.ACTIVE = 'Y', T.VERSION_NO = ? WHERE T.ID = ?");
					for(String priceValuationId : priceValuationIdsList){
						
						ps.setLong(1, versionNo);
						ps.setString(2, priceValuationId);
	
						//批处理
						ps.addBatch();
	
						count++;//计数器
						
						if(count == NumberConstants.NUMBER_500){
							//批量插入
							ps.executeBatch();
							//事务提交
							con.commit();
							
							ps.clearBatch();
							
							count = 0;
						}
					}
					if(count > 0){
						//批量插入
						ps.executeBatch();
						//事务提交
						con.commit();
						
						ps.clearBatch();
						
						count = 0;
					}	
					
					
					//激活明细表
					ps = con.prepareStatement("UPDATE pkp.T_SRV_PRICING_CRITERIA_DETAIL T SET T.ACTIVE = 'Y', T.VERSION_NO = ? WHERE T.PRICING_VALUATION_ID = ?");
					for(String priceValuationId : priceValuationIdsList){
						
						ps.setLong(1, versionNo);
						ps.setString(2, priceValuationId);
	
						//批处理
						ps.addBatch();
	
						count++;//计数器
						
						if(count == NumberConstants.NUMBER_200){
							//批量插入
							ps.executeBatch();
							//事务提交
							con.commit();
							
							ps.clearBatch();
							
							count = 0;
						}
					}
					if(count > 0){
						//批量插入
						ps.executeBatch();
						//事务提交
						con.commit();
						
						ps.clearBatch();
						
						count = 0;
					}
					priceValuationIdsList.clear();
				}
				
				//激活修改价格方案
				ps = con.prepareStatement("UPDATE pkp.T_SRV_PRICE_PLAN T SET T.T_SRV_PRICE_REGION_ID = ?, T.T_SRV_PRICE_REGION_CODE = ?, t.NAME = ?, t.BEGIN_TIME = ?, t.END_TIME = ?, t.ACTIVE = ?, t.DESCRIPTION = ?, t.VERSION_INFO = ?, t.VERSION_NO = ?, t.REF_ID = ?, t.CREATE_TIME = ?, t.MODIFY_TIME = ?, t.CREATE_USER_CODE = ?, t.MODIFY_USER_CODE = ?, t.CREATE_ORG_CODE = ?, t.MODIFY_ORG_CODE = ?, t.CURRENCY_CODE = ?, t.TRANSPORT_FLAG = ?, t.CUSTOMER_NAME = ?, t.CUSTOMER_CODE = ?  WHERE T.ID = ?");
				for(PricePlanEntity pricePlanEntity : pricePlanEntityList){
					
					ps.setString(1, pricePlanEntity.getPriceRegionId());
					ps.setString(2, pricePlanEntity.getPriceRegionCode());
					ps.setString(NumberConstants.NUMBER_3, pricePlanEntity.getName());
					if(pricePlanEntity.getBeginTime()!= null){
						ps.setTimestamp(NumberConstants.NUMBER_4, new Timestamp(pricePlanEntity.getBeginTime().getTime()));
					}else{
						ps.setTimestamp(NumberConstants.NUMBER_4, null);
					}
					if(pricePlanEntity.getEndTime()!= null){
						ps.setTimestamp(NumberConstants.NUMBER_5, new Timestamp(pricePlanEntity.getEndTime().getTime()));
					}else{
						ps.setTimestamp(NumberConstants.NUMBER_5, null);
					}
					ps.setString(NumberConstants.NUMBER_6, pricePlanEntity.getActive());
					ps.setString(NumberConstants.NUMBER_7, pricePlanEntity.getDescription());
					ps.setString(NumberConstants.NUMBER_8, pricePlanEntity.getVersionInfo());
					ps.setLong(NumberConstants.NUMBER_9, pricePlanEntity.getVersionNo());
					ps.setString(NumberConstants.NUMBER_10, pricePlanEntity.getRefId());
					if(pricePlanEntity.getCreateDate()!= null){
						ps.setTimestamp(NumberConstants.NUMBER_11, new Timestamp(pricePlanEntity.getCreateDate().getTime()));
					}else{
						ps.setTimestamp(NumberConstants.NUMBER_11, null);
					}
					ps.setTimestamp(NumberConstants.NUMBER_12, new Timestamp(new Date().getTime()));
					ps.setString(NumberConstants.NUMBER_13, pricePlanEntity.getCreateUser());
					ps.setString(NumberConstants.NUMBER_14, pricePlanEntity.getModifyUser());
					ps.setString(NumberConstants.NUMBER_15, pricePlanEntity.getCreateOrgCode());
					ps.setString(NumberConstants.NUMBER_16, pricePlanEntity.getModifyOrgCode());
					ps.setString(NumberConstants.NUMBER_17, pricePlanEntity.getCurrencyCode());
					ps.setString(NumberConstants.NUMBER_18, pricePlanEntity.getTransportFlag());
					ps.setString(NumberConstants.NUMBER_19, pricePlanEntity.getCustomerName());
					ps.setString(NumberConstants.NUMBER_20, pricePlanEntity.getCustomerCode());
					ps.setString(NumberConstants.NUMBER_21, pricePlanEntity.getId());

					//批处理
					ps.addBatch();

					count++;//计数器
					
					if(count == NumberConstants.NUMBER_500){
						//批量插入
						ps.executeBatch();
						//事务提交
						con.commit();
						
						ps.clearBatch();
						
						count = 0;
					}
				}
				if(count > 0){
					//批量插入
					ps.executeBatch();
					//事务提交
					con.commit();
					
					ps.clearBatch();
					
					count = 0;
				}
				pricePlanEntityList.clear();
			} catch (Exception e) {
				//记录异常日志
				LOGGER.error("插入T_SRV_PRICING_CRITERIA_DETAIL表时发生异常：" + e.getMessage());
				try {
					//回滚
					if(con != null){
						con.rollback();
					}
				} catch (SQLException e1) {
					//记录异常日志
					LOGGER.error("事务回滚发生异常：" + e1.getMessage());
					throw new PriceBusinessException(e1.getMessage());
				}
				throw new PriceBusinessException(e.getMessage());
			}finally{
				//关闭ps
				try {
					if(ps != null){
						ps.close();
					}
				} catch (SQLException e) {
					try {
						//回滚
						if(con != null){
							con.rollback();
						}
					} catch (SQLException e1) {
						//记录异常日志
						LOGGER.error("事务回滚发生异常：" + e1.getMessage());
						throw new PriceBusinessException(e1.getMessage());
					}
					//记录ps关闭异常信息
					LOGGER.error("关闭prepareStatement异常：" + e.getMessage());
				}finally{
					if(con != null){
						try {
							//关闭connection
							con.close();
						} catch (SQLException e) {
							try {
								//回滚
								con.rollback();
							} catch (SQLException e1) {
								//记录异常日志
								LOGGER.error("事务回滚发生异常：" + e1.getMessage());
								throw new PriceBusinessException(e1.getMessage());
							}
							//记录关闭con异常日志
							LOGGER.error("关闭connection异常：" + e.getMessage());
						}
					}
				}
			}
		}
		//返回处理成功
		return FossConstants.SUCCESS;
	}
	/**
	 * 中止价格方案批处理
	 * @param pricePlanBatch
	 * @return huangwei 2015-12-05
	 */
	@Override
	public int stopPricePlanBatch(List<PricePlanEntity> pricePlanEntityList, List<PriceValuationEntity> priceValuationEntityList) {
		if(CollectionUtils.isNotEmpty(pricePlanEntityList)){
			Connection con = null;
			PreparedStatement ps = null;
			int count = 0;
			try {
				//获取连接
				con = getSqlSession().getConfiguration().getEnvironment().getDataSource().getConnection();
				con.setAutoCommit(false);
				
				if(CollectionUtils.isNotEmpty(priceValuationEntityList)){
					//中止规则表
					ps = con.prepareStatement("UPDATE pkp.T_SRV_PRICING_VALUATION T SET T.GOODS_TYPE_ID = ?, T.GOODS_TYPE_CODE = ?, T.PRODUCT_ID = ?, T.PRODUCT_CODE = ?, T.DEPT_REGION_ID = ?, T.ARRV_REGION_ID = ?, T.SALES_CHANNEL_CODE = ?, T.SALES_CHANNEL_ID = ?, T.PRICING_ENTRY_ID = ?, T.PRICING_ENTRY_CODE = ?, T.PRICING_INDUSTRY_ID = ?, T.PRICING_INDUSTRY_CODE = ?, T.PRICE_PLAN_ID = ?, T.PRICE_PLAN_CODE = ?, T.MARKETING_EVENT_ID = ?, T.MARKETING_EVENT_CODE = ?, T.DESCRIPTION = ?, T.VERSION_NO = ?, T.ACTIVE = ?, T.BEGIN_TIME = ?, T.END_TIME = ?, T.LONG_OR_SHORT = ?, T.TYPE = ?, T.CURRENCY_CODE = ?, T.CODE = ?, T.NAME = ?, T.MODIFY_TIME = ?, T.MODIFY_USER_CODE = ?, T.MODIFY_ORG_CODE = ?, T.FLIGHT_SHIFT = ?, T.CENTRALIZE_PICKUP = ?, T.BUSINESS_BEGIN_TIME = ?, T.BUSINESS_END_TIME = ?, T.CUSTOMER_CODE = ?, T.CUSTOMER_NAME = ? WHERE T.ID = ?");
					for(PriceValuationEntity priceValuationEntity : priceValuationEntityList){
						
						ps.setString(1, priceValuationEntity.getGoodsTypeId());
						ps.setString(2, priceValuationEntity.getGoodsTypeCode());
						ps.setString(NumberConstants.NUMBER_3, priceValuationEntity.getProductId());
						ps.setString(NumberConstants.NUMBER_4, priceValuationEntity.getProductCode());
						ps.setString(NumberConstants.NUMBER_5, priceValuationEntity.getDeptRegionId());
						ps.setString(NumberConstants.NUMBER_6, priceValuationEntity.getArrvRegionId());
						ps.setString(NumberConstants.NUMBER_7, priceValuationEntity.getSalesChannelCode());
						ps.setString(NumberConstants.NUMBER_8, priceValuationEntity.getSalesChannelId());
						ps.setString(NumberConstants.NUMBER_9, priceValuationEntity.getPricingEntryId());
						ps.setString(NumberConstants.NUMBER_10, priceValuationEntity.getPricingEntryCode());
						ps.setString(NumberConstants.NUMBER_11, priceValuationEntity.getPricingIndustryId());
						ps.setString(NumberConstants.NUMBER_12, priceValuationEntity.getPricingIndustryCode());
						ps.setString(NumberConstants.NUMBER_13, priceValuationEntity.getPricePlanId());
						ps.setString(NumberConstants.NUMBER_14, priceValuationEntity.getPricePlanCode());
						ps.setString(NumberConstants.NUMBER_15, priceValuationEntity.getMarketingEventId());
						ps.setString(NumberConstants.NUMBER_16, priceValuationEntity.getMarketingEventCode());
						ps.setString(NumberConstants.NUMBER_17, priceValuationEntity.getRemarks());
						ps.setLong(NumberConstants.NUMBER_18, priceValuationEntity.getVersionNo());
						ps.setString(NumberConstants.NUMBER_19, priceValuationEntity.getActive());
						if(priceValuationEntity.getBeginTime()!= null){
							ps.setTimestamp(NumberConstants.NUMBER_20, new Timestamp(priceValuationEntity.getBeginTime().getTime()));
						}else{
							ps.setTimestamp(NumberConstants.NUMBER_20, null);
						}
						if(priceValuationEntity.getEndTime()!= null){
							ps.setTimestamp(NumberConstants.NUMBER_21, new Timestamp(priceValuationEntity.getEndTime().getTime()));
						}else{
							ps.setTimestamp(NumberConstants.NUMBER_21, null);
						}
						ps.setString(NumberConstants.NUMBER_22, priceValuationEntity.getLongOrShort());
						ps.setString(NumberConstants.NUMBER_23, priceValuationEntity.getType());
						ps.setString(NumberConstants.NUMBER_24, priceValuationEntity.getCurrencyCode());
						ps.setString(NumberConstants.NUMBER_25, priceValuationEntity.getCode());
						ps.setString(NumberConstants.NUMBER_26, priceValuationEntity.getName());
						ps.setTimestamp(NumberConstants.NUMBER_27, new Timestamp(new Date().getTime()));
						ps.setString(NumberConstants.NUMBER_28, priceValuationEntity.getModifyUser());
						ps.setString(NumberConstants.NUMBER_29, priceValuationEntity.getModifyOrgCode());
						ps.setString(NumberConstants.NUMBER_30, priceValuationEntity.getLightShift());
						ps.setString(NumberConstants.NUMBER_31, priceValuationEntity.getCentralizePickup());
						ps.setString(NumberConstants.NUMBER_32, priceValuationEntity.getBusinessBeginTime());
						ps.setString(NumberConstants.NUMBER_33, priceValuationEntity.getBusinessEndTime());
						ps.setString(NumberConstants.NUMBER_34, priceValuationEntity.getCustomerCode());
						ps.setString(NumberConstants.NUMBER_35, priceValuationEntity.getCustomerName());
						ps.setString(NumberConstants.NUMBER_36, priceValuationEntity.getId());
	
						//批处理
						ps.addBatch();
	
						count++;//计数器
						
						if(count == NumberConstants.NUMBER_500){
							//批量插入
							ps.executeBatch();
							//事务提交
							con.commit();
							
							ps.clearBatch();
							
							count = 0;
						}
					}
					if(count > 0){
						//批量插入
						ps.executeBatch();
						//事务提交
						con.commit();
						
						ps.clearBatch();
						
						count = 0;
					}	
					priceValuationEntityList.clear();
				}
				
				//激活修改价格方案
				ps = con.prepareStatement("UPDATE pkp.T_SRV_PRICE_PLAN T SET T.T_SRV_PRICE_REGION_ID = ?, T.T_SRV_PRICE_REGION_CODE = ?, t.NAME = ?, t.BEGIN_TIME = ?, t.END_TIME = ?, t.ACTIVE = ?, t.DESCRIPTION = ?, t.VERSION_INFO = ?, t.VERSION_NO = ?, t.REF_ID = ?, t.CREATE_TIME = ?, t.MODIFY_TIME = ?, t.CREATE_USER_CODE = ?, t.MODIFY_USER_CODE = ?, t.CREATE_ORG_CODE = ?, t.MODIFY_ORG_CODE = ?, t.CURRENCY_CODE = ?, t.TRANSPORT_FLAG = ?, t.CUSTOMER_NAME = ?, t.CUSTOMER_CODE = ?  WHERE T.ID = ?");
				for(PricePlanEntity pricePlanEntity : pricePlanEntityList){
					
					ps.setString(1, pricePlanEntity.getPriceRegionId());
					ps.setString(2, pricePlanEntity.getPriceRegionCode());
					ps.setString(NumberConstants.NUMBER_3, pricePlanEntity.getName());
					if(pricePlanEntity.getBeginTime()!= null){
						ps.setTimestamp(NumberConstants.NUMBER_4, new Timestamp(pricePlanEntity.getBeginTime().getTime()));
					}else{
						ps.setTimestamp(NumberConstants.NUMBER_4, null);
					}
					if(pricePlanEntity.getEndTime()!= null){
						ps.setTimestamp(NumberConstants.NUMBER_5, new Timestamp(pricePlanEntity.getEndTime().getTime()));
					}else{
						ps.setTimestamp(NumberConstants.NUMBER_5, null);
					}
					ps.setString(NumberConstants.NUMBER_6, pricePlanEntity.getActive());
					ps.setString(NumberConstants.NUMBER_7, pricePlanEntity.getDescription());
					ps.setString(NumberConstants.NUMBER_8, pricePlanEntity.getVersionInfo());
					ps.setLong(NumberConstants.NUMBER_9, pricePlanEntity.getVersionNo());
					ps.setString(NumberConstants.NUMBER_10, pricePlanEntity.getRefId());
					if(pricePlanEntity.getCreateDate()!= null){
						ps.setTimestamp(NumberConstants.NUMBER_11, new Timestamp(pricePlanEntity.getCreateDate().getTime()));
					}else{
						ps.setTimestamp(NumberConstants.NUMBER_11, null);
					}
					ps.setTimestamp(NumberConstants.NUMBER_12, new Timestamp(new Date().getTime()));
					ps.setString(NumberConstants.NUMBER_13, pricePlanEntity.getCreateUser());
					ps.setString(NumberConstants.NUMBER_14, pricePlanEntity.getModifyUser());
					ps.setString(NumberConstants.NUMBER_15, pricePlanEntity.getCreateOrgCode());
					ps.setString(NumberConstants.NUMBER_16, pricePlanEntity.getModifyOrgCode());
					ps.setString(NumberConstants.NUMBER_17, pricePlanEntity.getCurrencyCode());
					ps.setString(NumberConstants.NUMBER_18, pricePlanEntity.getTransportFlag());
					ps.setString(NumberConstants.NUMBER_19, pricePlanEntity.getCustomerName());
					ps.setString(NumberConstants.NUMBER_20, pricePlanEntity.getCustomerCode());
					ps.setString(NumberConstants.NUMBER_21, pricePlanEntity.getId());

					//批处理
					ps.addBatch();

					count++;//计数器
					
					if(count == NumberConstants.NUMBER_500){
						//批量插入
						ps.executeBatch();
						//事务提交
						con.commit();
						
						ps.clearBatch();
						
						count = 0;
					}
				}
				if(count > 0){
					//批量插入
					ps.executeBatch();
					//事务提交
					con.commit();
					
					ps.clearBatch();
					
					count = 0;
				}
				pricePlanEntityList.clear();
			} catch (Exception e) {
				//记录异常日志
				LOGGER.error("插入T_SRV_PRICING_CRITERIA_DETAIL表时发生异常：" + e.getMessage());
				try {
					//回滚
					if(con != null){
						con.rollback();
					}
				} catch (SQLException e1) {
					//记录异常日志
					LOGGER.error("事务回滚发生异常：" + e1.getMessage());
					throw new PriceBusinessException(e1.getMessage());
				}
				throw new PriceBusinessException(e.getMessage());
			}finally{
				//关闭ps
				try {
					if(ps != null){
						ps.close();
					}
				} catch (SQLException e) {
					try {
						//回滚
						if(con != null){
							con.rollback();
						}
					} catch (SQLException e1) {
						//记录异常日志
						LOGGER.error("事务回滚发生异常：" + e1.getMessage());
						throw new PriceBusinessException(e1.getMessage());
					}
					//记录ps关闭异常信息
					LOGGER.error("关闭prepareStatement异常：" + e.getMessage());
				}finally{
					if(con != null){
						try {
							//关闭connection
							con.close();
						} catch (SQLException e) {
							try {
								//回滚
								con.rollback();
							} catch (SQLException e1) {
								//记录异常日志
								LOGGER.error("事务回滚发生异常：" + e1.getMessage());
								throw new PriceBusinessException(e1.getMessage());
							}
							//记录关闭con异常日志
							LOGGER.error("关闭connection异常：" + e.getMessage());
						}
					}
				}
			}
		}
		//返回处理成功
		return FossConstants.SUCCESS;
	}
	@SuppressWarnings("unchecked")
    @Override
    public List<PricePlanEntity> queryPricePlan(
	    PricePlanEntity pricePlanEntity ) {
	return getSqlSession().selectList(NAME_SPACE + "queryPricePlan", pricePlanEntity);
    }
 
	
}