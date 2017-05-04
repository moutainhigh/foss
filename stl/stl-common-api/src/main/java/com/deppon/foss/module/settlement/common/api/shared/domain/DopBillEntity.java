package com.deppon.foss.module.settlement.common.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Dop应付单应收单生成实体
 * 
 * @ClassName: DopPayRecEntity
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2015-11-30 下午3:41:29
 */
public class DopBillEntity implements Serializable {

	/**
	 * @since Ver 1.0
	 */
	private static final long serialVersionUID = 1L;
	
	/***DOP推送数据****/
	
	/**
	 * 运单号
	 */
	private String wayBillNo;

	/**
	 * 签收时间
	 */
	private Date signTime;

	/**
	 * 部门编码
	 */
	private String orgCode;

	/**
	 * 部门名称
	 */
	private String orgName;

	/**
	 * 客户编码
	 */
	private String customerCode;

	/**
	 * 客户名称
	 */
	private String customerName;

	/**
	 * 应收金额
	 */
	private BigDecimal recAmount;

	/**
	 * 应付金额
	 */
	private BigDecimal payAmount;
	
	/**
	 * 应付明细
	 */
	private String payDetail;
	
	/**
	 * 应收明细
	 */
	private String recDetail;
	
	/**********FOSS使用,不用传入***********/
	/**
	 * 所属子公司名称
	 */
	private String subCompanyName;
	
	/**
	 * 所属子公司编码
	 */
	private String subCompanyCode;
	
	/**
	 * 是否成功
	 */
	private String isSuccess;

	/**
	 * 错误信息
	 */
	private String errorMsg;
	
	/**
	 * 对账单号
	 */
	private String statementNo;
	
	/**
	 * 已核销金额
	 */
	private BigDecimal verifyAmount;
	
	
	public String getSubCompanyName() {
		return subCompanyName;
	}

	public void setSubCompanyName(String subCompanyName) {
		this.subCompanyName = subCompanyName;
	}

	public String getSubCompanyCode() {
		return subCompanyCode;
	}

	public void setSubCompanyCode(String subCompanyCode) {
		this.subCompanyCode = subCompanyCode;
	}

	public String getStatementNo() {
		return statementNo;
	}

	public void setStatementNo(String statementNo) {
		this.statementNo = statementNo;
	}

	public BigDecimal getVerifyAmount() {
		return verifyAmount;
	}

	public void setVerifyAmount(BigDecimal verifyAmount) {
		this.verifyAmount = verifyAmount;
	}

	public String getWayBillNo() {
		return wayBillNo;
	}

	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
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

	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public BigDecimal getRecAmount() {
		return recAmount;
	}

	public void setRecAmount(BigDecimal recAmount) {
		this.recAmount = recAmount;
	}

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public Date getSignTime() {
		return signTime;
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	public String getPayDetail() {
		return payDetail;
	}

	public void setPayDetail(String payDetail) {
		this.payDetail = payDetail;
	}

	public String getRecDetail() {
		return recDetail;
	}

	public void setRecDetail(String recDetail) {
		this.recDetail = recDetail;
	}

	@Override
	public String toString() {
		return "DopBillEntity [wayBillNo=" + wayBillNo + ", signTime="
				+ signTime + ", orgCode=" + orgCode + ", orgName=" + orgName
				+ ", customerCode=" + customerCode + ", customerName="
				+ customerName + ", recAmount=" + recAmount + ", payAmount="
				+ payAmount + ", payDetail=" + payDetail + ", recDetail="
				+ recDetail + ", subCompanyName=" + subCompanyName
				+ ", subCompanyCode=" + subCompanyCode + ", isSuccess="
				+ isSuccess + ", errorMsg=" + errorMsg + ", statementNo="
				+ statementNo + ", verifyAmount=" + verifyAmount + "]";
	}
	
}
