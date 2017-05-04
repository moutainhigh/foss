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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/NetGroupDao.java
 * 
 * FILE NAME        	: NetGroupDao.java
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
import org.springframework.util.CollectionUtils;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.INetGroupDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.NetGroupEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 网点组Dao
 * @author foss-zhujunyong
 * @date Oct 25, 2012 11:35:38 AM
 * @version 1.0
 */
public class NetGroupDao extends SqlSessionDaoSupport implements INetGroupDao {

    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".netGroup.";

    /** 
     * <p>添加网点组</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:35:38 AM
     * @param netGroup
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.INetGroupDao#addNetGroup(com.deppon.foss.module.base.baseinfo.api.shared.domain.NetGroupEntity)
     */
    @Override
    public NetGroupEntity addNetGroup(NetGroupEntity netGroup) {
	Date now = new Date();
	netGroup.setId(UUIDUtils.getUUID());
	netGroup.setVirtualCode(netGroup.getId());
	netGroup.setCreateDate(now);
	netGroup.setVersion(now.getTime());
	netGroup.setModifyDate(new Date(NumberConstants.ENDTIME));
	netGroup.setModifyUser(netGroup.getCreateUser());
	netGroup.setActive(FossConstants.ACTIVE);
	int result = getSqlSession().insert(NAMESPACE + "addNetGroup", netGroup);
	return result > 0 ? netGroup : null;
    }

    /** 
     * <p>作废网点组</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:35:38 AM
     * @param netGroup
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.INetGroupDao#deleteNetGroup(com.deppon.foss.module.base.baseinfo.api.shared.domain.NetGroupEntity)
     */
    @Override
    public NetGroupEntity deleteNetGroup(NetGroupEntity netGroup) {
	Date now = new Date();
	netGroup.setActive(FossConstants.INACTIVE);
	netGroup.setModifyDate(now);
	netGroup.setVersion(now.getTime());
	int result = getSqlSession().update(NAMESPACE + "deleteNetGroup", netGroup);
	return result > 0 ? netGroup : null;
    }

    /** 
     * <p>更新网点组</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:35:38 AM
     * @param netGroup
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.INetGroupDao#updateNetGroup(com.deppon.foss.module.base.baseinfo.api.shared.domain.NetGroupEntity)
     */
    @Override
    public NetGroupEntity updateNetGroup(NetGroupEntity netGroup) {
	NetGroupEntity entity = deleteNetGroup(netGroup);
	if (entity == null) {
	    return null;
	}
	
	entity.setId(UUIDUtils.getUUID());
	entity.setCreateDate(entity.getModifyDate());
	entity.setVersion(entity.getModifyDate().getTime());
	entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	entity.setCreateUser(entity.getModifyUser());
	entity.setActive(FossConstants.ACTIVE);
	int result = getSqlSession().insert(NAMESPACE + "addNetGroup", entity);
	return result > 0 ? entity : null;
    }

    /** 
     * <p>根据id查询网点组</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:35:39 AM
     * @param id
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.INetGroupDao#queryNetGroupById(java.lang.String)
     */
    @Override
    public NetGroupEntity queryNetGroupById(String id) {
	return (NetGroupEntity) getSqlSession().selectOne(NAMESPACE + "queryNetGroupById", id);
    }

