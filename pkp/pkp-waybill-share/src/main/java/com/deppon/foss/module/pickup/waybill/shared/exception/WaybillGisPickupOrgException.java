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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/exception/WaybillGisPickupOrgException.java
 * 
 * FILE NAME        	: WaybillGisPickupOrgException.java
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
 * 通过 gis查询网点相关信息
 * 
 * @author 043258-foss-zhaobin
 * @date 2012-10-24 下午2:18:35
 */
public class WaybillGisPickupOrgException extends BusinessException {

	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = 8413305711958663907L;

	/**
	 *  创建一个新的实例WaybillGisPickupOrgException
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-4 下午3:21:35
	 */
	public WaybillGisPickupOrgException() {
		super();
	}

	/**
	 *  创建一个新的实例WaybillGisPickupOrgException
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-4 下午3:21:35
	 */
	public WaybillGisPickupOrgException(String code, String msg, String natvieMsg, Throwable cause) {
		super(code, msg, natvieMsg, cause);
		this.errCode = code;
	}

	/**
	 *  创建一个新的实例WaybillGisPickupOrgException
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-4 下午3:21:35
	 */
	public WaybillGisPickupOrgException(String code, String msg, String natvieMsg) {
		super(code, msg, natvieMsg);
		this.errCode = code;
	}

	/**
	 *  创建一个新的实例WaybillGisPickupOrgException
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-4 下午3:21:35
	 */
	public WaybillGisPickupOrgException(String code, String msg, Throwable cause) {
		super(code, msg, cause);
		this.errCode = code;
	}

	/**
	 *  创建一个新的实例WaybillGisPickupOrgException
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-4 下午3:21:35
	 */
	public WaybillGisPickupOrgException(String code, String msg) {
		super(code, msg);
		this.errCode = code;
	}

	/**
	 *  创建一个新的实例WaybillGisPickupOrgException
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-4 下午3:21:35
	 */
	public WaybillGisPickupOrgException(String msg, Throwable cause) {
		super(msg, cause);
		this.errCode = msg;
	}

	/**
	 *  创建一个新的实例WaybillGisPickupOrgException
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-4 下午3:21:35
	 */
	public WaybillGisPickupOrgException(String msg) {
		super(msg);
		this.errCode = msg;
	}

}