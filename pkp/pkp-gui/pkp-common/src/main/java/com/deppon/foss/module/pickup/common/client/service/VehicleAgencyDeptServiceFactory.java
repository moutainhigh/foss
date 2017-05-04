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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/service/VehicleAgencyDeptServiceFactory.java
 * 
 * FILE NAME        	: VehicleAgencyDeptServiceFactory.java
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

import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.module.pickup.common.client.service.impl.VehicleAgencyDeptService;
import com.google.inject.Injector;

/**
 * (查询偏线和空运代理网点)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-jiangfei,date:2012-11-9 下午7:37:28, </p>
 * @author foss-jiangfei
 * @date 2012-11-9 下午7:37:28
 * @since
 * @version
 */
public class VehicleAgencyDeptServiceFactory {
	public static IVehicleAgencyDeptService getVehicleAgencyDeptService() {
		Injector injector = GuiceContextFactroy.getInjector();
		return injector.getInstance(VehicleAgencyDeptService.class);
	}
}