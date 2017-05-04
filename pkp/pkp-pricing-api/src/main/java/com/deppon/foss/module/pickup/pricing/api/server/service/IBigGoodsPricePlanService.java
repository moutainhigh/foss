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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/server/service/IPricePlanService.java
 * 
 * FILE NAME        	: IPricePlanService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.server.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PricePlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionBigGoodsArriveEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionBigGoodsEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PricePlanDetailDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryPricePlanDetailBean;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.BigGoodsPriceManageMentVo;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.PriceManageMentVo;

/**
 * 
 * (时效方案主信息,对时效主方案的管理定义，包括方案新增、变更、激活操作)
 * @author 岳洪杰
 * @date 2012-10-12 上午10:47:06
 * @since
 * @version
 */
public interface IBigGoodsPricePlanService extends IService {
    
    /**
     * 
     * <p>(根据传入生效时间与始发区域ID加上数据状态查询所符合的最新价格方案版本)</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-12 下午3:12:01
     * @param beginTime 生效时间
     * @param deptRegionId 始发区域
     * @param active 是否激活
     * @return
     * @see
     */
     PricePlanEntity findPricePlanByRegionId(Date beginTime,String deptRegionId,String active);
    
    /**
     * 
     * <p>(新增价格方案)</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-14 下午1:48:33
     * @param pricePlanEntity
     * @return
     * @see
     */
    PricePlanEntity addPricePlan(PricePlanEntity pricePlanEntity);
    
    /**
     * 
     * <p>(新增价格方案明细)</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-14 下午7:17:01
     * @param dto
     * @return
     * @see
     */
    List<PricePlanDetailDto> addPricePlanDetail(PricePlanDetailDto pricePlanDetailDto);
    
    
    /**
     * 
     * <p>将状态为未激活的记录更新为激活状态，并且要新激活的记录需同已有激活记录进行失效截止日期的正确衔接</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-12 下午2:04:13
     * @param pricePlanIds
     * @see
     */
    void activePricePlan(List<String> pricePlanIds);
    
    
    /**
     * 
     * <p>立即激活</p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-2-21 上午10:26:02
     * @param pricePlanEntity
     * @see
     */
    void immediatelyActivePricePlan(PricePlanEntity pricePlanEntity);
    
    
    /**
     * 
     * <p>(删除价格主方案-草稿状态
     * 	        删除步骤,按照顺序删除计费明细,计费规则,价格方案)</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-12 上午11:36:03
     * @param pricePlanIds
     * @return
     * @see
     */
    void deletePricePlan(List<String> pricePlanIds);
    
    /**
     * 
     * <p>(删除价格明细-草稿状态
     * 	        删除步骤,按照顺序删除计费明细,计费规则)</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-12 上午11:36:03
     * @param valuationIds
     * @return
     * @see
     */
     List<PricePlanDetailDto> deletePriceDetailPlan(List<String> valuationIds);
    
    
    /**
     * 
     * <p>(复制方案)</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-12 下午5:24:19
     * @param pricePlanId
     * @return string pricePlanId
     */
    String  copyPricePlan(String pricePlanId);
    
    /**
     * 
     * <p>
     * (查询复制方案信息以及分页的明细信息)
     * 
     * </p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-12 下午5:20:23
     * @param pricePlanId
     * @param start 分页起始页
     * @param limit 分页截止页
     * @return
     * @see
     */
    BigGoodsPriceManageMentVo queryCopyPricePlanInfo(String pricePlanId);
    
    /**
     * 
     * queryPricePlanBatchInfo(分页查询价格方案)
     * @param pricePlanEntity
     * @param start
     * @param limit
     * @return 
     * List<PricePlanEntity>
     * @exception 
     * @since  1.0.0
     */
    List<PricePlanEntity> queryPricePlanBatchInfo(PricePlanEntity pricePlanEntity, int start,int limit);
    
    /**
     * 
     * queryPricePlanBatchInfoCount(价格方案查询总数)
     * (这里描述这个方法适用条件 – 可选)
     * @param pricePlanEntity
     * @return 
     * Long 总记录数 
     * @exception 
     * @since  1.0.0
     */
    Long queryPricePlanBatchInfoCount(PricePlanEntity pricePlanEntity);
    
    /**
     * 
     * queryPricePlanDetailInfo
     * (查询价格方案明细分页)
     * @param queryPricePlanDetailBean
     * @author DP-Foss-YueHongJie
     * @return 
     * List<ResultPricePlanDetailBean>
     * @exception 
     * @since  1.0.0
     */
     List<PricePlanDetailDto> queryPricePlanDetailInfo(QueryPricePlanDetailBean queryPricePlanDetailBean,int start,int limit);
     
