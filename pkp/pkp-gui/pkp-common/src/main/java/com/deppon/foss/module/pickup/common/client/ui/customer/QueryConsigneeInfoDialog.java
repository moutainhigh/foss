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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/ui/customer/QueryConsigneeDialog.java
 * 
 * FILE NAME        	: QueryConsigneeDialog.java
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

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXTable;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.core.context.ApplicationContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerContactDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;
import com.deppon.foss.module.pickup.common.client.ui.commonUI.TableFactory;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.vo.QueryMemberDialogVo;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * 定义“选择收货客户”界面
 * 
 * @author 026123-foss-lifengteng
 * @date 2012-10-22 上午8:57:29
 */
public class QueryConsigneeInfoDialog extends JDialog {
	
//	private static final int FOUR = 4;
	
	private static final int THREE = 3;
	
	/**
	 * 国际化
	 */
	private II18n i18n = I18nManager.getI18n(QueryConsigneeInfoDialog.class);
	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = 3263729854076836821L;

	/**
	 *  创建表格
	 */
	public JXTable tblConsignee;

	/**
	 *  接收客户信息
	 */
	QueryMemberDialogVo customerVo;
	
	/**
	 * 联系人列表
	 */
	List<CustomerContactDto> contactList;

	/**
	 * 构造方法：创建一个新的实例QueryConsigneeDialog
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-22 上午8:57:46
	 */
	public QueryConsigneeInfoDialog(List<CustomerQueryConditionDto> contactList) {
		// 传入运单开单界面对象
		super(ApplicationContext.getApplication().getWorkbench().getFrame());
		initTable();
		init();
		setTableData(contactList);
		//Enter键监控
		EnterKeyForQueryInfoMobile enterTable=new EnterKeyForQueryInfoMobile(this);
		tblConsignee.addKeyListener(enterTable);
	}

	/**
	 * 初始化表格
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-13 上午9:35:05
	 */
	public void initTable() {
		// 创建数据表
		tblConsignee = new QueryTable(new CustomerDetailTableModel());
		// 设置滚动条
		tblConsignee.setAutoscrolls(true);
		// 设置表格排序
		tblConsignee.setSortable(false);
		// 设置表格编辑状态
		tblConsignee.setEditable(false);
		// 设置
		tblConsignee.setColumnControlVisible(true);
		// 设置单行选择模式
		tblConsignee.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// 设置手动调整滚动条
		tblConsignee.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		// 设置水平滚动
		tblConsignee.setHorizontalScrollEnabled(true);
		// 设置
		tblConsignee.setColumnControlVisible(true);
		// 创建序列
		TableFactory.createRowColumn(tblConsignee);
		// 监听处理事件
		tblConsignee.addMouseListener(new ClickTableHandler());
	}

	/**
	 * 设置表格数据
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-13 上午10:14:33
	 */
	public void setTableData(List<CustomerQueryConditionDto> contactList ) {
		if (contactList.isEmpty()) {
			return;
		}
		if(tblConsignee != null){
			//获得表格模型
			CustomerDetailTableModel tableModel = (CustomerDetailTableModel) tblConsignee.getModel();
			tableModel.setData(CommonUtils.convertToMemberVo(contactList, WaybillConstants.YES));
			// 刷新表格数据
			tableModel.fireTableDataChanged();
			//默认选中查询结果的第一行
			if(tblConsignee.getRowCount()>0){
				tblConsignee.requestFocus();
				tblConsignee.setRowSelectionAllowed(true);
				tblConsignee.setRowSelectionInterval(0,0);
			}
		}
	}

	/**
	 * 定义一个一般内部类：设置dialog监听esc按键的处理事件
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-25 上午10:21:54
	 */
	private class EscHandler implements ActionListener {

		/** 
		 * 关闭对话框
		 * @author 026123-foss-lifengteng
		 * @date 2012-12-25 上午10:35:21
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}

	/**
	 * 将选择的客户信息包装到Bean中并传到开单界面上去
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-22 下午3:30:33
	 */
	public void obtainCustomer() {
		// 获得选中行
		int row = tblConsignee.getSelectedRow();
		if (row < 0) {
			MsgBox.showError(i18n.get("foss.gui.common.queryConsigneeDialog.msgBox.nullSelectedRow"));
		} else {
			
			customerVo = new QueryMemberDialogVo();
			CustomerDetailTableModel model = (CustomerDetailTableModel) tblConsignee.getModel();
			List<QueryMemberDialogVo> data = model.getData();
			customerVo = data.get(row);
			// 关闭界面，释放资源
			dispose();
		}
	}
	
	/**
	 * table表格Enter键监控
	 * @author WangQianJin
	 * @date 2013-5-20 下午4:30:46
	 */
	public void tableEnter(){
		obtainCustomer();
	}

	/**
	 * 定义一般内部类：处理“确定”按钮事件
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-22 下午2:46:58
	 */
	private class ConfirmHandler implements ActionListener {

		/** 
		 * 获取客户信息
		 * @author 026123-foss-lifengteng
		 * @date 2012-12-25 上午10:35:43
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			obtainCustomer();
		}
	}

	/**
	 * 定义一般内部类：处理“取消”按钮事件
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-22 下午2:47:51
	 */
	private class CancelHandler implements ActionListener {

		/** 
		 * 关闭对话框
		 * @author 026123-foss-lifengteng
		 * @date 2012-12-25 上午10:35:52
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}

	/**
	 * 一般内部类：表格的单、双击处理事件
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-23 上午10:16:42
	 */
	private class ClickTableHandler extends MouseAdapter {

