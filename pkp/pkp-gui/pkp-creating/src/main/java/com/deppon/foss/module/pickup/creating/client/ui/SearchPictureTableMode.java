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

import javax.swing.table.DefaultTableModel;
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
public class SearchPictureTableMode extends DefaultTableModel {

	private static final long serialVersionUID = 1016386589304301730L;

	private static final int FOUR = 4;

	private static final int NUM_180 = 180;

	private static final int TEN = 10;

	private static final int NUM_130 = 130;

	private static final int NUM_150 = 150;

	private static final int NUM_330 = 330;
	
	private static final int SEVENTEEN = 17;
	
	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(SearchPictureTableMode.class); 

	//log object
	private static Log log = LogFactory.getLog(SearchPictureTableMode.class);

	// 常量配置区
	private String[] tableHeader = {
			i18n.get("foss.gui.creating.SearchPictureTableMode.tableHeader.no"),
			i18n.get("foss.gui.creating.SearchPictureTableMode.tableHeader.operate"),
			i18n.get("foss.gui.creating.SearchPictureTableMode.tableHeader.waybillNo"),
			i18n.get("foss.gui.creating.SearchPictureTableMode.tableHeader.orderNo"),
			i18n.get("foss.gui.creating.SearchPictureTableMode.tableHeader.picture"),
			i18n.get("foss.gui.creating.SearchPictureTableMode.tableHeader.status"),
			i18n.get("foss.gui.creating.SearchPictureTableMode.tableHeader.remark"),
			i18n.get("foss.gui.creating.SearchPictureTableMode.tableHeader.driverCode"),
			i18n.get("foss.gui.creating.SearchPictureTableMode.tableHeader.driverName"),
			i18n.get("foss.gui.creating.SearchPictureTableMode.tableHeader.vehicleNo"),
			i18n.get("foss.gui.creating.SearchPictureTableMode.tableHeader.receiveOrgName"),
			i18n.get("foss.gui.creating.SearchPictureTableMode.tableHeader.operator"),
			i18n.get("foss.gui.creating.SearchPictureTableMode.tableHeader.operatorName"),
			"hidecol1",
			"hidecol2",
			"hidecol3",
			"hidecol4",
			i18n.get("foss.gui.creating.SearchPictureTableMode.tableHeader.bill.time"),
			"hidecol6",
			"hidecol7",
			"hidecol8",
			i18n.get("foss.gui.creating.SearchPictureTableMode.tableHeader.driver.img.upload.time"),
			i18n.get("foss.gui.creating.SearchPictureTableMode.tableHeader.transport.properties"),
			i18n.get("foss.gui.creating.SearchPictureTableMode.tableHeader.destination.station"),
			i18n.get("foss.gui.creating.SearchPictureTableMode.tableHeader.pending.user"),
			i18n.get("foss.gui.creating.SearchPictureTableMode.tableHeader.pending.org")
	};
	
	private Object[][] data;
	
	public SearchPictureTableMode(Object[][] data){
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
		if(columnIndex == FOUR || columnIndex ==1) {
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
			if(col >= NumberConstants.NUMBER_13 && col<=NumberConstants.NUMBER_20 && col != NumberConstants.NUMBER_17){//去掉备注
				column.setMinWidth(0);
		        column.setMaxWidth(0);
		        column.setWidth(0);
		        column.setPreferredWidth(0);
		        continue;
			}
			if (col == 0 || col == FOUR) {
				column.setPreferredWidth(NumberConstants.NUMBER_50);
				column.setMaxWidth(NumberConstants.NUMBER_50);
			} else if (col == 1) {
				column.setPreferredWidth(NUM_330);//250
				column.setMaxWidth(NUM_330);//
			} else if (col == SEVENTEEN) {
				column.setPreferredWidth(NUM_130);
				column.setMaxWidth(NUM_130);
			}else if (col == TEN) {
				column.setPreferredWidth(NUM_180);
				column.setMaxWidth(NUM_180);
			} else if(col == NumberConstants.NUMBER_20) {
				column.setPreferredWidth(NUM_150);
				column.setMaxWidth(NUM_150);
			}else {
				column.setPreferredWidth(NumberConstants.NUMBER_100);
			}
		} // for col
	}

	public Object[][] getData() {
		return data;
	}

	public void setData(Object[][] data) {
		this.data = data;
	}

	/**
	 * table界面移除行，可自动更新序号值和table界面
	 * @param serialNum serialNum 开始序号值；如果serialNum小于0则不会自动更新序号
	 * @param row 要移除的行，从0开始
	 */
	public void removeRow(int serialNum,int row) {
		Object[][] tmp = new Object[data.length-1][] ;
		for(int i=0;i<data.length-1;i++){
			if(i < row){
				tmp[i] = data[i] ;
			}else{
				tmp[i] = data[i+1] ;
			}
			if(serialNum >= 0){
				tmp[i][0] = i+serialNum ;
			}
		}
		data  = tmp ;
		this.fireTableDataChanged();//
	}
	
	

}