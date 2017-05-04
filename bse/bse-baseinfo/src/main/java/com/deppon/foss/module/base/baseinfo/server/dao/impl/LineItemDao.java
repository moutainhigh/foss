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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/LineItemDao.java
 * 
 * FILE NAME        	: LineItemDao.java
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

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ILineDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ILineItemDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISendLineInfoToWDGHService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LineItemEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 线段Dao测试类
 * @author foss-zhujunyong
 * @date Oct 25, 2012 4:07:58 PM
 * @version 1.0
 */
public class LineItemDao extends SqlSessionDaoSupport implements ILineItemDao {

	/**
     * 同步线路信息给wdgh 系统接口service
     */
    private ISendLineInfoToWDGHService sendLineInfoToWDGHService;
    
    /**
     * lineDao
     */
    
    private ILineDao lineDao;
    
    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".lineItem.";

	public void setSendLineInfoToWDGHService(
			ISendLineInfoToWDGHService sendLineInfoToWDGHService) {
		this.sendLineInfoToWDGHService = sendLineInfoToWDGHService;
	}

	public void setLineDao(ILineDao lineDao) {
		this.lineDao = lineDao;
	}
	
    /** 
     * <p>添加线段</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 4:07:58 PM
     * @param lineItem
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILineItemDao#addLineItem(com.deppon.foss.module.base.baseinfo.api.shared.domain.LineItemEntity)
     */
    @Override
    public LineItemEntity addLineItem(LineItemEntity lineItem) {
	Date now = new Date();
	lineItem.setId(UUIDUtils.getUUID());
	lineItem.setVirtualCode(lineItem.getId());
	lineItem.setCreateDate(now);
	lineItem.setModifyDate(new Date(NumberConstants.ENDTIME));
	lineItem.setModifyUser(lineItem.getCreateUser());
	lineItem.setVersion(now.getTime());
	lineItem.setActive(FossConstants.ACTIVE);
	int result = getSqlSession().insert(NAMESPACE + "addLineItem", lineItem);
	updateLineAging(lineItem);
	LineEntity line = lineDao.queryLineByVirtualCode(lineItem.getLineVirtualCode());
	//同步更新线路时效到WDGH
	sendLineInfoToWDGHService.syncLineInfos(line, NumberConstants.TWO.toString());
	return result > 0 ? lineItem : null;
    }

    /** 
     * <p>作废线段</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 4:07:58 PM
     * @param lineItem
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILineItemDao#deleteLineItem(com.deppon.foss.module.base.baseinfo.api.shared.domain.LineItemEntity)
     */
    @Override
    public LineItemEntity deleteLineItem(LineItemEntity lineItem) {
    	
	Date now = new Date();
	lineItem.setActive(FossConstants.INACTIVE);
	lineItem.setModifyDate(now);
	lineItem.setVersion(now.getTime());
	int result = getSqlSession().update(NAMESPACE + "deleteLineItem", lineItem);
	
	//更新线路冗余字段
	updateLineAging(lineItem);
	LineEntity line = lineDao.queryLineByVirtualCode(lineItem.getLineVirtualCode());
	//同步更新线路信息到WDGH
	sendLineInfoToWDGHService.syncLineInfos(line,(NumberConstants.TWO).toString());
	
	return result > 0 ? lineItem : null;
    }

    /** 
     * <p>更新线段信息</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 4:07:58 PM
     * @param lineItem
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILineItemDao#updateLineItem(com.deppon.foss.module.base.baseinfo.api.shared.domain.LineItemEntity)
     */
    @Override
    public LineItemEntity updateLineItem(LineItemEntity lineItem) {
	LineItemEntity entity = deleteLineItem(lineItem);
	if (entity == null) {
	    return null;
	}

	entity.setId(UUIDUtils.getUUID());
	entity.setCreateDate(entity.getModifyDate());
	entity.setVersion(entity.getModifyDate().getTime());
	entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	entity.setCreateUser(entity.getModifyUser());
	entity.setActive(FossConstants.ACTIVE);
	int result = getSqlSession().insert(NAMESPACE + "addLineItem", entity);
	
	return result > 0 ? entity : null;
    }

