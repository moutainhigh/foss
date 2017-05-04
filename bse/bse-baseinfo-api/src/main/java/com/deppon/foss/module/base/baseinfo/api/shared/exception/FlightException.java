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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/exception/FlightException.java
 * 
 * FILE NAME        	: FlightException.java
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
 * 用来处理“航班信息”业务操作异常类：SUC-785
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-11-3 上午9:34:53</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-11-3 上午9:34:53
 * @since
 * @version
 */
public class FlightException extends BusinessException {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 2378099139210465928L;

    /**
     * "航班信息"的业务异常ERROR_KEY
     */
    public static final String FLIGHT_ADD_SUCCESS = "foss.bse.baseinfo.flightException.addSuccess";
    
    public static final String FLIGHT_ADD_FAILURE = "foss.bse.baseinfo.flightException.addFailure";
    
    public static final String FLIGHT_DEL_SUCCESS = "foss.bse.baseinfo.flightException.delSuccess";
    
    public static final String FLIGHT_DEL_FAILURE = "foss.bse.baseinfo.flightException.delFailure";
    
    public static final String FLIGHT_UPD_SUCCESS = "foss.bse.baseinfo.flightException.updSuccess";
    
    public static final String FLIGHT_UPD_FAILURE = "foss.bse.baseinfo.flightException.updFailure";

    public FlightException(String code, String msg, Throwable cause) {
	super(code, msg, cause);
    }

    public FlightException(String code, String msg) {
	super(code, msg);
    }
}
