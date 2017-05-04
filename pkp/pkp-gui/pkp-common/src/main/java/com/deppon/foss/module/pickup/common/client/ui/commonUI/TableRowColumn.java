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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/ui/commonUI/TableRowColumn.java
 * 
 * FILE NAME        	: TableRowColumn.java
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
package com.deppon.foss.module.pickup.common.client.ui.commonUI;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import org.jdesktop.swingx.table.TableColumnExt;

/**
 * 
 * 表格扩展按钮列
 * @author 102246-foss-shaohongliang
 * @date 2012-11-13 下午3:19:10
 */
public class TableRowColumn implements TableCellRenderer{

	/**
	 * default column width
	 */
	private static final int DEFAULTCOLUMNWIDTH = 28;

	/**
	 * color
	 */
	private static final int COLORNUMBER = 240;
	
	@SuppressWarnings("unused")
	private JTable table;

	private JLabel renderLbl;
	
	private Color backgroundColor = new Color(COLORNUMBER, COLORNUMBER, COLORNUMBER);
	
	private Color forgroundColor = Color.DARK_GRAY;
	
	private int columnWidth = DEFAULTCOLUMNWIDTH;

	TableRowColumn(JTable table) {
		super();
		this.table = table;
		renderLbl = new JLabel();
		renderLbl.setOpaque(true);
		renderLbl.setHorizontalAlignment(SwingUtilities.CENTER);
		TableColumnModel columnModel = table.getColumnModel();
		TableColumnExt tc = new TableColumnExt();
		tc.setCellRenderer(this);
		tc.setMaxWidth(columnWidth);
		tc.setMinWidth(columnWidth);
		tc.setEditable(false);
		//set ModelIndex fix two modlecolumn with 0 index
		tc.setEditable(false);
		columnModel.addColumn(tc);
		table.moveColumn(columnModel.getColumnCount()-1, 0);
		
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		renderLbl.setForeground(forgroundColor);
		renderLbl.setBackground(backgroundColor);
		renderLbl.setText(String.valueOf(row + 1));
		return renderLbl;
	}


}