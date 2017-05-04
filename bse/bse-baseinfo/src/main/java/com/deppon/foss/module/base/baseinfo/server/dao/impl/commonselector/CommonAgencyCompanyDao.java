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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/commonselector/CommonAgencyCompanyDao.java
 * 
 * FILE NAME        	: CommonAgencyCompanyDao.java
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
 * FILE    NAME: CommonAirAgencyCompanyDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.dao.impl.commonselector;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonAgencyCompanyDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity;

/**
 * 公共查询组件--空运代理公司查询dao实现.
 *
 * @author 078823-foss-panGuangJun
 * @date 2012-12-6 上午9:35:24
 */
public class CommonAgencyCompanyDao extends SqlSessionDaoSupport implements
		ICommonAgencyCompanyDao {
	
	/** The Constant NAMESPACE. */
	private static final String NAMESPACE = "foss.bse.bse-baseinfo.commonBusinessPartner.";

	/**
	 * 空运代理公司查询.
	 *
	 * @param entity the entity
	 * @param limit the limit
	 * @param start the start
	 * @return the list
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-6 上午9:35:24
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonAgencyCompanyDao#queryAgencyCompanysByCondtion(com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity,
	 * int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BusinessPartnerEntity> queryAgencyCompanysByCondtion(
			BusinessPartnerEntity entity, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + "queryAllInfos",
				entity, rowBounds);
	}

	/**
	 * 空运代理公司查询总条数.
	 *
	 * @param entity the entity
	 * @return the long
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-6 上午9:35:24
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonAgencyCompanyDao#countRecordByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity)
	 */
	@Override
	public Long countRecordByCondition(BusinessPartnerEntity entity) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryCount",
				entity);
	}

}
