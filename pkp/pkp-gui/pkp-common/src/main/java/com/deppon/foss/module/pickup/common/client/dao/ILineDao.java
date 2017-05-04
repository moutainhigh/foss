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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/dao/ILineDao.java
 * 
 * FILE NAME        	: ILineDao.java
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

import com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity;


/**
 * 线路信息数据访问接口
 * @author 105089-foss-yangtong
 * @date 2012-10-24 上午10:51:02
 */
public interface ILineDao {
	
	/**
	 * 
	 * 功能：按id查询
	 * @param:
	 * @return Department
	 * @since:1.6
	 */
	LineEntity queryById(String id);

	/**
	 * 
	 * 功能：插条记录
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
	boolean addLine(LineEntity line);

	/**
	 * 
	 * 功能：更新条记录
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	void updateLine(LineEntity line);
	
	/**
	 * 功能：查询记录
	 * @param:
	 * @return List<Department>
	 * @since:1.6
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
     * <p>根据查询条件查询线路</p> 
     * @author foss-jiangfei
     * @date Oct 26, 2012 3:22:56 PM
     * @param line
     * @param start
     * @param limit
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILineService#queryLineListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity, int, int)
     */

    List<LineEntity> queryLineListByCondition(LineEntity line,
	    int start, int limit) ;

	/**
	 * @param lineEntity
	 */
	void delete(LineEntity lineEntity);
}