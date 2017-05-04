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
 * FILE PATH        	: mainframeUI/src/main/java/com/deppon/foss/module/mainframe/client/service/IMenuService.java
 * 
 * FILE NAME        	: IMenuService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.mainframe.client.service;

import java.util.Map;

import org.java.plugin.Plugin;

import com.deppon.foss.module.mainframe.client.MenuNodeInfo;

/**
 * 菜单服务
 * @author 026113-foss-linwensheng
 *
 */
public interface IMenuService {
	
	/**
	 * 获取菜单对象
	 * @return  List<MenuNodeInfo>
	 */
	
	Map<String,  MenuNodeInfo>  getMenusData(Plugin plugin);

	
   
	

}