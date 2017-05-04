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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changing/client/ui/internal/IncrementPanel.java
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
package com.deppon.foss.module.pickup.changing.client.ui.internal;

import java.awt.Dimension;
import java.awt.Insets;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
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
import com.deppon.foss.module.pickup.changing.client.action.DeleteOtherChargeAction;
import com.deppon.foss.module.pickup.changing.client.action.QueryBankAccountAction;
import com.deppon.foss.module.pickup.changing.client.action.QueryOtherChargeAction;
import com.deppon.foss.module.pickup.changing.client.listener.CompareBeforeAfterValueBindingListener;
import com.deppon.foss.module.pickup.changing.client.ui.WaybillRFCUI;
import com.deppon.foss.module.pickup.changing.client.utils.Common;
import com.deppon.foss.module.pickup.changing.client.vo.WaybillInfoVo;
import com.deppon.foss.module.pickup.common.client.utils.BZPartnersJudge;
import com.deppon.foss.module.pickup.common.client.utils.CalculateFeeTotalUtils;
import com.deppon.foss.module.pickup.common.client.utils.NumberDocument;
import com.deppon.foss.module.pickup.common.client.utils.NumberDocumentUtils;
import com.deppon.foss.module.pickup.common.client.utils.PositiveNumberDocument;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.common.client.vo.OtherChargeVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.util.CollectionUtils;
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
	private JLabel lblNewLabel;
	private JLabel lblNewLabel1;


	/**
	 * 保价声明价值
	 */
	@Bind("insuranceAmount")
	@NotNull
	@BindingArgs({ @BindingArg(name = "fieldName", value = "保价声明价值") })
	@FocusSeq(seq=1)
	private JTextFieldValidate txtInsuranceValue;
	
	/**
	 * 保价费率
	 */
	@Bind("insuranceRate")
	@NotNull
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "保价费率") })
	private JTextFieldValidate txtInsuranceRate;
	
	/**
	 * 报价费率范围
	 * */
	@Bind("insuranceRateRange")
	private JTextFieldValidate txtInsuranceRateRange;	//@BindingArgs({ @BindingArg(name = FIELDNAME, value = "报价费率范围") })
	
	public JTextFieldValidate getTxtInsuranceRateRange() {
		return txtInsuranceRateRange;
	}

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
	 * 其他费用安装费
	 */
	@ButtonAction(type = InstallAction.class)
	@FocusSeq(seq=10)
	private JButton btnInstall = new JButton(i18n.get("pickup.changing.btnInstall"));

	/**
	 * 增加按钮
	 */
	@ButtonAction(type = QueryOtherChargeAction.class)
	@FocusSeq(seq=10)
	private JButton btnAdd = new JButton(i18n.get("pickup.changing.add"));

	/**
	 * 删除按钮
	 */
	@ButtonAction(type = DeleteOtherChargeAction.class)
	@FocusSeq(seq=11)
	private JButton btnDelete = new JButton(i18n.get("pickup.changing.delete"));

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
	
	//添加保价费、代收手续费 2016年1月16日 12:23:37 葛亮亮
	/**
	 * 保价费
	 */
	@Bind("supportFee")
	@BindingArgs({@BindingArg(name = FIELDNAME, value = "保价费")})
	private JTextFieldValidate txtSupportFee ;
	
	//代收手续费
	@Bind("collectingFee")
	@BindingArgs({@BindingArg(name = FIELDNAME, value = "代收手续费")})
	@FocusSeq(seq = 5)
	private JTextFieldValidate txtCollectingFee ;
	

	/**
	 * 营销活动Model
	 */
	private DefaultComboBoxModel activeInfoModel;
