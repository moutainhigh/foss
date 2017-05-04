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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/exception/WaybillQueryException.java
 * 
 * FILE NAME        	: WaybillQueryException.java
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
 * 运单、更改单查询异常
 * @author 026113-foss-linwensheng
 *
 */
public class WaybillQueryException extends BusinessException{

	/**
	 * 序列化标识 
	 */
	private static final long serialVersionUID = 933485473645019187L;
	
	//传入的Dto对象为空
	public static final String WAYBILLLIST_NULL="foss.pkp.waybill.waybillQueryService.exception.nullWaybillList";
		
	//起始单号不为空，但是结束单号为空
	public static final String ENDWAYBILLNO_NULL="foss.pkp.waybill.waybillQueryService.exception.nullEndWaybillNo";
			
	//结束单号不为空，但是起始单号为空
	public static final String STARTWAYBILLNO_NULL="foss.pkp.waybill.waybillQueryService.exception.nullStartWaybillNo";
			
	//起始时间不为空，结束时间为空
	public static final String ENDBILLTIME_NULL="foss.pkp.waybill.waybillQueryService.exception.nullEndBillTime";
			
	//结束时间不为空，起始时间为空
	public static final String STARTBILLTIME_NULL="foss.pkp.waybill.waybillQueryService.exception.nullStartBillTime";
	
	//没有传入时间，请选择时间进行查询
	public static final String TIME_NULL="foss.pkp.waybill.waybillQueryService.exception.time.null";
			
	/**
	 *  （创建一个新的实例 ）WaybillQueryException
	 * @author 0026113-foss-linwensheng
	 * @date 2012-12-18 下午9:55:35
	 */
	public WaybillQueryException() {
		super();
	}

	/**
	 *  （创建一个新的实例 ）WaybillQueryException
	 * @author 0026113-foss-linwensheng
	 * @date 2012-12-18 下午9:55:35
	 */
	public WaybillQueryException(String code, String msg, String natvieMsg, Throwable cause) {
		super(code, msg, natvieMsg, cause);
	}

	/**
	 *  （创建一个新的实例 ）WaybillQueryException
	 * @author 0026113-foss-linwensheng
	 * @date 2012-12-18 下午9:55:36
	 */
	public WaybillQueryException(String code, String msg, String natvieMsg) {
		super(code, msg, natvieMsg);
	}

	/**
	 *  （创建一个新的实例 ）WaybillQueryException
	 * @author 0026113-foss-linwensheng
	 * @date 2012-12-18 下午9:55:36
	 */
	public WaybillQueryException(String code, String msg, Throwable cause) {
		super(code, msg, cause);
	}

	/**
	 *  （创建一个新的实例 ）WaybillQueryException
	 * @author 0026113-foss-linwensheng
	 * @date 2012-12-18 下午9:55:36
	 */
	public WaybillQueryException(String code, String msg) {
		super(code, msg);
	}

	/**
	 *  （创建一个新的实例 ）WaybillQueryException
	 * @author 0026113-foss-linwensheng
	 * @date 2012-12-18 下午9:55:36
	 */
	public WaybillQueryException(String msg, Throwable cause) {
		super(msg, cause);
		this.errCode = msg;
	}

	/**
	 *  （创建一个新的实例 ）WaybillQueryException
	 * @author 0026113-foss-linwensheng
	 * @date 2012-12-18 下午9:55:36
	 */
	public WaybillQueryException(String msg) {
		super(msg);
		this.errCode=msg;
	}

}