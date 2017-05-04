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
 * PROJECT NAME	: pkp-waybill-share
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/domain/ReturnBillProcessEntity.java
 * 
 * FILE NAME        	: ReturnBillProcessEntity.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * 签收单返单处理
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-11-7 上午11:32:07, </p>
 * @author foss-sunrui
 * @date 2012-11-7 上午11:32:07
 * @since
 * @version
 */
public class ReturnBillProcessEntity extends BaseEntity{
    /**
     * 序列化版本号
     * （用一句话描述这个变量表示什么）
     */
    private static final long serialVersionUID = -8380381982215977439L;
    //运单号
    private String waybillNo; //运单号
    //返单状态s
    private String returnBillStatus; //返单状态s
    //返单类型
    private String returnbillType; //返单类型
    //返单时间
    private Date returnbillTime; //返单时间
    //处理人名
    private String handler; //处理人名
    //处理人工号
    private String handlerCode; //处理人工号
    //确认时间
    private Date verifyTime;//确认时间
    //反馈信息
    private String feedbackInfo;//反馈信息
    //快递号
    private String expressNo;//快递号
    //创建人名称
    private String createUserName;  //创建人名称
    //创建人编码
    private String createUserCode;  //创建人编码
    //创建组织名称
    private String createOrgName; //创建组织名称
    //创建组织编码
    private String createOrgCode;//创建组织编码
    //创建时间
    private Date createTime;   //创建时间

	/**
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return the returnBillStatus
	 */
	public String getReturnBillStatus() {
		return returnBillStatus;
	}

	/**
	 * @param returnBillStatus the returnBillStatus to set
	 */
	public void setReturnBillStatus(String returnBillStatus) {
		this.returnBillStatus = returnBillStatus;
	}

	/**
	 * @return the returnbillType
	 */
	public String getReturnbillType() {
		return returnbillType;
	}

	/**
	 * @param returnbillType the returnbillType to set
	 */
	public void setReturnbillType(String returnbillType) {
		this.returnbillType = returnbillType;
	}

	/**
	 * @return the returnbillTime
	 */
	public Date getReturnbillTime() {
		return returnbillTime;
	}

	/**
	 * @param returnbillTime the returnbillTime to set
	 */
	public void setReturnbillTime(Date returnbillTime) {
		this.returnbillTime = returnbillTime;
	}

	/**
	 * @return the handler
	 */
	public String getHandler() {
		return handler;
	}

	/**
	 * @param handler the handler to set
	 */
	public void setHandler(String handler) {
		this.handler = handler;
	}

	/**
	 * @return the handlerCode
	 */
	public String getHandlerCode() {
		return handlerCode;
	}

	/**
	 * @param handlerCode the handlerCode to set
	 */
	public void setHandlerCode(String handlerCode) {
		this.handlerCode = handlerCode;
	}

	/**
	 * @return the verifyTime
	 */
	public Date getVerifyTime() {
		return verifyTime;
	}

	/**
	 * @param verifyTime the verifyTime to set
	 */
	public void setVerifyTime(Date verifyTime) {
		this.verifyTime = verifyTime;
	}

	/**
	 * @return the feedbackInfo
	 */
	public String getFeedbackInfo() {
		return feedbackInfo;
	}

	/**
	 * @param feedbackInfo the feedbackInfo to set
	 */
	public void setFeedbackInfo(String feedbackInfo) {
		this.feedbackInfo = feedbackInfo;
	}

	/**
	 * @return the expressNo
	 */
	public String getExpressNo() {
		return expressNo;
	}

	/**
	 * @param expressNo the expressNo to set
	 */
	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	/**
	 * @return the createUserName
	 */
	public String getCreateUserName() {
		return createUserName;
	}

	/**
	 * @param createUserName the createUserName to set
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	/**
	 * @return the createUserCode
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}

	/**
	 * @param createUserCode the createUserCode to set
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	/**
	 * @return the createOrgName
	 */
	public String getCreateOrgName() {
		return createOrgName;
	}

	/**
	 * @param createOrgName the createOrgName to set
	 */
	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}

	/**
	 * @return the createOrgCode
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}

	/**
	 * @param createOrgCode the createOrgCode to set
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

   
}