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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/FreightRouteDao.java
 * 
 * FILE NAME        	: FreightRouteDao.java
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
import org.apache.ibatis.session.RowBounds;
import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IFreightRouteDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 走货路径Dao
 * @author foss-zhujunyong
 * @date Oct 29, 2012 1:52:39 PM
 * @version 1.0
 */
public class FreightRouteDao extends SqlSessionDaoSupport implements IFreightRouteDao {


    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".freightRoute.";

    /** 
     * <p>添加走货路径</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:35:38 AM
     * @param freightRoute
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IFreightRouteDao#addFreightRoute(com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity)
     */
    @Override
    public FreightRouteEntity addFreightRoute(FreightRouteEntity freightRoute) {
	Date now = new Date();
	freightRoute.setId(UUIDUtils.getUUID());
	freightRoute.setVirtualCode(freightRoute.getId());
	freightRoute.setCreateDate(now);
	freightRoute.setVersion(now.getTime());
	freightRoute.setModifyDate(new Date(NumberConstants.ENDTIME));
	freightRoute.setModifyUser(freightRoute.getCreateUser());
	freightRoute.setActive(FossConstants.ACTIVE);
	int result = getSqlSession().insert(NAMESPACE + "addFreightRoute", freightRoute);
	return result > 0 ? freightRoute : null;
    }

    /** 
     * <p>生效或失效走货路径</p> 
     * @author foss-zhujunyong
     * @date Jan 16, 2013 8:07:24 PM
     * @param freightRoute
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IFreightRouteDao#validFreightRoute(com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity)
     */
    @Override
    public int validFreightRoute(FreightRouteEntity freightRoute) {
	Date now = new Date();
	freightRoute.setModifyDate(now);
	freightRoute.setVersion(now.getTime());
	return getSqlSession().update(NAMESPACE + "validFreightRoute", freightRoute);
    }
    
    
    /** 
     * <p>作废走货路径</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:35:38 AM
     * @param freightRoute
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IFreightRouteDao#deleteFreightRoute(com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity)
     */
    @Override
    public FreightRouteEntity deleteFreightRoute(FreightRouteEntity freightRoute) {
	Date now = new Date();
	freightRoute.setActive(FossConstants.INACTIVE);
	freightRoute.setModifyDate(now);
	freightRoute.setVersion(now.getTime());
	int result = getSqlSession().update(NAMESPACE + "deleteFreightRoute", freightRoute);
	return result > 0 ? freightRoute : null;
    }

    /** 
     * <p>更新走货路径</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:35:38 AM
     * @param freightRoute
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IFreightRouteDao#updateFreightRoute(com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity)
     */
    @Override
    public FreightRouteEntity updateFreightRoute(FreightRouteEntity freightRoute) {
	FreightRouteEntity entity = deleteFreightRoute(freightRoute);
	if (entity == null) {
	    return null;
	}
	
	entity.setId(UUIDUtils.getUUID());
	entity.setCreateDate(entity.getModifyDate());
	entity.setVersion(entity.getModifyDate().getTime());
	entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	entity.setCreateUser(entity.getModifyUser());
	entity.setActive(FossConstants.ACTIVE);
	entity.setModifyUser(FossUserContext.getCurrentInfo().getEmpCode());
	int result = getSqlSession().insert(NAMESPACE + "addFreightRoute", entity);
	return result > 0 ? entity : null;
    }

    /** 
     * <p>根据id查询走货路径</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:35:39 AM
     * @param id
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IFreightRouteDao#queryFreightRouteById(java.lang.String)
     */
    @Override
    public FreightRouteEntity queryFreightRouteById(String id) {
	return (FreightRouteEntity) getSqlSession().selectOne(NAMESPACE + "queryFreightRouteById", id);
    }

    /**
     * 
     * <p>根据虚拟代码查询走货路径</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:33:00 AM
     * @param virtualCode
     * @return
     * @see
     */
    @Override
    public FreightRouteEntity queryFreightRouteByVirtualCode(String virtualCode){
	Map<String, String> map = new HashMap<String, String>();
	map.put("active", FossConstants.ACTIVE);
	map.put("virtualCode", virtualCode);
	return (FreightRouteEntity) getSqlSession().selectOne(NAMESPACE + "queryFreightRouteByVirtualCode", map);
    }
    
    
    /** 
     * <p>根据查询条件查询走货路径</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:35:39 AM
     * @param freightRoute
     * @param start
     * @param limit
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IFreightRouteDao#queryFreightRouteListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity, int, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<FreightRouteEntity> queryFreightRouteListByCondition(FreightRouteEntity freightRoute,
	    int start, int limit) {
	freightRoute.setActive(FossConstants.ACTIVE);
	return (List<FreightRouteEntity>) getSqlSession().selectList(NAMESPACE + "queryFreightRouteListByCondition", freightRoute, new RowBounds(start, limit));
    }

    /** 
     * <p>根据查询条件查询走货路径数量</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:35:39 AM
     * @param freightRoute
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IFreightRouteDao#countFreightRouteListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity)
     */
    @Override
    public long countFreightRouteListByCondition(FreightRouteEntity freightRoute) {
	return (Long) getSqlSession().selectOne(NAMESPACE + "countFreightRouteListByCondition", freightRoute);
    }
    
    
    @SuppressWarnings("unchecked")
    @Override
    public List<FreightRouteEntity> queryFreightRouteForDownload(FreightRouteEntity freightRoute) {
	return (List<FreightRouteEntity>) getSqlSession().selectList(NAMESPACE + "queryFreightRouteForDownload", freightRoute); 
    }

    @Override
    public Date queryLastModifyTime() {
	Long version = (Long) getSqlSession().selectOne(NAMESPACE + "queryLastVersion");
	if (version == null) {
	    return null;
	}
	return new Date(version);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<FreightRouteEntity> queryFreightRouteListForCache() {
	String active = FossConstants.ACTIVE;
	return (List<FreightRouteEntity>)getSqlSession().selectList(NAMESPACE + "queryFreightRouteListForCache", active);
    }

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IFreightRouteDao#queryFreightRouteForDownloadByPage(com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity, int, int)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<FreightRouteEntity> queryFreightRouteForDownloadByPage(FreightRouteEntity freightRoute,
			int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return (List<FreightRouteEntity>) getSqlSession().selectList(
				NAMESPACE + "queryFreightRouteForDownload", freightRoute,rowBounds); 
	    
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FreightRouteEntity> queryAirFreightRouteList(String sourceCode) {
		
		return (List<FreightRouteEntity>) getSqlSession().selectList(NAMESPACE + "queryAirFreightRouteList", sourceCode);
	}
    
}
