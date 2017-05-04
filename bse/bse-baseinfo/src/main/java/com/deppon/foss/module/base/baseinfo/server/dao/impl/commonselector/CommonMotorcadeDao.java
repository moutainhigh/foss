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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/commonselector/CommonMotorcadeDao.java
 * 
 * FILE NAME        	: CommonMotorcadeDao.java
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
 * FILE    NAME: CommonMotorcadeDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.dao.impl.commonselector;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonMotorcadeDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CommonMotorcadeDto;

/**
 * 公共查询选择器--dao实现.
 *
 * @author 078823-foss-panGuangJun
 * @date 2012-12-11 下午2:24:21
 */
public class CommonMotorcadeDao extends SqlSessionDaoSupport implements
		ICommonMotorcadeDao {
	
	/** The Constant NAMESPACE. */
	private static final String NAMESPACE = "foss.bse.bse-baseinfo.commonMotorcade.";

	/**
	 * 查询车队.
	 *
	 * @param entity the entity
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-11 下午2:24:21
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonMotorcadeDao#queryMotorcadeByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity,
	 * int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MotorcadeEntity> queryMotorcadeByCondition(CommonMotorcadeDto dto,
			int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + "queryMotorcadeByCondition",dto, rowBounds);
	}

	/**
	 * 查询车队总条数.
	 *
	 * @param entity the entity
	 * @return the long
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-11 下午2:24:21
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonMotorcadeDao#queryMotorcadeByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity)
	 */
	@Override
	public long queryMotorcadeByConditionCount(CommonMotorcadeDto dto) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "countMotorcadeByCondition", dto);
	}

}
