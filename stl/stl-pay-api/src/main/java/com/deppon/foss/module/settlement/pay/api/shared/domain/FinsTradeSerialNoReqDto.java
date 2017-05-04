package com.deppon.foss.module.settlement.pay.api.shared.domain;

import java.io.Serializable;


/**
 * 用于对接财务资助删除流水号的请求实体类
 * @author 357637
 *
 */
public class FinsTradeSerialNoReqDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/**
	 * 交易流水号
	 */
	private String tradeSerialNo;

	public String getTradeSerialNo() {
		return tradeSerialNo;
	}

	public void setTradeSerialNo(String tradeSerialNo) {
		this.tradeSerialNo = tradeSerialNo;
	}

	@Override
	public String toString() {
		return "NciTradeSerialNoReqEntity [tradeSerialNo=" + tradeSerialNo
				+ "]";
	}
	
}
