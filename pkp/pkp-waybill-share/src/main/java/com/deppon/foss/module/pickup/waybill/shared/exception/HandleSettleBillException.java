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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/exception/HandleSettleBillException.java
 * 
 * FILE NAME        	: HandleSettleBillException.java
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
 * FILE    NAME: HandleSettleBillException.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.pickup.waybill.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;


/**
 * 新增结算单据时异常
 * @author 026123-foss-lifengteng
 * @date 2012-11-8 下午5:22:01
 */
public class HandleSettleBillException extends BusinessException{

	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = -6043244483564263161L;

	/**
	 * HandleSettleBillException
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-8 下午5:22:01
	 */
	public HandleSettleBillException() { }

	/**
	 * HandleSettleBillException
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-8 下午5:23:18
	 */
	public HandleSettleBillException(String code, String msg, String natvieMsg, Throwable cause) {
		super(code, msg, natvieMsg, cause);
	}

	/**
	 * HandleSettleBillException
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-8 下午5:23:18
	 */
	public HandleSettleBillException(String code, String msg, String natvieMsg) {
		super(code, msg, natvieMsg);
	}

	/**
	 * HandleSettleBillException
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-8 下午5:23:18
	 */
	public HandleSettleBillException(String code, String msg, Throwable cause) {
		super(code, msg, cause);
	}

	/**
	 * HandleSettleBillException
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-8 下午5:23:18
	 */
	public HandleSettleBillException(String code, String msg) {
		super(code, msg);
	}

	/**
	 * HandleSettleBillException
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-8 下午5:23:18
	 */
	public HandleSettleBillException(String msg, Throwable cause) {
		super(msg, cause);
		this.errCode = msg;
	}

	/**
	 * HandleSettleBillException
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-8 下午5:23:18
	 */
	public HandleSettleBillException(String msg) {
		super(msg);
		this.errCode=msg;
	}

}