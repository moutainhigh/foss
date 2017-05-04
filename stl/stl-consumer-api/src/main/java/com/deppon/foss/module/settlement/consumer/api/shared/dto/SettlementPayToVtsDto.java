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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/domain/RepaymentEntity.java
 * 
 * FILE NAME        	: RepaymentEntity.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;

/**
 * 付款.
 *
 * @author 043258-
 * foss-zhaobin
 * @date 2013-3-11
 * 下午4:36:59
 * @since
 * @version
 */
public class SettlementPayToVtsDto extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** 当前登录用户信息. */
//	private CurrentInfo currentInfo;
	
	/**
	 * 来源系统
	 * @author 218392
	 * VTS系统默认：自己生成的GUID 
	 */
	private String businessId;
	
	//员工工号
	private String empCode;
	
	//员工姓名
	private String empName;
	
	//当前登录部门编码
	private String currentDeptCode;
	
	//当前登录部门名称
	private String currentDeptName;
	
	/** 运单号. */
	private String waybillNo;
	
	/** 付款编号. */
	private String repaymentNo;
	
	/** 是否有效. */
	private String active;
	
	/** 客户编码. */
	private String consigneeCode;
	
	/** 客户名称. */
	private String consigneeName;
	
	/** 客户名称原. */
	private String beforeConsigneeName;
	
	/** 付款方式. */
	private String paymentType;
	
	/** 款项认领编号. */
	private String claimNo;
	
	/** 实付运费. */
	private BigDecimal actualFreight;
	
	/** 代收货款. */
	private BigDecimal codAmount;
	
	/** 付款时间. */
	private Date paymentTime;
	
	/** 最后更新时间. */
	private Date modifyTime;
	
	/** 仓储费. */
	private BigDecimal storageFee;
	/**
	 * 修改前仓储费
	 */
	private BigDecimal storageFeeOld;
	/**
	 * 修改仓储费备注
	 */
	private String updateStorageChargeReason;
	/** 操作人. */
	private String operator;
	
	/** 操作人编码. */
	private String operatorCode;
	
	/** 操作部门. */
	private String operateOrgName;
	
	/** 操作部门编码. */
	private String operateOrgCode;
	
	/** 币种 RMB. */
	private String currencyCode;
	
	/** 是否审批中. */
	private String isRfcing;
	
	/** 是否已有财务单据. */
	private String stlbillGeneratedStatus;
	
	/** job id. */
	private String jobId;
	
	/** 提货人姓名. */
	private String deliverymanName;
	
	/** 证件类型. */
	private String identifyType;
	
	/** 证件号码. */
	private String identifyCode;
	
	/** 密码. */
	private String passWord;
	
	/** 后台job时间间隔. */
	private BigDecimal timeRange;
	
	/** 财务单据初始状态. */
	private String firStlbillGeneratedStatus;
	
	/** 默认job id. */
	private String defJobId;
	/**
	 * 当前时间
	 */
	private Date currentTime;
	 /**
     * 证件号码（代收人）
     */
    private String codIdentifyCode;
    
    /**
     * 证件类型（代收人）
     */
    private String codIdentifyType;
    
    /**
     * 是否结清货款
     */
    private String settleStatus;
    
    /**
     * 是否快速结清
     */
    private Boolean isQuick;
    
	/************************* 返回vts属性 ****************************************/

	/**
	 * 结清货款是否成功
	 */
	private boolean isSuccess;
	
	/**
	 * 消息
	 */
	private String msg;

	public Date getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(Date currentTime) {
		this.currentTime = currentTime;
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
	 * Gets the repayment no.
	 *
	 * @return the repaymentNo
	 */
	public String getRepaymentNo() {
		return repaymentNo;
	}

	/**
	 * Sets the repayment no.
	 *
	 * @param repaymentNo the repaymentNo to see
	 */
	public void setRepaymentNo(String repaymentNo) {
		this.repaymentNo = repaymentNo;
	}

	/**
	 * Gets the active.
	 *
	 * @return the active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * Sets the active.
	 *
	 * @param active the active to see
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * Gets the consignee code.
	 *
	 * @return the consigneeCode
	 */
	public String getConsigneeCode() {
		return consigneeCode;
	}

	/**
	 * Sets the consignee code.
	 *
	 * @param consigneeCode the consigneeCode to see
	 */
	public void setConsigneeCode(String consigneeCode) {
		this.consigneeCode = consigneeCode;
	}

	/**
	 * Gets the consignee name.
	 *
	 * @return the consigneeName
	 */
	public String getConsigneeName() {
		return consigneeName;
	}

	/**
	 * Sets the consignee name.
	 *
	 * @param consigneeName the consigneeName to see
	 */
	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	/**
	 * Gets the payment type.
	 *
	 * @return the paymentType
	 */
	public String getPaymentType() {
		return paymentType;
	}

	/**
	 * Sets the payment type.
	 *
	 * @param paymentType the paymentType to see
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	/**
	 * Gets the claim no.
	 *
	 * @return the claimNo
	 */
	public String getClaimNo() {
		return claimNo;
	}

	/**
	 * Sets the claim no.
	 *
	 * @param claimNo the claimNo to see
	 */
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}

	/**
	 * Gets the payment time.
	 *
	 * @return the paymentTime
	 */
	public Date getPaymentTime() {
		return paymentTime;
	}

	/**
	 * Sets the payment time.
	 *
	 * @param paymentTime the paymentTime to see
	 */
	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
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
	 * Gets the currency code.
	 *
	 * @return the currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * Sets the currency code.
	 *
	 * @param currencyCode the currencyCode to see
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * Gets the is rfcing.
	 *
	 * @return the isRfcing
	 */
	public String getIsRfcing() {
		return isRfcing;
	}

	/**
	 * Sets the is rfcing.
	 *
	 * @param isRfcing the isRfcing to see
	 */
	public void setIsRfcing(String isRfcing) {
		this.isRfcing = isRfcing;
	}

	/**
	 * Gets the stlbill generated status.
	 *
	 * @return the stlbillGeneratedStatus
	 */
	public String getStlbillGeneratedStatus() {
		return stlbillGeneratedStatus;
	}

	/**
	 * Sets the stlbill generated status.
	 *
	 * @param stlbillGeneratedStatus the stlbillGeneratedStatus to see
	 */
	public void setStlbillGeneratedStatus(String stlbillGeneratedStatus) {
		this.stlbillGeneratedStatus = stlbillGeneratedStatus;
	}

	/**
	 * Gets the deliveryman name.
	 *
	 * @return the deliverymanName
	 */
	public String getDeliverymanName() {
		return deliverymanName;
	}

	/**
	 * Sets the deliveryman name.
	 *
	 * @param deliverymanName the deliverymanName to see
	 */
	public void setDeliverymanName(String deliverymanName) {
		this.deliverymanName = deliverymanName;
	}

	/**
	 * Gets the identify type.
	 *
	 * @return the identifyType
	 */
	public String getIdentifyType() {
		return identifyType;
	}

	/**
	 * Sets the identify type.
	 *
	 * @param identifyType the identifyType to see
	 */
	public void setIdentifyType(String identifyType) {
		this.identifyType = identifyType;
	}

	/**
	 * Gets the identify code.
	 *
	 * @return the identifyCode
	 */
	public String getIdentifyCode() {
		return identifyCode;
	}

	/**
	 * Sets the identify code.
	 *
	 * @param identifyCode the identifyCode to see
	 */
	public void setIdentifyCode(String identifyCode) {
		this.identifyCode = identifyCode;
	}

	/**
	 * Gets the job id.
	 *
	 * @return the jobId
	 */
	public String getJobId() {
		return jobId;
	}

	/**
	 * Sets the job id.
	 *
	 * @param jobId the jobId to see
	 */
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	/**
	 * Gets the pass word.
	 *
	 * @return the passWord
	 */
	public String getPassWord() {
		return passWord;
	}

	/**
	 * Sets the pass word.
	 *
	 * @param passWord the passWord to see
	 */
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	/**
	 * Gets the actual freight.
	 *
	 * @return the actualFreight
	 */
	public BigDecimal getActualFreight() {
		return actualFreight;
	}

	/**
	 * Sets the actual freight.
	 *
	 * @param actualFreight the actualFreight to see
	 */
	public void setActualFreight(BigDecimal actualFreight) {
		this.actualFreight = actualFreight;
	}

	/**
	 * Gets the cod amount.
	 *
	 * @return the codAmount
	 */
	public BigDecimal getCodAmount() {
		return codAmount;
	}

	/**
	 * Sets the cod amount.
	 *
	 * @param codAmount the codAmount to see
	 */
	public void setCodAmount(BigDecimal codAmount) {
		this.codAmount = codAmount;
	}

	/**
	 * Gets the storage fee.
	 *
	 * @return the storageFee
	 */
	public BigDecimal getStorageFee() {
		return storageFee;
	}

	/**
	 * Sets the storage fee.
	 *
	 * @param storageFee the storageFee to see
	 */
	public void setStorageFee(BigDecimal storageFee) {
		this.storageFee = storageFee;
	}

	/**
	 * Gets the fir stlbill generated status.
	 *
	 * @return the firStlbillGeneratedStatus
	 */
	public String getFirStlbillGeneratedStatus() {
		return firStlbillGeneratedStatus;
	}

	/**
	 * Sets the fir stlbill generated status.
	 *
	 * @param firStlbillGeneratedStatus the firStlbillGeneratedStatus to see
	 */
	public void setFirStlbillGeneratedStatus(String firStlbillGeneratedStatus) {
		this.firStlbillGeneratedStatus = firStlbillGeneratedStatus;
	}

	/**
	 * Gets the def job id.
	 *
	 * @return the defJobId
	 */
	public String getDefJobId() {
		return defJobId;
	}

	/**
	 * Sets the def job id.
	 *
	 * @param defJobId the defJobId to see
	 */
	public void setDefJobId(String defJobId) {
		this.defJobId = defJobId;
	}

	/**
	 * Gets the modify time.
	 *
	 * @return the modifyTime
	 */
	public Date getModifyTime()
	{
		return modifyTime;
	}

	/**
	 * Sets the modify time.
	 *
	 * @param modifyTime the modifyTime to see
	 */
	public void setModifyTime(Date modifyTime)
	{
		this.modifyTime = modifyTime;
	}

	/**
	 * Gets the time range.
	 *
	 * @return the time range
	 */
	public BigDecimal getTimeRange() {
		return timeRange;
	}

	/**
	 * Sets the time range.
	 *
	 * @param timeRange the new time range
	 */
	public void setTimeRange(BigDecimal timeRange) {
		this.timeRange = timeRange;
	}

	public String getBeforeConsigneeName() {
		return beforeConsigneeName;
	}

	public void setBeforeConsigneeName(String beforeConsigneeName) {
		this.beforeConsigneeName = beforeConsigneeName;
	}

	public String getCodIdentifyCode() {
		return codIdentifyCode;
	}

	public void setCodIdentifyCode(String codIdentifyCode) {
		this.codIdentifyCode = codIdentifyCode;
	}

	public String getCodIdentifyType() {
		return codIdentifyType;
	}

	public void setCodIdentifyType(String codIdentifyType) {
		this.codIdentifyType = codIdentifyType;
	}

	public BigDecimal getStorageFeeOld() {
		return storageFeeOld;
	}

	public void setStorageFeeOld(BigDecimal storageFeeOld) {
		this.storageFeeOld = storageFeeOld;
	}

	public String getUpdateStorageChargeReason() {
		return updateStorageChargeReason;
	}

	public void setUpdateStorageChargeReason(String updateStorageChargeReason) {
		this.updateStorageChargeReason = updateStorageChargeReason;
	}

	public boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getSettleStatus() {
		return settleStatus;
	}

	public void setSettleStatus(String settleStatus) {
		this.settleStatus = settleStatus;
	}

//	public CurrentInfo getCurrentInfo() {
//		return currentInfo;
//	}
//
//	public void setCurrentInfo(CurrentInfo currentInfo) {
//		this.currentInfo = currentInfo;
//	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getCurrentDeptCode() {
		return currentDeptCode;
	}

	public void setCurrentDeptCode(String currentDeptCode) {
		this.currentDeptCode = currentDeptCode;
	}

	public String getCurrentDeptName() {
		return currentDeptName;
	}

	public void setCurrentDeptName(String currentDeptName) {
		this.currentDeptName = currentDeptName;
	}

	public Boolean getIsQuick() {
		return isQuick;
	}

	public void setIsQuick(Boolean isQuick) {
		this.isQuick = isQuick;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	
	
}