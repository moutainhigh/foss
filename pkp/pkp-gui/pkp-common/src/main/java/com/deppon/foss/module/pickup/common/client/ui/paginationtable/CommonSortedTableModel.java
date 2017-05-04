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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/ui/paginationtable/CommonSortedTableModel.java
 * 
 * FILE NAME        	: CommonSortedTableModel.java
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
 * 支持排序的TableModel
 * 
 * @author shixiaowei
 * 
 */
@SuppressWarnings("rawtypes")
public abstract class CommonSortedTableModel extends AbstractSortedTableModel
		implements Navigatable {
	/**
	 *  序列化版本号
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * DataUpdater，用户更新数据的类
	 */
	CommonSortedTableDataUpdater updater = null;

	/**
	 * 当前页数
	 */
	private int currentPage = 0;

	/**
	 * 当前页容量
	 */
	private int pageSize = 0;

	/**
	 * 实现Navigatable的方法，转到指定页面
	 */
	public void pageTo(int currentPage, int pageSize) {
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		updateData();
	}

	
	/**
	 * @return the currentPage
	 */
	public int getCurrentPage() {
		return currentPage;
	}


	/**
	 * @param currentPage the currentPage to set
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}


	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}


	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	/**
	 * 更新数据
	 * 
	 */
	public void refresh() {
		updateData();
	}

	/**
	 * 更新显示数据
	 */
	@SuppressWarnings("unchecked")
	protected void updateData() {
		// 调用DataUpdater的updateData方法
		if (updater != null) {
			updater.updateData(currentPage, pageSize, getCurrentSortedColumn(),
					isAsc(), this);
			// 更新Data的显示
			this.fireTableDataChanged();
		}
	}

	/**
	 * 返回记录总数
	 */
	@SuppressWarnings("unchecked")
	public int getTotalCount() {
		if (updater != null) {
			return updater.getTotalCount(this, currentPage, pageSize, getCurrentSortedColumn(),
					isAsc() );
		}
		return 0;
	}

	/**
	 * 添加updater
	 * 
	 * @param listener
	 */
	public CommonSortedTableModel setDataUpdater(
			CommonSortedTableDataUpdater updater) {
		this.updater = updater;
		return this;
	}
	
	/**
	 * 返回updater
	 */
	public CommonSortedTableDataUpdater getDataUpdater(){
		return this.updater;
	}

	/**
	 * 去除updater
	 */
	public CommonSortedTableModel removeDataUpdater() {
		updater = null;
		return this;
	}

	/**
	 * 返回该column是否是需要排序的column
	 * 
	 * @param column
	 * @return
	 */
	public abstract boolean isSortedColumn(int column);

	/**
	 * 覆盖父类的方法，当model被重新set之后，初始化当前分页状态
	 */
	protected void modelSetted() {
		super.modelSetted();
		initStatus();
	}

	/**
	 * 初始化排序分页的状态
	 */
	public void initStatus() {
		currentPage = 0;
		pageSize = 0;
	}
}