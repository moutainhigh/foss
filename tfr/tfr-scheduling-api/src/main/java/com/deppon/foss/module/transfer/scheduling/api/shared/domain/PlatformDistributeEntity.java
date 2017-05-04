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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/domain/PlatformDistributeEntity.java
 * 
 *  FILE NAME     :PlatformDistributeEntity.java
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
package com.deppon.foss.module.transfer.scheduling.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;
import com.deppon.foss.module.transfer.scheduling.api.shared.exception.PlatformDispatchException;

/**
 * 分配月台实体
 * @author 104306-foss-wangLong
 * @date 2012-10-31 下午3:13:16
 */
public class PlatformDistributeEntity extends BaseEntity {
	
	private static final long serialVersionUID = 41539158980198752L;
	
	/** 外场编号 */
	private String transferCenterNo;
		
	/** 外场名称 */
	private String transferCenterName;
		
	/** 月台号虚拟编号  */
	private String platformNo;
		
	/** 使用启始时间 */
	private Date useStartTime;
		
	/** 使用结束时间 */
	private Date useEndTime;
		
	/** 车辆类型 */
	private String vehicleModel;
		
	/** 车牌号 */
	private String vehicleNo;
		
	/** 装车任务编号 */
	private String loadTaskNo;
		
	/** 状态 */
	private String status;
		
	/** 分配类型  计划 | 实际 */
	private String type;
	
	/** 计划来源 */
	private String scheduleSource;
	
	/**
	 * 获得transferCenterNo
	 * @return the transferCenterNo
	 */
	public String getTransferCenterNo() {
		return transferCenterNo;
	}
	
	/**
	 * 获得transferCenterName
	 * @return the transferCenterName
	 */
	public String getTransferCenterName() {
		return transferCenterName;
	}
	
	/**
	 * 获得platformNo
	 * @return the platformNo
	 */
	public String getPlatformNo() {
		return platformNo;
	}
	
	/**
	 * 获得useStartTime
	 * @return the useStartTime
	 */
	public Date getUseStartTime() {
		return useStartTime;
	}
	
	/**
	 * 获得useEndTime
	 * @return the useEndTime
	 */
	public Date getUseEndTime() {
		return useEndTime;
	}
	
	/**
	 * 获得vehicleModel
	 * @return the vehicleModel
	 */
	public String getVehicleModel() {
		return vehicleModel;
	}
	
	/**
	 * 获得vehicleNo
	 * @return the vehicleNo
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}
	
	/**
	 * 获得loadTaskNo
	 * @return the loadTaskNo
	 */
	public String getLoadTaskNo() {
		return loadTaskNo;
	}
	
	/**
	 * 获得status
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * 获得type
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * 获得scheduleSource
	 * @return the scheduleSource
	 */
	public String getScheduleSource() {
		return scheduleSource;
	}
	
	/**
	 * 设置transferCenterNo
	 * @param transferCenterNo the transferCenterNo to set
	 */
	public void setTransferCenterNo(String transferCenterNo) {
		this.transferCenterNo = transferCenterNo;
	}
	
	/**
	 * 设置transferCenterName
	 * @param transferCenterName the transferCenterName to set
	 */
	public void setTransferCenterName(String transferCenterName) {
		this.transferCenterName = transferCenterName;
	}
	
	/**
	 * 设置platformNo
	 * @param platformNo the platformNo to set
	 */
	public void setPlatformNo(String platformNo) {
		this.platformNo = platformNo;
	}
	
	/**
	 * 设置useStartTime
	 * @param useStartTime the useStartTime to set
	 */
	@DateFormat
	public void setUseStartTime(Date useStartTime) {
		if(useStartTime.equals(getUseEndTime())){
			throw new PlatformDispatchException("结束时间不能等于开始时间");
		}else{
			this.useStartTime = useStartTime;
		}

	}
	
	/**
	 * 设置useEndTime
	 * @param useEndTime the useEndTime to set
	 */
	@DateFormat
	public void setUseEndTime(Date useEndTime) {
		if(useEndTime.equals(getUseStartTime())){
			throw new PlatformDispatchException("结束时间不能等于开始时间");
		}else{
			this.useEndTime = useEndTime;
		}
		
	}
	
	/**
	 * 设置vehicleModel
	 * @param vehicleModel the vehicleModel to set
	 */
	public void setVehicleModel(String vehicleModel) {
		this.vehicleModel = vehicleModel;
	}
	
	/**
	 * 设置vehicleNo
	 * @param vehicleNo the vehicleNo to set
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	
	/**
	 * 设置loadTaskNo
	 * @param loadTaskNo the loadTaskNo to set
	 */
	public void setLoadTaskNo(String loadTaskNo) {
		this.loadTaskNo = loadTaskNo;
	}
	
	/**
	 * 设置status
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * 设置type
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * 设置scheduleSource
	 * @param scheduleSource the scheduleSource to set
	 */
	public void setScheduleSource(String scheduleSource) {
		this.scheduleSource = scheduleSource;
	}
	
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[transferCenterNo,").append(transferCenterNo).append("]");
		stringBuilder.append("[transferCenterName,").append(transferCenterName).append("]");
		stringBuilder.append("[platformNo,").append(platformNo).append("]");
		stringBuilder.append("[useStartTime,").append(useStartTime).append("]");
		stringBuilder.append("[useEndTime,").append(useEndTime).append("]");
		stringBuilder.append("[vehicleModel,").append(vehicleModel).append("]");
		stringBuilder.append("[vehicleNo,").append(vehicleNo).append("]");
		stringBuilder.append("[loadTaskNo,").append(loadTaskNo).append("]");
		stringBuilder.append("[status,").append(status).append("]");
		stringBuilder.append("[type,").append(type).append("]");
		stringBuilder.append("[scheduleSource,").append(scheduleSource).append("]");
		return stringBuilder.toString();
	}
}