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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/domain/JobSuccessLogEntity.java
 * 
 * FILE NAME        	: JobSuccessLogEntity.java
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
 * 成功日志信息
 * 
 * @author ibm-wangfei
 * @date Feb 27, 2013 10:37:45 AM
 */
public class JobSuccessLogEntity extends BaseEntity {
	private static final long serialVersionUID = -6149569770101843366L;

	/**
	 * job名称
	 */
	private String jobName;

	/**
	 * 成功信息
	 */
	private String successMsg;

	/**
	 * 创建日期
	 */
	private Date createTime;
	
	/**
	 * SUCCESS_ID
	 */
	private String successId;

	/**
	 * 获取 job名称.
	 *
	 * @return the job名称
	 */
	public String getJobName() {
		return jobName;
	}

	/**
	 * 设置 job名称.
	 *
	 * @param jobName the new job名称
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	/**
	 * 获取 成功信息.
	 *
	 * @return the 成功信息
	 */
	public String getSuccessMsg() {
		return successMsg;
	}

	/**
	 * 设置 成功信息.
	 *
	 * @param successMsg the new 成功信息
	 */
	public void setSuccessMsg(String successMsg) {
		this.successMsg = successMsg;
	}

	/**
	 * 获取 创建日期.
	 *
	 * @return the 创建日期
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置 创建日期.
	 *
	 * @param createTime the new 创建日期
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getSuccessId() {
		return successId;
	}

	public void setSuccessId(String successId) {
		this.successId = successId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}