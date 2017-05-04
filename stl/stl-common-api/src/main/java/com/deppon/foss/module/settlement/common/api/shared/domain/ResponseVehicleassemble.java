package com.deppon.foss.module.settlement.common.api.shared.domain;

import java.io.Serializable;

/**
 * 请求悟空系统，查询配载单数response实体
 * @author 325369
 * @date   2016-9-6
 */
public class ResponseVehicleassemble implements Serializable{
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	
	private int status;
	
	private String exMsg;
	
	private String data;

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

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	

}
