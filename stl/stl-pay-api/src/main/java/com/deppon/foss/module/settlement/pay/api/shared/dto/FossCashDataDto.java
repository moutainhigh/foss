/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.settlement.pay.api.shared.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 交款实体
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:105762,date:2014-7-28 下午3:28:55,content: </p>
 * @author 105762
 * @date 2014-7-28 下午3:28:55
 * @since 1.6
 * @version 1.0
 */
public class FossCashDataDto {
	/**
	 * 缴款日期
	 */
	private Date cashDate;
	/**
	 * 部门标杆编码
	 */
	private String deptNum;
	/**
	 * 金额
	 */
	private BigDecimal cashAmt;

	/**
	  * @return  the cashDate
	 */
	public Date getCashDate() {
		return cashDate;
	}

	/**
	 * @param cashDate the cashDate to set
	 */
	public void setCashDate(Date cashDate) {
		this.cashDate = cashDate;
	}

	/**
	  * @return  the deptNum
	 */
	public String getDeptNum() {
		return deptNum;
	}

	/**
	 * @param deptNum the deptNum to set
	 */
	public void setDeptNum(String deptNum) {
		this.deptNum = deptNum;
	}

	/**
	  * @return  the cashAmt
	 */
	public BigDecimal getCashAmt() {
		return cashAmt;
	}

	/**
	 * @param cashAmt the cashAmt to set
	 */
	public void setCashAmt(BigDecimal cashAmt) {
		this.cashAmt = cashAmt;
	}
}