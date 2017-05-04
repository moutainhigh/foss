package com.deppon.pda.bdm.module.foss.unload.shared.domain;

public class UnldSerialNoModel {
	/**是否未打包装*/
	private String isWrap;
	/**流水号*/
	private String serialNo;
	/**
	 * 是否更换标签
	 */
	private String isChgLabel;
	public String getIsWrap() {
		return isWrap;
	}
	
	public void setIsWrap(String isWrap) {
		this.isWrap = isWrap;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getIsChgLabel() {
		return isChgLabel;
	}

	public void setIsChgLabel(String isChgLabel) {
		this.isChgLabel = isChgLabel;
	}
	
	
}
