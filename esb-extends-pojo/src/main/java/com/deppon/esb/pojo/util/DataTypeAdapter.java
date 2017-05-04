package com.deppon.esb.pojo.util;

import java.util.Calendar;
import java.util.Date;

import javax.xml.bind.DatatypeConverter;

/**
 * 数据类型转换适配器，用于schema和java对象的属性类型转换.
 * 
 * @author HuangHua
 * @date 2012-12-21 上午9:37:21
 */
public final class DataTypeAdapter {

    /**
	 * Instantiates a new data type adapter.
	 */
    private DataTypeAdapter() {
    }

    /**
	 * 字符转为Date.
	 * 
	 * @param s
	 *            the s
	 * @return the date
	 * @author HuangHua
	 * @date 2012-12-21 上午9:38:18
	 */
    public static Date parseDate(String s) {
        if (s == null) {
            return null;
        }
        return DatatypeConverter.parseDate(s).getTime();
    }
    
    /**
	 * Prints the date.
	 * 
	 * @param dt
	 *            the dt
	 * @return the string
	 */
    public static String printDate(Date dt) {
        if (dt == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        return DatatypeConverter.printDate(c);
    }

    /**
	 * 字符转为=time.
	 * 
	 * @param s
	 *            the s
	 * @return the date
	 * @author HuangHua
	 * @date 2012-12-21 上午9:38:38
	 */
    public static Date parseTime(String s) {
        if (s == null) {
            return null;
        }
        return DatatypeConverter.parseTime(s).getTime();
    }
    
    /**
	 * Prints the time.
	 * 
	 * @param dt
	 *            the dt
	 * @return the string
	 */
    public static String printTime(Date dt) {
        if (dt == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        return DatatypeConverter.printTime(c);
    }

    /**
	 * 字符转为datetime.
	 * 
	 * @param s
	 *            the s
	 * @return the date
	 * @author HuangHua
	 * @date 2012-12-21 上午9:39:12
	 */
    public static Date parseDateTime(String s) {
        if (s == null) {
            return null;
        }
        return DatatypeConverter.parseDateTime(s).getTime();
    }
    
    /**
	 * Prints the date time.
	 * 
	 * @param dt
	 *            the dt
	 * @return the string
	 */
    public static String printDateTime(Date dt) {
        if (dt == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        return DatatypeConverter.printDateTime(c);
    }
}