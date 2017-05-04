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
 * PROJECT NAME	: pkp-predeliver
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/server/dao/impl/QueryGoodsDao.java
 * 
 * FILE NAME        	: QueryGoodsDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IQueryGoodsDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.GoodsInfoConditionDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.GoodsInfoDto;

/**
 * 
 * 货量查询DAO
 * @author 043258-foss-zhaobin
 * @date 2012-10-11 下午2:46:49
 * @since
 * @version
 */
public class QueryGoodsDao extends iBatis3DaoImpl implements IQueryGoodsDao
{
	/**
	 * 货量name space
	 */
	private static final String NAMESPACE_CONTRACT = "foss.pickup.predeliver.querygoods.";
	/**
	 * 货量查询
	 */
	private static final String QUERY_GOODSINFO = "queryGoodsInfo"; 
	/**
	 * 货量查询总条数
	 */
	private static final String QUERY_GOODSINFOCOUNT = "queryGoodsInfoCount"; 

	/**
	 * 
	 * @根据查询条件返回货量查询集合
	 * @author 043258-foss-zhaobin
	 * @2012-10-16
	 * @return List
	 * @param goodsInfoConditionVo  货量查询条件
	 * @param start  数据开始下标
	 * @param limit  获取数据长度
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<GoodsInfoDto> queryGoods(GoodsInfoConditionDto goodsInfoConditionDto,int start, int limit)
	{
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(
				NAMESPACE_CONTRACT + QUERY_GOODSINFO, goodsInfoConditionDto,
				rowBounds);
	}

	/**
	 * 
	 * 查询货量总条数，用于分页
	 * @author 043258-foss-zhaobin
	 * @date 2012-10-22 下午5:18:40
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.IQueryGoodsDao#getGoodsInfoCount(com.deppon.foss.module.pickup.predeliver.api.shared.dto.GoodsInfoConditionDto)
	 */
	@Override
	public Long getGoodsInfoCount(GoodsInfoConditionDto goodsInfoConditionDto) 
	{
		return (Long)this.getSqlSession().selectOne(NAMESPACE_CONTRACT + QUERY_GOODSINFOCOUNT,goodsInfoConditionDto);
	}
	
	/**
	 * 
	 * 货物总量 非分页
	 * @author 043258-foss-zhaobin
	 * @date 2012-11-22 下午3:22:14
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.IQueryGoodsDao#queryGoodsInfo(com.deppon.foss.module.pickup.predeliver.api.shared.dto.GoodsInfoConditionDto)
	 */
	@SuppressWarnings("unchecked")
	public List<GoodsInfoDto> queryGoodsInfo(GoodsInfoConditionDto goodsInfoConditionDto)
	{
		return this.getSqlSession().selectList(
				NAMESPACE_CONTRACT + QUERY_GOODSINFO, goodsInfoConditionDto);
	}
}