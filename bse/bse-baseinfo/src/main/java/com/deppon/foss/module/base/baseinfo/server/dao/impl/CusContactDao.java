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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/CusContactDao.java
 * 
 * FILE NAME        	: CusContactDao.java
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

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ICusContactDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ContactEntity;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;


/**
 * 客户联系人信息DAO接口实现
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-11-21 上午9:45:41 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-11-21 上午9:45:41
 * @since
 * @version
 */
public class CusContactDao extends SqlSessionDaoSupport implements ICusContactDao {
    
    private static final String NAMESPACE = "foss.bse.bse-baseinfo.cusContact.";

    /** 
     * 新增客户联系人信息
     * @author 094463-foss-xieyantao
     * @date 2012-11-21 上午9:02:53
     * @param entity
     *            客户联系人信息实体
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICusContactDao#addCusContact(com.deppon.foss.module.base.baseinfo.api.shared.domain.ContactEntity)
     */
    @Override
    public int addCusContact(ContactEntity entity) {
	
	return this.getSqlSession().insert(NAMESPACE + "insert", entity);
    }

    /** 
     * 根据code作废客户联系人信息
     * @author dp-xieyantao
     * @date 2012-11-20 下午1:49:51
     * @param crmId
     * @param modifyUser
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICusContactDao#deleteCusContactByCode(java.lang.String, java.lang.String)
     */
    @Override
    public int deleteCusContactByCode(BigDecimal crmId, String modifyUser) {
	
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("code", crmId);
	map.put("modifyUser", modifyUser);
	map.put("modifyDate", new Date());
	map.put("active", FossConstants.INACTIVE);
	map.put("active0", FossConstants.ACTIVE);
	
	return this.getSqlSession().update(NAMESPACE + "deleteByCode", map);
    }

    /** 
     * 修改客户联系人信息
     * @author dp-xieyantao
     * @date 2012-11-21 上午9:02:53
     * @param entity
     *            客户联系人信息实体
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICusContactDao#updateCusContact(com.deppon.foss.module.base.baseinfo.api.shared.domain.ContactEntity)
     */
    @Override
    public int updateCusContact(ContactEntity entity) {
	
	return this.getSqlSession().update(NAMESPACE + "update", entity);
    }
    
    /**
     * <p>根据crmId,最后一次修改时间查询客户联系人是否存在</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-11-26 下午2:58:32
     * @param crmId
     * @param lastupdatetime
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICusContactDao#queryCusContactByCrmId(java.math.BigDecimal, java.util.Date)
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean queryCusContactByCrmId(BigDecimal crmId, Date lastupdatetime) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("modifyDate", lastupdatetime);
	map.put("crmId", crmId);
	map.put("active", null);
	
	List<ContactEntity> list = this.getSqlSession().selectList(NAMESPACE + "queryCusContactByCrmId", map);
	return CollectionUtils.isNotEmpty(list);
    }

    /**
     * 根据手机号码查询联系人信息
     *
     * auther:wangpeng_078816
     * date:2014-4-23
     *
     */
	@Override
	public ContactEntity queryCusContactByMobile(String mobilePhone) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("mobilePhone", mobilePhone);
		List<ContactEntity> list = this.getSqlSession().selectList(NAMESPACE + "queryContactInfo", map);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	/**
     * 根据手机号码或电话号码与名称查询联系人信息
     *
     * auther:WangQianJin
     * date:2014-08-10
     *
     */
	@Override
	public ContactEntity queryCusContactByMobileOrPhoneAndName(CustomerQueryConditionDto queryParam){
		Map<String,String> map = new HashMap<String,String>();
		map.put("mobilePhone", queryParam.getMobilePhone());
		map.put("contactPhone", queryParam.getContactPhone());
		map.put("contactName", queryParam.getContactName());
		List<ContactEntity> list = this.getSqlSession().selectList(NAMESPACE + "queryCusContactByMobileOrPhoneAndName", map);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
    }

    /**
     * 根据CUSTFOSSID查询散客的联系人信息
     *
     * auther:css
     * date:2015-07-15 20:15
     *
     */
	@Override
	public ContactEntity queryCusContactByOwnCustId(String ownCustId) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("ownCustId", ownCustId);		
		List<ContactEntity> list = this.getSqlSession().selectList(NAMESPACE + "queryCusContactByOwnCustId", map);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
    /** 
     * 修改客户联系人信息
     * @author css
     * @date 2015-7-16 16:04:53
     * @param entity
     *            客户联系人信息实体
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICusContactDao#updateCusContact(com.deppon.foss.module.base.baseinfo.api.shared.domain.ContactEntity)
     */
    @Override
    public int updateCusContactByCusfossid(ContactEntity entity) {
	
	return this.getSqlSession().update(NAMESPACE + "updateCusContactByCusfossid", entity);
    }

    /**
     * 根据手机号码验证联系人是否存在
     * auther:273296
     * date:2016-4-14
     *
     */
	@Override
	public boolean queryExistsCusContactByMobileOrTelphone(String mobilePhone,String telphone) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("mobilePhone", mobilePhone);
		map.put("contactPhone", telphone);
		long count = (Long)this.getSqlSession().selectOne(NAMESPACE + "queryExistsCusContactByMobileOrTelphone", map);
		if(count>0){
			return true;
		}
		return false;
	}

	/**
     * 根据crmId验证联系人是否存在
     * auther:273296
     * date:2016-4-14
     *
     */
	@Override
	public boolean queryExistsCusContactByCrmId(BigDecimal crmId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("crmId", crmId);
		long count = (Long)this.getSqlSession().selectOne(NAMESPACE + "queryExistsCusContactByCrmId", map);
		if(count>0){
			return true;
		}
		return false;
	}

}
