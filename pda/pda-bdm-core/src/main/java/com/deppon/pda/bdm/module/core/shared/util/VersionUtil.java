package com.deppon.pda.bdm.module.core.shared.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * TODO(程序版本号工具类)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Administrator,date:2013-5-21 下午10:29:45,content:TODO </p>
 * @author Administrator
 * @date 2013-5-21 下午10:29:45
 * @since
 * @version
 */
public class VersionUtil {
	public static final int num=10000;
	public static final int num1=1;
	public static long parseVersionToLong(String version){
		Pattern pattern = Pattern.compile("^[0-9]{1,4}\\.[0-9]{1,4}\\.[0-9]{1,4}\\.[0-9]{1,4}$");
		Matcher matcher = pattern.matcher(version);
		if(matcher.matches()){
			String[] ver = version.split("\\.");
			long res = 0;
			for (int i = 0; i < ver.length; i++) {
				res = res + Long.parseLong(ver[i])*getMathPower(num, ver.length-num1-i);
			}
			return res;
		}else{
			return -1;
		}
	}
	public static long getMathPower(long source,long index){
		long res = 0;
		if(index==0){
			res = 1;
		}else{
			res = source;
			for (int i = 1; i < index; i++) {
				res = res*source;
			}
		}
		return res;
	}
} 
