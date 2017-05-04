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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/ui/PublishPriceLinkTableMode.java
 * 
 * FILE NAME        	: PublishPriceLinkTableMode.java
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
package com.deppon.foss.module.pickup.common.client.ui;

import java.awt.Component;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.apache.commons.lang.ObjectUtils.Null;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.swingx.JXTable;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.module.pickup.common.client.ui.customer.QueryMemberDialog;

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
public class PublishPriceLinkTableMode  extends AbstractTableModel {
	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1016386589304301730L;
	
/*	private static final int ZERO = 0;

	private static final int ONE = 1;

	private static final int TWO = 2;

	private static final int THREE = 3;

	private static final int FOUR = 4;

	private static final int FIVE = 5;

	private static final int SIX = 6;

	private static final int SEVEN = 7;

	private static final int EIGHT = 8;

	private static final int NIVE = 9;

	private static final int TEN = 10;

	private static final int ELEVEN = 11;

	private static final int TWELVE = 12;
*/
	//log object
	private static Log LOG = LogFactory.getLog(PublishPriceLinkTableMode.class);
	
	/**
	 * 国际化
	 */
	private II18n i18n = I18nManager.getI18n(QueryMemberDialog.class);

	/**
	 * 常量配置区
	 */
	private String[] tableHeader = {
			i18n.get("foss.gui.common.waybillEditUI.common.select"),
			i18n.get("foss.gui.creating.queryPublishPriceUI.targetOrgCode.label"),
			i18n.get("foss.gui.common.PublishPriceLinkTableMode.tableName.productInfo"),
			i18n.get("foss.gui.common.PublishPriceLinkTableMode.tableName.heavyCargo"),
			i18n.get("foss.gui.common.PublishPriceLinkTableMode.tableName.lowCargo"),
			i18n.get("foss.gui.common.PublishPriceLinkTableMode.tableName.operatingTime"),
			i18n.get("foss.gui.common.PublishPriceLinkTableMode.tableName.fetchGoodsTime"),
			i18n.get("foss.gui.common.PublishPriceLinkTableMode.tableName.longShortDistinct"),
			i18n.get("foss.gui.common.PublishPriceLinkTableMode.tableName.lowestGoods"),
			i18n.get("foss.gui.common.PublishPriceLinkTableMode.tableName.flightTime"),
			i18n.get("foss.gui.common.PublishPriceLinkTableMode.tableName.isPickup"), 
			i18n.get("foss.gui.common.PublishPriceLinkTableMode.tableName.discriptionInfo"),
			i18n.get("foss.gui.common.PublishPriceLinkTableMode.tableName.flagDownFare")};
	/**
	 * 表格数据
	 */
	//private List<PublishPriceEntity> data;
	private Object[][] data;
	/**
	 * 构造方法
	 * @param data
	 */
	/*public PublishPriceLinkTableMode(List<PublishPriceEntity> data){
		this.data = data;
	}*/
	public PublishPriceLinkTableMode(Object[][] data){
		this.data = data;
	}
	/**
	 * 功能：getColumnCount
	 * 
	 * @param:
	 * @return:
	 * @since:1.6
	 */
	public int getColumnCount() {	
		return tableHeader.length;
		
	}

	/**
	 * 功能：getRowCount
	 * 
	 * @param:
	 * @return:
	 * @since:1.6
	 */
	public int getRowCount() {
		if(data != null){
			//return data.size();
			return data.length;
		}else{	
			return 0;
		}
	}

