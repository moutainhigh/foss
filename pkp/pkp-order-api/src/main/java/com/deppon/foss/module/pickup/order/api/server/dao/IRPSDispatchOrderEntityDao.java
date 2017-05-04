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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/api/server/dao/IDispatchOrderEntityDao.java
 * 
 * FILE NAME        	: IDispatchOrderEntityDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.api.server.dao;

import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.RPSOrderJobEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.DispatchOrderConditionDto;

/**
 * rps调度订单实体DAO
 * @author 038590-foss-wanghui
 * @date 2012-10-26 上午9:28:19
 */
public interface IRPSDispatchOrderEntityDao {
	
	/**
	 * 
	 * 根据订单号，查询订单
	 * @param conditionDto
	 * 			orderNo
	 * 				订单号
	 * 			districtList
	 * 				行政区域list
	 * 			smallZoneList
	 * 				定人定区List
	 * 			businessAreaList
	 * 				营业区List
	 * 			vehicleType
	 * 				车型
	 * 			orderStatus
	 * 				订单状态
	 * 			salesDepartmentCodes
	 * 				营业部
	 * 			serviceFleetList
	 * 				接送货车队组
	 * 			status
	 * 				签到状态
	 * 			defaultOrderStatus
	 * 				默认的订单状态
	 * 			driverCode
	 * 				司机code
	 * 			vehicleNo
	 * 				车牌号
	 * 			deviceNo
	 * 				设备号
	 * 			fleetCode
	 * 				车队编码
	 * 			isPda
	 * 				是否PDA
	 * @author ibm-wangfei
	 * @date Dec 11, 2012 3:45:13 PM
	 */
	DispatchOrderEntity queryOrdersByOrderNo(DispatchOrderConditionDto dto);
	
	/**
	 * 按照订单号删除订单
	 * @param record
	 * @return
	 */
	int deleteDispatchOrderForNo(DispatchOrderConditionDto dto);
	
	/**
	 * 
	 * 插入调度订单实体
	 * @param record
	 * 			orderNo
	 * 				订单号
	 * 			orderVehicleOrgName
	 * 				约车部门名称
	 * 			orderVehicleOrgCode
	 * 				约车部门code
	 * 			orderVehicleTime
	 * 				约车时间
	 * 			orderType
	 * 				订单类型
	 * 			vehicleType
	 * 				车型
	 * 			weight
	 * 				重量
	 * 			volume
	 * 				体积
	 * 			pickupProvince
	 * 				接货省
	 * 			pickupCity
	 * 				接货市
	 * 			pickupCounty
	 * 				接货区
	 * 			pickupElseAddress
	 * 				接货其他地址信息
	 * 			earliestPickupTime
	 * 				接货最早时间
	 * 			latestPickupTime
	 * 				接货最晚时间
	 * 			customerName
	 * 				客户姓名
	 * 			goodsName
	 * 				货物名称
	 * 			goodsType
	 * 				货物类型
	 * 			goodsPackage
	 * 				包装
	 * 			tel
	 * 				电话
	 * 			mobile
	 * 				手机
	 * 			goodsQty
	 * 				件数
	 * 			consigneeAddress
	 * 				收货地址
	 * 			orderNotes
	 * 				备注
	 * 			driverName
	 * 				司机姓名
	 * 			driverCode
	 * 				司机code
	 * 			vehicleNo
	 * 				车牌号
	 * 			deviceNo
	 * 				设备号
	 * 			salesDepartmentName
	 * 				营业部名称
	 * 			salesDepartmentCode
	 * 				营业部code
	 * 			orderStatus
	 * 				订单状态
	 * 			orderSendStatus
	 * 				订单发送状态
	 * 			operateNotes
	 * 				操作备注
	 * 			operator
	 * 				操作人名称
	 * 			operatorCode
	 * 				操作人code
	 * 			operateOrgName
	 * 				操作部门code
	 * 			operateOrgCode
	 * 				操作部门name
	 * 			operateTime
	 * 				操作时间
	 * 			preprocessId
	 * 				定人定区id
	 * 			vehicleNoSuggested
	 * 				预处理建议车辆
	 * 			driverNameSuggested
	 * 				预处理建议司机姓名
	 * 			driverCodeSuggested
	 * 				预处理建议司机code
	 * 			productCode
	 * 				运输性质
	 * 			isPda
	 * 				是否PDA发送
	 * 			isSms
	 * 				是否短信发送
	 * 			isCustomer
	 * 				是否发送给客户
	 * 			orderTime
	 * 				下单时间
	 * 			createUserCode
	 * 				创建人名称
	 * 			createUserName
	 * 				创建人名称
	 * 			fleetCode
	 * 				派车车队编码
	 * 			customerPickupOrgCode
	 * 				提货网点
	 * 			receiveMethod
	 * 				提货方式
	 * 			orderSource
	 * 				订单来源
	 * 			pickupProvinceCode
	 * 				接货省code
	 * 			pickupCityCode
	 * 				接货城市code
	 * 			pickupCountyCode
	 * 				接货区code
	 * @author 038590-foss-wanghui
	 * @date 2012-12-18 下午8:28:03
	 */
    int insertSelective(DispatchOrderEntity record);
	
    /**
	 * 新增RPS订单job信息 
	 */
    int addRpsOrderJob(RPSOrderJobEntity rpsOrderJobEntity);
	
}