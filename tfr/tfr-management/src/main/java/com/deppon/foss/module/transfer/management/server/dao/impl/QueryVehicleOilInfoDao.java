/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-management
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/server/dao/impl/QueryVehicleOilInfoDao.java
 *  
 *  FILE NAME          :QueryVehicleOilInfoDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-management
 * PACKAGE NAME: com.deppon.foss.module.transfer.management.server.dao.impl
 * FILE    NAME: QueryVehicleOilInfoDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.management.server.dao.impl;

import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.management.api.server.dao.IQueryVehicleOilInfoDao;
import com.deppon.foss.module.transfer.management.api.shared.dto.VehicleOilInfoDto;

/**
 * 查询车辆油耗DAO
 * @author 046130-foss-xuduowei
 * @date 2012-12-27 下午2:38:07
 */
public class QueryVehicleOilInfoDao extends iBatis3DaoImpl implements
		IQueryVehicleOilInfoDao {
	
	private final static String NAMESPACE = "foss.management.queryvehicleoil.";
	
	/** 
	 * 查询车辆油耗接口（供LMS使用）
	 * @author 046130-foss-xuduowei
	 * @date 2012-12-27 下午2:38:07
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IQueryVehicleOilInfoDao#queryVehicleOilInfo(java.lang.String, java.util.Date, java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleOilInfoDto> queryVehicleOilInfo(Map<String,Object> map) {
		
		return getSqlSession().selectList(NAMESPACE+"selectVehicleOil", map);
	}

}