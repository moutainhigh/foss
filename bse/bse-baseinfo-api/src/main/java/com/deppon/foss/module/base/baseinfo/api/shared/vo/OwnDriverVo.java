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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/OwnDriverVo.java
 * 
 * FILE NAME        	: OwnDriverVo.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo-api
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.api.shared.vo
 * FILE    NAME: OwnDriverVo.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnDriverEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DriverDto;

/**
 *公司车辆vo
 * @author panGuangJun
 * @date 2012-12-3 上午9:22:30
 */
public class OwnDriverVo implements Serializable{
	/**
	*serialVersionUID
	*/
	private static final long serialVersionUID = -21668415667691448L;
	//查询条件
	private OwnDriverEntity driverEntity;
	//返回结果
	private List<OwnDriverEntity> driverEntities;
	//所有司机dto
	private List<DriverDto>  driverDtos;
	public OwnDriverEntity getDriverEntity() {
		return driverEntity;
	}
	public void setDriverEntity(OwnDriverEntity driverEntity) {
		this.driverEntity = driverEntity;
	}
	public List<OwnDriverEntity> getDriverEntities() {
		return driverEntities;
	}
	public void setDriverEntities(List<OwnDriverEntity> driverEntities) {
		this.driverEntities = driverEntities;
	}
	/**
	 *getter
	 */
	public List<DriverDto> getDriverDtos() {
		return driverDtos;
	}
	/**
	 *setter
	 */
	public void setDriverDtos(List<DriverDto> driverDtos) {
		this.driverDtos = driverDtos;
	}
	
}
