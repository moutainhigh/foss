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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/api/server/service/IOrderPreprocessService.java
 * 
 * FILE NAME        	: IOrderPreprocessService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.api.server.service;

import java.util.Map;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderEntity;


/**
 * 
 * @ClassName: IOrderPreprocessExpressService 
 * @Description: 快递预处理服务
 * @author YANGBIN
 * @date 2014-5-4 下午4:15:20 
 *
 */
public interface IOrderPreprocessExpressService extends IService {
	/**
	 * 
	 * @Title: preprocess 
	 * @Description: 处理方法
	 * @param     设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	public void preprocess();

	/**
	 * 
	 * @Title: addExceptionLog 
	 * @Description: 添加异常日志
	 * @param @param dispatchOrderEntity
	 * @param @param e    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	public void addExceptionLog(DispatchOrderEntity dispatchOrderEntity, Exception e);
	
	/**
	 * 
	 * @Title: updateOrderPreprocess 
	 * @Description: 根据GIS返回结果，查询接货小区，更新订单预处理建议
	 * @param @param dispatchOrderEntity
	 * @param @param result    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	public void updateOrderPreprocess(DispatchOrderEntity dispatchOrderEntity, Map<String, Object> result);
}