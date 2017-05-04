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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/IInfoDeptScoresDao.java
 * 
 * FILE NAME        	: IInfoDeptScoresDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.InfoDeptScoresEntity;
/**
 * 用来操作交互“信息部得分”的数据库对应数据访问DAO接口：SUC-222
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-12-17 上午9:53:27</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-12-17 上午9:53:27
 * @since
 * @version
 */
public interface IInfoDeptScoresDao {

    /**
     * <p>新增一个“信息部得分”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午2:30:53
     * @param infoDeptScores “信息部得分”实体
     * @return 影响记录数
     * @see
     */
     int addInfoDeptScores(InfoDeptScoresEntity infoDeptScores);

    /**
     * <p>新增一个“信息部得分”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午2:30:51
     * @param infoDeptScores “信息部得分”实体
     * @return 影响记录数
     * @see
     */
     int addInfoDeptScoresBySelective(InfoDeptScoresEntity infoDeptScores);
    
    /**
     * <p>根据“信息部得分”记录唯一标识删除（物理删除）一条“信息部得分”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午2:30:42
     * @param id 记录唯一标识
     * @return 影响记录数
     * @see
     */
     int deleteInfoDeptScores(String id);

    /**
     * <p>修改一个“信息部得分”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午2:30:47
     * @param infoDeptScores “信息部得分”实体
     * @return 影响记录数
     * @see
     */
     int updateInfoDeptScoresBySelective(InfoDeptScoresEntity infoDeptScores);

    /**
     * <p>修改一个“信息部得分”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午2:30:45
     * @param infoDeptScores “信息部得分”实体
     * @return 影响记录数
     * @see
     */
     int updateInfoDeptScores(InfoDeptScoresEntity infoDeptScores);
    
    /**
     * <p>根据条件有选择的查询“信息部得分”唯一激活可用状态实体（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-30 下午8:32:32
     * @param infoDeptScores 以“信息部得分”实体承载的条件参数实体
     * @return 符合条件的“信息部得分”实体
     * @see
     */
     InfoDeptScoresEntity queryInfoDeptScoresBySelective(InfoDeptScoresEntity infoDeptScores);
    
    /**
     * <p>根据条件有选择的统计出符合条件的“信息部得分”实体记录数（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-25 上午10:15:40
     * @param infoDeptScores 以“信息部得分”实体承载的条件参数实体
     * @return 符合条件的“信息部得分”实体记录条数
     * @see
     */
     List<InfoDeptScoresEntity> queryInfoDeptScoresListBySelective(InfoDeptScoresEntity infoDeptScores);
}
