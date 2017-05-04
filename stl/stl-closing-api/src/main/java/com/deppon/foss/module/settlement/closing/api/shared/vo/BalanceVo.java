package com.deppon.foss.module.settlement.closing.api.shared.vo;

import java.util.Date;

/**
 * 
 * 结账批次VO
 * 
 * @author 046644-foss-zengbinwen
 * @date 2013-3-15 下午3:15:24
 */
public class BalanceVo {

	/**
	 * 截止日期
	 */
	private Date endDate;

	/**
	 * 结账当前日期
	 */
	private Date currentBalanceDate;

	/**
	 * 结账程序是否在运行
	 */
	private boolean balanceRun;

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the currentBalanceDate
	 */
	public Date getCurrentBalanceDate() {
		return currentBalanceDate;
	}

	/**
	 * @param currentBalanceDate
	 *            the currentBalanceDate to set
	 */
	public void setCurrentBalanceDate(Date currentBalanceDate) {
		this.currentBalanceDate = currentBalanceDate;
	}

	/**
	 * @return the balanceRun
	 */
	public boolean isBalanceRun() {
		return balanceRun;
	}

	/**
	 * @param balanceRun
	 *            the balanceRun to set
	 */
	public void setBalanceRun(boolean balanceRun) {
		this.balanceRun = balanceRun;
	}

}
