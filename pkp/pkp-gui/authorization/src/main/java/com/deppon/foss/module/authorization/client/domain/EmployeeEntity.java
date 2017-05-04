/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-gui
 * 
 * FILE PATH        	: authorization/src/main/java/com/deppon/foss/module/authorization/client/domain/EmployeeEntity.java
 * 
 * FILE NAME        	: EmployeeEntity.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.authorization.client.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 员工
 * 描        述： T_ORG_EMPLOYEE表的实体对象
 * @author 087584-foss-lijun
 * @date 2012-10-29 上午11:57:43
 */

public class EmployeeEntity extends BaseEntity {

    private static final long serialVersionUID = -3967231350437995984L;

    /**
     * 职员姓名
     */
    private String empName;

    /**
     * 姓名拼音
     */
    private String pinyin;

    /**
     * 工号
     */
    private String empCode;

    /**
     * 性别
     */
    private String gender;

    /**
     * 部门
     */
    private DepartmentEntity department;

    /**
     * 部门标杆编码
     */
    private String unifieldCode;

    /**
     * 职位
     */
    private String title;

    /**
     * 职等
     */
    private String degree;

    /**
     * 出生日期
     */
    private Date birthdate;

    /**
     * 状态
     */
    private String status;

    /**
     * 入职日期
     */
    private Date entryDate;

    /**
     * 离职日期
     */
    private Date leaveDate;

    /**
     * 电话
     */
    private String phone;

    /**
     * 身份证号
     */
    private String identityCard;

    /**
     * 手机号码
     */
    private String mobilePhone;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 是否启用
     */
    private String active;

    public String getEmpName() {
	return empName;
    }

    public void setEmpName(String empName) {
	this.empName = empName;
    }

    public String getPinyin() {
	return pinyin;
    }

    public void setPinyin(String pinyin) {
	this.pinyin = pinyin;
    }

    public String getEmpCode() {
	return empCode;
    }

    public void setEmpCode(String empCode) {
	this.empCode = empCode;
    }

    public String getGender() {
	return gender;
    }

    public void setGender(String gender) {
	this.gender = gender;
    }

    public DepartmentEntity getDepartment() {
	return department;
    }

    public void setDepartment(DepartmentEntity department) {
	this.department = department;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public String getDegree() {
	return degree;
    }

    public void setDegree(String degree) {
	this.degree = degree;
    }

    public Date getBirthdate() {
	return birthdate;
    }

    public void setBirthdate(Date birthdate) {
	this.birthdate = birthdate;
    }

    public String getStatus() {
	return status;
    }

    public void setStatus(String status) {
	this.status = status;
    }

    public String getPhone() {
	return phone;
    }

    public void setPhone(String phone) {
	this.phone = phone;
    }

    public String getIdentityCard() {
	return identityCard;
    }

    public void setIdentityCard(String identityCard) {
	this.identityCard = identityCard;
    }

    public Date getEntryDate() {
	return entryDate;
    }

    public void setEntryDate(Date entryDate) {
	this.entryDate = entryDate;
    }

    public Date getLeaveDate() {
	return leaveDate;
    }

    public void setLeaveDate(Date leaveDate) {
	this.leaveDate = leaveDate;
    }

    public String getMobilePhone() {
	return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
	this.mobilePhone = mobilePhone;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getActive() {
	return active;
    }

    public void setActive(String active) {
	this.active = active;
    }

    public String getUnifieldCode() {
	return unifieldCode;
    }

    public void setUnifieldCode(String unifieldCode) {
	this.unifieldCode = unifieldCode;
    }

}