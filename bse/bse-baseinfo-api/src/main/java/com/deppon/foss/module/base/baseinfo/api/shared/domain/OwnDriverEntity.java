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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/OwnDriverEntity.java
 * 
 * FILE NAME        	: OwnDriverEntity.java
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
 * 用来存储交互“公司司机”的数据库对应实体：无SUC
 * 
 * @author 100847-foss-GaoPeng
 * @date 2012-10-29 下午4:07:10
 * @since
 * @version
 */
public class OwnDriverEntity extends BaseEntity {
    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 5600944862576475987L;

    /**
     * 司机工号
     */
    private String empCode;

    /**
     * 司机姓名
     */
    private String empName;

    /**
     * 司机电话
     */
    private String empPhone;

    /**
     * 所属部门
     */
    private String orgId;
    
    /**
     * 是否启用
     */
    private String active;
    
    /**
     * 驾照类型
     */
    private String licenseType;
    
    /**
     * 外请车审核状态
     */
    private String status;
    
   
    
    /**
     * 上级车队组织
     */
    private String parentOrgCode;
    
    /**
     * 扩展字段下级子组织列表
     */
    private List<String> subOrgCodeList;
    
  
    private List<String> fleetType;
    
    /**
     * 
     */
   private String waybillFlag;
    
   /**
    * 所属部门名称
    */
   private String orgName;

	 

	public String getOrgName() {
	    return orgName;
    }

    public void setOrgName(String orgName) {
	    this.orgName = orgName;
    }

	public List<String> getFleetType() {
		return fleetType;
	}

	public void setFleetType(List<String> fleetType) {
		this.fleetType = fleetType;
	}

	public List<String> getSubOrgCodeList() {
		return subOrgCodeList;
	}

	public void setSubOrgCodeList(List<String> subOrgCodeList) {
		this.subOrgCodeList = subOrgCodeList;
	}

	public String getParentOrgCode() {
		return parentOrgCode;
	}

	public void setParentOrgCode(String parentOrgCode) {
		this.parentOrgCode = parentOrgCode;
	}

	public String getWaybillFlag() {
		return waybillFlag;
	}

	public void setWaybillFlag(String waybillFlag) {
		this.waybillFlag = waybillFlag;
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
	 * @return empName
	 */
	public String getEmpName() {
		return empName;
	}

	/**
	 * @param  empName  
	 */
	public void setEmpName(String empName) {
		this.empName = empName;
	}

	/**
	 * @return empPhone
	 */
	public String getEmpPhone() {
		return empPhone;
	}

	/**
	 * @param  empPhone  
	 */
	public void setEmpPhone(String empPhone) {
		this.empPhone = empPhone;
	}

	/**
	 * @return orgId
	 */
	public String getOrgId() {
		return orgId;
	}

	/**
	 * @param  orgId  
	 */
	public void setOrgId(String orgId) {
		this.orgId = orgId;
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
	 * @return licenseType
	 */
	public String getLicenseType() {
		return licenseType;
	}

	/**
	 * @param  licenseType  
	 */
	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}

	/**
	 * @return status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param  status  
	 */
	public void setStatus(String status) {
		this.status = status;
	}
}
