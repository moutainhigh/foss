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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/server/dao/IFlightPricePlanDao.java
 * 
 * FILE NAME        	: IFlightPricePlanDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.FlightPricePlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.FlightPricePlanDto;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.FlightPricePlanException;

/**
 * 
 *  航空代理公司航班运价方案管理
 * @author DP-Foss-YueHongJie
 * @date 2012-10-31 下午3:35:22
 */
public interface IFlightPricePlanDao {
    
    /**
     * 
     * 新增航空代理公司航班运价方案
     * @author DP-Foss-YueHongJie
     * @date 2012-10-31 上午10:44:59
     * @return 1：成功；-1：失败
     */
     int addFlightPricePlan(FlightPricePlanEntity entity) ;
    
    /**
     * 批量根据Id删除空运代理公司航班方案信息
     * @author DP-Foss-YueHongJie
     * @date 2012-10-31 上午10:44:59
     * @param  id 航空方案主键字符串数组
     * @return 1：成功；-1：失败
     * @see
     */
     void deleteFlightPricePlanById(List<String> id);
    
    /**
     * 修改航空代理公司航班运价方案 
     * @author DP-Foss-YueHongJie
     * @date 2012-10-31 上午11:03:18
     * @param entity 航空公司运价方案实体
     * @return 1：成功；-1：失败
     * @see
     */
     int updateFlightPricePlan(FlightPricePlanEntity entity);
    
    /**
     * 根据传入对象查询符合条件所有空运代理公司航班运价方案信息 
     * @author  DP-Foss-YueHongJie
     * @param entity 航空代理公司运价方案实体
     * @return 符合条件的实体列表
     */
     List<FlightPricePlanEntity> queryFlightPricePlans(FlightPricePlanDto dto);
    
    
    /**
     * 
     * <p>批量激活</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-22 上午10:19:34
     * @param ids
     * @see
     */
     void activeFlightPricePlanByIds(List<String> ids); 
    
    
    /**
     * 
     * <p>分页查询方案</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-22 下午2:21:36
     * @param flightPricePlanEntity
     * @param start
     * @param limit
     * @return
     * @throws FlightPricePlanException
     * @see
     */
     List<FlightPricePlanEntity> queryFlightPricePlanByEntityPagging(FlightPricePlanEntity flightPricePlanEntity,int start,int limit)throws FlightPricePlanException;
    
    /**
     * 
     * <p>查询方案总数</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-22 下午2:22:05
     * @param flightPricePlanEntity
     * @return
     * @throws FlightPricePlanException
     * @see
     */
     Long queryFlightPricePlanByEntityPaggingCount(FlightPricePlanEntity flightPricePlanEntity)throws FlightPricePlanException;
    
    /**
     * 
     * <p>升级方案信息-后台按照复制前的数据复制一份信息</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-22 下午3:13:46
     * @param flightPricePlanEntity
     * @see
     */
     void copyFlightPricePlanEntity(FlightPricePlanEntity flightPricePlanEntity);
    
    /**
     * 
     * <p>查询单个方案</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-22 下午4:30:45
     * @param id
     * @return
     * @see
     */
     FlightPricePlanEntity getFlightPricePlanEntityById(String id);
}