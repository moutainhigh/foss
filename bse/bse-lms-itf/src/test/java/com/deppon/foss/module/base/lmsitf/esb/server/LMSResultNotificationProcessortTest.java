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
 * PROJECT NAME	: bse-lms-itf
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/lmsitf/esb/server/LMSResultNotificationProcessortTest.java
 * 
 * FILE NAME        	: LMSResultNotificationProcessortTest.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.lmsitf.esb.server;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.deppon.foss.framework.server.context.AppContext;
/**
 * LMS数据同步服务监听类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-11-22 下午1:37:41</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-11-22 下午1:37:41
 * @since
 * @version
 */
public class LMSResultNotificationProcessortTest{

    private static final Logger LOGGER = LoggerFactory.getLogger(LMSResultNotificationProcessortTest.class);
    
    protected ApplicationContext appContext = null;
    
    @Before
    public void before(){
	AppContext.initAppContext("application", "http://192.168.17.167/poc", "");
	appContext = new ClassPathXmlApplicationContext(
		"classpath*:com/deppon/foss/module/base/lmsitf/server/META-INF/springTest.xml",
		"classpath*:com/deppon/foss/module/base/lmsitf/server/META-INF/spring.xml",
		"classpath*:com/deppon/foss/module/base/dict/server/META-INF/spring.xml",
		"classpath*:com/deppon/foss/module/base/baseinfo/api/server/META-INF/spring.xml",
		"classpath*:com/deppon/foss/module/base/baseinfo/server/META-INF/spring.xml");
    }

    @Ignore
    @Test
    public void testLMSResultNotificationProcessort() throws Exception {

	LOGGER.info("****************** ESB Monitor Service Start ******************");

	Object lock = new Object();
	synchronized (lock) {
	    lock.wait();
	}
    }
}
