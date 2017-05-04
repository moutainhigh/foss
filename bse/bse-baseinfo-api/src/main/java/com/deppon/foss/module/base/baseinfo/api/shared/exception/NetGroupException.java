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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/exception/NetGroupException.java
 * 
 * FILE NAME        	: NetGroupException.java
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
 * 网点组异常类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-12-5 下午3:13:34 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-12-5 下午3:13:34
 * @since
 * @version
 */
public class NetGroupException extends BusinessException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1355507272131677368L;

    public static final String NETGROUP_EXIST = "foss.bse.baseinfo.netGroup.netGroupExist";
    public static final String SOURCE_TARGET_EQUALS = "foss.bse.baseinfo.netGroup.sourceTargetEquals";
    public static final String ILLEGAL_NET_GROUP = "foss.bse.baseinfo.netGroup.illegalNetGroup";
    
    
    public NetGroupException(String errCode){
	super();
	super.errCode = errCode;
    }
    
    public NetGroupException(String code,String msg){
	
	super(code,msg);
    }

}
