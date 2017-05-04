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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/IAirlinesAgentService.java
 * 
 * FILE NAME        	: IAirlinesAgentService.java
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
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesAgentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesAgentEntityTransEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.AirlinesAgentException;
/**
 * 用来操作交互“航空公司代理人”的数据库对应数据访问Dao调用的Service接口：SUC-61
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-15 下午4:39:30</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-15 下午4:39:30
 * @since
 * @version
 */
public interface IAirlinesAgentService extends IService {
    
    /**
     * <p>新增一个“航空公司代理人”实体入库（忽略实体中是否存在空值）</p>
     * @author 100847-foss-GaoPeng
     * @date 2012-10-15 下午4:40:48
     * @param airlinesAgent “航空公司代理人”实体
     * @param createUser 创建人
     * @param ignoreNull true：忽略空值，false：包含空值
     * @return 1：成功；0：失败
     * @see
     */
     int addAirlinesAgent(AirlinesAgentEntity airlinesAgent, String createUser, boolean ignoreNull) throws AirlinesAgentException;

    /**
     * <p>根据“航空公司代理人”记录唯一标识作废（逻辑删除）一条“航空公司代理人”记录</p>
     * @author 100847-foss-GaoPeng
     * @date 2012-10-15 下午4:40:31
     * @param ids 记录唯一标识集合
     * @param modifyUser 修改人
     * @return 1：成功；0：失败
     * @see
     */
     int deleteAirlinesAgent(List<String> ids, String modifyUser) throws AirlinesAgentException;
    
    /**
     * <p>修改一个“航空公司代理人”实体入库（忽略实体中是否存在空值）</p>
     * @author 100847-foss-GaoPeng
     * @date 2012-10-15 下午4:41:34
     * @param airlinesAgent “航空公司代理人”实体
     * @param modifyUser 修改人
     * @param ignoreNull true：忽略空值，false：包含空值
     * @return 1：成功；0：失败
     * @see
     */
     int updateAirlinesAgent(AirlinesAgentEntity airlinesAgent, String modifyUser,  boolean ignoreNull) throws AirlinesAgentException;

    /**
     * <p>根据“航空公司代理人”记录唯一标识查询出一条“航空公司代理人”记录</p>
     * @author 100847-foss-GaoPeng
     * @date 2012-10-15 下午4:41:28
     * @param id 记录唯一标识
     * @return “航空公司代理人”实体
     * @see
     */
     AirlinesAgentEntity queryAirlinesAgent(String id) throws AirlinesAgentException;
    
    /**
     * <p>根据条件有选择的检索出符合条件的“航空公司代理人”唯一记录（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-12-3 上午10:36:42
     * @param airlinesAgent 以“航空公司代理人”实体承载的条件参数实体
     * @return 符合条件的“航空公司代理人”实体
     * @see
     */
     AirlinesAgentEntity queryAirlinesAgentBySelective(AirlinesAgentEntity airlinesAgent) throws AirlinesAgentException;
    
    /**
     * <p>根据条件有选择的检索出符合条件的“航空公司代理人”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-15 下午4:41:30
     * @param airlinesAgent 以“航空公司代理人”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 分页的Action和Service通讯封装对象
     * @see
     */
     PaginationDto queryAirlinesAgentListBySelectiveCondition(AirlinesAgentEntity airlinesAgent, int offset, int limit) throws AirlinesAgentException;

     /**
      * <p>根据条件有选择的检索出符合条件的“航空公司代理人”实体列表（条件做自动判断，只选择实体中非空值）</p>.
      * 
      * @param airlinesAgent 以“航空公司代理人”实体承载的条件参数实体
      * @return 分页的Action和Service通讯封装对象
      * @throws AirlinesAgentException
      * @author 313353-foss-Qiupeng
      * @date 2016-05-28 上午8:39:04
      */
	List<AirlinesAgentEntityTransEntity> queryAirlinesAgentTransListBySelectiveCondition(
			AirlinesAgentEntity airlinesAgent)
			throws AirlinesAgentException;
}
