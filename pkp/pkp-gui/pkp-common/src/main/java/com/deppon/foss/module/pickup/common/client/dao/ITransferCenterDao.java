/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/dao/ITransferCenterDao.java
 * 
 * FILE NAME        	: ITransferCenterDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
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
package com.deppon.foss.module.pickup.common.client.dao;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OutfieldEntity;

/**
 * 
 * 外场数据持久层接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-10-16 下午7:55:12, </p>
 * @author foss-sunrui
 * @date 2012-10-16 下午7:55:12
 * @since
 * @version
 */
public interface ITransferCenterDao {
	
	/**
	 * 删除数据
	 * @param id
	 * @return
	 */
    int deleteByPrimaryKey(String id);

    /**
     * 插入数据
     * @param record
     * @return
     */
    int insert(OutfieldEntity record);

    /**
     * 插入数据
     * @param record
     * @return
     */
    int insertSelective(OutfieldEntity record);

    /**
     * 查询数据
     * @param id
     * @return
     */
    OutfieldEntity selectByPrimaryKey(String id);

    /**
     * 更新数据
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(OutfieldEntity record);

    /**
     * 更新数据
     * @param record
     * @return
     */
    int updateByPrimaryKey(OutfieldEntity record);
}