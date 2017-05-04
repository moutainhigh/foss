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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/domain/LoadDestOrgEntity.java
 *  
 *  FILE NAME          :LoadDestOrgEntity.java
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
 * FILE    NAME: LoadDestOrgEntity.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.io.Serializable;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 装车任务到达部门实体
 * @author dp-duyi
 * @date 2012-11-19 上午8:52:54
 */
public class LoadDestOrgEntity extends BaseEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4185978128608590975L;
	/**id*/
	private String id;
	/**loadTaskId*/
	private String loadTaskId;
	/**destOrgCode*/
	private String destOrgCode;
	/**destOrgName*/
	private String destOrgName;
	/**truckDepartPlanDetailId*/
	private String truckDepartPlanDetailId;
	/**beCreateHandOver*/
	private String beCreateHandOver;
	/***收货人地址区域**/
	private  String   receiveCustDistName; // RECEIVE_CUSTOMER_DIST_CODE
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * 
	 * @return 收货人地址区域
	 */
	
	public String getReceiveCustDistName() {
		return receiveCustDistName;
	}
    /**
     * Set 收货人地址区域
     * @param receiveCustDistName
     */
	public void setReceiveCustDistName(String receiveCustDistName) {
		this.receiveCustDistName = receiveCustDistName;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Gets the loadTaskId.
	 *
	 * @return the loadTaskId
	 */
	public String getLoadTaskId() {
		return loadTaskId;
	}
	
	/**
	 * Sets the loadTaskId.
	 *
	 * @param loadTaskId the new loadTaskId
	 */
	public void setLoadTaskId(String loadTaskId) {
		this.loadTaskId = loadTaskId;
	}
	
	/**
	 * Gets the destOrgCode.
	 *
	 * @return the destOrgCode
	 */
	public String getDestOrgCode() {
		return destOrgCode;
	}
	
	/**
	 * Sets the destOrgCode.
	 *
	 * @param destOrgCode the new destOrgCode
	 */
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}
	
	/**
	 * Gets the destOrgName.
	 *
	 * @return the destOrgName
	 */
	public String getDestOrgName() {
		return destOrgName;
	}
	
	/**
	 * Sets the destOrgName.
	 *
	 * @param destOrgName the new destOrgName
	 */
	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}
	
	/**
	 * Gets the truckDepartPlanDetailId.
	 *
	 * @return the truckDepartPlanDetailId
	 */
	public String getTruckDepartPlanDetailId() {
		return truckDepartPlanDetailId;
	}
	
	/**
	 * Sets the truckDepartPlanDetailId.
	 *
	 * @param truckDepartPlanDetailId the new truckDepartPlanDetailId
	 */
	public void setTruckDepartPlanDetailId(String truckDepartPlanDetailId) {
		this.truckDepartPlanDetailId = truckDepartPlanDetailId;
	}
	
	/**
	 * Gets the beCreateHandOver.
	 *
	 * @return the beCreateHandOver
	 */
	public String getBeCreateHandOver() {
		return beCreateHandOver;
	}
	
	/**
	 * Sets the beCreateHandOver.
	 *
	 * @param beCreateHandOver the new beCreateHandOver
	 */
	public void setBeCreateHandOver(String beCreateHandOver) {
		this.beCreateHandOver = beCreateHandOver;
	}
}