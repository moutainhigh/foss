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
 * PROJECT NAME	: pkp-changing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changing/client/ui/internal/message/ChangedInfoPanel.java
 * 
 * FILE NAME        	: ChangedInfoPanel.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.changing.client.ui.internal.message;

import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXTable;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.focuspolicy.annotation.ContainerSeq;
import com.deppon.foss.module.mainframe.client.ui.JDTitledBorder;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcChangeDetailDto;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * 
 * 变更信息Panel
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-10-29 上午9:53:38
 */
@ContainerSeq(seq=10)
public class ChangedInfoPanel extends JPanel {
	
	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 5246664552909817584L;
	
	private static final int THREE = 3;
	
	/**
	 *  国际化
	 */
	private static final II18n i18n = I18nManager.getI18n(ChangedInfoPanel.class);

	private JXTable table;

	/**
	 * 
	 * 构造变更明细页面
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午9:32:29
	 */
	public ChangedInfoPanel() {
		setBorder(new JDTitledBorder(i18n.get("foss.gui.changing.changedInfoPanel.title")));
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				RowSpec.decode("default:grow"),}));

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "2, 1, fill, fill");

		table = new JXTable();
		table.setModel(new ChangeInfoDetailTableModel());
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
	 * 填充变更明细
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午9:32:46
	 */
	public void setChangeDetail(List<WaybillRfcChangeDetailDto> changeDetailList) {
		ChangeInfoDetailTableModel changeInfoDetailTableModel = ((ChangeInfoDetailTableModel) table.getModel());
		changeInfoDetailTableModel.setData(changeDetailList);
		// 刷新表格数据
		changeInfoDetailTableModel.fireTableDataChanged();
	}

	/**
	 * 
	 * 自定义变更明细表格数据模型
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午9:33:05
	 */
	private class ChangeInfoDetailTableModel extends DefaultTableModel {

		/**
		 * 序列化版本号
		 */
		private static final long serialVersionUID = 5883365603131625962L;

		/**
		 * 变更明细集合
		 */
		private List<WaybillRfcChangeDetailDto> changeDetailList;

		/**
		 * 
		 * 获取数据
		 * @author 102246-foss-shaohongliang
		 * @date 2012-12-25 上午9:33:28
		 */
		public List<WaybillRfcChangeDetailDto> getData() {
			return changeDetailList;
		}

		/**
		 * 
		 * 填充数据
		 * @author 102246-foss-shaohongliang
		 * @date 2012-12-25 上午9:33:36
		 */
		public void setData(List<WaybillRfcChangeDetailDto> changeDetailList) {
			this.changeDetailList = changeDetailList;
		}

		@Override
		public String getColumnName(int column) {
			switch (column) {
			case 0:
				//变更项
				return i18n.get("foss.gui.changing.changedInfoPanel.GridChangeItem.label");
			case 1:
				//变更前信息
				return i18n.get("foss.gui.changing.changedInfoPanel.GridChangeBefore.label");
			case 2:
				//变更后信息
				return i18n.get("foss.gui.changing.changedInfoPanel.GridChangeAfter.label");
			default:
				return "";
			}
		}

		@Override
		public int getColumnCount() {
			return THREE;
		}

		@Override
		public int getRowCount() {
			return changeDetailList == null ? 0 : changeDetailList.size();
		}

		@Override
		public Object getValueAt(int row, int column) {
			switch (column) {
			case 0:
				//变更项
				return changeDetailList.get(row).getRfcItems();
			case 1:
				//变更前
				return changeDetailList.get(row).getBeforeRfcInfo();
			case 2:
				//变更后
				return changeDetailList.get(row).getAfterRfcInfo();
			default:
				return super.getValueAt(row, column);
			}

		}
	}
	
	/**
	 * 
	 * 获取变更明细数据
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午9:34:12
	 */
	public List<WaybillRfcChangeDetailDto> getTableData() {
		ChangeInfoDetailTableModel changeInfoDetailTableModel = ((ChangeInfoDetailTableModel) table.getModel());
		return changeInfoDetailTableModel.getData();
	}
}