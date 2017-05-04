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
 * FILE PATH        	: authorization/src/main/java/com/deppon/foss/module/authorization/client/sync/LoginDataSyncProcessor.java
 * 
 * FILE NAME        	: LoginDataSyncProcessor.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.authorization.client.sync;

import org.java.plugin.Plugin;

import com.deppon.foss.framework.client.commons.task.ITaskContext;
import com.deppon.foss.framework.client.component.sync.service.imp.SyncDataBaseLineService;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.core.workbench.IPluginAware;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.boot.client.autorun.IAutoRunner;

/**
 * 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-yangtong,date:2012-10-12 上午9:40:37,content:TODO </p>
 * @author dp-yangtong
 * @date 2012-10-12 上午9:40:37
 * @since
 * @version
 */
public class LoginDataSyncProcessor  implements
		IAutoRunner, IPluginAware {
	//private SyncDataBaseLineService syncDataBaseLineDao;
	private Plugin plugin;



	@Override
	public void execute(ITaskContext context) throws Exception {
		// TODO Auto-generated method stub
		UserEntity user=(UserEntity) SessionContext.getCurrentUser();
		
		user.getId();
		
	}

	@Override
	public void setPlugin(Plugin plugin) {
		// TODO Auto-generated method stub
		this.plugin = plugin;
	}

	public Plugin getPlugin() {
		return plugin;
	}

}