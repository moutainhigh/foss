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
import com.deppon.foss.module.pickup.order.api.shared.dto.OrderHandleDto;

/**
 * 订单处理传给GPSservice
 * @author 045925-YANGBIN
 * @date 2014-03-08 上午9:49:49
 */
public interface IGPSOrderTaskService extends IService {
	/**
	 * 
	* @Title: putOrderTaskToGPS 
	* @Description: PDA确认接货时，讲约车订单发送给GPS进行车辆跟踪
	* @param    orderHandleDto
	* @return boolean    返回类型 
	* @throws
	 */
	public boolean putOrderTaskToGPS(OrderHandleDto orderHandleDto,int optState);
}