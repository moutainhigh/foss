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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/LoadAndUnloadSquadService.java
 * 
 * FILE NAME        	: LoadAndUnloadSquadService.java
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

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadSquadDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILoadAndUnloadSquadService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPorterService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadSquadEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PorterEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.AirAgencyCompanyException;

/**
 * 装卸车小队 Service实现
 * 
 * @author 087584-foss-lijun
 * @date 2012-11-2 下午2:25:2
 */
public class LoadAndUnloadSquadService implements ILoadAndUnloadSquadService {
	/**
     * 日志.
     */
   // private static final Logger LOGGER = LoggerFactory.getLogger(LoadAndUnloadSquadAction.class);

    /**
     * 装卸车小队 新增
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:25:2
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILoadAndUnloadSquadService#addLoadAndUnloadSquad(com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadSquadEntity)
     */
    @Override
    public LoadAndUnloadSquadEntity addLoadAndUnloadSquad(LoadAndUnloadSquadEntity entity) {
	//检查参数的合法性
	if (null == entity) {
	    return null;
	}
	LoadAndUnloadSquadEntity alreadyExistsEntity = queryLoadAndUnloadSquadByCode(entity.getCode());
	if(alreadyExistsEntity!=null){
		throw new AirAgencyCompanyException("装卸车小队编码已存在");
		
	}else{
		return loadAndUnloadSquadDao.addLoadAndUnloadSquad(entity);
		
	}
    }

    /**
     * 通过code标识来删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:25:2
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadSquadDao#deleteLoadAndUnloadSquad(java.lang.String)
     */
    @Override
    @Transactional
    public LoadAndUnloadSquadEntity deleteLoadAndUnloadSquad(LoadAndUnloadSquadEntity entity) {
	// 请求合法性判断：
	if (null == entity || StringUtils.isBlank(entity.getCode())) {
	    return null;
	}
	return loadAndUnloadSquadDao.deleteLoadAndUnloadSquad(entity);
    }

    /**
     * 通过code标识来批量删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:25:2
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadSquadDao#deleteLoadAndUnloadSquadMore(java.lang.String[])
     */
    @Override
    @Transactional
    public LoadAndUnloadSquadEntity deleteLoadAndUnloadSquadMore(String[] codes , String deleteUser) {
	return loadAndUnloadSquadDao.deleteLoadAndUnloadSquadMore(codes, deleteUser);
    }

    /**
     * 更新
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:25:2
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadSquadDao#updateLoadAndUnloadSquad(com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadSquadEntity)
     */
    @Override
    @Transactional
    public LoadAndUnloadSquadEntity updateLoadAndUnloadSquad(LoadAndUnloadSquadEntity entity) {
	//检查参数的合法性
	if (null == entity || StringUtils.isBlank(entity.getCode())) {
	    return null;
	}
	//先根据id查询出原来code与修改后的code作比较，如果不一样则要验证code唯一性；
	LoadAndUnloadSquadEntity loadAndUnloadSquadEntity = new LoadAndUnloadSquadEntity();
	loadAndUnloadSquadEntity.setId(entity.getId());
	loadAndUnloadSquadEntity.setActive("Y");
	List<LoadAndUnloadSquadEntity> reluts = this.queryLoadAndUnloadSquadExactByEntity(loadAndUnloadSquadEntity,0,NumberConstants.NUMBER_1000);
	for(LoadAndUnloadSquadEntity squadEntity:reluts){
		if(!StringUtils.equals(squadEntity.getCode(), entity.getCode())){
			LoadAndUnloadSquadEntity alreadyExistsEntity = queryLoadAndUnloadSquadByCode(entity.getCode());
			if(alreadyExistsEntity!=null){
				throw new AirAgencyCompanyException("装卸车小队编码已存在");
			}
		}
	}
	return loadAndUnloadSquadDao.updateLoadAndUnloadSquad(entity);
    }



    /**
     * 以下全为查询 
     */
	
    /**
     * 精确查询 
     * 通过 CODE 查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:25:2
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadSquadService#queryLoadAndUnloadSquadByCode(java.lang.String)
     */
    @Override
    public LoadAndUnloadSquadEntity queryLoadAndUnloadSquadByCode(String code) {
	if (null == code) {
	    return null;
	}
	LoadAndUnloadSquadEntity entityResult = loadAndUnloadSquadDao.queryLoadAndUnloadSquadByCode(code);
	entityResult = this.attachOrg(entityResult);
	entityResult = this.attachPorter(entityResult);
	
	return entityResult;
    }


    /**
     * 精确查询 
     * 根据多个编码批量查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:1:47
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadSquadService#queryLoadAndUnloadSquadByCode(java.lang.String)
     */
    @Override
    public List<LoadAndUnloadSquadEntity> queryLoadAndUnloadSquadBatchByCode(
	    String[] codes) {
	if (codes==null||codes.length==0){
	    return null;
	}
	
	List<LoadAndUnloadSquadEntity> entityResults = loadAndUnloadSquadDao.queryLoadAndUnloadSquadBatchByCode(codes);
	entityResults = this.attachOrg(entityResults);
	entityResults = this.attachPorter(entityResults);
	
	return entityResults;
    }

