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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/dao/IFreightRouteLineDao.java
 * 
 * FILE NAME        	: IFreightRouteLineDao.java
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

import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteLineEntity;

/**
 * 走货路径线路
 * @author 105089-foss-yangtong
 * @date 2012-10-24 下午3:15:09
 */
public interface IFreightRouteLineDao {
	
	/**
	 * 
	 * 功能：按id查询
	 * @param:
	 * @return Department
	 * @since:1.6
	 */
	FreightRouteLineEntity queryById(String id);

	/**
	 * 
	 * 功能：插条记录
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
	boolean addFreightRouteLine(FreightRouteLineEntity freightRouteLine);

	/**
	 * 
	 * 功能：更新条记录
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	void updateFreightRouteLine(FreightRouteLineEntity freightRouteLine);
	
	/**
	 * 功能：查询记录
	 * @param:
	 * @return List<FreightRouteLineEntity>
	 * @since:1.6
	 */
	List<FreightRouteLineEntity> queryAll();
  
    /**
     * 
     * <p>查询特定走货路径下的所有走货路径线路</p> 
     * @author foss-jiangfei
     * @date Nov 6, 2012 6:20:14 PM
     * @return
     * @see
     */
    List<FreightRouteLineEntity> queryFreightRouteLineListByFreightRoute(FreightRouteLineEntity freightRouteLine);

	/**
	 * 删除
	 * @param freightRouteLineEntity
	 */
	void delete(FreightRouteLineEntity freightRouteLineEntity);

}