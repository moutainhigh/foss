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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/IStorageDao.java
 * 
 * FILE NAME        	: IStorageDao.java
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

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.StorageEntity;


/**
 * 库位
 * @author foss-zhujunyong
 * @date Oct 16, 2012 3:06:43 PM
 * @version 1.0
 */
public interface IStorageDao {

	// 增加
    	StorageEntity addStorage(StorageEntity storage);

	// 删除
    	StorageEntity deleteStorage(StorageEntity storage);
	
	// 更新
	StorageEntity updateStorage(StorageEntity storage);
	
	// 取单个实体
	StorageEntity queryStorageById(String id);
	
	// 按条件查询
    /** 
     * <p>按条件查询库位列表</p> 
     * @author foss-zhujunyong
     * @date Oct 16, 2012 6:17:40 PM
     * @param storage
     * @param start
     * @param limit
     * @return 
     */
	List<StorageEntity> queryStorageListByCondition(StorageEntity storage, int start, int limit);

	// 按条件查询的总数
	long countStorageListByCondition(StorageEntity storage);

	// 查询某一外场下的所有库位列表
	List<StorageEntity> queryStorageListByOrganizationCode(String organizationCode);

	// 批量作废库位
	int deleteStorages(List<String> virtualCodes, String modifyUser);
	
	// 取最后修改时间
	Date queryLastModifyTime();
	
	// 装置所有有效的库位到cache中
	List<StorageEntity> queryStorageListForCache();
	
	// 查询所有指定日期后更新的库位
	List<StorageEntity> queryStorageListViaDateForCache(Date date);
}
