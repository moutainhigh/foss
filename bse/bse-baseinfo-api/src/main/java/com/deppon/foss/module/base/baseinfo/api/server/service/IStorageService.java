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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/IStorageService.java
 * 
 * FILE NAME        	: IStorageService.java
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

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.StorageEntity;


/**
 * 库位服务
 * @author foss-zhujunyong
 * @date Oct 17, 2012 10:06:55 AM
 * @version 1.0
 */
public interface IStorageService extends IService{
	/**
	 * 
	 * <p>增加库位</p> 
	 * @date Oct 12, 2012 11:02:38 AM
	 * @param storage
	 * @return
	 * @see
	 */
    	StorageEntity addStorage(StorageEntity storage);

	/**
	 *
	 * <p>作废库位</p> 
	 * @author zhujunyong
	 * @date Oct 12, 2012 11:03:14 AM
	 * @param storage
	 * @return
	 * @see
	 */
    	StorageEntity deleteStorage(StorageEntity storage);
	
	/**
	 * 
	 * <p>更新库位</p> 
	 * @author zhujunyong
	 * @date Oct 12, 2012 11:03:45 AM
	 * @param storage
	 * @return
	 * @see
	 */
	StorageEntity updateStorage(StorageEntity storage);
	
	/**
	 * 
	 * <p>取单个库位</p> 
	 * @author zhujunyong
	 * @date Oct 12, 2012 11:04:00 AM
	 * @param virtualCode
	 * @return
	 * @see
	 */
	StorageEntity queryStorageById(String id);
	
	/**
	 * 
	 * <p>按条件查询库位</p> 
	 * @author zhujunyong
	 * @date Oct 12, 2012 11:06:14 AM
	 * @param storage
	 * @param start
	 * @param limit
	 * @return
	 * @see
	 */
	List<StorageEntity> queryStorageListByCondition(StorageEntity storage, int start, int limit);

	/**
	 * 
	 * <p>按条件查询库位</p> 
	 * @author zhujunyong
	 * @date Oct 12, 2012 11:06:14 AM
	 * @param storage
	 * @param start
	 * @param limit
	 * @param userCode 员工号
	 * @param currentOrgCode 用户当前登录部门编码
	 * @return
	 * @see
	 */
	List<StorageEntity> queryStorageListByCondition(StorageEntity storage, int start, int limit, String userCode, String deptCode);

	/**
	 * 
	 * <p>按条件查询库位</p> 
	 * @author zhujunyong
	 * @date Oct 12, 2012 11:06:14 AM
	 * @param storage
	 * @return
	 * @see
	 */
	List<StorageEntity> queryStorageListByCondition(StorageEntity storage);
	
	/**
	 * 
	 * <p>按条件查询库位总数</p> 
	 * @author zhujunyong
	 * @date Oct 12, 2012 11:06:33 AM
	 * @param storage
	 * @return
	 * @see
	 */
	long countStorageListByCondition(StorageEntity storage);

	/**
	 * 
	 * <p>按条件查询库位总数</p> 
	 * @author zhujunyong
	 * @date Oct 12, 2012 11:06:33 AM
	 * @param storage
	 * @param userCode 员工号
	 * @return
	 * @see
	 */
	long countStorageListByCondition(StorageEntity storage, String userCode, String deptCode);
	
	/**
	 * 
	 * <p>查询某一外场下的所有库位列表</p> 
	 * @author foss-zhujunyong
	 * @date Oct 16, 2012 10:42:43 AM
	 * @param organizationCode
	 * @return
	 * @see
	 */
	List<StorageEntity> queryStorageListByOrganizationCode(String organizationCode);

	/**
	 * 
	 * <p>整合库位列表，用于取消或新增库位对某一库区的关联</p> 
	 * @author foss-zhujunyong
	 * @date Nov 19, 2012 7:56:32 PM
	 * @param storageList
	 * @see
	 */
	//void mergeStorages(List<StorageEntity> storageList, String goodsAreaVirtualCode);
	
	/**
	 * 
	 * <p>查询挂在某个库区下的所有库位</p> 
	 * @author foss-zhujunyong
	 * @date Nov 20, 2012 1:45:30 PM
	 * @param goodsAreaVirtualCode
	 * @return
	 * @see
	 */
	List<StorageEntity> queryStorageListByGoodsArea(String goodsAreaVirtualCode);

	
	/**
	 * 
	 * <p>从缓存中查询挂在某个库区下的所有库位</p> 
	 * @author foss-zhujunyong
	 * @date Feb 28, 2013 11:25:15 AM
	 * @param organizationCode
	 * @param goodsAreaVirtualCode
	 * @return
	 * @see
	 */
	List<StorageEntity> queryStorageListByGoodsAreaFromCache(String organizationCode, String goodsAreaVirtualCode);
	
	
	/**
	 * 
	 * <p>批量作废库位</p> 
	 * @author foss-zhujunyong
	 * @date Nov 28, 2012 11:25:32 AM
	 * @param virtualCodes
	 * @param modifyUser
	 * @return
	 * @see
	 */
	int deleteStorages(List<String> virtualCodes, String modifyUser);
	  /**
     * 
     * <p>
     * 按库位条件查询库位
     * </p>
     * 
     * @author foss-zhujunyong
     * @date Jan 14, 2013 5:18:15 PM
     * @param orgCode外场编码
     * @param storageCode库位编码
     * @param goodsAreaVirtualCode 库区虚拟编码
     * @return
     * @see
     */
	StorageEntity queryStorageByCode(String orgCode, String storageCode,
			String goodsAreaVirtualCode);
}
