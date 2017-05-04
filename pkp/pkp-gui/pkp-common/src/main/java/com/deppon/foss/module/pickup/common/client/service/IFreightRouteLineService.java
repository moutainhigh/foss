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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/service/IFreightRouteLineService.java
 * 
 * FILE NAME        	: IFreightRouteLineService.java
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
package com.deppon.foss.module.pickup.common.client.service;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteLineEntity;

/**
 * 走货路径线路
 * 
 * @author 105089-foss-yangtong
 * @date 2012-10-24 下午3:40:46
 */
public interface IFreightRouteLineService {

	/**
	 * 按id查询
	 * @param id
	 * @return
	 */
	FreightRouteLineEntity queryById(String id);

	/**
	 * 插条记录
	 * @param freightRouteLine
	 */
	void addFreightRouteLine(FreightRouteLineEntity freightRouteLine);

	/**
	 * 更新条记录
	 * @param freightRouteLine
	 */
	void updateFreightRouteLine(FreightRouteLineEntity freightRouteLine);

	/**
	 * 新增或更新记录
	 * @param freightRouteLine
	 */
	void saveOrUpdate(FreightRouteLineEntity freightRouteLine);

	/**
	 * 查询记录
	 * @return
	 */
	List<FreightRouteLineEntity> queryAll();

	/**
	 * 
	 * <p>
	 * 查询特定走货路径下的所有走货路径线路
	 * </p>
	 * 
	 * @author foss-jiangfei
	 * @date Nov 6, 2012 6:20:14 PM
	 * @return
	 * @see
	 */
	List<FreightRouteLineEntity> queryFreightRouteLineListByFreightRoute(
			String freightRouteVirtualCode);

	/**
	 * 删除
	 * @param freightRouteLineEntity
	 */
	 void delete(FreightRouteLineEntity freightRouteLineEntity);
}