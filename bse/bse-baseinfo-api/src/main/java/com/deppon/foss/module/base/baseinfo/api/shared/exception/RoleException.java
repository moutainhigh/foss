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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/exception/RoleException.java
 * 
 * FILE NAME        	: RoleException.java
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
 * <b style="font-family:微软雅黑"><small>Description:与角色信息有关的异常</small></b> </br>
 * <b style="font-family:微软雅黑"><small>HISTORY</small></b></br> <b
 * style="font-family:微软雅黑"><small> ID DATE PERSON REASON</small></b><br>
 ******************************************** 
 * <div style="font-family:微软雅黑,font-size:70%"> 1 2012-08-30 钟庭杰 新增 </div>
 ******************************************** 
 */
public class RoleException extends BusinessException {

    private static final long serialVersionUID = 590525254182760551L;

    /**
     * 角色编码不能为空
     */
    public static final String ROLE_CODE_NULL = "foss.authorization.RoleCodeNullException";

	public static final String ROLE_NULL = "foss.authorization.RoleNullException";

	public static final String ROLE_NAME_NULL = "foss.authorization.RoleNameNullException";

    /**
     * 异常的构造方法
     * 
     * @param errCode
     * @since
     */
    public RoleException(String errCode) {
	super();
	super.errCode = errCode;
    }

    public RoleException(String code, String msg) {
	super(code, msg);
    }

    public RoleException(String msg, Throwable cause) {
	super(msg, cause);
    }
}
