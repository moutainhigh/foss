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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/IMinistryOfInformationDao.java
 * 
 * FILE NAME        	: IMinistryOfInformationDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.InfoDeptEntity;
/**
 * 用来操作交互“信息部”的数据库对应数据访问DAO接口：SUC-222
 * <p style="display:none">modifyinfoDept</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-12 上午11:52:10</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-12 上午11:52:10
 * @since
 * @version
 */
public interface IMinistryOfInformationDao {
    		  
    /**
     * <p>根据“信息部”记录唯一标识作废一条“信息部”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午11:52:13
     * @param id 记录唯一标识
     * @return 1：成功；0：失败
     * @see
     */
     int deleteMinistryOfInformation(String id);

    /**
     * <p>新增一个“信息部”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午11:52:15
     * @param infoDept “信息部”实体
     * @return 1：成功；0：失败
     * @see
     */
     int addMinistryOfInformation(InfoDeptEntity infoDept);

    /**
     * <p>新增一个“信息部”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午11:52:18
     * @param infoDept “信息部”实体
     * @return 1：成功；0：失败
     * @see
     */
     int addMinistryOfInformationBySelective(InfoDeptEntity infoDept);

    /**
     * <p>根据“信息部”记录唯一标识查询出一条“信息部”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午11:52:20
     * @param id 记录唯一标识
     * @return “信息部”实体
     * @see
     */
     InfoDeptEntity queryMinistryOfInformation(String id);

    /**
     * <p>修改一个“信息部”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午11:52:22
     * @param infoDept “信息部”实体
     * @return 1：成功；0：失败
     * @see
     */
     int updateMinistryOfInformationBySelective(InfoDeptEntity infoDept);

    /**
     * <p>修改一个“信息部”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午11:52:24
     * @param infoDept “信息部”实体
     * @return 1：成功；0：失败
     * @see
     */
     int updateMinistryOfInformation(InfoDeptEntity infoDept);
}
