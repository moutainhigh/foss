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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/exception/TodoActionException.java
 * 
 * FILE NAME        	: TodoActionException.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-waybill-share
 * PACKAGE NAME: com.deppon.foss.module.pickup.waybill.shared.exception
 * FILE    NAME: TodoActionException.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.pickup.waybill.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 处理待办事项异常
 * 
 * @author 043258-foss-zhaobin
 * @date 2012-10-24 下午2:18:35
 */
public class TodoActionException extends BusinessException {

	/**
	 * 序列化版本号（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String TODO_EXCEPTION = "pkp.waybill.todoService.exception";
	
	//删除待办事项消息失败！原因：
	public static final String DELETEPENDINGS_FAIL="foss.pkp.waybill.waybillManagerService.exception.failDeletePendings";
    
	/**
	 * 构造方法
	 * @param code
	 */
	public TodoActionException(String code) {
		super();
		this.errCode = code;
	}

	/**
	 * （创建一个新的实例 ）TodoActionException
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-19 下午5:26:53
	 */
	public TodoActionException() {
		super();
	}

	/**
	 * （创建一个新的实例 ）TodoActionException
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-19 下午5:26:53
	 */
	public TodoActionException(String code, String msg, String natvieMsg,
			Throwable cause) {
		super(code, msg, natvieMsg, cause);
	}

	/**
	 * （创建一个新的实例 ）TodoActionException
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-19 下午5:26:53
	 */
	public TodoActionException(String code, String msg, String natvieMsg) {
		super(code, msg, natvieMsg);
	}

	/**
	 * （创建一个新的实例 ）TodoActionException
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-19 下午5:26:53
	 */
	public TodoActionException(String code, String msg, Throwable cause) {
		super(code, msg, cause);
	}

	/**
	 * （创建一个新的实例 ）TodoActionException
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-19 下午5:26:53
	 */
	public TodoActionException(String code, String msg) {
		super(code, msg);
	}

	/**
	 * （创建一个新的实例 ）TodoActionException
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-19 下午5:26:53
	 */
	public TodoActionException(String msg, Throwable cause) {
		super(msg, cause);
		this.errCode = msg;
	}
	
	public TodoActionException(String code,Object... arges ){
		super(code,arges);
		this.errCode = code;
	}

}