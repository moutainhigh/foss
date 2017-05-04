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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/exception/WaybillLabeledGoodException.java
 * 
 * FILE NAME        	: WaybillLabeledGoodException.java
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
 * FILE    NAME: WaybillLabeledGoodException.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.pickup.waybill.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 标签异常
 * 
 * @author 026123-foss-lifengteng
 * @date 2012-11-12 下午1:49:07
 */
public class WaybillLabeledGoodException extends BusinessException {

	/**
	 * 生成序列标识
	 */
	private static final long serialVersionUID = 1230160526412636144L;

	//PDA货签信息不能为空！
	public static final String LABELEDGOODS_NULL="foss.pkp.waybill.labeledGoodService.exception.nullLabeledGoodsList";
	
	//待办
	public static final String LABEL_GOOD_TODO = "foss.pkp.waybill.todoActionService.isUsingLabelGoodTodo";
	/**
	 * WaybillLabeledGoodException
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-12 下午1:49:59
	 */
	public WaybillLabeledGoodException() {
		super();
	}

	/**
	 * WaybillLabeledGoodException
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-12 下午1:49:59
	 */
	public WaybillLabeledGoodException(String code, String msg,
			String natvieMsg, Throwable cause) {
		super(code, msg, natvieMsg, cause);
	}

	/**
	 * WaybillLabeledGoodException
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-12 下午1:49:59
	 */
	public WaybillLabeledGoodException(String code, String msg, String natvieMsg) {
		super(code, msg, natvieMsg);
	}

	/**
	 * 创建一个新的实例 WaybillLabeledGoodException
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-12 下午1:49:59
	 */
	public WaybillLabeledGoodException(String code, String msg, Throwable cause) {
		super(code, msg, cause);
	}

	/**
	 * （创建一个新的实例 ）WaybillLabeledGoodException
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-12 下午1:49:59
	 */
	public WaybillLabeledGoodException(String code, String msg) {
		super(code, msg);
	}

	/**
	 * （创建一个新的实例 ）WaybillLabeledGoodException
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-12 下午1:49:59
	 */
	public WaybillLabeledGoodException(String msg, Throwable cause) {
		super(msg, cause);
		this.errCode = msg;
	}

	/**
	 * （创建一个新的实例 ）WaybillLabeledGoodException
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-12 下午1:49:59
	 */
	public WaybillLabeledGoodException(String msg) {
		super(msg);
		this.errCode = msg;
	}

}