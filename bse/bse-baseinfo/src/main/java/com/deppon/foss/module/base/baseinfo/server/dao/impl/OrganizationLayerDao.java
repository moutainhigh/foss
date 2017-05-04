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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/OrganizationLayerDao.java
 * 
 * FILE NAME        	: OrganizationLayerDao.java
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
 * FILE    NAME: OrganizationLayerDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IOrganizationLayerDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrganizationLayerEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * 组织层级dao实现
 * 
 * @author 078823-foss-panGuangJun
 * @date 2012-12-13 上午11:41:14
 */
public class OrganizationLayerDao extends SqlSessionDaoSupport implements
		IOrganizationLayerDao {
	private static final String NAMESPACE = "foss.bse.bse-baseinfo.organizationLayer.";

	/**
	 * 查询最后更新时间
	 * 
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-13 上午11:41:14
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IOrganizationLayerDao#getLastModifyTime()
	 */
	@Override
	public Date getLastModifyTime() {
		return (Date) this.getSqlSession().selectOne(
				NAMESPACE + "getLastUpdateTime");
	}

	/**
	 * 查询所有组织层级
	 * 
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-13 上午11:41:14
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IOrganizationLayerDao#getAllOrganizationLayer()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Collection<OrganizationLayerEntity> getAllOrganizationLayer() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("active", FossConstants.ACTIVE);
		return this.getSqlSession().selectList(
				NAMESPACE + "queryOrganizationLayerByAllCondition", map);
	}

	/**
	 * 根据code查询出组织层级信息列表
	 * @author 101911-foss-zhouChunlai
	 * @date 2013-2-22 上午10:18:27
	 */
	@SuppressWarnings("unchecked")
	public List<OrganizationLayerEntity> queryOrgLayerInfoByCode(String code) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("active", FossConstants.ACTIVE);
		return getSqlSession().selectList(NAMESPACE + "queryOrgLayerInfoByCode", map);
	}

}
