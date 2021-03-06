/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/dao/IAdministrativeRegionsDao.java
 * 
 * FILE NAME        	: IAdministrativeRegionsDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
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
package com.deppon.foss.module.pickup.common.client.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;


/**
 * 行政区域 
 * @author 105089-foss-yangtong
 * @date 2012-11-6 下午3:33:56
 */
public interface IAdministrativeRegionsDao {
	
	/**
	 * 
	 * 功能：插条记录
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
	boolean addAdministrativeRegions(AdministrativeRegionsEntity administrativeRegions);

	/**
	 * 
	 * 功能：更新条记录
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	void updateAdministrativeRegions(AdministrativeRegionsEntity administrativeRegions);
	/**
     * 精确查询 
     * 通过 CODE 查询
     * 
     * @author foss-jiangfei
     * @date 2012-10-31 下午4:9:39
     * @see 
     * */
    
    AdministrativeRegionsEntity queryAdministrativeRegionsByCode(String code) ;

	/**
	 * 删除
	 * @param aministrativeRegions
	 */
	void delete(AdministrativeRegionsEntity aministrativeRegions);
	
	/**
	 * 根据codes 查询行政区域集合
	 * @author 097972-foss-dengtingting
	 * @date 2013-3-28 上午10:14:21
	 */
	List<AdministrativeRegionsEntity> queryAdministrativeRegionsByCodeActive(
			    List<String> codes, String active);
}