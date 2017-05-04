package com.deppon.esb.pojo.domain.foss2dop;

import java.io.Serializable;

public class LabelInfoJmsResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	private String uuid;
	private String waybillNo;
	
	/**
	 *0-失败，1-成功 
	 */
	private String code;
	private String message;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
}
