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
import com.deppon.foss.module.pickup.order.api.server.dao.IOrderPdaReturnRecordEntityDao;
import com.deppon.foss.module.pickup.order.api.shared.domain.OrderPdaReturnRecordEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;

/**
 * 
* @ClassName: OrderReportEntityDao 
* @Description: 快递订单退回记录DAO层实现 
* @author YANGBIN
* @date 2014-5-9 下午4:39:12 
*
 */
public class OrderPdaReturnRecordEntityDao extends iBatis3DaoImpl implements IOrderPdaReturnRecordEntityDao {
	public static final String NAMESPACE = "com.deppon.foss.module.pickup.order.api.shared.domain.OrderPdaReturnRecordEntity.";
	
	public static final String DELETE_ORDER_PDA_RETURN = "deleteOrderPdaReturn";
	
	@Override
	public int insertPdaReturnRecord(
			com.deppon.foss.module.pickup.order.api.shared.domain.OrderPdaReturnRecordEntity orderPdaReturnRecordEntity) {
		return getSqlSession().insert(NAMESPACE + "insertPdaReturnRecord", orderPdaReturnRecordEntity);
	}
	
	@Override
	public Long queryOrderPdaReturnByDriverCode(
			com.deppon.foss.module.pickup.order.api.shared.domain.OrderPdaReturnRecordEntity orderPdaReturnRecordEntity) {
		return (Long) getSqlSession().selectOne(NAMESPACE + "queryOrderPdaReturnByDriverCode", orderPdaReturnRecordEntity);
	}

	@Override
	public int deleteOrderPdaReturn(
			OrderPdaReturnRecordEntity orderPdaReturnRecordEntity) {
		return getSqlSession().delete(NAMESPACE + DELETE_ORDER_PDA_RETURN, orderPdaReturnRecordEntity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public OrderPdaReturnRecordEntity queryOrderPdaReturnByCommon(
			OrderPdaReturnRecordEntity orderPdaReturnRecordEntity) {
		orderPdaReturnRecordEntity.setProductCodes(ProductEntityConstants.EXPRESS_PRODUCT_CODE_LIST);
		List<OrderPdaReturnRecordEntity> list = 
					getSqlSession().selectList(NAMESPACE + "queryOrderPdaReturnByCommon", orderPdaReturnRecordEntity);
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}
		return null;
	}

}