		/** 
		 * 鼠标双击事件
		 * @author 026123-foss-lifengteng
		 * @date 2012-12-25 上午10:36:01
		 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseClicked(MouseEvent e) {
			// 双击
			if (e.getClickCount() == 2) {
				obtainCustomer();
			}
		}
	}

	/**
	 * 定义一般内部类，便于代码的维护与复杂功能的扩展
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-23 上午10:54:43
	 */
	private static class QueryTable extends JXTable {
		/**
		 * 序列化标识
		 */
		private static final long serialVersionUID = -197002875015293867L;

		/**
		 * 构造一个表格
		 * @author 026123-foss-lifengteng
		 * @date 2012-12-25 上午10:36:34
		 */
		public  QueryTable(DefaultTableModel tblModel) {
			super.setModel(tblModel);
		}

		/**
		 * 设置表格为不可编辑
		 * 
		 * @author 026123-foss-lifengteng
		 * @date 2012-10-22 上午11:09:44
		 * @see org.jdesktop.swingx.JXTable#isCellEditable(int, int)
		 */
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}

		/**
		 * 设置表格中CheckBox
		 * 
		 * @author 026123-foss-lifengteng
		 * @date 2012-10-22 上午11:10:08
		 * @see javax.swing.JTable#getColumnClass(int)
		 */
		@Override
		public Class<?> getColumnClass(int column) {
			return getValueAt(0, column).getClass();
		}

	}
	
	/**
	 * 扩展表格数据模型
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-30 下午3:26:20
	 */
	private class CustomerDetailTableModel extends DefaultTableModel{
		private static final long serialVersionUID = -4861545747385664431L;

		private List<QueryMemberDialogVo> contactList;
		
		/**
		 * 获得数据集合
		 * @author 026123-foss-lifengteng
		 * @date 2012-11-26 下午7:49:32
		 */
		public List<QueryMemberDialogVo> getData(){
			return contactList;
		}
		
		/**
		 * 填充数据集合
		 * @author 026123-foss-lifengteng
		 * @date 2012-11-26 下午7:49:14
		 */
		public void setData(List<QueryMemberDialogVo> contactList) {
			this.contactList = contactList;
		}
		
		/** 
		 * 获得行数
		 * @author 026123-foss-lifengteng
		 * @date 2012-11-26 下午7:47:00
		 * @see javax.swing.table.DefaultTableModel#getRowCount()
		 */
		@Override
		public int getRowCount() {
			return contactList == null ? 0 : contactList.size();
		}
		
		/** 
		 * 重写方法：获得表格行数
		 * @author 026123-foss-lifengteng
		 * @date 2012-11-26 下午7:46:34
		 * @see javax.swing.table.DefaultTableModel#getColumnCount()
		 */
		@Override
		public int getColumnCount() {
			return THREE;
		}

		/** 
		 * 重写方法：获得列名
		 * @author 026123-foss-lifengteng
		 * @date 2012-11-26 下午7:46:18
		 * @see javax.swing.table.DefaultTableModel#getColumnName(int)
		 */
		@Override
		public String getColumnName(int column) {
			switch (column) {
			case 0:
				return i18n.get("foss.gui.common.queryConsigneeDialog.columnName.phone");
			case 1:
				return i18n.get("foss.gui.common.queryConsigneeDialog.columnName.tel");
			case 2:
				return i18n.get("foss.gui.common.queryConsigneeDialog.columnName.address");
			default:
				return "";
			}
		}
		
		/** 
		 * 重写model方法，获得行数据对象
		 * @author 026123-foss-lifengteng
		 * @date 2012-11-26 下午7:45:44
		 * @see javax.swing.table.DefaultTableModel#getValueAt(int, int)
		 */
		@Override
		public Object getValueAt(int row, int column) {
			switch (column) {
			case 0:
				return contactList.get(row).getMobilePhone();
			case 1:
				return contactList.get(row).getPhone();
			case 2:
				return contactList.get(row).getAddressInfo();
			default:
				return super.getValueAt(row, column);
			}
		}
	}

	/**
	 * 初始化界面控件
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-22 上午8:59:04
	 */
	private void init() {
		JPanel panel = new JPanel();
		panel.setLayout(new FormLayout(new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("50dlu:grow"),
				FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("50dlu:grow"), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("50dlu:grow"),
				FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("50dlu:grow"), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("50dlu:grow"),
				FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("1dlu"), }, new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("fill:77dlu:grow"), FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, }));

		//选择收货客户
		JLabel lblConsignee = new JLabel(i18n.get("foss.gui.common.queryConsigneeDialog.selectConsignee.label"));
		lblConsignee.setFont(new Font("宋体", Font.BOLD, NumberConstants.NUMBER_12));
		panel.add(lblConsignee, "2, 2, 5, 1, left, default");

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(tblConsignee);
		panel.add(scrollPane, "2, 4, 11, 1, fill, fill");

		//确认按钮
		JButton btnConfirm = new JButton(i18n.get("foss.gui.common.waybillEditUI.common.confirm"));
		btnConfirm.addActionListener(new ConfirmHandler());
		panel.add(btnConfirm, "6, 6, left, default");

		//取消按钮
		JButton btnCancel = new JButton(i18n.get("foss.gui.common.waybillEditUI.common.cancel"));
		btnCancel.addActionListener(new CancelHandler());
		// Dialog监听ESC事件
		panel.registerKeyboardAction(new EscHandler(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		panel.add(btnCancel, "10, 6, left, default");
		// 设置模态
		setModal(true);
		// 将panel加入到容器中
		getContentPane().add(panel);
		// 自动撑展弹出框
		pack();

	}

	
	/**
	 * @return the customerVo .
	 */
	public QueryMemberDialogVo getCustomerVo() {
		return customerVo;
	}

	
	/**
	 *@param customerVo the customerVo to set.
	 */
	public void setCustomerVo(QueryMemberDialogVo customerVo) {
		this.customerVo = customerVo;
	}

	

}