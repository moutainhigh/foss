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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/exception/UserException.java
 * 
 * FILE NAME        	: UserException.java
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
 * 用来处理“用户信息”业务操作异常类类：SUC-226
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-11-7 下午7:28:35</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-11-7 下午7:28:35
 * @since
 * @version
 */
public class UserException extends BusinessException {

    private static final long serialVersionUID = -1982586715098015347L;

    /**
     * 登录名不能为空
     */
    public static final String LOGINNAME_NULL = "foss.authorization.LoginNameNullException";

    /**
     * 用户ID不能为空
     */
    public static final String USER_ID_NULL = "foss.authorization.UserIdNullException";

    /**
     * 用户对象不能为空
     */
    public static final String USER_IS_NULL = "foss.authorization.UserIsNullException";

    /**
     * 用户密码不能为空
     */
    public static final String PW_NULL = "foss.authorization.UserPasswordNullException";

    /**
     * 用户状态不能为空
     */
    public static final String USER_STATUS_NULL = "foss.authorization.UserStatusNullException";

    /**
     * 用户已经存在
     */
    public static final String USER_EXIST = "foss.authorization.UserIsExistException";

    /**
     * 用户编码不能为空
     */
	public static final String USER_CODE_NULL = "foss.authorization.UserCodeNullException";
	
	/**
	 * 当前登录用户对应的职员为空
	 */
	public static final String CURRENT_USER_EMP_NULL = "foss.authorization.currentUserEmpNull";
	
	/**
	 * 当前登录用户直属部门为空
	 */
	public static final String CURRENT_USER_EMP_ORG_NULL = "foss.authorization.currentUserEmpOrgNull";
	
	/**
	 * 用户未配置角色信息
	 */
	public static final String CURRENT_USER_NO_ROLE = "foss.authorization.currentUserNoRoleInfo";
	
	/**
	 * 用户修改密码失败信息
	 */
	public static final String UPDATE_PSW_FAIL = "foss.authorization.updatePswFail";
	
	
    public UserException(String code) {
	super();
	super.errCode = code;
    }

    public UserException(String code, String msg, Throwable cause) {
	super(code, msg, cause);
    }

    public UserException(String code, String msg) {
    super(code, msg);
    }
}
