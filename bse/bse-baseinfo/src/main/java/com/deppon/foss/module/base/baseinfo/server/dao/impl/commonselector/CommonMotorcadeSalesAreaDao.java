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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/commonselector/CommonMotorcadeSalesAreaDao.java
 * 
 * FILE NAME        	: CommonMotorcadeSalesAreaDao.java
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
 * FILE    NAME: CommonMotorcadeSalesAreaDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.dao.impl.commonselector;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonMotorcadeSalesAreaDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeServeSalesAreaEntity;

/**
 * 车队对应营业区查询Dao实现.
 *
 * @author 078823-foss-panGuangJun
 * @date 2012-12-5 下午2:51:37
 */
public class CommonMotorcadeSalesAreaDao extends  SqlSessionDaoSupport implements
		ICommonMotorcadeSalesAreaDao {
	
	/** The Constant NAMESPACE. */
	private static final String NAMESPACE = "foss.bse.bse-baseinfo.commonMotorcadeSalesArea.";

	/**
	 * 车队对应营业区查询.
	 *
	 * @param entity the entity
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-5 下午2:51:38
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonMotorcadeSalesAreaDao#queryMotorcadeServeSalesAreaByCondtion(com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeServeSalesAreaEntity,
	 * int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MotorcadeServeSalesAreaEntity> queryMotorcadeServeSalesAreaByCondtion(
			MotorcadeServeSalesAreaEntity entity, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(
				NAMESPACE + "queryMotorcadeServeSalesAreaByCondition", entity,
				rowBounds);
	}

	/**
	 * 车队对应营业区查询总条数.
	 *
	 * @param entity the entity
	 * @return the long
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-5 下午2:51:38
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonMotorcadeSalesAreaDao#queryMotorcadeServeSalesAreaByCondtionCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeServeSalesAreaEntity)
	 */
	@Override
	public long queryMotorcadeServeSalesAreaByCondtionCount(
			MotorcadeServeSalesAreaEntity entity) {
		return (Long) this.getSqlSession().selectOne(
				NAMESPACE + "queryMotorcadeServeSalesAreaByConditionCount",
				entity);
	}

}
