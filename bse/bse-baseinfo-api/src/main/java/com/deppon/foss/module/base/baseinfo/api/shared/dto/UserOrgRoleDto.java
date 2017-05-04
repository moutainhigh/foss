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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/UserOrgRoleDto.java
 * 
 * FILE NAME        	: UserOrgRoleDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.RoleEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * 用户部门解决DTO，用户用户部门角色的查询列表
 * 
 * @author 087584-foss-lijun
 * @date 2012-12-28 下午2:47:52
 * @author 132599-foss-shenweihua
 * @date 2013-05-09 下午4:17:52\
 * 增加人员职位
 */
public class UserOrgRoleDto implements Serializable {

    private static final long serialVersionUID = 12342141L;
    /**
     * 下面数据来自于人员
     */
    
    /**
     * 人员工号/编码
     */
    private String empCode;
    
    /**
     * 人员姓名
     */
    private String empName;
    
    /**
     * 人员职位
     * @return
     */
    private String title;
    
	/**
     * 用户角色权限中的部门标杆编码
     */
    private String orgUnifiedCode;
    /**
     * 所属部门标杆编码
     */
    private String unifiedCode;

    /**
     * 部门名称
     */
    private String orgName;

    /**
     * 人员手机号
     */
    private String phone;

    /**
     * 下面数据来自于部门，用户部门角色
     */

    /**
     * 操作部门标杆编码
     */
    private String operateOrgUnifiedCode;

    /**
     * 操作部门名称
     */
    private String operateOrgName;

    /**
     * 操作部门是否是用户所属部门，是YES=Y,否NO=N
     */
    private String isDefaultOrg;
    
    /**
     * 角色名称，查询时作为参数传入
     */
    private String roleName;
    
    /**
     * 角色列表
     */
    private List<RoleEntity> roleList;
    

    
    
    
    
    
    public String getPhone() {
        return phone;
    }



    
    public void setPhone(String phone) {
        this.phone = phone;
    }



    
    public String getRoleName() {
        return roleName;
    }



    
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }



    public List<RoleEntity> getRoleList() {
        return roleList;
    }


    
    public void setRoleList(List<RoleEntity> roleList) {
        this.roleList = roleList;
    }


    public String getEmpCode() {
        return empCode;
    }

    
    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    
    public String getEmpName() {
        return empName;
    }

    
    public void setEmpName(String empName) {
        this.empName = empName;
    }

    
    public String getOrgUnifiedCode() {
        return orgUnifiedCode;
    }

    
    public void setOrgUnifiedCode(String orgUnifiedCode) {
        this.orgUnifiedCode = orgUnifiedCode;
    }

    
    public String getOrgName() {
        return orgName;
    }

    
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOperateOrgUnifiedCode() {
        return operateOrgUnifiedCode;
    }

    
    public void setOperateOrgUnifiedCode(String operateOrgUnifiedCode) {
        this.operateOrgUnifiedCode = operateOrgUnifiedCode;
    }

    
    public String getOperateOrgName() {
        return operateOrgName;
    }

    
    public void setOperateOrgName(String operateOrgName) {
        this.operateOrgName = operateOrgName;
    }

    
    public String getIsDefaultOrg() {
	this.isDefaultOrg = StringUtils.equals(this.orgUnifiedCode,
		this.operateOrgUnifiedCode) ? FossConstants.YES
		: FossConstants.NO;
        return isDefaultOrg;
    }

    
    public void setIsDefaultOrg(String isDefaultOrg) {
        this.isDefaultOrg = isDefaultOrg;
    }




	public String getUnifiedCode() {
		return unifiedCode;
	}




	public void setUnifiedCode(String unifiedCode) {
		this.unifiedCode = unifiedCode;
	}
	
	/**
	 * 获取人员职位
	 * @return
	 */
    public String getTitle() {
		return title;
	}
    
    /**
	 * 设置人员职位
	 * @return
	 */
	public void setTitle(String title) {
		this.title = title;
	}


    
    
}
