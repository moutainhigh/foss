/*******************************************************************************
 * Copyright 2014 BSE TEAM
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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/CostCenterDeptEntity.java
 * 
 * FILE NAME        	: CostCenterDeptEntity.java
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
 * 成本中心部门Entity
 * 
 * @author foss-WeiXing
 * @date 2014-07-29 上午10:28:02
 */
public class CostCenterDeptEntity extends BaseEntity {

    private static final long serialVersionUID = -3967231350437995984L;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 部门代码
     */
    private String deptCode;

    /**
     * 部门性质名称
     */
    private String typeName;

    /**
     * 部门性质代码
     */
    private String typeCode;

    /**
     * 信息ID
     */
    private String adminId;
    
    /**
     * 标杆编码
     */
    private String simpleCode;

    /**
    * 状态
    */	
    private String status;
    
    /**
     * 是否冻结
     */	
     private String isFreeze;
     
     /**
      * 是否是成本中心
      */	
      private String isCostOrgUnit;
    
    /**
     * 是否启用
     */
    private String active;
    
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getSimpleCode() {
		return simpleCode;
	}

	public void setSimpleCode(String simpleCode) {
		this.simpleCode = simpleCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getIsFreeze() {
		return isFreeze;
	}

	public void setIsFreeze(String isFreeze) {
		this.isFreeze = isFreeze;
	}

	public String getIsCostOrgUnit() {
		return isCostOrgUnit;
	}

	public void setIsCostOrgUnit(String isCostOrgUnit) {
		this.isCostOrgUnit = isCostOrgUnit;
	}

}
