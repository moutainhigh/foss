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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/IDepartureStandardDao.java
 * 
 * FILE NAME        	: IDepartureStandardDao.java
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

import com.deppon.foss.module.base.baseinfo.api.shared.domain.DepartureStandardEntity;


/**
 * 发车标准Dao
 * @author foss-zhujunyong
 * @date Oct 26, 2012 11:36:37 AM
 * @version 1.0
 */
public interface IDepartureStandardDao {

    /**
     * 
     * <p>添加发车标准</p> 
     * @author foss-zhujunyong
     * @date Oct 26, 2012 3:19:01 PM
     * @param departureStandard
     * @return
     * @see
     */
    DepartureStandardEntity addDepartureStandard(DepartureStandardEntity departureStandard);
    
    /**
     * 
     * <p>作废发车标准</p> 
     * @author foss-zhujunyong
     * @date Oct 26, 2012 3:19:13 PM
     * @param departureStandard
     * @return
     * @see
     */
    DepartureStandardEntity deleteDepartureStandard(DepartureStandardEntity departureStandard);

    /**
     * 
     * <p>根据线路作废发车标准</p> 
     * @author foss-zhujunyong
     * @date Nov 12, 2012 3:25:51 PM
     * @param departureStandard
     * @return
     * @see
     */
    int deleteDepartureStandardByLine(String lineVirtualCode, String modifyUser);
    
    /**
     * 
     * <p>更新发车标准</p> 
     * @author foss-zhujunyong
     * @date Oct 26, 2012 3:19:28 PM
     * @param departureStandard
     * @return
     * @see
     */
    DepartureStandardEntity updateDepartureStandard(DepartureStandardEntity departureStandard);

    /**
     * 
     * <p>查询发车标准详情</p> 
     * @author foss-zhujunyong
     * @date Oct 26, 2012 3:19:45 PM
     * @param id
     * @return
     * @see
     */
    DepartureStandardEntity queryDepartureStandardById(String id);

    /**
     * 
     * <p>查询特定线路下的发车标准列表</p> 
     * @author foss-zhujunyong
     * @date Oct 26, 2012 3:20:00 PM
     * @param lineVirtualCode
     * @return
     * @see
     */
    List<DepartureStandardEntity> queryDepartureStandardListByLineVirtualCode(String lineVirtualCode);
    
    /**
     * 
     * <p>查询特定线路下特定班次的发车标准</p> 
     * @author foss-zhujunyong
     * @date Nov 12, 2012 9:25:37 AM
     * @param lineVirtualCode
     * @param start
     * @return
     * @see
     */
    DepartureStandardEntity queryDepartureStandardByOrder(String lineVirtualCode, int start);
    
    /**
     * 
     * <p>下载班次表</p> 
     * @author foss-zhujunyong
     * @date Dec 29, 2012 10:14:35 AM
     * @param line
     * @return
     * @see
     */
    List<DepartureStandardEntity> queryDepartureStandardListForDownload(DepartureStandardEntity line);    
    
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
     * <p>取所有激活的发车标准进入缓存</p> 
     * @author foss-zhujunyong
     * @date Dec 17, 2012 7:44:09 PM
     * @return
     * @see
     */
    List<DepartureStandardEntity> queryDepartureStandardListForCache();

	/**
	 * 
	 * 分页下载 防止数据量太大
	 * @param entity
	 * @param i
	 * @param thousand
	 * @return
	 */
    List<DepartureStandardEntity>  queryDepartureStandardListForDownloadByPage(
			DepartureStandardEntity entity, int i, int thousand);
    
    List<DepartureStandardEntity> queryDepartureStandardByVirtueCode(String virtueCode);
}
