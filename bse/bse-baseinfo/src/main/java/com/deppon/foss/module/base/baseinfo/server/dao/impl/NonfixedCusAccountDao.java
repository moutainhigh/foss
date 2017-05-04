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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/NonfixedCusAccountDao.java
 * 
 * FILE NAME        	: NonfixedCusAccountDao.java
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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.INonfixedCusAccountDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.NonfixedCusAccountEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * 临欠散客开户银行账户DAO接口实现
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-11-29 上午11:41:12 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-11-29 上午11:41:12
 * @since
 * @version
 */
public class NonfixedCusAccountDao extends SqlSessionDaoSupport implements INonfixedCusAccountDao {
    
    private static final String NAMESPACE = "foss.bse.bse-baseinfo.nonfixedCusAccount.";

    /** 
     * 新增临欠散客开户银行账户
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-11-21 上午9:10:37
     * @param entity
     *            临欠散客开户银行账户实体
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICusAccountDao#addCusAccount(com.deppon.foss.module.base.baseinfo.api.shared.domain.NonfixedCustomerEntity)
     */
    @Override
    public int addCusAccount(NonfixedCusAccountEntity entity) {
	
	return this.getSqlSession().insert(NAMESPACE + "insert", entity);
    }

    /** 
     * 根据开户账号作废临欠散客开户银行账户
     * @author dp-xieyantao
     * @date 2012-11-20 上午9:33:05
     * @param crmId
     * @param modifyUser
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICusAccountDao#deleteCusAccountByCode(java.lang.String, java.lang.String)
     */
    @Override
    public int deleteCusAccountByCode(BigInteger crmId, String modifyUser) {
	
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("code", crmId);
	map.put("modifyUser", modifyUser);
	map.put("modifyDate", new Date());
	map.put("active", FossConstants.INACTIVE);
	map.put("active0", FossConstants.ACTIVE);
	
	return this.getSqlSession().update(NAMESPACE + "deleteByCode", map);
    }

    /** 
     * 修改临欠散客开户银行账户
     * @author dp-xieyantao
     * @date 2012-11-21 上午9:10:37
     * @param entity
     *            临欠散客开户银行账户实体
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICusAccountDao#updateCusAccount(com.deppon.foss.module.base.baseinfo.api.shared.domain.NonfixedCustomerEntity)
     */
    @Override
    public int updateCusAccount(NonfixedCusAccountEntity entity) {
	
	return this.getSqlSession().update(NAMESPACE + "update", entity);
    }
    
    /**
     * <p>根据crmId,最后一次修改时间查询临欠散客开户银行账户是否存在</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-11-26 下午3:05:15
     * @param crmId
     * @param lastupdatetime
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICusAccountDao#queryCusAccountByCrmId(java.math.BigDecimal, java.util.Date)
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean queryCusAccountByCrmId(BigDecimal crmId, Date lastupdatetime) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("modifyDate", lastupdatetime);
	map.put("crmId", crmId);
	map.put("active", FossConstants.ACTIVE);
	
	List<NonfixedCusAccountEntity> list = this.getSqlSession().selectList(NAMESPACE + "queryCusAccountByCrmId", map);
	return CollectionUtils.isNotEmpty(list);
    }
    
    /**
     * <p>根据crmId查询临欠散客银行账户信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-3-8 上午11:50:31
     * @param crmId 临欠散客银行账户信息ID
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.INonfixedCusAccountDao#queryCusAccountByCrmId(java.math.BigInteger)
     */
    @SuppressWarnings("unchecked")
    @Override
    public NonfixedCusAccountEntity queryCusAccountByCrmId(BigInteger crmId) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("modifyDate", null);
	map.put("crmId", crmId);
	map.put("active", FossConstants.ACTIVE);
	
	List<NonfixedCusAccountEntity> list = this.getSqlSession().selectList(NAMESPACE + "queryCusAccountByCrmId", map);
	if(CollectionUtils.isNotEmpty(list)){
	    return list.get(0);
	}else {
	    return null;
	}
    }
    
    /**
     * <p>根据临欠散客的客户编码查询散客的银行账号</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-2-21 上午9:17:18
     * @param custCode 临欠散客客户编码
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.INonfixedCusAccountDao#queryCusAccountByCustCode(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<NonfixedCusAccountEntity> queryCusAccountByCustCode(
	    String custCode) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("active", FossConstants.ACTIVE);
	map.put("custCode", custCode);
	
	return this.getSqlSession().selectList(NAMESPACE + "queryCusAccountByCustCode", map);
    }
    
    /**
     * <p>根据客户编码查询时间最近的客户银行账户</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-5-2 下午5:02:13
     * @param custCode 客户编码
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.INonfixedCusAccountDao#queryAccountInfoByCustCode(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<NonfixedCusAccountEntity> queryAccountInfoByCustCode(
	    String custCode) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("active", FossConstants.ACTIVE);
	map.put("custCode", custCode);
	
	return this.getSqlSession().selectList(NAMESPACE + "queryAccountInfoByCustCode", map);
    }
    
}
