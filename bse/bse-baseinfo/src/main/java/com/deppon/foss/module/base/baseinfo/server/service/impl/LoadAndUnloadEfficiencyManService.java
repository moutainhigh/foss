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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/LoadAndUnloadEfficiencyManService.java
 * 
 * FILE NAME        	: LoadAndUnloadEfficiencyManService.java
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

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.base.util.define.SysCtrlConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadEfficiencyManDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILoadAndUnloadEfficiencyManService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncEfficiencyService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadEfficiencyManEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LoadAndUnloadEfficiencyManException;
import com.deppon.foss.util.define.FossConstants;

/**
 * 装卸车标准-吨-人天 Service实现
 * 
 * @author 087584-foss-lijun
 * @date 2012-11-2 上午13:49:37
 */
public class LoadAndUnloadEfficiencyManService implements ILoadAndUnloadEfficiencyManService {

    /**
     * 装卸车标准-吨-人天 新增
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 上午13:49:37
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILoadAndUnloadEfficiencyManService#addLoadAndUnloadEfficiencyMan(com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadEfficiencyManEntity)
     */
    @Override
    @Transactional
    public LoadAndUnloadEfficiencyManEntity addLoadAndUnloadEfficiencyMan(
	    LoadAndUnloadEfficiencyManEntity entity)
	    throws LoadAndUnloadEfficiencyManException {
	//检查参数的合法性
	if (null == entity) {
	    return null;
	}
	
	LoadAndUnloadEfficiencyManEntity entityCondition = this
		.queryLoadAndUnloadEfficiencyManByOrgCode(entity.getOrgCode());
	if(entityCondition != null){
	    throw new LoadAndUnloadEfficiencyManException("此部门已在装卸车人力效率标准中存在","此部门已在装卸车人力效率标准中存在");
	}
	
	entity = loadAndUnloadEfficiencyManDao.addLoadAndUnloadEfficiencyMan(entity);
	if(null != entity){
		//  同步装卸车标准时间信息去其他系统
		List<LoadAndUnloadEfficiencyManEntity> entitys = new ArrayList<LoadAndUnloadEfficiencyManEntity>();
		entity = this.attachOrg(entity);
		entitys.add(entity);
		syncEfficiencDataToOther(entitys, NumberConstants.NUMBER_1);
	}
	
	
	return entity;
    }

    /**
     * 通过code标识来删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 上午13:49:37
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadEfficiencyManDao#deleteLoadAndUnloadEfficiencyMan(java.lang.String)
     */
    @Override
    @Transactional
    public LoadAndUnloadEfficiencyManEntity deleteLoadAndUnloadEfficiencyMan(LoadAndUnloadEfficiencyManEntity entity)  throws BusinessException {
	// 请求合法性判断：
	if (null == entity || StringUtils.isBlank(entity.getOrgCode())) {
	    return null;
	}
	
	entity = loadAndUnloadEfficiencyManDao.deleteLoadAndUnloadEfficiencyMan(entity);
	
	
	if(null != entity){
		//  同步装卸车标准时间信息去其他系统
		List<LoadAndUnloadEfficiencyManEntity> entitys = new ArrayList<LoadAndUnloadEfficiencyManEntity>();
		entitys.add(entity);
		syncEfficiencDataToOther(entitys, NumberConstants.NUMBER_3);
	}
	
	
	return entity;
    }

