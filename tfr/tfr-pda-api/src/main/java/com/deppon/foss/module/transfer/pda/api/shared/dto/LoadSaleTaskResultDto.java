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
 *  PROJECT NAME  : tfr-pda-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/pda/api/shared/dto/LoadTaskResultDto.java
 *  
 *  FILE NAME          :LoadTaskResultDto.java
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
 * PROJECT NAME: tfr-pda-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.pda.api.shared.dto
 * FILE    NAME: LoadTaskResultDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.pda.api.shared.dto;

import java.math.BigDecimal;

/**
 * 建立装车任务返回给PDA的Dto
 * @author dp-332219
 * @date 2016-11-21
 */
public class LoadSaleTaskResultDto extends TaskResultDto{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**车辆额定载重*/
	private double vehicleDeadWeight;
	/**车辆额定净空*/
	private double vehicleDeadVolume;
	/**车型名称*/
	private String vehicleLengthName;
	/**快递转换体积参数***/
	private BigDecimal expressConvertParameter;
	
	/**
	 * Gets the 车辆额定载重.
	 *
	 * @return the 车辆额定载重
	 */
	public double getVehicleDeadWeight() {
		return vehicleDeadWeight;
	}
	
	/**
	 * Sets the 车辆额定载重.
	 *
	 * @param vehicleDeadWeight the new 车辆额定载重
	 */
	public void setVehicleDeadWeight(double vehicleDeadWeight) {
		this.vehicleDeadWeight = vehicleDeadWeight;
	}
	
	/**
	 * Gets the 车辆额定净空.
	 *
	 * @return the 车辆额定净空
	 */
	public double getVehicleDeadVolume() {
		return vehicleDeadVolume;
	}
	
	/**
	 * Sets the 车辆额定净空.
	 *
	 * @param vehicleDeadVolume the new 车辆额定净空
	 */
	public void setVehicleDeadVolume(double vehicleDeadVolume) {
		this.vehicleDeadVolume = vehicleDeadVolume;
	}

	/**
	 * Gets the 车型名称.
	 *
	 * @return the 车型名称
	 */
	public String getVehicleLengthName() {
		return vehicleLengthName;
	}

	/**
	 * Sets the 车型名称.
	 *
	 * @param vehicleLengthName the new 车型名称
	 */
	public void setVehicleLengthName(String vehicleLengthName) {
		this.vehicleLengthName = vehicleLengthName;
	}

	/**
	 * Gets the 快递转换体积参数.
	 *
	 * @return the 快递转换体积参数
	 */
	public BigDecimal getExpressConvertParameter() {
		return expressConvertParameter;
	}
	/**
	 * Sets the 快递转换体积参数.
	 *
	 * @param expressConvertParameter the new 快递转换体积参数
	 */
	public void setExpressConvertParameter(BigDecimal expressConvertParameter) {
		this.expressConvertParameter = expressConvertParameter;
	}

	
}