package com.deppon.foss.module.transfer.common.api.shared.domain;

import java.io.Serializable;
import java.util.Date;

public class CreateErrorLogEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String ID;
	//类型
	private String bussinessType;
	//错误信息
	private String errorInfo;
	//业务id或no
	private String bussinessNo;
	//源表
	private String sortTable;
	//创建时间
	private Date createTime;
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getBussinessType() {
		return bussinessType;
	}
	public void setBussinessType(String bussinessType) {
		this.bussinessType = bussinessType;
	}
	public String getErrorInfo() {
		return errorInfo;
	}
	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}
	public String getBussinessNo() {
		return bussinessNo;
	}
	public void setBussinessNo(String bussinessNo) {
		this.bussinessNo = bussinessNo;
	}
	public String getSortTable() {
		return sortTable;
	}
	public void setSortTable(String sortTable) {
		this.sortTable = sortTable;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	

}
