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
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/IAttachementService.java
 * 
 * FILE NAME        	: IAttachementService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.server.web.upload.AttachementEntity;
import com.deppon.foss.framework.service.IService;

public interface IAttachementService extends IService {

	/**
	 * 修改附件信息
	 * 
	 * @param entity
	 * @return 1：成功；0：失败
	 */
	int updateAttachementInfo(AttachementEntity entity);

	/**
	 * 根据ID获取附件信息
	 * 
	 * @param id
	 *            附件信息ID
	 * @return 附件信息实体
	 */
	AttachementEntity queryAttachementInfoById(String id);

	/**
	 * 获取某业务下所有附件信息列表
	 * 
	 * @param relatedKey
	 *            关联外键(业务信息id、单号等), modulePath 附件所属业务的模块路径
	 * @return 附件信息列表
	 */
	List<AttachementEntity> getAttachementInfos(String relatedKey,
			String modulePath);
}
