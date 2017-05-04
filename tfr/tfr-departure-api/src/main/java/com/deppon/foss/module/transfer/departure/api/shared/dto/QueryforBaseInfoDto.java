package com.deppon.foss.module.transfer.departure.api.shared.dto;

import java.io.Serializable;

public class QueryforBaseInfoDto implements Serializable {
	
	private String startName;
	private String endName;
	public String getStartName() {
		return startName;
	}
	public void setStartName(String startName) {
		this.startName = startName;
	}
	public String getEndName() {
		return endName;
	}
	public void setEndName(String endName) {
		this.endName = endName;
	}
	


}
