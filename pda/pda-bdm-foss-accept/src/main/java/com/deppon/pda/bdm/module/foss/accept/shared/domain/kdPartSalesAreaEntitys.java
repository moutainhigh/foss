package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import java.io.Serializable;

public class kdPartSalesAreaEntitys implements Serializable{
	private static final long serialVersionUID = 1L;
	//营业区编码
	private String areaCode;
	//营业部区名称
	private String areaName;
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	

}
