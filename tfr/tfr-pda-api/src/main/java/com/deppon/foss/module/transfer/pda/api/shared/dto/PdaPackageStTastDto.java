package com.deppon.foss.module.transfer.pda.api.shared.dto;

import java.util.Date;

public class PdaPackageStTastDto {
	/**清仓任务编号*/
	private String stTaskNo;
	/**清仓任务ID*/
	private String stTaskId;
	/**包号*/
	private String packageNo;
	/**类(快)*/
	private String type;
	/**运单件数*/
	private int wayBillQiy;
	/**扫描状态*/
	private String scanStatus;
	/**包总重量*/
	private double packageWeight;
	/**包总体积*/
	private double packageVolume;
	/**目的站*/
	private String  arriveOrgName;
	/**入库时间*/
	private Date inStockTime;
	
	public String getStTaskNo() {
		return stTaskNo;
	}
	public void setStTaskNo(String stTaskNo) {
		this.stTaskNo = stTaskNo;
	}
	public String getStTaskId() {
		return stTaskId;
	}
	public void setStTaskId(String stTaskId) {
		this.stTaskId = stTaskId;
	}
	public String getPackageNo() {
		return packageNo;
	}
	public void setPackageNo(String packageNo) {
		this.packageNo = packageNo;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getWayBillQiy() {
		return wayBillQiy;
	}
	public void setWayBillQiy(int wayBillQiy) {
		this.wayBillQiy = wayBillQiy;
	}
	public String getScanStatus() {
		return scanStatus;
	}
	public void setScanStatus(String scanStatus) {
		this.scanStatus = scanStatus;
	}
	public double getPackageWeight() {
		return packageWeight;
	}
	public void setPackageWeight(double packageWeight) {
		this.packageWeight = packageWeight;
	}
	public double getPackageVolume() {
		return packageVolume;
	}
	public void setPackageVolume(double packageVolume) {
		this.packageVolume = packageVolume;
	}
	public String getArriveOrgName() {
		return arriveOrgName;
	}
	public void setArriveOrgName(String arriveOrgName) {
		this.arriveOrgName = arriveOrgName;
	}
	public Date getInStockTime() {
		return inStockTime;
	}
	public void setInStockTime(Date inStockTime) {
		this.inStockTime = inStockTime;
	}
	
}
