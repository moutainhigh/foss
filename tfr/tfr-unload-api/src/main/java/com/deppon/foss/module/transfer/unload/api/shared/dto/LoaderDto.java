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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/api/shared/dto/LoaderDto.java
 *  
 *  FILE NAME          :LoaderDto.java
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
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-unload-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.unload.api.shared.dto
 * FILE    NAME: LoaderDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.unload.api.shared.dto;

import java.io.Serializable;


public class LoaderDto implements Serializable{

	private static final long serialVersionUID = 7526827936282013455L;
	/**
	 *  id
	 */
	private String id;
	/**
	 * 理货员编号
	 */
	private String loaderCode;  
	/**
	 * 理货员名称
	 */
	private String loaderName;
	/**
	 * 理货员部门编码
	 */
	private String orgCode;          
	/**
	 * 理货员部门名称
	 */
	private String orgName;         
	/**
	 * 状态:在线、离线
	 */
	private String state;    
	/**
	 * 职位
	 */
	private String title;  
	/**
	 * 未完成货量
	 */
	private double unfinishedWeight;  
	/**
	 * 未完成任务数
	 */
	private double unfinishedTaskQty; 
	/**
	 * 已分配货量
	 */
	private double assignedWeight;    
	/**
	 * 已完成货量
	 */
	private double finishedWeight;    
	/**
	 * 查询开始时间
	 */
	private String queryTimeBegin;    
	/**
	 * 查询结束时间
	 */
	private String queryTimeEnd;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the loaderCode
	 */
	public String getLoaderCode() {
		return loaderCode;
	}
	/**
	 * @param loaderCode the loaderCode to set
	 */
	public void setLoaderCode(String loaderCode) {
		this.loaderCode = loaderCode;
	}
	/**
	 * @return the loaderName
	 */
	public String getLoaderName() {
		return loaderName;
	}
	/**
	 * @param loaderName the loaderName to set
	 */
	public void setLoaderName(String loaderName) {
		this.loaderName = loaderName;
	}
	/**
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}
	/**
	 * @param orgCode the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	/**
	 * @return the orgName
	 */
	public String getOrgName() {
		return orgName;
	}
	/**
	 * @param orgName the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the unfinishedWeight
	 */
	public double getUnfinishedWeight() {
		return unfinishedWeight;
	}
	/**
	 * @param unfinishedWeight the unfinishedWeight to set
	 */
	public void setUnfinishedWeight(double unfinishedWeight) {
		this.unfinishedWeight = unfinishedWeight;
	}
	/**
	 * @return the unfinishedTaskQty
	 */
	public double getUnfinishedTaskQty() {
		return unfinishedTaskQty;
	}
	/**
	 * @param unfinishedTaskQty the unfinishedTaskQty to set
	 */
	public void setUnfinishedTaskQty(double unfinishedTaskQty) {
		this.unfinishedTaskQty = unfinishedTaskQty;
	}
	/**
	 * @return the assignedWeight
	 */
	public double getAssignedWeight() {
		return assignedWeight;
	}
	/**
	 * @param assignedWeight the assignedWeight to set
	 */
	public void setAssignedWeight(double assignedWeight) {
		this.assignedWeight = assignedWeight;
	}
	/**
	 * @return the finishedWeight
	 */
	public double getFinishedWeight() {
		return finishedWeight;
	}
	/**
	 * @param finishedWeight the finishedWeight to set
	 */
	public void setFinishedWeight(double finishedWeight) {
		this.finishedWeight = finishedWeight;
	}
	/**
	 * @return the queryTimeBegin
	 */
	public String getQueryTimeBegin() {
		return queryTimeBegin;
	}
	/**
	 * @param queryTimeBegin the queryTimeBegin to set
	 */
	public void setQueryTimeBegin(String queryTimeBegin) {
		this.queryTimeBegin = queryTimeBegin;
	}
	/**
	 * @return the queryTimeEnd
	 */
	public String getQueryTimeEnd() {
		return queryTimeEnd;
	}
	/**
	 * @param queryTimeEnd the queryTimeEnd to set
	 */
	public void setQueryTimeEnd(String queryTimeEnd) {
		this.queryTimeEnd = queryTimeEnd;
	}  
	
}