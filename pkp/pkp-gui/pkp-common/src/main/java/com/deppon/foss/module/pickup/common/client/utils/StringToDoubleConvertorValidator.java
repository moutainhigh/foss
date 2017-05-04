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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/utils/StringToDoubleConvertorValidator.java
 * 
 * FILE NAME        	: StringToDoubleConvertorValidator.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.common.client.utils;

import com.deppon.foss.framework.client.commons.binding.validators.AbstractConvertValidator;
import com.deppon.foss.framework.client.commons.conversion.IConverter;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
/**
 * 
 * 
*******************************************
* <b style="font-family:微软雅黑"><small>Description:类型转换校验器</small></b>   </br>
* <b style="font-family:微软雅黑"><small>HISTORY</small></b></br>
* <b style="font-family:微软雅黑"><small> ID      DATE    PERSON     REASON</small></b><br>
********************************************
* <div style="font-family:微软雅黑,font-size:70%"> 
*  2012-3-31 linws    新增
* </div>  
********************************************
 */
public class StringToDoubleConvertorValidator extends AbstractConvertValidator {
	
	/**
	 * 国际化对象
	 */
	private static final II18n i18n = I18nManager.getI18n(StringToDoubleConvertorValidator.class);

	@Override
	public IConverter getConverter() {
		return new StringToDoubleConverter();
	}

	@Override
	protected String getErrorMessage() {
		return i18n.get("foss.gui.common.stringToDoubleConvertorValidator.errorMessage");
	}

}