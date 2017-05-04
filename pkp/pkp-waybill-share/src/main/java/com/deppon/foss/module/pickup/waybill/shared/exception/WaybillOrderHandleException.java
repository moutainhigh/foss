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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/exception/WaybillOrderHandleException.java
 * 
 * FILE NAME        	: WaybillOrderHandleException.java
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
 * FILE    NAME: WaybillOrderHandleException.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.pickup.waybill.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;


/**
 * 描述类的职责
 * @author 026123-foss-lifengteng
 * @date 2012-12-27 下午9:55:53
 */
public class WaybillOrderHandleException extends BusinessException {

	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = 6410860920428584479L;
	
	//更新订单状态失败：{0}
	public static final String UPDATESTATUS_FAIL="foss.pkp.waybill.oAErrorService.exception.failUpdateBillStatus";
			
	//更新订单状态失败！原因：{0}
	public static final String UPDATESTATUS_FAILREASON="foss.pkp.waybill.oAErrorService.exception.failReasonUpdateBillStatus";
			

	/**
	 * 创建一个新的实例WaybillOrderHandleException
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-27 下午9:55:53
	 */
	public WaybillOrderHandleException(String msg) {
		super(msg);
		this.errCode=msg;
	}

	/**
	 * 创建一个新的实例WaybillOrderHandleException
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-27 下午9:55:53
	 */
	public WaybillOrderHandleException(String msg, Throwable cause) {
		super(msg, cause);
		this.errCode=msg;
	}

	/**
	 * 创建一个新的实例WaybillOrderHandleException
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-27 下午9:55:53
	 */
	public WaybillOrderHandleException(String code, String msg) {
		super(code, msg);
		this.errCode=code;
	}

	/**
	 * 创建一个新的实例WaybillOrderHandleException
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-27 下午9:55:53
	 */
	public WaybillOrderHandleException(String code, String msg, Throwable cause) {
		super(code, msg, cause);
		this.errCode=code;
	}

	/**
	 * 创建一个新的实例WaybillOrderHandleException
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-27 下午9:55:53
	 */
	public WaybillOrderHandleException(String code, String msg, String natvieMsg) {
		super(code, msg, natvieMsg);
		this.errCode=code;
	}

	/**
	 * 创建一个新的实例WaybillOrderHandleException
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-27 下午9:55:53
	 */
	public WaybillOrderHandleException(String code, String msg, String natvieMsg, Throwable cause) {
		super(code, msg, natvieMsg, cause);
		this.errCode=code;
	}
	
	/**
	 * 创建一个新的实例WaybillOrderHandleException
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-27 下午9:55:53
	 */
	public WaybillOrderHandleException(String code,Object... args) {
		super(code,args);
		this.errCode = code;
	}
	

}