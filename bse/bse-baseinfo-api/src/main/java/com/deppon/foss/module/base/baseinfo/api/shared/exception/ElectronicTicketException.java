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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/exception/EmployeeException.java
 * 
 * FILE NAME        	: EmployeeException.java
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
 * 用来处理“人员信息”业务操作异常类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-12-9 下午6:36:53</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-12-9 下午6:36:53
 * @since
 * @version
 */
public class ElectronicTicketException extends BusinessException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	

    public static final String ElectronicTicket_ADD_SUCCESS = "新增成功";
    
    public static final String ElectronicTicket_ADD_FAILURE = "新增失败";
    
    public static final String ElectronicTicket_DEL_SUCCESS = "作废成功";
    
    public static final String ElectronicTicket_DEL_FAILURE = "作废失败";
    
    public static final String ElectronicTicket_UPD_SUCCESS = "修改成功";
    
    public static final String ElectronicTicket_UPD_FAILURE = "修改失败";

    public ElectronicTicketException(String code, String msg) {
	super(code, msg);
    }

    public ElectronicTicketException(String msg, Throwable cause) {
	super(msg, cause);
    }

    public ElectronicTicketException(String msg) {
	super(msg);
    }
}
