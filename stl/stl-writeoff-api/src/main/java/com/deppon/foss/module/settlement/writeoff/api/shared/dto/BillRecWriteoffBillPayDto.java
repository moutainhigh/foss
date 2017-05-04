/**
 * Copyright 2013 STL TEAM
 */
/*
 * PROJECT NAME: stl-writeoff-api
 * PACKAGE NAME: com.deppon.foss.module.settlement.writeoff.api.shared.dto
 * FILE    NAME: BillRecWriteoffBillPayDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.settlement.writeoff.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;

/**
 * 应收冲应付 输入Dto
 * 
 * @author 095793-foss-LiQin
 * @date 2012-10-16 下午8:30:15
 */
public class BillRecWriteoffBillPayDto implements Serializable {

	/**
	 * 应收单序列号
	 */
	private static final long serialVersionUID = -5081644084166686248L;

	/**
	 * 应收单
	 */
	private BillReceivableEntity billReceivableEntity;

	/**
	 * 应付单
	 */
	private BillPayableEntity billPayableEntity;
	
	/**
	 * 应付单冻结状态，仅用于代收货款反核销后重新核销
	 */
	private String billFrozenStaFrozen;

	/**
	 * 代收货款退款状态 已退款
	 */

	private String codStatusRD;
	
	/**
	 * 应收单列表
	 */
	private List<BillReceivableEntity> billReceivableEntityList = new ArrayList<BillReceivableEntity>();

	/**
	 * 应付单列表
	 */
	private List<BillPayableEntity> billPayableEntityList = new ArrayList<BillPayableEntity>();

	/**
	 *应收单开始日期
	 */
	private Date recBusinessStartDate;
	
	/**
	 * 应收单结束日期
	 */
	private Date recBusinessEndDate;
	
	/**
	 * 应付单开始日期
	 */
	private Date payBusinessStartDate;
	
	/**
	 * 应付单结束日期
	 */
	private Date payBusinessEndDate;
	
	/**
	 * 页面最大显示条数
	 */
	private int maxShowNum;

	/**
	 * 应收单客户编码
	 */
	private String customerCode;

	/**
	 * 客户类型
	 */
	private String customerType;

	/**
	 * 登录用户所属部门
	 */
	private String generatingOrgCode;

	/**
	 * 登录用户所属部门名称
	 */

	private String generatingOrgName;

	/**
	 * 应收单号集
	 */
	private List<String> receivableNos;

	/**
	 * 应付单号集
	 */
	private List<String> payableNos;

	/**
	 * 查询应收单集合
	 */
	private String receivableNosOrWaybillNos;

	/**
	 * 查询应付单集合
	 */
	private String payNosOrWaybillNos;

	/**
	 * 来源单号集合
	 */
	private List<String> sourceBillNosList;

	/**
	 * 是否有效
	 */
	private String active;

	/**
	 * 应付单是否有效
	 */
	private String payActive;

	/**
	 * 应收单是否有效
	 */
	private String recActive;

	/**
	 * 是否非红单
	 */
	private String isRedBack;

	/**
	 * 应收单是否已生成对账单
	 */
	private String isGrenerateStatement;

	/**
	 * 应收单是否非红单
	 */
	private String recIsRedBack;

	/**
	 * 应付单是否非红单
	 */
	private String payIsRedBack;

	/**
	 * 应收单退款状态
	 */
	private String recRefundStatus;

	/**
	 * 应付单退款状态
	 */
	private String payRefundStatus;

	/**
	 * 应收单状态
	 */
	private List<String> recStatusList;

	/**
	 * 应付单付款状态
	 */
	private List<String> payStatusList;
	
	/**
	 * 应付单支付状态已付款
	 */
	private String  payStatusYes;
	
	/**
	 * 应付单支付状态未付款
	 */
	private String payStatusNo;
	/**
	 * 应付单登录部门
	 */

	private String receivableOrgCode;

	/**
	 * 应付单登录部门
	 */

	private String payableOrgCode;

	/**
	 * 应收单单据子类型
	 */
	private List<String> billRecTypeList;

	/**
	 * 应付单单据子类型
	 */
	private List<String> billPayTypeList;

