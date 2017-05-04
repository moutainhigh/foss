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
 * PROJECT NAME	: pkp-creating
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/service/WaybillServiceFactory.java
 * 
 * FILE NAME        	: WaybillServiceFactory.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.creating.client.service;

import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.module.pickup.common.client.service.impl.AdministrativeRegionsService;
import com.deppon.foss.module.pickup.common.client.service.impl.CustomerService;
import com.deppon.foss.module.pickup.common.client.service.impl.OrgInfoService;
import com.deppon.foss.module.pickup.common.client.service.impl.OuterBranchService;
import com.deppon.foss.module.pickup.common.client.service.impl.ProductService;
import com.deppon.foss.module.pickup.common.client.service.impl.SalesDepartmentService;
import com.deppon.foss.module.pickup.creating.client.service.impl.OffLineWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.impl.OnLineWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.impl.SalesDeptWaybillService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.google.inject.Injector;

/**
 * 
 * (维护所有Service对象实例化)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:Administrator,date:2012-10-16 下午05:48:16,
 * </p>
 * 
 * @author 025000-FOSS-helong
 * @date 2012-10-16 下午05:48:16
 * @since
 * @version
 */
public class WaybillServiceFactory {

	public static IWaybillService getWaybillService() {
		if (WaybillConstants.ONLINE_LOGIN.equals(SessionContext.get(WaybillConstants.LOGIN_TYPE).toString())) {
			return new OnLineWaybillService();
		} else {
			return new OffLineWaybillService();
		}
	}
	
	/**本地服务
	 * zxy
	 * @return
	 */
	public static IWaybillService getOfflineWaybillService() {
		return new OffLineWaybillService();
	}

	public static SalesDeptWaybillService getSalesDeptWaybillService() {
		Injector injector = GuiceContextFactroy.getInjector();
		return injector.getInstance(SalesDeptWaybillService.class);
	}
	
	
	/**
	 * 
	 * 功能：注入本地营业部服务类
	 * @param:
	 * @return  
	 * @since:1.6
	 */
	public static SalesDepartmentService getSalesDepartmentService() {
		Injector injector = GuiceContextFactroy.getInjector();
		return injector.getInstance(SalesDepartmentService.class);
	}
	
	/**
	 * 注入本地 偏线空运代理服务类
	 * @author 097972-foss-dengtingting
	 * @date 2013-3-28 上午10:34:06
	 */
	public static OuterBranchService getOuterBranchService(){
		Injector injector = GuiceContextFactroy.getInjector();
		return injector.getInstance(OuterBranchService.class);
	}
	
	/**
	 * 注入本地 行政区域服务类
	 * @author 097972-foss-dengtingting
	 * @date 2013-3-28 上午10:34:06
	 */
	public static AdministrativeRegionsService getAdministrativeRegionsService(){
		Injector injector = GuiceContextFactroy.getInjector();
		return injector.getInstance(AdministrativeRegionsService.class);
	}
	
	/**
	 * 注入本地 产品服务类
	 * @author 097972-foss-dengtingting
	 * @date 2013-3-28 上午10:34:06
	 */
	public static ProductService getProductService(){
		Injector injector = GuiceContextFactroy.getInjector();
		return injector.getInstance(ProductService.class);
	}
	
	/**
	 * 注入组织服务类
	 * @author WangQianJin
	 * @date 2013-05-16
	 */
	public static OrgInfoService getOrgInfoService(){
			Injector injector = GuiceContextFactroy.getInjector();
			return injector.getInstance(OrgInfoService.class);
	}
	
	/**
	 * 注入客户服务类
	 * @author 200945
	 * @return
	 */
	public static CustomerService getCustomerService(){
		Injector injector = GuiceContextFactroy.getInjector();
		return injector.getInstance(CustomerService.class);
	}
}