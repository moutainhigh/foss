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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/RoleVo.java
 * 
 * FILE NAME        	: RoleVo.java
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

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ResourceConflictEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.RoleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.RoleResourceEntity;

/**
 * 角色VO
 * 
 * @author 087584-foss-lijun
 * @date 2012-12-29 下午4:05:52
 */
public class RoleVo implements Serializable {
    private static final long serialVersionUID = 8640505034012243242L;

    /**
     * 角色实体List
     */
    private List<RoleEntity> roleEntityList;
    
    /**
     * 权限详情
     */
    private RoleEntity roleEntityDetail;
    

    /**
     * 角色包含的角色权限实体List
     */
    private List<RoleResourceEntity> roleResourceEntityList;

    private String resourceCodes;
    
    private String deleteResourceCodes;
    //权限互斥实体
    private ResourceConflictEntity resourceConflict;
    
    //当前角色
    private  String currRoleCode;
    //权限互斥是否验证通过
    private  boolean validate;
    
    //用户权限冲突提示信息
    private String  userResourceConflictInfo;
    /**
     * 下面是get,set方法：
     */
    
    
    public String getResourceCodes() {
        return resourceCodes;
    }

    
    
    public String getDeleteResourceCodes() {
        return deleteResourceCodes;
    }


    
    public void setDeleteResourceCodes(String deleteResourceCodes) {
        this.deleteResourceCodes = deleteResourceCodes;
    }


    public void setResourceCodes(String resourceCodes) {
        this.resourceCodes = resourceCodes;
    }

    
    public List<RoleEntity> getRoleEntityList() {
        return roleEntityList;
    }
    
    public void setRoleEntityList(List<RoleEntity> roleEntityList) {
        this.roleEntityList = roleEntityList;
    }

    
    
    public List<RoleResourceEntity> getRoleResourceEntityList() {
        return roleResourceEntityList;
    }
    
    public void setRoleResourceEntityList(
    	List<RoleResourceEntity> roleResourceEntityList) {
        this.roleResourceEntityList = roleResourceEntityList;
    }
    
    public RoleEntity getRoleEntityDetail() {
        return roleEntityDetail;
    }

    
    public void setRoleEntityDetail(RoleEntity roleEntityDetail) {
        this.roleEntityDetail = roleEntityDetail;
    }



	public ResourceConflictEntity getResourceConflict() {
		return resourceConflict;
	}



	public void setResourceConflict(ResourceConflictEntity resourceConflict) {
		this.resourceConflict = resourceConflict;
	}



	public String getCurrRoleCode() {
		return currRoleCode;
	}



	public void setCurrRoleCode(String currRoleCode) {
		this.currRoleCode = currRoleCode;
	}



	public boolean isValidate() {
		return validate;
	}



	public void setValidate(boolean validate) {
		this.validate = validate;
	}



	public String getUserResourceConflictInfo() {
		return userResourceConflictInfo;
	}



	public void setUserResourceConflictInfo(String userResourceConflictInfo) {
		this.userResourceConflictInfo = userResourceConflictInfo;
	}

    
    
}
