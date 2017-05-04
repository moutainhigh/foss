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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/domain/ExceptionProcessDetailEntity.java
 * 
 * FILE NAME        	: ExceptionProcessDetailEntity.java
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
 * 异常处理记录.
 *
 * @author 043258-foss-zhaobin
 * @date 2012-11-12 下午4:29:05
 */
public class ExceptionProcessDetailEntity extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** 异常_ID. */
	private String tSrvExceptionId;
	
	/** 异常处理结果. */
	private String notes;
	
	/** 操作人. */
	private String operator;
	
	/** 操作人编码. */
	private String operatorCode;
	
	/** 操作部门. */
	private String operateOrgName;
	
	/** 操作部门编码. */
	private String operateOrgCode;
	
	/** 操作时间. */
	private Date operateTime;

	/**
	 * Gets the t srv exception id.
	 *
	 * @return the tSrvExceptionId
	 */
	public String gettSrvExceptionId() {
		return tSrvExceptionId;
	}

	/**
	 * Sets the t srv exception id.
	 *
	 * @param tSrvExceptionId the tSrvExceptionId to see
	 */
	public void settSrvExceptionId(String tSrvExceptionId) {
		this.tSrvExceptionId = tSrvExceptionId;
	}

	/**
	 * Gets the notes.
	 *
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * Sets the notes.
	 *
	 * @param notes the notes to see
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * Gets the operator.
	 *
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * Sets the operator.
	 *
	 * @param operator the operator to see
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * Gets the operator code.
	 *
	 * @return the operatorCode
	 */
	public String getOperatorCode() {
		return operatorCode;
	}

	/**
	 * Sets the operator code.
	 *
	 * @param operatorCode the operatorCode to see
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**
	 * Gets the operate org name.
	 *
	 * @return the operateOrgName
	 */
	public String getOperateOrgName() {
		return operateOrgName;
	}

	/**
	 * Sets the operate org name.
	 *
	 * @param operateOrgName the operateOrgName to see
	 */
	public void setOperateOrgName(String operateOrgName) {
		this.operateOrgName = operateOrgName;
	}

	/**
	 * Gets the operate org code.
	 *
	 * @return the operateOrgCode
	 */
	public String getOperateOrgCode() {
		return operateOrgCode;
	}

	/**
	 * Sets the operate org code.
	 *
	 * @param operateOrgCode the operateOrgCode to see
	 */
	public void setOperateOrgCode(String operateOrgCode) {
		this.operateOrgCode = operateOrgCode;
	}

	/**
	 * Gets the operate time.
	 *
	 * @return the operateTime
	 */
	public Date getOperateTime() {
		return operateTime;
	}

	/**
	 * Sets the operate time.
	 *
	 * @param operateTime the operateTime to see
	 */
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
}