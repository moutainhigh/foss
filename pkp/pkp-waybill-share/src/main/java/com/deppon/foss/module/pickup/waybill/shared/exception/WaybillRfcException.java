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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/exception/WaybillRfcException.java
 * 
 * FILE NAME        	: WaybillRfcException.java
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

import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 更改单对外调用异常
 * 
 * @author 026113-foss-linwensheng
 * 
 */
public class WaybillRfcException extends BusinessException {
	

	
	/**
	 *请检查运单号是否输入
	 */
	public static  final String CHECK_WAYBILL_NUMBER_ALREADY_INPUT = "foss.waybillRfc.CheckWaybillNumberAlreadyInput";

	
	/**
	 * 流水号不能为空
	 */
	public static  final String WAYBILL_SERIAL_NUMBER_CAN_NOT_NULL = "foss.waybillRfc.waybillSerialNumberCannotNull";
	
	
	/**
	 * 部门编码不能为空
	 */
	public static  final String ORG_NUMBER_CAN_NOT_NULL = "foss.waybillRfc.OrgNumberCannotNull";

	
	/**
	 * 更改单提交失败,原因是老版本运单的运单号不等于本次修改的运单号！请重新发更改（重新打开更改菜单）！
	 */
	public static  final String CHECK_DATA_INTEGRITY = "foss.waybillRfc.check.Data.Integrity";

	
	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * 更改内容
	 */
	private List<String> waybillFRcs;

	/**
	 * 创建一个新的实例 WaybillRfcException
	 * 
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-25 上午9:59:39
	 */
	public WaybillRfcException() {
		super();
	}

	/**
	 * 运单更改异常方法
	 * 
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-25 上午9:59:39
	 */
	public WaybillRfcException(String code, String msg) {
		super(code, msg);
	}

	/**
	 * 运单更改异常方法
	 * 
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-25 上午9:59:39
	 */
	public WaybillRfcException(String code) {
		super(code);
		this.errCode = code;
	}

	/**
	 * 用于把错误的更改单单号通过异常进行返回
	 * 
	 * @param code
	 * @param msg
	 * @param waybillFRcs
	 */
	public WaybillRfcException(String code, String msg, List<String> waybillFRcs) {
		super(code, msg);
		this.waybillFRcs = waybillFRcs;
	}

	/**
	 * @return the waybillFRcs .
	 */
	public List<String> getWaybillFRcs() {
		return waybillFRcs;
	}

	/**
	 * @param waybillFRcs
	 *            the waybillFRcs to set.
	 */
	public void setWaybillFRcs(List<String> waybillFRcs) {
		this.waybillFRcs = waybillFRcs;
	}

}