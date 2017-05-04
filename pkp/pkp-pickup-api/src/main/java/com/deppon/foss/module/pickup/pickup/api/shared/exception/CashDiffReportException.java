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
 * PROJECT NAME	: pkp-pickup-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pickup/api/shared/exception/CashDiffReportException.java
 * 
 * FILE NAME        	: CashDiffReportException.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pickup.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 接送货现金差异报表-查看现金差异报表Exception
 *
 */
public class CashDiffReportException extends BusinessException {

	//序列化版本号
	private static final long serialVersionUID = 1L;

	/**
	 * 构造函数
	 * @param code  exception code
	 */
	public CashDiffReportException(String code) {
		super();
		this.errCode = code;
	}

}