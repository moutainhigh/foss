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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/IInfoDeptScoresService.java
 * 
 * FILE NAME        	: IInfoDeptScoresService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.InfoDeptScoresEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.InfoDeptScoresException;
/**
 * 用来操作交互“信息部得分”的数据库对应数据访问Service接口：SUC-222
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-12-17 上午10:45:18</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-12-17 上午10:45:18
 * @since
 * @version
 */
public interface IInfoDeptScoresService extends IService {

    /**
     * <p>新增一个“信息部得分”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 上午11:02:43
     * @param infoDeptScores “信息部得分”实体
     * @param createUser 创建人
     * @param ignoreNull true：忽略空值，false：包含空值
     * @return 1：成功；0：失败
     * @see
     */
     int addInfoDeptScores(InfoDeptScoresEntity infoDeptScores, String createUser, boolean ignoreNull) throws InfoDeptScoresException;
    
    /**
     * <p>修改一个“信息部得分”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 上午11:02:48
     * @param infoDeptScores “信息部得分”实体
     * @param modifyUser 修改人
     * @param ignoreNull true：忽略空值，false：包含空值
     * @return 1：成功；0：失败
     * @see
     */
     int updateInfoDeptScores(InfoDeptScoresEntity infoDeptScores, String modifyUser,  boolean ignoreNull) throws InfoDeptScoresException;
    
    /**
     * <p>根据条件有选择的检索出符合条件的“信息部”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 上午11:02:46
     * @param ownTruck 以“信息部”实体承载的条件参数实体
     * @return 分页的Action和Service通讯封装对象 
     * @see
     */
     List<InfoDeptScoresEntity> queryInfoDeptScoresListBySelectiveCondition(InfoDeptScoresEntity infoDeptScores) throws InfoDeptScoresException;
}
