package com.deppon.pda.bdm.module.foss.delivery.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * 
 * @ClassName: AccountStatementResult 
 * @Description: TODO(查询对账单数据返回值) 
 * @author &268974  wangzhili
 * @date 2016-2-24 下午4:53:25 
 *
 */
public class AccountStatementResult implements Serializable{

	 
	/**  
	 * @author 268974  wangzhili
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 *   
	 * @since Ver 1.0   
	*/
	private static final long serialVersionUID = 1L;
	
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
	 * 归属系统
	 */
	private String affiliation;
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
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
	public String getAffiliation() {
		return affiliation;
	}
	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}
	
}
