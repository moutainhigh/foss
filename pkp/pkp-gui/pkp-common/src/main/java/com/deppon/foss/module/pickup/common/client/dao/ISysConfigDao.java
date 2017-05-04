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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/dao/ISysConfigDao.java
 * 
 * FILE NAME        	: ISysConfigDao.java
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

import java.util.List;

import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;

/**
 * 
 * 系统配置数据持久层接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-10-23 下午3:50:20, </p>
 * @author foss-sunrui
 * @date 2012-10-23 下午3:50:20
 * @since
 * @version
 */
public interface ISysConfigDao {

    /**
     * 
     * <p>通过主键查询系统配置</p> 
     * @author foss-sunrui
     * @date 2012-10-23 下午3:50:45
     * @param id
     * @return
     * @see
     */
    ConfigurationParamsEntity queryByPrimaryKey(String id);
    
    /**
     * 
     * <p>通过配置项标示查询系统配置,根据confcode和orgcode一起查询</p> 
     * @author foss-sunrui
     * @date 2012-10-23 下午4:04:00
     * @param confCode
     * @param orgCode
     * @return
     * @see
     */
    ConfigurationParamsEntity queryByConfCode(String confCode, String orgCode);
    
    /**
     * 
     * <p>通过配置项标示查询系统配置,只根据confcode来查询</p> 
     * @author foss-sunrui
     * @date 2012-10-23 下午4:10:25
     * @param confCode
     * @return 
     */
    ConfigurationParamsEntity queryByConfCode(String confCode);
    
    /**
     * 
     * <p>通过配置项集合查询系统配置</p> 
     * @author foss-sunrui
     * @date 2012-10-24 下午1:45:48
     * @param confCodes
     * @param orgCode
     * @return
     * @see
     */
    List<ConfigurationParamsEntity> queryByConfCodeArray(String[] confCodes, String orgCode);
    
    /**
	 * 
	 * 功能：插条记录
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
    boolean addConfig(ConfigurationParamsEntity config);

	/**
	 * 
	 * 功能：更新条记录
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	void updateConfig(ConfigurationParamsEntity config);

	/**
	 * 删除
	 * @param config
	 */
	void delete(ConfigurationParamsEntity config);

}