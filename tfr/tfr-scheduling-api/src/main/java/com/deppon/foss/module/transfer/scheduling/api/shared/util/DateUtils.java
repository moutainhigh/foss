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
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  
 *  PROJECT NAME  : tfr-scheduling-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/util/DateUtils.java
 * 
 *  FILE NAME     :DateUtils.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-scheduling-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.api.shared
 * FILE    NAME: DateUtils.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.api.shared.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;

/**
 * 时间工具
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-10-25 上午11:16:35
 */
public class DateUtils {

	/**
	 * 星期天
	 */
	static String SUNDAY_DESC = "天";
	/**
	 * 星期一
	 */
	static String MONDAY_DESC = "一";
	/**
	 * 星期二
	 */
	static String TUESDAY_DESC = "二";
	/**
	 * 星期三
	 */
	static String WEDNESDAY_DESC = "三";
	/**
	 * 星期四
	 */
	static String THURSDAY_DESC = "四";
	/**
	 * 星期五
	 */
	static String FRIDAY_DESC = "五";
	/**
	 * 星期六
	 */
	static String SATURDAY_DESC = "六";

	/**
	 * 星期转中文
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-25 上午11:16:54
	 */
	public static String convertDayOfWeek(int dayNum) {
		String day = "";
		switch (dayNum) {
		case Calendar.SUNDAY:
			day = SUNDAY_DESC;
			break;
		case Calendar.MONDAY:
			day = MONDAY_DESC;
			break;
		case Calendar.TUESDAY:
			day = TUESDAY_DESC;
			break;
		case Calendar.WEDNESDAY:
			day = WEDNESDAY_DESC;
			break;
		case Calendar.THURSDAY:
			day = THURSDAY_DESC;
			break;
		case Calendar.FRIDAY:
			day = FRIDAY_DESC;
			break;
		case Calendar.SATURDAY:
			day = SATURDAY_DESC;
			break;
		default:
			day = "";
			break;
		}
		return day;
	}

	/**
	 * 验证日期字符串是否在本年月中
	 * 
	 * @param yearMonth
	 *            年月
	 * @param scheduleDate
	 *            计划日期
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-13 下午1:37:36
	 */
	public static boolean validateDateRange(String yearMonth, Date scheduleDate) {
		if (StringUtils.isNotBlank(yearMonth) && scheduleDate != null) {
			String ymd = com.deppon.foss.util.DateUtils.convert(scheduleDate,
					com.deppon.foss.util.DateUtils.DATE_FORMAT);
			if (StringUtils.isNotBlank(ymd)) {
				if (ymd.indexOf(yearMonth) > -1) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public static Date parseString2Date(String source, String pattern) {
		try {
			return new SimpleDateFormat(pattern).parse(source);
		} catch (ParseException e) {
			return null;
		}
	}
	
	public static String formatDate2String(Date date, String pattern) {
		return new SimpleDateFormat(pattern).format(date);
	}
	
	public static Date addDate(Date date, int field, int amount) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(field, amount);
		return calendar.getTime();
	}
}