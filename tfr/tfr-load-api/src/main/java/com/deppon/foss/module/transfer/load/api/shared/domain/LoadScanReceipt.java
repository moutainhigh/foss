package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.io.Serializable;

public class LoadScanReceipt implements Serializable{
	/**
	 * 
	 */
    private static final long serialVersionUID = -7041409115747970589L;
	
	private String taskCode;//任务号
    private String wayBillNo;//运单号
    private String serialNo;//流水号
    private Integer isSCan;//扫描或撤销扫描（1：扫描，0：撤销扫描）
    private Integer isFlag;//是否成功（1：成功，0：失败）
    private String errorMsg;//错误消息 errorMsg;//错误消息
    private String id;
    
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

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

    public Integer getIsSCan() {
        return isSCan;
    }

    public void setIsSCan(Integer isSCan) {
        this.isSCan = isSCan;
    }

    public Integer getIsFlag() {
        return isFlag;
    }

    public void setIsFlag(Integer isFlag) {
        this.isFlag = isFlag;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
