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
 * PROJECT NAME	: bse-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/server/service/impl/SMSAdvertisingSloganForDeptService.java
 * 
 * FILE NAME        	: SMSAdvertisingSloganForDeptService.java
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
package com.deppon.foss.module.base.common.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.common.api.server.dao.ISMSAdvertisingSloganForDeptDao;
import com.deppon.foss.module.base.common.api.server.service.ISMSAdvertisingSloganForDeptService;
import com.deppon.foss.module.base.common.api.shared.domain.SloganAppOrgEntity;
import com.deppon.foss.module.base.common.api.shared.exception.SMSAdVertisingSloganException;
import com.deppon.foss.module.base.common.api.shared.exception.SloganAppOrgException;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.util.define.FossConstants;

/**
 * 部门短信广告语service接口实现：主要对部门短信广告语增删改查操作
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2012-10-17 下午1:46:41
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2012-10-17 下午1:46:41
 * @since
 * @version
 */
public class SMSAdvertisingSloganForDeptService implements
	ISMSAdvertisingSloganForDeptService {

    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(SMSAdvertisingSloganForDeptService.class);

    /**
     * 部门短信广告语Dao接口.
     */
    private ISMSAdvertisingSloganForDeptDao smsAdvertisingSloganForDeptDao;

    /**
     * 组织信息 Service接口.
     */
    private IOrgAdministrativeInfoService orgAdministrativeInfoService;
    
    /**
     * 设置 部门短信广告语Dao接口.
     * 
     * @param smsAdvertisingSloganForDeptDao
     *            the smsAdvertisingSloganForDeptDao to set
     */
    public void setSmsAdvertisingSloganForDeptDao(
	    ISMSAdvertisingSloganForDeptDao smsAdvertisingSloganForDeptDao) {
	this.smsAdvertisingSloganForDeptDao = smsAdvertisingSloganForDeptDao;
    }
    
    /**
     * 设置 组织信息 Service接口.
     *
     * @param orgAdministrativeInfoService the orgAdministrativeInfoService to set
     */
    public void setOrgAdministrativeInfoService(
    	IOrgAdministrativeInfoService orgAdministrativeInfoService) {
        this.orgAdministrativeInfoService = orgAdministrativeInfoService;
    }

    /**
     * 新增部门短信广告语.
     * 
     * @param entity
     * @return
     * @throws SloganAppOrgException
     * @author 094463-foss-xieyantao
     * @date 2012-10-17 下午1:46:41
     * @see com.deppon.foss.module.base.common.api.server.service.ISMSAdvertisingSloganForDeptService#addSMSAdvertisingSloganForDept(com.deppon.foss.module.base.common.api.shared.domain.SloganAppOrgEntity)
     */
    @Transactional
    @Override
    public int addSMSAdvertisingSloganForDept(SloganAppOrgEntity entity)
	    throws SloganAppOrgException {

	if (null == entity) {
	    throw new SMSAdVertisingSloganException("传入的参数不允许为空！");
	} else {
	    SloganAppOrgEntity sloganAppOrg = querySloganAppOrgByCode(entity.getOrgCode(),
		    entity.getSloganCode(),null);
	    if (null != sloganAppOrg) {
		throw new SloganAppOrgException("一个部门只能存在一条短信广告语！");
	    }

	    entity.setActive(FossConstants.ACTIVE);
	    // 类型短信广告语
	    entity.setSloganSort(DictionaryValueConstants.BSE_SLOGAN_TYPE_SMS);
	    return smsAdvertisingSloganForDeptDao
		    .addSMSAdvertisingSloganForDept(entity);
	}
    }

    /**
     * 根据id作废部门短信广告语信息.
     * 
     * @param idstr
     * @param modifyUser
     * @return
     * @throws SloganAppOrgException
     * @author 094463-foss-xieyantao
     * @date 2012-10-17 下午1:46:41
     * @see com.deppon.foss.module.base.common.api.server.service.ISMSAdvertisingSloganForDeptService#deleteSMSAdvertisingSloganForDeptByCode(java.lang.String,
     *      java.lang.String)
     */
    @Transactional
    @Override
    public int deleteSMSAdvertisingSloganForDeptByCode(String idstr,
	    String modifyUser) throws SloganAppOrgException {

	if (StringUtil.isBlank(idstr)) {
	    throw new SMSAdVertisingSloganException("部门短信广告语的ID不允许为空！");
	} else {
	    
	    String[] ids = StringUtil.split(idstr, SymbolConstants.EN_COMMA);
	    return smsAdvertisingSloganForDeptDao
		    .deleteSMSAdvertisingSloganForDeptByCode(ids, modifyUser);
	}
    }

    /**
     * 修改短信广告语信息.
     * 
     * @param entity
     * @return
     * @throws SloganAppOrgException
     * @author 094463-foss-xieyantao
     * @date 2012-10-17 下午1:46:41
     * @see com.deppon.foss.module.base.common.api.server.service.ISMSAdvertisingSloganForDeptService#updateSMSAdvertisingSloganForDept(com.deppon.foss.module.base.common.api.shared.domain.SloganAppOrgEntity)
     */
    @Transactional
    @Override
    public int updateSMSAdvertisingSloganForDept(SloganAppOrgEntity entity)
	    throws SloganAppOrgException {

	if (null == entity) {
	    throw new SMSAdVertisingSloganException("传入的参数不允许为空！");
	} else if (StringUtil.isBlank(entity.getId())) {
	    throw new SMSAdVertisingSloganException("部门短信广告语的ID不允许为空！");
	} else {
	    //修改时验证必须排除本身信息
	    SloganAppOrgEntity sloganAppOrg = querySloganAppOrgByCode(entity.getOrgCode(),
		    entity.getSloganCode(),entity.getId());
	    if (null != sloganAppOrg) {
		throw new SloganAppOrgException("一个部门只能存在一条短信广告语！");
	    }

	    // 类型短信广告语
	    entity.setSloganSort(DictionaryValueConstants.BSE_SLOGAN_TYPE_SMS);
	    return smsAdvertisingSloganForDeptDao
		    .updateSMSAdvertisingSloganForDept(entity);
	}

    }

    /**
     * 根据传入对象查询符合条件所有部门短信广告语信息.
     * 
     * @param entity
     * @param limit
     * @param start
     * @return
     * @throws SloganAppOrgException
     * @author 094463-foss-xieyantao
     * @date 2012-10-17 下午1:46:41
     * @see com.deppon.foss.module.base.common.api.server.service.ISMSAdvertisingSloganForDeptService#querySMSAdvertisingSloganForDepts(com.deppon.foss.module.base.common.api.shared.domain.SloganAppOrgEntity,
     *      int, int)
     */
    @Transactional
    @Override
    public List<SloganAppOrgEntity> querySMSAdvertisingSloganForDepts(
	    SloganAppOrgEntity entity, int limit, int start)
	    throws SloganAppOrgException {

	entity.setActive(FossConstants.ACTIVE);
	// 类型短信广告语
	entity.setSloganSort(DictionaryValueConstants.BSE_SLOGAN_TYPE_SMS);
	return convertInfoList(smsAdvertisingSloganForDeptDao
		.querySMSAdvertisingSloganForDepts(entity, limit, start));
    }

    /**
     * 统计总记录数.
     * 
     * @param entity
     * @return
     * @throws SloganAppOrgException
     * @author 094463-foss-xieyantao
     * @date 2012-10-17 下午1:46:41
     * @see com.deppon.foss.module.base.common.api.server.service.ISMSAdvertisingSloganForDeptService#getCount(com.deppon.foss.module.base.common.api.shared.domain.SloganAppOrgEntity)
     */
    @Transactional
    @Override
    public Long queryRecordCount(SloganAppOrgEntity entity)
	    throws SloganAppOrgException {

	entity.setActive(FossConstants.ACTIVE);
	// 类型短信广告语
	entity.setSloganSort(DictionaryValueConstants.BSE_SLOGAN_TYPE_SMS);
	LOGGER.debug(entity.getSloganSort());
	return smsAdvertisingSloganForDeptDao.queryRecordCount(entity);
    }

    /**
     * <p>
     * 根据短信广告语虚拟编码、部门编码查询部门短信广告语
     * </p>
     * .
     * 
     * @param orgCode
     *            部门编码
     * @param smsSloganVirtualCode
     *            短信广告语虚拟编码
     * @return
     * @author 094463-foss-xieyantao
     * @date 2013-1-10 下午2:55:12
     * @see com.deppon.foss.module.base.common.api.server.service.ISMSAdvertisingSloganForDeptService#querySloganAppOrgByCode(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public SloganAppOrgEntity querySloganAppOrgByCode(String orgCode,
	    String smsSloganVirtualCode,String sloganAppOrgId) {
	if (StringUtil.isBlank(orgCode)
		|| StringUtil.isBlank(smsSloganVirtualCode)) {
	    throw new SloganAppOrgException("传入的参数不允许为空！");
	} else {
	    return convertTemplateAppOrg(smsAdvertisingSloganForDeptDao.querySloganAppOrgByCode(
		    orgCode, smsSloganVirtualCode,null));
	}
    }
    
    /**
     * <p>
     * 填充部门名称
     * </p>
     * .
     * 
     * @param entity
     * @return
     * @author 094463-foss-xieyantao
     * @date 2012-11-12 上午9:12:38
     * @see
     */
    private SloganAppOrgEntity convertTemplateAppOrg(
	    SloganAppOrgEntity entity) {
	if (null == entity) {
	    return null;
	} else {
	    // 通过部门编码获得该部门的实体
	    OrgAdministrativeInfoEntity org = orgAdministrativeInfoService
		    .queryOrgAdministrativeInfoByCode(entity.getOrgCode());

	    if (null != org) {
		// 设置管理公司名称
		entity.setOrgName(org.getName());
	    }
	    
	    return entity;
	}

    }

    /**
     * <p>
     * 填充部门名称
     * </p>
     * .
     * 
     * @param list
     * @return
     * @author 094463-foss-xieyantao
     * @date 2012-11-12 上午9:13:00
     * @see
     */
    private List<SloganAppOrgEntity> convertInfoList(
	    List<SloganAppOrgEntity> list) {
	List<SloganAppOrgEntity> entities = new ArrayList<SloganAppOrgEntity>();

	if (CollectionUtils.isNotEmpty(list)) {
	    for (SloganAppOrgEntity entity : list) {
		entity = convertTemplateAppOrg(entity);
		entities.add(entity);
	    }
	    return entities;
	} else {
	    return null;
	}
    }


}
