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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/ContactWithAirlinesDao.java
 * 
 * FILE NAME        	: ContactWithAirlinesDao.java
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
import com.deppon.foss.module.base.baseinfo.api.server.dao.IContactWithAirlinesDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ContactAirlinesEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 用来操作交互“正单交货人”的数据库对应数据访问DAO接口实现类：SUC-37
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-11-2 下午5:29:19</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-11-2 下午5:29:19
 * @since
 * @version
 */
public class ContactWithAirlinesDao extends SqlSessionDaoSupport implements IContactWithAirlinesDao {

    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".contactwithAirlines";
    
    /**
     * <p>新增一个“正单交货人”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午11:53:16
     * @param contactAirlines “正单交货人”实体
     * @return 影响记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IContactWithAirlinesDao#addContactWithAirlines(com.deppon.foss.module.base.baseinfo.api.shared.domain.ContactAirlinesEntity)
     */
    @Override
    public int addContactWithAirlines(ContactAirlinesEntity contactAirlines) {
	contactAirlines.setId(UUIDUtils.getUUID());
	contactAirlines.setActive(FossConstants.ACTIVE);
	contactAirlines.setCreateDate(new Date());
	contactAirlines.setModifyDate(contactAirlines.getCreateDate());
	contactAirlines.setModifyUser(contactAirlines.getCreateUser());
	return getSqlSession().insert(NAMESPACE + ".addContactWithAirlines", contactAirlines);
    }

    /**
     * <p>新增一个“正单交货人”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午11:53:13
     * @param contactAirlines “正单交货人”实体
     * @return 影响记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IContactWithAirlinesDao#addContactWithAirlinesBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.ContactAirlinesEntity)
     */
    @Override
    public int addContactWithAirlinesBySelective(ContactAirlinesEntity contactAirlines) {
	contactAirlines.setId(UUIDUtils.getUUID());
	contactAirlines.setActive(FossConstants.ACTIVE);
	contactAirlines.setCreateDate(new Date());
	contactAirlines.setModifyDate(contactAirlines.getCreateDate());
	contactAirlines.setModifyUser(contactAirlines.getCreateUser());
	return getSqlSession().insert(NAMESPACE + ".addContactWithAirlinesBySelective", contactAirlines);
    }

    /**
     * <p>根据“正单交货人”记录唯一标识作废（物理删除）一条“正单交货人”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午11:53:02
     * @param id 记录唯一标识
     * @return 影响记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IContactWithAirlinesDao#deleteContactWithAirlines(java.lang.String)
     */
    @Override
    public int deleteContactWithAirlines(String id) {
	return getSqlSession().delete(NAMESPACE + ".deleteContactWithAirlines", id);
    }
    
    /**
     * <p>修改一个“正单交货人”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午11:53:09
     * @param contactAirlines “正单交货人”实体
     * @return 影响记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IContactWithAirlinesDao#updateContactWithAirlinesBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.ContactAirlinesEntity)
     */
    @Override
    public int updateContactWithAirlinesBySelective(
	    ContactAirlinesEntity contactAirlines) {
	contactAirlines.setModifyDate(new Date());
	return getSqlSession().update(NAMESPACE + ".updateContactWithAirlinesBySelective", contactAirlines);
    }

    /**
     * <p>修改一个“正单交货人”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午11:53:07
     * @param contactAirlines “正单交货人”实体
     * @return 影响记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IContactWithAirlinesDao#updateContactWithAirlines(com.deppon.foss.module.base.baseinfo.api.shared.domain.ContactAirlinesEntity)
     */
    @Override
    public int updateContactWithAirlines(ContactAirlinesEntity contactAirlines) {
	contactAirlines.setModifyDate(new Date());
	return getSqlSession().update(NAMESPACE + ".updateContactWithAirlines", contactAirlines);
    }

    /**
     * <p>根据条件有选择的检索出符合条件的“正单交货人”唯一实体（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午11:53:11
     * @param contactAirlines 以“正单交货人”实体承载的条件参数实体
     * @return “正单交货人”实体
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IContactWithAirlinesDao#queryContactWithAirlinesBySelective(java.lang.String)
     */
    @Override
    @SuppressWarnings("unchecked")
    public ContactAirlinesEntity queryContactWithAirlinesBySelective(ContactAirlinesEntity contactAirlines) {
	if(StringUtils.isBlank(contactAirlines.getId())){
	    //强制设置为只查询“启用”的记录
	    contactAirlines.setActive(FossConstants.ACTIVE);
	}//ID存在，则记录肯定唯一，忽略其他参数
	
	List<ContactAirlinesEntity> contactAirlinesList = null;
	RowBounds rowBounds = new RowBounds(NumberConstants.NUMERAL_ZORE, NumberConstants.NUMERAL_ONE);
	contactAirlinesList = getSqlSession().selectList(NAMESPACE + ".queryContactWithAirlinesListBySelective", contactAirlines, rowBounds);
	if (CollectionUtils.isEmpty(contactAirlinesList)) {
	    return null;
	}
	return contactAirlinesList.get(NumberConstants.NUMERAL_ZORE);
    }
    
    /**
     * <p>根据条件有选择的检索出符合条件的“正单交货人”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-2 下午5:36:39
     * @param contactAirlines 以“正单交货人”实体承载的条件参数实体
     * @return 符合条件的“正单交货人”实体列表
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IContactWithAirlinesDao#queryContactWithAirlinesListBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.ContactAirlinesEntity)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<ContactAirlinesEntity> queryContactWithAirlinesListBySelective(
            ContactAirlinesEntity contactAirlines) {
	//强制设置为只查询“启用”的记录
	contactAirlines.setActive(FossConstants.ACTIVE);
        return getSqlSession().selectList(NAMESPACE + ".queryContactWithAirlinesListBySelective", contactAirlines);
    }
    
    /**
     * <p>根据条件（分页模糊）有选择的统计出符合条件的“正单交货人”实体记录数（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-25 上午10:15:40
     * @param flight 以“正单交货人”实体承载的条件参数实体
     * @return 符合条件的“正单交货人”实体记录条数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IContactWithAirlinesDao#queryContactAirlinesCountBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.ContactAirlinesEntity)
     */
    @Override
    public long queryContactAirlinesCountBySelectiveCondition(ContactAirlinesEntity contactAirlines) {
	long recordRount = 0;
	//强制设置为只查询“启用”的记录s
	contactAirlines.setActive(FossConstants.ACTIVE);
        Object result = getSqlSession().selectOne(NAMESPACE + ".queryContactAirlinesCountBySelectiveCondition", contactAirlines);
        if (null != result) {
            recordRount = Long.parseLong(result.toString());
	}
        return recordRount;
    }
    
    /**
     * <p>根据条件有选择的检索出符合条件的“正单交货人”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-2 下午5:36:39
     * @param contactAirlines 以“正单交货人”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 符合条件的“正单交货人”实体列表
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IContactWithAirlinesDao#queryContactAirlinesListBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.ContactAirlinesEntity, int, int)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<ContactAirlinesEntity> queryContactAirlinesListBySelectiveCondition(
            ContactAirlinesEntity contactAirlines, int offset, int limit) {
	//强制设置为只查询“启用”的记录
	contactAirlines.setActive(FossConstants.ACTIVE);
        return getSqlSession().selectList(NAMESPACE + ".queryContactAirlinesListBySelectiveCondition", contactAirlines, new RowBounds(offset, limit));
    }
}
