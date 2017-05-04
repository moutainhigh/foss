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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/IMinistryOfInformationScoresDao.java
 * 
 * FILE NAME        	: IMinistryOfInformationScoresDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.InfoDeptScoresEntity;
/**
 * 用来操作交互“信息部标准得分”的数据库对应数据访问DAO接口：SUC-222
 * <p style="display:none">modifyinfoDeptScores</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-12 上午11:50:54</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-12 上午11:50:54
 * @since
 * @version
 */
public interface IMinistryOfInformationScoresDao {

    /**
     * <p>根据“信息部标准得分”记录唯一标识作废一条“信息部标准得分”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午11:51:26
     * @param id 记录唯一标识
     * @return 1：成功；0：失败
     * @see
     */
     int deleteMinistryOfInformationScores(String id);

    /**
     * <p>新增一个“信息部标准得分”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午11:51:34
     * @param infoDeptScores “信息部标准得分”实体
     * @return 1：成功；0：失败
     * @see
     */
     int addMinistryOfInformationScores(InfoDeptScoresEntity infoDeptScores);

    /**
     * <p>新增一个“信息部标准得分”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午11:51:36
     * @param infoDeptScores “信息部标准得分”实体
     * @return 1：成功；0：失败
     * @see
     */
     int addMinistryOfInformationScoresBySelective(InfoDeptScoresEntity infoDeptScores);

    /**
     * <p>根据“信息部标准得分”记录唯一标识查询出一条“信息部标准得分”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午11:51:40
     * @param id 记录唯一标识
     * @return “信息部标准得分”实体
     * @see
     */
     InfoDeptScoresEntity queryMinistryOfInformationScores(String id);

    /**
     * <p>修改一个“信息部标准得分”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午11:51:42
     * @param infoDeptScores “信息部标准得分”实体
     * @return 1：成功；0：失败
     * @see
     */
     int updateMinistryOfInformationScoresBySelective(InfoDeptScoresEntity infoDeptScores);

    /**
     * <p>修改一个“信息部标准得分”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午11:51:44
     * @param infoDeptScores “信息部标准得分”实体
     * @return 1：成功；0：失败
     * @see
     */
     int updateMinistryOfInformationScores(InfoDeptScoresEntity infoDeptScores);
}
