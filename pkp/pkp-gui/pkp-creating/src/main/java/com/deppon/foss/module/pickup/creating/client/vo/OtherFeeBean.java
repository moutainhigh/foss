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
 * PROJECT NAME	: pkp-creating
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/vo/OtherFeeBean.java
 * 
 * FILE NAME        	: OtherFeeBean.java
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
package com.deppon.foss.module.pickup.creating.client.vo;

/**
 * (描述类的职责 其他费用类 是打印bean中一个元素)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:jiangfei,date:2012-10-17 下午4:11:08,
 * </p>
 * 
 * @author jiangfei
 * @date 2012-10-17 下午4:11:08
 * @since
 * @version
 */
public class OtherFeeBean {

	/**
	 * （其他费用名字）
	 */
	private String otherFeeName;

	/**
	 * （其他费用值）
	 */
	private Long otherFeeValue;
	
	/**
	 * 其他费用CODE
	 */
	private String otherFeeCode;

	
	public String getOtherFeeCode() {
		return otherFeeCode;
	}

	
	public void setOtherFeeCode(String otherFeeCode) {
		this.otherFeeCode = otherFeeCode;
	}

	/**
	 * getOtherFeeName
	 * @return String
	 */
	public String getOtherFeeName() {
		return otherFeeName;
	}

	/**
	 * setOtherFeeName
	 * @param otherFeeName String
	 */
	public void setOtherFeeName(String otherFeeName) {
		this.otherFeeName = otherFeeName;
	}

	/**
	 * getOtherFeeValue
	 * @return Long
	 */
	public Long getOtherFeeValue() {
		return otherFeeValue;
	}

	/**
	 * setOtherFeeValue
	 * @param otherFeeValue Long
	 */
	public void setOtherFeeValue(Long otherFeeValue) {
		this.otherFeeValue = otherFeeValue;
	}

}