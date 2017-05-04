package com.deppon.pda.bdm.module.core.shared.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 * @author halley.w
 */
public class DateUtils {

  /**
   * 默认日期格式
   */
  private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
  
  /**
   * 默认无分隔符日期格式
   */
  private static SimpleDateFormat DATE_NO_SEP_FORMAT = new SimpleDateFormat("yyyyMMdd");

  /**
   * 默认日期时间格式
   */
  private static SimpleDateFormat DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  /**
   * 默认无分隔符日期时间格式
   */
  private static SimpleDateFormat DATETIME_NO_SEP_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");

  /**
   * 默认时间格式
   */
  private static SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");

  /**
   * 默认无分隔符时间格式
   */
  private static SimpleDateFormat TIME_NO_SEP_FORMAT = new SimpleDateFormat("HHmmss");
  private static SimpleDateFormat sdfSS = new SimpleDateFormat("ss");
  private static SimpleDateFormat sdfHHmm = new SimpleDateFormat("HHmm");

  /**
   * 
  * @Description: TODO 获取当前系统时间,秒
  * @return
  * @return String    
  * @author mt hyssmt@vip.qq.com
  * @date 2013-10-2 下午3:23:37
   */
  public static String getSS(){
	return sdfSS.format(new Date());
  }
  
  /**
   * 
  * @Description: TODO 获取当前系统时间,时分秒
  * @return
  * @return String    
  * @author mt hyssmt@vip.qq.com
  * @date 2013-10-2 下午3:23:48
   */
  public static String getHHmm(){
	return sdfHHmm.format(new Date());
  }
  /**
   * 返回当前日期格式化字符串
   * 格式为yyyy-MM-dd
   * @return
   */
  public static String getNowDateStr(){
	  return DATE_FORMAT.format(new Date());
  }
  
  /**
   * 返回当前日期日间格式化字符串
   * 格式为yyyy-MM-dd HH:mm:ss
   * @return
   */
  public static String getNowDateTimeStr(){
	  return DATETIME_FORMAT.format(new Date());
  }
  
  /**
   * 返回当前时间格式化字符串
   * 格式为：HH:mm:ss
   * @return
   */
  public static String getNowTimeStr(){
	  return TIME_FORMAT.format(new Date());
  }
  
  /**
   * 格式化日期对象
   * 
   * @param date
   * @return 格式化日期字符串，格式为：yyyy-MM-dd
   */
  public static String formatDate(Date date){
	  return DATE_FORMAT.format(date);
  }
  
  /**
   * 格式化日期时间对象
   * @param date
   * @return 格式化日期字符串，格式为：yyyy-MM-dd HH:mm:ss
   */
  public static String formatDateTime(Date date){
	  return DATETIME_FORMAT.format(date);
  }
  
  /**
   * 格式化时间对象
   * @param date
   * @return 格式化时间字符串，格式为：HH:mm:ss
   */
  public static String formatTime(Date date){
	  return TIME_FORMAT.format(date);
  }
  
  /**
   * 格式化时间戳为日期字符串
   * @param timestamp
   * @return
   */
  public static String formatDate(Timestamp timestamp){
	  return DATE_FORMAT.format(timestamp);
  }
  
  /**
   * 格式化时间戳为日期时间字符串
   * @param timestamp
   * @return
   */
  public static String formatDateTime(Timestamp timestamp){
	  return DATETIME_FORMAT.format(timestamp);
  }
  
  /**
   * 格式化时间戳为时间字符串
   * @param timestamp
   * @return
   */
  public static String formatTime(Timestamp timestamp){
	  return TIME_FORMAT.format(timestamp);
  }
  
  /**
   * 解析日期字符串
   * @param dateStr 日期字符串，格式为：yyyy-MM-dd
   * @return
   * @throws ParseException
   */
  public static Date parseDate(String dateStr) throws ParseException{
	  return DATE_FORMAT.parse(dateStr);
  }
  
  /**
   * 解析日期时间字符串
   * @param dateTimeStr 日期时间字符串，格式为：yyyy-MM-dd HH:mm:ss
   * @return
   * @throws ParseException
   */
  public static Date parseDateTime(String dateTimeStr) throws ParseException{
	  return DATETIME_FORMAT.parse(dateTimeStr);
  }
  
  /**
   * 解析时间字符串
   * @param timeStr 时间字符串，格式为：HH:mm:ss
   * @return
   * @throws ParseException
   */
  public static Date parseTime(String timeStr) throws ParseException{
	  return TIME_FORMAT.parse(timeStr);
  }
  
  /**
   * 解析日期字符串转化为时间戳
   * @param dateStr
   * @return
   * @throws ParseException
   */
  public static Timestamp parseTimestampByDate(String dateStr) throws ParseException{
	  return new Timestamp(DATE_FORMAT.parse(dateStr).getTime());
  }
  
