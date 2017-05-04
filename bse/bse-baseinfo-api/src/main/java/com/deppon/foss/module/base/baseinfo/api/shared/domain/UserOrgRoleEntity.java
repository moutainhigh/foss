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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/UserOrgRoleEntity.java
 * 
 * FILE NAME        	: UserOrgRoleEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 用来存储交互“用户组织角色信息”的数据库对应实体：SUC-41
 * 
 * @author 100847-foss-GaoPeng
 * @date 2012-11-7 下午7:55:19
 * @since
 * @version
 */
public class UserOrgRoleEntity extends BaseEntity {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = -3533675121283972669L;

    /**
     * 虚拟编码
     */
    private String virtualCode;
    
    /**
     * 用户
     */
    private String empCode;

    /**
     * 部门 （原来为部门标杆编码，现改为部门标杆编码）
     */
    private String orgCode;

    /**
    * 部门标杆编码
    */	
    private String orgUnifiedCode;
    
    /**
    * 角色
    */	
    private String roleCode;

    /**
    * 是否启用
    */	
    private String active;

    /**
    * 版本控制号
    */	
    private Long version;

	/**
	 * @return virtualCode
	 */
	public String getVirtualCode() {
		return virtualCode;
	}

	/**
	 * @param  virtualCode  
	 */
	public void setVirtualCode(String virtualCode) {
		this.virtualCode = virtualCode;
	}

	/**
	 * @return empCode
	 */
	public String getEmpCode() {
		return empCode;
	}

	/**
	 * @param  empCode  
	 */
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	/**
	 * @return orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * @param  orgCode  
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

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
	 * @return roleCode
	 */
	public String getRoleCode() {
		return roleCode;
	}

	/**
	 * @param  roleCode  
	 */
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
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
	 * @return version
	 */
	public Long getVersion() {
		return version;
	}

	/**
	 * @param  version  
	 */
	public void setVersion(Long version) {
		this.version = version;
	}
}
