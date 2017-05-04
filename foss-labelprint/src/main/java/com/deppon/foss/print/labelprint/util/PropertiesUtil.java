package com.deppon.foss.print.labelprint.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Properties;

public class PropertiesUtil {

    private static Properties m_Properties = null;
    private static final String CONF_FILE = "conf.properties"; 
     
    /**
     * @throws IOException
     * 
     */
    private PropertiesUtil(){
    	
    }
    public static boolean hasInited = false;
    public static void initProperties() throws IOException {
    	if(!hasInited){
	    	System.out.println("//========="+(new Date()).toString()+" [ init Properties ... ] ");
	    	ClassPathResourceUtil _ru = new ClassPathResourceUtil();
	        m_Properties = new Properties();
	        InputStream in = _ru.getInputStream(CONF_FILE);
	        
	        m_Properties.load(new InputStreamReader(in,"UTF-8"));
	        hasInited = true;
    	}
    }
    
    public static String get(String key){
    	if(m_Properties!=null){
    		return m_Properties.getProperty(key);
    	}       
    	return null;
    }
}
