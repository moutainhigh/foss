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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/RoleEntity.java
 * 
 * FILE NAME        	: RoleEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.Collection;
import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.framework.entity.IRole;

/**
 * 角色entity对象
 * 
 * @author 087584-foss-lijun
 * @date 2012-11-4 下午2:49:47
 */
public class RoleEntity extends BaseEntity implements IRole {

    /**
     * 序列化ID
     */
    private static final long serialVersionUID = -3967231350438328890L;

    /**
    * 数据版本号
    */	
    private Long versionNo;

    /**
    * 角色名称
    */
    private String name;

    /**
    * 角色编码
    */
    private String code;

    /**
     * 角色描述
     */
    private String notes;

    /**
     * 是否启用
     */
    private String active;

    /**
     * 功能对象ID
     */
    private Collection<String> resIds;
    
    /**
     * 
     */
    private List<RoleResourceEntity> roleResourceEntityList;

	/**
	 * @return resIds
	 */
    @Override
    public Collection<String> getFunctionIds() {
	return this.resIds;
    }
    
	/**
	 * @param  value  
	 */
    public void setFunctionIds(Collection<String> value) {
    	this.resIds = value;
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
	 * @return resIds
	 */
	public Collection<String> getResIds() {
		return resIds;
	}

	/**
	 * @param  resIds  
	 */
	public void setResIds(Collection<String> resIds) {
		this.resIds = resIds;
	}

	/**
	 * @return roleResourceEntityList
	 */
	public List<RoleResourceEntity> getRoleResourceEntityList() {
		return roleResourceEntityList;
	}

	/**
	 * @param  roleResourceEntityList  
	 */
	public void setRoleResourceEntityList(
			List<RoleResourceEntity> roleResourceEntityList) {
		this.roleResourceEntityList = roleResourceEntityList;
	}
}