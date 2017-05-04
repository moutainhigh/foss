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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/ui/customer/QueryConsignerDialog.java
 * 
 * FILE NAME        	: QueryConsignerDialog.java
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
import java.util.ArrayList;
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

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.core.context.ApplicationContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerContactDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;
import com.deppon.foss.module.pickup.common.client.ui.commonUI.TableFactory;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.vo.QueryMemberDialogVo;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * 定义“选择发货客户”界面
 * 
 * @author dp-lifengteng
 * @date 2012-10-15 上午10:46:18
 */
public class QueryConsignerDialog extends JDialog {
	
	private static final int THREE = 3;

	private static final int FOUR = 4;

	private static final int FIVE = 5;

	private static final int SIX = 6;

	private static final int SEVEN = 7;

	private static final int EIGHT = 8;

	private static final int NINE = 9;
	
//	private static final int TEN = 10;
	
//	private static final int ELEVEN = 11;
	
	private static final int TWELVE = 12;
	
	/**
	 * 国际化
	 */
	private II18n i18n = I18nManager.getI18n(QueryConsignerDialog.class);
	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = 3263729854076836821L;

	// 创建表格
	private JXTable tblConsigner;

	// 封装数据的VO类
	private QueryMemberDialogVo customerVo;

	//联系人列表
	List<CustomerContactDto> contactList ;

	/**
	 * 构造方法传入查询条件
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-15 下午8:31:29
	 */
	public QueryConsignerDialog(List<CustomerQueryConditionDto> contactList ) {
		// 传入运单开单界面对象
		super(ApplicationContext.getApplication().getWorkbench().getFrame());
		initTable();
		init();
		setTableData(contactList);
		//Enter键监控
		EnterKeyForQueryMobile enterTable=new EnterKeyForQueryMobile(this);
		tblConsigner.addKeyListener(enterTable);
	}

	/**
	 * 初始化表格
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-13 上午9:35:05
	 */
	public void initTable() {
		//创建数据表
		tblConsigner = new QueryTable(new CustomerDetailTableModel());
		//设置滚动条
		tblConsigner.setAutoscrolls(true);
		//设置表格排序
		tblConsigner.setSortable(false);
		//设置
		tblConsigner.setColumnControlVisible(true);
		// 设置单行选择模式
		tblConsigner.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// 设置手动调整滚动条
		tblConsigner.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		// 设置水平滚动
		tblConsigner.setHorizontalScrollEnabled(true);
		// 设置
		tblConsigner.setColumnControlVisible(true);
		
//		// 让所有列自动调整
//		tblConsigner.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
//		// 充满表格
//		tblConsigner.packAll();
		//创建序列
		TableFactory.createRowColumn(tblConsigner);
		// 监听处理事件
		tblConsigner.addMouseListener(new ClickTableHandler());
	}
	
