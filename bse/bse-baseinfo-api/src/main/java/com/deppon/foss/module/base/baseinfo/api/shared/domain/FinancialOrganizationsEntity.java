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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/FinancialOrganizationsEntity.java
 * 
 * FILE NAME        	: FinancialOrganizationsEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 财务组织
 * 
 * @author 087584-foss-lijun
 * @date 2012-10-20 下午2:35:52
 */
public class FinancialOrganizationsEntity extends BaseEntity {

	/**
	 * 序列化ID
	 */
    private static final long serialVersionUID = -3967231350890263156L;

    /**
     * 财务组织名称
     */
    private String name;

    /**
     * 财务组织编码
     */
    private String code;

    /**
     * 上级组织编码
     */
    private String parentOrgCode;
    
    /**
     * 上级组织名称
     */
    private String parentOrgName;

    /**
     * 是否成本中心
     */
    private String costCenter;

    /**
     * 是否子公司
     */
    private String subSidiary;

    /**
     * 全称
     */
    private String fullName;

    /**
     * 银行账号开户名
     */
    private String bankAccountName;

    /**
     * 银行账号
     */
    private String bankAccount;

    /**
     * 银行账号开户支行
     */
    private String bankAccountSubbranch;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date modifyTime;

    /**
     * 是否启用
     */
    private String active;

    /**
     * 创建人
     */
    private String createUserCode;

    /**
     * 更新人
     */
    private String modifyUserCode;

    /**
     * 备注
     */
    private String notes;
    
    /**
     * 全路径
     */
    private String fullPath;
    
    /**
     * 是否是叶子结点
     */
    private String isLeaf;
    
    /**
     * 子公司编码
     */
    private String subCompanyCode;
    
    /**
     * 子公司名称
     */
    private String subCompanyName;
    
    /**
    * 财务组织标杆编码
    */	
    private String finOrgBenchmarkCode;

    /**
    * 上级财务组织标杆编码
    */	
    private String parentFinOrgBenchmarkCode;

    /**
    * 是否已封存
    */	
    private String isMothBall;

    /**
    * 助记码
    */	
    private String sysOrgCode;

    /**
    * UUMS财务组织ID
    */	
    private String finOrgId;
    
    /**
    * 数据版本号
    */	
    private Long versionNo;

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param  name  
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param  code  
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return parentOrgCode
	 */
	public String getParentOrgCode() {
		return parentOrgCode;
	}

	/**
	 * @param  parentOrgCode  
	 */
	public void setParentOrgCode(String parentOrgCode) {
		this.parentOrgCode = parentOrgCode;
	}

	/**
	 * @return parentOrgName
	 */
	public String getParentOrgName() {
		return parentOrgName;
	}

	/**
	 * @param  parentOrgName  
	 */
	public void setParentOrgName(String parentOrgName) {
		this.parentOrgName = parentOrgName;
	}

	/**
	 * @return costCenter
	 */
	public String getCostCenter() {
		return costCenter;
	}

	/**
	 * @param  costCenter  
	 */
	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	/**
	 * @return subSidiary
	 */
	public String getSubSidiary() {
		return subSidiary;
	}

	/**
	 * @param  subSidiary  
	 */
	public void setSubSidiary(String subSidiary) {
		this.subSidiary = subSidiary;
	}

	/**
	 * @return fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @param  fullName  
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * @return bankAccountName
	 */
	public String getBankAccountName() {
		return bankAccountName;
	}

	/**
	 * @param  bankAccountName  
	 */
	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	/**
	 * @return bankAccount
	 */
	public String getBankAccount() {
		return bankAccount;
	}

	/**
	 * @param  bankAccount  
	 */
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	/**
	 * @return bankAccountSubbranch
	 */
	public String getBankAccountSubbranch() {
		return bankAccountSubbranch;
	}

	/**
	 * @param  bankAccountSubbranch  
	 */
	public void setBankAccountSubbranch(String bankAccountSubbranch) {
		this.bankAccountSubbranch = bankAccountSubbranch;
	}

	/**
	 * @return createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param  createTime  
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return modifyTime
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * @param  modifyTime  
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * @return active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param  active  
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * @return createUserCode
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}

	/**
	 * @param  createUserCode  
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	/**
	 * @return modifyUserCode
	 */
	public String getModifyUserCode() {
		return modifyUserCode;
	}

	/**
	 * @param  modifyUserCode  
	 */
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	/**
	 * @return notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param  notes  
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * @return fullPath
	 */
	public String getFullPath() {
		return fullPath;
	}

	/**
	 * @param  fullPath  
	 */
	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	/**
	 * @return isLeaf
	 */
	public String getIsLeaf() {
		return isLeaf;
	}

	/**
	 * @param  isLeaf  
	 */
	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}

	/**
	 * @return subCompanyCode
	 */
	public String getSubCompanyCode() {
		return subCompanyCode;
	}

	/**
	 * @param  subCompanyCode  
	 */
	public void setSubCompanyCode(String subCompanyCode) {
		this.subCompanyCode = subCompanyCode;
	}

	/**
	 * @return subCompanyName
	 */
	public String getSubCompanyName() {
		return subCompanyName;
	}

	/**
	 * @param  subCompanyName  
	 */
	public void setSubCompanyName(String subCompanyName) {
		this.subCompanyName = subCompanyName;
	}

	/**
	 * @return finOrgBenchmarkCode
	 */
	public String getFinOrgBenchmarkCode() {
		return finOrgBenchmarkCode;
	}

	/**
	 * @param  finOrgBenchmarkCode  
	 */
	public void setFinOrgBenchmarkCode(String finOrgBenchmarkCode) {
		this.finOrgBenchmarkCode = finOrgBenchmarkCode;
	}

	/**
	 * @return parentFinOrgBenchmarkCode
	 */
	public String getParentFinOrgBenchmarkCode() {
		return parentFinOrgBenchmarkCode;
	}

	/**
	 * @param  parentFinOrgBenchmarkCode  
	 */
	public void setParentFinOrgBenchmarkCode(String parentFinOrgBenchmarkCode) {
		this.parentFinOrgBenchmarkCode = parentFinOrgBenchmarkCode;
	}

	/**
	 * @return isMothBall
	 */
	public String getIsMothBall() {
		return isMothBall;
	}

	/**
	 * @param  isMothBall  
	 */
	public void setIsMothBall(String isMothBall) {
		this.isMothBall = isMothBall;
	}

	/**
	 * @return sysOrgCode
	 */
	public String getSysOrgCode() {
		return sysOrgCode;
	}

	/**
	 * @param  sysOrgCode  
	 */
	public void setSysOrgCode(String sysOrgCode) {
		this.sysOrgCode = sysOrgCode;
	}

	/**
	 * @return finOrgId
	 */
	public String getFinOrgId() {
		return finOrgId;
	}

	/**
	 * @param  finOrgId  
	 */
	public void setFinOrgId(String finOrgId) {
		this.finOrgId = finOrgId;
	}

	/**
	 * @return versionNo
	 */
	public Long getVersionNo() {
		return versionNo;
	}

	/**
	 * @param  versionNo  
	 */
	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}
}