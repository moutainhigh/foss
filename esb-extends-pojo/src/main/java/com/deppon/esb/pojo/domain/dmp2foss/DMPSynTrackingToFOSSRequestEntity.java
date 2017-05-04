package com.deppon.esb.pojo.domain.dmp2foss;

import java.io.Serializable;
import java.util.Date;


/**
 * 零担大件家装轨迹请求DMP参数实体
 *@author 205109-foss-zenghaibin
 *@date 2015-04-29 上午 9:49：30 
 ***/
public class DMPSynTrackingToFOSSRequestEntity implements Serializable{
	private static final long serialVersionUID = 3031910894252017200L;

	/**运单号**/
	private String wayBillNo;
	//当前状态
	private String currentStatus;
	//操作人员
	private String operateName;
	//操作类型名称
	private String operateTypeName;
	//操作时间(发生时间)
	private Date operateTime;
	//操作件数
	private Integer operateNumber;
	//备注
	private String notes;
	
	//DMP同步数据到FOSS的待同步轨迹表

	//发生城市;
	private String operateCity;
	//部门编码
	private String orgCode;
	//操作 部门名称
	private String orgName;
	public String getWayBillNo() {
		return wayBillNo;
	}
	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}
	public String getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
	public String getOperateName() {
		return operateName;
	}
	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}
	public String getOperateTypeName() {
		return operateTypeName;
	}
	public void setOperateTypeName(String operateTypeName) {
		this.operateTypeName = operateTypeName;
	}
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	public Integer getOperateNumber() {
		return operateNumber;
	}
	public void setOperateNumber(Integer operateNumber) {
		this.operateNumber = operateNumber;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getOperateCity() {
		return operateCity;
	}
	public void setOperateCity(String operateCity) {
		this.operateCity = operateCity;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	
		
}
