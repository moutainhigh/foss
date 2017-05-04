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
 * FILE PATH        	: init/src/main/java/com/deppon/foss/module/init/client/sync/guice/SyncModule.java
 * 
 * FILE NAME        	: SyncModule.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.init.client.sync.guice;

import com.deppon.foss.framework.client.component.dataaccess.GuiceModule;
import com.deppon.foss.framework.client.component.sync.dao.ISyncDataBaseLineDao;
import com.deppon.foss.framework.client.component.sync.dao.imp.SyncDataBaseLineDao;
import com.deppon.foss.framework.client.component.sync.service.ISyncDataBaseLineService;
import com.deppon.foss.framework.client.component.sync.service.imp.SyncDataBaseLineService;
import com.deppon.foss.module.init.client.sync.dao.ISynSaleDepartmentDao;
import com.deppon.foss.module.init.client.sync.dao.impl.SynSaleDepartmentDao;
import com.google.inject.Binder;


/**
 * 数据同步功能的注入类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-yangtong,date:2012-10-12 上午11:42:04, </p>
 * @author dp-yangtong
 * @date 2012-10-12 上午11:42:04
 * @since
 * @version
 */
public class SyncModule extends GuiceModule {
	/**
	 * 
	 * 功能：configure 注册bean
	 * @param binder
	 * @author 026113-foss-linwensheng
	 * @date 2013-3-18 下午1:49:42
	 * @see com.google.inject.Module#configure(com.google.inject.Binder)
	 */
	@Override
	public void configure(Binder binder) {
		/**
		 * 同步数据DAO绑定
		 */
		binder.bind(ISyncDataBaseLineDao.class).to(SyncDataBaseLineDao.class)
				.asEagerSingleton();
		/**
		 * 同步数据服务绑定
		 */
		binder.bind(ISyncDataBaseLineService.class)
				.to(SyncDataBaseLineService.class).asEagerSingleton();
		
		binder.bind(ISynSaleDepartmentDao.class).to(SynSaleDepartmentDao.class)
		.asEagerSingleton();
	}

}