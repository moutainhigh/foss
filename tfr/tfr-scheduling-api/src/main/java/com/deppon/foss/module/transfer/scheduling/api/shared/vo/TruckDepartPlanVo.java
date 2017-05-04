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
 *  
 *  PROJECT NAME  : tfr-scheduling-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/vo/TruckDepartPlanVo.java
 * 
 *  FILE NAME     :TruckDepartPlanVo.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-scheduling-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.api.shared.vo
 * FILE    NAME: TruckDepartPlanVo.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.api.shared.vo;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DriverAssociationDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckDepartPlanUpdateEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.CarInfoDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.ForecastForPlanDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.MergeLogDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.PlanVehicleDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDto;

/**
 * 发车计划VO
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-11-22 下午3:06:09 *
 */
public class TruckDepartPlanVo implements java.io.Serializable {

	private static final long serialVersionUID = 9186356163265383504L;

	/**
	 * 计划列表
	 */
	private List<TruckDepartPlanDto> planList;
	/**
	 * 计划明细列表
	 */
	private List<TruckDepartPlanDetailDto> detailList;
	/**
	 * 修改记录日志列表
	 */
	private List<TruckDepartPlanUpdateEntity> logList;
	/**
	 * 计划Dto
	 */
	private TruckDepartPlanDto planDto;
	/**
	 * 计划明细Dto
	 */
	private TruckDepartPlanDetailDto detailDto;
	/**
	 * 修改记录日志
	 */
	private TruckDepartPlanUpdateEntity log;
	/**
	 * 用户查询车辆
	 */
	private CarInfoDto carDto;
	/**
	 * 返回查询车辆结果
	 */
	private List<CarInfoDto> carList;
	/**
	 * 所管辖的组织
	 */
	private List<OrgAdministrativeInfoEntity> orgList;
	/**
	 * 人员信息
	 */
	private EmployeeEntity emp;
	/**
	 * 车辆信息
	 */
	private PlanVehicleDto vehicle;
	/**
	 * 自有车司机信息
	 */
	private DriverAssociationDto driver;
	/**
	 * 合并记录列表
	 */
	private List<MergeLogDto> mergeLogs;
	/**
	 * 货量预测信息
	 */
	private ForecastForPlanDto forecastDto;
	/**
	 * 部门信息
	 */
	private OrgAdministrativeInfoEntity orgInfo;
	/**
	 * 计划已经存在
	 */
	private String planExsit;

	//部门名称
	private List<String> origName;
	
	/**
	 * 获取 计划列表.
	 * 
	 * @return the 计划列表
	 */
	public List<TruckDepartPlanDto> getPlanList() {
		return planList;
	}

	/**
	 * 设置 计划列表.
	 * 
	 * @param planList
	 *            the new 计划列表
	 */
	public void setPlanList(List<TruckDepartPlanDto> planList) {
		this.planList = planList;
	}

	/**
	 * 获取 计划明细列表.
	 * 
	 * @return the 计划明细列表
	 */
	public List<TruckDepartPlanDetailDto> getDetailList() {
		return detailList;
	}

	/**
	 * 设置 计划明细列表.
	 * 
	 * @param detailList
	 *            the new 计划明细列表
	 */
	public void setDetailList(List<TruckDepartPlanDetailDto> detailList) {
		this.detailList = detailList;
	}

	/**
	 * 获取 计划Dto.
	 * 
	 * @return the 计划Dto
	 */
	public TruckDepartPlanDto getPlanDto() {
		return planDto;
	}

	/**
	 * 设置 计划Dto.
	 * 
	 * @param planDto
	 *            the new 计划Dto
	 */
	public void setPlanDto(TruckDepartPlanDto planDto) {
		this.planDto = planDto;
	}

	/**
	 * 获取 计划明细Dto.
	 * 
	 * @return the 计划明细Dto
	 */
	public TruckDepartPlanDetailDto getDetailDto() {
		return detailDto;
	}

	/**
	 * 设置 计划明细Dto.
	 * 
	 * @param detailDto
	 *            the new 计划明细Dto
	 */
	public void setDetailDto(TruckDepartPlanDetailDto detailDto) {
		this.detailDto = detailDto;
	}

	/**
	 * 获取 修改记录日志列表.
	 * 
	 * @return the 修改记录日志列表
	 */
	public List<TruckDepartPlanUpdateEntity> getLogList() {
		return logList;
	}

