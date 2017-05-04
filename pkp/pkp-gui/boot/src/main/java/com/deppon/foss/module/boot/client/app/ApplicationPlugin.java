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
 * FILE PATH        	: boot/src/main/java/com/deppon/foss/module/boot/client/app/ApplicationPlugin.java
 * 
 * FILE NAME        	: ApplicationPlugin.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.boot.client.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.util.Properties;

import org.java.plugin.util.ExtendedProperties;

import com.deppon.foss.framework.client.core.context.ApplicationContext;


/**
 * Application Plugin插件对象
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-yangtong,date:2012-10-12 上午9:28:42, </p>
 * @author dp-yangtong
 * @date 2012-10-12 上午9:28:42
 * @since
 * @version
 */
public class ApplicationPlugin extends org.java.plugin.boot.ApplicationPlugin {
	/**
	 * 定义客户端GUI启动的Application对象
	 */
	private Application application;
	@Override
	protected org.java.plugin.boot.Application initApplication(ExtendedProperties config,
				String[] args) throws Exception {
	    	/**
		 * 获取配置文件foss_db_url
		 */
		String dbProperty = System.getProperty("foss_db_url");
		/**
		 * 判断dbProperty是否为空或是否为空字符串
		 */
		if (dbProperty==null || "".equals(dbProperty)) {
		    	/**
			 * 获取配置文件foss_config_home
			 */
			String conf = System.getProperty("foss_config_home");
			/**
			 * 追加配置文件hsqldb.properties
			 */
			dbProperty = conf + File.separator + "hsqldb.properties";
			/**
			 * 创建一个File对象
			 */
			File dbFile = new File(dbProperty);
			/**
			 * 判断dbFile对象是否存在
			 */
			if(!dbFile.exists()){
			    	/**
				 * 创建一个新的file
				 */
				dbFile.createNewFile();
				/**
				 * 创建一个配置文件对象properties
				 */
				Properties properties = new Properties();
				/**
				 * 向properties里添加数据库连接信息
				 */
				properties.put("driver", "org.hsqldb.jdbc.JDBCDriver");
				properties.put("url", "jdbc:hsqldb:file:database/hsqldb/foss");
				properties.put("username", "SA");
				properties.put("password", "");
				/**
				 * 创建一个输出流对象
				 */
				OutputStream oStream = new FileOutputStream(dbFile);
				/**
				 * 保存属性列表
				 */
				properties.store(oStream, "hsqldb.properties");
				/**
				 * 关闭输出流
				 */
				oStream.close();
			}
			/**
			 * 设置系统属性foss_db_url
			 */
			System.setProperty("foss_db_url",dbFile.getAbsolutePath());
		}
		/**
		 * 创建一个Class对象
		 */
		Class<?> appClass = getManager().getPluginClassLoader(getDescriptor()).loadClass("com.deppon.foss.module.boot.client.app.Application");
		/**
		 * 获取构造对象
		 */
		Constructor<?> constructor = appClass.getConstructor(new Class<?>[0]);
		/**
		 * 获取一个实例
		 */
		application = (Application)constructor.newInstance(new Object[0]);
		/**
		 * 设置实例的ApplicationPlugin
		 */
		application.setApplicationPlugin(this);
		/**
		 * 声明一个布尔型变量，默认为false
		 */
		boolean disableAutoRuns = false;
		/**
		 * 循环遍历args
		 */
		for (String arg : args) {
		    	/**
			 * 判断arg是否等于"--disable_auto_runs"
			 */
			if ("--disable_auto_runs".equals(arg)) {
				disableAutoRuns = true;
			}
		}
		/**
		 * 设置应用属性EnableAutoRuns
		 */
		application.setEnableAutoRuns(!disableAutoRuns);
		/**
		 * 设置应用内容的Application为当前类属性application
		 */
		ApplicationContext.setApplication(application);
		return application;
	}
	@Override
	protected void doStart() throws Exception {
		
	}
	

	@Override
	protected void doStop() throws Exception {}
}