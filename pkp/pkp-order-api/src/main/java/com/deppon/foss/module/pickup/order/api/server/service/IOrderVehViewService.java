/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 038590-foss-wanghui
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
 * PROJECT NAME	: pkp-order-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/api/server/service/IOrderTaskHandleService.java
 * 
 * FILE NAME        	: IOrderTaskHandleService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.order.api.shared.dto.DriverQueryDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.ExpressWorkerStatusDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OrderVehViewSignDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckSignDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.VehicleViewSmallZoneDto;

public interface IOrderVehViewService extends IService {
	/**
     * 
     * @Title: queryDriverByCommon 
     * @Description: 查询出对应签到司机统计信息,分页查询
     * @param @param driverQueryDto
     * @param @return    设定文件 
     * @return List<OwnTruckSignDto>    返回类型 
     * @throws
     */
    public List<OwnTruckSignDto> queryDriverByCommon(DriverQueryDto driverQueryDto ,int start, int limit);
    
    /**
     * 
     * @Title: queryDriverByCommonCount 
     * @Description: 查询出对应签到司机统计信息
     * @param @param driverQueryDto
     * @param @return    设定文件 
     * @return Long    返回类型 
     * @throws
     */
    public Long queryDriverByCommonCount(DriverQueryDto driverQueryDto); 
    
    /**
     * 
     * @Title: queryOrderVehViewByCommon 
     * @Description: 查询司机、车牌对应的订单信息
     * @param @param driverQueryDto
     * @param @return    设定文件 
     * @return List<OrderVehViewSignDto>    返回类型 
     * @throws
     */
	public List<OrderVehViewSignDto> queryOrderVehViewByCommon(DriverQueryDto driverQueryDto);
	
	/**
     * 
     * @Title: openExpressWorker 
     * @Description: 开启车辆
     * @param @param dto
     * @param @return    设定文件 
     * @return int    返回类型 
     * @throws
     */
	public int openExpressWorker(ExpressWorkerStatusDto dto);
   	
	/**
     * 
     * @Title: stopExrpessWorker 
     * @Description: 暂停车辆
     * @param @param dto
     * @param @return    设定文件 
     * @return int    返回类型 
     * @throws
     */
	public int stopExrpessWorker(ExpressWorkerStatusDto dto); 
	
	/**
     * 
     * @Title: queryDriverByCommonAll 
     * @Description: 查询出对应签到司机统计信息,总数据
     * @param @param driverQueryDto
     * @param @return    设定文件 
     * @return List<VehicleViewSmallZoneDto>    返回类型 
     * @throws
     */
    public List<VehicleViewSmallZoneDto> queryDriverByCommonAll(DriverQueryDto driverQueryDto ,int start, int limit);
	
	
    /**
     * 根据大区编码查询小区 
     */
    public List<DriverQueryDto> querySmallZoneCodesByBigZoneCodes(DriverQueryDto driverQueryDto);
}