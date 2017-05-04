package com.deppon.foss.module.transfer.stock.api.shared.domain;

import java.io.Serializable;

public class RequestParammPojo implements Serializable{

	private static final long serialVersionUID = 1L;
	private String waybillNOs;
	
	
	public String getWaybillNOs() {
		return waybillNOs;
	}

	public void setWaybillNOs(String waybillNOs) {
		this.waybillNOs = waybillNOs;
	}

	
}
