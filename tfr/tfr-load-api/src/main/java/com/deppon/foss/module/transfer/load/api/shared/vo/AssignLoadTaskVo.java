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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/vo/AssignLoadTaskVo.java
 *  
 *  FILE NAME          :AssignLoadTaskVo.java
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
 * PROJECT NAME: tfr-load-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.api.shared.vo
 * FILE    NAME: AssignLoadTaskVo.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.domain.AssignLoadTaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.AssignLoadTaskQueryConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.DeliverBillQueryConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoaderQueryConditionDto;

/**
 * AssignLoadTaskVo
 * @author dp-duyi
 * @date 2012-10-19 上午8:24:14
 */
public class AssignLoadTaskVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1730466605343735050L;
	/**派送单查询条件*/
	private DeliverBillQueryConditionDto deliverBillQCVo;
	/**理货员查询条件*/
	private LoaderQueryConditionDto loaderQCVo;
	/**理货员*/
	private List<LoaderEntity> loaders;
	/**派送单*/
	private List<DeliverBillEntity> bills;
	/**已分配装车任务*/
	private AssignLoadTaskEntity assignLoadTask;
	/**已分配装车任务s*/
	private List<AssignLoadTaskEntity> assignedTasks;
	/**已分配派送单查询条件*/
	private AssignLoadTaskQueryConditionDto assignLoadTaskQcVo;
	/**理货员编号*/
	private String loaderCode;
	/**未完成工作量*/
	private LoaderEntity unfinishWorkLoad;
	/**取消分配时的分配记录ID*/
	private String assignTaskId;
	/**取消分配时的派送单号*/
	private String assignBillNo;
	
	/**
	 * Gets the 派送单查询条件.
	 *
	 * @return the 派送单查询条件
	 */
	public DeliverBillQueryConditionDto getDeliverBillQCVo() {
		return deliverBillQCVo;
	}
	
	/**
	 * Sets the 派送单查询条件.
	 *
	 * @param deliverBillQCVo the new 派送单查询条件
	 */
	public void setDeliverBillQCVo(DeliverBillQueryConditionDto deliverBillQCVo) {
		this.deliverBillQCVo = deliverBillQCVo;
	}
	
	/**
	 * Gets the 理货员查询条件.
	 *
	 * @return the 理货员查询条件
	 */
	public LoaderQueryConditionDto getLoaderQCVo() {
		return loaderQCVo;
	}
	
	/**
	 * Sets the 理货员查询条件.
	 *
	 * @param loaderQCVo the new 理货员查询条件
	 */
	public void setLoaderQCVo(LoaderQueryConditionDto loaderQCVo) {
		this.loaderQCVo = loaderQCVo;
	}
	
	/**
	 * Gets the 理货员.
	 *
	 * @return the 理货员
	 */
	public List<LoaderEntity> getLoaders() {
		return loaders;
	}
	
	/**
	 * Sets the 理货员.
	 *
	 * @param loaders the new 理货员
	 */
	public void setLoaders(List<LoaderEntity> loaders) {
		this.loaders = loaders;
	}
	
	/**
	 * Gets the 派送单.
	 *
	 * @return the 派送单
	 */
	public List<DeliverBillEntity> getBills() {
		return bills;
	}
	
	/**
	 * Sets the 派送单.
	 *
	 * @param bills the new 派送单
	 */
	public void setBills(List<DeliverBillEntity> bills) {
		this.bills = bills;
	}
	
	/**
	 * Gets the 已分配装车任务.
	 *
	 * @return the 已分配装车任务
	 */
	public AssignLoadTaskEntity getAssignLoadTask() {
		return assignLoadTask;
	}
	
	/**
	 * Sets the 已分配装车任务.
	 *
	 * @param assignLoadTask the new 已分配装车任务
	 */
	public void setAssignLoadTask(AssignLoadTaskEntity assignLoadTask) {
		this.assignLoadTask = assignLoadTask;
	}
	
	/**
	 * Gets the 已分配装车任务s.
	 *
	 * @return the 已分配装车任务s
	 */
	public List<AssignLoadTaskEntity> getAssignedTasks() {
		return assignedTasks;
	}
	
	/**
	 * Sets the 已分配装车任务s.
	 *
	 * @param assignedTasks the new 已分配装车任务s
	 */
	public void setAssignedTasks(List<AssignLoadTaskEntity> assignedTasks) {
		this.assignedTasks = assignedTasks;
	}
	
	/**
	 * Gets the 已分配派送单查询条件.
	 *
	 * @return the 已分配派送单查询条件
	 */
	public AssignLoadTaskQueryConditionDto getAssignLoadTaskQcVo() {
		return assignLoadTaskQcVo;
	}
	
	/**
	 * Sets the 已分配派送单查询条件.
	 *
	 * @param assignLoadTaskQcVo the new 已分配派送单查询条件
	 */
	public void setAssignLoadTaskQcVo(
			AssignLoadTaskQueryConditionDto assignLoadTaskQcVo) {
		this.assignLoadTaskQcVo = assignLoadTaskQcVo;
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
	 * Gets the 未完成工作量.
	 *
	 * @return the 未完成工作量
	 */
	public LoaderEntity getUnfinishWorkLoad() {
		return unfinishWorkLoad;
	}
	
	/**
	 * Sets the 未完成工作量.
	 *
	 * @param unfinishWorkLoad the new 未完成工作量
	 */
	public void setUnfinishWorkLoad(LoaderEntity unfinishWorkLoad) {
		this.unfinishWorkLoad = unfinishWorkLoad;
	}
	
	/**
	 * Gets the 取消分配时的分配记录ID.
	 *
	 * @return the 取消分配时的分配记录ID
	 */
	public String getAssignTaskId() {
		return assignTaskId;
	}
	
	/**
	 * Sets the 取消分配时的分配记录ID.
	 *
	 * @param assignTaskId the new 取消分配时的分配记录ID
	 */
	public void setAssignTaskId(String assignTaskId) {
		this.assignTaskId = assignTaskId;
	}
	
	/**
	 * Gets the 取消分配时的派送单号.
	 *
	 * @return the 取消分配时的派送单号
	 */
	public String getAssignBillNo() {
		return assignBillNo;
	}
	
	/**
	 * Sets the 取消分配时的派送单号.
	 *
	 * @param assignBillNo the new 取消分配时的派送单号
	 */
	public void setAssignBillNo(String assignBillNo) {
		this.assignBillNo = assignBillNo;
	}
	
}