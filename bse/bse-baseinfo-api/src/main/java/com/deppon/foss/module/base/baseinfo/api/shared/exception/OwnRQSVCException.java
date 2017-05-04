/*******************************************************************************
 * Copyright 2014 BSE TEAM
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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/exception/OwnRQSVCException.java
 * 
 * FILE NAME        	: OwnRQSVCException.java
 * 
 * AUTHOR			: FOSS综合开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2014  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;
/**
 * 用来处理“公司车辆-骨架车”业务操作异常类类：SUC-785
 * @author 187862-dujunhui
 * @date 2014-06-10 下午3:08:12
 * @since
 * @version
 */
public class OwnRQSVCException extends BusinessException {

    /**
	 * TODO（Serial Version UID）
	 */
	private static final long serialVersionUID = -6763753416867090678L;
	/**
     * "公司车辆-骨架车"的业务异常ERROR_KEY
     */
    public static final String OWNRQSVC_ID_NULL_ERROR_CODE = "foss.bse.bse-baseinfo.OwnRQSVCIdNullException";
    
    public OwnRQSVCException(String code, String msg, Throwable cause) {
	super(code, msg, cause);
    }

    public OwnRQSVCException(String code, String msg) {
	super(code, msg);
    }

    public OwnRQSVCException(String msg) {
	super(msg);
    }
}
