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
 * FILE PATH        	: authorization/src/main/java/com/deppon/foss/module/authorization/client/guice/AuthorizationModule.java
 * 
 * FILE NAME        	: AuthorizationModule.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.authorization.client.guice;

import com.deppon.foss.framework.client.component.dataaccess.GuiceModule;
import com.deppon.foss.module.authorization.client.dao.IDepartmentDao;
import com.deppon.foss.module.authorization.client.dao.IUserDao;
import com.deppon.foss.module.authorization.client.dao.IUserFunctionDao;
import com.deppon.foss.module.authorization.client.dao.imp.DepartmentDao;
import com.deppon.foss.module.authorization.client.dao.imp.UserDao;
import com.deppon.foss.module.authorization.client.dao.imp.UserFunctionDao;
import com.deppon.foss.module.authorization.client.service.DepartmentService;
import com.deppon.foss.module.authorization.client.service.IDepartmentService;
import com.google.inject.Binder;


/**
 * AuthorizationModule
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-yangtong,date:2012-10-12 上午9:37:45,content:
 * @author dp-yangtong
 * @date 2012-10-12 上午9:37:45
 * @since
 * @version
 */
public class AuthorizationModule extends GuiceModule {
	/**
	 * 功能：configure
	 * @param:
	 * @return:
	 * @since:1.6
	 */
	@Override
	public void configure(Binder binder) {
		binder.bind(IDepartmentDao.class).to(DepartmentDao.class)
				.asEagerSingleton();

		binder.bind(IDepartmentService.class).to(DepartmentService.class)
				.asEagerSingleton();

		binder.bind(IUserFunctionDao.class).to(UserFunctionDao.class).asEagerSingleton();

		binder.bind(IUserDao.class).to(UserDao.class).asEagerSingleton();
		
	}

}