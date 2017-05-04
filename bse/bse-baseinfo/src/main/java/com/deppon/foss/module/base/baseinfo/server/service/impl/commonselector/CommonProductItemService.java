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
 * PROJECT NAME	: bse-baseinfo
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/commonselector/CommonProductItemService.java
 * 
 * FILE NAME        	: CommonProductItemService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector
 * FILE    NAME: CommonProductItemDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonProductItemDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonProductItemService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ProductItemEntity;

/**
 * 产品条数查service实现.
 *
 * @author 078823-foss-panGuangJun
 * @date 2012-12-4 下午2:39:15
 */
public class CommonProductItemService implements ICommonProductItemService {
	
	/** The common product item dao. */
	private ICommonProductItemDao commonProductItemDao;
	
	/**
	 * 根据条件查询产品条目.
	 *
	 * @param productEntity the product entity
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-4 下午2:39:15
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonProductItemDao#findPagingByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.ProductItemEntity, int, int)
	 */
	@Override
	public List<ProductItemEntity> findPagingByCondition(
			ProductItemEntity productEntity, int start, int limit) {
		return commonProductItemDao.findPagingByCondition(productEntity, start, limit);
	}

	/**
	 * 查询符合条件的总数.
	 *
	 * @param entity the entity
	 * @return the long
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-4 下午2:39:15
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonProductItemDao#countPagingByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.ProductItemEntity)
	 */
	@Override
	public long countPagingByCondition(ProductItemEntity entity) {
		return commonProductItemDao.countPagingByCondition(entity);
	}

	/**
	 * setter.
	 *
	 * @param commonProductItemDao the new common product item dao
	 */
	public void setCommonProductItemDao(ICommonProductItemDao commonProductItemDao) {
		this.commonProductItemDao = commonProductItemDao;
	}

}
