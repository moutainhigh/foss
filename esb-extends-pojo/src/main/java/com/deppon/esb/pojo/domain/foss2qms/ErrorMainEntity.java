package com.deppon.esb.pojo.domain.foss2qms;

import java.io.Serializable;
public class ErrorMainEntity implements Serializable { 
	private static final long serialVersionUID = -9021959415392159800L;
	//"del": "2",   // 固定值
	 private String del;
	 //"docStandarId": 3703,   // 根据FOSS净空判断是使用哪个文件标准(到时候我会把ID给你)
    private String docStandarId;
    // "errorCategory": "1",   // 固定值
    private String errorCategory;
    // "errorTypeId": "L201503250051",  // 固定值
    private String errorTypeId;
    //"lastAvaibleFBTime": "",			
    private String lastAvaibleFBTime;
    //"namespace": "com.deppon.qms.module.error.ltl.domain.LDErrSubNoWaybillEntity",  // 固定值
    private String namespace;
    //"needFeedback": "2", // 固定值
    private String needFeedback;
    // "repDeptCode": "DP13505",  // 上报人部门标杆码   (客户端传值)
    private String repDeptCode;
    // "repDeptName": "快递客户服务部", // 上报人部门   (客户端传值)
    private String repDeptName;
    // "repEmpJob": "快递客户服务部总监",  //上报人岗位  (客户端传值)
    private String repEmpJob;
    // "repEmpName": "曲建华 ",  // 上报人姓名 (客户端传值)
    private String repEmpName;
    // "repEmpcode": "054055",  // 上报人工号  (客户端传值)
    private String repEmpcode;
    // "repSource": "2",    // 固定值
    private String repSource;
    // "repSystem": "FOSS",   // 上报系统
    private String repSystem;
    // "repTime": "2016-07-09 17:12:13",  //上报时间
    private String repTime;
    //  "wayBillType": "-1"   // 固定值}
    private String wayBillType;
	public String getDel() {
		return del;
	}
	public void setDel(String del) {
		this.del = del;
	}
	public String getDocStandarId() {
		return docStandarId;
	}
	public void setDocStandarId(String docStandarId) {
		this.docStandarId = docStandarId;
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
	public String getLastAvaibleFBTime() {
		return lastAvaibleFBTime;
	}
	public void setLastAvaibleFBTime(String lastAvaibleFBTime) {
		this.lastAvaibleFBTime = lastAvaibleFBTime;
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
	public String getRepEmpJob() {
		return repEmpJob;
	}
	public void setRepEmpJob(String repEmpJob) {
		this.repEmpJob = repEmpJob;
	}
	public String getRepEmpName() {
		return repEmpName;
	}
	public void setRepEmpName(String repEmpName) {
		this.repEmpName = repEmpName;
	}
	public String getRepEmpcode() {
		return repEmpcode;
	}
	public void setRepEmpcode(String repEmpcode) {
		this.repEmpcode = repEmpcode;
	}
	public String getRepSource() {
		return repSource;
	}
	public void setRepSource(String repSource) {
		this.repSource = repSource;
	}
	public String getRepSystem() {
		return repSystem;
	}
	public void setRepSystem(String repSystem) {
		this.repSystem = repSystem;
	}
	public String getRepTime() {
		return repTime;
	}
	public void setRepTime(String repTime) {
		this.repTime = repTime;
	}
	public String getWayBillType() {
		return wayBillType;
	}
	public void setWayBillType(String wayBillType) {
		this.wayBillType = wayBillType;
	}
    
}