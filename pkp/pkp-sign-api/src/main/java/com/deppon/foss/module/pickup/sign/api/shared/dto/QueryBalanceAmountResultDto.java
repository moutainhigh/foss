package com.deppon.foss.module.pickup.sign.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 当付款方式为余额结清时调用CUBC查询的返回结果集实体
 * @author 353654
 *
 */
public class QueryBalanceAmountResultDto implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private BigDecimal balanceAmount;
	
	private String meg;
	
	private String resuleMark;

	public BigDecimal getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(BigDecimal balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public String getMeg() {
		return meg;
	}

	public void setMeg(String meg) {
		this.meg = meg;
	}

	public String getResuleMark() {
		return resuleMark;
	}

	public void setResuleMark(String resuleMark) {
		this.resuleMark = resuleMark;
	}
}
