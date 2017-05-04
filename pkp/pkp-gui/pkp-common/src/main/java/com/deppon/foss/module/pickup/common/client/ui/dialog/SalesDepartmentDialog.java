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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/ui/dialog/SalesDepartmentDialog.java
 * 
 * FILE NAME        	: SalesDepartmentDialog.java
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
package com.deppon.foss.module.pickup.common.client.ui.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXTable;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.pickup.common.client.dto.QueryPickupPointDto;
import com.deppon.foss.module.pickup.common.client.service.ISalesDepartmentService;
import com.deppon.foss.module.pickup.common.client.service.VehicleAgencyDeptServiceFactory;
import com.deppon.foss.util.define.FossConstants;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;


/**
 * 部门对话框界面
 * 
 * @author 026123-foss-lifengteng
 * @date 2012-11-13 上午8:27:14
 */
public class SalesDepartmentDialog extends JDialog {
	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = -5960720224792355823L;

	/**
	 * 定义字符OK
	 */
	private static final String OK = "OK";

	/**
	 * 定义静态常量10
	 */
	private static final int TEN = 10;

	/**
	 * 定义静态常量5
	 */
	private static final int FIVE = 5;

	/**
	 * 定义静态常量407
	 */
	private static final int FOURZEROSEVEN = 407;

	/**
	 * 定义静态常量531
	 */
	private static final int FIVETHREEONE = 531;

	/**
	 * 定义静态常量100
	 */
	private static final int HUNDRED = 100;


	/**
	 * 营业部
	 * 提供营业部查询的相关服务接口
	 */
	private ISalesDepartmentService salesDepartmentService = GuiceContextFactroy.getInjector().getInstance(ISalesDepartmentService.class);

	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(SalesDepartmentDialog.class);

	/**
	 * 定义面板
	 */
	private final JPanel contentPanel = new JPanel();

	/**
	 * 营业部名称
	 */
	private JTextField txtSalesDepartmentName;

	/**
	 * 显示数据的表格
	 */
	private JXTable table;

	/**
	 * 营业部实体对象
	 */
	private SaleDepartmentEntity saleDepartmentEntity;

	/**
	 * 组织类型
	 */
	private String netDetType;

	/**
	 * 是否需要根据财务组织查询
	 */
	private boolean needBillGroup = true;
	
	//是否只查詢出做到的營業部
	private boolean isArrived = false;
	
	//确定
	private JButton okButton;
	
	//查询
	private JButton btnNewButton;
	
	//运单号
	private String waybillNo = null ;

	/**
	 * 构造管理营业部出发运单对象
	 */
	public SalesDepartmentDialog() {
		//初始化表格
		initTable();
		//初始化数据
		init();
	}
	
	/**
	 * 构造管理营业部出发运单对象
	 */
	public SalesDepartmentDialog(String waybillNo) {
		this.waybillNo = waybillNo ;
		//初始化表格
		initTable();
		//初始化数据
		init();
	}
	
	/**
	 * 初始化營業部數據
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-3 下午3:47:12
	 */
	public void initSalesDepartmentDialog(boolean needBillGroup, String netDetType){
		//是否需要根据财务组织查询
		this.needBillGroup = needBillGroup;
		//组织类型
		this.netDetType = netDetType;
		//初始化表格
		initTable();
		//初始化数据
		init();
		//监听Enter
		EnterKeyDepartment enterSalesDepartmentName=new EnterKeyDepartment(btnNewButton);		
		txtSalesDepartmentName.addKeyListener(enterSalesDepartmentName);
		EnterKeyDepartment enterTable=new EnterKeyDepartment(this);	
		table.addKeyListener(enterTable);
	}

	/**
	 * 构造管理营业部出发运单对象
	 */
	public SalesDepartmentDialog(boolean needBillGroup, String netDetType) {
		initSalesDepartmentDialog(needBillGroup,netDetType);
	}

	
	/**
	 * 構造构造管理营业部出发运单对象，增加是否只顯示做到達的營業部
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-3 下午3:31:28
	 */
	public SalesDepartmentDialog(boolean needBillGroup, String netDetType,boolean isArrived){
		if(isArrived){
			this.isArrived = isArrived;
		}
		
		initSalesDepartmentDialog(needBillGroup,netDetType);
	}

