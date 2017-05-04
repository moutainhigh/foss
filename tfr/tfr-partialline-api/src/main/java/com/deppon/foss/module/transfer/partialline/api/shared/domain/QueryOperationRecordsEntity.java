package com.deppon.foss.module.transfer.partialline.api.shared.domain;

import java.util.Date;

public class QueryOperationRecordsEntity {
   
	/**操作城市**/
	private String wayBillNo;
	/**操作类型**/
	private String operationType;
	/**代理轨迹更新时间**/
	private Date updateTime;
	/**人工操作时间**/
	private Date manualOperationTime;
	/**备注**/
	private String notes;
	public String getWayBillNo() {
		return wayBillNo;
	}
	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getManualOperationTime() {
		return manualOperationTime;
	}
	public void setManualOperationTime(Date manualOperationTime) {
		this.manualOperationTime = manualOperationTime;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	
}
