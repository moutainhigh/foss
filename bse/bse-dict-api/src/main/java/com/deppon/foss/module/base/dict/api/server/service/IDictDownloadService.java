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
 * PROJECT NAME	: bse-dict-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/dict/api/server/service/IDictDownloadService.java
 * 
 * FILE NAME        	: IDictDownloadService.java
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
package com.deppon.foss.module.base.dict.api.server.service;

import com.deppon.foss.base.util.ClientUpdateDataPack;
import com.deppon.foss.base.util.DataBundle;



/**
 * 数据字典下载服务
 * @author foss-zhujunyong
 * @date Oct 17, 2012 11:26:17 AM
 * @version 1.0
 */
public interface IDictDownloadService {

    /**
     * 
     * <p>数据字典-词 数据下载</p> 
     * @author 087584-foss-lijun
     * @date 2012-11-7 下午9:7:48
     * @param clientInfo
     * @return
     * @see
     */
    DataBundle downloadDataDictionaryData(ClientUpdateDataPack clientInfo);

    /**
     * 
     * <p>数据字典-值 数据下载</p> 
     * @author 087584-foss-lijun
     * @date 2012-11-7 下午9:22:25
     * @param clientInfo
     * @return
     * @see
     */
    DataBundle downloadDataDictionaryValueData(ClientUpdateDataPack clientInfo);

    /**
     * 
     * <p>系统配置参数 数据下载</p> 
     * @author 087584-foss-lijun
     * @date 2012-11-7 下午9:30:36
     * @param clientInfo
     * @return
     * @see
     */
    DataBundle downloadConfigurationParamsData(ClientUpdateDataPack clientInfo);
    
}
