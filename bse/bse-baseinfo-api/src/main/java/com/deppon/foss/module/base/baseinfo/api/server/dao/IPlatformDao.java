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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/IPlatformDao.java
 * 
 * FILE NAME        	: IPlatformDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/**
 * 
 */
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.PlatformEntity;

/**
 * @author zhujunyong
 * Create on Oct 10, 2012
 * 月台
 */
public interface IPlatformDao {

    /**
     * 
     * <p>增加</p> 
     * @author foss-zhujunyong
     * @date Mar 7, 2013 1:57:47 PM
     * @param platform
     * @return
     * @see
     */
    PlatformEntity addPlatform(PlatformEntity platform);

    /**
     * 
     * <p>
     * 删除
     * </p>
     * 
     * @author foss-zhujunyong
     * @date Mar 7, 2013 1:57:30 PM
     * @param platform
     * @return
     * @see
     */
    PlatformEntity deletePlatform(PlatformEntity platform);
	
    /**
     * 
     * <p>
     * 更新
     * </p>
     * 
     * @author foss-zhujunyong
     * @date Mar 7, 2013 1:57:27 PM
     * @param platform
     * @return
     * @see
     */
    PlatformEntity updatePlatform(PlatformEntity platform);
	
    /**
     * 
     * <p>
     * 取单个实体
     * </p>
     * 
     * @author foss-zhujunyong
     * @date Mar 7, 2013 1:57:24 PM
     * @param virtualCode
     * @return
     * @see
     */
    PlatformEntity queryPlatformByVirtualCode(String virtualCode);

    /**
     * 
     * <p>
     * 按条件查询
     * </p>
     * 
     * @author foss-zhujunyong
     * @date Mar 7, 2013 1:57:21 PM
     * @param platform
     * @param start
     * @param limit
     * @return
     * @see
     */
    List<PlatformEntity> queryPlatformListByCondition(PlatformEntity platform, int start, int limit);

    /**
     * 
     * <p>
     * 按条件查询的总数
     * </p>
     * 
     * @author foss-zhujunyong
     * @date Mar 7, 2013 1:57:19 PM
     * @param platform
     * @return
     * @see
     */
    long countPlatformListByCondition(PlatformEntity platform);

    /**
     * 
     * <p>
     * 查询某一外场下的在起始编号和截止编号之间的月台列表
     * </p>
     * 
     * @author foss-zhujunyong
     * @date Mar 7, 2013 1:57:15 PM
     * @param organizationCode
     * @param startCode
     * @param endCode
     * @return
     * @see
     */
    List<PlatformEntity> queryPlatformListByOrgCodeAndPlatformCodeLimit(String organizationCode, String startCode, String endCode);

    /**
     * 
     * <p>
     * 根据月台编码查询月台实体
     * </p>
     * 
     * @author foss-zhujunyong
     * @date Mar 7, 2013 1:57:12 PM
     * @param organizationCode
     * @param platformCode
     * @return
     * @see
     */
    PlatformEntity queryPlatformByCode(String organizationCode, String platformCode);

    /**
     * 
     * <p>
     * 批量作废月台
     * </p>
     * 
     * @author foss-zhujunyong
     * @date Mar 7, 2013 1:57:09 PM
     * @param virtualCodes
     * @param modifyUser
     * @return
     * @see
     */
    int deletePlatforms(List<String> virtualCodes, String modifyUser);
	
    /**
     * 
     * <p>
     * 取最后修改时间
     * </p>
     * 
     * @author foss-zhujunyong
     * @date Mar 7, 2013 1:57:06 PM
     * @return
     * @see
     */
    Date queryLastModifyTime();
	
    /**
     * 
     * <p>
     * 装置所有有效的月台到cache中
     * </p>
     * 
     * @author foss-zhujunyong
     * @date Mar 7, 2013 1:56:59 PM
     * @return
     * @see
     */
    List<PlatformEntity> queryPlatformListForCache();
	
    /**
     * 
     * <p>
     * 查询所有指定日期后更新的月台
     * </p>
     * 
     * @author foss-zhujunyong
     * @date Mar 7, 2013 1:56:46 PM
     * @param date
     * @return
     * @see
     */
    List<PlatformEntity> queryPlatformListViaDateForCache(Date date);

    /**
     * 
     * <p>
     * 为导出方法取月台列表(包括取各种名称冗余属性)
     * </p>
     * 
     * @author foss-zhujunyong
     * @date Mar 7, 2013 1:56:20 PM
     * @param platform
     * @param start
     * @param limit
     * @return
     * @see
     */
    List<PlatformEntity> queryPlatformListForExport(PlatformEntity platform, int start, int limit);
    /**
     * 
     * <p>
     * 为导出方法取月台列表(包括取各种名称冗余属性)
     * </p>
     * 
     * @author foss-zhujunyong
     * @date Mar 7, 2013 1:56:20 PM
     * @param platform
     * @param start
     * @param limit
     * @return
     * @see
     */
	List<PlatformEntity> queryPlatformListForExportByVehicleType2(
			PlatformEntity platform, int start, int limit);

}
