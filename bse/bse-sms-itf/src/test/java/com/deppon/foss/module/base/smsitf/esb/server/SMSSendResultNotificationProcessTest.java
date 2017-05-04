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
 * PROJECT NAME	: bse-sms-itf
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/smsitf/esb/server/SMSSendResultNotificationProcessTest.java
 * 
 * FILE NAME        	: SMSSendResultNotificationProcessTest.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.smsitf.esb.server;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
/**
 * 用来测试接受短信平台发送回来的"短信发送失败日志信息"
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-11-22 下午1:37:56</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-11-22 下午1:37:56
 * @since
 * @version
 */
@ContextConfiguration(locations = {
	"classpath*:com/deppon/foss/module/base/smsitf/server/META-INF/springTest.xml",
	"classpath*:com/deppon/foss/module/base/smsitf/server/META-INF/spring.xml",
	"classpath*:com/deppon/foss/module/base/baseinfo/api/server/META-INF/spring.xml",
	"classpath*:com/deppon/foss/module/base/common/server/META-INF/spring.xml"
})
public class SMSSendResultNotificationProcessTest extends AbstractJUnit4SpringContextTests {

    private static final Logger logger = LoggerFactory.getLogger(SMSSendResultNotificationProcessTest.class);

    @Ignore
    @Test
    public void testProcess() throws Exception {

	logger.info("****************** ESB Monitor Service Start ******************");

	Object lock = new Object();
	synchronized (lock) {
	    lock.wait();
	}
    }

}
