/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: pkp-pricing-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/server/util/Constants.java
 * 
 * FILE NAME        	: Constants.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.server.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 读取properties文件定义常量
 * @author 038590-foss-wanghui
 * @date 2012-12-25 上午10:01:04
 */
public class Constants {
	private static final Logger LOGGER = LoggerFactory.getLogger(Constants.class);

	private static final String BASE_NAME = "com.deppon.foss.module.pickup.pricing.api.shared.util.pricing-config";

	private static ResourceBundle resourceBundle;
	
	/**
	 * 加载静态资源
	 */
	static {
		if (resourceBundle == null) {
			try {
				resourceBundle = ResourceBundle.getBundle(BASE_NAME);
			} catch (Exception e) {
				LOGGER.error("", e);
			}
		}
	}

	/**
	 * ResourceBundle键值判断
	 * 
	 * @param resource
	 * @param key
	 * @return String
	 */
	public static String getKeyValue(String key) {
		String retValue = "";
		try {
			retValue = resourceBundle.getString(key);
		} catch (MissingResourceException e) {
			LOGGER.error("", e);
		} catch (Exception e) {
			LOGGER.error("", e);
		}
		return retValue;
	}
}