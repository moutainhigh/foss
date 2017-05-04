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

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.NetGroupEntity;


/**
 * 网点组Dao
 * @author foss-zhujunyong
 * @date Oct 25, 2012 11:28:49 AM
 * @version 1.0
 */
public interface INetGroupDao {
    
    /**
     * 
     * <p>添加网点组</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:32:15 AM
     * @param netGroup
     * @return
     * @see
     */
    NetGroupEntity addNetGroup(NetGroupEntity netGroup);
    
    /**
     * 
     * <p>作废网点组</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:32:30 AM
     * @param netGroup
     * @return
     * @see
     */
    NetGroupEntity deleteNetGroup(NetGroupEntity netGroup);

    /**
     * 
     * <p>根据网点组名称作废一批网点组实体</p> 
     * @author foss-zhujunyong
     * @date Nov 4, 2012 1:21:16 PM
     * @param netGroup
     * @return
     * @see
     */
    int deleteNetGroupByName(NetGroupEntity netGroup);
    
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
    int deleteNetGroupByFreightRoute(String freightRouteVirtualCode, String modifyUser);
    
    /**
     * 
     * <p>更新网点组</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:32:46 AM
     * @param netGroup
     * @return
     * @see
     */
    NetGroupEntity updateNetGroup(NetGroupEntity netGroup);

    /**
     * 
     * <p>根据ID查询网点组</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:33:00 AM
     * @param id
     * @return
     * @see
     */
    NetGroupEntity queryNetGroupById(String id);

    /**
     * 
     * <p>根据走货路径查询网点组</p> 
     * @author foss-zhujunyong
     * @date Nov 4, 2012 1:55:09 PM
     * @param netGroup
     * @return
     * @see
     */
    List<NetGroupEntity> queryNetGroupByFreightRoute(String freightRouteVirtualCode);
    
    /**
     * 
     * <p>根据走货路径，出发营业部和到达营业部查询网点组记录</p> 
     * @author foss-zhujunyong
     * @date Nov 4, 2012 12:24:12 PM
     * @see
     */
    NetGroupEntity queryNetGroupBySourceTargetFreightRoute(NetGroupEntity netGroup);

    /**
     * 
     * <p>根据出发营业部和到达营业部查询网点组记录</p> 
     * @author foss-zhujunyong
     * @date Nov 4, 2012 12:24:12 PM
     * @see
     */
    List<NetGroupEntity> queryNetGroupBySourceTarget(NetGroupEntity netGroup);

    /**
     * 
     * <p>查询符合名称的网点组实体</p> 
     * @author foss-zhujunyong
     * @date Nov 4, 2012 3:25:14 PM
     * @param netGroup
     * @return
     * @see
     */
    List<NetGroupEntity> queryNetGroupByName(String netGroup);
    

    /**
     * 
     * <p>下载网点组表</p> 
     * @author foss-zhujunyong
     * @date Nov 6, 2012 11:01:13 AM
     * @param netGroup
     * @return
     * @see
     */
    List<NetGroupEntity> queryNetGroupListForDownload(NetGroupEntity netGroup);

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
     * <p>取所有激活的网点组进入缓存</p> 
     * @author foss-zhujunyong
     * @date Dec 17, 2012 7:44:09 PM
     * @return
     * @see
     */
    List<NetGroupEntity> queryNetGroupListForCache();
    
}
