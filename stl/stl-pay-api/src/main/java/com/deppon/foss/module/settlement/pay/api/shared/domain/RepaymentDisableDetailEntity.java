package com.deppon.foss.module.settlement.pay.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 还款单作废明细
 *
 *
 * @author 092036-foss-bochenlong
 * @date 2013-11-19 下午5:04:20
 */
public class RepaymentDisableDetailEntity {
	/**ID*/
	private String id;
	/**还款单作废申请ID*/
	private String repaymentApplicationId;
	/**核销单号*/
	private String writeOffBillNo;
	/**还款单号*/
	private String repaymentNo;
	/**应收单号*/
	private String receviceNo;
	/**应收运单号*/
	private String receviceWaybillNo;
	/**对账单号*/
	private String statementNo;
	/**客户编码*/
	private String customerCode;
	/**客户名称*/
	private String customerName;
	/**金额*/
	private BigDecimal amount;
	/**核销类型*/
	private String writeoffType;
	/**核销时间*/
	private Date writeoffTime;
	/**核销部门编码*/
	private String orgCode;
	/**核销部门名称*/
	private String orgName;
	/**创建时间*/
	private Date createTime;
	/**修改时间*/
	private Date modifyTime;
	/**有效*/
	private String active;
	/**初始化*/
	private String isInit;
	/**
	 * @return the id
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
	 * @return the repaymentApplicationId
	 */
	public String getRepaymentApplicationId() {
		return repaymentApplicationId;
	}
	/**
	 * @param repaymentApplicationId the repaymentApplicationId to set
	 */
	public void setRepaymentApplicationId(String repaymentApplicationId) {
		this.repaymentApplicationId = repaymentApplicationId;
	}
	/**
	 * @return the writeOffBillNo
	 */
	public String getWriteOffBillNo() {
		return writeOffBillNo;
	}
	/**
	 * @param writeOffBillNo the writeOffBillNo to set
	 */
	public void setWriteOffBillNo(String writeOffBillNo) {
		this.writeOffBillNo = writeOffBillNo;
	}
	/**
	 * @return the repaymentNo
	 */
	public String getRepaymentNo() {
		return repaymentNo;
	}
	/**
	 * @param repaymentNo the repaymentNo to set
	 */
	public void setRepaymentNo(String repaymentNo) {
		this.repaymentNo = repaymentNo;
	}
	/**
	 * @return the receviceNo
	 */
	public String getReceviceNo() {
		return receviceNo;
	}
	/**
	 * @param receviceNo the receviceNo to set
	 */
	public void setReceviceNo(String receviceNo) {
		this.receviceNo = receviceNo;
	}
	/**
	 * @return the receviceWaybillNo
	 */
	public String getReceviceWaybillNo() {
		return receviceWaybillNo;
	}
	/**
	 * @param receviceWaybillNo the receviceWaybillNo to set
	 */
	public void setReceviceWaybillNo(String receviceWaybillNo) {
		this.receviceWaybillNo = receviceWaybillNo;
	}
	/**
	 * @return the statementNo
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
	 * @return the customerCode
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
	 * @return the customerName
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
	 * @return the amount
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
	 * @return the writeoffType
	 */
	public String getWriteoffType() {
		return writeoffType;
	}
	/**
	 * @param writeoffType the writeoffType to set
	 */
	public void setWriteoffType(String writeoffType) {
		this.writeoffType = writeoffType;
	}
	/**
	 * @return the writeoffTime
	 */
	public Date getWriteoffTime() {
		return writeoffTime;
	}
	/**
	 * @param writeoffTime the writeoffTime to set
	 */
	public void setWriteoffTime(Date writeoffTime) {
		this.writeoffTime = writeoffTime;
	}
	/**
	 * @return the orgCode
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
	 * @return the orgName
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
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * @return the modifyTime
	 */
	public Date getModifyTime() {
		return modifyTime;
	}
	/**
	 * @param modifyTime the modifyTime to set
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	/**
	 * @return the active
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
	 * @return the isInit
	 */
	public String getIsInit() {
		return isInit;
	}
	/**
	 * @param isInit the isInit to set
	 */
	public void setIsInit(String isInit) {
		this.isInit = isInit;
	}
}
