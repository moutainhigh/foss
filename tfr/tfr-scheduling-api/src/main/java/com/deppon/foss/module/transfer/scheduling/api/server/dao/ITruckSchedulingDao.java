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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/server/dao/ITruckSchedulingDao.java
 * 
 *  FILE NAME     :ITruckSchedulingDao.java
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
 * FILE    NAME: ITruckSchedulingDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.ScheduleDriverDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.SimpleTruckScheduleDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckScheduleGridDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckSchedulingDto;

/**
 * 排班表DAO接口
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-10-26 上午9:57:08
 */
public interface ITruckSchedulingDao {

	/**
	 * 插入排班表
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-26 上午9:59:55
	 */
	void insertTruckScheduling(TruckSchedulingEntity truckScheduling);

	/**
	 * 批量插入排班表
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-26 上午9:59:55
	 */
	void batchInsertTruckScheduling(List<TruckSchedulingEntity> list);

	/**
	 * 删除排班信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-26 上午9:59:57
	 */
	void deleteTruckScheduling(TruckSchedulingEntity truckScheduling);

	/**
	 * 更新排班信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-26 上午10:00:01
	 */
	void updateTruckScheduling(TruckSchedulingEntity truckScheduling);

	/**
	 * 查询排班信息列表
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-26 上午10:00:15
	 */
	List<TruckSchedulingEntity> queryTruckScheduling(TruckSchedulingEntity truckScheduling);

	/**
	 * 查询已经初始化计划列表
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-14 上午9:56:33
	 */
	Long queryHasInitedList(TruckSchedulingEntity truckScheduling);

	/**
	 * 查询某车队，某月的所有司机排班统计表
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-26 下午12:56:32
	 */
	List<TruckScheduleGridDto> queryTruckSchedulingList(TruckSchedulingEntity tsEntity, int limit, int start);
	/**
	 * 总数
	 * @author 096598-foss-zhongyubing
	 * @date 2013-3-20 下午7:34:24
	 */
	Long queryTruckSchedulingListTotal(TruckSchedulingEntity tsEntity);

	/**
	 * 查询一条排班信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-26 上午10:00:28
	 */
	TruckSchedulingEntity queryTruckSchedulingByParams(TruckSchedulingEntity truckScheduling);

	/**
	 * 查询是否 这些司机编码都存在(导入验证),如果有不存在的，则返回不存在的司机代码
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-3 下午12:45:36
	 */
	List<TruckSchedulingEntity> queryNotExistDriverCodes(ScheduleDriverDto scheduleDriverDto);

	/**
	 * 批量更新计划
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-3 下午4:18:56
	 */
	void batchUpdateTruckScheduling(List<TruckSchedulingDto> list);

	/**
	 * 根据参数查询出一条唯一的计划
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-6 下午2:48:44
	 */
	TruckSchedulingEntity queryTruckSchedulingByParams(SimpleTruckScheduleDto simDto);

	/**
	 * 查询排班任务ID对应的计划数据
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-6 下午2:25:03
	 */
	List<TruckSchedulingEntity> queryTruckScheduleByTaskIds(SimpleTruckScheduleDto truckScheduling);

	/**
	 * 导出
	 * @author 096598-foss-zhongyubing
	 * @date 2013-3-20 下午7:42:36
	 */
	List<TruckScheduleGridDto> queryTruckSchedulingList(TruckSchedulingEntity tsEntity);

}