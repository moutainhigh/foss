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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/ui/dialog/HandoverDetailDialog.java
 * 
 * FILE NAME        	: HandoverDetailDialog.java
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
package com.deppon.foss.module.pickup.changingexp.client.ui.dialog;

import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXTable;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.core.context.ApplicationContext;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.pickup.changingexp.client.service.WaybillRfcServiceFactory;
import com.deppon.foss.module.pickup.changingexp.client.vo.WaybillInfoVo;
import com.deppon.foss.module.pickup.common.client.service.ISalesDepartmentService;
import com.deppon.foss.module.transfer.unload.api.shared.dto.HandOverAndUnloadDto;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * 
 * 交接明细对话框
 * @author 102246-foss-shaohongliang
 * @date 2012-11-9 下午4:02:13
 */
public class HandoverDetailDialog extends JDialog {
	/**
	 * 国际化对象
	 */
	private static final II18n i18n = I18nManager.getI18n(HandoverDetailDialog.class); 

	/**
	 * DEFAULT CSS STYLE
	 */
	private static final String DEFAULTGROW = "default:grow";

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -7849212672344489263L;
	
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

	private static final int NUM_280 = 280;

	private static final int NUM_580 = 580;
	
	/**
	 * 交接明细表格
	 */
	private JXTable table;

	/**
	 * 货物名称
	 */
	private JLabel lblGoodsName;

	/**
	 * 货物数量
	 */
	private JLabel lblGoodsCount;

	/**
	 * 出发部门名称
	 */
	private JLabel lblLeaveDeptName;

	/**
	 * 到达部门名称
	 */
	private JLabel lblArriveDeptName;

	/**
	 * 库存部门名称
	 */
	private JLabel lblInventoryDeptName;

	/**
	 * 运单号
	 */
	private JLabel lblWaybillNo;
	
	/**
	 * 查询营业部Service
	 */
	private ISalesDepartmentService salesDepartmentService=WaybillRfcServiceFactory.getSalesDepartmentService();	
	

	/**
	 * 
	 * 实例化交接单对话框
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午10:00:35
	 */
	public HandoverDetailDialog(WaybillInfoVo waybillInfoVo, List<HandOverAndUnloadDto> handoverDetailDto) {
		super(ApplicationContext.getApplication().getWorkbench().getFrame());
		init();
		setData(waybillInfoVo, handoverDetailDto);
	}

