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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/ui/customer/BankAccountDialog.java
 * 
 * FILE NAME        	: BankAccountDialog.java
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
package com.deppon.foss.module.pickup.common.client.ui.customer;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAccountEntity;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import org.jdesktop.swingx.JXTable;
import javax.swing.JScrollPane;

/**
 * 
 * 代收货款选择银行账户信息
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2013-1-11 下午1:46:32,content:TODO </p>
 * @author foss-sunrui
 * @date 2013-1-11 下午1:46:32
 * @since
 * @version
 */
public class BankAccountDialog extends JDialog {

	private static final String GROW = "50dlu:grow";

	private static final long serialVersionUID = 5763203112274581813L;
	
	/**
	 * 国际化
	 */
	private II18n i18n = I18nManager.getI18n(BankAccountDialog.class);

	private JXTable table;

	private CusAccountEntity cusAccountEntity;

	/**
	 * Create the dialog.
	 */
	public BankAccountDialog(List<CusAccountEntity> list) {
		initTable();
		init();
		setData(list);
		//默认选中结果的第一行
		selectFirstRow();
		//Table列表Enter键监控
		EnterKeyForBankAccount enterTable=new EnterKeyForBankAccount(this);
		table.addKeyListener(enterTable);
	}

	/**
	 * 
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-6 下午04:07:03
	 */
	private void init() {
		getContentPane().setLayout(
				new FormLayout(new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("10dlu"), FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("35dlu"), FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode(GROW), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode(GROW),
						FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode(GROW), FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode(GROW), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode(GROW),
						FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("10dlu"), }, new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("60dlu"), }));

		JScrollPane scrollPane = new JScrollPane(table);
		getContentPane().add(scrollPane, "1, 2, 16, 5, fill, fill");

		
		// 设置模态
		setModal(true);
		pack();
	}

	/**
	 * 
	 * 初始化表格
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-3 上午10:47:03
	 */
	private void initTable() {
		table = new JXTable();
		table.setModel(new BankAccountModel());
		table.setAutoscrolls(true);
		table.setSortable(false);
		table.setEditable(false);
		table.setColumnControlVisible(true);

		// 居中显示
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		render.setHorizontalAlignment(SwingConstants.CENTER);
		table.setDefaultRenderer(Object.class, render);
		((DefaultTableCellRenderer) table.getTableHeader()
				.getDefaultRenderer())
				.setHorizontalAlignment(SwingConstants.CENTER);
		table.addMouseListener(new ClickTableHandler());
	}

	/**
	 * 一般内部类：表格的单、双击处理事件
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-23 上午10:16:42
	 */
	private class ClickTableHandler extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			// 双击
			if (e.getClickCount() == 2) {
				setBankAccountInfo();
				// 关闭界面，释放资源
				dispose();
			}
		}
	}
	
	/**
	 * Enter键处理事件
	 * @author WangQianJin
	 * @date 2013-5-18 上午10:11:58
	 */
	public void tableEnter(){
		setBankAccountInfo();
		// 关闭界面，释放资源
		dispose();
	}
	
	/**
	 * 默认选中查询结果的第一行
	 * @author WangQianJin
	 * @date 2013-5-20 上午10:11:58
	 */
	private void selectFirstRow(){
		if(table!=null && table.getRowCount()>0){
			table.requestFocus();
			table.setRowSelectionAllowed(true);
			table.setRowSelectionInterval(0,0);
		}
	}
	
	/**
	 * 
	 * 设置银行信息
	 * @author 025000-FOSS-helong
	 * @date 2012-12-6 下午07:40:27
	 */
	private void setBankAccountInfo()
	{
		int row = table.getSelectedRow();
		BankAccountModel model = ((BankAccountModel) table.getModel());
		List<CusAccountEntity> list = model.getBankInfoList();
		if(list != null)
		{
			if(!list.isEmpty())
			{
				cusAccountEntity = list.get(row);
			}
		}
	}

	/**
	 * 
	 * 将数据添加到表格
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-3 上午11:29:46
	 */
	public void setData(List<CusAccountEntity> bankList) {
		BankAccountModel model = ((BankAccountModel) table.getModel());
		model.setBankInfoList(bankList);
		// 刷新表格数据
		model.fireTableDataChanged();
	}

	public CusAccountEntity getCusAccountEntity() {
		return cusAccountEntity;
	}

	public void setCusAccountEntity(CusAccountEntity cusAccountEntity) {
		this.cusAccountEntity = cusAccountEntity;
	}

	/**
	 * 
	 * 封装表格的数据模型，设置成以对象进行操作
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-3 上午11:30:03
	 */
	public class BankAccountModel extends DefaultTableModel {

		private static final int THREE = 3;

		private static final int TWO = 2;

		private static final int ONE = 1;

		private static final int ZERO = 0;

		private static final long serialVersionUID = 5883365603131625962L;

		private List<CusAccountEntity> bankInfoList;

		public List<CusAccountEntity> getBankInfoList() {
			return bankInfoList;
		}

		public void setBankInfoList(List<CusAccountEntity> bankInfoList) {
			this.bankInfoList = bankInfoList;
		}

		@Override
		public String getColumnName(int column) {
			switch (column) {
			case ZERO:
				return i18n.get("foss.gui.common.bankAccountDialog.columnName.accountName");
			case ONE:
				return i18n.get("foss.gui.common.bankAccountDialog.columnName.account");
			case TWO:
				return i18n.get("foss.gui.common.bankAccountDialog.columnName.accountBank");
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
			return bankInfoList == null ? 0 : bankInfoList.size();
		}

		@Override
		public Object getValueAt(int row, int column) {
			switch (column) {
			case ZERO:
				return bankInfoList.get(row).getAccountName();
			case ONE:
				return bankInfoList.get(row).getAccountNo();
			case TWO:
				return bankInfoList.get(row).getOpeningBankName();
			default:
				return super.getValueAt(row, column);
			}
		}
	}

}