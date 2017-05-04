package com.deppon.foss.module.transfer.unload.api.shared.domain;


import com.deppon.foss.framework.entity.BaseEntity;

public class PDAUnloaddiffReportLogEntity extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//差异报告编号
	private String reportCode;
	//PDA处理状态
	private String status;
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
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
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
