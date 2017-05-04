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
 * PROJECT NAME	: stl-crm-itf
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/crmitf/server/esb/StartupESBEnvTest.java
 * 
 * FILE NAME        	: StartupESBEnvTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.crmitf.server.esb;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = {
		"classpath:com/deppon/foss/module/settlement/crmitf/server/META-INF/spring.xml",
		"classpath:com/deppon/foss/module/settlement/crmitf/test/spring.xml",
		"classpath:com/deppon/foss/module/settlement/common/server/META-INF/spring.xml",
		"classpath:com/deppon/foss/module/settlement/consumer/server/META-INF/spring.xml"})
public class StartupESBEnvTest extends AbstractJUnit4SpringContextTests {

	private final Logger logger = LogManager.getLogger(getClass());

	@Test
	@Ignore
	public void testStartup() throws Exception {

		logger.info("=== ESB Monitor Service Start ===");

		Object lock = new Object();
		synchronized (lock) {
			lock.wait();
		}

	}

}
