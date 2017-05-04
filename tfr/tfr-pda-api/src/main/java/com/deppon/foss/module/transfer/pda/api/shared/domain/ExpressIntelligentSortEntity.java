package com.deppon.foss.module.transfer.pda.api.shared.domain;

import java.util.Date;
import java.util.List;

public class ExpressIntelligentSortEntity {
 //包号
 private String pacakgeNo;
 //包目的站
 private String destDeptCode;
 //出发部门
 private String orgCode;
 //建包人工号
 private String createUserCode;
 //建包人姓名
 private String createUserName;
 //建包人部门编码
 private String createDeptCode;
 //建包人部门
 private String createDeptName;
 //创建时间
 private Date createTime;
 //结束时间
 private Date finshTime;
 //是否直达包
 private String beThroughPacakge;
 //设备号（格口号）
 private String deviceNo;
 //运单信息
 private List<ExpressIntelligentSortDetailEntity> waybillInfos;
 
 
 
public List<ExpressIntelligentSortDetailEntity> getWaybillInfos() {
	return waybillInfos;
}
public void setWaybillInfos(
		List<ExpressIntelligentSortDetailEntity> waybillInfos) {
	this.waybillInfos = waybillInfos;
}
public String getPacakgeNo() {
	return pacakgeNo;
}
public void setPacakgeNo(String pacakgeNo) {
	this.pacakgeNo = pacakgeNo;
}
public String getDestDeptCode() {
	return destDeptCode;
}
public void setDestDeptCode(String destDeptCode) {
	this.destDeptCode = destDeptCode;
}
public String getCreateUserCode() {
	return createUserCode;
}
public void setCreateUserCode(String createUserCode) {
	this.createUserCode = createUserCode;
}
public String getCreateUserName() {
	return createUserName;
}
public void setCreateUserName(String createUserName) {
	this.createUserName = createUserName;
}
public String getCreateDeptCode() {
	return createDeptCode;
}
public void setCreateDeptCode(String createDeptCode) {
	this.createDeptCode = createDeptCode;
}
public String getCreateDeptName() {
	return createDeptName;
}
public void setCreateDeptName(String createDeptName) {
	this.createDeptName = createDeptName;
}
public Date getCreateTime() {
	return createTime;
}
public void setCreateTime(Date createTime) {
	this.createTime = createTime;
}
public Date getFinshTime() {
	return finshTime;
}
public void setFinshTime(Date finshTime) {
	this.finshTime = finshTime;
}
public String getBeThroughPacakge() {
	return beThroughPacakge;
}
public void setBeThroughPacakge(String beThroughPacakge) {
	this.beThroughPacakge = beThroughPacakge;
}
public String getOrgCode() {
	return orgCode;
}
public void setOrgCode(String orgCode) {
	this.orgCode = orgCode;
}
public String getDeviceNo() {
	return deviceNo;
}
public void setDeviceNo(String deviceNo) {
	this.deviceNo = deviceNo;
}
 
 
 
}
