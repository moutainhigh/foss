package com.deppon.foss.module.transfer.partialline.api.shared.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 外发工具类
 * @author 257220
 *
 */
public class PartialLineUtil {
	/**
	 * 将回车输入的字符串，转换成List
	 * @param  toConvertStr String 待转换的字符串
	 * @return 把string转为list
	 * @author 257220
	 * @date 2015-9-1上午8:56:25
	 */
	public static List<String> getListFromStr(String toConvertStr) {
		List<String> list = new ArrayList<String>();
		if(StringUtils.isNotEmpty(toConvertStr)) {
			String[] strs = toConvertStr.split("\\n");
			for(String str:strs) {
				if(StringUtils.isNotEmpty(str)) {
					list.add(str);
				}
			}
		}
		return list;
	}
	
	/**
	 * 将集合转换成字符串
	 * @param list
	 * @author gongjp
	 * @date 2015-05-06
	 * @param list
	 * @return String
	 */
	public static String changeArrayToString(List<String> list) {
		StringBuilder sb=new StringBuilder();
		if(list!=null&&list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				String error = list.get(i);
				sb.append(error+",");
				if(i%4==0){
					sb.append("\r\n");
				}
			}
			sb.deleteCharAt(sb.length()-1);
		}
		return sb.toString();
	}
}
