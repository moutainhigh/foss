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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/dto/RtSearchReturnBillProcessDto.java
 *
 * FILE NAME        	: RtSearchReturnBillProcessDto.java
 *
 * AUTHOR			: FOSS接送货系统开发组
 *
 * HOME PAGE		: http://www.deppon.com
 *
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 签收单返单
 * @date 2012-10-20 下午2:07:55
 */
public class RtSearchReturnBillProcessDto implements Serializable {
	private static final long serialVersionUID = 1005324943392486078L;

	// 签收单返单ID
	private String id;

	// 运单号
	private String waybillNo;

	// 返单状态
	private String returnbillStatus;

	// 返单时间
	private Date returnbillTime;

	// 处理人
	private String handler;

	// 处理人编码
	private String handlerCode;

	// 核实时间
	private Date verifyTime;

	// 反馈信息
	private String feedbackInfo;

	// 快递号
	private String expressNo;

	// 快递公司
	private String expressCompany;

	// 创建人名字
	private String createUserName;

	// 创建人编码
	private String createUserCode;

	// 创建组织名字
	private String createOrgName;

	// 创建组织编码
	private String createOrgCode;

	// 创建时间
	private Date createTime;

	// 返单类型
	private String returnbillType;

	// 是否发送短信
	private String issendSms;
	/**
	 * 发货客户手机
	 */
	private String deliveryCustomerMobilephone;

	/**
	 * 发货客户联系人
	 */
	private String deliveryCustomerContact;
	/**
	 * 发货客户电话
	 */
	private String deliveryCustomerTel;

	/**
	 * 收货部门
	 */
	private String receiveOrgName;
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
	 * 返单ID数组
	 */
	private String[] ids;

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	//是否本部门
	private String orgDiff;

	public String getOrgDiff() {
		return orgDiff;
	}

	public void setOrgDiff(String orgDiff) {
		this.orgDiff = orgDiff;
	}

	public String getReceiveOrgName() {
		return receiveOrgName;
	}

	public void setReceiveOrgName(String receiveOrgName) {
		this.receiveOrgName = receiveOrgName;
	}

	public String getDeliveryCustomerTel() {
		return deliveryCustomerTel;
	}

	public void setDeliveryCustomerTel(String deliveryCustomerTel) {
		this.deliveryCustomerTel = deliveryCustomerTel;
	}

	/**
	 * @return deliveryCustomerMobilephone : return the property deliveryCustomerMobilephone.
	 */
	public String getDeliveryCustomerMobilephone() {
		return deliveryCustomerMobilephone;
	}

	/**
	 * @param deliveryCustomerMobilephone : set the property deliveryCustomerMobilephone.
	 */
	public void setDeliveryCustomerMobilephone(String deliveryCustomerMobilephone) {
		this.deliveryCustomerMobilephone = deliveryCustomerMobilephone;
	}

	/**
	 * @return deliveryCustomerContact : return the property deliveryCustomerContact.
	 */
	public String getDeliveryCustomerContact() {
		return deliveryCustomerContact;
	}

	/**
	 * @param deliveryCustomerContact : set the property deliveryCustomerContact.
	 */
	public void setDeliveryCustomerContact(String deliveryCustomerContact) {
		this.deliveryCustomerContact = deliveryCustomerContact;
	}

	/**
	 * @return the issendSms
	 */
	public String getIssendSms() {
		return issendSms;
	}

	/**
	 * @param issendSms the issendSms to see
	 */
	public void setIssendSms(String issendSms) {
		this.issendSms = issendSms;
	}

	/**
	 * @return the returnbillType
	 */
	public String getReturnbillType() {
		return returnbillType;
	}

	/**
	 * @param returnBillType the return bill type
	 */
	public void setReturnbillType(String returnBillType) {
		this.returnbillType = returnBillType;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to see
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo the waybillNo to see
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
	 * @param returnbillStatus the returnbillStatus to see
	 */
	public void setReturnbillStatus(String returnbillStatus) {
		this.returnbillStatus = returnbillStatus;
	}

	/**
	 * @return the returnbillTime
	 */
	public Date getReturnbillTime() {
		return returnbillTime;
	}

	/**
	 * @param returnbillTime the returnbillTime to see
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
	 * @param handler the handler to see
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
	 * @param handlerCode the handlerCode to see
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
	 * @param verifyTime the verifyTime to see
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
	 * @param feedbackInfo the feedbackInfo to see
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
	 * @param expressNo the expressNo to see
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
	 * @param createUserName the createUserName to see
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
	 * @param createUserCode the createUserCode to see
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
	 * @param createOrgName the createOrgName to see
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
	 * @param createOrgCode the createOrgCode to see
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
	 * @param createTime the createTime to see
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the expressCompany
	 */
	public String getExpressCompany() {
		return expressCompany;
	}

	/**
	 * @param expressCompany the expressCompany to see
	 */
	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
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