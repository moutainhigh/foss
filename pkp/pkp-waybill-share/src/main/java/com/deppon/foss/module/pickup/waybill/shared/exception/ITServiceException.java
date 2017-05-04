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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/exception/CrmOrderImportException.java
 * 
 * FILE NAME        	: CrmOrderImportException.java
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
 * IT服务台一键上报异常
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-sunrui,date:2012-11-14 下午2:55:05,
 * </p>
 * 
 * @author WangQianJin
 * @date 2013-11-14
 * @since
 * @version
 */
public class ITServiceException extends BusinessException {

	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = 8508645843507717365L;
	
	//IT服务台一键上报失败：
	public static final String IT_UPLOAD_SERVICE_FAIL="foss.pkp.waybill.itUploadService.exception.failupload";
	
	
	/**
	 * 
	 * 创建一个新的实例CrmOrderImportExveption
	 * 
	 * @author foss-sunrui
	 * @date 2012-12-25 上午9:44:45
	 */
	public ITServiceException() {
		super();
	}

	/**
	 * CRM订单导入异常类
	 * 
	 * @author foss-sunrui
	 * @date 2012-12-25 上午9:44:53
	 */
	public ITServiceException(String code, String msg, String natvieMsg, Throwable cause) {
		super(code, msg, natvieMsg, cause);
	}

	/**
	 * 
	 * CMR订单导入异常类
	 * 
	 * @author foss-sunrui
	 * @date 2012-12-25 上午9:45:23
	 */
	public ITServiceException(String code, String msg, String natvieMsg) {
		super(code, msg, natvieMsg);
	}

	/**
	 * CMR订单导入异常类
	 * 
	 * @author foss-sunrui
	 * @date 2012-12-25 上午9:46:09
	 */
	public ITServiceException(String code, String msg, Throwable cause) {
		super(code, msg, cause);
	}

	/**
	 * CMR订单导入异常类
	 * 
	 * @author foss-sunrui
	 * @date 2012-12-25 上午9:46:09
	 */
	public ITServiceException(String code, String msg) {
		super(code, msg);
	}

	/**
	 * CMR订单导入异常类
	 * 
	 * @author foss-sunrui
	 * @date 2012-12-25 上午9:46:09
	 */
	public ITServiceException(String msg, Throwable cause) {
		super(msg, cause);
		this.errCode = msg;
	}

	/**
	 * CMR订单导入异常类
	 * 
	 * @author foss-sunrui
	 * @date 2012-12-25 上午9:46:09
	 */
	public ITServiceException(String msg) {
		super(msg);
		this.errCode = msg;
	}

}