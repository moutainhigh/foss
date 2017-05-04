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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/commonselector/ICommonPlatformService.java
 * 
 * FILE NAME        	: ICommonPlatformService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo-api
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.api.server.service
 * FILE    NAME: ICommonPlatformService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.PlatformEntity;

/**
 * 月台查询service接口定义
 * @author panGuangJun
 * @date 2012-12-1 上午9:20:09
 */
public interface ICommonPlatformService {
	/**
	 * 
	 * <p>根据查询条件查询月台数量</p> 
	 * @param plat
	 * @return long
	 * @author panGuangJun
	 * @date 2012-12-1 上午9:21:02
	 */
	 long countPlatformListByCondition(PlatformEntity plat);
    /** 
     * <p>根据查询条件查询月台列表</p> 
     * @author foss-zhujunyong
     * @date Oct 16, 2012 10:44:03 AM
     * @param _platform
     * @param start
     * @param limit
     * @return List<PlatformEntity>
	 * @author panGuangJun
	 * @date 2012-12-1 上午9:21:02
	 */
	 List<PlatformEntity> queryPlatformListByCondition(PlatformEntity plat,int start,int limit);
}
