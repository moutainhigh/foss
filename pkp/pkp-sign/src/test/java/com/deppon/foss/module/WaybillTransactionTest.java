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
 * PROJECT NAME	: pkp-sign
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/WaybillTransactionTest.java
 * 
 * FILE NAME        	: WaybillTransactionTest.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module;


import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillTransactionEntity;
import com.deppon.foss.module.pickup.sign.server.service.impl.WaybillTransactionService;

public class WaybillTransactionTest 
{
	private WaybillTransactionService waybillTransactionService = null;

	private static ApplicationContext ctx = null;

	String[] xmls = new String[] {
			"com/deppon/foss/module/pickup/sign/test/META-INF/spring.xml"};
		
	@Before
	public void init() throws Exception {
		try {
			if (ctx == null || waybillTransactionService == null) {
				ctx = new ClassPathXmlApplicationContext(xmls);
				waybillTransactionService = ctx.getBean(WaybillTransactionService.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void queryWaybill() 
	{	
		WaybillTransactionEntity waybillTransactionEntity = new WaybillTransactionEntity();
		waybillTransactionEntity.setWaybillNo("20120011");
		Assert.assertNotNull("标识业务完结失败",waybillTransactionService.updateBusinessOver(waybillTransactionEntity));
		Assert.assertNotNull("标识财务完结失败",waybillTransactionService.updateFinanceOver(waybillTransactionEntity));
		Assert.assertNotNull("反标识业务完结失败",waybillTransactionService.updateReverseBusinessOver(waybillTransactionEntity));
		Assert.assertNotNull("反标识财务完结失败",waybillTransactionService.updateReverseFinanceOver(waybillTransactionEntity));
	}
	
	@Test
	public void forList() 
	{	
		List<String> waybillList = new ArrayList<String>();
		waybillList.add("221212121");
		waybillList.add("222222244");
		waybillList.add("222222245");
		//waybillTransactionService.updateReverseFinanceOverforList(waybillList);
		waybillTransactionService.updateFinanceOverforList(waybillList);
		
	}
}