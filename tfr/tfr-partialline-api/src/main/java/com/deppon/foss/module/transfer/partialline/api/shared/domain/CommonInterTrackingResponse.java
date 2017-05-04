package com.deppon.foss.module.transfer.partialline.api.shared.domain;

import java.io.Serializable;

public class CommonInterTrackingResponse implements Serializable {

	 /** 
	  * @author 352203 
	  * @since Ver 1.0   
	 */
	private static final long serialVersionUID = 2116620529302708075L;
	
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 接收成功 或者 失败
	 */
	private Boolean success;
	
	/**
	 * 若失败，将错误原因写进该字段
	 */
	private String errorMsg;

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
