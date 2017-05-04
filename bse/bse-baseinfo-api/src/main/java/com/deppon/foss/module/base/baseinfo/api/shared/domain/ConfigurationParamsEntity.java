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
 * PROJECT NAME	: bse-dict-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/dict/api/shared/domain/ConfigurationParamsEntity.java
 * 
 * FILE NAME        	: ConfigurationParamsEntity.java
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
 * 系统配置参数
 * 
 * @author 087584-foss-lijun
 * @date 2012-10-20 下午2:35:52
 */
public class ConfigurationParamsEntity  extends BaseEntity {
    private static final long serialVersionUID = -3967231354244015250L;

    /**
    *配置项代码
    */	
    private String code;

    /**
    *配置项名称
    */	
    private String confName;

    /**
    *配置项值
    */	
    private String confValue;

    /**
    *单位
    */	
    private String unit;

    /**
    *备注
    */	
    private String notes;
    
    /**
    * 数据版本号
    */	
    private Long versionNo;
    
    /**
    *是否启用
    */	
    private String active;

    /**
    * 部门编码
    */	
    private String orgCode;
    
    /**
    * 部门名称，不从数据库中取
    */	
    private String orgName;

    /**
    *配置类型
    */	
    private String confType;

    /**
    *数据类型
    */	
    private String dataType;

    /**
    *虚拟编码
    */	
    private String virtualCode;

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
	 * @return confName
	 */
	public String getConfName() {
		return confName;
	}

	/**
	 * @param  confName  
	 */
	public void setConfName(String confName) {
		this.confName = confName;
	}

	/**
	 * @return confValue
	 */
	public String getConfValue() {
		return confValue;
	}

	/**
	 * @param  confValue  
	 */
	public void setConfValue(String confValue) {
		this.confValue = confValue;
	}

	/**
	 * @return unit
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * @param  unit  
	 */
	public void setUnit(String unit) {
		this.unit = unit;
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
	 * @return orgName
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * @param  orgName  
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
	 * @return confType
	 */
	public String getConfType() {
		return confType;
	}

	/**
	 * @param  confType  
	 */
	public void setConfType(String confType) {
		this.confType = confType;
	}

	/**
	 * @return dataType
	 */
	public String getDataType() {
		return dataType;
	}

	/**
	 * @param  dataType  
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
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
}