     /**
      * 
      * <p>查询汽运价格方案</p> 
      * @author DP-Foss-YueHongJie
      * @date 2013-4-10 下午5:37:29
      * @param queryPricePlanDetailBean
      * @return
      * @see
      */
     List<PricePlanDetailDto> queryPricePlanDetailInfo(QueryPricePlanDetailBean queryPricePlanDetailBean);
    
    
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
     * <p>(修改价格方案信息)</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-12 下午7:14:52
     * @param priceEntity
     * @see
     */
     PricePlanEntity modifyPricePlan(PricePlanEntity priceEntity);
    
    /**
     * 
     * <p>(修改方案明细信息)</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-12 下午7:17:38
     * @param priceManageMentVo
     * @see
     */
     List<PricePlanDetailDto> modifyPricePlanDetail(BigGoodsPriceManageMentVo bigGoodspriceManageMentVo);
    
  
    /**
     * 
     * <p>(中止方案)</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-19 上午10:31:23
     * @param pricePlanEntity
     * @see
     */
     void stopPricePlan(PricePlanEntity pricePlanEntity);
    
    /**
     * 
     * <p>(修改价格方信息)</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-20 下午6:39:23
     * @param priceManageMentVo
     * @see
     */
     void modifyPricePlan(PriceManageMentVo priceManageMentVo);
 

    /**
     * 批量导入价格方案和明细
     * 
     * @author zhangdongping
     * @date 2012-12-23 上午12:35:09
     * @param detailMap
     * @param priceRegionEntityMap
     * @param productEntityMap
     * @see
     */
     void addPricePlanBatch(Map<String, List<PricePlanDetailDto>> detailMap,
 			Map<String, PriceRegionBigGoodsEntity> priceRegionEntityMap,
 			Map<String, PriceRegionBigGoodsArriveEntity> priceArrvRegionEntityMap, 
 			Map<String, ProductEntity> productEntityMap);
 
    
    /**
     * 
     * <p>查询单个方案主信息</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-23 下午3:13:02
     * @param id
     * @return
     * @see
     */
     PricePlanEntity getPricePlanEntity(String id);
    
    
    /**
     * 
     * <p>价格方案导出</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-27 上午10:06:22
     * @param queryPricePlanDetailBean
     * @return
     * @see
     */
     ExportResource  exportPricePlan(PricePlanEntity pricePlanEntity);
     /**
      * 
      *
      * @Function: com.deppon.foss.module.pickup.pricing.api.server.service.IPricePlanService.queryPricePlanCompareResult
      * @Description:查询价格方案比对结果集
      *
      * @param pricePlanEntityOld
      * @param pricePlanEntity
      * @return
      *
      * @version:v1.0
      * @author:130376-YANGKANG
      * @date:2014-5-9 上午11:21:00
      *
      * Modification History:
      * Date         Author      Version     Description
      * -----------------------------------------------------------------
      * 2014-5-9    130376-YANGKANG      v1.0.0         create
      */
     List<PricePlanDetailDto> queryPricePlanCompareResult(PricePlanEntity pricePlanEntityOld,PricePlanEntity pricePlanEntity);
    /**
     * 
     *
     * @Function: com.deppon.foss.module.pickup.pricing.api.server.service.IPricePlanService.exportPricePlanCompareResult
     * @Description:价格方案比对结果导出
     *
     * @param pricePlanDetailDtos
     * @return
     *
     * @version:v1.0
     * @author:130376-YANGKANG
     * @date:2014-5-9 上午10:34:59
     *
     * Modification History:
     * Date         Author      Version     Description
     * -----------------------------------------------------------------
     * 2014-5-9    130376-YANGKANG      v1.0.0         create
     */
     ExportResource exportPricePlanCompareResult(PricePlanEntity pricePlanEntityOld, PricePlanEntity pricePlanEntity);
    /**
     * 
     * <p>价格方案明细导出</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-27 上午10:06:22
     * @param queryPricePlanDetailBean
     * @return
     * @see
     */
     ExportResource  exportPricePlanDetail(QueryPricePlanDetailBean queryPricePlanDetailBean);
     /**
      * 
      *
      * @Function: com.deppon.foss.module.pickup.pricing.api.server.service.IPricePlanService.queryComparePricePlanBatchInfo
      * @Description:分页查询价格比对的方案
      *
      * @param pricePlanEntity
      * @param start
      * @param limit
      * @return
      *
      * @version:v1.0
      * @author:130376-YANGKANG
      * @date:2014-5-20 下午3:58:03
      *
      * Modification History:
      * Date         Author      Version     Description
      * -----------------------------------------------------------------
      * 2014-5-20    130376-YANGKANG      v1.0.0         create
      */
     List<PricePlanEntity> queryComparePricePlanBatchInfo(PricePlanEntity pricePlanEntity, int start,int limit);

	List<PricePlanDetailDto> queryPricePlanDetailDtoList(String pricePlanId);
}