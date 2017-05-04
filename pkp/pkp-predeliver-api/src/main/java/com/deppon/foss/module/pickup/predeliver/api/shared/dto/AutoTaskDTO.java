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
 *  PROJECT NAME  : tfr-departure-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/api/shared/dto/AutoTaskDTO.java
 *  
 *  FILE NAME          :AutoTaskDTO.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 */
public class AutoTaskDTO extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3703272448562684594L;

	/**********原状态************/
	private String oldStatus;
	
	/**********新状态************/
	private String newStatus;
	
	/**********当前时间************/
	private Date currentTime;
	
	/**********任务明细ID************/
	private String taskDetailId;
	
	/**********任务类型************/
	private String taskDetailType;
	
	/**
	 * 单票出入库数量
	 */
	private Integer ioQty; 
	/**
	 * IO时录入，单票出入库部门CODE
	 */
	private String stockOrgCode; 

	/**
	 * 获取 ********原状态***********.
	 *
	 * @return the ********原状态***********
	 */
	public String getOldStatus()
	{
		return oldStatus;
	}

	/**
	 * 设置 ********原状态***********.
	 *
	 * @param oldStatus the new ********原状态***********
	 */
	public void setOldStatus(String oldStatus)
	{
		this.oldStatus = oldStatus;
	}

	/**
	 * 获取 ********新状态***********.
	 *
	 * @return the ********新状态***********
	 */
	public String getNewStatus()
	{
		return newStatus;
	}

	/**
	 * 设置 ********新状态***********.
	 *
	 * @param newStatus the new ********新状态***********
	 */
	public void setNewStatus(String newStatus)
	{
		this.newStatus = newStatus;
	}

	/**
	 * 获取 ********当前时间***********.
	 *
	 * @return the ********当前时间***********
	 */
	public Date getCurrentTime()
	{
		return currentTime;
	}

	/**
	 * 设置 ********当前时间***********.
	 *
	 * @param currentTime the new ********当前时间***********
	 */
	public void setCurrentTime(Date currentTime)
	{
		this.currentTime = currentTime;
	}

	/**
	 * 获取 ********任务明细ID***********.
	 *
	 * @return the ********任务明细ID***********
	 */
	public String getTaskDetailId()
	{
		return taskDetailId;
	}

	/**
	 * 设置 ********任务明细ID***********.
	 *
	 * @param taskDetailId the new ********任务明细ID***********
	 */
	public void setTaskDetailId(String taskDetailId)
	{
		this.taskDetailId = taskDetailId;
	}

	/**
	 * 获取 ********任务类型***********.
	 *
	 * @return the ********任务类型***********
	 */
	public String getTaskDetailType()
	{
		return taskDetailType;
	}

	/**
	 * 设置 ********任务类型***********.
	 *
	 * @param taskDetailType the new ********任务类型***********
	 */
	public void setTaskDetailType(String taskDetailType)
	{
		this.taskDetailType = taskDetailType;
	}

	public Integer getIoQty() {
		return ioQty;
	}

	public void setIoQty(Integer ioQty) {
		this.ioQty = ioQty;
	}

	public String getStockOrgCode() {
		return stockOrgCode;
	}

	public void setStockOrgCode(String stockOrgCode) {
		this.stockOrgCode = stockOrgCode;
	}

	
}