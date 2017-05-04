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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/NonfixedCustomerDao.java
 * 
 * FILE NAME        	: NonfixedCustomerDao.java
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

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.INonfixedCustomerDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.NonfixedCustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerPaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;
import com.deppon.foss.util.define.FossConstants;


/**
 * 散客信息DAO接口实现
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-11-21 上午9:48:21 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-11-21 上午9:48:21
 * @since
 * @version
 */
public class NonfixedCustomerDao extends SqlSessionDaoSupport implements
	INonfixedCustomerDao {
    
    private static final String NAMESPACE = "foss.bse.bse-baseinfo.nonfixedCustomer.";

    /** 
     * 新增散客信息
     * @author 094463-foss-xieyantao
     * @date 2012-11-21 上午8:59:59
     * @param entity 散客信息实体
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.INonfixedCustomerDao#addNonfixedCustomer(com.deppon.foss.module.base.baseinfo.api.shared.domain.NonfixedCustomerEntity)
     */
    @Override
    public int addNonfixedCustomer(NonfixedCustomerEntity entity) {
	
	return this.getSqlSession().insert(NAMESPACE + "insert", entity);
    }

    /** 
     * 根据code作废散客信息
     * @author dp-xieyantao
     * @date 2012-11-20 下午6:20:51
     * @param code
     * @param modifyUser
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.INonfixedCustomerDao#deleteNonfixedCustomerByCode(java.lang.String, java.lang.String)
     */
    @Override
    public int deleteNonfixedCustomerByCode(String code, String modifyUser) {
	
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("code", code);
	map.put("modifyUser", modifyUser);
	map.put("modifyDate", new Date());
	map.put("active", FossConstants.INACTIVE);
	map.put("active0", FossConstants.ACTIVE);
	
	return this.getSqlSession().update(NAMESPACE + "deleteByCode", map);
    }

    /** 
     * 修改散客信息
     * @author dp-xieyantao
     * @date 2012-11-21 上午8:59:59
     * @param entity
     *            散客信息实体
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.INonfixedCustomerDao#updateNonfixedCustomer(com.deppon.foss.module.base.baseinfo.api.shared.domain.NonfixedCustomerEntity)
     */
    @Override
    public int updateNonfixedCustomer(NonfixedCustomerEntity entity) {
	
	return this.getSqlSession().update(NAMESPACE + "update", entity);
    }
    
    /**
     * <p>根据传入查询条件对象dto，查询符合条件的临欠散客信息集合list</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-1-31 上午10:28:20
     * @param condition
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.INonfixedCustomerDao#queryCustomerByCondition(com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<CustomerQueryConditionDto> queryCustomerByCondition(
	    CustomerQueryConditionDto condition) {
	Map<String, Object> map = new HashMap<String, Object>();
	//2015-09-23 DATE-6211模糊查询问题，在向接送货确认无影响后改为精确查询
			condition.setExactQuery(true);
    	map.put("condition", condition);
    	map.put("active", FossConstants.ACTIVE);
    	
    	return this.getSqlSession().selectList(NAMESPACE + "queryCustomerByCondition", map);
    }
    /**
     * <p>根据传入查询条件对象dto，查询符合条件的临欠散客信息集合list（可以查询无效信息）</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-1-31 上午10:28:20
     * @param condition
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.INonfixedCustomerDao#queryCustomerByCondition(com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<NonfixedCustomerEntity> queryCustomerByConditionAll(
	    CustomerQueryConditionDto condition) {
	    Map<String, Object> map = new HashMap<String, Object>();
    	map.put("condition", condition);
    	return this.getSqlSession().selectList(NAMESPACE + "queryCustomerByConditionAll", map);
    }
    
    
    /**
     * <p>根据传入查询条件对象dto，查询符合条件的临欠散客信息集合list</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-1-31 上午10:28:20
     * @param condition
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.INonfixedCustomerDao#queryCustomerByCondition(com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<CustomerQueryConditionDto> queryCustomerByConditionByPage(
    		CustomerPaginationDto condition) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	//2015-09-23 DATE-6211模糊查询问题，在向接送货确认无影响后改为精确查询
    	condition.setExactQuery(true);
    	map.put("condition", condition);
    	map.put("active", FossConstants.ACTIVE);
    	RowBounds rowBounds = new RowBounds(condition.getPageNum()-1, condition.getPageSize());
    	return this.getSqlSession().selectList(NAMESPACE + "queryCustomerByCondition", map, rowBounds);
    }
    
    
    /**
     * <p>根据传入查询条件对象dto，查询符合条件的临欠散客信息集合list</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-1-31 上午10:28:20
     * @param condition
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.INonfixedCustomerDao#queryCustomerByCondition(com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto)
     */
    public int countCustomerByCondition(
    		CustomerPaginationDto condition) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("condition", condition);
    	map.put("active", FossConstants.ACTIVE);
    	
    	return (Integer)this.getSqlSession().selectOne(NAMESPACE + "countCustomerByCondition", map);
    }
    
    /**
     * <p>根据临欠散客crmId查询散客信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-3-8 下午2:31:32
     * @param crmId ID 
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.INonfixedCustomerDao#queryEntityByCrmId(java.math.BigInteger)
     */
    @Override
    public NonfixedCustomerEntity queryEntityByCrmId(BigInteger crmId) {
	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("crmId", crmId);
    	map.put("active", FossConstants.ACTIVE);
    	
    	return (NonfixedCustomerEntity)this.getSqlSession().selectOne(NAMESPACE + "queryEntityByCrmId", map);
    }
    
    /**
     * <p>通过传入一个客户编码查询出财务未作废散客信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-7-9 上午11:51:03
     * @param custCode 散客编码
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.INonfixedCustomerDao#queryNoDeletedCustInfoByCode(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<NonfixedCustomerEntity> queryNoDeletedCustInfoByCode(String custCode) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("isDelete", FossConstants.NO);
    	map.put("custCode", custCode);
    	
    	return this.getSqlSession().selectList(NAMESPACE + "queryNoDeletedCustInfoByCode", map);
    }
    
    /**
	 * 根据查询条件获取客户信息是否存在
	 * @创建时间 2014-5-27 下午6:57:11   
	 * @创建人： WangQianJin
	 */
    @Override
	public int queryCustomerExistByCondition(CustomerQueryConditionDto condition){
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("condition", condition);
    	map.put("active", FossConstants.ACTIVE);
    	return (Integer)this.getSqlSession().selectOne(NAMESPACE + "queryCustomerExistByCondition", map);
	}

}