	private void init() {
		//设置弹出框位置和大小
		setBounds(HUNDRED, HUNDRED, FIVETHREEONE, FOURZEROSEVEN);
		//设置布局
		getContentPane().setLayout(new BorderLayout());
		//内容面板
		contentPanel.setBorder(new EmptyBorder(FIVE, FIVE, FIVE, FIVE));
		//中间对齐
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		//
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC, }, new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("default:grow"), }));
		{
			JLabel lblNewLabel = new JLabel(i18n.get("foss.gui.common.salesDepartmentDialog.departmentName.label"));
			contentPanel.add(lblNewLabel, "2, 2, right, default");
		}
		{
			txtSalesDepartmentName = new JTextField();
			contentPanel.add(txtSalesDepartmentName, "4, 2, 12, 1, fill, default");
			txtSalesDepartmentName.setColumns(TEN);
		}
		{
			btnNewButton = new JButton(i18n.get("foss.gui.common.waybillEditUI.common.query"));
			btnNewButton.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					String name = txtSalesDepartmentName.getText();
					if (name == null || "".equals(name)) {
						MsgBox.showInfo(i18n.get("foss.gui.common.salesDepartmentDialog.msgBox.nullSalesDepartmentName"));
					} else {
						if (needBillGroup) {
							// 获取当前用户
							UserEntity user = (UserEntity) SessionContext.getCurrentUser();
							// 当前用户所在部门
							OrgAdministrativeInfoEntity dept = user.getEmployee().getDepartment();
							setData(salesDepartmentService.querySaleDepartmentByNameForCentralizedExp(name, dept.getCode(),waybillNo));
//							setData(salesDepartmentService.querySaleDepartmentByNameForCentralized(name, dept.getCode()));
						} else {

							if (DictionaryValueConstants.OUTERBRANCH_TYPE_PX.equals(netDetType) || DictionaryValueConstants.OUTERBRANCH_TYPE_KY.equals(netDetType)) {
								QueryPickupPointDto temp = new QueryPickupPointDto();
								temp.setDestNetType(netDetType);
								temp.setActive(FossConstants.ACTIVE);
								temp.setOrgSimpleName(name.trim());
								List<OuterBranchEntity> tableInfoOfOuterBranchParamsDto = VehicleAgencyDeptServiceFactory.getVehicleAgencyDeptService().queryOuterBranchs(temp);
								if (tableInfoOfOuterBranchParamsDto != null && tableInfoOfOuterBranchParamsDto.size() > 0) {
									List<SaleDepartmentEntity> sdList = new ArrayList<SaleDepartmentEntity>();
									for (OuterBranchEntity ob : tableInfoOfOuterBranchParamsDto) {
										SaleDepartmentEntity sd = new SaleDepartmentEntity();
										sd.setCode(ob.getAgentDeptCode());
										sd.setName(ob.getAgentDeptName());
										sdList.add(sd);
									}
									setData(sdList);
								}

							} else if ("all".equals(netDetType)) {
								QueryPickupPointDto temp = new QueryPickupPointDto();
								temp.setDestNetType(DictionaryValueConstants.OUTERBRANCH_TYPE_PX);
								temp.setActive(FossConstants.ACTIVE);
								temp.setPickUpPoint(name.trim());
								List<OuterBranchEntity> tableInfoOfOuterBranchParamsDto = VehicleAgencyDeptServiceFactory.getVehicleAgencyDeptService().queryOuterBranchs(temp);

								if (tableInfoOfOuterBranchParamsDto == null) {
									tableInfoOfOuterBranchParamsDto = new ArrayList<OuterBranchEntity>();
								}

								temp.setDestNetType(DictionaryValueConstants.OUTERBRANCH_TYPE_KY);
								List<OuterBranchEntity> tableInfoOfOuterBranchParamsDto2 = VehicleAgencyDeptServiceFactory.getVehicleAgencyDeptService().queryOuterBranchs(temp);

								if (tableInfoOfOuterBranchParamsDto2 != null && tableInfoOfOuterBranchParamsDto2.size() > 0) {
									tableInfoOfOuterBranchParamsDto.addAll(tableInfoOfOuterBranchParamsDto2);
								}

								List<SaleDepartmentEntity> sdList = new ArrayList<SaleDepartmentEntity>();
								if (tableInfoOfOuterBranchParamsDto != null && tableInfoOfOuterBranchParamsDto.size() > 0) {

									for (OuterBranchEntity ob : tableInfoOfOuterBranchParamsDto) {
										SaleDepartmentEntity sd = new SaleDepartmentEntity();
										sd.setCode(ob.getAgentDeptCode());
										sd.setName(ob.getAgentDeptName());
										sdList.add(sd);
									}

								}
								List<SaleDepartmentEntity> sdLis2 = salesDepartmentService.querySaleDeptByNameOnline(name,isArrived);
								if (sdLis2 != null && sdLis2.size() > 0) {
									sdList.addAll(sdLis2);
								}
								setData(sdList);
							} else {
								// 查询自由部门
								setData(salesDepartmentService.querySaleDeptByNameOnline(name,isArrived));
							}

						}
					}
					//获取光标
					if(table!=null && table.getRowCount()>0){
						table.requestFocus();
						table.setRowSelectionAllowed(true);
						table.setRowSelectionInterval(0,0);
					}
				}
			});
			contentPanel.add(btnNewButton, "16, 2");
		}
		{
			JScrollPane scrollPane = new JScrollPane(table);
			contentPanel.add(scrollPane, "2, 4, 19, 1, fill, fill");
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton(i18n.get("foss.gui.common.waybillEditUI.common.confirm"));
				okButton.setActionCommand(OK);
				buttonPane.add(okButton);

				okButton.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						setSaleDepartmentInfo();
						// 关闭界面，释放资源
						dispose();
					}

				});
				//Enter键有监控，取消setDefaultButton，如果不取消，会报异常
				//getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton(i18n.get("foss.gui.common.waybillEditUI.common.quit"));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				cancelButton.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						dispose();// 关闭界面
					}

				});
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
		table = new JXTable();
		table.setModel(new SaleDepartmentModel());
		table.setAutoscrolls(true);
		table.setSortable(false);
		table.setEditable(false);
		table.setColumnControlVisible(true);

		// 居中显示
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		render.setHorizontalAlignment(SwingConstants.CENTER);
		table.setDefaultRenderer(Object.class, render);
		((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		table.addMouseListener(new ClickTableHandler());
	}

	/**
	 * 
	 * 将数据添加到表格
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-3 上午11:29:46
	 */
	public void setData(List<SaleDepartmentEntity> saleDepartment) {
		SaleDepartmentModel model = ((SaleDepartmentModel) table.getModel());
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
				// 关闭界面，释放资源
				dispose();
			}
		}
	}
	
	/**
	 * 一般内部类：表格的单、双击处理事件
	 * 
	 * @author WangQianJin
	 * @date 2013-05-16
	 */
	public void tableEnter() {
		setSaleDepartmentInfo();
		// 关闭界面，释放资源
		dispose();
	}

	/**
	 * 
	 * 设置银行信息
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-6 下午07:40:27
	 */
	private void setSaleDepartmentInfo() {
		int row = table.getSelectedRow();
		SaleDepartmentModel model = ((SaleDepartmentModel) table.getModel());
		List<SaleDepartmentEntity> list = model.getData();
		if (list != null) {
			if (!list.isEmpty()) {
				saleDepartmentEntity = list.get(row);
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
	public class SaleDepartmentModel extends DefaultTableModel {

		private static final long serialVersionUID = 5883365603131625962L;

		private List<SaleDepartmentEntity> data;

		public List<SaleDepartmentEntity> getData() {
			return data;
		}

		public void setData(List<SaleDepartmentEntity> data) {
			this.data = data;
		}

		@Override
		public String getColumnName(int column) {
			switch (column) {
			case 0:
				return i18n.get("foss.gui.common.salesDepartmentDialog.columnName.departmentName");
			case 1:
				return i18n.get("foss.gui.common.salesDepartmentDialog.columnName.departmentCode");
			default:
				return "";
			}
		}

		@Override
		public int getColumnCount() {
			return 2;
		}

		@Override
		public int getRowCount() {
			return data == null ? 0 : data.size();
		}

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

	public SaleDepartmentEntity getSaleDepartmentEntity() {
		return saleDepartmentEntity;
	}

	public void setSaleDepartmentEntity(SaleDepartmentEntity saleDepartmentEntity) {
		this.saleDepartmentEntity = saleDepartmentEntity;
	}
}