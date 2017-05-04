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
 * FILE PATH        	: login/src/main/java/com/deppon/foss/module/login/client/util/BeanToJsonUtil.java
 * 
 * FILE NAME        	: BeanToJsonUtil.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.login.client.util;

import java.io.InputStream;
import java.io.OutputStream;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * 基于json工具对象到json字符串之间的互相转换
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-yangtong,date:2012-10-12 上午9:31:14, </p>
 * @author niujian
 * @date 2012-12-18
 * @since
 * @version
 */
public class BeanToJsonUtil {
	/**
	 * 
	 * 对象转换为JSON
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	public static void beanToJson(OutputStream out, Object obj)	throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		JsonGenerator jsonGenerator = mapper.getJsonFactory()
				.createJsonGenerator(out, JsonEncoding.UTF8);
		if(jsonGenerator!=null){
			jsonGenerator.writeObject(obj);
			jsonGenerator.flush();
			if (!jsonGenerator.isClosed()) {
				jsonGenerator.close();
			}
		}
	}
	/**
	 * 
	 * 转换为对象
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	public static Object jsonToBean(InputStream in, Class clz) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return mapper.readValue(in, clz);
	}
}