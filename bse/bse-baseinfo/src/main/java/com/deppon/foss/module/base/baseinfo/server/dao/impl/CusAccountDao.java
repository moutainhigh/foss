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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/CusAccountDao.java
 * 
 * FILE NAME        	: CusAccountDao.java
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ICusAccountDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAccountEntity;
import com.deppon.foss.util.define.FossConstants;


/**
 * 客户开户银行DAO接口实现
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-11-21 上午9:33:55 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-11-21 上午9:33:55
 * @since
 * @version
 */
public class CusAccountDao extends SqlSessionDaoSupport implements ICusAccountDao {
    
    private static final String NAMESPACE = "foss.bse.bse-baseinfo.cusAccount.";

    /** 
     * 新增客户开户银行
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-11-21 上午9:10:37
     * @param entity
     *            客户开户银行实体
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICusAccountDao#addCusAccount(com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAccountEntity)
     */
    @Override
    public int addCusAccount(CusAccountEntity entity) {
	
	return this.getSqlSession().insert(NAMESPACE + "insert", entity);
    }

    /** 
     * 根据开户账号作废客户开户银行
     * @author dp-xieyantao
     * @date 2012-11-20 上午9:33:05
     * @param crmId
     * @param modifyUser
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICusAccountDao#deleteCusAccountByCode(java.lang.String, java.lang.String)
     */
    @Override
    public int deleteCusAccountByCode(BigDecimal crmId, String modifyUser) {
	
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("code", crmId);
	map.put("modifyUser", modifyUser);
	map.put("modifyDate", new Date());
	map.put("active", FossConstants.INACTIVE);
	map.put("active0", FossConstants.ACTIVE);
	
	return this.getSqlSession().update(NAMESPACE + "deleteByCode", map);
    }

    /** 
     * 修改客户开户银行
     * @author dp-xieyantao
     * @date 2012-11-21 上午9:10:37
     * @param entity
     *            客户开户银行实体
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICusAccountDao#updateCusAccount(com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAccountEntity)
     */
    @Override
    public int updateCusAccount(CusAccountEntity entity) {
	
	return this.getSqlSession().update(NAMESPACE + "update", entity);
    }
    
    /**
     * <p>根据crmId,最后一次修改时间查询客户开户银行是否存在</p> 
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
	map.put("active", null);
	
	List<CusAccountEntity> list = this.getSqlSession().selectList(NAMESPACE + "queryCusAccountByCrmId", map);
	return CollectionUtils.isNotEmpty(list);
    }
    
    /**
     * <p>根据客户编码查询时间最近的客户银行账户</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-5-2 上午9:15:40
     * @param custCode 客户编码
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICusAccountDao#queryAccountInfoByCustCode(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<CusAccountEntity> queryAccountInfoByCustCode(String custCode) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("custCode", custCode);
	map.put("active", FossConstants.ACTIVE);
	
	return this.getSqlSession().selectList(NAMESPACE + "queryAccountInfoByCustCode", map);
    }

    /**
     * 根据客户编码查询有效的银行账户信息
     *
     * auther:wangpeng_078816
     * date:2014-4-30
     *
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<CusAccountEntity> queryAccountLatestNewInfoByCustCode(
			String custCode) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("custCode", custCode);
		map.put("active", FossConstants.ACTIVE);
		
		return this.getSqlSession().selectList(NAMESPACE + "queryAccountLatestNewInfoByCustCode", map);
	}

}
