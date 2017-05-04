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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/ui/paginationtable/SortedTable.java
 * 
 * FILE NAME        	: SortedTable.java
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.table.TableModel;

import org.jdesktop.swingx.JXTable;

/**
 * 支持排序的JTable，需要与AbstractSortedTableModel共同使用
 * 
 * @author shixiaowei
 * 
 */
public class SortedTable extends JXTable {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = -4582616316827804509L;

	/**
	 * 是否是排序的table
	 */
	private boolean isSortedTable = false;

	/**
	 * 是否按照内容自动调整列宽
	 */
	private boolean autoResizeColumnWidths = false;

	/**
	 * TableModel
	 */
	private AbstractSortedTableModel model = null;
	
	/**
	 * 构造方法
	 * 
	 */
	public SortedTable() {
		this(null);
	}


	/**
	 * 构造方法
	 * 
	 * @param model
	 */
	public SortedTable(TableModel model) {
		super();
		init();
		if (model != null) {
			setModel(model);
		}
	}

	
	
	/**
	 * setModel，如果该model不为AbstractSortedTableModel，则该table不排序
	 */
	public void setModel(TableModel model) {
		super.setModel(model);
		if (model instanceof AbstractSortedTableModel) {
			isSortedTable = true;
			this.model = (AbstractSortedTableModel) model;
			this.model.setTable(this);
			this.model.fireModelSetted();
		}
	}

	/**
	 * 初始化
	 */
	private void init() {
		// 添加表头监听
		this.getTableHeader().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent mouseevent) {
				tableHeaderClicked(mouseevent);
			}
		});

		// 添加TableHeader的Renderer
		this.getTableHeader().setDefaultRenderer(
				new DefaultSortedHeaderCellRenderer(this.getTableHeader()
						.getDefaultRenderer()));
	}

	/**
	 * 点击表头
	 * @param e
	 */
	private void tableHeaderClicked(MouseEvent e) {
		if (!isSortedTable) {
			return;
		}
		int column = convertColumnIndexToModel(columnAtPoint(e.getPoint()));
		model.sort(column);
	}

	/**
	 * 是否默认自动宽度
	 * @return
	 */
	public boolean isAutoResizeColumnWidths() {
		return autoResizeColumnWidths;
	}

	/**
	 * 设置列宽度
	 * @param autoResizeColumnWidths
	 */
	public void setAutoResizeColumnWidths(boolean autoResizeColumnWidths) {
		this.autoResizeColumnWidths = autoResizeColumnWidths;
		if (model != null)
			model.setAutoResizeColumnWidths(autoResizeColumnWidths);
	}

	/**
	 * 是否可以排序
	 * @return
	 */
	public boolean isSortedTable() {
		return isSortedTable;
	}

}