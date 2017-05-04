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
package com.deppon.foss.module.pickup.pricing.dubbo.api.server.service;

import java.util.Date;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.FlightPricePlanEntity;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.exception.FlightPricePlanException;

/**
 * 
 * 航空公司运价方案服务
 * @author DP-Foss-YueHongJie
 * @date 2012-10-31 下午3:17:34
 */
public interface IFlightPricePlanService extends IService{
    
    
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
}