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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/IOrganizationLayerDao.java
 * 
 * FILE NAME        	: IOrganizationLayerDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo-api
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.api.server.dao
 * FILE    NAME: IOrganizationLayerDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrganizationLayerEntity;

/**
 * 组织层级dao接口
 * @author 078823-foss-panGuangJun
 * @date 2012-12-13 上午11:30:37
 */
public interface IOrganizationLayerDao {

	/**
	 * 查询最后更新时间
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-13 上午11:37:16
	* @return Date
	* @param 
	 */
	Date getLastModifyTime();

	/**
	 *查询所有组织层级
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-13 上午11:38:13
	* @return Collection<OrganizationLayerEntity>
	* @param 
	 */
	Collection<OrganizationLayerEntity> getAllOrganizationLayer();
	
	/**
	 * 根据code查询出组织层级信息列表
	 * @author 101911-foss-zhouChunlai
	 * @date 2013-2-22 上午10:18:27
	 */
	List<OrganizationLayerEntity> queryOrgLayerInfoByCode(String code);
}
