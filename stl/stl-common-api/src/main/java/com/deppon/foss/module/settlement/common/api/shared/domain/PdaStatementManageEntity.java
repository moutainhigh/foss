package com.deppon.foss.module.settlement.common.api.shared.domain;

import java.math.BigDecimal;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * PDA对账单管理
 * 
 * @ClassName: PdaStatementManageEntity
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2016-1-18 下午4:20:32
 */
public class PdaStatementManageEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	/********FOSS数据回传到POS********/
	
	/**
	 * 对账单号
	 */
	private String statementNo;
	
	/**
	 * 客户名称
	 */
	private String customerName;
	
	/**
	 * 客户编码
	 */
	private String customeCode;
	
	/**
	 * 对账单金额
	 */
	private String amount;
	
	/**
	 * 未核销金额
	 */
	private BigDecimal unVerifyAmount;
	
	/**
	 * 未还金额
	 */
	private BigDecimal unpaidAmount;
	
	/**
	 * 版本号
	 */
	private String version;
	
	/**
	 * 数据来源系统
	 */
	private String systemSour;
	
	/******getter/setter*********/
	public String getStatementNo() {
		return statementNo;
	}

	public void setStatementNo(String statementNo) {
		this.statementNo = statementNo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomeCode() {
		return customeCode;
	}

	public void setCustomeCode(String customeCode) {
		this.customeCode = customeCode;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public BigDecimal getUnVerifyAmount() {
		return unVerifyAmount;
	}

	public void setUnVerifyAmount(BigDecimal unVerifyAmount) {
		this.unVerifyAmount = unVerifyAmount;
	}

	public BigDecimal getUnpaidAmount() {
		return unpaidAmount;
	}

	public void setUnpaidAmount(BigDecimal unpaidAmount) {
		this.unpaidAmount = unpaidAmount;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSystemSour() {
		return systemSour;
	}

	public void setSystemSour(String systemSour) {
		this.systemSour = systemSour;
	}

	@Override
	public String toString() {
		return "PdaStatementManageEntity [statementNo=" + statementNo
				+ ", customerName=" + customerName + ", customeCode="
				+ customeCode + ", amount=" + amount + ", unVerifyAmount="
				+ unVerifyAmount + ", unpaidAmount=" + unpaidAmount
				+ ", version=" + version + "]";
	}
	
	
}
