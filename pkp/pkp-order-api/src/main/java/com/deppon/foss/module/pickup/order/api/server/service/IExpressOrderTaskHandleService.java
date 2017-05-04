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

import com.deppon.foss.esb.crm.server.order.CancelOrderRequest;
import com.deppon.foss.esb.crm.server.order.CancelOrderResponse;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.order.api.shared.dto.DispatchOrderVehicleDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.FailOrderDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OrderHandleDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.TransferOrderDto;

/**
 * 订单处理的service
 * @author 038590-foss-wanghui
 * @date 2012-10-23 上午9:49:49
 */
public interface IExpressOrderTaskHandleService extends IService {
	/** 
	 * 受理订单
	 * @param 
	 * 			id
	 * 				订单ID
	 * 			orderNo
	 * 				订单号
	 * 			orderType
	 * 				订单类型 -- 接货or转货
	 * 			rejectReason
	 * 				拒绝原因
	 * 			desc
	 * 				其他原因描述
	 * 			orderStatus
	 * 				订单待改状态
	 * 			originOrderStatus
	 * 				订单原始状态
	 * 			driverName
	 * 				司机姓名
	 * 			driverCode
	 * 				司机编码
	 * 			vehicleNo
	 * 				车牌号
	 * 			deviceNo
	 * 				设备号
	 * 			operateNotes
	 * 				订单操作备注
	 * 			operator
	 * 				操作人
	 * 			operatorCode
	 * 				操作人编码
	 * 			operateOrgName
	 * 				操作部门
	 * 			operateOrgCode
	 * 				操作部门编码
	 * 			operateTime
	 * 				操作时间
	 * 			isPda
	 * 				是否PDA
	 * 			isSms
	 * 				是否短信
	 * 			isCustomer
	 * 				是否发送客户
	 * 			pickupRegionCode
	 * 				接货小区Code
	 * 			pickupRegionName
	 * 				接货小区名称
	 * 			weight
	 * 				重量
	 * 			volume
	 * 				体积
	 * 			goodsQty
	 * 				件数
	 * 			driverMobile
	 * 				司机手机
	 * 			customerMobile
	 * 				客户手机
	 * 			pickupAddress
	 * 				接货地址
	 * 			salesDepartmentName
	 * 				营业部名称
	 * 			salesDepartmentCode
	 * 				营业部编码
	 * 			orderNotes
	 * 				备注
	 * 			pickupTime
	 * 				接货时间
	 * 			customerName
	 * 				客户姓名
	 * 			tel
	 * 				客户电话
	 * 			orderSendStatus
	 * 				订单发送状态
	 * @author 038590-foss-wanghui
	 * @date 2012-10-23 上午9:50:09
	 * @see com.deppon.foss.module.pickup.order.api.server.service.IExpressOrderTaskHandleService#acceptOrder(java.util.List)
	 */
	List<FailOrderDto> acceptOrder(List<OrderHandleDto> orderHandleDtoList);

	/** 
	 * 拒绝订单
	 * @param 
	 * 			id
	 * 				订单ID
	 * 			orderNo
	 * 				订单号
	 * 			orderType
	 * 				订单类型 -- 接货or转货
	 * 			rejectReason
	 * 				拒绝原因
	 * 			desc
	 * 				其他原因描述
	 * 			orderStatus
	 * 				订单待改状态
	 * 			originOrderStatus
	 * 				订单原始状态
	 * 			driverName
	 * 				司机姓名
	 * 			driverCode
	 * 				司机编码
	 * 			vehicleNo
	 * 				车牌号
	 * 			deviceNo
	 * 				设备号
	 * 			operateNotes
	 * 				订单操作备注
	 * 			operator
	 * 				操作人
	 * 			operatorCode
	 * 				操作人编码
	 * 			operateOrgName
	 * 				操作部门
	 * 			operateOrgCode
	 * 				操作部门编码
	 * 			operateTime
	 * 				操作时间
	 * 			isPda
	 * 				是否PDA
	 * 			isSms
	 * 				是否短信
	 * 			isCustomer
	 * 				是否发送客户
	 * 			pickupRegionCode
	 * 				接货小区Code
	 * 			pickupRegionName
	 * 				接货小区名称
	 * 			weight
	 * 				重量
	 * 			volume
	 * 				体积
	 * 			goodsQty
	 * 				件数
	 * 			driverMobile
	 * 				司机手机
	 * 			customerMobile
	 * 				客户手机
	 * 			pickupAddress
	 * 				接货地址
	 * 			salesDepartmentName
	 * 				营业部名称
	 * 			salesDepartmentCode
	 * 				营业部编码
	 * 			orderNotes
	 * 				备注
	 * 			pickupTime
	 * 				接货时间
	 * 			customerName
	 * 				客户姓名
	 * 			tel
	 * 				客户电话
	 * 			orderSendStatus
	 * 				订单发送状态
	 * @author 038590-foss-wanghui
	 * @date 2012-10-23 上午9:50:13
	 * @see com.deppon.foss.module.pickup.order.api.server.service.IExpressOrderTaskHandleService#rejectOrder(com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderEntity)
	 */
	boolean rejectOrder(OrderHandleDto orderHandleDto);

	
	Integer updateDispatchOrderStatusById(DispatchOrderVehicleDto dto);
	
