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
 * PROJECT NAME	: pkp-changingexp
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/ui/internal/transport/ReturnRecordPanel.java
 * 
 * FILE NAME        	: ReturnRecordPanel.java
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
package com.deppon.foss.module.pickup.changingexp.client.ui.internal.transport;

import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXTable;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.module.pickup.changingexp.client.vo.TransportRecordVo;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * 
 * 返货记录面板
 * @author 102246-foss-shaohongliang
 * @date 2012-12-25 上午9:39:08
 */
public class ReturnRecordPanel extends JPanel {
	
	private static final int THREE = 3;

	private static final int FOUR = 4;

	private static final int FIVE = 5;

	private static final int SIX = 6;

	private static final int SEVEN = 7;
	
	/**
	 *  国际化
	 */
	private static II18n i18n = I18nManager.getI18n(ReturnRecordPanel.class);

	/**
	 * DEFAULT CSS
	 */
	private static final String DEFAULTGROW = "default:grow";

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 2548707145140644321L;
	
	/**
	 * 返货记录Model
	 */
	private ReturnRecordTableModel tableModel;

	/**
	 * 
	 * ReturnRecordPanel
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午9:39:40
	 */
	public ReturnRecordPanel() {
		init();
	}
	
	/**
	 * 
	 * 初始化布局
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午9:39:58
	 */
	private void init() {
		setLayout(new FormLayout(new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode(DEFAULTGROW), FormFactory.RELATED_GAP_COLSPEC, }, new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("min(55dlu;default)"), FormFactory.RELATED_GAP_ROWSPEC, }));

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "2, 2, fill, fill");

		JXTable table = new JXTable();
		tableModel = new ReturnRecordTableModel();
		table.setModel(tableModel);
		
		table.setAutoscrolls(true);
		table.setSortable(false);
		table.setEditable(false);
		table.setColumnControlVisible(true);

		// 居中显示
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		render.setHorizontalAlignment(SwingConstants.CENTER);
		table.setDefaultRenderer(Object.class, render);
		((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

		scrollPane.setViewportView(table);
	}

	/**
	 * 
	 * 填充返货记录
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午9:40:07
	 */
	public void setRecordList(List<TransportRecordVo> returnRecordVos) {
		tableModel.setData(returnRecordVos);
		tableModel.fireTableDataChanged();
		
	}
	
	/**
	 * 
	 * 自定义返货记录表格模型
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午9:40:17
	 */
	private class ReturnRecordTableModel extends DefaultTableModel {

		/**
		 * 序列化版本号
		 */
		private static final long serialVersionUID = 5883365603131625962L;

		/**
		 * 返货记录
		 */
		private List<TransportRecordVo> data;

		/**
		 * 
		 * 填充返货记录
		 * @author 102246-foss-shaohongliang
		 * @date 2012-12-25 上午9:40:41
		 */
		public void setData(List<TransportRecordVo> data) {
			this.data = data;
		}

		@Override
		public String getColumnName(int column) {
			switch (column) {
			case 0:
				//返货提货方式
				return i18n.get("foss.gui.changingexp.transportInfoPanel.returnPickMode.label");
			case 1:
				//返货目的站
				return i18n.get("foss.gui.changingexp.transportInfoPanel.returnDestination.label");
			case 2:
				//返货提货网点
				return i18n.get("foss.gui.changingexp.transportInfoPanel.returnPickBranch.label");
			case THREE:
				//返货运输性质
				return i18n.get("foss.gui.changingexp.transportInfoPanel.returnProductType.label");
			case FOUR:
				//返货计费类型
				return i18n.get("foss.gui.changingexp.transportInfoPanel.returnBillingType.label");
			case FIVE:
				//返货费率
				return i18n.get("foss.gui.changingexp.transportInfoPanel.returnUnitPrice.label");
			case SIX:
				//返货费
				return i18n.get("foss.gui.changingexp.transportInfoPanel.returnFee.label");
			default:
				return "";
			}
		}

		@Override
		public int getColumnCount() {
			return SEVEN;
		}

		@Override
		public int getRowCount() {
			return data == null ? 0 : data.size();
		}

		@Override
		public Object getValueAt(int row, int column) {
			switch (column) {
			case 0:
				//提货方式
				return data.get(row).getReceiveMethod();
			case 1:
				//目的站
				return data.get(row).getTargetOrgCode();
			case 2:
				//提货网点
				return data.get(row).getCustomerPickupOrgName();
			case THREE:
				//运输性质
				return data.get(row).getProductCode();
			case FOUR:
				//计费类型
				return data.get(row).getBillingType();
			case FIVE:
				//费率
				return data.get(row).getUnitPrice();
			case SIX:
				//返货费
				return data.get(row).getTransportFee();
			default:
				return super.getValueAt(row, column);
			}

		}
	}
}