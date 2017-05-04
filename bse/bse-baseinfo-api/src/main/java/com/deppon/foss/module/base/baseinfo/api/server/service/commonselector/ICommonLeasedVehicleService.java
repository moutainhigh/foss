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
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/commonselector/ICommonLeasedVehicleService.java
 * 
 * FILE NAME        	: ICommonLeasedVehicleService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo-api
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.api.server.service
 * FILE    NAME: ICommonLeasedVehicleService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedTruckEntity;

/**
 * 公共组件--外请车查询接口定义
 * @author panGuangJun
 * @date 2012-12-1 上午9:23:15
 */
public interface ICommonLeasedVehicleService {
    /**
     * <p>根据条件有选择的检索出符合条件的“外请车（厢式车、挂车、拖头）”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author panGuangjun
     * @date 2012-12-1 上午10:23:19
     * @param airlinesAgent 以“外请车（厢式车、挂车、拖头）”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 符合条件的“外请车（厢式车、挂车、拖头）”实体列表
     */
     List<LeasedTruckEntity> queryLeasedVehicleListCondition(LeasedTruckEntity leasedTruck,int start,int limit);
    /**
     * <p>根据条件有选择的统计出符合条件的“外请车（厢式车、挂车、拖头）”实体记录数（条件做自动判断，只选择实体中非空值）</p> 
      * @author panGuangjun
      * @date 2012-12-1 上午10:23:19
     * @param leasedTruck 以“外请车（厢式车、挂车、拖头）”实体承载的条件参数实体
     * @return 符合条件的“外请车（厢式车、挂车、拖头）”实体记录条数
     */
     long queryLeasedVehicleRecordCountBySelectiveCondition(LeasedTruckEntity leasedTruck);
}