	/**
	 * 扩展表格数据模型
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-26 下午7:42:45
	 */
	private class CustomerDetailTableModel extends DefaultTableModel{
		private static final long serialVersionUID = 9071126508541610144L;
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
			return SEVEN;
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
				return i18n.get("foss.gui.common.queryConsignerDialog.columnName.actionColumn");
			case 1:
				return i18n.get("foss.gui.common.queryConsignerDialog.columnName.customerCode");
			case 2:
				return i18n.get("foss.gui.common.queryConsignerDialog.columnName.customerName");
			case THREE:
				return i18n.get("foss.gui.common.queryConsignerDialog.columnName.deptName");
			case FOUR:
				return i18n.get("foss.gui.common.queryConsignerDialog.columnName.contact");
			case FIVE:
				return i18n.get("foss.gui.common.queryConsignerDialog.columnName.phone");
			default:
				return getColumnNameExp(column) ;
			}
		}
		
		private String getColumnNameExp(int column){
			switch (column) {
			case SIX:
				return i18n.get("foss.gui.common.queryConsignerDialog.columnName.tel");
			case SEVEN:
				return i18n.get("foss.gui.common.queryConsignerDialog.columnName.address");
			case EIGHT:
				return i18n.get("foss.gui.common.queryConsignerDialog.columnName.addressNote");
			case NINE:
				return i18n.get("foss.gui.common.queryConsignerDialog.columnName.flabelleavemonth");
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
				return contactList.get(row).getSelected();
			case 1:
				return contactList.get(row).getCustomerCode();
			case 2:
				return contactList.get(row).getCustomerName();
			case THREE:
				return contactList.get(row).getDeptName();
			case FOUR:
				return contactList.get(row).getLinkman();
			case FIVE:
				return contactList.get(row).getMobilePhone();
			default:
				return getValueAtExp(row,column);
			}
		}
		private Object getValueAtExp(int row, int column) {
			switch (column) {
			case SIX:
				return contactList.get(row).getPhone();
			case SEVEN:
				return contactList.get(row).getAddress();
			case EIGHT:
				return contactList.get(row).getCustAddrRemark();
			case NINE:
				return contactList.get(row).getFlabelleavemonth();
			default:
				return super.getValueAt(row, column);
			}
		}
	}
	
	
	/**
	 * 设置表格数据
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-13 上午10:14:33
	 */
	public void setTableData(List<CustomerQueryConditionDto> contactList ) {
		if (contactList == null || contactList.size() == 0) {
			return;
		}
		if(tblConsigner != null){
			//获得表格模型
			CustomerDetailTableModel tableModel = (CustomerDetailTableModel) tblConsigner.getModel();
			tableModel.setData(CommonUtils.convertToMemberVo(contactList));
			// 刷新表格数据
			tableModel.fireTableDataChanged();
			//默认选中查询结果的第一行
			if(tblConsigner.getRowCount()>0){
				tblConsigner.requestFocus();
				tblConsigner.setRowSelectionAllowed(true);
				tblConsigner.setRowSelectionInterval(0,0);
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
		 * @date 2012-12-25 上午10:39:21
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}

	/**
	 * 获取客户信息
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-23 上午10:07:30
	 */
	public void obtainCustomer() {
		// 获得选中行
		int row = tblConsigner.getSelectedRow();
		if (row < 0) {
			MsgBox.showError(i18n.get("foss.gui.common.queryConsigneeDialog.msgBox.nullSelectedRow"));
		} else {
			
			customerVo = new QueryMemberDialogVo();
			CustomerDetailTableModel model = (CustomerDetailTableModel) tblConsigner.getModel();
			List<QueryMemberDialogVo> data = model.getData();
			customerVo = data.get(row);
			// 关闭界面，释放资源
			dispose();
		}
	}
	
	/**
	 * table表格Enter键监控
	 * @author WangQianJin
	 * @date 2013-5-20 下午4:25:40
	 */
	public void tableEnter(){
		obtainCustomer();
	}

	/**
	 * 一般内部类：按钮“取消”的处理事件
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-23 上午10:06:51
	 */
	private class CancelHandler implements ActionListener {
		/** 
		 * 关闭对话框
		 * @author 026123-foss-lifengteng
		 * @date 2012-12-25 上午10:39:35
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}

	/**
	 * 一般内部类：按钮“确定”的处理事件
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-23 上午10:06:54
	 */
	private class ConfirmHandler implements ActionListener {
		/** 
		 * 获取客户信息
		 * @author 026123-foss-lifengteng
		 * @date 2012-12-25 上午10:39:52
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			obtainCustomer();
		}
	}

	/**
	 * 一般内部类：表格的单、双击处理事件
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-23 上午10:16:42
	 */
	private class ClickTableHandler extends MouseAdapter {
		/** 
		 * 鼠标双击与单击事件
		 * @author 026123-foss-lifengteng
		 * @date 2012-12-25 上午10:40:06
		 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseClicked(MouseEvent e) {
			// 双击
			if (e.getClickCount() == 2) {
				obtainCustomer();
				// 单击:设置选中行的checkBox为选中状态，其它行为非选中状态
			} else if (e.getClickCount() == 1) {
				//总行数
				int rowCount = tblConsigner.getRowCount();
				//选中行
				int row = tblConsigner.getSelectedRow();
				
				if(rowCount<=0 || row <0 || row > rowCount ){
					return ;
				}
				setRowSelected(row);
			}
		}
		
		/**
		 * 设置表格选择行为checkBox选中状态
		 * @author 026123-foss-lifengteng
		 * @date 2013-2-22 下午4:25:53
		 */
		public void setRowSelected(int row){
			//表格数据模型
			CustomerDetailTableModel tableModel = (CustomerDetailTableModel) tblConsigner.getModel();
			//获得表格数据集合
			List<QueryMemberDialogVo> dataList = tableModel.getData();
			//定义新的数据集合
			List<QueryMemberDialogVo> newList = new ArrayList<QueryMemberDialogVo>();
			//获得选中行对象
			QueryMemberDialogVo selectedVo = dataList.get(row);
			//遍历集合
			for (QueryMemberDialogVo vo : dataList) {
				if(vo.equals(selectedVo)){
					//设置为选中
					vo.setSelected(true);
				}else{
					//设置为不选中
					vo.setSelected(false);
				}
				newList.add(vo);
			}
			
			//重新设置数据
			tableModel.setData(newList);
			// 刷新表格数据
			tableModel.fireTableDataChanged();
			//设置选中行
			tblConsigner.setRowSelectionInterval(row, row);
		}
	}
	
	/**
	 * 定义一般内部类，便于代码的维护与复杂功能的扩展
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-23 上午9:51:19
	 */
	public static class QueryTable extends JXTable {

		/**
		 * 序列化标识
		 */
		private static final long serialVersionUID = -7600440331210122023L;

		/**
		 * 构造方法，创建一个实例
		 * 
		 * @author 026123-foss-lifengteng
		 * @date 2012-10-23 上午9:54:39
		 */
		public QueryTable(DefaultTableModel tblModel) {
			super.setModel(tblModel);
		}

		/**
		 * 设置表格为不可编辑
		 * 
		 * @author 026123-foss-lifengteng
		 * @date 2012-10-23 上午9:49:39
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
		 * @date 2012-10-23 上午9:50:52
		 * @see javax.swing.JTable#getColumnClass(int)
		 */
		@Override
		public Class<?> getColumnClass(int column) {
	        Object value = getValueAt(0, column);
	        if (value != null) {
	            return value.getClass();
	        }
	        return super.getClass();
		}
	}

	/**
	 * 
	 * 初始化界面控件
	 * 
	 * @author dp-lifengteng
	 * @date 2012-10-15 上午10:49:13
	 */
	private void init() {
		JPanel panel = new JPanel();
		panel.setLayout(new FormLayout(new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("50dlu:grow"),
				FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("50dlu:grow"), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("50dlu:grow"),
				FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("50dlu:grow"), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("50dlu:grow"),
				FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("50dlu:grow"), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("50dlu:grow"),
				FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("1dlu"), }, new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("fill:77dlu:grow"), FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, }));

		//选择发货客户
		JLabel lblCustomer = new JLabel(i18n.get("foss.gui.common.queryConsignerDialog.selectDeliveryCustomer.label"));
		lblCustomer.setFont(new Font("宋体", Font.BOLD, TWELVE));
		panel.add(lblCustomer, "2, 2, 5, 1, left, default");

		//滚动面板
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, "2, 4, 13, 1, fill, fill");

		//确认按钮
		scrollPane.add(tblConsigner);
		scrollPane.setViewportView(tblConsigner);
		JButton btnConfirm = new JButton(i18n.get("foss.gui.common.queryConsignerDialog.confirm.label"));
		// 增加监听事件
		btnConfirm.addActionListener(new ConfirmHandler());
		panel.add(btnConfirm, "6, 6, left, default");

		//取消按钮
		JButton btnCancel = new JButton(i18n.get("foss.gui.common.waybillEditUI.common.cancel"));
		// 增加监听事件
		btnCancel.addActionListener(new CancelHandler());
		panel.add(btnCancel, "10, 6, left, default");
		// Dialog监听ESC事件
		panel.registerKeyboardAction(new EscHandler(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		add(panel);
		// 模态
		setModal(true);
		// 撑展界面
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