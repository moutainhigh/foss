package com.deppon.foss.module.transfer.common.api.shared.dto;

public class NolabelRequestFromQMSDto {
	//操作类型 FIND:认领；DONE:异常货已处理
	private String operateType;
	//无标签运单号
	private String noLabelBillNo;
	//无标签多货流水号
	private String noLabelSerialNo;
	//无标签多货编号
	private String errorCode;
	//原运单号
	private String originalWaybillNo;
	//原流水号
	private String originalSerialNo;
	//操作人工号
	private String operatorCode;
	//操作人姓名
	private String operatorName;
	//操作部门编码，非标杆编码
	private String operateDeptCode;
	//运单类型:ECS FOSS
	private String waybillStyle;
	
	public String getOperateType() {
		return operateType;
	}
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	public String getNoLabelBillNo() {
		return noLabelBillNo;
	}
	public void setNoLabelBillNo(String noLabelBillNo) {
		this.noLabelBillNo = noLabelBillNo;
	}
	public String getNoLabelSerialNo() {
		return noLabelSerialNo;
	}
	public void setNoLabelSerialNo(String noLabelSerialNo) {
		this.noLabelSerialNo = noLabelSerialNo;
	}
	public String getOriginalWaybillNo() {
		return originalWaybillNo;
	}
	public void setOriginalWaybillNo(String originalWaybillNo) {
		this.originalWaybillNo = originalWaybillNo;
	}
	public String getOriginalSerialNo() {
		return originalSerialNo;
	}
	public void setOriginalSerialNo(String originalSerialNo) {
		this.originalSerialNo = originalSerialNo;
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
	public String getOperateDeptCode() {
		return operateDeptCode;
	}
	public void setOperateDeptCode(String operateDeptCode) {
		this.operateDeptCode = operateDeptCode;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getWaybillStyle() {
		return waybillStyle;
	}
	public void setWaybillStyle(String waybillStyle) {
		this.waybillStyle = waybillStyle;
	}
}
