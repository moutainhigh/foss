package com.deppon.foss.module.transfer.agent.server.service;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.deppon.foss.framework.cache.CacheManager;
import com.deppon.foss.framework.server.cache.message.MessageCache;
import com.deppon.foss.framework.shared.util.Properties;

public class MessageBundleUtils {

	private static Locale locale = new Locale("zh", "CN");
	
	private static MessageBundleUtils messageBundleUtils = new MessageBundleUtils();
	
	private static String regEx = "[\u4e00-\u9fa5]";   
	
	private static Pattern pat = Pattern.compile(regEx);  
	
	private MessageBundleUtils() {
	}
	
	public static MessageBundleUtils getInstance()	{
		return messageBundleUtils;
	}
	
	public String getMessage(String key, Object... args) {
	    if(key == null) {
	    	return null;
	    }
	    
	    //如果包含中文，直接返回字符串
	    if(isContainsChinese(key)){
	    	return key;
	    }
	    
		MessageCache cache = (MessageCache) CacheManager.getInstance().getCache(MessageCache.UUID);
		
        Properties properties = cache.get(locale.toString());
        if (properties != null) {
          String value = properties.getProperty(key, key);
          if (!value.equals(key)) {
        	  return (args == null || args.length == 0) ? value : MessageFormat.format(value, args);
          }
        }
        
		return key;
	}
	
	private boolean isContainsChinese(String str){    
        Matcher matcher = pat.matcher(str);     
        boolean flg = false;  
        if (matcher.find()) {    
            flg = true;   
        }     
        return flg;     
    }
}
