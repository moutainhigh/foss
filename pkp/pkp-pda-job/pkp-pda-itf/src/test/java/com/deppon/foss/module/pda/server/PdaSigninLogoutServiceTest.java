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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/pda/server/PdaSigninLogoutServiceTest.java
 * 
 * FILE NAME        	: PdaSigninLogoutServiceTest.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pda.server;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.deppon.foss.module.pickup.pda.api.server.service.IPdaSigninLogoutService;


/**
 * PDA登录注销接口测试类
 * @author 097972-foss-dengtingting
 *
 */
public class PdaSigninLogoutServiceTest {
	String[] xmls = new String[] {
			"com/deppon/foss/module/pda/test/server/META-INF/spring.xml",
			"com/deppon/foss/module/pda/server/META-INF/spring.xml"
			,"com/deppon/foss/module/pickup/pickup/server/META-INF/spring.xml",
			"com/deppon/foss/module/pickup/predeliver/server/META-INF/spring.xml",
			"com/deppon/foss/module/pickup/order/server/META-INF/spring.xml"
			};
	
	private static ApplicationContext ctx = null;
	
	private IPdaSigninLogoutService pdaSigninLogoutService;
	
	@Before
	public void setUp() throws Exception {
		if(ctx == null){
			ctx = new ClassPathXmlApplicationContext(xmls);
		}
		pdaSigninLogoutService = (IPdaSigninLogoutService) ctx.getBean("pdaSigninLogoutService");
	}

	@Test
	public void testSignIn(){
//		pdaSigninLogoutService.signIn("000002", "097972", "沪A001",new Date(),"司机");
	}
	
	@Test
	public void testLoginOut(){
		pdaSigninLogoutService.loginOut("000001", "097972", "沪A001",new Date());
	}
}