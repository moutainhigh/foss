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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/api/server/service/IVehicleManageService.java
 * 
 * FILE NAME        	: IVehicleManageService.java
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
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.BindingLeasedTruckDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.LeasedTruckConditionDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.LeasedTruckDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckConditionDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckDtoWithCount;
import com.deppon.foss.module.pickup.order.api.shared.dto.VehicleActualSituationDto;

/**
 * 调度订单车辆相关的管理Service
 * @author 038590-foss-wanghui
 * @date 2012-10-29 下午5:31:27
 */
public interface IVehicleManageService extends IService {

	/**
	 * 
	 * 查询已用车辆
	 * @param ownTruckConditionDto
     * 			vehicleNo
     * 				车牌号
     * 			vehicleType
     * 				车型
     * 			serviceFleetCode
     * 				接送货车队
     * 			driverCode
     * 				司机编码
     * 			regionType
     * 				区域类型（接货or送货）
     * 			regionNature
     * 				区域大小
     * 			active
     * 				是否激活
     * 			bundleStatus
     * 				绑定状态
     * 			schedulingType
     * 				排班类型
     * 			schedulingStatus
     * 				排班状态
     * 			schedulingPlanType
     * 				计划状态
     * 			departPlanType
     * 				物流班车类型
     * 			orgCode
     * 				组织code（营业部或车队）
     * 			departPlanStatus
     * 				发车计划状态
	 * @author 038590-foss-wanghui
	 * @date 2012-10-30 下午7:41:33
	 */
	List<OwnTruckDto> queryUsedVehicle(OwnTruckConditionDto ownTruckConditionDto);
	
	/**
	 * 
	 * 查询自有车
	 * @param ownTruckConditionDto
     * 			vehicleNo
     * 				车牌号
     * 			vehicleType
     * 				车型
     * 			serviceFleetCode
     * 				接送货车队
     * 			driverCode
     * 				司机编码
     * 			regionType
     * 				区域类型（接货or送货）
     * 			regionNature
     * 				区域大小
     * 			active
     * 				是否激活
     * 			bundleStatus
     * 				绑定状态
     * 			schedulingType
     * 				排班类型
     * 			schedulingStatus
     * 				排班状态
     * 			schedulingPlanType
     * 				计划状态
     * 			departPlanType
     * 				物流班车类型
     * 			orgCode
     * 				组织code（营业部或车队）
     * 			departPlanStatus
     * 				发车计划状态
	 * @author 038590-foss-wanghui
	 * @date 2012-10-30 下午7:47:13
	 */
	OwnTruckDtoWithCount queryOwnTruck(OwnTruckConditionDto ownTruckConditionDto, int start, int limit);
	
	/**
	 * 
	 * 查询外请车
	 * @param leasedTruckConditionDto 外请车查询条件Dto
	 * 			vehicleNo
	 * 				车牌号
	 * 			vehicleType
	 * 				车型
	 * 			driverName
	 * 				司机姓名
	 * 			driverMobile
	 * 				司机手机
	 * 			openVehicle
	 * 				是否敞篷
	 * @author 038590-foss-wanghui
	 * @date 2012-10-31 上午8:23:19
	 */
	List<LeasedTruckDto> queryLeasedTruck(LeasedTruckConditionDto leasedTruckConditionDto, int start, int limit);
	
	/**
	 * 
	 * 查询外请车的数量
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-4-13 上午8:48:01
	 */
	Long queryLeasedTruckCount(LeasedTruckConditionDto leasedTruckConditionDto);
	/**
	 * 
	 * 根据车牌号修改车辆实际情况
	 * @param vehicleActualSituationDto
	 * 			id
	 * 				id
	 * 			vehicleNo
	 * 				车牌号
	 * 			remainingWeight
	 * 				剩余重量
	 * 			remainingVolume
	 * 				剩余体积
	 * 			alreadyPickupGoodsQty
	 * 				已接票数
	 * 			nonePickupGoodsQty
	 * 				未接票数
	 * 			alreadyDeliverGoodsQty
	 * 				已送票数
	 * 			noneDeliverGoodsQty
	 * 				未送票数
	 * @author 038590-foss-wanghui
	 * @date 2012-11-3 下午2:25:27
	 */
	boolean modifyVehicle(VehicleActualSituationDto vehicleActualSituationDto);
	
