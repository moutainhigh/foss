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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/PublicBankAccountDao.java
 * 
 * FILE NAME        	: PublicBankAccountDao.java
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

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IPublicBankAccountDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PublicBankAccountEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 对公银行账号DAO
 * 
 * 如果没有特别说明，查询时均查没有销户的（即正常的）
 * 
 * @author 087584-foss-lijun
 * @date 2013-1-7 下午8:14:44
 */
public class PublicBankAccountDao extends SqlSessionDaoSupport implements
	IPublicBankAccountDao {

	/**
	 * 声明SQL命名空间
	 */
    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX
	    + ".publicBankAccount.";

    /**
     * 新增
     * 
     * @author 087584-foss-lijun
     * @date 2012-12-10 下午8:4:6
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPublicBankAccountDao#addPublicBankAccount(com.deppon.foss.module.base.baseinfo.api.shared.domain.PublicBankAccountEntity)
     */
    @Override
    public PublicBankAccountEntity addPublicBankAccount(PublicBankAccountEntity entity) {
		// 请求合法性验证：
		if (null == entity) {
			//返回为null
			return entity;
		}
		//获取当前时间
		Date now = new Date();
		//设置Primary Key
		entity.setId(UUIDUtils.getUUID());
		//设置创建日期
		entity.setCreateDate(now);
		//设置修改日期
		entity.setModifyDate(new Date(NumberConstants.ENDTIME));
		//设置创建人
		entity.setModifyUser(entity.getCreateUser());
		//设置是否有效
		entity.setActive(FossConstants.ACTIVE);
		//返回影响的记录条数
		int result = getSqlSession().insert(NAMESPACE + "addPublicBankAccount",
				entity);
		//如果影响的条数大于零则返回对象本身，否则返回null
		return result > NumberConstants.ZERO ? entity : null;
    }

    /**
     * 通过FID 标识来删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-12-10 下午8:4:6
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPublicBankAccountDao#deletePublicBankAccount(java.lang.String)
     */
    @Override
    public PublicBankAccountEntity deletePublicBankAccount(PublicBankAccountEntity entity) {
	// 请求参数合法性验证
	if(null == entity){
	    return null;
	}
	if (StringUtils.isBlank(entity.getBankAcc())) {
	    return null;
	}
	
	// 处理删除时要更新的数据
	Date now = new Date();
	entity.setActive(FossConstants.INACTIVE);
	entity.setModifyDate(now);
	
	Map<String, Object> map=new HashMap<String, Object>();
	map.put("entity", entity);
	// 只删除active为有效的：
	map.put("conditionActive", FossConstants.ACTIVE);
	int result = getSqlSession().update(NAMESPACE + "deletePublicBankAccount", map);
	return result > NumberConstants.ZERO ? entity : null;
    }

    /**
     * 通过FID 标识来批量删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-12-10 下午8:4:6
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPublicBankAccountDao#deletePublicBankAccountMore(java.lang.String[], java.lang.String)
     */
    @Override
    public PublicBankAccountEntity deletePublicBankAccountMore(String[] codes , String deleteUser) {
	// 请求合法性判断：
	if(ArrayUtils.isEmpty(codes)) {
	    return null;
	}
	
	// 处理删除时要更新的数据
	Date now = new Date();
	PublicBankAccountEntity entity = new PublicBankAccountEntity();
	entity.setActive(FossConstants.INACTIVE);
	entity.setModifyDate(now);
	entity.setModifyUser(deleteUser);

	Map<String, Object> map = new HashMap<String, Object>();
	map.put("codes", codes);
	map.put("entity", entity);
	// 只删除active为有效的：
	map.put("conditionActive", FossConstants.ACTIVE);
	
	int result = getSqlSession().update(
		NAMESPACE + "deletePublicBankAccountMore", map);
	return result > NumberConstants.ZERO ? entity : null;
    }

    /**
     * 通过FID标识更新
     * 
     * @author 087584-foss-lijun
     * @date 2012-12-10 下午8:4:6
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPublicBankAccountDao#updatePublicBankAccount(com.deppon.foss.module.base.baseinfo.api.shared.domain.PublicBankAccountEntity)
     */
    @Override
    public PublicBankAccountEntity updatePublicBankAccount(PublicBankAccountEntity entity) {
	// 请求合法性判断：
	if (null == entity) {
	    return entity;
	}
	if (StringUtils.isBlank(entity.getBankAcc())) {
	    return entity;
	}
	
	// 更新要先删除旧的数据：
	PublicBankAccountEntity result = this.deletePublicBankAccount(entity);
	if (result == null) {
	    String msg = "更新时，作废失败";
	    LOGGER.error(msg);
	}
	
	// 组装插入参数
	entity.setId(UUIDUtils.getUUID());

	// CreateUser为传入的用户编码，CreateDate为当前时间
	Date now = new Date();
	entity.setCreateDate(now);
	// ModifyDate为2999年，为一个常量
	entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	entity.setCreateUser(entity.getModifyUser());
	
	entity.setActive(FossConstants.ACTIVE);
	int resultNum = getSqlSession().insert(NAMESPACE + "addPublicBankAccount", entity);
	return resultNum > NumberConstants.ZERO ? entity : null;
    }



    /**
     * 以下全为查询：
     */
    
    /**
     * 通过 标识编码查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-12-10 下午8:4:6
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPublicBankAccountDao#queryPublicBankAccountByCode(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public PublicBankAccountEntity queryPublicBankAccountByFid(String code) {
	if (StringUtils.isBlank(code)) {
	    return null;
	}
	
	// 构造查询条件：
	PublicBankAccountEntity entity=new PublicBankAccountEntity();
	entity.setActive(FossConstants.ACTIVE);
	entity.setAccountStatus(FossConstants.YES);
	entity.setFid(code);
	
	List<PublicBankAccountEntity> entitys = this.getSqlSession().selectList(
		NAMESPACE + "queryPublicBankAccountByFid", entity);
	if (CollectionUtils.isEmpty(entitys)) {
	    return null;
	} else {
	    return entitys.get(NumberConstants.ZERO);
	}
    }

    
    /**
     * 精确查询
     * 
     * 根据多个标识编码批量查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:1:47
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPublicBankAccountDao#queryPublicBankAccountBatchBy(java.lang.String[])
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<PublicBankAccountEntity> queryPublicBankAccountBatchByFid(
	    String[] codes) {
	// 请求参数合法性判断
	if (ArrayUtils.isEmpty(codes)){
	    return null;
	}
	
	// 构造查询条件：
	Map<String,Object> map = new HashMap<String , Object>();
	map.put("codes", codes);
	map.put("active", FossConstants.ACTIVE);
	map.put("accountStatus", FossConstants.YES);
	
	return getSqlSession().selectList(
		NAMESPACE + "queryPublicBankAccountBatchByFid", map);
    }

    /** 
     * 精确查询
     * 
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:11:15
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPublicBankAccountDao#queryPublicBankAccountExactByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.PublicBankAccountEntity, int, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<PublicBankAccountEntity> queryPublicBankAccountExactByEntity(
	    PublicBankAccountEntity entity, int start, int limit) {
	PublicBankAccountEntity queryEntity;
	if (null == entity) {
	    queryEntity = new PublicBankAccountEntity();
	}else{
	    queryEntity = entity;
	}
	queryEntity.setActive(FossConstants.ACTIVE);
	queryEntity.setAccountStatus(FossConstants.YES);
	RowBounds rowBounds = new RowBounds(start, limit);
	return getSqlSession()
		.selectList(NAMESPACE + "queryPublicBankAccountExactByEntity",
			queryEntity,
			rowBounds);
    }

    /**
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:09:53
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPublicBankAccountDao#queryPublicBankAccountExactByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.PublicBankAccountEntity)
     */
    @Override
    public long queryPublicBankAccountExactByEntityCount(PublicBankAccountEntity entity) {
	PublicBankAccountEntity queryEntity;
	if (null == entity) {
	    queryEntity = new PublicBankAccountEntity();
	}else{
	    queryEntity = entity;
	}
	queryEntity.setActive(FossConstants.ACTIVE);
	queryEntity.setAccountStatus(FossConstants.YES);
	return (Long)getSqlSession().selectOne(
		NAMESPACE + "queryPublicBankAccountExactByEntityCount",
		queryEntity);
    }

    /**
     * 模糊查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-12-10 下午8:4:6
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPublicBankAccountDao#queryPublicBankAccountMore(java.lang.String[])
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<PublicBankAccountEntity> queryPublicBankAccountByEntity(
	    PublicBankAccountEntity entity, int start, int limit) {
	PublicBankAccountEntity queryEntity;
	if (null == entity) {
	    queryEntity = new PublicBankAccountEntity();
	}else{
	    queryEntity = entity;
	}
	queryEntity.setActive(FossConstants.ACTIVE);
	queryEntity.setAccountStatus(FossConstants.YES);
	RowBounds rowBounds = new RowBounds(start, limit);
	return getSqlSession().selectList(NAMESPACE + "queryPublicBankAccountByEntity", queryEntity,
			rowBounds);
    }

    /**
     * 模糊查询
     * 动态的查询条件-查询总条数。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为���糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-12-10 下午8:4:6
     * @see com.deppon.foss.module.base.baseinfo.server.dao.IPublicBankAccountDao#queryPublicBankAccountByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.PublicBankAccountEntity)
     */
    @Override
    public long queryPublicBankAccountByEntityCount(PublicBankAccountEntity entity) {
	PublicBankAccountEntity queryEntity;
	if (null == entity) {
	    queryEntity = new PublicBankAccountEntity();
	}else{
	    queryEntity = entity;
	}
	queryEntity.setActive(FossConstants.ACTIVE);
	queryEntity.setAccountStatus(FossConstants.YES);
	return (Long)getSqlSession().selectOne(NAMESPACE + "queryPublicBankAccountByEntityCount", queryEntity);
    }
	
    /**
     * 根据entity精确查询,用于数据下载
     * entity里面根据表结构，要动态（可不传入）传入MODIFY_TIME,员工编号，部门编号,
     * 
     * @author 087584-foss-lijun
     * @date 2012-12-10 下午8:4:6
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<PublicBankAccountEntity> queryPublicBankAccountForDownload(PublicBankAccountEntity entity){
	PublicBankAccountEntity queryEntity;
	if (null == entity) {
	    queryEntity = new PublicBankAccountEntity();
	}else{
	    queryEntity = entity;
	}
	return (List<PublicBankAccountEntity>) getSqlSession().selectList(
		NAMESPACE + "queryPublicBankAccountForDownload", queryEntity);
    }
    
    /**
     * 下面是特殊查询
     */
    
    /**
     * 通过 标识编码FID查询所有ACTIVE有效的（正常和销户的）
     * 
     * @author 087584-foss-lijun
     * @date 2012-12-10 下午8:4:6
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPublicBankAccountDao#queryPublicBankAccountActiveByFid(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public PublicBankAccountEntity queryPublicBankAccountOfActiveByFid(String code) {
	if (StringUtils.isBlank(code)) {
	    return null;
	}
	
	// 构造查询条件：
	PublicBankAccountEntity entity=new PublicBankAccountEntity();
	entity.setActive(FossConstants.ACTIVE);
	entity.setAccountStatus(FossConstants.YES);
	entity.setFid(code);
	
	List<PublicBankAccountEntity> entitys = this.getSqlSession().selectList(
		NAMESPACE + "queryPublicBankAccountOfActiveByFid", entity);
	if (CollectionUtils.isEmpty(entitys)) {
	    return null;
	} else {
	    return entitys.get(NumberConstants.ZERO);
	}
    }
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(LimitedWarrantyItemsDao.class);


}
