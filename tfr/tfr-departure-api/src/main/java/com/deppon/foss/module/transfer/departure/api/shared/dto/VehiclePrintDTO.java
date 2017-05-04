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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/api/shared/dto/VehiclePrintDTO.java
 *  
 *  FILE NAME          :VehiclePrintDTO.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.departure.api.shared.dto;

import com.deppon.foss.framework.entity.BaseEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class VehiclePrintDTO.
 */
public class VehiclePrintDTO extends BaseEntity{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3703272448562684594L;

	/** ***********车牌号**************. */
	private String vehicleNo;        
	
	/** ***********所述车队名称**************. */
	private String vehicleMotorcadeName;     
	
	/** ***********直属部门组别**************. */
	private String vehicleOrganizationName;      
	
	/** ***********车牌号扫描码**************. */
	private String vehicleCode;

	/** ***********员工编号**************. */
	private String userCode;
	
	/** ***********打印日期**************. */
	private String printTime;
	/**
	 * Gets the vehicle no.
	 *
	 * @return the vehicle no
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * Sets the vehicle no.
	 *
	 * @param vehicleNo the new vehicle no
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * Gets the vehicle motorcade name.
	 *
	 * @return the vehicle motorcade name
	 */
	public String getVehicleMotorcadeName() {
		return vehicleMotorcadeName;
	}

	/**
	 * Sets the vehicle motorcade name.
	 *
	 * @param vehicleMotorcadeName the new vehicle motorcade name
	 */
	public void setVehicleMotorcadeName(String vehicleMotorcadeName) {
		this.vehicleMotorcadeName = vehicleMotorcadeName;
	}

	/**
	 * Gets the vehicle organization name.
	 *
	 * @return the vehicle organization name
	 */
	public String getVehicleOrganizationName() {
		return vehicleOrganizationName;
	}

	/**
	 * Sets the vehicle organization name.
	 *
	 * @param vehicleOrganizationName the new vehicle organization name
	 */
	public void setVehicleOrganizationName(String vehicleOrganizationName) {
		this.vehicleOrganizationName = vehicleOrganizationName;
	}

	/**
	 * Gets the vehicle code.
	 *
	 * @return the vehicle code
	 */
	public String getVehicleCode() {
		return vehicleCode;
	}

	/**
	 * Sets the vehicle code.
	 *
	 * @param vehicleCode the new vehicle code
	 */
	public void setVehicleCode(String vehicleCode) {
		this.vehicleCode = vehicleCode;
	}

	/**
	 * Gets the user code.
	 *
	 * @return the user code
	 */
	public String getUserCode() {
		return userCode;
	}

	/**
	 * Sets the user code.
	 *
	 * @param userCode the new user code
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	/**
	 * Gets the prints the time.
	 *
	 * @return the prints the time
	 */
	public String getPrintTime() {
		return printTime;
	}

	/**
	 * Sets the prints the time.
	 *
	 * @param printTime the new prints the time
	 */
	public void setPrintTime(String printTime) {
		this.printTime = printTime;
	}		 
	
	
}