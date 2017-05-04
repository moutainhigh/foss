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
 * PROJECT NAME	: pkp-pricing-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/server/dao/IPricePlanDao.java
 * 
 * FILE NAME        	: IPricePlanDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceCriteriaDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PricePlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceValuationEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.pop.PopPriceDetailSectionEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryExistPricePlanDetailBean;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryPricePlanDetailBean;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ResultPricePlanDetailBean;

/**
 * 
 * TODO(价格主方案)
 * @author 岳洪杰
 * @date 2012-10-15 上午10:42:51
 * @since
 * @version
 */
public interface IPricePlanDao {
    /**
     * 
     * <p>(删除价格方案)</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-12 下午3:06:29
     * @param id
     * @return
     * @see
     */
    int deleteByPrimaryKey(String id);

    /**
     * 
     * <p>(新增价格方案,所有列)</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-12 下午3:07:13
     * @param record
     * @return
     * @see
     */
    int insert(PricePlanEntity record);

    /**
     * 
     * <p>(新增价格方案,根据属性值是否为NULL决定是否插入)</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-12 下午3:07:29
     * @param record
     * @return
     * @see
     */
    int insertSelective(PricePlanEntity record);

    /**
     * 
     * <p>(查询价格方案主信息)</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-12 下午3:09:24
     * @param id
     * @return
     * @see
     */
    PricePlanEntity selectByPrimaryKey(String id);

    /**
     * 
     * <p>(修改价格方案)</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-12 下午3:09:53
     * @param record
     * @return
     * @see
     */
    int updateByPrimaryKeySelective(PricePlanEntity record);
    
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
    PricePlanEntity findPricePlanByDeptRegionId(Date beginTime,String deptRegionId,String active);
    
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
     List<PricePlanEntity> findPricePlanByDownload(String deptRegionId,Date cuurentTime,String active);
    
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
     List<PricePlanEntity> queryPricePlanBatchInfo(PricePlanEntity pricePlanEntity,
		int start, int limit);
    
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
     List<PricePlanEntity> queryPricePlanBatchInfo(PricePlanEntity pricePlanEntity);
    
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
     Long queryPricePlanBatchInfoCount(PricePlanEntity record);
    
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
     List<ResultPricePlanDetailBean> queryPricePlanDetailInfo(QueryPricePlanDetailBean queryPricePlanDetailBean,int start,int limit);
    
    /**
     * 
     * <p>(查询价格方案明细-不分页)</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-12 下午5:02:24
     * @param queryPricePlanDetailBean
     * @return
     * @see
     */
     List<ResultPricePlanDetailBean> queryPricePlanDetailInfo(QueryPricePlanDetailBean queryPricePlanDetailBean);
    
    
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
     Long queryPricePlanDetailInfoCount(QueryPricePlanDetailBean queryPricePlanDetailBean);
    
    /**
     * 
     * <p>(查询同一个出发地方案下已经出现的重复目的地信息)</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-17 上午8:23:00
     * @param queryExistPricePlanDetailBean
     * @return
     * @see
     */
     List<ResultPricePlanDetailBean> isExistRpeatPricePlanDetailData(QueryExistPricePlanDetailBean queryExistPricePlanDetailBean);
     
     /**
      * 查询同一个出发地方案下已经出现的重复目的地空运价格信息
      * @param queryExistPricePlanDetailBean
      * @return
      */
     List<ResultPricePlanDetailBean> isExistRpeatAirLinePricePlanDetailData(QueryExistPricePlanDetailBean queryExistPricePlanDetailBean);
    
    /**
     * 
     * <p>(新增方案查询同一个出发地方案下已经出现的重复目的地信息)</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-19 下午7:45:09
     * @param queryExistPricePlanDetailBean
     * @return
     * @see
     */
     List<ResultPricePlanDetailBean> isExistRpeatPricePlanDetailForEdit(QueryExistPricePlanDetailBean queryExistPricePlanDetailBean);
    
    
    /**
     * 
     * <p>(激活价格方案)</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-17 下午3:09:24
     * @param pricePlanIds
     * @see
     */
    void activePricePlan(List<String> pricePlanIds);
    /**
     * 
     *
     * @Function: com.deppon.foss.module.pickup.pricing.api.server.dao.IPricePlanDao.queryComparePricePlanBatchInfo
     * @Description:根据名称和始发区域查询价格比对信息
     *
     * @param pricePlanEntity
     * @param start
     * @param limit
     * @return
     *
     * @version:v1.0
     * @author:130376-YANGKANG
     * @date:2014-5-20 下午3:48:47
     *
     * Modification History:
     * Date         Author      Version     Description
     * -----------------------------------------------------------------
     * 2014-5-20    130376-YANGKANG      v1.0.0         create
     */
    List<PricePlanEntity> queryComparePricePlanBatchInfo(PricePlanEntity pricePlanEntity, int start, int limit);
    //zb modify start
    /**
     * 
     * <p>(查询同一个出发和目的地方案下已经出现的重复目的地信息)</p> 
     * @author DP-Foss-zhangbin
     * @date 2015-05-27 上午12:23:00
     * @param queryExistPricePlanDetailBean
     * @return
     * @see
     */
	List<ResultPricePlanDetailBean> isExistRpeatPricePlanDetailDataForCustomer(
			QueryExistPricePlanDetailBean queryExistPriceBean);
	//zb modify end
	//zb modify start
	 /**
     * 
     * <p>(查询当前客户的当前版本和即将成为当前版本的版本)</p> 
     * @author DP-Foss-zhangbin
     * @date 2015-05-27 上午12:23:00
     * @param queryPricePlanForCustomer
     * @return
     * @see
     */
	Long queryPricePlanForCustomer(PricePlanEntity pricePlanEntity);
	//zb modify end
	/**
	 * 批量导入价格方案
	 * @param pricePlanBatch
	 * @return huangwei 2015-12-05
	 */
	int insertPricePlanAllBatch(List<PricePlanEntity> pricePlanBatch, List<PriceValuationEntity> priceValuationBatch, List<PriceCriteriaDetailEntity> priceCriteriaDetailEntityBatch, List<PopPriceDetailSectionEntity> popPriceDetailSectionBatch);
	
	/**
	 * 删除价格方案批处理
	 * @param priceValuationIdsList
	 * @param pricePlanIdsList
	 * @return
	 */
	int deletePricePlanBatch(List<String> priceValuationIdsList, List<String> pricePlanIdsList);
	/**
	 * 激活价格方案批处理
	 * @param priceValuationIdsList
	 * @param pricePlanIdsList
	 * @return
	 */
	int activePricePlanBatch(List<PricePlanEntity> pricePlanEntityList, List<String> priceValuationIdsList);
	
	/**
	 * 中止价格方案批处理
	 * @param pricePlanEntityList
	 * @param priceValuationEntityList
	 * @return
	 */
	int stopPricePlanBatch(List<PricePlanEntity> pricePlanEntityList, List<PriceValuationEntity> priceValuationEntityList);
	/**
	 * 
	 * @param pricePlanEntity
	 * @return
	 */
	List<PricePlanEntity> queryPricePlan(PricePlanEntity pricePlanEntity );
}