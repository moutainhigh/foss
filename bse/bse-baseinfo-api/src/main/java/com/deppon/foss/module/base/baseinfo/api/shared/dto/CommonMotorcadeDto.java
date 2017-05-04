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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/CommonMotorcadeDto.java
 * 
 * FILE NAME        	: CommonMotorcadeDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity;

/**
 * 车队Dto
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2013-1-11 下午4:31:16
 */
public class CommonMotorcadeDto extends MotorcadeEntity {

	private static final long serialVersionUID = -2433194620242714605L;

	/**
	 * 查询参数
	 */
	private String queryParam;
	/**
	 * 查询该组织下包含的顶级车队
	 */
	private String topFleetOrgCode;
	/**
	 * 查询全网顶级车队标识
	 */
	private String isFullFleetOrgFlag;
	
	private List<String> motorcadeCodes;

	
	/**
	 * 递归该组织下所有组织
	 */
	private List<String> loopOrgCodes;
	/**
	 * 所有下级组织编码
	 */
	private List<String> subOrgList;
	
	



	public List<String> getSubOrgList() {
		return subOrgList;
	}


	
	public void setSubOrgList(List<String> subOrgList) {
		this.subOrgList = subOrgList;
	}




	public List<String> getLoopOrgCodes() {
		return loopOrgCodes;
	}



	public void setLoopOrgCodes(List<String> loopOrgCodes) {
		this.loopOrgCodes = loopOrgCodes;
	}



	public List<String> getMotorcadeCodes() {
		return motorcadeCodes;
	}

	public void setMotorcadeCodes(List<String> motorcadeCodes) {
		this.motorcadeCodes = motorcadeCodes;
	}

	public String getQueryParam() {
		return queryParam;
	}

	public void setQueryParam(String queryParam) {
		this.queryParam = queryParam;
	}

	public String getTopFleetOrgCode() {
		return topFleetOrgCode;
	}

	public void setTopFleetOrgCode(String topFleetOrgCode) {
		this.topFleetOrgCode = topFleetOrgCode;
	}

	public String getIsFullFleetOrgFlag() {
		return isFullFleetOrgFlag;
	}

	public void setIsFullFleetOrgFlag(String isFullFleetOrgFlag) {
		this.isFullFleetOrgFlag = isFullFleetOrgFlag;
	}

}
