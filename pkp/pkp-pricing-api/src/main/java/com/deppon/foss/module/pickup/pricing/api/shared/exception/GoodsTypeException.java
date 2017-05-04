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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/exception/GoodsTypeException.java
 * 
 * FILE NAME        	: GoodsTypeException.java
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
 * @Description: 货物类型异常定义类
 * GoodsTypeException.java Create on 2013-1-5 下午3:17:42
 * Company:IBM
 * @author FOSSDP-sz
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class GoodsTypeException extends BusinessException{

    private static final long serialVersionUID = 6722694803049723586L;
    
    public static final String GOODS_TYPE_CHECK_PARAMETER_ERROR_CODE = "foss.pkp.pkp-pricing.GoodsTypeException";
    
    public GoodsTypeException(String code, String msg, Throwable cause) {
	super(code, msg, cause);
    }

    public GoodsTypeException(String code, String msg) {
	super(code, msg);
    }
}