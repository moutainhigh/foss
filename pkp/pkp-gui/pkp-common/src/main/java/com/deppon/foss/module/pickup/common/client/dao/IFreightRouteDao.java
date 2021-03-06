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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/dao/IFreightRouteDao.java
 * 
 * FILE NAME        	: IFreightRouteDao.java
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
import java.util.Map;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity;

/**
 * 
 * 走货路径数据持久层接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-10-16 下午7:53:59, </p>
 * @author foss-sunrui
 * @date 2012-10-16 下午7:53:59
 * @since
 * @version
 */
public interface IFreightRouteDao {

    /**
     * 
     * <p>通过主键获取走线路径</p> 
     * @author foss-sunrui
     * @date 2012-10-18 下午7:25:46
     * @param id
     * @return
     * @see
     */
    FreightRouteEntity queryByPrimaryKey(String id);
    
    /**
     * 
     * <p>通过发货营业部和收货营业部获取走货路径</p> 
     * @author foss-sunrui
     * @date 2012-10-18 下午7:26:15
     * @param map
     * @return
     * @see
     */
    FreightRouteEntity queryBySaleDept(Map<String,String> map);
    
    /**
     * 
     * <p>根据虚拟代码查询走货路径</p> 
     * @author foss-jiangfei
     * @date Oct 25, 2012 11:33:00 AM
     * @param virtualCode
     * @return
     * @see
     */
    FreightRouteEntity queryFreightRouteByVirtualCode(String virtualCode);
    
    
    /**
     * 
     * <p>根据条件查询走货路径</p> 
     * @author foss-jiangfei
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
	 * 功能：插条记录
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
    boolean addFreightRoute(FreightRouteEntity freightRoute);

/**
	 * 
	 * 功能：更新条记录
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	void updateFreightRoute(FreightRouteEntity freightRoute);
	
	/**
	 * 删除
	 * @param freightRouteEntity
	 */
	void delete(FreightRouteEntity freightRouteEntity);
    
}