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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/server/service/impl/UpdateTaskStatusService.java
 *  
 *  FILE NAME          :UpdateTaskStatusService.java
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMotorcadeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleAssociationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LeasedVehicleTypeException;
import com.deppon.foss.module.base.common.api.server.service.IBusinessMonitorService;
import com.deppon.foss.module.base.common.api.shared.define.BusinessMonitorIndicator;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.arrival.api.server.dao.IArrivalDao;
import com.deppon.foss.module.transfer.arrival.api.shared.define.ArrivalConstant;
import com.deppon.foss.module.transfer.arrival.api.shared.domain.ArrivalEntity;
import com.deppon.foss.module.transfer.arrival.api.shared.domain.TruckArrivalEntity;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrJobTodoService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrNotifyService;
import com.deppon.foss.module.transfer.common.api.server.service.IWkBillAddTfrNotifyService;
import com.deppon.foss.module.transfer.common.api.shared.define.BusinessGoalContants;
import com.deppon.foss.module.transfer.common.api.shared.define.BusinessSceneConstants;
import com.deppon.foss.module.transfer.common.api.shared.define.NotifyWkConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrNotifyEntity;
import com.deppon.foss.module.transfer.common.api.shared.dto.WkHandOverBillEntity;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.departure.api.handle.DepartureHandle;
import com.deppon.foss.module.transfer.departure.api.server.dao.IDepartureDao;
import com.deppon.foss.module.transfer.departure.api.server.service.IDepartureService;
import com.deppon.foss.module.transfer.departure.api.server.service.IUpdateTaskStatusService;
import com.deppon.foss.module.transfer.departure.api.shared.define.DepartureConstant;
import com.deppon.foss.module.transfer.departure.api.shared.domain.TruckActionDetailEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.TruckDepartEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.TruckTaskDetailEntity;
import com.deppon.foss.module.transfer.load.api.server.service.IOutsideVehicleChargeService;
import com.deppon.foss.module.transfer.load.api.server.service.IPDACommonService;
import com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDADepartDto;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 车辆到达放行之后更新一些状态的公用方法.
 * 
 * @author foss-liubinbin(for IBM)
 * @date 2012-12-24 下午6:58:01
 */
public class UpdateTaskStatusService implements IUpdateTaskStatusService {
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(UpdateTaskStatusService.class);
	
	private ITfrJobTodoService tfrJobTodoService;

	public void setTfrJobTodoService(ITfrJobTodoService tfrJobTodoService) {
		this.tfrJobTodoService = tfrJobTodoService;
	}
	/**
	 * 同步给快递车辆任务 通知表
	 */
	private ITfrNotifyService tfrNotifyService;
	
	public void setTfrNotifyService(ITfrNotifyService tfrNotifyService) {
		this.tfrNotifyService = tfrNotifyService;
	}
	
	// 屏蔽ECS系统接口服务类
	private IConfigurationParamsService configurationParamsService;
	
