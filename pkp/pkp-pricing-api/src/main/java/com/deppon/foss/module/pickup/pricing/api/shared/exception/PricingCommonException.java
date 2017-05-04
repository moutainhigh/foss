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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/exception/PricingCommonException.java
 * 
 * FILE NAME        	: PricingCommonException.java
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
 * 用来处理“区域”业务操作异常类：SUC-211
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:078838-foss-zhangbin,date:2012-11-26 上午11:00:35</p>
 * @author 078838-foss-zhangbin
 * @date 2012-11-26 上午11:00:35
 * @since
 * @version
 */
public class PricingCommonException extends BusinessException {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = -2631402327822344449L;

    /**
     * 登陆用户信息为空，请重新登录
     */
    public static final String NOT_LOGIN = "foss.pkp.pkp-pricing.NotLogin";
    
    public PricingCommonException(String code, String msg, Throwable cause) {
	super(code, msg, cause);
    }

    public PricingCommonException(String code, String msg) {
	super(code, msg);
    }
}