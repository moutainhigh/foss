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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/SiteGroupSiteDao.java
 * 
 * FILE NAME        	: SiteGroupSiteDao.java
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

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ISiteGroupSiteDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SiteGroupSiteEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * 站点组站点Dao接口实现类：可以对站点组站点提供增删改查的操作
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-12 下午4:41:12 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-12 下午4:41:12
 * @since
 * @version
 */
public class SiteGroupSiteDao extends SqlSessionDaoSupport implements
	ISiteGroupSiteDao {
    
    private static final String NAMESPACE = "foss.bse.bse-baseinfo.sitegroupsite.";
    
    /**
     * 新增站点组站点 
     * @author 094463-foss-xieyantao
     * @date 2012-10-12 下午4:41:12
     * @param entity 站点组站点实体
     * @return 1：成功；-1：失败
     * @see
     */
    @Override
    public int addSiteGroupSite(SiteGroupSiteEntity entity) {
	
	return this.getSqlSession().insert(NAMESPACE + "insert", entity);
    }
    
    /**
     * 根据code作废站点组站点信息 
     * @author 094463-foss-xieyantao
     * @date 2012-10-12 下午4:41:12
     * @param codes ID字符串数组
     * @param modifyUser
     * @return 1：成功；-1：失败 
     * @see
     */
    @Override
    public int deleteSiteGroupSiteByCode(String[] codes, String modifyUser) {
	
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("codes", codes);
	map.put("modifyUser", modifyUser);
	map.put("modifyDate", new Date());
	map.put("active", FossConstants.INACTIVE);
	map.put("active0", FossConstants.ACTIVE);
	
	return this.getSqlSession().update(NAMESPACE + "deleteByCode", map);
    }
    
    /**
     * 修改站点组站点信息 
     * @author 094463-foss-xieyantao
     * @date 2012-10-12 下午4:41:12
     * @param entity 站点组站点实体
     * @return 1：成功；-1：失败
     * @see
     */
    @Override
    public int updateSiteGroupSite(SiteGroupSiteEntity entity) {
	
	return this.getSqlSession().update(NAMESPACE + "update", entity);
    }
    
    /**
     * 根据传入对象查询符合条件所有站点组站点信息 
     * @author 094463-foss-xieyantao
     * @date 2012-10-12 下午4:41:12
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     * @see
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<SiteGroupSiteEntity> querySiteGroupSites(
	    SiteGroupSiteEntity entity, int limit, int start) {
	
	RowBounds rowBounds = new RowBounds(start, limit);
	
	return this.getSqlSession().selectList(NAMESPACE + "queryAllInfos", entity,rowBounds);
    }
    
    /**
     * 统计总记录数 
     * @author 094463-foss-xieyantao
     * @date 2012-10-12 下午4:41:12
     * @param entity 站点组站点实体
     * @return
     * @see
     */
    @Override
    public Long queryRecordCount(SiteGroupSiteEntity entity) {
	
	return  (Long)this.getSqlSession().selectOne(NAMESPACE + "queryCount", entity);
    }
    
    /**
     * <p>根据站点组虚拟编码查询所有站点</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-11-8 下午2:32:13
     * @param siteGroupCode 站点组虚拟编码
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISiteGroupSiteDao#queryAllSitesByCode(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<SiteGroupSiteEntity> queryAllSitesByCode(String siteGroupCode) {
	
	Map<String, String> map = new HashMap<String, String>();
	map.put("active", FossConstants.ACTIVE);
	map.put("siteGroupCode", siteGroupCode);
	
	return this.getSqlSession().selectList(NAMESPACE + "queryAllSitesByCode", map);
    }
    
    /**
     * <p>验证所属站点组虚拟编码、站点序号是否唯一</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-12-28 上午11:05:08
     * @param entity
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISiteGroupSiteDao#queryGroupSiteBySeqVirtualCode(com.deppon.foss.module.base.baseinfo.api.shared.domain.SiteGroupSiteEntity)
     */
    @Override
    public SiteGroupSiteEntity queryGroupSiteBySeqVirtualCode(
	    SiteGroupSiteEntity entity) {
	
	return (SiteGroupSiteEntity)this.getSqlSession().selectOne(NAMESPACE+"queryGroupSiteBySeqVirtualCode", entity);
    }
    
    /**
     * <p>验证所属站点组站点是否重复</p> 
     * @author 132599-foss-shenweihua
     * @date 2013-06-04 上午11:05:08
     * @param entity
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISiteGroupSiteDao#queryGroupSiteBySeqVirtualCode(com.deppon.foss.module.base.baseinfo.api.shared.domain.SiteGroupSiteEntity)
     */
    @Override
    public SiteGroupSiteEntity queryGroupSiteBySiteCode(
	    SiteGroupSiteEntity entity) {
	
	return (SiteGroupSiteEntity)this.getSqlSession().selectOne(NAMESPACE+"queryGroupSiteBySiteCode", entity);
    }

}
