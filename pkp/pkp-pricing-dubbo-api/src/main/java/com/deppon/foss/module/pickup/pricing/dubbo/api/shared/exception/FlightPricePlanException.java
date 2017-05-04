/**
 *  initial comments.
 */
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
 * PROJECT NAME	: pkp-pricing-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/exception/FlightPricePlanException.java
 * 
 * FILE NAME        	: FlightPricePlanException.java
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
package com.deppon.foss.module.pickup.pricing.dubbo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 
 * 空运代理公司运价方案异常类
 * @author DP-Foss-YueHongJie
 * @date 2012-11-1 下午1:46:12
 */
public class FlightPricePlanException extends BusinessException {

    private static final long serialVersionUID = 1429184847881706693L;

    public FlightPricePlanException(String errCode){
	super();
	super.errCode = errCode;
    }
    
    public FlightPricePlanException(String code,String msg){
	super(code,msg);
    }
    
}