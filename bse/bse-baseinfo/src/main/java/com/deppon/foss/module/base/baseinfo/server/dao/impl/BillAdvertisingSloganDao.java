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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/BillAdvertisingSloganDao.java
 * 
 * FILE NAME        	: BillAdvertisingSloganDao.java
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
import com.deppon.foss.module.base.baseinfo.api.server.dao.IBillAdvertisingSloganDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BillSloganEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * 单据广告语Dao接口实现类：提供对单据广告语的增删改查的基本操作
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-12 下午4:11:34 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-12 下午4:11:34
 * @since
 * @version
 */
public class BillAdvertisingSloganDao extends SqlSessionDaoSupport implements
	IBillAdvertisingSloganDao {
    
    private static final String NAMESPACE = "foss.bse.bse-baseinfo.billslogan.";
    
    /**
     * 新增单据广告语 
     * @author 094463-foss-xieyantao
     * @date 2012-10-12 下午4:11:34
     * @param entity
     * @return
     * @see
     */
    @Override
    public int addBillAdvertisingSlogan(BillSloganEntity entity) {
	
	return this.getSqlSession().insert(NAMESPACE + "insert", entity);
    }
    
    /**
     * 根据code作废单据广告语信息 
     * @author 094463-foss-xieyantao
     * @date 2012-10-12 下午4:11:34
     * @param codes
     * @return
     * @see
     */
    @Override
    public int deleteBillAdvertisingSloganByCode(String[] codes,String modifyUser) {
	
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("codes", codes);
	map.put("modifyUser", modifyUser);
	map.put("modifyDate", new Date());
	map.put("active", FossConstants.INACTIVE);
	
	return this.getSqlSession().update(NAMESPACE + "deleteByCode", map);
    }
    
    /**
     * 修改单据广告语信息 
     * @author 094463-foss-xieyantao
     * @date 2012-10-12 下午4:11:34
     * @param entity
     * @return
     * @see
     */
    @Override
    public int updateBillAdvertisingSlogan(BillSloganEntity entity) {
	
	return this.getSqlSession().update(NAMESPACE + "update", entity);
    }
    
    /**
     * 根据传入对象查询符合条件所有单据广告语信息 
     * @author 094463-foss-xieyantao
     * @date 2012-10-12 下午4:11:34
     * @param entity
     * @param limit
     * @param start
     * @return
     * @see
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<BillSloganEntity> queryBillAdvertisingSlogans(
	    BillSloganEntity entity, int limit, int start) {
        
	RowBounds rowBounds = new RowBounds(start, limit);
	
	return this.getSqlSession().selectList(NAMESPACE + "getAllInfos", entity,rowBounds);
    }
    
    /**
     * 统计总记录数 
     * @author 094463-foss-xieyantao
     * @date 2012-10-12 下午4:11:34
     * @param entity
     * @return
     * @see
     */
    @Override
    public Long queryRecordCount(BillSloganEntity entity) {
	
	return  (Long)this.getSqlSession().selectOne(NAMESPACE + "getCount", entity);
    }
    
    /**
     * <p>根据单据广告语代码查询单据广告语信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-12-4 上午10:58:47
     * @param billSloganCode
     * @param sloganId 单据广告语ID
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IBillAdvertisingSloganDao#queryBillSloganContent(java.lang.String)
     */
    @Override
    public BillSloganEntity queryBillSloganContent(String billSloganCode,String sloganId) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("active", FossConstants.ACTIVE);
	map.put("billSloganCode", billSloganCode);
	map.put("sloganId", sloganId);
	
	return (BillSloganEntity)this.getSqlSession().selectOne(NAMESPACE + "queryBillSloganContent", map);
    }
    
    /**
     * <p>根据单据广告语名称查询单据广告语</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-1-10 下午2:55:12
     * @param sloganName 单据广告语名称
     * @param sloganId 单据广告语ID
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IBillAdvertisingSloganDao#queryBillSloganBySmsSloganName(java.lang.String)
     */
    @Override
    public BillSloganEntity queryBillSloganBySmsSloganName(String sloganName,String sloganId) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("active", FossConstants.ACTIVE);
	map.put("sloganName", sloganName);
	map.put("sloganId", sloganId);
	
	return (BillSloganEntity)this.getSqlSession().selectOne(NAMESPACE + "queryBillSloganBySmsSloganName", map);
    }

}
