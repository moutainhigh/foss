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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/INetGroupDao.java
 * 
 * FILE NAME        	: INetGroupDao.java
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
import java.util.Map;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.NetGroupMixEntity;


/**
 * 网点组Dao
 * @author foss-zhujunyong
 * @date Oct 25, 2012 11:28:49 AM
 * @version 1.0
 */
public interface INetGroupMixDao {
    
    /**
     * 
     * <p>添加网点组</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:32:15 AM
     * @param netGroup
     * @return
     * @see
     */
    NetGroupMixEntity addNetGroupMix(NetGroupMixEntity netGroup);
    
    /**
     * 
     * <p>作废网点组</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:32:30 AM
     * @param netGroup
     * @return
     * @see
     */
    NetGroupMixEntity deleteNetGroupMix(NetGroupMixEntity netGroup);

    /**
     * 
     * <p>根据网点组名称作废一批网点组实体</p> 
     * @author foss-zhujunyong
     * @date Nov 4, 2012 1:21:16 PM
     * @param netGroup
     * @return
     * @see
     */
    int deleteNetGroupMixByCode(NetGroupMixEntity netGroup);
    
    /**
     * 
     * <p>根据走货路径作废网点组</p> 
     * @author foss-zhujunyong
     * @date Nov 13, 2012 10:16:08 AM
     * @param freightRouteVirtualCode
     * @param modifyUser
     * @return
     * @see
     */
    int deleteNetGroupMixByFreightRoute(String freightRouteVirtualCode, String modifyUser);
    
    /**
     * 
     * <p>更新网点组</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:32:46 AM
     * @param netGroup
     * @return
     * @see
     */
    NetGroupMixEntity updateNetGroupMix(NetGroupMixEntity netGroup);

    /**
     * 
     * <p>根据ID查询网点组</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:33:00 AM
     * @param id
     * @return
     * @see
     */
    NetGroupMixEntity queryNetGroupMixById(String id);

    /**
     * 
     * <p>根据走货路径查询网点组</p> 
     * @author foss-zhujunyong
     * @date Nov 4, 2012 1:55:09 PM
     * @param netGroup
     * @return
     * @see
     */
    List<NetGroupMixEntity> queryNetGroupMixByFreightRoute(String freightRouteVirtualCode);
    

    /**
     * 
     * <p>根据出发营业部和到达营业部查询网点组记录</p> 
     * @author foss-zhujunyong
     * @date Nov 4, 2012 12:24:12 PM
     * @see
     */
    List<NetGroupMixEntity> queryNetGroupMixBySourceTarget(String sourceCode, String targetCode) ;

    /**
     * 
     * <p>查询符合名称的网点组实体</p> 
     * @author foss-zhujunyong
     * @date Nov 4, 2012 3:25:14 PM
     * @param netGroup
     * @return
     * @see
     */
    List<NetGroupMixEntity> queryNetGroupMixByCode(String netGroup);
    

    /**
     * 
     * <p>下载网点组表</p> 
     * @author foss-zhujunyong
     * @date Nov 6, 2012 11:01:13 AM
     * @param map
     * @return
     * @see
     */
    List<NetGroupMixEntity> queryNetGroupMixListForDownload(Map<String, Object> map);

    /**
     * 
     * <p>取所有激活的网点组进入缓存</p> 
     * @author foss-zhujunyong
     * @date Dec 17, 2012 7:44:09 PM
     * @return
     * @see
     */
    List<NetGroupMixEntity> queryNetGroupListForCache();

    /**
     * 
     * <p>分页下载网点组表</p> 
     * @author foss-zhujunyong
     * @date Nov 6, 2012 11:01:13 AM
     * @param map
     * @return
     * @see
     */
    List<NetGroupMixEntity> queryNetGroupMixListForDownload(Map<String, Object> c, int start, int limited);
    
    /**
     * 
     * <p>按虚拟编码作废网点组</p> 
     * @author foss-zhujunyong
     * @date Jun 27, 2013 10:56:49 AM
     * @param virtualCode
     * @param modifyUser
     * @return
     * @see
     */
    int deleteNetGroupMixByVirtualCode(String virtualCode, String modifyUser);
    
    /**
     * 
     * <p>根据出发营业部编码和出发外场编码找出符合的网点组编码列表</p> 
     * @author foss-zhujunyong
     * @date Jun 27, 2013 11:15:06 AM
     * @param salesCode
     * @param transferCode
     * @param expNetworkGroup :是够快递网点组
     * @return
     * @see
     */
    List<String> queryVirtualCodeListBySourceSalesCode(String salesCode, String transferCode, String expNetworkGroup);
    
    /**
     * 
     * <p>根据到达营业部编码和到达外场编码找出符合的网点组编码列表</p> 
     * @author foss-zhujunyong
     * @date Jun 27, 2013 11:15:06 AM
     * @param salesCode
     * @param transferCode
     * @return
     * @see
     */
    List<String> queryVirtualCodeListByTargetSalesCode(String salesCode, String transferCode,String expNetworkGroup);
    
    /**
     * 
     * <p>根据网点组虚拟编码和产品数组筛选对应的网点组编码</p> 
     * @author foss-张继恒
     * @date 2.19, 2013 11:15:06 AM
     * @param salesCode
     * @param transferCode
     * @return
     * @see
     */
    
    public List<String> queryVirtualCodeListByProductCodeIn(List<String> virtualCodeList,String[] productList);
    /**
     * 
     * <p>根据网点组虚拟编码和产品数组筛选对应的网点组编码</p> 
     * @author foss-张继恒
     * @date 2.19, 2013 11:15:06 AM
     * @param salesCode
     * @param transferCode
     * @return
     * @see
     */
    
    public List<String> queryVirtualCodeListByProductCodeNotIn(List<String> virtualCodeList,String[] productList); 
    
    /**
     * 
     * <p>根据virtualcode查询相应网点组实体</p> 
     * @author foss-qrs
     * @param virtualCode
     * @return
     * @see
     */
    NetGroupMixEntity queryNetGroupMixByVirtualCode(String virtualCode);
    
    /**
     * 
     * <p>根据3字段查询相应网点组实体</p> 
     * @author foss-qrs
     * @param netGroup
     * @return
     * @see
     */
    List<NetGroupMixEntity> queryNetGroupMix(NetGroupMixEntity netGroup);
}
