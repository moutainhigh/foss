package com.deppon.foss.module.transfer.lostwarning.domain;


public class WayBillPkgInfo  {

	// 流水号
	private String flowCode;
	// 包号
	private String packageNumber;
	// 建包部门编码
	private String packDeptCode;
	// 建包部门名称
	private String packDeptName;
	// 解包部门编码
	private String unpackDeptCode;
	// 解包部门名称
	private String unpackDeptName;

	public String getFlowCode() {
		return flowCode;
	}

	public void setFlowCode(String flowCode) {
		this.flowCode = flowCode;
	}

	public String getPackageNumber() {
		return packageNumber;
	}

	public void setPackageNumber(String packageNumber) {
		this.packageNumber = packageNumber;
	}

	public String getPackDeptCode() {
		return packDeptCode;
	}

	public void setPackDeptCode(String packDeptCode) {
		this.packDeptCode = packDeptCode;
	}

	public String getPackDeptName() {
		return packDeptName;
	}

	public void setPackDeptName(String packDeptName) {
		this.packDeptName = packDeptName;
	}

	public String getUnpackDeptCode() {
		return unpackDeptCode;
	}

	public void setUnpackDeptCode(String unpackDeptCode) {
		this.unpackDeptCode = unpackDeptCode;
	}

	public String getUnpackDeptName() {
		return unpackDeptName;
	}

	public void setUnpackDeptName(String unpackDeptName) {
		this.unpackDeptName = unpackDeptName;
	}

}
