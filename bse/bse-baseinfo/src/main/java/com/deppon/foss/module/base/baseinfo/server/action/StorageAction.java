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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/StorageAction.java
 * 
 * FILE NAME        	: StorageAction.java
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

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IStorageService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.StorageEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.StorageVo;

/**
 * 库位ACTION
 * 
 * @author 078838-foss-zhangbin
 * @date 2012-11-28
 * @version 1.0
 */
public class StorageAction extends AbstractAction {
	private static final long serialVersionUID = 2883644272419312426L;

	// 前后台传的参数
	private StorageVo storageVo = new StorageVo();
	public StorageVo getStorageVo() {
		return storageVo;
	}

	public void setStorageVo(StorageVo storageVo) {
		this.storageVo = storageVo;
	}
	//库位service
	private IStorageService storageService;
	public void setStorageService(IStorageService storageService) {
		this.storageService = storageService;
	}
	
	
	/**
	 * .
	 * <p>
	 * 查询所有的月台根据条件<br/>
	 * 方法名：queryStorageListByCondition
	 * </p>
	 * 
	 * @author 078838-foss-zhangbin
	 * @时间 2012-11-28
	 * @since JDK1.6
	 */
	@JSON
	public String queryStorageListByCondition() {
		try {
			UserEntity user = (UserEntity) UserContext.getCurrentUser();// 获取当前登录用户
			String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
			String currentOrgCode = user.getEmployee().getOrgCode();
			this.setTotalCount(storageService.countStorageListByCondition(storageVo.getStorageEntity(),userCode, currentOrgCode));
			List<StorageEntity> storageEntityList = storageService.queryStorageListByCondition(storageVo.getStorageEntity(), start, limit,userCode, currentOrgCode);
			storageVo.setStorageEntityList(storageEntityList);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * .
	 * <p>
	 * 批量作废库位信息<br/>
	 * 方法名：deleteStorage
	 * </p>
	 * 
	 * @author 078838-foss-zhangbin
	 * @时间 2012-11-28
	 * @since JDK1.6
	 */
	@JSON
	public String deleteStorage() {
		try {
			UserEntity user = (UserEntity) UserContext.getCurrentUser();// 获取当前登录用户
			String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
			storageService.deleteStorages(storageVo.getIds(), userCode);
			return returnSuccess(MessageType.DELETE_STORAGE_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	/**
	 * .
	 * <p>
	 * 修改库位信息<br/>
	 * 方法名：updateStorage
	 * </p>
	 * 
	 * @author 078838-foss-zhangbin
	 * @时间 2012-11-28
	 * @since JDK1.6
	 */
	@JSON
	public String updateStorage() {
		try {
			UserEntity user = (UserEntity) UserContext.getCurrentUser();// 获取当前登录用户
			String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
			storageVo.getStorageEntity().setModifyUser(userCode);
			storageService.updateStorage(storageVo.getStorageEntity());
			return returnSuccess(MessageType.UPDATE_STORAGE_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * .
	 * <p>
	 * 新增库位信息<br/>
	 * 方法名：addStorage
	 * </p>
	 * 
	 * @author 078838-foss-zhangbin
	 * @时间 2012-11-28
	 * @since JDK1.6
	 */
	@JSON
	public String addStorage() {
		try {
			UserEntity user = (UserEntity) UserContext.getCurrentUser();// 获取当前登录用户
			String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
			storageVo.getStorageEntity().setCreateUser(userCode);
			storageService.addStorage(storageVo.getStorageEntity());
			return returnSuccess(MessageType.SAVE_STORAGE_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * .
	 * <p>
	 * 查询库位详细信息<br/>
	 * 方法名：queryStorageByVirtualCode
	 * </p>
	 * 
	 * @author 078838-foss-zhangbin
	 * @时间 2012-11-29
	 * @since JDK1.6
	 */
	@JSON
	public String queryStorageById() {
		try {
			StorageEntity storageEntity = storageService.queryStorageById(storageVo.getStorageEntity().getId());
			storageVo.setStorageEntity(storageEntity);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * .
	 * <p>
	 * 查询库位信息根据外场CODE<br/>
	 * 方法名：queryStorageListByOrganizationCode
	 * </p>
	 * 
	 * @author 078838-foss-zhangbin
	 * @时间 2012-11-28
	 * @since JDK1.6
	 */
	@JSON
	public String queryStorageListByOrganizationCode() {
		try {
			List<StorageEntity> storageEntityList  = storageService.queryStorageListByOrganizationCode(storageVo.getStorageEntity().getOrganizationCode());
			storageVo.setStorageEntityList(storageEntityList);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
}
