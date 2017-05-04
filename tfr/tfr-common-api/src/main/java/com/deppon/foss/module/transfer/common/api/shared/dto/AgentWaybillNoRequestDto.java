package com.deppon.foss.module.transfer.common.api.shared.dto;
/**
 * @author niuly
 * @function 快递100请求轨迹的代理单号
 * @date 2015年2月3日下午4:43:40
 */
public class AgentWaybillNoRequestDto {
	//快递代理单号
	private String number;
	//快递代理公司在快递100对应的编码
	private String company;
	//出发地城市
	private String from;
	//目的地城市
	private String to;
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	
}
