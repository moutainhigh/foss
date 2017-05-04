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
 * FILE PATH        	: boot/src/main/java/com/deppon/foss/module/boot/client/plugin/PluginInjectUtil.java
 * 
 * FILE NAME        	: PluginInjectUtil.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.boot.client.plugin;

import java.util.ArrayList;
import java.util.List;

import org.java.plugin.Plugin;
import org.java.plugin.registry.Extension;
import org.java.plugin.registry.ExtensionPoint;

import com.deppon.foss.module.boot.client.app.Application;
 

/**
 * plugin注入帮助类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-yangtong,date:2012-10-12 上午9:31:14, </p>
 * @author dp-yangtong
 * @date 2012-10-12 上午9:31:14
 * @since
 * @version
 */
public class PluginInjectUtil {
	
	public static void inject(Application app){
	    	/**
	    	 * 根据参数app获取插件对象appPlugin
	    	 */
		Plugin appPlugin = app.getApplicationPlugin();
		/**
	    	 * 根据插件对象获取扩展点对象
	    	 */
		ExtensionPoint autoRunExtPoint = appPlugin.getManager().getRegistry().
			getExtensionPoint(appPlugin.getDescriptor().getId(), "plugins-all");
		/**
	    	 * 创建一个Extension类型的List集合
	    	 */
		List<Extension> extensions = new ArrayList<Extension>(autoRunExtPoint.getConnectedExtensions());
		/**
	    	 * 判断extensions的size是否是0，如果是，则返回
	    	 */
		if (extensions.size() == 0) {
			return;
		}
	}
}