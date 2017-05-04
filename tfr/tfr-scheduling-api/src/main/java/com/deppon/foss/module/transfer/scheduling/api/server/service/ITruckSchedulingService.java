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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/server/service/ITruckSchedulingService.java
 * 
 *  FILE NAME     :ITruckSchedulingService.java
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
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.api.server.service
 * FILE    NAME: ITruckSchedulingService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.api.server.service;

import java.util.List;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckScheduleGridDto;

/**
 * 排班表SERVICE接口
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-10-26 下午5:00:12
 */
public interface ITruckSchedulingService {
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
	int batchInsertTruckScheduling(List<TruckSchedulingEntity> list);

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
	 * 查询某车队，某月的所有司机排班统计表
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-26 下午12:56:32
	 */
	List<TruckScheduleGridDto> queryTruckSchedulingList(TruckSchedulingEntity tsEntity, int limit, int start);

	/**
	 * 查询一条排班信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-26 上午10:00:28
	 */
	TruckSchedulingEntity queryTruckSchedulingByParams(TruckSchedulingEntity truckScheduling);

	/**
	 * 删除工作类型到“未知”状态
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-6 下午12:57:43
	 */
	void deleteTruckScheduling(List<String> scheduleIds, CurrentInfo user);

	/**
	 * 获取线路和车牌信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-1-28 下午3:18:44
	 */
	void queryLineInfoAndVehicleNo(List<TruckScheduleGridDto> tsDtos, TruckSchedulingEntity tsEntity);

	/**
	 * 查询部门信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-24 上午11:51:17
	 */
	String queryDepartment(String code);

	/**
	 * 查询总条数
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-3-20 下午7:36:57
	 */
	Long queryTruckSchedulingListTotal(TruckSchedulingEntity tsEntity);

	/**
	 * 导出
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-3-20 下午7:41:36
	 */
	List<TruckScheduleGridDto> queryTruckSchedulingList(TruckSchedulingEntity tsEntity);

	/**
	 * 排班数据初始化
	 * 
	 * @author 096598-foss-zhongyubing
	 * @param userEntity
	 * @date 2013-4-18 下午2:37:38
	 */
	void initDeaprtDriverScheduling(TruckSchedulingEntity schedulingEntity, UserEntity userEntity);
	/**
	 * 根据已有的排班复制新的排班
	 * @author foss-heyongdong
	 * @date 2015年1月14日 09:23:04
	 * @param tsEntity
	 * */
	void copyTruckScheduling(TruckSchedulingEntity tsEntity);
}