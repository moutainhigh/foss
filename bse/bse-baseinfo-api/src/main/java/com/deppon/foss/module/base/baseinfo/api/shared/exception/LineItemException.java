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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/exception/LineException.java
 * 
 * FILE NAME        	: LineException.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;


/**
 * 
 * 线段异常类
 * @author foss-zhujunyong
 * @date Mar 14, 2013 1:43:10 PM
 * @version 1.0
 */
public class LineItemException extends BusinessException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -6275652785631411414L;

    public static final String LINE_DOES_NOT_EXIST = "foss.bse.baseinfo.lineItem.lineDoesNotExist";
    public static final String LINEITEM_DOES_NOT_EXIST = "foss.bse.baseinfo.lineItem.lineItemDoesNotExist";
    public static final String VALID_LINE_CAN_NOT_CHANGE_SITE = "foss.bse.baseinfo.lineItem.validLineCanNotChangeSite";
    public static final String VALID_LINE_CAN_NOT_ADD_OR_REMOVE = "foss.bse.baseinfo.lineItem.validLineCanNotAddOrRemove";
    
    public LineItemException(String errCode){
	super();
	super.errCode = errCode;
    }
    
    public LineItemException(String code,String msg){
	super(code,msg);
    }
    
    public LineItemException(String code, Object[] argument) {
	super();
	super.errCode = code;
	super.setErrorArguments(argument);
    }

}
