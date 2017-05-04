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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/exception/WorkdayException.java
 * 
 * FILE NAME        	: WorkdayException.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;
/**
 * 用来处理“外请车辆（厢式车、挂车、拖头）”业务操作异常类：关联的SUC-104、SUC-608、SUC-44、SUC-103
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-22 下午4:27:19</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-22 下午4:27:19
 * @since
 * @version
 */
public class WorkdayException extends BusinessException {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 9063168317009336403L;

    /**
     * ""的业务异常ERROR_KEY
     */
    public static final String WORKDAY_QUERY_PARAMETER_ERROR_CODE = "foss.bse.bse-baseinfo.DataDictionaryValueException";
    
    public WorkdayException(String code, String msg, Throwable cause) {
	super(code, msg, cause);
    }

    public WorkdayException(String code, String msg) {
	super(code, msg);
    }
    
    public WorkdayException(String errCode){
	super();
	super.errCode = errCode;
    }
}
