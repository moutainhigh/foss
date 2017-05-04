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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/CommonReightRouteLineCondition.java
 * 
 * FILE NAME        	: CommonReightRouteLineCondition.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo-api
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.api.shared.dto
 * FILE    NAME: CommonReightRouteLineDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.api.shared.dto;

/**
 * 线路线段查询dto
 * 
 * @author 078823-foss-panGuangJun
 * @date 2012-12-7 下午2:53:06
 */
public class CommonReightRouteLineCondition {

	// 是否有效
	private String active;

	// 达到站名称
	private String destName;

	// 到达站编码
	private String destCode;

	// 始发站名称
	private String ogirName;

	// 始发站编码
	private String orgCode;

	/**
	 * getter
	 */
	public String getActive() {
		return active;
	}

	/**
	 * setter
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * getter
	 */
	public String getDestName() {
		return destName;
	}

	/**
	 * setter
	 */
	public void setDestName(String destName) {
		this.destName = destName;
	}

	/**
	 * getter
	 */
	public String getOgirName() {
		return ogirName;
	}

	/**
	 * setter
	 */
	public void setOgirName(String ogirName) {
		this.ogirName = ogirName;
	}

	public String getDestCode() {
		return destCode;
	}

	public void setDestCode(String destCode) {
		this.destCode = destCode;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

}
