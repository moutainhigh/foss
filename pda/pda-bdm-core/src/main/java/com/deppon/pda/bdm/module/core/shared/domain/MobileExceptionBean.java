package com.deppon.pda.bdm.module.core.shared.domain;

import java.io.Serializable;
import java.util.Date;

public class MobileExceptionBean implements Serializable  {

	/**  
	 * @Fields serialVersionUID : TODO 
	 */
	private static final long serialVersionUID = -6647436232903747823L;
	
	private String uuid;
	/**
	 * 工号
	 */
	private String usercode; 
	/**
	 * 司机
	 */
	private String truckCode; 
	/**
	 * 手机号
	 */
	private String mobilephone; 
	/**
	 * 手机时间
	 */
	private Date mobileTime;  
	/**
	 * 异常信息
	 */
	private String exception;
	/**
	 * 服务端插入时间
	 */
	private Date creatTime;
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public String getTruckCode() {
		return truckCode;
	}
	public void setTruckCode(String truckCode) {
		this.truckCode = truckCode;
	}
	public String getMobilephone() {
		return mobilephone;
	}
	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	public Date getMobileTime() {
		return mobileTime;
	}
	public void setMobileTime(Date mobileTime) {
		this.mobileTime = mobileTime;
	}
	public Date getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}
	
	
}
