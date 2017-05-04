/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  沪A123457
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-arrival
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/arrival/server/handle/ArrivalHandle.java
 *  
 *  FILE NAME          :ArrivalHandle.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.arrival.server.handle;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
/**
 * 
 * 一些公用的方法集合
 * 
 * @author foss-liubinbin(for IBM)
 * @date 2012-12-24 下午6:48:56
 */
public class ArrivalHandle {
	/***** 时间格式 *****/
	public static final String DATE_FORMAT_1 = "yyyy-MM-dd HH:mm:ss";
	/***** 时间格式 *****/
	public static final String DATE_FORMAT_2 = "yyyy-MM-dd";
	/**
	 * 
	 * 字符串类型的转成date类型的
	 * 
	 * @author IBM-liubinbin
	 * @date 2012-11-8 下午3:01:50
	 */
	public static Date getDate(String strDate) {
		if ("".equals(strDate) || null == strDate)
			return null;
		return ArrivalHandle.getDate(strDate, ArrivalHandle.DATE_FORMAT_1);
	}
	/**
	 * 
	 * 转换日期为指定的模版类型
	 * 
	 * @author IBM-liubinbin
	 * @date 2012-11-8 下午3:02:56
	 */
	public static Date getDate(String strDate, String format) {
		SimpleDateFormat objSimpleDateFormat = new SimpleDateFormat(format);
		Date objDate = null;

		try {
			objDate = (Date) objSimpleDateFormat.parse(strDate);
		} catch (java.text.ParseException e) {
			// 日期比较不正确
			throw new TfrBusinessException("日期比较不正确");
		}
		return objDate;
	}
	/**
	 * 
	 * date类型的转成String类型的
	 * 
	 * @author IBM-liubinbin
	 * @date 2012-11-8 下午3:04:20
	 */
	public static String getDateStr(Date date) {
		if (null != date) {
			SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_1);
			return format.format(date);
		}
		return "";
	}
	/**
	 * 
	 * 取得当前日期
	 * 
	 * @author dp-liubinbin
	 * @date 2012-10-24 下午2:23:53
	 */
	public static Date getCurrentDate() {
		return new Date();
	}
	/**
	 * 
	 * 放行时更新任务车辆明细
	 * 
	 * @author dp-liubinbin
	 * @date 2012-10-24 下午2:23:53
	 */
	public static String getCurrentOrgCode() {
		return FossUserContext.getCurrentDeptCode();
	}
	private static Calendar fromCal = Calendar.getInstance();
	/**
	 * 
	 * 给日期增加小时数
	 * 
	 * @author IBM-liubinbin
	 * @date 2012-11-8 下午2:44:08
	 */
	public static Date adddate(Date date, int addHour, int min) {
		// 只根据日期来比较
		if (date == null) {
			return null;
		}
		fromCal.setTime(date);
		fromCal.add(Calendar.HOUR_OF_DAY, addHour);
		fromCal.add(Calendar.MINUTE, min);
		System.out.println(date);
		return fromCal.getTime();
	}
	
	/**
	 * 
	 * 给日期增加天数
	 * 
	 * @author IBM-liubinbin
	 * @date 2012-11-8 下午2:44:08
	 */
	public static Date adddate(Date date, int addDay) {
		// 只根据日期来比较
		if (date == null) {
			return null;
		}
		fromCal.setTime(date);
		fromCal.add(Calendar.DAY_OF_YEAR, addDay);
		System.out.println(date);
		return fromCal.getTime();
	}
	/**
	 * 
	 * 预计到达时间跟当前日期比较，如果大于当前日期，则准时到达，
	 * 返回true
	 * 
	 * @author IBM-liubinbin
	 * @date 2012-11-8 下午2:44:08
	 */
	public static boolean isOnTime(Date planArrivalTime) {
		// 只根据日期来比较
		if (null == planArrivalTime) {
			return false;
		}
		if (planArrivalTime.after(new Date())) {
			return true;
		}
		return false;
	}
	
	public static void main(String args[]){
		System.out.print(adddate(new Date(),-2));
	}
}