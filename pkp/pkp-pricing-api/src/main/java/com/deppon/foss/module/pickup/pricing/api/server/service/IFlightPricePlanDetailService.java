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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/server/service/IFlightPricePlanDetailService.java
 * 
 * FILE NAME        	: IFlightPricePlanDetailService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.server.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.FlightPricePlanDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.FlightPricePlanDetailException;


public interface IFlightPricePlanDetailService {

    /**
     * 
     * 新增航空代理公司航班运价方案明细
     * @author DP-Foss-YueHongJie
     * @date 2012-10-31 上午10:44:59
     * @return 1：成功；-1：失败
     */
    public int addFlightPricePlanDetail(FlightPricePlanDetailEntity entity)throws FlightPricePlanDetailException ;
    
    /**
     * 根据Id删除空运代理公司航班方案信息明细
     * @author DP-Foss-YueHongJie
     * @date 2012-10-31 上午10:44:59
     * @param  id 航空方案主键字符串数组
     * @return 1：成功；-1：失败
     * @see
     */
    public void deleteFlightPricePlanDetailById(List<String> ids)throws FlightPricePlanDetailException;
    
    /**
     * 修改航空代理公司航班运价方案明细
     * @author DP-Foss-YueHongJie
     * @date 2012-10-31 上午11:03:18
     * @param entity 航空公司运价方案实体
     * @return 1：成功；-1：失败
     * @see
     */
    public int updateFlightPricePlanDetail(FlightPricePlanDetailEntity entity)throws FlightPricePlanDetailException;
    
    /**
     * 根据传入对象查询符合条件所有空运代理公司航班运价方案信息明细
     * @author  DP-Foss-YueHongJie
     * @param entity 航空代理公司运价方案实体
     * @return 符合条件的实体列表
     */
    public List<FlightPricePlanDetailEntity> queryFlightPricePlanDetails(FlightPricePlanDetailEntity entity)throws FlightPricePlanDetailException;
    
    
    /**
     * 
     * 根据以下条件确定唯一航空公司运价方案明细提供
     * @author DP-Foss-YueHongJie
     * @param airlinesCode 航班公司编码
     * @param flightNo 航班号
     * @param loadOrgCode 配载部门编码
     * @param destDistictCode 到达站编码
     * @param goodsTypeCode 货物类型
     * @param billDate 当前日期 
     * @date 2012-10-31 下午2:36:28
     */
    public FlightPricePlanDetailEntity findFlightPricePlanDetail(String flightNo,String airlinesCode,String loadOrgCode,String destDistictCode ,String goodsTypeCode,BigDecimal weight,Date billDate)throws FlightPricePlanDetailException;
    
    /**
     * 
     * <p>分页查询方案明细</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-22 下午3:57:27
     * @param flightPricePlanDetailEntity
     * @param start
     * @param limit
     * @return
     * @see
     */
    public List<FlightPricePlanDetailEntity> queryFlightPricePlanDetailPagging(FlightPricePlanDetailEntity flightPricePlanDetailEntity, int start , int limit);
    
    /**
     * 
     * <p>查询明细总数</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-22 下午3:57:43
     * @param flightPricePlanDetailEntity
     * @return
     * @see
     */
    public Long queryFlightPricePlanDetailPaggingCount(FlightPricePlanDetailEntity flightPricePlanDetailEntity);
    
    
    /**
     * 
     * <p>查询单个明细</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-22 下午4:25:45
     * @param id
     * @return
     * @see
     */
    public FlightPricePlanDetailEntity queryFlightPricePlanDetailById(String id);
    
    
}