package com.deppon.foss.module.trackings.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

public class TrackExternalLogEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//运单号
	private String waybillNo;
	//流水号
	private String serialNo;

	//推送结果描述
	private String msgInfo;
	//推送类型
	private String msgType;
	//推送结果
	private String synResult;
	
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getMsgInfo() {
		return msgInfo;
	}
	public void setMsgInfo(String msgInfo) {
		this.msgInfo = msgInfo;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getSynResult() {
		return synResult;
	}
	public void setSynResult(String synResult) {
		this.synResult = synResult;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

}
