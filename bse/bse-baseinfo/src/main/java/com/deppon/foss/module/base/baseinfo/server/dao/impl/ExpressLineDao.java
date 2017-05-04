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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/ExpressLineDao.java
 * 
 * FILE NAME        	: ExpressLineDao.java
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
import com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressLineDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressLineEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 快递线路Dao
 * @author foss-zhujunyong
 * @date Oct 25, 2012 11:35:38 AM
 * @version 1.0
 */
public class ExpressLineDao extends SqlSessionDaoSupport implements IExpressLineDao {


    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".expressline.";

    /** 
     * <p>生效或失效线路</p> 
     * @author foss-zhujunyong
     * @date Jan 16, 2013 7:57:48 PM
     * @param line
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILineDao#validLine(com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity)
     */
    @Override
    public int validLine(ExpressLineEntity line) {
	Date now = new Date();
	line.setModifyDate(now);
	line.setVersion(now.getTime());
	return getSqlSession().update(NAMESPACE + "validExpressLine",line);
    }

    /** 
     * <p>添加线路</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:35:38 AM
     * @param line
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILineDao#addLine(com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity)
     */
    @Override
    public ExpressLineEntity addLine(ExpressLineEntity line) {
    
	Date now = new Date();
	line.setId(UUIDUtils.getUUID());
	line.setVirtualCode(line.getId());
	line.setCreateDate(now);
	line.setModifyDate(new Date(NumberConstants.ENDTIME));
	line.setModifyUser(line.getCreateUser());
	line.setVersion(now.getTime());
	line.setActive(FossConstants.ACTIVE);
	int result = getSqlSession().insert(NAMESPACE + "addExpressLine", line);
	return result > 0 ? line : null;
    }

    /** 
     * <p>作废线路</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:35:38 AM
     * @param line
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILineDao#deleteLine(com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity)
     */
    @Override
    public ExpressLineEntity deleteLine(ExpressLineEntity line) {
	Date now = new Date();
	line.setActive(FossConstants.INACTIVE);
	line.setModifyDate(now);
	line.setVersion(now.getTime());
	int result = getSqlSession().update(NAMESPACE + "deleteExpressLine", line);
	return result > 0 ? line : null;
    }

    
    /** 
     * <p>更新线路</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:35:38 AM
     * @param line
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILineDao#updateLine(com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity)
     */
    @Override
    public ExpressLineEntity updateLine(ExpressLineEntity line) {
    	ExpressLineEntity entity = deleteLine(line);
	if (entity == null) {
	    return null;
	}
	
	entity.setId(UUIDUtils.getUUID());
	entity.setCreateDate(entity.getModifyDate());
	entity.setVersion(entity.getModifyDate().getTime());
	entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	entity.setCreateUser(entity.getModifyUser());
	entity.setActive(FossConstants.ACTIVE);
	int result = getSqlSession().insert(NAMESPACE + "addExpressLine", entity);
	return result > 0 ? entity : null;
    }

    /** 
     * <p>根据id查询线路</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:35:39 AM
     * @param id
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILineDao#queryLineById(java.lang.String)
     */
    @Override
    public ExpressLineEntity queryLineById(String id) {
	return (ExpressLineEntity) getSqlSession().selectOne(NAMESPACE + "queryExpressLineById", id);
    }

    /** 
     * <p>根据虚拟编码查询线路</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:35:39 AM
     * @param id
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILineDao#queryLineById(java.lang.String)
     */
    @Override
    public ExpressLineEntity queryLineByVirtualCode(String virtualCode) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("active", FossConstants.ACTIVE);
	map.put("virtualCode", virtualCode);
	return (ExpressLineEntity) getSqlSession().selectOne(NAMESPACE + "queryExpressLineByVirtualCode", map);
    }

    /** 
     * <p>根据查询条件查询线路</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:35:39 AM
     * @param line
     * @param start
     * @param limit
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILineDao#queryLineListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity, int, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ExpressLineEntity> queryLineListByCondition(ExpressLineEntity line,
	    int start, int limit) {
	line.setActive(FossConstants.ACTIVE);
	return (List<ExpressLineEntity>) getSqlSession().selectList(NAMESPACE + "queryExpressLineListByCondition", line, new RowBounds(start, limit));
    }

    /** 
     * <p>根据查询条件查询线路数量</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:35:39 AM
     * @param line
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILineDao#countLineListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity)
     */
    @Override
    public long countLineListByCondition(ExpressLineEntity line) {
	line.setActive(FossConstants.ACTIVE);
	return (Long) getSqlSession().selectOne(NAMESPACE + "countExpressLineListByCondition", line);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ExpressLineEntity> queryLineListForDownload(ExpressLineEntity line) {
	return (List<ExpressLineEntity>) getSqlSession().selectList(NAMESPACE + "queryExpressLineListForDownload", line);
    }
    
    /**
     * 
     * <p>按出发营业部查找出发线路和全量中转线路以及全量到达线路</p> 
     * @author foss-zhujunyong
     * @date Mar 21, 2013 3:01:19 PM
     * @param map
     * @return
     * @see
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ExpressLineEntity> queryLineListForDownloadViaFilter(Map<String, Object> map) {
	return (List<ExpressLineEntity>) getSqlSession().selectList(NAMESPACE + "queryExpressLineListForDownloadViaFilter", map);
    }
    
    
    @SuppressWarnings("unchecked")
    @Override
    public List<ExpressLineEntity> queryLineListBySourceAndType(ExpressLineEntity line) {
	return (List<ExpressLineEntity>) getSqlSession().selectList(NAMESPACE + "queryExpressLineListBySourceAndType", line);
    }
    

    @Override
    public Date queryLastModifyTime() {
	Long version = (Long) getSqlSession().selectOne(NAMESPACE + "queryExpressLastVersion");
	if (version == null) {
	    return null;
	}
	return new Date(version);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<ExpressLineEntity> queryLineListForCache() {
	String active = FossConstants.ACTIVE;
	return (List<ExpressLineEntity>)getSqlSession().selectList(NAMESPACE + "queryExpressLineListForCache", active);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<ExpressLineEntity> queryLineListForExport(ExpressLineEntity line, int start, int limit) {
	line.setActive(FossConstants.ACTIVE);
	return (List<ExpressLineEntity>) getSqlSession().selectList(NAMESPACE + "queryExpressLineListForExport", line, new RowBounds(start, limit));
    }

    /**
	 * 分页下载
	 * @param entity
	 * @param i
	 * @param thousand
	 * @return
	 */ @SuppressWarnings("unchecked")
	public List<ExpressLineEntity> queryLineListForDownloadByPage(ExpressLineEntity line,
			int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return (List<ExpressLineEntity>) getSqlSession().selectList(NAMESPACE + "queryExpressLineListForDownload", line, rowBounds);
		   
	}
	 /**
	  * 
	  *<P>根据线路简码 精确查询线路</P>
      * @author :130566-zengJunfan
	  * @date : 2013-10-18上午10:53:14
	  * @param simpleCode
	  * @return
	  */	
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpressLineEntity> queryLineEntityBySimpleCode(String simpleCode) {
		if(StringUtils.isBlank(simpleCode)){
			return null;
		}
		ExpressLineEntity entity =new ExpressLineEntity();
		entity.setActive(FossConstants.ACTIVE);
		entity.setSimpleCode(simpleCode);
		return (List<ExpressLineEntity>) getSqlSession().selectList(NAMESPACE + "queryExpressLineEntityBySimpleCode", entity);
	}
}
