package com.deppon.foss.module.pickup.pda.api.shared.dto;

import java.math.BigDecimal;

/** 
 * @ClassName: PdaFinancialDto 
 * @Description: Pda查询代收款和到付款输出Dto
 * @author 237982-foss-fangwenjun 
 * @date 2015-6-29 下午5:35:17 
 *  
 */
public class PdaFinancialDto {

	/**
	 * 代收款
	 */
	private BigDecimal codAmount;
	
	/**
	 * 到付款
	 */
	private BigDecimal toPayAmount;

	/**
	 * 获取codAmount
	 * @return the codAmount
	 */
	public BigDecimal getCodAmount() {
		return codAmount;
	}

	/**
	 * 设置codAmount
	 * @param codAmount 要设置的codAmount
	 */
	public void setCodAmount(BigDecimal codAmount) {
		this.codAmount = codAmount;
	}

	/**
	 * 获取toPayAmount
	 * @return the toPayAmount
	 */
	public BigDecimal getToPayAmount() {
		return toPayAmount;
	}

	/**
	 * 设置toPayAmount
	 * @param toPayAmount 要设置的toPayAmount
	 */
	public void setToPayAmount(BigDecimal toPayAmount) {
		this.toPayAmount = toPayAmount;
	}
	
	
}
