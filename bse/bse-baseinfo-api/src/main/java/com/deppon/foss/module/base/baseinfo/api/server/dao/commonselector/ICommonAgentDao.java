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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/ICommonAgentDao.java
 * 
 * FILE NAME        	: ICommonAgentDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AgentEntity;

public interface ICommonAgentDao {
   
	/**
	 * <p>根据条件（分页模糊）有选择的统计出符合条件的“航空公司代理人”记录数（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-lifanghong
	 * @date  2013-4-25 
	 * @param airlinesAgent 以“航空公司代理人”实体承载的条件参数实体
	 * @return 符合条件的“航空公司代理人”记录数
	 * @see
	 */		
	Long queryAirlinesAgentRecordCountBySelectiveCondition(
			AgentEntity agent);
	/**
     * <p>根据条件（分页模糊）有选择的检索出符合条件的“航空公司代理人”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-lifanghong
     * @date  2013-4-25  下午2:59:18
     * @param airlinesAgent 以“航空公司代理人”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 符合条件的“航空公司代理人”实体列表
     * @see
     */
	List<AgentEntity> queryAirlinesAgentListBySelectiveCondition(
			AgentEntity agent, int offset, int limit);

	

}
