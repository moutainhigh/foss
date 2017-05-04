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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/server/service/ICalculateOptimalPlatformService.java
 * 
 *  FILE NAME     :ICalculateOptimalPlatformService.java
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
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.server.service.impl
 * FILE    NAME: ICalculateOptimalPlatformService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.api.server.service;

import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.OptimalPlatformDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.exception.CalculateOptimalPlatformException;

/**
 * 计算最优月台
 * @author 104306-foss-wangLong
 * @date 2012-11-15 上午6:33:11
 */
public interface ICalculateOptimalPlatformService extends IService {

	/** 
	 * 计算最优月台</br>
	 * 正在使用, 未使用的月台都参与计算
	 * @author 104306-foss-wangLong
	 * @date 2012-11-15 上午6:33:11
	 * @param handOverBillNoList 交接单编号List
	 * @see #calcOptimalPlatform(List, Map)
	 * @return 返回月台基础信息List
	 */
	List<OptimalPlatformDto> calcOptimalPlatform(List<String> handOverBillNoList) 
			throws CalculateOptimalPlatformException;
	
	/** 
	 * 计算最优月台</br>
	 * 正在使用, 未使用的月台都参与计算
	 * @author 104306-foss-wangLong
	 * @date 2012-11-15 上午6:33:11
	 * @param truckTaskDetailId 任务车辆明细ID
	 * @see #calcOptimalPlatform(List, Map)
	 * @return 返回月台基础信息List
	 */
	List<OptimalPlatformDto> calcOptimalPlatform(String truckTaskDetailId) 
			throws CalculateOptimalPlatformException;
	
	/** 
	 * 计算最优月台</br>
	 * 仅计算未使用(空闲)的月台
	 * @author 104306-foss-wangLong
	 * @date 2012-11-15 上午6:33:11
	 * @param handOverBillNoList 交接单编号List
	 * @see #calcOptimalPlatform(List, Map)
	 * @return 返回月台基础信息List
	 */
	List<OptimalPlatformDto> calcUnusedOptimalPlatform(List<String> handOverBillNoList)
			throws CalculateOptimalPlatformException;
	
	/** 
	 * 计算最优月台</br>
	 * 仅计算未使用(空闲)的月台
	 * @author 104306-foss-wangLong
	 * @date 2012-11-15 上午6:33:11
	 * @param truckTaskDetailId 任务车辆明细ID
	 * @see #calcOptimalPlatform(List, Map)
	 * @return 返回月台基础信息List
	 */
	List<OptimalPlatformDto> calcUnusedOptimalPlatform(String truckTaskDetailId)
			throws CalculateOptimalPlatformException;
	
	/**
	 * 计算最优月台JOB.
	 * @author huyue
	 * @date 2013-4-11 下午1:44:03
	 */
	public void calcOptimalPlatformJob(String truckTaskDetailId, List<String> handOverBillNoList, String deptOrgCode, String vehicleNo);

	/**
	 * 删除最优月台JOB.
	 * @author huyue
	 * @date 2013-4-11 下午1:44:33
	 */
	public void deleteOptimalPlatformJob(String truckTaskDetailId);
}