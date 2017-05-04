package com.deppon.pda.bdm.module.foss.delivery.shared.domain;

import java.math.BigDecimal;

/** 
  * @ClassName PayMessageEntity 
  * @Description 
  * @author  
  * @date 
*/ 
/**  
 *@author 
 *@date   
 */
public class PayMessageResultEntity{
	/**
	 * 代收款
	 */
	private BigDecimal codAmount;
	
	/**
	 * 到付款
	 */
	private BigDecimal toPayAmount;

	public BigDecimal getCodAmount() {
		return codAmount;
	}

	public void setCodAmount(BigDecimal codAmount) {
		this.codAmount = codAmount;
	}

	public BigDecimal getToPayAmount() {
		return toPayAmount;
	}

	public void setToPayAmount(BigDecimal toPayAmount) {
		this.toPayAmount = toPayAmount;
	}
	
	
	
}
