/**
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
 *  initial comments.
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
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  
 *  you may not use this file except in compliance with the License.
 *  
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  
 *  
 *  distributed under the License is distributed on an "AS IS" BASIS,
 * 
 *  
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  
 *  
 *  See the License for the specific language governing permissions and
 *  
 *  
 *  limitations under the License.
 *  
 *  
 *  Contributors:
 *  
 *  
 *  
 *  
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-load
 *  
 *  
 *  
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/server/service/impl/TruckTaskService.java
 *  
 *  FILE NAME          :TruckTaskService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 *  
 ******************************************************************************/
/**
 * 
 * 
 * 
 * 
 * PROJECT NAME: tfr-load
 * 
 * 
 * 
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.server.dao.impl
 * 
 * 
 * FILE    NAME: TaskTruckDao.java
 * 
 * 
 * 
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
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
 *  交接单与任务车辆表的关系			正	生成交接单的时候，生成任务车辆表记录（异步）	
 *  	
 *  	
 *	封签与任务车辆表的关系			正/反	封签完毕后，匹配任务车辆表， 任务车辆表生成后，匹配封签表
 *		
 *	
 *	配载单与任务车辆表的关系			正	生成配载单的时候，生成任务车辆表记录（异步）	
 *		
 *
 *  证件包与任务车辆表的关系			正/反	证件包领取完毕后，匹配任务车辆表， 任务车辆表生成后，匹配证件包表	
 *  		
 *  
 *	放行与任务车辆表的关系			正/反	系统放行后，匹配任务车辆表记录
 *			
 *
 *	到达与任务车辆表的关系			正	系统到达后，匹配任务车辆表记录	
 *		
 *
 *	外请车报到与任务车辆关系			反				
 *
 *
 *  到达与接送货卸车表关系			正/反	系统到达后，匹配接送货卸车表记录，接送货卸车表记录生成后，匹配到达表					
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
 */ 
package com.deppon.foss.module.transfer.load.server.service.impl;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressLineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DepartureStandardDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleAssociationDto;
import com.deppon.foss.module.base.common.api.server.service.IBusinessMonitorService;
import com.deppon.foss.module.base.common.api.shared.define.BusinessMonitorIndicator;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.common.api.server.service.IFOSSToWkService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrNotifyService;
import com.deppon.foss.module.transfer.common.api.shared.define.NotifyWkConstants;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrJobBusinessTypeEnum;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrNotifyEntity;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskCallESBDao;
import com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao;
import com.deppon.foss.module.transfer.load.api.server.dao.IWKTfrBillDao;
import com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService;
import com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskService;
import com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.define.TaskTruckConstant;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillSerialNoDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.TruckGPSTaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.HandOverBillDetailDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.HandOverMsgDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.TruckTaskHandOverDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.VehiclEmptyBillDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.WayBillHandOverDto;
import com.deppon.foss.module.transfer.scheduling.api.define.TransportPathConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ICalculateTransportPathService;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 任务车辆Service.
 *
 * @author dp-duyi
 * @date 2012-11-7 上午10:25:35
 */
public class TruckTaskService implements ITruckTaskService { 
	/** 
	 * 日志. 
	 *  
	 */
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	/**
	 * 应用监控服务
	 */
	private IBusinessMonitorService businessMonitorService;

	public void setBusinessMonitorService(
			IBusinessMonitorService businessMonitorService) {
		this.businessMonitorService = businessMonitorService;
	}
	
	/**
	 * ECS系统接口调用服务类
	 */
	private IFOSSToWkService fossToWkService;
	
	public void setFossToWkService(IFOSSToWkService fossToWkService) {
		this.fossToWkService = fossToWkService;
	}
	
	/** 
	 * 车辆任务dao.
	 *  
	 */
	private ITruckTaskDao truckTaskDao;
	/**
	 * Sets the truck task dao.
	 *
	 * @param truckTaskDao the new truck task dao
	 */
	public void setTruckTaskDao(ITruckTaskDao truckTaskDao) {
		this.truckTaskDao = truckTaskDao;
	}
	/** 
	 * The truck task service.
	 * 
	 * 
	 */
	private ITruckTaskService truckTaskService;
	/**
	 * Sets the truck task service.
	 *
	 *
	 * @param truckTaskService the new truck task service
	 */
	public void setTruckTaskService(ITruckTaskService truckTaskService) {
		this.truckTaskService = truckTaskService;
	}
	/** 
	 * The line service. 
	 * 
	 */
	private ILineService lineService;
	
	private IExpressLineService expresslineService ;
	
	/**
	 * Sets the line service.
	 *
	 *
	 * @param lineService the new line service
	 */
	public void setLineService(ILineService lineService) {
		this.lineService = lineService;
	}
	public void setExpresslineService(IExpressLineService expresslineService) {
		this.expresslineService = expresslineService;
	}
	/** 
	 * The vehicle service. 
	 * 
	 * 
	 */
	private IVehicleService vehicleService;
	/**
	 * Sets the vehicle service.
	 *
	 *
	 * @param vehicleService the new vehicle service
	 */
	public void setVehicleService(IVehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}
	/** 
	 * The org administrative info service.
	 * 
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * Sets the org administrative info service.
	 *
	 *
	 * @param orgAdministrativeInfoService the new org administrative info service
	 */
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	/** 
	 * 
	 * The calculate transport path service.
	 * 
	 */
	private ICalculateTransportPathService calculateTransportPathService;
	/**
	 * Sets the calculate transport path service.
	 *
	 *
	 * @param calculateTransportPathService the new calculate transport path service
	 */
	public void setCalculateTransportPathService(ICalculateTransportPathService calculateTransportPathService) {
		this.calculateTransportPathService = calculateTransportPathService;
	}
	/** The hand over bill service. */
	private IHandOverBillService handOverBillService;
	/**
	 * Sets the hand over bill service.
	 *
	 * @param handOverBillService the new hand over bill service
	 */
	public void setHandOverBillService(IHandOverBillService handOverBillService) {
		this.handOverBillService = handOverBillService;
	}
	/** The tfr common service. */
	private ITfrCommonService tfrCommonService;
	/**
	 * Sets the tfr common service.
	 *
	 * @param tfrCommonService the new tfr common service
	 */
	public void setTfrCommonService(ITfrCommonService tfrCommonService) {
		this.tfrCommonService = tfrCommonService;
	}
	private ITruckTaskCallESBDao truckTaskCallESBDao;
	public void setTruckTaskCallESBDao(ITruckTaskCallESBDao truckTaskCallESBDao) {
		this.truckTaskCallESBDao = truckTaskCallESBDao;
	}
	/**
	 * ************************ 更新配载单 *************************.
	 */
	private IVehicleAssembleBillService vehicleAssembleBillService;
	
	private IConfigurationParamsService configurationParamsService;
	
	private ITfrNotifyService tfrNotifyService;
	
	private IWKTfrBillDao wKTfrBillDao;
	
