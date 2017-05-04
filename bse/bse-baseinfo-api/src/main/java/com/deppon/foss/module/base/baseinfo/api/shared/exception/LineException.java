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
 * 始发线路/到达线路异常类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-11-9 下午6:58:13 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-11-9 下午6:58:13
 * @since
 * @version
 */
public class LineException extends BusinessException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 6125710440831899003L;

    public static final String SIMPLECODE_EXIST = "foss.bse.baseinfo.line.lineSimpleNameExist";
    public static final String USED_BY_FREIGHTROUTE = "foss.bse.baseinfo.line.usedByFreightRoute";
    public static final String DUPLICATION = "foss.bse.baseinfo.line.duplication";
    
    public static final String LINESITE_CAN_NOT_DUPLICATION = "foss.bse.baseinfo.line.siteCanNotDuplication";
    public static final String LINEITEM_NOT_EXIST_IN_TRANSFER_LINE = "foss.bse.baseinfo.line.lineItemNotExistInTransferLine";
    public static final String SOURCE_TARGET_SEQUENCE_CANNOT_BE_EMPTY = "foss.bse.baseinfo.line.sourceTargetSequenceCanNotBeEmpty";
    public static final String LINEITEM_SITE_CAN_NOT_DUPLICATION = "foss.bse.baseinfo.line.itemSiteCanNotDuplication";
    public static final String SEQUENCE_MUST_BE_NUMBER = "foss.bse.baseinfo.line.sequenceMustBeNumber";
    public static final String LINEITEM_MUST_BY_ORDER = "foss.bse.baseinfo.line.lineItemMustByOrder";
    public static final String VALID_LINE_CAN_NOT_BE_DELETED = "foss.bse.baseinfo.line.validLineCanNotBeDeleted";
    public static final String LINE_SITE_MUST_BY_ORDER = "foss.bse.baseinfo.line.lineSiteMustByOrder";
    public static final String LINE_DOES_NOT_EXIST = "foss.bse.baseinfo.line.lineDoesNotExist";
    public static final String VALID_LINE_CAN_NOT_CHANGE_SITE = "foss.bse.baseinfo.line.validLineCanNotChangeSite";
    
    public LineException(String errCode){
	super();
	super.errCode = errCode;
    }
    
    public LineException(String errCode, Object... para) {
	super(errCode, para);
	super.errCode = errCode;
    }
    

}
