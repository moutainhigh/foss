/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 038590-foss-wanghui
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
 * PROJECT NAME	: pkp-order-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/api/shared/util/ApiUtil.java
 * 
 * FILE NAME        	: ApiUtil.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.api.shared.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * GPS地图URL生成工具类
 * @author 038590-foss-wanghui
 * @date 2012-12-25 上午9:56:36
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class ApiUtil {
	// 默认字符集编码
	private static final String DEFAULT_CHARSET = "utf-8";
	// 偏移量
	private static final int XFF = 0XFF;
	private static final String ADD = "0";
	
	/**
	 * 组装请求参数，返回值为key1=value1&key2=value2形式
	 * @param 
	 * @author 038590-foss-wanghui
	 * @date 2012-12-25 上午9:58:36
	 */
	public static String createRequestParam(Map<String, String> map) {
		StringBuilder param = new StringBuilder();

		for (Iterator<Map.Entry<String, String>> it = map.entrySet().iterator(); it.hasNext();) {
			Map.Entry<String, String> e = it.next();
			String value = e.getValue();
			if (value != null) {
				try {
					value = URLEncoder.encode(value, DEFAULT_CHARSET);
				} catch (UnsupportedEncodingException ex) {
				}
				param.append("&").append(e.getKey()).append("=").append(value);
			}
		}
		return param.toString().substring(1);
	}
	
	/**
	 * 二行制转字符串
	 * @param 
	 * @author 038590-foss-wanghui
	 * @date 2012-12-25 上午9:58:46
	 */
	public static String byte2hex(byte[] b) {

		StringBuffer hs = new StringBuffer();
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & XFF));
			if (stmp.length() == 1){
				hs.append(ADD).append(stmp);
			} else {
				hs.append(stmp);
			}
		}
		return hs.toString().toUpperCase();
	}
	
	/**
	 * 签名方法，用于生成签名。
	 * @param params 传给服务器的参数
	 * @param secret 分配给您的APP_SECRET
	 * @author 038590-foss-wanghui
	 * @date 2012-12-25 上午9:58:54
	 */
	public static String sign(Map params, String secret) {
		String result = null;
		if (params == null){
			return result;
		}

		// 将参数按key排序
		Set<String> keys = params.keySet();
		String[] ps = new String[keys.size()];
		int i = 0;
		for (String key : keys) {
			Object value = params.get(key);
			if (value != null) {
				ps[i++] = key + value.toString();
			}
		}
		if (i == 0){
			return result;
		}
		String[] ss = new String[i];
		System.arraycopy(ps, 0, ss, 0, i);	
		Arrays.sort(ss);
		
		// 将secret同时放在头尾，拼成字符串
		StringBuilder orgin = new StringBuilder(secret);
		for (int j = 0; j < ss.length; j++) {
			orgin.append(ss[j]);
		}
		orgin.append(secret);
		
		try {
			// MD5加密
			MessageDigest md = MessageDigest.getInstance("MD5");
			result = byte2hex(md.digest(orgin.toString().getBytes("utf-8")));
		} catch (Exception ex) {
			throw new java.lang.RuntimeException("sign error !", ex);
		}
		return result;
	}

	/**
	 * 得到完整的请求参数组装
	 * @param 
	 * @author 038590-foss-wanghui
	 * @date 2012-12-25 上午9:59:18
	 */
	public static String createFullParam(String secret, Map<String, String> map) {
		// 时间戳
		map.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		// 生成签名
		map.remove("sign");
		String sign = ApiUtil.sign(map, secret);
		// 组装协议参数sign
		map.put("sign", sign);
		
		return ApiUtil.createRequestParam(map);
	}
	
	/**
	 * 
	 * 获得完整的GPS URL
	 * @param 
	 * @author 038590-foss-wanghui
	 * @date 2012-12-25 上午9:59:47
	 */
	public static String getUri(String url, String secret, Map<String, String> map) {
		String result = ApiUtil.createFullParam(secret, map);
		return url + "?" + result;
	}
}