package com.deppon.pda.bdm.module.foss.unload.shared.domain;

import java.io.Serializable;

public class ResultStockPositionNumber implements Serializable{
	
	private static final long serialVersionUID = 2075791909076280396L;
	/**
	 * 运单号
	 */
	private String waybillNO;
	/**
	 * 流水号
	 */
	private String serialNO;
	/**
	 * 定位编号
	 */
	private String stockPositionNumber;
	/**
	 * 部门编号
	 */
	private String orgCode;
	public String getWaybillNO() {
		return waybillNO;
	}
	public void setWaybillNO(String waybillNO) {
		this.waybillNO = waybillNO;
	}
	public String getSerialNO() {
		return serialNO;
	}
	public void setSerialNO(String serialNO) {
		this.serialNO = serialNO;
	}
	public String getStockPositionNumber() {
		return stockPositionNumber;
	}
	public void setStockPositionNumber(String stockPositionNumber) {
		this.stockPositionNumber = stockPositionNumber;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	
	
}
