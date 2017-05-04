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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/commonselector/CommonAirlinesDao.java
 * 
 * FILE NAME        	: CommonAirlinesDao.java
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
 * FILE    NAME: CommonAirlinesDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.dao.impl.commonselector;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonAirlinesDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesEntity;

/**
 * 公共选择器--航空公司查询dao实现.
 *
 * @author 078823-foss-panGuangJun
 * @date 2012-12-7 下午3:36:59
 */
public class CommonAirlinesDao extends SqlSessionDaoSupport implements
		ICommonAirlinesDao {
	
	/** The Constant NAMESPACE. */
	private static final String NAMESPACE = "foss.bse.bse-baseinfo.commmonAirlines.";

	/**
	 * 查询航空公司.
	 *
	 * @param entity the entity
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-7 下午3:36:59
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonAirlinesDao#queryAirlines(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesEntity,
	 * int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AirlinesEntity> queryAirlines(AirlinesEntity entity, int start,
			int limit) {
		RowBounds bounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + "getAllInfos",
				entity, bounds);
	}

	/**
	 * 查询总数.
	 *
	 * @param entity the entity
	 * @return the long
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-7 下午3:36:59
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonAirlinesDao#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesEntity)
	 */
	@Override
	public Long queryRecordCount(AirlinesEntity entity) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "getCount",
				entity);
	}

}
