package com.deppon.foss.module.transfer.packaging.api.shared.dto;


import com.deppon.foss.framework.entity.BaseEntity;

@SuppressWarnings("serial")
public class QueryAssistPackedDto extends BaseEntity{

	//运单号
	private String waybillNo;
	
	//包装部门code
	private String packDeptCode;
	
	//开单部门
	private String waybillCreateDept;
	
	//包装供应商
	private String packageSupplierCode;
	
	//包装开始时间
	private String packedBeginDate;
	
	//包装结束时间
	private String packedEndDate;
	
	//审核状态
	private String auditStatus;

	
	//get and set
	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getPackDeptCode() {
		return packDeptCode;
	}

	public void setPackDeptCode(String packDeptCode) {
		this.packDeptCode = packDeptCode;
	}

	public String getWaybillCreateDept() {
		return waybillCreateDept;
	}

	public void setWaybillCreateDept(String waybillCreateDept) {
		this.waybillCreateDept = waybillCreateDept;
	}

	public String getPackageSupplierCode() {
		return packageSupplierCode;
	}

	public void setPackageSupplierCode(String packageSupplierCode) {
		this.packageSupplierCode = packageSupplierCode;
	}

	public String getPackedBeginDate() {
		return packedBeginDate;
	}

	public void setPackedBeginDate(String packedBeginDate) {
		this.packedBeginDate = packedBeginDate;
	}

	public String getPackedEndDate() {
		return packedEndDate;
	}

	public void setPackedEndDate(String packedEndDate) {
		this.packedEndDate = packedEndDate;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	
}
