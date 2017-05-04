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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/IFreightRouteLineService.java
 * 
 * FILE NAME        	: IFreightRouteLineService.java
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
import java.util.Map;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteLineEntity;


/**
 * 走货路径线路服务接口
 * @author foss-zhujunyong
 * @date Nov 6, 2012 5:46:50 PM
 * @version 1.0
 */
public interface IFreightRouteLineService extends IService{
    /**
     * 
     * <p>添加走货路径线路</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:32:15 AM
     * @param freightRouteLine
     * @return
     * @see
     */
    FreightRouteLineEntity addFreightRouteLine(FreightRouteLineEntity freightRouteLine);
    
    /**
     * 
     * <p>作废走货路径线路</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:32:30 AM
     * @param freightRouteLine
     * @return
     * @see
     */
    FreightRouteLineEntity deleteFreightRouteLine(FreightRouteLineEntity freightRouteLine);

    /**
     * 
     * <p>更新走货路径线路</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:32:46 AM
     * @param freightRouteLine
     * @return
     * @see
     */
    FreightRouteLineEntity updateFreightRouteLine(FreightRouteLineEntity freightRouteLine);

    /**
     * 
     * <p>根据ID查询走货路径线路</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:33:00 AM
     * @param id
     * @return
     * @see
     */
    FreightRouteLineEntity queryFreightRouteLineById(String id);

    /**
     * 
     * <p>查询特定走货路径下的所有走货路径线路</p> 
     * @author foss-zhujunyong
     * @date Nov 6, 2012 6:20:14 PM
     * @return
     * @see
     */
    List<FreightRouteLineEntity> queryFreightRouteLineListByFreightRoute(String freightRouteVirtualCode);

    /**
     * 
     * <p>查询特定走货路径下的所有走货路径线路,不包括冗余属性</p> 
     * @author foss-zhujunyong
     * @date Mar 25, 2013 10:57:44 AM
     * @param freightRouteVirtualCode
     * @return
     * @see
     */
    List<FreightRouteLineEntity> querySimpleFreightRouteLineListByFreightRoute(String freightRouteVirtualCode);
    
    /**
     * 
     * <p>根据走货路径批量作废走货路径线路</p> 
     * @author foss-zhujunyong
     * @date Nov 13, 2012 10:49:34 AM
     * @param freightRouteVirtualCode
     * @param modifyUser
     * @return
     * @see
     */
    int deleteFreightRouteLineByFreightRoute(String freightRouteVirtualCode, String modifyUser);
    
    /**
     * 
     * <p>根据线路虚拟编码找出关联的走货路径线路，并按走货路径分组</p> 
     * @author foss-zhujunyong
     * @date Jan 21, 2013 3:29:10 PM
     * @param lineVirtualCode
     * @return Map<String, List<FreightRouteLineEntity>> key为走货路径虚拟编码，value为该走货路径用到的线路
     * @see
     */
    Map<String, List<FreightRouteLineEntity>> queryFreightRouteLineMapByLineVirtualCode(String lineVirtualCode);
    
    void validateSourceTargetOrder(FreightRouteLineEntity freightRouteLine);

	void invalidList(String key);

}
