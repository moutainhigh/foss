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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/commonselector/ICommonProductItemService.java
 * 
 * FILE NAME        	: ICommonProductItemService.java
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
 * FILE    NAME: ICommonProductItemService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ProductItemEntity;

/**
 * 公共查询组件--查询产品条目service
 * @author 078823-foss-panGuangJun
 * @date 2012-12-4 下午2:32:12
 */
public interface ICommonProductItemService {
	/**
	 * 
	 * 产品条目查询--分页查询
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-4 下午2:35:52
	 * @param entity:查询条件
	 * @param start:起始条数
	 * @param limit:页数大小
	* @return List<ProductItemEntity>:返回的产品条目
	 */
	 List<ProductItemEntity> findPagingByCondition(ProductItemEntity productEntity, int start, int limit);
	/**
	 * 
	 * 查询总条数
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-4 下午2:35:56
	 * @param entity:查询条件
	* @return long:条目总条数
	 */
	 long countPagingByCondition(ProductItemEntity entity);
}
