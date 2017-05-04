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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/UserDeptDataEntity.java
 * 
 * FILE NAME        	: UserDeptDataEntity.java
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
 * 
 * 用户部门信息
 * 
 * @author 何波
 * @date 2013-2-23 下午1:07:26
 * @since
 * @version
 */
public class UserDeptDataEntity extends BaseEntity {
	
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -3967231350569569593L;
	
	/**
	 * 用户
	 */
	private EmployeeEntity userEntity;

	/**
	 * 部门
	 */
	private OrgAdministrativeInfoEntity dept;

	/**
	 * 版本号
	 */
	private Long versionNo;

	/**
	 * 是否选择子组织  何波 添加 2013-2-22
	 */
    private boolean subOrg;
    
	/**
	 * 将subOrg转换成true - Y  false - N
	 */
    private String  subOrgFlag;
	/**
	 * getter
	 */
	public OrgAdministrativeInfoEntity getDept() {
		return dept;
	}

	/**
	 * setter
	 */
	public void setDept(OrgAdministrativeInfoEntity dept) {
		this.dept = dept;
	}

	/**
	 *getter
	 */
	public Long getVersionNo() {
		return versionNo;
	}

	/**
	 *setter
	 */
	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}

	/**
	 *getter
	 */
	public EmployeeEntity getUserEntity() {
		return userEntity;
	}

	/**
	 *setter
	 */
	public void setUserEntity(EmployeeEntity userEntity) {
		this.userEntity = userEntity;
	}

	/**
	 * @return subOrg
	 */
	public boolean isSubOrg() {
		return subOrg;
	}

	/**
	 * @param  subOrg  
	 */
	public void setSubOrg(boolean subOrg) {
		this.subOrg = subOrg;
	}

	/**
	 * @return subOrgFlag
	 */
	public String getSubOrgFlag() {
		return subOrgFlag;
	}

	/**
	 * @param  subOrgFlag  
	 */
	public void setSubOrgFlag(String subOrgFlag) {
		this.subOrgFlag = subOrgFlag;
	}
}
