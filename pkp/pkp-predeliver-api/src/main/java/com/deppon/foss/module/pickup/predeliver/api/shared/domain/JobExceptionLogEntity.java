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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/domain/JobExceptionLogEntity.java
 * 
 * FILE NAME        	: JobExceptionLogEntity.java
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
 * 记录定时任务在执行过程中发现的错误详细信息
 * 
 * @author ibm-wangfei
 * @date Feb 27, 2013 10:38:05 AM
 */
public class JobExceptionLogEntity extends BaseEntity {
	private static final long serialVersionUID = 8369588890608390706L;

	/**
	 * job名称
	 */
	private String jobName;

	/**
	 * 错误id
	 */
	private String errorId;

	/**
	 * 错误code
	 */
	private String errorCode;

	/**
	 * 错误内容
	 */
	private String errorMsg;

	/**
	 * 创建时间
	 */
	private Date createTime;

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
	 * 获取 错误id.
	 *
	 * @return the 错误id
	 */
	public String getErrorId() {
		return errorId;
	}

	/**
	 * 设置 错误id.
	 *
	 * @param errorId the new 错误id
	 */
	public void setErrorId(String errorId) {
		this.errorId = errorId;
	}

	/**
	 * 获取 错误code.
	 *
	 * @return the 错误code
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * 设置 错误code.
	 *
	 * @param errorCode the new 错误code
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * 获取 错误内容.
	 *
	 * @return the 错误内容
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * 设置 错误内容.
	 *
	 * @param errorMsg the new 错误内容
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	/**
	 * 获取 创建时间.
	 *
	 * @return the 创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置 创建时间.
	 *
	 * @param createTime the new 创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}