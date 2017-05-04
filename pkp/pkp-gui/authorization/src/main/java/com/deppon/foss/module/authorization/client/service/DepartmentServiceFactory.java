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
 * PROJECT NAME	: pkp-gui
 * 
 * FILE PATH        	: authorization/src/main/java/com/deppon/foss/module/authorization/client/service/DepartmentServiceFactory.java
 * 
 * FILE NAME        	: DepartmentServiceFactory.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.authorization.client.service;

import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.google.inject.Injector;


/**
 * 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-yangtong,date:2012-10-12 上午9:39:26,content:TODO </p>
 * @author dp-yangtong
 * @date 2012-10-12 上午9:39:26
 * @since
 * @version
 */
public class DepartmentServiceFactory {
	/**
	 * 
	 * 功能：getDepartmentService
	 * 
	 * @param:
	 * @return DepartmentService
	 * @since:1.6
	 */
	public static DepartmentService getDepartmentService() {
		Injector injector = GuiceContextFactroy.getInjector();
		return injector.getInstance(DepartmentService.class);
	}

}