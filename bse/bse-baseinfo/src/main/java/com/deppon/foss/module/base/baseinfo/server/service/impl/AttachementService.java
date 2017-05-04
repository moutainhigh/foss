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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/AttachementService.java
 * 
 * FILE NAME        	: AttachementService.java
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

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.server.web.upload.AttachementEntity;
import com.deppon.foss.framework.server.web.upload.AttachementException;
import com.deppon.foss.framework.server.web.upload.IAttachementListener;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IAttachementDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAttachementService;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.define.FossConstants;

/**
 * 附件信息Service接口实现
 * @author DPAP
 * @date 2013-02-27
 */
public class AttachementService implements IAttachementService,IAttachementListener {
	
    /**
     * 附件信息Dao接口.
     */
    private IAttachementDao attachementDao;
    
    public void setAttachementDao(IAttachementDao attachementDao) {
    	this.attachementDao = attachementDao;
    }

	@Override
	public int addAttachementInfo(AttachementEntity entity) throws AttachementException {
		if (null == entity) {
		    return FossConstants.FAILURE;
		}
		String currentUser = FossUserContext.getCurrentUser().getEmployee().getEmpCode();
		entity.setCreateDate(new Date());
		entity.setModifyDate(new Date());
		entity.setCreateUser(currentUser);
		entity.setModifyUser(currentUser);
		entity.setActive("Y");
		return attachementDao.addAttachementInfo(entity);
	}

	@Override
	public int deleteAttachementInfo(String id) throws AttachementException {
		String modifyUser = FossUserContext.getCurrentUser().getEmployee().getEmpCode();
	    return attachementDao.deleteAttachementInfo(id, modifyUser);
	}

	@Override
	public int updateAttachementInfo(AttachementEntity entity) throws AttachementException {
		if (null == entity) {
		    return FossConstants.FAILURE;
		}
		String currentUser = FossUserContext.getCurrentUser().getEmployee().getEmpCode();
		entity.setModifyUser(currentUser);
		entity.setModifyDate(new Date());
	    return attachementDao.updateAttachementInfo(entity);
	}

	@Override
	public AttachementEntity queryAttachementInfoById(String id) {
		return attachementDao.queryAttachementInfoById(id);
	}

	@Override
	public List<AttachementEntity> getAttachementInfos(String relatedKey,
			String modulePath) {
		return attachementDao.getAttachementInfos(relatedKey, modulePath);
	}
	 
}
