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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/utils/StringToDateConverter.java
 * 
 * FILE NAME        	: StringToDateConverter.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.common.client.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.deppon.foss.framework.client.commons.conversion.ConversionException;
import com.deppon.foss.framework.client.commons.conversion.IConverter;


public class StringToDateConverter implements IConverter {

	/**
	 * 
	 * 将界面数据转换成VO类型的数据
	 * @author 025000-FOSS-helong
	 * @date 2012-11-28 下午09:33:35
	 * @see com.deppon.foss.framework.client.commons.conversion.IConverter#to(java.lang.Object)
	 */
	@Override
	public Object to(Object source) throws ConversionException {
		if (source == null || "".equals(source))
			return null;
		
		if (source instanceof String) {
			try {
				SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				//完整的时间  
				Date date=sdf.parse((String)source); 
				return date;
			} catch (Exception e) {
				throw new ConversionException("Can't convert to Date.");
			}
		}
		
		throw new ConversionException("Can't convert to Date.");
	}

	/**
	 * 
	 * 将VO的数据类型转换成界面的Sring类型
	 * @author 025000-FOSS-helong
	 * @date 2012-11-28 下午09:34:03
	 * @see com.deppon.foss.framework.client.commons.conversion.IConverter#from(java.lang.Object)
	 */
	@Override
	public Object from(Object target) throws ConversionException {
		if(target == null){
			return "";
		}else{
			
			SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//完整的时间  
			String time=sdf.format((Date)target); 
			return time;
		}
	}

}