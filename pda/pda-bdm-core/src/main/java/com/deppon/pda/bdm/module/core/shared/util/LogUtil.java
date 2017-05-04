package com.deppon.pda.bdm.module.core.shared.util;

import com.deppon.pda.bdm.module.core.shared.exception.IPdaException;

/**      
 * 日志工具 
 * @author chengang       
 * @version 1.0     
 * @created 2012-9-6 下午05:52:58
 */
public class LogUtil {
	
	/**
	 * <p>日志格式</p>
	 * @param e 异常
	 * @return String 日志信息
	 */
	public static String logFormat(Throwable e){
		StringBuffer buf = new StringBuffer();
		if(e instanceof IPdaException){
			buf.append("[").append(((IPdaException)e).getErrCode()).append("] ")
				.append("[").append(((IPdaException)e).getErrId()).append("]");
		}
		logFormat(e,buf);
		return buf.toString();
	}
	private static String logFormat(Throwable e, StringBuffer buf){
		StackTraceElement[] exTrace = e.getStackTrace();
		buf.append(e.getClass())
			.append(":")
			.append(e.getMessage())
			.append("\r\n");
		if (exTrace != null) {
			for (int i = 0; i < exTrace.length; i++) {
				buf.append("\t").append(exTrace[i].toString())
					.append("\r\n");
			}
		}
		if(e.getCause()!=null){
			buf.append("Caused by: ");
			logFormat(e.getCause(), buf);
		}
		return buf.toString();
	}
}
