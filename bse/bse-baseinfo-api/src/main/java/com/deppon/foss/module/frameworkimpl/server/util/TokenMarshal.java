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
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/frameworkimpl/server/util/TokenMarshal.java
 * 
 * FILE NAME        	: TokenMarshal.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.frameworkimpl.server.util;

import org.apache.commons.codec.binary.Base64;

import com.deppon.foss.module.frameworkimpl.shared.domain.Token;

/**
 * 令牌序列化与反序列化
 * 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:ningyu,date:2012-11-28 下午9:02:21</p>
 * @author ningyu
 * @date 2012-11-28 下午9:02:21
 * @since
 * @version
 */
final public class TokenMarshal {
	private TokenMarshal(){
	}

	/**
	 * 序列化(Base64编码)
	 * 
	 * @author ningyu
	 * @date 2012-11-28 下午9:02:40
	 * @param token
	 * @return
	 * @see
	 */
	public static String marshal(Token token) {
		return new String(Base64.encodeBase64String(token.toBytes()));
	}

	/**
	 * 反序列化（Base64解码）
	 * 
	 * @author ningyu
	 * @date 2012-11-28 下午9:02:32
	 * @param tokenStr
	 * @return
	 * @see
	 */
	public static Token unMarshal(String tokenStr) {
		return new Token(Base64.decodeBase64(tokenStr));
	}
}
