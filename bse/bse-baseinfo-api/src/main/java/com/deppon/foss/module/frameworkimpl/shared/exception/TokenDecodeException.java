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
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/frameworkimpl/shared/exception/TokenDecodeException.java
 * 
 * FILE NAME        	: TokenDecodeException.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.frameworkimpl.shared.exception;

import com.deppon.foss.framework.exception.GeneralException;

/**
 * token解密异常
 * 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:ningyu,date:2012-11-30 上午8:53:52</p>
 * @author ningyu
 * @date 2012-11-30 上午8:53:52
 * @since
 * @version
 */
public class TokenDecodeException extends GeneralException {
	
	private static final long serialVersionUID = -2593714308471864839L;
	
	public static final String ERROR_CODE = "errror.common.tokenDecode.failure";

	public TokenDecodeException(Throwable cause) {
		super(cause);
		this.errCode = ERROR_CODE;
	}

	public TokenDecodeException() {
		super();
		this.errCode = ERROR_CODE;
	}

}
