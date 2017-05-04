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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/utils/StringToLongConverter.java
 * 
 * FILE NAME        	: StringToLongConverter.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.common.client.utils;

import com.deppon.foss.framework.client.commons.conversion.ConversionException;
import com.deppon.foss.framework.client.commons.conversion.IConverter;
/**
 * 
 * 
*******************************************
* <b style="font-family:微软雅黑"><small>Description:String类型转换为DOUBLE类型</small></b>   </br>
* <b style="font-family:微软雅黑"><small>HISTORY</small></b></br>
* <b style="font-family:微软雅黑"><small> ID      DATE    PERSON     REASON</small></b><br>
********************************************
* <div style="font-family:微软雅黑,font-size:70%"> 
*  2012-3-31 linws    新增
* </div>  
********************************************
 */
public class StringToLongConverter implements IConverter {

    /**
	 * 
	 * @see com.deppon.foss.framework.client.commons.conversion.IConverter#to(java.lang.Object)
	 * to
	 * @param source
	 * @return
	 * @throws ConversionException
	 * @since:
	 */
	@Override
	public Object to(Object source) throws ConversionException {

		if (source == null || "".equals(source))
			throw new ConversionException("Null LONG.");
		
		if (source instanceof String) {
			try {
				return Long.parseLong((String)source);
			} catch (Exception e) {
				throw new ConversionException("Can't convert to LONG.");
			}
		}
		
		throw new ConversionException("Can't convert to LONG.");


	}

	/**
	 * 
	 * @see com.deppon.foss.framework.client.commons.conversion.IConverter#from(java.lang.Object)
	 * from
	 * @param target
	 * @return
	 * @throws ConversionException
	 * @since:
	 */
	@Override
	public Object from(Object target) throws ConversionException {

		return String.valueOf((Long)target);

	}
}