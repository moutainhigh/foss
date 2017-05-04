package com.deppon.foss.esb.server;

import java.io.Serializable;

public class SampleDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5628669102860139938L;
	
	
	private String id;
	private String code;
	private String desc;
	
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
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
