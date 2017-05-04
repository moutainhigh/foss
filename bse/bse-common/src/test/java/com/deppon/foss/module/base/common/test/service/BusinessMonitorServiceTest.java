/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: bse-common
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/common/test/service/BusinessMonitorServiceTest.java
 * 
 * FILE NAME        	: BusinessMonitorServiceTest.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-common
 * PACKAGE NAME: com.deppon.foss.module.base.common.test.service
 * FILE    NAME: BusinessMonitorServiceTest.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.common.test.service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.common.api.server.service.IBusinessMonitorService;
import com.deppon.foss.module.base.common.api.shared.define.BusinessMonitorIndicator;
import com.deppon.foss.module.base.common.test.BaseTestCase;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;


/**
 * 业务监控服务测试类
 * @author ibm-zhuwei
 * @date 2013-1-31 上午10:28:06
 */
public class BusinessMonitorServiceTest extends BaseTestCase {

	@Autowired
	private IBusinessMonitorService businessMonitorService;

	@Test
	public void testMonitorCounter1() {
		
		businessMonitorService.counter(BusinessMonitorIndicator.BILLING_DELIVERY_COUNT, buildCurrentInfo());
	}

	@Test
	public void testMonitorCounter2() {
		
		Map<BusinessMonitorIndicator, Number> indicators = new HashMap<BusinessMonitorIndicator, Number>();
		indicators.put(BusinessMonitorIndicator.BILLING_DELIVERY_COUNT, 1);
		indicators.put(BusinessMonitorIndicator.BILLING_TOPAY_COUNT, 1);
		indicators.put(BusinessMonitorIndicator.BILLING_TOTAL_AMOUNT, NumberUtils.createBigDecimal("10000.00"));
		indicators.put(BusinessMonitorIndicator.BILLING_COD_AMOUNT, NumberUtils.createBigDecimal("8000.00"));
		
		businessMonitorService.counter(indicators, buildCurrentInfo());
	}

	@Test
	public void testMonitorCounter3() {

		businessMonitorService.counter(BusinessMonitorIndicator.BILLING_DELIVERY_COUNT, buildCurrentInfo());
		
		long begin = System.currentTimeMillis();
		
		int times = 1000;
		for (int i = 0; i < times; ++i) {
    		Map<BusinessMonitorIndicator, Number> indicators = new HashMap<BusinessMonitorIndicator, Number>();
    		indicators.put(BusinessMonitorIndicator.BILLING_DELIVERY_COUNT, 1);
    		indicators.put(BusinessMonitorIndicator.BILLING_TOPAY_COUNT, 2);
    		indicators.put(BusinessMonitorIndicator.BILLING_TOTAL_AMOUNT, NumberUtils.createBigDecimal("10000.00"));
    		indicators.put(BusinessMonitorIndicator.BILLING_COD_AMOUNT, NumberUtils.createBigDecimal("8000.00"));

    		businessMonitorService.counter(indicators, buildCurrentInfo());
		}
		
		long end = System.currentTimeMillis();
		DecimalFormat df = new DecimalFormat("#.########");
		
		logger.info("total seconds: " + df.format((end - begin) / 1000.0)
				+ " , per request seconds: " + df.format((end - begin) / 1000.0 / times));
	}
	
	@Test
	public void testMonitorCounterResource1() {

		businessMonitorService.counterResource("AAA", buildCurrentInfo());
	}

	@Test
	public void testOnline() {
		businessMonitorService.online(buildCurrentInfo(), "test");
		
	}

	@Test
	public void testOffline() {
		businessMonitorService.offline(buildCurrentInfo());
	}

	@Test
	public void testOnline2() {
		businessMonitorService.online(buildCurrentInfo("001", "W011303070309"), "test");
		businessMonitorService.online(buildCurrentInfo("002", "W011303070309"), "test");
		businessMonitorService.online(buildCurrentInfo("003", "W011303070309"), "test");
		businessMonitorService.online(buildCurrentInfo("004", "W011303070309"), "test");
		businessMonitorService.online(buildCurrentInfo("005", "W011303070309"), "test");
		businessMonitorService.online(buildCurrentInfo("006", "W011303070309"), "test");
		businessMonitorService.online(buildCurrentInfo("007", "W011303070309"), "test");
		businessMonitorService.online(buildCurrentInfo("008", "W011303070309"), "test");
		businessMonitorService.online(buildCurrentInfo("009", "W011303070309"), "test");
		
		businessMonitorService.online(buildCurrentInfo("001", "W01"), "test");
	}

	private CurrentInfo buildCurrentInfo() {
		UserEntity user = new UserEntity();
		EmployeeEntity employee = new EmployeeEntity();
		employee.setEmpCode("000123");
		employee.setEmpName("张三");
		user.setEmployee(employee);
		user.setUserName("zhangsan");

		OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
		dept.setCode("W011303070309");
		
		CurrentInfo currentInfo = new CurrentInfo(user, dept);
		return currentInfo;
	}

	private CurrentInfo buildCurrentInfo(String empCode, String orgCode) {
		UserEntity user = new UserEntity();
		EmployeeEntity employee = new EmployeeEntity();
		employee.setEmpCode(empCode);
		user.setEmployee(employee);

		OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
		dept.setCode(orgCode);
		
		CurrentInfo currentInfo = new CurrentInfo(user, dept);
		return currentInfo;
	}
}
