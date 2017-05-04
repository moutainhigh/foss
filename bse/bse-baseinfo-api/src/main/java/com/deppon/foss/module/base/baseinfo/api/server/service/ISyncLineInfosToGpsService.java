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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/ISyncLineInfosToGpsService.java
 * 
 * FILE NAME        	: ISyncLineInfosToGpsService.java
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
package com.deppon.foss.module.base.baseinfo.api.server.service;

import org.example.deppon_gps_service.CommonException;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.LineInfoDto;


/**
 * Foss向GPS传送班线数据信息Web Service客户端服务接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Administrator,date:2012-12-8 下午1:54:01</p>
 * @author 094463-foss-xieyantao
 * @date 2012-12-8 下午1:54:01
 * @since
 * @version
 */
public interface ISyncLineInfosToGpsService extends IService {
    
    /**
     * <p>向GPS同步线路信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-12-8 下午1:57:01
     * @param dto
     * @return
     * @see
     */
     boolean syncLineInfos(LineInfoDto dto);
     /**
      * <p>
      * 向短途GPS同步线路信息
      * </p>
      * 
      * @author 130346-foss-lifanghong
      * @param entity
      * @return
      * @throws CommonException
      * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISyncLineInfosToGpsService#syncLineInfos(com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity)
      */
	void syncLineInfosToGps(LineInfoDto dto);

}
