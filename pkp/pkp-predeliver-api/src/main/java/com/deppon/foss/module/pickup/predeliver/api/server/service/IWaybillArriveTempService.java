/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-predeliver-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/server/service/IWaybillArriveTempService.java
 * 
 * FILE NAME        	: IWaybillArriveTempService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.server.service;

import com.deppon.foss.framework.service.IService;

/**
 * 
 * 运单到达数量、到达时间临时表Service
 * @author ibm-wangfei
 * @date Oct 19, 2012 10:10:25 AM
 */
public interface IWaybillArriveTempService extends IService{
	
	/**
	 * 
	 * 计算运单到达数量、到达时间临时表存储过程
	 * @author ibm-wangfei
	 * @date Nov 20, 2012 3:24:18 PM
	 */
	void preprocess();

	void autoNotify();
	
	/**
	 * 自动推送到货信息JOB
	 * @author 243921-FOSS-zhangtingting
	 * @date 2015-12-02 上午08:58:03
	 */
	void autoSendArrivalGoods();
}