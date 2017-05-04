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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/commonselector/CommonProductItemDao.java
 * 
 * FILE NAME        	: CommonProductItemDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.server.dao.impl
 * FILE    NAME: CommonProductItemDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.dao.impl.commonselector;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonProductItemDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ProductItemEntity;

/**
 * 公共查询组件--产品条目dao实现.
 *
 * @author 078823-foss-panGuangJun
 * @date 2012-12-4 下午2:18:04
 */
public class CommonProductItemDao extends  SqlSessionDaoSupport implements
		ICommonProductItemDao {

	/** The Constant NAMESPACE. */
	private static final String NAMESPACE = "foss.bse.bse-baseinfo.CommonProductItem.";

	/**
	 * 查询产品条目.
	 *
	 * @param productEntity the product entity
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-4 下午2:18:05
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonProductItemDao#findPagingByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.ProductItemEntity,
	 * int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ProductItemEntity> findPagingByCondition(
			ProductItemEntity productEntity, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(
				NAMESPACE + "findByCondition", productEntity, rowBounds);
	}

	/**
	 * 查询总条数.
	 *
	 * @param entity the entity
	 * @return the long
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-4 下午2:18:05
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonProductItemDao#countPagingByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.ProductItemEntity)
	 */
	@Override
	public long countPagingByCondition(ProductItemEntity entity) {
		Object obj = this.getSqlSession().selectOne(
				NAMESPACE + "countPagingByCondition", entity);
		if (null==obj) {
			return 0L;
		}else{
			return (Long)obj;
		}
	}

}
