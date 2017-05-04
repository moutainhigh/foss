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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/FreightRouteLineDao.java
 * 
 * FILE NAME        	: FreightRouteLineDao.java
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
import org.apache.ibatis.session.RowBounds;
import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IFreightRouteDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IFreightRouteLineDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISendFreightRouteInfoToWDGHService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteLineEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 走货路径线路Dao
 * @author foss-zhujunyong
 * @date Oct 25, 2012 11:35:38 AM
 * @version 1.0
 */
public class FreightRouteLineDao extends SqlSessionDaoSupport implements IFreightRouteLineDao {
	
    /**
     * 
     * sendFreightRouteInfoToWDGHService
     */
   
    private ISendFreightRouteInfoToWDGHService sendFreightRouteInfoToWDGHService;
    
    /**
     * 
     * freightRouteDao
     */
    
    private IFreightRouteDao freightRouteDao;

    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".freightRouteLine.";
    
	   /**
  * 
  * @author foss-qirongsheng
  * @date Mar 24, 2016 5:37:32 PM
  * @param sendFreightRouteInfoToWDGHService
  * @see
  */
	public void setSendFreightRouteInfoToWDGHService(
			ISendFreightRouteInfoToWDGHService sendFreightRouteInfoToWDGHService) {
		this.sendFreightRouteInfoToWDGHService = sendFreightRouteInfoToWDGHService;
	}

	public void setFreightRouteDao(IFreightRouteDao freightRouteDao) {
		this.freightRouteDao = freightRouteDao;
	}

    /** 
     * <p>添加走货路径线路</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:35:38 AM
     * @param freightRouteLine
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IFreightRouteLineDao#addFreightRouteLine(com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteLineEntity)
     */
    @Override
    public FreightRouteLineEntity addFreightRouteLine(FreightRouteLineEntity freightRouteLine) {
	Date now = new Date();
	freightRouteLine.setId(UUIDUtils.getUUID());
	freightRouteLine.setVirtualCode(freightRouteLine.getId());
	freightRouteLine.setCreateDate(now);
	freightRouteLine.setVersion(now.getTime());
	freightRouteLine.setModifyDate(new Date(NumberConstants.ENDTIME));
	freightRouteLine.setModifyUser(freightRouteLine.getCreateUser());
	freightRouteLine.setActive(FossConstants.ACTIVE);
	int result = getSqlSession().insert(NAMESPACE + "addFreightRouteLine", freightRouteLine);

	return result > 0 ? freightRouteLine : null;
    }

    /** 
     * <p>作废走货路径线路</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:35:38 AM
     * @param freightRouteLine
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IFreightRouteLineDao#deleteFreightRouteLine(com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteLineEntity)
     */
    @Override
    public FreightRouteLineEntity deleteFreightRouteLine(FreightRouteLineEntity freightRouteLine) {
	Date now = new Date();
	freightRouteLine.setActive(FossConstants.INACTIVE);
	freightRouteLine.setModifyDate(now);
	freightRouteLine.setVersion(now.getTime());
	int result = getSqlSession().update(NAMESPACE + "deleteFreightRouteLine", freightRouteLine);
	updateFreightRouteAging(freightRouteLine);
	
	FreightRouteEntity frEntity = freightRouteDao.queryFreightRouteByVirtualCode(freightRouteLine.getFreightRouteVirtualCode());
	//同步更新走货路径到WDGH
	List<FreightRouteEntity> frlist = new ArrayList<FreightRouteEntity>();
	frlist.add(frEntity);
	sendFreightRouteInfoToWDGHService.syncFreightRouteInfo(frlist, (NumberConstants.TWO).toString());
	
	return result > 0 ? freightRouteLine : null;
    }

    /** 
     * <p>作废走货路径线路</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:35:38 AM
     * @param freightRouteLine
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IFreightRouteLineDao#deleteFreightRouteLine(com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteLineEntity)
     */
    @Override
    public FreightRouteLineEntity deleteFreightRouteLines(FreightRouteLineEntity freightRouteLine) {
	Date now = new Date();
	freightRouteLine.setActive(FossConstants.INACTIVE);
	freightRouteLine.setModifyDate(now);
	freightRouteLine.setVersion(now.getTime());
	int result = getSqlSession().update(NAMESPACE + "deleteFreightRouteLine", freightRouteLine);
	
	updateFreightRouteAging(freightRouteLine);
	return result > 0 ? freightRouteLine : null;
    }
    
    /** 
     * <p>更新走货路径线路</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:35:38 AM
     * @param freightRouteLine
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IFreightRouteLineDao#updateFreightRouteLine(com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteLineEntity)
     */
    @Override
    public FreightRouteLineEntity updateFreightRouteLine(FreightRouteLineEntity freightRouteLine) {
	FreightRouteLineEntity entity = deleteFreightRouteLine(freightRouteLine);
	
	if (entity == null) {
	    return null;
	}
	
	entity.setId(UUIDUtils.getUUID());
	entity.setCreateDate(entity.getModifyDate());
	entity.setVersion(entity.getModifyDate().getTime());
	entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	entity.setCreateUser(entity.getModifyUser());
	entity.setActive(FossConstants.ACTIVE);
	int result = getSqlSession().insert(NAMESPACE + "addFreightRouteLine", entity);

	return result > 0 ? entity : null;
    }

