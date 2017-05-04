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

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.order.api.server.dao.IOrderAutoExceptionLogEntityDao;
import com.deppon.foss.module.pickup.order.api.shared.domain.OrderAutoExceptionLogEntity;

/**
 * 
 * @ClassName: OrderAutoExceptionLogEntityDao 
 * @Description: 自动处理业务异常日志DAO层实现 
 * @author YANGBIN
 * @date 2014-5-9 下午4:43:13 
 *
 */
public class OrderAutoExceptionLogEntityDao extends iBatis3DaoImpl implements IOrderAutoExceptionLogEntityDao {
	public static final String NAMESPACE = "com.deppon.foss.module.pickup.order.api.shared.domain.OrderAutoExceptionLogEntity.";

	@Override
	public int insertAutoOrderExceptionLog(
			OrderAutoExceptionLogEntity orderAutoExceptionLogEntity) {
		return getSqlSession().insert(NAMESPACE + "insertAutoOrderExceptionLog", orderAutoExceptionLogEntity);
	}

	
}