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
 *  PROJECT NAME  : tfr-departure-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/api/server/service/IUpdateTaskStatusService.java
 *  
 *  FILE NAME          :IUpdateTaskStatusService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.departure.api.server.service;

import java.util.Date;
import java.util.List;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.departure.api.shared.domain.TruckDepartEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.TruckTaskDetailEntity;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDADepartDto;

/**
 * 
 * 对外的PDA接口
 * 
 * @author foss-liubinbin(for IBM)
 * @date 2012-11-2 上午10:25:00
 */
public interface IUpdateTaskStatusService extends IService {
	/**
	 * 
	 * 放行后更新相应表的状态
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-31 下午6:27:07
	 */
	void updateTaskStatus(TruckDepartEntity manualEntity, String departType);

	/**
	 * 
	 * 判断是否存在该车牌号的该任务，通过车牌号，出发部门，状态来查询
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-31 下午6:27:07
	 */
	void existTaskOrNot(TruckDepartEntity manualEntity, String departType);

	/**
	 * 
	 * 更新同一个任务下，同一个出发部门出发的时间（GPS）不更新车辆的状态 放行的时间已经在gps的服务里面做过了
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-3-26 下午1:08:33
	 */
	void updateGPSDepartTime(TruckTaskDetailEntity truckTaskDetailEntity,
			Date gpsDepartTime);

	/**
	 * 
	 * 增加一条到达信息
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-3-29 上午11:09:37
	 */
	String addTruckArrival(PDADepartDto pdaDto, String departType,Date planArriveTime);

	/**
	 * 
	 * 更改到达之后的状态
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-3-29 上午11:09:37
	 */
	void updateArriveStatus(List<TruckTaskDetailEntity> taskDetailList,
			PDADepartDto pdaDto, String departType, String truckTaskId,
			String destOrgCode);
}