    /** 
     * <p>更新走货路径线路</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:35:38 AM
     * @param freightRouteLine
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IFreightRouteLineDao#updateFreightRouteLine(com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteLineEntity)
     */
    @Override
    public FreightRouteLineEntity updateFreightRouteLines(FreightRouteLineEntity freightRouteLine) {
	FreightRouteLineEntity entity = deleteFreightRouteLines(freightRouteLine);
	
	if (entity == null) {
	    return null;
	}
	
	entity.setId(UUIDUtils.getUUID());
	entity.setCreateDate(entity.getModifyDate());
	entity.setVersion(entity.getModifyDate().getTime());
	entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	entity.setCreateUser(entity.getModifyUser());
	entity.setActive(FossConstants.ACTIVE);
	int result = getSqlSession().insert(NAMESPACE + "addFreightRouteLine", entity);

	return result > 0 ? entity : null;
    }
    
    /** 
     * <p>根据id查询走货路径线路</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:35:39 AM
     * @param id
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IFreightRouteFreightRouteLineDao#queryFreightRouteLineById(java.lang.String)
     */
    @Override
    public FreightRouteLineEntity queryFreightRouteLineById(String id) {
	return (FreightRouteLineEntity) getSqlSession().selectOne(NAMESPACE + "queryFreightRouteLineById", id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<FreightRouteLineEntity> queryFreightRouteLineForDownload(Map<String, Object> map) {
	return (List<FreightRouteLineEntity>) getSqlSession().selectList(NAMESPACE + "queryFreightRouteLineForDownload", map);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<FreightRouteLineEntity> queryFreightRouteLineListByFreightRoute(FreightRouteLineEntity freightRouteLine) {
	freightRouteLine.setActive(FossConstants.ACTIVE);
	return (List<FreightRouteLineEntity>) getSqlSession().selectList(NAMESPACE + "queryFreightRouteLineListByFreightRoute", freightRouteLine);
    }

    @Override
    public int deleteFreightRouteLineByFreightRoute(String freightRouteVirtualCode, String modifyUser) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("active", FossConstants.ACTIVE);
	map.put("inactive", FossConstants.INACTIVE);
	map.put("freightRouteVirtualCode", freightRouteVirtualCode);
	map.put("modifyDate", new Date());
	map.put("modifyUser", modifyUser);
	map.put("version",new Date().getTime());	//zxy 20140327 MANA-2018 设置版本号
	int result = getSqlSession().update(NAMESPACE + "deleteFreightRouteLineByFreightRoute", map);
	// 更新走货路径时效
	FreightRouteLineEntity entity = new FreightRouteLineEntity();
	entity.setFreightRouteVirtualCode(freightRouteVirtualCode);
	entity.setActive(FossConstants.ACTIVE);
	//更新走货路径时效
	updateFreightRouteAging(entity);
	FreightRouteEntity frEntity = freightRouteDao.queryFreightRouteByVirtualCode(entity.getFreightRouteVirtualCode());
	//同步更新走货路径时效到WDGH
	if(null != frEntity){
		List<FreightRouteEntity> frlist = new ArrayList<FreightRouteEntity>();
		frlist.add(frEntity);
		sendFreightRouteInfoToWDGHService.syncFreightRouteInfo(frlist,NumberConstants.TWO.toString());
	}
	return result;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<FreightRouteLineEntity> queryFreightRouteLineListByLine(String lineVirtualCode) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("lineVirtualCode", lineVirtualCode);
	map.put("active", FossConstants.YES);
	return (List<FreightRouteLineEntity>)getSqlSession().selectList(NAMESPACE + "queryFreightRouteLineListByLine", map);
    }

    /**
     * 
     * <p>更新走货路径中的冗余字段：时效</p> 
     * @author foss-zhujunyong
     * @date Dec 19, 2012 9:48:57 AM
     * @see
     */
    @Override
    public FreightRouteEntity updateFreightRouteAging(FreightRouteLineEntity entity) {
	entity.setActive(FossConstants.ACTIVE);
	Long aging = (Long)getSqlSession().selectOne(NAMESPACE + "sumAgingByFreightRouteVirtualCode", entity);
	
	FreightRouteEntity freightRoute = new FreightRouteEntity();
	freightRoute.setAging(aging);
	freightRoute.setVirtualCode(entity.getFreightRouteVirtualCode());
	freightRoute.setActive(FossConstants.ACTIVE);
	freightRoute.setVersion(new Date().getTime());			//zxy 20140327 MANA-2018 设置版本号
	freightRoute.setModifyDate(new Date());
	freightRoute.setModifyUser(FossUserContext.getCurrentInfo().getEmpCode());
	int result = getSqlSession().update(NAMESPACE + "updateFreightRouteAging", freightRoute);
	
	return result > 0 ? freightRoute : null;
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
    public List<FreightRouteLineEntity> queryFreightRouteLineListForCache() {
	String active = FossConstants.ACTIVE;
	return (List<FreightRouteLineEntity>)getSqlSession().selectList(NAMESPACE + "queryFreightRouteLineListForCache", active);
    }

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IFreightRouteLineDao#queryFreightRouteLineForDownloadByPage(java.util.Map, int, int)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public  List<FreightRouteLineEntity> queryFreightRouteLineForDownloadByPage(
			Map<String, Object> map, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return (List<FreightRouteLineEntity>) getSqlSession().selectList(NAMESPACE + "queryFreightRouteLineForDownload", map, rowBounds);
		   
	}

    
}
