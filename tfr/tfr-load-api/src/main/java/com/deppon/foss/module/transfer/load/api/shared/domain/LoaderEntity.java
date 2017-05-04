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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/domain/LoaderEntity.java
 *  
 *  FILE NAME          :LoaderEntity.java
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
 *LoaderEntity
 * @author duyi
 * @date 2012-10-11 下午12:56:59
 * @since
 * @version
 */
public class LoaderEntity extends BaseEntity{
	/**serialVersionUID*/
	private static final long serialVersionUID = -3622242244516516104L;
	/**id*/
	private String id;
	/**理货员编号*/
	private String loaderCode;
	/**理货员名称*/
	private String loaderName;
	/**部门编号*/
	private String orgCode;
	/**部门名称*/
	private String orgName;
	/**职位*/
	private String title;
	/**未完成重量*/
	private double unfinishedWeight;
	/**未完成任务数*/
	private int unfinishedTaskQty;
	/**已分配重量*/
	private double assignedWeight;
	/**已完成任务数*/
	private double finishedWeight;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Gets the 理货员编号.
	 *
	 * @return the 理货员编号
	 */
	public String getLoaderCode() {
		return loaderCode;
	}
	
	/**
	 * Sets the 理货员编号.
	 *
	 * @param loaderCode the new 理货员编号
	 */
	public void setLoaderCode(String loaderCode) {
		this.loaderCode = loaderCode;
	}
	
	/**
	 * Gets the 理货员名称.
	 *
	 * @return the 理货员名称
	 */
	public String getLoaderName() {
		return loaderName;
	}
	
	/**
	 * Sets the 理货员名称.
	 *
	 * @param loaderName the new 理货员名称
	 */
	public void setLoaderName(String loaderName) {
		this.loaderName = loaderName;
	}
	
	/**
	 * Gets the 部门编号.
	 *
	 * @return the 部门编号
	 */
	public String getOrgCode() {
		return orgCode;
	}
	
	/**
	 * Sets the 部门编号.
	 *
	 * @param orgCode the new 部门编号
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	/**
	 * Gets the 部门名称.
	 *
	 * @return the 部门名称
	 */
	public String getOrgName() {
		return orgName;
	}
	
	/**
	 * Sets the 部门名称.
	 *
	 * @param orgName the new 部门名称
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	/**
	 * Gets the 职位.
	 *
	 * @return the 职位
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Sets the 职位.
	 *
	 * @param title the new 职位
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Gets the 未完成重量.
	 *
	 * @return the 未完成重量
	 */
	public double getUnfinishedWeight() {
		return unfinishedWeight;
	}
	
	/**
	 * Sets the 未完成重量.
	 *
	 * @param unfinishedWeight the new 未完成重量
	 */
	public void setUnfinishedWeight(double unfinishedWeight) {
		this.unfinishedWeight = unfinishedWeight;
	}
	
	/**
	 * Gets the 未完成任务数.
	 *
	 * @return the 未完成任务数
	 */
	public int getUnfinishedTaskQty() {
		return unfinishedTaskQty;
	}
	
	/**
	 * Sets the 未完成任务数.
	 *
	 * @param unfinishedTaskQty the new 未完成任务数
	 */
	public void setUnfinishedTaskQty(int unfinishedTaskQty) {
		this.unfinishedTaskQty = unfinishedTaskQty;
	}
	
	/**
	 * Gets the 已分配重量.
	 *
	 * @return the 已分配重量
	 */
	public double getAssignedWeight() {
		return assignedWeight;
	}
	
	/**
	 * Sets the 已分配重量.
	 *
	 * @param assignedWeight the new 已分配重量
	 */
	public void setAssignedWeight(double assignedWeight) {
		this.assignedWeight = assignedWeight;
	}
	
	/**
	 * Gets the 已完成任务数.
	 *
	 * @return the 已完成任务数
	 */
	public double getFinishedWeight() {
		return finishedWeight;
	}
	
	/**
	 * Sets the 已完成任务数.
	 *
	 * @param finishedWeight the new 已完成任务数
	 */
	public void setFinishedWeight(double finishedWeight) {
		this.finishedWeight = finishedWeight;
	}
	
}