	/**
	 * 屏蔽ECS系统接口服务类
	 * @param configurationParamsService
	 */
	public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}
	
	private IWkBillAddTfrNotifyService wkBillAddTfrNotifyService;
	
	/**
	 * @param wkBillAddTfrNotifyService the wkBillAddTfrNotifyService to set
	 */
	public void setWkBillAddTfrNotifyService(IWkBillAddTfrNotifyService wkBillAddTfrNotifyService) {
		this.wkBillAddTfrNotifyService = wkBillAddTfrNotifyService;
	}
	
	/**
	 * 通过车辆放行信息，找到车辆任务明细，更新状态， 并增加一条任务绑定的信息 手工绑定.
	 * 
	 * @param manualEntity
	 *            the manual entity
	 * @param departType
	 *            the depart type
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-2 下午2:16:05
	 */
	@Override
	public void updateTaskStatus(TruckDepartEntity manualEntity,
			String departType) {
		// 如果是接送货的，直接返回,只对长途跟短途的车辆更改状态
		if (DepartureConstant.DEPART_ITEM_TYPE_PKP.equals(manualEntity
				.getDepartItems())) {
			return;
		}
		if (StringUtil.isBlank(manualEntity.getTruckTaskId())) {
			LOGGER.error("不存在该车辆的任务，不能放行");
			throw new TfrBusinessException("不存在该车辆的任务，不能放行", "");
			// return;
		}
		// 任务车辆绑定表车如一条关联数据，先根据车牌号跟放行ID查到该任务，在进行更新
		TruckTaskDetailEntity truckTaskDetailEntity = new TruckTaskDetailEntity();
		truckTaskDetailEntity.setTruckTaskId(manualEntity.getTruckTaskId());
		if (DepartureConstant.ACTUAL_DEPART_TYPE_MANUAL.equals(departType)) {
			// 手工
			truckTaskDetailEntity.setOrigOrgCode(DepartureHandle
					.getCurrentOrgCode());
		} else if (DepartureConstant.ACTUAL_DEPART_TYPE_PDA.equals(departType)) {
			truckTaskDetailEntity.setOrigOrgCode(manualEntity
					.getPdaDepartOrgCode());
		} else {
			truckTaskDetailEntity.setId(manualEntity.getId());
			truckTaskDetailEntity.setOrigOrgCode(manualEntity
					.getGpsDepartOrgCode());
		}
		// 手工跟PDA部门需要转换
		if (DepartureConstant.ACTUAL_DEPART_TYPE_MANUAL.equals(departType)
				|| DepartureConstant.ACTUAL_DEPART_TYPE_PDA.equals(departType)) {
			// 调用综合接口查询出发部门是否为外场
			// 设置查询参数
			List<String> bizTypesList = new ArrayList<String>();
			// 车队类型
			bizTypesList.add(BizTypeConstants.ORG_TRANS_DEPARTMENT);
			// 外场类型
			bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);
			// 空运总调类型
			bizTypesList.add(BizTypeConstants.ORG_AIR_DISPATCH);
			// 派送不营业部
			bizTypesList.add(BizTypeConstants.ORG_SALES_DEPARTMENT);
			// 查询上级部门
			OrgAdministrativeInfoEntity origOrg = orgAdministrativeInfoComplexService
					.queryOrgAdministrativeInfoByCode(truckTaskDetailEntity
							.getOrigOrgCode(), bizTypesList);
			if (origOrg != null) {
				// 如果营业部
				if (FossConstants.YES.equals(origOrg.getSalesDepartment())) {
					SaleDepartmentEntity saleDetp = saleDepartmentService
							.querySaleDepartmentByCode(origOrg.getCode());
					if (saleDetp != null
							&& FossConstants.YES.equals(saleDetp.getStation())) {
						origOrg = pdaCommonService
								.getCurrentOutfieldCode(truckTaskDetailEntity
										.getOrigOrgCode());
					}
				}
				//如果是车队,找到车队对应的外场
				else if(FossConstants.YES.equals(origOrg.getTransDepartment()))
				{
					MotorcadeEntity motorcade = motorcadeService.queryMotorcadeByCode(origOrg.getCode());
					if(motorcade!=null&&motorcade.getTransferCenter()!=null)
					{
						origOrg.setCode(motorcade.getTransferCenter());
					}
				}
				// 装车部门为外场
				else {
					origOrg = pdaCommonService
							.getCurrentOutfieldCode(truckTaskDetailEntity
									.getOrigOrgCode());
				}
			}
			// 外场不为空
			if (origOrg != null) {
				truckTaskDetailEntity.setOrigOrgCode(origOrg.getCode());
			}// 营业部
			else {
				// 无需操作
			}
		}

		// 放行的时候判断是否是集配交接单，集配交接单不能放行
		departureService.isDistanceHandover(manualEntity.getTruckTaskId(),
				truckTaskDetailEntity.getOrigOrgCode(),null);
		List<TruckTaskDetailEntity> taskDetailList = departureDao.queryTruckTaskDetail(truckTaskDetailEntity);
		if (null == taskDetailList || taskDetailList.size() == 0) {
			LOGGER.error("不存在该车辆的任务，不能放行");
			throw new TfrBusinessException("不存在该车辆的任务，不能放行", "");
			// return;
		}
		//sonar-352203
		if (null == taskDetailList || taskDetailList.size() <= 0) {
			return;
		}
			// 放行时往监控程序中插入数据
			if(!DepartureConstant.ACTUAL_DEPART_TYPE_GPS.equals(departType))
//			{
				businessMonitorDepart(manualEntity, taskDetailList, departType);
