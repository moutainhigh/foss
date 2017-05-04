package com.deppon.foss.module.pickup.waybill.shared.domain;






public class WaybillProInfo{
	 //流水号
    private String srialNo;
    //操作人编码
    private String operateUserCode;
    //操作人名称
    private String operateUserName;
    //操作部门编码 
    private String operateOrgCode;
    //操作部门名称
    private String operateOrgName;
    //操作原因 
    private String operateReason;
    //操作时间
    private String operateTime;
	public String getSrialNo() {
		return srialNo;
	}
	public void setSrialNo(String srialNo) {
		this.srialNo = srialNo;
	}
	public String getOperateUserCode() {
		return operateUserCode;
	}
	public void setOperateUserCode(String operateUserCode) {
		this.operateUserCode = operateUserCode;
	}
	public String getOperateUserName() {
		return operateUserName;
	}
	public void setOperateUserName(String operateUserName) {
		this.operateUserName = operateUserName;
	}
	public String getOperateOrgCode() {
		return operateOrgCode;
	}
	public void setOperateOrgCode(String operateOrgCode) {
		this.operateOrgCode = operateOrgCode;
	}
	public String getOperateOrgName() {
		return operateOrgName;
	}
	public void setOperateOrgName(String operateOrgName) {
		this.operateOrgName = operateOrgName;
	}
	public String getOperateReason() {
		return operateReason;
	}
	public void setOperateReason(String operateReason) {
		this.operateReason = operateReason;
	}
	public String getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}
	
}
