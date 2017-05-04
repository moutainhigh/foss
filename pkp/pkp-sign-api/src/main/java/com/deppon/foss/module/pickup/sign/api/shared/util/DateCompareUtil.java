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
 * PROJECT NAME	: pkp-sign-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/util/TemplateUtil.java
 * 
 * FILE NAME        	: TemplateUtil.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.shared.util;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 日期比较
 * @author meiying
 *
 */
public class DateCompareUtil {
	
	/**
	 * 判断当前传入的日期是否在系统时间的当天内
	 * @author foss-meiying
	 * @date 2013-6-12 下午8:56:58
	 * @param map
	 * @return
	 * @see
	 */
	public static Boolean isToday(Date signTime) {
		if(signTime == null){
			return false;
		}
        Date sysTime=new Date();  
       //将下面的 理解成  yyyy-MM-dd 00：00：00 更好理解点  
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
        String todayStr = format.format(sysTime); 
        String signStr = format.format(signTime);
       if(todayStr.equals(signStr)){ //是今天  
            return true;  
        }  
        else{ //不是今天
            return false;  
        }  
	}
}