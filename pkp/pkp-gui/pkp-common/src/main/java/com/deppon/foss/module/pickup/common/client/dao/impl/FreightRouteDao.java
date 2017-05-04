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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/dao/impl/FreightRouteDao.java
 * 
 * FILE NAME        	: FreightRouteDao.java
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

import org.apache.commons.lang.ObjectUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity;
import com.deppon.foss.module.pickup.common.client.dao.IFreightRouteDao;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

/**
 * 
 * 走货路径数据持久层
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-sunrui,date:2012-10-18 下午7:23:53,
 * </p>
 * 
 * @author foss-sunrui
 * @date 2012-10-18 下午7:23:53
 * @since
 * @version
 */
public class FreightRouteDao implements IFreightRouteDao {
	/**
	 * 名称空间
	 */
	private static final String NAMESPACE = "foss.pkp.freightRouteMapper.";
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
	 * <p>
	 * 通过主键获取走线路径
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-18 下午7:24:25
	 * @param id
	 * @return
	 * @see com.deppon.foss.module.pickup.common.client.dao.IFreightRouteDao#queryByPrimaryKey(java.lang.String)
	 */
	public FreightRouteEntity queryByPrimaryKey(String id) {
		return sqlSession.selectOne(NAMESPACE+ "selectFreightRouteByPrimaryKey", id);
	}

	/**
	 * 
	 * <p>
	 * 通过发货营业部和收货营业部获取走货路径
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-18 下午7:25:00
	 * @param map
	 * @return
	 * @see com.deppon.foss.module.pickup.common.client.dao.IFreightRouteDao#queryBySaleDept(java.util.Map)
	 */
	public FreightRouteEntity queryBySaleDept(Map<String, String> map) {
		return sqlSession.selectOne(NAMESPACE + "selectFreightRouteBySaleDept", map);
	}

	/**
	 * 
	 * <p>
	 * 根据虚拟代码查询走货路径
	 * </p>
	 * 
	 * @author foss-jiangfei
	 * @date Oct 25, 2012 11:33:00 AM
	 * @param virtualCode
	 * @return
	 * @see
	 */
	public FreightRouteEntity queryFreightRouteByVirtualCode(String virtualCode) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("active", FossConstants.ACTIVE);
		map.put("virtualCode", virtualCode);
		return (FreightRouteEntity) sqlSession.selectOne(NAMESPACE+ "queryFreightRouteByVirtualCode", map);
	}

	/**
	 * <p>
	 * 根据查询条件查询走货路径
	 * </p>
	 * 
	 * @author foss-jiangfei
	 * @date Oct 25, 2012 11:35:39 AM
	 * @param freightRoute
	 * @param start
	 * @param limit
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IFreightRouteDao#queryFreightRouteListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity,
	 *      int, int)
	 */
	public List<FreightRouteEntity> queryFreightRouteListByCondition(
			FreightRouteEntity freightRoute, int start, int limit) {
		freightRoute.setActive(FossConstants.ACTIVE);
		return sqlSession.selectList(NAMESPACE
				+ "queryFreightRouteListByCondition", freightRoute,
				new RowBounds(start, limit));
	}

	/**
	 * 
	 * 功能：插条记录
	 * 
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
	public boolean addFreightRoute(FreightRouteEntity freightRoute) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", freightRoute.getId());
		String id = sqlSession.selectOne(NAMESPACE + "selectById", map);
		if(ObjectUtils.NULL.equals(ObjectUtils.defaultIfNull(
				id, ObjectUtils.NULL))){
			sqlSession.insert(NAMESPACE + "insertFreightRoute", freightRoute);
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
	public void updateFreightRoute(FreightRouteEntity freightRoute) {
		sqlSession.update(NAMESPACE + "updateByPrimaryKeySelective", freightRoute);
	}


	/**
	 * 删除
	 * @param freightRouteEntity
	 */
	public void delete(FreightRouteEntity freightRouteEntity) {
		sqlSession.delete(NAMESPACE + "delete", freightRouteEntity);
		
	}

}