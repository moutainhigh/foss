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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/server/dao/impl/TruckScheduleDao.java
 * 
 * FILE NAME        	: TruckScheduleDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.order.api.server.dao.ITruckScheduleDao;
import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckConditionDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckDto;

/**
 * 排班表DAO接口实现
 * 
 * @author 038590-foss-wanghui
 * @date 2012-10-26 上午10:02:04
 */
@SuppressWarnings("unchecked")
public class TruckScheduleDao extends iBatis3DaoImpl implements ITruckScheduleDao {

	private static final String NAMESPACE = "com.deppon.foss.module.pickup.order.api.shared.domain.TruckSchedulingEntity.";
	
	/**
	 * 查询排班信息列表
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
	 * @date 2012-10-26 上午10:00:15
	 */
	@Override
	public List<OwnTruckDto> queryTruckScheduling(
			OwnTruckConditionDto ownTruckConditionDto) {
		return getSqlSession().selectList(NAMESPACE + "queryTruckScheduling", ownTruckConditionDto);
	}

	/**
	 * 
	 * 根据司机编码查询排班表
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
	 * @date 2012-11-16 下午6:31:22
	 */
	@Override
	public OwnTruckDto queryTruckSchedulingByDriverCode(
			OwnTruckConditionDto ownTruckConditionDto) {
		return (OwnTruckDto) getSqlSession().selectOne(NAMESPACE + "queryTruckSchedulingByDriverCode", ownTruckConditionDto);
	}

	/**
	 * 
	 * 根据车牌号查询排班表
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
	 *            查询条件。包括车牌号，排班类型，派单状态，计划状态等。
	 * @return 排班记录
	 * @author ibm-wangxiexu
	 * @date 2013-1-12 下午8:50:02
	 */
	@Override
	public List<OwnTruckDto> queryTruckSchedulingByVehicleNo(
			OwnTruckConditionDto ownTruckConditionDto) {
		return getSqlSession().selectList(NAMESPACE + "queryTruckSchedulingByVehicleNo", ownTruckConditionDto);
	}}