//			}
			//用来标识是否有已出发的车辆任务
			boolean isUpdate = false;
			for (TruckTaskDetailEntity truckTaskDetailEntity1 : taskDetailList) {
				//只更新未出发的
				if(DepartureConstant.ARRIVAL_VEHICLE_UNDEPART.equals(truckTaskDetailEntity1.getStatus()))
				{
					// 更新状态
					truckTaskDetailEntity1.setStatus(DepartureConstant.ARRIVAL_VEHICLE_ONTHEWAY);
					if (DepartureConstant.ACTUAL_DEPART_TYPE_MANUAL.equals(departType)) {
						// 放行方式为纸质放行
						truckTaskDetailEntity1.setActualDepartType(DepartureConstant.ACTUAL_DEPART_TYPE_MANUAL);
						truckTaskDetailEntity1.setActualDepartTime(new Date());
					} else if (DepartureConstant.ACTUAL_DEPART_TYPE_PDA.equals(departType)) {
						// 放行方式为PDA放行
						truckTaskDetailEntity1.setActualDepartType(DepartureConstant.ACTUAL_DEPART_TYPE_PDA);
						truckTaskDetailEntity1.setActualDepartTime(manualEntity.getPdaDepartTime());
					} else if (DepartureConstant.ACTUAL_DEPART_TYPE_GPS.equals(departType)) {
						// 放行方式为GPS放行
						truckTaskDetailEntity1.setActualDepartType(DepartureConstant.ACTUAL_DEPART_TYPE_GPS);
						truckTaskDetailEntity1.setActualDepartTime(manualEntity.getGpsDepartTime());
					}
					truckTaskDetailEntity1.setTruckDepartId(manualEntity.getId());
					//校验同一个车辆任务下只能有一个有运费的车次号
					//校验同一个车辆任务不能含有 两个有运费的车次
					List<String> vehicleAssemNOs=vehicleAssembleBillService.queryTruckBillByDetailId(truckTaskDetailEntity1.getId(), null);
					if(CollectionUtils.isNotEmpty(vehicleAssemNOs)&&vehicleAssemNOs.size()>1){
						throw  new TfrBusinessException("同一车牌号，快递与零担均显示运费，无法出发！"); 
					}
					departureDao.updateTaskByManual(truckTaskDetailEntity1);
					// 更新任务车辆的状态，只要任务车辆明细有一条已出发，任务车辆的状态也更改为已出发
					TruckTaskDetailEntity truckTaskEntity = new TruckTaskDetailEntity();
					truckTaskEntity.setId(truckTaskDetailEntity1.getTruckTaskId());
					truckTaskEntity.setStatus(DepartureConstant.ARRIVAL_VEHICLE_ONTHEWAY);
					truckTaskEntity.setOstatus(DepartureConstant.ARRIVAL_VEHICLE_HALFWAY_ARRIVE);
					departureDao.updateTruckTask(truckTaskEntity);
					// 查询任务明细，如果有值，默认取出第一条
					TruckActionDetailEntity actionDetail = new TruckActionDetailEntity();
					actionDetail.setId(DepartureConstant.SEQ_TRUCK_ACTION_JOB);
					actionDetail.setBundType(DepartureConstant.JOB_TRUCK_DEPART);
					actionDetail.setTruckTaskDetailId(truckTaskDetailEntity1.getId());
					actionDetail.setStatus(DepartureConstant.JOB_NOT_START);
					actionDetail.setCreateTime(DepartureHandle.getCurrentDate());
					if (DepartureConstant.ACTUAL_DEPART_TYPE_MANUAL.equals(departType)) {
						actionDetail.setOperatorCode(DepartureHandle.getCurrentUserCode());
						actionDetail.setOperatorName(DepartureHandle.getCurrentUserName());
					}
					departureDao.addTruckActionDetail(actionDetail);
					//插入jobtodo表中
					this.tfrJobTodoService.addJobTodo(truckTaskDetailEntity1.getId(),
							BusinessSceneConstants.BUSINESS_SCENE_TRUCK_DEPARTURE, 
							new String[]{BusinessGoalContants.BUSINESS_GOAL_EXPRESS100,
								BusinessGoalContants.BUSINESS_GOAL_TPS,
								BusinessGoalContants.BUSINESS_GOAL_TO_TAOBAOJZ
								},DepartureHandle.getCurrentDate(), departType);
					//调用ECS系统接口开关
					if (configurationParamsService.queryTfrSwitch4Ecs()) {
						/** 新增同步给快递系统 车辆任务信息 通知任务 */
						LOGGER.error("调用插入临时表，通过JOB推送数据给悟空");
						// 根据车辆任务明细ID查询出悟空交接单
						List<WkHandOverBillEntity> wkHandoverbillList = wkBillAddTfrNotifyService
								.queryWkHandOverBillByTruckTaskDetailId(truckTaskDetailEntity1.getId());
						// 已交接单为单位插入到临时表
						for (WkHandOverBillEntity entity : wkHandoverbillList) {
							String currentUserInfo = null;
							if (DepartureConstant.ACTUAL_DEPART_TYPE_MANUAL.equals(departType)) {
								currentUserInfo = DepartureHandle.getCurrentUserCode() + ","
										+ DepartureHandle.getCurrentUserName() + ","
										+ DepartureHandle.getCurrentOrgCode();
							}
							String paramJson = entity.getHandoverBillNo() + "," + entity.getOperationOrgCode();
							tfrNotifyService.addTfrNotifyEntity(new TfrNotifyEntity(UUIDUtils.getUUID(),
									NotifyWkConstants.NOTIFY_TYPE_TRUCK_DEPARTURE, truckTaskDetailEntity1.getId(),
									BusinessSceneConstants.WK_HANDORVERBILL_HAVE_DEPART, currentUserInfo, paramJson));
						}
					}
					
//					/**
//					 * 调用tps接口同步tps出发信息
//					 */
//					try {
//						LOGGER.error("调用tps接口--同步出发开始!车辆任务id："+truckTaskDetailEntity1.getId());
//						this.departureService.synchDepartArriveInfoToTps(truckTaskDetailEntity1.getId(), manualEntity.getPdaDepartTime(), "start");
//						LOGGER.error("调用tps接口--同步出发结束!车辆任务id："+truckTaskDetailEntity1.getId());
//					} catch (Exception e) {
//						LOGGER.error("调用tps接口--同步出发失败,错误信息："+e.toString());
//						e.printStackTrace();
//					}
					
				}else{
					isUpdate = true;
				}
			}
			if(isUpdate) {
				// 更新同一个任务下，同一个出发部门出发的时间（PDA或者手工，或者GPS）
				if (manualEntity.getId() != null) {
					truckTaskDetailEntity.setTruckDepartId(manualEntity.getId());
					updateDepartTime(manualEntity, truckTaskDetailEntity,departType);
				}
			}
