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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/server/service/IAdviseWorkNumberService.java
 * 
 *  FILE NAME     :IAdviseWorkNumberService.java
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
package com.deppon.foss.module.transfer.scheduling.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.AdviseWorkNumberDto;

/**
 * 预测货量service类
 * 
 * @author huyue
 * @date 2012-10-31 下午5:24:06
 */
public interface IAdviseWorkNumberService extends IService {

	/**
	 * 获取计算上班人数所需的货量预测信息,车辆统计信息
	 * @author huyue
	 * @date 2012-12-11 下午5:05:09
	 */
	AdviseWorkNumberDto getForecastData(String orgCode,
			String action) throws TfrBusinessException;
	
}