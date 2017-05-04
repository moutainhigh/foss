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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/exception/ProductItemServiceException.java
 * 
 * FILE NAME        	: ProductItemServiceException.java
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
 * 产品条目异常处理类
 * @author DP-Foss-YueHongJie
 * @date 2013-1-6 下午3:34:12
 * @version 1.0
 */
public class ProductItemServiceException extends BusinessException{

    private static final long serialVersionUID = 157043205123843247L;
    public ProductItemServiceException(String errCode){
	super();
	super.errCode = errCode;
    }
    
    public ProductItemServiceException(String code,String msg){
	super(code,msg);
    }
}