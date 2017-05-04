package com.deppon.foss.module.settlement.pay.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 按对账单还款的返回Dto
 * 
 * @author foss-qiaolifeng
 * @date 2012-12-5 下午1:37:49
 */
public class BillStatementToPaymentResultDto implements Serializable {

	/**
	 * 按对账单还款的返回Dto序列号
	 */
	private static final long serialVersionUID = -2811794819665771075L;

	/**
	 * 汇款编号
	 */
	private String remittanceNumber;

	/**
	 * 汇款人名称
	 */
	private String remittanceName;

	/**
	 * 汇款日期
	 */
	private Date remittanceDate;

	/**
	 * 汇款可用金额
	 */
	private BigDecimal availableAmount;

	
	/**
	 * @return remittanceNumber
	 */
	public String getRemittanceNumber() {
		return remittanceNumber;
	}

	
	/**
	 * @param remittanceNumber
	 */
	public void setRemittanceNumber(String remittanceNumber) {
		this.remittanceNumber = remittanceNumber;
	}

	
	/**
	 * @return remittanceName
	 */
	public String getRemittanceName() {
		return remittanceName;
	}

	
	/**
	 * @param remittanceName
	 */
	public void setRemittanceName(String remittanceName) {
		this.remittanceName = remittanceName;
	}

	
	/**
	 * @return remittanceDate
	 */
	public Date getRemittanceDate() {
		return remittanceDate;
	}

	
	/**
	 * @param remittanceDate
	 */
	public void setRemittanceDate(Date remittanceDate) {
		this.remittanceDate = remittanceDate;
	}

	
	/**
	 * @return availableAmount
	 */
	public BigDecimal getAvailableAmount() {
		return availableAmount;
	}

	
	/**
	 * @param availableAmount
	 */
	public void setAvailableAmount(BigDecimal availableAmount) {
		this.availableAmount = availableAmount;
	}

	

}
