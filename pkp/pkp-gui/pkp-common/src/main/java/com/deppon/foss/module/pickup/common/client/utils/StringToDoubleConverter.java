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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/utils/StringToDoubleConverter.java
 * 
 * FILE NAME        	: StringToDoubleConverter.java
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
public class StringToDoubleConverter implements IConverter {

	@Override
	public Object to(Object source) throws ConversionException {
		return Double.parseDouble((String) source);
	}

	@Override
	public Object from(Object target) throws ConversionException {
		return String.valueOf(target);
	}

}