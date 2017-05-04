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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/utils/MessageI18nUtil.java
 * 
 * FILE NAME        	: MessageI18nUtil.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.common.client.utils;

import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;

/**
 * 获取异常国际化
 * 
 * @author 026113-foss-linwensheng
 * @date 2013-02-18 上午9:17:13
 */
public class MessageI18nUtil {

	/**
	 * 获取异常国际化（由于在线时也会调用本地的服务，不需要区分在线和离线）
	 * @param e
	 * @param i18n
	 * @return
	 */
	public static String getMessage(BusinessException e, II18n i18n) {
		String message = MessageI18nUtil.returnmessage(e,i18n);
		if (message == null) {
			return e.getMessage();
		}
		return message;
	}
	
	
	
	
	/**
	 * 获取异常国际化（由于在线时也会调用本地的服务，不需要区分在线和离线）
	 * @param e
	 * @param i18n
	 * @return
	 */
	public static String getMessage(II18n i18n, String messageType,
			Object... args) {
		String message = MessageI18nUtil.returnmessage(i18n,messageType,args);
		if (message == null) {
			return messageType;
		}
		return message;
	}
	
	
	
	/**
	 * 获取异常国际化（由于在线时也会调用本地的服务，不需要区分在线和离线）
	 * @param e
	 * @param i18n
	 * @return
	 */
	public static String getMessage(II18n i18n, String messageType) {
		String message = MessageI18nUtil.returnmessage(i18n,messageType);
		if (message == null) {
			return messageType;
		}
		return message;
	}
	
	
	
	

	/**
	 * <p>
	 * 传入消息类型，得到国际化信息后返回"message"
	 * </p>
	 * 
	 * @author 026113-foss-linwensheng
	 * @date 2012-8-30 上午11:27:49
	 * @param messageType
	 *            消息的KEY
	 * @return String message
	 */
	protected static String returnmessage(II18n i18n, String messageType) {
		return returnmessage(i18n, messageType, new Object[0]);
	}

	/**
	 * <p>
	 * 传入消息类型和格式化参数，得到国际化信息后返回"message"
	 * </p>
	 * 
	 * @author 026113-foss-linwensheng
	 * @date 2012-8-30 上午11:28:35
	 * @param messageType
	 *            消息的KEY
	 * @param args
	 *            格式化参数
	 * @return String message
	 */
	protected  static String returnmessage(II18n i18n, String messageType,
			Object... args) {
		String message = null;
		if (StringUtil.isNotBlank(messageType)) {
			message = i18n.get(messageType, args);
		}
		return message;
	}

	/**
	 * 通过传入业务异常，从异常中得到异常类型，得到异常类的国际化信息，并返回"message"
	 * 
	 * @author 026113-foss-linwensheng
	 * @date 2012-8-30 上午11:28:35
	 * @param BusinessException
	 *            业务异常
	 * @return String message
	 */
	protected static String returnmessage(BusinessException e, II18n i18n) {
		String message = i18n.get(e.getErrorCode(), e.getErrorArguments());
		return message;
	}
}