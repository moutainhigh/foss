package com.deppon.foss.module.settlement.pay.api.shared.domain;

import java.io.Serializable;

public class FinsTradeSerialNoRespDto implements Serializable{
	
	private static final long serialVersionUID = 1L;

	//交易流水号
	private String tradeSerialNo;
		
	/*
	 * 删除状态 --  
	 * 1代表成功.   
	 * 0代表FINS系统中没有此交易流水号的数据。
	 * 2代表FINS系统在进行删除操作时出现异常。
	 * 3代表FOSS系统传递过来的参数为空。
	 */
	private String state;

	public String getTradeSerialNo() {
		return tradeSerialNo;
	}

	public void setTradeSerialNo(String tradeSerialNo) {
		this.tradeSerialNo = tradeSerialNo;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "FinsTradeSerialNoRespEntity [tradeSerialNo=" + tradeSerialNo
				+ ", state=" + state + "]";
	}
	
}
