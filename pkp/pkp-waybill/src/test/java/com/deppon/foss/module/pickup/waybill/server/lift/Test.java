/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-waybill
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/pickup/waybill/server/lift/Test.java
 * 
 * FILE NAME        	: Test.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-waybill
 * PACKAGE NAME: com.deppon.foss.module.pickup.waybill.server.lift
 * FILE    NAME: Test.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.pickup.waybill.server.lift;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.jfree.data.time.Hour;

import com.deppon.foss.framework.shared.util.string.StringUtil;


/**
 * TODO（描述类的职责）
 * @author 026123-foss-lifengteng
 * @date 2012-11-19 下午7:59:32
 */
public class Test {
	
	public static Date mergeTime(Date date,String time){
		if (date == null || time == null) {
			return null;
		}
		
		if(time.length() != 4)
			throw new IllegalArgumentException("非法的时间格式");
		if(!StringUtil.isNumeric(time))
			throw new IllegalArgumentException("非法的时间字符");
		
		Calendar oldCal = Calendar.getInstance();
		oldCal.setTime(date);
		oldCal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(time.substring(0, 2)));
		oldCal.set(Calendar.MINUTE, Integer.valueOf(time.substring(2, 4)));
		oldCal.set(Calendar.SECOND, 0);
		oldCal.set(Calendar.MILLISECOND, 0);
		//oldCal.add(Calendar.HOUR, 1);


		return oldCal.getTime();
	}

	/**
	 * TODO（方法详细描述说明、方法参数的具体涵义）
	 * @author 026123-foss-lifengteng
	 * @throws ParseException 
	 * @date 2012-11-19 下午7:59:32
	 */
	public static void main(String[] args) throws ParseException {
		System.out.println(new SimpleDateFormat("yy-MM-dd HH:mm:ss").format(Test.mergeTime(new Date(),"1366")));
		System.out.println( new SimpleDateFormat("yyyyMMddHHmmss").parse("20121120231200"));
	}

}