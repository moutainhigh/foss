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
 * PROJECT NAME	: pkp-waybill-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/server/dao/IWaybillAcHisPdaDao.java
 * 
 * FILE NAME        	: IWaybillAcHisPdaDao.java
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
package com.deppon.foss.module.pickup.waybill.api.server.dao;

import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillAcHisPdaEntity;

/**
 * PDA操作历史记录dao
 * @author 026123-foss-lifengteng
 * @date 2012-12-10 下午4:55:55
 */
public interface IWaybillAcHisPdaDao {

    /**
     * 插入操作历史记录
     * @author 026123-foss-lifengteng
     * @date 2012-12-10 下午4:56:17
     */
    int insert(WaybillAcHisPdaEntity record);

    /**
     * 有选择的插入操作历史记录
     * @author 026123-foss-lifengteng
     * @date 2012-12-10 下午4:56:20
     */
    int insertSelective(WaybillAcHisPdaEntity record);

    /**
     * 根据ID插入操作历史记录
     * @author 026123-foss-lifengteng
     * @date 2012-12-10 下午4:56:22
     */
    WaybillAcHisPdaEntity selectByPrimaryKey(String id);

    /**
     * 根据Id删除历史记录
     * @author 043260-foss-suyujun
     * @date 2012-12-10
     * @param id
     * @return int
     */
	int deleteByPrimaryKey(String id);

	/**
	 * 更新运单ＰＤＡ操作记录
	 * @author 043260-foss-suyujun
	 * @date 2012-12-11
	 * @param entity
	 * @return
	 * @return int
	 * @see
	 */
	int updateWaybillAcHisPdaEntity(WaybillAcHisPdaEntity entity);
}