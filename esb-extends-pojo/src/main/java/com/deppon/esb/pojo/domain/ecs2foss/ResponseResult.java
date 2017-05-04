package com.deppon.esb.pojo.domain.ecs2foss;

import java.io.Serializable;


/**
 * 响应结果集对象
 * @author 
 *
 */
public class ResponseResult implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 8698885404992061568L;
	//ID
	private String id;
	//编号
	private String code;
	//活动状态
	private String active;
	//执行描述
	private String info;
	//执行时间
	private Long executeTime;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public Long getExecuteTime() {
		return executeTime;
	}
	public void setExecuteTime(Long executeTime) {
		this.executeTime = executeTime;
	}
}
