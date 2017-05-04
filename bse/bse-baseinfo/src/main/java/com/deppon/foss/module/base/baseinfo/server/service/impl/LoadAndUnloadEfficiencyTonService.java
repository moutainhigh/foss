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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/LoadAndUnloadEfficiencyTonService.java
 * 
 * FILE NAME        	: LoadAndUnloadEfficiencyTonService.java
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.base.util.define.SysCtrlConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadEfficiencyTonDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILoadAndUnloadEfficiencyTonService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncEfficiencyService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadEfficiencyTonEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LoadAndUnloadEfficiencyTonException;
import com.deppon.foss.util.define.FossConstants;

/**
 * 装卸车标准-吨-时间 Service实现
 * 
 * @author 087584-foss-lijun
 * @date 2012-11-2 下午2:6:55
 */
public class LoadAndUnloadEfficiencyTonService implements ILoadAndUnloadEfficiencyTonService {

    /**
     * 装卸车标准-吨-时间 新增
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:6:55
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILoadAndUnloadEfficiencyTonService#addLoadAndUnloadEfficiencyTon(com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadEfficiencyTonEntity)
     */
    @Override
    @Transactional
    public LoadAndUnloadEfficiencyTonEntity addLoadAndUnloadEfficiencyTon(
	    LoadAndUnloadEfficiencyTonEntity entity)
	    throws LoadAndUnloadEfficiencyTonException {
	//检查参数的合法性
	if (null == entity) {
	    return null;
	}
	
	LoadAndUnloadEfficiencyTonEntity entityCondition = this
		.queryLoadAndUnloadEfficiencyTonByOrgCode(entity.getOrgCode());
	if(entityCondition != null){
	    throw new LoadAndUnloadEfficiencyTonException("","此部门的装卸车效率标准已经存在");
	}
	LoadAndUnloadEfficiencyTonEntity newEntity = loadAndUnloadEfficiencyTonDao.addLoadAndUnloadEfficiencyTon(entity);
	if(null != newEntity){
		//  同步装卸车效率标准信息去其他系统
		List<LoadAndUnloadEfficiencyTonEntity> entitys = new ArrayList<LoadAndUnloadEfficiencyTonEntity>();
		newEntity = this.attachOrg(newEntity);
		entitys.add(newEntity);
		syncEfficiencDataToOther(entitys, NumberConstants.NUMBER_1);
	}
	
	return newEntity;
    }

    /**
     * 通过code标识来删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:6:55
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadEfficiencyTonDao#deleteLoadAndUnloadEfficiencyTon(java.lang.String)
     */
    @Override
    @Transactional
    public LoadAndUnloadEfficiencyTonEntity deleteLoadAndUnloadEfficiencyTon(LoadAndUnloadEfficiencyTonEntity entity)  throws BusinessException {
	// 请求合法性判断：
	if (null == entity || StringUtils.isBlank(entity.getOrgCode())) {
	    return null;
	}
	
	LoadAndUnloadEfficiencyTonEntity newEntity = loadAndUnloadEfficiencyTonDao.deleteLoadAndUnloadEfficiencyTon(entity);
	if(null != newEntity){
		//  同步装卸车效率标准信息去其他系统
		List<LoadAndUnloadEfficiencyTonEntity> entitys = new ArrayList<LoadAndUnloadEfficiencyTonEntity>();
		entitys.add(newEntity);
		syncEfficiencDataToOther(entitys, NumberConstants.NUMBER_3);
	}
	
	return newEntity;
    }

    /**
     * 通过code标识来批量删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:6:55
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadEfficiencyTonDao#deleteLoadAndUnloadEfficiencyTonMore(java.lang.String[])
     */
    @Override
    @Transactional
    public LoadAndUnloadEfficiencyTonEntity deleteLoadAndUnloadEfficiencyTonMore(String[] codes , String deleteUser)  throws BusinessException {
    	LoadAndUnloadEfficiencyTonEntity entity = loadAndUnloadEfficiencyTonDao.deleteLoadAndUnloadEfficiencyTonMore(codes, deleteUser);
    	if(null != entity){
    		//  同步装卸车效率标准信息去其他系统
        	List<LoadAndUnloadEfficiencyTonEntity> entitys = new ArrayList<LoadAndUnloadEfficiencyTonEntity>();
        	for(String code : codes){
        		LoadAndUnloadEfficiencyTonEntity deleteEntity = new LoadAndUnloadEfficiencyTonEntity();
        		deleteEntity.setActive(FossConstants.INACTIVE);
    			deleteEntity.setModifyDate(new Date());
    			deleteEntity.setModifyUser(deleteUser);
    			deleteEntity.setOrgCode(code);
    			entitys.add(deleteEntity);
        	}
        	
        	syncEfficiencDataToOther(entitys, NumberConstants.NUMBER_3);
    	}
    	
    	return entity;
    }

