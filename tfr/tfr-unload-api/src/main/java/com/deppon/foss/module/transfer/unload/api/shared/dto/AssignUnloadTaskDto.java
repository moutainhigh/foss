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
 *  PROJECT NAME  : tfr-unload-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/api/shared/dto/AssignUnloadTaskDto.java
 *  
 *  FILE NAME          :AssignUnloadTaskDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-unload-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.unload.api.shared.domain
 * FILE    NAME: AssignUnloadTask.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.unload.api.shared.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * 分配卸车任务
 * @author dp-duyi
 * @date 2012-10-18 下午5:05:12
 */
public class AssignUnloadTaskDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -480385382321794553L;
	/**loader*/
	private LoaderDto loader;
	/**bill*/
	private ArriveBillDto bill;
	/**bills*/
	private List<ArriveBillDto> bills;
	/**vehicle*/
	private AssignUnloadTaskTotalDto vehicle;
	/**id*/
	private String id;
	/**beCanceled*/
	private String beCanceled;
	/**createUserName*/
	private String createUserName;
	/**createUserCode*/
	private String createUserCode;
	/**modifyUserName*/
	private String modifyUserName;
	/**modifyUserCode*/
	private String modifyUserCode;
	/**createOrgCode*/
	private String createOrgCode;
	/**当前部门顶级外场下所有子部门**/
	private List<String> orgCodes = new ArrayList<String>();
	/**createOrgName*/
	private String createOrgName;
	/**createTime*/
	private String createTime;
	/**modifyTime*/
	private String modifyTime;
	/**state*/
	private String state;
	/**unloadBeginTime*/
	private String unloadBeginTime;
	/**unloadEndTime*/
	private String unloadEndTime;
	private String billNo;
	/**queryTimeBegin*/
	private String queryTimeBegin;
	/**queryTimeEnd*/
	private String queryTimeEnd;
	
	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	/**
	 * Gets the bills.
	 *
	 * @return the bills
	 */
	public List<ArriveBillDto> getBills() {
		return bills;
	}

	/**
	 * Sets the bills.
	 *
	 * @param bills the new bills
	 */
	public void setBills(List<ArriveBillDto> bills) {
		this.bills = bills;
	}

	/**
	 * Gets the loader.
	 *
	 * @return the loader
	 */
	public LoaderDto getLoader() {
		return loader;
	}
	
	/**
	 * Sets the loader.
	 *
	 * @param loader the new loader
	 */
	public void setLoader(LoaderDto loader) {
		this.loader = loader;
	}
	
	/**
	 * Gets the bill.
	 *
	 * @return the bill
	 */
	public ArriveBillDto getBill() {
		return bill;
	}
	
	/**
	 * Sets the bill.
	 *
	 * @param bill the new bill
	 */
	public void setBill(ArriveBillDto bill) {
		this.bill = bill;
	}
	
	/**
	 * Gets the vehicle.
	 *
	 * @return the vehicle
	 */
	public AssignUnloadTaskTotalDto getVehicle() {
		return vehicle;
	}
	
	/**
	 * Sets the vehicle.
	 *
	 * @param vehicle the new vehicle
	 */
	public void setVehicle(AssignUnloadTaskTotalDto vehicle) {
		this.vehicle = vehicle;
	}
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
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
	
	/**
	 * Gets the createTime.
	 *
	 * @return the createTime
	 */
	public String getCreateTime() {
		return createTime;
	}
	
	/**
	 * Sets the createTime.
	 *
	 * @param createTime the new createTime
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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
	
	/**
	 * Gets the beCanceled.
	 *
	 * @return the beCanceled
	 */
	public String getBeCanceled() {
		return beCanceled;
	}
	
	/**
	 * Sets the beCanceled.
	 *
	 * @param beCanceled the new beCanceled
	 */
	public void setBeCanceled(String beCanceled) {
		this.beCanceled = beCanceled;
	}
	
	/**
	 * Gets the unloadBeginTime.
	 *
	 * @return the unloadBeginTime
	 */
	public String getUnloadBeginTime() {
		return unloadBeginTime;
	}
	
	/**
	 * Sets the unloadBeginTime.
	 *
	 * @param unloadBeginTime the new unloadBeginTime
	 */
	public void setUnloadBeginTime(String unloadBeginTime) {
		this.unloadBeginTime = unloadBeginTime;
	}
	
	/**
	 * Gets the unloadEndTime.
	 *
	 * @return the unloadEndTime
	 */
	public String getUnloadEndTime() {
		return unloadEndTime;
	}
	
	/**
	 * Sets the unloadEndTime.
	 *
	 * @param unloadEndTime the new unloadEndTime
	 */
	public void setUnloadEndTime(String unloadEndTime) {
		this.unloadEndTime = unloadEndTime;
	}
	
	/**
	 * Gets the queryTimeBegin.
	 *
	 * @return the queryTimeBegin
	 */
	public String getQueryTimeBegin() {
		return queryTimeBegin;
	}
	
	/**
	 * Sets the queryTimeBegin.
	 *
	 * @param queryTimeBegin the new queryTimeBegin
	 */
	public void setQueryTimeBegin(String queryTimeBegin) {
		this.queryTimeBegin = queryTimeBegin;
	}
	
	/**
	 * Gets the queryTimeEnd.
	 *
	 * @return the queryTimeEnd
	 */
	public String getQueryTimeEnd() {
		return queryTimeEnd;
	}
	
	/**
	 * Sets the queryTimeEnd.
	 *
	 * @param queryTimeEnd the new queryTimeEnd
	 */
	public void setQueryTimeEnd(String queryTimeEnd) {
		this.queryTimeEnd = queryTimeEnd;
	}

	/**   
	 * orgCodes   
	 *   
	 * @return  the orgCodes   
	 */
	
	public List<String> getOrgCodes() {
		return orgCodes;
	}

	/**   
	 * @param orgCodes the orgCodes to set
	 * Date:2013-7-16上午11:02:30
	 */
	public void setOrgCodes(List<String> orgCodes) {
		this.orgCodes = orgCodes;
	}
}