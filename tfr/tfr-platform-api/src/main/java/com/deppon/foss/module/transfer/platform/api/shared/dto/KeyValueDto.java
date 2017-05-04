package com.deppon.foss.module.transfer.platform.api.shared.dto;

import java.io.Serializable;

public class KeyValueDto implements Serializable {

	private static final long serialVersionUID = -3596743164960526604L;

	private String code;

	private String name;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