    /**
     * 更新
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:6:55
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadEfficiencyTonDao#updateLoadAndUnloadEfficiencyTon(com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadEfficiencyTonEntity)
     */
    @Override
    @Transactional
    public LoadAndUnloadEfficiencyTonEntity updateLoadAndUnloadEfficiencyTon(LoadAndUnloadEfficiencyTonEntity entity) {
	//检查参数的合法性
	if (null == entity || StringUtils.isBlank(entity.getOrgCode())) {
	    return null;
	}
	
	LoadAndUnloadEfficiencyTonEntity newEntity = loadAndUnloadEfficiencyTonDao.updateLoadAndUnloadEfficiencyTon(entity);
	if(null != newEntity){
		//  同步装卸车效率标准信息去其他系统
		List<LoadAndUnloadEfficiencyTonEntity> entitys = new ArrayList<LoadAndUnloadEfficiencyTonEntity>();
		newEntity = this.attachOrg(newEntity);
		entitys.add(newEntity);
		syncEfficiencDataToOther(entitys, NumberConstants.NUMBER_2);
	}
	
	return newEntity;
    }



    /**
     * 以下全为查询 
     */
	
    /**
     * 精确查询 
     * 通过 ORG_CODE 查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:6:55
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadEfficiencyTonService#queryLoadAndUnloadEfficiencyTonByCode(java.lang.String)
     */
    @Override
    public LoadAndUnloadEfficiencyTonEntity queryLoadAndUnloadEfficiencyTonByOrgCode(String code) {
	if (StringUtils.isBlank(code)) {
	    return null;
	}
	
	LoadAndUnloadEfficiencyTonEntity result = loadAndUnloadEfficiencyTonDao.queryLoadAndUnloadEfficiencyTonByOrgCode(code);
	int i = 0;
	while (result == null && i < SysCtrlConstants.ORG_QUERY_RECURRENCE_NUM) {
	    OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(code);
	    if (org == null || StringUtils.isBlank(org.getParentOrgCode())) {
		break;
	    }
	    code = org.getParentOrgCode();
	    result = loadAndUnloadEfficiencyTonDao.queryLoadAndUnloadEfficiencyTonByOrgCode(code);
	    i++;
	}
	
	result = this.attachOrg(result);
	return result;
    }


    /**
     * 精确查询 
     * 根据多个编码批量查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:1:47
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadEfficiencyTonService#queryLoadAndUnloadEfficiencyTonByCode(java.lang.String)
     */
    @Override
    public List<LoadAndUnloadEfficiencyTonEntity> queryLoadAndUnloadEfficiencyTonBatchByOrgCode(
	    String[] codes) {
	if (codes==null||codes.length==0){
	    return null;
	}
	
	List<LoadAndUnloadEfficiencyTonEntity> entityResults = loadAndUnloadEfficiencyTonDao
		.queryLoadAndUnloadEfficiencyTonBatchByOrgCode(codes);
	entityResults = this.attachOrg(entityResults);
	return entityResults;
    }

    /** 
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:11:15
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadEfficiencyTonService#queryLoadAndUnloadEfficiencyTonExactByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadEfficiencyTonEntity, int, int)
     */
    @Override
    public List<LoadAndUnloadEfficiencyTonEntity> queryLoadAndUnloadEfficiencyTonExactByEntity(
	    LoadAndUnloadEfficiencyTonEntity entity, int start, int limit) {
	List<LoadAndUnloadEfficiencyTonEntity> entityResults = loadAndUnloadEfficiencyTonDao
		.queryLoadAndUnloadEfficiencyTonExactByEntity(entity, start,
			limit);
	entityResults = this.attachOrg(entityResults);
	return entityResults;
    }

