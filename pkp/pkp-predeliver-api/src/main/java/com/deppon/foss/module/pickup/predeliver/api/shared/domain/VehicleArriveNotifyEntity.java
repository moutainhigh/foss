/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-predeliver-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/domain/StorageExecdateEntity.java
 * 
 * FILE NAME        	: StorageExecdateEntity.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * 自动通知
 * 
 * @author ibm-wangfei
 * @date Jun 24, 2013 3:24:35 PM
 */
public class VehicleArriveNotifyEntity extends BaseEntity {
	private static final long serialVersionUID = -7985201661412127761L;
	private String truckTaskDetailId;
	private String taskDetailType;
	private String status;
	private String statusN;
	private Date createTime;
	private String exceptionMessage;
	private String jobId;
	private String jobIdN;
	private int rowNum;	
	public String getTruckTaskDetailId() {
		return truckTaskDetailId;
	}
	public void setTruckTaskDetailId(String truckTaskDetailId) {
		this.truckTaskDetailId = truckTaskDetailId;
	}
	public String getTaskDetailType() {
		return taskDetailType;
	}
	public void setTaskDetailType(String taskDetailType) {
		this.taskDetailType = taskDetailType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getExceptionMessage() {
		return exceptionMessage;
	}
	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getStatusN() {
		return statusN;
	}
	public void setStatusN(String statusN) {
		this.statusN = statusN;
	}
	public String getJobIdN() {
		return jobIdN;
	}
	public void setJobIdN(String jobIdN) {
		this.jobIdN = jobIdN;
	}
	public int getRowNum() {
		return rowNum;
	}
	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

}