    /** 
     * <p>根据走货路径，出发营业部和到达营业部查询网点组记录</p> 
     * @author foss-zhujunyong
     * @date Nov 4, 2012 12:45:44 PM
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.INetGroupDao#queryNetGroupBySourceTarget(java.lang.String, java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public NetGroupEntity queryNetGroupBySourceTargetFreightRoute(NetGroupEntity netGroup) {
	netGroup.setActive(FossConstants.ACTIVE);
	List<NetGroupEntity> list = (List<NetGroupEntity>) getSqlSession().selectList(NAMESPACE + "queryNetGroupBySourceTargetFreightRoute", netGroup, new RowBounds(0, 1));
	return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    /** 
     * <p>根据网点组名称作废一批网点组实体</p> 
     * @author foss-zhujunyong
     * @date Nov 4, 2012 1:21:47 PM
     * @param netGroup
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.INetGroupDao#deleteNetGroupByName(com.deppon.foss.module.base.baseinfo.api.shared.domain.NetGroupEntity)
     */
    @Override
    public int deleteNetGroupByName(NetGroupEntity netGroup) {
	Date now = new Date();
	netGroup.setActive(FossConstants.INACTIVE);
	netGroup.setModifyDate(now);
	return getSqlSession().update(NAMESPACE + "deleteNetGroupByName", netGroup);
    }

    /** 
     * <p>根据走货路径，出发营业部和到达营业部查询网点组记录</p> 
     * @author foss-zhujunyong
     * @date Nov 4, 2012 1:59:21 PM
     * @param netGroup
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.INetGroupDao#queryNetGroupByFreightRoute(com.deppon.foss.module.base.baseinfo.api.shared.domain.NetGroupEntity)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<NetGroupEntity> queryNetGroupByFreightRoute(
	    String freightRouteVirtualCode) {
	NetGroupEntity netGroup = new NetGroupEntity();
	netGroup.setFreightRouteVirtualCode(freightRouteVirtualCode);
	netGroup.setActive(FossConstants.ACTIVE);
	return (List<NetGroupEntity>) getSqlSession().selectList(NAMESPACE + "queryNetGroupByFreightRoute", netGroup);
    }

    /** 
     * <p>查询符合名称的网点组实体</p> 
     * @author foss-zhujunyong
     * @date Nov 4, 2012 3:25:37 PM
     * @param netGroup
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.INetGroupDao#queryNetGroupByName(com.deppon.foss.module.base.baseinfo.api.shared.domain.NetGroupEntity)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<NetGroupEntity> queryNetGroupByName(String netGroupName) {
	NetGroupEntity netGroup = new NetGroupEntity();
	netGroup.setActive(FossConstants.ACTIVE);
	netGroup.setNetGroupName(netGroupName);
	return (List<NetGroupEntity>) getSqlSession().selectList(NAMESPACE + "queryNetGroupByName", netGroup);
    }

    /** 
     * <p>根据出发营业部和到达营业部查询网点组记录</p> 
     * @author foss-zhujunyong
     * @date Nov 4, 2012 4:41:50 PM
     * @param netGroup
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.INetGroupDao#queryNetGroupBySourceTarget(com.deppon.foss.module.base.baseinfo.api.shared.domain.NetGroupEntity)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<NetGroupEntity> queryNetGroupBySourceTarget(
	    NetGroupEntity netGroup) {
	return (List<NetGroupEntity>) getSqlSession().selectList(NAMESPACE + "queryNetGroupBySourceTarget", netGroup);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<NetGroupEntity> queryNetGroupListForDownload(NetGroupEntity netGroup) {
	return (List<NetGroupEntity>) getSqlSession().selectList(NAMESPACE + "queryNetGroupListForDownload", netGroup);
    }

    @Override
    public int deleteNetGroupByFreightRoute(String freightRouteVirtualCode,
	    String modifyUser) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("active", FossConstants.ACTIVE);
	map.put("inactive", FossConstants.INACTIVE);
	map.put("freightRouteVirtualCode", freightRouteVirtualCode);
	map.put("modifyDate", new Date());
	map.put("modifyUser", modifyUser);
	return getSqlSession().update(NAMESPACE + "deleteNetGroupByFreightRoute", map);
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
    public List<NetGroupEntity> queryNetGroupListForCache() {
	String active = FossConstants.ACTIVE;
	return (List<NetGroupEntity>)getSqlSession().selectList(NAMESPACE + "queryNetGroupListForCache", active);
    }
    
}
