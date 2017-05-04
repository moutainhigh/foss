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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/CusContactService.java
 * 
 * FILE NAME        	: CusContactService.java
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
package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.baseinfo.api.server.dao.ICusContactDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICusContactService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ContactEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.CusContactException;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;

/**
 * 客户联系人信息Service接口实现
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2012-11-20 下午6:58:39
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2012-11-20 下午6:58:39
 * @since
 * @version
 */
public class CusContactService implements ICusContactService {

    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(CusContactService.class);

    /**
     * 客户联系人信息DAO接口.
     */
    private ICusContactDao cusContactDao;
    
    /**
     * 设置 客户联系人信息DAO接口.
     *
     * @param cusContactDao the new 客户联系人信息DAO接口
     */
    public void setCusContactDao(ICusContactDao cusContactDao) {
	this.cusContactDao = cusContactDao;
    }
    
    /**
     * 新增客户联系人信息.
     *
     * @param entity 客户联系人信息实体
     * @return 1：成功；-1：失败
     * @throws CusContactException 
     * @author 094463-foss-xieyantao
     * @date 2012-11-20 下午1:49:51
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ICusContactService#addCusContact(com.deppon.foss.module.base.baseinfo.api.shared.domain.ContactEntity)
     */
    @Transactional
    @Override
    public int addCusContact(ContactEntity entity) throws CusContactException {

	if (null == entity) {

	    return FossConstants.FAILURE;
	}
	if(null == entity.getCrmId()){
	    throw new CusContactException("客户联系人信息CRM_ID不允许为空！");
	}
	
	//先验证在数据库是否存在
	boolean isFlag = cusContactDao.queryCusContactByCrmId(entity.getCrmId(),null);
	
	if(isFlag){//存在就修改
	    cusContactDao.updateCusContact(entity);
	}else {
	    entity.setId(UUIDUtils.getUUID());
	    // 第一次记录新增时，虚拟编码为记录的id
	    entity.setVirtualCode(entity.getId());

	    cusContactDao.addCusContact(entity);
	}
	
	return FossConstants.SUCCESS;
    }

    /**
     * 根据code作废客户联系人信息.
     *
     * @param crmId 
     * @param modifyUser 
     * @return 1：成功；-1：失败
     * @throws CusContactException 
     * @author dp-xieyantao
     * @date 2012-11-20 下午1:49:51
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ICusContactService#deleteCusContactByCode(java.lang.String,
     * java.lang.String)
     */
    @Transactional
    @Override
    public int deleteCusContactByCode(BigDecimal crmId, String modifyUser)
	    throws CusContactException {

	if (null == crmId) {
	    throw new CusContactException("客户联系人ID不允许为空！");
	} else {
	    LOGGER.debug("crmId: " + crmId);
	    return cusContactDao.deleteCusContactByCode(crmId, modifyUser);
	}
    }

    /**
     * 修改客户联系人信息 对于有版本控制的编辑，先逻辑删除该记录，再新增一条记录.
     *
     * @param entity 客户联系人信息实体
     * @return 1：成功；-1：失败
     * @throws CusContactException 
     * @author dp-xieyantao
     * @date 2012-11-20 下午1:49:51
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ICusContactService#updateCusContact(com.deppon.foss.module.base.baseinfo.api.shared.domain.ContactEntity)
     */
    @Transactional
    @Override
    public int updateCusContact(ContactEntity entity)
	    throws CusContactException {

	if (null == entity) {
	    return FossConstants.FAILURE;
	}
	if (null == entity.getCrmId()) {
	    throw new CusContactException("客户联系人ID不允许为空！");
	} else {
	    // 作废信息
	   /* int flag = cusContactDao.deleteCusContactByCode(entity.getCrmId(),
		    entity.getModifyUser());

	    if (flag > 0) {// 作废成功
		entity.setActive(FossConstants.ACTIVE);
		entity.setId(UUIDUtils.getUUID());

		return cusContactDao.addCusContact(entity);
	    }*/
	    
	    return cusContactDao.updateCusContact(entity);
//	    return FossConstants.FAILURE;
	}
    }

    /**
     * <p>
     * 根据crmId,最后一次修改时间查询客户联系人是否存在
     * </p>.
     *
     * @param crmId 
     * @param lastupdatetime 
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2012-11-26 上午10:30:05
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ICusContactService#queryCusContactByCrmId(java.math.BigDecimal,
     * java.util.Date)
     */
    @Override
    public boolean queryCusContactByCrmId(BigDecimal crmId, Date lastupdatetime) {
        if(null == crmId){
            throw new CusContactException("客户联系人ID不允许为空！");
	}else {
	    return cusContactDao.queryCusContactByCrmId(crmId, lastupdatetime);
	}
    }

    /**
	 * 新增散客联系人信息
	 *
	 * auther:wangpeng_078816
	 * date:2014-4-22
	 *
	 */
    @Transactional
	@Override
	public int addNonfixedCusContact(ContactEntity entity) {
		if (null == entity) {
		    return FossConstants.FAILURE;
		}
		if(StringUtils.isEmpty(entity.getId())){
			entity.setId(UUIDUtils.getUUID());
		}
		    // 第一次记录新增时，虚拟编码为记录的id
		    entity.setVirtualCode(entity.getId());
		    cusContactDao.addCusContact(entity);
		return FossConstants.SUCCESS;
	}

    /**
     * 根据手机号码查询联系人信息
     *
     * auther:wangpeng_078816
     * date:2014-4-23
     *
     */

	@Override
	public ContactEntity queryCusContactByMobile(String mobile) {
		if(StringUtils.isEmpty(mobile)){
			throw new CusContactException("客户联系人手机号码不允许为空！");
		}
		return cusContactDao.queryCusContactByMobile(mobile);
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
		return cusContactDao.queryCusContactByMobileOrPhoneAndName(queryParam);
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
		return cusContactDao.queryCusContactByOwnCustId(ownCustId);
	}

    /**
     * 修改客户联系人信息
     * @author css
     * @date 2015-07-16 下午4:02:53
     * @param entity
     *            客户联系人信息实体
     * @return 1：成功；-1：失败
     * @see
     */
	@Override
	public int updateCusContactByCusfossid(ContactEntity entity) {
		if (null == entity) {
		    return FossConstants.FAILURE;
		}
		return cusContactDao.updateCusContactByCusfossid(entity);
	}
	
	

	 /**
	 * 根据手机号码，固话验证联系人是否存在
     * auther:273296
     * date:2016-4-14
     *
     */
	@Override
	public boolean queryExistsCusContactByMobileOrTelphone(String mobilePhone,String telphone) {
		return cusContactDao.queryExistsCusContactByMobileOrTelphone(mobilePhone,telphone);
	}

	  /**
		 * 新增客户联系人信息
		 *
		 * auther:wangpeng_078816
		 * date:2014-4-22
		 *
		 */
	@Override
	public int addCusContactNew(ContactEntity contactEntity) {
		if (null == contactEntity) {
		    return FossConstants.FAILURE;
		}
		if(StringUtils.isEmpty(contactEntity.getId()))
				contactEntity.setId(UUIDUtils.getUUID());
		// 第一次记录新增时，虚拟编码为记录的id
		contactEntity.setVirtualCode(contactEntity.getId());
		cusContactDao.addCusContact(contactEntity);
		return FossConstants.SUCCESS;
	}


	/**
     * 根据crmId验证联系人是否存在
     * auther:273296
     * date:2016-4-14
     *
     */
	@Override
	public boolean queryExistsCusContactByCrmId(BigDecimal crmId) {
		return cusContactDao.queryExistsCusContactByCrmId(crmId);
	}



}
