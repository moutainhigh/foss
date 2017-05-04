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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/dao/impl/FreightRouteLineDao.java
 * 
 * FILE NAME        	: FreightRouteLineDao.java
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

import org.apache.commons.lang.ObjectUtils;
import org.apache.ibatis.session.SqlSession;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteLineEntity;
import com.deppon.foss.module.pickup.common.client.dao.IFreightRouteLineDao;
import com.deppon.foss.util.define.FossConstants;

/**
 * 走货路径线路
 * 
 * @author 105089-foss-yangtong
 * @date 2012-10-24 下午3:21:39
 */
public class FreightRouteLineDao implements IFreightRouteLineDao {
	/**
	 * 名称空间
	 */
	private static final String NAMESPACE = "foss.pkp.FreightRouteLineEntityMapper.";
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
	 * 功能：按id查询
	 * 
	 * @param:
	 * @return Department
	 * @since:1.6
	 */
	public FreightRouteLineEntity queryById(String id) {
		return sqlSession.selectOne(NAMESPACE + "selectByPrimaryKey", id);
	}

	/**
	 * 
	 * 功能：插条记录
	 * 
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
	public boolean addFreightRouteLine(FreightRouteLineEntity freightRouteLine) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", freightRouteLine.getId());
		String id = sqlSession.selectOne(NAMESPACE + "selectById", map);
		if(ObjectUtils.NULL.equals(ObjectUtils.defaultIfNull(
				id, ObjectUtils.NULL))){
			sqlSession.insert(NAMESPACE + "insertSelective", freightRouteLine);
			return true;
		}else{
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
	public void updateFreightRouteLine(FreightRouteLineEntity freightRouteLine) {
		sqlSession.update(NAMESPACE + "updateByPrimaryKeySelective",freightRouteLine);
	}

	/**
	 * 功能：查询记录
	 * 
	 * @param:
	 * @return List<Department>
	 * @since:1.6
	 */
	public List<FreightRouteLineEntity> queryAll() {
		//getAll
		return null;
	}

	/**
	 * <p>
	 * (通过走货路径查询走货路径线段集合)
	 * </p>
	 * 
	 * @author foss-jiangfei
	 * @date 2012-11-9 上午9:34:23
	 * @param freightRouteLine
	 * @return
	 * @see com.deppon.foss.module.pickup.common.client.dao.IFreightRouteLineDao#queryFreightRouteLineListByFreightRoute(com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteLineEntity)
	 */
	public List<FreightRouteLineEntity> queryFreightRouteLineListByFreightRoute(
			FreightRouteLineEntity freightRouteLine) {
		freightRouteLine.setActive(FossConstants.ACTIVE);
		return sqlSession.selectList(NAMESPACE
				+ "queryFreightRouteLineListByFreightRoute", freightRouteLine);
	}
	/**
	 * 删除
	 * @param freightRouteLineEntity
	 */
	public void delete(FreightRouteLineEntity freightRouteLineEntity){
		sqlSession.delete(NAMESPACE + "delete", freightRouteLineEntity);
	}
}