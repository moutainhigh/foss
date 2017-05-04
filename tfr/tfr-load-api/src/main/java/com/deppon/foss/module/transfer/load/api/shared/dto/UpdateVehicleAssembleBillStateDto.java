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
 *  PROJECT NAME  : tfr-load-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/dto/UpdateVehicleAssembleBillStateDto.java
 *  
 *  FILE NAME          :UpdateVehicleAssembleBillStateDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.api.shared.dto;

import java.io.Serializable;

/** 
 * @className: UpdateVehicleAssembleBillStateDto
 * @author: ShiWei shiwei@outlook.com
 * @description: TODO
 * @date: 2012-11-26 下午8:28:21
 * 
 */
public class UpdateVehicleAssembleBillStateDto implements Serializable {

	private static final long serialVersionUID = -6432129992596015654L;
	//配载车次号
	private String vehicleAssembleNo;
	//目标状态
	private int targetState;
	
	public String getVehicleAssembleNo() {
		return vehicleAssembleNo;
	}
	public void setVehicleAssembleNo(String vehicleAssembleNo) {
		this.vehicleAssembleNo = vehicleAssembleNo;
	}
	public int getTargetState() {
		return targetState;
	}
	public void setTargetState(int targetState) {
		this.targetState = targetState;
	}
	
}