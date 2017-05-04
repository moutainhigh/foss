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
 * PROJECT NAME	: pkp-pda-itf
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/pda/server/WayBillNoLocusServiceTest.java
 * 
 * FILE NAME        	: WayBillNoLocusServiceTest.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pda.server;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.pickup.predeliver.api.server.service.IWayBillNoLocusService;

/**
 * 
 * 通知客户DAO测试类
 * 
 * @author ibm-wangfei
 * @date Oct 24, 2012 5:06:00 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations =
{ 
		"classpath*:com/deppon/foss/module/pda/test/server/META-INF/spring.xml",
		"classpath*:com/deppon/foss/module/pickup/predeliver/server/META-INF/spring.xml",
		"classpath*:com/deppon/foss/module/pickup/waybill/server/META-INF/spring.xml",
		"classpath*:com/deppon/foss/module/base/baseinfo/server/META-INF/orgAdministrativeInfo.xml",
		"classpath*:com/deppon/foss/module/base/baseinfo/server/META-INF/administrativeRegions.xml",
		"classpath*:com/deppon/foss/module/base/baseinfo/server/META-INF/financialOrganizations.xml",
		"classpath*:com/deppon/foss/module/base/dict/server/META-INF/dataDictionary.xml",
		"classpath*:com/deppon/foss/module/base/dict/server/META-INF/dataDictionaryCache.xml",
		"classpath*:com/deppon/foss/module/base/dict/server/META-INF/dataDictionaryValue.xml"
})
@TransactionConfiguration
@Transactional
public class WayBillNoLocusServiceTest
{

	@Autowired
	private IWayBillNoLocusService wayBillNoLocusService = null;

	@BeforeTransaction
	public void beforeTransaction()
	{
		wayBillNoLocusService.getWayBillLocusForPda("90909801");
	}

	/**
	 * 
	 * 测试新增运单客户通知记录
	 * 
	 * @author ibm-liubinbin
	 * @date Oct 24, 2012 5:29:55 PM
	 */
	@Test
	@Transactional
	public void testWriteDepartData()
	{
//		sharedService.queryDriverInfoByVehicleNo("123");
	}

}