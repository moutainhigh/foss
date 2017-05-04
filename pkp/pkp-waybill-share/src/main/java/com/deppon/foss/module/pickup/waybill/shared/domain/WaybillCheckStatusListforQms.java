package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="WaybillCheckStatusListforQms")
public class WaybillCheckStatusListforQms{
	 //消息代码
    private String message_code;
    //消息说明
    private String message_detail;
    //处理明细
    private List<WaybillCheckStatus> detail;
    
	public String getMessage_code() {
		return message_code;
	}
	public void setMessage_code(String messageCode) {
		this.message_code = messageCode;
	}
	public String getMessage_detail() {
		return message_detail;
	}
	public void setMessage_detail(String messageDetail) {
		this.message_detail = messageDetail;
	}
	public List<WaybillCheckStatus> getDetail() {
		return detail;
	}
	public void setDetail(List<WaybillCheckStatus> detail) {
		this.detail = detail;
	}
}
