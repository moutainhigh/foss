package com.deppon.pda.bdm.module.foss.clear.shared.domain;

import java.io.Serializable;
import java.util.Date;

public class PackageStTastDto implements Serializable {
	/**  
	 * @Fields serialVersionUID : TODO 
	 */
	private static final long serialVersionUID = 1L;
	/**清仓任务编号*/
	private String taskCode;
	/**清仓任务ID*/
	private String taskId;
	/**包号*/
	private String packageNo;
	/**类(快)*/
	private String type;
	/**运单件数*/
	private int piece;
	/**扫描状态*/
	private String scanStatus;
	/**包总重量*/
	private double  packageWeight;
	/**包总体积*/
	private double  packageVolume;
	/**目的站*/
	private String  arriveOrgName;
	/**入库时间*/
	private Date inStockTime;
	
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
	
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public int getPiece() {
		return piece;
	}
	public void setPiece(int piece) {
		this.piece = piece;
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
	public String getScanStatus() {
		return scanStatus;
	}
	public void setScanStatus(String scanStatus) {
		this.scanStatus = scanStatus;
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
