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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/exception/LeasedVehicleException.java
 * 
 * FILE NAME        	: LeasedVehicleException.java
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
public class LeasedVehicleException extends BusinessException {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 9063168317009336403L;

    /**
     * "外请车辆（厢式车、挂车、拖头）"的业务异常ERROR_KEY
     */
    public static final String LEASEDVEHICLE_ADD_SUCCESS = "foss.bse.baseinfo.leasedVehicleException.addSuccess";
    
    public static final String LEASEDVEHICLE_ADD_FAILURE = "foss.bse.baseinfo.leasedVehicleException.addFailure";
    
    public static final String LEASEDVEHICLE_DEL_SUCCESS = "foss.bse.baseinfo.leasedVehicleException.delSuccess";
    
    public static final String LEASEDVEHICLE_DEL_FAILURE = "foss.bse.baseinfo.leasedVehicleException.delFailure";
    
    public static final String LEASEDVEHICLE_UPD_SUCCESS = "foss.bse.baseinfo.leasedVehicleException.updSuccess";
    
    public static final String LEASEDVEHICLE_UPD_FAILURE = "foss.bse.baseinfo.leasedVehicleException.updFailure";
    
    public static final String ID_ISNULL = "foss.bse.baseinfo.leasedVehicleException.idIsNull";
    
    public static final String PARAMETER_ISNULL = "foss.bse.baseinfo.leasedVehicleException.parameterIsNull";
    
    public static final String VEHICLE_NO_ISNULL = "foss.bse.baseinfo.leasedVehicleException.vehicleNoIsNull";
    
    public static final String VEHICLE_TYPE_ISNULL = "foss.bse.baseinfo.leasedVehicleException.vehicleTypeIsNull";

    public static final String CREATER_ISNULL = "foss.bse.baseinfo.leasedVehicleException.createrIsNull";
    
    public static final String MODIFIER_ISNULL = "foss.bse.baseinfo.leasedVehicleException.modifierIsNull";
    
    public static final String VEHICLE_IS_NOT_NULL = "foss.bse.baseinfo.leasedVehicleException.vehicleIsNotNull";
    
    public static final String VEHICLE_ISNULL = "foss.bse.baseinfo.leasedVehicleException.vehicleIsNull";
    
    public static final String IMPACT_RECORD = "foss.bse.baseinfo.leasedVehicleException.impactRecord";
    
    public static final String DTO_COPY_PROPERTIES = "foss.bse.baseinfo.leasedVehicleException.dto.copyProperties";
    /**
     * 该外请车车牌不合法，应由字母、数字或者汉字随意组合，汉字不能超过两个
     */
    public static final String VEHICLE_NO_ISERROR="foss.bse.baseinfo.leasedVehicleException.vehicleNoIsError";
    /*
     * foss.bse.baseinfo.leasedVehicleException.vehicleIsOwn=该外请车信息已经在公司车信息中录入，请核实外请车辆信息是否正确
     */
    public static final String VEHICLE_IS_OWN="foss.bse.baseinfo.leasedVehicleException.vehicleIsOwn";
    /**
     * foss.bse.baseinfo.leasedVehicleException.isNotDelete=该信息已入库，无法作废！
     */
    public static final String IS_NOT_DELETE = "foss.bse.baseinfo.leasedVehicleException.isNotDelete";
    public LeasedVehicleException(String code, String msg, Throwable cause) {
	super(code, msg, cause);
    }
    public LeasedVehicleException( String msg) {
    	super(msg);
       }
    public LeasedVehicleException(String code, String msg) {
	super(code, msg);
    }
}
