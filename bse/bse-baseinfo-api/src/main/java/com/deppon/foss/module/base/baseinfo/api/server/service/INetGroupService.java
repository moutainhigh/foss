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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/INetGroupService.java
 * 
 * FILE NAME        	: INetGroupService.java
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
import com.deppon.foss.module.base.baseinfo.api.shared.dto.NetGroupDto;


/**
 * 网点组服务接口
 * @author foss-zhujunyong
 * @date Nov 2, 2012 11:46:35 AM
 * @version 1.0
 */
public interface INetGroupService extends IService{

    /**
     * 
     * <p>添加网点组</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:32:15 AM
     * @param netGroup
     * @return
     * @see
     */
    NetGroupDto addNetGroup(NetGroupDto netGroup);
    
    /**
     * 
     * <p>作废网点组</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:32:30 AM
     * @param netGroup
     * @return
     * @see
     */
    int deleteNetGroup(NetGroupDto netGroup);

    /**
     * 
     * <p>更新网点组</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:32:46 AM
     * @param netGroup
     * @return
     * @see
     */
    NetGroupDto updateNetGroup(NetGroupDto netGroup);

    /**
     * 
     * <p>根据走货路径取所有的网点组</p> 
     * @author foss-zhujunyong
     * @date Nov 4, 2012 10:32:15 AM
     * @param freightRouteVirtualCode
     * @return
     * @see
     */
    List<NetGroupDto> queryNetGroupListByFreightRoute(String freightRouteVirtualCode);
    
    
    /**
     * 
     * <p>根据出发营业部和到达营业部找出符合的走货路径</p> 
     * @author foss-zhujunyong
     * @date Nov 2, 2012 11:52:05 AM
     * @param sourceSaleCode 出发营业部部门编码
     * @param targetSaleCode 到达营业部部门编码
     * @return 走货路径列表
     * @see
     */
    List<String> queryFreightRouteCode(String sourceSaleCode, String targetSaleCode);
    
    /**
     * 
     * <p>根据走货路径批量作废网点组</p> 
     * @author foss-zhujunyong
     * @date Nov 13, 2012 10:18:37 AM
     * @param freightRouteVirtualCode
     * @param modifyUser
     * @return
     * @see
     */
    int deleteNetGroupByFreightRoute(String freightRouteVirtualCode, String modifyUser);
    
    /**
     * 
     * <p>根据出发营业部编码和出发外场编码作废网点组</p> 
     * @author foss-zhujunyong
     * @date Jun 27, 2013 11:22:19 AM
     * @param salesCode
     * @param transferCode
     * @param modifyUser 
     * @see
     */
    void deleteSourceNetGroup(String salesCode, String transferCode, String modifyUser, String transType,String expNetworkGroup);
    
    /**
     * 
     * <p>根据到达营业部编码和到达外场编码作废网点组</p> 
     * @author foss-zhujunyong
     * @date Jun 27, 2013 11:22:19 AM
     * @param salesCode
     * @param transferCode
     * @param modifyUser
     * @see
     */
    void deleteTargetNetGroup(String salesCode, String transferCode, String modifyUser,String expNetworkGroup);
    
}
