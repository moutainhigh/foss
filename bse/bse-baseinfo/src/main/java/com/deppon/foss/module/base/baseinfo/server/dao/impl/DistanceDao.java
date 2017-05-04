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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/DistanceDao.java
 * 
 * FILE NAME        	: DistanceDao.java
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IDistanceDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DistanceEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 库区月台距离dao
 * @author foss-zhujunyong
 * @date Oct 18, 2012 10:50:35 AM
 * @version 1.0
 */
public class DistanceDao extends SqlSessionDaoSupport implements
	IDistanceDao {
    
    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".distance.";
    
    /** 
     * <p>添加库位到月台的距离</p> 
     * @author foss-zhujunyong
     * @date Oct 18, 2012 10:50:35 AM
     * @param entity
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IDistanceDao#addDistance(com.deppon.foss.module.base.baseinfo.api.shared.domain.DistanceEntity)
     */
    @Override
    public DistanceEntity addDistance(DistanceEntity entity) {
	entity.setId(UUIDUtils.getUUID());
	entity.setCreateDate(new Date());
	entity.setModifyDate(entity.getCreateDate());
	entity.setModifyUser(entity.getCreateUser());
	entity.setActive(FossConstants.ACTIVE);
	int result = getSqlSession().insert(NAMESPACE + "addDistance", entity);
	return result > 0 ? entity : null;
    }

    /** 
     * <p>根据库区虚拟代码和月台虚拟代码作废库位月台距离</p> 
     * @author foss-zhujunyong
     * @date Oct 18, 2012 10:50:35 AM
     * @param entity
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IDistanceDao#deleteDistance(com.deppon.foss.module.base.baseinfo.api.shared.domain.DistanceEntity)
     */
    @Override
    public int deleteDistance(DistanceEntity entity) {
	return getSqlSession().delete(NAMESPACE + "deleteDistance", entity);
    }

    /** 
     * <p>根据库区虚拟代码批量作废库位月台距离</p> 
     * @author foss-zhujunyong
     * @date Oct 18, 2012 10:50:36 AM
     * @param storageVirtualCode
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IDistanceDao#deleteDistanceByStorage(java.lang.String)
     */
    @Override
    public int deleteDistanceByStorage(String storageVirtualCode) {
	return getSqlSession().delete(NAMESPACE + "deleteDistanceByStorage", storageVirtualCode);
    }

    /** 
     * <p>根据月台虚拟代码批量作废库位月台距离</p> 
     * @author foss-zhujunyong
     * @date Oct 18, 2012 10:50:36 AM
     * @param platformVirtualCode
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IDistanceDao#deleteDistanceByPlatform(java.lang.String)
     */
    @Override
    public int deleteDistanceByPlatform(String platformVirtualCode) {
	return getSqlSession().delete(NAMESPACE + "deleteDistanceByPlatform", platformVirtualCode);
    }

    /** 
     * <p>更新库位月台距离</p> 
     * @author foss-zhujunyong
     * @date Oct 18, 2012 10:50:36 AM
     * @param entity
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IDistanceDao#updateDistance(com.deppon.foss.module.base.baseinfo.api.shared.domain.DistanceEntity)
     */
    @Override
    public DistanceEntity updateDistance(DistanceEntity entity) {
	entity.setModifyDate(new Date());
	entity.setActive(FossConstants.ACTIVE);
	int result = getSqlSession().update(NAMESPACE + "updateDistance", entity);
	return result > 0 ? entity : null;
    }

    /** 
     * <p>查询指定库位的所有距离</p> 
     * @author foss-zhujunyong
     * @date Oct 18, 2012 10:50:36 AM
     * @param virtualStorageCode
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IDistanceDao#queryDistanceByStorage(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<DistanceEntity> queryDistanceByStorage(
	    String virtualStorageCode) {
	return (List<DistanceEntity>) getSqlSession().selectList(NAMESPACE + "queryDistanceByStorage", virtualStorageCode);
    }

    /** 
     * <p>查询指定月台的所有距离</p> 
     * @author foss-zhujunyong
     * @date Oct 18, 2012 10:50:36 AM
     * @param virtualPlatformCode
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IDistanceDao#queryDistanceByPlatform(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<DistanceEntity> queryDistanceByPlatform(
	    String virtualPlatformCode) {
	return (List<DistanceEntity>) getSqlSession().selectList(NAMESPACE + "queryDistanceByPlatform", virtualPlatformCode);
    }

    /**
     * 
     * <p>
     * 查询指定外场的所有月台到库位的距离
     * </p>
     * 
     * @author foss-zhujunyong
     * @date Mar 11, 2013 5:04:45 PM
     * @param organizationCode
     * @return
     * @see
     */
    @SuppressWarnings("unchecked")
    public List<DistanceEntity> queryDistanceListByOrganizationCode(String organizationCode) {
	if (StringUtils.isBlank(organizationCode)) {
	    return new ArrayList<DistanceEntity> ();
	}
	return (List<DistanceEntity>) getSqlSession().selectList(NAMESPACE + "queryDistanceListByOrganizationCode", organizationCode);
    }
    
}
