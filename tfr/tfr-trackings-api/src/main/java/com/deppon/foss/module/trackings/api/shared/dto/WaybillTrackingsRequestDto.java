package com.deppon.foss.module.trackings.api.shared.dto;

import java.util.List;

public class WaybillTrackingsRequestDto {
	//消息ID
	private String msgId;
	//运单号
	private String code;
	//跟踪状态 
	private String watchStatus;
	//推送操作状态:append:扩展，override:覆盖
	private String operation;
	//包裹运输状态，0在途、1揽收、2疑难、3签收、4退签、5派件、6退回、7转单、8结算
	private String status;
	//快递公司编码
	private String company;
	//回调参数
	private String callback;
	//轨迹详情
	private List<WaybillTrackingsResultDto> detail;
	
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getWatchStatus() {
		return watchStatus;
	}
	public void setWatchStatus(String watchStatus) {
		this.watchStatus = watchStatus;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getCallback() {
		return callback;
	}
	public void setCallback(String callback) {
		this.callback = callback;
	}
	public List<WaybillTrackingsResultDto> getDetail() {
		return detail;
	}
	public void setDetail(List<WaybillTrackingsResultDto> detail) {
		this.detail = detail;
	}
	
}
