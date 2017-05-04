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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/ontheway/api/shared/util/ApiUtil.java
 *  
 *  FILE NAME          :ApiUtil.java
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
 * 在途GPS解析工具类
 * @author harry.min(minzaohua@chinawayltd.com)
 *
 */
@SuppressWarnings("unchecked")
public class ApiUtil {
	public final static String DEFAULT_CHARSET = "utf-8";
	public static final String appUrl = Constants.getKeyValue("gpsurl");
	public static final String appKey = Constants.getKeyValue("app_key");
	public static final String appSecret = Constants.getKeyValue("app_secret");
	
	/*
	 * 组装请求参数，返回值为key1=value1&key2=value2形式
	 */
	public static String createRequestParam(Map<String, Object> map) {
		StringBuilder param = new StringBuilder();

		for (Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator(); it.hasNext();) {
			Map.Entry<String, Object> e = it.next();
			Object value = e.getValue();
			if (value != null) {
				try {
					value = URLEncoder.encode(value.toString(), DEFAULT_CHARSET);
				} catch (UnsupportedEncodingException ex) {
				}
				param.append("&").append(e.getKey()).append("=").append(value);
			}
		}
		return param.toString().substring(1);
	}
	
	/*
	 * 二行制转字符串
	 */
	public static String byte2hex(byte[] b) {

		StringBuffer hs = new StringBuffer();
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs.append("0").append(stmp);
			else
				hs.append(stmp);
		}
		return hs.toString().toUpperCase();
	}

	/*
	 * 签名方法，用于生成签名。
	 * 
	 * @param params 传给服务器的参数
	 * 
	 * @param secret 分配给您的APP_SECRET
	 */
	@SuppressWarnings("rawtypes")
	public static String sign(Map params, String secret) {
		String result = null;
		if (params == null)
			return result;

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
		if (i == 0)
			return result;
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
			MessageDigest md = MessageDigest.getInstance("MD5");
			result = byte2hex(md.digest(orgin.toString().getBytes("utf-8")));
		} catch (Exception ex) {
			throw new java.lang.RuntimeException("sign error !");
		}
		return result;
	}


	
	/**
	 * 得到完整的请求参数组装
	 */
	public static String createFullParam(String method, Map<String, Object> map) {
		map.put("app_key", appKey);
		map.put("method", method);
		// 时间戳
		map.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		// 生成签名
		map.remove("sign");
		String sign = ApiUtil.sign(map, appSecret);
		// 组装协议参数sign
		map.put("sign", sign);
		
		return ApiUtil.createRequestParam(map);
	}
	
	/**
	 * 
	 * date类型的转成String类型的
	 * @author IBM-liubinbin
	 * @date 2012-11-8 下午3:04:20
	 */
	public static String getDateStr(Date date)
	{
		if(null!=date)
		{
			SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_1);
			return format.format(date);
		}
		return getCurrentDate();
	}
	
	/**
	 * 
	 * 取得当前日期
	 * 
	 * @author dp-liubinbin
	 * @date 2012-10-24 下午2:23:53
	 */
	private static String getCurrentDate()
	{
		SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_1);
		return format.format(new Date());
	}
	public static final String DATE_FORMAT_1 = "yyyy-MM-dd HH:mm:ss";
	
	public static String getUri(String method, Map<String, Object> map) {
		String result = ApiUtil.createFullParam(method, map);
		return appUrl + "?" + result;
	}
}