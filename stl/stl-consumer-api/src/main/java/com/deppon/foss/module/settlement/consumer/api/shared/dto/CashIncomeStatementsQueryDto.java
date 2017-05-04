package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 现金收入明细报表查询传入参数：DTO
 * 
 * @author 099995-foss-wujiangtao
 * @date 2012-11-7 下午5:11:30
 * @since
 * @version
 */
public class CashIncomeStatementsQueryDto implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -7117821077214145816L;

	/**
	 * 日期类型
	 */
	private String dateType;

	/**
	 * 开始日期
	 */
	private Date startDate;

	/**
	 * 结束日期
	 */
	private Date endDate;

	/**
	 * 记账开始日期
	 */
	private Date accountStartDate;

	/**
	 * 记账结束日期
	 */
	private Date accountEndDate;

	/**
	 * 收银开始日期
	 */
	private Date cashConfirmStartDate;

	/**
	 * 收银结束日期
	 */
	private Date cashConfirmEndDate;

	/**
	 * 部门编码（作为收收款部门）
	 */
	private String orgCode;

	/**
	 * 大区编码
	 */
	private String largeAreaCode;

	/**
	 * 大区名称
	 */
	private String largeAreaName;

	/**
	 * 小区编码
	 */
	private String smallAreaCode;

	/**
	 * 小区名称
	 */
	private String smallAreaName;

	/**
	 * 单据类别
	 */
	private String billType;

	/**
	 * 单据状态
	 */
	private String status;

	/**
	 * 收入部门编码
	 */
	private String generatingOrgCode;
	
	/**
	 * 收入部门名称
	 */
	private String generatingOrgName;
	
	/**
	 * 收款部门编码
	 */
	private String collectionOrgCode;

	/**
	 * 收款部门名称
	 */
	private String collectionOrgName;
	
	/**
	 * 收款部门编码List
	 */
	private List<String> collectionOrgCodes;

	/**
	 * 收银确认人工号（收银员）
	 */
	private String cashConfirmUserCode;

	/**
	 * 收银确认人名称
	 */
	private String cashConfirmUserName;

	/**
	 * 还款单号集合
	 */
	private List<String> repaymentNos;

	/**
	 * 对账单号集合
	 */
	private List<String> statementBillNos;

	/**
	 * 预收单号集合
	 */
	private List<String> depositReceivedNos;

	/**
	 * 运单号集合
	 */
	private List<String> waybillNos;

	/**
	 * 是否有效
	 */
	private String active;

	/**
	 * 是否红单
	 */
	private String isRedBack;

	/**
	 * 现金收款单类型标识
	 */
	private String cashBillType;

	/**
	 * 还款单类型标识
	 */
	private String repaymentBillType;

	/**
	 * 预收单类型标识
	 */
	private String depositReceivedBillType;

	/**
	 * 付款方式集合包含（现金、银行卡、支票、电汇、网上支付）
	 */
	private List<String> paymentTypes;
	
	/**
	 * 支付方式
	 */
	private  String   paymentType;

	/**
	 * 是否需要分页，考虑到导出Excel需要调用同样的Service方法故加入这个参数
	 */
	private boolean isPaging;
	
	/**
	 * 当前操作者编码
	 */
	private String empCode;
	
	/**
	 * 大区小区中营业部的状态
	 */
	private String orgActive;

	/**
	 *  核销类型
	 */
	private List<String> writeoffType;
	
	/**
	 * @get
	 * @return writeoffType
	 */
	public List<String> getWriteoffType() {
		/*
		 * @get
		 * @return writeoffType
		 */
		return writeoffType;
	}

	
	/**
	 * @set
	 * @param writeoffType
	 */
	public void setWriteoffType(List<String> writeoffType) {
		/*
		 *@set
		 *@this.writeoffType = writeoffType
		 */
		this.writeoffType = writeoffType;
	}

	/**
	 * @return the dateType
	 */
	public String getDateType() {
		return dateType;
	}

	/**
	 * @param dateType
	 *            the dateType to set
	 */
	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the accountStartDate
	 */
	public Date getAccountStartDate() {
		return accountStartDate;
	}

	/**
	 * @param accountStartDate
	 *            the accountStartDate to set
	 */
	public void setAccountStartDate(Date accountStartDate) {
		this.accountStartDate = accountStartDate;
	}

	/**
	 * @return the accountEndDate
	 */
	public Date getAccountEndDate() {
		return accountEndDate;
	}

	/**
	 * @param accountEndDate
	 *            the accountEndDate to set
	 */
	public void setAccountEndDate(Date accountEndDate) {
		this.accountEndDate = accountEndDate;
	}

	/**
	 * @return the cashConfirmStartDate
	 */
	public Date getCashConfirmStartDate() {
		return cashConfirmStartDate;
	}

	/**
	 * @param cashConfirmStartDate
	 *            the cashConfirmStartDate to set
	 */
	public void setCashConfirmStartDate(Date cashConfirmStartDate) {
		this.cashConfirmStartDate = cashConfirmStartDate;
	}

	/**
	 * @return the cashConfirmEndDate
	 */
	public Date getCashConfirmEndDate() {
		return cashConfirmEndDate;
	}

	/**
	 * @param cashConfirmEndDate
	 *            the cashConfirmEndDate to set
	 */
	public void setCashConfirmEndDate(Date cashConfirmEndDate) {
		this.cashConfirmEndDate = cashConfirmEndDate;
	}

	/**
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * @param orgCode
	 *            the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * @return the largeAreaCode
	 */
	public String getLargeAreaCode() {
		return largeAreaCode;
	}

	/**
	 * @param largeAreaCode
	 *            the largeAreaCode to set
	 */
	public void setLargeAreaCode(String largeAreaCode) {
		this.largeAreaCode = largeAreaCode;
	}

	/**
	 * @return the largeAreaName
	 */
	public String getLargeAreaName() {
		return largeAreaName;
	}

	/**
	 * @param largeAreaName
	 *            the largeAreaName to set
	 */
	public void setLargeAreaName(String largeAreaName) {
		this.largeAreaName = largeAreaName;
	}

	/**
	 * @return the smallAreaCode
	 */
	public String getSmallAreaCode() {
		return smallAreaCode;
	}

	/**
	 * @param smallAreaCode
	 *            the smallAreaCode to set
	 */
	public void setSmallAreaCode(String smallAreaCode) {
		this.smallAreaCode = smallAreaCode;
	}

	/**
	 * @return the smallAreaName
	 */
	public String getSmallAreaName() {
		return smallAreaName;
	}

	/**
	 * @param smallAreaName
	 *            the smallAreaName to set
	 */
	public void setSmallAreaName(String smallAreaName) {
		this.smallAreaName = smallAreaName;
	}

	/**
	 * @return the billType
	 */
	public String getBillType() {
		return billType;
	}

	/**
	 * @param billType
	 *            the billType to set
	 */
	public void setBillType(String billType) {
		this.billType = billType;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the generatingOrgCode
	 */
	public String getGeneratingOrgCode() {
		return generatingOrgCode;
	}

	/**
	 * @param generatingOrgCode
	 *            the generatingOrgCode to set
	 */
	public void setGeneratingOrgCode(String generatingOrgCode) {
		this.generatingOrgCode = generatingOrgCode;
	}

	/**
	 * @return the generatingOrgName
	 */
	public String getGeneratingOrgName() {
		return generatingOrgName;
	}

	/**
	 * @param generatingOrgName
	 *            the generatingOrgName to set
	 */
	public void setGeneratingOrgName(String generatingOrgName) {
		this.generatingOrgName = generatingOrgName;
	}

	/**
	 * @return the collectionOrgCode
	 */
	public String getCollectionOrgCode() {
		return collectionOrgCode;
	}

	/**
	 * @param collectionOrgCode
	 *            the collectionOrgCode to set
	 */
	public void setCollectionOrgCode(String collectionOrgCode) {
		this.collectionOrgCode = collectionOrgCode;
	}

	/**
	 * @return the collectionOrgName
	 */
	public String getCollectionOrgName() {
		return collectionOrgName;
	}

	/**
	 * @param collectionOrgName
	 *            the collectionOrgName to set
	 */
	public void setCollectionOrgName(String collectionOrgName) {
		this.collectionOrgName = collectionOrgName;
	}

	/**
	 * @return the cashConfirmUserCode
	 */
	public String getCashConfirmUserCode() {
		return cashConfirmUserCode;
	}

	/**
	 * @param cashConfirmUserCode
	 *            the cashConfirmUserCode to set
	 */
	public void setCashConfirmUserCode(String cashConfirmUserCode) {
		this.cashConfirmUserCode = cashConfirmUserCode;
	}

	/**
	 * @return the cashConfirmUserName
	 */
	public String getCashConfirmUserName() {
		return cashConfirmUserName;
	}

	/**
	 * @param cashConfirmUserName
	 *            the cashConfirmUserName to set
	 */
	public void setCashConfirmUserName(String cashConfirmUserName) {
		this.cashConfirmUserName = cashConfirmUserName;
	}

	/**
	 * @return the repaymentNos
	 */
	public List<String> getRepaymentNos() {
		return repaymentNos;
	}

	/**
	 * @param repaymentNos
	 *            the repaymentNos to set
	 */
	public void setRepaymentNos(List<String> repaymentNos) {
		this.repaymentNos = repaymentNos;
	}

	/**
	 * @return the statementBillNos
	 */
	public List<String> getStatementBillNos() {
		return statementBillNos;
	}

	/**
	 * @param statementBillNos
	 *            the statementBillNos to set
	 */
	public void setStatementBillNos(List<String> statementBillNos) {
		this.statementBillNos = statementBillNos;
	}

	/**
	 * @return the depositReceivedNos
	 */
	public List<String> getDepositReceivedNos() {
		return depositReceivedNos;
	}

	/**
	 * @param depositReceivedNos
	 *            the depositReceivedNos to set
	 */
	public void setDepositReceivedNos(List<String> depositReceivedNos) {
		this.depositReceivedNos = depositReceivedNos;
	}

	/**
	 * @return the waybillNos
	 */
	public List<String> getWaybillNos() {
		return waybillNos;
	}

	/**
	 * @param waybillNos
	 *            the waybillNos to set
	 */
	public void setWaybillNos(List<String> waybillNos) {
		this.waybillNos = waybillNos;
	}

	/**
	 * @return the active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active
	 *            the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * @return the isRedBack
	 */
	public String getIsRedBack() {
		return isRedBack;
	}

	/**
	 * @param isRedBack
	 *            the isRedBack to set
	 */
	public void setIsRedBack(String isRedBack) {
		this.isRedBack = isRedBack;
	}

	/**
	 * @return the cashBillType
	 */
	public String getCashBillType() {
		return cashBillType;
	}

	/**
	 * @param cashBillType
	 *            the cashBillType to set
	 */
	public void setCashBillType(String cashBillType) {
		this.cashBillType = cashBillType;
	}

	/**
	 * @return the repaymentBillType
	 */
	public String getRepaymentBillType() {
		return repaymentBillType;
	}

	/**
	 * @param repaymentBillType
	 *            the repaymentBillType to set
	 */
	public void setRepaymentBillType(String repaymentBillType) {
		this.repaymentBillType = repaymentBillType;
	}

	/**
	 * @return the depositReceivedBillType
	 */
	public String getDepositReceivedBillType() {
		return depositReceivedBillType;
	}

	/**
	 * @param depositReceivedBillType
	 *            the depositReceivedBillType to set
	 */
	public void setDepositReceivedBillType(String depositReceivedBillType) {
		this.depositReceivedBillType = depositReceivedBillType;
	}

	/**
	 * @return the paymentTypes
	 */
	public List<String> getPaymentTypes() {
		return paymentTypes;
	}

	/**
	 * @param paymentTypes
	 *            the paymentTypes to set
	 */
	public void setPaymentTypes(List<String> paymentTypes) {
		this.paymentTypes = paymentTypes;
	}

	/**
	 * @return the isPaging
	 */
	public boolean isPaging() {
		return isPaging;
	}

	/**
	 * @param isPaging
	 *            the isPaging to set
	 */
	public void setPaging(boolean isPaging) {
		this.isPaging = isPaging;
	}

	
	/**
	 * @return  the collectionOrgCodes
	 */
	public List<String> getCollectionOrgCodes() {
		return collectionOrgCodes;
	}

	
	/**
	 * @param collectionOrgCodes the collectionOrgCodes to set
	 */
	public void setCollectionOrgCodes(List<String> collectionOrgCodes) {
		this.collectionOrgCodes = collectionOrgCodes;
	}

	
	/**
	 * @return  the empCode
	 */
	public String getEmpCode() {
		return empCode;
	}

	
	/**
	 * @param empCode the empCode to set
	 */
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	
	/**
	 * @return  the orgActive
	 */
	public String getOrgActive() {
		return orgActive;
	}

	
	/**
	 * @param orgActive the orgActive to set
	 */
	public void setOrgActive(String orgActive) {
		this.orgActive = orgActive;
	}


	public String getPaymentType() {
		return paymentType;
	}


	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	

}
