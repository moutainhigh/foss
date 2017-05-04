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
 *  PROJECT NAME  : tfr-common-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/common/api/shared/domain/TfrJobProcessEntity.java
 *  
 *  FILE NAME          :TfrJobProcessEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.common.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 任务执行实体
 * 
 * @author foss-yuyongxiang
 * @date 2013年5月13日 08:42:34
 */
public class TfrJobProcessDto implements Serializable {

	private static final long serialVersionUID = 5950661876670342075L;
	/** 任务名称 */
	private String bizName;
	/** 任务编码 */
	private String bizCode;
	/** 业务开始时间 */
	private Date bizStartTime;
	/** 业务截止时间 */
	private Date bizEndTime;
	/** 执行结果 */
	private String status;
	/** 线程号 */
	private Integer threadNo;
	/** 线程数 */
	private Integer threadCount;
	/** 任务实际开始时间 */
	private Date jobStartTime;
	/** 任务实际完成时间 */
	private Date jobEndTime;

	
	
	/** 业务开始时间 from*/
	private Date bizStartTimeFrom;
	/** 业务开始时间 to*/
	private Date bizStartTimeTo;
	/** 业务截止时间 From*/
	private Date bizEndTimeFrom;
	/** 业务截止时间 To*/
	private Date bizEndTimeTo;
	/** 任务实际开始时间 from */
	private Date jobStartTimeFrom;
	/** 任务实际开始时间 to */
	private Date jobStartTimeTo;
	/** 任务实际完成时间 from*/
	private Date jobEndTimeFrom;
	/** 任务实际完成时间  to*/
	private Date jobEndTimeTo;

	public String getBizName() {
		return bizName;
	}

	public void setBizName(String bizName) {
		this.bizName = bizName;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public Date getBizStartTime() {
		return bizStartTime;
	}

	public void setBizStartTime(Date bizStartTime) {
		this.bizStartTime = bizStartTime;
	}

	public Date getBizEndTime() {
		return bizEndTime;
	}

	public void setBizEndTime(Date bizEndTime) {
		this.bizEndTime = bizEndTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getThreadNo() {
		return threadNo;
	}

	public void setThreadNo(Integer threadNo) {
		this.threadNo = threadNo;
	}

	public Integer getThreadCount() {
		return threadCount;
	}

	public void setThreadCount(Integer threadCount) {
		this.threadCount = threadCount;
	}

	public Date getJobStartTime() {
		return jobStartTime;
	}

	public void setJobStartTime(Date jobStartTime) {
		this.jobStartTime = jobStartTime;
	}

	public Date getJobEndTime() {
		return jobEndTime;
	}

	public void setJobEndTime(Date jobEndTime) {
		this.jobEndTime = jobEndTime;
	}

	/**
	 * @return the bizStartTimeFrom
	 */
	public Date getBizStartTimeFrom() {
		return bizStartTimeFrom;
	}

	/**
	 * @param bizStartTimeFrom the bizStartTimeFrom to set
	 */
	public void setBizStartTimeFrom(Date bizStartTimeFrom) {
		this.bizStartTimeFrom = bizStartTimeFrom;
	}

	/**
	 * @return the bizStartTimeTo
	 */
	public Date getBizStartTimeTo() {
		return bizStartTimeTo;
	}

	/**
	 * @param bizStartTimeTo the bizStartTimeTo to set
	 */
	public void setBizStartTimeTo(Date bizStartTimeTo) {
		this.bizStartTimeTo = bizStartTimeTo;
	}

	/**
	 * @return the bizEndTimeFrom
	 */
	public Date getBizEndTimeFrom() {
		return bizEndTimeFrom;
	}

	/**
	 * @param bizEndTimeFrom the bizEndTimeFrom to set
	 */
	public void setBizEndTimeFrom(Date bizEndTimeFrom) {
		this.bizEndTimeFrom = bizEndTimeFrom;
	}

	/**
	 * @return the bizEndTimeTo
	 */
	public Date getBizEndTimeTo() {
		return bizEndTimeTo;
	}

	/**
	 * @param bizEndTimeTo the bizEndTimeTo to set
	 */
	public void setBizEndTimeTo(Date bizEndTimeTo) {
		this.bizEndTimeTo = bizEndTimeTo;
	}

	/**
	 * @return the jobStartTimeFrom
	 */
	public Date getJobStartTimeFrom() {
		return jobStartTimeFrom;
	}

	/**
	 * @param jobStartTimeFrom the jobStartTimeFrom to set
	 */
	public void setJobStartTimeFrom(Date jobStartTimeFrom) {
		this.jobStartTimeFrom = jobStartTimeFrom;
	}

	/**
	 * @return the jobStartTimeTo
	 */
	public Date getJobStartTimeTo() {
		return jobStartTimeTo;
	}

	/**
	 * @param jobStartTimeTo the jobStartTimeTo to set
	 */
	public void setJobStartTimeTo(Date jobStartTimeTo) {
		this.jobStartTimeTo = jobStartTimeTo;
	}

	/**
	 * @return the jobEndTimeFrom
	 */
	public Date getJobEndTimeFrom() {
		return jobEndTimeFrom;
	}

	/**
	 * @param jobEndTimeFrom the jobEndTimeFrom to set
	 */
	public void setJobEndTimeFrom(Date jobEndTimeFrom) {
		this.jobEndTimeFrom = jobEndTimeFrom;
	}

	/**
	 * @return the jobEndTimeTo
	 */
	public Date getJobEndTimeTo() {
		return jobEndTimeTo;
	}

	/**
	 * @param jobEndTimeTo the jobEndTimeTo to set
	 */
	public void setJobEndTimeTo(Date jobEndTimeTo) {
		this.jobEndTimeTo = jobEndTimeTo;
	}
}