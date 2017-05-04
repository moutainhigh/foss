package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author hehaisu
 * @date 2015-2-7 上午10:53:50
 */
public class WaybillPictureSendSmsEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1317401798810648581L;
	/**
	 * 主键
	 */
	private String id;
	private String jobId;
	private String waybillNo;
	/**
	 * 短信内容
	 */
	private String smsMessage;
	private String createUserCode; 
	private String createUserName;
	private String createOrgCode;
	private String createOrgName;
	private Date createTime;
	private String phone;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getSmsMessage() {
		return smsMessage;
	}
	public void setSmsMessage(String smsMessage) {
		this.smsMessage = smsMessage;
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
	public String getCreateOrgCode() {
		return createOrgCode;
	}
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}
	public String getCreateOrgName() {
		return createOrgName;
	}
	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
