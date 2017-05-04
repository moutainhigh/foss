/*******************************************************************************
 * 
 * Copyright 2013 BSE TEAM
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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/IFreightRouteService.java
 * 
 * FILE NAME        	: IFreightRouteService.java
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

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LineItemEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.FreightRouteLineDto;


/**
 * 走货路径服务类
 * @author foss-zhujunyong
 * @date Nov 1, 2012 1:39:33 PM
 * @version 1.0
 */
public interface IFreightRouteService extends IService{

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
     * <p>批量作废走货路径</p> 
     * @author foss-zhujunyong
     * @date Jan 11, 2013 10:12:54 AM
     * @param virtualCodes
     * @param modifyUser
     * @return
     * @see
     */
    int deleteFreightRouteList(List<String> virtualCodes, String modifyUser);    
    
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
     * <p>根据虚拟代码查询走货路径, 不包括冗余属性</p> 
     * @author foss-zhujunyong
     * @date Mar 25, 2013 3:14:06 PM
     * @param virtualCode
     * @return
     * @see
     */
    FreightRouteEntity querySimpleFreightRouteByVirtualCode(String virtualCode);
    
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
     * <p>根据条件查询走货路径, 不包括冗余属性</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:34:06 AM
     * @param freightRoute
     * @param start
     * @param limit
     * @return
     * @see
     */
    List<FreightRouteEntity> querySimpleFreightRouteListByCondition(FreightRouteEntity freightRoute, int start, int limit);

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
    List<FreightRouteEntity> queryFreightRouteListByCondition(FreightRouteEntity freightRoute);
    
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
     * <p>通过出发部门，到达部门，产品类型和出发时间选取一条走货路径</p> 
     * @author foss-zhujunyong
     * @date Nov 1, 2012 1:48:53 PM
     * @param orginalOrganizationCode 出发部门（营业部或外场）编码
     * @param destinationOrganizationCode 到达部门（营业部或外场）编码
     * @param productType 第三级产品类型
     * @param time 出发时间
     * @return
     * @see
     */
    List<FreightRouteLineDto> queryFreightRouteBySourceTarget(String orginalOrganizationCode, String destinationOrganizationCode, String productCode, Date time);
    
    /**
     * 
     * <p>通过出发部门，到达部门，产品类型选取一条走货路径</p> 
     * @author foss-zhujunyong
     * @date Nov 1, 2012 1:48:53 PM
     * @return
     * @see
     */
    FreightRouteEntity queryFreightRouteBySourceTarget(String sourceCode, String targetCode, String productCode);
    

    /**
     * 
     * <p>使走货路径生效，需要验证业务规则</p> 
     * @author foss-zhujunyong
     * @date Jan 9, 2013 2:11:17 PM
     * @param virtualCode
     * @param modifyUser
     * @return
     * @see
     */
    FreightRouteEntity valid(String virtualCode, String modifyUser);
    
    /**
     * 
     * <p>使走货路径失效</p> 
     * @author foss-zhujunyong
     * @date Jan 9, 2013 2:39:35 PM
     * @param virtualCode
     * @param modifyUser
     * @return
     * @see
     */
    FreightRouteEntity invalid(String virtualCode, String modifyUser);
        
    /**
     * 
     * <p>更新线段的时候因为要同步更新走货路径线路的时效，所以放到此处进行更新</p> 
     * @author foss-zhujunyong
     * @date Jan 21, 2013 3:43:47 PM
     * @param lineItem
     * @return
     * @see
     */
    LineItemEntity updateLineItem(LineItemEntity lineItem);
    
    /**
     * 
     * <p>查询两点之间的走货路径，页面查询专用</p> 
     * @author foss-zhujunyong
     * @date Jan 23, 2013 3:24:05 PM
     * @param sourceCode
     * @param targetCode
     * @param productCode
     * @param time
     * @return
     * @see
     */
    List<FreightRouteLineDto> queryEnhanceFreightRouteBySourceTarget(String sourceCode, String targetCode, String productCode, Date time);

    /**
     * 
     * <p>根据当前走货路径找出可以打木架的外场列表</p> 
     * @author foss-zhujunyong
     * @date Dec 17, 2012 10:30:17 AM
     * @param virtualCode
     * @return key为外场编码，value为外场名称，按走货顺序排列
     * @see
     */
    Map<String, String> queryPackingOrgs(String virtualCode);
    
    /**
     * 
     * <p>根据走货路径查询途经的网点字符串，给综合查询用</p> 
     * @author foss-zhujunyong
     * @date Mar 1, 2013 2:13:17 PM
     * @param sourceCode
     * @param targetCode
     * @param virtualCode
     * @return
     * @see
     */
    String queryRouteStringBySourceTarget(String sourceCode, String targetCode, String productCode);    
    
    /**
     * 
     * <p>根据走货路径查询途经的网点字符串，给综合查询用</p> 
     * @author foss-zhujunyong
     * @date Mar 1, 2013 2:13:17 PM
     * @param sourceCode
     * @param targetCode
     * @param virtualCode
     * @return
     * @see
     */
    String queryRouteStringByVirtualCode(String sourceCode, String targetCode, String virtualCode);

    /**
     * 
     * <p>清空走货路径缓存</p> 
     * @author foss-zhujunyong
     * @date Mar 6, 2013 10:35:17 AM
     * @param key
     * @see
     */
    void invalidEntity(String key);
    

	void updateFreightRouteAging(LineItemEntity entity);

	List<FreightRouteLineDto> queryByCache(String sourceCode, String targetCode, String productCode);

	List<FreightRouteLineDto> query4Billing(String sourceCode, String targetCode, String productCode);    
}
