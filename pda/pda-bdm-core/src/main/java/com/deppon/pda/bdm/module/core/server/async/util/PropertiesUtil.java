package com.deppon.pda.bdm.module.core.server.async.util;

import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesUtil {
	private static Logger log = Logger.getLogger(PropertiesUtil.class);
	public static int getInt(Properties prop, String name, int defaultValue){
		String value = prop.getProperty(name);
		try{
			if(value!=null && Integer.valueOf(value)>0){
				return Integer.valueOf(value);
			}
		}catch (NumberFormatException e) {
			log.error("The property["+name+"] is not valid int value!");
		}
		return defaultValue;
	}
	
	public static int getInt(Properties prop, String name){
		return getInt(prop,name,0);
	}
	
	public static double getDouble(Properties prop, String name, int defaultValue){
		String value = prop.getProperty(name);
		try{
			if(value!=null && Double.valueOf(value)>0){
				return Double.valueOf(value);
			}
		}catch (NumberFormatException e) {
			log.error("The property["+name+"] is not valid double value!");
		}
		return defaultValue;
	}
	
	public static double getDouble(Properties prop, String name){
		return getDouble(prop,name,0);
	}
}
