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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/exception/HandleTransportPathException.java
 * 
 * FILE NAME        	: HandleTransportPathException.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-waybill-share
 * PACKAGE NAME: com.deppon.foss.module.pickup.waybill.shared.exception
 * FILE    NAME: WaybillTransportPathException.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.pickup.waybill.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;


/**
 * 中转线路处理异常
 * @author 026123-foss-lifengteng
 * @date 2012-11-8 下午3:42:22
 */
public class HandleTransportPathException extends BusinessException {

	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = 1920690272942205764L;
	
	//调用中转接口生成运单线路信息时出错，原因：
	public static final String ADDLINE_FAIL="foss.pkp.waybill.waybillManagerService.exception.failAddLine";
	
	//集中接送货部门对应的外场编码为空
	public static final String NO_TRANSFERCENTER_CODE="foss.pkp.waybill.waybillManagerService.exception.noTransferCenterCode";
	
	//若没有查询到外场编码，则抛出异常信息
	public static final String NO_TRANSFERCENTER_INFO="foss.pkp.waybill.waybillManagerService.exception.noTransferCenterInfo";

	/**
	 * （创建一个新的实例 ）HandleTransportPathException
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-8 下午3:44:24
	 */
	public HandleTransportPathException() {
		super();
		//  Auto-generated constructor stub
	}

	/**
	 * （创建一个新的实例 ）HandleTransportPathException
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-8 下午3:44:24
	 */
	public HandleTransportPathException(String code, String msg, String natvieMsg, Throwable cause) {
		super(code, msg, natvieMsg, cause);
		//  Auto-generated constructor stub
	}

	/**
	 * （创建一个新的实例 ）HandleTransportPathException
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-8 下午3:44:24
	 */
	public HandleTransportPathException(String code, String msg, String natvieMsg) {
		super(code, msg, natvieMsg);
		//  Auto-generated constructor stub
	}

	/**
	 * （创建一个新的实例 ）HandleTransportPathException
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-8 下午3:44:24
	 */
	public HandleTransportPathException(String code, String msg, Throwable cause) {
		super(code, msg, cause);
		//  Auto-generated constructor stub
	}

	/**
	 * （创建一个新的实例 ）HandleTransportPathException
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-8 下午3:44:24
	 */
	public HandleTransportPathException(String code, String msg) {
		super(code, msg);
		//  Auto-generated constructor stub
	}

	/**
	 * （创建一个新的实例 ）HandleTransportPathException
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-8 下午3:44:24
	 */
	public HandleTransportPathException(String msg, Throwable cause) {
		super(msg, cause);
		this.errCode = msg;
		//  Auto-generated constructor stub
	}
	
	/**
	 * （创建一个新的实例 ）HandleTransportPathException
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-8 下午3:44:24
	 */
	public HandleTransportPathException(String code,Object... args) {
		super(code,args);
		this.errCode = code;
	}

	/**
	 * （创建一个新的实例 ）HandleTransportPathException
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-8 下午3:44:24
	 */
	public HandleTransportPathException(String msg) {
		super(msg);
		this.errCode = msg;
	}


}