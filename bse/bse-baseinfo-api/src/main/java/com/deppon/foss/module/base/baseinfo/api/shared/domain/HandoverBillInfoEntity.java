package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 交单管理 基础信息 实体类HandoverBillBasicInfoEntity
 * @author 189284
 *
 */
public class HandoverBillInfoEntity  extends BaseEntity{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;// 主键id                                          
	private String department;//部门信息    
	private String departmentName;//部门名称
	private String startdateOneday;//自动交当日运单开始时间              
	private String enddateOneday;//自动交当日运单结束时间              
	private String startdateTwoday;//自动交第二日运单开始时间            
	private String enddateTwoday;//自动交第二日运单结束时间            
	private String automationMark;//只自动交入库的运单（Y：是，N否）    
	private String artificialMark;//人工可交未入库的运单（Y：是，N否）  
	private Date createTime;//建时间                            
	private Date modifyTime;//修改时间                                  
	private String active;//是否启用                      
	private String createUser;//"""创建人工号"""                  
	private String modifyUser;//"""更新人工号""" 
	private String createDepartment;//创建人部门编码
	private String empName;//创建人名称
	/**
	 * 获取ID
	 * @return  the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置ID
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取 部门信息
	 * @return  the department
	 */
	public String getDepartment() {
		return department;
	}
	/**
	 * 设置部门信息
	 * @param department the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}
	/**
	 * 获取 自动交当日运单开始时间
	 * @return  the startdateOneday
	 */
	public String getStartdateOneday() {
		return startdateOneday;
	}
	/**
	 * 设置自动交当日运单开始时间
	 * @param startdateOneday the startdateOneday to set
	 */
	public void setStartdateOneday(String startdateOneday) {
		this.startdateOneday = startdateOneday;
	}
	/**
	 * 获取 自动交当日运单结束时间
	 * @return  the enddateOneday
	 */
	public String getEnddateOneday() {
		return enddateOneday;
	}
	/**
	 * 设置 自动交当日运单结束时间
	 * @param enddateOneday the enddateOneday to set
	 */
	public void setEnddateOneday(String enddateOneday) {
		this.enddateOneday = enddateOneday;
	}
	/**
	 * 获取 自动交第二日运单开始时间 
	 * @return  the startdateTwoday
	 */
	public String getStartdateTwoday() {
		return startdateTwoday;
	}
	/**
	 * 设置 自动交第二日运单开始时间 
	 * @param startdateTwoday the startdateTwoday to set
	 */
	public void setStartdateTwoday(String startdateTwoday) {
		this.startdateTwoday = startdateTwoday;
	}
	/**
	 * 获取 自动交第二日运单结束时间 
	 * @return  the enddateTwoday
	 */
	public String getEnddateTwoday() {
		return enddateTwoday;
	}
	/**
	 * 设置 自动交第二日运单结束时间 
	 * @param enddateTwoday the enddateTwoday to set
	 */
	public void setEnddateTwoday(String enddateTwoday) {
		this.enddateTwoday = enddateTwoday;
	}
	/**
	 * 获取 只自动交入库的运单（Y：是，N否）
	 * @return  the automationMark
	 */
	public String getAutomationMark() {
		return automationMark;
	}
	/**
	 * 设置 只自动交入库的运单（Y：是，N否）
	 * @param automationMark the automationMark to set
	 */
	public void setAutomationMark(String automationMark) {
		this.automationMark = automationMark;
	}
	/**
	 * 获取  人工可交未入库的运单（Y：是，N否）
	 * @return  the artificialMark
	 */
	public String getArtificialMark() {
		return artificialMark;
	}
	/**
	 * 设置 人工可交未入库的运单（Y：是，N否）
	 * @param artificialMark the artificialMark to set
	 */
	public void setArtificialMark(String artificialMark) {
		this.artificialMark = artificialMark;
	}
	/**
	 * 获取创建时间
	 * @return  the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置创建时间
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * @return  the modifyTime
	 */
	public Date getModifyTime() {
		return modifyTime;
	}
	/**
	 * @param modifyTime the modifyTime to set
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	/**
	 * @return  the active
	 */
	public String getActive() {
		return active;
	}
	/**
	 * @param active the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}
	/**
	 * @return  the createUser
	 */
	public String getCreateUser() {
		return createUser;
	}
	/**
	 * @param createUser the createUser to set
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	/**
	 * @return  the modifyUser
	 */
	public String getModifyUser() {
		return modifyUser;
	}
	/**
	 * @param modifyUser the modifyUser to set
	 */
	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
	/**
	 * 获取 创建人部门编码
	 * @return  the createDepartment
	 */
	public String getCreateDepartment() {
		return createDepartment;
	}
	/**
	 * 设置 创建人部门编码
	 * @param createDepartment the createDepartment to set
	 */
	public void setCreateDepartment(String createDepartment) {
		this.createDepartment = createDepartment;
	}
	/**
	 * 获取  部门名称
	 * @return  the departmentName
	 */
	public String getDepartmentName() {
		return departmentName;
	}
	/**
	 * 设置 部门名称
	 * @param departmentName the departmentName to set
	 */
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	/**
	 * 获取 创建人名称
	 * @return  the empName
	 */
	public String getEmpName() {
		return empName;
	}
	/**
	 * 设置 创建人名称
	 * @param empName the empName to set
	 */
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	
	
}
