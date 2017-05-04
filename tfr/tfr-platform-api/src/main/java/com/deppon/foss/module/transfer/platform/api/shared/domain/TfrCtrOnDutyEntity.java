package com.deppon.foss.module.transfer.platform.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class TfrCtrOnDutyEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 外场编码
	 */
	private String tfrCtrCode;

	/**
	 * 外场名称
	 */
	private String tfrCtrName;

	/**
	 * 部门编码
	 */
	private String orgCode;

	/**
	 * 部门名称
	 */
	private String orgName;

	/**
	 * 员工编码
	 */
	private String empCode;

	/**
	 * 员工名称
	 */
	private String empName;

	/**
	 * 岗位code
	 */
	private String postCode;

	/**
	 * 岗位名称
	 */
	private String postName;

	/**
	 * 上班所属日期
	 */
	private Date onDutyDate;

	/**
	 * 上班开始时间
	 */
	private Date beginTime;

	/**
	 * 上班结束时间
	 */
	private Date endTime;

	/**
	 * 是否特殊人员
	 */
	private String special;

	/**
	 * 创建人编码
	 */
	private String createUserCode;

	/**
	 * 创建人名称
	 */
	private String createUserName;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改人编码
	 */
	private String modifyUserCode;

	/**
	 * 修改人名称
	 */
	private String modifyUserName;

	/**
	 * 修改时间
	 */
	private Date modifyTime;

	private Date beginDatePart;

	private Date beginTimePart;

	private Date endDatePart;

	private Date endTimePart;

	public String getTfrCtrCode() {
		return tfrCtrCode;
	}

	public void setTfrCtrCode(String tfrCtrCode) {
		this.tfrCtrCode = tfrCtrCode;
	}

	public String getTfrCtrName() {
		return tfrCtrName;
	}

	public void setTfrCtrName(String tfrCtrName) {
		this.tfrCtrName = tfrCtrName;
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

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public Date getOnDutyDate() {
		return onDutyDate;
	}

	public void setOnDutyDate(Date onDutyDate) {
		this.onDutyDate = onDutyDate;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getSpecial() {
		return special;
	}

	public void setSpecial(String special) {
		this.special = special;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getModifyUserCode() {
		return modifyUserCode;
	}

	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	public String getModifyUserName() {
		return modifyUserName;
	}

	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Date getBeginDatePart() {
		return beginDatePart;
	}

	public void setBeginDatePart(Date beginDatePart) {
		this.beginDatePart = beginDatePart;
	}

	public Date getBeginTimePart() {
		return beginTimePart;
	}

	public void setBeginTimePart(Date beginTimePart) {
		this.beginTimePart = beginTimePart;
	}

	public Date getEndDatePart() {
		return endDatePart;
	}

	public void setEndDatePart(Date endDatePart) {
		this.endDatePart = endDatePart;
	}

	public Date getEndTimePart() {
		return endTimePart;
	}

	public void setEndTimePart(Date endTimePart) {
		this.endTimePart = endTimePart;
	}

}
