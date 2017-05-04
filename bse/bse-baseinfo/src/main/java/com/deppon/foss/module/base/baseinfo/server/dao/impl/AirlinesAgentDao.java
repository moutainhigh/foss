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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/AirlinesAgentDao.java
 * 
 * FILE NAME        	: AirlinesAgentDao.java
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
package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IAirlinesAgentDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesAgentEntity;
import com.deppon.foss.util.UUIDUtils;
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
public class AirlinesAgentDao extends SqlSessionDaoSupport implements IAirlinesAgentDao {
    
    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".airlinesAgent";
    
    /** 
     * <p>新增一个“航空公司代理人”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-15 下午2:45:03
     * @param airlinesAgent “航空公司代理人”实体
     * @return 1：成功；0：失败
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAirlinesAgentDao#addAirlinesAgent(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesAgentEntity)
     */
    @Override
    public int addAirlinesAgent(AirlinesAgentEntity airlinesAgent) {
	airlinesAgent.setId(UUIDUtils.getUUID());
	airlinesAgent.setCreateDate(new Date());
	airlinesAgent.setActive(FossConstants.ACTIVE);
	airlinesAgent.setModifyDate(airlinesAgent.getCreateDate());
	airlinesAgent.setModifyUser(airlinesAgent.getCreateUser());
	return getSqlSession().insert(NAMESPACE + ".addAirlinesAgent", airlinesAgent);
    }

    /** 
     * <p>新增一个“航空公司代理人”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-15 下午2:45:03
     * @param airlinesAgent “航空公司代理人”实体
     * @return 1：成功；0：失败
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAirlinesAgentDao#addAirlinesAgentBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesAgentEntity)
     */
    @Override
    public int addAirlinesAgentBySelective(AirlinesAgentEntity airlinesAgent) {
	airlinesAgent.setId(UUIDUtils.getUUID());
	airlinesAgent.setCreateDate(new Date());
	airlinesAgent.setActive(FossConstants.ACTIVE);
	airlinesAgent.setModifyDate(airlinesAgent.getCreateDate());
	airlinesAgent.setModifyUser(airlinesAgent.getCreateUser());
	return getSqlSession().insert(NAMESPACE + ".addAirlinesAgentBySelective", airlinesAgent);
    }
    
    /** 
     * <p>修改一个“航空公司代理人”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-15 下午2:45:03
     * @param airlinesAgent “航空公司代理人”实体
     * @return 1：成功；0：失败
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAirlinesAgentDao#updateAirlinesAgentBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesAgentEntity)
     */
    @Override
    public int updateAirlinesAgentBySelective(AirlinesAgentEntity airlinesAgent) {
	airlinesAgent.setModifyDate(new Date());
	return getSqlSession().update(NAMESPACE + ".updateAirlinesAgentBySelective", airlinesAgent);
    }

    /** 
     * <p>修改一个“航空公司代理人”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-15 下午2:45:03
     * @param airlinesAgent “航空公司代理人”实体
     * @return 1：成功；0：失败
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAirlinesAgentDao#updateAirlinesAgent(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesAgentEntity)
     */
    @Override
    public int updateAirlinesAgent(AirlinesAgentEntity airlinesAgent) {
	airlinesAgent.setModifyDate(new Date());
	return getSqlSession().update(NAMESPACE + ".updateAirlinesAgent", airlinesAgent);
    }
    
