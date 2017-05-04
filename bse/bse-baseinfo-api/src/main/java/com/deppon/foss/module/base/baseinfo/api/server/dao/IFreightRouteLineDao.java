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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/IFreightRouteLineDao.java
 * 
 * FILE NAME        	: IFreightRouteLineDao.java
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
import java.util.Map;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteLineEntity;


/**
 * 走货路径线路Dao
 * @author foss-zhujunyong
 * @date Oct 29, 2012 1:49:18 PM
 * @version 1.0
 */
public interface IFreightRouteLineDao {

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
     * <p>下载走货路径线路</p> 
     * @author foss-zhujunyong
     * @date Nov 6, 2012 10:14:43 AM
     * @param freightRouteLine
     * @return
     * @see
     */
    List<FreightRouteLineEntity> queryFreightRouteLineForDownload(Map<String, Object> map);
    
    /**
     * 
     * <p>查询特定走货路径下的所有走货路径线路</p> 
     * @author foss-zhujunyong
     * @date Nov 6, 2012 6:20:14 PM
     * @return
     * @see
     */
    List<FreightRouteLineEntity> queryFreightRouteLineListByFreightRoute(FreightRouteLineEntity freightRouteLine);
    
    /**
     * 
     * <p>根据走货路径批量作废走货路径线路</p> 
     * @author foss-zhujunyong
     * @date Nov 13, 2012 10:31:16 AM
     * @param freightRouteVirtualCode
     * @param modifyUser
     * @return
     * @see
     */
    int deleteFreightRouteLineByFreightRoute(String freightRouteVirtualCode, String modifyUser);
 
    /**
     * 
     * <p>查询表中有多少引用了指定线路的走货路径线路</p> 
     * @author foss-zhujunyong
     * @date Dec 24, 2012 9:58:48 AM
     * @param lineVirtualCode
     * @return
     * @see
     */
    List<FreightRouteLineEntity> queryFreightRouteLineListByLine(String lineVirtualCode);

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
     * <p>取所有激活的走货路径线路进入缓存</p> 
     * @author foss-zhujunyong
     * @date Dec 17, 2012 7:44:09 PM
     * @return
     * @see
     */
    List<FreightRouteLineEntity> queryFreightRouteLineListForCache();

	/**
	 * 分页下载防止数据量太大
	 * @param map
	 * @param i
	 * @param thousand
	 * @return
	 */
    List<FreightRouteLineEntity> queryFreightRouteLineForDownloadByPage(Map<String, Object> map,
			int i, int thousand);
    
    FreightRouteEntity updateFreightRouteAging(FreightRouteLineEntity entity);

	FreightRouteLineEntity deleteFreightRouteLines(
			FreightRouteLineEntity freightRouteLine);

	FreightRouteLineEntity updateFreightRouteLines(
			FreightRouteLineEntity freightRouteLine);
}
