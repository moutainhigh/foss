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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/ui/internal/transport/TransferRecordPanel.java
 * 
 * FILE NAME        	: TransferRecordPanel.java
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
 * 转运记录面板
 * @author 102246-foss-shaohongliang
 * @date 2012-12-25 上午9:47:16
 */
public class TransferRecordPanel extends JPanel {

	/**
	 *  国际化
	 */
	private static II18n i18n = I18nManager.getI18n(TransferRecordPanel.class);
	
	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 5619134124505221955L;
	
	/**
	 * 转运记录Model
	 */
	private TransferRecordTableModel tableModel;

	/**
	 * 
	 * TransferRecordPanel
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午9:47:38
	 */
	public TransferRecordPanel() {
		init();
	}

	/**
	 * 
	 * 布局初始化
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午9:48:50
	 */
	private void init() {
		setLayout(new FormLayout(new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), FormFactory.RELATED_GAP_COLSPEC, }, new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("min(55dlu;default)"), FormFactory.RELATED_GAP_ROWSPEC, }));

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "2, 2, fill, fill");

		JXTable table = new JXTable();
		tableModel = new TransferRecordTableModel();
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
	 * 填充转运记录
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午9:49:11
	 */
	public void setRecordList(List<TransportRecordVo> transferRecordVos) {
		tableModel.setData(transferRecordVos);
		tableModel.fireTableDataChanged();
	}
	
	/**
	 * 
	 * 转运记录数据模型
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午9:49:29
	 */
	private class TransferRecordTableModel extends DefaultTableModel {

		private static final int EIGHT = 8;

		private static final int SEVEN = 7;

		private static final int SIX = 6;

		private static final int FIVE = 5;

		private static final int FOUR = 4;

		private static final int THREE = 3;

		private static final int TWO = 2;

		private static final int ONE = 1;

		private static final int ZERO = 0;

		/**
		 * 序列化版本号
		 */
		private static final long serialVersionUID = 5883365603131625962L;

		private List<TransportRecordVo> data;

		public void setData(List<TransportRecordVo> data) {
			this.data = data;
		}

		@Override
		public String getColumnName(int column) {
			switch (column) {
			case ZERO:
				//转运提货方式
				return i18n.get("foss.gui.changingexp.transportInfoPanel.transferPickMode.label");
			case ONE:
				//转运目的站
				return i18n.get("foss.gui.changingexp.transportInfoPanel.transferDestination.label");
			case TWO:
				//转运提货网点
				return i18n.get("foss.gui.changingexp.transportInfoPanel.transferPickBranch.label");
			case THREE:
				//转运运输性质
				return i18n.get("foss.gui.changingexp.transportInfoPanel.transferProductType.label");
			case FOUR:
				//转运航班类型
				return i18n.get("foss.gui.changingexp.transportInfoPanel.transferPredictFlight.label");
			case FIVE:
				//转运计费类型
				return i18n.get("foss.gui.changingexp.transportInfoPanel.transferBillingType.label");
			case SIX:
				//转运费率
				return i18n.get("foss.gui.changingexp.transportInfoPanel.transferUnitPrice.label");
			case SEVEN:
				//转运费
				return i18n.get("foss.gui.changingexp.transportInfoPanel.transferFee.label");
			default:
				return "";
			}
		}

		@Override
		public int getColumnCount() {
			return EIGHT;
		}

		@Override
		public int getRowCount() {
			return data == null ? 0 : data.size();
		}

		@Override
		public Object getValueAt(int row, int column) {
			switch (column) {
			case ZERO:
				//转运提货方式
				return data.get(row).getReceiveMethod();
			case ONE:
				//转运目的站
				return data.get(row).getTargetOrgCode();
			case TWO:
				//转运提货网点
				return data.get(row).getCustomerPickupOrgName();
			case THREE:
				//转运运输性质
				return data.get(row).getProductCode();
			case FOUR:
				//转运预配航班
				return data.get(row).getFlightShift();
			case FIVE:
				//转运计费类型
				return data.get(row).getBillingType();
			case SIX:
				//转运费率
				return data.get(row).getUnitPrice();
			case SEVEN:
				//转运费
				return data.get(row).getTransportFee();
			default:
				return super.getValueAt(row, column);
			}

		}
	}
}