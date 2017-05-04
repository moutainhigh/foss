/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/exception/ResourceException.java
 * 
 * FILE NAME        	: ResourceException.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 
 * 
*******************************************
* <b style="font-family:微软雅黑"><small>Description:与资源信息有关的异常</small></b>   </br>
* <b style="font-family:微软雅黑"><small>HISTORY</small></b></br>
* <b style="font-family:微软雅黑"><small> ID      DATE    PERSON     REASON</small></b><br>
********************************************
* <div style="font-family:微软雅黑,font-size:70%"> 
* 1 2012-11-6 钟庭杰    新增
* </div>  
********************************************
 */
public class ResourceException extends BusinessException {

	private static final long serialVersionUID = -1982586715098015347L;

	/**
	 * 资源编码列表为空
	 */
	public static final String RESOURCE_CODE_LIST_NULL = "foss.authorization.ResourceCodeListNullException";

	/**
	 * 资源URI为空
	 */
	public static final String RESOURCE_URI_NULL =  "foss.authorization.ResourceUriNullException";
	
	/**
	 * 资源列表为空
	 */
	public static final String RESOURCES_NULL =  "foss.authorization.ResourceNullException";

	/**
	 * 资源编码为空
	 */
	public static final String RESOURCE_CODE_NULL = "foss.authorization.ResourceCodeNullException";
	

	/**
	 * 权限编码已存在
	 */
	public static final String RESOURCE__CODE_EXIST = "foss.authorization.ResourceCodeNullException";

	/**
	 * 权限编码为空
	 */
	public static final String RESOURCE__CODE_URI_NULL = "foss.authorization.ResourceCodeNullException";
	

	/**
	 * 权限入口已存在
	 */
	public static final String ENTRY_NTRY_URI_EXIST = "foss.authorization.ResourceCodeNullException";

	/**
	 * 权限入口为空
	 */
	public static final String RESOURCE__ENTRY_URI_NULL = "foss.authorization.ResourceCodeNullException";
	
	
	public ResourceException(String errCode){
		super();
		super.errCode = errCode;
	}	
	
	public ResourceException(String code,String msg){
		super(code,msg);
	}
}
