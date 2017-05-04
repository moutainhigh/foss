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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/exception/WaybillRfcExceptionType.java
 * 
 * FILE NAME        	: WaybillRfcExceptionType.java
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
package com.deppon.foss.module.settlement.dubbo.api.define;

/**
 * 更改单一次类型
 * 
 * @author 026113-foss-linwensheng
 * 
 */
public class WaybillRfcExceptionType {
	/**
	 * 传回的运单数量太大
	 */
	public static final String QUERY_NUMBER_TOO_GARGE_ERROR_CODE = "foss.pkp.waybillRfc.queryNumberTooLarge";

	/**
	 * 查询的数量为0
	 */
	public static final String QUERY_NUMBER_NULL_ERROR_CODE = "foss.pkp.waybillRfc.queryNumberNull";

	/**
	 * 查询的运单为空
	 */
	public static final String QUERY_WAYBILLNO_NULL_ERROR_CODE = "foss.pkp.waybillRfc.queryWaybillNoNull";

	/**
	 * 传入的查询ID列表为空
	 */
	public static final String WAYBILLRFC_ID_LIST_NULL_ERROR_CODE = "foss.pkp.waybillRfc.id.list.null";
	/**
	 * 传入的备注为空
	 */
	public static final String WAYBILLRFC_NOTE_NULL_ERROR_CODE = "foss.pkp.waybillRfc.note.null";
	/**
	 * 传入的状态为空
	 */
	public static final String WAYBILLRFC_WRITE_OFF_STATUS_NULL_ERROR_CODE = "foss.pkp.waybillRfc.write.off.status.null";

	/**
	 * 传入的单号已经被处理过，请查找查询
	 */
	public static final String WAWYBILLRFC_WRITE_OFF_STATUS_ALREADY_HANDLE ="foss.pkp.waybillRfc.write.off.status.already.handle";
	
	/**
	 * 还为处理的
	 */
	public static final String WAWYBILLRFC_WRITE_OFF_STATUS_NO_HANDLE="foss.pkp.waybillRfc.write.off.status.no.handle";
	


}