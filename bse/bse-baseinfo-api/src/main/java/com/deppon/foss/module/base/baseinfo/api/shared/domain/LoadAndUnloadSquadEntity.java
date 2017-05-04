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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/LoadAndUnloadSquadEntity.java
 * 
 * FILE NAME        	: LoadAndUnloadSquadEntity.java
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
 * 装卸车小队
 * 
 * @author 087584-foss-lijun
 * @date 2012-10-20 下午2:35:52
 */
public class LoadAndUnloadSquadEntity  extends BaseEntity {
	
	/**
	 * 序列化ID
	 */
    private static final long serialVersionUID = -3967231351501405405L;

    /**
    *装卸车小队名称
    */	
    private String name;

    /**
    *装卸车小队编码
    */	
    private String code;

    /**
    *上级部门
    */	
    private String parentOrgCode;
    
    /**
    * 上级部门名称
    */	
    private String parentOrgName;
    
    /**
    * 理货业务类型
    */	
    private String arrangeBizType;

    /**
     * 理货员
     */
    private List<PorterEntity> porters;

    /**
    *是否启用
    */	
    private String active;

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
	 * @return parentOrgName
	 */
	public String getParentOrgName() {
		return parentOrgName;
	}

	/**
	 * @param  parentOrgName  
	 */
	public void setParentOrgName(String parentOrgName) {
		this.parentOrgName = parentOrgName;
	}

	/**
	 * @return arrangeBizType
	 */
	public String getArrangeBizType() {
		return arrangeBizType;
	}

	/**
	 * @param  arrangeBizType  
	 */
	public void setArrangeBizType(String arrangeBizType) {
		this.arrangeBizType = arrangeBizType;
	}

	/**
	 * @return porters
	 */
	public List<PorterEntity> getPorters() {
		return porters;
	}

	/**
	 * @param  porters  
	 */
	public void setPorters(List<PorterEntity> porters) {
		this.porters = porters;
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