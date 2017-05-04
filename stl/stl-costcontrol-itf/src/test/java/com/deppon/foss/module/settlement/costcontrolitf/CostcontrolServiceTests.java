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
 * PROJECT NAME	: stl-costcontrol-itf
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/costcontrolitf/CostcontrolServiceTests.java
 * 
 * FILE NAME        	: CostcontrolServiceTests.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.costcontrolitf;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = {
		"classpath:com/deppon/foss/module/settlement/common/server/META-INF/spring.xml",
		"classpath:com/deppon/foss/module/settlement/costcontrolitf/server/META-INF/spring.xml",
		"classpath:com/deppon/foss/module/settlement/pay/server/META-INF/spring.xml",
		"classpath:com/deppon/foss/module/settlement/costcontrolitf/spring.xml" })
public class CostcontrolServiceTests extends AbstractJUnit4SpringContextTests {

	private static final Logger logger = LogManager
			.getLogger(CostcontrolServiceTests.class);


	/**
	 * 
	 *启动监控程序
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-5 下午2:51:27
	 */
	@Test
	@Ignore
	public void testStartEsbMonitor() throws Exception {

		logger.info("=== ESB Monitor Service Start ===");

		Object lock = new Object();
		synchronized (lock) {
			lock.wait();
		}
	}
	
}
