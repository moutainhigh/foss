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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/domain/NotificationEntity.java
 * 
 * FILE NAME        	: NotificationEntity.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;

/**
 * 客户通知记录
 * @author ibm-wangfei
 * @date Oct 19, 2012 3:15:01 PM
 */
public class NotificationEntity2 extends BaseEntity {
	private static final long serialVersionUID = 4812215769362148639L;
	/** 必输字段 begin */
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 通知类型
	 */
	private String noticeType;
	/**
	 * 通知内容
	 */
	private String noticeContent;
	/**
	 * 通知信息_语音
	 */
	private String noticeContentVoice;
	/**
	 * 操作人
	 */
	private String operator;
	/**
	 * 操作人编码
	 */
	private String operatorNo;
	/**
	 * 操作部门
	 */
	private String operateOrgName;
	/**
	 * 操作部门编码
	 */
	private String operateOrgCode;
	/**
	 * 接收人姓名
	 */
	private String consignee;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 操作时间
	 */
	private Date operateTime;
	/**
	 * 模块名称
	 */
	private String moduleName;
	/** 必输字段 end */
	/**
	 * 币种
	 */
	private String currencyCode;
	
	/**
	 * 客户资质
	 */
	private String customerQulification;
	/**
	 * 小票费用
	 */
	private String smallTicketFee;
	/**
	 * 付款方式
	 */
	private String paymentType;
	/**
	 * 送货日期
	 */
	private String deliverDate;
	/**
	 * 送货要求
	 */
	private String deliverRequire;
	/**
	 * 是否征收仓储费
	 */
	private String isStorageCharge;
	/**
	 * 预计提货时间
	 */
	private String estimatedPickupTime;
	/**
	 * 派送方式
	 */
	private String receiveMethod;
	/**
	 * 通知结果
	 */
	private String noticeResult;
	/**
	 * 异常备注
	 */
	private String exceptionNotes;

	/**
	 * 是否必送货
	 */
	private String isSentRequired;
	/**
	 * 是否需要发票
	 */
	private String isNeedInvoice;
	/**
	 * 通知件数
	 */
	private Integer arriveGoodsQty;
	/**
	 * 默认查询通知结果
	 */
	private String[] notifyResults;
	/**
	 * 排序
	 */
	private String order;
	/**
	 * new通知结果
	 */
	private String newNoticeResult;
	/**
	 * 收货客户联系人
	 */
	private String receiveCustomerContact;
	/**
	 * 收货客户手机号码
	 */
	private String receiveCustomerMobilephone;
	/**
	 * 送货类型
	 */
	private String deliverType;
	/**
	 * 收货客户code
	 */
	private String receiveCustomerCode;
	/**
	 * 创建部门code
	 */
	private String createOrgCode;
	/**
	 * 到付金额
	 */
	private BigDecimal toPayAmount;
	/**
	 * 语音实体对应的id
	 */
	private String noticeContentVoiceID;
	// 是否提前通知
	private String isPreNotify;
	// 通知时车辆状态
	private String taskStatus;

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
	 * @return the customerQulification
	 */
	public String getCustomerQulification() {
		return customerQulification;
	}

	/**
	 * @param customerQulification the customerQulification to see
	 */
	public void setCustomerQulification(String customerQulification) {
		this.customerQulification = customerQulification;
	}

	/**
	 * @return the smallTicketFee
	 */
	public String getSmallTicketFee() {
		return smallTicketFee;
	}

	/**
	 * @param smallTicketFee the smallTicketFee to see
	 */
	public void setSmallTicketFee(String smallTicketFee) {
		this.smallTicketFee = smallTicketFee;
	}

	/**
	 * @return the paymentType
	 */
	public String getPaymentType() {
		return paymentType;
	}

	/**
	 * @param paymentType the paymentType to see
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	/**
	 * @return the deliverRequire
	 */
	public String getDeliverRequire() {
		return deliverRequire;
	}

	/**
	 * @param deliverRequire the deliverRequire to see
	 */
	public void setDeliverRequire(String deliverRequire) {
		this.deliverRequire = deliverRequire;
	}

	/**
	 * @return the isStorageCharge
	 */
	public String getIsStorageCharge() {
		return isStorageCharge;
	}

	/**
	 * @param isStorageCharge the isStorageCharge to see
	 */
	public void setIsStorageCharge(String isStorageCharge) {
		this.isStorageCharge = isStorageCharge;
	}

	/**
	 * @return the estimatedPickupTime
	 */
	public String getEstimatedPickupTime() {
		return estimatedPickupTime;
	}

