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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/complex/RoleConflictService.java
 * 
 * FILE NAME        	: RoleConflictService.java
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
package com.deppon.foss.module.base.baseinfo.server.service.impl.complex;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IResourceConflictDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IResourceService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IRoleConflictService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ResourceConflictEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ResourceEntity;
/**
 * 用来提供交互所有关于“司机（公司、外请）”的数据库对应数据访问复杂的Service接口实现类：无SUC
 * <p>注意：由于数据建模设计中，公司车不直接关系到司机，外请车关系到司机，公司司机直接关系的是工号</p>
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-30 下午5:23:09</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-30 下午5:23:09
 * @since
 * @version
 */
public class RoleConflictService implements IRoleConflictService {

    /**
     * 下面是service及get,set方法
     */
    
    //"公司司机"Service接口
    private IResourceService resourceService;
    
    //"外请车司机"Service接口
    private IResourceConflictDao resourceConflictDao;

    /**
     * 
     * @date Mar 12, 2013 2:15:50 PM
     * @param resourceService
     * @see
     */
    public void setResourceService(IResourceService resourceService) {
        this.resourceService = resourceService;
    }

    /**
     * 
     * @date Mar 12, 2013 2:15:56 PM
     * @param resourceConflictDao
     * @see
     */
    public void setResourceConflictDao(IResourceConflictDao resourceConflictDao) {
        this.resourceConflictDao = resourceConflictDao;
    }

    
    
    /**
     * 根据2个角色编码查询这个两角色包含的权限有哪些是互斥的
     * 
     * 在给用户部门分配角色时判断角色互斥
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-26 下午4:36:7
     */
    @Override
    public List<ResourceConflictEntity> queryResourceConflictBy2Role(
	    String firstRoleCode, String secondRoleCode) {
	if (StringUtils.isBlank(firstRoleCode)
		|| StringUtils.isBlank(secondRoleCode)) {
	    return null;
	}
	
	List<ResourceConflictEntity> entitys = resourceConflictDao
		.queryResourceConflictBy2Role(firstRoleCode, secondRoleCode);
	
	if(CollectionUtils.isEmpty(entitys)){
	    return null;
	}
	entitys = this.attachResourceName(entitys);

	return entitys;
    }
    
    
    
    
    /**
     * 下面是工具方法
     */

    /**
     * 给互斥的权限加上名称,两个互斥的权限编码不能为空
     * 
     * @author 087584-foss-lijun
     * @date 2012-12-1 上午8:34:24
     */
    public ResourceConflictEntity attachResourceName(ResourceConflictEntity entity){
	if(entity == null || StringUtils.isBlank(entity.getFirstCode())
		|| StringUtils.isBlank(entity.getSecondCode())){
	    return entity;
	}
	
	ResourceEntity resource = resourceService.queryResourceByCode(entity.getFirstCode());
	if(resource!=null){
	    entity.setFirstCodeName(resource.getName());
	}
	resource = resourceService.queryResourceByCode(entity.getSecondCode());
	if(resource!=null){
	    entity.setSecondCodeName(resource.getName());
	}
	return entity;
    }
    

    /**
     * 给互斥的权限加上名称,两个互斥的权限编码不能为空
     * 
     * @author 087584-foss-lijun
     * @date 2012-12-1 上午8:34:24
     */
    public List<ResourceConflictEntity> attachResourceName(List<ResourceConflictEntity> entitys){
	if(CollectionUtils.isEmpty(entitys)){
	    return entitys;
	}
	
	for(ResourceConflictEntity entity : entitys){
	    this.attachResourceName(entity);
	}

	return entitys;
    }

}