    /**
     * 通过code标识来批量删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 上午13:49:37
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadEfficiencyManDao#deleteLoadAndUnloadEfficiencyManMore(java.lang.String[])
     */
    @Override
    @Transactional
    public LoadAndUnloadEfficiencyManEntity deleteLoadAndUnloadEfficiencyManMore(String[] codes , String deleteUser)  throws BusinessException {
    	LoadAndUnloadEfficiencyManEntity entity = loadAndUnloadEfficiencyManDao.deleteLoadAndUnloadEfficiencyManMore(codes, deleteUser);
    	if(null != entity){
    		//  同步装卸车标准时间信息去其他系统
    		List<LoadAndUnloadEfficiencyManEntity> entitys = new ArrayList<LoadAndUnloadEfficiencyManEntity>();
    		for(String code : codes){
    			LoadAndUnloadEfficiencyManEntity delteEntity = new LoadAndUnloadEfficiencyManEntity();
    			delteEntity.setActive(FossConstants.INACTIVE);
    			delteEntity.setModifyDate(new Date());
    			delteEntity.setModifyUser(deleteUser);
    			delteEntity.setOrgCode(code);
    			entitys.add(delteEntity);
    		}
        	syncEfficiencDataToOther(entitys, NumberConstants.NUMBER_3);
    	}
    	return entity;
    }

    /**
     * 更新
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 上午13:49:37
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadEfficiencyManDao#updateLoadAndUnloadEfficiencyMan(com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadEfficiencyManEntity)
     */
    @Override
    @Transactional
    public LoadAndUnloadEfficiencyManEntity updateLoadAndUnloadEfficiencyMan(LoadAndUnloadEfficiencyManEntity entity) {
	//检查参数的合法性
	if (null == entity || StringUtils.isBlank(entity.getOrgCode())) {
	    return null;
	}
	entity = loadAndUnloadEfficiencyManDao.updateLoadAndUnloadEfficiencyMan(entity);
	if(null != entity){
		//  同步装卸车标准时间信息去其他系统
		List<LoadAndUnloadEfficiencyManEntity> entitys = new ArrayList<LoadAndUnloadEfficiencyManEntity>();
		entity = this.attachOrg(entity);
		entitys.add(entity);
		syncEfficiencDataToOther(entitys, NumberConstants.NUMBER_2);
	}
	
	return entity;
    }



    /**
     * 以下全为查询 
     */
	
    /**
     * 精确查询 
     * 通过 ORG_CODE 查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 上午13:49:37
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadEfficiencyManService#queryLoadAndUnloadEfficiencyManByCode(java.lang.String)
     */
    @Override
    public LoadAndUnloadEfficiencyManEntity queryLoadAndUnloadEfficiencyManByOrgCode(String code) {
	if (StringUtils.isBlank(code)) {
	    return null;
	}
	LoadAndUnloadEfficiencyManEntity entityResult = loadAndUnloadEfficiencyManDao.queryLoadAndUnloadEfficiencyManByOrgCode(code);
	entityResult = this.attachOrg(entityResult);
	return entityResult;
    }

    /**
     * 
     * <p>查询装卸车效率，如果本部门没有则依次找上级部门的</p> 
     * @author foss-zhujunyong
     * @date Apr 17, 2013 5:17:55 PM
     * @param code
     * @return
     * @see
     */
    @Override
    public LoadAndUnloadEfficiencyManEntity queryLoadAndUnloadEfficiencyManUpByOrgCode(String code) {
	LoadAndUnloadEfficiencyManEntity result = null;
	int i = 0;
	while (StringUtils.isNotBlank(code) && i < SysCtrlConstants.ORG_QUERY_RECURRENCE_NUM) {
	    result = queryLoadAndUnloadEfficiencyManByOrgCode(code);
	    if (result != null) {
		break;
	    }
	    OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(code);
	    if (org == null) {
		break;
	    }
	    code = org.getParentOrgCode();
	}
	return result;
    }
    
    

    /**
     * 精确查询 
     * 根据多个编码批量查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:1:47
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadEfficiencyManService#queryLoadAndUnloadEfficiencyManByCode(java.lang.String)
     */
    @Override
    public List<LoadAndUnloadEfficiencyManEntity> queryLoadAndUnloadEfficiencyManBatchByOrgCode(
	    String[] codes) {
	if (codes==null||codes.length==0){
	    return null;
	}
	
	return loadAndUnloadEfficiencyManDao.queryLoadAndUnloadEfficiencyManBatchByOrgCode(codes);
    }

