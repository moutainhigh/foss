/**
 *  initial comments.
 */
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
 * PROJECT NAME	: pkp-pricing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/dao/impl/PricePlanDao.java
 * 
 * FILE NAME        	: PricePlanDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.pickup.pricing.api.server.dao.ICarloadLinePricePlanDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.CarloadLinePricePlanDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.CarloadLinePricePlanEntity;
/**
 * 整车线路价格方案dao
 * @author hehaisu
 * @date 2015-1-22 下午4:25:55
 */
public class CarloadLinePricePlanDao extends SqlSessionDaoSupport implements  ICarloadLinePricePlanDao{
	/**
	 * ibatis mapper namespace
	 */
    private static final String NAME_SPACE = "foss.pkp.pkp-pricing.LinePricePlanEntityMapper.";
   

    /**
	 * 分页查询方案
	 * 
	 * @param CarloadLinePricePlanEntity
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<CarloadLinePricePlanEntity> queryCarloadLinePricePlanByEntity(
			CarloadLinePricePlanEntity carloadLinePriceEntity, int start,
			int limit) {
		RowBounds rowBounds = new RowBounds(start,limit);
		return getSqlSession().selectList(NAME_SPACE + "queryCarloadLinePricePlanByEntity", carloadLinePriceEntity, rowBounds);
	}
	
	/**
	 * 查询方案总条数
	 * @param CarloadLinePricePlanEntity
	 * @return
	 */
	public Long queryCarloadLinePricePlanCountByEntity(
			CarloadLinePricePlanEntity carloadLinePriceEntity) {
		return (Long) getSqlSession().selectOne(NAME_SPACE + "queryCarloadLinePricePlanCountByEntity", carloadLinePriceEntity);
	}
	
	/**
	 * 查询方案明细
	 * @param carloadLinePriceDetailEntity
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<CarloadLinePricePlanDetailEntity> queryCarloadLinePricePlanDetailByEntity(
			CarloadLinePricePlanDetailEntity carloadLinePriceDetailEntity,
			int start, int limit) {
		RowBounds rowBounds = new RowBounds(start,limit);
		return getSqlSession().selectList(NAME_SPACE + "queryCarloadLinePricePlanDetailByEntity", carloadLinePriceDetailEntity, rowBounds);
	}
	
	/**
	 * 查询方案明细总条数
	 * @param carloadLinePriceDetailEntity
	 * @return
	 */
	public Long queryCarloadLinePricePlanDetailCountByEntity(
			CarloadLinePricePlanDetailEntity carloadLinePriceDetailEntity) {
		
		return (Long) getSqlSession().selectOne(NAME_SPACE + "queryCarloadLinePricePlanDetailCountByEntity", carloadLinePriceDetailEntity);
	}
	
	/**
	 * 插入价格方案
	 * @param carloadLinePriceEntity
	 * @return
	 */
	public int insertCarloadLinePricePlan(
			CarloadLinePricePlanEntity carloadLinePriceEntity) {
		return getSqlSession().insert(NAME_SPACE + "insertCarloadLinePricePlan",carloadLinePriceEntity);
	}
	
	/**
	 * 插入价格方案明细
	 * @param carloadLinePriceDetailEntity
	 * @return
	 */
	public int insertCarloadLinePricePlanDetail(
			CarloadLinePricePlanDetailEntity carloadLinePriceDetailEntity) {
		return getSqlSession().insert(NAME_SPACE + "insertCarloadLinePricePlanDetail",carloadLinePriceDetailEntity);
	}

	
}