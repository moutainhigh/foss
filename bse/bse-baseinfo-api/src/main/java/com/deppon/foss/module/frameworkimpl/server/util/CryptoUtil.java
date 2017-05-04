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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/frameworkimpl/server/util/CryptoUtil.java
 * 
 * FILE NAME        	: CryptoUtil.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.frameworkimpl.server.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.framework.shared.encypt.base64.BASE64Encoder;
import com.deppon.foss.module.frameworkimpl.shared.exception.EncodeMd5Exception;

/**
 * OA MD5加密方式工具类
 * 
 * @author Administrator
 * 
 */
public class CryptoUtil {

	private static final Log logger = LogFactory.getLog(CryptoUtil.class);

	public CryptoUtil() {
	}

	public static String base64Encode(byte bytes[]) {
		if (bytes == null) {
			return "";
		} else {
			return (new BASE64Encoder()).encode(bytes);
		}
	}

	public static String digestByMD5(String text) {
		if (text == null) {
			return null;
		}
	//	byte digest[] = new byte[0];
		try {
		//	digest = md5(text.getBytes());
			return base64Encode(md5(text.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage(),e);
			throw new EncodeMd5Exception(e);
		}
	}

	private static byte[] md5(byte input[]) throws NoSuchAlgorithmException {
		MessageDigest alg = MessageDigest.getInstance("MD5");
		alg.update(input);
		byte digest[] = alg.digest();
		return digest;
	}
}
