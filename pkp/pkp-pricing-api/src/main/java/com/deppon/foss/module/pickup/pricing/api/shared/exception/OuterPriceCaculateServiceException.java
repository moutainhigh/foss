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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/exception/AirlinesValueAddException.java
 * 
 * FILE NAME        	: AirlinesValueAddException.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;


public class OuterPriceCaculateServiceException extends BusinessException{

    /**
	 * 序列化ID 
	 */
	private static final long serialVersionUID = -2887815545213476283L;
	
	/**非NULL校验**/
	public static final String OUTPRICE_CACULATE_NULL_ERROR_CODE = "传入参数不能为空,请检查!";
    
    public OuterPriceCaculateServiceException(String errCode){
    	super();
    	super.errCode = errCode;
    }
    
    public OuterPriceCaculateServiceException(String code,String msg){
    	super(code,msg);
    }

}