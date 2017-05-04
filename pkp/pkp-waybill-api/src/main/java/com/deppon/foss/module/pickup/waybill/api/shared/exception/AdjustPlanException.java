/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-waybill-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/shared/exception/AdjustPlanException.java
 * 
 * FILE NAME        	: AdjustPlanException.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
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
package com.deppon.foss.module.pickup.waybill.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 
 * 执行计划异常处理
 * @author 105089-foss-yangtong
 * @date 2012-11-27 下午5:26:34
 */
public class AdjustPlanException extends BusinessException{
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 构造方法
	 * @param code
	 */
	public AdjustPlanException(String code) {
		/*
		 * 构造方法
		 */
		super();
		this.errCode = code;
	}
}