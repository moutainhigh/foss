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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/pda/api/shared/domain/PDAAssignUnloadBillEntity.java
 *  
 *  FILE NAME          :PDAAssignUnloadBillEntity.java
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
 * PACKAGE NAME: com.deppon.foss.module.transfer.pda.api.shared.domain
 * FILE    NAME: PDAAssignUnloadBillEntity.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.pda.api.shared.domain;

import java.io.Serializable;

/**
 * 已分配卸车单据
 * @author dp-duyi
 * @date 2012-12-26 下午4:19:17
 */
public class PDAAssignUnloadBillEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1260261225089843338L;
	/**单据编号*/
	private String billNo;
	/**任务编号*/
	private String taskNo;
	/**车牌号*/
	private String vehicleNo;
	/**卸车状态:未开始、进行中*/
	private String state;
	/**月台号*/
	private String platformNo;
	/**月台虚拟编号*/
	private String platformVirtualCode;
	/**单据类型*/
	private String unloadOrderType;
	
	/**
	 * Gets the 单据编号.
	 *
	 * @return the 单据编号
	 */
	public String getBillNo() {
		return billNo;
	}
	
	/**
	 * Sets the 单据编号.
	 *
	 * @param billNo the new 单据编号
	 */
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	
	/**
	 * Gets the 任务编号.
	 *
	 * @return the 任务编号
	 */
	public String getTaskNo() {
		return taskNo;
	}
	
	/**
	 * Sets the 任务编号.
	 *
	 * @param taskNo the new 任务编号
	 */
	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}
	
	/**
	 * Gets the 车牌号.
	 *
	 * @return the 车牌号
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}
	
	/**
	 * Sets the 车牌号.
	 *
	 * @param vehicleNo the new 车牌号
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	
	/**
	 * Gets the 卸车状态:未开始、进行中.
	 *
	 * @return the 卸车状态:未开始、进行中
	 */
	public String getState() {
		return state;
	}
	
	/**
	 * Sets the 卸车状态:未开始、进行中.
	 *
	 * @param state the new 卸车状态:未开始、进行中
	 */
	public void setState(String state) {
		this.state = state;
	}
	
	/**
	 * Gets the 月台号.
	 *
	 * @return the 月台号
	 */
	public String getPlatformNo() {
		return platformNo;
	}
	
	/**
	 * Sets the 月台号.
	 *
	 * @param platformNo the new 月台号
	 */
	public void setPlatformNo(String platformNo) {
		this.platformNo = platformNo;
	}
	
	/**
	 * Gets the 月台虚拟编号.
	 *
	 * @return the 月台虚拟编号
	 */
	public String getPlatformVirtualCode() {
		return platformVirtualCode;
	}
	
	/**
	 * Sets the 月台虚拟编号.
	 *
	 * @param platformVirtualCode the new 月台虚拟编号
	 */
	public void setPlatformVirtualCode(String platformVirtualCode) {
		this.platformVirtualCode = platformVirtualCode;
	}
	
	/**
	 * Gets the 卸车单据类型.
	 *
	 * @return the 卸车单据类型
	 */
	public String getUnloadOrderType() {
		return unloadOrderType;
	}
	
	/**
	 * Sets the 卸车单据类型.
	 *
	 * @param unloadOrderType the new 卸车单据类型
	 */
	public void setUnloadOrderType(String unloadOrderType) {
		this.unloadOrderType = unloadOrderType;
	}
}