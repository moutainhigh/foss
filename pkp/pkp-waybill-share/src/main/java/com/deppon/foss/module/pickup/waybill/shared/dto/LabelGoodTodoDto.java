package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;

public class LabelGoodTodoDto implements Serializable {
	private static final long serialVersionUID = 1L;

	//代办ID
	private String labelGoodId;
	
	//运单号
	private String waybillNo;
	
	//流水号
	private String serialNo;
	
	//更改单ID
	private String waybillRfcId;
	
	//异常信息
	private String exceptionMsg;

	public String getLabelGoodId() {
		return labelGoodId;
	}

	public void setLabelGoodId(String labelGoodId) {
		this.labelGoodId = labelGoodId;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getWaybillRfcId() {
		return waybillRfcId;
	}

	public void setWaybillRfcId(String waybillRfcId) {
		this.waybillRfcId = waybillRfcId;
	}

	public String getExceptionMsg() {
		return exceptionMsg;
	}

	public void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}
}
