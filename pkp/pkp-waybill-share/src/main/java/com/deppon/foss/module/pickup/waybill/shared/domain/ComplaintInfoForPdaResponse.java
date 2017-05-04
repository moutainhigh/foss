package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.util.List;

import javax.xml.ws.Holder;

import com.deppon.esb.header.ESBHeader;

public class ComplaintInfoForPdaResponse {
	// Y 是 N否
	public String ifSuccess;
	// 异常信息
	public String messageException;
     // ESB 头信息
    protected Holder<ESBHeader> holder;
	// 投诉的详细信息
	List<ComplaintDetail> complaintDetail;
	public String getIfSuccess() {
		return ifSuccess;
	}
	public void setIfSuccess(String ifSuccess) {
		this.ifSuccess = ifSuccess;
	}
	public String getMessageException() {
		return messageException;
	}
	public void setMessageException(String messageException) {
		this.messageException = messageException;
	}
	public List<ComplaintDetail> getComplaintDetail() {
		return complaintDetail;
	}
	public void setComplaintDetail(List<ComplaintDetail> complaintDetail) {
		this.complaintDetail = complaintDetail;
	}
	public Holder<ESBHeader> getHolder() {
		return holder;
	}
	public void setHolder(Holder<ESBHeader> holder) {
		this.holder = holder;
	}

}
