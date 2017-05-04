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
 * PROJECT NAME	: pkp-sign-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/dto/StayHandoverDto.java
 * 
 * FILE NAME        	: StayHandoverDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.shared.dto;

import java.io.Serializable;

/**
 * 签收反签收库存dto
 * @author foss-meiying
 * @date 2012-11-28 上午11:41:39
 * @since
 * @version
 */
public class SignStockDto implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	 /**
     * 出入库类型
     */
    private String inoutType;
    /**
     * 状态 0-待执行 1-执行中 2-异常
     */
    private String status;
    /**
     * jobid
     */
    private String jobId;
    /**
     * 原来状态 0-待执行 1-执行中 2-异常
     */
    private String oldStatus;
    /**
     * 每次查询多少条数据
     */
    private Integer tempCount;
	
	/**
	 * Gets the 出入库类型.
	 *
	 * @return the 出入库类型
	 */
	public String getInoutType() {
		return inoutType;
	}
	
	/**
	 * Sets the 出入库类型.
	 *
	 * @param inoutType the new 出入库类型
	 */
	public void setInoutType(String inoutType) {
		this.inoutType = inoutType;
	}
	
	/**
	 * Gets the 状态 0-待执行 1-执行中 2-异常.
	 *
	 * @return the 状态 0-待执行 1-执行中 2-异常
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * Sets the 状态 0-待执行 1-执行中 2-异常.
	 *
	 * @param status the new 状态 0-待执行 1-执行中 2-异常
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * Gets the jobid.
	 *
	 * @return the jobid
	 */
	public String getJobId() {
		return jobId;
	}
	
	/**
	 * Sets the jobid.
	 *
	 * @param jobId the new jobid
	 */
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	
	/**
	 * Gets the 原来状态 0-待执行 1-执行中 2-异常.
	 *
	 * @return the 原来状态 0-待执行 1-执行中 2-异常
	 */
	public String getOldStatus() {
		return oldStatus;
	}
	
	/**
	 * Sets the 原来状态 0-待执行 1-执行中 2-异常.
	 *
	 * @param oldStatus the new 原来状态 0-待执行 1-执行中 2-异常
	 */
	public void setOldStatus(String oldStatus) {
		this.oldStatus = oldStatus;
	}
	
	/**
	 * Gets the 每次查询多少条数据.
	 *
	 * @return the 每次查询多少条数据
	 */
	public Integer getTempCount() {
		return tempCount;
	}
	
	/**
	 * Sets the 每次查询多少条数据.
	 *
	 * @param tempCount the new 每次查询多少条数据
	 */
	public void setTempCount(Integer tempCount) {
		this.tempCount = tempCount;
	}
	

}