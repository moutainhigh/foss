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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/commonselector/ICommonPriceRegionService.java
 * 
 * FILE NAME        	: ICommonPriceRegionService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo-api
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.api.server.service
 * FILE    NAME: ICommonReginService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.PriceRegionEntity;

/**
 *公共组件--价格区域和
 * @author panGuangJun
 * @date 2012-12-4 上午9:27:10
 */
public interface ICommonPriceRegionService {
	/**
	 * .
	 * <p>
	 * 根据条件查询区域信息<br/>
	 * （分页） 方法名：searchRegionByCondition
	 * </p>
	 * 
	 * @param regionEntity
	 *            查询条件
	 * @param start
	 *            其实查询位置
	 * @param limit
	 *            每页几条
	 * @author panGuangjun
	 * @时间 2012-12-4
	 * @since JDK1.6
	 */
	public List<PriceRegionEntity> searchRegionByCondition(
			PriceRegionEntity regionEntity, int start, int limit);
	/**
	 * .
	 * <p>
	 * 根据条件查询区域信息个数<br/>
	 * 方法名：countRegionByCondition
	 * </p>
	 * 
	 * @param regionEntity
	 *            查询条件
	 * @author panGuangjun
	 * @时间 2012-12-4
	 * @since JDK1.6
	 */
	Long countRegionByCondition(PriceRegionEntity regionEntity);
}
