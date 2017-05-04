package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import java.io.Serializable;

public class KdOneManyLimitEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String confName;
	private String confValue;
	public String getConfName() {
		return confName;
	}
	public void setConfName(String confName) {
		this.confName = confName;
	}
	public String getConfValue() {
		return confValue;
	}
	public void setConfValue(String confValue) {
		this.confValue = confValue;
	}
	

}
