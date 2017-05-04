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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/DepartureStandardDao.java
 * 
 * FILE NAME        	: DepartureStandardDao.java
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressDepartureStandardDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDepartureStandardEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 发车标准Dao
 * @author foss-zhujunyong
 * @date Oct 26, 2012 11:41:33 AM
 * @version 1.0
 */
public class ExpressDepartureStandardDao extends SqlSessionDaoSupport implements
	IExpressDepartureStandardDao {

    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".expressDepartureStandard.";
    
    /** 
     * <p>添加发车标准</p> 
     * @author foss-zhujunyong
     * @date Oct 26, 2012 11:41:33 AM
     * @param departureStandard
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IDepartureStandardDao#addDepartureStandard(com.deppon.foss.module.base.baseinfo.api.shared.domain.DepartureStandardEntity)
     */
    @Override
    public ExpressDepartureStandardEntity addDepartureStandard(
    		ExpressDepartureStandardEntity departureStandard) {
	Date now = new Date();
	departureStandard.setId(UUIDUtils.getUUID());
	departureStandard.setVirtualCode(departureStandard.getId());
	departureStandard.setCreateDate(now);
	departureStandard.setModifyDate(new Date(NumberConstants.ENDTIME));
	departureStandard.setModifyUser(departureStandard.getCreateUser());
	departureStandard.setVersion(now.getTime());
	departureStandard.setActive(FossConstants.ACTIVE);
	int result = getSqlSession().insert(NAMESPACE + "addExpressDepartureStandard", departureStandard);
	return result > 0 ? departureStandard : null;
    }

    /** 
     * <p>作废发车标准</p> 
     * @author foss-zhujunyong
     * @date Oct 26, 2012 11:41:33 AM
     * @param departureStandard
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IDepartureStandardDao#deleteDepartureStandard(com.deppon.foss.module.base.baseinfo.api.shared.domain.DepartureStandardEntity)
     */
    @Override
    public ExpressDepartureStandardEntity deleteDepartureStandard(
    		ExpressDepartureStandardEntity departureStandard) {
	Date now = new Date();
	departureStandard.setModifyDate(now);
	departureStandard.setVersion(now.getTime());
	departureStandard.setActive(FossConstants.INACTIVE);
	int result = getSqlSession().update(NAMESPACE + "deleteExpressDepartureStandard", departureStandard);
	return result > 0 ? departureStandard : null;
    }

    /** 
     * <p>更新发车标准</p> 
     * @author foss-zhujunyong
     * @date Oct 26, 2012 11:41:33 AM
     * @param departureStandard
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IDepartureStandardDao#updateDepartureStandard(com.deppon.foss.module.base.baseinfo.api.shared.domain.DepartureStandardEntity)
     */
    @Override
    public ExpressDepartureStandardEntity updateDepartureStandard(
    		ExpressDepartureStandardEntity departureStandard) {
    	ExpressDepartureStandardEntity entity = deleteDepartureStandard(departureStandard);
	if (entity == null) {
	    return null;
	}
	entity.setId(UUIDUtils.getUUID());
	entity.setCreateDate(entity.getModifyDate());
	entity.setVersion(entity.getModifyDate().getTime());
	entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	entity.setCreateUser(entity.getModifyUser());
	entity.setActive(FossConstants.ACTIVE);
	int result = getSqlSession().insert(NAMESPACE + "addExpressDepartureStandard", entity);
	return result > 0 ? entity : null;
    }

    /** 
     * <p>查询发车标准详情</p> 
     * @author foss-zhujunyong
     * @date Oct 26, 2012 11:41:34 AM
     * @param id
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IDepartureStandardDao#queryDepartureStandardById(java.lang.String)
     */
    @Override
    public ExpressDepartureStandardEntity queryDepartureStandardById(String id) {
	return (ExpressDepartureStandardEntity) getSqlSession().selectOne(NAMESPACE + "queryExpressDepartureStandardById", id);
    }

    /** 
     * <p>查询特定线路下的发车标准列表</p> 
     * @author foss-zhujunyong
     * @date Oct 26, 2012 11:41:34 AM
     * @param lineVirtualCode
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IDepartureStandardDao#queryDepartureStandardListByLineVirtualCode(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ExpressDepartureStandardEntity> queryDepartureStandardListByLineVirtualCode(
	    String lineVirtualCode) {
	Map<String, String> map = new HashMap<String, String> ();
	map.put("active", FossConstants.ACTIVE);
	map.put("lineVirtualCode", lineVirtualCode);
	return (List<ExpressDepartureStandardEntity>) getSqlSession().selectList(NAMESPACE + "queryExpressDepartureStandardListByLineVirtualCode", map);
    }
    
    @Override
    public ExpressDepartureStandardEntity queryDepartureStandardByOrder(String lineVirtualCode, int start){
	Map<String, Object> map = new HashMap<String, Object> ();
	map.put("active", FossConstants.ACTIVE);
	map.put("lineVirtualCode", lineVirtualCode);
	map.put("order", start);
	@SuppressWarnings("unchecked")
	List<ExpressDepartureStandardEntity> list = (List<ExpressDepartureStandardEntity>) getSqlSession().selectList(NAMESPACE + "queryExpressDepartureStandardByOrder", map);
	return CollectionUtils.isNotEmpty(list) ? list.get(0) : null; 
    }

    @Override
    public int deleteDepartureStandardByLine(String lineVirtualCode, String modifyUser) {
	Map<String, Object> map = new HashMap<String, Object>();
	Date now = new Date();
	map.put("active", FossConstants.ACTIVE);
	map.put("inactive", FossConstants.INACTIVE);
	map.put("lineVirtualCode", lineVirtualCode);
	map.put("modifyDate", now);
	map.put("version", now.getTime());
	map.put("modifyUser", modifyUser);
	return getSqlSession().update(NAMESPACE + "deleteExpressDepartureStandardByLine", map);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ExpressDepartureStandardEntity> queryDepartureStandardListForDownload(ExpressDepartureStandardEntity line) {
	return (List<ExpressDepartureStandardEntity>) getSqlSession().selectList(NAMESPACE + "queryExpressDepartureStandardListForDownload", line);
    }

    @Override
    public Date queryLastModifyTime() {
	Long version = (Long) getSqlSession().selectOne(NAMESPACE + "queryExpressLastVersion");
	if (version == null) {
	    return null;
	}
	return new Date(version);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ExpressDepartureStandardEntity> queryDepartureStandardListForCache() {
	String active = FossConstants.ACTIVE;
	return (List<ExpressDepartureStandardEntity>)getSqlSession().selectList(NAMESPACE + "queryExpressDepartureStandardListForCache", active);
    }

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IDepartureStandardDao#queryDepartureStandardListForDownloadByPage(com.deppon.foss.module.base.baseinfo.api.shared.domain.DepartureStandardEntity, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpressDepartureStandardEntity>  queryDepartureStandardListForDownloadByPage(
			ExpressDepartureStandardEntity entity, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return (List<ExpressDepartureStandardEntity>) getSqlSession().selectList(NAMESPACE 
				+ "queryExpressDepartureStandardListForDownload",entity, rowBounds);
		   
	}
    
    
}
