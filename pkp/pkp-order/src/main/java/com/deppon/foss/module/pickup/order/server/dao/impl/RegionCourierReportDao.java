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
 * PROJECT NAME	: pkp-order
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/server/dao/impl/PdaSignEntityDao.java
 * 
 * FILE NAME        	: PdaSignEntityDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.server.dao.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;


import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.order.api.server.dao.IRegionCourierReportDao;
import com.deppon.foss.module.pickup.order.api.shared.domain.RegionCourierReportEntity;

/**
 * 
 * @ClassName: RegionCourierReportDao 
 * @Description: 快递员统计数据DAO
 * @author YANGBIN
 * @date 2014-5-9 下午4:39:12 
 *
 */
public class RegionCourierReportDao extends iBatis3DaoImpl implements IRegionCourierReportDao {

	public static final String NAMESPACE = "com.deppon.foss.module.pickup.order.api.shared.domain.RegionCourierReportEntity.";

	@Override
	public RegionCourierReportEntity queryOneByCommon(String courierCode) {
		@SuppressWarnings("unchecked")
		List<RegionCourierReportEntity> list = getSqlSession().selectList(NAMESPACE+"queryOneByCommon",courierCode);
		if(CollectionUtils.isNotEmpty(list)){
			RegionCourierReportEntity reportEntity = list.get(0);
			return reportEntity;
		}
		return null;
	}

	@Override
	public String queryMinCourierByCommon(List<String> list) {
		return (String) getSqlSession().selectOne(NAMESPACE+"queryMinCourierByCommon",list);
	}

	@Override
	public int insert(RegionCourierReportEntity regionCourierReportEntity) {
		return getSqlSession().insert(NAMESPACE+"insert",regionCourierReportEntity);
	}

	@Override
	public int update(RegionCourierReportEntity regionCourierReportEntity) {
		return getSqlSession().update(NAMESPACE+"update",regionCourierReportEntity);

	}
	@Override
	public int delete(RegionCourierReportEntity regionCourierReportEntity) {
		return getSqlSession().delete(NAMESPACE+"delete",regionCourierReportEntity);

	}
	
}