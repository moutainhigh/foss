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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/service/impl/LoadTruckTaskDetailService.java
 * 
 *  FILE NAME     :LoadTruckTaskDetailService.java
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
 * FILE    NAME: LoadTruckTaskDetailService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.server.service.impl;

import java.util.List;

import com.deppon.foss.module.transfer.scheduling.api.server.dao.ILoadTruckTaskDetailDao;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ILoadTruckTaskDetailService;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TfrLoadTruckTaskDetailDto;

/**
 * 车辆任务SERVICE
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-12-20 下午4:00:30
 */
public class LoadTruckTaskDetailService implements ILoadTruckTaskDetailService {

	ILoadTruckTaskDetailDao loadTruckTaskDetailDao;

	/**
	 * 根据车牌号查询
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-20 下午4:00:31
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ILoadTruckTaskDetailService#queryTruckTaskDetailByVehicleNo(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TfrLoadTruckTaskDetailDto)
	 */
	@Override
	public List<TfrLoadTruckTaskDetailDto> queryTruckTaskDetailByVehicleNo(TfrLoadTruckTaskDetailDto truckTaskDetail) {

		return loadTruckTaskDetailDao.queryTruckTaskDetailByVehicleNo(truckTaskDetail);
	}

	public void setLoadTruckTaskDetailDao(ILoadTruckTaskDetailDao loadTruckTaskDetailDao) {
		this.loadTruckTaskDetailDao = loadTruckTaskDetailDao;
	}

}