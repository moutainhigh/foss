package com.deppon.foss.module.trackings.api.shared.dto;


public class WaybillTrackingsResultDto {
	//路由ID
	private String id;
	//描述
	//private String description;
	private String context;
	//时间
	private String time;
	//地址
	private String location;
	//操作人
	private String operator;
	//操作人电话
	private String tel;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	/*public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}*/
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	
}
