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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/ui/paginationtable/DefaultSortedHeaderCellRenderer.java
 * 
 * FILE NAME        	: DefaultSortedHeaderCellRenderer.java
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
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

import com.deppon.foss.base.util.define.NumberConstants;

/**
 * 排序表格的HeaderCellRenderer
 * 
 * <p>
 * 点击表头后，根据当前排序状态在表头上画出上下箭头。
 * 
 * @author shixiaowei
 * 
 */
public class DefaultSortedHeaderCellRenderer implements TableCellRenderer {
	/**
	 * 原render
	 */
	private TableCellRenderer render = null;

	/**
	 * 上箭头
	 */
	private UpAndDownArrow upArrow = new UpAndDownArrow(true);

	/**
	 * 下箭头
	 */
	private UpAndDownArrow downArrow = new UpAndDownArrow(false);

	/**
	 * 构造函数，传入原tableHeaderRenderer
	 * 
	 * @param render
	 */
	public DefaultSortedHeaderCellRenderer(TableCellRenderer render) {
		this.render = render;
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		// 调用render的方法
		Component comp = render.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, column);

		// 加上箭头，当前只能在header为JLabel的表头上加箭头
		if (table.getModel() instanceof AbstractSortedTableModel
				&& comp instanceof JLabel) {
			AbstractSortedTableModel model = (AbstractSortedTableModel) table
					.getModel();
			// 如果当前显示的column就是排序的column，则画上箭头
			if (table.convertColumnIndexToModel(column) == model
					.getCurrentSortedColumn()) {
				if (model.isAsc()) {
					((JLabel) comp)
							.setHorizontalTextPosition(SwingConstants.LEFT);
					((JLabel) comp).setIconTextGap(NumberConstants.NUMBER_8);
					((JLabel) comp).setIcon(upArrow);
				} else {
					((JLabel) comp)
							.setHorizontalTextPosition(SwingConstants.LEFT);
					((JLabel) comp).setIconTextGap(NumberConstants.NUMBER_8);
					((JLabel) comp).setIcon(downArrow);
				}
			} else {
				((JLabel) comp).setIcon(null);
			}
		}
		return comp;
	}
}

/**
 * 上、下箭头，将来刻根据需要，修改该类文件
 * 
 * @author Administrator
 * 
 */
class UpAndDownArrow implements Icon {
	/**
	 * size
	 */
	private int size = NumberConstants.NUMBER_4;

	/**
	 * 方向
	 */
	private boolean asc = true;

	public UpAndDownArrow(boolean asc) {
		this.asc = asc;
	}

	public int getIconHeight() {
		return size + 1;
	}

	public int getIconWidth() {
		return size * 2 + 1;
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {
		Color color = (Color) UIManager.get("controlShadow");
		g.setColor(color);

		if (asc) {
			for (int i = 0; i <= size; i++) {
				g.drawLine(x + i, y + size - i, x + size * 2 - i, y + size - i);
			}
		} else {
			for (int i = 0; i <= size; i++) {
				g.drawLine(x + i, y + i, x + size * 2 - i, y + i);
			}
		}
	}

}