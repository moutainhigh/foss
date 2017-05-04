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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/server/service/IFlightPricePlanService.java
 * 
 * FILE NAME        	: IFlightPricePlanService.java
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

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.FlightPricePlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.GoodsTypeEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.FlightDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.FlightPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.FlightPricePlanDto;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.FlightPricePlanException;

/**
 * 
 * 航空公司运价方案服务
 * @author DP-Foss-YueHongJie
 * @date 2012-10-31 下午3:17:34
 */
public interface IFlightPricePlanService extends IService{

    /**
     * 
     * 添加运价方案信息
     * @author DP-Foss-YueHongJie
     * @date 2012-10-31 下午3:17:53
     */
     FlightPricePlanEntity addFlightPricePlanInfo(FlightPricePlanEntity entity)throws FlightPricePlanException;
    
    /**
     * 
     * 批量删除运价方案信息
     * @author DP-Foss-YueHongJie
     * @date 2012-10-31 下午3:18:04
     */
     void deleteFlightPricePlanByIds(List<String> ids)throws FlightPricePlanException;
    
    /**
     * 
     * <p>批量激活方案</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-21 下午1:51:31
     * @param ids
     * @throws FlightPricePlanException
     * @see
     */
     void activeFlightPricePlanByIds(List<String> ids)throws FlightPricePlanException;
    
    
    /**
     * 
     * <p>立即激活</p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-3-1 下午4:27:27
     * @param flightPricePlanEntity
     * @throws FlightPricePlanException
     * @see
     */
     void immediatelyActiveFlightPrice(FlightPricePlanEntity flightPricePlanEntity)throws FlightPricePlanException;
    
    /**
     * 
     * <p>立即中止</p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-3-1 下午4:28:16
     * @param flightPricePlanEntity
     * @throws FlightPricePlanException
     * @see
     */
     void immediatelyStopFlightPrice(FlightPricePlanEntity flightPricePlanEntity)throws FlightPricePlanException;
    
    
    /**
     * 
     * 修改运价方案信息 
     * @author DP-Foss-YueHongJie
     * @date 2012-10-31 下午3:18:20
     */
     FlightPricePlanEntity updateFlightPricePlanByEntity(FlightPricePlanEntity entity)throws FlightPricePlanException;
    
    /**
     * 
     * 查询运价方案信息
     * @author DP-Foss-YueHongJie
     * @date 2012-10-31 下午3:18:52
     */
     List<FlightPricePlanEntity> queryFlightPricePlanByEntity(FlightPricePlanDto dto)throws FlightPricePlanException;
    
    /**
     * 
     * <p>分页查询运价方案信息</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-22 下午3:29:35
     * @param flightPricePlanEntity
     * @param start
     * @param limit
     * @return
     * @throws FlightPricePlanException
     * @see
     */
     List<FlightPricePlanEntity> queryFlightPricePlanByEntity(FlightPricePlanEntity flightPricePlanEntity,int start,int limit)throws FlightPricePlanException;
    
    
    /**
     * 
     * <p>查询航空代理运价方案总数</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-22 下午3:29:18
     * @param flightPricePlanEntity
     * @return
     * @throws FlightPricePlanException
     * @see
     */
     Long queryFlightPricePlanByEntityCount(FlightPricePlanEntity flightPricePlanEntity)throws FlightPricePlanException;
    
    
    /**
     * 
     * 根据条件查询唯一运价方案信息
     * @author DP-Foss-YueHongJie
     * @param flightCode 航班公司编码
     * @param loadOrgCode 配载部门编码
     * @param active 是否生效
     * @param billDate 当前日期 
     * @date 2012-10-31 下午3:19:10
     */
     FlightPricePlanEntity findFlightPricePlanByCondition(String flightCode,String loadOrgCode,String active,Date billDate)throws FlightPricePlanException;
    
    
    /**
     * 
     * <p>查询单个方案</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-22 下午4:29:33
     * @return
     * @see
     */
     FlightPricePlanEntity getFlightPricePlanEntityById(String id);
    
    /**
     * 
     * <p>复制运价方案</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-24 下午1:48:02
     * @param id
     * @return
     * @see
     */
     FlightPricePlanEntity copyFlightPricePlanEntity(String id);
     
     /**
      * excel批量导入保存航空运价方案
      * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
      * @author DP-Foss-YueHongJie
      * @date 2013-4-7 下午1:37:38
      * @param detail
      * @param airlines
      * @param loadOrgs
      * @param destRegion
      * @param goods
      * @param flightDtos
      * @see
      */
     public String addFlightPricePlanBatch(Map<String,List<FlightPriceDto>> detail,Map<String,AirlinesEntity> airlines,Map<String,OrgAdministrativeInfoEntity> loadOrgs,Map<String,AdministrativeRegionsEntity> destRegion,Map<String,GoodsTypeEntity> goods,Map<String,FlightDto> flightDtos);
}