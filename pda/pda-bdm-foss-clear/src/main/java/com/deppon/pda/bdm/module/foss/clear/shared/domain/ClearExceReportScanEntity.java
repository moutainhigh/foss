package com.deppon.pda.bdm.module.foss.clear.shared.domain;

import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;

public class ClearExceReportScanEntity extends ScanMsgEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 清仓差异报告扫描编码 
	 */
    private String reportCode;
    /**
     * 清仓差异报告序列号
     */
    private String serialNo;
    
    private String scanFlag;
    private String scanStatus;
    private String taskCode;
    private String isMore;
    
    
    public String getIsMore() {
        return isMore;
    }
    public void setIsMore(String isMore) {
        this.isMore = isMore;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getScanFlag() {
        return scanFlag;
    }
    public void setScanFlag(String scanFlag) {
        this.scanFlag = scanFlag;
    }
    public String getScanStatus() {
        return scanStatus;
    }
    public void setScanStatus(String scanStatus) {
        this.scanStatus = scanStatus;
    }
    public String getTaskCode() {
        return taskCode;
    }
    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }
    public String getReportCode() {
        return reportCode;
    }
    public void setReportCode(String reportCode) {
        this.reportCode = reportCode;
    }
    public String getSerialNo() {
        return serialNo;
    }
    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }
    
	
    
}
