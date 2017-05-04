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
 *  PROJECT NAME  : tfr-load-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/exception/HandOverBillExceptionCode.java
 *  
 *  FILE NAME          :HandOverBillExceptionCode.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 
* @ClassName: LFDrivingFileException
* @Description: 长途车行驶档案 异常类
* @author ZX_189284
* @date 2016-5-24 下午2:32:20
*
 */
public class LFDrivingFileException extends BusinessException {	
	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/ 
	private static final long serialVersionUID = 1L;

	public LFDrivingFileException() {
		super();
	}

	public LFDrivingFileException(String errCode, String msg, String natvieMsg, Throwable cause) {
		super(errCode, msg, natvieMsg, cause);
	}

	public LFDrivingFileException(String errCode, String msg, String natvieMsg) {
		super(errCode, msg, natvieMsg);
	}

	public LFDrivingFileException(String errCode, String msg, Throwable cause) {
		super(errCode, msg, cause);
	}

	public LFDrivingFileException(String errCode, String msg) {
		super(errCode, msg);
	}

	public LFDrivingFileException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public LFDrivingFileException(String msg) {
		super(msg);
		this.errCode = msg;
	}
	
	public LFDrivingFileException(String code,Object... args) {
		super(code,args);
	}
	
	public LFDrivingFileException(String code,String msg, Object... args) {
		super(code,msg,args);
	}
}