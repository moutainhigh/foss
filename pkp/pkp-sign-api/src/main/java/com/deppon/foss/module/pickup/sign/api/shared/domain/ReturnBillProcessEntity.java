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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/domain/ReturnBillProcessEntity.java
 * 
 * FILE NAME        	: ReturnBillProcessEntity.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;

/**
 * 签收单返单
 * @date 2012-11-20 
 */
public class ReturnBillProcessEntity extends BaseEntity {
	
	
	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;
		

    /**
     * 运单号
     */
    private String waybillNo;
    
    /**
     * 返单状态
     */
    private String returnbillStatus;
    
    /**
     * 返单type
     */
    private String returnbillType;
    
    /**
     * 返单时间
     */
    private Date returnbillTime;
    
    /**
     * 处理人
     */
    private String handler;
    
    /**
     *确认时间
     */
    private Date verifyTime;
    
    /**
     * 反馈信息
     */
    private String feedbackInfo;

    /**
     * 快递号
     */
    private String expressNo;
    
    /**
     * 快递公司
     */
    private String expressCompany;

    /**
     * 创建人名字
     */
    private String createUserName;

    /**
     * 创建人编码
     */
    private String createUserCode;
    
    /**
     * 创建组织名字
     */
    private String createOrgName;

    /**
     * 创建组织编码 
     */
    private String createOrgCode;

    /**
     * 创建时间
     */
    private Date createTime;

	/**
	 * 确认人
	 */
	private String confirmHandler;
	/**
	 * 确认时间
	 */
	private Date confirmTime;
	/**
	 * 返单确认
	 */
	private String returnbillConfirm;


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
	 * @return the returnbillStatus
	 */
	public String getReturnbillStatus() {
		return returnbillStatus;
	}


	/**
	 * @param returnbillStatus the returnbillStatus to set
	 */
	public void setReturnbillStatus(String returnbillStatus) {
		this.returnbillStatus = returnbillStatus;
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
	 * @return the expressCompany
	 */
	public String getExpressCompany() {
		return expressCompany;
	}


	/**
	 * @param expressCompany the expressCompany to set
	 */
	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
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


	/**
	 * @return the verifyTime
	 */
	public Date getVerifyTime() {
		return verifyTime;
	}


	@DateFormat
    public void setVerifyTime(Date verifyTime) {
        this.verifyTime = verifyTime;
    }

	public String getConfirmHandler() {
		return confirmHandler;
	}

	public void setConfirmHandler(String confirmHandler) {
		this.confirmHandler = confirmHandler;
	}

	public Date getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	public String getReturnbillConfirm() {
		return returnbillConfirm;
	}

	public void setReturnbillConfirm(String returnbillConfirm) {
		this.returnbillConfirm = returnbillConfirm;
	}
}