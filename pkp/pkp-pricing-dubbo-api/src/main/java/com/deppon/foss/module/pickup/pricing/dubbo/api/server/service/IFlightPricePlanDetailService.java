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
package com.deppon.foss.module.pickup.pricing.dubbo.api.server.service;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.FlightPricePlanDetailEntity;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.exception.FlightPricePlanDetailException;


public interface IFlightPricePlanDetailService {
    
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
    
}