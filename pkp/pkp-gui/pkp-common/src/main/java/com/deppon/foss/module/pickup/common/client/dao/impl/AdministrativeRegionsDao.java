/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/dao/impl/AdministrativeRegionsDao.java
 * 
 * FILE NAME        	: AdministrativeRegionsDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
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
package com.deppon.foss.module.pickup.common.client.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.pickup.common.client.dao.IAdministrativeRegionsDao;
import com.deppon.foss.util.define.FossConstants;

/**
 * 行政区域
 * 
 * @author 105089-foss-yangtong
 * @date 2012-11-6 下午3:33:56
 */
public class AdministrativeRegionsDao implements IAdministrativeRegionsDao {

	/**
	 * 名称空间
	 */
	private static final String NAMESPACE = "foss.pkp.AdministrativeRegionsMapper.";

	/**
	 * 数据库连接
	 */
	private SqlSession sqlSession;

	/**
	 * 数据库连接
	 * 
	 * @return void
	 * @since:1.6
	 */
	@Inject
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	/**
	 * 
	 * 功能：插条记录
	 * 
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
	@Override
	public boolean addAdministrativeRegions(AdministrativeRegionsEntity administrativeRegions) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", administrativeRegions.getId());
		String id = sqlSession.selectOne(NAMESPACE + "selectById", map);
		if (ObjectUtils.NULL.equals(ObjectUtils.defaultIfNull(id, ObjectUtils.NULL))) {
			sqlSession.insert(NAMESPACE + "insertSelective", administrativeRegions);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * 功能：更新条记录
	 * 
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	@Override
	public void updateAdministrativeRegions(AdministrativeRegionsEntity administrativeRegions) {
		sqlSession.update(NAMESPACE + "updateByPrimaryKeySelective", administrativeRegions);

	}

	/**
	 * 通过 标识编码查询
	 * 
	 * @author foss-jiangfei
	 * @date 2012-10-31 下午3:10:19
	 * @see com.deppon.foss.module.pickup.common.client.dao.IAdministrativeRegionsDao#queryAdministrativeRegionsByCode(java.lang.String)
	 */
	@Override
	public AdministrativeRegionsEntity queryAdministrativeRegionsByCode(String code) {
		if (StringUtils.isBlank(code)) {
			return null;
		}

		// 构造查询条件：
		AdministrativeRegionsEntity entity = new AdministrativeRegionsEntity();
		entity.setActive(FossConstants.ACTIVE);
		entity.setCode(code);

		List<AdministrativeRegionsEntity> entitys = sqlSession.selectList(NAMESPACE + "queryAdministrativeRegionsByCodeLocal", entity);
		if (entitys == null || entitys.isEmpty()) {
			return null;
		} else {
			return entitys.get(0);
		}
	}

	/**
	 * 删除
	 * 
	 * @param aministrativeRegions
	 */
	public void delete(AdministrativeRegionsEntity aministrativeRegions) {
		sqlSession.delete(NAMESPACE + "delete", aministrativeRegions);
	}

	@Override
	public List<AdministrativeRegionsEntity> queryAdministrativeRegionsByCodeActive(List<String> codes, String active) {
		// 请求合法性判断
		if (CollectionUtils.isEmpty(codes)) {
			return null;
		}
		// 构造查询条件：
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codes", codes);
		map.put("active", active);
		List<AdministrativeRegionsEntity> entitys = sqlSession.selectList(NAMESPACE + "queryAdministrativeRegionsByCodeActive", map);
		return entitys;
	}
}