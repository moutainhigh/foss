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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/IFreightRouteDao.java
 * 
 * FILE NAME        	: IFreightRouteDao.java
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

import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity;


/**
 * 走货路径Dao
 * @author foss-zhujunyong
 * @date Oct 29, 2012 1:49:18 PM
 * @version 1.0
 */
public interface IFreightRouteDao {

    /**
     * 
     * <p>添加走货路径</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:32:15 AM
     * @param freightRoute
     * @return
     * @see
     */
    FreightRouteEntity addFreightRoute(FreightRouteEntity freightRoute);
    
    /**
     * 
     * <p>作废走货路径</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:32:30 AM
     * @param freightRoute
     * @return
     * @see
     */
    FreightRouteEntity deleteFreightRoute(FreightRouteEntity freightRoute);
    
    /**
     * 
     * <p>生效或失效走货路径</p> 
     * @author foss-zhujunyong
     * @date Jan 16, 2013 7:57:05 PM
     * @param freightRoute
     * @return
     * @see
     */
    int validFreightRoute(FreightRouteEntity freightRoute);
    
    /**
     * 
     * <p>更新走货路径</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:32:46 AM
     * @param freightRoute
     * @return
     * @see
     */
    FreightRouteEntity updateFreightRoute(FreightRouteEntity freightRoute);

    /**
     * 
     * <p>根据ID查询走货路径</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:33:00 AM
     * @param id
     * @return
     * @see
     */
    FreightRouteEntity queryFreightRouteById(String id);

    /**
     * 
     * <p>根据虚拟代码查询走货路径</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:33:00 AM
     * @param virtualCode
     * @return
     * @see
     */
    FreightRouteEntity queryFreightRouteByVirtualCode(String virtualCode);

    
    /**
     * 
     * <p>根据条件查询走货路径</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:34:06 AM
     * @param freightRoute
     * @param start
     * @param limit
     * @return
     * @see
     */
    List<FreightRouteEntity> queryFreightRouteListByCondition(FreightRouteEntity freightRoute, int start, int limit);

    /**
     * 
     * <p>根据条件计算走货路径数量</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:34:24 AM
     * @param freightRoute
     * @return
     * @see
     */
    long countFreightRouteListByCondition(FreightRouteEntity freightRoute);
    
    /**
     * 
     * <p>下载走货路径表</p> 
     * @author foss-zhujunyong
     * @date Nov 6, 2012 9:35:51 AM
     * @param freightRoute
     * @return
     * @see
     */
    List<FreightRouteEntity> queryFreightRouteForDownload(FreightRouteEntity freightRoute);

    /**
     * 
     * <p>取最后修改时间</p> 
     * @author foss-zhujunyong
     * @date Dec 17, 2012 7:37:04 PM
     * @return
     * @see
     */
    Date queryLastModifyTime();
    
    /**
     * 
     * <p>取所有激活的走货路径进入缓存</p> 
     * @author foss-zhujunyong
     * @date Dec 17, 2012 7:44:09 PM
     * @return
     * @see
     */
    List<FreightRouteEntity> queryFreightRouteListForCache();

	/**
	 * 分页下载防止数据量太大 
	 * @param entity
	 * @param i
	 * @param thousand
	 * @return
	 */
    List<FreightRouteEntity> queryFreightRouteForDownloadByPage(FreightRouteEntity entity, int i,
			int thousand);
    
    /**
     * 根据始发外场，查找其空运走货路径
     * @author ibm-zhuwei
     * @date 2013-7-22 上午11:48:38
     * @param sourceCode
     */
    List<FreightRouteEntity> queryAirFreightRouteList(String sourceCode);
}