    /** 
     * <p>查询线段详情</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 4:07:58 PM
     * @param id
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILineItemDao#queryLineItemById(java.lang.String)
     */
    @Override
    public LineItemEntity queryLineItemById(String id) {
	return (LineItemEntity) getSqlSession().selectOne(NAMESPACE + "queryLineItemById", id);
    }
    
    /**
     * 
     * <p>根据虚拟编码查询线段</p> 
     * @author foss-zhujunyong
     * @date Mar 14, 2013 1:31:35 PM
     * @param virtualCode
     * @return
     * @see
     */
    @Override
    public LineItemEntity queryLineItemByVirtualCode(String virtualCode) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("virtualCode", virtualCode);
	map.put("active", FossConstants.ACTIVE);
	return (LineItemEntity) getSqlSession().selectOne(NAMESPACE + "queryLineItemByVirtualCode", map);
    }

    /** 
     * <p>查询特定线路下的线段列表</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 4:07:58 PM
     * @param lineVirtualCode
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILineItemDao#queryLineItemListByLineVirtualCode(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<LineItemEntity> queryLineItemListByLineVirtualCode(
	    String lineVirtualCode) {
	Map<String, String> map = new HashMap<String, String> ();
	map.put("active", FossConstants.ACTIVE);
	map.put("lineVirtualCode", lineVirtualCode);
	return (List<LineItemEntity>) getSqlSession().selectList(NAMESPACE + "queryLineItemListByLineVirtualCode", map);
    }

    /**
     * 
     * <p>更新线路中的冗余字段：普车时效和卡车时效</p> 
     * @author foss-zhujunyong
     * @date Oct 26, 2012 4:28:57 PM
     * @see
     */
    @Override
    public LineEntity updateLineAging(LineItemEntity lineItem) {
	lineItem.setActive(FossConstants.ACTIVE);
	@SuppressWarnings("unchecked")
	Map<String, BigDecimal> map = (Map<String, BigDecimal>)getSqlSession().selectOne(NAMESPACE + "sumAgingByLineVirtualCode", lineItem);
	Long common = map.get("COMMON").longValue();
	Long fast = map.get("FAST").longValue();
	Long passby = map.get("PASSBY").longValue();
	
	LineEntity line = new LineEntity();
	line.setCommonAging(common + passby);
	line.setFastAging(fast + passby);
	line.setVirtualCode(lineItem.getLineVirtualCode());
	line.setActive(FossConstants.ACTIVE);
	line.setVersion(System.currentTimeMillis());
	line.setModifyDate(new Date());
	line.setModifyUser(FossUserContext.getCurrentInfo().getEmpCode());
	getSqlSession().update(NAMESPACE + "updateLineAging", line);
	
	return line;
    }


    /**
     * 
     * <p>查询符合条件的线段</p> 
     * @author foss-zhujunyong
     * @date Nov 5, 2012 3:13:56 PM
     * @param orginalOrganizationCode
     * @return
     * @see
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<LineItemEntity> queryLineItemListByCondition (LineItemEntity lineItem) {
	return (List<LineItemEntity>) getSqlSession().selectList(NAMESPACE + "queryLineItemListByCondition", lineItem);
    }

    @Override
    public int deleteLineItemByLine(String lineVirtualCode, String modifyUser){
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("active", FossConstants.ACTIVE);
	map.put("inactive", FossConstants.INACTIVE);
	map.put("lineVirtualCode", lineVirtualCode);
	map.put("modifyDate", new Date());
	map.put("modifyUser", modifyUser);
	int result = getSqlSession().update(NAMESPACE + "deleteLineItemByLine", map);

	return result;
    }

    /** 
     * <p>下载线段</p> 
     * @author foss-zhujunyong
     * @date Jan 15, 2013 2:43:43 PM
     * @param lineItem
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILineItemDao#queryLineItemListForDownload(com.deppon.foss.module.base.baseinfo.api.shared.domain.LineItemEntity)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<LineItemEntity> queryLineItemListForDownload(LineItemEntity lineItem) {
	return (List<LineItemEntity>) getSqlSession().selectList(NAMESPACE + "queryLineItemListForDownload", lineItem);
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
    public List<LineItemEntity> queryLineItemListForCache() {
	String active = FossConstants.ACTIVE;
	return (List<LineItemEntity>)getSqlSession().selectList(NAMESPACE + "queryLineItemListForCache", active);
    }
    
    
}
