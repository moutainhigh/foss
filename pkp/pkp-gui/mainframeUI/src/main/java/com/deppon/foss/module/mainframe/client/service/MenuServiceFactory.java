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
 * FILE PATH        	: mainframeUI/src/main/java/com/deppon/foss/module/mainframe/client/service/MenuServiceFactory.java
 * 
 * FILE NAME        	: MenuServiceFactory.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.mainframe.client.service;

import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.module.mainframe.client.service.imp.MenuService;
import com.google.inject.Injector;

/**
 * 菜单服务工厂
 * @author 026113-foss-linwensheng
 *
 */
public class MenuServiceFactory {

	
	public static MenuService getMenuService()
	{
	    	//创建并获取注入对象
		Injector injector = GuiceContextFactroy.getInjector();
		//返回注入对象的一个实例
		return injector.getInstance(MenuService.class);
	}
	
	
	
	
	
}