	/**
	 * 
	 * 填充页面数据
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午10:00:58
	 */
	private void setData(WaybillInfoVo waybillInfoVo, List<HandOverAndUnloadDto> handoverDetailDto) {
		//货物名称
		lblGoodsName.setText(waybillInfoVo.getGoodsName());
		
		//货物数量
		lblGoodsCount.setText(String.valueOf(waybillInfoVo.getGoodsQtyTotal()));

		//出发部门名称
		lblLeaveDeptName.setText(waybillInfoVo.getReceiveOrgName());

		//到达部门名称
		lblArriveDeptName.setText(waybillInfoVo.getTargetOrgCode());

		//库存部门
		if(waybillInfoVo.getStockStatus() == null){
			lblInventoryDeptName.setText(null);
		}else{
			lblInventoryDeptName.setText(null);
			if(waybillInfoVo.getStockStatus().getCurrentStockOrgCode()!=null && !"".equals(waybillInfoVo.getStockStatus().getCurrentStockOrgCode())){
				SaleDepartmentEntity department=salesDepartmentService.querySaleDepartmentByCode(waybillInfoVo.getStockStatus().getCurrentStockOrgCode());
				if(department!=null){
					lblInventoryDeptName.setText(department.getName());
				}
			}
		}
		//运单号
		lblWaybillNo.setText(waybillInfoVo.getImportWaybillNo());
		
		HandoverDetailTableModel tableModel = (HandoverDetailTableModel)table.getModel();
		tableModel.setData(handoverDetailDto);
		tableModel.fireTableDataChanged();
	}

	
	/**
	 * 
	 * 初始化布局
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午9:18:23
	 */
	private void init() {
		setTitle(i18n.get("foss.gui.changingexp.handoverDetailDialog.init.handoverDetail.label"));
		setModal(true);
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode(DEFAULTGROW),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode(DEFAULTGROW),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode(DEFAULTGROW),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode(DEFAULTGROW),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode(DEFAULTGROW),
				FormFactory.RELATED_GAP_ROWSPEC,}));
		
		JLabel label = new JLabel(i18n.get("foss.gui.changingexp.handoverDetailDialog.init.waybillNo.label"));
		getContentPane().add(label, "2, 2, right, default");
		
		lblWaybillNo = new JLabel("New label");
		getContentPane().add(lblWaybillNo, "4, 2");
		
		JLabel label1 = new JLabel(i18n.get("foss.gui.changingexp.handoverDetailDialog.init.goodsName.label"));
		getContentPane().add(label1, "6, 2, right, default");
		
		lblGoodsName = new JLabel("New label");
		getContentPane().add(lblGoodsName, "8, 2");
		
		JLabel label2 = new JLabel(i18n.get("foss.gui.changingexp.handoverDetailDialog.init.goodsCount.label"));
		getContentPane().add(label2, "10, 2, right, default");
		
		lblGoodsCount = new JLabel("New label");
		getContentPane().add(lblGoodsCount, "12, 2");
		
		JLabel label3 = new JLabel(i18n.get("foss.gui.changingexp.handoverDetailDialog.init.departureDept.label"));
		getContentPane().add(label3, "2, 4, right, default");
		
		lblLeaveDeptName = new JLabel("New label");
		getContentPane().add(lblLeaveDeptName, "4, 4");
		
		JLabel label4 = new JLabel(i18n.get("foss.gui.changingexp.handoverDetailDialog.init.arriveDept.label"));
		getContentPane().add(label4, "6, 4, right, default");
		
		lblArriveDeptName = new JLabel("New label");
		getContentPane().add(lblArriveDeptName, "8, 4");
		
		JLabel label5 = new JLabel(i18n.get("foss.gui.changingexp.handoverDetailDialog.init.stockDept.label"));
		getContentPane().add(label5, "10, 4, right, default");
		
		lblInventoryDeptName = new JLabel("New label");
		getContentPane().add(lblInventoryDeptName, "12, 4");
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, "2, 6, 11, 1, fill, fill");
		
		table = new JXTable();
		table.setModel(new HandoverDetailTableModel());
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
		
		setSize(NUM_580, NUM_280);
	}

	/**
	 * 
	 * 交接单数据模型
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午10:02:05
	 */
	private class HandoverDetailTableModel extends DefaultTableModel {

		/**
		 * 序列化版本号
		 */
		private static final long serialVersionUID = 5883365603131625962L;

		/**
		 * 交接单明细
		 */
		private List<HandOverAndUnloadDto> data;

		/**
		 * 
		 * 设置数据
		 * @author 102246-foss-shaohongliang
		 * @date 2012-12-24 下午10:02:21
		 */
		public void setData(List<HandOverAndUnloadDto> data) {
			this.data = data;
		}

		@Override
		public String getColumnName(int column) {
			switch (column) {
			case ZERO:
				return i18n.get("foss.gui.changingexp.handoverDetailDialog.columnName.handoverBillNo");
			case ONE:
				return i18n.get("foss.gui.changingexp.handoverDetailDialog.columnName.handoverDept");
			case TWO:
				return i18n.get("foss.gui.changingexp.handoverDetailDialog.columnName.handoverCount");
			case THREE:
				return i18n.get("foss.gui.changingexp.handoverDetailDialog.columnName.unloadCount");
			case FOUR:
				return i18n.get("foss.gui.changingexp.handoverDetailDialog.columnName.lackGoodsCount");
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
				return data.get(row).getHandOverNo();
			case ONE:
				return data.get(row).getOrigOrgName();
			case TWO:
				return data.get(row).getHandOverQty();
			case THREE:
				return data.get(row).getUnloadQty();
			case FOUR:
				return data.get(row).getHandOverQty() - data.get(row).getUnloadQty();
			default:
				return "";
			}
		}
	}
}