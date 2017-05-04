package com.deppon.pda.bdm.module.core.shared.exception;

import com.deppon.pda.bdm.module.core.shared.util.DateUtils;

/**
 * 异常ID生成工具类
 * @author wanghongling
 * @date 2012-09-10
 * @version 1.0
 *
 */
public class ErrIdGeneratorUtil {
	private static int num = 0;
	//同步次数
	private static final int count=10;
	private static String lastPreStr;
	 
	public static synchronized String generateID(){
		String now = DateUtils.getNowTimeStrNoSep();
		if(now.equalsIgnoreCase(lastPreStr)){
			num++;
		}
		lastPreStr = now;
		if(num<count){
			return lastPreStr+"0"+num;
		}else{
			return lastPreStr+num;
		}
	}
}
