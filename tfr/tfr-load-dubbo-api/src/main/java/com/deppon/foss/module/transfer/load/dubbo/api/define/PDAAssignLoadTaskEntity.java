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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/pda/api/shared/domain/PDAAssignLoadTaskEntity.java
 *  
 *  FILE NAME          :PDAAssignLoadTaskEntity.java
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
 * FILE    NAME: PDAAssignLoadTaskEntity.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.dubbo.api.define;

import java.io.Serializable;
import java.util.List;

/**
 * PDA接口返回值:已分配装车任务
 * @author dp-duyi
 * @date 2012-11-6 下午12:25:29
 */
public class PDAAssignLoadTaskEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3024495662076812045L;
	private String id;
	/**deliverBillNo*/
	private String deliverBillNo;
	/**taskNo*/
	private String taskNo;
	/**vehicleNo*/
	private String vehicleNo;
	/**state*/
	private String state;
	/*月台号*/
	private String platformNo;
	/*装车类型*/
	private String loadTaskType;
	/**接驳点编码*/
	private String accessPointCode;
	/**接驳点名称**/
	private String accessPointName;
	/*到达部门编码*/
	private List<String> destOrgCodes;
	/*到达部门名称*/
	private List<String> destOrgNames;
	/**扫描总件数**/
	private Integer scanQtyTotal;
	//快递员
	private String tayller;
	public List<String> getDestOrgCodes() {
		return destOrgCodes;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setDestOrgCodes(List<String> destOrgCodes) {
		this.destOrgCodes = destOrgCodes;
	}

	public List<String> getDestOrgNames() {
		return destOrgNames;
	}

	public void setDestOrgNames(List<String> destOrgNames) {
		this.destOrgNames = destOrgNames;
	}

	public String getPlatformNo() {
		return platformNo;
	}

	public void setPlatformNo(String platformNo) {
		this.platformNo = platformNo;
	}

	public String getLoadTaskType() {
		return loadTaskType;
	}

	public void setLoadTaskType(String loadTaskType) {
		this.loadTaskType = loadTaskType;
	}

	/**
	 * Gets the deliverBillNo.
	 *
	 * @return the deliverBillNo
	 */
	public String getDeliverBillNo() {
		return deliverBillNo;
	}
	
	/**
	 * Sets the deliverBillNo.
	 *
	 * @param deliverBillNo the new deliverBillNo
	 */
	public void setDeliverBillNo(String deliverBillNo) {
		this.deliverBillNo = deliverBillNo;
	}
	
	/**
	 * Gets the taskNo.
	 *
	 * @return the taskNo
	 */
	public String getTaskNo() {
		return taskNo;
	}
	
	/**
	 * Sets the taskNo.
	 *
	 * @param taskNo the new taskNo
	 */
	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}
	
	/**
	 * Gets the vehicleNo.
	 *
	 * @return the vehicleNo
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}
	
	/**
	 * Sets the vehicleNo.
	 *
	 * @param vehicleNo the new vehicleNo
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	
	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	
	/**
	 * Sets the state.
	 *
	 * @param state the new state
	 */
	public void setState(String state) {
		this.state = state;
	}

	public String getAccessPointCode() {
		return accessPointCode;
	}

	public void setAccessPointCode(String accessPointCode) {
		this.accessPointCode = accessPointCode;
	}

	public String getAccessPointName() { 
		return accessPointName;
	}

	public void setAccessPointName(String accessPointName) {
		this.accessPointName = accessPointName;
	}

	public Integer getScanQtyTotal() {
		return scanQtyTotal;
	}

	public void setScanQtyTotal(Integer scanQtyTotal) {
		this.scanQtyTotal = scanQtyTotal;
	}

	public String getTayller() {
		return tayller;
	}

	public void setTayller(String tayller) {
		this.tayller = tayller;
	}

	
}