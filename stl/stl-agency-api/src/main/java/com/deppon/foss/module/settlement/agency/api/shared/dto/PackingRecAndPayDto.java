package com.deppon.foss.module.settlement.agency.api.shared.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 包装其他应收应付查询 dto <p style="display:none">modifyRecord</p> <p style="display:none">version:V1.0,author:105762,date:2014-5-16 下午3:09:10,content:TODO</p>
 * 
 * @author 105762
 * @date 2014-5-16 下午3:09:10
 * @since 1.6
 * @version 1.0
 */
public class PackingRecAndPayDto {
	/**
	 * 应收/应付单编号
	 */
	private String billNo;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 产生年月
	 */
	private String billTime;
	/**
	 * 收款类别/应付类型
	 */
	private String collectionOrPayableType;
	/**
	 * 应收/应付部门编码
	 */
	private String orgCode;
	/**
	 * 应收/应付部门名称
	 */
	private String orgName;
	/**
	 * 包装供应商编码
	 */
	private String customerCode;
	/**
	 * 包装供应商
	 */
	private String customerName;
	/**
	 * 对账单号
	 */
	private String statementNo;
	/**
	 * 审核状态
	 */
	private String auditStatus;
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
	 * 业务日期
	 */
	private Date businessDate;
	/**
	 * 记账日期
	 */
	private Date accountDate;
	/**
	 * 是否有效
	 */
	private String active;
	/**
	 * 是否红单
	 */
	private String isRedBack;
	/**
	 * 备注
	 */
	private String notes;
	/**
	 * ID
	 */
	private String id;

	/**
	  * @return  the billNo
	 */
	public String getBillNo() {
		return billNo;
	}

	/**
	 * @param billNo the billNo to set
	 */
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	/**
	  * @return  the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	  * @return  the billTime
	 */
	public String getBillTime() {
		return billTime;
	}

	/**
	 * @param billTime the billTime to set
	 */
	public void setBillTime(String billTime) {
		this.billTime = billTime;
	}

	/**
	  * @return  the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * @param orgCode the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	  * @return  the orgName
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * @param orgName the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
	  * @return  the customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * @param customerCode the customerCode to set
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	  * @return  the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	  * @return  the statementNo
	 */
	public String getStatementNo() {
		return statementNo;
	}

	/**
	 * @param statementNo the statementNo to set
	 */
	public void setStatementNo(String statementNo) {
		this.statementNo = statementNo;
	}

	/**
	  * @return  the auditStatus
	 */
	public String getAuditStatus() {
		return auditStatus;
	}

	/**
	 * @param auditStatus the auditStatus to set
	 */
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	/**
	  * @return  the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	  * @return  the verifyAmount
	 */
	public BigDecimal getVerifyAmount() {
		return verifyAmount;
	}

	/**
	 * @param verifyAmount the verifyAmount to set
	 */
	public void setVerifyAmount(BigDecimal verifyAmount) {
		this.verifyAmount = verifyAmount;
	}

	/**
	  * @return  the unverifyAmount
	 */
	public BigDecimal getUnverifyAmount() {
		return unverifyAmount;
	}

	/**
	 * @param unverifyAmount the unverifyAmount to set
	 */
	public void setUnverifyAmount(BigDecimal unverifyAmount) {
		this.unverifyAmount = unverifyAmount;
	}

	/**
	  * @return  the businessDate
	 */
	public Date getBusinessDate() {
		return businessDate;
	}

	/**
	 * @param businessDate the businessDate to set
	 */
	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}

	/**
	  * @return  the accountDate
	 */
	public Date getAccountDate() {
		return accountDate;
	}

	/**
	 * @param accountDate the accountDate to set
	 */
	public void setAccountDate(Date accountDate) {
		this.accountDate = accountDate;
	}

	/**
	  * @return  the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	  * @return  the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	  * @return  the active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	  * @return  the isRedBack
	 */
	public String getIsRedBack() {
		return isRedBack;
	}

	/**
	 * @param isRedBack the isRedBack to set
	 */
	public void setIsRedBack(String isRedBack) {
		this.isRedBack = isRedBack;
	}

	/**
	  * @return  the collectionOrPayableType
	 */
	public String getCollectionOrPayableType() {
		return collectionOrPayableType;
	}

	/**
	 * @param collectionOrPayableType the collectionOrPayableType to set
	 */
	public void setCollectionOrPayableType(String collectionOrPayableType) {
		this.collectionOrPayableType = collectionOrPayableType;
	}

}
