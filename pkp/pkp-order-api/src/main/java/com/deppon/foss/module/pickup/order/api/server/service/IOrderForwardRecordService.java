/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 038590-foss-wanghui
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
 * PROJECT NAME	: pkp-order-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/api/server/service/IOrderTaskHandleService.java
 * 
 * FILE NAME        	: IOrderTaskHandleService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.order.api.shared.domain.OrderForwardRecordEntity;

public interface IOrderForwardRecordService extends IService {

	/**
	 * 
	 * @Title: insertOrderForward 
	 * @Description: 新增转发记录
	 * @param @param orderForwardRecordEntity
	 * @param @return    设定文件 
	 * @return int    返回类型 
	 * @throws
	 */
	public int insertOrderForward(OrderForwardRecordEntity orderForwardRecordEntity);
	/**
	 * 
	 * @Title: queryOrderForwordByDriverCode 
	 * @Description: 根据快递员工号，以及当天操作时间，查看是否有转发记录 
	 * @param @param orderForwardRecordEntity
	 * @param @return    设定文件 
	 * @return Long    返回类型 
	 * @throws
	 */
	public Long queryOrderForwordByDriverCode(OrderForwardRecordEntity orderForwardRecordEntity);

}