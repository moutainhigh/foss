package com.deppon.pda.bdm.module.core.shared.util;

/**
 * 字符串工具类
 * @author halley.w
 */
public class StringUtils {
	public static boolean isEmpty(String s){
		return s==null||s.isEmpty();
	}
	
	/*
	 * 过滤字符
	 * 
	 * */
	public static String convert(String field){
		if(field != null){
			field = field.replaceAll("\"", "|");
			field = field.replaceAll("%", "o|o");
			field = field.replaceAll("&", " ");
			field = field.replaceAll("\\+", " ");
			return field+" ";
		}
		return null;
	}
	
}
