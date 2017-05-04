package com.deppon.pda.bdm.module.foss.delivery.shared.domain;

import java.io.Serializable;
import java.util.List;

public class VestBatchResult implements Serializable{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1152363784844602090L;

	/**
	 * 归属系统编码
	 */
	private String vestSystemCode;
	
	/**
	 * 归属服务的对象
	 */
	private List<String> vestObject;

	public String getVestSystemCode() {
		return vestSystemCode;
	}

	public void setVestSystemCode(String vestSystemCode) {
		this.vestSystemCode = vestSystemCode;
	}

	public List<String> getVestObject() {
		return vestObject;
	}

	public void setVestObject(List<String> vestObject) {
		this.vestObject = vestObject;
	}

}
