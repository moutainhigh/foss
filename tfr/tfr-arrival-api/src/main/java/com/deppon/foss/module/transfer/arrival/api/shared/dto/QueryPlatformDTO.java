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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/arrival/api/shared/dto/QueryPlatformDTO.java
 *  
 *  FILE NAME          :QueryPlatformDTO.java
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


/**
 * 
 * 操作到达记录时，传值到后台进行验证，操作
 * 
 * @author IBM-liubinbin
 * @date 2012-11-5 下午4:11:08
 */
public class QueryPlatformDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3703272448562684594L;

	/************* 车牌号 ****************/
	private String vehicleNo;
	
	/**************是否显示空月台*************/
	private String onlyNullPlatform;
	
	/**************任务车辆明细ID*************/
	private String truckTaskDetailId;
	
	/**************交接单号************/
	private String handoverNo;

	/**
	 * 获取 *********** 交接编号、车牌号 ***************.
	 *
	 * @return the *********** 交接编号、车牌号 ***************
	 */
	public String getVehicleNo(){
		return vehicleNo;
	}

	/**
	 * 设置 *********** 交接编号、车牌号 ***************.
	 *
	 * @param vehicleNo the new *********** 交接编号、车牌号 ***************
	 */
	public void setVehicleNo(String vehicleNo){
		this.vehicleNo = vehicleNo;
	}

	/**
	 * 获取 ************是否显示空月台************.
	 *
	 * @return the ************是否显示空月台************
	 */
	public String getOnlyNullPlatform(){
		return onlyNullPlatform;
	}

	/**
	 * 设置 ************是否显示空月台************.
	 *
	 * @param onlyNullPlatform the new ************是否显示空月台************
	 */
	public void setOnlyNullPlatform(String onlyNullPlatform){
		this.onlyNullPlatform = onlyNullPlatform;
	}

	public String getTruckTaskDetailId() {
		return truckTaskDetailId;
	}

	public void setTruckTaskDetailId(String truckTaskDetailId) {
		this.truckTaskDetailId = truckTaskDetailId;
	}

	public String getHandoverNo() {
		return handoverNo;
	}

	public void setHandoverNo(String handoverNo) {
		this.handoverNo = handoverNo;
	}

}