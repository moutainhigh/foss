
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;

public class ResponseGXG implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private String result;
	private String resultCode;
	private String desc;
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}