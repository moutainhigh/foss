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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/ui/internal/IncrementPanel.java
 * 
 * FILE NAME        	: IncrementPanel.java
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
package com.deppon.foss.module.pickup.changingexp.client.ui.internal;

import java.awt.Dimension;
import java.awt.Insets;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.lang.StringUtils;
import org.jdesktop.swingx.JXTable;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.binding.validation.annotations.NotNull;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.annotation.ButtonAction;
import com.deppon.foss.framework.client.component.focuspolicy.annotation.ContainerSeq;
import com.deppon.foss.framework.client.component.focuspolicy.annotation.FocusSeq;
import com.deppon.foss.framework.client.core.binding.annotation.Bind;
import com.deppon.foss.framework.client.core.binding.annotation.BindingArg;
import com.deppon.foss.framework.client.core.binding.annotation.BindingArgs;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.client.widget.validatewidget.JTextFieldValidate;
import com.deppon.foss.module.mainframe.client.ui.JDTitledBorder;
import com.deppon.foss.module.pickup.changingexp.client.action.DeleteOtherChargeAction;
import com.deppon.foss.module.pickup.changingexp.client.action.QueryBankAccountAction;
import com.deppon.foss.module.pickup.changingexp.client.action.QueryOtherChargeAction;
import com.deppon.foss.module.pickup.changingexp.client.listener.CompareBeforeAfterValueBindingListener;
import com.deppon.foss.module.pickup.changingexp.client.ui.WaybillRFCUI;
import com.deppon.foss.module.pickup.changingexp.client.utils.Common;
import com.deppon.foss.module.pickup.changingexp.client.vo.WaybillInfoVo;
import com.deppon.foss.module.pickup.common.client.utils.BZPartnersJudge;
import com.deppon.foss.module.pickup.common.client.utils.CalculateFeeTotalUtils;
import com.deppon.foss.module.pickup.common.client.utils.FloatDocumentUtils;
import com.deppon.foss.module.pickup.common.client.utils.NumberDocument;
import com.deppon.foss.module.pickup.common.client.utils.PositiveNumberDocument;
import com.deppon.foss.module.pickup.common.client.vo.OtherChargeVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * 增值业务信息面板
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:102246-foss-shaohongliang,date:2012-10-17
 * 上午9:20:52,
 * </p>
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-10-17 上午9:20:52
 * @since
 * @version
 */
@ContainerSeq(seq=7)
public class IncrementPanel extends JPanel {
	/**
	 * 国际化
	 */
	private static II18n i18n = I18nManager.getI18n(IncrementPanel.class);

	
	private static final int FIVE = 5;



	/**
	 * 14 FOR FOR SIZE
	 */
	

	/**
	 * 10 FOR COLUMN NUMBER
	 */
	private static final int TEN = 10;

	/**
	 * fieldname
	 */
	private static final String FIELDNAME = "fieldName";
	/**
	 * 8 FOR COLUMN NUMBER
	 */
	private static final int EIGHT = 8;

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 6678647321837651011L;

	/**
	 * 保价声明价值
	 */
	@Bind("insuranceAmount")
	@NotNull
	@BindingArgs({ @BindingArg(name = "fieldName", value = "保价声明价值") })
	@FocusSeq(seq=1)
	private JTextFieldValidate txtInsuranceValue;

	/**
	 * 代收货款
	 */
	@Bind("codAmount")
	@NotNull
	@BindingArgs({ @BindingArg(name = "fieldName", value = "代收货款") })
	@FocusSeq(seq=2)
	private JTextFieldValidate txtCashOnDelivery;

	/**
	 * 收款人
	 */
	@Bind("accountName")
	@FocusSeq(seq=4)
	private JTextFieldValidate txtPayee;

	/**
	 * 收款账号
	 */
	@Bind("accountCode")
	@FocusSeq(seq=5)
	private JTextFieldValidate txtBankAccount;

	/**
	 * 包装费
	 */
	@Bind("packageFee")
	@NotNull
	@BindingArgs({ @BindingArg(name = "fieldName", value = "包装费") })
	@FocusSeq(seq=7)
	private JTextFieldValidate txtPackCharge;

