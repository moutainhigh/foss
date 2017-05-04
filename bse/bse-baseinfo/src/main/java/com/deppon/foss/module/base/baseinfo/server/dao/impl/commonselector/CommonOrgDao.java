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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/commonselector/CommonOrgDao.java
 * 
 * FILE NAME        	: CommonOrgDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.server.dao.impl
 * FILE    NAME: CommonOrgDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.dao.impl.commonselector;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonOrgDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonOrgEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgSearchCondition;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CommonOrgDto;

/**
 * 组织公共组件查询dao.
 *
 * @author panGuangJun
 * @date 2012-11-28 上午10:39:50
 */
public class CommonOrgDao extends SqlSessionDaoSupport implements ICommonOrgDao {
	
	/** The namespace. */
    private static final String NAMESPACE = "foss.bse.bse-baseinfo.commmonOrg.";

	/**
	 * Query org by condition.
	 *
	 * @param condtion the condtion
	 * @return the list
	 * @description 通过条件查询组织信息--返回组织的编码和名称
	 * @author panGuangJun
	 * @date 2012-11-28 上午10:40:22
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonOrgDao#queryOrgByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonOrgEntity)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CommonOrgEntity> queryOrgByCondition(OrgSearchCondition condtion) {
		RowBounds bounds = null;
		if (null == condtion || condtion.getLimit() == 0) {
			bounds = new RowBounds(0, Integer.MAX_VALUE);
		} else {
			bounds = new RowBounds(condtion.getStart(), condtion.getLimit());
		}
		return this.getSqlSession().selectList(
				NAMESPACE + "queryCommonOrgByCondition", condtion, bounds);
	}

	/**
	 * Count org by condition.
	 *
	 * @param orgSearchCondtion the org search condtion
	 * @return the long
	 * @description 通过条件查询组织信息总数--返回组织的编码和名称
	 * @author panGuangJun
	 * @date 2012-11-28 上午11:21:00
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonOrgDao#countOrgByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgSearchCondition)
	 */
	@Override
	public long countOrgByCondition(OrgSearchCondition orgSearchCondtion) {
		Object obj = this.getSqlSession().selectOne(
				NAMESPACE + "countCommonOrgByCondition", orgSearchCondtion);
		if (null == obj) {
			return 0;
		} else {
			return (Long) obj;
		}
	}

	/**
	 * 根据名称/编码/拼音查询组织信息.
	 *
	 * @param orgSearchCondtion the org search condtion
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-6 下午3:12:46
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonOrgDao#queryOrgByParam(java.lang.String,int,int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CommonOrgEntity> queryOrgByParam(OrgSearchCondition orgSearchCondtion, int start,
			int limit) {
		RowBounds bounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(
				NAMESPACE + "queryCommonOrgByCondition", orgSearchCondtion, bounds);
	}

	/**
	 * 根据名称/编码/拼音查询组织总条数.
	 *
	 * @param orgSearchCondtion the org search condtion
	 * @return the long
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-6 下午3:12:46
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonOrgDao#countOrgByParam(java.lang.String)
	 */
	@Override
	public Long countOrgByParam(OrgSearchCondition orgSearchCondtion) {
		return (Long) this.getSqlSession().selectOne(
				NAMESPACE + "countCommonOrgByCondition", orgSearchCondtion);
	}

	/** 
	 * <p>org组织表选择器</p> 
	 * @author 232607 
	 * @date 2015-6-2 上午10:52:20
	 * @param dto
	 * @param start
	 * @param limit
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonOrgDao#queryOrgByParamNew(com.deppon.foss.module.base.baseinfo.api.shared.dto.CommonOrgDto, int, int)
	 */
	@SuppressWarnings("unchecked")
	public List<CommonOrgEntity> queryOrgByParamNew(CommonOrgDto dto, int start,int limit){
		return getSqlSession().selectList(NAMESPACE + "queryOrgByParam", dto, new RowBounds(start, limit));
	}
	/** 
	 * <p>org组织表选择器</p> 
	 * @author 232607 
	 * @date 2015-6-2 上午10:53:06
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonOrgDao#queryOrgByParamNewCount(com.deppon.foss.module.base.baseinfo.api.shared.dto.CommonOrgDto)
	 */
	public Long queryOrgByParamNewCount(CommonOrgDto dto){
		return (Long)getSqlSession().selectOne(NAMESPACE + "queryOrgByParamCount", dto);
	}
}
