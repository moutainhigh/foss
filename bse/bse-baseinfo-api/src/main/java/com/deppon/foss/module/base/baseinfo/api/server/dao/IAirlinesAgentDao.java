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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/IAirlinesAgentDao.java
 * 
 * FILE NAME        	: IAirlinesAgentDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesAgentEntity;
/**
 * 用来操作交互“航空公司代理人”的数据库对应数据访问DAO接口：SUC-61
 * <p style="display:none">modifyairlinesAgent</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-12 上午8:49:36</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-12 上午8:49:36
 * @since
 * @version
 */
public interface IAirlinesAgentDao {

    /**
     * <p>新增一个“航空公司代理人”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午8:49:46
     * @param airlinesAgent “航空公司代理人”实体
     * @return 1：成功；0：失败
     * @see
     */
     int addAirlinesAgent(AirlinesAgentEntity airlinesAgent);

    /**
     * <p>新增一个“航空公司代理人”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午8:49:49
     * @param airlinesAgent “航空公司代理人”实体
     * @return 1：成功；0：失败
     * @see
     */
     int addAirlinesAgentBySelective(AirlinesAgentEntity airlinesAgent);

    /**
     * <p>修改一个“航空公司代理人”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午8:49:55
     * @param airlinesAgent “航空公司代理人”实体
     * @return 1：成功；0：失败
     * @see
     */
     int updateAirlinesAgentBySelective(AirlinesAgentEntity airlinesAgent);

    /**
     * <p>修改一个“航空公司代理人”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午8:49:58
     * @param airlinesAgent “航空公司代理人”实体
     * @return 1：成功；0：失败
     * @see
     */
     int updateAirlinesAgent(AirlinesAgentEntity airlinesAgent);
    
    /**
     * <p>根据条件有选择的检索出符合条件的“航空公司代理人”唯一记录（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-12-3 上午10:36:42
     * @param airlinesAgent 以“航空公司代理人”实体承载的条件参数实体
     * @return 符合条件的“航空公司代理人”实体
     * @see
     */
     AirlinesAgentEntity queryAirlinesAgentBySelective(AirlinesAgentEntity airlinesAgent);
    
    /**
     * <p>根据条件有选择的检索出符合条件的“航空公司代理人”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-15 下午2:59:18
     * @param airlinesAgent 以“航空公司代理人”实体承载的条件参数实体
     * @return 符合条件的“航空公司代理人”实体列表
     * @see
     */
     List<AirlinesAgentEntity> queryAirlinesAgentListBySelective(AirlinesAgentEntity airlinesAgent);

    /**
     * <p>根据条件（分页模糊）有选择的统计出符合条件的“航空公司代理人”记录数（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-12-3 上午10:37:09
     * @param airlinesAgent 以“航空公司代理人”实体承载的条件参数实体
     * @return 符合条件的“航空公司代理人”记录数
     * @see
     */
     Long queryAirlinesAgentRecordCountBySelectiveCondition(AirlinesAgentEntity airlinesAgent);
    
    /**
     * <p>根据条件（分页模糊）有选择的检索出符合条件的“航空公司代理人”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-15 下午2:59:18
     * @param airlinesAgent 以“航空公司代理人”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 符合条件的“航空公司代理人”实体列表
     * @see
     */
     List<AirlinesAgentEntity> queryAirlinesAgentListBySelectiveCondition(AirlinesAgentEntity airlinesAgent, int offset, int limit);
     
     /**
      * 根据代理人编码查询
      * @param airlinesAgent
      * @return 符合条件的“航空公司代理人”实体列表
      * @author 313353
      * @date 2016/05/28
      */
     List<AirlinesAgentEntity> queryAirlinesAgentListByAgentCode(AirlinesAgentEntity airlinesAgent);
}
