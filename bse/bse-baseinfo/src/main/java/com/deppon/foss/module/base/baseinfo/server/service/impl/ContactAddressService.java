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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/ContactAddressService.java
 * 
 * FILE NAME        	: ContactAddressService.java
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IContactAddressDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IContactAddressService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ContactAddressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.ContactAddressException;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 联系人接送货地址Service接口实现
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2012-11-20 下午6:46:53
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2012-11-20 下午6:46:53
 * @since
 * @version
 */
public class ContactAddressService implements IContactAddressService {

    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(ContactAddressService.class);

    /**
     * 联系人接送货地址DAO接口.
     */
    private IContactAddressDao contactAddressDao;
    
    /**
     * 设置 联系人接送货地址DAO接口.
     *
     * @param contactAddressDao the new 联系人接送货地址DAO接口
     */
    public void setContactAddressDao(IContactAddressDao contactAddressDao) {
	this.contactAddressDao = contactAddressDao;
    }
    
    /**
     * 新增联系人接送货地址.
     *
     * @param entity 联系人接送货地址实体
     * @return 1：成功；-1：失败 * @see
     * com.deppon.foss.module.base.baseinfo.api.server.
     * service.IContactAddressService
     * #addContactAddress(com.deppon.foss.module
     * .base.baseinfo.api.shared.domain.ContactAddressEntity)
     * @throws ContactAddressException 
     * @author 094463-foss-xieyantao
     * @date 2012-11-20 下午1:49:51
     */
    @Transactional
    @Override
    public int addContactAddress(ContactAddressEntity entity)
	    throws ContactAddressException {

	if (null == entity) {

	    throw new ContactAddressException("传入的参数不允许为空！");
	} else {
	    if (null == entity.getCrmId()) {
		throw new ContactAddressException("联系人接送货地址CRM_ID不允许为空！");
	    }
	    
	    // 先验证在数据库是否存在
	    boolean isFlag = contactAddressDao.queryContactAddressByCrmId(entity.getCrmId(), null);

	    if (isFlag) {// 存在就修改
		contactAddressDao.updateContactAddress(entity);
	    } else {
		entity.setId(UUIDUtils.getUUID());

		contactAddressDao.addContactAddress(entity);
	    }
	    
	    return FossConstants.SUCCESS;
	}
    }

    /**
     * 根据code作废联系人接送货地址.
     *
     * @param crmId 
     * @param modifyUser 
     * @return 1：成功；-1：失败
     * @throws ContactAddressException 
     * @author dp-xieyantao
     * @date 2012-11-20 下午1:49:51
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IContactAddressService#deleteContactAddressByCode(java.lang.String,
     * java.lang.String)
     */
    @Transactional
    @Override
    public int deleteContactAddressByCode(BigDecimal crmId, String modifyUser)
	    throws ContactAddressException {

	if (null == crmId) {
	    throw new ContactAddressException("crmId 不允许为空！");
	}else {
	    LOGGER.debug("crmId :" + crmId);
	    return contactAddressDao.deleteContactAddressByCode(crmId, modifyUser);
	}
    }

    /**
     * 修改联系人接送货地址.
     *
     * @param entity 联系人接送货地址实体
     * @return 1：成功；-1：失败
     * @throws ContactAddressException 
     * @author dp-xieyantao
     * @date 2012-11-20 下午1:49:51
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IContactAddressService#updateContactAddress(com.deppon.foss.module.base.baseinfo.api.shared.domain.ContactAddressEntity)
     */
    @Transactional
    @Override
    public int updateContactAddress(ContactAddressEntity entity)
	    throws ContactAddressException {

	if (null == entity) {
	    throw new ContactAddressException("传入的参数不允许为空！");
	}
	if (null == entity.getCrmId()) {
	    throw new ContactAddressException("crmId 不允许为空！");
	}
	/*// 作废记录
	int flag = contactAddressDao.deleteContactAddressByCode(
		entity.getCrmId(), entity.getModifyUser());

	if (flag > 0) {// 作废成功
	    entity.setActive(FossConstants.ACTIVE);
	    entity.setId(UUIDUtils.getUUID());

	    return contactAddressDao.addContactAddress(entity);
	}*/
	
	return contactAddressDao.updateContactAddress(entity);
//	return FossConstants.FAILURE;
    }

    /**
     * <p>
     * 根据crmId,最后一次修改时间查询联系人接送货地址是否存在
     * </p>.
     *
     * @param crmId 
     * @param lastupdatetime 
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2012-11-26 上午10:34:00
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IContactAddressService#queryContactAddressByCrmId(java.math.BigDecimal,
     * java.util.Date)
     */
    @Override
    public boolean queryContactAddressByCrmId(BigDecimal crmId,
	    Date lastupdatetime) {

	return contactAddressDao.queryContactAddressByCrmId(crmId,
		lastupdatetime);
    }

}
