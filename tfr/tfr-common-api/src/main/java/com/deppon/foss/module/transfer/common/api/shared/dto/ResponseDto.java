/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-common-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/common/api/shared/dto/ResponseDto.java
 *  
 *  FILE NAME          :ResponseDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.common.api.shared.dto;

/**
 * 
 * 上传无标签多货返回对象
 * @author 046130-foss-xuduowei
 * @date 2012-11-27 下午8:21:53
 */
public class ResponseDto {
	/**
	 * 是否成功 false代码正常 true代表发生异常
	 */
	private boolean isException;
	/**
	 * 异常编码
	 */
	private String code;
	/**
	 * 异常信息
	 */
	private String message;
	/**
	 * 差错编号
	 */
	private String errorsNo;
	/**
	 * 差错编号(用于新增封签,由于OA接口返回为errorNo)
	 */
	private String errorNo;
	/**
	 * 是否成功
	 */
	private boolean isSuccess;
	/**
	 * 失败原因
	 */
	private String failureReason;
	
	public String getFailureReason() {
		return failureReason;
	}

	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}

	/**
	 * 差错id
	 */
	private String errorID;
	
	/**
	 * 
	 *
	 * @return 
	 */
	public boolean getIsException() {
		return isException;
	}
	
	/**
	 * 
	 *
	 * @param isException 
	 */
	public void setIsException(boolean isException) {
		this.isException = isException;
	}
	
	/**
	 * 获取 异常编码.
	 *
	 * @return the 异常编码
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * 设置 异常编码.
	 *
	 * @param code the new 异常编码
	 */
	public void setCode(String code) {
		this.code = code;
	}
	
	/**
	 * 获取 异常信息.
	 *
	 * @return the 异常信息
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * 设置 异常信息.
	 *
	 * @param message the new 异常信息
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * 获取 差错编号.
	 *
	 * @return the 差错编号
	 */
	public String getErrorsNo() {
		return errorsNo;
	}
	
	/**
	 * 设置 差错编号.
	 *
	 * @param errorsNo the new 差错编号
	 */
	public void setErrorsNo(String errorsNo) {
		this.errorsNo = errorsNo;
	}
	
	/**
	 * 
	 *
	 * @return 
	 */
	public boolean getIsSuccess() {
		return isSuccess;
	}
	
	/**
	 * 
	 *
	 * @param isSuccess 
	 */
	public void setIsSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	
	/**
	 * 获取 差错id.
	 *
	 * @return the 差错id
	 */
	public String getErrorID() {
		return errorID;
	}
	
	/**
	 * 设置 差错id.
	 *
	 * @param errorID the new 差错id
	 */
	public void setErrorID(String errorID) {
		this.errorID = errorID;
	}

	public String getErrorNo() {
		return errorNo;
	}

	public void setErrorNo(String errorNo) {
		this.errorNo = errorNo;
	}
	
}