    /**
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:09:53
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadEfficiencyTonService#queryLoadAndUnloadEfficiencyTonExactByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadEfficiencyTonEntity)
     */
    @Override
    public long queryLoadAndUnloadEfficiencyTonExactByEntityCount(LoadAndUnloadEfficiencyTonEntity entity) {
	return loadAndUnloadEfficiencyTonDao.queryLoadAndUnloadEfficiencyTonExactByEntityCount(entity);
    }
 
    /**
     * 模糊查询 
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:6:55
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadEfficiencyTonService#deleteLoadAndUnloadEfficiencyTonMore(java.lang.String[])
     */
    @Override
    public List<LoadAndUnloadEfficiencyTonEntity> queryLoadAndUnloadEfficiencyTonByEntity(
	    LoadAndUnloadEfficiencyTonEntity entity, int start, int limit) {
	List<LoadAndUnloadEfficiencyTonEntity> entityResults = loadAndUnloadEfficiencyTonDao
		.queryLoadAndUnloadEfficiencyTonByEntity(entity, start, limit);
	entityResults = this.attachOrg(entityResults);
	return entityResults;
    }

    /**
     * 动态的查询条件-查询总条数。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:6:55
     * @see com.deppon.foss.module.baseinfo.server.service.ILoadAndUnloadEfficiencyTonService#queryLoadAndUnloadEfficiencyTonCountByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.district.shared.domain.LoadAndUnloadEfficiencyTonEntity)
     */
    @Override
    public long queryLoadAndUnloadEfficiencyTonByEntityCount(LoadAndUnloadEfficiencyTonEntity entity) {
	return loadAndUnloadEfficiencyTonDao.queryLoadAndUnloadEfficiencyTonByEntityCount(entity);
    }
    
    
    
    /**
     * 下面是工具方法
     */
    
    
    /**
     * 给部门加上部门名称
     * 
     * @author 087584-foss-lijun
     * @date 2012-12-3 下午4:55:43
     */
    public LoadAndUnloadEfficiencyTonEntity attachOrg(LoadAndUnloadEfficiencyTonEntity entity){
	if(entity == null || StringUtils.isBlank(entity.getOrgCode())){
	    return entity;
	}
	OrgAdministrativeInfoEntity orgAdministrativeInfo = orgAdministrativeInfoService
		.queryOrgAdministrativeInfoByCode(entity.getOrgCode());
	if(orgAdministrativeInfo != null){
	    entity.setOrgName(orgAdministrativeInfo.getName());
	}
	return entity;
    }
    
    
    /**
     * 给部门加上部门名称
     * 
     * @author 087584-foss-lijun
     * @date 2012-12-3 下午4:55:43
     */
    public List<LoadAndUnloadEfficiencyTonEntity> attachOrg(List<LoadAndUnloadEfficiencyTonEntity> entitys){
	if(CollectionUtils.isEmpty(entitys)){
	    return entitys;
	}

	for(LoadAndUnloadEfficiencyTonEntity entity : entitys){
	    this.attachOrg(entity);
	}

	return entitys;
    }
    
    /**
     * 下面是dao对象的声明及get,set方法：
     */
    private ILoadAndUnloadEfficiencyTonDao loadAndUnloadEfficiencyTonDao;
    
    private IOrgAdministrativeInfoService orgAdministrativeInfoService;
    
    private ISyncEfficiencyService syncEfficiencyService;

    public void setLoadAndUnloadEfficiencyTonDao(ILoadAndUnloadEfficiencyTonDao loadAndUnloadEfficiencyTonDao) {
	this.loadAndUnloadEfficiencyTonDao = loadAndUnloadEfficiencyTonDao;
    }

    
    public void setOrgAdministrativeInfoService(
    	IOrgAdministrativeInfoService orgAdministrativeInfoService) {
        this.orgAdministrativeInfoService = orgAdministrativeInfoService;
    }

	public void setSyncEfficiencyService(
			ISyncEfficiencyService syncEfficiencyService) {
		this.syncEfficiencyService = syncEfficiencyService;
	}
    
	/**
	 * 装卸车效率标准信息同步
	 * @author 310854
	 * @date 2016-4-7
	 */
    private void syncEfficiencDataToOther(List<LoadAndUnloadEfficiencyTonEntity> entitys, int operationType)  throws BusinessException{
    	syncEfficiencyService.syncEfficiency(entitys, operationType);
    }
}
