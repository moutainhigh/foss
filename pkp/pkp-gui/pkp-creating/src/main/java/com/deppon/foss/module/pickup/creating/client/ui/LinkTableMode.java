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
 * PROJECT NAME	: pkp-creating
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/ui/LinkTableMode.java
 * 
 * FILE NAME        	: LinkTableMode.java
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
package com.deppon.foss.module.pickup.creating.client.ui;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.apache.commons.lang.ObjectUtils.Null;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.swingx.JXTable;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;

  
/**
 * 
 * link table ui
 * <p style="display:none">
 * version:V1.0,author:Administrator,date:2012-10-17 上午11:16:43,
 * </p>
 * @author 105089-FOSS-yangtong
 * @date 2012-10-17 上午11:16:43
 * @since
 * @version
 */
public class LinkTableMode extends AbstractTableModel {

	private static final long serialVersionUID = 1016386589304301730L;
	
	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(LinkTableMode.class); 

	//log object
	private static Log log = LogFactory.getLog(LinkTableMode.class);

	// 常量配置区
	private String[] tableHeader = {i18n.get("foss.gui.creating.linkTableMode.column.zero"),
			i18n.get("foss.gui.creating.linkTableMode.column.one"),
			i18n.get("foss.gui.creating.linkTableMode.column.two"),
			i18n.get("foss.gui.creating.linkTableMode.column.three"),
			i18n.get("foss.gui.creating.linkTableMode.column.four"),
			i18n.get("foss.gui.creating.linkTableMode.column.five"),
			i18n.get("foss.gui.creating.linkTableMode.column.six"),
			//发货客户
			i18n.get("foss.gui.creating.linkTableMode.column.deliveryCustomer"),
			//发货人
			i18n.get("foss.gui.creating.linkTableMode.column.deliveryContact"),
			//发货客户所属部门
			i18n.get("foss.gui.creating.linkTableMode.column.custOfDept"),
			i18n.get("foss.gui.creating.linkTableMode.column.seven"),
			i18n.get("foss.gui.creating.linkTableMode.column.eight"),
			i18n.get("foss.gui.creating.linkTableMode.column.nine"),
			i18n.get("foss.gui.creating.linkTableMode.column.ten"),
			i18n.get("foss.gui.creating.linkTableMode.column.eleven"),
			i18n.get("foss.gui.creating.linkTableMode.column.twelve"),
			i18n.get("foss.gui.creating.linkTableMode.column.thirteen"),
			i18n.get("foss.gui.creating.linkTableMode.column.fourteen"),
			i18n.get("foss.gui.creating.linkTableMode.column.fifteen"),
			i18n.get("foss.gui.creating.linkTableMode.column.sixteen"),
			i18n.get("foss.gui.creating.linkTableMode.column.seventeen"),
			i18n.get("foss.gui.creating.linkTableMode.column.eighteen")};
	
	private Object[][] data;
	
	public LinkTableMode(Object[][] data){
		this.data = data;
	}
	
	@Override
	public int getColumnCount() {
		
		return tableHeader.length;
		
	}

	@Override
	public int getRowCount() {
		if(data != null){
			return data.length;
		}else	
		{
			return 0;
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(data != null){
			try{
				return data[rowIndex][columnIndex];
			}catch(Exception e){
				return null;
			}
		}else{	
			return null;
		}
		 
	}

	@Override
	public String getColumnName(int column) {
		
		return tableHeader[column];
	}

	/**
	 * 默认情况下这个方法不用重新实现的，但是这样就会造成如果这个列式boolean的类型，就当做string来处理了
	 * 如果是boolean的类型那么用checkbox来显示
	 */
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		Object o = getValueAt(0, columnIndex);
		if (o != null) {
			return o.getClass(); 
		} else { 
			return Null.class; 
		}
		
	}

 
	/**
	 * 来判断当前选中的单元格是够可以被编辑，因为我是从第二列需要可以编辑的，也就是复选框的列可以编辑的，故
	 * 我有个逻辑判断的哈
	 */
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if(columnIndex == 0 || columnIndex ==1) {
			return true;
		}
		return false;
	}

	/**
	 * 如果这个列可以被编辑的话，但是没有这个方法，当回车后是恢复之前的内容的，只有在这个地方通过对新值的
	 * 获取然后再设置进去才会被更改的。
	 */
	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		log.info("[row: "+ rowIndex + ", column: " + columnIndex + ", value: " + value + " ]");
		data[rowIndex][columnIndex] = value;
	}
	
	public static void adjustColumnPreferredWidths(JXTable table) {
		// strategy - get max width for cells in column and
		// make that the preferred width
		TableColumnModel columnModel = table.getColumnModel();
		for (int col = 0; col < table.getColumnCount(); col++) {
			
			TableColumn column = columnModel.getColumn(col);
			
			column.setPreferredWidth(NumberConstants.NUMBER_50);
		} // for col
	}

	public Object[][] getData() {
		return data;
	}

	public void setData(Object[][] data) {
		this.data = data;
	}
	
	

}