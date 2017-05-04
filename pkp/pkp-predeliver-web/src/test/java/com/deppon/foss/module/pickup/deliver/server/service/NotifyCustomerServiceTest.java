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
 * PROJECT NAME	: pkp-deliver-web
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/pickup/deliver/server/service/NotifyCustomerServiceTest.java
 * 
 * FILE NAME        	: NotifyCustomerServiceTest.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.deliver.server.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.pickup.deliver.server.BaseTestCase;
import com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyCustomerService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IWaybillArriveTempService;

/**
 * 
 * NotifyCustomerServiceTest
 * @author ibm-wangfei
 * @date Dec 25, 2012 3:17:41 PM
 */
public class NotifyCustomerServiceTest extends BaseTestCase {

	@Autowired
	private INotifyCustomerService notifyCustomerService;
	
	@Autowired
	private IWaybillArriveTempService waybillArriveTempService;

	/**
	 * Test process warehousing timeout.
	 */
	@Test
	public void testProcessWarehousingTimeout() {
		// 运单仓储异常JOB
		notifyCustomerService.processWarehousingTimeout();
	}
	
	/**
	 * Test compute storage charge.
	 */
	@Test
	public void testComputeStorageCharge() {
		// 计算仓储费--定时任务
		//notifyCustomerService.computeStorageCharge();
	}
	
	/**
	 * 
	 * 运单到达数量、到达时间临时表Service
	 * @author ibm-wangfei
	 * @date Dec 27, 2012 10:48:08 AM
	 */
	@Test
	public void testPreprocess() {
		waybillArriveTempService.preprocess();
	}
}