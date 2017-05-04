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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/server/service/IWaybillRfcJobService.java
 * 
 * FILE NAME        	: IWaybillRfcJobService.java
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

import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodTodoEntity;


/**
 * 运单变更定时任务service
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-10-31 下午2:43:41
 */
public interface IWaybillRfcJobService {

	/**
	 *1 1）系统生成待办事项时货物未入受理部门库存，若（当前时间-货物入库时间）>X，即为处理超时，系统记录处理超时次数；
	 * 2）系统生成待办事项时货物已入受理部门库存，若（当前时间-生成待办事项时间）>X，即为处理超时，系统记录处理超时次数。
     *2.对于处理超时的运单变更，系统每隔Y时间发送一条催促信息给处理部门；
     *3.X与Y的值可在系统中进行配置；
     *4.待办事项处理后，系统不再提示催促信息。
     * 
	 */
	void prepareSendMsg();

	void updateLabeledGoodTodoEntityById(LabeledGoodTodoEntity todoEntity);
	
	
}