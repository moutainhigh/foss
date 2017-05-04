package com.deppon.foss.module.transfer.pda.api.shared.domain;

import java.io.Serializable;

/**
 * 叉车离线扫描 运单明细
 * */
public class PDATrayOfflineScanWaybillEntiy  implements Serializable{
	
	private static final long serialVersionUID = -3037136839780660393L;
	/**
	 * 运单号
	 * */
	
	private String waybillNo;
	/**
	 * 流水号
	 * */
	private String serialNo;
	
	/**包号**/
	private String packageNo;
	
	
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
	
}
