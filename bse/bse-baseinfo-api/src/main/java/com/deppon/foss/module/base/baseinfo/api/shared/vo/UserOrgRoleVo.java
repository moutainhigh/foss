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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/UserOrgRoleVo.java
 * 
 * FILE NAME        	: UserOrgRoleVo.java
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

import com.deppon.foss.module.base.baseinfo.api.shared.domain.RoleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserOrgRoleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.UserOrgRoleDto;


/**
 * 用户部门角色VO
 * 
 * @author 087584-foss-lijun
 * @date 2012-12-29 下午4:09:16
 */
public class UserOrgRoleVo implements Serializable {
    private static final long serialVersionUID = 8640505034012743142L;
    
    // 用户部门角色DTO列表
    private List<UserOrgRoleDto> userOrgRoleDtoList;

    // 用户部门角色DTO详情
    private UserOrgRoleDto userOrgRoleDto;
    
    /**
     * 下面两个变量，在查询角色列表时使用
     */
    
    // 用户部门角色实体
    private UserOrgRoleEntity userOrgRoleEntity;
    
    // 角色列表信息
    private List<RoleEntity> roleList;

    
    /**
     * 下面是get,set方法
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-16 下午2:20:42
     */

    public List<UserOrgRoleDto> getUserOrgRoleDtoList() {
        return userOrgRoleDtoList;
    }

 
    public void setUserOrgRoleDtoList(List<UserOrgRoleDto> userOrgRoleDtoList) {
        this.userOrgRoleDtoList = userOrgRoleDtoList;
    }

    
    public UserOrgRoleDto getUserOrgRoleDto() {
        return userOrgRoleDto;
    }

    
    public void setUserOrgRoleDto(UserOrgRoleDto userOrgRoleDto) {
        this.userOrgRoleDto = userOrgRoleDto;
    }

    
    public List<RoleEntity> getRoleList() {
        return roleList;
    }

    
    public void setRoleList(List<RoleEntity> roleList) {
        this.roleList = roleList;
    }


    
    public UserOrgRoleEntity getUserOrgRoleEntity() {
        return userOrgRoleEntity;
    }


    
    public void setUserOrgRoleEntity(UserOrgRoleEntity userOrgRoleEntity) {
        this.userOrgRoleEntity = userOrgRoleEntity;
    }


}
