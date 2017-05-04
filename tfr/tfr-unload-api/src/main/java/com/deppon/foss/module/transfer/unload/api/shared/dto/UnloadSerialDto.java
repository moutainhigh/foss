package com.deppon.foss.module.transfer.unload.api.shared.dto;

import java.io.Serializable;

public class UnloadSerialDto implements Serializable {

	private static final long serialVersionUID = -5318509018801078157L;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 流水号
	 */
	private String serialNo;

	/**
	 * 是否需要打包装
	 */
	private String needPacked;

	/**
	 * 是否有代办
	 */
	private String hasTodo;

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getNeedPacked() {
		return needPacked;
	}

	public void setNeedPacked(String needPacked) {
		this.needPacked = needPacked;
	}

	public String getHasTodo() {
		return hasTodo;
	}

	public void setHasTodo(String hasTodo) {
		this.hasTodo = hasTodo;
	}

	@Override
	public String toString() {
		return "UnloadSerialDto [waybillNo=" + waybillNo + ", serialNo=" + serialNo + ", needPacked=" + needPacked
				+ ", hasTodo=" + hasTodo + "]";
	}

}
