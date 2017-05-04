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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/api/server/service/IOrderTaskQueryService.java
 * 
 * FILE NAME        	: IOrderTaskQueryService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.api.server.service;

import java.io.InputStream;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.order.api.shared.dto.DispatchOrderConditionDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.DispatchOrderDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.DispatchOrderVehicleDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OrderQueryHandleDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckConditionDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckDto;

/**
 * 订单查询Service
 * @author 038590-foss-wanghui
 * @date 2012-10-20 上午10:17:18
 */
public interface IOrderExpressService extends IService {
	/**
	 * 
	 * 查询小件订单
	 * @param start
	 * @param limit
	 * @return
	 */
	DispatchOrderDto queryExpressDispatchOrders(DispatchOrderConditionDto dispatchOrderConditionDto, int start, int limit);

	/**
	 * 查询可用人员
	 * 
	 * @param ownTruckConditionDto
	 * @return
	 */
	List<OwnTruckDto> queryUsedUser(OwnTruckConditionDto ownTruckConditionDto);
	/**
	 * 查询全部人员
	 * 
	 * @param ownTruckConditionDto
	 * @return
	 */
	List<OwnTruckDto> queryAllUser(OwnTruckConditionDto ownTruckConditionDto, int start, int limit);

	/**
	 * 查询小件派车记录
	 * @param dto
	 * @param start
	 * @param limit
	 * @return
	 */
	List<DispatchOrderVehicleDto> queryVehicleRecordBy(DispatchOrderVehicleDto dto, int start, int limit);
	
	/**
	 *根据查询条件查询调度订单派车记录总条数 
	 * @param dto
	 * 			id
	 * 				派车记录Id
	 * 			orderId
	 * 				订单ID
	 * 			orderVehicleOrgName
	 * 				约车部门名称
	 * 			orderNo
	 * 				订单号
	 * 			deliverBeginTime
	 * 				派车时间 ==处理订单时间开始时间
	 * 			deliverEndTime
	 * 				派车时间 ==处理订单时间结束时间
	 * 			earliestPickupTime
	 * 				接货最早时间 == 用车时间
	 * 			latestPickupTime
	 * 				接货最晚时间
	 * 			orderVehicleTime
	 * 				约车时间
	 * 			driverName
	 * 				司机姓名
	 * 			vehicleNo
	 * 				车牌号
	 * 			orderNotes
	 * 				备注 ==订单备注
	 * 			pickupRegionCode
	 * 				定人定区
	 * 			orderSendStatus
	 * 				订单任务发送状态
	 * 			orderStatus
	 * 				订单任务完成状态 == 订单状态
	 * 			orderType
	 * 				订单类型
	 * 			usecarTime
	 * 				用车时间
	 * 			pdaStatus
	 * 				PDA使用状态
	 * 			deliverTime
	 * 				派车时间
	 * @author 097972-foss-dengtingting
	 * @date 2012-11-7 下午1:58:49
	 */
	Long getVehicleRecordByCount(DispatchOrderVehicleDto dto);
	
	/**
	 * 导出列表
	 * @param dto
	 * @return
	 */
	InputStream queryVehicleRecord(DispatchOrderVehicleDto dto);

	Long queryAllUserCount(OwnTruckConditionDto ownTruckConditionDto);

	/**
	 * 根据页面录入的城市code获取查询需要的行政区域列表
	 * @param countyCodes
	 * @return
	 */
	OrderQueryHandleDto setOrderQueryHandleDto(String countyCodes);
	
}