	public void setwKTfrBillDao(IWKTfrBillDao wKTfrBillDao) {
		this.wKTfrBillDao = wKTfrBillDao;
	}
	public void setTfrNotifyService(ITfrNotifyService tfrNotifyService) {
		this.tfrNotifyService = tfrNotifyService;
	}
	public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}
	public void setVehicleAssembleBillService(
			IVehicleAssembleBillService vehicleAssembleBillService) {
		this.vehicleAssembleBillService = vehicleAssembleBillService;
	}

	/**
	 * 插入任务车辆.
	 *
	 *
	 * @param handOverDto the hand over dto
	 * 
	 * @return the int
	 * 
	 * @author dp-duyi
	 * 
	 * @date 2012-11-7 下午1:50:09
	 * 
	 * @see
	 */
	private int insertTruckTask(TruckTaskHandOverDto handOverDto){
		TruckTaskEntity truckTask = new TruckTaskEntity();
		truckTask.setId(handOverDto.getTruckTaskId());
		truckTask.setBusinessType(handOverDto.getHandOverType());
		//营业部交接后，车辆任务状态直接为已到达 2016年11月28日 11:33:51 360903
		if(LoadConstants.HANDOVER_TYPE_SALES_DEPARTMENT.startsWith(handOverDto.getHandOverType())){
			truckTask.setState(TaskTruckConstant.TASK_TRUCK_STATE_ARRIVED);
		}else{
			truckTask.setState(TaskTruckConstant.TASK_TRUCK_STATE_UNDEPART);
		}
		truckTask.setDriverCode1(handOverDto.getDriverCode1());
		truckTask.setDriverCode2(handOverDto.getDriverCode2());
		truckTask.setDriverName1(handOverDto.getDriverName1());
		truckTask.setDriverName2(handOverDto.getDriverName2());
		truckTask.setDriverPhone1(handOverDto.getDriverPhone1());
		truckTask.setDriverPhone2(handOverDto.getDriverPhone2());
		truckTask.setOrigOrgCode(handOverDto.getOrigOrgCode());
		if(StringUtils.isNotBlank(handOverDto.getDeptPlanDetailId())){
			truckTask.setLineName(handOverDto.getLineName());
			truckTask.setLineVirtualCode(handOverDto.getLineVirtualCode());
		}else{
			//若发车计划为空， 调用综合接口查询线路等
			Date baseDate = new Date();
			DepartureStandardDto departureStandard = lineService.queryDepartureStandardListBySourceTargetDirectly(handOverDto.getOrigOrgCode(), handOverDto.getDestOrgCode(), baseDate);
			if(departureStandard != null){
				if(departureStandard.getOrder() != null){
					truckTask.setLineVirtualCode(departureStandard.getOrder().toString());
				}
				truckTask.setLineName(departureStandard.getLineName());
			}else{
				// 调用快递接口查询线路
				DepartureStandardDto departureStandard2 = expresslineService .queryDepartureStandardListBySourceTargetDirectly(handOverDto.getOrigOrgCode(), handOverDto.getDestOrgCode(), baseDate);
				if(departureStandard2!=null){
					if(departureStandard2.getOrder() != null){
						truckTask.setLineVirtualCode(departureStandard2.getOrder().toString());
					}
					truckTask.setLineName(departureStandard2.getLineName());
				}
				
			}
		}
		truckTask.setVehicleNo(handOverDto.getVehicleNo());
		truckTask.setCreateTime(new Date());
		truckTask.setBeCarLoad(handOverDto.getBeCarLoad());
		return truckTaskDao.insertTruckTask(truckTask);
	}
	
	/**
	 * 新增任务车辆明细.
	 * 
	 * 
	 *
	 * @param handOverDto the hand over dto
	 * 
	 * @return the int,如果已出发则返回1，未出发，返回0
	 * 
	 * @author dp-duyi
	 * 
	 * @date 2012-11-7 下午1:50:09
	 * 
	 * @see
	 */
	private int insertTruckTaskDetail(TruckTaskHandOverDto handOverDto){
		int beDepart = 0;
		TruckTaskDetailEntity truckTaskDetail = new TruckTaskDetailEntity();
		truckTaskDetail.setCreateTime(new Date());
		//营业部交接后，车辆任务明细状态直接为已到达 2016年11月28日 11:33:51 360903
		if(LoadConstants.HANDOVER_TYPE_SALES_DEPARTMENT.startsWith(handOverDto.getHandOverType())){
			truckTaskDetail.setState(TaskTruckConstant.TASK_TRUCK_STATE_ARRIVED);
			//当前时间
			Date date = new Date();
			//出发时间
			truckTaskDetail.setActualDepartTime(date);
			//到达时间默认加半小时
			//long arriveTime = date.getTime()+(1800*1000);
			//Calendar c = Calendar.getInstance();
			//c.setTimeInMillis(arriveTime);
			//truckTaskDetail.setActualArriveTime(c.getTime());
			long arriveTime = date.getTime()+(ConstantsNumberSonar.SONAR_NUMBER_1800*ConstantsNumberSonar.SONAR_NUMBER_1000);
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(arriveTime);
			truckTaskDetail.setActualArriveTime(c.getTime());
			
			truckTaskDetail.setTruckDepartId(handOverDto.getHandOverBillNo());
		}else{
			//未出发-UNDEPART
			truckTaskDetail.setState(TaskTruckConstant.TASK_TRUCK_STATE_UNDEPART);
		}
		//车辆任务明细id
		truckTaskDetail.setId(handOverDto.getTruckTaskDettailId());
		truckTaskDetail.setBusinessType(handOverDto.getHandOverType());
		truckTaskDetail.setDestOrgCode(handOverDto.getDestOrgCode());
		truckTaskDetail.setDestOrgName(handOverDto.getDestOrgName());
		truckTaskDetail.setOrigOrgCode(handOverDto.getOrigOrgCode());
		truckTaskDetail.setOrigOrgName(handOverDto.getOrigOrgName());
		truckTaskDetail.setBeCarLoad(handOverDto.getBeCarLoad());
		truckTaskDetail.setParentId(handOverDto.getTruckTaskId());
		truckTaskDetail.setLoaderCode(handOverDto.getLoaderCode());
		truckTaskDetail.setLoaderName(handOverDto.getLoaderName());
		//如果发车计划不为空则线路、计划出发时间、计划到达时间、线路为发车计划中相应值
		if(StringUtils.isNotBlank(handOverDto.getDeptPlanDetailId())){
			truckTaskDetail.setPlanArriveTime(handOverDto.getPlanArriveTime());
			truckTaskDetail.setPlanDepartTime(handOverDto.getPlanDepartTime());
			setRuningTime(handOverDto, truckTaskDetail);
			truckTaskDetail.setLineVirtualCode(handOverDto.getLineVirtualCode());
			truckTaskDetail.setLineName(handOverDto.getLineName());
			truckTaskDetail.setFrequecyNo(handOverDto.getFrequecyNo());
		}else{
			//若发车计划为空， 调用综合接口查询线路等
			Date baseDate = new Date();
			DepartureStandardDto departureStandard = lineService.queryDepartureStandardListBySourceTargetDirectly(handOverDto.getOrigOrgCode(), handOverDto.getDestOrgCode(), baseDate);
			if(departureStandard != null){
				if(departureStandard.getOrder() != null){
					truckTaskDetail.setFrequecyNo(departureStandard.getOrder().toString());
					truckTaskDetail.setPlanArriveTime(departureStandard.getArriveDate(baseDate));
					truckTaskDetail.setPlanDepartTime(departureStandard.getLeaveDate(baseDate));
					
					setRuningTime(handOverDto, truckTaskDetail);
				}else{
					long commonAging = 0;
					if(departureStandard.getCommonAging() != null){
						commonAging = departureStandard.getCommonAging()*ConstantsNumberSonar.SONAR_NUMBER_60*ConstantsNumberSonar.SONAR_NUMBER_60;
					}
					Date planArriveTime = new Date(baseDate.getTime() + commonAging);
					truckTaskDetail.setPlanArriveTime(planArriveTime);
					truckTaskDetail.setPlanDepartTime(baseDate);
					
					setRuningTime(handOverDto, truckTaskDetail);
				}
				truckTaskDetail.setLineName(departureStandard.getLineName());
				// 班次虚拟编码
				truckTaskDetail.setLineVirtualCode(departureStandard.getLineVirtualCode());
			}else{
				DepartureStandardDto departureStandard2 = expresslineService.queryDepartureStandardListBySourceTargetDirectly(handOverDto.getOrigOrgCode(), handOverDto.getDestOrgCode(), baseDate);
				if(departureStandard2 != null){
					if(departureStandard2.getOrder() != null){
						truckTaskDetail.setFrequecyNo(departureStandard2.getOrder().toString());
						truckTaskDetail.setPlanArriveTime(departureStandard2.getArriveDate(baseDate));
						truckTaskDetail.setPlanDepartTime(departureStandard2.getLeaveDate(baseDate));
						
						setRuningTime(handOverDto, truckTaskDetail);
					}else{
						long commonAging = 0;
						if(departureStandard2.getCommonAging() != null){
							commonAging = departureStandard2.getCommonAging()*ConstantsNumberSonar.SONAR_NUMBER_60*ConstantsNumberSonar.SONAR_NUMBER_60;
						}
						Date planArriveTime = new Date(baseDate.getTime() + commonAging);
						truckTaskDetail.setPlanArriveTime(planArriveTime);
						truckTaskDetail.setPlanDepartTime(baseDate);
						
						setRuningTime(handOverDto, truckTaskDetail);
					}
					truckTaskDetail.setLineName(departureStandard2.getLineName());
					// 班次虚拟编码
					truckTaskDetail.setLineVirtualCode(departureStandard2.getLineVirtualCode());
				}
			}
		}
		truckTaskDetail.setVehicleNo(handOverDto.getVehicleNo());
		//如果是挂牌号则该任务要填充挂牌号字段
		if(StringUtils.equals(handOverDto.getBeTrailerVehicleNo(), FossConstants.YES)){
			truckTaskDetail.setTrailerVehicleNo(handOverDto.getTrailerVehicleNo());
		}
		//调用综合接口查询车型、GPS设备号、车辆所属部门编码、车辆所属部门名称
		VehicleAssociationDto vehicleDto = truckTaskService.getVehicle(handOverDto.getVehicleNo());
			if(vehicleDto != null){
				truckTaskDetail.setTruckType(vehicleDto.getVehicleLengthName());
				truckTaskDetail.setVehicleOrgCode(vehicleDto.getVehicleMotorcadeCode());
				truckTaskDetail.setVehicleOrgName(vehicleDto.getVehicleMotorcadeName());
				truckTaskDetail.setVehicleOwnerType(vehicleDto.getVehicleOwnershipType());
			}
		
		//封签、放行
		List<TruckTaskHandOverDto> truckSeals = truckTaskDao.queryVehicleSeal(truckTaskDetail);
		if(CollectionUtils.isNotEmpty(truckSeals)){
			TruckTaskHandOverDto truckSeal = truckSeals.get(0);
			truckTaskDetail.setTruckDepartId(truckSeal.getTruckDepartId());
			//若已封封签，则绑定封签
			if(StringUtils.isNotBlank(truckSeal.getSealId())){
				truckTaskDetail.setVehicleSealId(truckSeal.getSealId());
				//更新封签
				truckTaskDao.updateVehicleSeal(truckTaskDetail);
			}
			//若已放行，则绑定放行记录
			if(StringUtils.isNotBlank(truckSeal.getTruckDepartId())){
				truckTaskDetail.setTruckDepartId(truckSeal.getTruckDepartId());
				//如果出发时间不为空
				if(truckSeal.getActualDepartTime() != null){
					truckTaskDetail.setState(TaskTruckConstant.TASK_TRUCK_STATE_ONTHEWAY);
					truckTaskDetail.setActualDepartTime(truckSeal.getActualDepartTime());
					truckTaskDetail.setActualDepartType(truckSeal.getActualDepartType());
					//修改任务车辆状态为在途
					TruckTaskEntity truckTask = new TruckTaskEntity();
					truckTask.setId(handOverDto.getTruckTaskId());
					truckTask.setState(TaskTruckConstant.TASK_TRUCK_STATE_ONTHEWAY);
					//若有放行ID，则将任务车辆状态更新为在途
					truckTaskDao.updateTruckTaskState(truckTask);
					beDepart = 1;
				}
			}
		}
		truckTaskDao.insertTruckTaskDetail(truckTaskDetail);
		//GPS设备号
		if(vehicleDto != null){
				//TODO truckTaskDetail.getBusinessType();
			if(StringUtils.equals(truckTaskDetail.getBusinessType(),TaskTruckConstant.BUSINESS_TYPE_SHORT_DISTANCE)) {
				TruckGPSTaskEntity truckGPSTask = new TruckGPSTaskEntity();
				truckGPSTask.setId(UUIDUtils.getUUID());
				truckGPSTask.setGpsDeviceNo(vehicleDto.getVehicleGpsNo());
				truckGPSTask.setDestOrgCode(handOverDto.getDestOrgCode());
				truckGPSTask.setOrigOrgCode(handOverDto.getOrigOrgCode());
				truckGPSTask.setTruckTaskDetailId(handOverDto.getTruckTaskDettailId());
				truckGPSTask.setVehicleNo(handOverDto.getVehicleNo());
				truckGPSTask.setOperateType(TaskTruckConstant.GPS_OPERATE_TYPE_NEW);
				truckGPSTask.setBeSuccess(FossConstants.NO);
				truckTaskDao.insertTruckGPSTask(truckGPSTask);
			}  else if(StringUtils.equals(truckTaskDetail.getBusinessType(),TaskTruckConstant.BUSINESS_TYPE_LONG_DISTANCE)) {
				if(vehicleDto.isHasGps()){
					TruckGPSTaskEntity truckGPSTask = new TruckGPSTaskEntity();
					truckGPSTask.setId(UUIDUtils.getUUID());
					truckGPSTask.setGpsDeviceNo(vehicleDto.getVehicleGpsNo());
					truckGPSTask.setDestOrgCode(handOverDto.getDestOrgCode());
					truckGPSTask.setOrigOrgCode(handOverDto.getOrigOrgCode());
					truckGPSTask.setTruckTaskDetailId(handOverDto.getTruckTaskDettailId());
					truckGPSTask.setVehicleNo(handOverDto.getVehicleNo());
					truckGPSTask.setOperateType(TaskTruckConstant.GPS_OPERATE_TYPE_NEW);
					truckGPSTask.setBeSuccess(FossConstants.NO);
					truckTaskDao.insertTruckGPSTask(truckGPSTask);
					//调用gps接口，同步跟踪车辆数据,同步成功，则更新待跟踪gps信息为成功，否则更新待跟踪gps信息为失败
					//this.synAddTruckGPSTask(truckGPSTask, handOverDto);
					}
			}
		}
		//更新证件包
		truckTaskDao.updateCertificateBag(truckTaskDetail);
		return beDepart;
	}
	
	
	public void setRuningTime(TruckTaskHandOverDto handOverDto, TruckTaskDetailEntity truckTaskDetail) {
		Date pdt = truckTaskDetail.getPlanDepartTime();
		if(pdt == null) {
			pdt = new Date();
		}
		Date pat = truckTaskDetail.getPlanArriveTime();
		if(pat == null) {
			pat = new Date();
		}
		Long runingTimes = DateUtils.getMinuteDiff(pdt, pat);
		truckTaskDetail.setRuningTimes(runingTimes.intValue());
	}
	
	public void setRuningTimeBuByVehiclEmptyBillDto(VehiclEmptyBillDto vehiclEmDto, TruckTaskDetailEntity truckTaskDetail) {
		Date pdt = truckTaskDetail.getPlanDepartTime();
		if(pdt == null) {
			pdt = new Date();
		}
		Date pat = truckTaskDetail.getPlanArriveTime();
		if(pat == null) {
			pat = new Date();
		}
		Long runingTimes = DateUtils.getMinuteDiff(pdt, pat);
		truckTaskDetail.setRuningTimes(runingTimes.intValue());
	}
	
	/**
	 * 调用gps接口，同步跟踪车辆数据,同步成功，则更新待跟踪gps信息为成功，否则更新待跟踪gps信息为失败.
	 *
	 *
	 * @param truckGPSTask the truck gps task
	 * 
	 * @param handOverDto the hand over dto
	 * 
	 * @author dp-duyi
	 * 
	 * @date 2012-11-7 下午1:50:09
	 * 
	 * @see
	 */
	/*private void synAddTruckGPSTask(TruckGPSTaskEntity truckGPSTask,TruckTaskHandOverDto handOverDto){
		try{
			TaskVehicleDto taskVehicleDto = new TaskVehicleDto();
			taskVehicleDto.setVehicleId(truckGPSTask.getId());
			taskVehicleDto.setArrivalDept(truckGPSTask.getDestOrgCode());
			taskVehicleDto.setCubage(handOverDto.getVolumeTotal());
			taskVehicleDto.setIsDeleted(TaskTruckConstant.GPS_OPERATE_TYPE_NEW);
			taskVehicleDto.setStartDept(truckGPSTask.getDestOrgCode());
			if(handOverDto.getPlanDepartTime()!= null){
				SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				taskVehicleDto.setStartTime(df.format(handOverDto.getPlanDepartTime()));
			}
			taskVehicleDto.setVehicleNo(truckGPSTask.getVehicleNo());
			taskVehicleDto.setWeight(handOverDto.getWeightTotal());
			boolean beSuccess = fossToGPSService.notifyTaskVehicleInfo(taskVehicleDto);
			if(beSuccess){
				truckGPSTask.setBeSuccess(FossConstants.YES);
				//更新待跟踪车辆信息：是否成功
				truckTaskDao.updateTruckGPSTask(truckGPSTask);
			}
		}catch(Exception e){
			//日志
			LOGGER.error(truckGPSTask.getVehicleNo()+"同步跟踪车辆数据失败", e);
		}
	}*/
	/**
	 * 插入任务车辆单据明细.
	 *
	 *
	 * @param handOverDto the hand over dto
	 * 
	 * @param billType the bill type
	 * 
	 * @return the int
	 * 
	 * @author dp-duyi
	 * 
	 * @date 2012-11-7 下午1:50:09
	 * 
	 * @see
	 */
	private int insertTruckTaskBill(TruckTaskHandOverDto handOverDto,String billType){
		TruckTaskBillEntity truckTaskBill = new TruckTaskBillEntity();
		truckTaskBill.setId(UUIDUtils.getUUID());
		truckTaskBill.setBillNo(handOverDto.getHandOverBillNo());
		truckTaskBill.setAssignState(TaskTruckConstant.BILL_ASSIGN_STATE_UNASSIGN);
		HandOverBillEntity handOverBillEntity = handOverBillService.queryHandOverBillByNo(handOverDto.getHandOverBillNo());
		if(handOverBillEntity != null && LoadConstants.HANDOVER_TYPE_LONG_DISTANCE.equals(handOverBillEntity.getHandOverType())){
			//长途交接单交接单状态为已交接的时候单据级别为1，否则为0
			if(LoadConstants.HANDOVERBILL_STATE_FORMAL_HANDOVER == handOverBillEntity.getHandOverBillState()){
				truckTaskBill.setBillLevel(TaskTruckConstant.BILL_LEVEL_VALID);
			}else{
				truckTaskBill.setBillLevel(TaskTruckConstant.BILL_LEVEL_UNVALID);
			}
		}else{
			truckTaskBill.setBillLevel(TaskTruckConstant.BILL_LEVEL_VALID);
		}
		truckTaskBill.setBillType(billType);
		truckTaskBill.setParentId(handOverDto.getTruckTaskDettailId());
		truckTaskBill.setBillingTime(handOverDto.getBillingTime());
		truckTaskBill.setLoadTaskNo(handOverDto.getLoadTaskNo());
		truckTaskBill.setCreateTime(new Date());
		/*if(TaskTruckConstant.BILL_TYPE_VEHICLEASSEMBLE.equals(billType)){
			
		}*/
		return truckTaskDao.insertTruckTaskBill(truckTaskBill);
	}
	/**
	 * 根据交接单新增任务车辆.
	 * 
	 *
	 * @param handOverDto the hand over dto
	 * 
	 * @return the int
	 * 
	 * @author dp-duyi
	 * 
	 * @date 2012-11-7 下午1:50:09
	 * 
	 * @see
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public int createTruckTask(TruckTaskHandOverDto handOverDto){
		LOGGER.error("交接单生成任务车辆开始"+handOverDto.getHandOverBillNo());
		String truckTaskId = truckTaskDao.queryBeCreateTruckTask(handOverDto);
		//如果是挂牌号则设置挂牌号
		if(StringUtils.equals(handOverDto.getBeTrailerVehicleNo(),FossConstants.YES)){
			handOverDto.setTrailerVehicleNo(handOverDto.getVehicleNo());
		}
		//查询最新生成的单据明细是否大于7天,先去掉啊，加上出问题啊，又不知道问题在哪啊，先去掉试试啊，纠结
		/*if(StringUtils.isNotBlank(truckTaskId)){
			String taskBillId = truckTaskDao.queryLastedCreateTruckTaskBill(truckTaskId, null, null);
			if(StringUtils.isBlank(taskBillId)){
				truckTaskId = null;
			}
		}*/
		int beDepart = 0;
		//不存在任务车辆
		if(StringUtils.isBlank(truckTaskId)){
			handOverDto.setTruckTaskId(UUIDUtils.getUUID());
			LOGGER.error("生成任务车辆主表开始"+handOverDto.getHandOverBillNo());
			this.insertTruckTask(handOverDto);
			LOGGER.error("生成任务车辆主表结束"+handOverDto.getHandOverBillNo());
			
			handOverDto.setTruckTaskDettailId(UUIDUtils.getUUID());
			LOGGER.error("生成任务车辆明细开始"+handOverDto.getHandOverBillNo());
			beDepart = this.insertTruckTaskDetail(handOverDto);
			LOGGER.error("生成任务车辆明细结束"+handOverDto.getHandOverBillNo());
			//存在任务车辆
		}else{
			handOverDto.setTruckTaskId(truckTaskId);
			TruckTaskDetailEntity truckTaskDetailEntity = truckTaskDao.queryBeCreateTruckTaskDetail(handOverDto);
			if(truckTaskDetailEntity == null || StringUtils.isBlank(truckTaskDetailEntity.getId())){//不存在任务车辆明细
				handOverDto.setTruckTaskDettailId(UUIDUtils.getUUID());
				LOGGER.error("生成任务车辆明细开始"+handOverDto.getHandOverBillNo());
				beDepart = this.insertTruckTaskDetail(handOverDto);
				LOGGER.error("生成任务车辆明细结束"+handOverDto.getHandOverBillNo());
			}else{
				handOverDto.setTruckTaskDettailId(truckTaskDetailEntity.getId());
				//如果该存在的任务是整车，但是新增的任务不是整车则提示
				if(StringUtils.equals(truckTaskDetailEntity.getBeCarLoad(),FossConstants.YES) && StringUtils.equals(handOverDto.getBeCarLoad(),FossConstants.NO) ){					
					LOGGER.error("该车辆已做了整车不能再做非整车交接"+handOverDto.getHandOverBillNo());
					throw new TfrBusinessException("该车辆已做了整车不能再做非整车交接");
				}else if(StringUtils.equals(truckTaskDetailEntity.getBeCarLoad(),FossConstants.NO) && StringUtils.equals(handOverDto.getBeCarLoad(),FossConstants.YES)){
					LOGGER.error("该车辆已做了非整车不能再做整车交接"+handOverDto.getHandOverBillNo());
					throw new TfrBusinessException("该车辆已做了非整车不能再做整车交接");
				}
				if(truckTaskDetailEntity.getState().equals(TaskTruckConstant.TASK_TRUCK_STATE_ONTHEWAY)){
					beDepart = 1;
				}
			}
		}
		
		LOGGER.error("生成任务车辆单据开始"+handOverDto.getHandOverBillNo());
		this.insertTruckTaskBill(handOverDto,TaskTruckConstant.BILL_TYPE_HANDOVER);
		LOGGER.error("生成任务车辆单据开始"+handOverDto.getHandOverBillNo());
		
		Map<String,String> handOverBill = new HashMap<String,String>();
		//交接单号
		handOverBill.put("billNo", handOverDto.getHandOverBillNo());
		//新增车辆任务
		handOverBill.put("beCreateTruckTask", FossConstants.YES);
		//更新交接单是否生成任务车辆状态
		//若已出发，则更新交接单状态
		if(beDepart == 1){
			//handOverBill.put("HANDOVERBILL_STATE", "30");
		}
		int updateCount = truckTaskDao.updateHandOverBillState(handOverBill);
		if(updateCount == 1){
			LOGGER.error("交接单生成任务车辆结束"+handOverDto.getHandOverBillNo());
			return updateCount;
		}else{
			LOGGER.error("交接单生成任务车辆异常更新交接单件数不为一"+handOverDto.getHandOverBillNo());
			throw new TfrBusinessException("无效交接单");
		} 
	}
	
	///////////////////根据空驶单生成车辆任务////////////////////////////
	/**
	 * 根据交接单新增任务车辆.根据空驶单生成车辆任务
	 *
	 * @param VehiclEmptyBillDto vehiclEmDto
	 * 
	 * @return int
	 * 
	 * @author zhangpeng
	 * 
	 * @date 2015-10-23 下午1:50:09
	 * 
	 * @see
	 */
	@SuppressWarnings("unused")
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public int createTruckTaskByVehiclEmDto(VehiclEmptyBillDto vehiclEmDto){
		int beDepart = 0;
		int beDepart1=0;
		int beDepart2=0;
		int updateCount=0;
		vehiclEmDto.setTruckTaskId(UUIDUtils.getUUID());
		vehiclEmDto.setBusinessType("VEHICLE_EMPTY");
		LOGGER.error("生成任务车辆主表开始"+vehiclEmDto.getVehiclEmptyBillNo());
		beDepart1=this.insertTruckTaskByVehiclEmDto(vehiclEmDto);
		LOGGER.error("生成任务车辆主表结束"+vehiclEmDto.getVehiclEmptyBillNo());
		vehiclEmDto.setTruckTaskDettailId(UUIDUtils.getUUID());
		LOGGER.error("生成任务车辆明细开始"+vehiclEmDto.getVehiclEmptyBillNo());
		beDepart = this.insertTruckTaskDetailVehiclEmptyBillDto(vehiclEmDto);
		LOGGER.error("生成任务车辆明细结束"+vehiclEmDto.getVehiclEmptyBillNo());
		LOGGER.error("生成任务车辆单据开始"+vehiclEmDto.getVehiclEmptyBillNo());
		beDepart2=this.insertTruckTaskBillByVehiclEmDto(vehiclEmDto,TaskTruckConstant.BILL_TYPE_VEHICLEMPBILL);
		LOGGER.error("生成任务车辆单据结束"+vehiclEmDto.getVehiclEmptyBillNo());
		if(beDepart!=0 && beDepart1!=0 && beDepart2!=0){
			return updateCount=1;
	   }else{
			LOGGER.error("空驶单生成任务车辆异常"+vehiclEmDto.getVehiclEmptyBillNo());
			throw new TfrBusinessException("空驶单生成失败");
		    } 
		}
	///////////////////////根据空驶单插入车辆任务表数据/////////////////////////////////////////////////////////////////////////////
	/**
	 * 新增任务车辆任务.
	 * 
	 * 
	 *
	 * @param VehiclEmptyBillDto vehiclEmDto
	 * 
	 * @return the int,如果已出发则返回1，未出发，返回0
	 * 
	 * @author dp-duyi
	 * 
	 * @date 2012-11-7 下午1:50:09
	 * 
	 * @see
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	private int insertTruckTaskByVehiclEmDto( VehiclEmptyBillDto vehiclEmDto){
		TruckTaskEntity truckTask = new TruckTaskEntity();
		truckTask.setId(vehiclEmDto.getTruckTaskId());
		truckTask.setVehicleNo(vehiclEmDto.getVehicleNo());
		truckTask.setBusinessType(vehiclEmDto.getBusinessType());
		truckTask.setState(TaskTruckConstant.TASK_TRUCK_STATE_UNDEPART);
		truckTask.setDriverCode1(vehiclEmDto.getDriverCode());
		truckTask.setDriverName1(vehiclEmDto.getDriverName());
		truckTask.setDriverPhone1(vehiclEmDto.getDriverTel());
	    truckTask.setLineName(vehiclEmDto.getLineName());
		truckTask.setLineVirtualCode(vehiclEmDto.getLineVirtualCode());
		truckTask.setCreateTime(new Date());
		truckTask.setOrigOrgCode(vehiclEmDto.getOrigOrgCode());
		return truckTaskDao.insertTruckTask(truckTask);
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
   /**
	 * 新增任务车辆明细.
	 * 
	 * 
	 *
	 * @param handOverDto the hand over dto
	 * 
	 * @return the int,如果已出发则返回1，未出发，返回0
	 * 
	 * @author dp-duyi
	 * 
	 * @date 2012-11-7 下午1:50:09
	 * 
	 * @see
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	private int insertTruckTaskDetailVehiclEmptyBillDto(VehiclEmptyBillDto vehiclEmDto){
		TruckTaskDetailEntity truckTaskDetail = new TruckTaskDetailEntity();
		truckTaskDetail.setCreateTime(new Date());
		//未出发-UNDEPART
		truckTaskDetail.setState(TaskTruckConstant.TASK_TRUCK_STATE_UNDEPART);
		//车辆任务明细id
		truckTaskDetail.setId(vehiclEmDto.getTruckTaskDettailId());
		truckTaskDetail.setBusinessType(vehiclEmDto.getBusinessType());
		truckTaskDetail.setDestOrgCode(vehiclEmDto.getDestOrgCode());
		truckTaskDetail.setDestOrgName(vehiclEmDto.getDestOrgName());
		truckTaskDetail.setOrigOrgCode(vehiclEmDto.getOrigOrgCode());
		truckTaskDetail.setOrigOrgName(vehiclEmDto.getOrigOrgName());
		truckTaskDetail.setParentId(vehiclEmDto.getTruckTaskId());
		truckTaskDetail.setCreater(vehiclEmDto.getCreaterUserName());
		//如果发车计划不为空则线路、计划出发时间、计划到达时间、线路为发车计划中相应值
		/*if(StringUtils.isNotBlank(vehiclEmDto.getDeptPlanDetailId())){
			truckTaskDetail.setPlanArriveTime(vehiclEmDto.getPlanArriveTime());
			truckTaskDetail.setPlanDepartTime(vehiclEmDto.getPlanDepartTime());
			setRuningTime(vehiclEmDto, truckTaskDetail);
			truckTaskDetail.setLineVirtualCode(vehiclEmDto.getLineVirtualCode());
			truckTaskDetail.setLineName(vehiclEmDto.getLineName());
			truckTaskDetail.setFrequecyNo(handOverDto.getFrequecyNo());
		}
		else{*/
		   //若发车计划为空， 调用综合接口查询线路等
			Date baseDate = new Date();
			DepartureStandardDto departureStandard = lineService.queryDepartureStandardListBySourceTargetDirectly(vehiclEmDto.getOrigOrgCode(), vehiclEmDto.getDestOrgCode(), baseDate);
			if(departureStandard != null){
				if(departureStandard.getOrder() != null){
					truckTaskDetail.setFrequecyNo(departureStandard.getOrder().toString());
					truckTaskDetail.setPlanArriveTime(departureStandard.getArriveDate(baseDate));
					truckTaskDetail.setPlanDepartTime(departureStandard.getLeaveDate(baseDate));
					
					setRuningTimeBuByVehiclEmptyBillDto(vehiclEmDto, truckTaskDetail);
				}else{
					long commonAging = 0;
					if(departureStandard.getCommonAging() != null){
						commonAging = departureStandard.getCommonAging()*ConstantsNumberSonar.SONAR_NUMBER_60*ConstantsNumberSonar.SONAR_NUMBER_60;
					}
					Date planArriveTime = new Date(baseDate.getTime() + commonAging);
					truckTaskDetail.setPlanArriveTime(planArriveTime);
					truckTaskDetail.setPlanDepartTime(baseDate);
					
					setRuningTimeBuByVehiclEmptyBillDto(vehiclEmDto, truckTaskDetail);
				}
				truckTaskDetail.setLineName(departureStandard.getLineName());
				// 班次虚拟编码
				truckTaskDetail.setLineVirtualCode(departureStandard.getLineVirtualCode());
			}else{
				DepartureStandardDto departureStandard2 = expresslineService.queryDepartureStandardListBySourceTargetDirectly(vehiclEmDto.getOrigOrgCode(), vehiclEmDto.getDestOrgCode(), baseDate);
				if(departureStandard2 != null){
					if(departureStandard2.getOrder() != null){
						truckTaskDetail.setFrequecyNo(departureStandard2.getOrder().toString());
						truckTaskDetail.setPlanArriveTime(departureStandard2.getArriveDate(baseDate));
						truckTaskDetail.setPlanDepartTime(departureStandard2.getLeaveDate(baseDate));
						
						setRuningTimeBuByVehiclEmptyBillDto(vehiclEmDto, truckTaskDetail);
					}else{
						long commonAging = 0;
						if(departureStandard2.getCommonAging() != null){
							commonAging = departureStandard2.getCommonAging()*ConstantsNumberSonar.SONAR_NUMBER_60*ConstantsNumberSonar.SONAR_NUMBER_60;
						}
						Date planArriveTime = new Date(baseDate.getTime() + commonAging);
						truckTaskDetail.setPlanArriveTime(planArriveTime);
						truckTaskDetail.setPlanDepartTime(baseDate);
						
						setRuningTimeBuByVehiclEmptyBillDto(vehiclEmDto, truckTaskDetail);
					}
					truckTaskDetail.setLineName(departureStandard2.getLineName());
					// 班次虚拟编码
					truckTaskDetail.setLineVirtualCode(departureStandard2.getLineVirtualCode());
				}
			}
		
		truckTaskDetail.setVehicleNo(vehiclEmDto.getVehicleNo());
		//如果是挂牌号则该任务要填充挂牌号字段
	/*	if(StringUtils.equals(vehiclEmDto.getBeTrailerVehicleNo(), FossConstants.YES)){
			truckTaskDetail.setTrailerVehicleNo(vehiclEmDto.getTrailerVehicleNo());
		}*/
		//调用综合接口查询车型、GPS设备号、车辆所属部门编码、车辆所属部门名称
		VehicleAssociationDto vehicleDto = truckTaskService.getVehicle(vehiclEmDto.getVehicleNo());
			if(vehicleDto != null){
				truckTaskDetail.setTruckType(vehicleDto.getVehicleLengthName());
				truckTaskDetail.setVehicleOrgCode(vehicleDto.getVehicleMotorcadeCode());
				truckTaskDetail.setVehicleOrgName(vehicleDto.getVehicleMotorcadeName());
				truckTaskDetail.setVehicleOwnerType(vehicleDto.getVehicleOwnershipType());
			}
		
		//封签、放行
		List<TruckTaskHandOverDto> truckSeals = truckTaskDao.queryVehicleSeal(truckTaskDetail);
		if(CollectionUtils.isNotEmpty(truckSeals)){
			TruckTaskHandOverDto truckSeal = truckSeals.get(0);
			truckTaskDetail.setTruckDepartId(truckSeal.getTruckDepartId());
			//若已封封签，则绑定封签
			if(StringUtils.isNotBlank(truckSeal.getSealId())){
				truckTaskDetail.setVehicleSealId(truckSeal.getSealId());
				//更新封签
				truckTaskDao.updateVehicleSeal(truckTaskDetail);
			}
			//若已放行，则绑定放行记录
			if(StringUtils.isNotBlank(truckSeal.getTruckDepartId())){
				truckTaskDetail.setTruckDepartId(truckSeal.getTruckDepartId());
				//如果出发时间不为空
				if(truckSeal.getActualDepartTime() != null){
					truckTaskDetail.setState(TaskTruckConstant.TASK_TRUCK_STATE_ONTHEWAY);
					truckTaskDetail.setActualDepartTime(truckSeal.getActualDepartTime());
					truckTaskDetail.setActualDepartType(truckSeal.getActualDepartType());
					//修改任务车辆状态为在途
					TruckTaskEntity truckTask = new TruckTaskEntity();
					truckTask.setId(vehiclEmDto.getTruckTaskId());
					truckTask.setState(TaskTruckConstant.TASK_TRUCK_STATE_ONTHEWAY);
					//若有放行ID，则将任务车辆状态更新为在途
					truckTaskDao.updateTruckTaskState(truckTask);
				}
			}
		}
		int  a=truckTaskDao.insertTruckTaskDetail(truckTaskDetail);
		//GPS设备号
		if(vehicleDto != null){
				//TODO truckTaskDetail.getBusinessType();
			if(StringUtils.equals(truckTaskDetail.getBusinessType(),TaskTruckConstant.BUSINESS_TYPE_SHORT_DISTANCE)) {
				TruckGPSTaskEntity truckGPSTask = new TruckGPSTaskEntity();
				truckGPSTask.setId(UUIDUtils.getUUID());
				truckGPSTask.setGpsDeviceNo(vehicleDto.getVehicleGpsNo());
				truckGPSTask.setDestOrgCode(vehiclEmDto.getDestOrgCode());
				truckGPSTask.setOrigOrgCode(vehiclEmDto.getOrigOrgCode());
				truckGPSTask.setTruckTaskDetailId(vehiclEmDto.getTruckTaskDettailId());
				truckGPSTask.setVehicleNo(vehiclEmDto.getVehicleNo());
				truckGPSTask.setOperateType(TaskTruckConstant.GPS_OPERATE_TYPE_NEW);
				truckGPSTask.setBeSuccess(FossConstants.NO);
				truckTaskDao.insertTruckGPSTask(truckGPSTask);
			}  else if(StringUtils.equals(truckTaskDetail.getBusinessType(),TaskTruckConstant.BUSINESS_TYPE_LONG_DISTANCE)) {
				if(vehicleDto.isHasGps()){
					TruckGPSTaskEntity truckGPSTask = new TruckGPSTaskEntity();
					truckGPSTask.setId(UUIDUtils.getUUID());
					truckGPSTask.setGpsDeviceNo(vehicleDto.getVehicleGpsNo());
					truckGPSTask.setDestOrgCode(vehiclEmDto.getDestOrgCode());
					truckGPSTask.setOrigOrgCode(vehiclEmDto.getOrigOrgCode());
					truckGPSTask.setTruckTaskDetailId(vehiclEmDto.getTruckTaskDettailId());
					truckGPSTask.setVehicleNo(vehiclEmDto.getVehicleNo());
					truckGPSTask.setOperateType(TaskTruckConstant.GPS_OPERATE_TYPE_NEW);
					truckGPSTask.setBeSuccess(FossConstants.NO);
					truckTaskDao.insertTruckGPSTask(truckGPSTask);
					//调用gps接口，同步跟踪车辆数据,同步成功，则更新待跟踪gps信息为成功，否则更新待跟踪gps信息为失败
					//this.synAddTruckGPSTask(truckGPSTask, handOverDto);
					}
			}
		}
		//更新证件包
		truckTaskDao.updateCertificateBag(truckTaskDetail);
		return a;
	}
  ///////////////////////根据空驶单插入车辆单据明细//////////////////////////////
	/**
	 * 插入任务车辆单据明细.
	 *
	 *
	 * @param handOverDto the hand over dto
	 * 
	 * @param billType the bill type
	 * 
	 * @return the int
	 * 
	 * @author dp-duyi
	 * 
	 * @date 2012-11-7 下午1:50:09
	 * 
	 * @see
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	private int insertTruckTaskBillByVehiclEmDto(VehiclEmptyBillDto vehiclEmDto,String billType){
		TruckTaskBillEntity truckTaskBill = new TruckTaskBillEntity();
		truckTaskBill.setId(UUIDUtils.getUUID());
		truckTaskBill.setBillNo(vehiclEmDto.getVehiclEmptyBillNo());
		//单据分配状态
	    //truckTaskBill.setAssignState(TaskTruckConstant.BILL_ASSIGN_STATE_UNASSIGN);
		//VehiclEmptyBillDto vehiclEmptyBillDto = handOverBillService.queryHandOverBillByNo(vehiclEmDto.getVehiclEmptyBillNo());
		truckTaskBill.setBillLevel(TaskTruckConstant.BILL_LEVEL_VALID);
		truckTaskBill.setBillType(billType);
		truckTaskBill.setParentId(vehiclEmDto.getTruckTaskDettailId());
		truckTaskBill.setBillingTime(vehiclEmDto.getCreateTime());
		truckTaskBill.setCreateTime(new Date());
		return truckTaskDao.insertTruckTaskBill(truckTaskBill);
	}
	
	/**
	 * 根据配载单更新任务车辆明细.
	 * 
	 * 
	 *
	 * @param assembleBill the assemble bill
	 * 
	 * @return the int
	 * 
	 * @author dp-duyi
	 * 
	 * @date 2012-11-9 上午8:26:35
	 * 
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskService#updateTruckTaskByAssembleBill(com.deppon.foss.module.transfer.load.api.shared.dto.TruckTaskHandOverDto)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public int updateTruckTaskByAssembleBill(TruckTaskHandOverDto assembleBill) {
		LOGGER.error("配载单生成任务车辆开始"+assembleBill.getHandOverBillNo());
		//如果车辆为外请车，且费用>0则，更新任务车辆中费用配载单单号
		VehicleAssociationDto vehicleDto = truckTaskService.getVehicle(assembleBill.getVehicleNo());
		if(vehicleDto != null){
			//资产归属类型：外请
			if(ComnConst.ASSETS_OWNERSHIP_TYPE_LEASED.equals(vehicleDto.getVehicleOwnershipType())){
				if(assembleBill.getFeeTotal()>0){
					TruckTaskEntity truckTask = new TruckTaskEntity();
					truckTask.setId(assembleBill.getTruckTaskId());
					truckTask.setChargingAssembleNo(assembleBill.getHandOverBillNo());
					//更新任务车辆中费用交接单编号
					truckTaskDao.updateChargingAssembleNo(truckTask);
				}
			}
			//如果车辆状态是已出发，需要把配载单的状态变成已出发
			if(assembleBill.getActualDepartTime()!=null)
			{
				vehicleAssembleBillService.updateVehicleAssembleBillStateByVNo(
						assembleBill.getHandOverBillNo(),
						LoadConstants.VEHICLEASSEMBLEBILL_STATE_ALREADY_DEPART);
			}
		}
		
		//插入任务车辆单据
		this.insertTruckTaskBill(assembleBill, TaskTruckConstant.BILL_TYPE_VEHICLEASSEMBLE);
		
		//将任务车辆中相应交接单单据级别更新为0
		Map<String,String> condition = new HashMap<String,String>();
		condition.put("billNo", assembleBill.getHandOverBillNo());
		condition.put("billLevel", TaskTruckConstant.BILL_LEVEL_UNVALID);
		truckTaskDao.updateTruckTaskBillStateByAssembleBill(condition);
		
		//将配载单的是否生成装车任务至为1
		Map<String,String> assembleBillMap = new HashMap<String,String>();
		assembleBillMap.put("billNo", assembleBill.getHandOverBillNo());
		assembleBillMap.put("beCreateTruckTask", FossConstants.YES);
		//如果车辆已经出发，则更新配载单状态为在途
		if(assembleBill.getActualDepartTime() != null){
			//assembleBillMap.put("state", "20");
		}
		LOGGER.error("配载单生成任务车辆结束"+assembleBill.getHandOverBillNo());
		int updateCount = truckTaskDao.updateAssembleBillState(assembleBillMap);
		if(updateCount == 1){
			return updateCount;
		}else{
			LOGGER.error("配载单生成任务车辆异常更新配载单件数不为一"+assembleBill.getHandOverBillNo());
			throw new TfrBusinessException("无效配载单");
		}
	}
	/**
	 * 根据交接单批量新增任务车辆.
	 *
	 * @param bizJobStartTime the biz job start time
	 * 
	 * @param bizJobEndTime the biz job end time
	 * 
	 * @param threadNo the thread no
	 * 
	 * @param threadCount the thread count
	 * 
	 * @return the int
	 * 
	 * @author dp-duyi
	 * 
	 * @date 2012-11-7 下午2:35:20
	 * 
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskService#batchCreateTruckTask()
	 */
	@Override
	public int batchCreateTruckTaskByHandOverBill(Date bizJobStartTime,	Date bizJobEndTime,int threadNo, int threadCount) {
		List<TruckTaskHandOverDto> handOverDtos = truckTaskDao.queryUnCreateTaskTruckHandOver(bizJobStartTime,bizJobEndTime,threadNo,threadCount,null);
		int insertCount = 0;
		if(CollectionUtils.isNotEmpty(handOverDtos)){
			insertCount = handOverDtos.size();
			LOGGER.error("生成任务车辆交接单数"+insertCount+" bizJobStartTime"+bizJobStartTime+" bizJobEndTime"+bizJobEndTime);
			if(insertCount > 0){
				for(TruckTaskHandOverDto handOverDto : handOverDtos){
					LOGGER.error("生成任务车辆开始"+handOverDto.getHandOverBillNo());
					truckTaskService.createTruckTask(handOverDto);
					LOGGER.error("生成任务车辆结束"+handOverDto.getHandOverBillNo());
					try{
						LOGGER.error("调用交接单监控服务开始"+handOverDto.getHandOverBillNo());
						this.handOverBillCountMonitor(handOverDto);
						LOGGER.error("调用交接单监控服务结束"+handOverDto.getHandOverBillNo());
					}catch(Exception e){
						LOGGER.error("交接单号：" + handOverDto.getHandOverBillNo() + "监控失败异常！",e);
					}
				}
			}
		}
		
		return insertCount;
	}
	private void handOverBillCountMonitor(TruckTaskHandOverDto handOverDto){
		/*应用监控服务*/
		UserEntity user = null;
		OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
		dept.setCode(handOverDto.getOrigOrgCode());
		CurrentInfo currentInfo = new CurrentInfo(user,dept);
		//交接单数量计数器
		BusinessMonitorIndicator handOverBillCounterIndicator = BusinessMonitorIndicator.HANDOVER_BILL_COUNT;
		businessMonitorService.counter(handOverBillCounterIndicator, currentInfo);
		/**
		 * 交接票数计数器
		 */
		Map<BusinessMonitorIndicator, Number> waybillPiecesMap = new HashMap<BusinessMonitorIndicator, Number>();
		BusinessMonitorIndicator waybillPiecesIndicator = BusinessMonitorIndicator.HANDOVER_WAYBILL_COUNT;
		//票数
		Number waybillPieces = handOverDto.getWayBillQtyTotal();
		waybillPiecesMap.put(waybillPiecesIndicator, waybillPieces);
		businessMonitorService.counter(waybillPiecesMap, currentInfo);
		/**
		 * 交接件数计数器
		 */
		Map<BusinessMonitorIndicator, Number> piecesMap = new HashMap<BusinessMonitorIndicator, Number>();
		BusinessMonitorIndicator piecesIndicator = BusinessMonitorIndicator.HANDOVER_LABEL_COUNT;
		//总件数
		Number pieces = handOverDto.getGoodsQtyTotal();
		piecesMap.put(piecesIndicator, pieces);
		businessMonitorService.counter(piecesMap, currentInfo);
	}
	/**
	 * 根据配载单批量更新任务车辆明细.
	 * 
	 * 
	 *
	 * @param bizJobStartTime the biz job start time
	 * 
	 * @param bizJobEndTime the biz job end time
	 * 
	 * @param threadNo the thread no
	 * 
	 * @param threadCount the thread count
	 * 
	 * @return the int
	 * 
	 * @author dp-duyi
	 * 
	 * @date 2012-11-9 上午8:26:35
	 * 
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskService#batchUpdateTruckTaskByAssembleBill()
	 */
	@Override
	public int batchUpdateTruckTaskByAssembleBill(Date bizJobStartTime,	Date bizJobEndTime,int threadNo, int threadCount) {
		List<TruckTaskHandOverDto> assembleBills = truckTaskDao.queryUnCreateTruckTaskAssembleBill(bizJobStartTime,bizJobEndTime,threadNo,threadCount,null);
		if(CollectionUtils.isNotEmpty(assembleBills)){
			LOGGER.error("生成任务车辆交接单数"+assembleBills.size()+" bizJobStartTime"+bizJobStartTime+" bizJobEndTime"+bizJobEndTime);
			for(TruckTaskHandOverDto assmbleBill : assembleBills){
				LOGGER.error("配载单生成任务车辆开始"+assmbleBill.getHandOverBillNo());
				truckTaskService.updateTruckTaskByAssembleBill(assmbleBill);
				LOGGER.error("配载单生成任务车辆结束"+assmbleBill.getHandOverBillNo());
			}
		}
		return assembleBills.size();
	}
	/**
	 * 扫描交接单、配载单，批量生成任务车辆.
	 * 
	 *
	 * @param bizJobStartTime the biz job start time
	 * 
	 * @param bizJobEndTime the biz job end time
	 * 
	 * @param threadNo the thread no
	 * 
	 * @param threadCount the thread count
	 * 
	 * @return the int
	 * 
	 * @author dp-duyi
	 * 
	 * @date 2012-11-9 上午10:30:02
	 * 
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskService#batchCreateTruckTask()
	 */
	@Override
	@Transactional
	public int batchCreateTruckTask(Date bizJobStartTime,Date bizJobEndTime,int threadNo, int threadCount) {
		/*
		 * 1、要先查询出运单明细，然后生成任务车辆，
		 * 
		 * 因为状态标识使用的是一个字段，生成任务车辆的时候把该字段改成Y
		 */
		//根据交接单生成任务车辆
		LOGGER.error("根据交接单生成任务车辆开始");
		truckTaskService.batchCreateTruckTaskByHandOverBill(bizJobStartTime,bizJobEndTime,threadNo,threadCount);
		LOGGER.error("根据交接单生成任务车辆结束");
		
		//根据配载单生成任务车辆
		LOGGER.error("根据配载单生成任务车辆开始");
		truckTaskService.batchUpdateTruckTaskByAssembleBill(bizJobStartTime,bizJobEndTime,threadNo,threadCount);
		LOGGER.error("根据配载单生成任务车辆结束");
		return 1;
	}
	/**
	 * 根据交接单删除任务车辆.
	 *
	 *
	 *
	 * @param billNo the bill no
	 * 
	 * @return the int
	 * 
	 * @author dp-duyi
	 * 
	 * @date 2012-11-9 下午1:55:24
	 * 
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskService#deleteTruckTaskByHandOverBill(java.lang.String)
	 */
	@Override
	@Transactional
	public int deleteTruckTaskByHandOverBill(String billNo) {
		//根据交接单查询任务车辆ID，任务车辆明细ID
		TruckTaskHandOverDto truckTask = truckTaskDao.queryTruckTaskIdByHandOverBill(billNo);
		//任务车辆id不为空
		//任务车辆明细不为空
		if(truckTask != null){
			if(StringUtils.isNotBlank(truckTask.getTruckTaskId()) && StringUtils.isNotBlank(truckTask.getTruckTaskDettailId())){
				//任务车辆id
				String truckTaskId = truckTask.getTruckTaskId();
				//任务车辆明细id
				String truckTaskDetailId = truckTask.getTruckTaskDettailId();
				//任务车辆id不为空
				if(StringUtils.isNotBlank(truckTaskId)){
					truckTaskDao.selectBillForUpdateByTruckTaskId(truckTaskId);
					//查询装车任务中单据数 
					if(truckTaskDao.queryBillCountByTruckTask(truckTaskId)>1){
						//查询装车任务明细中单据数
						if(truckTaskDao.queryBillCountByTruckTaskDetail(truckTaskDetailId)<=1){
							//删除任务车辆明细
							truckTaskDao.deleteTruckTaskDetail(truckTaskDetailId);
							if(StringUtils.isNotBlank(truckTask.getTruckGPSTaskId())){
								String truckGPSTaskId = truckTask.getTruckGPSTaskId();
								//GPS任务列表
								TruckGPSTaskEntity truckGPSTask = new TruckGPSTaskEntity();
								truckGPSTask.setId(truckGPSTaskId);
								truckGPSTask.setOperateType(TaskTruckConstant.GSP_OPERATE_TYPE_DELETE);
								truckTaskDao.deleteTruckTaskGPSDetail(truckGPSTaskId);
								//调用gps接口，同步跟踪车辆数据,同步成功，则更新待跟踪gps信息为成功，否则更新待跟踪gps信息为失败
								//this.synDeleteTruckGPSTask(truckGPSTask);
							}
						}
					}else{
						// 删除任务车辆
						truckTaskDao.deleteTruckTask(truckTaskId);
						// 删除任务车辆明细
						truckTaskDao.deleteTruckTaskDetail(truckTaskDetailId);
						if(StringUtils.isNotBlank(truckTask.getTruckGPSTaskId())){
							String truckGPSTaskId = truckTask.getTruckGPSTaskId();
							TruckGPSTaskEntity truckGPSTask = new TruckGPSTaskEntity();
							truckGPSTask.setId(truckGPSTaskId);
							//同步到gps操作类型 -删除
							truckGPSTask.setOperateType(TaskTruckConstant.GSP_OPERATE_TYPE_DELETE);
							truckTaskDao.deleteTruckTaskGPSDetail(truckGPSTaskId);
							//调用gps接口，同步跟踪车辆数据,同步成功，则更新待跟踪gps信息为成功，否则更新待跟踪gps信息为失败
							//this.synDeleteTruckGPSTask(truckGPSTask);
						}
					}
				}
				//删除任务车辆单据
				return truckTaskDao.deleteTruckTaskBill(billNo);
			}
		}
		return 0;
	}
	/**
	 * 调用gps接口，同步跟踪车辆数据,同步成功，则更新待跟踪gps信息为成功，否则更新待跟踪gps信息为失败.
	 *
	 *
	 * @param truckGPSTask the truck gps task
	 * 
	 * @author dp-duyi
	 * 
	 * @date 2012-11-9 下午1:55:24
	 * 
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskService#deleteTruckTaskbyAssembelBill(java.lang.String)
	 */
	/*private void synDeleteTruckGPSTask(TruckGPSTaskEntity truckGPSTask){
		try{
			TaskVehicleDto taskVehicleDto = new TaskVehicleDto();
			//删除
			taskVehicleDto.setIsDeleted(TaskTruckConstant.GSP_OPERATE_TYPE_DELETE);
			taskVehicleDto.setVehicleId(truckGPSTask.getId());
			//上传任务车辆信息
			boolean beSuccess = fossToGPSService.notifyTaskVehicleInfo(taskVehicleDto);
			if(beSuccess){
				truckGPSTask.setBeSuccess(FossConstants.YES);
			}else{
				truckGPSTask.setBeSuccess(FossConstants.NO);
			}
		}catch(Exception e){
			LOGGER.error("调用gps接口失败", e);
			truckGPSTask.setBeSuccess(FossConstants.NO);
		}finally{
			truckTaskDao.updateTruckGPSTask(truckGPSTask);
		}
	}*/
	/**
	 * 根据配载单删除任务车辆.
	 * 
	 *
	 * @param billNo the bill no
	 * 
	 * @return the int
	 * 
	 * @author dp-duyi
	 * 
	 * @date 2012-11-9 下午1:55:24
	 * 
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskService#deleteTruckTaskbyAssembelBill(java.lang.String)
	 */
	@Override
	@Transactional
	public int deleteTruckTaskbyAssembelBill(String billNo) {
		//配载单中交接单单据级别修改为1
		Map<String,String> condition = new HashMap<String,String>();
		condition.put("billNo", billNo);
		condition.put("billLevel", TaskTruckConstant.BILL_LEVEL_VALID);
		truckTaskDao.updateTruckTaskBillStateByAssembleBill(condition);
		
		//删除任务车辆中该配载单对应的费用配载单号
		List<TruckTaskEntity> truckTasks = truckTaskDao.queryTruckTaskByAssembleNo(billNo);
		if(CollectionUtils.isNotEmpty(truckTasks)){
			TruckTaskEntity truckTask = truckTasks.get(0);
					if(truckTask != null){
						if(billNo.equals(truckTask.getChargingAssembleNo())){
							truckTask.setChargingAssembleNo(null);
							truckTaskDao.updateChargingAssembleNo(truckTask);
						}
					}
		}
		//删除该配载单任务车辆单据
		return truckTaskDao.deleteTruckTaskBill(billNo);
	}
	/**
	 * 交接单更新车牌号时更新任务车辆.
	 *
	 *
	 * @param billNo the bill no
	 * 
	 * @return the int
	 * 
	 * @author dp-duyi
	 * 
	 * @date 2012-11-9 下午3:40:23
	 * 
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskService#handOverBillUpdateVehicleNo(java.lang.String)
	 */
	@Override
	@Transactional
	public int handOverBillUpdateVehicleNo(String billNo) {
		//删除任务车辆
		this.deleteTruckTaskByHandOverBill(billNo);
		//将交接单的是否生成装车任务至为0
		Map<String,String> handOverBill = new HashMap<String,String>();
		handOverBill.put("billNo", billNo);
		handOverBill.put("beCreateTruckTask", FossConstants.NO);
		return truckTaskDao.updateHandOverBillState(handOverBill);
	}
	/**
	 * 配载单更新车牌号时更新任务车辆.
	 * 
	 *
	 * @param billNo the bill no
	 * 
	 * @return the int
	 * 
	 * @author dp-duyi
	 * 
	 * @date 2012-11-9 下午3:40:24
	 * 
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskService#assembleBillUpdateVehicleNo(java.lang.String)
	 */
	@Override
	@Transactional
	public int assembleBillUpdateVehicleNo(String billNo) {
		//删除任务车辆单据
		this.deleteTruckTaskbyAssembelBill(billNo);
		
		//将配载单的是否生成装车任务至为0
		Map<String,String> assembleBillMap = new HashMap<String,String>();
		assembleBillMap.put("billNo", billNo);
		assembleBillMap.put("beCreateTruckTask", FossConstants.NO);
		return truckTaskDao.updateAssembleBillState(assembleBillMap);
		
	}
	/**
	 * 配载单中插入交接单时修改该交接单任务车辆单据级别为0.
	 *
	 *
	 *
	 * @param handOverBillNo the hand over bill no
	 * 
	 * @return the int
	 * 
	 * @author dp-duyi
	 * 
	 * @date 2012-11-12 上午9:01:52
	 * 
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskService#updateAssembleBillByInsertHandOverBill(java.lang.String)
	 */
	@Override
	public int updateBillLevelToUnValid(String handOverBillNo) {
		Map<String,String> truckTaskBill = new HashMap<String,String>();
		//交接单号
		truckTaskBill.put("billNo", handOverBillNo);
		//未分配-UNASSIGN
		truckTaskBill.put("billLevel", TaskTruckConstant.BILL_LEVEL_UNVALID);
		//根据交接单号修改任务车辆单据状态
		return truckTaskDao.updateTruckTaskBillStateByHandOverBill(truckTaskBill);
	}
	/**
	 * 配载单中删除交接单时修改该交接单任务车辆单据级别为1.
	 *
	 *
	 *
	 * @param handOverBillNo the hand over bill no
	 * 
	 * @return the int
	 * 
	 * @author dp-duyi
	 * 
	 * @date 2012-11-12 上午9:01:52
	 * 
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskService#updateAssembleBillByDeleteHandOverBill(java.lang.String)
	 */
	@Override
	public int updateBillLevelToValid(String handOverBillNo) {
		Map<String,String> truckTaskBill = new HashMap<String,String>();
		//交接单号
		truckTaskBill.put("billNo", handOverBillNo);
		//单据级别 1-有效
		truckTaskBill.put("billLevel", TaskTruckConstant.BILL_LEVEL_VALID);
		//根据交接单号修改任务车辆单据状态
		return truckTaskDao.updateTruckTaskBillStateByHandOverBill(truckTaskBill);
	}
	/**
	 * 查询推送营业部交接单信息.
	 *
	 *
	 *
	 * @param handOverBillNo the hand over bill no
	 * @return the string
	 * @author dp-duyi
	 * @date 2012-11-15 下午2:29:19
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskService#handOverBillMsg(java.lang.String)
	 */
	@Override
	public String queryHandOverBillMsg(String handOverBillNo) {
		
		try{
			HandOverMsgDto handOverMsg = truckTaskDao.queryHandOverBillMsg(handOverBillNo);
			//调用综合接口查询到达部门是否为营业部
			OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(handOverMsg.getDestOrigCode());
			if("Y".equals(org.getSalesDepartment())){
				StringBuilder msg = new StringBuilder();
				msg.append("【预到达交接单】:");
				msg.append("交接单号:");
				msg.append(handOverMsg.getHandOverNo());
				if(StringUtils.isNotBlank(handOverMsg.getVehicleNo())){
					msg.append(" 车牌号:");
					msg.append(handOverMsg.getVehicleNo());
				}
				if(StringUtils.isNotBlank(handOverMsg.getDriver())){
					msg.append(" 司机:");
					msg.append(handOverMsg.getDriver());
				}
				if(StringUtils.isNotBlank(handOverMsg.getDriverPhone())){
					msg.append(" 司机电话:");
					msg.append(handOverMsg.getDriverPhone());
				}
				if(handOverMsg.getDepartTime() != null){
					msg.append(" 车辆出发时间:");
					SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
					msg.append(df.format(handOverMsg.getDepartTime()));
				}
				if(StringUtils.isNotBlank(handOverMsg.getOrigOrgName())){
					msg.append(" 出发部门:");
					msg.append(handOverMsg.getOrigOrgName());
				}
				msg.append(" 总票数:");
				msg.append(handOverMsg.getWayBillQtyTotal());
				msg.append(" 卡货总票数:");
				msg.append(handOverMsg.getFastWayBillQtyToal());
				msg.append(" 总金额:");
				msg.append(handOverMsg.getMoneyTotal());
				msg.append(" 总重量:");
				msg.append(handOverMsg.getWeigtTotal());
				msg.append(" 总体积:");
				msg.append(handOverMsg.getVulomeTotal());
				if(StringUtils.isNotBlank(handOverMsg.getCreatorCode())){
					msg.append("#");
					msg.append(handOverMsg.getCreatorCode());
				}
				return msg.toString();
				
			}else{
				return null;
			}
		}catch(Exception e){
			//日志
			LOGGER.error("查询推送营业部交接单信息失败",e);
			return null;
		}
	}
	/**
	 * 接送货接口：根据运单号、流水号查询在途交接单出发部门编码，到达部门编码.
	 *
	 *
	 *
	 * @param wayBillNo the way bill no
	 * 
	 * @param serialNo the serial no
	 * 
	 * @return the hand over bill entity
	 * 
	 * @author dp-duyi
	 * 
	 * @date 2012-11-23 上午8:52:03
	 * 
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskService#queryOnTheWayHandOverBillBySerialNo(java.lang.String, java.lang.String)
	 */
	@Override
	public HandOverBillEntity queryOnTheWayHandOverBillBySerialNo(
			String wayBillNo, String serialNo) {
		if(StringUtils.isNotBlank(wayBillNo) && StringUtils.isNotBlank(serialNo)){
			Map<String, Object> condition = new HashMap<String,Object>();
			condition.put("wayBillNo", wayBillNo);
			condition.put("serialNo", serialNo);
			List<String> states = new ArrayList<String>();
			//30：已出发
			states.add(String.valueOf(LoadConstants.HANDOVERBILL_STATE_ALREADY_DEPART));
			//21：已配载 (集配交接单专属状态)
			states.add(String.valueOf(LoadConstants.HANDOVERBILL_STATE_ALREADY_ASSEMBLE));
			//20：已交接
			states.add(String.valueOf(LoadConstants.HANDOVERBILL_STATE_FORMAL_HANDOVER));
			//40：已到达
			states.add(String.valueOf(LoadConstants.HANDOVERBILL_STATE_ALREADY_ARRIVE));
			//状态列表
			condition.put("states", states);
			try{
				HandOverBillEntity handOver = truckTaskDao.queryOnTheWayHandOverBillBySerialNo(condition);
				return handOver;
			}catch(Exception e){
				LOGGER.error(wayBillNo+" "+serialNo+"接送货接口：根据运单号、流水号查询在途交接单出发部门编码，到达部门编码失败", e);
				return null;
			}
		}else{
			return null;
		}
	}
	/**
	 * 交接单新增时，更新走货路径状态.
	 *
	 *
	 *
	 * @param handOverDetails the hand over details
	 * 
	 * @author dp-duyi
	 * 
	 * @date 2012-11-23 上午10:24:45
	 * 
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskService#updateTransportPath()
	 */
	@Override
	@Transactional
	public Date updateTransportPath(Date bizJobStartTime,Date bizJobEndTime,int threadNo, int threadCount) {
		LOGGER.error("查询更新走货路径交接单那开始");
		List<HandOverBillDetailDto> handOverDetails = truckTaskDao.queryUnUpdateTransportPathHandOverBill(bizJobStartTime,bizJobEndTime,threadNo,threadCount);
		LOGGER.error("查询更新走货路径交接单那结束");
		
		if(CollectionUtils.isNotEmpty(handOverDetails)){
			LOGGER.error("任务车辆更新走货路径条数:"+handOverDetails.size());
			for(HandOverBillDetailDto handOverDetail : handOverDetails){
				String origOrgCode = handOverDetail.getOrigOrgCode();
				String receiveOrgCode = handOverDetail.getReceiveOrgCode();
				String transPropertyCode = handOverDetail.getTransPropertyCode();
				truckTaskService.updateHandOverBeUpdateTransportPath(handOverDetail.getHandOverBillNo());
				//查询流水号
				List<HandOverBillSerialNoDetailEntity> serialDtos = handOverBillService.getHandOverBillSerialNoDetailsByWayBillNo(handOverDetail.getWaybillNo(),handOverDetail.getHandOverBillNo());
				if(CollectionUtils.isNotEmpty(serialDtos)){
					List<String> serialNoList = new ArrayList<String>();
					for(HandOverBillSerialNoDetailEntity serialDto : serialDtos){
						serialNoList.add(serialDto.getSerialNo());
					}
					try{
						//更新走货路径状态
						int beJoinCar = 0;
						if(FossConstants.YES.equals((handOverDetail.getBeJoinCar()))){
							beJoinCar = 1;
						}else{
							beJoinCar = 0;
						}
						LOGGER.error("任务车辆更新走货路径开始"+handOverDetail.getHandOverBillNo()+" "+handOverDetail.getWaybillNo());
						calculateTransportPathService.loadVehicleRequiresNewTransactional(handOverDetail.getWaybillNo(), serialNoList, TransportPathConstants.TRANSPORTPATH_STATUS_HANDOVER,serialNoList.size(), beJoinCar, handOverDetail.getOrigOrgCode(), handOverDetail.getDestOrgCode(),handOverDetail.getVehicleNo());
						LOGGER.error("任务车辆更新走货路径结束"+handOverDetail.getHandOverBillNo()+" "+handOverDetail.getWaybillNo());
						
					}catch(Exception e){
						LOGGER.error(handOverDetail.getWaybillNo()+"交接单新增时，更新走货路径状态失败", e);
						//任务执行日志实体
						TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
						jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.UPDATE_TRANSPORT_PATH.getBizName());
						jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.UPDATE_TRANSPORT_PATH.getBizCode());
						jobProcessLogEntity.setRemark("更新走货路径失败！,运单编号为：" + handOverDetail.getWaybillNo());
						jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
						jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
						//记录job日志
						tfrCommonService.addJobProcessLog(jobProcessLogEntity);
					}
					//更新走货路径modify_start_time begin
					try{
						//更新走货路径调整出发时间(tfr.t_opt_path_detail.modify_start_time)
						//货量预测用到
						//当前部门, 收货部门, 运输性质, 流水号
						LOGGER.error("更新走货路径调整出发时间开始"+handOverDetail.getHandOverBillNo()+" "+handOverDetail.getWaybillNo());
						calculateTransportPathService.changeModifyStartTime(handOverDetail.getWaybillNo(), serialNoList, 
								origOrgCode, receiveOrgCode, transPropertyCode);
						LOGGER.error("更新走货路径调整出发时间结束"+handOverDetail.getHandOverBillNo()+" "+handOverDetail.getWaybillNo());
					}catch(Exception e){
						LOGGER.error(handOverDetail.getWaybillNo()+"交接单新增时，更新走货路径状态失败", e);
						//任务执行日志实体
						TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
						jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.UPDATE_TRANSPORT_PATH.getBizName());
						jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.UPDATE_TRANSPORT_PATH.getBizCode());
						jobProcessLogEntity.setRemark("更新走货路径失败！,运单编号为：" + handOverDetail.getWaybillNo());
						jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
						jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
						//记录job日志
						tfrCommonService.addJobProcessLog(jobProcessLogEntity);
					}
					//end
				}
			}
			
			return handOverDetails.get(handOverDetails.size()-1).getModifyDate();
		}
		return new Date();
	}
	/**
	 * 批量同步未成功同步至gps系统的待跟踪车辆.
	 *
	 *
	 *
	 * @param queryCount the query count
	 * 
	 * @author dp-duyi
	 * 
	 * @date 2012-11-27 下午3:03:59
	 * 
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskService#batchFailedSynTruckGpsTask(int)
	 *//*
	@Override
	public void batchFailedSynTruckGpsTask(int queryCount) {
		List<TaskVehicleDto> truckGPSTasks = truckTaskDao.queryFailedTruckGPSTasks(queryCount);
		if(CollectionUtils.isNotEmpty(truckGPSTasks)){
			for(TaskVehicleDto taskVehicle : truckGPSTasks){
				try{
					LOGGER.error("任务车辆同步车辆信息至GPS开始"+taskVehicle.getVehicleNo());
					boolean beSuccess = fossToGPSService.notifyTaskVehicleInfo(taskVehicle);
					LOGGER.error("任务车辆同步车辆信息至GPS结束"+taskVehicle.getVehicleNo());
					if(beSuccess){
						TruckGPSTaskEntity taskGpsTaskEntity = new TruckGPSTaskEntity();
						taskGpsTaskEntity.setId(taskVehicle.getVehicleId());
						taskGpsTaskEntity.setBeSuccess(FossConstants.YES);
						truckTaskDao.updateTruckGPSTask(taskGpsTaskEntity);
					}
				}catch(Exception e){
					LOGGER.error("批量同步至gps系统的待跟踪车辆失败",e);
				}
			}
		}
	}*/
	/**
	 * 接送货接口：根据运单号查询运单交接信息.
	 *
	 *
	 *
	 * @param wayBillNo the way bill no
	 * 
	 * @return the list
	 * 
	 * @author dp-duyi
	 * 
	 * @date 2013-1-5 上午10:50:21
	 * 
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskService#queryWayBillHandOverInfo(java.lang.String)
	 */
	@Override
	public List<WayBillHandOverDto> queryWayBillHandOverInfo(String wayBillNo) {
		if(StringUtils.isNotBlank(wayBillNo)){
			return truckTaskDao.queryWayBillHandOverInfo(wayBillNo);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskService#getVehicle(java.lang.String)
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public VehicleAssociationDto getVehicle(String vehicleNo){
		try{
			VehicleAssociationDto vehicleDto = vehicleService.queryVehicleAssociationDtoByVehicleNo(vehicleNo);
			return vehicleDto;
		}catch(Exception e){
			LOGGER.error("vehicleService.queryVehicleAssociationDtoByVehicleNo(vehicleNo)失败",e);
			return null;
		}
	}
	
	/**
	 * （方法详细描述说明、方法参数的具体涵义）.
	 *
	 *
	 *
	 * @param handOverDetails the hand over details
	 * 
	 * @author dp-duyi
	 * 
	 * @date 2013-3-15 下午12:54:48
	 * 
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskService#updateCRMGoodsState(java.util.List)
	 */
	/*@Override
	public void updateCRMGoodsState(List<HandOverBillDetailDto> handOverDetails) {
		if(CollectionUtils.isNotEmpty(handOverDetails)){
			for(HandOverBillDetailDto goods: handOverDetails){
				if(StringUtils.isNotBlank(goods.getOrderNo())){
					LOGGER.error("更新CRM订单开始：" + goods + "-" + "IN_TRANSIT");
					// 获取CRM的映射订单状态
					String crmOrderStatus = "IN_TRANSIT";
					// 为空则表明不需要将状态反馈给CRM
					if (StringUtils.isEmpty(crmOrderStatus)) {
						return;
					}
					// 更新CRM订单
					CrmModifyInfoDto crmModifyInfoDto = new CrmModifyInfoDto();
					// 订单号
					crmModifyInfoDto.setOrderNumber(goods.getOrderNo());
					// 司机姓名
					crmModifyInfoDto.setDriverName(goods.getDriverName());
					// 司机手机号
					crmModifyInfoDto.setDriverMobile(goods.getDriverPhone());
					// 操作人编码
					crmModifyInfoDto.setOprateUserNum(goods.getCreatorCode());
					// 操作人组织code
					OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(goods.getOrigOrgCode());
					if(org != null){
						crmModifyInfoDto.setOprateDeptCode(org.getUnifiedCode());
					}
					// 订单状态
					crmModifyInfoDto.setGoodsStatus(crmOrderStatus);
					// 操作备注
					//crmModifyInfoDto.setBackInfo(orderHandleDto.getOperateNotes());
					// 调用CRM订单修改接口
					crmOrderJMSService.sendModifyOrder(crmModifyInfoDto);
					LOGGER.error("更新CRM订单成功");
				}
			}
		}
	}*/
	/**
	 * 查询未出发的任务车辆明细记录
	 * @author 045923-foss-shiwei
	 * @date 2013-5-15 上午9:53:41
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao#queryUndepartRecByVehicleNo(com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskDetailEntity)
	 */
	@Override
	public List<String> queryUndepartRecByVehicleNo(TruckTaskDetailEntity queryCon) {
		return this.truckTaskDao.queryUndepartRecByVehicleNo(queryCon);
	}
	/** 
	 * @author dp-duyi
	 * @date 2013-6-22 上午11:58:40
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskService#CreateTruckTaskByHandOverBill(java.lang.String)
	 */
	@Override
	public int createTruckTaskByHandOverBill(String handOverBillNo) {
		//sonar优化 218427
		if(handOverBillNo==null){
			throw new TfrBusinessException("handOverBillNo为空");
		}
		List<TruckTaskHandOverDto> handOverDtos = truckTaskDao.queryUnCreateTaskTruckHandOver(null,null,0,1,handOverBillNo);
		int insertCount = 0;
		if(CollectionUtils.isNotEmpty(handOverDtos)){
			insertCount = handOverDtos.size();
			if(insertCount > 0){
				for(TruckTaskHandOverDto handOverDto : handOverDtos){
					LOGGER.error("生成任务车辆开始"+handOverDto.getHandOverBillNo());
					this.createTruckTask(handOverDto);
					LOGGER.error("生成任务车辆结束"+handOverDto.getHandOverBillNo());
					try{
						LOGGER.error("调用交接单监控服务开始"+handOverDto.getHandOverBillNo());
						this.handOverBillCountMonitor(handOverDto);
						LOGGER.error("调用交接单监控服务结束"+handOverDto.getHandOverBillNo());
					}catch(Exception e){
						LOGGER.error("交接单号：" + handOverDto.getHandOverBillNo() + "监控失败异常！",e);
					}
				}
			}
		}
		
		return insertCount;
	}
	/** 
	 * @author dp-duyi
	 * @date 2013-6-22 上午11:58:40
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskService#createTruckTaskByAssembleBill(java.lang.String)
	 */
	@Override
	public int createTruckTaskByAssembleBill(String assembleBillNo) {
		List<TruckTaskHandOverDto> assembleBills = truckTaskDao.queryUnCreateTruckTaskAssembleBill(null,null,0,1,assembleBillNo);
		if(CollectionUtils.isNotEmpty(assembleBills)){
			for(TruckTaskHandOverDto assmbleBill : assembleBills){
				LOGGER.error("配载单生成任务车辆开始"+assmbleBill.getHandOverBillNo());
				this.updateTruckTaskByAssembleBill(assmbleBill);
				LOGGER.error("配载单生成任务车辆结束"+assmbleBill.getHandOverBillNo());
			}
		}
		return assembleBills.size();
	}
	/** 
	 * @author dp-duyi
	 * @date 2013-7-2 下午8:20:58
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskService#updateHandOverBeUpdateTransportPath(java.lang.String)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public int updateHandOverBeUpdateTransportPath(String handOverNo) {
		return truckTaskDao.updateHandOverBeUpdateTransportPath(handOverNo);
	}
	/** 
	 * @author dp-duyi
	 * @date 2013-7-2 下午8:24:34
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskService#updateHandOverBillBeUpdateCRM(java.util.List)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void updateHandOverBillBeUpdateCRM(String handBillNo) {
		truckTaskCallESBDao.updateHandOverBillBeUpdateCRM(handBillNo);
	}

	/**
	 * 
	 * <p>配合GPS项目-根据任务id查询交接单信息</p> 
	 * @author alfred
	 * @date 2014-4-1 下午4:19:27
	 * @param truckDetailId
	 * @return 
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskService#queryhandOverBillBytruckDetailId(java.lang.String)
	 */
	@Override
	public List<TruckTaskHandOverDto> queryhandOverBillBytruckDetailId(
			String truckDetailId) {
		return truckTaskDao.queryhandOverBillBytruckDetailId(truckDetailId);
	}
	/**
	 * 通过配载车次号/交接单号查询车辆任务明细信息
	 * @author foss-heyongdong
	 * @date 2014年4月21日 14:01:32
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskService#queryTruckTaskDetail(java.lang.String)
	 * */
	@Override
	public List<TruckTaskDetailEntity> queryTruckTaskDetail(String billNo){
		return truckTaskDao.queryTruckTaskDetail(billNo);
	}
	
	/**
	 * 修改车辆任务 ，用于提前装车
	 * @author 105869
	 * @date 2014年9月1日 10:28:17
	 * @param vehicleNo,trailerHandOverbills
	 * */
	@Override 
	public int modifyTruckTaskByTrailerHandOverBill(String vehilceNo,List<String> trailerHandOverbills) {
		if(CollectionUtils.isEmpty(trailerHandOverbills)||trailerHandOverbills.size()<=0){
			return 0;
		}
		
		//随便找个foss的交接单号去查询车辆任务单据里面的wk交接单
		String billNo = trailerHandOverbills.get(0);
		TruckTaskHandOverDto truckTask = truckTaskDao.queryTruckTaskIdByHandOverBill(billNo);
		if (truckTask == null || StringUtils.isEmpty(truckTask.getTruckTaskDettailId())) {
			return 0;
		}
		List<String> wKHandoverbillList = wKTfrBillDao.getWkHandoverbillno(truckTask.getTruckTaskDettailId());
		for(String idx : wKHandoverbillList){
			//将表tfr.t_wk_trf_bill列BECREATETRUCKTASK设为N
			Map<String,String> wkBill = new HashMap<String,String>();
			wkBill.put("billNo", idx);
			wkBill.put("beCreateTruckTask", FossConstants.NO);
			wkBill.put("vehilceNo", vehilceNo);
			truckTaskDao.updateHandOverBillStateWk(wkBill);
			truckTaskDao.deleteTruckTaskBill(idx);
		}
		
		//根据主任务ID查询主任务出发部门
//		TruckTaskEntity task = truckTaskDao.queryTruckTaskById(truckTask.getTruckTaskId());
		
		for(String handOverBillNo:trailerHandOverbills){
			List<TruckTaskHandOverDto> handOverDtos = truckTaskDao.queryTaskTruckHandOverBill(handOverBillNo);
			int insertCount = 0;
			if(CollectionUtils.isNotEmpty(handOverDtos)){
				insertCount = handOverDtos.size();
				if(insertCount > 0){
					for(TruckTaskHandOverDto handOverDto : handOverDtos){
						LOGGER.error("提前装车交接单配载更新任务车辆开始"+handOverDto.getHandOverBillNo());
						this.modifyTruckTask(handOverDto,vehilceNo);
						LOGGER.error("提前装车交接单配载更新任务车辆结束"+handOverDto.getHandOverBillNo());
						try{
							LOGGER.error("调用交接单监控服务开始"+handOverDto.getHandOverBillNo());
							this.handOverBillCountMonitor(handOverDto);
							LOGGER.error("调用交接单监控服务结束"+handOverDto.getHandOverBillNo());
						}catch(Exception e){
							LOGGER.error("交接单号：" + handOverDto.getHandOverBillNo() + "监控失败异常！",e);
						}
					}
				}
			}
			
		}
		
		// 调用ECS系统接口开关
		if (configurationParamsService.queryTfrSwitch4Ecs()) {
			/** 删除车辆任务同步给快递系统 通知任务 */
			Log.error("插入临时表，通过JOB推送给ECS系统");
			for(String idx : wKHandoverbillList){
//				String parmartJson = truckTask.getVehicleNo()+","+truckTask.getOrigOrgCode() + "," + idx + "," + task.getOrigOrgCode();
//				tfrNotifyService.addTfrNotifyEntity(
//						new TfrNotifyEntity(UUIDUtils.getUUID(), NotifyWkConstants.NOTIFY_TYPE_DELETE_TRUCK_TASK,
//								truckTask.getTruckTaskId(), truckTask.getTruckTaskDettailId(), DepartureHandle.getCurrentUserCode(), parmartJson));
				
				tfrNotifyService.addTfrNotifyEntity(
						new TfrNotifyEntity(UUIDUtils.getUUID(), NotifyWkConstants.NOTIFY_TYPE_CREATE_TRUCK_TASK,
								idx, null, null));
				
				//调用悟空修改交接单的车牌号
				try {
					fossToWkService.editWkHandOverBillVehicleno(vehilceNo, idx, truckTask.getOrigOrgCode());
				} catch (Exception e) {
					LOGGER.error("交接单号：" + idx + "失败异常！",e.getCause());
					throw new TfrBusinessException("ECS系统异常,更新ECS系统交接单车牌号失败" + e.getCause());
				}
			}
		}
		
		return 1;
	}
	/**
	 * 在配载单中添加挂牌号交接单时对车辆任务修改
	 * */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	private int modifyTruckTask(TruckTaskHandOverDto handOverDto,String vehicleNo) {
		LOGGER.error("配载时修改任务车辆开始"+handOverDto.getHandOverBillNo());
		
		String trailerVehicleNo = handOverDto.getVehicleNo();
		String billNo = handOverDto.getHandOverBillNo();
		//用托头号（配载的车牌号）查询是否存在车辆任务
		TruckTaskHandOverDto queryTask=new TruckTaskHandOverDto();
		queryTask.setVehicleNo(vehicleNo);
		String truckTaskId = truckTaskDao.queryBeCreateTruckTask(queryTask);
	
		//根据交接单查询任务车辆ID，任务车辆明细ID
		TruckTaskHandOverDto truckTask = truckTaskDao.queryTruckTaskIdByHandOverBill(billNo);
		//如果车辆任务相等，则不需要操作
		if(truckTask!=null&&StringUtils.isNotBlank(truckTask.getTruckTaskId())&&StringUtils.equals(truckTaskId, truckTask.getTruckTaskId())){
			return 0;
		}
		List<TruckTaskHandOverDto> truckTrailerSeals =null;
		//查询挂牌号的封签放行记录
		if(truckTask!=null&&StringUtils.isNotBlank(truckTask.getTruckTaskId())){
			TruckTaskDetailEntity qeuryTrailerSeal = new TruckTaskDetailEntity();
			qeuryTrailerSeal.setParentId(truckTask.getTruckTaskId());
			qeuryTrailerSeal.setVehicleNo(trailerVehicleNo);
			truckTrailerSeals= truckTaskDao.queryVehicleSealByTaskId(qeuryTrailerSeal);
			
		}
		//sonar 加入为空判断  218427
		if(truckTrailerSeals==null){
			throw new TfrBusinessException("truckTrailerSeals为空");
		}
		/*******************************删除挂牌号任务车辆*********************************/
		//任务车辆id不为空
		//任务车辆明细不为空 
		if(truckTask != null){
			if(StringUtils.isNotBlank(truckTask.getTruckTaskId()) && StringUtils.isNotBlank(truckTask.getTruckTaskDettailId())){
				//任务车辆id
				String trailertruckTaskId = truckTask.getTruckTaskId();
				//任务车辆明细id
				String truckTaskDetailId = truckTask.getTruckTaskDettailId();
				//任务车辆id不为空
				if(StringUtils.isNotBlank(trailertruckTaskId)){
					truckTaskDao.selectBillForUpdateByTruckTaskId(trailertruckTaskId);
					//查询装车任务中单据数 
					if(truckTaskDao.queryBillCountByTruckTask(trailertruckTaskId)>1){
						//查询装车任务明细中单据数
						if(truckTaskDao.queryBillCountByTruckTaskDetail(truckTaskDetailId)<=1){
							//删除任务车辆明细
							truckTaskDao.deleteTruckTaskDetail(truckTaskDetailId);
							if(StringUtils.isNotBlank(truckTask.getTruckGPSTaskId())){
								String truckGPSTaskId = truckTask.getTruckGPSTaskId();
								//GPS任务列表
								TruckGPSTaskEntity truckGPSTask = new TruckGPSTaskEntity();
								truckGPSTask.setId(truckGPSTaskId);
								truckGPSTask.setOperateType(TaskTruckConstant.GSP_OPERATE_TYPE_DELETE);
								truckTaskDao.deleteTruckTaskGPSDetail(truckGPSTaskId);
								//调用gps接口，同步跟踪车辆数据,同步成功，则更新待跟踪gps信息为成功，否则更新待跟踪gps信息为失败
								//this.synDeleteTruckGPSTask(truckGPSTask);
							}
						}
					}else{
						// 删除任务车辆
						truckTaskDao.deleteTruckTask(trailertruckTaskId);
						// 删除任务车辆明细
						truckTaskDao.deleteTruckTaskDetail(truckTaskDetailId);
						if(StringUtils.isNotBlank(truckTask.getTruckGPSTaskId())){
							String truckGPSTaskId = truckTask.getTruckGPSTaskId();
							TruckGPSTaskEntity truckGPSTask = new TruckGPSTaskEntity();
							truckGPSTask.setId(truckGPSTaskId);
							//同步到gps操作类型 -删除
							truckGPSTask.setOperateType(TaskTruckConstant.GSP_OPERATE_TYPE_DELETE);
							truckTaskDao.deleteTruckTaskGPSDetail(truckGPSTaskId);
							//调用gps接口，同步跟踪车辆数据,同步成功，则更新待跟踪gps信息为成功，否则更新待跟踪gps信息为失败
							//this.synDeleteTruckGPSTask(truckGPSTask);
						}
					}
				}
				//删除任务车辆单据
				 truckTaskDao.deleteTruckTaskBill(billNo);
			}
		}
		
		/*****************************新增任务车辆或者合并任务车辆***************************/
		//重新设置车牌号
		handOverDto.setVehicleNo(vehicleNo);
		//设置挂牌号
		handOverDto.setTrailerVehicleNo(trailerVehicleNo);
		
		//不存在任务车辆,则作废原有的挂牌号车辆任务
		if(StringUtils.isBlank(truckTaskId)){
			handOverDto.setTruckTaskId(UUIDUtils.getUUID());
			LOGGER.error("生成任务车辆主表开始"+handOverDto.getHandOverBillNo());
			this.insertTruckTask(handOverDto);
			LOGGER.error("生成任务车辆主表结束"+handOverDto.getHandOverBillNo());
			
			handOverDto.setTruckTaskDettailId(UUIDUtils.getUUID());
			LOGGER.error("生成任务车辆明细开始"+handOverDto.getHandOverBillNo());
			 this.insertTruckTaskDetail(handOverDto);
			LOGGER.error("生成任务车辆明细结束"+handOverDto.getHandOverBillNo());
			//存在任务车辆
		}else{
			handOverDto.setTruckTaskId(truckTaskId);
			TruckTaskDetailEntity truckTaskDetailEntity = truckTaskDao.queryBeCreateTruckTaskDetail(handOverDto);
			if(truckTaskDetailEntity == null || StringUtils.isBlank(truckTaskDetailEntity.getId())){//不存在任务车辆明细
				handOverDto.setTruckTaskDettailId(UUIDUtils.getUUID());
				LOGGER.error("生成任务车辆明细开始"+handOverDto.getHandOverBillNo());
				 this.insertTruckTaskDetail(handOverDto);
				LOGGER.error("生成任务车辆明细结束"+handOverDto.getHandOverBillNo());
			}else{
				handOverDto.setTruckTaskDettailId(truckTaskDetailEntity.getId());
				//更新挂牌号字段
				this.updateTruckTaskDetail(handOverDto);
				//如果该存在的任务是整车，但是新增的任务不是整车则提示
				if(StringUtils.equals(truckTaskDetailEntity.getBeCarLoad(),FossConstants.YES) && StringUtils.equals(handOverDto.getBeCarLoad(),FossConstants.NO) ){					
					LOGGER.error("该车辆已做了整车不能再做非整车交接"+handOverDto.getHandOverBillNo());
					throw new TfrBusinessException("该车辆已做了整车不能再做非整车交接");
				}else if(StringUtils.equals(truckTaskDetailEntity.getBeCarLoad(),FossConstants.NO) && StringUtils.equals(handOverDto.getBeCarLoad(),FossConstants.YES)){
					LOGGER.error("该车辆已做了非整车不能再做整车交接"+handOverDto.getHandOverBillNo());
					throw new TfrBusinessException("该车辆已做了非整车不能再做整车交接");
				}
				
			}
		}
		
		LOGGER.error("生成任务车辆单据开始"+handOverDto.getHandOverBillNo());
		this.insertTruckTaskBill(handOverDto,TaskTruckConstant.BILL_TYPE_HANDOVER);
		LOGGER.error("生成任务车辆单据开始"+handOverDto.getHandOverBillNo());
		
		Map<String,String> handOverBill = new HashMap<String,String>();
		//交接单号
		handOverBill.put("billNo", handOverDto.getHandOverBillNo());
		//新增车辆任务
		handOverBill.put("beCreateTruckTask", FossConstants.YES);
		
		int updateCount = truckTaskDao.updateHandOverBillState(handOverBill);
		//查询托头（配载车牌号）的封签、放行记录
		TruckTaskDetailEntity qeurySeal = new TruckTaskDetailEntity();
		qeurySeal.setVehicleNo(vehicleNo);
		List<TruckTaskHandOverDto> truckSeals = truckTaskDao.queryVehicleSeal(qeurySeal);
		/*************************处理封签信息**************************/
		//如果配载的车牌号没有绑定封签，而挂牌号绑定了封签则 把挂牌号绑定的封签 绑定到配载车牌号的任务上
		if(CollectionUtils.isEmpty(truckSeals)&&truckSeals.size()<=0
				&&CollectionUtils.isNotEmpty(truckTrailerSeals)&&truckTrailerSeals.size()>0){
			TruckTaskHandOverDto truckSeal = truckTrailerSeals.get(0);
			//若已封封签，则绑定封签
			if(StringUtils.isNotBlank(truckSeal.getSealId())){
				//跟新实体
				TruckTaskDetailEntity truckTaskDetail = new TruckTaskDetailEntity();
				//封签id
				truckTaskDetail.setVehicleSealId(truckSeal.getSealId());
				//车牌号
				truckTaskDetail.setVehicleNo(vehicleNo);
				//主任务id
				truckTaskDetail.setParentId(handOverDto.getTruckTaskId());
				//更新封签
				truckTaskDao.updateVehicleSeal(truckTaskDetail);
			}
			
		}
		
		
		if(updateCount == 1){
			LOGGER.error("交接单生成任务车辆结束"+handOverDto.getHandOverBillNo());
			return updateCount;
		}else{
			LOGGER.error("交接单生成任务车辆异常更新交接单件数不为一"+handOverDto.getHandOverBillNo());
			throw new TfrBusinessException("无效交接单");
		} 
		
	}
	/**
	 * 更新车辆任务明细
	 * */
	@Override
	public int updateTruckTaskDetail(TruckTaskHandOverDto handOverDto) {
		TruckTaskDetailEntity truckTaskDetail = new TruckTaskDetailEntity();
		//设置挂牌号字段
		truckTaskDetail.setTrailerVehicleNo(handOverDto.getTrailerVehicleNo());
		//任务车辆明细id
		truckTaskDetail.setId(handOverDto.getTruckTaskDettailId());
		truckTaskDao.updateTruckTaskDetail(truckTaskDetail);
		return 0;
		
	}
	/**
	 **查询车辆任务明细信息，通过车牌号、目的站、状态
	 *@author foss-heyongdong
	 *@date 2014年9月11日 09:16:24
	 *@see com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskService#qeuryTruckTaskDetail(com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskDetailEntity)
	 */
	@Override
	public List<String> queryTruckTaskDetailAndBill(TruckTaskDetailEntity qeuryParam) {
		
		return truckTaskDao.queryTruckTaskDetailAndBill(qeuryParam);
	}
	/**
	 * 通过配载单号生成在途装车辆任务
	 * @author 105869
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskService#createTruckTaskForMidLoad(java.lang.String)
	 */
	@Override
	public int createTruckTaskForMidLoad(String vehicleAssembleNo,String ontheWayOrgCode,String ontheWayOrgName) {
		List<TruckTaskHandOverDto> vehicleAssembles =truckTaskDao.queryUncreateTaskVehicleAssem(vehicleAssembleNo);
		if(CollectionUtils.isEmpty(vehicleAssembles)||vehicleAssembles.size()<=0){
			return 0;
		}
		
		//获取创建车辆任务的信息
		TruckTaskHandOverDto vehicleAssemble=vehicleAssembles.get(0);
		if(StringUtils.isEmpty(vehicleAssemble.getDestOrgCode())){
			vehicleAssemble.setDestOrgCode(ontheWayOrgCode);
		}
		if(StringUtils.isEmpty(vehicleAssemble.getDestOrgName())){
			vehicleAssemble.setDestOrgName(ontheWayOrgName);
		}
		String truckTaskId = truckTaskDao.queryBeCreateTruckTask(vehicleAssemble);
		//如果不存在车辆任务
		if(StringUtils.isEmpty(truckTaskId)){
			vehicleAssemble.setTruckTaskId(UUIDUtils.getUUID());
			LOGGER.error("生成任务车辆主表开始"+vehicleAssemble.getHandOverBillNo());
			this.insertTruckTask(vehicleAssemble);
			LOGGER.error("生成任务车辆主表结束"+vehicleAssemble.getHandOverBillNo());
			
			vehicleAssemble.setTruckTaskDettailId(UUIDUtils.getUUID());
			LOGGER.error("生成任务车辆明细开始"+vehicleAssemble.getHandOverBillNo());
			 this.insertTruckTaskDetail(vehicleAssemble);
			LOGGER.error("生成任务车辆明细结束"+vehicleAssemble.getHandOverBillNo());
			//存在任务车辆
		}else{
			vehicleAssemble.setTruckTaskId(truckTaskId);
			TruckTaskDetailEntity truckTaskDetailEntity = truckTaskDao.queryBeCreateTruckTaskDetail(vehicleAssemble);
			if(truckTaskDetailEntity == null || StringUtils.isBlank(truckTaskDetailEntity.getId())){//不存在任务车辆明细
				vehicleAssemble.setTruckTaskDettailId(UUIDUtils.getUUID());
				LOGGER.error("生成任务车辆明细开始"+vehicleAssemble.getHandOverBillNo());
				 this.insertTruckTaskDetail(vehicleAssemble);
				LOGGER.error("生成任务车辆明细结束"+vehicleAssemble.getHandOverBillNo());
			}
		}
		return 0;
	}
	/**
	 * 删除配载单或者修改配载单车牌号或者修改配载单中途到达部门时需要删除原有的中途到达部门
	 * @author 105869
	 * @date 2014年9月17日 16:20:55
	 * @param vehicleAssembleNo 
	 * 
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskService#deleteTruckTaskForMidLoad(java.lang.String)
	 */
	@Override
	public void deleteTruckTaskForMidLoad(String orgCode,String destOrgCode,String vehicleNo) {
		//根据交接单查询任务车辆ID，任务车辆明细ID
		TruckTaskDetailEntity queryTask=new TruckTaskDetailEntity();
		queryTask.setVehicleNo(vehicleNo);
		queryTask.setOrigOrgCode(orgCode);
		queryTask.setDestOrgCode(destOrgCode);
		TruckTaskHandOverDto truckTask = truckTaskDao.queryTruckTaskAndDetail(queryTask);
		if(truckTask != null){
			if(StringUtils.isNotBlank(truckTask.getTruckTaskId()) && StringUtils.isNotBlank(truckTask.getTruckTaskDettailId())){
				//任务车辆id
				String truckTaskId = truckTask.getTruckTaskId();
				//任务车辆明细id
				String truckTaskDetailId = truckTask.getTruckTaskDettailId();
				//任务车辆id不为空
				if(StringUtils.isNotBlank(truckTaskId)){
					truckTaskDao.selectBillForUpdateByTruckTaskId(truckTaskId);
					//查询装车任务中单据数 
					if(truckTaskDao.queryBillCountByTruckTask(truckTaskId)>=1){
						//查询装车任务明细中单据数
						if(truckTaskDao.queryBillCountByTruckTaskDetail(truckTaskDetailId)<1){
							//删除任务车辆明细
							truckTaskDao.deleteTruckTaskDetail(truckTaskDetailId);
							if(StringUtils.isNotBlank(truckTask.getTruckGPSTaskId())){
								String truckGPSTaskId = truckTask.getTruckGPSTaskId();
								//GPS任务列表
								TruckGPSTaskEntity truckGPSTask = new TruckGPSTaskEntity();
								truckGPSTask.setId(truckGPSTaskId);
								truckGPSTask.setOperateType(TaskTruckConstant.GSP_OPERATE_TYPE_DELETE);
								truckTaskDao.deleteTruckTaskGPSDetail(truckGPSTaskId);
								//调用gps接口，同步跟踪车辆数据,同步成功，则更新待跟踪gps信息为成功，否则更新待跟踪gps信息为失败
								//this.synDeleteTruckGPSTask(truckGPSTask);
							}
						}
					}else{
						// 删除任务车辆
						truckTaskDao.deleteTruckTask(truckTaskId);
						// 删除任务车辆明细
						truckTaskDao.deleteTruckTaskDetail(truckTaskDetailId);
						if(StringUtils.isNotBlank(truckTask.getTruckGPSTaskId())){
							String truckGPSTaskId = truckTask.getTruckGPSTaskId();
							TruckGPSTaskEntity truckGPSTask = new TruckGPSTaskEntity();
							truckGPSTask.setId(truckGPSTaskId);
							//同步到gps操作类型 -删除
							truckGPSTask.setOperateType(TaskTruckConstant.GSP_OPERATE_TYPE_DELETE);
							truckTaskDao.deleteTruckTaskGPSDetail(truckGPSTaskId);
							//调用gps接口，同步跟踪车辆数据,同步成功，则更新待跟踪gps信息为成功，否则更新待跟踪gps信息为失败
							//this.synDeleteTruckGPSTask(truckGPSTask);
						}
					}
				}
				
			}
		}
	}
}