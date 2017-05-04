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
 * PROJECT NAME	: bse-bank-itf
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/bankitf/ServiceTests.java
 * 
 * FILE NAME        	: ServiceTests.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.base.bankitf;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * 启动服务监听
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2012-11-19
 * 上午9:24:28
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2012-11-19 上午9:24:28
 * @since
 * @version
 */
@ContextConfiguration(locations = {
	"classpath:com/deppon/foss/module/base/bank/itf/server/META-INF/spring.xml",
	"classpath:com/deppon/foss/module/base/bank/itf/spring.xml",
	"classpath*:com/deppon/foss/module/base/baseinfo/server/META-INF/regionalVehicle.xml",
	"classpath*:com/deppon/foss/module/base/baseinfo/server/META-INF/orgAdministrativeInfoComplex.xml"})
public class ServiceTests extends AbstractJUnit4SpringContextTests {
    
    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(ServiceTests.class);
    /**
     * <p>启动监听器</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-12-25 上午9:44:04
     * @throws Exception
     * @see
     */
    @Test
    public void testStartMonitor() throws Exception {

	LOGGER.info("=== ESB Monitor Service Start ===");

	Object lock = new Object();
	synchronized (lock) {
	    lock.wait();
	}
    }

}
