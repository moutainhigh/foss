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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/utils/StringToBigDecimalConverter.java
 * 
 * FILE NAME        	: StringToBigDecimalConverter.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.common.client.utils;

import java.math.BigDecimal;

import com.deppon.foss.framework.client.commons.conversion.ConversionException;
import com.deppon.foss.framework.client.commons.conversion.IConverter;


public class StringToBigDecimalConverter implements IConverter {

	@Override
	public Object to(Object source) throws ConversionException {
		if (source == null || "".equals(source))
			return null;
		
		if (source instanceof String) {
			try {
				return new BigDecimal((String)source);
			} catch (Exception e) {
				throw new ConversionException("Can't convert to BigDecimal.");
			}
		}
		
		throw new ConversionException("Can't convert to BigDecimal.");
	}

	@Override
	public Object from(Object target) throws ConversionException {
		if(target == null){
			return "";
		}else{
			//去除末尾多余的0，用非科学计数法显示
			return ((BigDecimal)target).stripTrailingZeros().toPlainString();
		}
	}

}