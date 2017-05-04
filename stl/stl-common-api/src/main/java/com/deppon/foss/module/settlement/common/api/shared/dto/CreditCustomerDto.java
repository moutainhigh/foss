package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 客户额度信息Dto
 * 
 * @author dp-huangxb
 * @date 2012-10-19 下午5:04:46
 */
public class CreditCustomerDto {

	/**
	 * 客户编码
	 */
	private String customerCode;

	/**
	 * 申请欠款额度
	 */
	private BigDecimal totalAmount;

	/**
	 * 已用额度
	 */
	private BigDecimal usedAmount;

	/**
	 * 是否超期欠款
	 */
	private String isOverdue;

	/**
	 * 备注信息
	 */
	private String notes;
    /**
     *最早欠款日期
     */
    private Date minDebitDate;

    public Date getMinDebitDate() {
        return minDebitDate;
    }

    public void setMinDebitDate(Date minDebitDate) {
        this.minDebitDate = minDebitDate;
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
	 * @return totalAmount
	 */
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param totalAmount
	 */
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @return usedAmount
	 */
	public BigDecimal getUsedAmount() {
		return usedAmount;
	}

	/**
	 * @param usedAmount
	 */
	public void setUsedAmount(BigDecimal usedAmount) {
		this.usedAmount = usedAmount;
	}

	/**
	 * @return isOverdue
	 */
	public String getIsOverdue() {
		return isOverdue;
	}

	/**
	 * @param isOverdue
	 */
	public void setIsOverdue(String isOverdue) {
		this.isOverdue = isOverdue;
	}

	/**
	 * @return notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

}
