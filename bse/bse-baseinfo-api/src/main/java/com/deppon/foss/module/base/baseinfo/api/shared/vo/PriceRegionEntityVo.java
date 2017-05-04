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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/PriceRegionEntityVo.java
 * 
 * FILE NAME        	: PriceRegionEntityVo.java
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
 * FILE    NAME: PriceRegionEntityVo.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.PriceRegionEntity;

/**
 * 价格区域和失效区域vo
 * @author panGuangJun
 * @date 2012-12-4 上午10:44:11
 */
public class PriceRegionEntityVo implements Serializable{
	/**
	*serialVersionUID
	*/
	private static final long serialVersionUID = -2625223759644134998L;
	//查询条件
	private PriceRegionEntity priceRegionEntity;
	//查询结果
	private List<PriceRegionEntity> priceRegionEntities;
	public PriceRegionEntity getPriceRegionEntity() {
		return priceRegionEntity;
	}
	public void setPriceRegionEntity(PriceRegionEntity priceRegionEntity) {
		this.priceRegionEntity = priceRegionEntity;
	}
	public List<PriceRegionEntity> getPriceRegionEntities() {
		return priceRegionEntities;
	}
	public void setPriceRegionEntities(List<PriceRegionEntity> priceRegionEntities) {
		this.priceRegionEntities = priceRegionEntities;
	}
}
