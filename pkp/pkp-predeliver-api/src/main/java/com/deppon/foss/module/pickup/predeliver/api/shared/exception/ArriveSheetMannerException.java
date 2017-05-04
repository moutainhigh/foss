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
 * PROJECT NAME	: pkp-predeliver-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/exception/ArriveSheetMannerException.java
 * 
 * FILE NAME        	: ArriveSheetMannerException.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * : 异常类
 * @author dengtingting
 *
 */
public class ArriveSheetMannerException extends BusinessException{
	
	private static final long serialVersionUID = 1L;
	public static final String OBJECT_NULL_NOT_SIGN_CHANGE_OPERATE="pkp.predeliver.arriveSheetMannerException.objectNullNotSignChangeOperate"; //传入的对象为空，不能进行签收变更操作
	public static final String OBJECT_NULL_NOT_REVERSE_SIGN_OPERATE ="pkp.predeliver.arriveSheetMannerException.objectNullNotReverseSignOperate"; //传入的集合对象为空，不能进行反签收操作
	public static final String ENTITY_IS_NOT_EXIST ="pkp.predeliver.arriveSheetMannerException.entityIsNotExist"; //该运单对应实体不存在 
	public static final String WAYBILL_SIGN_RESULT_IS_NOT_EXIST ="pkp.predeliver.arriveSheetMannerException.waybillSignResultIsNotExist"; //该运单号在运单签收结果里不存在
	public static final String ARRIVE_SHEET_CREATING ="pkp.predeliver.arriveSheetMannerException.creating"; //该运单的到达联正在生成中
	public ArriveSheetMannerException(String code){
		  super();
		  this.errCode = code;
	}
	//有两个参数的构造函数
	public ArriveSheetMannerException(String code, Throwable cause) {
		super(code,cause);
		this.errCode = code;
	}
}