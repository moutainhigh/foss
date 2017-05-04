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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/domain/AssignLoadTaskEntity.java
 *  
 *  FILE NAME          :AssignLoadTaskEntity.java
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
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.transfer.load.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * AssignLoadTaskEntity

 * @author Administrator
 * @date 2012-10-11 下午12:57:26
 * @since
 * @version
 */
public class AssignLoadTaskEntity extends BaseEntity{


	/**
	 * 
	 */
	private static final long serialVersionUID = 6530053695089676241L;
	/**bill*/
	private DeliverBillEntity bill;
	/**loader*/
	private LoaderEntity loader;
	/**id*/
	private String id;
	/**assignTime*/
	private String assignTime;
	/**modifyTime*/
	private String modifyTime;
	/**createUserName*/
	private String createUserName;
	/**createUserCode*/
	private String createUserCode;
	/**modifyUserName*/
	private String modifyUserName;
	/**modifyUserCode*/
	private String modifyUserCode;
	/**taskState*/
	private String taskState;
	/**beCancelled*/
	private String beCancelled;
	/**createOrgCode*/
	private String createOrgCode;
	/**createOrgName*/
	private String createOrgName;
	
	/**
	 * Gets the bill.
	 *
	 * @return the bill
	 */
	public DeliverBillEntity getBill() {
		return bill;
	}
	
	/**
	 * Sets the bill.
	 *
	 * @param bill the new bill
	 */
	public void setBill(DeliverBillEntity bill) {
		this.bill = bill;
	}
	
	/**
	 * Gets the loader.
	 *
	 * @return the loader
	 */
	public LoaderEntity getLoader() {
		return loader;
	}
	
	/**
	 * Sets the loader.
	 *
	 * @param loader the new loader
	 */
	public void setLoader(LoaderEntity loader) {
		this.loader = loader;
	}
	
	
	public String getId() {
		return id;
	}
	

	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Gets the assignTime.
	 *
	 * @return the assignTime
	 */
	public String getAssignTime() {
		return assignTime;
	}
	
	/**
	 * Sets the assignTime.
	 *
	 * @param assignTime the new assignTime
	 */
	public void setAssignTime(String assignTime) {
		this.assignTime = assignTime;
	}
	
	/**
	 * Gets the createUserName.
	 *
	 * @return the createUserName
	 */
	public String getCreateUserName() {
		return createUserName;
	}
	
	/**
	 * Sets the createUserName.
	 *
	 * @param createUserName the new createUserName
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	
	/**
	 * Gets the createUserCode.
	 *
	 * @return the createUserCode
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}
	
	/**
	 * Sets the createUserCode.
	 *
	 * @param createUserCode the new createUserCode
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}
	
	/**
	 * Gets the modifyUserName.
	 *
	 * @return the modifyUserName
	 */
	public String getModifyUserName() {
		return modifyUserName;
	}
	
	/**
	 * Sets the modifyUserName.
	 *
	 * @param modifyUserName the new modifyUserName
	 */
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}
	
	/**
	 * Gets the modifyUserCode.
	 *
	 * @return the modifyUserCode
	 */
	public String getModifyUserCode() {
		return modifyUserCode;
	}
	
	/**
	 * Sets the modifyUserCode.
	 *
	 * @param modifyUserCode the new modifyUserCode
	 */
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}
	
	/**
	 * Gets the taskState.
	 *
	 * @return the taskState
	 */
	public String getTaskState() {
		return taskState;
	}
	
	/**
	 * Sets the taskState.
	 *
	 * @param taskState the new taskState
	 */
	public void setTaskState(String taskState) {
		this.taskState = taskState;
	}
	
	/**
	 * Gets the beCancelled.
	 *
	 * @return the beCancelled
	 */
	public String getBeCancelled() {
		return beCancelled;
	}
	
	/**
	 * Sets the beCancelled.
	 *
	 * @param beCancelled the new beCancelled
	 */
	public void setBeCancelled(String beCancelled) {
		this.beCancelled = beCancelled;
	}
	
	/**
	 * Gets the modifyTime.
	 *
	 * @return the modifyTime
	 */
	public String getModifyTime() {
		return modifyTime;
	}
	
	/**
	 * Sets the modifyTime.
	 *
	 * @param modifyTime the new modifyTime
	 */
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	/**
	 * Gets the createOrgCode.
	 *
	 * @return the createOrgCode
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}
	
	/**
	 * Sets the createOrgCode.
	 *
	 * @param createOrgCode the new createOrgCode
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}
	
	/**
	 * Gets the createOrgName.
	 *
	 * @return the createOrgName
	 */
	public String getCreateOrgName() {
		return createOrgName;
	}
	
	/**
	 * Sets the createOrgName.
	 *
	 * @param createOrgName the new createOrgName
	 */
	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}
	
}