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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IPlatAreaDistanceDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PlatAreaDistanceEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 库区月台距离Dao实现类
 * @author zxt
 * @date 2014-04-24
 * @version 1.0
 */
public class PlatAreaDistanceDao extends SqlSessionDaoSupport implements
	IPlatAreaDistanceDao {
    
    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".platAreaDistance.";
    
    @Override
    public PlatAreaDistanceEntity addDistance(PlatAreaDistanceEntity entity) {
	entity.setId(UUIDUtils.getUUID());
	entity.setCreateDate(new Date());
	entity.setModifyDate(entity.getCreateDate());
	entity.setModifyUser(entity.getCreateUser());
	entity.setActive(FossConstants.ACTIVE);
	int result = getSqlSession().insert(NAMESPACE + "addDistance", entity);
	return result > 0 ? entity : null;
    }

    @Override
    public int deleteDistance(PlatAreaDistanceEntity entity) {
	return getSqlSession().delete(NAMESPACE + "deleteDistance", entity);
    }

    @Override
    public int deleteDistanceByGoodsArea(String virtualCode) {
	return getSqlSession().delete(NAMESPACE + "deleteDistanceByGoodsArea", virtualCode);
    }

    @Override
    public int deleteDistanceByPlatform(String virtualCode) {
	return getSqlSession().delete(NAMESPACE + "deleteDistanceByPlatform", virtualCode);
    }

    @Override
    public PlatAreaDistanceEntity updateDistance(PlatAreaDistanceEntity entity) {
	entity.setModifyDate(new Date());
	entity.setActive(FossConstants.ACTIVE);
	int result = getSqlSession().update(NAMESPACE + "updateDistance", entity);
	return result > 0 ? entity : null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PlatAreaDistanceEntity> queryDistanceByGoodsArea(
	    String virtualCode) {
	return (List<PlatAreaDistanceEntity>) getSqlSession().selectList(NAMESPACE + "queryDistanceByGoodsArea", virtualCode);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PlatAreaDistanceEntity> queryDistanceByPlatform(
	    String virtualCode) {
	return (List<PlatAreaDistanceEntity>) getSqlSession().selectList(NAMESPACE + "queryDistanceByPlatform", virtualCode);
    }

    @SuppressWarnings("unchecked")
    public List<PlatAreaDistanceEntity> queryDistanceListByOrganizationCode(String organizationCode) {
	if (StringUtils.isBlank(organizationCode)) {
	    return new ArrayList<PlatAreaDistanceEntity> ();
	}
	return (List<PlatAreaDistanceEntity>) getSqlSession().selectList(NAMESPACE + "queryDistanceListByOrganizationCode", organizationCode);
    }

    @SuppressWarnings("unchecked")
	@Override
	public PlatAreaDistanceEntity queryDistance(String goodsAreaVirtualCode,
			String platformVirtualCode) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("goodsAreaVirtualCode", goodsAreaVirtualCode);
		map.put("platformVirtualCode", platformVirtualCode);
		List<PlatAreaDistanceEntity> resultList = (List<PlatAreaDistanceEntity>) getSqlSession().selectList(NAMESPACE + "queryDistance", map);
		if(resultList != null && resultList.size() == 1) {
			return resultList.get(0);
		} else {
			return null ;
		}
	}
    
}
