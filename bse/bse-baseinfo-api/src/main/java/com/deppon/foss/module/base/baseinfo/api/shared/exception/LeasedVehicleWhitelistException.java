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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/exception/LeasedVehicleWhitelistException.java
 * 
 * FILE NAME        	: LeasedVehicleWhitelistException.java
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
 * (描述类的职责)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-20 下午5:03:58</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-20 下午5:03:58
 * @since
 * @version
 */
public class LeasedVehicleWhitelistException extends BusinessException {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 4204433965466239721L;
    
    /**
     * "外请车拖头"的业务异常ERROR_KEY
     */
    public static final String APPLY_SUCCESS_CODE = "foss.bse.baseinfo.leasedVehicleWhitelistException.appluSuccess";
    
    public static final String APPLY_FAILURE_CODE = "foss.bse.baseinfo.leasedVehicleWhitelistException.applyFailure";
    
    public static final String APPLY_PARAMETER_ISNULL = "foss.bse.baseinfo.leasedVehicleWhitelistException.parameterIsNull";
    
    public static final String APPLY_WHITELIST_ISNULL = "foss.bse.baseinfo.leasedVehicleWhitelistException.whitelistsIsNull";

    public static final String APPLY_PROPOSER_ISNULL = "foss.bse.baseinfo.leasedVehicleWhitelistException.proposerIsNull";

    public static final String APPLY_AUDITED = "foss.bse.baseinfo.leasedVehicleWhitelistException.audited";

    public static final String ID_ISNULL = "foss.bse.baseinfo.leasedVehicleWhitelistException.idIsNull";
    
    public static final String MODIFIER_ISNULL = "foss.bse.baseinfo.leasedVehicleWhitelistException.modifierIsNull";
    
    public static final String IMPACT_RECORD = "foss.bse.baseinfo.leasedVehicleWhitelistException.impactRecord";
    
    public LeasedVehicleWhitelistException(String code, String msg,
	    Throwable cause) {
	super(code, msg, cause);
    }

    public LeasedVehicleWhitelistException(String code, String msg) {
	super(code, msg);
    }
}
