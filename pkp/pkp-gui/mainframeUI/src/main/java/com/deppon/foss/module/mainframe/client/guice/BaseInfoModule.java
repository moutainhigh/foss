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
 * FILE PATH        	: mainframeUI/src/main/java/com/deppon/foss/module/mainframe/client/guice/BaseInfoModule.java
 * 
 * FILE NAME        	: BaseInfoModule.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.mainframe.client.guice;

import com.deppon.foss.framework.client.component.dataaccess.GuiceModule;
import com.deppon.foss.module.mainframe.client.service.IMenuService;
import com.deppon.foss.module.mainframe.client.service.imp.MenuService;
import com.google.inject.Binder;

/**
 * 绑定
 * @author 026113-foss-linwensheng
 * 
 */
public class BaseInfoModule extends GuiceModule {
	/**
	 * 配置
	 * @author 026113-foss-linwensheng
	 */
	@Override
	public void configure(Binder binder) {
		// 组织信息
		binder.bind(IMenuService.class).to(MenuService.class).asEagerSingleton();
	}

}