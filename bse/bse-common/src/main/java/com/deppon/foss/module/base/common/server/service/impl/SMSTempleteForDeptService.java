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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/server/service/impl/SMSTempleteForDeptService.java
 * 
 * FILE NAME        	: SMSTempleteForDeptService.java
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
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.framework.cache.CacheManager;
import com.deppon.foss.framework.cache.ICache;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.common.api.server.dao.ISMSTempleteForDeptDao;
import com.deppon.foss.module.base.common.api.server.service.ISMSTempleteForDeptService;
import com.deppon.foss.module.base.common.api.shared.domain.TemplateAppOrgEntity;
import com.deppon.foss.module.base.common.api.shared.exception.SMSTempleteForDeptException;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.common.FossTTLCache;
import com.deppon.foss.util.define.FossConstants;

/**
 * 部门短信模板Service接口实现：提供对部门短信部门增删改查操作
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2012-10-19 上午9:55:45
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2012-10-19 上午9:55:45
 * @since
 * @version
 */
public class SMSTempleteForDeptService implements ISMSTempleteForDeptService {

    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(SMSAdvertisingSloganService.class);

    /**
     * 部门短信模版Dao接口.
     */
    private ISMSTempleteForDeptDao smsTempleteForDeptDao;

    /**
     * 组织信息 Service接口.
     */
    private IOrgAdministrativeInfoService orgAdministrativeInfoService;

    /**
     * 设置 部门短信模版Dao接口.
     * 
     * @param smsTempleteForDeptDao
     *            the new 部门短信模版Dao接口
     */
    public void setSmsTempleteForDeptDao(
	    ISMSTempleteForDeptDao smsTempleteForDeptDao) {
	this.smsTempleteForDeptDao = smsTempleteForDeptDao;
    }

    /**
     * 设置 组织信息 Service接口.
     * 
     * @param orgAdministrativeInfoService
     *            the new 组织信息 Service接口
     */
    public void setOrgAdministrativeInfoService(
	    IOrgAdministrativeInfoService orgAdministrativeInfoService) {
	this.orgAdministrativeInfoService = orgAdministrativeInfoService;
    }

    /**
     * 新增部门短信模版.
     * 
     * @param entity
     * @return
     * @throws SMSTempleteForDeptException
     * @author 094463-foss-xieyantao
     * @date 2012-10-19 上午9:55:46
     * @see com.deppon.foss.module.base.common.api.server.service.ISMSTempleteForDeptService#addSMSTempleteForDept(com.deppon.foss.module.base.common.api.shared.domain.TemplateAppOrgEntity)
     */
    @Override
    public int addSMSTempleteForDept(TemplateAppOrgEntity entity)
	    throws SMSTempleteForDeptException {

	if (null != entity) {
	    entity.setId(UUIDUtils.getUUID());
	    entity.setCreateDate(new Date());
	    entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	    entity.setActive(FossConstants.ACTIVE);

	    TemplateAppOrgEntity templateAppOrg = queryAppOrgSmsByParams(
		    entity.getOrgId(), entity.getSmsVirtualCode(),null);
	    if (null != templateAppOrg) {
		throw new SMSTempleteForDeptException("一个部门只能存在一条短信模板！");
	    }

	    return smsTempleteForDeptDao.addSMSTempleteForDept(entity);
	} else {
	    throw new SMSTempleteForDeptException("传入的参数不允许为空！");
	}

    }

