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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/utils/AddressFieldConverter.java
 * 
 * FILE NAME        	: AddressFieldConverter.java
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
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.waybill.shared.dto.AddressFieldDto;


public class AddressFieldConverter implements IConverter {

	@Override
	public Object to(Object source) throws ConversionException {
		return null;
	}

	@Override
	public Object from(Object target) throws ConversionException {
		if(target == null){
			return null;
		}
		if(target instanceof AddressFieldDto){
			AddressFieldDto	addressFieldDto = (AddressFieldDto)target;
			StringBuffer sb = new StringBuffer();
			if(StringUtil.isNotEmpty(addressFieldDto.getProvinceName())){
				sb.append(addressFieldDto.getProvinceName());
				sb.append("-");
			}
			if(StringUtil.isNotEmpty(addressFieldDto.getCityName())){
				sb.append(addressFieldDto.getCityName());
				sb.append("-");
			}
			if(StringUtil.isNotEmpty(addressFieldDto.getCountyName())){
				sb.append(addressFieldDto.getCountyName());
				sb.append("-");
			}
			if(sb.length()>0){
				return sb.substring(0, sb.length() - 1);
			}else{
				return sb.toString();
			}
		}else{
			return null;
		}
		
	}

}