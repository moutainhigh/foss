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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/IExpressLineDao.java
 * 
 * FILE NAME        	: IExpressLineDao.java
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

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressLineEntity;


/**
 * 线路Dao
 * @author foss-zhujunyong
 * @date Oct 25, 2012 11:28:49 AM
 * @version 1.0
 */
public interface IExpressLineDao {
    
    /**
     * 
     * <p>添加线路</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:32:15 AM
     * @param line
     * @return
     * @see
     */
    ExpressLineEntity addLine(ExpressLineEntity line);
    
    /**
     * 
     * <p>作废线路</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:32:30 AM
     * @param line
     * @return
     * @see
     */
    ExpressLineEntity deleteLine(ExpressLineEntity line);

    /**
     * 
     * <p>生效或失效线路</p> 
     * @author foss-zhujunyong
     * @date Jan 16, 2013 7:57:05 PM
     * @param line
     * @return
     * @see
     */
    int validLine(ExpressLineEntity line);
    
    /**
     * 
     * <p>更新线路</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:32:46 AM
     * @param line
     * @return
     * @see
     */
    ExpressLineEntity updateLine(ExpressLineEntity line);

    /**
     * 
     * <p>根据ID查询线路</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:33:00 AM
     * @param id
     * @return
     * @see
     */
    ExpressLineEntity queryLineById(String id);

    /**
     * 
     * <p>根据虚拟编码查询线路</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:33:00 AM
     * @param id
     * @return
     * @see
     */
    ExpressLineEntity queryLineByVirtualCode(String virtualCode);
    
    /**
     * 
     * <p>根据条件查询线路</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:34:06 AM
     * @param line
     * @param start
     * @param limit
     * @return
     * @see
     */
    List<ExpressLineEntity> queryLineListByCondition(ExpressLineEntity line, int start, int limit);

    /**
     * 
     * <p>根据条件计算线路数量</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:34:24 AM
     * @param line
     * @return
     * @see
     */
    long countLineListByCondition(ExpressLineEntity line);
    /**
     * 
     *<P>根据线路简码 精确查询线路</P>
     * @author :130566-zengJunfan
     * @date : 2013-10-18上午10:53:14
     * @param simpleCode
     * @return
     */
    List<ExpressLineEntity> queryLineEntityBySimpleCode(String simpleCode);

    /**
     * 
     * <p>下载线路表</p> 
     * @author foss-zhujunyong
     * @date Nov 6, 2012 10:52:56 AM
     * @param line
     * @return
     * @see
     */
    List<ExpressLineEntity> queryLineListForDownload(ExpressLineEntity line);
    
    /**
     * 
     * <p>按出发营业部查找出发线路和全量中转线路以及全量到达线路</p> 
     * @author foss-zhujunyong
     * @date Mar 21, 2013 3:01:19 PM
     * @param map
     * @return
     * @see
     */
    List<ExpressLineEntity> queryLineListForDownloadViaFilter(Map<String, Object> map);
    
    /**
     * 
     * <p>按出发部门和运输类型查询线路</p> 
     * @author foss-zhujunyong
     * @date Dec 7, 2012 10:21:10 AM
     * @param line
     * @return
     * @see
     */
    List<ExpressLineEntity> queryLineListBySourceAndType(ExpressLineEntity line);
    
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
     * <p>取所有激活的线路进入缓存</p> 
     * @author foss-zhujunyong
     * @date Dec 17, 2012 7:44:09 PM
     * @return
     * @see
     */
    List<ExpressLineEntity> queryLineListForCache();
    
    /**
     * 
     * <p>为导出方法取线路列表(包括取各种名称冗余属性)</p> 
     * @author foss-zhujunyong
     * @date Mar 7, 2013 11:44:05 AM
     * @param line
     * @return
     * @see
     */
    List<ExpressLineEntity> queryLineListForExport(ExpressLineEntity line, int start, int limit);

	/**
	 * 分页下载
	 * @param entity
	 * @param i
	 * @param thousand
	 * @return
	 */
    List<ExpressLineEntity> queryLineListForDownloadByPage(ExpressLineEntity entity, int i, int thousand);

}
