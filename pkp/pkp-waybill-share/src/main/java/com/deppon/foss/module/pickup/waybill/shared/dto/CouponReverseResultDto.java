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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/CouponReverseResultDto.java
 * 
 * FILE NAME        	: CouponReverseResultDto.java
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
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;

/**
 * 
 * 反使用优惠券结果
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-12-19 上午10:20:57, </p>
 * @author foss-sunrui
 * @date 2012-12-19 上午10:20:57
 * @since
 * @version
 */
public class CouponReverseResultDto implements Serializable {

	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = 1758921917590903599L;
	//是否反使用成功
	private boolean isSuccess;
	//信息
	private String message;
	
	/**
	 * @return the isSuccess .
	 */
	public boolean isSuccess() {
		return isSuccess;
	}
	
	/**
	 *@param isSuccess the isSuccess to set.
	 */
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	
	/**
	 * @return the message .
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 *@param message the message to set.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	
}