	/**
	 * 
	 * 清空车辆的载重载空
	 * @param 
	 * @author 038590-foss-wanghui
	 * @date 2012-11-12 下午2:46:44
	 */
	void emptyVehicle();
	
	/**
	 * 
	 * 根据vehicleActualSituationEntity增加相应的车载信息
	 * @param vehicleActualSituationDto
	 * 			id
	 * 				id
	 * 			vehicleNo
	 * 				车牌号
	 * 			remainingWeight
	 * 				剩余重量
	 * 			remainingVolume
	 * 				剩余体积
	 * 			alreadyPickupGoodsQty
	 * 				已接票数
	 * 			nonePickupGoodsQty
	 * 				未接票数
	 * 			alreadyDeliverGoodsQty
	 * 				已送票数
	 * 			noneDeliverGoodsQty
	 * 				未送票数
	 * @author 038590-foss-wanghui
	 * @date 2012-11-19 下午5:16:45
	 */
	boolean addVehicleWVByVehicleNo(VehicleActualSituationDto vehicleActualSituationDto);
	
	/**
	 * 
	 * 根据车牌号查询对应的净空和剩余体积
	 * @param ownTruckConditionDto
     * 			vehicleNo
     * 				车牌号
     * 			vehicleType
     * 				车型
     * 			serviceFleetCode
     * 				接送货车队
     * 			driverCode
     * 				司机编码
     * 			regionType
     * 				区域类型（接货or送货）
     * 			regionNature
     * 				区域大小
     * 			active
     * 				是否激活
     * 			bundleStatus
     * 				绑定状态
     * 			schedulingType
     * 				排班类型
     * 			schedulingStatus
     * 				排班状态
     * 			schedulingPlanType
     * 				计划状态
     * 			departPlanType
     * 				物流班车类型
     * 			orgCode
     * 				组织code（营业部或车队）
     * 			departPlanStatus
     * 				发车计划状态
	 * @author 038590-foss-wanghui
	 * @date 2012-12-7 上午11:51:08
	 */
	OwnTruckDto queryVolumeByVehicleNo(OwnTruckConditionDto ownTruckConditionDto);
	
	/**
	 * 
	 * 根据车牌号查询该车辆的已接、未接、已送、未送票数
	 * @param vehicleNo
	 * 			车牌号
	 * @author 038590-foss-wanghui
	 * @date 2012-12-26 下午2:33:45
	 */
	VehicleActualSituationDto queryTaskByVehicleNo(String vehicleNo);
	
	OwnTruckDto queryTruckSchedulingByVehicleNo(OwnTruckConditionDto ownTruckConditionDto);
	
	OwnTruckEntity queryOwnVehicleBySelective(OwnTruckEntity entity,
			Object object);
	
	/**
	 * 手动查询已绑定外请车数量
	* @author 329757-foss-liuxiangcheng 
	* @date 2016-6-30 上午9:29:25
	* @throws
	 */
	long queryBundLeasedTruckCount(BindingLeasedTruckDto bindingLeasedTruckDto);

	/**
	 * 手动查询已绑定外请车列表
	* @author 329757-foss-liuxiangcheng 
	* @date 2016-6-30 上午9:29:48
	* @param @param bindingLeasedTruckDto
	* @param @param start
	* @param @param limit
	 */
	List<BindingLeasedTruckDto> queryBundLeasedTruck(
			BindingLeasedTruckDto bindingLeasedTruckDto, int start, int limit);
}