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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changing/client/ui/dialog/MultiUserChooserDialog.java
 * 
 * FILE NAME        	: MultiUserChooserDialog.java
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
package com.deppon.foss.module.pickup.changing.client.ui.dialog;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXTable;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.annotation.ButtonAction;
import com.deppon.foss.framework.client.component.buttonaction.factory.ButtonActionFactory;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.pickup.changing.client.action.MultiUserChooserOKAction;
import com.deppon.foss.module.pickup.common.client.action.DialogCloseAction;
import com.deppon.foss.module.pickup.common.client.ui.commonUI.TableFactory;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * 
 * 人员选择弹出对话框
 * @author 102246-foss-shaohongliang
 * @date 2012-12-27 上午11:04:10
 */
public class MultiUserChooserDialog extends JDialog {
	
	/**
	 * 国际化对象
	 */
	private static final II18n i18n = I18nManager.getI18n(MultiUserChooserDialog.class); 
	
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -4854970283509059864L;
	
	private static final int NUM_480 = 480;
	
	private JScrollPane scrollPane;
	
	private JXTable table;

	@ButtonAction(icon="", shortcutKey="", type = MultiUserChooserOKAction.class)
	private JButton btnOk;
	
	@ButtonAction(icon="", shortcutKey="", type = DialogCloseAction.class)
	private JButton btnClose;
	
	private List<EmployeeEntity> employeeEntities;
	

	
	/**
	 * 人员TableModel
	 */
	private MultiChooserDecoratorTableModel tableModel;
	
	/**
	 * 人员选择器控件
	 */
	private MultiUserChooser multiUserChooser;
	
	public MultiUserChooserDialog(List<EmployeeEntity> employeeEntities,
			MultiUserChooser multiUserChooser) {
//		super(multiUserChooser.getTopLevelAncestor() instanceof JDialog?
//				(JDialog)multiUserChooser.getTopLevelAncestor():
//				(JFrame)multiUserChooser.getTopLevelAncestor());
		this.employeeEntities = employeeEntities;
		this.multiUserChooser = multiUserChooser;
		init();
		bind();
		fillTableData();
	}

	/**
	 * 
	 * 数据 按钮绑定
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-27 上午11:36:08
	 */
	private void bind() {
		ButtonActionFactory.getIntsance().bindButtonAction(this);
	}

	/**
	 * 
	 * 填充表格数据
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-27 上午11:15:36
	 */
	private void fillTableData() {
		tableModel.setData(employeeEntities);
		tableModel.fireTableDataChanged();
	}

	private void init() {
		//人员选择
		setTitle(i18n.get("foss.gui.changing.multiUserChooserDialog.title"));
		setModal(true);
		setSize(NumberConstants.NUMBER_700, NUM_480);

		FormLayout formLayout = new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.BUTTON_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.BUTTON_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,});
		getContentPane().setLayout(formLayout);
		
		scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, "2, 2, 11, 1, fill, fill");
		
		table = new JXTable();
		table.setAutoscrolls(true);
		table.setSortable(false);
		table.setColumnControlVisible(true);
		table.setEditable(false);
		tableModel = new MultiChooserDecoratorTableModel();
		table.setModel(tableModel);
		
		scrollPane.setViewportView(table);
		TableFactory.createRowColumn(table);
		
		btnOk = new JButton(i18n.get("foss.gui.changing.waybillRFCUI.common.confirm"));
		getContentPane().add(btnOk, "10, 4");
		
		btnClose = new JButton(i18n.get("foss.gui.changing.waybillRFCUI.common.cancel"));
		getContentPane().add(btnClose, "12, 4");
	}
	
	 public void setSelectionMode(int selectionMode) {
		 table.setSelectionMode(selectionMode);
	 }
	
	/**
	 * 
	 * 获取选中的人员
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-27 上午11:34:16
	 */
	public List<EmployeeEntity> getSelectedEmployees(){
		List<EmployeeEntity> employeeEntities = new ArrayList<EmployeeEntity>();
		int[] rows = table.getSelectedRows();
		for(int row : rows){
			employeeEntities.add(this.employeeEntities.get(row));
		}
		return employeeEntities;
	}
	
	/**
	 * 
	 * 人员数据模型
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-27 上午11:14:13
	 */
	class MultiChooserDecoratorTableModel extends DefaultTableModel{

		/**
		 * 5
		 */
		private static final int FIVE = 5;

		/**
		 * 4
		 */
		private static final int FOUR = 4;

		/**
		 * 3
		 */
		private static final int THREE = 3;

		/**
		 * 2
		 */
		private static final int TWO = 2;

		/**
		 * 0
		 */
		private static final int ZERO = 0;

		/**
		 * 1
		 */
		private static final int ONE = 1;

		/**
		 * 序列化版本号
		 */
		private static final long serialVersionUID = 5883365603131625962L;

		/**
		 * 员工集合
		 */
		private List<EmployeeEntity> data;

		/**
		 * 
		 * 设置数据
		 * @author 102246-foss-shaohongliang
		 * @date 2012-12-24 下午10:02:21
		 */
		public void setData(List<EmployeeEntity> data) {
			this.data = data;
		}
		
//		@Override
//		public Class<?> getColumnClass(int columnIndex) {
//			if(columnIndex == 0){
//				return Boolean.class;
//			}
//			return super.getColumnClass(columnIndex);
//		}

		@Override
		public String getColumnName(int column) {
			switch (column) {
			case ZERO:
				return i18n.get("foss.gui.changing.multiUserChooserDialog.columnName.workNum");
			case ONE:
				return i18n.get("foss.gui.changing.multiUserChooserDialog.columnName.name");
			case TWO:
				return i18n.get("foss.gui.changing.multiUserChooserDialog.columnName.gender");
			case THREE:
				return i18n.get("foss.gui.changing.multiUserChooserDialog.columnName.belongDept");
			case FOUR:
				return i18n.get("foss.gui.changing.multiUserChooserDialog.columnName.position");
			default:
				return "";
			}
		}

		@Override
		public int getColumnCount() {
			return FIVE;
		}

		@Override
		public int getRowCount() {
			return data == null ? ZERO : data.size();
		}

		@Override
		public Object getValueAt(int row, int column) {
			switch (column) {
			case ZERO:
				//工号
				return data.get(row).getEmpCode();
			case ONE:
				//姓名
				return data.get(row).getEmpName();
			case TWO:
				//性别
				String gender = data.get(row).getGender();
				return "1".equals(gender)?i18n.get("foss.gui.changing.multiUserChooserDialog.gender.male"):i18n.get("foss.gui.changing.multiUserChooserDialog.gender.female");
			case THREE:
				//所属部门
				OrgAdministrativeInfoEntity department = data.get(row).getDepartment();
				if(department == null){
					return null;
				}else{
					return department.getName();
				}
			case FOUR:
				//职位
				return data.get(row).getTitle();
			default:
				return "";
			}
		}
	}

	
	/**
	 * @return the multiUserChooser
	 */
	public MultiUserChooser getMultiUserChooser() {
		return multiUserChooser;
	}
}