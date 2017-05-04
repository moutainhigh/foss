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
 * FILE PATH        	: boot/src/main/java/com/deppon/foss/module/boot/client/app/action/ReloadPluginAction.java
 * 
 * FILE NAME        	: ReloadPluginAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.boot.client.app.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;

import org.java.plugin.JpfException;
import org.java.plugin.PluginManager.PluginLocation;
import org.java.plugin.boot.ApplicationPlugin;
import org.java.plugin.standard.StandardPluginLocation;

 
/**
 * 重新载入插件
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-yangtong,date:2012-10-12 上午9:26:51,content:</p>
 * @author dp-yangtong
 * @date 2012-10-12 上午9:26:51
 * @since
 * @version
 */
public class ReloadPluginAction implements ActionListener {
    	
    	/**
    	 * 定义应用插件对象
    	 */
	ApplicationPlugin plugin;
	
	public ReloadPluginAction(ApplicationPlugin plugin)
	{
	    	/**
	    	 * 将参数plugin的值赋给属性plugin
	    	 */
		this.plugin=plugin;
	}
	
	/**
	 * <p>
	 * 执行重新载入
	 * </p>
	 * @param arg0
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

	    	/**
	    	 * 获取插件管理对象
	    	 */
		org.java.plugin.PluginManager pluginManager = plugin.getManager();
		/**
	    	 * 定义插件路径
	    	 */
		PluginLocation pluginLocation;
		try {
		    	/**
		    	 * 获取插件路径
		    	 */
			pluginLocation = StandardPluginLocation.create(new File("F://FOSS_2//FOSS//new-maven-client//client//appHome//newPlugins//newSamplewaybill"));
			/**
		    	 * 创建插件路径数组
		    	 */
			PluginLocation[] pluginLocations=new StandardPluginLocation[]{(StandardPluginLocation) pluginLocation};
			/**
			 * 发布插件
			 */
			pluginManager.publishPlugins(pluginLocations);
			/**
			 * 设置当前活动的插件
			 */
			pluginManager.activatePlugin("com.deppon.foss.module.newSamplewaybill");
			pluginManager.activatePlugin("com.deppon.foss.module.mainframe");
			
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (JpfException e4) {
			e4.printStackTrace();
		}
		
	}
}