package com.deppon.foss.module.transfer.unload.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class PDAUnloadDiffReportDetailLogEntity extends BaseEntity {


	private static final long serialVersionUID = 1L;
	//差异报告编号
	private String reportCode;
	//运单号
	private String waybillNo;
	//流水号
	private String serialNo;
	//PDA扫描时间
	private Date scanTime;
	//PDANO
	private String pdaNo;
	
	/**
	 * @return the reportCode
	 */
	public String getReportCode() {
		return reportCode;
	}
	/**
	 * @param reportCode the reportCode to set
	 */
	public void setReportCode(String reportCode) {
		this.reportCode = reportCode;
	}
	/**
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}
	/**
	 * @param waybillNo the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	/**
	 * @return the serialNo
	 */
	public String getSerialNo() {
		return serialNo;
	}
	/**
	 * @param serialNo the serialNo to set
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	/**
	 * @return the scanTime
	 */
	public Date getScanTime() {
		return scanTime;
	}
	/**
	 * @param scanTime the scanTime to set
	 */
	public void setScanTime(Date scanTime) {
		this.scanTime = scanTime;
	}
	
	/**
	 * @return the pdaNo
	 */
	public String getPdaNo() {
		return pdaNo;
	}
	/**
	 * @param pdaNo the pdaNo to set
	 */
	public void setPdaNo(String pdaNo) {
		this.pdaNo = pdaNo;
	}

}
