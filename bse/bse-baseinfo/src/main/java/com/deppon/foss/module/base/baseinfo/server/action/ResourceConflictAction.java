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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/ResourceConflictAction.java
 * 
 * FILE NAME        	: ResourceConflictAction.java
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
package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IResourceConflictService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ResourceConflictEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.ResourceConflictException;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.ResourceConflictVo;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;

/**
 * 权限互斥 action
 * 
 * @author 087584-foss-lijun
 * @date 2012-12-18 上午9:49:25
 */
public class ResourceConflictAction extends AbstractAction {

    /**
     * 新增权限互斥
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-21 上午9:06:41
     */
    @JSON
    public String addResourceConflict() {
	if(resourceConflictVo == null
		|| resourceConflictVo.getResourceConflictEntity()==null
		|| StringUtils.isBlank(resourceConflictVo.getResourceConflictEntity().getFirstCode())
		){
		return returnError(ResourceConflictException.RESOURCECONFLICT_ADD_FAILURE);
	}
	
	ResourceConflictEntity entityQuery = resourceConflictVo
		.getResourceConflictEntity();
	// 设置操作用户的用户编码
	UserEntity userOfCache = FossUserContext.getCurrentUser();
	String operUserCode = null;
	if(userOfCache!=null && userOfCache.getEmployee()!= null
		&& StringUtils.isNotBlank(userOfCache.getEmployee().getEmpCode())){
	    operUserCode = userOfCache.getEmployee().getEmpCode();
	}
	entityQuery.setCreateUser(operUserCode);
	entityQuery.setModifyUser(operUserCode);
	
	try {
	    ResourceConflictEntity entityCondition = resourceConflictService
		    .addResourceConflict(entityQuery);
	    if (entityCondition == null) {
		return returnError(ResourceConflictException.RESOURCECONFLICT_ADD_FAILURE);
	    }
	} catch (BusinessException e) {
	    LOGGER.error(ResourceConflictException.RESOURCECONFLICT_ADD_FAILURE, e);
	    return returnError(ResourceConflictException.RESOURCECONFLICT_ADD_FAILURE);
	}
	return returnSuccess(ResourceConflictException.RESOURCECONFLICT_ADD_SUCCESS);
    }


    /**
     * 删除权限互斥
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-21 上午9:06:41
     */
    @JSON
    public String deleteResourceConflict() {
	if(resourceConflictVo == null
		|| resourceConflictVo.getResourceConflictEntity()==null
		|| StringUtils.isBlank(resourceConflictVo.getResourceConflictEntity().getVirtualCode())){
	    return returnError(ResourceConflictException.RESOURCE_CONFLICT_CODE_NULL);
	}
	
	ResourceConflictEntity entityView = resourceConflictVo
		.getResourceConflictEntity();
	// 设置操作用户的用户编码
	UserEntity userOfCache = FossUserContext.getCurrentUser();
	String operUserCode = null;
	if(userOfCache!=null && userOfCache.getEmployee()!= null
		&& StringUtils.isNotBlank(userOfCache.getEmployee().getEmpCode())){
	    operUserCode = userOfCache.getEmployee().getEmpCode();
	}
	entityView.setCreateUser(operUserCode);
	entityView.setModifyUser(operUserCode);
	
	try{
	    ResourceConflictEntity entityDelete = resourceConflictService
		    .deleteResourceConflict(entityView);
	    if(entityDelete == null){
		return returnError(ResourceConflictException.RESOURCECONFLICT_DEL_FAILURE);
	    }
	}catch(BusinessException e){
	    LOGGER.error(ResourceConflictException.RESOURCECONFLICT_DEL_FAILURE, e);
	    return returnError(ResourceConflictException.RESOURCECONFLICT_DEL_FAILURE);
	}
	return returnSuccess(ResourceConflictException.RESOURCECONFLICT_DEL_SUCCESS);
    }

