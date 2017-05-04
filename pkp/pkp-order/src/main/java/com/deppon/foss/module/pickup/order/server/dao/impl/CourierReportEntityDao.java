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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/server/dao/impl/DispatchOrderEntityDao.java
 * 
 * FILE NAME        	: DispatchOrderEntityDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.order.api.server.dao.ICourierReportEntityDao;
import com.deppon.foss.module.pickup.order.api.shared.domain.CourierReportEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.CourierQueryDto;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;

/**
 * 
 * @ClassName: CourierReportEntityDao 
 * @Description: 快递每日统计报表DAO层实现
 * @author YANGBIN
 * @date 2014-5-9 下午4:39:12 
 *
 */
@SuppressWarnings("unchecked")
public class CourierReportEntityDao extends iBatis3DaoImpl implements ICourierReportEntityDao {
	public static final String NAMESPACE = "com.deppon.foss.module.pickup.order.api.shared.domain.CourierReportEntity.";

	@Override
	public int insertCourierReport(CourierReportEntity courierReportEntity) {
		return getSqlSession().insert(NAMESPACE + "insertCourierReport", courierReportEntity);
	}

	@Override
	public List<CourierReportEntity> queryCourierByCommon(
			CourierQueryDto courierQueryDto,int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return getSqlSession().selectList(NAMESPACE + "queryCourierByCommon", courierQueryDto,rowBounds);
	}
	
	@Override
	public List<CourierReportEntity> queryCourierByCommon(
			CourierQueryDto courierQueryDto) {
		return getSqlSession().selectList(NAMESPACE + "queryCourierByCommon", courierQueryDto);
	}

	@Override
	public Long queryCourierByCommonCount(CourierQueryDto courierQueryDto) {
		return (Long) getSqlSession().selectOne(NAMESPACE + "queryCourierByCommonCount", courierQueryDto);
	}

	@Override
	public List<CourierReportEntity> queryCourierReportByDay(Date curDate, Date preDate) {
		CourierQueryDto queryDto = new CourierQueryDto();
		queryDto.setBeginTime(preDate);
		queryDto.setEndTime(curDate);
		queryDto.setProductCodes(ProductEntityConstants.EXPRESS_PRODUCT_CODE_LIST);
		return getSqlSession().selectList(NAMESPACE + "queryCourierReportByDay",queryDto);
	}

	@Override
	public int deleteCourierReportByDay(Date date) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("date", date);
		return getSqlSession().delete(NAMESPACE + "deleteCourierReportByDay", map);
	}

}