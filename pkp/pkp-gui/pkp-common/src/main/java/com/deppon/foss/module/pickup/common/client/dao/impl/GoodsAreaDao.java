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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/dao/impl/GoodsAreaDao.java
 * 
 * FILE NAME        	: GoodsAreaDao.java
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
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;
import com.deppon.foss.module.pickup.common.client.dao.IGoodsAreaDao;
import com.deppon.foss.util.define.FossConstants;


/**
 * 库区
 * @author 105089-foss-yangtong
 * @date 2012-10-31 下午2:45:39
 */
public class GoodsAreaDao implements IGoodsAreaDao {
	/**
	 * 名称空间
	 */
	private static final String NAMESPACE = "foss.pkp.GoodsAreaEntityMapper.";
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
	public GoodsAreaEntity queryById(String id) {
		return sqlSession.selectOne(NAMESPACE + "selectByPrimaryKey",id);
	}
	
	/**
	 * 
	 * 功能：插条记录
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
	public boolean addGoodsArea(GoodsAreaEntity goodsArea) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", goodsArea.getId());
		String id = sqlSession.selectOne(NAMESPACE + "selectById", map);
		if(ObjectUtils.NULL.equals(ObjectUtils.defaultIfNull(
				id, ObjectUtils.NULL))){
			sqlSession.insert(NAMESPACE + "addGoodsArea", goodsArea);
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
	public void updateGoodsArea(GoodsAreaEntity goodsArea) {
		sqlSession.update(NAMESPACE + "updateGoodsArea", goodsArea);
	}
	
	/**
	 * 功能：查询记录
	 * @param:
	 * @return List<Department>
	 * @since:1.6
	 */
	public List<GoodsAreaEntity> queryAll() {
		return sqlSession.selectList(NAMESPACE + "getAll");
	}
	
	/** 
	 * <p>(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author foss-jiangfei
	 * @date 2012-11-8 下午8:06:53
	 * @param goodsArea
	 * @param start
	 * @param limit
	 * @return 
	 * @see com.deppon.foss.module.pickup.common.client.dao.IGoodsAreaDao#queryGoodsAreaByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity, int, int)
	 */
	public List<GoodsAreaEntity> queryGoodsAreaByCondition(
			GoodsAreaEntity goodsArea, int start, int limit) {
		goodsArea.setActive(FossConstants.ACTIVE);
		return sqlSession.selectList(NAMESPACE + "queryGoodsAreaListByCondition", goodsArea, new RowBounds(start, limit));
	}
	
	 /** 
     * <p>查询指定外场下的所有库区</p> 
     * @author foss-zhujunyong
     * @date Oct 22, 2012 11:56:58 AM
     * @param organizationCode
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IGoodsAreaDao#queryGoodsAreaListByOrganizationCode(java.lang.String)
     */
    public List<GoodsAreaEntity> queryGoodsAreaListByOrganizationCode(
	    String organizationCode, String goodsAreaType) {
		Map<String, String> map = new HashMap<String, String> ();
		map.put("active", FossConstants.ACTIVE);
		map.put("organizationCode", organizationCode);
		map.put("goodsAreaType", goodsAreaType);
		return sqlSession.selectList(NAMESPACE + "queryGoodsAreaListByOrganizationCode", map);
    }

	/**
	 * 删除
	 * @param goodsAreaEntity
	 */
	public void delete(GoodsAreaEntity goodsAreaEntity) {
		sqlSession.delete(NAMESPACE + "delete", goodsAreaEntity);
	}

}