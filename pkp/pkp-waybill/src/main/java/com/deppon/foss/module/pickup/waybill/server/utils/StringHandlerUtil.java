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
 * PROJECT NAME	: pkp-waybill
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/utils/StringHandlerUtil.java
 * 
 * FILE NAME        	: StringHandlerUtil.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-common
 * PACKAGE NAME: com.deppon.foss.module.pickup.common.client.utils
 * FILE    NAME: StringUtil.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.pickup.waybill.server.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.shared.util.string.StringUtil;

/**
 * 字符串操作函数
 * 
 * @author 026123-foss-lifengteng
 * @date 2012-11-12 下午2:38:58
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class StringHandlerUtil {

	private int counter;

	private HashMap map;

	public StringHandlerUtil() {
		map = new HashMap<String, Integer>();
	}

	/**
	 * 在字符串s的左边用字符串replace填充，直到长度为n时为止。如果s长度大于n，则返回s左边n个字符
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-12 下午2:41:01
	 */
	public static String lpad(String s, int n, String replace) {
		// 循环遍历
		while (s.length() < n) {
			// 用replace填充
			s = replace.concat(s);
		}
		// 字符串长度是否大于要求值
		if (s.length() > n) {
			s = s.substring(0, n - 1);
		}
		return s;
	}

	/**
	 * 在字符串s的右边用字符串replace填充，直到长度为n时为止。如果s长度大于n，则返回右边n个字符
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-12 下午2:40:58
	 */
	public static String rpad(String s, int n, String replace) {
		// 循环遍历
		while (s.length() < n) {
			// 用replace填充
			s = s.concat(replace);
		}
		// 字符串长度是否大于要求值
		if (s.length() > n) {
			s = s.substring(s.length() - n, n);
		}
		return s;
	}

	/**
	 * * 用于在hashmap中插入字符串 * @param string
	 */

	public void hashInsert(String string) {
		if (map.containsKey(string)) {
			counter = (Integer) map.get(string);
			map.put(string, ++counter);
		} else {
			map.put(string, 1);
		}
	}

	public Map getHashMap() {
		return map;
	}

	/**
	 * 将字符串转换为字符数组
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-19 下午6:51:23
	 */
	public static char[] strToChar(String str) {
		return StringUtil.defaultIfNull(str).toCharArray();
	}

	// 半角转全角 Char constant
	private static final int BANJIANTOQUANJIANCONSTANT = 65248;

	/**
	 * 半角转全角
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-16 下午8:24:09
	 */
	public static String toSBC(String input) {
		char c[] = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == ' ') {
				c[i] = '\u3000';
			} else if (c[i] < '\177') {
				c[i] = (char) (c[i] + BANJIANTOQUANJIANCONSTANT);

			}
		}
		return new String(c);
	}

	/**
	 * 全角转半角
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-16 下午8:24:31
	 */
	public static String toDBC(String input) {
		char c[] = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == '\u3000') {
				c[i] = ' ';
			} else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
				c[i] = (char) (c[i] - BANJIANTOQUANJIANCONSTANT);

			}
		}
		String returnString = new String(c);
		return returnString;
	}

	/**
	 * 对传入的电话号码根据“、”、“/”、“,”进行解析
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-16 下午8:36:57
	 */
	public static List<String> resolvePhone(String phone) {
		if (phone == null || "".equals(phone)) {
			return null;
		}
		phone = phone.replace('，', ',');
		String[] str = phone.split("、|,|/");
		return Arrays.asList(str);
	}

	/**
	 * 将空对象转换为默认值
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-17 下午3:07:45
	 */
	public static String decode(Object object, String defaultValue) {
		if (object == null) {
			return defaultValue;
		} else {
			return object.toString();
		}
	}

	/**
	 * 将空对象转换为空字符串
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-17 下午3:07:47
	 */
	public static String decode(Object object) {
		return decode(object, "");
	}

	/**
	 * 国家号码段分配如下： 　　 *
	 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188 　　 *
	 * 联通：130、131、132、152、155、156、185、186 　　 * 电信：133、153、180、189、（1349卫通）
	 * 
	 * 修改：以1开头的11位数字
	 * 
	 * 判断是否为手机号码
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 上午11:41:26
	 */
	public static boolean isMobileNO(String mobiles) {
		// String mobile = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
		String mobile = "1[0-9]{10}";
		Pattern p = Pattern.compile(mobile);
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
	
	/**
	 * 解析字符中的电话号码和手机号码
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 下午5:20:32
	 */
	public static Map<String, List<String>> resolveMobileAndPhone(String phone) {
		// 存放解析后的手机和电话号码
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		// 分隔字符串
		List<String> strList = resolvePhone(phone);
		// 电话号码
		List<String> phoneList = new ArrayList<String>();
		// 手机号码
		List<String> mobileList = new ArrayList<String>();

		if (CollectionUtils.isNotEmpty(strList)) {
			// 将解析出的字符串进行分类
			for (String str : strList) {
				if(StringUtils.isEmpty(str)){
					continue;
				}
				if (isMobileNO(str)) {
					mobileList.add(str);
				} else {
					phoneList.add(str);
				}
			}
		}

		map.put("mobiles", mobileList);
		map.put("phones", phoneList);
		return map;

	}
}