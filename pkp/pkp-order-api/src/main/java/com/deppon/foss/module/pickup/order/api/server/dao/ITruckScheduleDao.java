/**
 *  initial comments.
 */
/*
 * PROJECT NAME: tfr-scheduling-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.api.server.dao
 * FILE    NAME: ITruckSchedulingDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.pickup.order.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckConditionDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckDto;

/**
 * 排班表DAO接口
 * 
 * @author 038590-foss-wanghui
 * @date 2012-10-26 上午9:57:08
 */
public interface ITruckScheduleDao {

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
	List<OwnTruckDto> queryTruckScheduling(
			OwnTruckConditionDto ownTruckConditionDto);

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
	OwnTruckDto queryTruckSchedulingByDriverCode(
			OwnTruckConditionDto ownTruckConditionDto);

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
	List<OwnTruckDto> queryTruckSchedulingByVehicleNo(
			OwnTruckConditionDto ownTruckConditionDto);
}