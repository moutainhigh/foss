/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-departure
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/server/service/impl/QueryDriverByVehicleNoService.java
 *  
 *  FILE NAME          :QueryDriverByVehicleNoService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.departure.server.service.impl;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedDriverService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOwnDriverService;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DriverAssociationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LeasedDriverException;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.departure.api.server.dao.IArtificialDepartDao;
import com.deppon.foss.module.transfer.departure.api.server.service.IQueryDriverByVehicleNoService;
import com.deppon.foss.module.transfer.departure.api.shared.define.DepartureConstant;
import com.deppon.foss.module.transfer.departure.api.shared.define.ErrorConstant;
import com.deppon.foss.module.transfer.departure.api.shared.domain.LmsTruckDepartPlanEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.QueryDepartEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.RelationInfoEntity;
import com.deppon.foss.module.transfer.departure.api.shared.dto.VehicleNoInfoDTO;
/**
 * 车牌号查询司机信息接口实现.
 * 
 * @author foss-liubinbin(for IBM)
 * @date 2012-12-24 下午6:57:15
 */
public class QueryDriverByVehicleNoService implements
		IQueryDriverByVehicleNoService {
	/** 日志 */
	private static final Logger LOGGER = LogManager
			.getLogger(QueryDriverByVehicleNoService.class);
	/**
	 * 通过车牌号查找司机信息. 一、外请车（短途、长途）： 1.
	 * 长途外请车： 业务： a) 只要是长途外请车的，
	 * 都需要打印运输合同，所以在配载单生成界面，主驾驶员是必填项，
	 * 此时车牌号与主驾驶司机关联， 信息来源外请车辆基础资料。 b)
	 * 假如外请司机与车牌号有更改
	 * ，定会在外请车报道环节，将正确的司机、车辆信息登录车辆基础资料。
	 * 系统： 1.1 当车牌号的归属类型为外请车，车辆业务类型为长途时，
	 * 对应主驾驶司机的信息来源外请车基础资料； 1.2
	 * 当外请车辆临时出发放行或新增配载单时的任务放行
	 * ，都通过车牌号关联车辆基础资料中的司机的信息。 2. 短途外请车：
	 * 业务： a)
	 * 短途车辆的司机与车牌号是在车辆报道时，登记在外请车辆基础资料中
	 * 。车队调度约车系统派车时 ，来驱动短途外请车应用。 b)
	 * 受理约车时：通过车牌号关联车辆基础资料中外请车司机信息。 系统
	 * 2.1
	 * 当车牌号的归属类型为外请车，车辆业务类型为短途时，对应车辆、
	 * 司机信息来源外请车基础资料； 2.2 对应约车系统-调度派车时，
	 * 通过车牌号关联外请车基础资料中的司机的信息。 2.3
	 * 对应外场配载组人员
	 * “新增交接单”时，通过车牌号关联外请车基础资料中的司机的信息。
	 * 2.4 对应外请车辆临时出发放行或新增配载单时的任务车辆放行，
	 * 通过车牌号关联外请车基础资料中的司机的信息。
	 * 二、公司车（短途班车、长途）： 1. 长途公司车： 业务： a)
	 * 公司长途车辆的司机信息在长途发车计划界面中
	 * ，车队调度手动录入，属于必填项
	 * 。公司长途车辆初始时：一个车牌号后面挂靠默认3个司机
	 * ；三个挂靠司机是可以增、删
	 * 、改的；当发车计划生成后，一个车牌号只会对应2个司机
	 * （调度每次制定长途发车计划时，都会手动修改）。 系统： 1.1
	 * 任务车辆的出发放行
	 * ，新增配载单时，通过车牌号自动关联公司长途发车计划中的司机信息。
	 * 1.2 信息来源为“长途发车计划”。 1.3
	 * 司机信息界面显示规则：
	 * 公司长途车辆的司机的信息（姓名、电话）只能车队调度看到
	 * ；其他部门不能看到司机的信息
	 * 。（部门属性、车辆归属类型、车辆业务类型 2. 短途班车公司车：
	 * 业务： a) 短途班车发车计划中的司机信息来源于排班表
	 * ，短途班车发车计划中的司机信息
	 * ，车队调度可以更改(车队调度会与车队经理线下-电话沟通)。 b)
	 * 约车系统：可用车辆的司机信息来源排班表
	 * ，可用车辆的司机信息可以更改
	 * （车队调度会与车队经理线下-沟通确认），
	 * 车队调度修改司机后，执行调度派车任务。 系统： 2.1
	 * 短途班车发车计划，通过车牌号关联排班表中的司机信息； 2.2
	 * 短途班车的新增交接单的任务车辆，通过车牌号关联短途班车发车计划表。
	 * 2.3 系统约车，通过车牌号关联排班表中的司机信息。 2.4
	 * 通过系统约车-新增交接单的车辆，
	 * 通过车牌号关联显示系统约车中的调度派车任务列表中的司机信息。
	 * 三、派送车辆： 系统： 1.
	 * 接送货司机登录PDA时，动态绑定车牌号与司机信息。 1.1
	 * 车牌号的车辆业务类型类接送、送货时，来源PDA中的司机与车号绑定。
	 * 1.2
	 * 司机登录PDA，下拉任务时，查询车辆出发放行界面，加载接、送
	 * 、转货的司机信息。 四、公司车的临时车辆的外出 业务： a)
	 * 年审/季审、保养的车辆、司机来源LMS系统，
	 * LMS系统必须手动绑定车牌号与司机信息； b)
	 * 调度手动放行临时出发的车辆，司机信息手动录入，属于必填。 系统：
	 * 1.1 临时任务类型：加油、维修、
	 * 其他外出任务类型的车辆必须手动录入司机信息； 1.2
	 * 临时任务类型：保养、年审/季审的司机信息来源LMS系统。 1.3
	 * 临时任务类型：临时放空出发（约车），为约车系统-调度派车，
	 * 来源调度派车任务列表中的司机信息。
	 * 
	 * @param vehicleNo
	 *            the vehicle no
	 * @return the relation info entity
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-24 上午11:04:20
	 */
	@Override public RelationInfoEntity queryDriverInfoByVehicleNo(
			String vehicleNo) {
		if (StringUtils.isBlank(vehicleNo)) {
			// 车牌号不能为空
			throw new TfrBusinessException(ErrorConstant.VEHICLE_NO_IS_NOT_NULL);
		}
		VehicleNoInfoDTO vehicleNoInfoDTO = new VehicleNoInfoDTO();
		vehicleNoInfoDTO.setVehicleNo(vehicleNo);
		return queryDriverInfoByVehicleNo(vehicleNoInfoDTO);
	}
	
	@Override public RelationInfoEntity queryDriverInfoByVehicleNo(
			String vehicleNo, String id) {
		if (StringUtils.isBlank(vehicleNo)) {
			// 车牌号不能为空
			throw new TfrBusinessException(ErrorConstant.VEHICLE_NO_IS_NOT_NULL);
		}
		if (StringUtils.isBlank(id)) {
			// 数据错误
			throw new TfrBusinessException("数据错误");
		}
		VehicleNoInfoDTO vehicleNoInfoDTO = new VehicleNoInfoDTO();
		vehicleNoInfoDTO.setVehicleNo(vehicleNo);
		return queryDriverInfoByVehicleNo(vehicleNoInfoDTO, id);
	}
	
	/**
	 * 通过车牌号查找司机信息.
	 * 
	 * @param vehicleNoInfoDTO
	 *            the vehicle no info
	 *            dto
	 * @return the relation info entity
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-24 上午11:04:20
	 */
	@Override public RelationInfoEntity queryDriverInfoByVehicleNo(
			VehicleNoInfoDTO vehicleNoInfoDTO) {
		if (null == vehicleNoInfoDTO) {
			// 车牌号不能为空
			throw new TfrBusinessException(ErrorConstant.VEHICLE_NO_IS_NOT_NULL);
		}
		if (StringUtils.isBlank(vehicleNoInfoDTO.getVehicleNo())) {
			// 车牌号不能为空
			throw new TfrBusinessException(ErrorConstant.VEHICLE_NO_IS_NOT_NULL);
		}
		if (DepartureConstant.TRUCK_TYPE_LEASED.equals(vehicleNoInfoDTO
				.getTruckType())) {
			// 外请车
			return queryDriverInfoByVehicleNoFromBSE(vehicleNoInfoDTO
					.getVehicleNo());
		} else if (DepartureConstant.TRUCK_TYPE_OWN.equals(vehicleNoInfoDTO
				.getTruckType())) {
			// 公司车
			return queryDriverInfoByVehicleNoFromDepartPlan(vehicleNoInfoDTO
					.getVehicleNo());
		} else if (DepartureConstant.DEPART_ITEM_TYPE_PKP
				.equals(vehicleNoInfoDTO.getBusinessType())) {
			// 派送车|接送货
			return queryDriverInfoByVehicleNoFromSTL(vehicleNoInfoDTO
					.getVehicleNo());
		} else if (DepartureConstant.DEPART_TYPE_NOT_TASK_VEHICLE
				.equals(vehicleNoInfoDTO.getDepartType())) {
			// 公司的临时任务车辆
			return queryDriverInfoByVehicleNoFromLMS(vehicleNoInfoDTO
					.getVehicleNo());
		} else {
			// 如果只传入一个车牌号，则四种入口都需要进入，得到值就返回
			return getDriverInfoForAll(vehicleNoInfoDTO.getVehicleNo());
		}
	}
	
	/**
	 * 通过车牌号查找司机信息.
	 * 
	 * @param vehicleNoInfoDTO
	 *            the vehicle no info
	 *            dto
	 * @return the relation info entity
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-24 上午11:04:20
	 */
	public RelationInfoEntity queryDriverInfoByVehicleNo(
			VehicleNoInfoDTO vehicleNoInfoDTO, String id) {
		if (null == vehicleNoInfoDTO) {
			// 车牌号不能为空
			throw new TfrBusinessException(ErrorConstant.VEHICLE_NO_IS_NOT_NULL);
		}
		if (StringUtils.isBlank(vehicleNoInfoDTO.getVehicleNo())) {
			// 车牌号不能为空
			throw new TfrBusinessException(ErrorConstant.VEHICLE_NO_IS_NOT_NULL);
		}
		if (DepartureConstant.TRUCK_TYPE_LEASED.equals(vehicleNoInfoDTO
				.getTruckType())) {
			// 外请车
			return queryDriverInfoByVehicleNoFromBSE(vehicleNoInfoDTO
					.getVehicleNo());
		} else if (DepartureConstant.TRUCK_TYPE_OWN.equals(vehicleNoInfoDTO
				.getTruckType())) {
			// 公司车
			return queryDriverInfoByVehicleNoFromDepartPlan(vehicleNoInfoDTO
					.getVehicleNo());
		} else if (DepartureConstant.DEPART_ITEM_TYPE_PKP
				.equals(vehicleNoInfoDTO.getBusinessType())) {
			// 派送车|接送货
			return queryDriverInfoByVehicleNoFromSTL(vehicleNoInfoDTO
					.getVehicleNo());
		} else if (DepartureConstant.DEPART_TYPE_NOT_TASK_VEHICLE
				.equals(vehicleNoInfoDTO.getDepartType())) {
			// 公司的临时任务车辆
			return queryDriverInfoByVehicleNoFromLMS(vehicleNoInfoDTO
					.getVehicleNo());
		} else {
			// 如果只传入一个车牌号，则四种入口都需要进入，得到值就返回
			return getDriverInfoForAll(vehicleNoInfoDTO.getVehicleNo(), id);
		}
	}
	
	/**
	 * 只传入一个车牌号，则遍历四种业务，查到车牌号就返回.
	 * 
	 * @param vehicleNo
	 *            the vehicle no
	 * @return the driver info for all
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-6 下午2:41:21
	 */
	private RelationInfoEntity getDriverInfoForAll(String vehicleNo) {
		RelationInfoEntity relationInfo = queryDriverInfoByVehicleNoFromDepartPlan(vehicleNo);
		if (relationInfo != null) {
			// 通过车牌号调用进入发车计划查找公司车绑定司机的信息
			return relationInfo;
		}
		
		relationInfo = queryDriverInfoByVehicleNoFromLMS(vehicleNo);
		if (null != relationInfo) {
			// 通过车牌号找到对应的LMS放行车辆数据
			return relationInfo;
		}
		
		relationInfo = queryDriverInfoByVehicleNoFromSTL(vehicleNo);
		if (null != relationInfo) {
			// 通过车牌号调用接送或的接口查找车辆绑定司机的信息
			return relationInfo;
		}
		
		relationInfo = queryDriverInfoByVehicleNoFromBSE(vehicleNo);
		if (null != relationInfo) {
			// 通过车牌号调用综合管理的接口查找外请车司机的信息
			return relationInfo;
		}
		
		throw new TfrBusinessException(ErrorConstant.SEARCH_DRIVER_FAIL);
	}
	
	/**
	 * 只传入一个车牌号，则遍历四种业务，查到车牌号就返回.
	 * 
	 * @param vehicleNo
	 *            the vehicle no
	 * @return the driver info for all
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-6 下午2:41:21
	 */
	private RelationInfoEntity getDriverInfoForAll(String vehicleNo, String id) {
		RelationInfoEntity relationInfo = queryDriverInfoByVehicleNoFromDepartPlan(vehicleNo);
		if (relationInfo != null) {
			// 通过车牌号调用进入发车计划查找公司车绑定司机的信息
			return relationInfo;
		}
		
		relationInfo = queryDriverInfoByVehicleNoFromLMS(vehicleNo);
		if (null != relationInfo) {
			// 通过车牌号找到对应的LMS放行车辆数据
			return relationInfo;
		}
		
		relationInfo = queryDriverInfoByVehicleNoFromSTL(vehicleNo);
		if (null != relationInfo) {
			// 通过车牌号调用接送或的接口查找车辆绑定司机的信息
			return relationInfo;
		}
		
		relationInfo = queryDriverInfoByVehicleNoFromBSE(vehicleNo);
		if (null != relationInfo) {
			// 通过车牌号调用综合管理的接口查找外请车司机的信息
			return relationInfo;
		}
		
		relationInfo = queryDriverInfoByTruckTaskDetailId(vehicleNo, id);
		if (null != relationInfo) {
			// 根据车牌号从车辆任务中找司机信息
			return relationInfo;
		}
		
		throw new TfrBusinessException(ErrorConstant.SEARCH_DRIVER_FAIL);
	}
	
	/**
	 * 临时任务车辆，直接从LMS计划放行表查询数据.
	 * 
	 * @param vehicleNo
	 *            the vehicle no
	 * @return the relation info entity
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-6 下午1:41:54
	 */
	private RelationInfoEntity queryDriverInfoByVehicleNoFromLMS(
			String vehicleNo) {
		RelationInfoEntity relationInfoEntity = new RelationInfoEntity();
		QueryDepartEntity queryEntity = new QueryDepartEntity();
		queryEntity.setVehicleNo(vehicleNo);
		// 通过车牌号找到对应的LMS放行车辆数据
		List<LmsTruckDepartPlanEntity> list = artificialDepartDao
				.queryTemporaryAssignments(queryEntity);
		if (null != list && list.size() > 0) {
			if (list.get(0) != null) {
				// 设置司机姓名
				relationInfoEntity.setDriverName(list.get(0).getDriverName());
				// 设置司机电话
				relationInfoEntity.setDriverPhone(list.get(0).getDriverPhone());
				// 设置司机编码
				relationInfoEntity.setDriverCode(list.get(0).getDriverCode());
				return relationInfoEntity;
			}
		}
		return null;
	}
	/**
	 * 通过车牌号调用接送或的接口查找车辆绑定司机的信息.
	 * 
	 * @param vehicleNo
	 *            the vehicle no
	 * @return the relation info entity
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-6 下午1:41:54
	 */
	private RelationInfoEntity queryDriverInfoByVehicleNoFromSTL(
			String vehicleNo) {
		RelationInfoEntity relationInfoEntity = new RelationInfoEntity();
		QueryDepartEntity queryEntity = new QueryDepartEntity();
		queryEntity.setVehicleNo(vehicleNo);
		queryEntity.setStatus(DepartureConstant.PDA_BUND_YES);
		List<RelationInfoEntity> list = artificialDepartDao
				.queryDriverInfoByVehicleNoFormBSE(queryEntity);
		if (null != list && list.size() > 0) {
			if (list.get(0) != null) {
				// 设置司机姓名
				relationInfoEntity.setDriverName(list.get(0).getDriverName());
				// 设置司机电话
				relationInfoEntity.setDriverPhone(list.get(0).getDriverPhone());
				// 设置司机编码
				relationInfoEntity.setDriverCode(list.get(0).getDriverCode());
				// 设置司机经理电话
				relationInfoEntity.setFleetManagerPhone(list.get(0)
						.getFleetManagerPhone());
				return relationInfoEntity;
			}
		}
		return null;
	}
	/**
	 * 通过车牌号调用综合管理的接口查找外请车司机的信息.
	 * 
	 * @param vehicleNo
	 *            the vehicle no
	 * @return the relation info entity
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-6 下午1:41:54
	 */
	private RelationInfoEntity queryDriverInfoByVehicleNoFromBSE(
			String vehicleNo) {
		RelationInfoEntity relationInfoEntity = new RelationInfoEntity();
		QueryDepartEntity queryEntity = new QueryDepartEntity();
		queryEntity.setVehicleNo(vehicleNo);
		List<DriverAssociationDto> driverList;
		// 捕获异常
		try {
			// 调用外请车的接口通过车牌号查询车辆信息
			driverList = leasedDriverService
					.queryLesasedDriverAssociationDtoByVehicleNo(vehicleNo);
		} catch (LeasedDriverException e) {
			LOGGER.error(e);
			return null;
		}
		if (null != driverList && driverList.size() > 0) {
			// 设置司机姓名
			relationInfoEntity.setDriverName(driverList.get(0).getDriverName());
			// 设置司机电话
			relationInfoEntity.setDriverPhone(driverList.get(0)
					.getDriverPhone());
			// 设置司机编码
			relationInfoEntity.setDriverCode(driverList.get(0)
					.getDriverIdCard());
			return relationInfoEntity;
		}
		return null;
	}
	
	/**
	 * 根据车牌号从车辆任务中找司机信息
	 * 
	 * @param vehicleNo
	 *            the vehicle no
	 * @return the relation info entity
	 * @author foss-zhangyixin
	 * @date 2012-11-6 下午1:41:54
	 */
	private RelationInfoEntity queryDriverInfoByTruckTaskDetailId(
			String vehicleNo, String id) {
		RelationInfoEntity relationInfoEntity = new RelationInfoEntity();
		QueryDepartEntity queryEntity = new QueryDepartEntity();
		queryEntity.setVehicleNo(vehicleNo);
		queryEntity.setId(id);
		List<RelationInfoEntity> driverList;
		// 捕获异常
		try {
			// 调用外请车的接口通过车牌号查询车辆信息
			driverList = artificialDepartDao
					.queryDriverInfoByTruckTaskDetailId(queryEntity);
		} catch (LeasedDriverException e) {
			LOGGER.error(e);
			return null;
		}
		if (null != driverList && driverList.size() > 0) {
			// 设置司机姓名
			relationInfoEntity.setDriverName(driverList.get(0).getDriverName());
			// 设置司机电话
			relationInfoEntity.setDriverPhone(driverList.get(0)
					.getDriverPhone());
			// 设置司机编码
			relationInfoEntity.setDriverCode(driverList.get(0)
					.getDriverCode());
			return relationInfoEntity;
		}
		return null;
	}
	
	/**
	 * 通过车牌号调用进入发车计划查找公司车绑定司机的信息.
	 * 
	 * @param vehicleNo
	 *            the vehicle no
	 * @return the relation info entity
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-6 下午1:41:54
	 */
	private RelationInfoEntity queryDriverInfoByVehicleNoFromDepartPlan(
			String vehicleNo) {
		RelationInfoEntity relationInfoEntity = new RelationInfoEntity();
		QueryDepartEntity queryEntity = new QueryDepartEntity();
		queryEntity.setVehicleNo(vehicleNo);
		List<RelationInfoEntity> list = artificialDepartDao
				.queryDriverInfoByVehicleNoFormDepartPlan(queryEntity);
		if (null != list && list.size() > 0) {
			if (list.get(0) != null) {
				// 设置司机姓名
				relationInfoEntity.setDriverName(list.get(0).getDriverName());
				// 设置司机电话
				relationInfoEntity.setDriverPhone(list.get(0).getDriverPhone());
				// 设置司机编码
				relationInfoEntity.setDriverCode(list.get(0).getDriverCode());
				// 查询车队负责人的电话
				DriverAssociationDto driverDto = ownDriverService
						.queryOwnDriverAssociationDtoByDriverCode(list.get(0)
								.getDriverCode());
				if (driverDto != null) {
					// 查询车队负责人的电话
					relationInfoEntity.setFleetManagerPhone(driverDto
							.getDriverOrganizationLeaderPhone());
				}
				return relationInfoEntity;
			}
		}
		return null;
	}
	/**
	 * ********************任务车辆*********
	 * ******************** .
	 */
	private IArtificialDepartDao artificialDepartDao;
	/**
	 * ********************外请车**********
	 * *******************.
	 */
	private ILeasedDriverService leasedDriverService;
	/**
	 * ********************公司车**********
	 * *******************.
	 */
	private IOwnDriverService ownDriverService;
	/**
	 * Sets the artificial depart dao.
	 * 
	 * @param artificialDepartDao
	 *            the new artificial
	 *            depart dao
	 */
	public void setArtificialDepartDao(IArtificialDepartDao artificialDepartDao) {
		this.artificialDepartDao = artificialDepartDao;
	}
	/**
	 * Sets the leased driver service.
	 * 
	 * @param leasedDriverService
	 *            the new leased driver
	 *            service
	 */
	public void setLeasedDriverService(ILeasedDriverService leasedDriverService) {
		this.leasedDriverService = leasedDriverService;
	}
	/**
	 * Sets the own driver service.
	 * 
	 * @param ownDriverService
	 *            the new own driver
	 *            service
	 */
	public void setOwnDriverService(IOwnDriverService ownDriverService) {
		this.ownDriverService = ownDriverService;
	}
}