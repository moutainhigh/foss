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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/exception/WaybillPendingSaveException.java
 * 
 * FILE NAME        	: WaybillPendingSaveException.java
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
 * 运单暂存待补录信息保存异常
 * 运单暂存
 * pda运单暂存
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-sunrui,date:2012-11-14 下午2:55:05,
 * </p>
 * 
 * @author foss-sunrui
 * @date 2012-11-14 下午2:55:05
 * @since
 * @version
 */
public class WaybillPendingSaveException extends BusinessException{

	/**
	 * 序列化标识 
	 */
	private static final long serialVersionUID = 933485473645019187L;


    //更新待处理运单基本信息时失败
	public static final String UPDATEPENDINGBILL_FAIL="foss.pkp.waybill.WaybillPendingService.exception.failUpdatePendingBill";
	
    //保存待处理运单基本信息时失败：
	public static final String SAVEPENDINGBILL_FAIL="foss.pkp.waybill.WaybillPendingService.exception.failSavePendingBill";
	
    //保存待处理运单折扣明细信息时失败：
	public static final String SAVEPENDINGBILLDISCOUT_FAIL="foss.pkp.waybill.WaybillPendingService.exception.failSavePendingBillDiscout";
	
    //保存待处理运单费用明细信息时失败：
	public static final String SAVEPENDINGBILLDETAIL_FAIL="foss.pkp.waybill.WaybillPendingService.exception.failSavePendingBillDetail";
	
    //保存待处理运单付款明细信息时失败：
	public static final String SAVEPAYMENTPENDINGBILL_FAIL="foss.pkp.waybill.WaybillPendingService.exception.failSavePaymentPendingBill";
	
    //保存待处理运单打木架信息时失败：
	public static final String SAVEWOODENPENDINGBILL_FAIL="foss.pkp.waybill.WaybillPendingService.exception.failSavewoodenPendingBill";
	
	/**
	 *  （创建一个新的实例 ）WaybillPendingSaveException
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-18 下午9:55:35
	 */
	public WaybillPendingSaveException() {
		super();
	}

	/**
	 *  （创建一个新的实例 ）WaybillPendingSaveException
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-18 下午9:55:35
	 */
	public WaybillPendingSaveException(String code, String msg, String natvieMsg, Throwable cause) {
		super(code, msg, natvieMsg, cause);
	}

	/**
	 *  （创建一个新的实例 ）WaybillPendingSaveException
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-18 下午9:55:36
	 */
	public WaybillPendingSaveException(String code, String msg, String natvieMsg) {
		super(code, msg, natvieMsg);
	}

	/**
	 *  （创建一个新的实例 ）WaybillPendingSaveException
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-18 下午9:55:36
	 */
	public WaybillPendingSaveException(String code, String msg, Throwable cause) {
		super(code, msg, cause);
	}

	/**
	 *  （创建一个新的实例 ）WaybillPendingSaveException
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-18 下午9:55:36
	 */
	public WaybillPendingSaveException(String code, String msg) {
		super(code, msg);
	}

	/**
	 *  （创建一个新的实例 ）WaybillPendingSaveException
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-18 下午9:55:36
	 */
	public WaybillPendingSaveException(String msg, Throwable cause) {
		super(msg, cause);
		this.errCode=msg;
	}

	/**
	 *  （创建一个新的实例 ）WaybillPendingSaveException
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-18 下午9:55:36
	 */
	public WaybillPendingSaveException(String msg) {
		super(msg);
		this.errCode=msg;
	}
	
	/**
	 *  （创建一个新的实例 ）WaybillValidateException
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-23 下午4:20:19
	 */
	public WaybillPendingSaveException(String code,Object... args) {
		super(code,args);
		this.errCode = code;
	}

}