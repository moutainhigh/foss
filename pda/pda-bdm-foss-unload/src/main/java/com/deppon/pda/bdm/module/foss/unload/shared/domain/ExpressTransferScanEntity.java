package com.deppon.pda.bdm.module.foss.unload.shared.domain;

import java.io.Serializable;

public class ExpressTransferScanEntity implements Serializable{
	
	private static final long serialVersionUID = 2075791909076280396L;
	/**运单号*/
	private String wayBillNo;
	/**流水号*/
	private String serialNo;
	/**包号*/
	private String packageNo;
	/**部门编号*/
	private String orgCode;
	
	public String getWayBillNo() {
		return wayBillNo;
	}
	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getPackageNo() {
		return packageNo;
	}
	public void setPackageNo(String packageNo) {
		this.packageNo = packageNo;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	

}