	/**
	 * @param estimatedPickupTime the estimatedPickupTime to see
	 */
	public void setEstimatedPickupTime(String estimatedPickupTime) {
		this.estimatedPickupTime = estimatedPickupTime;
	}

	/**
	 * @return the noticeType
	 */
	public String getNoticeType() {
		return noticeType;
	}

	/**
	 * @param noticeType the noticeType to see
	 */
	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}

	/**
	 * @return the noticeContent
	 */
	public String getNoticeContent() {
		return noticeContent;
	}

	/**
	 * @param noticeContent the noticeContent to see
	 */
	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	/**
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * @param operator the operator to see
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * @return the operatorNo
	 */
	public String getOperatorNo() {
		return operatorNo;
	}

	/**
	 * @param operatorNo the operatorNo to see
	 */
	public void setOperatorNo(String operatorNo) {
		this.operatorNo = operatorNo;
	}

	/**
	 * @return the operateOrgName
	 */
	public String getOperateOrgName() {
		return operateOrgName;
	}

	/**
	 * @param operateOrgName the operateOrgName to see
	 */
	public void setOperateOrgName(String operateOrgName) {
		this.operateOrgName = operateOrgName;
	}

	/**
	 * @return the operateOrgCode
	 */
	public String getOperateOrgCode() {
		return operateOrgCode;
	}

	/**
	 * @param operateOrgCode the operateOrgCode to see
	 */
	public void setOperateOrgCode(String operateOrgCode) {
		this.operateOrgCode = operateOrgCode;
	}

	/**
	 * @return the consignee
	 */
	public String getConsignee() {
		return consignee;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * @param consignee the consignee to see
	 */
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile the mobile to see
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the receiveMethod
	 */
	public String getReceiveMethod() {
		return receiveMethod;
	}

	/**
	 * @param receiveMethod the receiveMethod to see
	 */
	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	/**
	 * @return the noticeResult
	 */
	public String getNoticeResult() {
		return noticeResult;
	}

	/**
	 * @param noticeResult the noticeResult to see
	 */
	public void setNoticeResult(String noticeResult) {
		this.noticeResult = noticeResult;
	}

	/**
	 * @return the exceptionNotes
	 */
	public String getExceptionNotes() {
		return exceptionNotes;
	}

	/**
	 * @param exceptionNotes the exceptionNotes to see
	 */
	public void setExceptionNotes(String exceptionNotes) {
		this.exceptionNotes = exceptionNotes;
	}

	/**
	 * @return the operateTime
	 */
	@DateFormat(formate = "yyyy-MM-dd hh:mm:ss")
	public Date getOperateTime() {
		return operateTime;
	}

	/**
	 * @param operateTime the operateTime to see
	 */
	@DateFormat(formate = "yyyy-MM-dd hh:mm:ss")
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	/**
	 * @return the isSentRequired
	 */
	public String getIsSentRequired() {
		return isSentRequired;
	}

	/**
	 * @param isSentRequired the isSentRequired to see
	 */
	public void setIsSentRequired(String isSentRequired) {
		this.isSentRequired = isSentRequired;
	}

	/**
	 * @return the isNeedInvoice
	 */
	public String getIsNeedInvoice() {
		return isNeedInvoice;
	}

	/**
	 * @param isNeedInvoice the isNeedInvoice to see
	 */
	public void setIsNeedInvoice(String isNeedInvoice) {
		this.isNeedInvoice = isNeedInvoice;
	}

	/**
	 * @return the deliverDate
	 */
	public String getDeliverDate() {
		return deliverDate;
	}

	/**
	 * @param deliverDate the deliverDate to see
	 */
	public void setDeliverDate(String deliverDate) {
		this.deliverDate = deliverDate;
	}

	/**
	 * @return the receiveCustomerMobilephone
	 */
	public String getReceiveCustomerMobilephone() {
		return receiveCustomerMobilephone;
	}

	/**
	 * @param receiveCustomerMobilephone the receiveCustomerMobilephone to see
	 */
	public void setReceiveCustomerMobilephone(String receiveCustomerMobilephone) {
		this.receiveCustomerMobilephone = receiveCustomerMobilephone;
	}

	/**
	 * @return the deliverType
	 */
	public String getDeliverType() {
		return deliverType;
	}

	/**
	 * @param deliverType the deliverType to see
	 */
	public void setDeliverType(String deliverType) {
		this.deliverType = deliverType;
	}

	/**
	 * @return the receiveCustomerCode
	 */
	public String getReceiveCustomerCode() {
		return receiveCustomerCode;
	}

	/**
	 * @param receiveCustomerCode the receiveCustomerCode to see
	 */
	public void setReceiveCustomerCode(String receiveCustomerCode) {
		this.receiveCustomerCode = receiveCustomerCode;
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
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the toPayAmount
	 */
	public BigDecimal getToPayAmount() {
		return toPayAmount;
	}

	/**
	 * @param toPayAmount the toPayAmount to see
	 */
	public void setToPayAmount(BigDecimal toPayAmount) {
		this.toPayAmount = toPayAmount;
	}

	/**
	 * @return the receiveCustomerContact
	 */
	public String getReceiveCustomerContact() {
		return receiveCustomerContact;
	}

	/**
	 * @param receiveCustomerContact the receiveCustomerContact to see
	 */
	public void setReceiveCustomerContact(String receiveCustomerContact) {
		this.receiveCustomerContact = receiveCustomerContact;
	}

	/**
	 * @return the arriveGoodsQty
	 */
	public Integer getArriveGoodsQty() {
		return arriveGoodsQty;
	}

	/**
	 * @param arriveGoodsQty the arriveGoodsQty to see
	 */
	public void setArriveGoodsQty(Integer arriveGoodsQty) {
		this.arriveGoodsQty = arriveGoodsQty;
	}

	/**
	 * @return the notifyResults
	 */
	public String[] getNotifyResults() {
		return notifyResults;
	}

	/**
	 * @param notifyResults the notifyResults to see
	 */
	public void setNotifyResults(String[] notifyResults) {
		this.notifyResults = notifyResults;
	}

	/**
	 * @return the order
	 */
	public String getOrder() {
		return order;
	}

	/**
	 * @param order the order to see
	 */
	public void setOrder(String order) {
		this.order = order;
	}

	/**
	 * @return the newNoticeResult
	 */
	public String getNewNoticeResult() {
		return newNoticeResult;
	}

	/**
	 * @param newNoticeResult the newNoticeResult to see
	 */
	public void setNewNoticeResult(String newNoticeResult) {
		this.newNoticeResult = newNoticeResult;
	}

	/**
	 * @return the moduleName
	 */
	public String getModuleName() {
		return moduleName;
	}

	/**
	 * @param moduleName the moduleName to see
	 */
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	@Override
	public String toString() {
		return "NotificationEntity [waybillNo=" + waybillNo + ", noticeType=" + noticeType + ", noticeContent=" + noticeContent + ", operator=" + operator + ", operatorNo=" + operatorNo + ", operateOrgName=" + operateOrgName + ", operateOrgCode="
				+ operateOrgCode + ", consignee=" + consignee + ", mobile=" + mobile + ", operateTime=" + operateTime + ", moduleName=" + moduleName + ", customerQulification=" + customerQulification + ", smallTicketFee=" + smallTicketFee
				+ ", paymentType=" + paymentType + ", deliverDate=" + deliverDate + ", deliverRequire=" + deliverRequire + ", isStorageCharge=" + isStorageCharge + ", estimatedPickupTime=" + estimatedPickupTime + ", receiveMethod=" + receiveMethod
				+ ", noticeResult=" + noticeResult + ", exceptionNotes=" + exceptionNotes + ", isSentRequired=" + isSentRequired + ", isNeedInvoice=" + isNeedInvoice + ", arriveGoodsQty=" + arriveGoodsQty + ", notifyResults="
				+ Arrays.toString(notifyResults) + ", order=" + order + ", newNoticeResult=" + newNoticeResult + ", receiveCustomerContact=" + receiveCustomerContact + ", receiveCustomerMobilephone=" + receiveCustomerMobilephone + ", deliverType="
				+ deliverType + ", receiveCustomerCode=" + receiveCustomerCode + ", createOrgCode=" + createOrgCode + ", toPayAmount=" + toPayAmount + "]";
	}

	public String getNoticeContentVoice() {
		return noticeContentVoice;
	}

	public void setNoticeContentVoice(String noticeContentVoice) {
		this.noticeContentVoice = noticeContentVoice;
	}

	public String getNoticeContentVoiceID() {
		return noticeContentVoiceID;
	}

	public void setNoticeContentVoiceID(String noticeContentVoiceID) {
		this.noticeContentVoiceID = noticeContentVoiceID;
	}

	public String getIsPreNotify() {
		return isPreNotify;
	}

	public void setIsPreNotify(String isPreNotify) {
		this.isPreNotify = isPreNotify;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

}