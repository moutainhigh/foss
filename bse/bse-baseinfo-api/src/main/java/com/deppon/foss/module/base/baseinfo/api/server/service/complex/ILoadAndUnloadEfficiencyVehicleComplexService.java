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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/complex/ILoadAndUnloadEfficiencyVehicleComplexService.java
 * 
 * FILE NAME        	: ILoadAndUnloadEfficiencyVehicleComplexService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service.complex;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.complex.LoadAndUnloadEfficiencyVehicleDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LoadAndUnloadEfficiencyVehicleException;

/**
 * 财务组织复杂查询 service接口
 * 
 * @author 087584-foss-lijun
 * @date 2012-12-18 上午11:04:19
 */
public interface ILoadAndUnloadEfficiencyVehicleComplexService {

    /**
     * 根据车辆的车牌号，部门编码，查询装卸车标准（卸一车需要多长时间）
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 上午13:49:37
     * 
     * @param 装车小时
     *            ，分钟，卸车小时，分钟。
     */
    LoadAndUnloadEfficiencyVehicleDto gainLoadAndUnloadEfficiencyVehicle(
	    String plateNumber, String orgCode)
	    throws LoadAndUnloadEfficiencyVehicleException;

    /**
     * 根据车辆的车牌号，部门编码，查询装卸车标准（卸一车需要多长时间）
     * 
     * 如果当前组织没有，向上查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 上午13:49:37
     */
    LoadAndUnloadEfficiencyVehicleDto gainLoadAndUnloadEfficiencyVehicleUp(
	    String plateNumber, String orgCode)
	    throws LoadAndUnloadEfficiencyVehicleException;
}
