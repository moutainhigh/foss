package com.deppon.foss.module.settlement.common.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 对账单确认回执(营业部去客户处收款时，一种款项收取凭证，用户会计审核还款单)
 * 
 * @author zhangjiheng
 * @date 2012-10-11 下午5:05:23
 */
public class StatementConfReceiptEntity extends BaseEntity {

	/**
	 * 实体序列号
	 */
	private static final long serialVersionUID = 1568232316844668120L;

	/**
	 * id
	 */
	private String id;

	/**
	 * 确认回执编号
	 */
	private String conReceiptNo;

	/**
	 * 对账单号
	 */
	private String statementBillNo;

	/**
	 * 部门编码
	 */
	private String orgCode;

	/**
	 * 部门编码
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
	 * 实收金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal receivedAmount;

	/**
	 * 还款方式
	 */
	private String paymentType;

	/**
	 * 还款日期
	 */
	private Date paymentDate;

	/**
	 * 收款人名称
	 */
	private String receiveEmpName;

	/**
	 * 创建人名称
	 */
	private String createUserName;

	/**
	 * 客户意见
	 */
	private String customerIdea;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return conReceiptNo
	 */
	public String getConReceiptNo() {
		return conReceiptNo;
	}

	/**
	 * @param conReceiptNo
	 */
	public void setConReceiptNo(String conReceiptNo) {
		this.conReceiptNo = conReceiptNo;
	}

	/**
	 * @return statementBillNo
	 */
	public String getStatementBillNo() {
		return statementBillNo;
	}

	/**
	 * @param statementBillNo
	 */
	public void setStatementBillNo(String statementBillNo) {
		this.statementBillNo = statementBillNo;
	}

	/**
	 * @return orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * @param orgCode
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * @return orgName
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * @param orgName
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
	 * @return customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * @param customerCode
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * @return customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param customerName
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @return receivedAmount
	 */
	public BigDecimal getReceivedAmount() {
		return receivedAmount;
	}

	/**
	 * @param receivedAmount
	 */
	public void setReceivedAmount(BigDecimal receivedAmount) {
		this.receivedAmount = receivedAmount;
	}

	/**
	 * @return paymentType
	 */
	public String getPaymentType() {
		return paymentType;
	}

	/**
	 * @param paymentType
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	/**
	 * @return paymentDate
	 */
	public Date getPaymentDate() {
		return paymentDate;
	}

	/**
	 * @param paymentDate
	 */
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	/**
	 * @return receiveEmpName
	 */
	public String getReceiveEmpName() {
		return receiveEmpName;
	}

	/**
	 * @param receiveEmpName
	 */
	public void setReceiveEmpName(String receiveEmpName) {
		this.receiveEmpName = receiveEmpName;
	}

	/**
	 * @return createUserName
	 */
	public String getCreateUserName() {
		return createUserName;
	}

	/**
	 * @param createUserName
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	/**
	 * @return customerIdea
	 */
	public String getCustomerIdea() {
		return customerIdea;
	}

	/**
	 * @param customerIdea
	 */
	public void setCustomerIdea(String customerIdea) {
		this.customerIdea = customerIdea;
	}

	/**
	 * @return createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
