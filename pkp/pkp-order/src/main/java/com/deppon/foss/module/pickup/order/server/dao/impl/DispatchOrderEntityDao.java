/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-order
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/server/dao/impl/DispatchOrderEntityDao.java
 * 
 * FILE NAME        	: DispatchOrderEntityDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchOrderEntityDao;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.DispatchOrderConditionDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.DispatchOrderVehicleDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.DriverQueryDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OrderHandleDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OrderVehViewSignDto;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.waybill.shared.dto.ClientEWaybillConditionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EWaybillSalesDepartDto;

/**
 * 调度订单实体DAO
 * @author 038590-foss-wanghui
 * @date 2012-10-26 上午9:28:19
 */
@SuppressWarnings("unchecked")
public class DispatchOrderEntityDao extends iBatis3DaoImpl implements IDispatchOrderEntityDao {

	public static final String NAMESPACE = "com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderEntity.";

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
	@Override
	public int insertSelective(DispatchOrderEntity record) {
		return getSqlSession().insert(NAMESPACE + "insertSelective", record);
	}

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
	@Override
	public int updateByPrimaryKeySelective(DispatchOrderEntity record) {
		return getSqlSession().update(NAMESPACE + "updateByPrimaryKeySelective", record);
	}

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
	@Override
	public int updateByOrderHandle(OrderHandleDto record) {
		return getSqlSession().update(NAMESPACE + "updateByOrderHandle", record);
	}

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
	@Override
	public List<DispatchOrderEntity> queryOrderByCommon(DispatchOrderConditionDto conditionDto, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return getSqlSession().selectList(NAMESPACE + "queryOrderByCommon", conditionDto, rowBounds);
	}
	
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
	@Override
	public Long queryOrderCountByCommon(DispatchOrderConditionDto conditionDto) {
		return (Long) getSqlSession().selectOne(NAMESPACE + "queryOrderCountByCommon", conditionDto);
	}

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
	@Override
	public int updateByPreprocess(DispatchOrderEntity dispatchOrderEntity) {
		return getSqlSession().update(NAMESPACE + "updateByPreprocess", dispatchOrderEntity);
	}
	
	@Override
	public int updateDriverInfo(DispatchOrderEntity dispatchOrderEntity) {
		return getSqlSession().update(NAMESPACE + "updateDriverInfo", dispatchOrderEntity);
	}

	/**
     * 
     * 批量处理订单
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
     * @date 2012-11-8 上午8:58:52
     */
	@Override
	public int batchUpdateByOrderHandle(List<OrderHandleDto> orderHandleDtos) {
		return getSqlSession().update(NAMESPACE + "batchUpdateByOrderHandle", orderHandleDtos);
	}

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
	@Override
	public Integer updateDispatchOrderStatusById(DispatchOrderVehicleDto dto) {
		return getSqlSession().update(NAMESPACE + "updateDispatchOrderStatusById", dto);
	}

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
	@Override
	public Integer queryOrdersBy(DispatchOrderConditionDto dto) {
		return (Integer) this.getSqlSession().selectOne(NAMESPACE + "queryOrderBy", dto);
	}

	/**
	 * 根据司机工号、车牌号、订单状态查询订单任务
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
	 * @author ibm-wangfei
	 * @date Dec 6, 2012 9:22:31 AM
	 */
	@Override
	public List<DispatchOrderEntity> queryOrdersByPda(DispatchOrderConditionDto dto) {
		return this.getSqlSession().selectList(NAMESPACE + "queryOrdersByPda", dto);
	}

	/**
	 * 
	 * 根据订单号，查询订单
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
	 * @author ibm-wangfei
	 * @date Dec 11, 2012 3:45:13 PM
	 */
	@Override
	public DispatchOrderEntity queryOrdersByOrderNo(DispatchOrderConditionDto dto) {
		List<DispatchOrderEntity> dispatchOrderEntityList = this.getSqlSession().selectList(NAMESPACE + "queryOrdersByOrderNo", dto);
		return CollectionUtils.isEmpty(dispatchOrderEntityList) ? null : dispatchOrderEntityList.get(0);
	}

	/**
	 * 根据调度订单查询条件查询订单（车队）
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
	@Override
	public List<DispatchOrderEntity> queryOrderByDefault(DispatchOrderConditionDto conditionDto, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + "queryOrderByDefault", conditionDto, rowBounds);
	}

	/**
	 * 根据调度订单查询条件查询订单总数（车队）
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
	@Override
	public Long queryOrderCountByDefault(DispatchOrderConditionDto conditionDto) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryOrderCountByDefault", conditionDto);
	}

	/**
	 * 
	 * 根据订单号查询订单
	 * @param orderNo
	 * 			订单号
	 * @author 038590-foss-wanghui
	 * @date 2013-2-2 下午2:48:49
	 */
	@Override	
	public DispatchOrderEntity queryOrderByOrderNo(String orderNo) {
		return (DispatchOrderEntity) this.getSqlSession().selectOne(NAMESPACE + "queryOrderByOrderNo", orderNo);
	}
	
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
	@Override
	public List<DispatchOrderEntity> queryExpressOrderByCommon(DispatchOrderConditionDto conditionDto, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return getSqlSession().selectList(NAMESPACE + "queryExpressOrderByCommon", conditionDto, rowBounds);
	}
	
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
	@Override
	public Long queryExpressOrderCountByCommon(DispatchOrderConditionDto conditionDto) {
		System.out.println(conditionDto.getExpressOrderCityCodes().size());
		return (Long) getSqlSession().selectOne(NAMESPACE + "queryExpressOrderCountByCommon", conditionDto);
	}


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
	@Override
	public int updateByExpressOrderHandle(OrderHandleDto record) {
		return getSqlSession().update(NAMESPACE + "updateByExpressOrderHandle", record);
	}
	