	/**
	 * 支付状态
	 */
	private List<String> statusList;

	/**
	 * 单据类型
	 */
	private List<String> billTypeList;
	
	
	/**
	 * 应付单单据类型
	 */
	private List<String>payableBillTypeList;
	
	
	/**
	 * 应收单单据类型
	 */
	private List<String>recBillTypeList;

	/**
	 * 应收单确定收入日期
	 */
	private Date recConrevenDate;

	/**
	 * 登录用户编码
	 */
	private String usercode;

	/**
	 * 登录用户名称
	 */
	private String username;

	/**
	 * 版本号
	 */
	private Short versionNo;

	/**
	 * 审核状态
	 */
	private String approveStatus;
	
	
	/**
	 * 查询类型
	 */
	private String queryType;
	
	
	/**
	 * 付款单的汇款状态
	 */
	private String remitStatus;
	
	/**
	 * 导出自定义导出列头
	 */
	private String [] arrayColumns;
	
	
	/**
	 * 导出自定义导出列中文名称
	 */
	private String[] arrayColumnNames;
	
	
	/**
	 * 应付单生效状态
	 */
	private String effectiveStatus;
	
	
	/**
	 * 理赔应付单类型
	 */
	private String claimsPaybleType;
	
	
	/**
	 * 应付单支付类别
	 */
	private List<String>paymentTypeList;
	

	/**
	 * 代收货款类型
	 */
	private List<String> codTypeList; 
	
	
	/**
	 * 应收单解锁时间小于当前时间
	 */
	private Date nowTime;
	
	
	
	/**
	 * 应付单冻结状态：未冻结
	 */
	private String noFrozenStatus;
	
	
	
