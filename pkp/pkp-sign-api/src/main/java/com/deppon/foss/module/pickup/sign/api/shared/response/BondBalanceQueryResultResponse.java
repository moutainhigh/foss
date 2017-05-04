package com.deppon.foss.module.pickup.sign.api.shared.response;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 保证金余额查询结果
 * @author 239284
 * @date 2016-1-14
 * @since 2016-1-1
 * @version 1.0.0 
 * @remark
 * @copyright Copyright (c) 2015 Deppon
 */
public class BondBalanceQueryResultResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 151059926004177236L;
	
	/**
	 * 期末余额
	 */
	private BigDecimal endAmount;
	
	/**
	 * 异常信息
	 */
	private String errorInfo;
	

	/**
	 * @return the endAmount
	 */
	public BigDecimal getEndAmount() {
		return endAmount;
	}

	/**
	 * @param endAmount the endAmount to set
	 */
	public void setEndAmount(BigDecimal endAmount) {
		this.endAmount = endAmount;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}
	
}
