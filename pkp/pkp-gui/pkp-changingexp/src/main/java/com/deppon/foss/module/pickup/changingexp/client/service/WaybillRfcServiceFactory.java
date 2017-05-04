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
 * PROJECT NAME	: pkp-changingexp
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/service/WaybillRfcServiceFactory.java
 * 
 * FILE NAME        	: WaybillRfcServiceFactory.java
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
package com.deppon.foss.module.pickup.changingexp.client.service;

import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.module.pickup.changingexp.client.service.impl.WaybillRfcService;
import com.deppon.foss.module.pickup.changingexp.client.service.impl.WaybillRfcVarificationService;
import com.deppon.foss.module.pickup.common.client.service.ISalesDepartmentService;
import com.deppon.foss.module.pickup.common.client.service.impl.OrgInfoService;
import com.deppon.foss.module.pickup.common.client.service.impl.SalesDepartmentService;
import com.google.inject.Injector;

/**
 * 
 * 维护所有Service对象实例化
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:Administrator,date:2012-10-16 下午05:48:16,
 * </p>
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-10-16 下午05:48:16
 * @since
 * @version
 */
public class WaybillRfcServiceFactory {

	/**
	 * 
	 * 获取变更单Service
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-18 下午8:05:21
	 */
	public static IWaybillRfcService getWaybillRfcService() {
		Injector injector = GuiceContextFactroy.getInjector();
		return injector.getInstance(WaybillRfcService.class);
	}

	/**
	 * 
	 * 获取变更单审核受理Service
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-18 下午8:05:38
	 */
	public static IWaybillRfcVarificationService getWaybillRfcVarificationService() {
		Injector injector = GuiceContextFactroy.getInjector();
		return injector.getInstance(WaybillRfcVarificationService.class);
	}
	
	/**
	 * 
	 * 获取查询营业部Service
	 * @author WangQianJin
	 * @date 2013-05-03
	 */
	public static ISalesDepartmentService getSalesDepartmentService() {
		Injector injector = GuiceContextFactroy.getInjector();
		return injector.getInstance(SalesDepartmentService.class);
	}
	
	/**
	 * 注入组织服务类
	 * @author WangQianJin
	 * @date 2013-06-28
	 */
	public static OrgInfoService getOrgInfoService(){
		Injector injector = GuiceContextFactroy.getInjector();
		return injector.getInstance(OrgInfoService.class);
	}
}