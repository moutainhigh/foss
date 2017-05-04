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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/util/TimeUtils.java
 * 
 *  FILE NAME     :TimeUtils.java
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
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.transfer.scheduling.api.shared.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.BooleanUtils;

/**
 * TODO工具类
 * @author huyue
 * @date 2012-11-27 下午2:09:57
 */
public class TimeUtils {

    private static final SimpleDateFormat SDF = new SimpleDateFormat("HHmm");
    private static final String REGULAR_TIME = "[0-2][\\d][0-5][\\d]";
    
    /**
     * 把day的年月日拼上time的小时分钟，再加上t的日期，返回一个整合的Date类型
     * @author huyue
     * @date 2012-11-27 下午2:08:05
     */
    public static Date convertStringToDate(Date day, String time, int t){
	if (day == null || time == null || BooleanUtils.isFalse(time.matches(REGULAR_TIME))) {
	    return null;
	}
	Calendar result = Calendar.getInstance();
	result.setTime(day);
	
	Date tmpDate = null;
	try {
	    tmpDate = SDF.parse(time);
	} catch (ParseException e) {
	    // time字符串不合法
	    return null;
	}
	Calendar tmpCalendar = Calendar.getInstance();
	tmpCalendar.setTime(tmpDate);
	result.set(Calendar.HOUR_OF_DAY, tmpCalendar.get(Calendar.HOUR_OF_DAY));
	result.set(Calendar.MINUTE, tmpCalendar.get(Calendar.MINUTE));
	result.add(Calendar.DATE, t);
	return result.getTime();
    }
    
    /**
     * 构造时间
     * @author huyue
     * @date 2012-11-27 下午2:07:31
     */
    public static Date createStartTime(Date inputTime, String startTime, int offset){
	SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
	Date startDate = null;
	try {
	    startDate = sdf.parse(startTime);
	} catch (ParseException e) {
	    return null;
	}
	Calendar startCal = Calendar.getInstance();
	startCal.setTime(startDate);
	
	Calendar inputCal = Calendar.getInstance();
	inputCal.setTime(inputTime);
	
	startCal.set(inputCal.get(Calendar.YEAR), inputCal.get(Calendar.MONTH), inputCal.get(Calendar.DATE));
	
	startCal.add(Calendar.MINUTE, offset);
	return startCal.getTime();
    }

    /**
     * 使用date的年月日和time的时分构造成一个新的Date对象
     * @author huyue
     * @date 2012-11-27 下午2:06:06
     */
    public static Date createStartTime(Date date, String time) {
	return createStartTime(date, time, 0);
    }
    
}