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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/ui/paginationtable/Navigatable.java
 * 
 * FILE NAME        	: Navigatable.java
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
 * 分页接口
 * @author shixiaowei
 *
 */
public interface Navigatable {
	/**
	 * 显示所需当前页的所有记录
	 * 
	 * @param currentPage
	 *            当前页码
	 * @param pageSize
	 *            每页显示的记录数
	 */
	void pageTo(int currentPage, int pageSize);

	/**
	 * 返回所有记录数
	 * 
	 * @return
	 */
	int getTotalCount();
	
	
	/**
	 * getCurrentPage
	 * @return
	 */
	int getCurrentPage();


	/**
	 * @param currentPage the currentPage to set
	 */
	void setCurrentPage(int currentPage);


	/**
	 * @return the pageSize
	 */
	int getPageSize();


	/**
	 * @param pageSize the pageSize to set
	 */
	void setPageSize(int pageSize);

}