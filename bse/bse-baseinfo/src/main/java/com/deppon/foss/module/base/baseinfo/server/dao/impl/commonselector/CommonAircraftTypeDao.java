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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/commonselector/CommonAircraftTypeDao.java
 * 
 * FILE NAME        	: CommonAircraftTypeDao.java
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
 * FILE    NAME: CommonAircraftTypeDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.dao.impl.commonselector;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonAircraftTypeDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AircraftTypeEntity;

/**
 * 公共查询选择器--机型dao实现.
 *
 * @author 078823-foss-panGuangJun
 * @date 2012-12-7 下午3:38:34
 */
public class CommonAircraftTypeDao extends SqlSessionDaoSupport implements
		ICommonAircraftTypeDao {
	
	/** The Constant NAMESPACE. */
	private static final String NAMESPACE = "foss.bse.bse-baseinfo.commonAircraftType.";

	/**
	 * 查询机型.
	 *
	 * @param aircraftType the aircraft type
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-7 下午3:38:34
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonAircraftTypeDao#queryAircraftTypeListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.AircraftTypeEntity,
	 * int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AircraftTypeEntity> queryAircraftTypeListByCondition(
			AircraftTypeEntity aircraftType, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(
				NAMESPACE + "queryAircraftTypeListByCondition", aircraftType,
				rowBounds);
	}

	/**
	 * 查询总条数.
	 *
	 * @param aircraftType the aircraft type
	 * @return the long
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-7 下午3:38:34
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonAircraftTypeDao#countAircraftTypeListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.AircraftTypeEntity)
	 */
	@Override
	public Long countAircraftTypeListByCondition(AircraftTypeEntity aircraftType) {
		return (Long) this.getSqlSession().selectOne(
				NAMESPACE + "countAircraftTypeListByCondition", aircraftType);
	}

}
