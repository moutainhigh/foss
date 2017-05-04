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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/PorterEntity.java
 * 
 * FILE NAME        	: PorterEntity.java
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
 * 理货员
 * 
 * @author 087584-foss-lijun
 * @date 2012-10-20 下午2:35:52
 */
public class PorterEntity  extends BaseEntity {
	
	/**
	 * 序列ID
	 */
    private static final long serialVersionUID = -3967231351518443810L;

    /**
    *工号
    */	
    private String empCode;

    /**
    *姓名
    */	
    private String empName;

    /**
    *拼音
    */	
    private String pinyin;

    /**
    *类型
    */	
    private String type;

    /**
    *装卸车小队
    */	
    private String parentOrgCode;

    /**
    *是否启用
    */	
    private String active;

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
	 * @return pinyin
	 */
	public String getPinyin() {
		return pinyin;
	}

	/**
	 * @param  pinyin  
	 */
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	/**
	 * @return type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param  type  
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return parentOrgCode
	 */
	public String getParentOrgCode() {
		return parentOrgCode;
	}

	/**
	 * @param  parentOrgCode  
	 */
	public void setParentOrgCode(String parentOrgCode) {
		this.parentOrgCode = parentOrgCode;
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