//	private JTextField textField;
	
	public DefaultComboBoxModel getActiveInfoModel() {
		return activeInfoModel;
	}

	public void setActiveInfoModel(DefaultComboBoxModel activeInfoModel) {
		this.activeInfoModel = activeInfoModel;
	}

	public JComboBox getCombActiveInfo() {
		return combActiveInfo;
	}

	public void setCombActiveInfo(JComboBox combActiveInfo) {
		this.combActiveInfo = combActiveInfo;
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
		setBorder(new JDTitledBorder(i18n.get("foss.gui.changing.incrementPanel.title")));
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("39dlu"),
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("17dlu"),
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("45dlu"),
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
		JLabel lblInsuranceValue = new JLabel(i18n.get("foss.gui.changing.incrementPanel.insuranceValue.label")+"：");
		add(lblInsuranceValue, "2, 1, right, default");

		txtInsuranceValue = new JTextFieldValidate();
		txtInsuranceValue.setDocument(new NumberDocument(EIGHT));
		add(txtInsuranceValue, "4, 1, fill, default");
		txtInsuranceValue.setColumns(TEN);
		//保价费率
		lblNewLabel = new JLabel(i18n.get("foss.gui.creating.canvasContentPanel.insuranceRate.label")+"：");
		add(lblNewLabel, "5, 1, right, default");
		lblNewLabel.setVisible(true);
		//保价费率
		txtInsuranceRate = new JTextFieldValidate();
		add(txtInsuranceRate, "6, 1, fill, default");
		txtInsuranceRate.setColumns(TEN);
		txtInsuranceRate.setEnabled(true);
		txtInsuranceRate.setVisible(true);
		
		lblNewLabel1 = new JLabel("‰");
		add(lblNewLabel1, "7, 1, right, default");
		lblNewLabel1.setVisible(true);
		
		txtInsuranceRateRange = new JTextFieldValidate();
		add(txtInsuranceRateRange, "8, 1, 2, 1, fill, default");
		txtInsuranceRateRange.setColumns(NumberConstants.NUMBER_10);
		txtInsuranceRateRange.setEnabled(false);

		//代收货款
		JLabel lblCashOnDelivery = new JLabel(i18n.get("foss.gui.changing.incrementPanel.cashOnDelivery.label")+"：");
		add(lblCashOnDelivery, "10, 1, right, default");

		txtCashOnDelivery = new JTextFieldValidate();
		txtCashOnDelivery.setDocument(new NumberDocumentUtils(NumberConstants.NUMBER_9));
		add(txtCashOnDelivery, "12, 1, fill, default");
		txtCashOnDelivery.setColumns(TEN);

		//退款类型
		JLabel lblRefundType = new JLabel(i18n.get("foss.gui.changing.incrementPanel.refundType.label")+"：");
		add(lblRefundType, "14, 1, right, default");

		combRefundType = new JComboBox();
		refundTypeModel = (DefaultComboBoxModel)combRefundType.getModel();
		add(combRefundType, "16, 1, fill, default");
		
		//表格滚动边框
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPane, "18, 1, 7, 5, fill, fill");

		//其他费用表格
		table = new OtherFeeJXTable();
		table.setAutoscrolls(true);
		table.setSortable(false);
		table.setColumnControlVisible(true);
		otherChargeTableModel = new WaybillOtherCharge();
		table.setModel(otherChargeTableModel);
		scrollPane.setViewportView(table);
		
		//收款人
		JLabel lblPayee = new JLabel(i18n.get("foss.gui.changing.incrementPanel.payee.label")+"：");
		add(lblPayee, "2, 3, right, default");

		txtPayee = new JTextFieldValidate();
		add(txtPayee, "4, 3, 5, 1, fill, default");
		txtPayee.setColumns(TEN);

		//收款账号
		JLabel lblBankAccount = new JLabel(i18n.get("foss.gui.changing.incrementPanel.bankAccount.label")+"：");
		add(lblBankAccount, "10, 3, right, default");
		
		JPanel panel = new JPanel();
		add(panel, "12, 3, fill, fill");
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		txtBankAccount = new JTextFieldValidate();
		panel.add(txtBankAccount);
		txtBankAccount.setColumns(TEN);
		panel.add(btnBankSearch);
		
		btnBankSearch.setMargin(new Insets(-2, 1, -2, 1));
		
		JLabel label = new JLabel("接货费：");
		add(label, "14, 3, right, default");
		
		txtPickUpCharge = new JTextFieldValidate();
		txtPickUpCharge.setEnabled(false);
		add(txtPickUpCharge, "16, 3, fill, default");
		txtPickUpCharge.setDocument(pickUpCharge);
		txtPickUpCharge.setColumns(NumberConstants.NUMBER_10);
		
		//包装费
		JLabel lblPackCharge = new JLabel(i18n.get("foss.gui.changing.incrementPanel.packCharge.label")+"：");
		add(lblPackCharge, "2, 5, right, default");

		txtPackCharge = new JTextFieldValidate();
		add(txtPackCharge, "4, 5, 5, 1, fill, default");
		txtPackCharge.setDocument(new NumberDocument(EIGHT));
		txtPackCharge.setColumns(TEN);
		txtPackCharge.setEditable(false);

		//装卸费
		JLabel lblServiceCharge = new JLabel(i18n.get("foss.gui.changing.incrementPanel.serviceCharge.label")+"：");
		
		txtServiceCharge = new JTextFieldValidate();
		txtServiceCharge.setDocument(new PositiveNumberDocument(FIVE));
		txtServiceCharge.setColumns(TEN);
		
		//添加保价费 2016年1月16日 13:16:57 葛亮亮
		//保价费
		JLabel lblNewLabel3 = new JLabel(i18n.get("foss.gui.changing.incrementPanel.lblNewLabel3.label")+"：");
		
		txtSupportFee = new JTextFieldValidate();		
		txtSupportFee.setDocument(new NumberDocument(FIVE));
		txtSupportFee.setColumns(NumberConstants.NUMBER_8);
		
		//合伙人隐藏装卸费，显示保价费,代收手续费
		if(!BZPartnersJudge.IS_PARTENER || !BZPartnersJudge.SERVICE_RATE){
			add(lblServiceCharge, "10, 5, right, default");
			add(txtServiceCharge, "12, 5, fill, default");
		}else{
			add(lblNewLabel3, "10, 5, right, default");
			add(txtSupportFee, "12, 5, fill, default");
		}
		
		//送货费
		JLabel lblDeliveryCharge = new JLabel(i18n.get("foss.gui.changing.incrementPanel.deliveryCharge.label")+"：");
		add(lblDeliveryCharge, "14, 5, right, default");

		txtDeliveryCharge = new JTextFieldValidate();
		add(txtDeliveryCharge, "16, 5, fill, default");
		txtDeliveryCharge.setDocument(new NumberDocument(FIVE));
		txtDeliveryCharge.setColumns(TEN);		
						
		labelActiveInfo = new JLabel(i18n.get("foss.gui.creating.incrementpanel.activeinfo.discount.name")+"：");	
		
		combActiveInfo = new JComboBox();
		//固定下拉框宽度，不随内容增加而增大
		combActiveInfo.setMaximumSize(new Dimension(NumberConstants.NUMBER_100,NumberConstants.NUMBER_23));
		combActiveInfo.setMinimumSize(new Dimension(NumberConstants.NUMBER_100,NumberConstants.NUMBER_23));
		combActiveInfo.setPreferredSize(new Dimension(NumberConstants.NUMBER_100,NumberConstants.NUMBER_23));
		activeInfoModel = (DefaultComboBoxModel)combActiveInfo.getModel();
		
		//代收手续费
		JLabel lblNewLabel4 = new JLabel(i18n.get("foss.gui.changing.incrementPanel.lblNewLabel4.label")+"：");
		
		txtCollectingFee = new JTextFieldValidate();	
		txtCollectingFee.setDocument(new NumberDocument(FIVE));
		txtCollectingFee.setColumns(NumberConstants.NUMBER_8);
		
		if(!BZPartnersJudge.IS_PARTENER || !BZPartnersJudge.GENERALIZE_ACTIVITY){
			add(labelActiveInfo, "2, 7, right, default");
			add(combActiveInfo, "4, 7, 5, 1, fill, default");
		}else{
			add(lblNewLabel4, "2, 7, right, default");
			add(txtCollectingFee, "4, 7, 5, 1, fill, default");
		}

		add(btnInstall, "20, 7");

		add(btnAdd, "22, 7");
		
		add(btnDelete, "24, 7");
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
				return i18n.get("foss.gui.changing.incrementPanel.gridName.label");
			case 1:
				return i18n.get("foss.gui.changing.incrementPanel.gridValue.label");
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
			//填充页面其他费用明细并判断是否允许更改费用
			if (waybillPanelVo != null ) {
				//判断为合伙人时，可以更改
				// ================优化内容:合伙人开单时，跳过此判断/时间:2016年10月26日上午8:24:02/LianHe/start================
				if (BZPartnersJudge.IS_PARTENER) {
					//为合伙人时，跳过判断，继续向下执行；
					
				} else {
					//不是合伙人开单时，判断是否是客户要求，若是则不允许更改
					if(waybillPanelVo instanceof WaybillInfoVo){
						if(WaybillRfcConstants.CUSTOMER_REQUIRE.equals(((WaybillInfoVo)waybillPanelVo).getRfcSource())){
							return;
						}					
					}				
				}
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
					 throw new Exception(i18n.get("foss.gui.changing.incrementPanel.exception.label"));
				}*/
			}catch(Exception e){
				/**
				 * 输入金额非法
				 */
				money = -1;
				MsgBox.showInfo(i18n.get("foss.gui.changing.incrementPanel.msgbox.label"));
				return;
			}
			// ================优化内容:合伙人二期：异常操作费更改值不能大于原来值/时间:2016年10月31日下午6:22:24/LianHe/start================
			//健壮性判断，为合伙人时才走这步操作
			if (waybillPanelVo != null && BZPartnersJudge.IS_PARTENER) {
					
				if (StringUtils.equals(PriceEntityConstants.PRICING_CODE_ZZ,vo.getCode())) {
					//获取系统计算的转运费
					double oldMoney = 0L;
					WaybillInfoVo waybillInfoVo = null;
					if (waybillPanelVo instanceof WaybillInfoVo) {
						//判断是返货还是转运
						waybillInfoVo = (WaybillInfoVo) waybillPanelVo;
						BigDecimal newZqExceptionOperateFee = (null != waybillInfoVo.getNewZqExceptionOperateFee() ? waybillInfoVo.getNewZqExceptionOperateFee() : BigDecimal.ZERO);
						oldMoney = newZqExceptionOperateFee.doubleValue();
					} else {
						MsgBox.showInfo(i18n.get("foss.gui.creating.incrementPanel.msgbox.label.tfrFee.value"));
						return;
					}
					//获取更改类型
					String rfcType = waybillInfoVo.getRfcType().getValueCode();
					//只有转运或者返货的时候才操作
					if (WaybillRfcConstants.TRANSFER.equals(rfcType) || WaybillRfcConstants.RETURN.equals(rfcType)) {
						//==================判断异常操作费是否合法===================
						if (money > oldMoney) {
							MsgBox.showInfo(i18n.get("foss.gui.creating.incrementPanel.msgbox.label.max.value"));
							return;
						}else if(money < 0){
							MsgBox.showInfo(i18n.get("foss.gui.creating.incrementPanel.msgbox.label.min.value"));
							return;
						}
						//===============异常操作费更改后，同时更改反转计算转运费和转运费率========
						//获取计费类型，根据计费类型进行不同的判断
						DataDictionaryValueVo billingType = waybillInfoVo.getBillingType();
						//如果计费类型为空，则设置默认为重量计费,弹出提示框
						if (billingType == null) { 
							MsgBox.showInfo(i18n.get("foss.gui.creating.incrementPanel.msgbox.label.billingType.value"));
							return;
						}
						//获取体积
						BigDecimal goodsVolumeTotal = (null != waybillInfoVo.getGoodsVolumeTotal() ? waybillInfoVo.getGoodsVolumeTotal() : BigDecimal.ZERO);
						//获取重量
						BigDecimal goodsWeightTotal = (null != waybillInfoVo.getGoodsWeightTotal() ? waybillInfoVo.getGoodsWeightTotal() : BigDecimal.ZERO);
						//定义转运费
						BigDecimal tfrFee = BigDecimal.ZERO;
						//定义转运费率
						BigDecimal tfrFeeRate = BigDecimal.ZERO;
						//转运费为修改的异常操作费
						tfrFee = BigDecimal.valueOf(money).setScale(2, RoundingMode.HALF_UP);
						if (StringUtils.equals("VOLUME", billingType.getValueCode())) {//体积计费时
							//如果goodsVolumeTotal为0，则不合理
							if (0 == goodsVolumeTotal.compareTo(BigDecimal.ZERO)) {
								MsgBox.showInfo(i18n.get("foss.gui.creating.incrementPanel.msgbox.label.tfrFee.value"));
								return;
							}
							tfrFeeRate = tfrFee.divide(goodsVolumeTotal,0,BigDecimal.ROUND_HALF_UP);
						} else if(StringUtils.equals("WEIGHT", billingType.getValueCode())){//重量计费时
							if (0 == goodsWeightTotal.compareTo(BigDecimal.ZERO)) {
								MsgBox.showInfo(i18n.get("foss.gui.creating.incrementPanel.msgbox.label.tfrFee.value"));
								return;
							}
							tfrFeeRate = tfrFee.divide(goodsWeightTotal,0,BigDecimal.ROUND_HALF_UP);
						}
						//给面板赋值
						waybillInfoVo.setTfrFee(tfrFee);
						waybillInfoVo.setTfrUnitPrice(tfrFeeRate);
						
						//=============更新存储到T_SRV_WAYBILLRFC_TRANSFER表的数据，
						//健壮性判断，由于合伙人异常操作费可以修改，为保证T_SRV_WAYBILLRFC_TRANSFER中费用信息和运单其他费用明细表中的信息一致
						if (waybillInfoVo.getRfcTranferList() != null && waybillInfoVo.getRfcTranferList().get(0) != null ) {
							waybillInfoVo.getRfcTranferList().get(0).setTransportFee(tfrFee);
							waybillInfoVo.getRfcTranferList().get(0).setTransportFeeRate(tfrFeeRate);
						}
						//普通开单时，更改的金额不能小于0
					}else if (WaybillRfcConstants.CUSTOMER_CHANGE.equals(rfcType)) {
						if(money < 0){
							MsgBox.showInfo(i18n.get("foss.gui.creating.incrementPanel.msgbox.label.min.value"));
							return;
						}
					}
				}
			}
			// ================优化内容:合伙人二期：异常操作费更改值不能大于原来值/时间:2016年10月31日下午6:22:28/LianHe/end================
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
						//to do nothing
					}
					if (waybillPanelVo != null) {
						waybillPanelVo.setOtherFee(waybillPanelVo.getOtherFee().subtract(BigDecimal.valueOf(oldvalue)).add(BigDecimal.valueOf(f1)));
						waybillPanelVo.setOtherFeeCanvas(waybillPanelVo.getOtherFee().toString());
					}
					
					if(StringUtils.isNotEmpty(vo.getUpperLimit())&&StringUtils.isNotEmpty(vo.getLowerLimit()))
					{
						
						BigDecimal upperLimit=new BigDecimal(vo.getUpperLimit());
						BigDecimal lowerLimit=new BigDecimal(vo.getLowerLimit());
						if(PriceEntityConstants.QT_CODE_CZHCZFWF.equals(vo.getCode())
						   ||
						   PriceEntityConstants.QT_CODE_CZHCZFWF_SDTJ.equals(vo.getCode())){
							if(valueCal.compareTo(upperLimit)==1)
							{
								MsgBox.showInfo(i18n.get("foss.gui.creating.incrementPanel.msgbox.label.max.value"));
								return;
							}
							else if(valueCal.compareTo(BigDecimal.ZERO)<0)
							{
								MsgBox.showInfo("金额不能小于0");
								return;
							}
						}else{
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
			//提交按钮不可用
			if(ui!=null){
				ui.getButtonPanel().getBtnSubmit().setEnabled(false);
			}
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
			try {
				switch (column) {
                case 0:
                    //费用名称
                    if(CollectionUtils.isNotEmpty(changeDetailList)){
                        return changeDetailList.get(row).getChargeName();
                    }else{
                        return null;
                    }
                case 1:
                    //费用金额
                    if(CollectionUtils.isNotEmpty(changeDetailList)){
                        return changeDetailList.get(row).getMoney();
                    }else{
                        return null;
                    }
                default:
                    if(CollectionUtils.isNotEmpty(changeDetailList)&&changeDetailList.get(row)!=null) {
                        return super.getValueAt(row, column);
                    }else{
                        return null;
                    }

                }
			} catch (Exception e) {
				return null;
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
	 * @return the getTxtInsuranceRate
	 */
	public JTextFieldValidate getTxtInsuranceRate() {
		return txtInsuranceRate;
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

	public JButton getBtnInstall() {
		return btnInstall;
	}

	public void setBtnInstall(JButton btnInstall) {
		this.btnInstall = btnInstall;
	}

	public DefaultComboBoxModel getRefundTypeModel() {
		return refundTypeModel;
	}
	
	public JTextFieldValidate getTxtSupportFee() {
		return txtSupportFee;
	}

	public void setTxtSupportFee(JTextFieldValidate txtSupportFee) {
		this.txtSupportFee = txtSupportFee;
	}

	public JTextFieldValidate getTxtCollectingFee() {
		return txtCollectingFee;
	}

	public void setTxtCollectingFee(JTextFieldValidate txtCollectingFee) {
		this.txtCollectingFee = txtCollectingFee;
	}

	/**
	 * 
	 * 将数据添加到表格
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-3 上午11:29:46
	 */
	public void setChangeDetail(List<OtherChargeVo> changeDetailList) {
		//从其他费用里面去除送货费子项
		Common.deleteDeliverFee(changeDetailList);
		otherChargeTableModel.setData(changeDetailList);
		// 刷新表格数据
		otherChargeTableModel.fireTableDataChanged();
		//提交按钮置灰  by: 352676
		if(ui!=null){
			ui.getButtonPanel().getBtnSubmit().setEnabled(false);
		}
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
		//从其他费用里面去除送货费子项
		Common.deleteDeliverFee(changeDetailList);
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
	
	public JLabel getLblNewLabel() {
		return lblNewLabel;
	}

	public void setLblNewLabel(JLabel lblNewLabel) {
		this.lblNewLabel = lblNewLabel;
	}

	public JLabel getLblNewLabel1() {
		return lblNewLabel1;
	}

	public void setLblNewLabel1(JLabel lblNewLabel1) {
		this.lblNewLabel1 = lblNewLabel1;
	}
	
}