  /**
   * 解析日期时间字符串转化为时间戳
   * @param dateTimeStr
   * @return
   * @throws ParseException
   */
  public static Timestamp parseTimestampByDateTime(String dateTimeStr) throws ParseException{
	  return new Timestamp(DATETIME_FORMAT.parse(dateTimeStr).getTime());
  }
  
  /**
   * 解析时间字符串转化为时间戳
   * @param timeStr
   * @return
   * @throws ParseException
   */
  public static Timestamp parseTimestampByTime(String timeStr) throws ParseException{
	  return new Timestamp(TIME_FORMAT.parse(timeStr).getTime());
  }

  

  
  /**
   * 返回当前日期无分隔符格式化字符串
   * 格式为yyyyMMdd
   * @return
   */
  public static String getNowDateStrNoSep(){
	  return DATE_NO_SEP_FORMAT.format(new Date());
  }
  
  public static String getNowDateStrNoSep(Date date) {
	  return DATE_NO_SEP_FORMAT.format(date);
  }
  
  /**
   * 返回当前日期时间无分隔符格式化字符串
   * 格式为yyyy-MM-dd HH:mm:ss
   * @return
   */
  public static String getNowDateTimeStrNoSep(){
	  return DATETIME_NO_SEP_FORMAT.format(new Date());
  }
  
  /**
   * 返回当前时间无分隔符格式化字符串
   * 格式为：HHmmss
   * @return
   */
  public static String getNowTimeStrNoSep(){
	  return TIME_NO_SEP_FORMAT.format(new Date());
  }
  
  /**
   * 格式化日期对象
   * 
   * @param date
   * @return 格式化日期字符串，格式为：yyyyMMdd
   */
  public static String formatDateNoSep(Date date){
	  return DATE_NO_SEP_FORMAT.format(date);
  }
  
  /**
   * 格式化日期时间对象
   * @param date
   * @return 格式化日期字符串，格式为：yyyyMMddHHmmss
   */
  public static String formatDateTimeNoSep(Date date){
	  return DATETIME_NO_SEP_FORMAT.format(date);
  }
  
  /**
   * 格式化时间对象
   * @param date
   * @return 格式化时间字符串，格式为：HHmmss
   */
  public static String formatTimeNoSep(Date date){
	  return TIME_NO_SEP_FORMAT.format(date);
  }
  
  /**
   * 格式化时间戳为日期字符串
   * @param timestamp
   * @return
   */
  public static String formatDateNoSep(Timestamp timestamp){
	  return DATE_NO_SEP_FORMAT.format(timestamp);
  }
  
  /**
   * 格式化时间戳为日期时间字符串
   * @param timestamp
   * @return
   */
  public static String formatDateTimeNoSep(Timestamp timestamp){
	  return DATETIME_NO_SEP_FORMAT.format(timestamp);
  }
  
  /**
   * 格式化时间戳为时间字符串
   * @param timestamp
   * @return
   */
  public static String formatTimeNoSep(Timestamp timestamp){
	  return TIME_NO_SEP_FORMAT.format(timestamp);
  }
  
  /**
   * 解析日期字符串
   * @param dateStr 日期字符串，格式为：yyyyMMdd
   * @return
   * @throws ParseException
   */
  public static Date parseDateNoSep(String dateStr) throws ParseException{
	  return DATE_NO_SEP_FORMAT.parse(dateStr);
  }
  
  /**
   * 解析日期时间字符串
   * @param dateTimeStr 日期时间字符串，格式为：yyyyMMddHHmmss
   * @return
   * @throws ParseException
   */
  public static Date parseDateTimeNoSep(String dateTimeStr) throws ParseException{
	  return DATETIME_NO_SEP_FORMAT.parse(dateTimeStr);
  }
  
  /**
   * 解析时间字符串
   * @param timeStr 时间字符串，格式为：HHmmss
   * @return
   * @throws ParseException
   */
  public static Date parseTimeNoSep(String timeStr) throws ParseException{
	  return TIME_NO_SEP_FORMAT.parse(timeStr);
  }
  
  /**
   * 解析日期字符串转化为时间戳
   * @param dateStr
   * @return
   * @throws ParseException
   */
  public static Timestamp parseTimestampByDateNoSep(String dateStr) throws ParseException{
	  return new Timestamp(DATE_NO_SEP_FORMAT.parse(dateStr).getTime());
  }
  
  /**
   * 解析日期时间字符串转化为时间戳
   * @param dateTimeStr
   * @return
   * @throws ParseException
   */
  public static Timestamp parseTimestampByDateTimeNoSep(String dateTimeStr) throws ParseException{
	  return new Timestamp(DATETIME_NO_SEP_FORMAT.parse(dateTimeStr).getTime());
  }
  
  /**
   * 解析时间字符串转化为时间戳
   * @param timeStr
   * @return
   * @throws ParseException
   */
  public static Timestamp parseTimestampByTimeNoSep(String timeStr) throws ParseException{
	  return new Timestamp(TIME_NO_SEP_FORMAT.parse(timeStr).getTime());
  }
}
