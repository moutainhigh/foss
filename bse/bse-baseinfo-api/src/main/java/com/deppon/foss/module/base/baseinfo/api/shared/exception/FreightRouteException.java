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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/exception/FreightRouteException.java
 * 
 * FILE NAME        	: FreightRouteException.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;


/**
 * 走货路径异常类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-11-8 下午2:28:44 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-11-8 下午2:28:44
 * @since
 * @version
 */
public class FreightRouteException extends BusinessException {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 3988065694486630442L;

    public static final String PRODUCT_NOT_EXIST = "foss.bse.baseinfo.freightRoute.productNotExist";
    
    public static final String PRODUCT_PRIORITY_NOT_EXIST = "foss.bse.baseinfo.freightRoute.productPriorityNotExist";

    public static final String ARGUMENT_ILLEGAL = "foss.bse.baseinfo.freightRoute.argumentIllegal";
    
    public static final String FREIGHTROUTE_NOT_EXIST = "foss.bse.baseinfo.freightRoute.freightRouteNotExist";

    public static final String FREIGHTROUTE_EXIST = "foss.bse.baseinfo.freightRoute.freightRouteExist";
    
    public static final String FREIGHT_ROUTE_SITE_CAN_NOT_DUPLICATION = "foss.bse.baseinfo.freightRoute.freightRouteSiteCanNotDuplication";
    public static final String FREIGHT_ROUTE_LINE_MUST_BE_EXIST = "foss.bse.baseinfo.freightRoute.freightRouteLineMustBeExist";
    public static final String CITYWIDE_ROUTE_LINE_EXIST = "foss.bse.baseinfo.freightRoute.cityWideRouteLineExist";
    public static final String FREIGHT_ROUTE_LINE_IS_EMPTY = "foss.bse.baseinfo.freightRoute.freightRouteLineIsEmpty";
    public static final String SOURCE_TARGET_SEQUENCE_CANNOT_BE_EMPTY = "foss.bse.baseinfo.freightRoute.sourceTargetSequenceCanNotBeEmpty";
    public static final String SITE_CAN_NOT_DUPLICATION = "foss.bse.baseinfo.freightRoute.siteCanNotDuplication";
    public static final String SEQUENCE_MUST_BE_NUMBER = "foss.bse.baseinfo.freightRoute.sequenceMustBeNumber";
    public static final String FREIGHTROUTELINE_MUST_BY_ORDER = "foss.bse.baseinfo.freightRoute.freightRouteLineMustByOrder";
    public static final String VALID_CAN_NOT_BE_DELETED = "foss.bse.baseinfo.freightRoute.validCanNotBeDeleted";
    public static final String FREIGHTROUTELINE_MUST_BE_VALID = "foss.bse.baseinfo.freightRoute.freightRouteLineMustBeValid";
    public static final String LINE_NOT_EXIST = "foss.bse.baseinfo.freightRoute.lineNotExist";
    public static final String COMMON_EXCEPTION = "foss.bse.baseinfo.freightRoute.commonException";
    
    public FreightRouteException(String errCode){
	super();
	super.errCode = errCode;
    }
    
//    public FreightRouteException(String code,String msg){
//	
//	super(code,msg);
//    }

    public FreightRouteException(String errCode, Object... para) {
	super(errCode, para);
	super.errCode = errCode;
    }
    
}