//		} 
	}

	/**
	 * 
	 * 更新同一个任务下，同一个出发部门出发的时间（PDA或者手工） 不更新车辆的状态
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-3-26 下午1:08:33
	 */
	private void updateDepartTime(TruckDepartEntity manualEntity,
			TruckTaskDetailEntity truckTaskDetailEntity, String departType) {
		// 更新放行表的时间
		truckTaskDetailEntity.setId(manualEntity.getId());
		departureDao.updateTruckDepartTimeByTask(manualEntity);
		// 根据车辆放行id获取该车辆放行对应的任务下，出发部门一致，车牌号一致的所有的任务车辆明细
		List<TruckTaskDetailEntity> truckTaskDetailList = departureDao
				.queryTruckTaskDetailByDepartId(truckTaskDetailEntity);
		for (TruckTaskDetailEntity truckTaskDetailEntity1 : truckTaskDetailList) {
			if (DepartureConstant.ACTUAL_DEPART_TYPE_PDA.equals(departType)
					&& (!DepartureConstant.ACTUAL_DEPART_TYPE_GPS.equals(truckTaskDetailEntity1.getActualDepartType()))) {
				// 如果是pda的，并且现在的状态不是gps的，可以直接更新
				if(DepartureConstant.ARRIVAL_VEHICLE_UNDEPART.equals(truckTaskDetailEntity1.getStatus())){
					truckTaskDetailEntity1.setStatus(DepartureConstant.ARRIVAL_VEHICLE_ONTHEWAY);
				}
				truckTaskDetailEntity1.setActualDepartTime(manualEntity.getPdaDepartTime());
				truckTaskDetailEntity1.setActualDepartType(DepartureConstant.ACTUAL_DEPART_TYPE_PDA);
			}
			if (DepartureConstant.ACTUAL_DEPART_TYPE_MANUAL.equals(departType)
					&& (!DepartureConstant.ACTUAL_DEPART_TYPE_PDA.equals(truckTaskDetailEntity1.getActualDepartType()))
					&& (!DepartureConstant.ACTUAL_DEPART_TYPE_GPS.equals(truckTaskDetailEntity1.getActualDepartType()))) {
				if(DepartureConstant.ARRIVAL_VEHICLE_UNDEPART.equals(truckTaskDetailEntity1.getStatus())||
						DepartureConstant.ARRIVAL_VEHICLE_ONTHEWAY.equals(truckTaskDetailEntity1.getStatus())){
					// 如果是纸质放行的，并且现在的不是pda的，并且现在的状态不是gps的，可以直接更新
					if(DepartureConstant.ARRIVAL_VEHICLE_UNDEPART.equals(truckTaskDetailEntity1.getStatus())){
						truckTaskDetailEntity1.setStatus(DepartureConstant.ARRIVAL_VEHICLE_ONTHEWAY);
					}
					truckTaskDetailEntity1.setActualDepartTime(manualEntity.getManualDepartTime());
					truckTaskDetailEntity1.setActualDepartType(DepartureConstant.ACTUAL_DEPART_TYPE_MANUAL);
				}
			}
			truckTaskDetailEntity1.setTruckDepartId(manualEntity.getId());
			if (DepartureConstant.ACTUAL_DEPART_TYPE_GPS.equals(departType)) {
				truckTaskDetailEntity1.setActualDepartTime(manualEntity.getGpsDepartTime());
			}
			departureDao.updateTaskByManual(truckTaskDetailEntity1);
		}
	}

	/**
	 * 
	 * 更新同一个任务下，同一个出发部门出发的时间（GPS） 不更新车辆的状态 放行的时间已经在gps的服务里面做过了
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-3-26 下午1:08:33
	 */
	public void updateGPSDepartTime(
			TruckTaskDetailEntity truckTaskDetailEntity, Date gpsDepartTime) {
		// 根据GPS的id获取该车辆放行对应的任务下，出发部门一致，车牌号一致的所有的任务车辆明细
		List<TruckTaskDetailEntity> truckTaskDetailList = departureDao
				.queryTruckTaskDetailByTaskId(truckTaskDetailEntity);
		for (TruckTaskDetailEntity truckTaskDetailEntity1 : truckTaskDetailList) {
			// 根据原来的出发类型和需要更新的出发类型来判断需要不需要更换放行类型
			// 如果是GPS，无条件更新
//			truckTaskDetailEntity1
//					.setStatus(DepartureConstant.ARRIVAL_VEHICLE_ONTHEWAY);
			truckTaskDetailEntity1.setActualDepartTime(gpsDepartTime);
			truckTaskDetailEntity1
					.setActualDepartType(DepartureConstant.ACTUAL_DEPART_TYPE_GPS);
			departureDao.updateTaskByManual(truckTaskDetailEntity1);
		}
	}

	/**
	 * 
	 * 放行时往监控中写数据
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-3-26 下午1:10:27
	 */
	private void businessMonitorDepart(TruckDepartEntity manualEntity,
			List<TruckTaskDetailEntity> taskDetailList, String departType) {
		VehicleAssociationDto dto = null;
		OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
		try {
			dto = vehicleService
					.queryVehicleAssociationDtoByVehicleNo(manualEntity
							.getVehicleNo());
		} catch (LeasedVehicleTypeException e) {
			LOGGER.error(e.getMessage());
			throw new BusinessException(e.getMessage(), "");
		}
		if (DepartureConstant.ACTUAL_DEPART_TYPE_MANUAL.equals(departType)) {
			dept.setCode(manualEntity.getManualDepartOrgCode());
		} else if (DepartureConstant.ACTUAL_DEPART_TYPE_PDA.equals(departType)) {
			dept.setCode(manualEntity.getPdaDepartOrgCode());
		} else if (DepartureConstant.ACTUAL_DEPART_TYPE_GPS.equals(departType)) {
			dept.setCode(manualEntity.getGpsDepartOrgCode());
		}
		CurrentInfo currentInfo = new CurrentInfo(null, dept);
		if (DepartureConstant.TRUCK_TYPE_LEASED.equals(dto
				.getVehicleOwnershipType())) {
			// 外请车
			if (!FossConstants.YES.equals(taskDetailList.get(0).getBeCarLoad())) {
				// 需要是不含整车的
				if (DepartureConstant.DEPART_ITEM_TYPE_LONG.equals(manualEntity
						.getDepartItems())) {
					// 长途发车台次
					BusinessMonitorIndicator counterIndicator = BusinessMonitorIndicator.LEASED_LONG_DEPART_COUNT;
					businessMonitorService.counter(counterIndicator,
							currentInfo);
				} else if (DepartureConstant.DEPART_ITEM_TYPE_SHORT
						.equals(manualEntity.getDepartItems())) {
					// 短途发车台次
					BusinessMonitorIndicator counterIndicator = BusinessMonitorIndicator.LEASED_SHORT_DEPART_COUNT;
					businessMonitorService.counter(counterIndicator,
							currentInfo);
				}
			}
			if (DepartureConstant.DEPART_ITEM_TYPE_PKP.equals(manualEntity
					.getDepartItems())) {
				// 集中接送货车发车台次
				BusinessMonitorIndicator counterIndicator = BusinessMonitorIndicator.LEASED_SCHEDULE_DEPART_COUNT;
				businessMonitorService.counter(counterIndicator, currentInfo);
			}
		}
		/* 应用监控服务 */
		// 车辆放行时计数
		if (DepartureConstant.DEPART_ITEM_TYPE_LONG.equals(manualEntity
				.getDepartItems())) {
			// 长途发车台次
			BusinessMonitorIndicator counterIndicator = BusinessMonitorIndicator.TRUCK_LONG_DEPART_COUNT;
			businessMonitorService.counter(counterIndicator, currentInfo);
		} else if (DepartureConstant.DEPART_ITEM_TYPE_SHORT.equals(manualEntity
				.getDepartItems())) {
			// 短途发车台次
			BusinessMonitorIndicator counterIndicator = BusinessMonitorIndicator.TRUCK_SHORT_DEPART_COUNT;
			businessMonitorService.counter(counterIndicator, currentInfo);
		} else if (DepartureConstant.DEPART_ITEM_TYPE_PKP.equals(manualEntity
				.getDepartItems())) {
			// 集中接送货车发车台次
			BusinessMonitorIndicator counterIndicator = BusinessMonitorIndicator.TRUCK_SCHEDULE_DEPART_COUNT;
			businessMonitorService.counter(counterIndicator, currentInfo);
		}
	}

	/**
	 * 判断是否存在该车牌号的该任务，通过车牌号，出发部门，状态来查询
	 * 
	 * @param manualEntity
	 *            the manual entity
	 * @param departType
	 *            the depart type
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-2 下午2:16:05
	 */
	@Override
	public void existTaskOrNot(TruckDepartEntity manualEntity, String departType) {
		// 任务车辆绑定表车如一条关联数据，先根据车牌号跟放行ID查到该任务，在进行更新
		TruckTaskDetailEntity truckTaskDetailEntity = new TruckTaskDetailEntity();
		truckTaskDetailEntity.setVehicleNo(manualEntity.getVehicleNo());
		truckTaskDetailEntity
				.setStatus(DepartureConstant.ARRIVAL_VEHICLE_UNDEPART);
		truckTaskDetailEntity.setOrigOrgCode(DepartureHandle
				.getCurrentOrgCode());
		List<TruckTaskDetailEntity> taskDetailList = departureDao
				.queryTruckTaskDetail(truckTaskDetailEntity);
		if (null == taskDetailList || taskDetailList.size() == 0) {
			LOGGER.error("不存在该车辆的任务，不能申请放行信息");
			throw new TfrBusinessException("不存在该车辆的任务，不能申请放行信息", "");
		}
	}

	/**
	 * 
	 * 增加一条到达信息
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-3-29 上午11:09:37
	 */
	@Override
	public String addTruckArrival(PDADepartDto pdaDto, String departType,Date planArriveTime) {
		TruckArrivalEntity truckArrivalEntity = new TruckArrivalEntity();
		// 判断已到还是晚到
		if (DepartureHandle.isOnTime(pdaDto.getOperatingTime(),planArriveTime)) {
			// 准时到达
			truckArrivalEntity.setStatus(ArrivalConstant.DEPART_ARRIVALED);
		} else {
			// 晚点到达
			truckArrivalEntity.setStatus(ArrivalConstant.DEPART_LATE_ARRIVALED);
		}
		truckArrivalEntity.setId(UUIDUtils.getUUID());
		// 车牌
		truckArrivalEntity.setVehicleNo(pdaDto.getVehicleNo());
		if (DepartureConstant.ACTUAL_DEPART_TYPE_GPS.equals(departType)) {
			truckArrivalEntity.setGpsArriveTime(pdaDto.getOperatingTime());
		} else {
			// 操作人编码
			truckArrivalEntity.setPdaArriveUserCode(pdaDto.getOperator());
			// 操作部门编码
			truckArrivalEntity.setPdaArriveOrgCode(pdaDto.getOrgCode());
			// 到达时间
			truckArrivalEntity.setPdaArriveTime(pdaDto.getOperatingTime());
		}
		// 创建人编码
		truckArrivalEntity.setCreateUserCode(pdaDto.getOperator());
		// 创建部门编码
		truckArrivalEntity.setCreateOrgCode(pdaDto.getOrgCode());
		// 创建时间
		truckArrivalEntity.setCreateTime(new Date());
		// 创建人名称
		truckArrivalEntity.setCreateUserName(pdaDto.getOperator());
		arrivalDao.insertTruckArrival(truckArrivalEntity);
		return truckArrivalEntity.getId();
	}

	/**
	 * 
	 * 更改到达之后的状态
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-3-29 上午11:09:37
	 */
	@Override
	public void updateArriveStatus(List<TruckTaskDetailEntity> taskDetailList,
			PDADepartDto pdaDto, String departType, String truckTaskId,
			String destOrgCode) {
		if (null != taskDetailList && taskDetailList.size() > 0) {
			for (TruckTaskDetailEntity truckTaskDetailEntity1 : taskDetailList) {
				// 根据任务车辆明细判断是否是集配交接单，集配交接单不能到达
				departureService.taskIsDistanceHandover(truckTaskDetailEntity1
						.getTruckTaskId(),null,truckTaskDetailEntity1.getDestOrgCode());
				// 如果该任务车辆明细没有做过到达，新增，并更改相应的状态
				if (StringUtil.isBlank(truckTaskDetailEntity1
						.getTruckArriveId())) {
					// 增加一条到达信息
					String arriveId = addTruckArrival(pdaDto, departType,truckTaskDetailEntity1.getPlanArriveTime());
					// 更新状态为到达
					truckTaskDetailEntity1
							.setStatus(ArrivalConstant.ARRIVAL_VEHICLE_ARRIVED);
					// 实际到达时间
					if (DepartureConstant.ACTUAL_DEPART_TYPE_GPS
							.equals(departType)) {
						// gps到达时间
						truckTaskDetailEntity1.setActualArriveTime(pdaDto
								.getOperatingTime());
						truckTaskDetailEntity1
								.setActualArriveType(DepartureConstant.ACTUAL_DEPART_TYPE_GPS);
					} else {
						truckTaskDetailEntity1.setActualArriveTime(new Date());
						// 到达类型
						truckTaskDetailEntity1.setActualArriveType(departType);
					}
					// 到达ID
					truckTaskDetailEntity1.setTruckArriveId(arriveId);
					departureDao.updateTaskByManual(truckTaskDetailEntity1);
					// 更新任务车辆的状态，先查看该条信息是不是最后一条未到达的任务
					ArrivalEntity arrivalEntity = new ArrivalEntity();
					// ID
					arrivalEntity.setId(truckTaskDetailEntity1.getId());
					// 任务ID
					arrivalEntity.setTruckTaskId(truckTaskDetailEntity1
							.getTruckTaskId());
					arrivalEntity
							.setDetailStatus(ArrivalConstant.ARRIVAL_VEHICLE_ARRIVED);
					if (arrivalDao.isLastTask(arrivalEntity) <= 0) {
						// 已经是最后一条了，需要更新任务车辆状态
						// 更新任务车辆状态
						TruckTaskDetailEntity truckTaskDetailEntity = new TruckTaskDetailEntity();
						truckTaskDetailEntity.setId(truckTaskDetailEntity1
								.getTruckTaskId());
						// 状态
						truckTaskDetailEntity
								.setStatus(ArrivalConstant.ARRIVAL_VEHICLE_ARRIVED);
						departureDao.updateTruckTask(truckTaskDetailEntity);
						//sonar-352203
					} else if(arrivalDao.beFinallyArrive(truckTaskDetailEntity1.getId()) > 0) {
						//else块为新加的 zyx MANA-227
						//当前车辆为最终到达时, 需自动到达其他子任务
						//不区分GPS或PDA到达
//						if(arrivalDao.beFinallyArrive(truckTaskDetailEntity1.getId()) > 0) {
							//查询当前主任务下未到达的子任务
							//(不管到达部门是否为当前部门, 只要当前车辆下配载单为最终到达), 就自动做到达其他子任务任务
							List<TruckTaskDetailEntity> truckTaskDetails = departureDao.queryUnArrivalTaskByTaskId(truckTaskId);
							for(TruckTaskDetailEntity truckTaskDetail : truckTaskDetails) {
								truckTaskDetail.setTruckArriveId(arriveId);
								truckTaskDetail.setStatus(ArrivalConstant.ARRIVAL_VEHICLE_ARRIVED);
								truckTaskDetail.setActualArriveTime(new Date());
								truckTaskDetail.setActualArriveType(DepartureConstant.ACTUAL_DEPART_TYPE_FOSS);
								departureDao.updateTaskByManual(truckTaskDetail);
								
								// job表增加一条数据
								TruckActionDetailEntity actionDetail = new TruckActionDetailEntity();
								//sonar-352203-set值抽出为方法
								initActionDetail(pdaDto, departType,
										truckTaskDetail, actionDetail);
								departureDao.addTruckActionDetail(actionDetail);
							}
							
							//更新车辆主任务, 此次任务结束
							TruckTaskDetailEntity truckTaskDetailTemp = new TruckTaskDetailEntity();
							truckTaskDetailTemp.setId(truckTaskId);
							truckTaskDetailTemp.setStatus(ArrivalConstant.ARRIVAL_VEHICLE_ARRIVED);
							
							departureDao.updateTruckTask(truckTaskDetailTemp);
//						}
						//end
					}
					//生成费用信息
					// job表增加一条数据
					TruckActionDetailEntity actionDetail = new TruckActionDetailEntity();
					// 类型
					actionDetail
							.setBundType(DepartureConstant.JOB_TRUCK_ARRIVAL);
					// 任务车辆明细ID
					actionDetail.setTruckTaskDetailId(truckTaskDetailEntity1
							.getId());
					// 状态
					actionDetail.setStatus(DepartureConstant.JOB_NOT_START);
					// 创建时间
					actionDetail
							.setCreateTime(DepartureHandle.getCurrentDate());
					if (DepartureConstant.ACTUAL_DEPART_TYPE_MANUAL.equals(departType)) {
						actionDetail.setOperatorCode(DepartureHandle.getCurrentUserCode());
						actionDetail.setOperatorName(DepartureHandle.getCurrentUserName());
					}
					departureDao.addTruckActionDetail(actionDetail);
					
					this.tfrJobTodoService.addJobTodo(truckTaskDetailEntity1.getId(),
							BusinessSceneConstants.BUSINESS_SCENE_TRUCK_ARRIVAL, 
							new String[]{BusinessGoalContants.BUSINESS_GOAL_EXPRESS100,
								BusinessGoalContants.BUSINESS_GOAL_TPS,
								BusinessGoalContants.BUSINESS_GOAL_TO_TAOBAOJZ},DepartureHandle.getCurrentDate(), "GPS");
					//调用ECS系统接口开关
					if (configurationParamsService.queryTfrSwitch4Ecs()) {
						/** 新增同步给快递系统 车辆任务信息 通知任务 */
						LOGGER.error("调用插入临时表，通过JOB推送数据给悟空");
						// 根据车辆任务明细ID查询出悟空交接单
						List<WkHandOverBillEntity> wkHandoverbillList = wkBillAddTfrNotifyService
								.queryWkHandOverBillByTruckTaskDetailId(truckTaskDetailEntity1.getId());
						// 已交接单为单位插入到临时表
						for (WkHandOverBillEntity entity : wkHandoverbillList) {
//							String currentUserInfo = null;
							//sonar-352203抽取为方法
							String currentUserInfo = initCurrentUserInfo(pdaDto,
									departType);
							String paramJson = entity.getHandoverBillNo() + "," + entity.getOperationOrgCode();

							tfrNotifyService.addTfrNotifyEntity(new TfrNotifyEntity(UUIDUtils.getUUID(),
									NotifyWkConstants.NOTIFY_TYPE_TRUCK_ARRIVAL, truckTaskDetailEntity1.getId(),
									BusinessSceneConstants.WK_HANDORVERBILL_HAVE_ARRIVE, currentUserInfo, paramJson));
						}
					}

				}
				//生成外请车时效费用 zyx
				//MANA-227
				outsideVehicleChargeService.addOutsideVehicleChargeForArrival(truckTaskDetailEntity1.getId());
				
//				/**
//				 * 调用tps接口同步tps到达信息
//				 */
//				try {
//					LOGGER.error("调用tps接口--同步到达开始!车辆任务id："+truckTaskDetailEntity1.getId());
//					this.departureService.synchDepartArriveInfoToTps(truckTaskDetailEntity1.getId(), new Date(), "arrive");
//					LOGGER.error("调用tps接口--同步到达开始!车辆任务id："+truckTaskDetailEntity1.getId());
//				} catch (Exception e) {
//					LOGGER.error("调用tps接口--同步到达失败,错误信息："+e.toString());
//					e.printStackTrace();
//				}
				
			}
		} else {
			// 如果没有找到记录，找最近两天的状态为到达的记录，带上到达部门，车牌号，为过滤条件
			// 任务车辆绑定表车如一条关联数据，先根据车牌号跟放行ID查到该任务，在进行更新
			TruckTaskDetailEntity truckTaskDetailEntity = new TruckTaskDetailEntity();
			// 车牌号
			truckTaskDetailEntity.setVehicleNo(pdaDto.getVehicleNo());
			// 状态
			truckTaskDetailEntity
					.setStatus(ArrivalConstant.ARRIVAL_VEHICLE_ARRIVED);

			if (DepartureConstant.ACTUAL_DEPART_TYPE_GPS.equals(departType)) {
				// GPS直接定位
				truckTaskDetailEntity.setTruckTaskId(truckTaskId);
				truckTaskDetailEntity.setDestOrgCode(destOrgCode);
			} else {
				// 不是GPS找到到达时间在两天之内的
				truckTaskDetailEntity.setCreateTime(DepartureHandle.adddate(
						new Date(), -2));
			}
			taskDetailList = departureDao
					.queryTruckTaskDetail(truckTaskDetailEntity);
			for (TruckTaskDetailEntity truckTaskDetailEntity1 : taskDetailList) {
				// 如果不是GPS到达的记录，更新该任务明细的到达方式为PDA，更新到达记录表
				TruckArrivalEntity truckArrivalEntity = new TruckArrivalEntity();
				truckArrivalEntity.setId(truckTaskDetailEntity1
						.getTruckArriveId());
				if (DepartureConstant.ACTUAL_DEPART_TYPE_GPS.equals(departType)) {
					if(truckTaskDetailEntity1.getActualArriveType()!=null)
					{//有GPS只算第一次的时间
						truckArrivalEntity.setGpsArriveTime(pdaDto
								.getOperatingTime());
					}
				} else {
					truckArrivalEntity.setPdaArriveUserCode(pdaDto
							.getOperator());
					truckArrivalEntity.setPdaArriveOrgCode(pdaDto.getOrgCode());
					truckArrivalEntity.setPdaTerminalNo(pdaDto
							.getPdaTerminalNo());
					truckArrivalEntity.setPdaArriveTime(pdaDto
							.getOperatingTime());
				}
				arrivalDao.updateTruckArrival(truckArrivalEntity);
				// 更新车辆任务明细的到达类型，到达时间
				if (DepartureConstant.ACTUAL_DEPART_TYPE_GPS.equals(departType)) {
					TruckTaskDetailEntity entity = new TruckTaskDetailEntity();
					entity.setId(truckTaskDetailEntity1.getId());
					if(!StringUtils.equals(truckTaskDetailEntity1.getActualArriveType(), "GPS")){
						entity.setActualArriveTime(pdaDto.getOperatingTime());
					}
					// 到达类型置为GPS到达
					entity.setActualArriveType(DepartureConstant.ACTUAL_DEPART_TYPE_GPS);
					// 更新任务车辆明细
					departureDao.updateTaskByManual(entity);
				} else {
					if (!DepartureConstant.ACTUAL_DEPART_TYPE_GPS
							.equals(truckTaskDetailEntity1
									.getActualArriveType())) {
						TruckTaskDetailEntity entity = new TruckTaskDetailEntity();
						entity.setId(truckTaskDetailEntity1.getId());
						entity.setActualArriveTime(pdaDto.getOperatingTime());
						// 到达类型置为PDA到达
						entity.setActualArriveType(DepartureConstant.ACTUAL_DEPART_TYPE_PDA);
						// 更新任务车辆明细
						departureDao.updateTaskByManual(entity);
					}
				}
			}
		}
	}

	/**
	 * @param pdaDto
	 * @param departType
	 * @return
	 */
	private String initCurrentUserInfo(PDADepartDto pdaDto, String departType) {
		String currentUserInfo;
		if (DepartureConstant.ACTUAL_DEPART_TYPE_MANUAL.equals(departType)) {
			currentUserInfo = DepartureHandle.getCurrentUserCode() + ","
					+ DepartureHandle.getCurrentUserName() + ","
					+ DepartureHandle.getCurrentOrgCode();
		} else {
			currentUserInfo = pdaDto.getOperator() + "," + pdaDto.getOperator() + ","
					+ pdaDto.getOrgCode();
		}
		return currentUserInfo;
	}

	/**
	 * @param pdaDto
	 * @param departType
	 * @param truckTaskDetail
	 * @param actionDetail
	 */
	private void initActionDetail(PDADepartDto pdaDto, String departType,
			TruckTaskDetailEntity truckTaskDetail,
			TruckActionDetailEntity actionDetail) {
		actionDetail.setId(DepartureConstant.SEQ_TRUCK_ACTION_JOB);
		actionDetail.setBundType(DepartureConstant.JOB_TRUCK_ARRIVAL);
		actionDetail.setTruckTaskDetailId(truckTaskDetail.getId());
		actionDetail.setStatus(DepartureConstant.JOB_NOT_START);
		actionDetail.setCreateTime(DepartureHandle.getCurrentDate());
		if (DepartureConstant.ACTUAL_DEPART_TYPE_MANUAL.equals(departType)) {
			actionDetail.setOperatorCode(DepartureHandle.getCurrentUserCode());
			actionDetail.setOperatorName(DepartureHandle.getCurrentUserName());
		} else {
			actionDetail.setOperatorCode(pdaDto.getOperator());
			actionDetail.setOperatorName(pdaDto.getOperator());
		}
	}
	
	/**
	 * 外请车时效费用service
	 */
	private IOutsideVehicleChargeService outsideVehicleChargeService;

	/**
	 * ********************* 车辆出发底层实现 ****************************.
	 */
	private IDepartureDao departureDao;
	/** ***到达*****. */
	private IArrivalDao arrivalDao;
	/**
	 * ********************* 监控服务 ****************************.
	 */
	private IBusinessMonitorService businessMonitorService;
	/**
	 * ****************** 车辆服务接口 *************************.
	 */
	private IVehicleService vehicleService;
	/**
	 * 装卸车PDA共通service接口
	 * 
	 */
	public IPDACommonService pdaCommonService;
	/**
	 * 查询部门service接口
	 * 
	 */
	IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * 营业部service接口
	 * 
	 */
	ISaleDepartmentService saleDepartmentService;

	IDepartureService departureService;
	IMotorcadeService motorcadeService;
	IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	private IVehicleAssembleBillService vehicleAssembleBillService;

	/**
	 * 设置 *********************车辆出发底层实现** ******************** ******.
	 * 
	 * @param departureDao
	 *            the new *************** ******车辆出发底层实现******
	 *            **********************
	 */
	public void setDepartureDao(IDepartureDao departureDao) {
		this.departureDao = departureDao;
	}

	/**
	 * Sets the business monitor service.
	 * 
	 * @param businessMonitorService
	 *            the new business monitor service
	 */
	public void setBusinessMonitorService(
			IBusinessMonitorService businessMonitorService) {
		this.businessMonitorService = businessMonitorService;
	}

	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	public void setVehicleAssembleBillService(
			IVehicleAssembleBillService vehicleAssembleBillService) {
		this.vehicleAssembleBillService = vehicleAssembleBillService;
	}

	/**
	 * Sets the vehicle service.
	 * 
	 * @param vehicleService
	 *            the new vehicle service
	 */
	public void setVehicleService(IVehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}

	public void setArrivalDao(IArrivalDao arrivalDao) {
		this.arrivalDao = arrivalDao;
	}

	public void setDepartureService(IDepartureService departureService) {
		this.departureService = departureService;
	}

	public void setPdaCommonService(IPDACommonService pdaCommonService) {
		this.pdaCommonService = pdaCommonService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setMotorcadeService(IMotorcadeService motorcadeService) {
		this.motorcadeService = motorcadeService;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	/**
	 * @param outsideVehicleChargeService the outsideVehicleChargeService to set
	 */
	public void setOutsideVehicleChargeService(
			IOutsideVehicleChargeService outsideVehicleChargeService) {
		this.outsideVehicleChargeService = outsideVehicleChargeService;
	}
}