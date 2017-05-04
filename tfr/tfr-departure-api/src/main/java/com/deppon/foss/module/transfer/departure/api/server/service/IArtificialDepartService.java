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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/api/server/service/IArtificialDepartService.java
 *  
 *  FILE NAME          :IArtificialDepartService.java
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

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.departure.api.shared.domain.QueryDepartEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.TruckDepartEntity;
/**
 * 
 * 人工放行逻辑接口
 * @author foss-liubinbin(for IBM)
 * @date 2012-12-25 上午9:00:24
 */
public interface IArtificialDepartService extends IService{
	/**
	 * 
	 * 查询记录条数，用于分页
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-9 上午10:12:49
	 */
	Long getCount(QueryDepartEntity queryEntity);
	/**
	 * 查询临时任务
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-29 下午5:22:40
	 */
	List<TruckDepartEntity> queryTemporaryAssignments(QueryDepartEntity queryEntity,
			int limit, int start);
	
	/**
	 * LMS放行
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-17 下午2:09:50
	 */
	void lmsDepart(List<TruckDepartEntity> truckList);

}