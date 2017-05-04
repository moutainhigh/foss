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
 *  PROJECT NAME  : tfr-arrival-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/arrival/api/shared/dto/PlatformDTO.java
 *  
 *  FILE NAME          :PlatformDTO.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.arrival.api.shared.dto;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * 查到的月台的信息.
 *
 * @author IBM-liubinbin
 * @date 2012-11-5 下午4:11:08
 */
public class PlatformDTO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3703272448562684594L;

	/** *********** 月台号 ***************. */
	private String platformNo;

	/** *********** 停用车型 ***************. */
	private String vehicleType;

	/** *********** 线路 ***************. */
	private String vehicleLengthValue;

	/** *********** 月台状态 ***************. */
	private String platformStatus;

	/** *********** 可用时间 ***************. */
	private String effectiveTime;

	/** *********** 是否有升降 ***************. */
	private String hasLift;

	/** *********** 虚拟编码 ***************. */
	private String virtualCode;

	/**
	 * 获取 *********** 月台号***************.
	 * 
	 * @return the *********** 月台号***************
	 */
	public String getPlatformNo() {
		return platformNo;
	}

	/**
	 * 设置 *********** 月台号***************.
	 * 
	 * @param platformNo
	 *            the new *********** 月台号***************
	 */
	public void setPlatformNo(String platformNo) {
		this.platformNo = platformNo;
	}

	/**
	 * 获取 *********** 停用车型 ***************.
	 * 
	 * @return the *********** 停用车型 ***************
	 */
	public String getVehicleType() {
		return vehicleType;
	}

	/**
	 * 设置 *********** 停用车型 ***************.
	 * 
	 * @param vehicleType
	 *            the new *********** 停用车型 ***************
	 */
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	/**
	 * 获取 *********** 线路 ***************.
	 * 
	 * @return the *********** 线路 ***************
	 */
	public String getVehicleLengthValue() {
		return vehicleLengthValue;
	}

	/**
	 * 设置 *********** 线路 ***************.
	 * 
	 * @param vehicleLengthValue
	 *            the new *********** 线路 ***************
	 */
	public void setVehicleLengthValue(String vehicleLengthValue) {
		this.vehicleLengthValue = vehicleLengthValue;
	}

	/**
	 * 获取 *********** 月台状态 ***************.
	 * 
	 * @return the *********** 月台状态 ***************
	 */
	public String getPlatformStatus() {
		return platformStatus;
	}

	/**
	 * 设置 *********** 月台状态 ***************.
	 * 
	 * @param platformStatus
	 *            the new *********** 月台状态 ***************
	 */
	public void setPlatformStatus(String platformStatus) {
		this.platformStatus = platformStatus;
	}

	/**
	 * 获取 *********** 可用时间 ***************.
	 * 
	 * @return the *********** 可用时间 ***************
	 */
	public String getEffectiveTime() {
		return effectiveTime;
	}

	/**
	 * 设置 *********** 可用时间 ***************.
	 * 
	 * @param effectiveTime
	 *            the new *********** 可用时间 ***************
	 */
	public void setEffectiveTime(String effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	/**
	 * 获取 *********** 是否有升降 ***************.
	 * 
	 * @return the *********** 是否有升降 ***************
	 */
	public String getHasLift() {
		return hasLift;
	}

	/**
	 * 设置 *********** 是否有升降 ***************.
	 * 
	 * @param hasLift
	 *            the new *********** 是否有升降 ***************
	 */
	public void setHasLift(String hasLift) {
		this.hasLift = hasLift;
	}

	/**
	 * Gets the virtual code.
	 *
	 * @return the virtual code
	 */
	public String getVirtualCode() {
		return virtualCode;
	}

	/**
	 * Sets the virtual code.
	 *
	 * @param virtualCode the new virtual code
	 */
	public void setVirtualCode(String virtualCode) {
		this.virtualCode = virtualCode;
	}

}