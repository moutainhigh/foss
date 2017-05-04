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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/server/dao/IFlightPricePlanDetailDao.java
 * 
 * FILE NAME        	: IFlightPricePlanDetailDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.server.dao;

import java.util.List;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.FlightPricePlanDetailEntity;


public interface IFlightPricePlanDetailDao {
    /**
     * 
     * 新增航空代理公司航班运价方案明细
     * @author DP-Foss-YueHongJie
     * @date 2012-10-31 上午10:44:59
     * @return 1：成功；-1：失败
     */
     int addFlightPricePlanDetail(FlightPricePlanDetailEntity entity) ;
    
    
    /**
     * 修改航空代理公司航班运价方案明细
     * @author DP-Foss-YueHongJie
     * @date 2012-10-31 上午11:03:18
     * @param entity 航空公司运价方案实体
     * @return 1：成功；-1：失败
     * @see
     */
     int updateFlightPricePlanDetail(FlightPricePlanDetailEntity entity);
    
    /**
     * 根据传入对象查询符合条件所有空运代理公司航班运价方案信息明细
     * @author  DP-Foss-YueHongJie
     * @param entity 航空代理公司运价方案实体
     * @return 符合条件的实体列表
     */
     List<FlightPricePlanDetailEntity> queryFlightPricePlanDetails(FlightPricePlanDetailEntity entity);
    
    
    /**
     * 
     * <p>查询航空运价明细分页</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-22 下午3:45:37
     * @param entity
     * @param start
     * @param limit
     * @return
     * @see
     */
     List<FlightPricePlanDetailEntity> queryFlightPricePlanDetailPagging(FlightPricePlanDetailEntity entity,int start,int limit);
    
    /**
     * 
     * <p>查询航空运价明细总数</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-22 下午3:45:45
     * @param entity
     * @return
     * @see
     */
     Long queryFlightPricePlanDetailPaggingCount(FlightPricePlanDetailEntity entity);
    
    /**
     * 
     * <p>查询唯一航空运价信息</p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-1-15 下午8:11:50
     * @param flightPricePlanId 航空运价主方案信息ID
     * @param destDistictCode 目的地站
     * @param goodsTypeCode 货物类别
     * @param flightNo 航班号
     * @param active 状态
     * @param currencyCode 币种
     * @return
     * @see
     */
     FlightPricePlanDetailEntity queryUniquenessFlightPricePlanDetail(String flightPricePlanId,String destDistictCode ,String goodsTypeCode, String flightNo,String active,String currencyCode);
    /**
     * 
     * <p>升级方案明细信息</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-22 下午4:03:20
     * @param flightPricePlanDetailEntity
     * @see
     */
     void copyFlightPricePlanDetailEntity(FlightPricePlanDetailEntity flightPricePlanDetailEntity);
    
    /**
     * 
     * <p>批量激活方案明细</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-22 下午4:06:06
     * @param ids
     * @see
     */
     void activeFlightPricePlanDetailByIds(List<String> ids);
    
    
    /**
     * 
     * <p>批量删除方案明细</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-22 下午4:07:21
     * @param ids
     * @see
     */
     void deleteFlightPricePlanDetailById(List<String> ids);
     
    /**
     * 
     * <p>查询单个明细</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-22 下午4:26:54
     * @param id
     * @return
     * @see
     */
     FlightPricePlanDetailEntity queryFlightPricePlanDetailById(String id);
}