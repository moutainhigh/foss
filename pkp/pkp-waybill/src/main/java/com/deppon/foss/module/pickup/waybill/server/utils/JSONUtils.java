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
 *  PROJECT NAME  : tfr-common
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/common/server/utils/JSONUtils.java
 *  
 *  FILE NAME          :JSONUtils.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-common
 * PACKAGE NAME: com.deppon.foss.module.transfer.common.server.utils
 * FILE    NAME: JSONUtils.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.pickup.waybill.server.utils;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * 获取objectMapper的工具类
 * @ClassName: JSONUtils 
 * @author 200664-yangjinheng
 * @date 2014年9月25日 上午11:07:27
 */
public class JSONUtils {
	private static ObjectMapper objectMapper;
	
	/**
	 * 获取objectMapper
	 * @Title: obtainObjectMapper 
	 * @author 200664-yangjinheng
	 * @date 2014年9月25日 上午11:07:37
	 * @throws
	 */
	public static ObjectMapper obtainObjectMapper() {
		if(objectMapper==null){
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper;
		}else{
			return objectMapper;
		}
	}
}