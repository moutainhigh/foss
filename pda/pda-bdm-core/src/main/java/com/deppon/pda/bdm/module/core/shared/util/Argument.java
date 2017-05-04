package com.deppon.pda.bdm.module.core.shared.util;

import java.util.Collection;
import java.util.Map;

import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;

/**
 * 参数工具类
 * @author wanghongling
 * @date 2012-09-10
 * @version 1.0
 *
 */
public class Argument {
	public final static double positiveNum = 0.000001;
	public final static double negativeNum =-0.0000001;
	public static void notNull(Object object, String argName) {
        if (object == null)
            throw new ArgumentInvalidException(argName, ArgumentInvalidException.INVALID_ARG_TYPE.NOT_NULL);
    }
    
    public static void notEmpty(Collection<?> object, String argName) {
        if (object == null || object.size() == 0)
            throw new ArgumentInvalidException(argName, ArgumentInvalidException.INVALID_ARG_TYPE.NOT_EMPTY);
    }

    public static void notEmpty(Map<?, ?> object, String argName) {
        if (object == null || object.size() == 0)
            throw new ArgumentInvalidException(argName, ArgumentInvalidException.INVALID_ARG_TYPE.NOT_EMPTY);
    }

    public static void notEmpty(Object[] object, String argName) {
        if (object == null || object.length == 0)
            throw new ArgumentInvalidException(argName, ArgumentInvalidException.INVALID_ARG_TYPE.NOT_EMPTY);
    }
    
    public static void notEmpty(CharSequence object, String argName) {
        if (object == null || object.length() == 0)
            throw new ArgumentInvalidException(argName, ArgumentInvalidException.INVALID_ARG_TYPE.NOT_EMPTY);
    }

    public static void hasText(String object, String argName) {
        if (object == null || object.length() == 0 || object.trim().length() == 0)
            throw new ArgumentInvalidException(argName, ArgumentInvalidException.INVALID_ARG_TYPE.HAS_TEXT);
    }

    public static void isTrue(boolean object, String argName) {
        if (! object)
            throw new ArgumentInvalidException(argName, ArgumentInvalidException.INVALID_ARG_TYPE.IS_TRUE);
    }

    public static void isFalse(boolean object, String argName) {
        if (object)
            throw new ArgumentInvalidException(argName, ArgumentInvalidException.INVALID_ARG_TYPE.IS_FALSE);
    }
    
    public static void notEmptyElements(Collection<?> object, String argName) {
        notEmpty(object, argName);
        for (Object element : object) {
            if (element instanceof CharSequence)
                notEmpty((CharSequence)element, argName);
            else 
                notNull(element, argName);
        }
    }

    public static void notEmptyElements(Map<?, ?> object, String argName) {
        notEmpty(object, argName);
        for (Object element : object.values()) {
            if (element instanceof CharSequence)
                notEmpty((CharSequence)element, argName);
            else 
                notNull(element, argName);
        }
    }

    public static void notEmptyElements(Object[] object, String argName) {
        notEmpty(object, argName);
        for (Object element : object) {
            if (element instanceof CharSequence)
                notEmpty((CharSequence)element, argName);
            else 
                notNull(element, argName);
        }
    }
    
    public static void isPositiveNum(int num, String argName){
    	if(num<=0){
            throw new ArgumentInvalidException(argName, ArgumentInvalidException.INVALID_ARG_TYPE.IS_POSITIVE);
    	}
    }
    
    public static void isPositiveNum(long num, String argName){
    	if(num<=0){
            throw new ArgumentInvalidException(argName, ArgumentInvalidException.INVALID_ARG_TYPE.IS_POSITIVE);
    	}
    }
    
    public static void isPositiveNum(double num, String argName){
    	if ( num>negativeNum && num< positiveNum ){
            throw new ArgumentInvalidException(argName, ArgumentInvalidException.INVALID_ARG_TYPE.IS_POSITIVE);
    	}
    }
    
    public static void isPositiveNum(float num, String argName){
    	if ( num>negativeNum && num< positiveNum ){
            throw new ArgumentInvalidException(argName, ArgumentInvalidException.INVALID_ARG_TYPE.IS_POSITIVE);
    	}
    }
    
    public static void isZero(int num, String argName){
    	if(num!=0){
            throw new ArgumentInvalidException(argName, ArgumentInvalidException.INVALID_ARG_TYPE.IS_ZERO);
    	}
    }
    
    public static void isZero(long num, String argName){
    	if(num!=0){
            throw new ArgumentInvalidException(argName, ArgumentInvalidException.INVALID_ARG_TYPE.IS_ZERO);
    	}
    }
    
    public static void isZero(double num, String argName){
    	if ( num>negativeNum && num< positiveNum ){
            throw new ArgumentInvalidException(argName, ArgumentInvalidException.INVALID_ARG_TYPE.IS_ZERO);
    	}
    }
    
    public static void isZero(float num, String argName){
    	if ( num>negativeNum && num< positiveNum ){
            throw new ArgumentInvalidException(argName, ArgumentInvalidException.INVALID_ARG_TYPE.IS_ZERO);
    	}
    }
    
    public static void isNegativeNum(int num, String argName){
    	if(num>=0){
            throw new ArgumentInvalidException(argName, ArgumentInvalidException.INVALID_ARG_TYPE.IS_NEGATIVE);
    	}
    }
    
    public static void isNegativeNum(long num, String argName){
    	if(num>=0){
            throw new ArgumentInvalidException(argName, ArgumentInvalidException.INVALID_ARG_TYPE.IS_NEGATIVE);
    	}
    }
    
    public static void isNegativeNum(double num, String argName){
    	if ( num>negativeNum && num< positiveNum ){
            throw new ArgumentInvalidException(argName, ArgumentInvalidException.INVALID_ARG_TYPE.IS_NEGATIVE);
    	}
    }
    
    public static void isNegativeNum(float num, String argName){
    	if ( num>negativeNum && num< positiveNum  ){
            throw new ArgumentInvalidException(argName, ArgumentInvalidException.INVALID_ARG_TYPE.IS_NEGATIVE);
    	} 
    }
}
