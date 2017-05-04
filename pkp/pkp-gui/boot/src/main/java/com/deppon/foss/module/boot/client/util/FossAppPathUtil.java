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
 * FILE PATH        	: boot/src/main/java/com/deppon/foss/module/boot/client/util/FossAppPathUtil.java
 * 
 * FILE NAME        	: FossAppPathUtil.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.boot.client.util;

import com.deppon.foss.module.pickup.waybill.shared.util.PropertiesUtil;

import java.io.File;

/**
 * 客户端路径获取对象帮助类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-yangtong,date:2012-10-12 上午9:31:14, </p>
 * @author niujian
 * @date 2012-12-19
 * @since
 * @version
 */
public class FossAppPathUtil {
	
    	/**
	 * 定义一个常量key_app_name
	 */
	public static final String key_app_name = "deppon.foss.app";
	/**
	 * 定义一个常量key_path_login
	 */
	public static final String key_path_login = "login";
	/**
	 * 定义一个常量key_file_remind_set
	 */
	public static final String key_file_remind_set = "remindset.properties";

	public static String getAppLocalPath() {
	    	/**
		 * 获取配置文件key为user.home的值，然后进行追加File.separator和key_app_name并将值赋给path
		 */
		String path = System.getProperty("user.home") 
				+ File.separator
                + PropertiesUtil.getKeyValue("env")
                + File.separator
				+ key_app_name;

		return path;	//返回path
	}
	
	public static String getAppLocalPathForLogin(){
	    	/**
		 * 获取应用本地路径追加File.separator和key_path_login并返回追加后的值
		 */
		return getAppLocalPath() + File.separator + key_path_login;
	}
	
	public static String getRemindSetFileName(){
	    	/**
		 * 获取应用本地路径追加File.separator和key_file_remind_set并返回追加后的值
		 */
		return getAppLocalPath() + File.separator + key_file_remind_set;
	}
}