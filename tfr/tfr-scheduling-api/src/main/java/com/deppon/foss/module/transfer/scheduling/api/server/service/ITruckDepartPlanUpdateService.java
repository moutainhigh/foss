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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/server/service/ITruckDepartPlanUpdateService.java
 * 
 *  FILE NAME     :ITruckDepartPlanUpdateService.java
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
 * FILE    NAME: ITruckDepartPlanUpdateDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckDepartPlanUpdateEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.exception.TruckDepartPlanException;

/**
 * 计划日志service接口
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-11-21 下午6:45:09
 */
public interface ITruckDepartPlanUpdateService extends IService {
	/**
	 * 新增日志
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-21 下午6:15:37
	 */
	void addTruckDepartPlanUpdate(TruckDepartPlanUpdateEntity truckDepartPlanUpdateEntity)
			throws TruckDepartPlanException;

	/**
	 * 查询日志
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-21 下午6:16:25
	 */
	List<TruckDepartPlanUpdateEntity> queryTruckDepartPlanUpdates(TruckDepartPlanDetailDto detailDto, int limit,
			int start) throws TruckDepartPlanException;

	/**
	 * 查询日志总条数
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-23 上午8:48:51
	 */
	Long queryTotalCount(TruckDepartPlanDetailDto detailDto) throws TruckDepartPlanException;

	/**
	 * 比对计划明细修改情况
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-3 下午5:39:18
	 */
	public TruckDepartPlanUpdateEntity compareDepartPlanDetail(TruckDepartPlanDetailDto oldDetail,
			TruckDepartPlanDetailDto detailDto, CurrentInfo user) throws TruckDepartPlanException;

	/**
	 * 批量新增日志
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-4 上午10:05:36
	 */
	void addTruckDepartPlanUpdates(List<TruckDepartPlanUpdateEntity> detailLogs);

}