	/**
	 * 功能：getValueAt
	 * 
	 * @param:
	 * @return:
	 * @since:1.6
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(data != null){
			return data[rowIndex][columnIndex];
		}else{	
			return null;
		}
		/*if(CollectionUtils.isNotEmpty(data)){
			switch (columnIndex) {
			case ZERO:
				return false;//是否选择
			case ONE:
				return data.get(rowIndex).getArrvRegionName();//目的站
			case TWO:
				return data.get(rowIndex).getProductItemName();//产品信息
			case THREE:
				return data.get(rowIndex).getHeavyPrice();//重货价格
			case FOUR:
				return data.get(rowIndex).getLightPrice();//轻货价格
			case FIVE:
				if(StringUtils.isNotEmpty(data.get(rowIndex).getEffectiveUnit()) 
						&& CommonConstants.TIME_DAY.equals(data.get(rowIndex).getEffectiveUnit())){
					return data.get(rowIndex).getArriveTime()+"("+i18n.get("foss.gui.common.QueryPublishPriceUI.discription.day")+")";//营运时效
				}else if(StringUtils.isNotEmpty(data.get(rowIndex).getEffectiveUnit()) 
						&& CommonConstants.TIME_HOURS.equals(data.get(rowIndex).getEffectiveUnit())){
					return data.get(rowIndex).getArriveTime()+"("+i18n.get("foss.gui.common.QueryPublishPriceUI.discription.hour")+")";//营运时效
				}else if(StringUtils.isNotEmpty(data.get(rowIndex).getArriveTime())){
					return data.get(rowIndex).getArriveTime();
				}else{
					return "";
				}
			case SIX:
				return data.get(rowIndex).getPickupTime();//取货时间
			case SEVEN:
				return data.get(rowIndex).getLongOrShort();//长短途
			case EIGHT:
				return data.get(rowIndex).getMinFee();//最低一票
			case NIVE:
				return data.get(rowIndex).getFlightShiftNo();//航班时间
			case TEN:
				if(StringUtils.isNotEmpty(data.get(rowIndex).getCentralizePickup()) 
						&& StringUtils.equals(data.get(rowIndex).getCentralizePickup(), FossConstants.YES)){
					return i18n.get("foss.gui.common.waybillEditUI.common.yes");
				}else if(StringUtils.isNotEmpty(data.get(rowIndex).getCentralizePickup()) 
						&& StringUtils.equals(data.get(rowIndex).getCentralizePickup(), FossConstants.NO)){
					return i18n.get("foss.gui.common.waybillEditUI.common.no");
				}else{
					return "";
				}
			case ELEVEN:
				return "";//描述备注
			case TWELVE:
				return data.get(rowIndex).getDeliveryCharges();//送货起步价
			default:
				return "";
			} 
		}
		return null;*/
	}


	/**
	 * 功能：getColumnName
	 * 
	 * @param:
	 * @return:
	 * @since:1.6
	 */
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
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		LOG.info("[row: "+ rowIndex + ", column: " + columnIndex + ", value: " + value + " ]");
		data[rowIndex][columnIndex] = value;
	}
	
	/**
	 * 调整列宽度 
	 * @param table
	 */
	public static void adjustColumnPreferredWidths(JXTable table) {
		// strategy - get max width for cells in column and
		// make that the preferred width
		TableColumnModel columnModel = table.getColumnModel();
		for (int col = 0; col < table.getColumnCount(); col++) {
			int maxwidth = 0;
			for (int row = 0; row < table.getRowCount(); row++) {
				TableCellRenderer rend =
				table.getCellRenderer(row, col);
				Object value = table.getValueAt(row, col);
				Component comp =
				rend.getTableCellRendererComponent(table,value,	false,false,row,col);
				maxwidth = Math.max(comp.getPreferredSize().width, maxwidth);

			} // for row
			// TableColumn column = columnModel.getColumn (col);
			// column.setPreferredWidth (maxwidth);
			TableColumn column = columnModel.getColumn(col);
			TableCellRenderer headerRenderer = column.getHeaderRenderer();
			if (headerRenderer == null){
				headerRenderer = table.getTableHeader().getDefaultRenderer();
			}
			Object headerValue = column.getHeaderValue();
			Component headerComp =	headerRenderer.getTableCellRendererComponent(table,headerValue,false,false,	0,col);
			maxwidth = Math.max(maxwidth,
			headerComp.getPreferredSize().width);
			column.setPreferredWidth(maxwidth);
		} // for col
	}
	/**
	 * 获得数据
	 * @return
	 */
	/*public List<PublishPriceEntity> getData() {
		return data;
	}*/
	public Object[][] getData() {
		return data;
	}

	/**
	 * 设置数据
	 * @param data
	 */
	/*public void setData(List<PublishPriceEntity> data) {
		this.data = data;
	}*/
	public void setData(Object[][] data) {
		this.data = data;
	}
	

}