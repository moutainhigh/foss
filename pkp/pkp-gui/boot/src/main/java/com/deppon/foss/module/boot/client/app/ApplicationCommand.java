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
 * FILE PATH        	: boot/src/main/java/com/deppon/foss/module/boot/client/app/ApplicationCommand.java
 * 
 * FILE NAME        	: ApplicationCommand.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.boot.client.app;

import java.io.File;
import java.io.FilenameFilter;


/**
 * Application Command 执行对象
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-yangtong,date:2012-10-12 上午9:28:14, </p>
 * @author dp-yangtong
 * @date 2012-10-12 上午9:28:14
 * @since
 * @version
 */
public class ApplicationCommand {

	public static File getLauncherHome(File fLauncherHome) {
	    	/**
	    	 * 判断参数fLauncherHome是否为目录
	    	 */
		if (fLauncherHome.isDirectory())
			return fLauncherHome;	//返回此目录
		/**
	    	 * 返回fLauncherHome的上一级目录
	    	 */
		return fLauncherHome.getParentFile();
	}

	public static String getStartLauncherCommand(String fossLauncherHome) {
	    	/**
	    	 * 创建一个StringBuilder对象
	    	 */
		StringBuilder commandBuilder = new StringBuilder();
		/**
	    	 * 在StringBuilder对象后面追加字符串
	    	 */
		commandBuilder.append(getJavaCommand());		
		commandBuilder.append(" -classpath ");
		commandBuilder.append(fossLauncherHome);
		commandBuilder.append(" com.deppon.foss.client.launch.Launcher");
		commandBuilder.append(getLauncherArgs());
		/**
	    	 * 将StringBuilder对象转换为String类型返回
	    	 */
		return commandBuilder.toString();
	}

	public static String getStartApplicationCommand(String fossHome) {
	    	/**
	    	 * 创建一个StringBuilder对象
	    	 */
		StringBuilder commandBuilder = new StringBuilder();
		/**
	    	 * 在StringBuilder对象后面追加字符串
	    	 */
		commandBuilder.append(getJavaCommand());
		commandBuilder.append(getClassPath(fossHome));
		commandBuilder.append(" org.java.plugin.boot.Boot");
		/**
	    	 * 将StringBuilder对象转换为String类型返回
	    	 */
		return commandBuilder.toString();
	}

	public static Object getLauncherArgs() {
	    	/**
	    	 * 创建一个StringBuilder对象
	    	 */
		StringBuilder argsBuilder = new StringBuilder();
		/**
	    	 * 在StringBuilder对象后面追加字符串
	    	 */
		argsBuilder.append(" --foss_home ");
		argsBuilder.append(getHome());
		argsBuilder.append(" --foss_plugins_home ");
		argsBuilder.append(getPluginsHome());
		/**
	    	 * 将StringBuilder对象转换为String类型返回
	    	 */
		return argsBuilder.toString();
	}
	
	private static String getHome(){
	    	/**
	    	 * 获取配置文件中的foss_home
	    	 */
		return System.getProperty("foss_home");
	}
	private static String getPluginsHome(){
	    	/**
	    	 * 获取配置文件中的foss_plugins_home
	    	 */
		return System.getProperty("foss_plugins_home");
	}

	public static String getClassPath(String fossHome) {
	    	/**
	    	 * 创建一个File对象
	    	 */
		File commonLibsHome = new File(fossHome, "lib");
		/**
	    	 * 判断commonLibsHome是否存在或是否为目录
	    	 */
		if (!commonLibsHome.exists() || !commonLibsHome.isDirectory()) {
		    	/**
		    	 * 如果commonLibsHome不存在或不是目录，则抛出一个异常
		    	 */
			throw new IllegalArgumentException(String.format(
					"%s must exist and must be a directory.", commonLibsHome));
		}
		/**
	    	 * 创建StringBuilder对象
	    	 */
		StringBuilder classPathBuilder = new StringBuilder();
		/**
	    	 * 创建String类型数组
	    	 */
		String[] jars = commonLibsHome.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
			    	/**
			    	 * 判断参数name是否以.jar结尾
			    	 */
				if (name.endsWith(".jar"))
					return true;

				return false;
			}
		});
		/**
	    	 * 创建StringBuilder类型对象
	    	 */
		StringBuilder bd = new StringBuilder();
		/**
	    	 * 返回commonLibsHome的绝对路径
	    	 */
		String sCommonLibsHome = commonLibsHome.getAbsolutePath();
		/**
	    	 * 循环遍历jars
	    	 */
		for (String jar : jars) {
		    	/**
		    	 * 在StringBuilder对象bd后追加字符串
		    	 */
			bd.append("lib\\" + jar + ";");
			/**
		    	 * 判断classPathBuilder的长度是否为0
		    	 */
			if (classPathBuilder.length() != 0) {
			    	/**
			    	 * 在StringBuilder对象classPathBuilder后追加配置文件path.separator获取的字符串
			    	 */
				classPathBuilder.append(System.getProperty("path.separator"));
			}
			/**
		    	 * 在StringBuilder对象classPathBuilder后追加配置文件file.separator获取的字符串和jar
		    	 */
			classPathBuilder.append(sCommonLibsHome
					+ System.getProperty("file.separator") + jar);
		}
		System.out.println(bd.toString());
		/**
	    	 * 判断classPathBuilder的长度是否为0
	    	 */
		if (classPathBuilder.length() != 0) {
		    	/**
		    	 * 将classPathBuilder转为String返回
		    	 */
			return " -classpath " + "\"" + classPathBuilder.toString() + "\"";
		}
		return "";
	}

	public static String getJavaCommand() {
	    	/**
	    	 * 获取配置文件中的java.home，file.separator追加并返回
	    	 */
		return "\"" + System.getProperty("java.home")
				+ System.getProperty("file.separator") + "bin"
				+ System.getProperty("file.separator") + "java\"";
	}
}