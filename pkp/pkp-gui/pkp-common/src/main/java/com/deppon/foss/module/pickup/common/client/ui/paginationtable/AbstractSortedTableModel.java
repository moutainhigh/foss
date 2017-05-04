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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/ui/paginationtable/AbstractSortedTableModel.java
 * 
 * FILE NAME        	: AbstractSortedTableModel.java
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
import java.util.Enumeration;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

/**
 * 支持分页查询的TableModel
 * 
 * @author shixiaowei
 * 
 */
public abstract class AbstractSortedTableModel extends AbstractTableModel {
	/**
	 *  序列化版本号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 当前使用该Model的table
	 */
	private SortedTable table;

	/**
	 * 当前需排序的column
	 */
	private int currentSortedColumn = -1;

	/**
	 * 当前的排序方向
	 */
	private boolean asc = true;

	/**
	 * 是否按照内容自动调整列宽
	 */
	private boolean autoResizeColumnWidths = false;

	/**
	 * 得到当前model所属的table
	 * 
	 * @return
	 */
	public SortedTable getTable() {
		return table;
	}

	/**
	 * 设置当前model所属的table
	 * 
	 * @param table
	 */
	public void setTable(SortedTable table) {
		this.table = table;
	}

	/**
	 * 被fireModelSetted调用，初始化当前分页状态
	 */
	protected void modelSetted() {
		currentSortedColumn = -1;
		asc = true;
		if (table != null)
			autoResizeColumnWidths = table.isAutoResizeColumnWidths();
	}

	/**
	 * 触发modelsetted方法，一般在该model被set到table中时，调用modelSetted
	 */
	public void fireModelSetted() {
		modelSetted();
	}

	/**
	 * 排序
	 * 
	 * @param column
	 *            需排序的column
	 */
	public void sort(int column) {
		if (isSortedColumn(column)) {
			if (this.currentSortedColumn == column) {
				asc = !asc;
			} else {
				this.currentSortedColumn = column;
				asc = true;
			}
			updateData();
		}
	}

	/**
	 * 更新数据
	 */
	protected void updateData() {
		fireTableDataChanged();
	}

	/**
	 * 得到当前排序的column
	 * 
	 * @return
	 */
	public int getCurrentSortedColumn() {
		return currentSortedColumn;
	}

	/**
	 * 返回当前排序状态是否是升序
	 * 
	 * @return
	 */
	public boolean isAsc() {
		return asc;
	}

	/**
	 * 返回当前table是否是自动调整列宽的状态
	 * 
	 * @return
	 */
	protected boolean isAutoResizeColumnWidths() {
		return autoResizeColumnWidths;
	}

	/**
	 * 返回当前table是否自动调整列宽
	 * 
	 * @param autoResizeColumnWidths
	 */
	protected void setAutoResizeColumnWidths(boolean autoResizeColumnWidths) {
		this.autoResizeColumnWidths = autoResizeColumnWidths;
	}

	/**
	 * 返回该column是否是需要排序的column
	 * 
	 * @param column
	 * @return
	 */
	public abstract boolean isSortedColumn(int column);

	/**
	 * 覆盖父类的方法，在更新表格数据后，自动调整列宽
	 */
	public void fireTableChanged(TableModelEvent e) {
		super.fireTableChanged(e);
		if (autoResizeColumnWidths)
			autoResizeColumnWidths();
	}

	/**
	 * 根据当前表格的数据，自动调整列宽
	 * 
	 */
	@SuppressWarnings("rawtypes")
	private void autoResizeColumnWidths() {
		JTable table = this.getTable();
		JTableHeader header = table.getTableHeader();
		int rowCount = table.getRowCount();

		
		Enumeration columns = table.getColumnModel().getColumns();
		while (columns.hasMoreElements()) {
			TableColumn column = (TableColumn) columns.nextElement();
			int col = header.getColumnModel().getColumnIndex(
					column.getIdentifier());
			int width = (int) table
					.getTableHeader()
					.getDefaultRenderer()
					.getTableCellRendererComponent(table,
							column.getIdentifier(), false, false, -1, col)
					.getPreferredSize().getWidth();
			for (int row = 0; row < rowCount; row++) {
				int preferedWidth = (int) table
						.getCellRenderer(row, col)
						.getTableCellRendererComponent(table,
								table.getValueAt(row, col), false, false, row,
								col).getPreferredSize().getWidth();
				width = Math.max(width, preferedWidth);
			}
			header.setResizingColumn(column); // this line is very important
			column.setWidth(width + table.getIntercellSpacing().width);
		}
	}
}