package com.deppon.foss.module.settlement.pay.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 录入付款单dto
 * 
 * @author 045738-foss-maojianqiang
 * @date 2012-11-26 下午6:47:48
 */
public class BillPaymentAddDto implements Serializable {

	/**
	 * 序列化编号
	 */
	private static final long serialVersionUID = 2308145719988131785L;

	/**
	 * id
	 */
	private String id;

	/**
	 * 应付单号
	 */
	private String payableNo;

	/**
	 * 单据子类型
	 */
	private String billType;

	/**
	 * 总金额
	 */
	private BigDecimal amount;

	/**
	 * 已核销金额
	 */
	private BigDecimal verifyAmount;

	/**
	 * 未核销金额
	 */
	private BigDecimal unverifyAmount;

	/**
	 * 本次付款金额
	 */
	private BigDecimal currentPaymentAmount;

	/**
	 * 备注
	 */
	private String notes;

	/**
	 * 版本号
	 */
	private Short versionNo;

	/**
	 * 记账日期
	 */
	private Date accountDate;

	/**
	 * 业务日期
	 */
	private Date businessDate;

	/**
	 * 付款单号
	 */
	private String paymentNo;

	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 来源单据编号
	 */
	private String sourceBillNo;
	
	/**
	 * 税金
	 */
	private BigDecimal taxAmount;
	
	/**
	 * 税后金额
	 */
	private BigDecimal tax;
	
	/**
	 * 临时租车业务发生日期
	 */
	private Date businessOfDate;

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return payableNo
	 */
	public String getPayableNo() {
		return payableNo;
	}

	/**
	 * @param payableNo
	 */
	public void setPayableNo(String payableNo) {
		this.payableNo = payableNo;
	}

	/**
	 * @return billType
	 */
	public String getBillType() {
		return billType;
	}

	/**
	 * @param billType
	 */
	public void setBillType(String billType) {
		this.billType = billType;
	}

	/**
	 * @return amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * @return verifyAmount
	 */
	public BigDecimal getVerifyAmount() {
		return verifyAmount;
	}

	/**
	 * @param verifyAmount
	 */
	public void setVerifyAmount(BigDecimal verifyAmount) {
		this.verifyAmount = verifyAmount;
	}

	/**
	 * @return unverifyAmount
	 */
	public BigDecimal getUnverifyAmount() {
		return unverifyAmount;
	}

	/**
	 * @param unverifyAmount
	 */
	public void setUnverifyAmount(BigDecimal unverifyAmount) {
		this.unverifyAmount = unverifyAmount;
	}

	/**
	 * @return currentPaymentAmount
	 */
	public BigDecimal getCurrentPaymentAmount() {
		return currentPaymentAmount;
	}

	/**
	 * @param currentPaymentAmount
	 */
	public void setCurrentPaymentAmount(BigDecimal currentPaymentAmount) {
		this.currentPaymentAmount = currentPaymentAmount;
	}

	/**
	 * @return notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * @return versionNo
	 */
	public Short getVersionNo() {
		return versionNo;
	}

	/**
	 * @param versionNo
	 */
	public void setVersionNo(Short versionNo) {
		this.versionNo = versionNo;
	}

	/**
	 * @return accountDate
	 */
	public Date getAccountDate() {
		return accountDate;
	}

	/**
	 * @param accountDate
	 */
	public void setAccountDate(Date accountDate) {
		this.accountDate = accountDate;
	}

	/**
	 * @return businessDate
	 */
	public Date getBusinessDate() {
		return businessDate;
	}

	/**
	 * @param businessDate
	 */
	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}

	/**
	 * @return paymentNo
	 */
	public String getPaymentNo() {
		return paymentNo;
	}

	/**
	 * @param paymentNo
	 */
	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}

	/**
	 * @GET
	 * @return sourceBillNo
	 */
	public String getSourceBillNo() {
		/*
		 *@get
		 *@ return sourceBillNo
		 */
		return sourceBillNo;
	}

	/**
	 * @SET
	 * @param sourceBillNo
	 */
	public void setSourceBillNo(String sourceBillNo) {
		/*
		 *@set
		 *@this.sourceBillNo = sourceBillNo
		 */
		this.sourceBillNo = sourceBillNo;
	}

	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}

	public BigDecimal getTax() {
		return tax;
	}

	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}

	public Date getBusinessOfDate() {
		return businessOfDate;
	}

	public void setBusinessOfDate(Date businessOfDate) {
		this.businessOfDate = businessOfDate;
	}

}
