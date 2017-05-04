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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/commonselector/CommonFlightDao.java
 * 
 * FILE NAME        	: CommonFlightDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.base.baseinfo.server.dao.impl.commonselector;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonTitleDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonTitleEntity;

/**
* 公共查询组件--“职位信息”的数据库对应数据访问DAO接口
* @author 187862-dujunhui
* @date 2014-08-08 上午8:41:34
*/
public class CommonTitleDao extends SqlSessionDaoSupport implements ICommonTitleDao {
    
    /** The Constant NAMESPACE. */
	private static final String NAMESPACE = "foss.bse.bse-baseinfo.commonTitle.";

	/** 
	 * <p>TODO(根据传入对象查询符合条件所有职位信息 )</p> 
	 * @author 187862
	 * @date 2014-8-8 上午8:41:26
	 * @param entity
	 * @param limit
	 * @param start
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonTitleDao#queryTitleListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonTitleEntity, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CommonTitleEntity> queryTitleListByCondition(
			CommonTitleEntity entity, int limit, int start) {
		// TODO Auto-generated method stub
		RowBounds bounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + "queryTitleListByCondition", entity, bounds);
	}

	/** 
	 * <p>TODO(统计总记录数 )</p> 
	 * @author 187862
	 * @date 2014-8-8 上午8:41:27
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonTitleDao#countTitleListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonTitleEntity)
	 */
	@Override
	public Long countTitleListByCondition(CommonTitleEntity entity) {
		// TODO Auto-generated method stub
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "countTitleListByCondition", entity);
	}


}
