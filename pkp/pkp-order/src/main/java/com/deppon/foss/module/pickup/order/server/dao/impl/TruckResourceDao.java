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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/server/dao/impl/TruckResourceDao.java
 * 
 * FILE NAME        	: TruckResourceDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.server.dao.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.order.api.server.dao.ITruckResourceDao;
import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckConditionDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.VehicleInfoDto;

/**
 * 获取车辆资源，主要包括借车、约车和物流班车
 * @author 038590-foss-wanghui
 * @date 2012-11-30 下午3:10:24
 */
@SuppressWarnings("unchecked")
public class TruckResourceDao extends iBatis3DaoImpl implements ITruckResourceDao {

	private static final String NAMESPACE = "foss.pickup.order.TruckResource.";
	
	/**
	 * 
	 * 查询物流班车
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
	 * @date 2012-11-30 下午3:28:41
	 */
	@Override
	public List<OwnTruckDto> queryTruckDepartPlan(OwnTruckConditionDto ownTruckConditionDto) {
		return getSqlSession().selectList(NAMESPACE + "queryTruckDepartPlan", ownTruckConditionDto);
	}

	/**
	 * 
	 * 查询约车
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
	 * @date 2012-11-30 下午3:30:00
	 */
	@Override
	public List<OwnTruckDto> queryOrderVehicle(OwnTruckConditionDto ownTruckConditionDto) {
		return getSqlSession().selectList(NAMESPACE + "queryOrderVehicle", ownTruckConditionDto);
	}

	/**
	 * 
	 * 查询借车
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
	 * @date 2012-11-30 下午3:30:55
	 */
	@Override
	public List<OwnTruckDto> queryBorrowVehicle(OwnTruckConditionDto ownTruckConditionDto) {
		return getSqlSession().selectList(NAMESPACE + "queryBorrowVehicle", ownTruckConditionDto);
	}

	/**
	 * 
	 * 根据车牌号查询车辆的净空和剩余体积
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
	 * @date 2012-12-7 上午10:50:22
	 */
	@Override
	public OwnTruckDto queryVolumeByVehicleNo(OwnTruckConditionDto ownTruckConditionDto) {
		List<OwnTruckDto> list = getSqlSession().selectList(NAMESPACE + "queryVolumeByVehicleNo", ownTruckConditionDto);
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		return list.get(0);
	}

	@Override
	public VehicleInfoDto queryVehicleTypeByVehicleNo(OwnTruckConditionDto ownTruckConditionDto) {
		VehicleInfoDto vehicleInfoDto = (VehicleInfoDto) getSqlSession().selectOne(NAMESPACE + "queryVehicleTypeByVehicleNo", ownTruckConditionDto);
		return vehicleInfoDto == null ? new VehicleInfoDto() : vehicleInfoDto;
	}

}