    /** 
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:11:15
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadSquadService#queryLoadAndUnloadSquadExactByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadSquadEntity, int, int)
     */
    @Override
    public List<LoadAndUnloadSquadEntity> queryLoadAndUnloadSquadExactByEntity(
	    LoadAndUnloadSquadEntity entity, int start, int limit) {
	List<LoadAndUnloadSquadEntity> entityResults = loadAndUnloadSquadDao
		.queryLoadAndUnloadSquadExactByEntity(entity, start, limit);
	entityResults = this.attachOrg(entityResults);
	entityResults = this.attachPorter(entityResults);
	
	return entityResults;
    }

    /**
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:09:53
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadSquadService#queryLoadAndUnloadSquadExactByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadSquadEntity)
     */
    @Override
    public long queryLoadAndUnloadSquadExactByEntityCount(LoadAndUnloadSquadEntity entity) {
	return loadAndUnloadSquadDao.queryLoadAndUnloadSquadExactByEntityCount(entity);
    }
 
    /**
     * 模糊查询 
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:25:2
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadSquadService#deleteLoadAndUnloadSquadMore(java.lang.String[])
     */
    @Override
    public List<LoadAndUnloadSquadEntity> queryLoadAndUnloadSquadByEntity(
	    LoadAndUnloadSquadEntity entity, int start, int limit) {
	List<LoadAndUnloadSquadEntity> entityResults = loadAndUnloadSquadDao.queryLoadAndUnloadSquadByEntity(entity, start, limit);
	entityResults = this.attachOrg(entityResults);
	entityResults = this.attachPorter(entityResults);
	return entityResults;
    }

    /**
     * 动态的查询条件-查询总条数。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:25:2
     * @see com.deppon.foss.module.baseinfo.server.service.ILoadAndUnloadSquadService#queryLoadAndUnloadSquadCountByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.district.shared.domain.LoadAndUnloadSquadEntity)
     */
    @Override
    public long queryLoadAndUnloadSquadByEntityCount(LoadAndUnloadSquadEntity entity) {
	return loadAndUnloadSquadDao.queryLoadAndUnloadSquadByEntityCount(entity);
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
    public LoadAndUnloadSquadEntity attachOrg(LoadAndUnloadSquadEntity entity){
	if(entity == null || StringUtils.isBlank(entity.getParentOrgCode())){
	    return entity;
	}
	OrgAdministrativeInfoEntity orgAdministrativeInfo = orgAdministrativeInfoService
		.queryOrgAdministrativeInfoByCode(entity.getParentOrgCode());
	if(orgAdministrativeInfo != null){
	    entity.setParentOrgName(orgAdministrativeInfo.getName());
	}
	return entity;
    }
    
    
    /**
     * 给部门加上部门名称
     * 
     * @author 087584-foss-lijun
     * @date 2012-12-3 下午4:55:43
     */
    public List<LoadAndUnloadSquadEntity> attachOrg(List<LoadAndUnloadSquadEntity> entitys){
	if(CollectionUtils.isEmpty(entitys)){
	    return entitys;
	}

	for(LoadAndUnloadSquadEntity entity : entitys){
	    this.attachOrg(entity);
	}

	return entitys;
    }
    
    
    /**
     * 添加装卸车小队
     * 
     * @author 087584-foss-lijun
     * @date 2012-12-3 下午4:55:43
     */
    public LoadAndUnloadSquadEntity attachPorter(LoadAndUnloadSquadEntity entity){
	// ParentOrgCode为装卸车小队的编码
	if(entity == null || StringUtils.isBlank(entity.getCode())){
	    return entity;
	}
	
	String[] codes= {entity.getCode()};
	List<PorterEntity> entitys = porterService.queryPorterBatchByParentOrgCode(codes);
	entity.setPorters(entitys);

	return entity;
    }
    
    
    /**
     * 添加装卸车小队
     * 
     * @author 087584-foss-lijun
     * @date 2012-12-3 下午4:55:43
     */
    public List<LoadAndUnloadSquadEntity> attachPorter(List<LoadAndUnloadSquadEntity> entitys){
	if(CollectionUtils.isEmpty(entitys)){
	    return entitys;
	}

	for(LoadAndUnloadSquadEntity entity : entitys){
	    this.attachPorter(entity);
	}

	return entitys;
    }
    
    

    /**
     * 下面是dao对象的声明及get,set方法：
     */
    private ILoadAndUnloadSquadDao loadAndUnloadSquadDao;

    private IOrgAdministrativeInfoService orgAdministrativeInfoService;
    
    private IPorterService porterService;

    public void setLoadAndUnloadSquadDao(ILoadAndUnloadSquadDao loadAndUnloadSquadDao) {
	this.loadAndUnloadSquadDao = loadAndUnloadSquadDao;
    }

    
    public void setOrgAdministrativeInfoService(
    	IOrgAdministrativeInfoService orgAdministrativeInfoService) {
        this.orgAdministrativeInfoService = orgAdministrativeInfoService;
    }

    
    public void setPorterService(IPorterService porterService) {
        this.porterService = porterService;
    }
    
    
}
