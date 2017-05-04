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
 * PROJECT NAME	: pkp-waybill-share
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/util/PropertiesUtil.java
 * 
 * FILE NAME        	: PropertiesUtil.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.pricing.api.shared.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * 获取配置文件键值
 * 该类用于从foss-config工程中读取配置文件信息
 * 该类在服务器端和gui端都能工作
 * 该类可以读取jar包中的 properties对象
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2013-2-25 下午4:11:40, </p>
 * @author foss-sunrui
 * @date 2013-2-25 下午4:11:40
 * @since
 * @version
 */
public class PropertiesUtil {
	
	/**
	 * 日志初始化
	 */
	private static final Log LOG = LogFactory.getLog(PropertiesUtil.class);

	/**
	 * properties文件处理类
	 * 用于存放从properties文件中读取的键值对
	 * 在启动的时候加载
	 */
	private static Properties properties;
	
	
	/**
	 * clientVersion.properties文件处理类
	 * 用于存放从clientVersion.properties文件中读取的键值对
	 * 在启动的时候加载
	 */
	private static Properties clientVersionProperties;
	
	
	/**
	 * 静态初始化代码块
	 * 在启动类装载的时候读取配置文件
	 */
	static {
		//如果配置信息为空
		if(properties==null){
			//输入流
			InputStream in = null;
			try {
				//从配置文件中 根据class path来读取配置文件
				in = Thread.currentThread().getContextClassLoader() .getResourceAsStream("foss.properties");
				//创建properties对象
				properties = new Properties();
				properties.load(in);//从流中读取properties对象信息
			} catch (Exception e) {
				// 异常处理
				LOG.error("未找到foss.properties配置文件", e);
			} finally {
				if (in != null) {//判断流不是null
					try {
						//关闭流
						in.close();//再finally中关闭流
					} catch (IOException e) {
						//异常处理
						LOG.error("文件流关闭失败", e);
					}
				}
			}
		}
		
		//如果配置信息为空
		if(clientVersionProperties==null){
			//输入流
			InputStream in = null;
			try {
				//从配置文件中 根据class path来读取配置文件
				in = Thread.currentThread().getContextClassLoader() .getResourceAsStream("clientVersion.properties");
				//创建properties对象
				clientVersionProperties = new Properties();
				clientVersionProperties.load(in);//从流中读取properties对象信息
			} catch (Exception e) {
				// 异常处理
				LOG.error("未找到clientVersion.properties配置文件", e);
			} finally {
				if (in != null) {//判断流不是null
					try {
						//关闭流
						in.close();//再finally中关闭流
					} catch (IOException e) {
						//异常处理
						LOG.error("文件流关闭失败", e);
					}
				}
			}
		}
		
	}
	
	/**
	 * properties键值判断
	 * 根据propeties中的key获取value
	 * @param resource
	 * @param key
	 * @return String
	 */
	public static String getKeyValue(String key){
		//得到value
		String retValue = properties.getProperty(key);//从properties中读取value
		if(retValue==null){//如果value是null
			return "";//返回空字符串
		}else{
			return retValue;//否则返回value
		}
	}
	
	
	/**
	 * clientVersion.properties键值判断
	 * 根据clientVersion.propeties中的key获取value
	 * @param resource
	 * @param key
	 * @return String
	 */
	public static String getClientVersionKeyValue(String key){
		//得到value
		String retValue = clientVersionProperties.getProperty(key);//从properties中读取value
		if(retValue==null){//如果value是null
			return "";//返回空字符串
		}else{
			return retValue;//否则返回value
		}
	}
	
}