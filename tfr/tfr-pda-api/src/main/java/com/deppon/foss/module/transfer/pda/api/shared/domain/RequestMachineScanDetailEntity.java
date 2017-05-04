package com.deppon.foss.module.transfer.pda.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

public class RequestMachineScanDetailEntity {
	private String waybillNo;//运单号
	
	private String deviceNo;//设备号
	
	private String serialNo;//流水号
	
	private String operatorCode;//操作人工号
	
	private String operationDeptCode;//操作人部门编码
	
	private Date scanTime;//操作时间
	
	private BigDecimal length;//长
	
	private BigDecimal width;//宽
	
	private BigDecimal height;//高
	
	private BigDecimal weight;//重量
	
	private BigDecimal volumn;//体积

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

	public String getOperatorCode() {
		return operatorCode;
	}

	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}


	public String getOperationDeptCode() {
		return operationDeptCode;
	}

	public void setOperationDeptCode(String operationDeptCode) {
		this.operationDeptCode = operationDeptCode;
	}

	public Date getScanTime() {
		return scanTime;
	}

	public void setScanTime(Date scanTime) {
		this.scanTime = scanTime;
	}

	public BigDecimal getLength() {
		return length;
	}

	public void setLength(BigDecimal length) {
		this.length = length;
	}

	public BigDecimal getWidth() {
		return width;
	}

	public void setWidth(BigDecimal width) {
		this.width = width;
	}

	public BigDecimal getHeight() {
		return height;
	}

	public void setHeight(BigDecimal height) {
		this.height = height;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public BigDecimal getVolumn() {
		return volumn;
	}

	public void setVolumn(BigDecimal volumn) {
		this.volumn = volumn;
	}

	public String getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	
	
}
