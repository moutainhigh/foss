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
 *  PROJECT NAME  : tfr-package-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/packaging/api/shared/exception/PackagingException.java
 *  
 *  FILE NAME          :PackagingException.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/**
 * 
 */
package com.deppon.foss.module.transfer.packaging.api.shared.exception;

import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;


/**
 * 
 * 包装异常类
 * @author 046130-foss-xuduowei
 * @date 2012-11-01 上午8:15:10
 */
public class PackagingException extends TfrBusinessException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4834230185776316571L;

	public PackagingException(String msg){
		super(msg);
		this.errCode = msg;
	}
}