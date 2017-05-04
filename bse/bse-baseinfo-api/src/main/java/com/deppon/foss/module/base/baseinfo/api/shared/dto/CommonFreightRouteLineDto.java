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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/CommonFreightRouteLineDto.java
 * 
 * FILE NAME        	: CommonFreightRouteLineDto.java
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
 * FILE    NAME: CommonFreightRouteLineDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteLineEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;

/**
 * 线路线段查询dto
 * @author 078823-foss-panGuangJun
 * @date 2012-12-7 下午1:40:14
 */
public class CommonFreightRouteLineDto {
	//线路线段
	private FreightRouteLineEntity freightRouteLineEntity;
	//出发点
	private OrgAdministrativeInfoEntity origOrg;
	//到达点
	private OrgAdministrativeInfoEntity destOrg;
	/**
	 *getter
	 */
	public FreightRouteLineEntity getFreightRouteLineEntity() {
		return freightRouteLineEntity;
	}
	/**
	 *setter
	 */
	public void setFreightRouteLineEntity(
			FreightRouteLineEntity freightRouteLineEntity) {
		this.freightRouteLineEntity = freightRouteLineEntity;
	}
	/**
	 *getter
	 */
	public OrgAdministrativeInfoEntity getOrigOrg() {
		return origOrg;
	}
	/**
	 *setter
	 */
	public void setOrigOrg(OrgAdministrativeInfoEntity origOrg) {
		this.origOrg = origOrg;
	}
	/**
	 *getter
	 */
	public OrgAdministrativeInfoEntity getDestOrg() {
		return destOrg;
	}
	/**
	 *setter
	 */
	public void setDestOrg(OrgAdministrativeInfoEntity destOrg) {
		this.destOrg = destOrg;
	}
	
}
