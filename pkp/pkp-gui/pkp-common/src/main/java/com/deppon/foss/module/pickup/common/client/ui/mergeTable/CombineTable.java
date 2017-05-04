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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/ui/mergeTable/CombineTable.java
 * 
 * FILE NAME        	: CombineTable.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.common.client.ui.mergeTable;

import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import com.deppon.foss.module.pickup.common.client.ui.paginationtable.AbstractSortedTableModel;
import com.deppon.foss.module.pickup.common.client.ui.paginationtable.SortedTable;

/**
 * 定义融合的表格
 * 
 * @author 026123-foss-lifengteng
 * @date 2012-10-22 上午8:43:05
 */

public class CombineTable extends SortedTable {

	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = -6250128676399526138L;

	public CombineData combineData;
	
	/**
	 * TableModel
	 */
	private AbstractSortedTableModel model = null;
	
	/**
	 * 构造方法：创建一个新的实例
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-22 上午8:43:37
	 */
	public CombineTable(TableModel tableModel) {
		super(tableModel);
		if (tableModel instanceof AbstractSortedTableModel) {
			this.model = (AbstractSortedTableModel) tableModel;
			this.model.setTable(this);
			this.model.fireModelSetted();
		}
		super.setUI(new CombineTableUI());
	}

	
	
	/**
	 * setModel，如果该model不为AbstractSortedTableModel，则该table不排序
	 */
	public void setModel(TableModel model) {
		super.setModel(model);
		if (model instanceof AbstractSortedTableModel) {
			this.model = (AbstractSortedTableModel) model;
			this.model.setTable(this);
			this.model.fireModelSetted();
		}
	}
	
	/**
	 * 构造方法：创建一个新的实例
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-22 上午8:44:47
	 */
	public CombineTable(CombineData combineData, TableModel tableModel) {
		super(tableModel);
		this.combineData = combineData;

		for (Integer column : combineData.combineColumns) {
			TableColumn tableColumn = super.columnModel.getColumn(column);
			tableColumn.setCellRenderer(new CombineColumnRender());
		}
		super.setUI(new CombineTableUI());
	}

	/**
	 * 构造方法：创建一个新的实例
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-22 上午8:45:06
	 */
	public void setCombineData(CombineData combineData) {
		this.combineData = combineData;

		for (Integer column : combineData.combineColumns) {
			TableColumn tableColumn = super.columnModel.getColumn(column);
			tableColumn.setCellRenderer(new CombineColumnRender());
		}
	}

	/**
	 * 重写方法，获得指定单元格的行高、列完对象
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-22 上午8:45:22
	 * @see javax.swing.JTable#getCellRect(int, int, boolean)
	 */
	@Override
	public Rectangle getCellRect(int row, int column, boolean includeSpacing) {
		if (combineData == null) {
			return super.getCellRect(row, column, includeSpacing);
		}
		int sk = combineData.visibleCell(row, column);
		Rectangle rect1 = super.getCellRect(sk, column, includeSpacing);
		if (combineData.span(sk, column) != 1) {
			for (int i = 1; i < combineData.span(sk, column); i++) {
				rect1.height += this.getRowHeight(sk + i);
			}
		}
		return rect1;
	}

	/**
	 * 返回行号
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-22 上午8:46:35
	 * @see org.jdesktop.swingx.JXTable#rowAtPoint(java.awt.Point)
	 */
	@Override
	public int rowAtPoint(Point p) {
		int column = super.columnAtPoint(p);
		if (column < 0) {
			return column;
		}
		int row = super.rowAtPoint(p);
		return combineData.visibleCell(row, column);
	}

	/**
	 * 设置表格是否可编辑
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-20 上午9:33:30
	 * @see org.jdesktop.swingx.JXTable#isCellEditable(int, int)
	 */
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	/**
	 * 设置合并行是否被选中
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-20 上午9:24:40
	 * @see javax.swing.JTable#isCellSelected(int, int)
	 */
	@Override
	public boolean isCellSelected(int row, int column) {
		if (combineData.combineColumns.contains(column) && isRowSelected(row)) {
			return false;
		}
		return super.isCellSelected(row, column);
	}

	/**
	 * 设置表格的boolean值的表示方式为checkBox
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-20 上午9:24:55
	 * @see javax.swing.JTable#getColumnClass(int)
	 */
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return getValueAt(0, columnIndex).getClass();
	}
}