    /**
     * 删除权限互斥
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-21 上午9:06:41
     */
    @JSON
    public String deleteResourceConflictMore() {
	if(resourceConflictVo == null
		|| resourceConflictVo.getResourceConflictEntity()==null
		|| StringUtils.isBlank(resourceConflictVo.getResourceConflictEntity().getVirtualCode())){
	    return returnError(ResourceConflictException.RESOURCECONFLICT_DEL_FAILURE);
	}
	
	ResourceConflictEntity entityView = resourceConflictVo
		.getResourceConflictEntity();
	// 设置操作用户的用户编码
	UserEntity userOfCache = FossUserContext.getCurrentUser();
	String operUserCode = null;
	if(userOfCache!=null && userOfCache.getEmployee()!= null
		&& StringUtils.isNotBlank(userOfCache.getEmployee().getEmpCode())){
	    operUserCode = userOfCache.getEmployee().getEmpCode();
	}
	entityView.setCreateUser(operUserCode);
	entityView.setModifyUser(operUserCode);
	
	String[] codes = resourceConflictVo.getResourceConflictEntity()
		.getVirtualCode().split(SymbolConstants.EN_COMMA);
	
	try{
	    ResourceConflictEntity entityDelete = resourceConflictService.deleteResourceConflictMore(codes,operUserCode);
	    if(entityDelete == null){
		return returnError(ResourceConflictException.RESOURCECONFLICT_DEL_FAILURE);
	    }
	}catch(BusinessException e){
	    LOGGER.error(ResourceConflictException.RESOURCECONFLICT_DEL_FAILURE, e);
	    return returnError(ResourceConflictException.RESOURCECONFLICT_DEL_FAILURE);
	}
	return returnSuccess(ResourceConflictException.RESOURCECONFLICT_DEL_SUCCESS);
    }
    
    
    
    /**
     * 动态条件，精确查询
     * 
     * 权限的编码如果不为空，则为精确查询的查询条件 
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-21 上午9:06:41
     */
    @JSON
    public String queryResourceConflictExactByEntity() {
	if(resourceConflictVo == null){
	    resourceConflictVo = new ResourceConflictVo();
	}
	
	ResourceConflictEntity entityCondition = resourceConflictVo.getResourceConflictEntity();
	
	if(limit == 0){
	    limit = Integer.MAX_VALUE;
	}
	// 返回的结果显示在表格中：
	resourceConflictVo.setResourceConflictList(resourceConflictService
		.queryResourceConflictExactByEntity(entityCondition, start,
			limit));
	totalCount = resourceConflictService.queryResourceConflictExactByEntityCount(entityCondition);
	return returnSuccess();
    }


    /**
     * 查询两个互斥的权限是否已经记录
     * 
     * 
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-21 上午9:06:41
     */
    @JSON
    public String queryResourceConflictExist() {
	if(resourceConflictVo == null
		|| resourceConflictVo.getResourceConflictEntity()==null
		|| StringUtils.isBlank(resourceConflictVo.getResourceConflictEntity().getFirstCode())
		){
		 return returnError(ResourceConflictException.RESOURCECONFLICT_QUE_FAILURE);
	}
	
	ResourceConflictEntity entityCondition = resourceConflictVo.getResourceConflictEntity();
	
	List<ResourceConflictEntity> entitys = resourceConflictService
		.queryResourceConflictExactByEntity(entityCondition, NumberConstants.NUMERAL_ONE,Integer.MAX_VALUE);
	if(!CollectionUtils.isEmpty(entitys)){
	    resourceConflictVo.setResourceConflictEntity(entitys.get(NumberConstants.NUMERAL_ZORE));
	}
	
	return returnSuccess();
    }

    /**
     * 下面是变量的声明
     */
    private static final long serialVersionUID = -4387627988772020011L;

    // 用于注入行政区域业务服务实现类
    private IResourceConflictService resourceConflictService;

    private ResourceConflictVo resourceConflictVo;


    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceConflictAction.class);

    /*
     * =================================================================
     * 下面是get,set方法：
     */

    
    public void setResourceConflictService(IResourceConflictService resourceConflictService) {
        this.resourceConflictService = resourceConflictService;
    }

    
    public ResourceConflictVo getResourceConflictVo() {
        return resourceConflictVo;
    }

    
    public void setResourceConflictVo(ResourceConflictVo resourceConflictVo) {
        this.resourceConflictVo = resourceConflictVo;
    }


}
