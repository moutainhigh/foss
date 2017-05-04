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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/utils/StringToBigDecimalConvertorValidator.java
 * 
 * FILE NAME        	: StringToBigDecimalConvertorValidator.java
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
 * 字符串与长整型数据类型转换
 * @author 025000-FOSS-helong
 * @date 2012-10-23 下午02:40:00
 */
public class StringToBigDecimalConvertorValidator extends AbstractConvertValidator {
	
	/**
	 * 国际化对象
	 */
	private static final II18n i18n = I18nManager.getI18n(StringToBigDecimalConvertorValidator.class);

	@Override
	public IConverter getConverter() {
		return new StringToBigDecimalConverter();
	}

	@Override
	protected String getErrorMessage() {
		return i18n.get("foss.gui.common.stringToBigDecimalConvertorValidator.errorMessage");
	}

}