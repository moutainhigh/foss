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
 *  PROJECT NAME  : tfr-departure-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/api/shared/domain/ArrivalInfoEntity.java
 *  
 *  FILE NAME          :ArrivalInfoEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.departure.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 */
public class LoadDetailEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3703272448562684594L;

	/**********运单号************/
	private String waybillNo;
	
	/**********车牌号************/
	private String vehicleNo;
	
	/**********装车开始时间************/
	private Date loadStartTime;

	/**********装车完成时间************/
	private Date loadEndTime;
	
	/**********装车人名称************/
	private String loaderName;
	
	/**********装车人编码************/
	private String loaderCode;
	
	/**********装车部门编码************/
	private String loadOrgCode;
	
	/**********装车部门名称************/
	private String loadOrgName;
	
	/**********扫描件数************/
	private Integer scanQty;
	
	/**********装车数量************/
	private Integer loadQty;
	
	/**********状态************/
	private String taskState;
	
	/*******类型***********/
	private String taskType;

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public Date getLoadStartTime() {
		return loadStartTime;
	}

	public void setLoadStartTime(Date loadStartTime) {
		this.loadStartTime = loadStartTime;
	}

	public Date getLoadEndTime() {
		return loadEndTime;
	}

	public void setLoadEndTime(Date loadEndTime) {
		this.loadEndTime = loadEndTime;
	}

	public String getLoaderName() {
		return loaderName;
	}

	public void setLoaderName(String loaderName) {
		this.loaderName = loaderName;
	}

	public String getLoaderCode() {
		return loaderCode;
	}

	public void setLoaderCode(String loaderCode) {
		this.loaderCode = loaderCode;
	}

	public String getLoadOrgCode() {
		return loadOrgCode;
	}

	public void setLoadOrgCode(String loadOrgCode) {
		this.loadOrgCode = loadOrgCode;
	}


	public String getLoadOrgName() {
		return loadOrgName;
	}

	public void setLoadOrgName(String loadOrgName) {
		this.loadOrgName = loadOrgName;
	}

	public Integer getScanQty() {
		return scanQty;
	}

	public void setScanQty(Integer scanQty) {
		this.scanQty = scanQty;
	}

	public Integer getLoadQty() {
		return loadQty;
	}

	public void setLoadQty(Integer loadQty) {
		this.loadQty = loadQty;
	}

	public String getTaskState() {
		return taskState;
	}

	public void setTaskState(String taskState) {
		this.taskState = taskState;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}



}