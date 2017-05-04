package com.deppon.foss.module.transfer.oa.server.shared.response;

import java.io.Serializable;

public class LineLossWaybillInfRequest implements Serializable{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**返回编码*/
	private String waybillNum;

	public String getWaybillNum() {
		return waybillNum;
	}

	public void setWaybillNum(String waybillNum) {
		this.waybillNum = waybillNum;
	}
	
}
