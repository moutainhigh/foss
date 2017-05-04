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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/dto/PassOrderApplyDto.java
 * 
 *  FILE NAME     :PassOrderApplyDto.java
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
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.api.shared.dto
 * FILE    NAME: PassOrderApplyDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.scheduling.api.shared.dto;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PassOrderApplyEntity;

/**
 * 约车审核DTO
 * @author 104306-foss-wangLong
 * @date 2012-11-26 下午9:29:25
 */
public class PassOrderApplyDto implements Serializable {

	private static final long serialVersionUID = 2831030749744843093L;
	
	private PassOrderApplyEntity passOrderApplyEntity = new PassOrderApplyEntity();
	
	/** 备注 */
	private String notes;
	
	/** 司机编号 */
	private String driverCode;
	
	/** 司机小队 */
	private String driverGroup;
	
	/**  车型  */
	private String orderVehicleModel;
	
	/** 放行任务 */
	private String truckDepartId;
	
	/** 放行任务状态 */
	private String truckDepartStatus;

	/** 约车审核id */
	private String passOrderApplyId;
	
	/** 约车审核log */
	private List<AuditOrderApplyDto> auditOrderApplyDtoList;
	
	/**
	 * 获得notes
	 * @return the notes
	 */	
	public PassOrderApplyEntity getPassOrderApplyEntity() {
		return passOrderApplyEntity;
	}

	/**
	 * 设置notes
	 * @param notes the notes to set
	 */	
	public void setPassOrderApplyEntity(PassOrderApplyEntity passOrderApplyEntity) {
		this.passOrderApplyEntity = passOrderApplyEntity;
	}

	/**
	 * 获得notes
	 * @return the notes
	 */	
	public String getNotes() {
		return notes;
	}

	/**
	 * 设置notes
	 * @param notes the notes to set
	 */	
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	/**
	 * 获得driverCode
	 * @return the driverCode
	 */	
	public String getDriverCode() {
		return driverCode;
	}

	/**
	 * 设置driverCode
	 * @param driverCode the driverCode to set
	 */	
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	/**
	 * 获得truckDepartId
	 * @return the truckDepartId
	 */	
	public String getTruckDepartId() {
		return truckDepartId;
	}

	/**
	 * 设置truckDepartId
	 * @param truckDepartId the truckDepartId to set
	 */	
	public void setTruckDepartId(String truckDepartId) {
		this.truckDepartId = truckDepartId;
	}

	/**
	 * 获得truckDepartStatus
	 * @return the truckDepartStatus
	 */	
	public String getTruckDepartStatus() {
		return truckDepartStatus;
	}

	/**
	 * 设置truckDepartStatus
	 * @param truckDepartStatus the truckDepartStatus to set
	 */	
	public void setTruckDepartStatus(String truckDepartStatus) {
		this.truckDepartStatus = truckDepartStatus;
	}

	/**
	 * 设置passOrderApplyId
	 * @param passOrderApplyId the passOrderApplyId to set
	 */	
	public String getPassOrderApplyId() {
		return passOrderApplyId;
	}

	/**
	 * 设置passOrderApplyId
	 * @param passOrderApplyId the passOrderApplyId to set
	 */	
	public void setPassOrderApplyId(String passOrderApplyId) {
		this.passOrderApplyId = passOrderApplyId;
	}

	/**
	 * 获得auditOrderApplyDtoList
	 * @return the auditOrderApplyDtoList
	 */	
	public List<AuditOrderApplyDto> getAuditOrderApplyDtoList() {
		return auditOrderApplyDtoList;
	}

	/**
	 * 设置auditOrderApplyDtoList
	 * @param auditOrderApplyDtoList the auditOrderApplyDtoList to set
	 */	
	public void setAuditOrderApplyDtoList(
			List<AuditOrderApplyDto> auditOrderApplyDtoList) {
		this.auditOrderApplyDtoList = auditOrderApplyDtoList;
	}

	/**
	 * 获得driverGroup
	 * @return the driverGroup
	 */	
	public String getDriverGroup() {
		return driverGroup;
	}

	/**
	 * 设置driverGroup
	 * @param driverGroup the driverGroup to set
	 */	
	public void setDriverGroup(String driverGroup) {
		this.driverGroup = driverGroup;
	}

	/**
	 * 获得orderVehicleModel
	 * @return the orderVehicleModel
	 */	
	public String getOrderVehicleModel() {
		return orderVehicleModel;
	}

	/**
	 * 设置orderVehicleModel
	 * @param orderVehicleModel the orderVehicleModel to set
	 */	
	public void setOrderVehicleModel(String orderVehicleModel) {
		this.orderVehicleModel = orderVehicleModel;
	}
	
}