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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/dao/impl/LineDao.java
 * 
 * FILE NAME        	: LineDao.java
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
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.pickup.common.client.dao.ILineDao;
import com.deppon.foss.util.define.FossConstants;


/**
 * 线路信息
 * @author 105089-foss-yangtong
 * @date 2012-10-24 上午10:54:32
 */
public class LineDao implements ILineDao{
	/**
	 * 名称空间
	 */
	private static final String NAMESPACE = "foss.pkp.LineEntityMapper.";
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
	 * @param:
	 * @return Department
	 * @since:1.6
	 */
	public LineEntity queryById(String id) {
		return sqlSession.selectOne( NAMESPACE + "selectByPrimaryKey",id);
	}
	
	/**
	 * 
	 * 功能：插条记录
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
	public boolean addLine(LineEntity line) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", line.getId());
		String id = sqlSession.selectOne(NAMESPACE + "selectById", map);
		if(ObjectUtils.NULL.equals(ObjectUtils.defaultIfNull(
				id, ObjectUtils.NULL))){
			sqlSession.insert( NAMESPACE + "insertSelective", line);
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
	public void updateLine(LineEntity line) {
		sqlSession.update( NAMESPACE + "updateByPrimaryKeySelective", line);
	}
	
	/**
	 * 功能：查询记录
	 * @param:
	 * @return List<Department>
	 * @since:1.6
	 */
	public List<LineEntity> queryAll() {
		return sqlSession.selectList( NAMESPACE + "getAll");
	}
	/**
	 * <p>
	 * 根据查询条件查询线路
	 * </p>
	 * 
	 * @author foss-jiangfei
	 * @date Oct 25, 2012 11:35:39 AM
	 * @param line
	 * @param start
	 * @param limit
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILineDao#queryLineListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity,
	 *      int, int)
	 */
	public List<LineEntity> queryLineListByCondition(LineEntity line,
			int start, int limit) {
		line.setActive(FossConstants.ACTIVE);
		return sqlSession.selectList(
				NAMESPACE+"queryLineListByCondition", line,
				new RowBounds(start, limit));
	}

	 /**
     * 
     * <p>通过始发营业部部门编码查询始发外场部门编码</p> 
     * @author foss-jiangfei
     * @date Nov 6, 2012 4:37:14 PM
     * @param sourceSaleCode
     * @param productCode
     * @return
     * @see
     */
	public String querySourceTransCode(String sourceSaleCode, String productCode) {
		String result = StringUtils.EMPTY;

		LineEntity sourceCondition = new LineEntity();
		sourceCondition.setOrginalOrganizationCode(sourceSaleCode);
		sourceCondition
				.setLineSort(DictionaryValueConstants.BSE_LINE_SORT_SOURCE);
		sourceCondition.setIsDefault(FossConstants.YES);
		List<LineEntity> sourceList = queryLineListByCondition(sourceCondition);
		if (sourceList.size()==0) {
			//  网点组配置和始发线路中都找不到，说明这是一个孤儿营业部，不能出发
			return result;
		} else {
			// 取得默认的始发配置外场
			return sourceList.get(0).getDestinationOrganizationCode();
		}
	}

	/**
     * 
     * <p>通过到达营业部部门编码查询到达外场部门编码</p> 
     * @author foss-jiangfei
     * @date Nov 6, 2012 4:38:32 PM
     * @param targetSaleCode
     * @param productCode
     * @return
     * @see
     */
	public String queryTargetTransCode(String targetSaleCode, String productCode) {
		String result = StringUtils.EMPTY;
		LineEntity targetCondition = new LineEntity();
		targetCondition.setDestinationOrganizationCode(targetSaleCode);
		targetCondition
				.setLineSort(DictionaryValueConstants.BSE_LINE_SORT_TARGET);
		targetCondition.setIsDefault(FossConstants.YES);
		List<LineEntity> targetList = queryLineListByCondition(targetCondition);
		if (targetList.size()==0) {
			//  网点组配置和到达线路中都找不到，说明这是一个孤儿营业部，不能出发
			return result;
		} else {
			// 取得默认的始发配置外场
			return targetList.get(0).getOrginalOrganizationCode();
		}
	}

	/**
	 * 
	 * <p>
	 * 根据查询条件查询线路,不带分页
	 * </p>
	 * 
	 * @author foss-jiangfei
	 * @date Nov 4, 2012 5:20:59 PM
	 * @param line
	 * @return
	 * @see
	 */
	public List<LineEntity> queryLineListByCondition(LineEntity line) {
		return queryLineListByCondition(line, 0, Integer.MAX_VALUE);
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.common.client.dao.ILineDao#delete(com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity)
	 */
	@Override
	public void delete(LineEntity lineEntity) {
		sqlSession.delete(NAMESPACE + "delete", lineEntity);
	}
}