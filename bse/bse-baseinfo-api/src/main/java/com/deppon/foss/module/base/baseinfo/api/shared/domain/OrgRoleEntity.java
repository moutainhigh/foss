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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/OrgRoleEntity.java
 * 
 * FILE NAME        	: OrgRoleEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;


/**
 * 用户部门角色 中的 部门角色
 * 
 * @author 087584-foss-lijun
 * @date 2012-11-21 下午5:17:08
 */
public class OrgRoleEntity extends BaseEntity{
	
	/**
	 * 序列化ID
	 */
    private static final long serialVersionUID = -3467231350438160812L;
    
    /**
     * 部门标杆编码
     */
    private String orgUnifiedCode;
    
    /**
     * 部门名称
     */
    private String isDefaultOrg;
    
    /**
     * 部门是否是默认部门
     */
    private String orgName;
    
    /**
     * 角色列表信息
     */
    private List<RoleEntity> roleList;

	/**
	 * @return orgUnifiedCode
	 */
	public String getOrgUnifiedCode() {
		return orgUnifiedCode;
	}

	/**
	 * @param  orgUnifiedCode  
	 */
	public void setOrgUnifiedCode(String orgUnifiedCode) {
		this.orgUnifiedCode = orgUnifiedCode;
	}

	/**
	 * @return isDefaultOrg
	 */
	public String getIsDefaultOrg() {
		return isDefaultOrg;
	}

	/**
	 * @param  isDefaultOrg  
	 */
	public void setIsDefaultOrg(String isDefaultOrg) {
		this.isDefaultOrg = isDefaultOrg;
	}

	/**
	 * @return orgName
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * @param  orgName  
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
	 * @return roleList
	 */
	public List<RoleEntity> getRoleList() {
		return roleList;
	}

	/**
	 * @param  roleList  
	 */
	public void setRoleList(List<RoleEntity> roleList) {
		this.roleList = roleList;
	}
}