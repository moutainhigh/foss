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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/server/dao/ITruckDepartPlanDao.java
 * 
 *  FILE NAME     :ITruckDepartPlanDao.java
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

import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.exception.TruckDepartPlanException;

/**
 * 计划接口
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-11-21 下午6:11:27
 */
public interface ITruckDepartPlanDao {

	/**
	 * 新增发车计划
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-21 下午6:15:37
	 */
	void addTruckDepartPlan(TruckDepartPlanDto truckDepartPlanDto) throws TruckDepartPlanException;

	/**
	 * 删除发车计划
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-21 下午6:15:46
	 */
	void deleteTruckDepartPlan(TruckDepartPlanDto truckDepartPlanDto) throws TruckDepartPlanException;

	/**
	 * 查询发车计划
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-21 下午6:16:25
	 */
	List<TruckDepartPlanDto> queryTruckDepartPlan(TruckDepartPlanDto truckDepartPlanDto, int limit, int start)
			throws TruckDepartPlanException;

	/**
	 * 查询发车计划
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-21 下午6:16:25
	 */
	List<TruckDepartPlanDto> queryTruckDepartPlanForExport(TruckDepartPlanDto truckDepartPlanDto)
			throws TruckDepartPlanException;

	/**
	 * 更新发车计划
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-21 下午6:16:36
	 */
	void updateTruckDepartPlan(TruckDepartPlanDto truckDepartPlanDto) throws TruckDepartPlanException;

	/**
	 * 根据发车计划的类型、状态、计划日期、出发部门、到达部门查询
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-29 下午1:41:58
	 */
	List<TruckDepartPlanDto> queryTruckDepartPlan(TruckDepartPlanDto truckDepartPlanDto);

	/**
	 * 查询发车计划总条数
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-3 下午2:05:10
	 */
	Long queryTruckDepartPlanTotal(TruckDepartPlanDto truckDepartPlanDto);

	/**
	 * 更新保存备注及异常标记
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-19 下午1:59:21
	 */
	void updatePlanRemark(TruckDepartPlanDto planDto);

	/**
	 * 根据ID查询发车计划
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-20 上午8:52:15
	 */
	TruckDepartPlanDto queryTruckDepartPlanById(TruckDepartPlanDto truckDepartPlanDto);

	/**
	 * 
	 * 根据
	 * 
	 * 状态status为Y
	 * 
	 * 线路编号lineNo
	 * 
	 * 发车日期departDate
	 * 
	 * 出发地 origOrgCode
	 * 
	 * 目的地destOrgCode
	 * 
	 * 查询当前最大的下一个班次号
	 * 
	 * 包括停发班次的
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2013-3-20 上午11:03:35
	 */
	Integer queryMaxfrequencyNo(TruckDepartPlanDetailDto detailDto);

}