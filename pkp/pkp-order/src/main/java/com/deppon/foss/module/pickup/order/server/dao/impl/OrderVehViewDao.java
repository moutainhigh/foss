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

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.order.api.server.dao.IOrderVehViewDao;
import com.deppon.foss.module.pickup.order.api.shared.dto.DriverBillCountDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.DriverQueryDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OrderVehViewSignDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckSignDto;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;

/**
 * 
 * @ClassName: OrderVehViewDao 
 * @Description: 可视化查询DAO层实现
 * @author YANGBIN
 * @date 2014-5-9 下午4:39:12 
 *
 */
@SuppressWarnings("unchecked")
public class OrderVehViewDao extends iBatis3DaoImpl implements IOrderVehViewDao {

	public static final String NAMESPACE = "com.deppon.foss.module.pickup.order.api.server.dao.IOrderVehViewDao.";

	@Override
	public List<OwnTruckSignDto> queryDriverByCommon(
			DriverQueryDto driverQueryDto, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return getSqlSession().selectList(NAMESPACE+"queryDriverByCommon",driverQueryDto,rowBounds);
	}

	@Override
	public Long queryDriverByCommonCount(DriverQueryDto driverQueryDto) {
		return (Long) getSqlSession().selectOne(NAMESPACE+"queryDriverByCommonCount",driverQueryDto);
	}

	@Override
	public List<OrderVehViewSignDto> queryOrderVehViewByCommon(
			DriverQueryDto driverQueryDto) {
		driverQueryDto.setProductCodes(ProductEntityConstants.EXPRESS_PRODUCT_CODE_LIST);
		return getSqlSession().selectList(NAMESPACE + "queryOrderVehViewByCommon", driverQueryDto);
	}

	@Override
	public List<DriverBillCountDto> queryOrderBillCount(
			DriverQueryDto driverQueryDto) {
		driverQueryDto.setProductCodes(ProductEntityConstants.EXPRESS_PRODUCT_CODE_LIST);
		return getSqlSession().selectList(NAMESPACE + "queryOrderBillCount", driverQueryDto);
	}

	@Override
	public List<DriverBillCountDto> queryDeliverBillCount(
			DriverQueryDto driverQueryDto) {
		return getSqlSession().selectList(NAMESPACE + "queryDeliverBillCount", driverQueryDto);
	}

	@Override
	public List<DriverQueryDto> querySmallZoneCodesByBigZoneCodes(
			DriverQueryDto driverQueryDto) {
		return getSqlSession().selectList(NAMESPACE + "querySmallZoneCodesByBigZoneCodes", driverQueryDto);
	}

	
}