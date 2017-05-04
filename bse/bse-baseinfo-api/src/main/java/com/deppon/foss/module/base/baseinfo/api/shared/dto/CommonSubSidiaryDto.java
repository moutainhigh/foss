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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/CommonSubSidiaryDto.java
 * 
 * FILE NAME        	: CommonSubSidiaryDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;

/**
 * 查询子公司信息Dto
 * @author 101911-foss-zhouChunlai
 * @date 2013-1-12 上午9:24:54
 */
public class CommonSubSidiaryDto implements Serializable {
 
	private static final long serialVersionUID = -1282807311447660654L;
	
	private String id;
	/**
	 * 前台查询参数
	 */
	private String queryParam;
	
	/**
	 * 子公司名称
	 */
	private String name;
	/**
	 * 子公司编码
	 */
	private String code;
	
	/**
	 * 子公司全称
	 */
	private String fullName;
	
	/**
	 * 是否有效
	 */
	private String active;
	
	
	
	public String getActive() {
		return active;
	}




	
	
	public String getId() {
		return id;
	}





	
	public void setId(String id) {
		this.id = id;
	}





	public void setActive(String active) {
		this.active = active;
	}




	public String getFullName() {
		return fullName;
	}



	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}



	public String getName() {
		return name;
	}


	
	public void setName(String name) {
		this.name = name;
	}


	
	public String getCode() {
		return code;
	}


	
	public void setCode(String code) {
		this.code = code;
	}


	public String getQueryParam() {
		return queryParam;
	}

	
	public void setQueryParam(String queryParam) {
		this.queryParam = queryParam;
	}
	
	
}
