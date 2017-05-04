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

import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderChangeEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderEntity;


/**
 * 预处理建议处理服务
 * @author 038590-foss-wanghui
 * @date 2012-11-2 下午5:24:15
 */
public interface IOrderPreprocessService extends IService {

	/**
	 * 
	 * 处理方法
	 * @param 
	 * @author 038590-foss-wanghui
	 * @date 2012-11-2 下午5:24:30
	 */
	void preprocess();
	/**
	 * 
	 * 删除待处理记录
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-4-19 上午11:00:31
	 */
	void deleteChange(List<DispatchOrderChangeEntity> changeList);
	/**
	 * 
	 * 添加异常日志
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-4-19 上午10:59:45
	 */
	void addExceptionLog(DispatchOrderEntity dispatchOrderEntity, Exception e);
	
	/**
	 * 
	 * 根据GIS返回结果，查询接货小区，更新订单预处理建议
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-4-19 上午10:50:12
	 */
	void updateOrderPreprocess(DispatchOrderEntity dispatchOrderEntity, Map<String, Object> result);
}