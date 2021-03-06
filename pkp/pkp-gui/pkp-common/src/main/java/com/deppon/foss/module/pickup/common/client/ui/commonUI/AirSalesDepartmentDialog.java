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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/ui/commonUI/AirSalesDepartmentDialog.java
 * 
 * FILE NAME        	: AirSalesDepartmentDialog.java
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

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JScrollPane;

import org.jdesktop.swingx.JXTable;

/**
 * 
 * 查询空运配载部门
 * @author 025000-FOSS-helong
 * @date 2013-1-21 下午05:36:52
 */
public class AirSalesDepartmentDialog extends JDialog {
	
	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = -5960720224792355823L;
	
	private static final int NUM_407 = 407;
	
	private static final int NUM_531 = 531;
	
	private static final int FIVE = 5;
	
	/**
	 * 国际化
	 */
	private II18n i18n = I18nManager.getI18n(AirSalesDepartmentDialog.class);

	/**
	 * panel
	 */
	private final JPanel contentPanel = new JPanel();
	
	/**
	 * 结果列表
	 */
	private JXTable table;
	
	/**
	 * 数据实体
	 */
	private OrgAdministrativeInfoEntity airDepartmentEntity;

	/**
	 * Create the dialog.
	 */
	public AirSalesDepartmentDialog(List<OrgAdministrativeInfoEntity> airDept) {
		initTable();
		init();
		setData(airDept);
	}
	
	/**
	 * 初始化界面信息
	 */
	private void init()
	{
		setBounds(NumberConstants.NUMBER_100, NumberConstants.NUMBER_100, NUM_531, NUM_407);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(FIVE, FIVE, FIVE, FIVE));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		{
			JScrollPane scrollPane = new JScrollPane(table);
			contentPanel.add(scrollPane, "2, 2, 19, 3, fill, fill");
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						setSaleDepartmentInfo();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		// 设置模态
		setModal(true);
	}
	
	
	/**
	 * 
	 * 初始化表格
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-3 上午10:47:03
	 */
	private void initTable() {
		//初始化表格
		table = new JXTable();
		//设置表格数据模型
		table.setModel(new AirDepartmentModel());
		//设置表格可以有滚动条
		table.setAutoscrolls(true);
		//禁止表格排序
		table.setSortable(false);
		//表格设置为不可编辑
		table.setEditable(false);
		//设置列可见状态
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
	 * 
	 * 将数据添加到表格
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-3 上午11:29:46
	 */
	public void setData(List<OrgAdministrativeInfoEntity> saleDepartment) {
		AirDepartmentModel model = ((AirDepartmentModel) table.getModel());
		model.setData(saleDepartment);
		// 刷新表格数据
		model.fireTableDataChanged();
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
				setSaleDepartmentInfo();
			}
		}
	}
	
	
	/**
	 * 
	 * 设置银行信息
	 * @author 025000-FOSS-helong
	 * @date 2012-12-6 下午07:40:27
	 */
	private void setSaleDepartmentInfo()
	{
		//获取表格点击行数
		int row = table.getSelectedRow();
		if(row < 0)
		{
			MsgBox.showInfo(i18n.get("foss.gui.creating.innerPickupSalesDepartmentDialog.msgbox.select"));
		}else
		{
			//获取表格数据模型
			AirDepartmentModel model = ((AirDepartmentModel) table.getModel());
			//获取表格所有行记录
			List<OrgAdministrativeInfoEntity> list = model.getData();
			if(list != null)
			{
				if(!list.isEmpty())
				{
					//获取选择的行记录
					airDepartmentEntity = list.get(row);
					// 关闭界面，释放资源
					dispose();
				}
			}
		}
	}

	/**
	 * 
	 * 封装表格的数据模型，设置成以对象进行操作
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-3 上午11:30:03
	 */
	public class AirDepartmentModel extends DefaultTableModel {

		private static final long serialVersionUID = 5883365603131625962L;

		/**
		 * 表格数据集合
		 */
		private List<OrgAdministrativeInfoEntity> data;
		
		/**
		 * 表格数据集合
		 */
		public List<OrgAdministrativeInfoEntity> getData() {
			return data;
		}

		/**
		 * 表格数据集合
		 */
		public void setData(List<OrgAdministrativeInfoEntity> data) {
			this.data = data;
		}

		/**
		 * 
		 * 重写表格获取列名称方法
		 * @author 025000-FOSS-helong
		 * @date 2012-12-24 下午09:03:18
		 * @param column
		 * @return
		 */
		@Override
		public String getColumnName(int column) {
			switch (column) {
			case 0:
				return i18n.get("foss.gui.common.basicPanel.departmentName.label");
			case 1:
				return i18n.get("foss.gui.common.basicPanel.departmentCode.label");
			default:
				return "";
			}
		}

		/**
		 * 
		 * 重写表格获取列总数方法
		 * @author 025000-FOSS-helong
		 * @date 2012-12-24 下午09:03:18
		 * @param column
		 * @return
		 */
		@Override
		public int getColumnCount() {
			return 2;
		}

		
		/**
		 * 
		 * 重写表格获取行总数方法
		 * @author 025000-FOSS-helong
		 * @date 2012-12-24 下午09:03:18
		 * @param column
		 * @return
		 */
		@Override
		public int getRowCount() {
			return data == null ? 0 : data.size();
		}

		

		/**
		 * 
		 * 重写获取数据的方法
		 * @author 025000-FOSS-helong
		 * @date 2012-12-24 下午09:08:19
		 * @param row
		 * @param column
		 * @return
		 */
		@Override
		public Object getValueAt(int row, int column) {
			switch (column) {
			case 0:
				return data.get(row).getName();
			case 1:
				return data.get(row).getCode();
			default:
				return super.getValueAt(row, column);
			}
		}
	}
	
	/**
	 * 
	 * 获取空运配载部门
	 * @author 025000-FOSS-helong
	 * @date 2013-1-22 上午08:09:38
	 * @return
	 */
	public OrgAdministrativeInfoEntity getAirDepartmentEntity() {
		return airDepartmentEntity;
	}

	/**
	 * 
	 * 空运配载部门
	 * @author 025000-FOSS-helong
	 * @date 2013-1-22 上午08:09:42
	 * @param airDepartmentEntity
	 */
	public void setAirDepartmentEntity(OrgAdministrativeInfoEntity airDepartmentEntity) {
		this.airDepartmentEntity = airDepartmentEntity;
	}

}