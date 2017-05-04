package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 组织的欠款额度信息
 * 
 * @author dp-huangxb
 * @date 2012-10-19 下午5:22:24
 */
public class CreditOrgDto implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 5992612138704227945L;

	/**
	 * 组织编码
	 */
	private String orgCode;

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
	 * 最大账期
	 */
	private int maxAccountDays;
    /**
     * 最早欠款日期
     */
    private Date minDebitDate;

    public Date getMinDebitDate() {
        return minDebitDate;
    }

    public void setMinDebitDate(Date minDebitDate) {
        this.minDebitDate = minDebitDate;
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

	/**
	 * @return maxAccountDays
	 */
	public int getMaxAccountDays() {
		return maxAccountDays;
	}

	/**
	 * @param maxAccountDays
	 */
	public void setMaxAccountDays(int maxAccountDays) {
		this.maxAccountDays = maxAccountDays;
	}

}
