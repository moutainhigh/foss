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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/frameworkimpl/shared/exception/EncodeMd5Exception.java
 * 
 * FILE NAME        	: EncodeMd5Exception.java
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
 * 
 * 
*******************************************
* <b style="font-family:微软雅黑"><small>Description:md5编码异常</small></b>   </br>
* <b style="font-family:微软雅黑"><small>HISTORY</small></b></br>
* <b style="font-family:微软雅黑"><small> ID      DATE    PERSON     REASON</small></b><br>
********************************************
* <div style="font-family:微软雅黑,font-size:70%"> 
* 1 	2012-8-30 	钟庭杰 	新增
* </div>  
********************************************
 */
public class EncodeMd5Exception extends GeneralException {

	private static final long serialVersionUID = 6829001589862824918L;
	
	public static final String ERROR_CODE = "errror.common.passwordEncodeMd5Code.failure";

	public EncodeMd5Exception(Throwable t) {
		super(t);
		this.errCode = "errror.common.passwordEncodeMd5Code.failure";
	}

	public EncodeMd5Exception() {
	}
}
