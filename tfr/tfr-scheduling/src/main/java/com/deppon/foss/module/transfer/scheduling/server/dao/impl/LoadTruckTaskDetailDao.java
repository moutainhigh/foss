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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/dao/impl/LoadTruckTaskDetailDao.java
 * 
 *  FILE NAME     :LoadTruckTaskDetailDao.java
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
 * FILE    NAME: LoadTruckTaskDetailDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.server.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.load.api.shared.define.TaskTruckConstant;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.ILoadTruckTaskDetailDao;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TfrLoadTruckTaskDetailDto;

/**
 * 车辆任务Dao
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-12-20 下午3:48:14
 */
public class LoadTruckTaskDetailDao extends iBatis3DaoImpl implements ILoadTruckTaskDetailDao {

	/**
	 * 前缀
	 */
	private static final String prefix = "Foss.scheduling.load.";

	/**
	 * 根据车牌号查询
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-20 下午3:48:15
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ILoadTruckTaskDetailDao#queryTruckTaskDetailByVehicleNo(com.deppon.foss.module.transfer.scheduling.api.shared.domain.TfrLoadTruckTaskDetailEntity)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TfrLoadTruckTaskDetailDto> queryTruckTaskDetailByVehicleNo(TfrLoadTruckTaskDetailDto truckTaskDetail) {
		if (truckTaskDetail != null && CollectionUtils.isNotEmpty(truckTaskDetail.getList())) {
			// 状态列表
			List<String> list = new ArrayList<String>();
			// 已装车 = 未出发-UNDEPART
			list.add(TaskTruckConstant.TASK_TRUCK_STATE_UNDEPART);
			// 已出发 = 在途-ONTHEWAY
			list.add(TaskTruckConstant.TASK_TRUCK_STATE_ONTHEWAY);
			// 已到达 = 已到达-ARRIVED
			list.add(TaskTruckConstant.TASK_TRUCK_STATE_ARRIVED);
			truckTaskDetail.setList(list);
			return this.getSqlSession().selectList(prefix + "queryTruckTaskDetailVehicleNo", truckTaskDetail);
		} else {
			return null;
		}
	}
}