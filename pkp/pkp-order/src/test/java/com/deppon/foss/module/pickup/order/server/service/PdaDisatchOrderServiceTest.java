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
 * PROJECT NAME	: pkp-order
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/pickup/order/server/service/PdaDisatchOrderServiceTest.java
 * 
 * FILE NAME        	: PdaDisatchOrderServiceTest.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.server.service;

import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.deppon.foss.module.pickup.pda.api.server.service.IPdaDispatchOrderService;

public class PdaDisatchOrderServiceTest extends TestCase {

	private IPdaDispatchOrderService pdaDisatchOrderService;

	private static ApplicationContext ctx = null;

	String[] xmls = new String[] { "com/deppon/foss/module/pickup/order/test/META-INF/spring.xml", 
			"com/deppon/foss/module/pickup/order/server/META-INF/spring.xml" };

	@Override
	protected void setUp() throws Exception {
		if (ctx == null) {
			ctx = new ClassPathXmlApplicationContext(xmls);
		}
		pdaDisatchOrderService = (IPdaDispatchOrderService) ctx.getBean("pdaDisatchOrderService");
	}

	@Test
	public void testGetDispatchOrderBy() {
		String driverCode = "033333", vehicleNo = "皖11140";
		// 根据司机工号、车牌号未接订单接口接口
		pdaDisatchOrderService.queryDispatchOrderByVehicle(driverCode, vehicleNo);
	}
}