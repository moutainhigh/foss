package com.deppon.pda.bdm.module.core.shared.util;

public class ExceptionFilterUtil {
	private static String BUSINESSEXCEPTION="com.deppon.foss.framework.exception.BusinessException";
	private static String JSONPARSEEXCEPTION="org.codehaus.jackson.JsonParseException";
	private static String JSONFORMATEXCEPTION="com.deppon.pda.bdm.module.core.shared.exception.sys.utilex.JsonFormatException";
	
    public static boolean filterException(String exception){
    	 boolean result = false;
    	if(BUSINESSEXCEPTION.equals(exception)
    			||JSONPARSEEXCEPTION.equals(exception)
    			||JSONFORMATEXCEPTION.equals(exception)){
    		result = true;
    	}
    	return result;
    }
}
