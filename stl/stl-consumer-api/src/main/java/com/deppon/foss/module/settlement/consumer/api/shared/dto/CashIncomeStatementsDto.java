package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 现金收入报表明细Dto
 * 
 * @author 099995-foss-wujiangtao
 * @date 2012-11-7 下午3:14:05
 * @since
 * @version
 */
public class CashIncomeStatementsDto implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3754080310832221185L;

	/**
	 * ID
	 */
	private String id;

	/**
	 * 单据编号
	 */
	private String billNo;

	/**
	 * 记账日期
	 */
	private Date accountDate;
	
	/**
	 * 运输性质
	 */
	private String productCode;

	/**
	 * 收款部门编码
	 */
	private String collectionOrgCode;

	/**
	 * 收款部门名称
	 */
	private String collectionOrgName;

	/**
	 * 收入部门编码
	 */
	private String generatingOrgCode;

	/**
	 * 收入部门名称
	 */
	private String generatingOrgName;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 单据类别
	 */
	private String billType;

	/**
	 * 单据状态
	 */
	private String status;

	/**
	 * 付款方式
	 */
	private String paymentType;

	/**
	 * 金额
	 */
	private BigDecimal amount;

	/**
	 * 收银确认人工号（收银员）
	 */
	private String cashConfirmUserCode;

	/**
	 * 收银确认人名称
	 */
	private String cashConfirmUserName;

	/**
	 * 收银确认时间
	 */
	private Date cashConfirmTime;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the billNo
	 */
	public String getBillNo() {
		return billNo;
	}

	/**
	 * @param billNo
	 *            the billNo to set
	 */
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	/**
	 * @return the accountDate
	 */
	public Date getAccountDate() {
		return accountDate;
	}

	/**
	 * @param accountDate
	 *            the accountDate to set
	 */
	public void setAccountDate(Date accountDate) {
		this.accountDate = accountDate;
	}

	/**
	 * @return the collectionOrgCode
	 */
	public String getCollectionOrgCode() {
		return collectionOrgCode;
	}

	/**
	 * @param collectionOrgCode
	 *            the collectionOrgCode to set
	 */
	public void setCollectionOrgCode(String collectionOrgCode) {
		this.collectionOrgCode = collectionOrgCode;
	}

	/**
	 * @return the collectionOrgName
	 */
	public String getCollectionOrgName() {
		return collectionOrgName;
	}

	/**
	 * @param collectionOrgName
	 *            the collectionOrgName to set
	 */
	public void setCollectionOrgName(String collectionOrgName) {
		this.collectionOrgName = collectionOrgName;
	}

	/**
	 * @return the generatingOrgCode
	 */
	public String getGeneratingOrgCode() {
		return generatingOrgCode;
	}

	/**
	 * @param generatingOrgCode
	 *            the generatingOrgCode to set
	 */
	public void setGeneratingOrgCode(String generatingOrgCode) {
		this.generatingOrgCode = generatingOrgCode;
	}

	/**
	 * @return the generatingOrgName
	 */
	public String getGeneratingOrgName() {
		return generatingOrgName;
	}

	/**
	 * @param generatingOrgName
	 *            the generatingOrgName to set
	 */
	public void setGeneratingOrgName(String generatingOrgName) {
		this.generatingOrgName = generatingOrgName;
	}

	/**
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo
	 *            the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return the billType
	 */
	public String getBillType() {
		return billType;
	}

	/**
	 * @param billType
	 *            the billType to set
	 */
	public void setBillType(String billType) {
		this.billType = billType;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the paymentType
	 */
	public String getPaymentType() {
		return paymentType;
	}

	/**
	 * @param paymentType
	 *            the paymentType to set
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	/**
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * @return the cashConfirmUserCode
	 */
	public String getCashConfirmUserCode() {
		return cashConfirmUserCode;
	}

	/**
	 * @param cashConfirmUserCode
	 *            the cashConfirmUserCode to set
	 */
	public void setCashConfirmUserCode(String cashConfirmUserCode) {
		this.cashConfirmUserCode = cashConfirmUserCode;
	}

	/**
	 * @return the cashConfirmUserName
	 */
	public String getCashConfirmUserName() {
		return cashConfirmUserName;
	}

	/**
	 * @param cashConfirmUserName
	 *            the cashConfirmUserName to set
	 */
	public void setCashConfirmUserName(String cashConfirmUserName) {
		this.cashConfirmUserName = cashConfirmUserName;
	}

	/**
	 * @return the cashConfirmTime
	 */
	public Date getCashConfirmTime() {
		return cashConfirmTime;
	}

	/**
	 * @param cashConfirmTime
	 *            the cashConfirmTime to set
	 */
	public void setCashConfirmTime(Date cashConfirmTime) {
		this.cashConfirmTime = cashConfirmTime;
	}

	/**
	 * @return productCode
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * @param productCode
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

}
