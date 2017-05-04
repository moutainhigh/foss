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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/IOrgLayerService.java
 * 
 * FILE NAME        	: IOrgLayerService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.Date;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrganizationLayerEntity;
 
/**
 * 组织层级service
 * @author 101911-foss-zhouChunlai
 * @date 2013-2-22 下午5:33:15
 */
public interface IOrgLayerService extends IService{
    
    
	/**
	 * 根据组织编号以及发生日期从缓存中查询符合条件组织信息实体。
	 * 如缓存中没有查询到相应实体，则直接从数据库中进行查询
	 * @author 101911-foss-zhouChunlai
	 * @date 2013-2-22 上午9:43:26
	 */
	OrganizationLayerEntity getOrgLayerEntityByCache(String code, Date date);
    
}
