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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/commonselector/CommonServiceZoneDao.java
 * 
 * FILE NAME        	: CommonServiceZoneDao.java
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
 * FILE    NAME: CommonServiceZoneDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.server.dao.impl.commonselector;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonServiceZoneDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SmallZoneEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ServiceZoneDto;

/**
 * 查询集中接送货大小区.
 *
 * @author 078823-foss-panGuangJun
 * @date 2012-12-28 上午10:56:36
 */
public class CommonServiceZoneDao extends SqlSessionDaoSupport implements ICommonServiceZoneDao {
	
    /** The namespace. */
    private static final String NAMESPACE ="foss.bse.bse-baseinfo.commonServiceZone.";

	/**
	 * 根据条件查询符合条件的大小区信息.
	 *
	 * @param smallZoneEntity the small zone entity
	 * @param offset the offset
	 * @param limit the limit
	 * @return the list
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-28 上午10:56:36
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonServiceZoneDao#queryServiceZoneListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.SmallZoneEntity, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ServiceZoneDto> queryServiceZoneListByCondition(
			SmallZoneEntity smallZoneEntity, int offset, int limit) {
		RowBounds rowBounds = new RowBounds(offset, limit);
		return this.getSqlSession().selectList(
				NAMESPACE + "queryVehicleTypeListByCondition", smallZoneEntity,
				rowBounds);
	}

	/**
	 * 根据条件查询符合条件的大小区信息总数.
	 *
	 * @param smallZoneEntity the small zone entity
	 * @return the long
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-28 上午10:56:36
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonServiceZoneDao#countServiceZoneListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.SmallZoneEntity)
	 */
	@Override
	public Long countServiceZoneListByCondition(SmallZoneEntity smallZoneEntity) {
		return (Long) this.getSqlSession().selectOne(
				NAMESPACE + "countVehicleTypesByCondition", smallZoneEntity);
	}

}
