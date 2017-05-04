package com.deppon.pda.bdm.module.foss.clear.shared.domain;

public class ClearSerialNo {
	/**是否未打包装*/
	private String isUnPacking;
	/**流水号*/
	private String serialNo;
	/**
	 * 是否更换标签
	 */
	private String isChgLabel;
	public String getIsUnPacking() {
		return isUnPacking;
	}
	public void setIsUnPacking(String isUnPacking) {
		this.isUnPacking = isUnPacking;
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
