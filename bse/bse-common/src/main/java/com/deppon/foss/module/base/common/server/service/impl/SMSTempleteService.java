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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/server/service/impl/SMSTempleteService.java
 * 
 * FILE NAME        	: SMSTempleteService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.common.server.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.framework.cache.CacheManager;
import com.deppon.foss.framework.cache.ICache;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.common.api.server.dao.ISMSTempleteDao;
import com.deppon.foss.module.base.common.api.server.service.ISMSTempleteForDeptService;
import com.deppon.foss.module.base.common.api.server.service.ISMSTempleteService;
import com.deppon.foss.module.base.common.api.shared.domain.SMSTemplateEntity;
import com.deppon.foss.module.base.common.api.shared.domain.TemplateAppOrgEntity;
import com.deppon.foss.module.base.common.api.shared.dto.SmsParamDto;
import com.deppon.foss.module.base.common.api.shared.exception.SMSTempleteException;
import com.deppon.foss.util.common.FossTTLCache;
import com.deppon.foss.util.define.FossConstants;

/**
 * 短信模板service接口实现：提供对短信模版的增删改查的基本操作
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2012-10-13
 * 上午11:14:53
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2012-10-13 上午11:14:53
 * @since
 * @version
 */
public class SMSTempleteService implements ISMSTempleteService {
    
    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SMSTempleteService.class);

    /**
     * 短信模版Dao接口.
     */
    private ISMSTempleteDao sMSTempleteDao;
    
    /**
     * 行政组织接口.
     */
    private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
    
    /**
     * 部门短信模板Service接口.
     */
    private ISMSTempleteForDeptService sMSTempleteForDeptService;

    /**
     * 短信模版Dao接口.
     *
     * @param sMSTempleteDao 
     */
    public void setsMSTempleteDao(ISMSTempleteDao sMSTempleteDao) {
        this.sMSTempleteDao = sMSTempleteDao;
    }

    /**
     * 设置 行政组织接口.
     *
     * @param orgAdministrativeInfoComplexService the new 行政组织接口
     */
    public void setOrgAdministrativeInfoComplexService(
    	IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
        this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
    }
    
    /**
     * 
     *
     * @param sMSTempleteForDeptService 
     */
    public void setsMSTempleteForDeptService(
    	ISMSTempleteForDeptService sMSTempleteForDeptService) {
        this.sMSTempleteForDeptService = sMSTempleteForDeptService;
    }

    /**
     * 新增短信模版.
     *
     * @param entity 
     * @return 
     * @throws SMSTempleteException 
     * @author 094463-foss-xieyantao
     * @date 2012-10-13 上午11:19:40
     * @see com.deppon.foss.module.base.common.api.server.service.ISMSTempleteService#addSMSTemplete(com.deppon.foss.module.base.common.api.shared.domain.SMSTemplateEntity)
     */
    @Transactional
    @Override
    public int addSMSTemplete(SMSTemplateEntity entity)
	    throws SMSTempleteException {
	if(null == entity){
	    throw new SMSTempleteException("传入的参数不允许为空！");
	}else {
	    SMSTemplateEntity smsTemplate = querySmsTemplateByCode(entity.getSmsCode(),null);
	    if(null != smsTemplate){
		throw new SMSTempleteException("短信模板代码不允许重复！");
	    }
	    smsTemplate = querySmsTemplateByName(entity.getSmsName(),null);
	    if(null != smsTemplate){
		throw new SMSTempleteException("短信模板名称不允许重复！");
	    }
	}
	return sMSTempleteDao.addSMSTemplete(entity);

    }

    /**
     * 根据code作废短信模版信息.
     *
     * @param codeStr 虚拟编码拼接的字符串
     * @param modifyUser 
     * @return 
     * @throws SMSTempleteException 
     * @author 094463-foss-xieyantao
     * @date 2012-10-13 上午11:19:53
     * @see com.deppon.foss.module.base.common.api.server.service.ISMSTempleteService#deleteSMSTempleteByCode(java.lang.String[])
     */
    @Transactional
    @Override
    public int deleteSMSTempleteByCode(String codeStr, String modifyUser)
	    throws SMSTempleteException {

	if (StringUtil.isBlank(codeStr)) {
	    throw new SMSTempleteException("虚拟编码不允许为空！");
	} else {
	    LOGGER.debug("codeStr:"+codeStr);
	    
	    String[]codes = StringUtil.split(codeStr, SymbolConstants.EN_COMMA);
	    
	    if(ArrayUtils.isNotEmpty(codes)){
		for(String virtualCode : codes){
		    //根据虚拟编码查询短信模板
		    SMSTemplateEntity entity = sMSTempleteDao.querySmsByVirtualCode(virtualCode);
		    if(null != entity){
			//根据短信模板编码清空缓存
			invalidSMSTemplateEntity(entity.getSmsCode());
		    }
		}
	    }

	    return sMSTempleteDao.deleteSMSTempleteByCode(codes, modifyUser);
	}
    }
    
    /**
     * 修改短信模版信息.
     *
     * @param entity 
     * @return 
     * @throws SMSTempleteException 
     * @author 094463-foss-xieyantao
     * @date 2012-10-13 上午11:20:05
     * @see com.deppon.foss.module.base.common.api.server.service.ISMSTempleteService#updateSMSTemplete(com.deppon.foss.module.base.common.api.shared.domain.SMSTemplateEntity)
     */
    @Transactional
    @Override
    public int updateSMSTemplete(SMSTemplateEntity entity)
	    throws SMSTempleteException {

	if (null == entity) {
	    return FossConstants.FAILURE;
	} else if (StringUtil.isBlank(entity.getVirtualCode())) {
	    throw new SMSTempleteException("短信模板虚拟编码不允许为空！");
	}else {
	    SMSTemplateEntity smsTemplate = querySmsTemplateByCode(entity.getSmsCode(),entity.getId());
	    if(null != smsTemplate){
		throw new SMSTempleteException("短信模板代码不允许重复！");
	    }
	    smsTemplate = querySmsTemplateByName(entity.getSmsName(),entity.getId());
	    if(null != smsTemplate){
		throw new SMSTempleteException("短信模板名称不允许重复！");
	    }
	    //根据短信模板编码清空缓存
	    invalidSMSTemplateEntity(entity.getSmsCode());
	}
	LOGGER.debug("VirtualCode: "+entity.getVirtualCode());
	
   
	return sMSTempleteDao.updateSMSTemplete(entity);
    }

    /**
     * 根据传入对象查询符合条件所有短信模版信息.
     *
     * @param entity 
     * @param limit 
     * @param start 
     * @return 
     * @throws SMSTempleteException 
     * @author 094463-foss-xieyantao
     * @date 2012-10-13 上午11:20:20
     * @see com.deppon.foss.module.base.common.api.server.service.ISMSTempleteService#querySMSTempletes(com.deppon.foss.module.base.common.api.shared.domain.SMSTemplateEntity,
     * int, int)
     */
    @Transactional
    @Override
    public List<SMSTemplateEntity> querySMSTempletes(SMSTemplateEntity entity,
	    int limit, int start) throws SMSTempleteException {
	entity.setActive(FossConstants.ACTIVE);
	return sMSTempleteDao.querySMSTempletes(entity, limit, start);
    }

    /**
     * 统计总记录数.
     *
     * @param entity 
     * @return 
     * @throws SMSTempleteException 
     * @author 094463-foss-xieyantao
     * @date 2012-10-13 上午11:20:28
     * @see com.deppon.foss.module.base.common.api.server.service.ISMSTempleteService#getCount(com.deppon.foss.module.base.common.api.shared.domain.SMSTemplateEntity)
     */
    @Transactional
    @Override
    public Long queryRecordCount(SMSTemplateEntity entity)
	    throws SMSTempleteException {
	entity.setActive(FossConstants.ACTIVE);
	return sMSTempleteDao.queryRecordCount(entity);
    }
    
    /**
     * <p>根据传入的参数查询符合条件的短信</p>.
     *
     * @param dto 
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2012-11-16 下午4:41:37
     * @see com.deppon.foss.module.base.common.api.server.service.ISMSTempleteService#querySmsByParam(com.deppon.foss.module.base.common.api.shared.dto.SmsParamDto)
     */
    @Transactional
    @Override
    public String querySmsByParam(SmsParamDto dto) {
	
	if(null == dto){
	    return null;
	}
	
	if(StringUtil.isBlank(dto.getSmsCode())){//短信模板编码不允许空
	    throw new SMSTempleteException("短信模板编码不允许为空！");
	}else {
	    LOGGER.debug("SMSCode: "+dto.getSmsCode());
	    //获取短信模板
//	    SMSTemplateEntity entity = sMSTempleteDao.querySmsBySmsCode(dto.getSmsCode(),null);
	    //先从缓存取数据，如果缓存没有数据，再从数据库里面查询数据
	    SMSTemplateEntity entity = querySMSTemplateByCode(dto.getSmsCode());
	    if(null == entity){
		return null;
	    }else {
		//获取短信模板内容
		String sms = entity.getContent();
		String message = "";
		if(StringUtil.isBlank(dto.getOrgCode())){
		    if(dto.getMap()!= null){
			message = repalceParam(dto.getMap(),sms);
		    }
		    return message;
		}else {
		    //根据部门编码查询本部门以及所有的上级部门
		    List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllUpByCode(dto.getOrgCode());
		    if(CollectionUtils.isEmpty(orgList)){
			return null;
		    }
		    String orgCode = dto.getOrgCode();
		    TemplateAppOrgEntity appOrgEntity = null;
		    for(int i = 0; i < orgList.size();i++){
			//根据短信的虚拟编码、部门编码查询部门短信信息 queryAppOrgByVirtualCodeAndOrgCode
			//appOrgEntity = sMSTempleteForDeptService.queryAppOrgSmsByParams(orgCode, entity.getVirtualCode(),null);
			appOrgEntity = sMSTempleteForDeptService.queryAppOrgByVirtualCodeAndOrgCode(orgCode, entity.getVirtualCode());
			if(appOrgEntity != null){
			    //获得部门短信模板内容
			    sms = appOrgEntity.getSmsContent();
			    break;
			}else {
			    //获取上级部门编码
			    orgCode = getParentOrgCode(orgList, orgCode);
			}
		    }
		    message = repalceParam(dto.getMap(), sms);
		    return message;
		}
	    }
	}
    }
    
    /**
     * <p>根据传入的部门编码查询上级部门的部门编码</p>.
     *
     * @param list 
     * @param orgCode 
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2012-11-17 上午8:11:55
     * @see
     */
    private String getParentOrgCode(List<OrgAdministrativeInfoEntity> list,String orgCode){
	
	Map<String, OrgAdministrativeInfoEntity> codeMap = new HashMap<String, OrgAdministrativeInfoEntity>();
	Map<String, OrgAdministrativeInfoEntity> unicodeMap = new HashMap<String, OrgAdministrativeInfoEntity>();
	for(OrgAdministrativeInfoEntity entity : list){
	    //组织编码作为key把集合中的组织实体放在map中
	    codeMap.put(entity.getCode(), entity);
	    //组织的标杆编码作为key把集合中的组织实体放在map中
	    unicodeMap.put(entity.getUnifiedCode(), entity);
	}
	LOGGER.debug("orgCode: "+ orgCode);
	//通过部门编码查询该部门的实体
	OrgAdministrativeInfoEntity orgEntity = codeMap.get(orgCode);
	
	if(orgEntity != null){
	    //如果上级标杆编码为空
	    if(StringUtil.isNotBlank(orgEntity.getParentOrgUnifiedCode())){
		
		OrgAdministrativeInfoEntity parentOrg = unicodeMap.get(orgEntity.getParentOrgUnifiedCode());
		if(null != parentOrg){
		  //通过部门的上级部门标杆编码查询上级部门编码
		    return parentOrg.getCode();
		}else {
		    return orgCode;
		}
	    }
	    return orgCode;
	}
	//没有上级，返回本部门的部门编码
	return orgCode;
    }
    
    /**
     * <p>替换短信内容里面的参数</p>.
     *
     * @param map 
     * @param smsContent 
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2012-11-17 上午11:21:10
     * @see
     */
    private String repalceParam(Map<String,String> map,String smsContent){
	if(StringUtil.isNotBlank(smsContent)){
	    for(Map.Entry<String, String> entry : map.entrySet()){
		    //获取参数对应的字符串
		    String paramValue = entry.getValue();
		    String paramKey = entry.getKey();
		    //把短信模板的内容根据传入的参数替换
		    smsContent = smsContent.replace("$"+paramKey+"$",paramValue != null ? paramValue : "");
	    }
	}
	
	return smsContent;
    }
    
    /**
     * <p>验证短信模板编码是否唯一</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-1-12 下午3:49:02
     * @param smsCode 模板编码
     * @param smsId 模板ID
     * @return 
     * @see com.deppon.foss.module.base.common.api.server.service.ISMSTempleteService#querySmsTemplateByCode(java.lang.String)
     */
    @Override
    public SMSTemplateEntity querySmsTemplateByCode(String smsCode,String smsId) {
	if(StringUtil.isBlank(smsCode)){
	    throw new SMSTempleteException("短信模板代码不允许为空！");
	}else {
	    return sMSTempleteDao.querySmsBySmsCode(smsCode,smsId);
	}
    }
    
    /**
     * <p>验证短信模板名称是否唯一</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-1-12 下午3:49:02
     * @param smsName 模板名称
     * @param smsId 模板ID
     * @return 
     * @see com.deppon.foss.module.base.common.api.server.service.ISMSTempleteService#querySmsTemplateByName(java.lang.String)
     */
    @Override
    public SMSTemplateEntity querySmsTemplateByName(String smsName,String smsId) {
	if(StringUtil.isBlank(smsName)){
	    throw new SMSTempleteException("短信模板名称不允许为空！");
	}else {
	    return sMSTempleteDao.querySmsTemplateByName(smsName,smsId);
	}
    }
    
    /**
     * <p>根据短信模板编码查询短信模板信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-4-12 下午3:17:41
     * @param smsCode
     * @return
     * @see
     */
    public SMSTemplateEntity querySMSTemplateByCode(String smsCode) {
	// 根据key取缓存数据
	SMSTemplateEntity entity = querySMSTemplateCache(smsCode);
	if (null == entity) {
	    return sMSTempleteDao.querySmsByCode(smsCode);
	} else {
	    return entity;
	}
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
    private SMSTemplateEntity querySMSTemplateCache(String key) {
	SMSTemplateEntity entity = null;
	try {
	    CacheManager cacheManager = CacheManager.getInstance();
	    if (cacheManager == null) {
		return entity;
	    }
	    ICache cache = cacheManager.getCache(FossTTLCache.SMSTEMPLATE_CACHE_UUID);
	    if (cache == null) {
		return entity;
	    }
	    entity = (SMSTemplateEntity) cache.get(key);
	} catch (Exception t) {
	    LOGGER.error("cache找不到", t);
	}
	return entity;
    }
    
    /**
     * <p>根据短信模板编码清空缓存</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-4-12 下午3:07:31
     * @param key
     * @see
     */
    @SuppressWarnings("unchecked")
    private void invalidSMSTemplateEntity(String key) {
	((ICache<String, SMSTemplateEntity>) CacheManager.getInstance().getCache(FossTTLCache.SMSTEMPLATE_CACHE_UUID)).invalid(key);
    }

}
