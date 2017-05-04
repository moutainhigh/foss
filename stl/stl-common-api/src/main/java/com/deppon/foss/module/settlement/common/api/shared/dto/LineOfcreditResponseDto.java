package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.math.BigDecimal;
import java.util.Date;

public class LineOfcreditResponseDto {
	/**
	 * 返回結果 0 失败 1成功
	 */
	private int result;
	/**
	 * 是否成功
	 */
	private String isNot;
	/**
	 * 最长临欠日期
	 */
	private Date  longCrdeitDate;
	/**
	 * 未核销临欠金额
	 */
	private BigDecimal unCrdeitAmount;
	
	public Date getLongCrdeitDate() {
		return longCrdeitDate;
	}
	public void setLongCrdeitDate(Date longCrdeitDate) {
		this.longCrdeitDate = longCrdeitDate;
	}
	public BigDecimal getUnCrdeitAmount() {
		return unCrdeitAmount;
	}
	public void setUnCrdeitAmount(BigDecimal unCrdeitAmount) {
		this.unCrdeitAmount = unCrdeitAmount;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public String getIsNot() {
		return isNot;
	}
	public void setIsNot(String isNot) {
		this.isNot = isNot;
	}
	
	
	
}
