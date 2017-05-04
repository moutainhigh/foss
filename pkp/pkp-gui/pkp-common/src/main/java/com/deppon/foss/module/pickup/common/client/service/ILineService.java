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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/service/ILineService.java
 * 
 * FILE NAME        	: ILineService.java
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
package com.deppon.foss.module.pickup.common.client.service;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity;




/**
 * 线路服务接口
 * @author 105089-foss-yangtong
 * @date 2012-10-24 上午10:40:00
 */
public interface ILineService {
	/**
	 * 按id查询
	 */
	LineEntity queryById(String id);
	/**
	 * 插条记录
	 */
	void addLine(LineEntity orgInfoEntity);
	/**
	 * 更新条记录
	 */
	void updateLine(LineEntity orgInfoEntity);
	/**
	 * 
	 * 新增或更新记录
	 */
	void saveOrUpdate(LineEntity orgInfo);
	/**
	 * 
	 * 查询记录
	 */
	List<LineEntity> queryAll();
	 /**
     * 
     * <p>通过始发营业部部门编码查询始发外场部门编码</p> 
     * @author foss-jiangfei
     * @date Nov 6, 2012 4:37:14 PM
     * @param sourceSaleCode
     * @param productCode
     * @return
     * @see
     */
	String querySourceTransCode(String sourceSaleCode, String productCode);
    
    /**
     * 
     * <p>通过到达营业部部门编码查询到达外场部门编码</p> 
     * @author foss-jiangfei
     * @date Nov 6, 2012 4:38:32 PM
     * @param targetSaleCode
     * @param productCode
     * @return
     * @see
     */
	String queryTargetTransCode(String targetSaleCode, String productCode);
    
    /**
     * 
     * <p>根据条件查询线路</p> 
     * @author foss-jiangfei
     * @date Oct 25, 2012 11:34:06 AM
     * @param line
     * @param start
     * @param limit
     * @return
     * @see
     */
	List<LineEntity> queryLineListByCondition(LineEntity line, int start, int limit);
	
	/**
	 * @param lineEntity
	 */
	 void delete(LineEntity lineEntity) ;
}