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
 *  
 *  PROJECT NAME  : tfr-scheduling
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/dao/impl/LeadTruckDao.java
 * 
 *  FILE NAME     :LeadTruckDao.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-scheduling
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.server.dao.impl
 * FILE    NAME: TruckDepartPlanDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.arrival.api.shared.domain.QueryArrivalEntity;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.ILeadTruckDao;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.LeadTruckEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.QueryLeadTruckEntity;

/**
 * 计划DAO实现
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-11-21 下午7:00:14
 */
public class LeadTruckDao extends iBatis3DaoImpl implements ILeadTruckDao {

	/**
	 * 前缀
	 */
	private static String prefix = "Foss.scheduling.LeadTruck.";

	@Override
	public List<LeadTruckEntity> queryLeadTruckGrid(
			QueryLeadTruckEntity queryEntity, int limit, int start)
	{
		RowBounds rowBounds = new RowBounds(start, limit);
		List<LeadTruckEntity> list = this.getSqlSession().selectList(
				prefix + "queryLeadTruckGrid", queryEntity, rowBounds);
		return list;
	}

	@Override
	public void addLeadTruck(LeadTruckEntity entity)
	{
		this.getSqlSession().insert(
				prefix + "addLeadTruck", entity);
		
	}

	/**
	 * 
	 * 记录查询数量
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-25 上午11:35:55
	 * @see com.deppon.foss.module.transfer.arrival.api.server.dao.IArrivalDao#queryArrivalGrid(com.deppon.foss.module.transfer.arrival.api.shared.domain.QueryArrivalEntity, int, int)
	 */
	@Override
	public Long getCount(QueryLeadTruckEntity queryEntity)
	{

		return (Long) this.getSqlSession().selectOne(prefix + "getCount",
				queryEntity);
	}
	@Override
	public void updateLeadTruck(LeadTruckEntity entity)
	{
		this.getSqlSession().update(
				prefix + "updateLeadTruck", entity);
		
	}
}