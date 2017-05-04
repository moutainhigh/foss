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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/ui/paginationtable/CommonSortedTableDataUpdater.java
 * 
 * FILE NAME        	: CommonSortedTableDataUpdater.java
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
package com.deppon.foss.module.pickup.common.client.ui.paginationtable;
/**
 * 分页排序TableModel的更新数据接口
 * 
 * @author shixiaowei
 * 
 */
public interface CommonSortedTableDataUpdater<T extends CommonSortedTableModel> {
	/**
	 * 更新数据
	 * 
	 * @param currentPage
	 *            当前页
	 * @param pageSize
	 *            页容量
	 * @param column
	 *            排序column
	 * @param asc
	 *            排序方向
	 */
	void updateData(int currentPage, int pageSize, int column,
			boolean asc, T model);

	/**
	 * 得到总记录数
	 * 
	 * @return
	 */
	int getTotalCount(T model, int currentPage, int pageSize, int column,
			boolean asc);
}