package com.deppon.foss.module.base.querying.shared.domain;

import java.math.BigDecimal;

/**
 * 2013-7-18 foss-132599-shenweihua
 * 发票基类   
 */
public class Invoice {
	/**
	 * 始开已开票金额
	 */
	private BigDecimal leaveAmount;
	
	/**
	 * 到达已开票金额
	 */
	private BigDecimal arriveAmount;
	
	/**获得始开已开票金额*/
	public BigDecimal getLeaveAmount() {
		return leaveAmount;
	}
	
	/**设置始开已开票金额*/
	public void setLeaveAmount(BigDecimal leaveAmount) {
		this.leaveAmount = leaveAmount;
	}
	
	/**获得到达已开票金额*/
	public BigDecimal getArriveAmount() {
		return arriveAmount;
	}
	
	/**获得到达已开票金额*/
	public void setArriveAmount(BigDecimal arriveAmount) {
		this.arriveAmount = arriveAmount;
	}

}
