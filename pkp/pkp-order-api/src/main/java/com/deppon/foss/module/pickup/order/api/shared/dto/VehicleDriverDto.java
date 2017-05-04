/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 038590-foss-wanghui
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
 * PROJECT NAME	: pkp-order-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/api/shared/dto/VehicleDriverDto.java
 * 
 * FILE NAME        	: VehicleDriverDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.api.shared.dto;

import java.io.Serializable;

/**
 * 车辆司机DTO
 * 
 * @author 038590-foss-wanghui
 * @date 2012-12-18 下午9:16:51
 */
public class VehicleDriverDto implements Serializable {

	private static final long serialVersionUID = 7303923818630104830L;
	/** 
	 * 区域类型（接货or送货）
	 */
	private String regionType;
	/** 
	 * 区域大小
	 */
	private String regionNature;
	/** 
	 * 是否激活
	 */
	private String active;
	/** 
	 * 绑定状态
	 */
	private String status;
	/** 
	 * 排班类型
	 */
	private String schedulingType;
	/** 
	 * 排班状态
	 */
	private String schedulingStatus;
	/** 
	 * 计划状态
	 */
	private String planType;
	/** 
	 * 组织code
	 */
	private String orgCode;
	/** 
	 * 车牌号
	 */
	private String vehicleNo;
	/** 
	 * 司机code
	 */
	private String driverCode;

	/**
	 * @return the vehicleNo
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * @param vehicleNo the vehicleNo to set
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * @return the driverCode
	 */
	public String getDriverCode() {
		return driverCode;
	}

	/**
	 * @param driverCode the driverCode to set
	 */
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	/**
	 * @return the regionType
	 */
	public String getRegionType() {
		return regionType;
	}

	/**
	 * @param regionType the regionType to set
	 */
	public void setRegionType(String regionType) {
		this.regionType = regionType;
	}

	/**
	 * @return the regionNature
	 */
	public String getRegionNature() {
		return regionNature;
	}

	/**
	 * @param regionNature the regionNature to set
	 */
	public void setRegionNature(String regionNature) {
		this.regionNature = regionNature;
	}

	/**
	 * @return the active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the schedulingType
	 */
	public String getSchedulingType() {
		return schedulingType;
	}

	/**
	 * @param schedulingType the schedulingType to set
	 */
	public void setSchedulingType(String schedulingType) {
		this.schedulingType = schedulingType;
	}

	/**
	 * @return the schedulingStatus
	 */
	public String getSchedulingStatus() {
		return schedulingStatus;
	}

	/**
	 * @param schedulingStatus the schedulingStatus to set
	 */
	public void setSchedulingStatus(String schedulingStatus) {
		this.schedulingStatus = schedulingStatus;
	}

	/**
	 * @return the planType
	 */
	public String getPlanType() {
		return planType;
	}

	/**
	 * @param planType the planType to set
	 */
	public void setPlanType(String planType) {
		this.planType = planType;
	}

	/**
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * @param orgCode the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

}