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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/CommonBigZoneDto.java
 * 
 * FILE NAME        	: CommonBigZoneDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.BigZoneEntity;


/**
 * 集中接送货大区Dto
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2013-1-12 上午9:24:54
 */
public class CommonBigZoneDto extends BigZoneEntity {
 
	private static final long serialVersionUID = -1282807311447660654L;
	
	private String queryParam;

	
	public String getQueryParam() {
		return queryParam;
	}

	
	public void setQueryParam(String queryParam) {
		this.queryParam = queryParam;
	}
	
	
}
