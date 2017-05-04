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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/common/api/shared/exception/TfrBusinessException.java
 *  
 *  FILE NAME          :TfrBusinessException.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 产品价格默认异常
 * @author foss-wuyingjie
 * @date 2012-10-26 下午4:31:05
 */
public class PriceBusinessException extends BusinessException{

	private static final long serialVersionUID = 8702128402954184055L;
	
	public static final String EXPORT_FILE_ERROR_CODE = "文件导出失败";

	public static final String ILLEGAL_ARGUMENT_EXCEPTION = "数据格式转换失败";
	
	public static final String DUPLICATE_CLIENT_OPERATION = "禁止多客户端同时操作相同数据";
	
	public static final String GET_CURRENTUSERINFO_FAILURE = "foss.transfer.exception.getCurrentUserinfoFailure";
	
	public static final String ADD_BACKLOG_SCHEDULE_FAILURE = "foss.transfer.exception.addBacklogScheduleFailure";
	
	public static final String SN_PZCC_ERROR_ORG = "始发地、目的地不能为空";

	public PriceBusinessException() {
		super();
	}

	public PriceBusinessException(String errCode, String msg, String natvieMsg, Throwable cause) {
		super(errCode, msg, natvieMsg, cause);
	}

	public PriceBusinessException(String errCode, String msg, String natvieMsg) {
		super(errCode, msg, natvieMsg);
	}

	public PriceBusinessException(String errCode, String msg, Throwable cause) {
		super(errCode, msg, cause);
	}

	public PriceBusinessException(String errCode, String msg) {
		super(errCode, msg);
	}

	public PriceBusinessException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public PriceBusinessException(String msg) {
		super(msg);
		this.errCode = msg;
	}
	
	public PriceBusinessException(String code,Object... args) {
		super(code,args);
	}
	
	public PriceBusinessException(String code,String msg, Object... args) {
		super(code,msg,args);
	}
}