    /** 
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:11:15
     */
    @Override
    public List<LoadAndUnloadEfficiencyManEntity> queryLoadAndUnloadEfficiencyManExactByEntity(
	    LoadAndUnloadEfficiencyManEntity entity, int start, int limit) {
	List<LoadAndUnloadEfficiencyManEntity> entityResults = loadAndUnloadEfficiencyManDao
		.queryLoadAndUnloadEfficiencyManExactByEntity(entity, start,
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
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadEfficiencyManService#queryLoadAndUnloadEfficiencyManExactByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadEfficiencyManEntity)
     */
    @Override
    public long queryLoadAndUnloadEfficiencyManExactByEntityCount(LoadAndUnloadEfficiencyManEntity entity) {
	return loadAndUnloadEfficiencyManDao.queryLoadAndUnloadEfficiencyManExactByEntityCount(entity);
    }
 
    /**
     * 模糊查询 
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 上午13:49:37
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadEfficiencyManService#deleteLoadAndUnloadEfficiencyManMore(java.lang.String[])
     */
    @Override
    public List<LoadAndUnloadEfficiencyManEntity> queryLoadAndUnloadEfficiencyManByEntity(
	    LoadAndUnloadEfficiencyManEntity entity, int start, int limit) {
	List<LoadAndUnloadEfficiencyManEntity> entityResults = loadAndUnloadEfficiencyManDao
		.queryLoadAndUnloadEfficiencyManByEntity(entity, start, limit);

	entityResults = this.attachOrg(entityResults);
	return entityResults;
    }

    /**
     * 动态的查询条件-查询总条数。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 上午13:49:37
     * @see com.deppon.foss.module.baseinfo.server.service.ILoadAndUnloadEfficiencyManService#queryLoadAndUnloadEfficiencyManCountByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.district.shared.domain.LoadAndUnloadEfficiencyManEntity)
     */
    @Override
    public long queryLoadAndUnloadEfficiencyManByEntityCount(LoadAndUnloadEfficiencyManEntity entity) {
	return loadAndUnloadEfficiencyManDao.queryLoadAndUnloadEfficiencyManByEntityCount(entity);
    }

    
    
    /**
     * 下面是特殊方法
     */
    
    
    
    /**
     * 给部门加上部门名称
     * 
     * @author 087584-foss-lijun
     * @date 2012-12-3 下午4:55:43
     */
    public LoadAndUnloadEfficiencyManEntity attachOrg(LoadAndUnloadEfficiencyManEntity entity){
	if(entity == null || StringUtils.isBlank(entity.getOrgCode())){
	    return entity;
	}
	entity.setOrgName(orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(entity.getOrgCode()));
	return entity;
    }
    
    
    /**
     * 给部门加上部门名称
     * 
     * @author 087584-foss-lijun
     * @date 2012-12-3 下午4:55:43
     */
    public List<LoadAndUnloadEfficiencyManEntity> attachOrg(List<LoadAndUnloadEfficiencyManEntity> entitys){
	if(CollectionUtils.isEmpty(entitys)){
	    return entitys;
	}

	for(LoadAndUnloadEfficiencyManEntity entity : entitys){
	    this.attachOrg(entity);
	}

	return entitys;
    }
    

    /**
     * 下面是dao对象的声明及get,set方法：
     */
    private ILoadAndUnloadEfficiencyManDao loadAndUnloadEfficiencyManDao;

    private IOrgAdministrativeInfoService orgAdministrativeInfoService;
    
    private ISyncEfficiencyService syncEfficiencyService;
    
    public void setLoadAndUnloadEfficiencyManDao(ILoadAndUnloadEfficiencyManDao loadAndUnloadEfficiencyManDao) {
	this.loadAndUnloadEfficiencyManDao = loadAndUnloadEfficiencyManDao;
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
    private void syncEfficiencDataToOther(List<LoadAndUnloadEfficiencyManEntity> entitys, int operationType)  throws BusinessException{
    	syncEfficiencyService.syncEfficiency(entitys, operationType);
    }
}
