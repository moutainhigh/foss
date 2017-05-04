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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.INetGroupMixDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.NetGroupMixEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 网点组Dao
 * @author foss-zhujunyong
 * @date Oct 25, 2012 11:35:38 AM
 * @version 1.0
 */
public class NetGroupMixDao extends SqlSessionDaoSupport implements INetGroupMixDao {

    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".netGroupMix.";

    /** 
     * <p>添加网点组</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:35:38 AM
     * @param netGroup
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.INetGroupDao#addNetGroup(com.deppon.foss.module.base.baseinfo.api.shared.domain.NetGroupEntity)
     */
    @Override
    public NetGroupMixEntity addNetGroupMix(NetGroupMixEntity netGroup) {
	Date now = new Date();
	netGroup.setId(UUIDUtils.getUUID());
	netGroup.setVirtualCode(netGroup.getId());
	netGroup.setCreateDate(now);
	netGroup.setVersion(now.getTime());
	netGroup.setModifyDate(new Date(NumberConstants.ENDTIME));
	netGroup.setModifyUser(netGroup.getCreateUser());
	netGroup.setActive(FossConstants.ACTIVE);
	int result = getSqlSession().insert(NAMESPACE + "addNetGroupMix", netGroup);
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
    public NetGroupMixEntity deleteNetGroupMix(NetGroupMixEntity netGroup) {
	int result = getSqlSession().update(NAMESPACE + "deleteNetGroupMix", netGroup);
	return result > 0 ? netGroup : null;
    }

    /**
     * 
     * <p>按虚拟编码作废网点组</p> 
     * @author foss-zhujunyong
     * @date Jun 27, 2013 10:56:49 AM
     * @param virtualCode
     * @param modifyUser
     * @return
     * @see
     */
    @Override
    public int deleteNetGroupMixByVirtualCode(String virtualCode, String modifyUser) {
	if (StringUtils.isBlank(virtualCode)) {
	    return FossConstants.FAILURE;
	}
	Date now = new Date();
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("inactive", FossConstants.INACTIVE);
	map.put("modifyDate", now);
	map.put("version", now.getTime());
	map.put("modifyUser", modifyUser);
	map.put("virtualCode", virtualCode);
	map.put("active", FossConstants.ACTIVE);	
	return getSqlSession().update(NAMESPACE + "deleteNetGroupMixByVirtualCode", map);
    }
    
    /**
     * 
     * <p>根据出发营业部编码和出发外场编码找出符合的网点组编码列表</p> 
     * @author foss-zhujunyong
     * @date Jun 27, 2013 11:15:06 AM
     * @param salesCode
     * @param transferCode
     * @return
     * @see
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<String> queryVirtualCodeListBySourceSalesCode(String salesCode, String transferCode,String expNetworkGroup) {
	if (StringUtils.isBlank(salesCode) || StringUtils.isBlank(transferCode)) {
	    return new ArrayList<String> ();
	}
	Map<String, String> map = new HashMap<String, String> ();
	map.put("salesCode", salesCode);
	map.put("transferCode", transferCode);
	map.put("active", FossConstants.ACTIVE);
	map.put("orgType", ComnConst.ORG_TYPE_SOURCE);
	map.put("expNetworkGroup", expNetworkGroup);
	return (List<String>)getSqlSession().selectList(NAMESPACE + "queryVirtualCodeListBySourceSalesCode", map);
    }
    
    /**
     * 
     * <p>根据到达营业部编码和到达外场编码找出符合的网点组编码列表</p> 
     * @author foss-zhujunyong
     * @date Jun 27, 2013 11:15:06 AM
     * @param salesCode
     * @param transferCode
     * @return
     * @see
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<String> queryVirtualCodeListByTargetSalesCode(String salesCode, String transferCode,String expNetworkGroup) {
	if (StringUtils.isBlank(salesCode) || StringUtils.isBlank(transferCode)) {
	    return new ArrayList<String> ();
	}
	Map<String, String> map = new HashMap<String, String> ();
	map.put("salesCode", salesCode);
	map.put("transferCode", transferCode);
	map.put("active", FossConstants.ACTIVE);
	map.put("orgType", ComnConst.ORG_TYPE_TARGET);
	map.put("expNetworkGroup", expNetworkGroup);
	return (List<String>)getSqlSession().selectList(NAMESPACE + "queryVirtualCodeListByTargetSalesCode", map);
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
    public NetGroupMixEntity updateNetGroupMix(NetGroupMixEntity netGroup) {
    	
    Date now = new Date();
    netGroup.setActive(FossConstants.INACTIVE);
    netGroup.setModifyDate(now);
    netGroup.setVersion(now.getTime());
	
	NetGroupMixEntity entity = deleteNetGroupMix(netGroup);
	if (entity == null) {
	    return null;
	}
	
	entity.setId(UUIDUtils.getUUID());
	entity.setCreateDate(entity.getModifyDate());
	entity.setVersion(entity.getModifyDate().getTime());
	entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	entity.setCreateUser(entity.getModifyUser());
	entity.setActive(FossConstants.ACTIVE);
	int result = getSqlSession().insert(NAMESPACE + "addNetGroupMix", entity);
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
    public NetGroupMixEntity queryNetGroupMixById(String id) {
	return (NetGroupMixEntity) getSqlSession().selectOne(NAMESPACE + "queryNetGroupMixById", id);
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
    public int deleteNetGroupMixByCode(NetGroupMixEntity netGroup) {
	Date now = new Date();
	netGroup.setActive(FossConstants.INACTIVE);
	netGroup.setModifyDate(now);
	netGroup.setVersion(now.getTime());
	return getSqlSession().update(NAMESPACE + "deleteNetGroupMixByCode", netGroup);
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
    public List<NetGroupMixEntity> queryNetGroupMixByFreightRoute(
	    String freightRouteVirtualCode) {
	NetGroupMixEntity netGroup = new NetGroupMixEntity();
	netGroup.setFreightRouteVirtualCode(freightRouteVirtualCode);
	netGroup.setActive(FossConstants.ACTIVE);
	return (List<NetGroupMixEntity>) getSqlSession().selectList(NAMESPACE + "queryNetGroupMixByFreightRoute", netGroup);
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
    public List<NetGroupMixEntity> queryNetGroupMixByCode(String netGroupCode) {
	NetGroupMixEntity netGroup = new NetGroupMixEntity();
	netGroup.setActive(FossConstants.ACTIVE);
	netGroup.setNetGroupCode(netGroupCode);
	return (List<NetGroupMixEntity>) getSqlSession().selectList(NAMESPACE + "queryNetGroupMixByCode", netGroup);
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
    public List<NetGroupMixEntity> queryNetGroupMixBySourceTarget(String sourceCode, String targetCode) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("active", FossConstants.ACTIVE);
	map.put("sourceType", ComnConst.ORG_TYPE_SOURCE);
	map.put("sourceCode", sourceCode);
	map.put("targetType", ComnConst.ORG_TYPE_TARGET);
	map.put("targetCode", targetCode);
	return (List<NetGroupMixEntity>) getSqlSession().selectList(NAMESPACE + "queryNetGroupMixBySourceTarget", map);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<NetGroupMixEntity> queryNetGroupMixListForDownload(Map<String, Object> map) {
	return (List<NetGroupMixEntity>) getSqlSession().selectList(NAMESPACE + "queryNetGroupMixListForDownload", map);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<NetGroupMixEntity> queryNetGroupMixListForDownload(Map<String, Object> map, int start, int limited) {
	RowBounds rowBounds = new RowBounds(start, limited);
	return (List<NetGroupMixEntity>) getSqlSession().selectList(NAMESPACE + "queryNetGroupMixListForDownload", map,rowBounds);
    }

    
    
    
    @Override
    public int deleteNetGroupMixByFreightRoute(String freightRouteVirtualCode, String modifyUser) {
	Map<String, Object> map = new HashMap<String, Object>();
	Date now=new Date();
	map.put("active", FossConstants.ACTIVE);
	map.put("inactive", FossConstants.INACTIVE);
	map.put("freightRouteVirtualCode", freightRouteVirtualCode);
	map.put("modifyDate", new Date());
	map.put("modifyUser", modifyUser);
	map.put("version", now.getTime());
	return getSqlSession().update(NAMESPACE + "deleteNetGroupMixByFreightRoute", map);
    }
    
    
    @SuppressWarnings("unchecked")
    @Override
    public List<NetGroupMixEntity> queryNetGroupListForCache() {
	String active = FossConstants.ACTIVE;
	return (List<NetGroupMixEntity>)getSqlSession().selectList(NAMESPACE + "queryNetGroupListForCache", active);
    }
    
    /**
     * 
     * <p>根据网点组虚拟编码和产品编码数组筛选对应的网点组编码</p> 
     * @author foss-张继恒
     * @date 2.19, 2013 11:15:06 AM
     * @param salesCode
     * @param transferCode
     * @return
     * @see
     */
    @SuppressWarnings("unchecked")
    public List<String> queryVirtualCodeListByProductCodeIn(List<String> virtualCodeList,String[] productList){
    	Map<String ,Object> map=new HashMap<String, Object>();
    	map.put("virtualCodeList", virtualCodeList);
    	map.put("productList", productList);
		List<String> selectList = (List<String>)getSqlSession().selectList(NAMESPACE + "queryVirtualCodeListByProductCodeIn", map);
		return selectList;
    	
    }
    
    /**
     * 
     * <p>根据网点组虚拟编码和产品编码数组筛选对应的网点组编码</p> 
     * @author foss-张继恒
     * @date 2.19, 2013 11:15:06 AM
     * @param salesCode
     * @param transferCode
     * @return
     * @see
     */
    @SuppressWarnings("unchecked")
    public List<String> queryVirtualCodeListByProductCodeNotIn(List<String> virtualCodeList,String[] productList){
    	Map<String ,Object> map=new HashMap<String, Object>();
    	map.put("virtualCodeList", virtualCodeList);
    	map.put("productList", productList);
		List<String> selectList = (List<String>)getSqlSession().selectList(NAMESPACE + "queryVirtualCodeListByProductCodeNotIn", map);
		return selectList;
    	
    }

	@Override
	public NetGroupMixEntity queryNetGroupMixByVirtualCode(String virtualCode) {
		return (NetGroupMixEntity) getSqlSession().selectOne(NAMESPACE + "queryNetGroupMixByVirtualCode", virtualCode);
	}
    

    @SuppressWarnings("unchecked")
	@Override
    public List<NetGroupMixEntity> queryNetGroupMix(NetGroupMixEntity netGroup) {
    	return (List<NetGroupMixEntity>)getSqlSession().selectList(NAMESPACE + "queryNetGroupMix", netGroup);

    }
}
