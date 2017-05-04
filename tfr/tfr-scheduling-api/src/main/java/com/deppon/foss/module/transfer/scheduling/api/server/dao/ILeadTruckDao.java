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
 *  PROJECT NAME  : tfr-scheduling-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/server/dao/ILeadTruckDao.java
 * 
 *  FILE NAME     :ILeadTruckDao.java
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
 * PROJECT NAME: tfr-scheduling-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.api.server.dao
 * FILE    NAME: TruckDepartPlanDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.scheduling.api.shared.domain.LeadTruckEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.QueryLeadTruckEntity;

/**
 * 计划接口
 * 
 * @author 096598-foss-liubinbin
 * @date 2012-11-21 下午6:11:27
 */
public interface ILeadTruckDao
{

	/**
	 * 
	 * 分页查询外请车询价
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-9 上午10:01:56
	 */
	public List<LeadTruckEntity> queryLeadTruckGrid(
			QueryLeadTruckEntity queryEntity, int limit, int start);

	/**
	 * 
	 * 分页查询外请车询价
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-9 上午10:01:56
	 */
	public Long getCount(QueryLeadTruckEntity queryEntity);

	/**
	 * 
	 * 新增一条外请车询价信息
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-9 上午10:01:56
	 */
	public void addLeadTruck(LeadTruckEntity entity);

	/**
	 * 
	 * 新增一条外请车询价信息
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-9 上午10:01:56
	 */
	public void updateLeadTruck(LeadTruckEntity entity);

}