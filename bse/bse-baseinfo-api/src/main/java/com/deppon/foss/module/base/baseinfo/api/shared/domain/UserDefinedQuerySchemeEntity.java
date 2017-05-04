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
 * PROJECT NAME	: bse-querying
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/querying/shared/domain/UserDefinedQuerySchemeEntity.java
 * 
 * FILE NAME        	: UserDefinedQuerySchemeEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-querying
 * PACKAGE NAME: com.deppon.foss.module.base.querying.shared
 * FILE    NAME: QueryingConstant.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 自定义查询 方案 实体.
 * 
 * @author 073586-FOSS-LIXUEXING
 * @date 2013-01-21 20:51
 */
public class UserDefinedQuerySchemeEntity  extends BaseEntity{
	private static final long serialVersionUID = -2270291827413730136L;
	/**
	 * 方案code
	 */
	private String code;
	/**
	 * 方案名称
	 */
	private String name;
	/**
	 * 方案绑定人
	 */
	private String userCode;
	/**
	 * 是否启用.
	 */
	private String active;
	/**
	 * 是否默认方案.
	 */
	private String defaults;

	/* 以下是getter和setter方法 */

	/**
	 * @return the active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active
	 *            the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return  the defaults
	 */
	public String getDefaults() {
	    return defaults;
	}

	
	/**
	 * @param defaults the defaults to set
	 */
	public void setDefaults(String defaults) {
	    this.defaults = defaults;
	}

	
	/**
	 * @return  the code
	 */
	public String getCode() {
	    return code;
	}

	
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
	    this.code = code;
	}

	
	/**
	 * @return  the userCode
	 */
	public String getUserCode() {
	    return userCode;
	}

	
	/**
	 * @param userCode the userCode to set
	 */
	public void setUserCode(String userCode) {
	    this.userCode = userCode;
	}

}
