/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-ontheway-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/ontheway/api/shared/util/Constants.java
 *  
 *  FILE NAME          :Constants.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.ontheway.api.shared.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * 
 * 读取properties文件定义常量
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-sunrui,date:2012-10-22 上午11:23:31,content:TODO
 * </p>
 * 
 * @author foss-sunrui
 * @date 2012-10-22 上午11:23:31
 * @since
 * @version
 */
public class Constants {
	private static final Logger logger = LogManager.getLogger(Constants.class);

	private static final String BASE_NAME = "com.deppon.foss.module.transfer.ontheway.api.shared.util.order-config";

	private static ResourceBundle resourceBundle;

	public final static String SUCCESS = "success";

	static {
		if (resourceBundle == null) {
			try {
				resourceBundle = ResourceBundle.getBundle(BASE_NAME);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("", e);
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
			logger.error("", e);
		} catch (NullPointerException e) {
			logger.error("", e);
		} catch (Exception e) {
			logger.error("", e);
		}
		return retValue;
	}
}