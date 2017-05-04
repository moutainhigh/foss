package com.deppon.foss.module.transfer.pda.api.shared.dto;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.pda.api.shared.domain.PDATrayOfflineScanWaybillEntiy;

public class PDATrayOfflineScanDto {
	/**
	 * 任务号
	 */
	private String offlineTaskNo;
	/**离线扫描时间*/
	private Date trayOfflineScanTime;
	/**叉车司机工号*/
	private String forkLiftDriverCode;
	/**叉车司机部门*/
	private String forkLiftDept;
	/**操作类型*/
	private String operatType;
	/**运单票数数*/
	private int waybillQty;
	
	private List<PDATrayOfflineScanWaybillEntiy> waybillEntity;
	
	public int getWaybillQty() {
		return waybillQty;
	}
	public void setWaybillQty(int waybillQty) {
		this.waybillQty = waybillQty;
	}
	
	public List<PDATrayOfflineScanWaybillEntiy> getWaybillEntity() {
		return waybillEntity;
	}
	public void setWaybillEntity(List<PDATrayOfflineScanWaybillEntiy> waybillEntity) {
		this.waybillEntity = waybillEntity;
	}
	public String getOfflineTaskNo() {
		return offlineTaskNo;
	}
	public void setOfflineTaskNo(String offlineTaskNo) {
		this.offlineTaskNo = offlineTaskNo;
	}
	public Date getTrayOfflineScanTime() {
		return trayOfflineScanTime;
	}
	public void setTrayOfflineScanTime(Date trayOfflineScanTime) {
		this.trayOfflineScanTime = trayOfflineScanTime;
	}
	public String getForkLiftDriverCode() {
		return forkLiftDriverCode;
	}
	public void setForkLiftDriverCode(String forkLiftDriverCode) {
		this.forkLiftDriverCode = forkLiftDriverCode;
	}
	public String getForkLiftDept() {
		return forkLiftDept;
	}
	public void setForkLiftDept(String forkLiftDept) {
		this.forkLiftDept = forkLiftDept;
	}
	public String getOperatType() {
		return operatType;
	}
	public void setOperatType(String operatType) {
		this.operatType = operatType;
	}
	
	
}
