package com.deppon.foss.module.transfer.common.api.shared.domain;

import java.io.Serializable;

public class QmsErrorMainEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	//差错编号
	private String errorId;
	//上报人工号
	private String repEmpcode;
	//上报人姓名
	private String repEmpName;
	//上报人职位
	private String repEmpJob;
	//上报人部门标杆编码
	private String repDeptCode;
	//上报人部门名称
	private String repDeptName;
	//差错类别（数据字典代码：errorCategory）
	private String errorCategory;
	private String errorCategoryVal;
	//差错类型id
	private String errorTypeId;
	//差错类型名称
	private String errorTypeName;
	//文件标准id
	private long docStandarId;
	//文件标准名称
	private String docStandarName;
	//差错状态（数据字典代码：errStatus）
	private String errorStatus;
	//上报时间
	private String repTime;
	//运单号 
	private String wayBillNum;
	//上报人事业部标杆编码
	private String repDivisionCode;
	private String repDivisionName;
	//收货部门标杆编码
	private String receiveDeptCode;
	//收货部门名称 
	private String receiveDeptName;
	//最后的反馈有效时间
	private String lastAvaibleFBTime;
	//是否已删除（数据字典代码：yesorno）
	private String del;

	private String namespace;
	private String needFeedback;
	public String getErrorId() {
		return errorId;
	}
	public void setErrorId(String errorId) {
		this.errorId = errorId;
	}
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
	public String getErrorCategoryVal() {
		return errorCategoryVal;
	}
	public void setErrorCategoryVal(String errorCategoryVal) {
		this.errorCategoryVal = errorCategoryVal;
	}
	public String getErrorTypeId() {
		return errorTypeId;
	}
	public void setErrorTypeId(String errorTypeId) {
		this.errorTypeId = errorTypeId;
	}
	public String getErrorTypeName() {
		return errorTypeName;
	}
	public void setErrorTypeName(String errorTypeName) {
		this.errorTypeName = errorTypeName;
	}
	public long getDocStandarId() {
		return docStandarId;
	}
	public void setDocStandarId(long docStandarId) {
		this.docStandarId = docStandarId;
	}
	public String getDocStandarName() {
		return docStandarName;
	}
	public void setDocStandarName(String docStandarName) {
		this.docStandarName = docStandarName;
	}
	public String getErrorStatus() {
		return errorStatus;
	}
	public void setErrorStatus(String errorStatus) {
		this.errorStatus = errorStatus;
	}
	public String getRepTime() {
		return repTime;
	}
	public void setRepTime(String repTime) {
		this.repTime = repTime;
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
	public String getLastAvaibleFBTime() {
		return lastAvaibleFBTime;
	}
	public void setLastAvaibleFBTime(String lastAvaibleFBTime) {
		this.lastAvaibleFBTime = lastAvaibleFBTime;
	}
	public String getDel() {
		return del;
	}
	public void setDel(String del) {
		this.del = del;
	}
	public String getNamespace() {
		return namespace;
	}
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	public String getNeedFeedback() {
		return needFeedback;
	}
	public void setNeedFeedback(String needFeedback) {
		this.needFeedback = needFeedback;
	}
	
	public String toString(){
		String returnValue = "差错编号："+errorId+",上报人工号:"+repEmpcode
				+",上报人姓名:"+repEmpName+",上报人工号:"+repEmpcode
				+",上报人职位:"+repEmpJob+",上报人工号:"+repDeptCode
				+",上报人部门标杆编码:"+repEmpcode+",上报人部门名称:"+repDeptName
		+",差错类别:"+errorCategory+",errorCategoryVal:"+errorCategoryVal
		+",差错类型id:"+errorTypeId+",差错类型名称:"+errorTypeName
		+",文件标准id:"+docStandarId+",文件标准名称:"+docStandarName
		+",差错状态:"+errorStatus+",上报时间:"+repTime
		+",运单号 :"+wayBillNum+",上报人事业部标杆编码:"+repDivisionCode
		+",上报人事业部标杆 :"+repDivisionName+",收货部门标杆编码:"+receiveDeptCode
		+",收货部门名称:"+receiveDeptName+",最后的反馈有效时间:"+lastAvaibleFBTime
		+",是否已删除 :"+del;
		return returnValue;
	}
	

}
