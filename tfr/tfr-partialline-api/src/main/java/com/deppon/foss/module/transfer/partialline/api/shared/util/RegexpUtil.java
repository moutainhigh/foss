package com.deppon.foss.module.transfer.partialline.api.shared.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * 正则工具类
 * @author 311396
 *
 */
public class RegexpUtil {
	/**
	 * 输入字符串、正则表达式，将匹配出的字符串返回
	 * @param  toConvertStr String 待转换的字符串
	 * @return string
	 * @author 311396
	 * @date 2016年8月19日08:27:16
	 */
	public static String getMatchString(String toConvertStr, String regex) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(toConvertStr);
		while(m.find()){
			return m.group(1);
		}
		return StringUtils.EMPTY;
	}
	
}
