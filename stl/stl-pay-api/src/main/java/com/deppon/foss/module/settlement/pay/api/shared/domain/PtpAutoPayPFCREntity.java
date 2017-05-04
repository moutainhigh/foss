package com.deppon.foss.module.settlement.pay.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 合伙人开单到付运费自定提现付款实体
 * @author foss-Jiang Xun
 * @date 2016-05-20 下午05:03:00
 */
public class PtpAutoPayPFCREntity implements Serializable{

	/**
	 * 付款单Vo序列号
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 付款单号
	 */
	private String paymentNo;
	
	/**
	 * 应付单单据子类型
	 */
	private String billType;

	/**
	 * 应付单号集合
	 */
	private String payableNos;
	
	/**
	 * 应付单号集合
	 */
	private List<String> payableNoList;
	
	/**
	 * 应付部门编码
	 */
	private String payableOrgCode;
	
	/**
	 * 应付部门标杆编码
	 */
	private String payableUnifiedCode;
	
	/**
	 * 应付部门名称
	 */
	private String payableOrgName;
	
	/**
	 * 客户编码
	 */
	private String customerCode;
	
	/**
	 * 客户标杆编码
	 */
	private String customerUnifiedCode;
	
	/**
	 * 客户名称
	 */
	private String customerName;

	/**
	 * 付款方式
	 */
	private String paymentType;
	
	/**
	 * 付款金额
	 */
	private BigDecimal amount;
	
	/**
	 * 付款备注
	 */
	private String paymentNotes;
	
	
	public String getPaymentNo() {
		return paymentNo;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}

	public String getPayableNos() {
		return payableNos;
	}

	public void setPayableNos(String payableNos) {
		this.payableNos = payableNos;
	}

	public List<String> getPayableNoList() {
		return payableNoList;
	}

	public void setPayableNoList(List<String> payableNoList) {
		this.payableNoList = payableNoList;
	}

	public String getPayableOrgCode() {
		return payableOrgCode;
	}

	public void setPayableOrgCode(String payableOrgCode) {
		this.payableOrgCode = payableOrgCode;
	}

	public String getPayableOrgName() {
		return payableOrgName;
	}

	public void setPayableOrgName(String payableOrgName) {
		this.payableOrgName = payableOrgName;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getPaymentNotes() {
		return paymentNotes;
	}

	public void setPaymentNotes(String paymentNotes) {
		this.paymentNotes = paymentNotes;
	}

	public String getPayableUnifiedCode() {
		return payableUnifiedCode;
	}

	public void setPayableUnifiedCode(String payableUnifiedCode) {
		this.payableUnifiedCode = payableUnifiedCode;
	}

	public String getCustomerUnifiedCode() {
		return customerUnifiedCode;
	}

	public void setCustomerUnifiedCode(String customerUnifiedCode) {
		this.customerUnifiedCode = customerUnifiedCode;
	}

}
