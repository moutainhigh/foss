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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/PlatformDao.java
 * 
 * FILE NAME        	: PlatformDao.java
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

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IPlatformDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PlatformEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 月台dao
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:zhujunyong,date:Oct 12, 2012 11:22:00 AM</p>
 * @author zhujunyong
 * @date Oct 12, 2012 11:22:00 AM
 * @since
 * @version
 */
public class PlatformDao extends SqlSessionDaoSupport implements IPlatformDao {

    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".platform.";
    
    /** 
     * <p>添加月台</p> 
     * @author zhujunyong
     * @date Oct 12, 2012 11:22:01 AM
     * @param platform
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPlatformDao#addPlatform(com.deppon.foss.module.base.baseinfo.api.shared.domain.PlatformEntity)
     */
    @Override
    public PlatformEntity addPlatform(PlatformEntity platform) {
	platform.setId(UUIDUtils.getUUID());
	platform.setVirtualCode(platform.getId());
	platform.setCreateDate(new Date());
	platform.setModifyDate(new Date(NumberConstants.ENDTIME));
	platform.setModifyUser(platform.getCreateUser());
	platform.setActive(FossConstants.ACTIVE);
	int result = getSqlSession().insert(NAMESPACE + "addPlatform", platform);
	return result > 0 ? platform : null;
    }

    /** 
     * <p>删除月台</p> 
     * @author zhujunyong
     * @date Oct 12, 2012 11:22:01 AM
     * @param platform
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPlatformDao#deletePlatform(com.deppon.foss.module.base.baseinfo.api.shared.domain.PlatformEntity)
     */
    @Override
    public PlatformEntity deletePlatform(PlatformEntity platform) {
	platform.setActive(FossConstants.INACTIVE);
	platform.setModifyDate(new Date());
	int result = getSqlSession().update(NAMESPACE + "deletePlatform", platform); 
	return result > 0 ? platform : null;
    }

    /** 
     * <p>更新月台</p> 
     * @author zhujunyong
     * @date Oct 12, 2012 11:22:01 AM
     * @param platform
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPlatformDao#updatePlatform(com.deppon.foss.module.base.baseinfo.api.shared.domain.PlatformEntity)
     */
    @Override
    public PlatformEntity updatePlatform(PlatformEntity platform) {
	PlatformEntity entity = deletePlatform(platform);
	if (entity == null) {
	    return null;
	}
	
	platform.setId(UUIDUtils.getUUID());
	platform.setCreateDate(entity.getModifyDate());
	platform.setModifyDate(new Date(NumberConstants.ENDTIME));
	platform.setCreateUser(entity.getModifyUser());
	platform.setActive(FossConstants.ACTIVE);
	int result = getSqlSession().insert(NAMESPACE + "addPlatform", platform);
	return result > 0 ? entity : null;
    }

    /** 
     * <p>查询单个月台</p> 
     * @author zhujunyong
     * @date Oct 12, 2012 11:22:01 AM
     * @param virtualCode
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPlatformDao#queryPlatformByVirtualCode(java.lang.String)
     */
    @Override
    public PlatformEntity queryPlatformByVirtualCode(String virtualCode) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("active", FossConstants.ACTIVE);
	map.put("virtualCode", virtualCode);
	return (PlatformEntity)getSqlSession().selectOne(NAMESPACE + "queryPlatformByVirtualCode", map);
    }

    /** 
     * <p>按条件查找月台列表</p> 
     * @author zhujunyong
     * @date Oct 12, 2012 11:22:01 AM
     * @param platform
     * @param start
     * @param limit
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPlatformDao#queryPlatformListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.PlatformEntity, int, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<PlatformEntity> queryPlatformListByCondition(
	    PlatformEntity platform, int start, int limit) {
	platform.setActive(FossConstants.ACTIVE);
	return (List<PlatformEntity>)getSqlSession().selectList(NAMESPACE + "queryPlatformListByCondition", platform, new RowBounds(start, limit));
    }

    /** 
     * <p>按条件查找月台数量</p> 
     * @author zhujunyong
     * @date Oct 12, 2012 11:22:01 AM
     * @param platform
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPlatformDao#countPlatformListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.PlatformEntity)
     */
    @Override
    public long countPlatformListByCondition(PlatformEntity platform) {
	platform.setActive(FossConstants.ACTIVE);
	return (Long)getSqlSession().selectOne(NAMESPACE + "countPlatformListByCondition", platform);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PlatformEntity> queryPlatformListByOrgCodeAndPlatformCodeLimit(
	    String organizationCode, String startCode, String endCode) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("active", FossConstants.ACTIVE);
	map.put("organizationCode", organizationCode);
	map.put("startCode", startCode);
	map.put("endCode", endCode);
	return (List<PlatformEntity>)getSqlSession().selectList(NAMESPACE + "queryPlatformListByOrgCodeAndPlatformCodeLimit", map);
    }

    @Override
    public PlatformEntity queryPlatformByCode(String organizationCode,
	    String platformCode) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("active", FossConstants.ACTIVE);
	map.put("organizationCode", organizationCode);
	map.put("platformCode", platformCode);
	return (PlatformEntity) getSqlSession().selectOne(NAMESPACE + "queryPlatformByCode", map);
    }

    @Override
    public int deletePlatforms(List<String> virtualCodes, String modifyUser) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("active", FossConstants.ACTIVE);
	map.put("inactive", FossConstants.INACTIVE);
	map.put("modifyDate", new Date());
	map.put("modifyUser", modifyUser);
	map.put("virtualCodes", virtualCodes);
	return getSqlSession().update(NAMESPACE + "deletePlatforms", map);
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
    public List<PlatformEntity> queryPlatformListForCache() {
	String active = FossConstants.ACTIVE;
	return (List<PlatformEntity>)getSqlSession().selectList(NAMESPACE + "queryPlatformListForCache", active);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<PlatformEntity> queryPlatformListViaDateForCache(Date date) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("active", FossConstants.ACTIVE);
	map.put("now", date);
	return getSqlSession().selectList(NAMESPACE + "queryPlatformListViaDateForCache", map);
    }

    /**
     * 
     * <p>
     * 为导出方法取月台列表(包括取各种名称冗余属性)
     * </p>
     * 
     * @author foss-zhujunyong
     * @date Mar 7, 2013 1:56:20 PM
     * @param platform
     * @param start
     * @param limit
     * @return
     * @see
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<PlatformEntity> queryPlatformListForExport(PlatformEntity platform, int start, int limit) {
	platform.setActive(FossConstants.ACTIVE);
	return (List<PlatformEntity>) getSqlSession().selectList(NAMESPACE + "queryPlatformListForExport", platform, new RowBounds(start, limit));
    }

	@Override
	public List<PlatformEntity> queryPlatformListForExportByVehicleType2(
			PlatformEntity platform, int start, int limit) {
		platform.setActive(FossConstants.ACTIVE);
			if(StringUtils.equals(platform.getLongDistance(), FossConstants.INACTIVE)){
				platform.setLongDistance(null);
			}
			if(StringUtils.equals(platform.getShortDistance(), FossConstants.INACTIVE)){
				platform.setShortDistance(null);
			}
			if(StringUtils.equals(platform.getPkp(), FossConstants.INACTIVE)){
				platform.setPkp(null);
			}
			return (List<PlatformEntity>) getSqlSession().selectList(NAMESPACE + "queryPlatformListForExportByVehicleType2", platform, new RowBounds(start, limit));
	    }
    
}