	/**
	 * 付款金额
	 */
	private BigDecimal paymentAmount;// PAYMENT_AMOUNT



	
	/**
	 * @return 
		billReceivableEntity
	 */
	public BillReceivableEntity getBillReceivableEntity() {
		return billReceivableEntity;
	}



	
	/**
	 * @param 
		billReceivableEntity
	 */
	public void setBillReceivableEntity(BillReceivableEntity billReceivableEntity) {
		this.billReceivableEntity = billReceivableEntity;
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
		billReceivableEntityList
	 */
	public List<BillReceivableEntity> getBillReceivableEntityList() {
		return billReceivableEntityList;
	}



	
	/**
	 * @param 
		billReceivableEntityList
	 */
	public void setBillReceivableEntityList(List<BillReceivableEntity> billReceivableEntityList) {
		this.billReceivableEntityList = billReceivableEntityList;
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
		maxShowNum
	 */
	public int getMaxShowNum() {
		return maxShowNum;
	}



	
	/**
	 * @param 
		maxShowNum
	 */
	public void setMaxShowNum(int maxShowNum) {
		this.maxShowNum = maxShowNum;
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
		customerType
	 */
	public String getCustomerType() {
		return customerType;
	}



	
	/**
	 * @param 
		customerType
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}



	
	/**
	 * @return 
		generatingOrgCode
	 */
	public String getGeneratingOrgCode() {
		return generatingOrgCode;
	}



	
	/**
	 * @param 
		generatingOrgCode
	 */
	public void setGeneratingOrgCode(String generatingOrgCode) {
		this.generatingOrgCode = generatingOrgCode;
	}



	
	/**
	 * @return 
		generatingOrgName
	 */
	public String getGeneratingOrgName() {
		return generatingOrgName;
	}



	
	/**
	 * @param 
		generatingOrgName
	 */
	public void setGeneratingOrgName(String generatingOrgName) {
		this.generatingOrgName = generatingOrgName;
	}



	
	/**
	 * @return 
		receivableNos
	 */
	public List<String> getReceivableNos() {
		return receivableNos;
	}



	
	/**
	 * @param 
		receivableNos
	 */
	public void setReceivableNos(List<String> receivableNos) {
		this.receivableNos = receivableNos;
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
		receivableNosOrWaybillNos
	 */
	public String getReceivableNosOrWaybillNos() {
		return receivableNosOrWaybillNos;
	}



	
	/**
	 * @param 
		receivableNosOrWaybillNos
	 */
	public void setReceivableNosOrWaybillNos(String receivableNosOrWaybillNos) {
		this.receivableNosOrWaybillNos = receivableNosOrWaybillNos;
	}



	
	/**
	 * @return 
		payNosOrWaybillNos
	 */
	public String getPayNosOrWaybillNos() {
		return payNosOrWaybillNos;
	}



	
	/**
	 * @param 
		payNosOrWaybillNos
	 */
	public void setPayNosOrWaybillNos(String payNosOrWaybillNos) {
		this.payNosOrWaybillNos = payNosOrWaybillNos;
	}



	
	/**
	 * @return 
		sourceBillNosList
	 */
	public List<String> getSourceBillNosList() {
		return sourceBillNosList;
	}



	
	/**
	 * @param 
		sourceBillNosList
	 */
	public void setSourceBillNosList(List<String> sourceBillNosList) {
		this.sourceBillNosList = sourceBillNosList;
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
		payActive
	 */
	public String getPayActive() {
		return payActive;
	}



	
	/**
	 * @param 
		payActive
	 */
	public void setPayActive(String payActive) {
		this.payActive = payActive;
	}



	
	/**
	 * @return 
		recActive
	 */
	public String getRecActive() {
		return recActive;
	}



	
	/**
	 * @param 
		recActive
	 */
	public void setRecActive(String recActive) {
		this.recActive = recActive;
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
		isGrenerateStatement
	 */
	public String getIsGrenerateStatement() {
		return isGrenerateStatement;
	}



	
	/**
	 * @param 
		isGrenerateStatement
	 */
	public void setIsGrenerateStatement(String isGrenerateStatement) {
		this.isGrenerateStatement = isGrenerateStatement;
	}



	
	/**
	 * @return 
		recIsRedBack
	 */
	public String getRecIsRedBack() {
		return recIsRedBack;
	}



	
	/**
	 * @param 
		recIsRedBack
	 */
	public void setRecIsRedBack(String recIsRedBack) {
		this.recIsRedBack = recIsRedBack;
	}



	
	/**
	 * @return 
		payIsRedBack
	 */
	public String getPayIsRedBack() {
		return payIsRedBack;
	}



	
	/**
	 * @param 
		payIsRedBack
	 */
	public void setPayIsRedBack(String payIsRedBack) {
		this.payIsRedBack = payIsRedBack;
	}



	
	/**
	 * @return 
		recRefundStatus
	 */
	public String getRecRefundStatus() {
		return recRefundStatus;
	}



	
	/**
	 * @param 
		recRefundStatus
	 */
	public void setRecRefundStatus(String recRefundStatus) {
		this.recRefundStatus = recRefundStatus;
	}



	
	/**
	 * @return 
		payRefundStatus
	 */
	public String getPayRefundStatus() {
		return payRefundStatus;
	}



	
	/**
	 * @param 
		payRefundStatus
	 */
	public void setPayRefundStatus(String payRefundStatus) {
		this.payRefundStatus = payRefundStatus;
	}



	
	/**
	 * @return 
		recStatusList
	 */
	public List<String> getRecStatusList() {
		return recStatusList;
	}



	
	/**
	 * @param 
		recStatusList
	 */
	public void setRecStatusList(List<String> recStatusList) {
		this.recStatusList = recStatusList;
	}



	
	/**
	 * @return 
		payStatusList
	 */
	public List<String> getPayStatusList() {
		return payStatusList;
	}



	
	/**
	 * @param 
		payStatusList
	 */
	public void setPayStatusList(List<String> payStatusList) {
		this.payStatusList = payStatusList;
	}



	
	/**
	 * @return 
		payStatusYes
	 */
	public String getPayStatusYes() {
		return payStatusYes;
	}



	
	/**
	 * @param 
		payStatusYes
	 */
	public void setPayStatusYes(String payStatusYes) {
		this.payStatusYes = payStatusYes;
	}



	
	/**
	 * @return 
		payStatusNo
	 */
	public String getPayStatusNo() {
		return payStatusNo;
	}



	
	/**
	 * @param 
		payStatusNo
	 */
	public void setPayStatusNo(String payStatusNo) {
		this.payStatusNo = payStatusNo;
	}



	
	/**
	 * @return 
		receivableOrgCode
	 */
	public String getReceivableOrgCode() {
		return receivableOrgCode;
	}



	
	/**
	 * @param 
		receivableOrgCode
	 */
	public void setReceivableOrgCode(String receivableOrgCode) {
		this.receivableOrgCode = receivableOrgCode;
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
		billRecTypeList
	 */
	public List<String> getBillRecTypeList() {
		return billRecTypeList;
	}



	
	/**
	 * @param 
		billRecTypeList
	 */
	public void setBillRecTypeList(List<String> billRecTypeList) {
		this.billRecTypeList = billRecTypeList;
	}



	
	/**
	 * @return 
		billPayTypeList
	 */
	public List<String> getBillPayTypeList() {
		return billPayTypeList;
	}



	
	/**
	 * @param 
		billPayTypeList
	 */
	public void setBillPayTypeList(List<String> billPayTypeList) {
		this.billPayTypeList = billPayTypeList;
	}



	
	/**
	 * @return 
		statusList
	 */
	public List<String> getStatusList() {
		return statusList;
	}



	
	/**
	 * @param 
		statusList
	 */
	public void setStatusList(List<String> statusList) {
		this.statusList = statusList;
	}



	
	/**
	 * @return 
		billTypeList
	 */
	public List<String> getBillTypeList() {
		return billTypeList;
	}



	
	/**
	 * @param 
		billTypeList
	 */
	public void setBillTypeList(List<String> billTypeList) {
		this.billTypeList = billTypeList;
	}



	
	/**
	 * @return 
		payableBillTypeList
	 */
	public List<String> getPayableBillTypeList() {
		return payableBillTypeList;
	}



	
	/**
	 * @param 
		payableBillTypeList
	 */
	public void setPayableBillTypeList(List<String> payableBillTypeList) {
		this.payableBillTypeList = payableBillTypeList;
	}



	
	/**
	 * @return 
		recBillTypeList
	 */
	public List<String> getRecBillTypeList() {
		return recBillTypeList;
	}



	
	/**
	 * @param 
		recBillTypeList
	 */
	public void setRecBillTypeList(List<String> recBillTypeList) {
		this.recBillTypeList = recBillTypeList;
	}



	
	/**
	 * @return 
		recConrevenDate
	 */
	public Date getRecConrevenDate() {
		return recConrevenDate;
	}



	
	/**
	 * @param 
		recConrevenDate
	 */
	public void setRecConrevenDate(Date recConrevenDate) {
		this.recConrevenDate = recConrevenDate;
	}



	
	/**
	 * @return 
		usercode
	 */
	public String getUsercode() {
		return usercode;
	}



	
	/**
	 * @param 
		usercode
	 */
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}



	
	/**
	 * @return 
		username
	 */
	public String getUsername() {
		return username;
	}



	
	/**
	 * @param 
		username
	 */
	public void setUsername(String username) {
		this.username = username;
	}



	
	/**
	 * @return 
		versionNo
	 */
	public Short getVersionNo() {
		return versionNo;
	}



	
	/**
	 * @param 
		versionNo
	 */
	public void setVersionNo(Short versionNo) {
		this.versionNo = versionNo;
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
		queryType
	 */
	public String getQueryType() {
		return queryType;
	}



	
	/**
	 * @param 
		queryType
	 */
	public void setQueryType(String queryType) {
		this.queryType = queryType;
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
		arrayColumns
	 */
	public String[] getArrayColumns() {
		return arrayColumns;
	}



	
	/**
	 * @param 
		arrayColumns
	 */
	public void setArrayColumns(String[] arrayColumns) {
		this.arrayColumns = arrayColumns;
	}



	
	/**
	 * @return 
		arrayColumnNames
	 */
	public String[] getArrayColumnNames() {
		return arrayColumnNames;
	}



	
	/**
	 * @param 
		arrayColumnNames
	 */
	public void setArrayColumnNames(String[] arrayColumnNames) {
		this.arrayColumnNames = arrayColumnNames;
	}



	
	/**
	 * @return 
		effectiveStatus
	 */
	public String getEffectiveStatus() {
		return effectiveStatus;
	}



	
	/**
	 * @param 
		effectiveStatus
	 */
	public void setEffectiveStatus(String effectiveStatus) {
		this.effectiveStatus = effectiveStatus;
	}



	
	/**
	 * @return 
		claimsPaybleType
	 */
	public String getClaimsPaybleType() {
		return claimsPaybleType;
	}



	
	/**
	 * @param 
		claimsPaybleType
	 */
	public void setClaimsPaybleType(String claimsPaybleType) {
		this.claimsPaybleType = claimsPaybleType;
	}



	
	/**
	 * @return 
		paymentTypeList
	 */
	public List<String> getPaymentTypeList() {
		return paymentTypeList;
	}



	
	/**
	 * @param 
		paymentTypeList
	 */
	public void setPaymentTypeList(List<String> paymentTypeList) {
		this.paymentTypeList = paymentTypeList;
	}



	
	/**
	 * @return 
		codTypeList
	 */
	public List<String> getCodTypeList() {
		return codTypeList;
	}



	
	/**
	 * @param 
		codTypeList
	 */
	public void setCodTypeList(List<String> codTypeList) {
		this.codTypeList = codTypeList;
	}



	
	/**
	 * @return 
		nowTime
	 */
	public Date getNowTime() {
		return nowTime;
	}



	
	/**
	 * @param 
		nowTime
	 */
	public void setNowTime(Date nowTime) {
		this.nowTime = nowTime;
	}



	
	/**
	 * @return 
		noFrozenStatus
	 */
	public String getNoFrozenStatus() {
		return noFrozenStatus;
	}



	
	/**
	 * @param 
		noFrozenStatus
	 */
	public void setNoFrozenStatus(String noFrozenStatus) {
		this.noFrozenStatus = noFrozenStatus;
	}



	
	/**
	 * @return 
		paymentAmount
	 */
	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}



	
	/**
	 * @param 
		paymentAmount
	 */
	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}




	/**
	 * @return recBusinessStartDate
	 */
	public Date getRecBusinessStartDate() {
		return recBusinessStartDate;
	}




	/**
	 * @param recBusinessStartDate
	 */
	public void setRecBusinessStartDate(Date recBusinessStartDate) {
		this.recBusinessStartDate = recBusinessStartDate;
	}




	/**
	 * @return recBusinessEndDate
	 */
	public Date getRecBusinessEndDate() {
		return recBusinessEndDate;
	}




	/**
	 * @param recBusinessEndDate
	 */
	public void setRecBusinessEndDate(Date recBusinessEndDate) {
		this.recBusinessEndDate = recBusinessEndDate;
	}




	/**
	 * @return payBusinessStartDate
	 */
	public Date getPayBusinessStartDate() {
		return payBusinessStartDate;
	}




	/**
	 * @param payBusinessStartDate
	 */
	public void setPayBusinessStartDate(Date payBusinessStartDate) {
		this.payBusinessStartDate = payBusinessStartDate;
	}




	/**
	 * @return payBusinessEndDate
	 */
	public Date getPayBusinessEndDate() {
		return payBusinessEndDate;
	}




	/**
	 * @param payBusinessEndDate
	 */
	public void setPayBusinessEndDate(Date payBusinessEndDate) {
		this.payBusinessEndDate = payBusinessEndDate;
	}


	/**
	 * @return
	 */
	public String getCodStatusRD() {
		return codStatusRD;
	}




	/**
	 * @param codStatusRD
	 */
	public void setCodStatusRD(String codStatusRD) {
		this.codStatusRD = codStatusRD;
	}

	/**
	 * @return
	 */
	public String getBillFrozenStaFrozen() {
		return billFrozenStaFrozen;
	}


	/**
	 * @param billFrozenStaFrozen
	 */
	public void setBillFrozenStaFrozen(String billFrozenStaFrozen) {
		this.billFrozenStaFrozen = billFrozenStaFrozen;
	}
}
