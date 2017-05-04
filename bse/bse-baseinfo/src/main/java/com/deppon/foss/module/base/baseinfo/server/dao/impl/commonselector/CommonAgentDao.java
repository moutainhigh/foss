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
 * PROJECT NAME	: bse-baseinfo
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/AgentDao.java
 * 
 * FILE NAME        	: AgentDao.java
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
package com.deppon.foss.module.base.baseinfo.server.dao.impl.commonselector;

import java.util.List;
import org.apache.ibatis.session.RowBounds;
import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonAgentDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AgentEntity;
import com.deppon.foss.util.define.FossConstants;
/**
 * 用来操作交互“航空公司代理人”的数据库对应数据访问DAO接口的实现类：SUC-61 
 * <p style="display:none">modifyairlinesAgent</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-15 下午2:45:03</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-15 下午2:45:03
 * @since
 * @version
 */
public class CommonAgentDao extends SqlSessionDaoSupport implements ICommonAgentDao {
    
    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".airlinesAgent";
    
    
    
    /**
     * <p>根据条件有选择的统计出符合条件的“航空公司代理人”记录数（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-lifanghong
     * @date 2013-4-25 上午10:37:09
     * @param airlinesAgent 以“航空公司代理人”实体承载的条件参数实体
     * @return 符合条件的“航空公司代理人”记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAirlinesAgentDao#queryAirlinesAgentRecordCountBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.AgentEntity)
     */
    @Override
    public Long queryAirlinesAgentRecordCountBySelectiveCondition(AgentEntity agent) {
        Long totalCount = 0l;
	//强制设置为只查询“启用”的记录
        agent.setActive(FossConstants.ACTIVE);
        Object obj = getSqlSession().selectOne(NAMESPACE + ".queryAirlinesAgentRecordCountBySelectiveCondition", agent);
        if (null != obj) {
	    totalCount = Long.valueOf(obj.toString());
	}
        return totalCount;
    }
    
    /**
     * <p>根据条件有选择的检索出符合条件的“航空公司代理人”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-lifanghong
     * @date 2013-4-25
     * @param airlinesAgent 以“航空公司代理人”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 符合条件的“航空公司代理人”实体列表
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAirlinesAgentDao#queryAirlinesAgentListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.AgentEntity, int, int)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<AgentEntity> queryAirlinesAgentListBySelectiveCondition(
            AgentEntity agent, int offset, int limit) {
	//强制设置为只查询“启用”的记录
    	agent.setActive(FossConstants.ACTIVE);
        return getSqlSession().selectList(NAMESPACE + ".queryAirlinesAgentListBySelectiveCondition",  agent, new RowBounds(offset, limit));
    }
}
