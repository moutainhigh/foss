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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/StorageDao.java
 * 
 * FILE NAME        	: StorageDao.java
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
import com.deppon.foss.module.base.baseinfo.api.server.dao.IStorageDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.StorageEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 库位Dao
 * @author foss-zhujunyong
 * @date Oct 16, 2012 6:17:39 PM
 * @version 1.0
 */
public class StorageDao extends SqlSessionDaoSupport implements IStorageDao {

    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".storage.";
    
    /** 
     * <p>新增库位</p> 
     * @author foss-zhujunyong
     * @date Oct 16, 2012 6:17:39 PM
     * @param storage
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IStorageDao#addStorage(com.deppon.foss.module.base.baseinfo.api.shared.domain.StorageEntity)
     */
    @Override
    public StorageEntity addStorage(StorageEntity storage) {
	storage.setId(UUIDUtils.getUUID());
	storage.setVirtualCode(storage.getId());
	storage.setCreateDate(new Date());
	storage.setModifyDate(new Date(NumberConstants.ENDTIME));
	storage.setModifyUser(storage.getCreateUser());
	storage.setActive(FossConstants.ACTIVE);
	int result = getSqlSession().insert(NAMESPACE + "addStorage", storage);
	return result > 0 ? storage : null;
    }

    /** 
     * <p>作废库位</p> 
     * @author foss-zhujunyong
     * @date Oct 16, 2012 6:17:39 PM
     * @param storage
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IStorageDao#deleteStorage(com.deppon.foss.module.base.baseinfo.api.shared.domain.StorageEntity)
     */
    @Override
    public StorageEntity deleteStorage(StorageEntity storage) {
	storage.setActive(FossConstants.INACTIVE);
	storage.setModifyDate(new Date());
	int result = getSqlSession().update(NAMESPACE + "deleteStorage", storage); 
	return result > 0 ? storage : null;
    }

    /** 
     * <p>更新库位</p> 
     * @author foss-zhujunyong
     * @date Oct 16, 2012 6:17:39 PM
     * @param storage
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IStorageDao#updateStorage(com.deppon.foss.module.base.baseinfo.api.shared.domain.StorageEntity)
     */
    @Override
    public StorageEntity updateStorage(StorageEntity storage) {
	StorageEntity entity = deleteStorage(storage);
	if (entity == null) {
	    return null;
	}
	
	entity.setId(UUIDUtils.getUUID());
	entity.setCreateDate(entity.getModifyDate());
	entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	entity.setCreateUser(entity.getModifyUser());
	entity.setActive(FossConstants.ACTIVE);
	int result = getSqlSession().insert(NAMESPACE + "addStorage", entity);
	return result > 0 ? entity : null;
    }

    /** 
     * <p>查询单个库位</p> 
     * @author foss-zhujunyong
     * @date Oct 16, 2012 6:17:39 PM
     * @param virtualCode
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IStorageDao#queryStorageByVirtualCode(java.lang.String)
     */
    @Override
    public StorageEntity queryStorageById(String id) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("active", FossConstants.ACTIVE);
	map.put("id", id);
	return (StorageEntity) getSqlSession().selectOne(NAMESPACE + "queryStorageById", map);
    }

    /** 
     * <p>按条件查询库位列表</p> 
     * @author foss-zhujunyong
     * @date Oct 16, 2012 6:17:40 PM
     * @param storage
     * @param start
     * @param limit
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IStorageDao#queryStorageListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.StorageEntity, int, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<StorageEntity> queryStorageListByCondition(
	    StorageEntity storage, int start, int limit) {
	storage.setActive(FossConstants.ACTIVE);
	return (List<StorageEntity>)getSqlSession().selectList(NAMESPACE + "queryStorageListByCondition", storage, new RowBounds(start, limit));
    }

    /** 
     * <p>按条件查找库位数量</p> 
     * @author foss-zhujunyong
     * @date Oct 16, 2012 6:17:40 PM
     * @param storage
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IStorageDao#countStorageListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.StorageEntity)
     */
    @Override
    public long countStorageListByCondition(StorageEntity storage) {
	storage.setActive(FossConstants.ACTIVE);
	return (Long)getSqlSession().selectOne(NAMESPACE + "countStorageListByCondition", storage);
    }

    /** 
     * <p>查询某一外场下的所有库位列表</p> 
     * @author foss-zhujunyong
     * @date Oct 16, 2012 6:17:40 PM
     * @param organizationCode
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IStorageDao#queryStorageListByOrganizationCode(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<StorageEntity> queryStorageListByOrganizationCode(
	    String organizationCode) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("active", FossConstants.ACTIVE);
	map.put("organizationCode", organizationCode);
	return (List<StorageEntity>)getSqlSession().selectList(NAMESPACE + "queryStorageListByOrganizationCode", map);
    }

    @Override
    public int deleteStorages(List<String> ids, String modifyUser) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("active", FossConstants.ACTIVE);
	map.put("inactive", FossConstants.INACTIVE);
	map.put("modifyDate", new Date());
	map.put("modifyUser", modifyUser);
	map.put("ids", ids);
	return getSqlSession().update(NAMESPACE + "deleteStorages", map);
    }
    
    
    
    @Override
    public Date queryLastModifyTime() {
	Date createDate = (Date) getSqlSession().selectOne(NAMESPACE + "queryLastCreateTime");
	Date modifyDate = (Date) getSqlSession().selectOne(NAMESPACE + "queryLastModifyTime", new Date());
	if (createDate == null || modifyDate == null) {
	    return null;
	}
	return new Date(Math.max(createDate.getTime(), modifyDate.getTime()));
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<StorageEntity> queryStorageListForCache() {
	String active = FossConstants.ACTIVE;
	return (List<StorageEntity>)getSqlSession().selectList(NAMESPACE + "queryStorageListForCache", active);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<StorageEntity> queryStorageListViaDateForCache(Date date) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("active", FossConstants.ACTIVE);
	map.put("now", date);
	return getSqlSession().selectList(NAMESPACE + "queryStorageListViaDateForCache", map);
    }


}
