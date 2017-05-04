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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/domain/ExceptionEntity.java
 * 
 * FILE NAME        	: ExceptionEntity.java
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
 *
 * 异常实体
 * @author 043258-foss-zhaobin
 * @date 2012-11-12 下午4:28:45
 * @since
 * @version
 */

public class ExceptionEntity extends BaseEntity {

	/** 序列化. */
	private static final long serialVersionUID = 1L;
	
	/** 运单号. */
	private String waybillNo;
	
	/** 异常类型. */
	private String exceptionType;
	
	/** 异常环节. */
	private String exceptionLink;
	
	/** 状态. */
	private String status;
	
	/** 流水号. */
	private String serialNo;
	
	/** 异常生成时间. */
	private Date exceptionTime;
	
	/** 登记人. */
	private String createUserName;
	
	/** 登记人编码. */
	private String createUserCode;
	
	/** 登记部门. */
	private String createOrgName;
	
	/** 登记部门编码. */
	private String createOrgCode;
	
	private String arrivesheetId;
	/** 备注-异常原因. */
	private String exceptionReason;
	/** 区县名称 **/
	private String countyName;
	/** 区县编码 **/
	private String countyCode;
	
	/**
	 * 异常操作
	 */
	private String exceptiOperate;
	
	/**
	 * 通知内容
	 */
	private String noticeContext;
	
	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public String getCountyCode() {
		return countyCode;
	}

	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	public String getExceptionReason() {
		return exceptionReason;
	}

	public void setExceptionReason(String exceptionReason) {
		this.exceptionReason = exceptionReason;
	}

	/**
	 * Gets the waybill no.
	 *
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * Sets the waybill no.
	 *
	 * @param waybillNo the waybillNo to see
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * Gets the exception type.
	 *
	 * @return the exceptionType
	 */
	public String getExceptionType() {
		return exceptionType;
	}

	/**
	 * Sets the exception type.
	 *
	 * @param exceptionType the exceptionType to see
	 */
	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}

	/**
	 * Gets the exception link.
	 *
	 * @return the exceptionLink
	 */
	public String getExceptionLink() {
		return exceptionLink;
	}

	/**
	 * Sets the exception link.
	 *
	 * @param exceptionLink the exceptionLink to see
	 */
	public void setExceptionLink(String exceptionLink) {
		this.exceptionLink = exceptionLink;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the status to see
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the serial no.
	 *
	 * @return the serialNo
	 */
	public String getSerialNo() {
		return serialNo;
	}

	/**
	 * Sets the serial no.
	 *
	 * @param serialNo the serialNo to see
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * Gets the exception time.
	 *
	 * @return the exceptionTime
	 */
	public Date getExceptionTime() {
		return exceptionTime;
	}

	/**
	 * Sets the exception time.
	 *
	 * @param exceptionTime the exceptionTime to see
	 */
	public void setExceptionTime(Date exceptionTime) {
		this.exceptionTime = exceptionTime;
	}

	/**
	 * Gets the create user name.
	 *
	 * @return the createUserName
	 */
	public String getCreateUserName() {
		return createUserName;
	}

	/**
	 * Sets the create user name.
	 *
	 * @param createUserName the createUserName to see
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	/**
	 * Gets the create user code.
	 *
	 * @return the createUserCode
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}

	/**
	 * Sets the create user code.
	 *
	 * @param createUserCode the createUserCode to see
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	/**
	 * Gets the create org name.
	 *
	 * @return the createOrgName
	 */
	public String getCreateOrgName() {
		return createOrgName;
	}

	/**
	 * Sets the create org name.
	 *
	 * @param createOrgName the createOrgName to see
	 */
	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}

	/**
	 * Gets the create org code.
	 *
	 * @return the createOrgCode
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}

	/**
	 * Sets the create org code.
	 *
	 * @param createOrgCode the createOrgCode to see
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	public String getArrivesheetId() {
		return arrivesheetId;
	}

	public void setArrivesheetId(String arrivesheetId) {
		this.arrivesheetId = arrivesheetId;
	}

	public String getExceptiOperate() {
		return exceptiOperate;
	}

	public void setExceptiOperate(String exceptiOperate) {
		this.exceptiOperate = exceptiOperate;
	}

	public String getNoticeContext() {
		return noticeContext;
	}

	public void setNoticeContext(String noticeContext) {
		this.noticeContext = noticeContext;
	}
	
}