	/**
	 * 装卸费
	 */
	@Bind("serviceFee")
	@NotNull
	@BindingArgs({ @BindingArg(name = "fieldName", value = "装卸费") })
	@FocusSeq(seq=8)
	private JTextFieldValidate txtServiceCharge;

	/**
	 * 送货费
	 */
	@Bind("deliveryGoodsFee")
	@NotNull
	@BindingArgs({ @BindingArg(name = "fieldName", value = "送货费") })
	@FocusSeq(seq=9)
	private JTextFieldValidate txtDeliveryCharge;

	/**
	 * 退款类型
	 */
	@Bind("refundType")
	@FocusSeq(seq=3)
	private JComboBox combRefundType;

	private JXTable table;

	private WaybillPanelVo waybillPanelVo;
	
	/**
	 * 增加按钮
	 */
	@ButtonAction(type = QueryOtherChargeAction.class)
	@FocusSeq(seq=10)
	private JButton btnAdd = new JButton(i18n.get("pickup.changingexp.add"));

	/**
	 * 删除按钮
	 */
	@ButtonAction(type = DeleteOtherChargeAction.class)
	@FocusSeq(seq=11)
	private JButton btnDelete = new JButton(i18n.get("pickup.changingexp.delete"));

	/**
	 * 查询收款人账号按钮
	 */
	@ButtonAction(icon = "query.png", shortcutKey = "", type = QueryBankAccountAction.class)
	@FocusSeq(seq=6)
	private JButton btnBankSearch = new JButton();

	/**
	 * 退款类型Model
	 */
	private DefaultComboBoxModel refundTypeModel;
	
	/**
	 * 其他费用Model
	 */
	private WaybillOtherCharge otherChargeTableModel;

	/**
	 * 主界面
	 */
	private WaybillRFCUI ui;
	/**
	 * 接货费
	 */
	@Bind("pickupFee")
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "接货费") })
	private JTextFieldValidate txtPickUpCharge;
	
	/**
	 * 市场营销活动名称
	 */
	private JLabel labelActiveInfo;
	@Bind("activeInfo")
	private JComboBox combActiveInfo;
	/**
	 * 营销活动Model
	 */
	private DefaultComboBoxModel activeInfoModel;
	
	public DefaultComboBoxModel getActiveInfoModel() {
		return activeInfoModel;
	}

	public void setActiveInfoModel(DefaultComboBoxModel activeInfoModel) {
		this.activeInfoModel = activeInfoModel;
	}
	
	/**
	 * 
	 * IncrementPanel
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午10:46:52
	 */
	public IncrementPanel() {
		init();
	}
	
	/**
	 * @return the ui
	 */
	public WaybillRFCUI getUi() {
		return ui;
	}

	/**
	 * @param ui the ui to set
	 */
	public void setUi(WaybillRFCUI ui) {
		this.ui = ui;
	}

	/**
	 * 
	 * 布局初始化
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午6:07:52
	 */
	private void init() {
		setBorder(new JDTitledBorder(i18n.get("foss.gui.changingexp.incrementPanel.title")));
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));

		NumberDocument pickUpCharge = new NumberDocument(FIVE);
		//保价声明价值
		JLabel lblInsuranceValue = new JLabel(i18n.get("foss.gui.changingexp.incrementPanel.insuranceValue.label")+"：");
		add(lblInsuranceValue, "2, 1, right, default");

		txtInsuranceValue = new JTextFieldValidate();
		txtInsuranceValue.setDocument(new NumberDocument(EIGHT));
		add(txtInsuranceValue, "4, 1, fill, default");
		txtInsuranceValue.setColumns(TEN);

		//代收货款
		JLabel lblCashOnDelivery = new JLabel(i18n.get("foss.gui.changingexp.incrementPanel.cashOnDelivery.label")+"：");
		add(lblCashOnDelivery, "6, 1, right, default");

		txtCashOnDelivery = new JTextFieldValidate();
