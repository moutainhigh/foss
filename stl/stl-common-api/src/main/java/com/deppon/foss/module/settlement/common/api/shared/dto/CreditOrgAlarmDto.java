package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.math.BigDecimal;

/**
 * 
 * 部门临欠额度、最大账期预期DTO
 * 
 * @author 046644-foss-zengbinwen
 * @date 2013-4-8 下午1:47:48
 */
public class CreditOrgAlarmDto {

	/**
	 * 部门编码
	 */
	private String orgCode;

	/**
	 * 部门名称
	 */
	private String orgName;

	/**
	 * 临欠客户预警值
	 */
	private BigDecimal maxDebitAmount;

	/**
	 * 部门已用额度
	 */
	private BigDecimal usedAmount;

	/**
	 * 账期最大天数
	 */
	private int debitLimitDays;

	/**
	 * 最大账期
	 */
	private int maxAccountDays;

	/**
	 * 临时欠款预警值
	 */
	private int debitLimitAlarmDays;

	/**
	 * 信用额度预警值
	 */
	private BigDecimal debitAlaramAmount;

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
	 * @return maxDebitAmount
	 */
	public BigDecimal getMaxDebitAmount() {
		return maxDebitAmount;
	}

	/**
	 * @param maxDebitAmount
	 */
	public void setMaxDebitAmount(BigDecimal maxDebitAmount) {
		this.maxDebitAmount = maxDebitAmount;
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
	 * @return debitLimitDays
	 */
	public int getDebitLimitDays() {
		return debitLimitDays;
	}

	/**
	 * @param debitLimitDays
	 */
	public void setDebitLimitDays(int debitLimitDays) {
		this.debitLimitDays = debitLimitDays;
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

	/**
	 * @return the debitLimitAlarmDays
	 */
	public int getDebitLimitAlarmDays() {
		return debitLimitAlarmDays;
	}

	/**
	 * @param debitLimitAlarmDays
	 *            the debitLimitAlarmDays to set
	 */
	public void setDebitLimitAlarmDays(int debitLimitAlarmDays) {
		this.debitLimitAlarmDays = debitLimitAlarmDays;
	}

	/**
	 * @return the debitAlaramAmount
	 */
	public BigDecimal getDebitAlaramAmount() {
		return debitAlaramAmount;
	}

	/**
	 * @param debitAlaramAmount
	 *            the debitAlaramAmount to set
	 */
	public void setDebitAlaramAmount(BigDecimal debitAlaramAmount) {
		this.debitAlaramAmount = debitAlaramAmount;
	}

}
