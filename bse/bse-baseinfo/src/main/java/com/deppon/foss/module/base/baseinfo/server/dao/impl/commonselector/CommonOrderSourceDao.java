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
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonOrderSourceDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonOrderSourceEntity;

/**
* 公共查询组件--“订单来源”的数据库对应数据访问DAO接口
* @author 187862-dujunhui
* @date 2014-9-22 下午2:45:34
*/
public class CommonOrderSourceDao extends SqlSessionDaoSupport implements ICommonOrderSourceDao {
    
    /** The Constant NAMESPACE. */
	private static final String NAMESPACE = "foss.bse.bse-baseinfo.commonOrderSource.";

	/** 
	 * <p>(根据传入对象查询符合条件所有职位信息 )</p> 
	 * @author 187862
	 * @date 2014-9-22 下午2:47:26
	 * @param entity
	 * @param limit
	 * @param start
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CommonOrderSourceEntity> queryOrderSourceListByCondition(
			CommonOrderSourceEntity entity, int limit, int start) {
		RowBounds bounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + "queryOrderSourceListByCondition", entity, bounds);
	}

	/** 
	 * <p>(统计总记录数 )</p> 
	 * @author 187862
	 * @date 2014-9-22 下午2:47:26
	 * @param entity
	 * @return 
	 */
	@Override
	public Long countOrderSourceListByCondition(CommonOrderSourceEntity entity) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "countOrderSourceListByCondition", entity);
	}
}
