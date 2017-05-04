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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/OwnTruckVo.java
 * 
 * FILE NAME        	: OwnTruckVo.java
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
 * FILE    NAME: OwnTractorsVo.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.TruckDto;

/**
 * @author panGuangJun
 * @date 2012-12-3 上午8:27:49
 */
public class OwnTruckVo implements Serializable{
	
	/**
	 *serialVersionUID
	 */
	private static final long serialVersionUID = 6080215836036034849L;
	//查询条件
	private OwnTruckEntity truck;
	//查询结果
	private List<OwnTruckEntity> ownTrucks ;
	//所有车辆集合
	private List<TruckDto> truckDtos;
	public OwnTruckEntity getTruck() {
		return truck;
	}
	public void setTruck(OwnTruckEntity truck) {
		this.truck = truck;
	}
	public List<OwnTruckEntity> getOwnTrucks() {
		return ownTrucks;
	}
	public void setOwnTrucks(List<OwnTruckEntity> ownTrucks) {
		this.ownTrucks = ownTrucks;
	}
	/**
	 *getter
	 */
	public List<TruckDto> getTruckDtos() {
		return truckDtos;
	}
	/**
	 *setter
	 */
	public void setTruckDtos(List<TruckDto> truckDtos) {
		this.truckDtos = truckDtos;
	}
	
	
}
