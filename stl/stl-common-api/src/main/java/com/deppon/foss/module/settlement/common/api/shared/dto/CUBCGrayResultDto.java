package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;

public class CUBCGrayResultDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String distributeType;//分发类型（FOSS/CUBC/ALL）
	
	private String message;

	public String getDistributeType() {
		return distributeType;
	}

	public void setDistributeType(String distributeType) {
		this.distributeType = distributeType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
