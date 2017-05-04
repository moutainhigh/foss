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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/BillAdvertisingSloganForDeptDao.java
 * 
 * FILE NAME        	: BillAdvertisingSloganForDeptDao.java
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
import com.deppon.foss.module.base.baseinfo.api.server.dao.IBillAdvertisingSloganForDeptDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BillSloganAppOrgEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * 部门单据广告语Dao接口实现类：提供对部门单据广告语的增删改查的基本操作
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-12 下午2:43:10 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-12 下午2:43:10
 * @since
 * @version
 */
public class BillAdvertisingSloganForDeptDao extends SqlSessionDaoSupport implements
	IBillAdvertisingSloganForDeptDao {
    
    private static final String NAMESPACE = "foss.bse.bse-baseinfo.billSloganAppOrg.";
    
    /**
     * 新增部门单据广告语 
     * @author 094463-foss-xieyantao
     * @date 2012-10-12 下午2:43:10
     * @param entity
     * @return
     * @see
     */
    @Override
    public int addBillAdvertisingSloganForDept(BillSloganAppOrgEntity entity) {
	
	return this.getSqlSession().insert(NAMESPACE + "insert", entity);
    }
    
    /**
     * 根据code作废部门单据广告语信息 
     * @author 094463-foss-xieyantao
     * @date 2012-10-12 下午2:43:10
     * @param codes
     * @return
     * @see
     */
    @Override
    public int deleteBillAdvertisingSloganForDeptByCode(String[] codes,String modifyUser) {
	
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("codes", codes);
	map.put("modifyUser", modifyUser);
	map.put("modifyDate", new Date());
	map.put("active", FossConstants.INACTIVE);
	
	return this.getSqlSession().update(NAMESPACE + "deleteByCode", map);
    }
    
    /**
     * 修改部门单据广告语信息 
     * @author 094463-foss-xieyantao
     * @date 2012-10-12 下午2:43:10
     * @param entity
     * @return
     * @see
     */
    @Override
    public int updateBillAdvertisingSloganForDept(BillSloganAppOrgEntity entity) {
	
	entity.setModifyDate(new Date());
	
	return this.getSqlSession().update(NAMESPACE + "update", entity);
    }
    
    /**
     * 根据传入对象查询符合条件所有部门单据广告语信息 
     * @author 094463-foss-xieyantao
     * @date 2012-10-12 下午2:43:10
     * @param entity
     * @param limit
     * @param start
     * @return
     * @see
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<BillSloganAppOrgEntity> queryBillAdvertisingSloganForDepts(
	    BillSloganAppOrgEntity entity, int limit, int start) {
	
	RowBounds rowBounds = new RowBounds(start, limit);
	
	return this.getSqlSession().selectList(NAMESPACE + "getAllInfos", entity,rowBounds);
    }
    
    /**
     * 统计总记录数 
     * @author 094463-foss-xieyantao
     * @date 2012-10-12 下午2:43:10
     * @param entity
     * @return
     * @see
     */
    @Override
    public Long queryRecordCount(BillSloganAppOrgEntity entity) {
	
	return  (Long)this.getSqlSession().selectOne(NAMESPACE + "getCount", entity);
    }
    
    /**
     * <p>根据单据广告语虚拟编码、部门编码查询部门单据广告语</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-1-10 下午2:55:12
     * @param orgCode 部门编码
     * @param sloganVirtualCode 单据广告语虚拟编码
     * @param appOrgId 部门单据广告语ID
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IBillAdvertisingSloganForDeptDao#querySloganAppOrgByCode(java.lang.String, java.lang.String)
     */
    @Override
    public BillSloganAppOrgEntity querySloganAppOrgByCode(String orgCode,
	    String sloganVirtualCode,String appOrgId) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("orgCode", orgCode);
	map.put("sloganVirtualCode", sloganVirtualCode);
	map.put("appOrgId", appOrgId);
	map.put("active", FossConstants.ACTIVE);
	
	return (BillSloganAppOrgEntity)this.getSqlSession().selectOne(NAMESPACE+"querySloganAppOrgByCode", map);
    }

}
