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

import com.deppon.foss.module.base.baseinfo.api.shared.domain.PlatAreaDistanceEntity;

/**
 * 库区月台距离Dao接口
 * @author zxt
 * @date 2014-04-24
 * @version 1.0
 */
public interface IPlatAreaDistanceDao {

	PlatAreaDistanceEntity addDistance(PlatAreaDistanceEntity entity);

	int deleteDistance(PlatAreaDistanceEntity entity);
	
	int deleteDistanceByGoodsArea(String virtualCode);
    
	int deleteDistanceByPlatform(String virtualCode);
	
	PlatAreaDistanceEntity updateDistance(PlatAreaDistanceEntity entity);

	List<PlatAreaDistanceEntity> queryDistanceByGoodsArea(String virtualCode);

	List<PlatAreaDistanceEntity> queryDistanceByPlatform(String virtualCode);
	
	List<PlatAreaDistanceEntity> queryDistanceListByOrganizationCode(String organizationCode);
	
	PlatAreaDistanceEntity queryDistance(String goodsAreaVirtualCode, String platformVirtualCode);
	
}
