package com.deppon.foss.print.labelprint.util;

import java.io.*;

public class ClassPathResourceUtil {
    
    public static final String DEFAULT_CONFIG_FILE_NAME = "config.xml"; 
    
    public ClassPathResourceUtil(){
        //System.out.println((getClass().getClassLoader().getResource("")).getPath());
    }
    
    public InputStream getInputStream(String r_fnam){
        InputStream is = null;
        try{
            is = getClass().getResourceAsStream(r_fnam);
            if(is==null){
                is = (getClass().getClassLoader()).getResourceAsStream(r_fnam);
            }
        }catch(Exception e){
            is = null;
            e.printStackTrace();
        }
        return is;
    }
    
    public static void main(String[] args) {
    	ClassPathResourceUtil _ru = new ClassPathResourceUtil();
    	 System.out.println((_ru.getClass().getClassLoader().getResource("demoprt.jasper")));

    }
}
