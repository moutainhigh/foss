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
 * PROJECT NAME	: bse-common-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/api/shared/exception/SMSAdVertisingSloganException.java
 * 
 * FILE NAME        	: SMSAdVertisingSloganException.java
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
package com.deppon.foss.module.base.common.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 短信广告语异常类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-12-19 上午9:02:02</p>
 * @author 094463-foss-xieyantao
 * @date 2012-12-19 上午9:02:02
 * @since
 * @version
 */
public class SMSAdVertisingSloganException extends BusinessException {

    
    private static final long serialVersionUID = -4364640392548561374L;

    public SMSAdVertisingSloganException(String errCode){
	super();
	super.errCode = errCode;
    }
    
    public SMSAdVertisingSloganException(String code,String msg){
	
	super(code,msg);
    }

}
