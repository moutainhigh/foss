package com.deppon.foss.module.settlement.agency.api.shared.domain;

import java.io.Serializable;

public class ResponseLdpExternalBill<T> implements Serializable{
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	
	private int status;

    private String exMsg;
    
    private T data;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getExMsg() {
		return exMsg;
	}

	public void setExMsg(String exMsg) {
		this.exMsg = exMsg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	
    
}
