/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: bse-sms-itf
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/smsitf/ValidationWebTest.java
 * 
 * FILE NAME        	: ValidationWebTest.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.smsitf;

import com.deppon.foss.util.test.AbstractValidationWebTest;

public class ValidationWebTest extends AbstractValidationWebTest {
	@Override
	protected String getValidationUrl() {
		return "http://localhost:8280/bse-sms-itf/";
	}
}
