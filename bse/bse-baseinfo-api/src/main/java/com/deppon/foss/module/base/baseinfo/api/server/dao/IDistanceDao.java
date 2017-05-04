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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/IDistanceDao.java
 * 
 * FILE NAME        	: IDistanceDao.java
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
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.DistanceEntity;

/**
 * 库位月台dao
 * @author foss-zhujunyong
 * @date Oct 18, 2012 9:29:18 AM
 * @version 1.0
 */
public interface IDistanceDao {

	/**
	 * 
	 * <p>添加库位到月台的距离</p> 
	 * @author foss-zhujunyong
	 * @date Oct 17, 2012 4:30:46 PM
	 * @param entity
	 * @return
	 * @see
	 */
	DistanceEntity addDistance(DistanceEntity entity);

	/**
	 * 
	 * <p>根据库区虚拟代码和月台虚拟代码作废库位月台距离</p> 
	 * @author foss-zhujunyong
	 * @date Oct 17, 2012 4:32:03 PM
	 * @param entity
	 * @return
	 * @see
	 */
	int deleteDistance(DistanceEntity entity);
	
	/**
	 * 
	 * <p>根据库区虚拟代码批量作废库位月台距离</p> 
	 * @author foss-zhujunyong
	 * @date Oct 17, 2012 4:36:41 PM
	 * @param storageVirtualCode
	 * @return
	 * @see
	 */
	int deleteDistanceByStorage(String storageVirtualCode);
    
	/**
	 * 
	 * <p>根据月台虚拟代码批量作废库位月台距离</p> 
	 * @author foss-zhujunyong
	 * @date Oct 17, 2012 5:49:51 PM
	 * @param platformVirtualCode
	 * @return
	 * @see
	 */
	int deleteDistanceByPlatform(String platformVirtualCode);
	
	/**
	 * 
	 * <p>更新库位月台距离</p> 
	 * @author foss-zhujunyong
	 * @date Oct 18, 2012 9:41:00 AM
	 * @param entity
	 * @return
	 * @see
	 */
	DistanceEntity updateDistance(DistanceEntity entity);

	/**
	 * 
	 * <p>查询指定库位的所有距离</p> 
	 * @author foss-zhujunyong
	 * @date Oct 18, 2012 9:45:03 AM
	 * @param virtualStorageCode
	 * @return
	 * @see
	 */
	List<DistanceEntity> queryDistanceByStorage(String virtualStorageCode);

	/**
	 * 
	 * <p>查询指定月台的所有距离</p> 
	 * @author foss-zhujunyong
	 * @date Oct 18, 2012 9:45:29 AM
	 * @param virtualPlatformCode
	 * @return
	 * @see
	 */
	List<DistanceEntity> queryDistanceByPlatform(String virtualPlatformCode);
	
	/**
	 * 
	 * <p>查询指定外场的所有月台到库位的距离</p> 
	 * @author foss-zhujunyong
	 * @date Mar 11, 2013 5:04:45 PM
	 * @param organizationCode
	 * @return
	 * @see
	 */
	List<DistanceEntity> queryDistanceListByOrganizationCode(String organizationCode);
}
