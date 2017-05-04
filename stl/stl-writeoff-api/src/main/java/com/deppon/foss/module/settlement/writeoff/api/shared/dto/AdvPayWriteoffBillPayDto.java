/**
 * Copyright 2013 STL TEAM
 */
/*
 * PROJECT NAME: stl-writeoff-api
 * PACKAGE NAME: com.deppon.foss.module.settlement.writeoff.api.shared.dto
 * FILE    NAME: AdvPayWriteoffBillPayDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.settlement.writeoff.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillAdvancedPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;

/**
 * 预付冲应付Dto参数实体
 * 
 * @author foss-qiaolifeng
 * @date 2012-10-25 下午2:42:01
 */
public class AdvPayWriteoffBillPayDto implements Serializable {

	/**
	 * 预付冲应付Dto参数实体序列号
	 */
	private static final long serialVersionUID = -6595412583022931803L;

	/**
	 * 查询标示
	 */
	private String queryByTab;
	
	/**
	 * 预付单
	 */
	private BillAdvancedPaymentEntity billAdvancedPaymentEntity;

	/**
	 * 预付单列表
	 */
	private List<BillAdvancedPaymentEntity> billAdvancedPaymentEntityList;

	/**
	 * 应付单
	 */
	private BillPayableEntity billPayableEntity;

	/**
	 * 应付单列表
	 */
	private List<BillPayableEntity> billPayableEntityList;
	
	/**
	 * 付款编号
	 */
	private String paymentNo;

	/**
	 * 付款单部门编码
	 */
	private String paymentOrgCode;

	/**
	 * 付款单部门名称
	 */
	private String paymentOrgName;

	/**
	 * 应付单部门编码
	 */
	private String payableOrgCode;

	/**
	 * 应付单部门名称
	 */
	private String payableOrgName;
	
	/**
	 * 应付单公司编码
	 */
	private String payableComCode;
	
	/**
	 * 应付单公司名称
	 */
	private String payableComName;

	/**
	 * 预付冲应付业务开始时间
	 */
	private Date startBusinessDate;

	/**
	 * 预付冲应付业务结束时间
	 */
	private Date endBusinessDate;

	/**
	 * 预付冲应付客户编码
	 */
	private String customerCode;

	/**
	 * 预付单是否有效
	 */
	private String active;

	/**
	 * 预付单是否红单
	 */
	private String isRedBack;

	/**
	 * 预付单汇款审批状态
	 */
	private String auditStatus;

	/**
	 * 预付单号集
	 */
	private List<String> advancesNos;

	/**
	 * 应付单号集
	 */
	private List<String> payableNos;

	/**
	 * 单据子类型集
	 */
	private List<String> billTypes;

	/**
	 * 预付单号
	 */
	private String advancesNo;

	/**
	 * 应付单号
	 */
	private String payableNo;

	/**
	 * 单据子类型
	 */
	private String billType;

	/**
	 * 来源单据类型
	 */
	private String sourceBillType;

	/**
	 * 付款单状态
	 */
	private String remitStatus;

	/**
	 * 支付状态
	 */
	private String payStatus;

	/**
	 * 应付单的审核状态
	 */
	private String approveStatus;
	
	/**
	 * 预付单总条数
	 */
	private int advPayNum;
	
	/**
	 * 预付单总金额
	 */
	private BigDecimal advPayAmount;
	
	/**
	 * 预付单总核销金额
	 */
	private BigDecimal advPayVerifyAmount;
	
	/**
	 * 预付单总未核销金额
	 */
	private BigDecimal advPayUnVerifyAmount;
	
	/**
	 * 应付单总条数
	 */
	private int billPayNum;
	
	/**
	 * 应付单总金额
	 */
	private BigDecimal billPayAmount;
	
	/**
	 * 应付单总已核销金额
	 */
	private BigDecimal billPayVerifyAmount;
	
