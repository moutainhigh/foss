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
package com.deppon.foss.module.transfer.common.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 任务执行实体
 * @author foss-wuyingjie
 * @date 2012-12-26 上午10:42:11
 */
public class TfrJobProcessEntity extends BaseEntity{
	
	private static final long serialVersionUID = -8493318554269278113L;
	
	/**ID*/
	private String id;
	/**任务名称*/
    private String bizName;
    /**任务编码*/
    private String bizCode;
    /**业务开始时间*/
    private Date bizStartTime;
    /**业务截止时间*/
    private Date bizEndTime;
    /**执行结果*/
    private String status;
    /**线程号*/
    private Integer threadNo;
    /**线程数*/
    private Integer threadCount;
    /**任务实际开始时间*/
    private Date jobStartTime;
    /**任务实际完成时间*/
    private Date jobEndTime;

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
}