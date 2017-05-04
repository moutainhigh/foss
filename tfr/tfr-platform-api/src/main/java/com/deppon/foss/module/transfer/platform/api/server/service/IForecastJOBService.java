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
 *  PROJECT NAME  : tfr-platform-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/platform/api/server/service/IForecastServiceJOB.java
 * 
 *  FILE NAME     :IForecastServiceJOB.java
 *  
 *  AUTHOR        : yuyongxiang
 *  
 *  TIME          : 2014-03-06 13:58:56
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
package com.deppon.foss.module.transfer.platform.api.server.service;

import java.util.Date;

import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;

/**
 * 预测货量 JOB 类，各种工具方法或者各种和DAO打交道的都在 IForecastService 中，
 * 本类中只负责所有的JOB端的数据逻辑处理以及业务逻辑处理
 * 
 * @author yuyongxiang-134019
 * @date 2014-03-06 13:59:11
 */
public interface IForecastJOBService extends IForecastService {

	/**
	 * 预测外场货量方法 分出发,到达 调用预测各外场方法
	 * 
	 * @author yuyongxiang-134019
	 * @date 2014-03-06 13:59:18
	 */
	void forecastTransferCenterTotal(String action,String threadNo,String threadCount, Date statistics)
			throws TfrBusinessException ;

	//zyx test used
	void zyxTest();
}
