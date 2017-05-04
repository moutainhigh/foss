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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/commonselector/CommonSaleDepartmentDao.java
 * 
 * FILE NAME        	: CommonSaleDepartmentDao.java
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
 * FILE    NAME: CommonSaleDepartmentDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.server.dao.impl.commonselector;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonSaleDepartmentDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;

/**
 * 营业部查询dao实现.
 *
 * @author 078823-foss-panGuangJun
 * @date 2012-12-5 下午2:53:11
 */
public class CommonSaleDepartmentDao extends  SqlSessionDaoSupport implements ICommonSaleDepartmentDao {
	
	/** The Constant NAMESPACE. */
	private static final String NAMESPACE="foss.bse.bse-baseinfo.commonSaleDepartment.";
	
	/**
	 * 营业部查询.
	 *
	 * @param entity the entity
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-5 下午2:53:11
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonSaleDepartmentDao#querySaleDepartmentExactByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SaleDepartmentEntity> querySaleDepartmentExactByEntity(
			SaleDepartmentEntity entity, int start, int limit) {
		RowBounds bounds = new RowBounds(start,limit);
		return this.getSqlSession().selectList(NAMESPACE+"querySaleDepartmentByEntity", entity,bounds);
	}

	/**
	 * 营业部查询总条数.
	 *
	 * @param entity the entity
	 * @return the long
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-5 下午2:53:11
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonSaleDepartmentDao#querySaleDepartmentExactByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity)
	 */
	@Override
	public long querySaleDepartmentExactByEntityCount(
			SaleDepartmentEntity entity) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE+"querySaleDepartmentByEntityCount",entity);
	}

    /**
     * 精确查询
     * 查询不包括虚拟营业部和快递点部的营业部
     *
     * @author 313353-foss-qiupeng
     * @date 2016-10-9 上午9:20:31
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<SaleDepartmentEntity> querySaleDeptFilterExactByEntity(
			SaleDepartmentEntity entity, int start, int limit) {
		RowBounds bounds = new RowBounds(start,limit);
		List<SaleDepartmentEntity> result = 
				this.getSqlSession().selectList(NAMESPACE+"querySaleDeptFilterByEntity", entity,bounds);
		return result;
	}

	/**
     * 精确查询-查询总条数，用于分页
     * 查询不包括虚拟营业部和快递点部的营业部总条数
     * 
     * @author 313353-foss-qiupeng
     * @date 2016-10-9 上午9:20:31
     */
	@Override
	public long querySaleDeptFilterExactByEntityCount(
			SaleDepartmentEntity entity) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE+"querySaleDepatFilterByEntityCount",entity);
	}

}