	/**
	 * 应付单总未核销金额
	 */
	private BigDecimal billPayUnVerifyAmount;

	
	/**
	 * @return 
		queryByTab
	 */
	public String getQueryByTab() {
		return queryByTab;
	}

	
	/**
	 * @param 
		queryByTab
	 */
	public void setQueryByTab(String queryByTab) {
		this.queryByTab = queryByTab;
	}

	
	/**
	 * @return 
		billAdvancedPaymentEntity
	 */
	public BillAdvancedPaymentEntity getBillAdvancedPaymentEntity() {
		return billAdvancedPaymentEntity;
	}

	
	/**
	 * @param 
		billAdvancedPaymentEntity
	 */
	public void setBillAdvancedPaymentEntity(BillAdvancedPaymentEntity billAdvancedPaymentEntity) {
		this.billAdvancedPaymentEntity = billAdvancedPaymentEntity;
	}

	
	/**
	 * @return 
		billAdvancedPaymentEntityList
	 */
	public List<BillAdvancedPaymentEntity> getBillAdvancedPaymentEntityList() {
		return billAdvancedPaymentEntityList;
	}

	
	/**
	 * @param 
		billAdvancedPaymentEntityList
	 */
	public void setBillAdvancedPaymentEntityList(List<BillAdvancedPaymentEntity> billAdvancedPaymentEntityList) {
		this.billAdvancedPaymentEntityList = billAdvancedPaymentEntityList;
	}

	
	/**
	 * @return 
		billPayableEntity
	 */
	public BillPayableEntity getBillPayableEntity() {
		return billPayableEntity;
	}

	
	/**
	 * @param 
		billPayableEntity
	 */
	public void setBillPayableEntity(BillPayableEntity billPayableEntity) {
		this.billPayableEntity = billPayableEntity;
	}

	
	/**
	 * @return 
		billPayableEntityList
	 */
	public List<BillPayableEntity> getBillPayableEntityList() {
		return billPayableEntityList;
	}

	
	/**
	 * @param 
		billPayableEntityList
	 */
	public void setBillPayableEntityList(List<BillPayableEntity> billPayableEntityList) {
		this.billPayableEntityList = billPayableEntityList;
	}

	
	/**
	 * @return 
		paymentNo
	 */
	public String getPaymentNo() {
		return paymentNo;
	}

	
	/**
	 * @param 
		paymentNo
	 */
	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}

	
	/**
	 * @return 
		paymentOrgCode
	 */
	public String getPaymentOrgCode() {
		return paymentOrgCode;
	}

	
	/**
	 * @param 
		paymentOrgCode
	 */
	public void setPaymentOrgCode(String paymentOrgCode) {
		this.paymentOrgCode = paymentOrgCode;
	}

	
	/**
	 * @return 
		paymentOrgName
	 */
	public String getPaymentOrgName() {
		return paymentOrgName;
	}

	
	/**
	 * @param 
		paymentOrgName
	 */
	public void setPaymentOrgName(String paymentOrgName) {
		this.paymentOrgName = paymentOrgName;
	}

	
	/**
	 * @return 
		payableOrgCode
	 */
	public String getPayableOrgCode() {
		return payableOrgCode;
	}

	
	/**
	 * @param 
		payableOrgCode
	 */
	public void setPayableOrgCode(String payableOrgCode) {
		this.payableOrgCode = payableOrgCode;
	}

	
	/**
	 * @return 
		payableOrgName
	 */
	public String getPayableOrgName() {
		return payableOrgName;
	}

	
	/**
	 * @param 
		payableOrgName
	 */
	public void setPayableOrgName(String payableOrgName) {
		this.payableOrgName = payableOrgName;
	}

	
	/**
	 * @return 
		payableComCode
	 */
	public String getPayableComCode() {
		return payableComCode;
	}

	
	/**
	 * @param 
		payableComCode
	 */
	public void setPayableComCode(String payableComCode) {
		this.payableComCode = payableComCode;
	}

	
	/**
	 * @return 
		payableComName
	 */
	public String getPayableComName() {
		return payableComName;
	}

	
	/**
	 * @param 
		payableComName
	 */
	public void setPayableComName(String payableComName) {
		this.payableComName = payableComName;
	}

	
	/**
	 * @return 
		startBusinessDate
	 */
	public Date getStartBusinessDate() {
		return startBusinessDate;
	}

	
	/**
	 * @param 
		startBusinessDate
	 */
	public void setStartBusinessDate(Date startBusinessDate) {
		this.startBusinessDate = startBusinessDate;
	}

	
	/**
	 * @return 
		endBusinessDate
	 */
	public Date getEndBusinessDate() {
		return endBusinessDate;
	}

	
	/**
	 * @param 
		endBusinessDate
	 */
	public void setEndBusinessDate(Date endBusinessDate) {
		this.endBusinessDate = endBusinessDate;
	}

	
	/**
	 * @return 
		customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	
	/**
	 * @param 
		customerCode
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	
	/**
	 * @return 
		active
	 */
	public String getActive() {
		return active;
	}

	
	/**
	 * @param 
		active
	 */
	public void setActive(String active) {
		this.active = active;
	}

	
	/**
	 * @return 
		isRedBack
	 */
	public String getIsRedBack() {
		return isRedBack;
	}

	
	/**
	 * @param 
		isRedBack
	 */
	public void setIsRedBack(String isRedBack) {
		this.isRedBack = isRedBack;
	}

	
	/**
	 * @return 
		auditStatus
	 */
	public String getAuditStatus() {
		return auditStatus;
	}

	
	/**
	 * @param 
		auditStatus
	 */
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	
	/**
	 * @return 
		advancesNos
	 */
	public List<String> getAdvancesNos() {
		return advancesNos;
	}

	
	/**
	 * @param 
		advancesNos
	 */
	public void setAdvancesNos(List<String> advancesNos) {
		this.advancesNos = advancesNos;
	}

	
	/**
	 * @return 
		payableNos
	 */
	public List<String> getPayableNos() {
		return payableNos;
	}

	
	/**
	 * @param 
		payableNos
	 */
	public void setPayableNos(List<String> payableNos) {
		this.payableNos = payableNos;
	}

	
	/**
	 * @return 
		billTypes
	 */
	public List<String> getBillTypes() {
		return billTypes;
	}

	
	/**
	 * @param 
		billTypes
	 */
	public void setBillTypes(List<String> billTypes) {
		this.billTypes = billTypes;
	}

	
	/**
	 * @return 
		advancesNo
	 */
	public String getAdvancesNo() {
		return advancesNo;
	}

	
	/**
	 * @param 
		advancesNo
	 */
	public void setAdvancesNo(String advancesNo) {
		this.advancesNo = advancesNo;
	}

	
	/**
	 * @return 
		payableNo
	 */
	public String getPayableNo() {
		return payableNo;
	}

	
	/**
	 * @param 
		payableNo
	 */
	public void setPayableNo(String payableNo) {
		this.payableNo = payableNo;
	}

	
	/**
	 * @return 
		billType
	 */
	public String getBillType() {
		return billType;
	}

	
	/**
	 * @param 
		billType
	 */
	public void setBillType(String billType) {
		this.billType = billType;
	}

	
	/**
	 * @return 
		sourceBillType
	 */
	public String getSourceBillType() {
		return sourceBillType;
	}

	
	/**
	 * @param 
		sourceBillType
	 */
	public void setSourceBillType(String sourceBillType) {
		this.sourceBillType = sourceBillType;
	}

	
	/**
	 * @return 
		remitStatus
	 */
	public String getRemitStatus() {
		return remitStatus;
	}

	
	/**
	 * @param 
		remitStatus
	 */
	public void setRemitStatus(String remitStatus) {
		this.remitStatus = remitStatus;
	}

	
	/**
	 * @return 
		payStatus
	 */
	public String getPayStatus() {
		return payStatus;
	}

	
	/**
	 * @param 
		payStatus
	 */
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	
	/**
	 * @return 
		approveStatus
	 */
	public String getApproveStatus() {
		return approveStatus;
	}

	
	/**
	 * @param 
		approveStatus
	 */
	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	
	/**
	 * @return 
		advPayNum
	 */
	public int getAdvPayNum() {
		return advPayNum;
	}

	
	/**
	 * @param 
		advPayNum
	 */
	public void setAdvPayNum(int advPayNum) {
		this.advPayNum = advPayNum;
	}

	
	/**
	 * @return 
		advPayAmount
	 */
	public BigDecimal getAdvPayAmount() {
		return advPayAmount;
	}

	
	/**
	 * @param 
		advPayAmount
	 */
	public void setAdvPayAmount(BigDecimal advPayAmount) {
		this.advPayAmount = advPayAmount;
	}

	
	/**
	 * @return 
		advPayVerifyAmount
	 */
	public BigDecimal getAdvPayVerifyAmount() {
		return advPayVerifyAmount;
	}

	
	/**
	 * @param 
		advPayVerifyAmount
	 */
	public void setAdvPayVerifyAmount(BigDecimal advPayVerifyAmount) {
		this.advPayVerifyAmount = advPayVerifyAmount;
	}

	
	/**
	 * @return 
		advPayUnVerifyAmount
	 */
	public BigDecimal getAdvPayUnVerifyAmount() {
		return advPayUnVerifyAmount;
	}

	
	/**
	 * @param 
		advPayUnVerifyAmount
	 */
	public void setAdvPayUnVerifyAmount(BigDecimal advPayUnVerifyAmount) {
		this.advPayUnVerifyAmount = advPayUnVerifyAmount;
	}

	
	/**
	 * @return 
		billPayNum
	 */
	public int getBillPayNum() {
		return billPayNum;
	}

	
	/**
	 * @param 
		billPayNum
	 */
	public void setBillPayNum(int billPayNum) {
		this.billPayNum = billPayNum;
	}

	
	/**
	 * @return 
		billPayAmount
	 */
	public BigDecimal getBillPayAmount() {
		return billPayAmount;
	}

	
	/**
	 * @param 
		billPayAmount
	 */
	public void setBillPayAmount(BigDecimal billPayAmount) {
		this.billPayAmount = billPayAmount;
	}

	
	/**
	 * @return 
		billPayVerifyAmount
	 */
	public BigDecimal getBillPayVerifyAmount() {
		return billPayVerifyAmount;
	}

	
	/**
	 * @param 
		billPayVerifyAmount
	 */
	public void setBillPayVerifyAmount(BigDecimal billPayVerifyAmount) {
		this.billPayVerifyAmount = billPayVerifyAmount;
	}

	
	/**
	 * @return 
		billPayUnVerifyAmount
	 */
	public BigDecimal getBillPayUnVerifyAmount() {
		return billPayUnVerifyAmount;
	}

	
	/**
	 * @param 
		billPayUnVerifyAmount
	 */
	public void setBillPayUnVerifyAmount(BigDecimal billPayUnVerifyAmount) {
		this.billPayUnVerifyAmount = billPayUnVerifyAmount;
	}

	

}
