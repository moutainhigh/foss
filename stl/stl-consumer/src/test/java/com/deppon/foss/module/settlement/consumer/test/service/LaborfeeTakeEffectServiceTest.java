/**
 * Copyright 2013 STL TEAM
 */
/*******************************************************************************
 * Copyright 2013 STL TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: stl-consumer
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/consumer/test/service/LaborfeeTakeEffectServiceTest.java
 * 
 * FILE NAME        	: LaborfeeTakeEffectServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.test.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.consumer.test.BaseTestCase;
import com.deppon.foss.module.settlement.consumer.api.server.service.ILaborfeeTakeEffectService;

/**
 * 生效装卸费测试类
 * @author foss-zhangxiaohui
 * @date Dec 6, 2012 8:07:53 PM
 */
public class LaborfeeTakeEffectServiceTest extends BaseTestCase{
	
	//生效装卸费Service
	@Autowired
	private ILaborfeeTakeEffectService laborfeeTakeEffectService;
	
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * 生效装卸费处理
	 * @author foss-zhangxiaohui
	 * @date Dec 7, 2012 11:48:49 AM
	 */
	@Test
	@Rollback(false)
	public void testProcess() {
		
		//日志打印
		this.logger.info("begin test ...");
		
		//生效装卸费Job启动
		laborfeeTakeEffectService.process();
		
		//test结束
		this.logger.info("end test ...");
	}
}
