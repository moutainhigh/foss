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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/exception/WayBillNoLocusException.java
 * 
 * FILE NAME        	: WayBillNoLocusException.java
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
 * 
 * 异常类
 * 
 * @author 136892
 */
public class WorkFlowException extends BusinessException {

	private static final long serialVersionUID = 1L;

	
	public static final String WORK_FLOW_ERROR = "该货物已被认领，请核实情况后再行审批";


	public static final String WORK_FLOW_OTHER_REASON= "其他原因变更失败";
	
	public static final String WORK_FLOW_APPROVE_FAILURE= "审批失败。";
	
	public static final String WORK_FLOW_GO_BACK_FAILURE= "退回失败。";
	
	public static final String WORK_FLOW_DRAFT_FAILURE= "起草失败，不存在该运单";
	
	public static final String WORK_FLOW_END= "该工作流已被审批。";


	public WorkFlowException(String code) {
		super();
		this.errCode = code;
	}

	// 有两个参数的构造函数
	public WorkFlowException(String code, Throwable cause) {
		super(code, cause);
		this.errCode = code;
	}

	// 有两个参数的构造函数
	public WorkFlowException(String code, String msg) {
		super(code, msg);
		this.errCode = code;
	}
}