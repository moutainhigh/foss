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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/AirlinesAccountDao.java
 * 
 * FILE NAME        	: AirlinesAccountDao.java
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
import com.deppon.foss.module.base.baseinfo.api.server.dao.IAirlinesAccountDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesAccountEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 用来操作交互“航空公司账户”的数据库对应数据访问DAO接口实现类：SUC-43 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-11-2 下午5:30:16</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-11-2 下午5:30:16
 * @since
 * @version
 */
public class AirlinesAccountDao extends SqlSessionDaoSupport implements IAirlinesAccountDao {

    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".airlinesaccount";
    
    /**
     * <p>新增一个“航空公司账户”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午8:49:16
     * @param airlinesAccount “航空公司账户”实体
     * @return 影响记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAirlinesAccountDao#addAirlinesAccount(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesAccountEntity)
     */
    @Override
    public int addAirlinesAccount(AirlinesAccountEntity airlinesAccount) {
	airlinesAccount.setId(UUIDUtils.getUUID());
	airlinesAccount.setActive(FossConstants.ACTIVE);
	airlinesAccount.setCreateDate(new Date());
	airlinesAccount.setModifyDate(airlinesAccount.getCreateDate());
	airlinesAccount.setModifyUser(airlinesAccount.getCreateUser());
	return getSqlSession().insert(NAMESPACE + ".addAirlinesAccount", airlinesAccount);
    }

    /**
     * <p>新增一个“航空公司账户”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午8:49:18
     * @param airlinesAccount “航空公司账户”实体
     * @return 影响记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAirlinesAccountDao#addAirlinesAccountBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesAccountEntity)
     */
    @Override
    public int addAirlinesAccountBySelective(
	    AirlinesAccountEntity airlinesAccount) {
	airlinesAccount.setId(UUIDUtils.getUUID());
	airlinesAccount.setActive(FossConstants.ACTIVE);
	airlinesAccount.setCreateDate(new Date());
	airlinesAccount.setModifyDate(airlinesAccount.getCreateDate());
	airlinesAccount.setModifyUser(airlinesAccount.getCreateUser());
	return getSqlSession().insert(NAMESPACE + ".addAirlinesAccountBySelective", airlinesAccount);
    }
    
    /**
     * <p>根据“航空公司账户”记录唯一标识作废（物理删除）一条“航空公司账户”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午8:49:10
     * @param id 记录唯一标识
     * @return 影响记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAirlinesAccountDao#deleteByPrimaryKey(java.lang.String)
     */
    @Override
    public int deleteAirlinesAccount(String id) {
	return getSqlSession().delete(NAMESPACE + ".deleteAirlinesAccount", id);
    }

    /**
     * <p>修改一个“航空公司账户”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午8:49:26
     * @param airlinesAccount “航空公司账户”实体
     * @return 影响记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAirlinesAccountDao#updateAirlinesAccountBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesAccountEntity)
     */
    @Override
    public int updateAirlinesAccountBySelective(AirlinesAccountEntity airlinesAccount) {
	airlinesAccount.setModifyDate(new Date());
	return getSqlSession().update(NAMESPACE + ".updateAirlinesAccountBySelective", airlinesAccount);
    }

    /**
     * <p>修改一个“航空公司账户”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午8:49:29
     * @param airlinesAccount “航空公司账户”实体
     * @return 影响记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAirlinesAccountDao#updateAirlinesAccount(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesAccountEntity)
     */
    @Override
    public int updateAirlinesAccount(AirlinesAccountEntity airlinesAccount) {
	airlinesAccount.setModifyDate(new Date());
	return getSqlSession().update(NAMESPACE + ".updateAirlinesAccount", airlinesAccount);
    }
    
    /**
     * <p>根据条件有选择的检索出符合条件的“航空公司账户”唯一的一个实体（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-12-3 上午10:09:01
     * @param airlinesAccount 以“航空公司账户”实体承载的条件参数实体
     * @return “航空公司账户”实体
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAirlinesAccountDao#queryAirlinesAccount(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesAccountEntity)
     */
    @Override
    @SuppressWarnings("unchecked")
    public AirlinesAccountEntity queryAirlinesAccountBySelective(AirlinesAccountEntity airlinesAccount) {
	if(StringUtils.isBlank(airlinesAccount.getId())){
	    //强制设置为只查询“启用”的记录
	    airlinesAccount.setActive(FossConstants.ACTIVE);
	}
	RowBounds rowBounds = new RowBounds(NumberConstants.NUMERAL_ZORE, NumberConstants.NUMERAL_ONE);
	List<AirlinesAccountEntity> airlinesAccountList = getSqlSession().selectList(NAMESPACE + ".queryAirlinesAccountListBySelective", airlinesAccount, rowBounds);
	if (CollectionUtils.isEmpty(airlinesAccountList)) {
	    return null;
	}
	return airlinesAccountList.get(NumberConstants.NUMERAL_ZORE);
    }
    
    /**
     * <p>根据条件有选择的检索出符合条件的“航空公司账户”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-2 下午5:36:39
     * @param leasedDriver 以“航空公司账户”实体承载的条件参数实体
     * @return 符合条件的“航空公司账户”实体列表
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAirlinesAccountDao#queryAirlinesAccountListBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesAccountEntity)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<AirlinesAccountEntity> queryAirlinesAccountListBySelective(AirlinesAccountEntity airlinesAccount) {
	//强制设置为只查询“启用”的记录
	airlinesAccount.setActive(FossConstants.ACTIVE);
	return getSqlSession().selectList(NAMESPACE + ".queryAirlinesAccountListBySelective", airlinesAccount);
    }
    
    /**
     * <p>根据条件（分页模糊）统计处出符合条件的“航空公司账户”实体记录数（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-12-3 上午10:10:07
     * @param airlinesAccount 以“航空公司账户”实体承载的条件参数实体
     * @return 符合条件的“航空公司账户”记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAirlinesAccountDao#queryAirlinesAccountRecordCountBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesAccountEntity)
     */
    @Override
    public Long queryAirlinesAccountRecordCountBySelectiveCondition(AirlinesAccountEntity airlinesAccount) {
        Long totalRecord = 0l;
	//强制设置为只查询“启用”的记录
	airlinesAccount.setActive(FossConstants.ACTIVE);
        Object obj = getSqlSession().selectOne(NAMESPACE + ".queryAirlinesAccountRecordCountBySelectiveCondition", airlinesAccount);
        if (null != obj) {
	    totalRecord = Long.valueOf(obj.toString());
	}
        return totalRecord;
    }
    
    /**
     * <p>根据条件（分页模糊）有选择的检索出符合条件的“航空公司账户”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-2 下午5:36:39
     * @param leasedDriver 以“航空公司账户”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 符合条件的“航空公司账户”实体列表
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAirlinesAccountDao#queryAirlinesAccountListBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesAccountEntity, int, int)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<AirlinesAccountEntity> queryAirlinesAccountListBySelectiveCondition(
            AirlinesAccountEntity airlinesAccount, int offset, int limit) {
	//强制设置为只查询“启用”的记录
	airlinesAccount.setActive(FossConstants.ACTIVE);
        return getSqlSession().selectList(NAMESPACE + ".queryAirlinesAccountListBySelectiveCondition", airlinesAccount, new RowBounds(offset, limit));
    }
}
