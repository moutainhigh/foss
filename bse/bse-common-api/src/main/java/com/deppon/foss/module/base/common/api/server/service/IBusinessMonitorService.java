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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/api/server/service/IBusinessMonitorService.java
 * 
 * FILE NAME        	: IBusinessMonitorService.java
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
 * FILE    NAME: IBusinessMonitorService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.common.api.server.service;

import java.util.Map;

import com.deppon.foss.module.base.common.api.shared.define.BusinessMonitorIndicator;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;


/**
 * 业务监控服务
 * @author ibm-zhuwei
 * @date 2013-1-29 下午2:52:01
 */
public interface IBusinessMonitorService {

	/**
	 * 简单计数器
	 * 应用场景：单个指标计数器
	 * @author ibm-zhuwei
	 * @date 2013-1-30 上午9:54:35
	 * @param indicator 指标
	 * @param currentInfo 当前操作人信息
	 */
	void counter(BusinessMonitorIndicator indicator, CurrentInfo currentInfo);
	
	/**
	 * 多个计数器
	 * 应用场景：针对一个业务肯能存在多个指标计数器，调用此方法批量计数；例如开单、签收
	 * @author ibm-zhuwei
	 * @date 2013-1-30 上午10:02:56
	 * @param indicators 指标列表【指标,金额/数量】
	 * @param currentInfo 当前操作人信息
	 */
	void counter(Map<BusinessMonitorIndicator, Number> indicators, CurrentInfo currentInfo);
	
	/**
	 * 菜单功能使用计数器
	 * @author ibm-zhuwei
	 * @date 2013-2-27 下午3:13:49
	 * @param resourceCode 功能编码
	 * @param currentInfo 当前操作人信息
	 */
	void counterResource(String resourceCode, CurrentInfo currentInfo);
	
	/**
	 * 用户上线
	 * @author ibm-zhuwei
	 * @date 2013-3-7 上午9:40:44
	 * @param currentInfo 当前操作人信息
	 * @param message 提供在线用户的客户端信息
	 */
	void online(CurrentInfo currentInfo, String message);

	/**
	 * 用户下线
	 * @author ibm-zhuwei
	 * @date 2013-3-7 上午9:40:44
	 * @param currentInfo 当前操作人信息
	 */
	void offline(CurrentInfo currentInfo);
	
}
