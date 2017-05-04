package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @package com.deppon.pda.bdm.module.foss.accept.shared.domain
 * @file QuotesEntity.java
 * @description PDA承运报价查询
 * @author ChenLiang
 * @created 2012-12-31 下午3:03:19
 * @version 1.0
 */
public class QuotesEntity implements Serializable {

	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 客户编号
	 */
	private String customerCode;

	/**
	 * 日期
	 */
	private Date date;

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
