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

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.DispatchOrderConditionDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.DispatchOrderVehicleDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.DriverQueryDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OrderHandleDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OrderVehViewSignDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ClientEWaybillConditionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EWaybillSalesDepartDto;

/**
 * 调度订单实体DAO
 * @author 038590-foss-wanghui
 * @date 2012-10-26 上午9:28:19
 */
public interface IDispatchOrderEntityDao {
	

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
	 * 
	 * 根据主键更新调度订单
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
    int updateByPrimaryKeySelective(DispatchOrderEntity record);
    
    /**
	 * 根据调度订单查询条件查询订单（车队）
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
	 * @author 038590-foss-wanghui
	 * @date 2012-12-18 下午8:28:03
	 */
    List<DispatchOrderEntity> queryOrderByDefault(DispatchOrderConditionDto conditionDto, int start, int limit);
    
    /**
	 * 根据调度订单查询条件查询订单总数（车队）
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
	 * @author 038590-foss-wanghui
	 * @date 2012-12-18 下午8:28:03
	 */
    Long queryOrderCountByDefault(DispatchOrderConditionDto conditionDto);
    
    /**
	 * 根据调度订单查询条件查询订单
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
	 * @author 038590-foss-wanghui
	 * @date 2012-12-18 下午8:28:03
	 */
    List<DispatchOrderEntity> queryOrderByCommon(DispatchOrderConditionDto conditionDto, int start, int limit);
    
    /**
	 * 根据调度订单查询条件查询订单总数
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
	 * @author 038590-foss-wanghui
	 * @date 2012-12-18 下午8:28:03
	 */
    Long queryOrderCountByCommon(DispatchOrderConditionDto conditionDto);
    
    /**
	 * 根据订单处理结果更新调度订单
	 * @param record
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
	 * @date 2012-10-30 上午8:59:10
	 */
    int updateByOrderHandle(OrderHandleDto record);
    
    /**
     * 
     * 批量处理订单
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
     * @date 2012-11-8 上午8:58:52
     */
    int batchUpdateByOrderHandle(List<OrderHandleDto> orderHandleDtos);
    
    /**
     * 
     * 根据预处理建议更新订单
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
     * @date 2012-11-7 下午4:57:33
     */
    int updateByPreprocess(DispatchOrderEntity dispatchOrderEntity);
    
    
    
    /**
	 * 根据订单ID更改调度订单完成状态
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
	 * @date 2012-11-8 上午10:47:25
	 */
	Integer updateDispatchOrderStatusById(DispatchOrderVehicleDto dto);
	
	/**
	 * 根据设备号、司机工号、车牌号、订单状态查询订单任务
	 * @param dto
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
	 * @author 097972-foss-dengtingting
	 * @date 2012-11-27 下午3:17:08
	 */
	Integer queryOrdersBy(DispatchOrderConditionDto dto);

	/**
	 * 根据司机工号、车牌号、订单状态查询订单任务
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
	 * @date Dec 6, 2012 9:22:31 AM
	 */
	List<DispatchOrderEntity> queryOrdersByPda(DispatchOrderConditionDto dto);

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
	 * 
	 * 根据订单号查询订单
	 * @param orderNo
	 * 			订单号
	 * @author 038590-foss-wanghui
	 * @date 2013-2-2 下午2:48:49
	 */
	DispatchOrderEntity queryOrderByOrderNo(String orderNo);
	
	/**
	 * 
	 * 根据订单号查询订单所有信息
	 * @param orderNo
	 * 			订单号
	 * @author 038590-foss-lianghaisheng
	 * @date 2013-2-2 下午2:48:49
	 */
	DispatchOrderEntity queryAllInfoByOrderNo(String orderNo);

	/**
	 * 更新小件订单
	 * @param record
	 * @return
	 */
	int updateByExpressOrderHandle(OrderHandleDto record);

	/**
	 * 根据调度订单查询条件查询订单总数
	 * @param dto
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
	 * @author 038590-foss-wanghui
	 * @date 2012-12-18 下午8:28:03
	 */
	Long queryExpressOrderCountByCommon(DispatchOrderConditionDto conditionDto);
	
	/**
	 * 根据调度订单查询条件查询订单
	 * @param dto
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
	 * @author 038590-foss-wanghui
	 * @date 2012-12-18 下午8:28:03
	 */
	List<DispatchOrderEntity> queryExpressOrderByCommon(DispatchOrderConditionDto conditionDto, int start, int limit);

	/**
	 * 按照订单号删除订单
	 * @param record
	 * @return
	 */
	int deleteDispatchOrderForNo(DispatchOrderConditionDto dto);

	/**
	 * 按照订单id
	 * @param record
	 * @return
	 */
	DispatchOrderEntity queryOrderById(String id);
	
	/**
	 * 订单可视化-按照查询条件查询出对应订单数据
	 * @param driverQueryDto
	 * @return List<OrderVehViewSignDto>
	 */
	List<OrderVehViewSignDto> queryOrderVehViewByDay(DriverQueryDto driverQueryDto);

	/**
	 * 更新订单状态
	 * @param orderNo
	 * @param status
	 */
	int updateSatusByOrderNo(String orderNo,String status);
	
	/**
	 * 根据订单号集合，批量更新订单状态
	 * @param orderNoList
	 * @param status
	 * @return
	 */
	int updateStatusByOrderNoList(List<String> orderNoList, String status);
	
	/**
	 * 更新PDA下拉数据对应的发送状态
	 * @param orderDtoListTemp
	 * @param status
	 * @return
	 */
	int updateSendStatus(List<String> orderNoList,String status);
	
	/**
	 * 查询订单详细数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-12-8 09:14:42
	 */
	DispatchOrderEntity queryBasicDispachOrderEntity(String orderNo);

	/**
	 * 查询DOP过来的数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-1-24 14:27:19
	 * @param ewaybillConditionDto
	 */
	List<EWaybillSalesDepartDto> queryEWaybillSalesDepart(ClientEWaybillConditionDto ewaybillConditionDto);

	/**
	 * 查询已经被退回的数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-1-24 14:27:19
	 * @param ewaybillConditionDto
	 */
	List<EWaybillSalesDepartDto> queryEWaybillSalesDepartForReturn(ClientEWaybillConditionDto ewaybillConditionDto);

	int updateDriverInfo(DispatchOrderEntity dispatchOrderEntity);	
	
	int updateSpecialAddressOrder(DispatchOrderEntity dispatchOrderEntity);

	int updateEwaybillOrderInfo(DispatchOrderEntity record);

	/**
	 * 根据原始订单号查询订单信息
	 * @param originalNumber
	 * @author foss-206860
	 * */
	List<DispatchOrderEntity> queryAllInfoByOriginalNumber(Map<String, Object> map);

	String queryISPICPACKAGEByWaybillNo(String waybillno);
}