	/**
	 * PDA异常，根据派车记录ID修改是否PDA为异常 且更新订单状态为：“待改接”
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
	 * @date 2012-11-9 下午3:13:35
	 */
	void updateIspdaByVehicleId(List<DispatchOrderVehicleDto> dtos);

	/**
	 * 
	 * 更新外部系统状态
	 * @param 
	 * 			id
	 * 				订单ID
	 * 			orderNo
	 * 				订单号
	 * 			orderType
	 * 				订单类型 -- 接货or转货
	 * 			rejectReason
	 * 				拒绝原因
	 * 			desc
	 * 				其他原因描述
	 * 			orderStatus
	 * 				订单待改状态
	 * 			originOrderStatus
	 * 				订单原始状态
	 * 			driverName
	 * 				司机姓名
	 * 			driverCode
	 * 				司机编码
	 * 			vehicleNo
	 * 				车牌号
	 * 			deviceNo
	 * 				设备号
	 * 			operateNotes
	 * 				订单操作备注
	 * 			operator
	 * 				操作人
	 * 			operatorCode
	 * 				操作人编码
	 * 			operateOrgName
	 * 				操作部门
	 * 			operateOrgCode
	 * 				操作部门编码
	 * 			operateTime
	 * 				操作时间
	 * 			isPda
	 * 				是否PDA
	 * 			isSms
	 * 				是否短信
	 * 			isCustomer
	 * 				是否发送客户
	 * 			pickupRegionCode
	 * 				接货小区Code
	 * 			pickupRegionName
	 * 				接货小区名称
	 * 			weight
	 * 				重量
	 * 			volume
	 * 				体积
	 * 			goodsQty
	 * 				件数
	 * 			driverMobile
	 * 				司机手机
	 * 			customerMobile
	 * 				客户手机
	 * 			pickupAddress
	 * 				接货地址
	 * 			salesDepartmentName
	 * 				营业部名称
	 * 			salesDepartmentCode
	 * 				营业部编码
	 * 			orderNotes
	 * 				备注
	 * 			pickupTime
	 * 				接货时间
	 * 			customerName
	 * 				客户姓名
	 * 			tel
	 * 				客户电话
	 * 			orderSendStatus
	 * 				订单发送状态
	 * @param orderStatus 订单状态
	 * @param reason 原因
	 * @author 038590-foss-wanghui
	 * @date 2012-12-6 上午10:21:50
	 */
	void updateExternalSystem(OrderHandleDto orderHandleDto, String reason);

	/**
	 * 添加订单操作历史
	 * @param 
	 * 			id
	 * 				订单ID
	 * 			orderNo
	 * 				订单号
	 * 			orderType
	 * 				订单类型 -- 接货or转货
	 * 			rejectReason
	 * 				拒绝原因
	 * 			desc
	 * 				其他原因描述
	 * 			orderStatus
	 * 				订单待改状态
	 * 			originOrderStatus
	 * 				订单原始状态
	 * 			driverName
	 * 				司机姓名
	 * 			driverCode
	 * 				司机编码
	 * 			vehicleNo
	 * 				车牌号
	 * 			deviceNo
	 * 				设备号
	 * 			operateNotes
	 * 				订单操作备注
	 * 			operator
	 * 				操作人
	 * 			operatorCode
	 * 				操作人编码
	 * 			operateOrgName
	 * 				操作部门
	 * 			operateOrgCode
	 * 				操作部门编码
	 * 			operateTime
	 * 				操作时间
	 * 			isPda
	 * 				是否PDA
	 * 			isSms
	 * 				是否短信
	 * 			isCustomer
	 * 				是否发送客户
	 * 			pickupRegionCode
	 * 				接货小区Code
	 * 			pickupRegionName
	 * 				接货小区名称
	 * 			weight
	 * 				重量
	 * 			volume
	 * 				体积
	 * 			goodsQty
	 * 				件数
	 * 			driverMobile
	 * 				司机手机
	 * 			customerMobile
	 * 				客户手机
	 * 			pickupAddress
	 * 				接货地址
	 * 			salesDepartmentName
	 * 				营业部名称
	 * 			salesDepartmentCode
	 * 				营业部编码
	 * 			orderNotes
	 * 				备注
	 * 			pickupTime
	 * 				接货时间
	 * 			customerName
	 * 				客户姓名
	 * 			tel
	 * 				客户电话
	 * 			orderSendStatus
	 * 				订单发送状态
	 * @author 038590-foss-wanghui
	 * @date 2012-11-7 上午11:14:52
	 */
	void addDispatchOrderActionHistory(OrderHandleDto orderHandleDto);
	
