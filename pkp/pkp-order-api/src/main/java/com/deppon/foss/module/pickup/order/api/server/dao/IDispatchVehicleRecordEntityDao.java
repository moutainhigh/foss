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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/api/server/dao/IDispatchVehicleRecordEntityDao.java
 * 
 * FILE NAME        	: IDispatchVehicleRecordEntityDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchVehicleRecordEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.DispatchOrderVehicleDto;

/**
 * 
 * 派车记录DAO
 * @author 038590-foss-wanghui
 * @date 2012-12-18 下午8:38:16
 */
public interface IDispatchVehicleRecordEntityDao {

	/**
	 * 
	 * 新增派车记录
	 * @param record
	 * 			tSrvDispatchOrderId
	 * 				调度订单id
	 * 			driverName
	 * 				司机姓名
	 * 			driverCode
	 * 				司机code
	 * 			vehicleNo
	 * 				车牌号
	 * 			deliverTime
	 * 				派车时间
	 * 			pickupRegionCode
	 * 				定人定区code
	 * 			pdaStatus
	 * 				是否使用PDA
	 * 			pickupRegionName
	 * 				定人定区名字
	 * 			orderStatus
	 * 				订单状态
	 * @author 038590-foss-wanghui
	 * @date 2012-12-18 下午8:39:27
	 */
    int addDispatchVehicleRecord(DispatchVehicleRecordEntity record);
    
    /**
	 * 根据查询处理订单任务DTO查询调度订单派车记录
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
	 * @date 2012-11-7 上午10:17:49
	 */
	List<DispatchOrderVehicleDto> queryVehicleRecordBy(DispatchOrderVehicleDto dto,int start,int limit);
	
	/**
	 * 获得派车记录总数
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
	 * @author 038590-foss-wanghui
	 * @date 2012-12-18 下午8:39:43
	 */
	Long getVehicleRecordByCount(DispatchOrderVehicleDto dto);
	
	/**
	 * 根据派车记录ID将派车记录ispad 改为异常
	 * @param entity
	 * 			tSrvDispatchOrderId
	 * 				调度订单id
	 * 			driverName
	 * 				司机姓名
	 * 			driverCode
	 * 				司机code
	 * 			vehicleNo
	 * 				车牌号
	 * 			deliverTime
	 * 				派车时间
	 * 			pickupRegionCode
	 * 				定人定区code
	 * 			pdaStatus
	 * 				是否使用PDA
	 * 			pickupRegionName
	 * 				定人定区名字
	 * 			orderStatus
	 * 				订单状态
	 * @author 097972-foss-dengtingting
	 * @date 2012-11-9 下午3:25:20
	 */
	Integer updateIspdaByVehicleId(DispatchVehicleRecordEntity entity);
	
	/**
	 * 批量添加派车记录 
	 * @param record
	 * 			tSrvDispatchOrderId
	 * 				调度订单id
	 * 			driverName
	 * 				司机姓名
	 * 			driverCode
	 * 				司机code
	 * 			vehicleNo
	 * 				车牌号
	 * 			deliverTime
	 * 				派车时间
	 * 			pickupRegionCode
	 * 				定人定区code
	 * 			pdaStatus
	 * 				是否使用PDA
	 * 			pickupRegionName
	 * 				定人定区名字
	 * 			orderStatus
	 * 				订单状态
	 * @author 038590-foss-wanghui
	 * @date 2012-12-18 下午8:40:49
	 */
	int batchAddDispatchVehicleRecord(List<DispatchVehicleRecordEntity> vehicleRecordEntities);
	
	/**
	 * 根据订单id更新派车记录
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
	 * @author 038590-foss-wanghui
	 * @date 2013-2-3 上午10:56:12
	 */
	Integer updateVehicleRecordByOrderId(DispatchOrderVehicleDto dispatchOrderVehicleDto);
	/**
	 * 
	 * 不分页查询派车记录（导出用）
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-6-4 上午11:55:38
	 */
	List<DispatchOrderVehicleDto> queryVehicleRecord(DispatchOrderVehicleDto dto);

	/**
	 * 根据查询小件处理订单任务DTO查询调度订单派车记录
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
	 * @date 2012-11-7 上午10:17:49
	 */
	Long getExpressVehicleRecordByCount(DispatchOrderVehicleDto dto);

	/**
	 * 根据查询小件处理订单任务DTO查询调度订单派车记录
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
	 * @date 2012-11-7 上午10:17:49
	 */
	List<DispatchOrderVehicleDto> queryExpressVehicleRecordBy(DispatchOrderVehicleDto dto, int start, int limit);
}