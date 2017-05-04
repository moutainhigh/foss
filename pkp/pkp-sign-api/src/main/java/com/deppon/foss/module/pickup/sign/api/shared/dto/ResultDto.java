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
 * PROJECT NAME	: pkp-sign-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/dto/ResultDto.java
 * 
 * FILE NAME        	: ResultDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.shared.dto;

import java.io.Serializable;

/**
 * 信息返回结果实体
 * @author Bobby
 * @date 2012-10-17 下午3:20:45
 * @since
 * @version
 */
public class ResultDto implements Serializable {
	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 6389135163224439269L;
	public static final String SUCCESS_NO_SEND = "2";
	public static final String SUCCESS = "1";
	public static final String FAIL = "0";

	// 结果返回代码：0，失败；1，成功
	private String code;

	// 当code为0，失败的情况下，这里返回错误信息
	private String msg;

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to see
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg the msg to see
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

}