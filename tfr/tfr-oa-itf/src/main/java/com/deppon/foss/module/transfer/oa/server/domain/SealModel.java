package com.deppon.foss.module.transfer.oa.server.domain;

/**
 * @title: SealModel
 * @description：零担封签差错接口实体
 * @author： 崔兴民
 * @date： 2016-11-1 14:30:25
 */
public class SealModel {
	// 交接单/配载单号
	private String wayBillNum;
	// 车牌号
	private String licensePlateNumber;

	// 出发部门名称
	private String setOutDeptName;
	// 出发部门编码
	private String setOutDeptCode;

	// 到达部门名称
	private String arriveDeptName;
	// 到达部门编码
	private String arriveDeptCode;

	// 交接时间
	private String transferTime;

	public String getWayBillNum() {
		return wayBillNum;
	}

	public void setWayBillNum(String wayBillNum) {
		this.wayBillNum = wayBillNum;
	}

	public String getLicensePlateNumber() {
		return licensePlateNumber;
	}

	public void setLicensePlateNumber(String licensePlateNumber) {
		this.licensePlateNumber = licensePlateNumber;
	}

	public String getSetOutDeptName() {
		return setOutDeptName;
	}

	public void setSetOutDeptName(String setOutDeptName) {
		this.setOutDeptName = setOutDeptName;
	}

	public String getSetOutDeptCode() {
		return setOutDeptCode;
	}

	public void setSetOutDeptCode(String setOutDeptCode) {
		this.setOutDeptCode = setOutDeptCode;
	}

	public String getArriveDeptName() {
		return arriveDeptName;
	}

	public void setArriveDeptName(String arriveDeptName) {
		this.arriveDeptName = arriveDeptName;
	}

	public String getArriveDeptCode() {
		return arriveDeptCode;
	}

	public void setArriveDeptCode(String arriveDeptCode) {
		this.arriveDeptCode = arriveDeptCode;
	}

	public String getTransferTime() {
		return transferTime;
	}

	public void setTransferTime(String transferTime) {
		this.transferTime = transferTime;
	}

}
