package com.deppon.foss.module.transfer.stock.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class StockPositionNumberEntity extends BaseEntity{

	/**
	 * 定位编号 200968 zwd 20141219 09:36
	 */
	private static final long serialVersionUID = 1L;
	
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
	/**
	 * 扫描时间
	 */
	private Date scanTime;
	/**
	 * 部门编号
	 */
	public String getOrgCode() {
		return orgCode;
	}
	/**
	 * 部门编号
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	/**
	 * 运单号
	 */
	public String getWaybillNO() {
		return waybillNO;
	}
	/**
	 * 运单号
	 */
	public void setWaybillNO(String waybillNO) {
		this.waybillNO = waybillNO;
	}
	/**
	 * 流水号
	 */
	public String getSerialNO() {
		return serialNO;
	}
	/**
	 * 流水号
	 */
	public void setSerialNO(String serialNO) {
		this.serialNO = serialNO;
	}
	/**
	 * 定位编号
	 */
	public String getStockPositionNumber() {
		return stockPositionNumber;
	}
	/**
	 * 定位编号
	 */
	public void setStockPositionNumber(String stockPositionNumber) {
		this.stockPositionNumber = stockPositionNumber;
	}
	/**
	 * 扫描时间
	 */
	public Date getScanTime() {
		return scanTime;
	}
	/**
	 * 扫描时间
	 */
	public void setScanTime(Date scanTime) {
		this.scanTime = scanTime;
	}

}