    /**
     * <p>根据条件有选择的检索出符合条件的“航空公司代理人”唯一记录（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-12-3 上午10:36:42
     * @param airlinesAgent 以“航空公司代理人”实体承载的条件参数实体
     * @return 符合条件的“航空公司代理人”实体
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAirlinesAgentDao#queryAirlinesAgentBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesAgentEntity)
     */
    @Override
    @SuppressWarnings("unchecked")
    public AirlinesAgentEntity queryAirlinesAgentBySelective(AirlinesAgentEntity airlinesAgent) {
	if(StringUtils.isBlank(airlinesAgent.getId())){
	    //强制设置为只查询“启用”的记录
	    airlinesAgent.setActive(FossConstants.ACTIVE);
	}
	RowBounds rowBounds = new RowBounds(NumberConstants.NUMERAL_ZORE, NumberConstants.NUMERAL_ONE);
	List<AirlinesAgentEntity> airlinesAgentList = getSqlSession().selectList(NAMESPACE + ".queryAirlinesAgentBySelective", airlinesAgent, rowBounds);
	if (CollectionUtils.isEmpty(airlinesAgentList)) {
    	    return null;
	}
	return airlinesAgentList.get(NumberConstants.NUMERAL_ZORE);
    }

    /**
     * <p>根据条件有选择的检索出符合条件的“航空公司代理人”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-15 下午2:59:18
     * @param airlinesAgent 以“航空公司代理人”实体承载的条件参数实体
     * @return 符合条件的“航空公司代理人”实体列表
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAirlinesAgentDao#queryAirlinesAgentListBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesAgentEntity)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<AirlinesAgentEntity> queryAirlinesAgentListBySelective(
            AirlinesAgentEntity airlinesAgent) {
	//强制设置为只查询“启用”的记录
	airlinesAgent.setActive(FossConstants.ACTIVE);
        return getSqlSession().selectList(NAMESPACE + ".queryAirlinesAgentBySelective", airlinesAgent);
    }
    
    /**
     * <p>根据条件有选择的统计出符合条件的“航空公司代理人”记录数（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-12-3 上午10:37:09
     * @param airlinesAgent 以“航空公司代理人”实体承载的条件参数实体
     * @return 符合条件的“航空公司代理人”记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAirlinesAgentDao#queryAirlinesAgentRecordCountBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesAgentEntity)
     */
    @Override
    public Long queryAirlinesAgentRecordCountBySelectiveCondition(AirlinesAgentEntity airlinesAgent) {
        Long totalCount = 0l;
	//强制设置为只查询“启用”的记录
	airlinesAgent.setActive(FossConstants.ACTIVE);
        Object obj = getSqlSession().selectOne(NAMESPACE + ".queryAirlinesAgentRecordCountBySelectiveCondition", airlinesAgent);
        if (null != obj) {
	    totalCount = Long.valueOf(obj.toString());
	}
        return totalCount;
    }
    
    /**
     * <p>根据条件有选择的检索出符合条件的“航空公司代理人”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-15 下午3:01:53
     * @param airlinesAgent 以“航空公司代理人”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 符合条件的“航空公司代理人”实体列表
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAirlinesAgentDao#queryAirlinesAgentListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesAgentEntity, int, int)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<AirlinesAgentEntity> queryAirlinesAgentListBySelectiveCondition(
            AirlinesAgentEntity airlinesAgent, int offset, int limit) {
	//强制设置为只查询“启用”的记录
	airlinesAgent.setActive(FossConstants.ACTIVE);
        return getSqlSession().selectList(NAMESPACE + ".queryAirlinesAgentListBySelectiveCondition",  airlinesAgent, new RowBounds(offset, limit));
    }
    
    /**
     * <p>根据条件有选择的检索出符合条件的“航空公司代理人”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-15 下午3:01:53
     * @param airlinesAgent 以“航空公司代理人”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 符合条件的“航空公司代理人”实体列表
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAirlinesAgentDao#queryAirlinesAgentListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesAgentEntity, int, int)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<AirlinesAgentEntity> queryAirlinesAgentListByAgentCode(AirlinesAgentEntity airlinesAgent) {
	//强制设置为只查询“启用”的记录
	airlinesAgent.setActive(FossConstants.ACTIVE);
        return getSqlSession().selectList(NAMESPACE + ".queryAirlinesAgentListByAgentCode",  airlinesAgent);
    }
    
}
