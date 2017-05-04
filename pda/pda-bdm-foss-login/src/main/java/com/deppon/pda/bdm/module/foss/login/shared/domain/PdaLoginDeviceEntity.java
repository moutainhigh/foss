package com.deppon.pda.bdm.module.foss.login.shared.domain;

import java.util.Date;
import com.deppon.pda.bdm.module.core.shared.domain.DomainEntity;


/**
 * PDA设备登陆信息
 * @author wenwuneng
 * @date 2013-07-31
 * @version 1.0
 * @since
 */
public class PdaLoginDeviceEntity extends DomainEntity {
	/** 
	 * @fields serialVersionUID 
	 */ 
	
	private static final long serialVersionUID = 1L;
	/**
	 *登陆设备号
	 */
	private String dvcCode;
	/**
	 * 用户工号
	 */
	private String userCode;
	
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 部门编码
	 */
	private String deptCode;
	/**
	 * 部门名称
	 */
	private String deptName;
	/**
	 * 是否是最后一次登陆，是：0，否 1
	 */
	private String status;
	/**
	 * 登陆时间
	 */
	private Date loginDate;
	private Date loginOutDate;
	public String getDvcCode() {
		return dvcCode;
	}
	public void setDvcCode(String dvcCode) {
		this.dvcCode = dvcCode;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}
	public Date getLoginOutDate() {
		return loginOutDate;
	}
	public void setLoginOutDate(Date loginOutDate) {
		this.loginOutDate = loginOutDate;
	}
	
}