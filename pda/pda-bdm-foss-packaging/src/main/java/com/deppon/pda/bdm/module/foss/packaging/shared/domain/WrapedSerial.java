package com.deppon.pda.bdm.module.foss.packaging.shared.domain;

public class WrapedSerial {
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 流水号
	 */
	private String serialNo;
	
	private String wrapType;
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getWrapType() {
		return wrapType;
	}

	public void setWrapType(String wrapType) {
		this.wrapType = wrapType;
	}
	
}
