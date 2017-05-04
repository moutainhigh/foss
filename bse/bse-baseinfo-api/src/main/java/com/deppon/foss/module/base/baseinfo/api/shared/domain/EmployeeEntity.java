/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/EmployeeEntity.java
 * 
 * FILE NAME        	: EmployeeEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 人员
 * 
 * @author 087584-foss-lijun
 * @date 2012-10-29 上午11:57:45
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
    private OrgAdministrativeInfoEntity department;
    /**
     * 部门名称
     */
    private String orgName;
    
    /**
     * 部门标杆编码
     */
    private String unifieldCode;

    /**
    * 组织编码
    */	
    private String orgCode;
    
    /**
     * 扩展字段父级组织
     */
    private String parentOrgCode;
    /**
     * 扩展字段下级子组织列表
     */
    private List<String> subOrgCodeList;
    
    /**
     * 职位Code
     */
    private String title;
    
    /**
     * 职位
     */
    private String titleName;

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
     * 入职日期（供显示）
     */
    private String enterDate;

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

    /**
     * 查询参数
     */
    private String queryParam;
    /**
     * 配合主数据项目用于创建时间格式转换
     */
    private String createTime;
    /**
     * 配合主数据项目用于修改时间格式转换
     */
    private String modifyTime;
    /**
     * 配合主数据-是否临时人员
     */
    private String isTempEmp;
    /**
     * 
     * 营业网点名字
     * @author 218392 张永雪
     * 
     */
    private String yingYeName;
    
    
    public String getTitleName() {
		return titleName;
	}


	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}


	public String getEnterDate() {
		return enterDate;
	}


	public void setEnterDate(String enterDate) {
		this.enterDate = enterDate;
	}


	public String getOrgName() {
		return orgName;
	}


	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}


	public String getOrgCode() {
        return orgCode;
    }

    
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

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

    public OrgAdministrativeInfoEntity getDepartment() {
	return department;
    }

    public void setDepartment(OrgAdministrativeInfoEntity department) {
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

    
	public String getQueryParam() {
		return queryParam;
	}


	
	public void setQueryParam(String queryParam) {
		this.queryParam = queryParam;
	}


	public void setUnifieldCode(String unifieldCode) {
	this.unifieldCode = unifieldCode;
    }


	public String getParentOrgCode() {
		return parentOrgCode;
	}


	public void setParentOrgCode(String parentOrgCode) {
		this.parentOrgCode = parentOrgCode;
	}


	public List<String> getSubOrgCodeList() {
		return subOrgCodeList;
	}


	public void setSubOrgCodeList(List<String> subOrgCodeList) {
		this.subOrgCodeList = subOrgCodeList;
	}


	/**
	 * 设置营业网点名字
	 * @return
	 */
	public String getYingYeName() {
		return yingYeName;
	}


	/**
	 * 获取营业网点名字
	 * @param yingYeName
	 */
	public void setYingYeName(String yingYeName) {
		this.yingYeName = yingYeName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getIsTempEmp() {
		return isTempEmp;
	}

	public void setIsTempEmp(String isTempEmp) {
		this.isTempEmp = isTempEmp;
	}
	
}
