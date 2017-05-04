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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/exception/PricePlanException.java
 * 
 * FILE NAME        	: PricePlanException.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 
 *  价格异常处理类 
 * PricePlanException
 * 
 * DP-Foss-YueHongJie
 * 2012-12-1 下午6:07:15
 * 
 * @version 1.0.0
 *
 */
public class PricePlanException extends BusinessException{

    private static final long serialVersionUID = 6722694803049723586L;
    
    
    public static final String PRICE_PLAN_ADD_INSERT_ERROR_CODE = "foss.pkp.pkp-pricing.PricePlanException.insertErro";//新增失败
    public static final String PRICE_PLAN_ADD_PLANISNULL_ERROR_CODE = "foss.pkp.pkp-pricing.PricePlanException.checkPlanIsNull";//批次为空
    public static final String PRICE_PLAN_ADD_DETAILISNULL_ERROR_CODE = "foss.pkp.pkp-pricing.PricePlanException.checkDeatilIsNull";//明细为空
    public static final String PRICE_PLAN_ADD_CHECKTIME_ERROR_CODE = "foss.pkp.pkp-pricing.PricePlanException.checkTime";//检查当前方案生效日期需要大于营业日期!
    public static final String PRICE_PLAN_ADD_PRICINGENTRYISNULL_ERROR_CODE = "foss.pkp.pkp-pricing.PricePlanException.pricingEntry";//检查计价条目为空
    public static final String PRICE_PLAN_ADD_DETAILEXIST_ERROR_CODE = "foss.pkp.pkp-pricing.PricePlanException.checkDetailIsExist";//检查计费规则
    public static final String PRICE_PLAN_ADD_CHECKPRODUCTITEM_ERROR_CODE = "foss.pkp.pkp-pricing.PricePlanException.checkProductItem";//检查产品条目不能为空!
    public static final String PRICE_PLAN_ADD_CHECK_VALUATIONISNULL_ERROR_CODE = "foss.pkp.pkp-pricing.PricePlanException.checkValuationIsNull";//根据计价方案没有找到对应的计费规则
    
    
    public PricePlanException(String code, String msg, Throwable cause) {
	super(code, msg, cause);
    }

    public PricePlanException(String code, String msg) {
	super(code, msg);
    }
    
    //国际化处理code
    public PricePlanException(String code) {
   	super(code);
    }
}