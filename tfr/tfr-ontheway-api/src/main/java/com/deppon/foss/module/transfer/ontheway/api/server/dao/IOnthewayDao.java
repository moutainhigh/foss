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
 *  PROJECT NAME  : tfr-ontheway-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/ontheway/api/server/dao/IOnthewayDao.java
 *  
 *  FILE NAME          :IOnthewayDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.ontheway.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.ontheway.api.shared.domain.ManualEntity;
import com.deppon.foss.module.transfer.ontheway.api.shared.domain.OnthewayEntity;
import com.deppon.foss.module.transfer.ontheway.api.shared.domain.QueryOnthewayEntity;
/**
 * 
 * 人工放行逻辑接口
 * @author foss-liubinbin(for IBM)
 * @date 2012-12-25 上午9:00:24
 */
public interface IOnthewayDao
{

	/**
	 * 查询临时任务,分页
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-29 下午5:22:40
	 */
	List<OnthewayEntity> queryOnthewayGrid(QueryOnthewayEntity queryEntity,
	int limit, int start);
	
	/**
	 * 通过任务车辆明细ID查询所有的跟踪明细
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-29 下午5:22:40
	 */
	List<OnthewayEntity> queryOnthewayGridById(String truckTaskDetailId);
	/**
	 * 
	 * 待跟踪长途车辆
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-26 上午9:43:28
	 */
	Long getCount(QueryOnthewayEntity queryEntity);
	/**
	 * 
	 * 增加一条在途信息
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-26 上午9:43:00
	 */
	void addManual(ManualEntity manualEntity);
	/**
	 * 
	 * 更行一些状态
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-26 上午9:43:00
	 */
	void updateManualStatus(ManualEntity manualEntity);
	
}