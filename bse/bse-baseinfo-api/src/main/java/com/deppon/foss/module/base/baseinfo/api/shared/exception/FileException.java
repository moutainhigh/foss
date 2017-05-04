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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/exception/FileException.java
 * 
 * FILE NAME        	: FileException.java
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
 * 用来处理“文件上传下载”业务操作异常类：SUC-211
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:078838-foss-zhangbin,date:2012-12-10 上午11:00:35</p>
 * @author 078838-foss-zhangbin
 * @date 2012-12-10 上午11:00:35
 * @since
 * @version
 */
public class FileException extends BusinessException {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = -2631402327822344449L;

    
    public FileException(String code, String msg, Throwable cause) {
	super(code, msg, cause);
    }

    public FileException(String code, String msg) {
	super(code, msg);
    }
}
