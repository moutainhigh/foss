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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/exception/UserOrgRoleException.java
 * 
 * FILE NAME        	: UserOrgRoleException.java
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
 * 用来处理“用户组织角色信息”业务操作异常类类：SUC-41
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-11-7 下午7:27:05</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-11-7 下午7:27:05
 * @since
 * @version
 */
public class UserOrgRoleException extends BusinessException {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = -4142814802265236007L;

    /**
     * 用户ID不能为空
     */
    public static final String USERORGROLE_ID_NULL = "dpap.authorization.UserOrgRoleEntityIdNullException";

    public UserOrgRoleException(String code, String msg, Throwable cause) {
	super(code, msg, cause);
    }

    public UserOrgRoleException(String code, String msg) {
	super(code, msg);
    }

    public UserOrgRoleException(String msg) {
	super(msg);
    }
}
