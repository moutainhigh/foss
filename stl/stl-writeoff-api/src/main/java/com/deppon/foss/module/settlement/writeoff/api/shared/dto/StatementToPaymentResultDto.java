package com.deppon.foss.module.settlement.writeoff.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountEntity;

/**
 * 按对账单还款界面返回结果
 * 
 * @author foss-qiaolifeng
 * @date 2012-11-26 下午1:52:21
 */
public class StatementToPaymentResultDto implements Serializable {

	/**
	 * 按对账单还款界面返回结果Dto序列号
	 */
	private static final long serialVersionUID = -6644783580994546925L;

	/**
	 * 客户名称
	 */
	private String customerName;

	/**
	 * 客户编码
	 */
	private String customerCode;

	/**
	 * 客户身份证号
	 */
	private String idCard;

	/**
	 * 客户联系电话
	 */
	private String phone;

	/**
	 * 客户联系地址
	 */
	private String address;

	/**
	 * 本期发生金额
	 */
	private BigDecimal currentAmount;

	/**
	 * 本期剩余未还金额
	 */
	private BigDecimal currentRemainAmount;

	/**
	 * 对账单号数组
	 */
	private String statementBillNos;

	/**
	 * 对账单版本号数组
	 */
	private String versionNos;

	/**
	 * 对账单实体集合
	 */
	private StatementOfAccountEntity statementOfAccount;

	
	/**
	 * @return 
		customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	
	/**
	 * @param 
		customerName
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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
		idCard
	 */
	public String getIdCard() {
		return idCard;
	}

	
	/**
	 * @param 
		idCard
	 */
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	
	/**
	 * @return 
		phone
	 */
	public String getPhone() {
		return phone;
	}

	
	/**
	 * @param 
		phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	
	/**
	 * @return 
		address
	 */
	public String getAddress() {
		return address;
	}

	
	/**
	 * @param 
		address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	
	/**
	 * @return 
		currentAmount
	 */
	public BigDecimal getCurrentAmount() {
		return currentAmount;
	}

	
	/**
	 * @param 
		currentAmount
	 */
	public void setCurrentAmount(BigDecimal currentAmount) {
		this.currentAmount = currentAmount;
	}

	
	/**
	 * @return 
		currentRemainAmount
	 */
	public BigDecimal getCurrentRemainAmount() {
		return currentRemainAmount;
	}

	
	/**
	 * @param 
		currentRemainAmount
	 */
	public void setCurrentRemainAmount(BigDecimal currentRemainAmount) {
		this.currentRemainAmount = currentRemainAmount;
	}

	
	/**
	 * @return 
		statementBillNos
	 */
	public String getStatementBillNos() {
		return statementBillNos;
	}

	
	/**
	 * @param 
		statementBillNos
	 */
	public void setStatementBillNos(String statementBillNos) {
		this.statementBillNos = statementBillNos;
	}

	
	/**
	 * @return 
		versionNos
	 */
	public String getVersionNos() {
		return versionNos;
	}

	
	/**
	 * @param 
		versionNos
	 */
	public void setVersionNos(String versionNos) {
		this.versionNos = versionNos;
	}

	
	/**
	 * @return 
		statementOfAccount
	 */
	public StatementOfAccountEntity getStatementOfAccount() {
		return statementOfAccount;
	}

	
	/**
	 * @param 
		statementOfAccount
	 */
	public void setStatementOfAccount(StatementOfAccountEntity statementOfAccount) {
		this.statementOfAccount = statementOfAccount;
	}

	
	


}
