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
package com.deppon.foss.module.pickup.creatingexp.client.ui;

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
 * 电子面单查询对应的TableModel
 * @author Foss-105888-Zhangxingwang
 * @date 2015-1-16 13:52:34
 */
public class ExpEWaybillTableMode extends AbstractTableModel {

	private static final long serialVersionUID = 1016386589304301730L;
	
	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(ExpEWaybillTableMode.class);

	//log object
	private static Log log = LogFactory.getLog(ExpEWaybillTableMode.class);

	// 常量配置区
	private String[] tableHeader = {
		//{单号,订单号,开单部门,收入部门,下单时间,FOSS激活时间,扫描时间,发货客户编码,发货联系人,收货联系人,出发城市,到达城市,快递员工号,订单来源,
		//运单状态,订单重量,运单重量,订单体积,运单体积,订单件数,运单件数,付款方式,总费用,代收货款金额,订单退回原因
			i18n.get("foss.gui.creating.expEWaybillTableMode.column.zero"),//选择
			i18n.get("foss.gui.creating.expEWaybillTableMode.column.one"),//操作列
			i18n.get("foss.gui.creating.expEWaybillTableMode.column.two"),//运单状态
			i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.Butten.create.wayBillNO"),//单号
//			i18n.get("foss.gui.creating.expEWaybillTableMode.column.four"),//订单号
//			i18n.get("foss.gui.creating.expEWaybillTableMode.column.five"),//开单部门
			i18n.get("foss.gui.creating.expEWaybillTableMode.column.six"),//收入部门
			i18n.get("foss.gui.creating.expEWaybillTableMode.column.seven"),//下单时间
//			i18n.get("foss.gui.creating.expEWaybillTableMode.column.eight"),//开单时间
			i18n.get("foss.gui.creating.expEWaybillTableMode.column.nine"),//扫描时间
			i18n.get("foss.gui.creating.expEWaybillTableMode.column.eleven"),//发货客户编码
			i18n.get("foss.gui.creating.expEWaybillTableMode.column.twelve"),//发货联系人
//			i18n.get("foss.gui.creating.expEWaybillTableMode.column.thirteen"),//收货联系人
//			i18n.get("foss.gui.creating.expEWaybillTableMode.column.fourteen"),//出发城市
//			i18n.get("foss.gui.creating.expEWaybillTableMode.column.fifteen"),//到达城市
			i18n.get("foss.gui.creating.expEWaybillTableMode.column.seventeen"),//快递员工号
//			i18n.get("foss.gui.creating.expEWaybillTableMode.column.eighteen"),//订单来源
//			i18n.get("foss.gui.creating.numberPanel.transferInfoPanel.productCode.label"),//运输性质
//			i18n.get("foss.gui.creating.expEWaybillTableMode.column.twenty"),//订单重量
//			i18n.get("foss.gui.creating.expEWaybillTableMode.column.twentyone"),//运单重量
//			i18n.get("foss.gui.creating.expEWaybillTableMode.column.twentytwo"),//订单体积
//			i18n.get("foss.gui.creating.expEWaybillTableMode.column.twentythree"),//运单体积
//			i18n.get("foss.gui.creating.expEWaybillTableMode.column.twentyfour"),//订单件数
//			i18n.get("foss.gui.creating.expEWaybillTableMode.column.twentyfive"),//运单件数
//			i18n.get("foss.gui.creating.expEWaybillTableMode.column.twentysix"),//付款方式
//			i18n.get("foss.gui.creating.expEWaybillTableMode.column.twentyseven"),//总费用
//			i18n.get("foss.gui.creating.expEWaybillTableMode.column.twentyeight"),//代收货款金额
			i18n.get("foss.gui.creating.expEWaybillTableMode.column.twentynine"),//订单退回原因
//			i18n.get("foss.gui.creating.expEWaybillTableMode.column.fixty"),//运单生成失败原因
//			i18n.get("foss.gui.creating.expEWaybillTableMode.column.fixtyone"),//库存部门
//			i18n.get("foss.gui.creating.expEWaybillTableMode.column.fixtytwo"),//卸车人
//			i18n.get("foss.gui.creating.expEWaybillTableMode.column.fixtythree")//卸车时间
	};
	
	private Object[][] data;
	
	public ExpEWaybillTableMode(Object[][] data){
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
		}
	}

	public Object[][] getData() {
		return data;
	}

	public void setData(Object[][] data) {
		this.data = data;
	}
	
	

}