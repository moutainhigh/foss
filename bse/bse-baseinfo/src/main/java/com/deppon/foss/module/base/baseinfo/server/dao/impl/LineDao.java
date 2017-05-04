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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/LineDao.java
 * 
 * FILE NAME        	: LineDao.java
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
import com.deppon.foss.module.base.baseinfo.api.server.dao.ILineDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 线路Dao
 * @author foss-zhujunyong
 * @date Oct 25, 2012 11:35:38 AM
 * @version 1.0
 */
public class LineDao extends SqlSessionDaoSupport implements ILineDao {


    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".line.";

    /** 
     * <p>生效或失效线路</p> 
     * @author foss-zhujunyong
     * @date Jan 16, 2013 7:57:48 PM
     * @param line
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILineDao#validLine(com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity)
     */
    @Override
    public int validLine(LineEntity line) {
	Date now = new Date();
	line.setModifyDate(now);
	line.setVersion(now.getTime());
	return getSqlSession().update(NAMESPACE + "validLine",line);
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
    public LineEntity addLine(LineEntity line) {
	Date now = new Date();
	line.setId(UUIDUtils.getUUID());
	line.setVirtualCode(line.getId());
	line.setCreateDate(now);
	line.setModifyDate(new Date(NumberConstants.ENDTIME));
	line.setModifyUser(line.getCreateUser());
	line.setVersion(now.getTime());
	line.setActive(FossConstants.ACTIVE);
	int result = getSqlSession().insert(NAMESPACE + "addLine", line);
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
    public LineEntity deleteLine(LineEntity line) {
	Date now = new Date();
	line.setActive(FossConstants.INACTIVE);
	line.setModifyDate(now);
	line.setVersion(now.getTime());
	int result = getSqlSession().update(NAMESPACE + "deleteLine", line);
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
    public LineEntity updateLine(LineEntity line) {
	LineEntity entity = deleteLine(line);
	if (entity == null) {
	    return null;
	}
	
	entity.setId(UUIDUtils.getUUID());
	entity.setCreateDate(entity.getModifyDate());
	entity.setVersion(entity.getModifyDate().getTime());
	entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	entity.setCreateUser(entity.getModifyUser());
	entity.setActive(FossConstants.ACTIVE);
	int result = getSqlSession().insert(NAMESPACE + "addLine", entity);
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
    public LineEntity queryLineById(String id) {
	return (LineEntity) getSqlSession().selectOne(NAMESPACE + "queryLineById", id);
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
    public LineEntity queryLineByVirtualCode(String virtualCode) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("active", FossConstants.ACTIVE);
	map.put("virtualCode", virtualCode);
	return (LineEntity) getSqlSession().selectOne(NAMESPACE + "queryLineByVirtualCode", map);
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
    public List<LineEntity> queryLineListByCondition(LineEntity line,
	    int start, int limit) {
	line.setActive(FossConstants.ACTIVE);
	return (List<LineEntity>) getSqlSession().selectList(NAMESPACE + "queryLineListByCondition", line, new RowBounds(start, limit));
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
    public long countLineListByCondition(LineEntity line) {
	line.setActive(FossConstants.ACTIVE);
	return (Long) getSqlSession().selectOne(NAMESPACE + "countLineListByCondition", line);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<LineEntity> queryLineListForDownload(LineEntity line) {
	return (List<LineEntity>) getSqlSession().selectList(NAMESPACE + "queryLineListForDownload", line);
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
    public List<LineEntity> queryLineListForDownloadViaFilter(Map<String, Object> map) {
	return (List<LineEntity>) getSqlSession().selectList(NAMESPACE + "queryLineListForDownloadViaFilter", map);
    }
    
    
    @SuppressWarnings("unchecked")
    @Override
    public List<LineEntity> queryLineListBySourceAndType(LineEntity line) {
	return (List<LineEntity>) getSqlSession().selectList(NAMESPACE + "queryLineListBySourceAndType", line);
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
    public List<LineEntity> queryLineListForCache() {
	String active = FossConstants.ACTIVE;
	return (List<LineEntity>)getSqlSession().selectList(NAMESPACE + "queryLineListForCache", active);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<LineEntity> queryLineListForExport(LineEntity line, int start, int limit) {
	line.setActive(FossConstants.ACTIVE);
	return (List<LineEntity>) getSqlSession().selectList(NAMESPACE + "queryLineListForExport", line, new RowBounds(start, limit));
    }

    /**
	 * 分页下载
	 * @param entity
	 * @param i
	 * @param thousand
	 * @return
	 */ @SuppressWarnings("unchecked")
	public List<LineEntity> queryLineListForDownloadByPage(LineEntity line,
			int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return (List<LineEntity>) getSqlSession().selectList(NAMESPACE + "queryLineListForDownload", line, rowBounds);
		   
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
	public List<LineEntity> queryLineEntityBySimpleCode(String simpleCode) {
		if(StringUtils.isBlank(simpleCode)){
			return null;
		}
		LineEntity entity =new LineEntity();
		entity.setActive(FossConstants.ACTIVE);
		entity.setSimpleCode(simpleCode);
		return (List<LineEntity>) getSqlSession().selectList(NAMESPACE + "queryLineEntityBySimpleCode", entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LineEntity> queryLineList(String orginalOrganizationCode) { 
		LineEntity entity =new LineEntity();
		entity.setOrginalOrganizationCode(orginalOrganizationCode);
		entity.setTransType(DictionaryValueConstants.BSE_LINE_TRANSTYPE_QIYUN);
		entity.setLineSort(DictionaryValueConstants.BSE_LINE_SORT_SOURCE);
		entity.setIsDefault(FossConstants.YES);
		entity.setActive(FossConstants.YES);
		//entity.setValid(FossConstants.YES);
		return (List<LineEntity>) getSqlSession().selectList(NAMESPACE + "queryLineList", entity);
	}
}
