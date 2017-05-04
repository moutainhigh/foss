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
 * PROJECT NAME	: pkp-waybill-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/server/service/IPickupToDoMsgService.java
 * 
 * FILE NAME        	: IPickupToDoMsgService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.waybill.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.waybill.shared.dto.PickupToDoMsgDto;

/**
 * 查询和操作综合待办消息接口
 * 使用bse-common-api下的 IToDoMsgService
 * @author niujian
 *
 */
public interface IPickupToDoMsgService extends IService {
	
	/**
	 * 
	 * 数据操作方法-根据条件查询待办计数
	 * @author niujian
	 * @date 2012-11-30 下午4:27:35
	 * @param backLogMsg
	 */
	List<PickupToDoMsgDto> queryToDoMsgList(String receiveOrgCode);
	
	/**
	 * 
	 * 数据操作方法-插入一个待办计数 +1
	 * @author niujian
	 * @date 2012-11-30 下午4:27:35
	 * @param backLogMsg
	 */
	void addOneToDoMsg(String businessType, String dealUrl, String receiveRoleCode, String receiveOrgCode, String businessNo, String serialNumber);
	
	/**
	 * 
	 * 数据操作方法-减少一个待办计数 -1
	 * @author niujian
	 * @date 2012-11-30 下午4:27:35
	 * @param backLogMsg
	 */
	void removeOneToDoMsg(String businessType, String receiveOrgCode,String serialNumber);
	
	/**
	 * 
	 * 数据操作方法-更新某种待办计数
	 * @author niujian
	 * @date 2012-11-30 下午4:27:35
	 * @param backLogMsg
	 */
	void updateSomeToDoMsg(String businessType, String receiveRoleCode,
			String receiveOrgCode, String businessNo, List<String> serialNumberlst);
}