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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/cache/ResourceUriCacheProvider.java
 * 
 * FILE NAME        	: ResourceUriCacheProvider.java
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

import java.util.List;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.cache.provider.ITTLCacheProvider;
import com.deppon.foss.framework.entity.IFunction;
import com.deppon.foss.framework.server.context.AppContext;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IResourceDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ResourceEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;

/**
 * 
 * 
 ******************************************* 
 * <b style="font-family:微软雅黑"><small>Description:功能权限缓存数据提供对象</small></b> </br>
 * <b style="font-family:微软雅黑"><small>HISTORY</small></b></br> <b
 * style="font-family:微软雅黑"><small> ID DATE PERSON REASON</small></b><br>
 ******************************************** 
 * <div style="font-family:微软雅黑,font-size:70%"> 1 2012-08-30 钟庭杰 新增 </div>
 ******************************************** 
 */
public class ResourceUriCacheProvider implements ITTLCacheProvider<IFunction> {

	private IResourceDao resourceDao;

	/**
	 * 
	 * @date Mar 11, 2013 3:05:41 PM
	 * @param resourceDao
	 * @see
	 */
	public void setResourceDao(IResourceDao resourceDao) {
		this.resourceDao = resourceDao;
	}

	@Override
	public IFunction get(String uri) {
		if(uri.equalsIgnoreCase(DictionaryValueConstants.BSE_RESOURCE_BELONG_SYSTEM_TYPE_WEB)){
			return resourceDao.queryResourceRoot(DictionaryValueConstants.BSE_RESOURCE_BELONG_SYSTEM_TYPE_WEB);
		}
		if(uri.equalsIgnoreCase(DictionaryValueConstants.BSE_RESOURCE_BELONG_SYSTEM_TYPE_GUI)){
			return resourceDao.queryResourceRoot(DictionaryValueConstants.BSE_RESOURCE_BELONG_SYSTEM_TYPE_GUI);
		}
		String contextPath = AppContext.getAppContext().getContextPath();
		String[] uris = new String[NumberConstants.NUMBER_3];
		int index = uri.indexOf("?");
		if(uri.indexOf("?")!=-1){
			uri = uri.substring(0, index);
		}
		uris[0] = uri;
		uris[1] = contextPath + uri;
		StringBuilder sb = new StringBuilder();
		sb.append(contextPath);
		sb.append("/#!");
		sb.append(uri.substring(1));
		uris[2] = sb.toString();
		List<ResourceEntity> resources = resourceDao
				.getResourceBatchByUri(uris);
		if (resources != null && resources.size() > 0) {
			return resources.get(0);
		} else {
			return null;
		}
	}
}
