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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/QueryVirtualResultDto.java
 * 
 * FILE NAME        	: QueryVirtualResultDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;

/**
 * 
 * 查询虚开运单号返回
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-12-25 上午9:08:55
 */
public class QueryVirtualResultDto implements Serializable {

	private static final long serialVersionUID = -8361365807166285001L;

	/**
	 * 是否成功 false代码正常 true代表发生异常
	 */
	private boolean exception;

	/**
	 * 异常编码
	 */
	private String code;

	/**
	 * 异常信息
	 */
	private String message;

	/**
	 * 处理结果
	 */
	private boolean handleResult;

	/**
	 * 运单号
	 */
	private String wayBillNo;

	/**
	 * 查询失败原因(查询不到或者其他业务规则原因导致查询失败需要返回原因)
	 */
	private String failReason;

	
	public QueryVirtualResultDto() {
		super();
	}

	/**
	 * @return the exception
	 */
	public boolean isException() {
		return exception;
	}



	/**
	 * @param exception the exception to set
	 */
	public void setException(boolean exception) {
		this.exception = exception;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the handleResult
	 */
	public boolean isHandleResult() {
		return handleResult;
	}

	/**
	 * @param handleResult
	 *            the handleResult to set
	 */
	public void setHandleResult(boolean handleResult) {
		this.handleResult = handleResult;
	}

	/**
	 * @return the wayBillNo
	 */
	public String getWayBillNo() {
		return wayBillNo;
	}

	/**
	 * @param wayBillNo
	 *            the wayBillNo to set
	 */
	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}

	/**
	 * @return the failReason
	 */
	public String getFailReason() {
		return failReason;
	}

	/**
	 * @param failReason
	 *            the failReason to set
	 */
	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

}