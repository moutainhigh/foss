package com.deppon.esb.inteface.domain.fins;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class FossToFinsRemitCommonRequest implements Serializable {
	
	private static final long serialVersionUID = 7834485399370567695L;
	/**
	 * 编号
	 */
	private String billNum;
	/**
	 * 汇款时间
	 */
	private Date remitDate;
	/**
	 * 汇款部门
	 */
	private String remitDept;
	/**
	 * 款项所属部门
	 */
	private String fundDept;
	/**
	 * 汇款金额
	 */
	private BigDecimal  remitTance;
	/**
	 * 汇入账号
	 */
	private String remitAccount;
	/**
	 * 账号类型
	 */
	private String accountType;
	/**
	 * 已使用金额（foss核销金额）
	 */
	private BigDecimal alreadyamount; 
	/**
	 * 汇款人
	 */
	private String remitPerson;
	
	 /**
     * 款项类别
     */
    private String fundType;
	
	/*******************get/set************************/
	public String getBillNum() {
		return billNum;
	}
	public void setBillNum(String billNum) {
		this.billNum = billNum;
	}
	public Date getRemitDate() {
		return remitDate;
	}
	public void setRemitDate(Date remitDate) {
		this.remitDate = remitDate;
	}
	public String getRemitDept() {
		return remitDept;
	}
	public void setRemitDept(String remitDept) {
		this.remitDept = remitDept;
	}
	public String getFundDept() {
		return fundDept;
	}
	public void setFundDept(String fundDept) {
		this.fundDept = fundDept;
	}
	public BigDecimal getRemitTance() {
		return remitTance;
	}
	public void setRemitTance(BigDecimal remitTance) {
		this.remitTance = remitTance;
	}
	public String getRemitAccount() {
		return remitAccount;
	}
	public void setRemitAccount(String remitAccount) {
		this.remitAccount = remitAccount;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public BigDecimal getAlreadyamount() {
		return alreadyamount;
	}
	public void setAlreadyamount(BigDecimal alreadyamount) {
		this.alreadyamount = alreadyamount;
	}
	public String getRemitPerson() {
		return remitPerson;
	}
	public void setRemitPerson(String remitPerson) {
		this.remitPerson = remitPerson;
	}
    public String getFundType() {
		return fundType;
	}
	public void setFundType(String fundType) {
		this.fundType = fundType;
	}
	@Override
	public String toString() {
		return "RequestInfo [billNum=" + billNum + ", remitDate=" + remitDate
				+ ", remitDept=" + remitDept + ", fundDept=" + fundDept
				+ ", remitTance=" + remitTance + ", remitAccount="
				+ remitAccount + ", accountType=" + accountType + ", alreadyamount=" + alreadyamount + "]";
	}

	

}
