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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/SiteGroupDao.java
 * 
 * FILE NAME        	: SiteGroupDao.java
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
import com.deppon.foss.module.base.baseinfo.api.server.dao.ISiteGroupDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SiteGroupEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * 站点组Dao接口实现类：对站点组提供增删改查的操作
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-12 下午4:31:20 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-12 下午4:31:20
 * @since
 * @version
 */
public class SiteGroupDao extends SqlSessionDaoSupport implements ISiteGroupDao {
    
    private static final String NAMESPACE = "foss.bse.bse-baseinfo.sitegroup.";
    
    /**
     * 新增站点组 
     * @author 094463-foss-xieyantao
     * @date 2012-10-12 下午4:31:20
     * @param entity 站点组实体
     * @return 1：成功；-1：失败
     * @see
     */
    @Override
    public int addSiteGroup(SiteGroupEntity entity) {
	
	return this.getSqlSession().insert(NAMESPACE + "insert", entity);
    }
    
    /**
     * 根据code作废站点组 
     * @author dp-xieyantao
     * @date 2012-10-12 下午4:31:20
     * @param codes ID字符串数组
     * @param modifyUser
     * @return 1：成功；-1：失败 
     * @see
     */
    @Override
    public int deleteSiteGroupByCode(String[] codes,String modifyUser) {
	
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("codes", codes);
	map.put("modifyUser", modifyUser);
	map.put("modifyDate", new Date());
	map.put("active", FossConstants.INACTIVE);
	map.put("active0", FossConstants.ACTIVE);
	
	return this.getSqlSession().update(NAMESPACE + "deleteByCode", map);
    }
    
    /**
     * 修改站点组 
     * @author dp-xieyantao
     * @date 2012-10-12 下午4:31:20
     * @param entity 站点组实体
     * @return 1：成功；-1：失败
     * @see
     */
    @Override
    public int updateSiteGroup(SiteGroupEntity entity) {
	
	return this.getSqlSession().update(NAMESPACE + "update", entity);
    }
    
    /**
     * 根据传入对象查询符合条件所有站点组信息 
     * @author dp-xieyantao
     * @date 2012-10-12 下午4:31:20
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     * @see
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<SiteGroupEntity> querySiteGroups(SiteGroupEntity entity,
	    int limit, int start) {
	
	RowBounds rowBounds = new RowBounds(start, limit);
	
	return this.getSqlSession().selectList(NAMESPACE + "queryAllInfos", entity,rowBounds);
    }
    
    /**
     * 统计总记录数 
     * @author dp-xieyantao
     * @date 2012-10-12 下午4:31:20
     * @param entity 站点组实体
     * @return
     * @see
     */
    @Override
    public Long queryRecordCount(SiteGroupEntity entity) {
	
	return  (Long)this.getSqlSession().selectOne(NAMESPACE + "queryCount", entity);
    }
    
    /**
     * 根据站点组虚拟编码作废该站点组下所有的站点  
     * @author 094463-foss-xieyantao
     * @date 2012-11-1 上午10:38:21
     * @param codes ID字符串数组
     * @param modifyUser
     * @return 1：成功；-1：失败 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISiteGroupDao#deleteSiteByCode(java.lang.String[], java.lang.String)
     */
    @Override
    public int deleteSiteByCode(String[] codes, String modifyUser) {
	
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("codes", codes);
	map.put("modifyUser", modifyUser);
	map.put("modifyDate", new Date());
	map.put("active", FossConstants.INACTIVE);
	map.put("active0", FossConstants.ACTIVE);
	
	return this.getSqlSession().update(NAMESPACE + "deleteSiteByCode", map);
    }
    
    /**
     * <p>根据站点组虚拟编码查询站点组信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-11-8 下午2:56:24
     * @param siteGroupCode 站点组虚拟编码
     * @return
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISiteGroupDao#querySiteGroupByCode(java.lang.String)
     */
    @Override
    public SiteGroupEntity querySiteGroupByCode(String siteGroupCode) {
	
	Map<String, String> map = new HashMap<String, String>();
	map.put("active", FossConstants.ACTIVE);
	map.put("siteGroupCode", siteGroupCode);
	
	return (SiteGroupEntity)this.getSqlSession().selectOne(NAMESPACE + "querySiteGroupByCode", map);
    }
    
    /**
     * <p>根据站点组站点（外场）的部门编码查询所属的站点组信息</p>  
     * @author 094463-foss-xieyantao
     * @date 2012-12-7 下午1:59:02
     * @param deptCode
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISiteGroupDao#querySiteGroupsBySiteCode(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<SiteGroupEntity> querySiteGroupsBySiteCode(String deptCode) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("active", FossConstants.ACTIVE);
	map.put("deptCode", deptCode);
	
	return this.getSqlSession().selectList(NAMESPACE + "querySiteGroupsBySiteCode", map);
    }
    
    /**
     * <p>验证站点组名称、所属部门编码、站点组类型组合是否唯一</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-12-28 下午2:30:21
     * @param entity 站点组实体
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISiteGroupDao#querySiteGroupIsExist(com.deppon.foss.module.base.baseinfo.api.shared.domain.SiteGroupEntity)
     */
    @Override
    public SiteGroupEntity querySiteGroupIsExist(SiteGroupEntity entity) {
	
	return (SiteGroupEntity)this.getSqlSession().selectOne(NAMESPACE+"querySiteGroupIsExist", entity);
    }

}
