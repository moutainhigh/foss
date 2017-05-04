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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/commonselector/CommonOwnVehicleDao.java
 * 
 * FILE NAME        	: CommonOwnVehicleDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
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
package com.deppon.foss.module.base.baseinfo.server.dao.impl.commonselector;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonOwnVehicleDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.TruckDto;

/**
 * 公共查询选择器--公司车辆DAO实现.
 *
 * @author 078823-foss-panGuangjun
 * @date 2012-12-10 下午2:31:52
 */
public class CommonOwnVehicleDao extends SqlSessionDaoSupport implements
		ICommonOwnVehicleDao {
	
	/** The Constant NAMESPACE. */
	private static final String NAMESPACE = "foss.bse.bse-baseinfo.commonOwntruck.";

	/**
	 * <p>
	 * 根据条件有选择的统计出符合条件的“公司车辆”实体记录数（条件做自动判断，只选择实体中非空值）
	 * </p>.
	 *
	 * @param ownTruck 以“公司车辆”实体承载的条件参数实体
	 * @return 符合条件的“公司车辆”实体记录条数
	 * @author 078823-foss-panGuangjun
	 * @date 2012-10-24 上午11:35:56
	 */
	public long queryOwnVehicleRecordCountBySelectiveCondition(
			OwnTruckEntity ownTruck) {
		return (Long) this.getSqlSession().selectOne(
				NAMESPACE + "queryOwnVehicleRecordCountByCondition", ownTruck);
	}

	/**
	 * <p>
	 * 根据条件有选择的检索出符合条件的“外请车厢式车”实体列表（条件做自动判断，只选择实体中非空值）
	 * </p>.
	 *
	 * @param ownTruck 以“外请车厢式车”实体承载的条件参数实体
	 * @param offset 开始记录数
	 * @param limit 限制记录数
	 * @return 符合条件的“外请车厢式车”实体列表
	 * @author 078823-foss-panGuangjun
	 * @date 2012-12-10 下午2:31:24
	 */
	@SuppressWarnings("unchecked")
	public List<OwnTruckEntity> queryOwnVehicleListBySelectiveCondition(
			OwnTruckEntity ownTruck, int offset, int limit) {
		RowBounds rowBounds = new RowBounds(offset, limit);
		return this.getSqlSession().selectList(
				NAMESPACE + "queryOwnVehicleListByCondition", ownTruck,
				rowBounds);
	}

	/**
	 * 查询所有车辆信息.
	 *
	 * @param ownTruck the own truck
	 * @return the long
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-28 上午10:51:05
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonOwnVehicleDao#queryVehicleRecordCountBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity)
	 */
	@Override
	public long queryVehicleRecordCountBySelectiveCondition(
			OwnTruckEntity ownTruck) {
		return (Long) this.getSqlSession().selectOne(
				NAMESPACE + "queryVehicleRecordCountByCondition", ownTruck);
	}

	/**
	 * 查询所有车辆总数.
	 *
	 * @param ownTruck the own truck
	 * @param offset the offset
	 * @param limit the limit
	 * @return the list
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-28 上午10:51:05
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonOwnVehicleDao#queryVehicleListBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TruckDto> queryVehicleListBySelectiveCondition(
			OwnTruckEntity ownTruck, int offset, int limit) {
		RowBounds rowBounds = new RowBounds(offset, limit);
		return this.getSqlSession().selectList(
				NAMESPACE + "queryVehicleListByCondition", ownTruck,
				rowBounds);
	}
}
