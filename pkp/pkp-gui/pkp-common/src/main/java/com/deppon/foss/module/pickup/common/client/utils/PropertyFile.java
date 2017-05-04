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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/utils/PropertyFile.java
 * 
 * FILE NAME        	: PropertyFile.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.common.client.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import com.deppon.foss.framework.client.core.context.ApplicationContext;

/**
 * 
 * 本地装卸费率读写
 * @author 025000-FOSS-helong
 * @date 2013-2-25 下午03:42:20
 */
public class PropertyFile {
	//装卸费费率
	private static final String PROPERTY_FILE = "serivcefee.properties";
	//根目录
	private String homeDir = "";
	
	
	/**
	 * 
	 * 初始化文件路径
	 * @author 025000-FOSS-helong
	 * @date 2013-2-25 下午02:37:05
	 */
	public PropertyFile()
	{
		homeDir = ApplicationContext.getDBHome();
		File dbHomeDir = new File(ApplicationContext.getDBHome());
		createFolder(dbHomeDir);
	}
	
	/**
	 * 
	 * 创建指定的文件夹
	 * @author 025000-FOSS-helong
	 * @date 2013-2-25 下午02:40:23
	 * @param file
	 */
	private static void createFolder(File file) {
		if (!file.exists() || file.isFile()) {
			file.mkdirs();
		}
	}
	
	/**
	 * 
	 * 根据Key 读取Value
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-2-25 下午02:26:16
	 * @param key
	 * @param value
	 */
	public String readData(String key) {
		Properties props = new Properties();
		try {
			File file = new File(homeDir+File.separator+PROPERTY_FILE);
			if (!file.exists())
				file.createNewFile();
			InputStream in = new BufferedInputStream(new FileInputStream(homeDir+File.separator+PROPERTY_FILE));
			props.load(in);
			in.close();
			String value = props.getProperty(key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * 修改或添加键值对 如果key存在，修改 反之，添加。
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-2-25 下午02:26:16
	 * @param key
	 * @param value
	 */
	public void writeData(String key, String value) {
		Properties prop = new Properties();
		try {
			File file = new File(homeDir+File.separator+PROPERTY_FILE);
			if (!file.exists())
				file.createNewFile();
			InputStream fis = new FileInputStream(file);
			prop.load(fis);
			fis.close();// 一定要在修改值之前关闭fis
			OutputStream fos = new FileOutputStream(homeDir+File.separator+PROPERTY_FILE);
			prop.setProperty(key, value);
			prop.store(fos, "ServiceFeeRate");

			fos.close();
		} catch (IOException e) {
			System.err.println("Visit " + homeDir+PROPERTY_FILE + " for updating " + value + " value error");
		}
	}
}