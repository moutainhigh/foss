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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/IInfoDeptScoresStdDao.java
 * 
 * FILE NAME        	: IInfoDeptScoresStdDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.InfoDeptScoresStdEntity;
/**
 * 用来操作交互“信息部基础标准”的数据库对应数据访问DAO接口：SUC-222
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-12-25 下午3:28:16</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-12-25 下午3:28:16
 * @since
 * @version
 */
public interface IInfoDeptScoresStdDao {

    /**
     * <p>根据条件有选择的统计出符合条件的“信息部基础标准”实体记录数（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-25 上午10:15:40
     * @param infoDeptScores 以“信息部基础标准”实体承载的条件参数实体
     * @return 符合条件的“信息部基础标准”实体记录条数
     * @see
     */
     List<InfoDeptScoresStdEntity> queryInfoDeptScoresStdListBySelective(InfoDeptScoresStdEntity infoDeptScoresStd);
    
}
