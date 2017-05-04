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

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.order.api.server.dao.IOrderReportEntityDao;
import com.deppon.foss.module.pickup.order.api.shared.domain.OrderReportEntity;

/**
 * 
 * @ClassName: OrderReportEntityDao 
 * @Description: 快递每日统计报表DAO层实现
 * @author YANGBIN
 * @date 2014-5-9 下午4:39:12 
 *
 */
@SuppressWarnings("unchecked")
public class OrderReportEntityDao extends iBatis3DaoImpl implements IOrderReportEntityDao {
	public static final String NAMESPACE = "com.deppon.foss.module.pickup.order.api.shared.domain.OrderReportEntity.";

	@Override
	public int insertOrderReport(OrderReportEntity orderReportEntity) {
		return getSqlSession().insert(NAMESPACE + "insertOrderReport", orderReportEntity);
	}

	@Override
	public int deleteOrderReport(OrderReportEntity orderReportEntity) {
		return getSqlSession().delete(NAMESPACE + "deleteOrderReport", orderReportEntity);

	}

	@Override
	public OrderReportEntity selectOrderReportByAddress(
			OrderReportEntity orderReportEntity) {
		List<OrderReportEntity> orderReportEntityList = getSqlSession().selectList(NAMESPACE + "selectOrderReportByAddress", orderReportEntity);
		return CollectionUtils.isEmpty(orderReportEntityList) ? null : orderReportEntityList.get(0);
	}
	@Override
	public OrderReportEntity selectOrderReportByAddressDriverCode(
			OrderReportEntity orderReportEntity) {
		List<OrderReportEntity> orderReportEntityList = getSqlSession().selectList(NAMESPACE + "selectOrderReportByAddressDriverCode", orderReportEntity);
		return CollectionUtils.isEmpty(orderReportEntityList) ? null : orderReportEntityList.get(0);
	}
	@Override
	public String queryMinReceiveOrderDriver(List<String> list) {
		String minDirverCode = (String) getSqlSession().selectOne(NAMESPACE+"queryMinReceiveOrderDriver", list);
		return minDirverCode;
	}
	@Override
	public Long queryReceiveCountOrderDriver(String driverCode) {
		return (Long)getSqlSession().selectOne(NAMESPACE+"queryReceiveCountOrderDriver", driverCode);
	}
}