    /**
     * 根据Id作废部门短信模版信息.
     * 
     * @param idStr
     * @param modifyUser
     * @return
     * @throws SMSTempleteForDeptException
     * @author 094463-foss-xieyantao
     * @date 2012-10-19 上午9:55:46
     * @see com.deppon.foss.module.base.common.api.server.service.ISMSTempleteForDeptService#deleteSMSTempleteForDeptByCode(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public int deleteSMSTempleteForDeptByCode(String idStr, String modifyUser)
	    throws SMSTempleteForDeptException {

	if (StringUtil.isBlank(idStr)) {
	    throw new SMSTempleteForDeptException("部门短信模板ID不允许为空！");
	} else {
	    LOGGER.debug("idStr:" + idStr);
	    String[] ids = StringUtil.split(idStr, SymbolConstants.EN_COMMA);
	    
	    if(ArrayUtils.isNotEmpty(ids)){
		for(String id : ids){
		    //根据虚拟编码查询短信模板
		    TemplateAppOrgEntity entity = smsTempleteForDeptDao.queryAppOrgEntityById(id);
		    if(null != entity){
			//根据短信模板编码清空缓存
			invalidSMSTemplateAppOrgEntity(montageString(entity.getOrgId(), entity.getSmsVirtualCode()));
		    }
		}
	    }
	    
	    return smsTempleteForDeptDao.deleteSMSTempleteForDeptByCode(ids,
		    modifyUser);
	}
    }

    /**
     * 修改部门短信模版信息.
     * 
     * @param entity
     * @return
     * @throws SMSTempleteForDeptException
     * @author 094463-foss-xieyantao
     * @date 2012-10-19 上午9:55:46
     * @see com.deppon.foss.module.base.common.api.server.service.ISMSTempleteForDeptService#updateSMSTempleteForDept(com.deppon.foss.module.base.common.api.shared.domain.TemplateAppOrgEntity)
     */
    @Override
    public int updateSMSTempleteForDept(TemplateAppOrgEntity entity)
	    throws SMSTempleteForDeptException {
	if (null == entity) {
	    throw new SMSTempleteForDeptException("传入的参数不允许为空！");
	} else {
	    if (StringUtil.isBlank(entity.getId())) {
		throw new SMSTempleteForDeptException("部门短信模板ID不允许为空！");
	    } else {
		LOGGER.debug("id:" + entity.getId());
		//根据短信模板虚拟编码、部门编码清空缓存
		invalidSMSTemplateAppOrgEntity(montageString(entity.getOrgId(), entity.getSmsVirtualCode()));
		//修改时验证需要排除本身
		TemplateAppOrgEntity templateAppOrg = queryAppOrgSmsByParams(
			entity.getOrgId(), entity.getSmsVirtualCode(),entity.getId());
		if (null != templateAppOrg) {
		    throw new SMSTempleteForDeptException("一个部门只能存在一条短信模板！");
		}
		return smsTempleteForDeptDao.updateSMSTempleteForDept(entity);
	    }
	}
    }

    /**
     * 根据传入对象查询符合条件所有部门短信模版信息.
     * 
     * @param entity
     * @return
     * @throws SMSTempleteForDeptException
     * @author 094463-foss-xieyantao
     * @date 2012-10-19 上午9:55:46
     * @see com.deppon.foss.module.base.common.api.server.service.ISMSTempleteForDeptService#querySMSTempleteForDepts(com.deppon.foss.module.base.common.api.shared.domain.TemplateAppOrgEntity,
     *      int, int)
     */
    @Override
    public List<TemplateAppOrgEntity> querySMSTempleteForDepts(
	    TemplateAppOrgEntity entity) throws SMSTempleteForDeptException {
	if (null != entity) {
	    entity.setActive(FossConstants.ACTIVE);
	    if (StringUtil.isNotBlank(entity.getSmsVirtualCode())) {
		LOGGER.debug("smsVirtualCode: " + entity.getSmsVirtualCode());
		return convertInfoList(smsTempleteForDeptDao
			.querySMSTempleteForDepts(entity));
	    } else {
		throw new SMSTempleteForDeptException("短信模板的虚拟编码不允许为空！");
	    }
	} else {
	    throw new SMSTempleteForDeptException("传入的参数不允许为空！");
	}
    }

    /**
     * 统计总记录数.
     * 
     * @param entity
     * @return
     * @throws SMSTempleteForDeptException
     * @author 094463-foss-xieyantao
     * @date 2012-10-19 上午9:55:46
     * @see com.deppon.foss.module.base.common.api.server.service.ISMSTempleteForDeptService#getCount(com.deppon.foss.module.base.common.api.shared.domain.TemplateAppOrgEntity)
     */
    @Override
    public Long queryRecordCount(TemplateAppOrgEntity entity)
	    throws SMSTempleteForDeptException {

	entity.setActive(FossConstants.ACTIVE);

	return smsTempleteForDeptDao.queryRecordCount(entity);
    }

