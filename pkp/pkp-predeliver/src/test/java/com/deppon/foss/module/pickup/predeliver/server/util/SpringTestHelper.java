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
 * PROJECT NAME	: pkp-predeliver
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/pickup/predeliver/server/util/SpringTestHelper.java
 * 
 * FILE NAME        	: SpringTestHelper.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.server.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTestHelper {

	protected ApplicationContext appContext = null;
	private static SpringTestHelper instance = new SpringTestHelper();

	private SpringTestHelper() {
		initContext();
	}

	public static SpringTestHelper get() {
		return instance;
	}

	protected void initContext() {
		try {
			appContext = new ClassPathXmlApplicationContext(
					"com/deppon/foss/module/pickup/predeliver/test/META-INF/springTest.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public <T> T getBean(Class<T> clazz){
		if(appContext != null){
			String className = clazz.getSimpleName();
			String startName = className.substring(0,1);
			String otherName = className.substring(1);
			
			String beanName =StringUtils.lowerCase(startName) + otherName;
			return (T) appContext.getBean(beanName);
		}
		return null;
	}

}