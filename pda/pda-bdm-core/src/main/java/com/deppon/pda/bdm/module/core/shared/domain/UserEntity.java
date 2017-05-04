package com.deppon.pda.bdm.module.core.shared.domain;

import java.io.Serializable;
/**
 * 
  * @ClassName UserEntity 
  * @Description 用户实体 
  * @author xujun cometzb@126.com 
  * @date 2012-12-26
 */
public class UserEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1433499558575326386L;

	/**
	 * 流水号
	 */
	private String id;
	
	/**
	 * 员工工号
	 */
	private String empCode;
	
	/**
	 * 用户密码
	 */
	private String password;
	
	/**
	 * 是否理货员
	 */
	private String isCarrier;
	
	/**
	 * 是否清仓员
	 */
	private String isClearer;
	
	/**
	 * 是否管理员
	 */
	private String isAdmin;
	
	/**
	 * 组织ID
	 */
	private String deptId;
	
	/**
	 * 员工姓名
	 */
	private String userName;
	
	/**
	 * 用户类型
	 */
	private String userType;
	
	/**
	 * 操作标志
	 */
	private String operFlag;
	/**
	 * 变化时间
	 */
	private String updTime; 
	
	/**
	 * 是否激活
	 */
	private String isActive;
	
	/**
	 * 生效时间
	 */
	private String activeTime;
	
	/**
	 * 版本号
	 */
	private String version;

	/**
	 * getter and setter
	 * 
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIsCarrier() {
		return isCarrier;
	}

	public void setIsCarrier(String isCarrier) {
		this.isCarrier = isCarrier;
	}

	public String getIsClearer() {
		return isClearer;
	}

	public void setIsClearer(String isClearer) {
		this.isClearer = isClearer;
	}

	public String getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	

	public String getOperFlag() {
		return operFlag;
	}

	public void setOperFlag(String operFlag) {
		this.operFlag = operFlag;
	}

	public String getUpdTime() {
		return updTime;
	}

	public void setUpdTime(String updTime) {
		this.updTime = updTime;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(String activeTime) {
		this.activeTime = activeTime;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	
	
}