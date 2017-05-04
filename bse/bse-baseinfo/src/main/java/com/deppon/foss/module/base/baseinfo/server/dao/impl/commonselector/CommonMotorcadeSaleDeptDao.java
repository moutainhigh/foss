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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/commonselector/CommonMotorcadeSaleDeptDao.java
 * 
 * FILE NAME        	: CommonMotorcadeSaleDeptDao.java
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
 * FILE    NAME: CommonMotorcadeSaleDeptDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.dao.impl.commonselector;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonMotorcadeSaleDeptDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesMotorcadeEntity;

/**
 * 查询车队对应营业部Dao实现.
 *
 * @author 078823-foss-panGuangJun
 * @date 2012-12-5 下午2:50:50
 */
public class CommonMotorcadeSaleDeptDao extends  SqlSessionDaoSupport implements
		ICommonMotorcadeSaleDeptDao {
	
	/** The Constant NAMESPACE. */
	private static final String NAMESPACE = "foss.bse.bse-baseinfo.commonMotorcadeSalesDept.";

	/**
	 * 查询车队对应营业部.
	 *
	 * @param entity the entity
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-5 下午2:50:50
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonMotorcadeSaleDeptDao#queryMotorcadeSalesDeptByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesMotorcadeEntity,
	 * int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SalesMotorcadeEntity> queryMotorcadeSalesDeptByCondition(
			SalesMotorcadeEntity entity, int start, int limit) {
		RowBounds bounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(
				NAMESPACE + "querySalesMotorcadeByEntity", entity, bounds);
	}

	/**
	 * 查询车队对应营业部总数.
	 *
	 * @param entity the entity
	 * @return the long
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-5 下午2:50:50
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonMotorcadeSaleDeptDao#queryMotorcadeSalesDeptByConditionCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesMotorcadeEntity)
	 */
	@Override
	public long queryMotorcadeSalesDeptByConditionCount(
			SalesMotorcadeEntity entity) {
		return (Long) this.getSqlSession().selectOne(
				NAMESPACE + "querySalesMotorcadeByEntityCount", entity);
	}

}
