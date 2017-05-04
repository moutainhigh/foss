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
 * FILE PATH        	: foss-webstart/src/main/java/com/deppon/foss/client/ClientAppMessages.java
 * 
 * FILE NAME        	: ClientAppMessages.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.client;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 国际化信息源 (JDK1.3兼容)
 */
public class ClientAppMessages {

	/**
	 * 国际化文件名
	 */
	private static final String BUNDLE_NAME = ClientAppMessages.class.getPackage()
			.getName().replace('.', '/')
			+ "/i18n/messages";

	/**
	 * 工具, 私有化构造函数
	 */
	private ClientAppMessages() {
	}

	/**
	 * 获取国际化信息
	 *
	 * @param key
	 *            国际化信息KEY
	 * @return 国际化信息
	 */
	public static String getString(String key) {
		try {
			return ResourceBundle.getBundle(BUNDLE_NAME).getString(key);
		} catch (MissingResourceException e) {
			return "?:" + key;
		}
	}

}