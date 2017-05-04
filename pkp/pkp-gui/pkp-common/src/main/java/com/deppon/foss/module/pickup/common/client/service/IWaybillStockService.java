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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/service/IWaybillStockService.java
 * 
 * FILE NAME        	: IWaybillStockService.java
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

import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;

/**
 * 
 * 获取始发库存code 和 最终只有网点库存部门
 * 
 * @author 026113-foss-linwensheng
 * @date 2012-11-27 下午2:51:50
 */
public interface IWaybillStockService {

	/**
	 * 
	 * 获取始发库存部门 1、通过组织表进行查询，如果查询不到，抛异常
	 * 2、如果查询到，查看是否是集中开单部门还是驻地部门还是其他部门，如果是集中开单部门，
	 * 那么查询出对应的始发配载部门，如果是驻地部门，查询出驻地部门外场，如果两者都不是，返回createOrgCode
	 * 
	 * @author 026113-foss-linwensheng
	 * @date 2012-11-27 下午2:27:51
	 * @see com.deppon.foss.module.pickup.waybill.server.service.IWaybillStockService#queryStartStocksDepartmentService(java.lang.String)
	 */
	String queryStartStocksDepartmentService(WaybillEntity waybillEntry);

	/**
	 * 最终库存部门： 判断最终配载部门是否是驻地部门，如果是，设置为对于外场，其他都设置为最终配载部门
	 * 
	 * @author 026113-foss-linwensheng
	 * @date 2012-11-27 下午8:08:40
	 */
	String queryEndStocksDepartmentService(WaybillEntity waybillEntry);

}