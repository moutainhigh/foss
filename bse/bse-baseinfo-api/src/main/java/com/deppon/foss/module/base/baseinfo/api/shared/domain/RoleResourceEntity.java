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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/RoleResourceEntity.java
 * 
 * FILE NAME        	: RoleResourceEntity.java
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
 * 角色权限entity对象
 * 
 * @author 087584-foss-lijun
 * @date 2012-11-4 下午2:41:47
 */
public class RoleResourceEntity  extends BaseEntity {
	
    /**
     * 序列化ID
     */	
    private static final long serialVersionUID = -3967231350569569593L;

    /**
     * 数据版本号
     */	
    private Long versionNo;

    /**
    *虚拟编码
    */	
    private String virtualCode;
    
    /**
    *角色
    */	
    private String roleCode;

    /**
    *权限
    */	
    private String resourceCode;
    
    /**
    *角色名称
    */	
    private String roleName;

    /**
    *权限名称
    */	
    private String resourceName;

    /**
    *是否启用
    */	
    private String active;
    
    /**
     * 权限类别
     */
    private String belongSystemType;
    
    /**
     * 权限级别
     */
    private String resType;
    
    
	
	public String getResType() {
		return resType;
	}


	
	public void setResType(String resType) {
		this.resType = resType;
	}


	/**
	 * @return versionNo
	 */
	public Long getVersionNo() {
		return versionNo;
	}

	
	public String getBelongSystemType() {
		return belongSystemType;
	}

	
	public void setBelongSystemType(String belongSystemType) {
		this.belongSystemType = belongSystemType;
	}

	/**
	 * @param  versionNo  
	 */
	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}

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
	 * @return resourceCode
	 */
	public String getResourceCode() {
		return resourceCode;
	}

	/**
	 * @param  resourceCode  
	 */
	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}

	/**
	 * @return roleName
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param  roleName  
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * @return resourceName
	 */
	public String getResourceName() {
		return resourceName;
	}

	/**
	 * @param  resourceName  
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
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

}
