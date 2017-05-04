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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/BargainAppOrgDao.java
 * 
 * FILE NAME        	: BargainAppOrgDao.java
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
import com.deppon.foss.module.base.baseinfo.api.server.dao.IBargainAppOrgDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BargainAppOrgEntity;
import com.deppon.foss.util.define.FossConstants;


/**
 * 合同适用部门DAO接口实现
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-11-21 上午9:24:19 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-11-21 上午9:24:19
 * @since
 * @version
 */
public class BargainAppOrgDao extends SqlSessionDaoSupport implements
	IBargainAppOrgDao {
    
    private static final String NAMESPACE = "foss.bse.bse-baseinfo.bargainAppOrg.";

    /** 
     * 新增合同适用部门
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-11-21 上午9:18:15
     * @param entity
     *            合同适用部门实体
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IBargainAppOrgDao#addBargainAppOrg(com.deppon.foss.module.base.baseinfo.api.shared.domain.BargainAppOrgEntity)
     */
    @Override
    public int addBargainAppOrg(BargainAppOrgEntity entity) {
	
	return this.getSqlSession().insert(NAMESPACE + "insert", entity);
    }

    /** 
     * 根据code作废合同适用部门
     * @author dp-xieyantao
     * @date 2012-11-21 上午9:18:15
     * @param crmId
     * @param modifyUser
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IBargainAppOrgDao#deleteBargainAppOrgByCode(java.lang.String, java.lang.String)
     */
    @Override
    public int deleteBargainAppOrgByCode(BigDecimal crmId, String modifyUser) {
	
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("code", crmId);
	map.put("modifyUser", modifyUser);
	map.put("modifyDate", new Date());
	map.put("active", FossConstants.INACTIVE);
	map.put("active0", FossConstants.ACTIVE);
	
	return this.getSqlSession().update(NAMESPACE + "deleteByCode", map);
    }

    /** 
     * 修改合同适用部门
     * @author dp-xieyantao
     * @date 2012-11-21 上午9:18:15
     * @param entity
     *            合同适用部门实体
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IBargainAppOrgDao#updateBargainAppOrg(com.deppon.foss.module.base.baseinfo.api.shared.domain.BargainAppOrgEntity)
     */
    @Override
    public int updateBargainAppOrg(BargainAppOrgEntity entity) {
	
	return this.getSqlSession().update(NAMESPACE + "update", entity);
    }
    
    /**
     * <p>根据crmId,最后一次修改时间查询合同适用部门是否存在</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-11-26 下午3:09:02
     * @param crmId
     * @param lastupdatetime
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IBargainAppOrgDao#queryBargainAppOrgByCrmId(java.math.BigDecimal, java.util.Date)
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean queryBargainAppOrgByCrmId(BigDecimal crmId,
	    Date lastupdatetime) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("modifyDate", lastupdatetime);
	map.put("crmId", crmId);
	map.put("active", null);
	
	List<BargainAppOrgEntity> list = this.getSqlSession().selectList(NAMESPACE + "queryBargainAppOrgByCrmId", map);
	return CollectionUtils.isNotEmpty(list);
    }
    
    /**
     * <p>根据客户合同CRM_ID查询合同适用部门信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-6-3 上午11:28:02
     * @param bargainCrmId 客户合同CRM_ID
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IBargainAppOrgDao#queryAppOrgByBargainCrmId(java.math.BigDecimal)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<BargainAppOrgEntity> queryAppOrgByBargainCrmId(
	    BigDecimal bargainCrmId) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("bargainId", bargainCrmId);
	map.put("active", FossConstants.ACTIVE);
	
	return this.getSqlSession().selectList(NAMESPACE + "queryAppOrgByBargainCrmId", map);
    }
    
    /**
     * 根据crmId和部门编码获取合同适用部门
     * @param bargainCrmId
     * @param unifiedCode
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<BargainAppOrgEntity> queryAppOrgByBargainCrmIdAndCode(BigDecimal bargainCrmId,String unifiedCode){
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("bargainId", bargainCrmId);
    	map.put("active", FossConstants.ACTIVE);
    	map.put("unifiedCode", unifiedCode);
    	return this.getSqlSession().selectList(NAMESPACE + "queryAppOrgByBargainCrmIdAndCode", map);
    }
    
    /**
     * 根据crmId和部门编码列表获取合同适用部门
     * @param bargainCrmId
     * @param unifiedCodeList
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<BargainAppOrgEntity> queryAppOrgByBargainCrmIdAndCodeList(BigDecimal bargainCrmId,List<String> unifiedCodeList){
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("bargainId", bargainCrmId);
    	map.put("active", FossConstants.ACTIVE);
    	map.put("unifiedCodeList", unifiedCodeList);
    	return this.getSqlSession().selectList(NAMESPACE + "queryAppOrgByBargainCrmIdAndCodeList", map);
    }

}
