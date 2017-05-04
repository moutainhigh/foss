package com.deppon.pda.bdm.module.foss.clear.shared.domain;

import java.io.Serializable;

public class DifferDetailEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	//包号
    private String packageNo;
	//运单号
	private String waybillNo;
	//流水号
	private String serialNo;
	//处理状态
	private String handleStatus;
	//目的站
	private String destStation;
	public String getPackageNo() {
		return packageNo;
	}
	public void setPackageNo(String packageNo) {
		this.packageNo = packageNo;
	}
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
	public String getHandleStatus() {
		return handleStatus;
	}
	public void setHandleStatus(String handleStatus) {
		this.handleStatus = handleStatus;
	}
	public String getDestStation() {
		return destStation;
	}
	public void setDestStation(String destStation) {
		this.destStation = destStation;
	}
	
	
	
}
