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
 * FILE PATH        	: foss-webstart/src/main/java/com/deppon/foss/client/DefaultGUIFTPConnSetupUtil.java
 * 
 * FILE NAME        	: DefaultGUIFTPConnSetupUtil.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class DefaultGUIFTPConnSetupUtil {
	
	private static DefaultGUIFTPConnSetupUtil instance;
	private Properties setDefProperties = null;
	private static final String key_default_ftp_conn_info_file_name = "foss.gui.ftp.properties"; 
	public static final String key_ftp_server = "ftp.server";
	public static final String key_ftp_port = "ftp.port";
	public static final String key_ftp_username = "ftp.username";
	public static final String key_ftp_password = "ftp.password";
			
	private DefaultGUIFTPConnSetupUtil() throws Exception{ initSetDefaultValues(); }
	
	public static DefaultGUIFTPConnSetupUtil getInstance() throws Exception{
		if(instance==null){
			instance = new DefaultGUIFTPConnSetupUtil();
		}
		return instance;
	}

	private void initSetDefaultValues() throws Exception {
		setDefProperties = new Properties();
		
		String path = System.getProperty("user.home") + File.separator + key_default_ftp_conn_info_file_name;
		File f = new File(path);
		if(f.exists()){
			FileInputStream fs = null;
			InputStreamReader is = null;
			try {
				// sonra 高危 不良实践-方法扩展功能可能在关闭流时失败 
				fs = new FileInputStream(f);
				is = new InputStreamReader(fs,"UTF-8");
				setDefProperties.load(is);
			} catch (Exception e) {
				//to do nothing
			} finally {
				try {
					if (is != null) {
						is.close();
					}
					if (fs != null) {
						fs.close();
					}
				}catch (Exception e) {
					//to do nothing
				}
			}
			
		}
		else {
			f.createNewFile();
		}
	}
	
	public void storeSetDefaultValuesToFile() throws Exception {
		String path = System.getProperty("user.home") + File.separator + key_default_ftp_conn_info_file_name;
		File f = new File(path);
		FileOutputStream fo = new FileOutputStream(f);
		setDefProperties.store(fo, "#foss lable print ");
		fo.close();
	}
	
	public void doSetDefaultValue(String pDefaultKey, String pDefaultValue) throws Exception {
		if(pDefaultValue==null){
			removeSetDefaultValue(pDefaultKey);
		}
		else {
			setDefProperties.put(pDefaultKey, pDefaultValue);
		}
		storeSetDefaultValuesToFile();
	}
	
	public String loadSetDefaultValue(String pDefaultKey){
		return (String)setDefProperties.get(pDefaultKey);
	}
	
	public void removeSetDefaultValue(String pDefaultKey){
		setDefProperties.remove(pDefaultKey);
	}
}