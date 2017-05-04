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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/dao/impl/EffectiveRegionOrgDao.java
 * 
 * FILE NAME        	: EffectiveRegionOrgDao.java
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
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang.ObjectUtils;
import org.apache.ibatis.session.SqlSession;

import com.deppon.foss.module.pickup.common.client.dao.IEffectiveRegionOrgDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.EffectiveRegionOrgEntity;


/**
 * 时效区域与部门对应关系数据访问
 * @author 105089-foss-yangtong
 * @date 2012-10-24 下午2:57:05
 */
public class EffectiveRegionOrgDao implements IEffectiveRegionOrgDao {
	/**
	 * 名称空间
	 */
	private static final String NAMESPACE = "foss.pkp.EffectiveRegionOrgEntityMapper.";
	/**
	 * 数据库连接
	 */
	private SqlSession sqlSession;
	
	/**
	 * 数据库连接
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
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
	public boolean addEffectiveRegionOrg(
			EffectiveRegionOrgEntity effectiveRegionOrg) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", effectiveRegionOrg.getId());
		String id = sqlSession.selectOne(NAMESPACE + "selectById", map);
		if(ObjectUtils.NULL.equals(ObjectUtils.defaultIfNull(
				id, ObjectUtils.NULL))){
			sqlSession.insert( NAMESPACE + "insertEffectiveRegionOrg", effectiveRegionOrg);
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 
	 * 功能：更新条记录
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	public void updateEffectiveRegionOrg(
			EffectiveRegionOrgEntity effectiveRegionOrg) {
		sqlSession.update( NAMESPACE + "updateEffectiveRegionOrg", effectiveRegionOrg);
	}

}