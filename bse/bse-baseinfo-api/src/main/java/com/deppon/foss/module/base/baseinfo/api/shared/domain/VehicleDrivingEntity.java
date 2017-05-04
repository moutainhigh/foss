package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class VehicleDrivingEntity extends BaseEntity {

	/**
	 * 长途车队信息实体类
	 * @author 323136
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	/**
	 * 长途车队名称
	 */
	private String longHaulFleetName; 
	
	/**
	 * 长途车队编码
	 */
	private String longHaulFleetCode;
	
	/**
	 * 下辖部门名称
	 */
	private String departmentName;
	
	/**
	 *  下辖部门编码 DEPARTMENT_CODE
	 */
	private String departmentCode;
	
	/**
	 * 行车编码
	 */
	private String trafficCode;
	
	/**
	 * 创建车队时间
	 */
	private Date createTime;
	
	/**
	 * 修改时间
	 */
	private Date modifyTime;
	
	/**
	 *创建人 
	 */
	private String createUser;
	
	/**
	 * 创建人名称
	 */
	private String createUserName;
	
	/**
	 * 修改人
	 */
	private String modifyUser;
	
	/**
	 * 修改人名称
	 */
	private String modifyUserName;
	
	/**
	 * 是否有效
	 */
	private String active;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLongHaulFleetName() {
		return longHaulFleetName;
	}

	public void setLongHaulFleetName(String longHaulFleetName) {
		this.longHaulFleetName = longHaulFleetName;
	}

	public String getLongHaulFleetCode() {
		return longHaulFleetCode;
	}

	public void setLongHaulFleetCode(String longHaulFleetCode) {
		this.longHaulFleetCode = longHaulFleetCode;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public String getTrafficCode() {
		return trafficCode;
	}

	public void setTrafficCode(String trafficCode) {
		this.trafficCode = trafficCode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public String getModifyUserName() {
		return modifyUserName;
	}

	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