    /**
     * <p>
     * 根据短信的虚拟编码、部门编码查询部门短信信息
     * </p>
     * .
     * 
     * @param orgCode
     *            部门编码
     * @param smsVirtualCode
     *            短信虚拟编码
     * @return
     * @author 094463-foss-xieyantao
     * @date 2012-11-17 上午10:40:25
     * @see com.deppon.foss.module.base.common.api.server.service.ISMSTempleteForDeptService#queryAppOrgSmsByParams(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public TemplateAppOrgEntity queryAppOrgSmsByParams(String orgCode,
	    String smsVirtualCode,String appOrgId) {
	if (StringUtil.isBlank(orgCode) || StringUtil.isBlank(smsVirtualCode)) {
	    return null;
	}
	LOGGER.debug("orgCode:" + orgCode);
	LOGGER.debug("smsVirtualCode:" + smsVirtualCode);
	return convertTemplateAppOrg(smsTempleteForDeptDao.queryAppOrgSmsByParams(orgCode,
		smsVirtualCode,appOrgId));
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
    private TemplateAppOrgEntity convertTemplateAppOrg(
	    TemplateAppOrgEntity entity) {
	if (null == entity) {
	    return null;
	} else {
	    // 通过部门编码获得该部门的实体
	    OrgAdministrativeInfoEntity org = orgAdministrativeInfoService
		    .queryOrgAdministrativeInfoByCode(entity.getOrgId());

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
    private List<TemplateAppOrgEntity> convertInfoList(
	    List<TemplateAppOrgEntity> list) {
	List<TemplateAppOrgEntity> entities = new ArrayList<TemplateAppOrgEntity>();

	if (CollectionUtils.isNotEmpty(list)) {
	    for (TemplateAppOrgEntity entity : list) {
		entity = convertTemplateAppOrg(entity);
		entities.add(entity);
	    }
	    return entities;
	} else {
	    return null;
	}
    }
    
    /**
     * <p>根据短信模板虚拟编码、部门编码查询短信模板信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-4-12 下午3:17:41
     * @param orgCode 部门编码
     * @param smsVirtualCode 短信模板虚拟编码
     * @return
     * @see
     */
    public TemplateAppOrgEntity queryAppOrgByVirtualCodeAndOrgCode(String orgCode,String smsVirtualCode) {
	// 根据key取缓存数据
	TemplateAppOrgEntity entity = querySMSTemplateAppOrgCache(montageString(orgCode,smsVirtualCode));
	if (null == entity) {
	    //根据短信模板虚拟编码、部门编码查询部门短信模板
	    return smsTempleteForDeptDao.queryAppOrgByVirtualCodeAndOrgCode(orgCode, smsVirtualCode);
	} else {
	    return entity;
	}
    }
    
    /**
     * <p>用冒号把部门编码、短信模板虚拟编码拼接起来</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-4-13 上午9:03:40
     * @param orgCode
     * @param smsVirtualCode
     * @return
     * @see
     */
    private String montageString(String orgCode,String smsVirtualCode){
	StringBuffer buffer = new StringBuffer();
	buffer.append(orgCode).append(SymbolConstants.EN_COLON).append(smsVirtualCode);
	
	return buffer.toString();
    }
    
    /**
     * <p>根据key取缓存数据</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-4-12 下午2:52:34
     * @param key
     * @return
     * @see
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private TemplateAppOrgEntity querySMSTemplateAppOrgCache(String key) {
	TemplateAppOrgEntity entity = null;
	try {
	    CacheManager cacheManager = CacheManager.getInstance();
	    if (cacheManager == null) {
		return entity;
	    }
	    ICache cache = cacheManager.getCache(FossTTLCache.TEMPLATEAPPORGENTITY_CACHE_UUID);
	    if (cache == null) {
		return entity;
	    }
	    entity = (TemplateAppOrgEntity) cache.get(key);
	} catch (Exception t) {
	    LOGGER.error("cache找不到", t);
	}
	return entity;
    }
    
    /**
     * <p>根据短信模板虚拟编码、部门编码清空缓存</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-4-12 下午3:07:31
     * @param key
     * @see
     */
    @SuppressWarnings("unchecked")
    private void invalidSMSTemplateAppOrgEntity(String key) {
	((ICache<String, TemplateAppOrgEntity>) CacheManager.getInstance().getCache(FossTTLCache.TEMPLATEAPPORGENTITY_CACHE_UUID)).invalid(key);
    }

}