	/**
	 * 按照订单号删除订单
	 * @param record
	 * @return
	 */
	@Override
	public int deleteDispatchOrderForNo(DispatchOrderConditionDto dto) {
		return getSqlSession().update(NAMESPACE + "deleteDispatchOrderForNo", dto);
	}

	/**
	 * author lianghaisheng
	 * 功能:根据订单号查询订单所有信息
	 * */
	@Override
	public DispatchOrderEntity queryAllInfoByOrderNo(String orderNo) {
		return (DispatchOrderEntity) getSqlSession().selectOne(NAMESPACE + "queryAllInfoByOrderNo", orderNo);
	}

	@Override
	public DispatchOrderEntity queryOrderById(String id) {
		return (DispatchOrderEntity) getSqlSession().selectOne(NAMESPACE + "queryOrderById", id);
	}

	@Override
	public List<OrderVehViewSignDto> queryOrderVehViewByDay(
			DriverQueryDto driverQueryDto) {
		driverQueryDto.setProductCodes(ProductEntityConstants.EXPRESS_PRODUCT_CODE_LIST);
		return getSqlSession().selectList(NAMESPACE + "queryOrderVehViewByDay", driverQueryDto);
	}

	@Override
	public int updateSatusByOrderNo(String orderNo, String status) {
		Map<String,String> condition = new HashMap<String, String>();
		condition.put("orderNo", orderNo);
		condition.put("status", status);
		return getSqlSession().update(NAMESPACE +"updateSatusByOrderNo", condition);
		
	}

	@Override
	public int updateStatusByOrderNoList(List<String> orderNoList, String status) {
		Map<String,Object> condition = new HashMap<String, Object>();
		condition.put("orderNoList", orderNoList);
		condition.put("status", status);
		return getSqlSession().update(NAMESPACE +"updateSatusByOrderNoList", condition);
	}

	@Override
	public int updateSendStatus(List<String> orderNoList, String status) {
		Map<String,Object> condition = new HashMap<String, Object>();
		condition.put("orderNoList", orderNoList);
		condition.put("status", status);
		return getSqlSession().update(NAMESPACE +"updateSendSatusByOrderNoList", condition);
	}
	
	/**
	 * 查询订单详细数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-12-8 09:14:42
	 */
	@Override
	public DispatchOrderEntity queryBasicDispachOrderEntity(String orderNo){
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("orderNo", orderNo);
		List<DispatchOrderEntity> list = this.getSqlSession().selectList(NAMESPACE+"queryBasicDispachOrderEntity", parameter);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}
	
	/**
	 * 更新特殊地址订单
	 */
	@Override
	public int updateSpecialAddressOrder(DispatchOrderEntity dispatchOrderEntity) {
		return getSqlSession().update(NAMESPACE +"updateSpecialAddressOrder", dispatchOrderEntity);
	}

		/**
	 * 查询DOP过来的数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-1-24 14:27:19
	 * @param ewaybillConditionDto
	 */
	@Override
	public List<EWaybillSalesDepartDto> queryEWaybillSalesDepart(ClientEWaybillConditionDto ewaybillConditionDto) {
		return this.getSqlSession().selectList(NAMESPACE+"queryEWaybillSalesDepart", ewaybillConditionDto);
	}
	
	/**
	 * 查询已经被退回的数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-1-24 14:27:19
	 * @param ewaybillConditionDto
	 */
	@Override
	public List<EWaybillSalesDepartDto> queryEWaybillSalesDepartForReturn(ClientEWaybillConditionDto ewaybillConditionDto) {
		return this.getSqlSession().selectList(NAMESPACE+"queryEWaybillSalesDepartForReturn", ewaybillConditionDto);
	}
	
	/**
	 * 更新电子运单订单信息
	 * @author FOSS-219396-chengdaolin
	 * @date 2015-06-24 11:27:19
	 * @param DispatchOrderEntity
	 */
	@Override
	public int updateEwaybillOrderInfo(DispatchOrderEntity record) {
		return getSqlSession().update(NAMESPACE + "updateEwaybillOrderInfo", record);
	}
	
	/**
	 * TODO 根据原始订单号查询订单信息
	 * @param originalNumber
	 * @author foss-206860
	 * @date 2015-9-11 下午2:41:21
	 */
	@Override
	public List<DispatchOrderEntity> queryAllInfoByOriginalNumber(Map<String, Object> map) {
		return getSqlSession().selectList(NAMESPACE + "queryAllInfoByOriginalNumber", map);
	}

	@Override
	public String queryISPICPACKAGEByWaybillNo(String waybillno) {
		// 根据运单号查询子母件关系表，判断是否子母件
		return (String) getSqlSession().selectOne(NAMESPACE + "queryISPICPACKAGEByWaybillNo", waybillno);
	}
	
}