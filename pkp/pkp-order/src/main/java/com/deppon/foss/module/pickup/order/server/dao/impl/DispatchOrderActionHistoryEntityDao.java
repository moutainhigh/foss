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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/server/dao/impl/DispatchOrderActionHistoryEntityDao.java
 * 
 * FILE NAME        	: DispatchOrderActionHistoryEntityDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchOrderActionHistoryEntityDao;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderActionHistoryEntity;

/**
 * 调度订单操作历史DAO
 * @author 038590-foss-wanghui
 * @date 2012-12-18 下午8:14:33
 */
public class DispatchOrderActionHistoryEntityDao extends iBatis3DaoImpl implements
		IDispatchOrderActionHistoryEntityDao {

	static final String NAMESPACE = "com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderActionHistoryEntityEntity.";
	
	/**
	 * 
	 * 单条增加调度订单操作历史
	 * @param record
	 * 			tSrvDispatchOrderId
	 * 				调度订单id
	 * 			orderStatus
	 * 				订单状态
	 * 			notes
	 * 				操作备注
	 * 			operator
	 * 				操作人
	 * 			operatorCode
	 * 				操作人编码
	 * 			operateTime 
	 * 				操作时间
	 * @author 038590-foss-wanghui
	 * @date 2012-12-18 下午8:17:41
	 */
	@Override
	public int addDispatchOrderActionHistory(DispatchOrderActionHistoryEntity record) {
		return getSqlSession().insert(NAMESPACE + "addDispatchOrderActionHistory", record);
	}

	/**
     * 
     * 批量增加调度订单操作历史
     * @param record
	 * 			tSrvDispatchOrderId
	 * 				调度订单id
	 * 			orderStatus
	 * 				订单状态
	 * 			notes
	 * 				操作备注
	 * 			operator
	 * 				操作人
	 * 			operatorCode
	 * 				操作人编码
	 * 			operateTime 
	 * 				操作时间
     * @author 038590-foss-wanghui
     * @date 2012-12-18 下午8:14:30
     */
	@Override
	public int batchAddDispatchOrderActionHistory(
			List<DispatchOrderActionHistoryEntity> actionHistoryEntities) {
		return getSqlSession().insert(NAMESPACE + "batchAddDispatchOrderActionHistory", actionHistoryEntities);
	}
	/**
	 * 已分配记录 快递
	 */
	@Override
	public String selectOperatorByorderid(String id) {
		return (String)getSqlSession().selectOne(NAMESPACE + "selectOperatorByorderid", id);
	}
	/**
	 * 已分配记录 零担
	 */
	@Override
	public String ldSelectOperatorByorderid(String id) {
		return (String)getSqlSession().selectOne(NAMESPACE + "ldSelectOperatorByorderid", id);
	}
	
}