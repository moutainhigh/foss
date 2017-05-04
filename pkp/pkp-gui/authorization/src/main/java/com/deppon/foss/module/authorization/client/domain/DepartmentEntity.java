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
 * FILE PATH        	: authorization/src/main/java/com/deppon/foss/module/authorization/client/domain/DepartmentEntity.java
 * 
 * FILE NAME        	: DepartmentEntity.java
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
 * 部门
 * 描        述： T_ORG_DEPARMENT部门表的实体对象
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-yangtong,date:2012-10-12 上午9:37:11,content:TODO </p>
 * @author dp-yangtong
 * @date 2012-10-12 上午9:37:11
 * @since
 * @version
 */
 
public class DepartmentEntity extends BaseEntity {

	private static final long serialVersionUID = -8090529007475424877L;
	//部门编号
	private String deptCode;
   //部门名称
	private String deptName;
    //部门负责人
	private String principal;
   //联系电话
	private String phone;
   //传真
	private String fax;
    //上级部门实体
	private DepartmentEntity parentCode;
    //所属子公司
	private String companyName;
	//邮编号码
	private String zipCode;
	//部门地址
	private String address;
	//状态
	private Boolean status;
	//启用日期
	private Date validDate;
	//作废日期
	private Date invalidDate;
	//显示顺序
	private Integer displayOrder;
	//部门层级
	private Byte deptLevel;
	//是否叶子节点
	private Boolean leaf;
	//部门描述
	private String deptDesc;
	//部门序列
	private String deptSeq;

	
	public String getDeptCode() {
		return this.deptCode;
	}
		 	
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
		
	public String getDeptName() {
		return this.deptName;
	}
		 	
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
		
	public String getPrincipal() {
		return this.principal;
	}
		 	
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
		
	public String getPhone() {
		return this.phone;
	}
		 	
	public void setPhone(String phone) {
		this.phone = phone;
	}
		
	public String getFax() {
		return this.fax;
	}
		 	
	public void setFax(String fax) {
		this.fax = fax;
	}
		
	public DepartmentEntity getParentCode() {
		return this.parentCode;
	}
		 	
	public void setParentCode(DepartmentEntity parentCode) {
		this.parentCode = parentCode;
	}
		
	public String getCompanyName() {
		return this.companyName;
	}
		 	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
		
	public String getZipCode() {
		return this.zipCode;
	}
		 	
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
		
	public String getAddress() {
		return this.address;
	}
		 	
	public void setAddress(String address) {
		this.address = address;
	}
		
	public Boolean getStatus() {
		return this.status;
	}
		 	
	public void setStatus(Boolean status) {
		this.status = status;
	}
		
	public Date getValidDate() {
		return this.validDate;
	}
		 	
	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}
		
	public Date getInvalidDate() {
		return this.invalidDate;
	}
		 	
	public void setInvalidDate(Date invalidDate) {
		this.invalidDate = invalidDate;
	}
		
	public Integer getDisplayOrder() {
		return this.displayOrder;
	}
		 	
	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}
		
	public Byte getDeptLevel() {
		return this.deptLevel;
	}
		 	
	public void setDeptLevel(Byte deptLevel) {
		this.deptLevel = deptLevel;
	}
	
	public String getDeptDesc() {
		return this.deptDesc;
	}
		 	
	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}
		
	public String getDeptSeq() {
		return this.deptSeq;
	}
		 	
	public void setDeptSeq(String deptSeq) {
		this.deptSeq = deptSeq;
	}

	public Boolean getLeaf() {
		return leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}
		
	
}