package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 
* @ClassName: WaybillDetailEntity 
* @Description: 开单明细-图片开单推送app
* @author dpyuanjb@deppon.com/092039
* @date 2014年10月28日 下午3:16:13 
*
 */
public class WaybillPushMessageEntity implements Serializable{
	
	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = -1185297549344493002L;
	// 运单号
	private String id;
	private String waybillCode;
	private String pushMessage;
	private String pushStatus;
	private Date createTime;
	private String modifyTime;
	private Date billTime;
	private String createUserCode;
	private String createUserName;
	private String createDeptCode;
	private String createDeptName;
	private String modifyUserCode;
	private String active;
	private String phone;//手机号
	private String driverName;//司机姓名
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWaybillCode() {
		return waybillCode;
	}
	public void setWaybillCode(String waybillCode) {
		this.waybillCode = waybillCode;
	}
	public String getPushMessage() {
		return pushMessage;
	}
	public void setPushMessage(String pushMessage) {
		this.pushMessage = pushMessage;
	}
	public String getPushStatus() {
		return pushStatus;
	}
	public void setPushStatus(String pushStatus) {
		this.pushStatus = pushStatus;
	}
	
	public String getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getBillTime() {
		return billTime;
	}
	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}
	public String getCreateUserCode() {
		return createUserCode;
	}
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}
	public String getModifyUserCode() {
		return modifyUserCode;
	}
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	
	
}
