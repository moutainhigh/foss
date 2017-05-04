package com.deppon.foss.module.transfer.common.api.shared.domain;

import java.util.Date;

public class AutoOverWeightToQMSMainEntity {

	private String repEmpcode;//上报人工号
	private String repEmpName;//上报人姓名
	private String repEmpJob;//上报人职位
	private String repDeptCode;//上报人部门标杆编码
	private String repDeptName;//上报人部门名称
	private String errorCategory; //差错类别                                
	private String errorTypeId;//差错类型                                     
	private String docStandarId;//文件标准                                    
	private String wayBillNum; //运单号
	private String repDivisionCode; //上报人事业部标杆编码
	private String repDivisionName; //上报人事业部
	private String receiveDeptCode; //收货部门标杆编码
	private String receiveDeptName; //收货部门名称
	private Date repTime;	//上报时间;
	public String getRepEmpcode() {
		return repEmpcode;
	}
	public void setRepEmpcode(String repEmpcode) {
		this.repEmpcode = repEmpcode;
	}
	public String getRepEmpName() {
		return repEmpName;
	}
	public void setRepEmpName(String repEmpName) {
		this.repEmpName = repEmpName;
	}
	public String getRepEmpJob() {
		return repEmpJob;
	}
	public void setRepEmpJob(String repEmpJob) {
		this.repEmpJob = repEmpJob;
	}
	public String getRepDeptCode() {
		return repDeptCode;
	}
	public void setRepDeptCode(String repDeptCode) {
		this.repDeptCode = repDeptCode;
	}
	public String getRepDeptName() {
		return repDeptName;
	}
	public void setRepDeptName(String repDeptName) {
		this.repDeptName = repDeptName;
	}
	public String getErrorCategory() {
		return errorCategory;
	}
	public void setErrorCategory(String errorCategory) {
		this.errorCategory = errorCategory;
	}
	public String getErrorTypeId() {
		return errorTypeId;
	}
	public void setErrorTypeId(String errorTypeId) {
		this.errorTypeId = errorTypeId;
	}
	public String getDocStandarId() {
		return docStandarId;
	}
	public void setDocStandarId(String docStandarId) {
		this.docStandarId = docStandarId;
	}
	public String getWayBillNum() {
		return wayBillNum;
	}
	public void setWayBillNum(String wayBillNum) {
		this.wayBillNum = wayBillNum;
	}
	public String getRepDivisionCode() {
		return repDivisionCode;
	}
	public void setRepDivisionCode(String repDivisionCode) {
		this.repDivisionCode = repDivisionCode;
	}
	public String getRepDivisionName() {
		return repDivisionName;
	}
	public void setRepDivisionName(String repDivisionName) {
		this.repDivisionName = repDivisionName;
	}
	public String getReceiveDeptCode() {
		return receiveDeptCode;
	}
	public void setReceiveDeptCode(String receiveDeptCode) {
		this.receiveDeptCode = receiveDeptCode;
	}
	public String getReceiveDeptName() {
		return receiveDeptName;
	}
	public void setReceiveDeptName(String receiveDeptName) {
		this.receiveDeptName = receiveDeptName;
	}
	public Date getRepTime() {
		return repTime;
	}
	public void setRepTime(Date repTime) {
		this.repTime = repTime;
	}
	
}
