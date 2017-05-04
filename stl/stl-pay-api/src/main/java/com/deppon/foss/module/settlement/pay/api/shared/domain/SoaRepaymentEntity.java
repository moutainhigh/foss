package com.deppon.foss.module.settlement.pay.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 对账还款关系
 * 
 * @author foss-qiaolifeng
 * @date 2012-11-9 下午5:21:47
 */
public class SoaRepaymentEntity extends BaseEntity {

	/**
	 * 实体序列号
	 */
	private static final long serialVersionUID = -8714695809628654488L;

	/**
	 * ID
	 */
	private String id;

	/**
	 * 对账单号
	 */
	private String statementBillNo;

	/**
	 * 还款单编号
	 */
	private String repaymentNo;

	/**
	 * 还款日期
	 */
	private Date paymentDate;

	/**
	 * 还款金额
	 */
	private BigDecimal repaymentAmount;

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
	 * @return repaymentNo
	 */
	public String getRepaymentNo() {
		return repaymentNo;
	}

	/**
	 * @param repaymentNo
	 */
	public void setRepaymentNo(String repaymentNo) {
		this.repaymentNo = repaymentNo;
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
	 * @return repaymentAmount
	 */
	public BigDecimal getRepaymentAmount() {
		return repaymentAmount;
	}

	/**
	 * @param repaymentAmount
	 */
	public void setRepaymentAmount(BigDecimal repaymentAmount) {
		this.repaymentAmount = repaymentAmount;
	}

}
