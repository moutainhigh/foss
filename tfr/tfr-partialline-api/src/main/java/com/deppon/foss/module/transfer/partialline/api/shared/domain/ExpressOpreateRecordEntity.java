package com.deppon.foss.module.transfer.partialline.api.shared.domain;

import java.util.Date;


/**
 * @author 218427
 * @date 2015年3月10日
 */
public class ExpressOpreateRecordEntity {
     private String id;
	/**操作城市**/
	private String wayBillNo;
	/**操作城市**/
	private String operationCity;
	/**操作类型**/
	private String operationType;
	/**代理轨迹更新时间**/
	private Date updateTime;
	/**类型**/
	private String transferType;
	/**人工操作时间**/
	private Date manualOperationTime;
	/**备注**/
	private String notes;
	/**操作人工号**/
	private String  operatorCode;
	/**操作人姓名**/
	private String  operatorName;
	
	public String getOperationCity() {
		return operationCity;
	}
	public void setOperationCity(String operationCity) {
		this.operationCity = operationCity;
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
	public String getWayBillNo() {
		return wayBillNo;
	}
	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTransferType() {
		return transferType;
	}
	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}
	public String getOperatorCode() {
		return operatorCode;
	}
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	
	
}
