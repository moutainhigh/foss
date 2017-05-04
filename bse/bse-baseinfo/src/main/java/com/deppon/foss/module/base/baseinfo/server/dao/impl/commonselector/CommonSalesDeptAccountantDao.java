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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/commonselector/CommonSalesDeptAccountantDao.java
 * 
 * FILE NAME        	: CommonSalesDeptAccountantDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.server.dao.impl.commonselector
 * FILE    NAME: CommonSalesDeptAccountantDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.dao.impl.commonselector;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonSalesDeptAccountantDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesDeptAccountantEntity;

/**
 * 公共查询选择器--区域会计查询dao实现.
 *
 * @author 078823-foss-panGuangJun
 * @date 2012-12-7 下午3:43:39
 */
public class CommonSalesDeptAccountantDao extends SqlSessionDaoSupport
		implements ICommonSalesDeptAccountantDao {
	
	/** The Constant NAMESPACE. */
	private static final String NAMESPACE = "foss.bse.bse-baseinfo.commmonSalesDeptAccountant.";

	/**
	 * 区域会计查询.
	 *
	 * @param entity the entity
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-7 下午3:43:39
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonSalesDeptAccountantDao#querySalesDeptAccountantGroup(com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesDeptAccountantEntity,
	 * int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SalesDeptAccountantEntity> querySalesDeptAccountantGroup(
			SalesDeptAccountantEntity entity, int start, int limit) {
		RowBounds bounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + "queryALlInfos",
				entity, bounds);
	}

	/**
	 * 区域会计总数.
	 *
	 * @param entity the entity
	 * @return the long
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-7 下午3:43:39
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonSalesDeptAccountantDao#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesDeptAccountantEntity)
	 */
	@Override
	public Long queryRecordCount(SalesDeptAccountantEntity entity) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryCount",
				entity);
	}
}
