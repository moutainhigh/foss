package com.deppon.foss.module.settlement.writeoff.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * @ClassName: HomeStatementDEntity
 * @Description: (家装明细实体)
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2015-11-24 下午1:59:14
 */
public class HomeStatementDEntity extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 单号
	 */
	private String payableNo;
	
	/**
	 * 业务日期
	 */
	private Date businessDate;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 部门名称
	 */
	private String orgName;
	/**
	 * 部门编码
	 */
	private String orgCode;

	/**
	 * 所属子公司名称
	 */
	private String subCompanyName;

	/**
	 * 所属子公司编码
	 */
	private String subCompanyCode;
	
	/**
	 * 单据子类型
	 */
	private String billType;

	/**
	 * 家装供应商名称
	 */
	private String homeSupplyName;

	/**
	 * 家装供应商编码
	 */
	private String homeSupplyCode;
	/**
	 * 金额
	 */
	private BigDecimal amount;
	/**
	 * 已核销
	 */
	private BigDecimal verifyAmount;
	/**
	 * 未核销
	 */
	private BigDecimal unverifyAmount;
	
	
	/**
	 * 应收单明细
	 *//*
	private String receivableNotes;

	*//**
	 * 应付单明细
	 *//*
	private String payablenotes;*/
	/**
	 * 备注
	 */
	private String notes;
	
	public String getPayableNo() {
		return payableNo;
	}

	public void setPayableNo(String payableNo) {
		this.payableNo = payableNo;
	}

	public Date getBusinessDate() {
		return businessDate;
	}

	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

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

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getHomeSupplyName() {
		return homeSupplyName;
	}

	public void setHomeSupplyName(String homeSupplyName) {
		this.homeSupplyName = homeSupplyName;
	}

	public String getHomeSupplyCode() {
		return homeSupplyCode;
	}

	public void setHomeSupplyCode(String homeSupplyCode) {
		this.homeSupplyCode = homeSupplyCode;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getVerifyAmount() {
		return verifyAmount;
	}

	public void setVerifyAmount(BigDecimal verifyAmount) {
		this.verifyAmount = verifyAmount;
	}

	public BigDecimal getUnverifyAmount() {
		return unverifyAmount;
	}

	public void setUnverifyAmount(BigDecimal unverifyAmount) {
		this.unverifyAmount = unverifyAmount;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Override
	public String toString() {
		return "HomeStatementDEntity [payableNo=" + payableNo
				+ ", businessDate=" + businessDate + ", waybillNo=" + waybillNo
				+ ", orgName=" + orgName + ", orgCode=" + orgCode
				+ ", subCompanyName=" + subCompanyName + ", subCompanyCode="
				+ subCompanyCode + ", billType=" + billType
				+ ", homeSupplyName=" + homeSupplyName + ", homeSupplyCode="
				+ homeSupplyCode + ", amount=" + amount + ", verifyAmount="
				+ verifyAmount + ", unverifyAmount=" + unverifyAmount
				+ ", notes=" + notes + "]";
	}
	
}
