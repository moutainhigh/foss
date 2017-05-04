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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/ResourceVo.java
 * 
 * FILE NAME        	: ResourceVo.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ResourceEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.RoleResourceEntity;

/**
 * 权限VO
 * 
 * @author 087584-foss-lijun
 * @date 2012-12-29 下午4:05:33
 */
public class ResourceVo implements Serializable {

    private static final long serialVersionUID = 8640505034012743242L;

    /**
     * 角色实体List
     */
    private List<ResourceEntity> resourceEntityList;

    /**
     * 权限详情
     */
    private ResourceEntity resourceEntityDetail;

    /**
     * 角色权限实体List
     */
    private List<RoleResourceEntity> roleResourceEntityList;

    private String roles;

    private String fullPath;
    
    private Set<String> allFullPath;
    
    private String currRoleCode;
    
    private long resourceNum;
    
    private Boolean isAdd;
    
    
    /**
     * 下面是get,set方法
     * 
     */
    
    public String getCurrRoleCode() {
        return currRoleCode;
    }

    public long getResourceNum() {
        return resourceNum;
    }

    public void setResourceNum(long resourceNum) {
        this.resourceNum = resourceNum;
    }

    public Boolean getIsAdd() {
        return isAdd;
    }
    
    public void setIsAdd(Boolean isAdd) {
        this.isAdd = isAdd;
    }

    public String getRoles() {
        return roles;
    }


    
    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getFullPath() {
        return fullPath;
    }
    
    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public void setCurrRoleCode(String currRoleCode) {
        this.currRoleCode = currRoleCode;
    }

    public List<ResourceEntity> getResourceEntityList() {
	return resourceEntityList;
    }

    public void setResourceEntityList(List<ResourceEntity> resourceEntityList) {
	this.resourceEntityList = resourceEntityList;
    }

    public ResourceEntity getResourceEntityDetail() {
	return resourceEntityDetail;
    }

    public void setResourceEntityDetail(ResourceEntity resourceEntityDetail) {
	this.resourceEntityDetail = resourceEntityDetail;
    }

    public List<RoleResourceEntity> getRoleResourceEntityList() {
	return roleResourceEntityList;
    }

    public void setRoleResourceEntityList(
	    List<RoleResourceEntity> roleResourceEntityList) {
	this.roleResourceEntityList = roleResourceEntityList;
    }

	public Set<String> getAllFullPath() {
		return allFullPath;
	}

	public void setAllFullPath(Set<String> allFullPath) {
		this.allFullPath = allFullPath;
	}



}
