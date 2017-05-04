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
 * PROJECT NAME	: bse-baseinfo
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/cache/OrgLayerCacheDeal.java
 * 
 * FILE NAME        	: OrgLayerCacheDeal.java
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
package com.deppon.foss.module.base.baseinfo.server.cache;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrganizationLayerEntity;

 
/**
 * 组织层级Cache处理
 * @author 101911-foss-zhouChunlai
 * @date 2013-2-22 上午9:29:56
 */
public class OrgLayerCacheDeal {

	private OrgLayerCache orgLayerCache;

	/**
	 * 根据组织编号以及发生日期从缓存中查询符合条件组织信息实体。
	 * 如缓存中没有查询到相应实体，则直接从数据库中进行查询
	 * @author 101911-foss-zhouChunlai
	 * @date 2013-2-22 上午9:43:26
	 */
	public OrganizationLayerEntity getOrgLayerEntityByCache(String code, Date date) {
		List<OrganizationLayerEntity> orgLayerEntityList = orgLayerCache.get(code);
		OrganizationLayerEntity entity = null;
		if(CollectionUtils.isNotEmpty(orgLayerEntityList)) {
			for (OrganizationLayerEntity orgLayerEntity : orgLayerEntityList) {
				Date beginTime = orgLayerEntity.getCreateDate();
				Date endTime = orgLayerEntity.getModifyDate();
				if(beginTime.getTime() <= date.getTime() && endTime.getTime() > date.getTime()) {
					entity = orgLayerEntity;
					break;
				}
			}
		} 
		return entity;
	}

	public void setOrgLayerCache(OrgLayerCache orgLayerCache) {
		this.orgLayerCache = orgLayerCache;
	}

}
