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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/LoadAndUnloadEfficiencyVehicleService.java
 * 
 * FILE NAME        	: LoadAndUnloadEfficiencyVehicleService.java
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
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadEfficiencyVehicleDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleTypeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILoadAndUnloadEfficiencyVehicleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncEfficiencyService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadEfficiencyVehicleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleTypeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LoadAndUnloadEfficiencyVehicleException;
import com.deppon.foss.util.define.FossConstants;

/**
 * 装卸车标准-车-时间 Service实现
 * 
 * @author 087584-foss-lijun
 * @date 2012-11-2 下午2:14:1
 */
public class LoadAndUnloadEfficiencyVehicleService implements ILoadAndUnloadEfficiencyVehicleService {

    /**
     * 装卸车标准-车-时间 新增
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:14:1
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILoadAndUnloadEfficiencyVehicleService#addLoadAndUnloadEfficiencyVehicle(com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadEfficiencyVehicleEntity)
     */
    @Override
    @Transactional
    public LoadAndUnloadEfficiencyVehicleEntity addLoadAndUnloadEfficiencyVehicle(
	    LoadAndUnloadEfficiencyVehicleEntity entity)
	    throws LoadAndUnloadEfficiencyVehicleException {
	//检查参数的合法性
	if (null == entity) {
	    return null;
	}
	
	LoadAndUnloadEfficiencyVehicleEntity entityCondition = new LoadAndUnloadEfficiencyVehicleEntity();
	entityCondition.setOrgCode(entity.getOrgCode());
	entityCondition.setVehicleTypeLength(entity.getVehicleTypeLength());
	List<LoadAndUnloadEfficiencyVehicleEntity> entityResults = this
		.queryLoadAndUnloadEfficiencyVehicleExactByEntity(
			entityCondition, NumberConstants.NUMERAL_ZORE,
			Integer.MAX_VALUE);
	if(CollectionUtils.isNotEmpty(entityResults)){
	    throw new LoadAndUnloadEfficiencyVehicleException("","已存在相同的部门编码和车型长度");
	}

	entity = loadAndUnloadEfficiencyVehicleDao.addLoadAndUnloadEfficiencyVehicle(entity);
	if(null != entity){
		//  同步装卸车标准时间信息去其他系统
		List<LoadAndUnloadEfficiencyVehicleEntity> entitys = new ArrayList<LoadAndUnloadEfficiencyVehicleEntity>();
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
     * @date 2012-11-2 下午2:14:1
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadEfficiencyVehicleDao#deleteLoadAndUnloadEfficiencyVehicle(java.lang.String)
     */
    @Override
    @Transactional
    public LoadAndUnloadEfficiencyVehicleEntity deleteLoadAndUnloadEfficiencyVehicle(LoadAndUnloadEfficiencyVehicleEntity entity)  throws BusinessException {
	// 请求合法性判断：
	if (null == entity || StringUtils.isBlank(entity.getVirtualCode())) {
	    return null;
	}
	
	entity = loadAndUnloadEfficiencyVehicleDao.deleteLoadAndUnloadEfficiencyVehicle(entity);
	if(null != entity){
		//  同步装卸车标准时间信息去其他系统
		List<LoadAndUnloadEfficiencyVehicleEntity> entitys = new ArrayList<LoadAndUnloadEfficiencyVehicleEntity>();
		entitys.add(entity);
		syncEfficiencDataToOther(entitys, NumberConstants.NUMBER_3);
	}
	
	return entity;
    }

    /**
     * 通过code标识来批量删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:14:1
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadEfficiencyVehicleDao#deleteLoadAndUnloadEfficiencyVehicleMore(java.lang.String[])
     */
    @Override
    @Transactional
    public LoadAndUnloadEfficiencyVehicleEntity deleteLoadAndUnloadEfficiencyVehicleMore(String[] codes , String deleteUser)  throws BusinessException {
    	LoadAndUnloadEfficiencyVehicleEntity entity = loadAndUnloadEfficiencyVehicleDao.deleteLoadAndUnloadEfficiencyVehicleMore(codes, deleteUser);
    	if(null != entity){
    		List<LoadAndUnloadEfficiencyVehicleEntity> entitys = new ArrayList<LoadAndUnloadEfficiencyVehicleEntity>();
    		for(String code : codes){
    			LoadAndUnloadEfficiencyVehicleEntity deleteEntity = new LoadAndUnloadEfficiencyVehicleEntity();
    			deleteEntity.setActive(FossConstants.INACTIVE);
    			deleteEntity.setModifyDate(new Date());
    			deleteEntity.setModifyUser(deleteUser);
    			deleteEntity.setVirtualCode(code);
    			entitys.add(deleteEntity);
    		}
    	
    		//  同步装卸车标准时间信息去其他系统
        	syncEfficiencDataToOther(entitys, NumberConstants.NUMBER_3);
    	}
    	
    	return entity;
    }

    /**
     * 更新
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:14:1
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadEfficiencyVehicleDao#updateLoadAndUnloadEfficiencyVehicle(com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadEfficiencyVehicleEntity)
     */
    @Override
    @Transactional
    public LoadAndUnloadEfficiencyVehicleEntity updateLoadAndUnloadEfficiencyVehicle(LoadAndUnloadEfficiencyVehicleEntity entity) {
	//检查参数的合法性
	if (null == entity || StringUtils.isBlank(entity.getVirtualCode())) {
	    return null;
	}
	
	entity = loadAndUnloadEfficiencyVehicleDao.updateLoadAndUnloadEfficiencyVehicle(entity);
	if(null != entity){
		//  同步装卸车标准时间信息去其他系统
		List<LoadAndUnloadEfficiencyVehicleEntity> entitys = new ArrayList<LoadAndUnloadEfficiencyVehicleEntity>();
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
     * 通过 VIRTUAL_CODE 查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:14:1
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadEfficiencyVehicleService#queryLoadAndUnloadEfficiencyVehicleByCode(java.lang.String)
     */
    @Override
    public LoadAndUnloadEfficiencyVehicleEntity queryLoadAndUnloadEfficiencyVehicleByVirtualCode(String code) {
	if (null == code) {
	    return null;
	}
	
	LoadAndUnloadEfficiencyVehicleEntity entityResult = loadAndUnloadEfficiencyVehicleDao
		.queryLoadAndUnloadEfficiencyVehicleByVirtualCode(code);
	entityResult = attachVehicleLength(entityResult);
	entityResult = this.attachOrg(entityResult);
	return entityResult;
    }


    /**
     * 精确查询 
     * 根据多个编码批量查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:1:47
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadEfficiencyVehicleService#queryLoadAndUnloadEfficiencyVehicleByCode(java.lang.String)
     */
    @Override
    public List<LoadAndUnloadEfficiencyVehicleEntity> queryLoadAndUnloadEfficiencyVehicleBatchByVirtualCode(
	    String[] codes) {
	if (ArrayUtils.isEmpty(codes)){
	    return null;
	}
	
	List<LoadAndUnloadEfficiencyVehicleEntity> entityResults = loadAndUnloadEfficiencyVehicleDao
		.queryLoadAndUnloadEfficiencyVehicleBatchByVirtualCode(codes);
	entityResults = attachVehicleLength(entityResults);
	entityResults = this.attachOrg(entityResults);
	return entityResults;
    }

    /** 
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:11:15
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadEfficiencyVehicleService#queryLoadAndUnloadEfficiencyVehicleExactByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadEfficiencyVehicleEntity, int, int)
     */
    @Override
    public List<LoadAndUnloadEfficiencyVehicleEntity> queryLoadAndUnloadEfficiencyVehicleExactByEntity(
	    LoadAndUnloadEfficiencyVehicleEntity entity, int start, int limit) {
	List<LoadAndUnloadEfficiencyVehicleEntity> entityResults = loadAndUnloadEfficiencyVehicleDao
		.queryLoadAndUnloadEfficiencyVehicleExactByEntity(entity,
			start, limit);
	entityResults = attachVehicleLength(entityResults);
	entityResults = this.attachOrg(entityResults);
	return entityResults;
    }

    /**
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:09:53
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadEfficiencyVehicleService#queryLoadAndUnloadEfficiencyVehicleExactByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadEfficiencyVehicleEntity)
     */
    @Override
    public long queryLoadAndUnloadEfficiencyVehicleExactByEntityCount(LoadAndUnloadEfficiencyVehicleEntity entity) {
	return loadAndUnloadEfficiencyVehicleDao.queryLoadAndUnloadEfficiencyVehicleExactByEntityCount(entity);
    }
 
    /**
     * 模糊查询 
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:14:1
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadEfficiencyVehicleService#deleteLoadAndUnloadEfficiencyVehicleMore(java.lang.String[])
     */
    @Override
    public List<LoadAndUnloadEfficiencyVehicleEntity> queryLoadAndUnloadEfficiencyVehicleByEntity(
	    LoadAndUnloadEfficiencyVehicleEntity entity, int start, int limit) {
	List<LoadAndUnloadEfficiencyVehicleEntity> entityResults = loadAndUnloadEfficiencyVehicleDao
		.queryLoadAndUnloadEfficiencyVehicleByEntity(entity, start,
			limit);

	entityResults = this.attachVehicleLength(entityResults);
	entityResults = this.attachOrg(entityResults);
	return entityResults;
    }

    /**
     * 动态的查询条件-查询总条数。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:14:1
     * @see com.deppon.foss.module.baseinfo.server.service.ILoadAndUnloadEfficiencyVehicleService#queryLoadAndUnloadEfficiencyVehicleCountByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.district.shared.domain.LoadAndUnloadEfficiencyVehicleEntity)
     */
    @Override
    public long queryLoadAndUnloadEfficiencyVehicleByEntityCount(LoadAndUnloadEfficiencyVehicleEntity entity) {
	return loadAndUnloadEfficiencyVehicleDao.queryLoadAndUnloadEfficiencyVehicleByEntityCount(entity);
    }
    
    
    /**
     * 下面是特殊查询方法
     */
	
    /**
     * 精确查询 
     * 
     * 通过部门编码，车长编码  查询出一条装卸车标准
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:14:1
     */
    @Override
    public LoadAndUnloadEfficiencyVehicleEntity queryLoadAndUnloadEfficiencyVehicleByOrgVehicleLength(
	    String orgCode, String vehicleLength) {
	if (StringUtils.isEmpty(orgCode) || StringUtils.isEmpty(vehicleLength)) {
	    return null;
	}
	
	LoadAndUnloadEfficiencyVehicleEntity entityCondition = new LoadAndUnloadEfficiencyVehicleEntity();
	entityCondition.setOrgCode(orgCode);
	entityCondition.setVehicleTypeLength(vehicleLength);
	List<LoadAndUnloadEfficiencyVehicleEntity> entityResults = this
		.queryLoadAndUnloadEfficiencyVehicleExactByEntity(
			entityCondition, NumberConstants.ZERO,
			Integer.MAX_VALUE);
	if(CollectionUtils.isNotEmpty(entityResults)){
	    return entityResults.get(NumberConstants.ZERO);
	}
	
	return null;
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
    public LoadAndUnloadEfficiencyVehicleEntity attachOrg(LoadAndUnloadEfficiencyVehicleEntity entity){
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
    public List<LoadAndUnloadEfficiencyVehicleEntity> attachOrg(List<LoadAndUnloadEfficiencyVehicleEntity> entitys){
	if(CollectionUtils.isEmpty(entitys)){
	    return entitys;
	}

	for(LoadAndUnloadEfficiencyVehicleEntity entity : entitys){
	    this.attachOrg(entity);
	}

	return entitys;
    }
    
    
    /**
     * 给车长编码加上车长度
     * 
     * @author 087584-foss-lijun
     * @date 2012-12-3 下午4:55:43
     */
    public LoadAndUnloadEfficiencyVehicleEntity attachVehicleLength(LoadAndUnloadEfficiencyVehicleEntity entity){
	if(entity == null || StringUtils.isBlank(entity.getVehicleTypeLength())){
	    return entity;
	}
	VehicleTypeEntity vehicleType = leasedVehicleTypeService
		.queryLeasedVehicleTypeByVehicleLengthCode(entity.getVehicleTypeLength());
	if(vehicleType != null){
	    entity.setVehicleLength(vehicleType.getVehicleLength());
	}
	return entity;
    }
    
    
    /**
     * 给车长编码加上车长度
     * 
     * @author 087584-foss-lijun
     * @date 2012-12-3 下午4:55:43
     */
    public List<LoadAndUnloadEfficiencyVehicleEntity> attachVehicleLength(
	    List<LoadAndUnloadEfficiencyVehicleEntity> entitys) {
	if(CollectionUtils.isEmpty(entitys)){
	    return entitys;
	}

	for(LoadAndUnloadEfficiencyVehicleEntity entity : entitys){
	    this.attachVehicleLength(entity);
	}

	return entitys;
    }

    /**
     * 下面是dao对象的声明及get,set方法：
     */
    private ILoadAndUnloadEfficiencyVehicleDao loadAndUnloadEfficiencyVehicleDao;
    
    private IOrgAdministrativeInfoService orgAdministrativeInfoService;
    
    private ILeasedVehicleTypeService leasedVehicleTypeService;
    
    private ISyncEfficiencyService syncEfficiencyService;

    public void setLoadAndUnloadEfficiencyVehicleDao(ILoadAndUnloadEfficiencyVehicleDao loadAndUnloadEfficiencyVehicleDao) {
	this.loadAndUnloadEfficiencyVehicleDao = loadAndUnloadEfficiencyVehicleDao;
    }
    
    public void setOrgAdministrativeInfoService(
    	IOrgAdministrativeInfoService orgAdministrativeInfoService) {
        this.orgAdministrativeInfoService = orgAdministrativeInfoService;
    }

    
    public void setLeasedVehicleTypeService(
    	ILeasedVehicleTypeService leasedVehicleTypeService) {
        this.leasedVehicleTypeService = leasedVehicleTypeService;
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
    private void syncEfficiencDataToOther(List<LoadAndUnloadEfficiencyVehicleEntity> entitys, int operationType)  throws BusinessException{
    	syncEfficiencyService.syncEfficiency(entitys, operationType);
    }
    
}
