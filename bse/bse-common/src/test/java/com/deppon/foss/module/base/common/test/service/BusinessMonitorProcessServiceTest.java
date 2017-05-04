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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/common/test/service/BusinessMonitorProcessServiceTest.java
 * 
 * FILE NAME        	: BusinessMonitorProcessServiceTest.java
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
 * FILE    NAME: BusinessMonitorProcessServiceTest.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.common.test.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.base.common.api.server.service.IBusinessMonitorProcessService;
import com.deppon.foss.module.base.common.test.BaseTestCase;


/**
 * 业务监控处理服务
 * @author ibm-zhuwei
 * @date 2013-2-2 上午10:42:21
 */
public class BusinessMonitorProcessServiceTest extends BaseTestCase {

	@Autowired
	private IBusinessMonitorProcessService businessMonitorProcessService;

	@Test
	@Rollback(false)
	public void testMonitorProcessService() {
		businessMonitorProcessService.processBusinessMonitor();
	}
}