	/**
	 * 设置 修改记录日志列表.
	 * 
	 * @param logList
	 *            the new 修改记录日志列表
	 */
	public void setLogList(List<TruckDepartPlanUpdateEntity> logList) {
		this.logList = logList;
	}

	/**
	 * 获取 修改记录日志.
	 * 
	 * @return the 修改记录日志
	 */
	public TruckDepartPlanUpdateEntity getLog() {
		return log;
	}

	/**
	 * 设置 修改记录日志.
	 * 
	 * @param log
	 *            the new 修改记录日志
	 */
	public void setLog(TruckDepartPlanUpdateEntity log) {
		this.log = log;
	}

	/**
	 * 获取 用户查询车辆.
	 * 
	 * @return the 用户查询车辆
	 */
	public CarInfoDto getCarDto() {
		return carDto;
	}

	/**
	 * 设置 用户查询车辆.
	 * 
	 * @param carDto
	 *            the new 用户查询车辆
	 */
	public void setCarDto(CarInfoDto carDto) {
		this.carDto = carDto;
	}

	/**
	 * 获取 返回查询车辆结果.
	 * 
	 * @return the 返回查询车辆结果
	 */
	public List<CarInfoDto> getCarList() {
		return carList;
	}

	/**
	 * 设置 返回查询车辆结果.
	 * 
	 * @param carList
	 *            the new 返回查询车辆结果
	 */
	public void setCarList(List<CarInfoDto> carList) {
		this.carList = carList;
	}

	/**
	 * 获取 所管辖的组织.
	 * 
	 * @return the 所管辖的组织
	 */
	public List<OrgAdministrativeInfoEntity> getOrgList() {
		return orgList;
	}

	/**
	 * 设置 所管辖的组织.
	 * 
	 * @param orgList
	 *            the new 所管辖的组织
	 */
	public void setOrgList(List<OrgAdministrativeInfoEntity> orgList) {
		this.orgList = orgList;
	}

	/**
	 * 获取 人员信息.
	 * 
	 * @return the 人员信息
	 */
	public EmployeeEntity getEmp() {
		return emp;
	}

	/**
	 * 设置 人员信息.
	 * 
	 * @param emp
	 *            the new 人员信息
	 */
	public void setEmp(EmployeeEntity emp) {
		this.emp = emp;
	}

	/**
	 * 获取 车辆信息.
	 * 
	 * @return the 车辆信息
	 */
	public PlanVehicleDto getVehicle() {
		return vehicle;
	}

	/**
	 * 设置 车辆信息.
	 * 
	 * @param vehicle
	 *            the new 车辆信息
	 */
	public void setVehicle(PlanVehicleDto vehicle) {
		this.vehicle = vehicle;
	}

	/**
	 * 获取 自有车司机信息.
	 * 
	 * @return the 自有车司机信息
	 */
	public DriverAssociationDto getDriver() {
		return driver;
	}

	/**
	 * 设置 自有车司机信息.
	 * 
	 * @param driver
	 *            the new 自有车司机信息
	 */
	public void setDriver(DriverAssociationDto driver) {
		this.driver = driver;
	}

	/**
	 * 获取 合并记录列表.
	 * 
	 * @return the 合并记录列表
	 */
	public List<MergeLogDto> getMergeLogs() {
		return mergeLogs;
	}

	/**
	 * 设置 合并记录列表.
	 * 
	 * @param mergeLogs
	 *            the new 合并记录列表
	 */
	public void setMergeLogs(List<MergeLogDto> mergeLogs) {
		this.mergeLogs = mergeLogs;
	}

	/**
	 * 货量预测信息
	 * 
	 * @return
	 */
	public ForecastForPlanDto getForecastDto() {
		return forecastDto;
	}

	/**
	 * 货量预测信息
	 * 
	 * @param forecastDto
	 */
	public void setForecastDto(ForecastForPlanDto forecastDto) {
		this.forecastDto = forecastDto;
	}

	/**
	 * 部门信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-1-12 下午5:08:27
	 */
	public OrgAdministrativeInfoEntity getOrgInfo() {
		return orgInfo;
	}

	/**
	 * 部门信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-1-12 下午5:08:29
	 */
	public void setOrgInfo(OrgAdministrativeInfoEntity orgInfo) {
		this.orgInfo = orgInfo;
	}

	public String getPlanExsit() {
		return planExsit;
	}

	public void setPlanExsit(String planExsit) {
		this.planExsit = planExsit;
	}

	public List<String> getOrigName() {
		return origName;
	}

	public void setOrigName(List<String> origName) {
		this.origName = origName;
	}
	
}