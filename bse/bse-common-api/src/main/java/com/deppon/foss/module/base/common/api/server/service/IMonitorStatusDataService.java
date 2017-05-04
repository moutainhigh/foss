/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: bse-common-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/api/server/service/IMonitorStatusDataService.java
 * 
 * FILE NAME        	: IMonitorStatusDataService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-common-api
 * PACKAGE NAME: com.deppon.foss.module.base.common.api.server.service
 * FILE    NAME: IMonitorStatusDataService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.common.api.server.service;


/**
 * 监控状态数据服务
 * @author ibm-zhuwei
 * @date 2013-2-22 下午2:10:32
 */
public interface IMonitorStatusDataService {

	/**
	 * REDIS持久化在线用户数
	 * @author ibm-zhuwei
	 * @date 2013-3-7 上午11:34:06
	 */
	void processOnlineMonitor();
	
	/**
	 * 模拟登陆各个模块应用
	 * @author ibm-zhuwei
	 * @date 2013-3-19 上午10:34:38
	 */
	void processSimulateLogin();
	
	/**
	 * 监控未处理订单
	 * @author ibm-zhuwei
	 * @date 2013-2-22 下午2:12:54
	 */
	void monitorPendingOrder();
	
}
