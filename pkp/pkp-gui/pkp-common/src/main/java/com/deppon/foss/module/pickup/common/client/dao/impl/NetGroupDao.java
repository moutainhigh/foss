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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/dao/impl/NetGroupDao.java
 * 
 * FILE NAME        	: NetGroupDao.java
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.NetGroupEntity;
import com.deppon.foss.module.pickup.common.client.dao.INetGroupDao;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

/**
 * 
 * 网点组数据持久层实现
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-sunrui,date:2012-10-17 上午9:24:13,
 * </p>
 * 
 * @author foss-sunrui
 * @date 2012-10-17 上午9:24:13
 * @since
 * @version
 */
public class NetGroupDao implements INetGroupDao {
	/**
	 * 名称空间
	 */
	private static final String NAMESPACE = "foss.pkp.NetGroupEntityMapper.";
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
	 * 通过主键获取网点组
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-17 上午9:24:33
	 * @param id
	 * @return
	 * @see com.deppon.foss.module.pickup.creating.client.dao.INetGroupDao#queryByPrimaryKey(java.lang.String)
	 */
	public NetGroupEntity queryByPrimaryKey(String id) {
		return sqlSession.selectOne(NAMESPACE + "selectNetGroupByPrimaryKey",id);
	}

	/**
	 * 
	 * <p>
	 * 通过营业部编码和网点组类型获取网点组
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-17 上午9:24:55
	 * @param map
	 * @return
	 * @see com.deppon.foss.module.pickup.creating.client.dao.INetGroupDao#queryBySaleDept(java.util.Map)
	 */
	public NetGroupEntity queryBySaleDept(Map<String, String> map) { 
		return sqlSession.selectOne(NAMESPACE + "selectNetGroupBySaleDept", map);
	}

	/**
	 * 
	 * <p>
	 * 根据出发营业部和到达营业部找出一条唯一的走货路径
	 * </p>
	 * 
	 * @author foss-jiangfei
	 * @date Nov 2, 2012 11:52:05 AM
	 * @param sourceSaleCode
	 *            出发营业部部门编码
	 * @param targetSaleCode
	 *            到达营业部部门编码
	 * @return 走货路径列表
	 * @see
	 */
	public List<String> queryFreightRouteCode(String sourceSaleCode,
			String targetSaleCode) {
		List<String> resultList = new ArrayList<String>();
		// 参数判断
		if (StringUtils.isBlank(sourceSaleCode)
				|| StringUtils.isBlank(targetSaleCode)) {
			return resultList;
		}

		NetGroupEntity condition = new NetGroupEntity();
		condition.setSourceOrganizationCode(sourceSaleCode);
		condition.setTargetOrganizationCode(targetSaleCode);
		List<NetGroupEntity> list = queryNetGroupBySourceTarget(condition);
		if (list.size() == 0) {
			return resultList;
		}
		for (NetGroupEntity entity : list) {
			resultList.add(entity.getFreightRouteVirtualCode());
		}
		return resultList;
	}

	/**
	 * <p>
	 * 根据出发营业部和到达营业部查询网点组记录
	 * </p>
	 * 
	 * @author foss-jiangfei
	 * @date Nov 4, 2012 4:41:50 PM
	 * @param netGroup
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.INetGroupDao#queryNetGroupBySourceTarget(com.deppon.foss.module.base.baseinfo.api.shared.domain.NetGroupEntity)
	 */
	public List<NetGroupEntity> queryNetGroupBySourceTarget(
			NetGroupEntity netGroup) {
		netGroup.setActive(FossConstants.ACTIVE);
		return sqlSession.selectList(NAMESPACE + "queryNetGroupBySourceTarget",
				netGroup);
	}

	/**
	 * 
	 * 功能：插条记录
	 * 
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
	public boolean addNetGroup(NetGroupEntity netGroup) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", netGroup.getId());
		String id = sqlSession.selectOne(NAMESPACE + "selectById", map);
		if(ObjectUtils.NULL.equals(ObjectUtils.defaultIfNull(
				id, ObjectUtils.NULL))){
			sqlSession.insert(NAMESPACE + "insertSelective", netGroup);
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
	public void updateNetGroup(NetGroupEntity netGroup) {
		sqlSession.update(NAMESPACE + "updateByPrimaryKeySelective", netGroup);
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.common.client.dao.INetGroupDao#delete(com.deppon.foss.module.base.baseinfo.api.shared.domain.NetGroupEntity)
	 */
	@Override
	public void delete(NetGroupEntity netGroupEntity) {
		sqlSession.delete(NAMESPACE + "delete", netGroupEntity);
		
	}
}