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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/commonselector/CommonSmallZoneDao.java
 * 
 * FILE NAME        	: CommonSmallZoneDao.java
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
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonSmallZoneDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SmallZoneEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CommonSmallZoneDto;


/**
 *公共选择器--接送货小区Dao
 * @author 101911-foss-zhouChunlai
 * @date 2013-1-12 上午10:17:25
 */
public class CommonSmallZoneDao extends SqlSessionDaoSupport implements ICommonSmallZoneDao {
    
    private static final String NAMESPACE = "foss.bse.bse-baseinfo.commonSmallzone.";
    /**
	 * 根据条件查询接送货小区.
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2013-1-12 上午10:06:59
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SmallZoneEntity> querySmallZoneByCondition(
			CommonSmallZoneDto dto, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + "querySmallZoneByCondition",dto, rowBounds);
	}
	/**
	 * 根据条件查询接送货小区总条数.
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2013-1-12 上午10:09:23
	 * @return 
	 */
	@Override
	public Long queryRecordCount(CommonSmallZoneDto dto) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryRecordCount", dto);
	}

}