	/**
	 * 
	 * 订单开单更新
	 * @param 
	 * 			id
	 * 				订单ID
	 * 			orderNo
	 * 				订单号
	 * 			orderType
	 * 				订单类型 -- 接货or转货
	 * 			rejectReason
	 * 				拒绝原因
	 * 			desc
	 * 				其他原因描述
	 * 			orderStatus
	 * 				订单待改状态
	 * 			originOrderStatus
	 * 				订单原始状态
	 * 			driverName
	 * 				司机姓名
	 * 			driverCode
	 * 				司机编码
	 * 			vehicleNo
	 * 				车牌号
	 * 			deviceNo
	 * 				设备号
	 * 			operateNotes
	 * 				订单操作备注
	 * 			operator
	 * 				操作人
	 * 			operatorCode
	 * 				操作人编码
	 * 			operateOrgName
	 * 				操作部门
	 * 			operateOrgCode
	 * 				操作部门编码
	 * 			operateTime
	 * 				操作时间
	 * 			isPda
	 * 				是否PDA
	 * 			isSms
	 * 				是否短信
	 * 			isCustomer
	 * 				是否发送客户
	 * 			pickupRegionCode
	 * 				接货小区Code
	 * 			pickupRegionName
	 * 				接货小区名称
	 * 			weight
	 * 				重量
	 * 			volume
	 * 				体积
	 * 			goodsQty
	 * 				件数
	 * 			driverMobile
	 * 				司机手机
	 * 			customerMobile
	 * 				客户手机
	 * 			pickupAddress
	 * 				接货地址
	 * 			salesDepartmentName
	 * 				营业部名称
	 * 			salesDepartmentCode
	 * 				营业部编码
	 * 			orderNotes
	 * 				备注
	 * 			pickupTime
	 * 				接货时间
	 * 			customerName
	 * 				客户姓名
	 * 			tel
	 * 				客户电话
	 * 			orderSendStatus
	 * 				订单发送状态
	 * @author 038590-foss-wanghui
	 * @date 2013-2-2 上午11:22:48
	 */
	void orderWaybill(OrderHandleDto orderHandleDto);
	
	/**
	 * 更新派车记录
	 * @param 
	 * 			id
	 * 				订单ID
	 * 			orderNo
	 * 				订单号
	 * 			orderType
	 * 				订单类型 -- 接货or转货
	 * 			rejectReason
	 * 				拒绝原因
	 * 			desc
	 * 				其他原因描述
	 * 			orderStatus
	 * 				订单待改状态
	 * 			originOrderStatus
	 * 				订单原始状态
	 * 			driverName
	 * 				司机姓名
	 * 			driverCode
	 * 				司机编码
	 * 			vehicleNo
	 * 				车牌号
	 * 			deviceNo
	 * 				设备号
	 * 			operateNotes
	 * 				订单操作备注
	 * 			operator
	 * 				操作人
	 * 			operatorCode
	 * 				操作人编码
	 * 			operateOrgName
	 * 				操作部门
	 * 			operateOrgCode
	 * 				操作部门编码
	 * 			operateTime
	 * 				操作时间
	 * 			isPda
	 * 				是否PDA
	 * 			isSms
	 * 				是否短信
	 * 			isCustomer
	 * 				是否发送客户
	 * 			pickupRegionCode
	 * 				接货小区Code
	 * 			pickupRegionName
	 * 				接货小区名称
	 * 			weight
	 * 				重量
	 * 			volume
	 * 				体积
	 * 			goodsQty
	 * 				件数
	 * 			driverMobile
	 * 				司机手机
	 * 			customerMobile
	 * 				客户手机
	 * 			pickupAddress
	 * 				接货地址
	 * 			salesDepartmentName
	 * 				营业部名称
	 * 			salesDepartmentCode
	 * 				营业部编码
	 * 			orderNotes
	 * 				备注
	 * 			pickupTime
	 * 				接货时间
	 * 			customerName
	 * 				客户姓名
	 * 			tel
	 * 				客户电话
	 * 			orderSendStatus
	 * 				订单发送状态
	 * @author 038590-foss-wanghui
	 * @date 2013-2-3 下午3:07:02
	 */
	void updateDispatchVehicleRecord(OrderHandleDto orderHandleDto);
	
	/**
	 * 根据模板获取短信内容
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-6-3 下午6:44:22
	 */
	String getSmsContent(String smsCode, OrderHandleDto orderHandleDto);
	
	/**
	 * 撤销订单
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-8-20 下午4:08:14
	 */
	boolean revokeTransferOrder(TransferOrderDto dto);
	
	/**
	 * 取消订单
	 * @param payload
	 * @param response
	 */
	void cancelOrder(CancelOrderRequest payload, CancelOrderResponse response);
}