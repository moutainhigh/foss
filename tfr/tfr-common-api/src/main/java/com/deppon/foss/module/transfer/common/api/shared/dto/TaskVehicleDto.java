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
 *  PROJECT NAME  : tfr-common-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/common/api/shared/dto/TaskVehicleDto.java
 *  
 *  FILE NAME          :TaskVehicleDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-gps-itf
 * PACKAGE NAME: com.deppon.foss.module.transfer.gps.server.dto
 * FILE    NAME: TaskVehicleDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.common.api.shared.dto;

/**
 * 任务车辆信息
 * @author 046130-foss-xuduowei
 * @date 2012-11-16 下午5:31:16
 */
public class TaskVehicleDto {
	/**
	 * GPS任务车辆唯一标示号
	 */
	private String vehicleId;
	/**
	 * 车辆任务id
	 */
	private String truckTaskId;
	/**
	 * 出发部门编码
	 */
	private String startDept;
	/**
	 * 到达部门编码
	 */
	private String arrivalDept;
	/**
	 * 车牌号
	 */
	private String vehicleNo;
	/**
	 * 预计发车时间(格式:2012-01-01 00:00:00)
	 */
	private String startTime;
	/**
	 * 装车重量(千克)
	 */
	private float weight;
	/**
	 * 装车体积(立方米)
	 */
	private float cubage;
	/**
	 * 操作标识(0:新增,1:删除)默认0
	 */
	private int isDeleted;
	/**
	 * 司机工号
	 */
	private String driverCode;
	/**
	 * 司机姓名
	 */
	private String dirverName;
	/**
	 * 虚拟路线编码
	 */
	private String lineCode;
	
	/**
	 * 获取 任务车辆唯一标示号.
	 *
	 * @return the 任务车辆唯一标示号
	 */
	public String getVehicleId() {
		return vehicleId;
	}
	
	/**
	 * 设置 任务车辆唯一标示号.
	 *
	 * @param vehicleId the new 任务车辆唯一标示号
	 */
	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}
	
	/**
	 * 获取 出发部门编码.
	 *
	 * @return the 出发部门编码
	 */
	public String getStartDept() {
		return startDept;
	}
	
	/**
	 * 设置 出发部门编码.
	 *
	 * @param startDept the new 出发部门编码
	 */
	public void setStartDept(String startDept) {
		this.startDept = startDept;
	}
	
	/**
	 * 获取 到达部门编码.
	 *
	 * @return the 到达部门编码
	 */
	public String getArrivalDept() {
		return arrivalDept;
	}
	
	/**
	 * 设置 到达部门编码.
	 *
	 * @param arrivalDept the new 到达部门编码
	 */
	public void setArrivalDept(String arrivalDept) {
		this.arrivalDept = arrivalDept;
	}
	
	/**
	 * 获取 车牌号.
	 *
	 * @return the 车牌号
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}
	
	/**
	 * 设置 车牌号.
	 *
	 * @param vehicleNo the new 车牌号
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	
	/**
	 * 获取 预计发车时间(格式:2012-01-01 00:00:00).
	 *
	 * @return the 预计发车时间(格式:2012-01-01 00:00:00)
	 */
	public String getStartTime() {
		return startTime;
	}
	
	/**
	 * 设置 预计发车时间(格式:2012-01-01 00:00:00).
	 *
	 * @param startTime the new 预计发车时间(格式:2012-01-01 00:00:00)
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	/**
	 * 获取 装车重量(千克).
	 *
	 * @return the 装车重量(千克)
	 */
	public float getWeight() {
		return weight;
	}
	
	/**
	 * 设置 装车重量(千克).
	 *
	 * @param weight the new 装车重量(千克)
	 */
	public void setWeight(float weight) {
		this.weight = weight;
	}
	
	/**
	 * 获取 装车体积(立方米).
	 *
	 * @return the 装车体积(立方米)
	 */
	public float getCubage() {
		return cubage;
	}
	
	/**
	 * 设置 装车体积(立方米).
	 *
	 * @param cubage the new 装车体积(立方米)
	 */
	public void setCubage(float cubage) {
		this.cubage = cubage;
	}
	
	/**
	 * 获取 操作标识(0:新增,1:删除)默认0.
	 *
	 * @return the 操作标识(0:新增,1:删除)默认0
	 */
	public int getIsDeleted() {
		return isDeleted;
	}
	
	/**
	 * 设置 操作标识(0:新增,1:删除)默认0.
	 *
	 * @param isDeleted the new 操作标识(0:新增,1:删除)默认0
	 */
	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getTruckTaskId() {
		return truckTaskId;
	}

	public String getDriverCode() {
		return driverCode;
	}

	public String getDirverName() {
		return dirverName;
	}

	public String getLineCode() {
		return lineCode;
	}

	public void setTruckTaskId(String truckTaskId) {
		this.truckTaskId = truckTaskId;
	}

	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	public void setDirverName(String dirverName) {
		this.dirverName = dirverName;
	}

	public void setLineCode(String lineCode) {
		this.lineCode = lineCode;
	} 
	
	
}