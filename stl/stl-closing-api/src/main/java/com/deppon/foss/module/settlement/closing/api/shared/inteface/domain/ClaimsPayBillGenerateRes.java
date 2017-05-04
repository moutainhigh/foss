package com.deppon.foss.module.settlement.closing.api.shared.inteface.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ClaimsPayBillGenerateRes {
	// 理赔类型(1-异常签收理赔，2-丢货理赔,3-未签收理赔)
	private String claimType;
	// 理赔方式(1-正常理赔，2-快速理赔，3-在线理赔)
	private String claimWay;
	// 业务类型(1-理赔，2-退运费，3-服务补救)
	private String businessType;
	// 部门标杆编码
	private String deptNo;
	// 客户编码
	private String custNo;
	// 客户联系电话
	private String customerPhone;
	// 理赔金额（单位：元，理赔金额，包括了索赔金额加上多陪金额）
	private BigDecimal claimMoney;
	// 运单号（不包含差错编号）
	private String billNo;
	// 创建人工号
	private String creatorNo;
	// 责任信息
	private List<ResponsibilityInfo> responsibilityInfos;
	// 银行支付信息
	private BankPayInfo bankPayInfo;
	// 最迟汇款日期
	private Date payBillLastTime;
	// 支付类别 现金:CH/电汇:TT/核销:W/核销后现金:WCH/核销后电汇:WTT
	private String paymentCategory;

	public String getClaimType() {
		return claimType;
	}

	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}

	public String getClaimWay() {
		return claimWay;
	}

	public void setClaimWay(String claimWay) {
		this.claimWay = claimWay;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getCustNo() {
		return custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public BigDecimal getClaimMoney() {
		return claimMoney;
	}

	public void setClaimMoney(BigDecimal claimMoney) {
		this.claimMoney = claimMoney;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getCreatorNo() {
		return creatorNo;
	}

	public void setCreatorNo(String creatorNo) {
		this.creatorNo = creatorNo;
	}

	public List<ResponsibilityInfo> getResponsibilityInfos() {
		return responsibilityInfos;
	}

	public void setResponsibilityInfos(
			List<ResponsibilityInfo> responsibilityInfos) {
		this.responsibilityInfos = responsibilityInfos;
	}

	public BankPayInfo getBankPayInfo() {
		return bankPayInfo;
	}

	public void setBankPayInfo(BankPayInfo bankPayInfo) {
		this.bankPayInfo = bankPayInfo;
	}

	public Date getPayBillLastTime() {
		return payBillLastTime;
	}

	public void setPayBillLastTime(Date payBillLastTime) {
		this.payBillLastTime = payBillLastTime;
	}

	public String getPaymentCategory() {
		return paymentCategory;
	}

	public void setPaymentCategory(String paymentCategory) {
		this.paymentCategory = paymentCategory;
	}

	public String getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

}
