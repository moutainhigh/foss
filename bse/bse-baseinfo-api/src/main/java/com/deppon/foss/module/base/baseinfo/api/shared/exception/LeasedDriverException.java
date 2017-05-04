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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/exception/LeasedDriverException.java
 * 
 * FILE NAME        	: LeasedDriverException.java
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
 * 用来处理“外请车司机”业务操作异常类：SUC-211
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-20 上午11:00:35</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-20 上午11:00:35
 * @since
 * @version
 */
public class LeasedDriverException extends BusinessException {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = -2631402327822344449L;

    /**
     * "外请车拖头"的业务异常ERROR_KEY
     */
    public static final String LEASEDDRIVER_ADD_SUCCESS = "foss.bse.baseinfo.leasedDriverException.addSuccess";
    
    public static final String LEASEDDRIVER_ADD_FAILURE = "foss.bse.baseinfo.leasedDriverException.addFailure";
    
    public static final String LEASEDDRIVER_DEL_SUCCESS = "foss.bse.baseinfo.leasedDriverException.delSuccess";
    
    public static final String LEASEDDRIVER_DEL_FAILURE = "foss.bse.baseinfo.leasedDriverException.delFailure";
    
    public static final String LEASEDDRIVER_UPD_SUCCESS = "foss.bse.baseinfo.leasedDriverException.updSuccess";
    
    public static final String LEASEDDRIVER_UPD_FAILURE = "foss.bse.baseinfo.leasedDriverException.updFailure";
    
    public static final String ID_ISNULL = "foss.bse.baseinfo.leasedDriverException.idIsNull";
    
    public static final String PARAMETER_ISNULL = "foss.bse.baseinfo.leasedDriverException.parameterIsNull";
    
    public static final String ID_CARD_ISNULL = "foss.bse.baseinfo.leasedDriverException.idCardIsNull";
    
    public static final String CREATER_ISNULL = "foss.bse.baseinfo.leasedDriverException.createrIsNull";
    
    public static final String MODIFIER_ISNULL = "foss.bse.baseinfo.leasedDriverException.modifierIsNull";
    
    public static final String DRIVER_IS_NOT_NULL = "foss.bse.baseinfo.leasedDriverException.driverIsNotNull";
    
    public static final String DRIVER_ISNULL = "foss.bse.baseinfo.leasedDriverException.driverIsNull";
    
    public static final String IMPACT_RECORD = "foss.bse.baseinfo.leasedDriverException.impactRecord";
    
    public static final String DTO_VEHICLENO_ISNULL = "foss.bse.baseinfo.leasedDriverException.dto.vehicleNoIsNull";
    
    public static final String DTO_DRIVER_ISNULL = "foss.bse.baseinfo.leasedDriverException.dto.driverIsNull";
    
    public static final String DTO_COPY_PROPERTIES = "foss.bse.baseinfo.leasedDriverException.dto.copyProperties";
    /**
     * foss.bse.baseinfo.leasedDriverException.isNotDelete=该信息已入库，无法作废！
     */
    public static final String IS_NOT_DELETE = "foss.bse.baseinfo.leasedDriverException.isNotDelete";
    
    public LeasedDriverException(String code, String msg, Throwable cause) {
	super(code, msg, cause);
    }

    public LeasedDriverException(String code, String msg) {
	super(code, msg);
    }
}
