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
 * PROJECT NAME	: pkp-waybill
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/dao/impl/WaybillStockLogDao.java
 * 
 * FILE NAME        	: WaybillStockLogDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillStockLogDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillStockLogEntity;
/**
 * 运单库存LOG DAO
 * @author 026113-foss-linwensheng
 * @date 2012-12-27 上午8:23:13
 */
public class WaybillStockLogDao extends iBatis3DaoImpl implements IWaybillStockLogDao {

	private static final String NAMESPACE = "foss.pkp.WaybillStockLogEntityMapper.";
	@Override
	public int addWaybillStockLogEntity(WaybillStockLogEntity record) {
		// TODO Auto-generated method stub
		return getSqlSession().insert(NAMESPACE+"insert", record);
	}

}