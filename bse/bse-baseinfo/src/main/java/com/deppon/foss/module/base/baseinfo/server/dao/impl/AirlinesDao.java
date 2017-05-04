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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/AirlinesDao.java
 * 
 * FILE NAME        	: AirlinesDao.java
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
import com.deppon.foss.module.base.baseinfo.api.server.dao.IAirlinesDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * 航空公司Dao接口实现：提供对航空公司的增删改查的基本操作
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-15 下午2:32:23 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-15 下午2:32:23
 * @since
 * @version
 */
public class AirlinesDao extends SqlSessionDaoSupport implements IAirlinesDao {
    
    private static final String NAMESPACE = "foss.bse.bse-baseinfo.airlines.";
    
    /**
     * 新增航空公司  
     * @author 094463-foss-xieyantao
     * @date 2012-10-15 下午2:37:26
     * @param entity 航空公司实体
     * @return 1：成功；-1：失败
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAirlinesDao#addAirlines(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesEntity)
     */
    @Override
    public int addAirlines(AirlinesEntity entity) {
	
	return this.getSqlSession().insert(NAMESPACE + "insert", entity);
    }
    
    /**
     * 根据code作废航空公司 
     * @author 094463-foss-xieyantao
     * @date 2012-10-15 下午2:37:33
     * @param codes code字符串数组
     * @return 1：成功；-1：失败 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAirlinesDao#deleteAirlinesByCode(java.lang.String[])
     */
    @Override
    public int deleteAirlinesByCode(String[] codes,String modifyUser) {
	
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("codes", codes);
	map.put("modifyUser", modifyUser);
	map.put("modifyDate", new Date());
	map.put("active", FossConstants.INACTIVE);
	map.put("active0", FossConstants.ACTIVE);
	
	return this.getSqlSession().update(NAMESPACE + "deleteByCode", map);
    }
    
    /**
     * 修改航空公司  
     * @author 094463-foss-xieyantao
     * @date 2012-10-15 下午2:37:42
     * @param entity 航空公司实体
     * @return 1：成功；-1：失败
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAirlinesDao#updateAirlines(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesEntity)
     */
    @Override
    public int updateAirlines(AirlinesEntity entity) {
	
	return this.getSqlSession().update(NAMESPACE + "update", entity);
    }
    
    /**
     * 根据传入对象查询符合条件所有航空公司信息 
     * @author 094463-foss-xieyantao
     * @date 2012-10-15 下午2:37:48
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAirlinesDao#queryAirlines(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesEntity, int, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<AirlinesEntity> queryAirlines(AirlinesEntity entity, int limit,
	    int start) {
	
	RowBounds rowBounds = new RowBounds(start, limit);
	
	return this.getSqlSession().selectList(NAMESPACE + "getAllInfos", entity,rowBounds);
    }
    
    /**
     * 统计总记录数 
     * @author 094463-foss-xieyantao
     * @date 2012-10-15 下午2:37:54
     * @param entity 航空公司实体
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAirlinesDao#getCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesEntity)
     */
    @Override
    public Long queryRecordCount(AirlinesEntity entity) {
	
	return  (Long)this.getSqlSession().selectOne(NAMESPACE + "getCount", entity);
    }
    
    /**
     * 根据航空公司编码查询公司是否存在 
     * @author 094463-foss-xieyantao
     * @date 2012-10-30 上午11:35:49
     * @param airlineCode 航空公司编码
     * @return
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAirlinesDao#queryAirlineByCode(java.lang.String)
     */
    @Override
    public AirlinesEntity queryAirlineByCode(String airlineCode) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("airlineCode", airlineCode);
	map.put("active", FossConstants.ACTIVE);
	
	return (AirlinesEntity)this.getSqlSession().selectOne(NAMESPACE + "queryAirline", map);
    }
    
    /**
     * 根据航空公司名称查询公司是否存在 
     * @author 094463-foss-xieyantao
     * @date 2012-10-30 上午11:35:49
     * @param airlineCode 航空公司名称
     * @return
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAirlinesDao#queryAirlineByName(java.lang.String)
     */
    @Override
    public AirlinesEntity queryAirlineByName(String airlineName) {
	
	Map<String, String> map = new HashMap<String, String>();
	map.put("airlineName", airlineName);
	map.put("active", FossConstants.ACTIVE);
	
	return (AirlinesEntity)this.getSqlSession().selectOne(NAMESPACE + "queryAirline", map);
    }
    
    /**
     * 根据航空公司简称查询公司是否存在 
     * @author 094463-foss-xieyantao
     * @date 2012-10-30 上午11:35:49
     * @param airlineCode 航空公司简称
     * @return
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAirlinesDao#queryAirlineBySimpleName(java.lang.String)
     */
    @Override
    public AirlinesEntity queryAirlineBySimpleName(String simpleName) {
	
	Map<String, String> map = new HashMap<String, String>();
	map.put("simpleName", simpleName);
	map.put("active", FossConstants.ACTIVE);
	
	return (AirlinesEntity)this.getSqlSession().selectOne(NAMESPACE + "queryAirline", map);
    }
    
    /**
     * <p>查询所有有效航空公司信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-11-28 下午4:21:48
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAirlinesDao#queryAllAirlines()
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<AirlinesEntity> queryAllAirlines() {
	Map<String, String> map = new HashMap<String, String>();
	map.put("active", FossConstants.ACTIVE);
	
	return this.getSqlSession().selectList(NAMESPACE + "queryAllAirlines",map);
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<AirlinesEntity> queryAirlinesById(String[] codes) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codes", codes);
		return getSqlSession().selectList(NAMESPACE + "queryAirlinesById", map);
	}

}
