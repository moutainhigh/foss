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
 *  PROJECT NAME  : tfr-unload-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/api/shared/exception/QueryUnloadProgressException.java
 *  
 *  FILE NAME          :QueryUnloadProgressException.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-unload-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.unload.api.shared.exception
 * FILE    NAME: QueryUnloadProgressException.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.platform.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 查询卸车进度异常类
 * @author 046130-foss-xuduowei
 * @date 2012-12-19 上午9:05:47
 */
public class QueryUnloadProgressException extends BusinessException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6954293679326279521L;

	public QueryUnloadProgressException(String msg) {
		super(msg);
		this.errCode = msg;
	}
	
	

}