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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/server/service/IPickupInstationJobMessageService.java
 * 
 * FILE NAME        	: IPickupInstationJobMessageService.java
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

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.waybill.shared.dto.PickupInstationJobMessageDto;

/**
 * Pkp 封装调用综合组提供的站内消息接口
 * @author niujian
 *
 */
public interface IPickupInstationJobMessageService  extends IService {

	/**
	 * 人员对组织的站内消息发送
	 * 调用综合提供的接口bse-common-api.IMessageService
	 * @author niujian
	 * @date 2012-12-28
	 */
	void createBatchInstationMsg(PickupInstationJobMessageDto msgDto); 	
	
	/**
	 * 人员对组织的站内消息发送,发送系统消息，发送目标固定是组织
	 * 调用综合提供的接口bse-common-api.IMessageService
	 * @author niujian
	 * @date 2012-12-28
	 */
	void createSysInstationMsgToOrg(String pReceiveOrgCode, String pContext);
}