//		txtCashOnDelivery.setDocument(new NumberDocumentAbs(EIGHT));
//		liyongfei 精确到角，设置代收货款可以输入小数
		FloatDocumentUtils cashOnDelivery = new FloatDocumentUtils(TEN, 2);
		txtCashOnDelivery.setDocument(cashOnDelivery);
		add(txtCashOnDelivery, "8, 1, fill, default");
		txtCashOnDelivery.setColumns(TEN);

		//退款类型
		JLabel lblRefundType = new JLabel(i18n.get("foss.gui.changingexp.incrementPanel.refundType.label")+"：");
		add(lblRefundType, "10, 1, right, default");

		combRefundType = new JComboBox();
		refundTypeModel = (DefaultComboBoxModel)combRefundType.getModel();
		add(combRefundType, "12, 1, fill, default");
		
		//表格滚动边框
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPane, "14, 1, 5, 5, fill, fill");

		//其他费用表格
		table = new OtherFeeJXTable();
		table.setAutoscrolls(true);
		table.setSortable(false);
		table.setColumnControlVisible(true);
		otherChargeTableModel = new WaybillOtherCharge();
		table.setModel(otherChargeTableModel);
		scrollPane.setViewportView(table);
		
		//收款人
		JLabel lblPayee = new JLabel(i18n.get("foss.gui.changingexp.incrementPanel.payee.label")+"：");
		add(lblPayee, "2, 3, right, default");

		txtPayee = new JTextFieldValidate();
		add(txtPayee, "4, 3, fill, default");
		txtPayee.setColumns(TEN);

		//收款账号
		JLabel lblBankAccount = new JLabel(i18n.get("foss.gui.changingexp.incrementPanel.bankAccount.label")+"：");
		add(lblBankAccount, "6, 3, right, default");
		
		JPanel panel = new JPanel();
		add(panel, "8, 3, fill, fill");
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		txtBankAccount = new JTextFieldValidate();
		panel.add(txtBankAccount);
		txtBankAccount.setColumns(TEN);
		panel.add(btnBankSearch);
		
		btnBankSearch.setMargin(new Insets(-2, 1, -2, 1));
		
		JLabel label = new JLabel("接货费：");
		add(label, "10, 3, right, default");
		
		txtPickUpCharge = new JTextFieldValidate();
		txtPickUpCharge.setEnabled(false);
		add(txtPickUpCharge, "12, 3, fill, default");
		txtPickUpCharge.setDocument(pickUpCharge);
		txtPickUpCharge.setColumns(TEN);
		//包装费
		JLabel lblPackCharge = new JLabel(i18n.get("foss.gui.changingexp.incrementPanel.packCharge.label")+"：");
		add(lblPackCharge, "2, 5, right, default");

		txtPackCharge = new JTextFieldValidate();
		add(txtPackCharge, "4, 5, fill, default");
		txtPackCharge.setDocument(new NumberDocument(EIGHT));
		txtPackCharge.setColumns(TEN);

		//装卸费
		JLabel lblServiceCharge = new JLabel(i18n.get("foss.gui.changingexp.incrementPanel.serviceCharge.label")+"：");
		add(lblServiceCharge, "6, 5, right, default");

		txtServiceCharge = new JTextFieldValidate();
		add(txtServiceCharge, "8, 5, fill, default");
		txtServiceCharge.setDocument(new PositiveNumberDocument(FIVE));
		txtServiceCharge.setColumns(TEN);
		txtServiceCharge.setEnabled(false);

		//送货费
		JLabel lblDeliveryCharge = new JLabel(i18n.get("foss.gui.changingexp.incrementPanel.deliveryCharge.label")+"：");
		add(lblDeliveryCharge, "10, 5, right, default");

		txtDeliveryCharge = new JTextFieldValidate();
		add(txtDeliveryCharge, "12, 5, fill, default");
		txtDeliveryCharge.setDocument(new NumberDocument(FIVE));
		txtDeliveryCharge.setColumns(TEN);
				
		labelActiveInfo = new JLabel(i18n.get("foss.gui.creating.incrementpanel.activeinfo.discount.name")+"：");
		//如果是合伙人营业部，市场推广活动出不显示
		if(!BZPartnersJudge.IS_PARTENER || !BZPartnersJudge.GENERALIZE_ACTIVITY_EXP){
			add(labelActiveInfo, "2, 7, right, default");
		}
		combActiveInfo = new JComboBox();
		//固定下拉框宽度，不随内容增加而增大
		combActiveInfo.setMaximumSize(new Dimension(NumberConstants.NUMBER_100, NumberConstants.NUMBER_23));
		combActiveInfo.setMinimumSize(new Dimension(NumberConstants.NUMBER_100, NumberConstants.NUMBER_23));
		combActiveInfo.setPreferredSize(new Dimension(NumberConstants.NUMBER_100, NumberConstants.NUMBER_23));
		activeInfoModel = (DefaultComboBoxModel)combActiveInfo.getModel();
		//如果是合伙人营业部，市场推广活动出不显示
		if(!BZPartnersJudge.IS_PARTENER || !BZPartnersJudge.GENERALIZE_ACTIVITY_EXP){
			add(combActiveInfo, "4, 7, fill, default");
		}

		add(btnAdd, "16, 7");
				
		add(btnDelete, "18, 7");
	}

	
	/**
	 * 
	 * 封装表格的数据模型，设置成以对象进行操作
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-3 上午11:30:03
	 */
	public class WaybillOtherCharge extends DefaultTableModel {

		/**
		 * 序列化版本号
		 */
		private static final long serialVersionUID = 5883365603131625962L;

		/**
		 * 表格数据
		 */
		private List<OtherChargeVo> changeDetailList;

		/**
		 * 获得表格数据
		 * @return
		 */
		public List<OtherChargeVo> getData() {
			return changeDetailList;
		}

		/**
		 * 设置表格数据
		 * @param changeDetailList
		 */
		public void setData(List<OtherChargeVo> changeDetailList) {
			this.changeDetailList = changeDetailList;
		}

		
		/**
		 * 设置单元格的头
		 *
		 */
		public String getColumnName(int column) {
			switch (column) {
			case 0:
				return i18n.get("foss.gui.changingexp.incrementPanel.gridName.label");
			case 1:
				return i18n.get("foss.gui.changingexp.incrementPanel.gridValue.label");
			default:
				return "";
			}
		}
		
		/**
		 * 设置单元格的值
		 *
		 */
		public void setValueAt(Object aValue, int row, int column) {
			List<OtherChargeVo> data = this.getData();
			
			/**
			 * 表格第一列名字是不能修改的
			 */
			if(column==0){
				return;
			}
			
			OtherChargeVo vo = data.get(row);
			
			/**
			 * vo is null
			 */
			if(vo==null){
				return;
			}
			
			//是否可以修改
			Boolean isUpdate = vo.getIsUpdate();

			/**
			 * 修改no
			 */
			if(isUpdate==null){
				isUpdate = Boolean.FALSE;
			}
			
			
			//更改单的时候  综合信息费  燃油附加费 不能修改
			if(WaybillConstants.OTHERFEE_ZHXX.equals(vo.getCode())
					|| WaybillConstants.OTHERFEE_RYFJ.equals(vo.getCode())){
				isUpdate = Boolean.FALSE;
			}
			
			double money  = 0;
			try{
				money = Double.parseDouble((String)aValue);
				/**
				 * 金额<=0
				 */
				/*if(money<=0){
					 throw new Exception(i18n.get("foss.gui.changingexp.incrementPanel.exception.label"));
				}*/
			}catch(Exception e){
				/**
				 * 输入金额非法
				 */
				money = -1;
				MsgBox.showInfo(i18n.get("foss.gui.changingexp.incrementPanel.msgbox.label"));
				return;
			}
			/**
			 *   保留2位小数
			 */
			BigDecimal b  =BigDecimal.valueOf(money);
			/**
			 *  四舍五入
			 */
			double f1 = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
			BigDecimal valueCal=b.setScale(2, BigDecimal.ROUND_HALF_UP);
			String moneyStr = Double.toString(f1);
			aValue = moneyStr;
			//非法
			if(!isUpdate){
				return;
			}else{
				/**
				 * set up money
				 */
				try {
					String oldMoney = vo.getMoney();

					double oldvalue = 0;
					try {
						oldvalue = Double.parseDouble(oldMoney);
					} catch (Exception e) {
					}
					if (waybillPanelVo != null) {
						waybillPanelVo.setOtherFee(waybillPanelVo.getOtherFee().subtract(BigDecimal.valueOf(oldvalue)).add(BigDecimal.valueOf(f1)));
						waybillPanelVo.setOtherFeeCanvas(waybillPanelVo.getOtherFee().toString());
					}
					
					if(StringUtils.isNotEmpty(vo.getUpperLimit())&&StringUtils.isNotEmpty(vo.getLowerLimit()))
					{
						
						BigDecimal upperLimit=new BigDecimal(vo.getUpperLimit());
						BigDecimal lowerLimit=new BigDecimal(vo.getLowerLimit());
						if(valueCal.compareTo(upperLimit)==1)
						{
							MsgBox.showInfo(i18n.get("foss.gui.creating.incrementPanel.msgbox.label.max.value"));
							return;
						}
						else if(valueCal.compareTo(lowerLimit)==-1)
						{
							MsgBox.showInfo(i18n.get("foss.gui.creating.incrementPanel.msgbox.label.min.value"));
							return;
						}
						
					}
					
					vo.setMoney(moneyStr);
				} catch (Exception e) {
					/**
					 * 输入金额非法
					 */
					money = -1;
					MsgBox.showInfo(i18n.get("foss.gui.creating.incrementPanel.msgbox.label"));
					return;
				}
			}
			/**
			 * go
			 */
			fireTableCellUpdated(row, column);
			
			//这里除了添加表格的修改内容 还要把修改的金额放到下面的明细中去
			
		}
		
		/**
		 * 获得列数
		 */
		public int getColumnCount() {
			return 2;
		}

		/**
		 * 获得行数
		 */
		public int getRowCount() {
			return changeDetailList == null ? 0 : changeDetailList.size();
		}

		/**
		 * 获得数据
		 */
		public Object getValueAt(int row, int column) {
			switch (column) {
			case 0:
				//费用名称
				return changeDetailList.get(row).getChargeName();
			case 1:
				//费用金额
				return changeDetailList.get(row).getMoney();
			default:
				return super.getValueAt(row, column);
			}

		}
	}
	
	
	
	
	
	
	public JTextFieldValidate getTxtPickUpCharge() {
		return txtPickUpCharge;
	}

	/**
	 * @return the txtInsuranceValue
	 */
	public JTextFieldValidate getTxtInsuranceValue() {
		return txtInsuranceValue;
	}

	/**
	 * @return the txtCashOnDelivery
	 */
	public JTextFieldValidate getTxtCashOnDelivery() {
		return txtCashOnDelivery;
	}

	/**
	 * @return the txtPayee
	 */
	public JTextFieldValidate getTxtPayee() {
		return txtPayee;
	}

	/**
	 * @return the txtBankAccount
	 */
	public JTextFieldValidate getTxtBankAccount() {
		return txtBankAccount;
	}

	/**
	 * @return the txtPackCharge
	 */
	public JTextFieldValidate getTxtPackCharge() {
		return txtPackCharge;
	}

	/**
	 * @return the txtServiceCharge
	 */
	public JTextFieldValidate getTxtServiceCharge() {
		return txtServiceCharge;
	}

	/**
	 * @return the txtDeliveryCharge
	 */
	public JTextFieldValidate getTxtDeliveryCharge() {
		return txtDeliveryCharge;
	}

	/**
	 * @return the combRefundType
	 */
	public JComboBox getCombRefundType() {
		return combRefundType;
	}

	/**
	 * @return the table
	 */
	public JXTable getTable() {
		return table;
	}

	/**
	 * @return the btnAdd
	 */
	public JButton getBtnAdd() {
		return btnAdd;
	}

	/**
	 * @return the btnDelete
	 */
	public JButton getBtnDelete() {
		return btnDelete;
	}

	public DefaultComboBoxModel getRefundTypeModel() {
		return refundTypeModel;
	}

	/**
	 * 
	 * 将数据添加到表格
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-3 上午11:29:46
	 */
	public void setChangeDetail(List<OtherChargeVo> changeDetailList) {
		//从其他费用里面去除送货费
		deleteDeliverFee(changeDetailList);
		otherChargeTableModel.setData(changeDetailList);
		// 刷新表格数据
		otherChargeTableModel.fireTableDataChanged();
		
		if(waybillPanelVo!=null){
			if(waybillPanelVo instanceof WaybillInfoVo){
				Common.getYokeCharge((WaybillInfoVo)waybillPanelVo);
			}
			CalculateFeeTotalUtils.calculateFee(waybillPanelVo);
		}
	}
	
	/**
	 * 
	 * 将数据添加到表格
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-3 上午11:29:46
	 */
	public void setChangeDetailNoCalculate(List<OtherChargeVo> changeDetailList) {
		//从其他费用里面去除送货费
		deleteDeliverFee(changeDetailList);
		otherChargeTableModel.setData(changeDetailList);
		// 刷新表格数据
		otherChargeTableModel.fireTableDataChanged();
	}

	
	public WaybillOtherCharge getOtherChargeTableModel() {
		return otherChargeTableModel;
	}

	public JButton getBtnBankSearch() {
		return btnBankSearch;
	}
	
	/**
	 * 删除送货费明细项
	 * @author WangQianJin
	 * @date 2013-6-18 下午2:39:35
	 */
	private void deleteDeliverFee(List<OtherChargeVo> changeDetailList){		
		List<OtherChargeVo> delList=new ArrayList<OtherChargeVo>();
		if(changeDetailList!=null && changeDetailList.size()>0){
			/**
			 * 删除所有的送货费子项(包括：送货费、送货上楼费、送货进仓费、超远派送费)
			 */
			for(OtherChargeVo chargeVo : changeDetailList){
				if(PriceEntityConstants.PRICING_CODE_SH.equals(chargeVo.getCode())
						|| PriceEntityConstants.PRICING_CODE_SHSL.equals(chargeVo.getCode())
						|| PriceEntityConstants.PRICING_CODE_SHJC.equals(chargeVo.getCode())
						|| PriceEntityConstants.PRICING_CODE_CY.equals(chargeVo.getCode())){					
					delList.add(chargeVo);
				}
			}
			//将要删除的送货费子项全部删除，子所以要放到delList里面一块删除，是因为遍历的时候不能直接删除，偶尔会报java.util.ConcurrentModificationException
			if(delList.size()>0){
				changeDetailList.removeAll(delList);
			}
		}
	}

	/**
	 * 其他费用table
	 * @author shixw
	 *
	 */
	public class OtherFeeJXTable extends JXTable{

		/**
		 * 序列化版本号
		 */
		private static final long serialVersionUID = 1L;
		
		/**
		 * 该单元格是否可以编辑
		 */
		public boolean isCellEditable(int row, int column) {
			
			WaybillOtherCharge model = (WaybillOtherCharge) this.getModel();
			List<OtherChargeVo> data = model.getData();
			
			/**
			 * 表格第一列名字是不能修改的
			 */
			if(column==0){
				return false;
			}
			
			OtherChargeVo vo = data.get(row);
			
			if(vo==null){
				return false;
			}
			
			
			//是否可以修改
			Boolean isUpdate = vo.getIsUpdate();

			if(isUpdate==null){
				isUpdate = Boolean.FALSE;
			}
			
			return isUpdate;
		}
		
	}


	/**
	 * removeTableListener
	 * 删除table内容修改的事件处理机制
	 * 其他费用变化 --> 变更信息对话框界面 
	 * @param compareListener
	 */
	public void removeTableListener(
			CompareBeforeAfterValueBindingListener compareListener) {
		table.getModel().removeTableModelListener(compareListener);
	}

	/**
	 * addTableListener
	 * 对table添加cell内容修改的事件处理机制
	 * 其他费用变化 --> 变更信息对话框界面 
	 * @param compareListener
	 */
	public void addTableListener(
			CompareBeforeAfterValueBindingListener compareListener) {
		table.getModel().addTableModelListener(compareListener);
	}
	
	/**
	 * @return the waybillPanelVo
	 */
	public WaybillPanelVo getWaybillPanelVo() {
		return waybillPanelVo;
	}

	/**
	 * @param waybillPanelVo the waybillPanelVo to set
	 */
	public void setWaybillPanelVo(WaybillPanelVo waybillPanelVo) {
		this.waybillPanelVo = waybillPanelVo;
	}

	/**
	 * 
	 * return the table
	 * @author 102246-foss-shaohongliang
	 * @date 2013-3-12 下午7:34:04
	 */
	public JXTable getTblOther() {
		return table;
	}
	
}