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
 * FILE PATH        	: boot/src/main/java/com/deppon/foss/module/boot/client/autorun/AutoRunUtil.java
 * 
 * FILE NAME        	: AutoRunUtil.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.boot.client.autorun;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.java.plugin.Plugin;
import org.java.plugin.PluginLifecycleException;
import org.java.plugin.registry.Extension;
import org.java.plugin.registry.ExtensionPoint;

import com.deppon.foss.framework.client.commons.exception.SevereException;
import com.deppon.foss.framework.client.core.application.IApplication;
import com.deppon.foss.framework.client.core.application.IApplicationAware;
import com.deppon.foss.framework.client.core.jpf.IPluginIdAware;
import com.deppon.foss.framework.client.core.workbench.IPluginAware;
import com.deppon.foss.framework.client.core.workbench.IWorkbench;
import com.deppon.foss.framework.client.core.workbench.IWorkbenchAware;
import com.deppon.foss.module.boot.client.app.Application;


/**
 * 自动运行任务Util工具类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-yangtong,date:2012-10-12 上午9:30:12, </p>
 * @author dp-yangtong
 * @date 2012-10-12 上午9:30:12
 * @since
 * @version
 */
public class AutoRunUtil {
	/**
	 * 日志对象
	 */
	public static final Log LOG = LogFactory.getLog(Application.class);
	
	
	public static List<AutoRun> scanAutoRuns(Application app){
	    	/**
		 * 获取插件对象
		 */
		Plugin appPlugin = app.getApplicationPlugin();
		/**
		 * 获取扩展点对象
		 */
		ExtensionPoint autoRunExtPoint = appPlugin.getManager().getRegistry().
			getExtensionPoint(appPlugin.getDescriptor().getId(), "auto-run");
		app.getSplash().getProgress().setValue(app.getSplash().getProgress().getValue() + 1);
		/**
		 * 创建一个Extension类型的List集合
		 */
		List<Extension> extensions = new ArrayList<Extension>(autoRunExtPoint.getConnectedExtensions());
		/**
		 * 判断extensions的size是否为0
		 */
		if (extensions.size() == 0) {
			return new ArrayList<AutoRun>();
		}
		/**
		 * 对集合进行排序
		 */
		Collections.sort(extensions, new Comparator<Extension>() {
			public int compare(Extension e1, Extension e2) {
			    	/**
				 * 获取参数order并将值转化为Number类型
				 */
				Number nOrder1 = e1.getParameter("order").valueAsNumber();
				Number nOrder2 = e2.getParameter("order").valueAsNumber();
				/**
				 * 判断nOrder1或nOrder2是否为空
				 */
				if (nOrder1 == null || nOrder2 == null) {
				    	/**
					 * 如果nOrder1或nOrder2为空，则抛出异常
					 */
					throw new IllegalArgumentException("Null order");
				}
				/**
				 * 获取order1和order2，并返回order1 - order2
				 */
				int order1 = nOrder1.intValue();
				int order2 = nOrder2.intValue();
				return order1 - order2;
			}
		});
		/**
		 * 创建AutoRun类型的List集合
		 */
		List<AutoRun> autoRuns = new ArrayList<AutoRun>();
		/**
		 * 循环遍历extensions集合
		 */
		for (Extension extension : extensions) {
		    	/**
			 * 定义AutoRun对象
			 */
			AutoRun autoRun = null;
			try {
			    	/**
				 * 读取autoRun对象
				 */
				autoRun = readAutoRun(appPlugin,extension);
			} catch (Exception e) {
			    	/**
				 * 抛出异常
				 */
				throw new RuntimeException(String.format("Can't load auto runner %s",
						extension.getParameter("class").valueAsString()), e);
			}
			try {
			    	/**
				 * 注入对象
				 */
				injectObjects(autoRun, app, app.getWorkbench(), appPlugin.getManager().getPlugin(extension.getDeclaringPluginDescriptor().getId()));
			} catch (PluginLifecycleException e) {
				LOG.error("throwable catch in application",e);
			}
			/**
			 * 在autoRuns集合中添加元素
			 */
			autoRuns.add(autoRun);
		}
		
		return autoRuns;
	}
	public static void injectObjects(AutoRun autoRun,IApplication app, IWorkbench workbench , Plugin plugin) {
	    	/**
	    	 * 判断autoRun.autoRunner对象是否是IPluginAware的一个实例
	    	 */
		if(autoRun.autoRunner instanceof IPluginAware){
			try {
			    	/**
			    	 * 设置autoRun.autoRunner对象的插件
			    	 */
				((IPluginAware)autoRun.autoRunner).setPlugin(plugin.getManager().getPlugin(autoRun.pluginId));
			} catch (PluginLifecycleException e) {
				throw new SevereException(e);
			}
		}
		/**
	    	 * 判断autoRun.autoRunner对象是否是IPluginIdAware的一个实例
	    	 */
		if(autoRun.autoRunner instanceof IPluginIdAware){
		    	/**
		    	 * 设置autoRun.autoRunner对象的插件Id
		    	 */
			((IPluginIdAware)autoRun.autoRunner).setPluginId(autoRun.pluginId);
		}
		/**
	    	 * 判断autoRun.autoRunner对象是否是IApplicationAware的一个实例
	    	 */
		if(autoRun.autoRunner instanceof IApplicationAware){
		    	/**
		    	 * 设置autoRun.autoRunner对象的Application
		    	 */
			((IApplicationAware)autoRun.autoRunner).setApplication(app);
		}
		/**
	    	 * 判断autoRun.autoRunner对象是否是IWorkbenchAware的一个实例
	    	 */
		if(autoRun.autoRunner instanceof IWorkbenchAware){
		    	/**
		    	 * 设置autoRun.autoRunner对象的Workbench
		    	 */
			((IWorkbenchAware)autoRun.autoRunner).setWorkbench(app.getWorkbench());
		}
	}
	public static AutoRun readAutoRun(Plugin appPlugin,Extension ext) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
	    	/**
	    	 * 创建一个AutoRun对象
	    	 */
		AutoRun autoRun = new AutoRun();
		try {
		    	/**
		    	 * 设置参数appPlugin的活动插件
		    	 */
			appPlugin.getManager().activatePlugin(ext.getDeclaringPluginDescriptor().getId());
		} catch (PluginLifecycleException e) {
		    	/**
		    	 * 抛出异常
		    	 */
			throw new RuntimeException(String.format("Can't activate plugin %s", ext.getDeclaringPluginDescriptor().getId()), e);
		}
		/**
	    	 * 获取参数class的值并赋给变量sAutoRunner
	    	 */
		String sAutoRunner = ext.getParameter("class").valueAsString();
		/**
	    	 * 根据参数appPlugin获取ClassLoader
	    	 */
		ClassLoader classLoader = appPlugin.getManager().getPluginClassLoader(ext.getDeclaringPluginDescriptor());
		/**
	    	 * 创建Class<?>对象
	    	 */
		Class<?> autoRunnerClass = classLoader.loadClass(sAutoRunner);
		/**
	    	 * 判断IAutoRunner接口与autoRunnerClass是否相同
	    	 */
		if (!IAutoRunner.class.isAssignableFrom(autoRunnerClass)) {
		    	/**
		    	 * 如果不相同，则抛出异常
		    	 */
			throw new IllegalArgumentException("Auto runner must implement IAutoRunner interface.");
		}
		/**
	    	 * 获取autoRunnerClass对象的实例
	    	 */
		autoRun.autoRunner = (IAutoRunner)autoRunnerClass.newInstance();
		/**
	    	 * 获取参数halt-on-error的值
	    	 */
		Boolean bHaltOnError = ext.getParameter("halt-on-error").valueAsBoolean();
		/**
	    	 * 判断bHaltOnError是否等于空，如果为空，则将autoRun.haltOnError的值设置为false，否则就设置为bHaltOnError的值
	    	 */
		autoRun.haltOnError = bHaltOnError == null ? false : bHaltOnError;
		/**
	    	 * 获取参数phase的值
	    	 */
		String sPhase = ext.getParameter("phase").valueAsString();
		/**
	    	 * 判断sPhase是否等于"before-login"
	    	 */
		if ("before-login".equals(sPhase)) {
		    	/**
		    	 * 设置autoRun.phase的值为AutoRunPhase.BEFORE_LOGIN
		    	 */
			autoRun.phase = AutoRunPhase.BEFORE_LOGIN;
		} else if ("after-login".equals(sPhase)){
	    	/**
	    	 * 设置autoRun.phase的值为AutoRunPhase.AFTER_LOGIN
	    	 */
			autoRun.phase = AutoRunPhase.AFTER_LOGIN;
		}else if ("background-login".equals(sPhase)){
	    	/**
	    	 * 设置autoRun.phase的值为AutoRunPhase.AFTER_LOGIN
	    	 */
			autoRun.phase = AutoRunPhase.BACKGROUND_LOGIN;
		}
		/**
	    	 * 获取参数order的值
	    	 */
		Number nOrder = ext.getParameter("order").valueAsNumber();
		/**
	    	 * 判断nOrder是否为空，如果为空，则autoRun.order为0，否则，autoRun.order为nOrder的值
	    	 */
		if (nOrder == null) {
			autoRun.order = 0;
		} else {
			autoRun.order = nOrder.intValue();
		}
		/**
	    	 * 获取插件ID
	    	 */
		autoRun.pluginId = ext.getDeclaringPluginDescriptor().getId();
		
