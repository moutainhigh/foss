package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="WaybillProInfoListforQms")
public class WaybillProInfoListforQms{
	 //消息代码
    private String message_code;
    //消息说明
    private String message_detail;
    //处理明细
    private List<WaybillProInfo> detail;
    
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
	public List<WaybillProInfo> getDetail() {
		return detail;
	}
	public void setDetail(List<WaybillProInfo> detail) {
		this.detail = detail;
	}
	
}
