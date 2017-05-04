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
 * PROJECT NAME	: pkp-waybill-share
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/exception/OAErrorException.java
 * 
 * FILE NAME        	: OAErrorException.java
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
package com.deppon.foss.module.pickup.waybill.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 
 * 用于OA查询虚开单错误信息
 * 
 * @author 025000-FOSS-helong
 * @date 2012-11-23 上午10:31:39
 */
public class OAErrorException extends BusinessException {

	/**
	 * 序列化版本号（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 4668118737380229341L;

	//差错处理编号为空！：
	public static final String HANDINGID_NULL="foss.pkp.waybill.oAErrorService.exception.nullHandingID";
		
	//OA差错接口无法访问
	public static final String OAERRORS_INACCESSIBLE="foss.pkp.waybill.oAErrorService.exception.oAErrorsInaccessible";
	
	//OA上报差错接口无法访问系统异常：包括连接超时，服务内部异常等不可预知的问题
	public static final String REPORT_OAERRORS_SYS_INACCESSIBLE="foss.pkp.waybill.oAErrorService.exception.reportOAErrorsSysInaccessible";
	
	//OA上报差错接口业务异常：包括必填问题，业务规则问题等限制异常
	public static final String REPORT_OAERRORS_BUZ_INACCESSIBLE_1="[OAERRORS_BUZ _001]审核失败，请稍后重试"; 
		
	/**
	 * （创建一个新的实例 ）
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-23 下午4:20:19
	 */
	public OAErrorException() {
		super();
		//  Auto-generated constructor stub
	}

	/**
	 * 
	 // * （创建一个新的实例 ）OAErrorException
	 * 
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-7 上午11:21:12
	 */
	public OAErrorException(String msg) {
		super(msg);
		this.errCode=msg;
	}

}