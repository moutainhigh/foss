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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/exception/PickupAndDeliverySmallZoneException.java
 * 
 * FILE NAME        	: PickupAndDeliverySmallZoneException.java
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
 * 展馆区域规划异常类
 * @author 187862-dujunhui
 * @date 2015-7-7 上午10:42:25
 * @since
 * @version
 */
public class ExhibitionAreaPlanException extends BusinessException {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 4435590281201486566L;
    
    public ExhibitionAreaPlanException(String errCode){
		super();
		super.errCode = errCode;
    }
    
    public ExhibitionAreaPlanException(String code,String msg){
    	super(code,msg);
    }

}