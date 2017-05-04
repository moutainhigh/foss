package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="WaybillDetaillListEntityforQms")
public class WaybillDetaillListEntityforQms{
	 //消息代码
    private String message_code;
    //消息说明
    private String message_detail;
    //返回记录的条数
    private Integer count;
    //处理明细
    private List<WayBillDetailForQms> detail;
    
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
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public List<WayBillDetailForQms> getDetail() {
		return detail;
	}
	public void setDetail(List<WayBillDetailForQms> detail) {
		this.detail = detail;
	}
}
