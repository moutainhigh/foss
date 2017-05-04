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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/utils/StrUtils.java
 * 
 * FILE NAME        	: StrUtils.java
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
 * FILE    NAME: StringUtils.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.pickup.common.client.utils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.deppon.foss.framework.shared.util.string.StringUtil;

/**
 * 字符处理方法： 1、全半角转换 2、对传入的电话号码根据“、”、“/”、“,”进行解析 3、将空对象转换为字符串
 * 
 * @author 026123-foss-lifengteng
 * @date 2012-11-16 下午8:23:15
 */
public class StrUtils {

	//半角转全角 Char  constant
    private static final int BANJIANTOQUANJIANCONSTANT = 65248;

	/**
     * 半角转全角
     * 
     * @author 026123-foss-lifengteng
     * @date 2012-11-16 下午8:24:09
     */
    public static String ToSBC(String input) {
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
    public static String ToDBC(String input) {
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
	 * 国家号码段分配如下：
　　	 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
　　	 * 联通：130、131、132、152、155、156、185、186
　　	 * 电信：133、153、180、189、（1349卫通）
	 *
	 * 修改：以1开头的11位数字
	 *
	 * 判断是否为手机号码
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 上午11:41:26
	 */
	public static boolean isMobileNO(String mobiles) {
		//String mobile = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
		String mobile = "1[0-9]{10}";
		Pattern p = Pattern.compile(mobile);
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
	
	/**
	 * 将字符串转换为字符数组
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-19 下午6:51:23
	 */
	public static char[] strToChar(String str){
		return StringUtil.defaultIfNull(str).toCharArray();
	}
}