		/**
	    	 * 获取参数cancelable
	    	 */
		Extension.Parameter param = ext.getParameter("cancelable");
		/**
	    	 * 定义布尔型变量cancelable，默认为true
	    	 */
		Boolean cancelable =true;
		/**
	    	 * 判断param是否为空，如果不为空，则cancelable设置为param的值
	    	 */
		if(param!=null){
			cancelable= param.valueAsBoolean();
		}
		/**
	    	 * 判断cancelable是否为空，如果为空，则autoRun.cancelable设置为true，否则，autoRun.cancelable设置为cancelable的值
	    	 */
		autoRun.cancelable = cancelable == null ? true : cancelable;
		
		/**
	    	 * 获取参数canRunBackground的值赋给param2
	    	 */
		Extension.Parameter param2= ext.getParameter("canRunBackground");
		Boolean canRunBackground =true;
		/**
	    	 * 判断param2是否为空，如果不为空，则canRunBackground设置为param2的值
	    	 */
		if(param2!=null){
			canRunBackground= param2.valueAsBoolean();
		}
		/**
	    	 * 判断canRunBackground是否为空，如果canRunBackground为空，则autoRun.canRunBackground设置为true，否则，设置为canRunBackground的值
	    	 */
		autoRun.canRunBackground = canRunBackground == null ? true : canRunBackground;
	
		
		return autoRun;
	}
	
	public static void executeAutoRuns(List<AutoRun> autoRuns,AutoRunPhase phase) {
	    	/**
	    	 * 创建一个顺序执行的自动运行任务执行者对象
	    	 */
		SequentialAutoRunExecutor executor = new SequentialAutoRunExecutor(autoRuns, phase);
		/**
	    	 * 调用executor的execute()方法
	    	 */
		executor.execute();
		/**
	    	 * 调用同步方法，使executor处于锁定状态
	    	 */
		synchronized (executor) {
			try {
			    	/**
			    	 * 调用wait()方法，使其处于等待状态
			    	 */
				
				if(AutoRunPhase.AFTER_LOGIN.equals(phase)){
					executor.wait();
				}
			} catch (InterruptedException e) {
				LOG.error("throwable catch in application",e);
			}
		}
		
		/*if (executor.isHalt()) {
			JOptionPane.showMessageDialog(phase == AutoRunPhase.AFTER_LOGIN ? workbench.getFrame() : null,
					"Auto runner can't work. Application will exit.", "Error", JOptionPane.ERROR_MESSAGE);
			
		}*/
	}
}