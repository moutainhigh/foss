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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/exception/LeasedVehicleWhitelistAuditException.java
 * 
 * FILE NAME        	: LeasedVehicleWhitelistAuditException.java
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
 * 用来处理“外请车白名单”审核（同意、拒绝）的业务操作异常类：SUC-104
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-22 上午9:47:06</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-22 上午9:47:06
 * @since
 * @version
 */
public class LeasedVehicleWhitelistAuditException extends BusinessException {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = -1871336298381381495L;
    
    /**
     * "外请车白名单"的业务异常ERROR_KEY
     */
    public static final String AUDIT_SUCCESS_CODE = "foss.bse.baseinfo.leasedVehicleWhitelistAuditException.auditSuccess";
    
    public static final String AUDIT_FAILURE_CODE = "foss.bse.baseinfo.leasedVehicleWhitelistAuditException.auditFailure";
    
    public static final String AUDIT_ID_ISNULL = "foss.bse.baseinfo.leasedVehicleWhitelistAuditException.idIsNull";
    
    public static final String AUDIT_AUDITOR_ISNULL = "foss.bse.baseinfo.leasedVehicleWhitelistAuditException.auditorIsNull";

    public static final String AUDIT_WHITELIST_ISNULL = "foss.bse.baseinfo.leasedVehicleWhitelistAuditException.whitelistsIsNull";
    
    public static final String AUDIT_VEHICLE_ISNULL = "foss.bse.baseinfo.leasedVehicleWhitelistAuditException.vehicleIsNull";
    
    public static final String AUDIT_REVOCATORY = "foss.bse.baseinfo.leasedVehicleWhitelistAuditException.withdraw";
    
    public LeasedVehicleWhitelistAuditException(String code, String msg,
	    Throwable cause) {
	super(code, msg, cause);
    }

    public LeasedVehicleWhitelistAuditException(String code, String msg) {
	super(code, msg);
    }
}
