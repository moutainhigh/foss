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
 * FILE PATH        	: init/src/main/java/com/deppon/foss/module/init/client/guice/GuiceModuleScanner.java
 * 
 * FILE NAME        	: GuiceModuleScanner.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.init.client.guice;

import java.util.ArrayList;
import java.util.List;

import org.java.plugin.Plugin;
import org.java.plugin.PluginLifecycleException;
import org.java.plugin.registry.Extension;
import org.java.plugin.registry.Extension.Parameter;
import org.java.plugin.registry.ExtensionPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.client.commons.task.ITaskContext;
import com.deppon.foss.framework.client.commons.task.TaskSupport;
import com.deppon.foss.framework.client.core.application.IApplication;
import com.deppon.foss.framework.client.core.application.IApplicationAware;
import com.deppon.foss.framework.client.core.jpf.utils.JpfUtils;
import com.deppon.foss.framework.client.core.workbench.IPluginAware;
import com.deppon.foss.module.boot.client.autorun.IAutoRunner;


/**
 * guice注入机制统一执行类，统一扫描插件
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-yangtong,date:2012-10-12 上午10:11:00, </p>
 * @author dp-yangtong
 * @date 2012-10-12 上午10:11:00
 * @since
 * @version
 */
public class GuiceModuleScanner extends TaskSupport implements IAutoRunner, IPluginAware, IApplicationAware {

	/**
	 * 插件
	 */
	private Plugin plugin;
	/**
	 * 从日志公共，获取到日期管理器
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(GuiceModuleScanner.class);
	/**
	 * 
	 * 执行所有基础了GUIMODULE的接口插件
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	@Override
	public void execute(ITaskContext context) throws Exception {
		/**
		 * 拿到guiceModule的所有扩展点
		 */
		ExtensionPoint extensionPoint = plugin.getDescriptor().getExtensionPoint("guiceModule");
		/**
		 * 创建一个Extension类型的List集合
		 */
		List<Extension> extensions = new ArrayList<Extension>(extensionPoint.getAvailableExtensions());
		/**
		 * 循环遍历List集合
		 */
		for (Extension extension : extensions) {
		    	/**
		    	 * 获取className
		    	 */
			String className = getParameterAsString(extension.getParameter("className"));
			
			try {
			    	/**
			    	 * 创建一个实例
			    	 */
				JpfUtils.createInstance(plugin.getManager().getPlugin(extension.getDeclaringPluginDescriptor().getId()), className);
			} catch (PluginLifecycleException e) {
				LOGGER.error(e.getMessage(),e);
			}

		}

	}

	@Override
	public void setApplication(IApplication application) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setPlugin(Plugin plugin) {
	    	/**
	    	 * 将参数plugin的值赋给属性plugin
	    	 */
		this.plugin = plugin;
	}

	private String getParameterAsString(Parameter parameter) {
	    	/**
	    	 * 判断参数是否为空或空字符串
	    	 */
		if (parameter == null || "".equals(parameter.valueAsString())) {
		    	/**
		    	 * 如果为空或为空字符串，则返回null
		    	 */
			return null;
		}
		/**
		 * 如果不为空则返回参数的值
		 */
		return parameter.valueAsString();
	}
}