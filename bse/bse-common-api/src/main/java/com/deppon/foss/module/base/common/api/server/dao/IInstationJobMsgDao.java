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
 * PROJECT NAME	: bse-common-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/api/server/dao/IInstationJobMsgDao.java
 * 
 * FILE NAME        	: IInstationJobMsgDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.common.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.common.api.shared.domain.InstationJobMsgEntity;


/**
 * 站内消息Job分发Dao
 * @author 101911-foss-zhouChunlai
 * @date 2012-12-10 上午8:48:08
 */
public interface IInstationJobMsgDao {
	
	/**
	 * 新增人员对组织发的站内消息
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-13 上午9:12:00
	 */
	int insertInstationJobMsg(InstationJobMsgEntity entity);
    
	/**
	 * 更新待分发的消息状态为已处理
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-13 上午9:12:00
	 */
	int updateInstationJobMsgStatusById(String id);
	
	/**
	 * 根据状态查询出所有分发站内消数据
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-13 上午11:16:51
	 */
	List<InstationJobMsgEntity> queryInstationJobMsgByStatus(String status);
}
