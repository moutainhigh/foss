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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/service/ISqlDebugService.java
 * 
 * FILE NAME        	: ISqlDebugService.java
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

import java.sql.ResultSet;

/**
 * 
 * GUI本地数据库调试服务
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-12-24 下午8:15:00,content: </p>
 * @author foss-sunrui
 * @date 2012-12-24 下午8:15:00
 * @since
 * @version
 */
public interface ISqlDebugService {

	/**
	 * 
	 * <p>查询记录</p> 
	 * @author foss-sunrui
	 * @date 2012-12-24 下午8:15:24
	 * @param sql
	 * @return
	 * @see
	 */
	ResultSet select(String sql);

	/**
	 * 
	 * <p>插入记录</p> 
	 * @author foss-sunrui
	 * @date 2012-12-24 下午8:15:26
	 * @param sql
	 * @return
	 * @see
	 */
	int insert(String sql);

	/**
	 * 
	 * <p>更新记录</p> 
	 * @author foss-sunrui
	 * @date 2012-12-24 下午8:15:29
	 * @param sql
	 * @return
	 * @see
	 */
	int update(String sql);

	/**
	 * 
	 * <p>删除记录</p> 
	 * @author foss-sunrui
	 * @date 2012-12-24 下午8:15:35
	 * @param sql
	 * @return
	 * @see
	 */
	int delete(String sql);

	/**
	 * 
	 * <p>获取所有表名</p> 
	 * @author foss-sunrui
	 * @date 2012-12-24 下午8:15:38
	 * @param schemas
	 * @return
	 * @see
	 */
	String[] loadAllTableNames(String[] schemas);
}