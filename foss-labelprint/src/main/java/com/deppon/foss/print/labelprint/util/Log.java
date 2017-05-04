package com.deppon.foss.print.labelprint.util;

import org.apache.log4j.Logger;

public class Log {
	public static Logger logger = Logger.getLogger("Label Print");
	
	public static boolean isDebugEnabled(){
		return logger.isDebugEnabled();
	}
	
	public static boolean isInfoEnabled(){
		return logger.isInfoEnabled();
	}
	
	public static void debug(String str){
		logger.debug("[ Label Print Log Debug: ]--"+str);
	}
	
	public static void info(String str){
		logger.info("[ Label Print Log Info: ]--"+str);
	}
	
	public static void error(String str){
		logger.error("[ Label Print Log Error: ]--"+str);
	}
	
	public static void debug(String str, Throwable e){
		logger.debug("[ Label Print Log Debug: ]--"+str, e);
	}
	
	public static void info(String str, Throwable e){
		logger.info("[ Label Print Log Info: ]--"+str,e);
	}
	
	public static void error(String str, Throwable e){
		logger.error("[ Label Print Log Error: ]--"+str,e);
	}
}
