package com.deppon.pda.bdm.module.ocb.shared.domain;

import java.io.Serializable;
import java.util.Date;

public class FlowCountBean implements Serializable{

	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = 9191823862984448001L;
	/**
	 * id
	 */
	private String id;
	/**
	 * 工号
	 */
	private String userCode;
	/**
	 * 车牌号
	 */
	private String truckCode;
	/**
	 * 版本号 
	 */
	private String versionCode;
	/**
	 * 司机手机号
	 */
	private String phoneNum;
	/**
	 * 部门编码
	 */
	private String deptCode;
	/**
	 * 流量 单位M
	 */
	private double flow;
	/**
	 * 发送日期
	 */
	private Date sendDate;
	
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getTruckCode() {
		return truckCode;
	}
	public void setTruckCode(String truckCode) {
		this.truckCode = truckCode;
	}
	public String getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public double getFlow() {
		return flow;
	}
	public void setFlow(double flow) {
		this.flow = flow;
	}
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
}
