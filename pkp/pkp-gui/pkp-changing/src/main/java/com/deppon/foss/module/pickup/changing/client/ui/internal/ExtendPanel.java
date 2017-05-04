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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changing/client/ui/internal/ExtendPanel.java
 * 
 * FILE NAME        	: ExtendPanel.java
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

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import com.deppon.foss.framework.client.commons.binding.validation.annotations.NotNull;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.core.binding.annotation.Bind;
import com.deppon.foss.framework.client.core.binding.annotation.BindingArg;
import com.deppon.foss.framework.client.core.binding.annotation.BindingArgs;
import com.deppon.foss.framework.client.widget.validatewidget.JTextFieldValidate;
import com.deppon.foss.module.mainframe.client.ui.JDTitledBorder;
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
public class ExtendPanel extends JPanel {
	/**
	 * 国际化对象
	 */
	private static final II18n i18n = I18nManager.getI18n(ExtendPanel.class);

	/**
	 * FIELD NAME
	 */
	private static final String FIELDNAME = "fieldName";


	/**
	 * 10
	 */
	private static final int TEN = 10;

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 7604383749076483167L;

	/**
	 * 计费类型
	 */
	@Bind("billingType")
	@NotNull
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "计费类型") })
	private JComboBox combBillingType;

	/**
	 * 计费重量
	 */
	@Bind("billWeight")
	@NotNull
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "计费重量") })
	private JTextFieldValidate txtBillWeight;

	/**
	 * 费率
	 */
	@Bind("unitPrice")
	@NotNull
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "费率") })
	private JTextFieldValidate txtUnitPrice;

	/**
	 * 公布价运费
	 */
	@Bind("transportFee")
	@NotNull
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "公布价运费") })
	private JTextFieldValidate txtTransportFee;

	/**
	 * 保价声明
	 */
	@Bind("insuranceAmount")
	@NotNull
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "保价声明") })
	private JTextFieldValidate txtInsuranceAmount;

	/**
	 * 保价费率
	 */
	@Bind("insuranceRate")
	@NotNull
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "保价费率") })
	private JTextFieldValidate txtInsuranceRate;

	/**
	 * 保价费
	 */
	@Bind("insuranceFee")
	@NotNull
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "保价费") })
	private JTextFieldValidate txtInsuranceFee;

	/**
	 * 代收金额
	 */
	@Bind("codAmount")
	@NotNull
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "代收金额") })
	private JTextFieldValidate txtCodAmount;

	/**
	 * 代收费率
	 */
	@Bind("codRate")
	@NotNull
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "代收费率") })
	private JTextFieldValidate txtCodRate;

	/**
	 * 代收手续费
	 */
	@Bind("codFee")
	@NotNull
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "代收手续费") })
	private JTextFieldValidate txtCodFee;

	/**
	 * 送货费
	 */
	@Bind("deliveryGoodsFee")
	@NotNull
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "送货费") })
	private JTextFieldValidate txtDeliveryGoodsFee;

	/**
	 * 包装费
	 */
	@Bind("packageFee")
	@NotNull
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "包装费") })
	private JTextFieldValidate txtPackageFee;

	/**
	 * 其他费用合计
	 */
	@Bind("otherFee")
	@NotNull
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "其他费用合计") })
	private JTextFieldValidate txtOtherFee;

	/**
	 * 优惠费用
	 */
	@Bind("promotionsFee")
	@NotNull
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "优惠费用") })
	private JTextFieldValidate txtPromotionsFee;

	/**
	 * 费用合计
	 */
	@Bind("totalFee")
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "费用合计") })
	private JTextFieldValidate txtTotalFee;

	private DefaultComboBoxModel billingTypeModel;
	
	@Bind("otherTotle")
	private JTextFieldValidate otherTotleTextField=new JTextFieldValidate();
	
	@Bind("packingTotle")
	private JTextFieldValidate packingTotleTextField=new JTextFieldValidate();

	public ExtendPanel() {
		setBorder(new JDTitledBorder(i18n.get("foss.gui.changing.extendPanel.title")));

		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,}));

		//计费类型
		JLabel lblBillingType = new JLabel(i18n.get("foss.gui.changing.extendPanel.billingType.label")+"：");
		add(lblBillingType, "2, 2, right, default");

		combBillingType = new JComboBox();
		billingTypeModel = (DefaultComboBoxModel) combBillingType.getModel();
		combBillingType.setEnabled(false);
		add(combBillingType, "4, 2, 3, 1, fill, default");

		//计费重量
		JLabel lblBillWeight = new JLabel(i18n.get("foss.gui.changing.extendPanel.billWeight.label")+"：");
		add(lblBillWeight, "2, 4, right, default");

		txtBillWeight = new JTextFieldValidate();
		txtBillWeight.setEnabled(false);
		add(txtBillWeight, "4, 4, 3, 1, fill, default");
		txtBillWeight.setColumns(TEN);

		//费率
		JLabel lblUnitPrice = new JLabel(i18n.get("foss.gui.changing.extendPanel.unitPrice.label")+"：");
		add(lblUnitPrice, "2, 6, right, default");

		txtUnitPrice = new JTextFieldValidate();
		txtUnitPrice.setEnabled(false);
		add(txtUnitPrice, "4, 6, 3, 1, fill, default");
		txtUnitPrice.setColumns(TEN);

		//运费
		JLabel lblTransportFee = new JLabel(i18n.get("foss.gui.changing.extendPanel.transportFee.label")+"：");
		add(lblTransportFee, "2, 8, right, default");

		txtTransportFee = new JTextFieldValidate();
		txtTransportFee.setEnabled(false);
		add(txtTransportFee, "4, 8, 3, 1, fill, default");
		txtTransportFee.setColumns(TEN);

		JSeparator separator = new JSeparator();
		add(separator, "2, 10, 3, 1");

		//保价申明价值
		JLabel lblInsuranceAmount = new JLabel(i18n.get("foss.gui.changing.extendPanel.insuranceAmount.label")+"：");
		add(lblInsuranceAmount, "2, 12, right, default");

		txtInsuranceAmount = new JTextFieldValidate();
		txtInsuranceAmount.setEnabled(false);
		add(txtInsuranceAmount, "4, 12, 3, 1, fill, default");
		txtInsuranceAmount.setColumns(TEN);

		//保价费率
		JLabel lblInsuranceRate = new JLabel(i18n.get("foss.gui.changing.extendPanel.insuranceRate.label")+"：");
		add(lblInsuranceRate, "2, 14, right, default");

		txtInsuranceRate = new JTextFieldValidate();
		txtInsuranceRate.setEnabled(false);
		add(txtInsuranceRate, "4, 14, 2, 1, fill, default");
		txtInsuranceRate.setColumns(TEN);
		
		JLabel label = new JLabel("‰");
		add(label, "6, 14");

		//保价费
		JLabel lblInsuranceFee = new JLabel(i18n.get("foss.gui.changing.extendPanel.insuranceFee.label")+"：");
		add(lblInsuranceFee, "2, 16, right, default");

		txtInsuranceFee = new JTextFieldValidate();
		txtInsuranceFee.setEnabled(false);
		add(txtInsuranceFee, "4, 16, 3, 1, fill, default");
		txtInsuranceFee.setColumns(TEN);

		JSeparator separator1 = new JSeparator();
		add(separator1, "2, 18, 3, 1");

		//代收货款
		JLabel lblCodAmount = new JLabel(i18n.get("foss.gui.changing.extendPanel.codAmount.label")+"：");
		add(lblCodAmount, "2, 20, right, default");

		txtCodAmount = new JTextFieldValidate();
		txtCodAmount.setEnabled(false);
		add(txtCodAmount, "4, 20, 3, 1, fill, default");
		txtCodAmount.setColumns(TEN);

		//代收费率
		JLabel lblCodRate = new JLabel(i18n.get("foss.gui.changing.extendPanel.codRate.label")+"：");
		add(lblCodRate, "2, 22, right, default");

		txtCodRate = new JTextFieldValidate();
		txtCodRate.setEnabled(false);
		add(txtCodRate, "4, 22, 2, 1, fill, default");
		txtCodRate.setColumns(TEN);
		
		JLabel label1 = new JLabel("‰");
		add(label1, "6, 22");

		//代收手续费
		JLabel lblCodFee = new JLabel(i18n.get("foss.gui.changing.extendPanel.codFee.label")+"：");
		add(lblCodFee, "2, 24, right, default");

		txtCodFee = new JTextFieldValidate();
		txtCodFee.setEnabled(false);
		add(txtCodFee, "4, 24, 3, 1, fill, default");
		txtCodFee.setColumns(TEN);

		JSeparator separator2 = new JSeparator();
		add(separator2, "2, 26, 3, 1");
		
		otherTotleTextField.setVisible(false);
		packingTotleTextField.setVisible(false);

		//包装费
		JLabel lblPackageFee = new JLabel(i18n.get("foss.gui.changing.extendPanel.packageFee.label")+"：");
		add(lblPackageFee, "2, 28, right, default");

		txtPackageFee = new JTextFieldValidate();
		txtPackageFee.setEnabled(false);
		add(txtPackageFee, "4, 28, 3, 1, fill, default");
		txtPackageFee.setColumns(TEN);

		//送货费
		JLabel lblDeliveryGoodsFee = new JLabel(i18n.get("foss.gui.changing.extendPanel.deliveryGoodsFee.label")+"：");
		add(lblDeliveryGoodsFee, "2, 30, right, default");

		txtDeliveryGoodsFee = new JTextFieldValidate();
		txtDeliveryGoodsFee.setEnabled(false);
		add(txtDeliveryGoodsFee, "4, 30, 3, 1, fill, default");
		txtDeliveryGoodsFee.setColumns(TEN);

		//其他费用
		JLabel lblOtherFee = new JLabel(i18n.get("foss.gui.changing.extendPanel.otherFee.label")+"：");
		add(lblOtherFee, "2, 32, right, default");

		txtOtherFee = new JTextFieldValidate();
		txtOtherFee.setEnabled(false);
		add(txtOtherFee, "4, 32, 3, 1, fill, default");
		txtOtherFee.setColumns(TEN);

		JSeparator separator3 = new JSeparator();
		add(separator3, "2, 34, 3, 1");

		//优惠费用
		JLabel lblPromotionsFee = new JLabel(i18n.get("foss.gui.changing.extendPanel.promotionsFee.label")+"：");
		add(lblPromotionsFee, "2, 36, right, default");

		txtPromotionsFee = new JTextFieldValidate();
		txtPromotionsFee.setEnabled(false);
		add(txtPromotionsFee, "4, 36, 3, 1, fill, default");
		txtPromotionsFee.setColumns(TEN);

		//费用合计
		JLabel lblTotalFee = new JLabel(i18n.get("foss.gui.changing.extendPanel.totalFee.label")+"：");
		add(lblTotalFee, "2, 38, right, default");

		txtTotalFee = new JTextFieldValidate();
		txtTotalFee.setEnabled(false);
		add(txtTotalFee, "4, 38, 3, 1, fill, default");
		txtTotalFee.setColumns(TEN);
	}

	/**
	 * 
	 * 计费类型get方法 
	 * @author foss-gengzhe
	 * @date 2012-12-25 上午10:48:41
	 * @return
	 * @see
	 */
	public JComboBox getCombBillingType() {
		return combBillingType;
	}

	public DefaultComboBoxModel getBillingTypeModel() {
		return billingTypeModel;
	}

	/**
	 * 
	 * 计费重量get方法
	 * @author foss-gengzhe
	 * @date 2012-12-25 上午10:48:41
	 * @return
	 * @see
	 */
	public JTextFieldValidate getTxtBillWeight() {
		return txtBillWeight;
	}

	/**
	 * 
	 * 费率get方法 
	 * @author foss-gengzhe
	 * @date 2012-12-25 上午10:48:41
	 * @return
	 * @see
	 */
	public JTextFieldValidate getTxtUnitPrice() {
		return txtUnitPrice;
	}

	/**
	 * 
	 * 公布价运费get方法
	 * @author foss-gengzhe
	 * @date 2012-12-25 上午10:48:41
	 * @return
	 * @see
	 */
	public JTextFieldValidate getTxtTransportFee() {
		return txtTransportFee;
	}

	/**
	 * 
	 * 保价声明get方法 
	 * @author foss-gengzhe
	 * @date 2012-12-25 上午10:48:41
	 * @return
	 * @see
	 */
	public JTextFieldValidate getTxtInsuranceAmount() {
		return txtInsuranceAmount;
	}

	/**
	 * 
	 * 保价费率get方法 
	 * @author foss-gengzhe
	 * @date 2012-12-25 上午10:48:41
	 * @return
	 * @see
	 */
	public JTextFieldValidate getTxtInsuranceRate() {
		return txtInsuranceRate;
	}

	/**
	 * 
	 * 保价费get方法 
	 * @author foss-gengzhe
	 * @date 2012-12-25 上午10:48:41
	 * @return
	 * @see
	 */
	public JTextFieldValidate getTxtInsuranceFee() {
		return txtInsuranceFee;
	}

	/**
	 * 
	 * 代收金额get方法
	 * @author foss-gengzhe
	 * @date 2012-12-25 上午10:48:41
	 * @return
	 * @see
	 */
	public JTextFieldValidate getTxtCodAmount() {
		return txtCodAmount;
	}

	/**
	 * 
	 * 代收费率get方法
	 * @author foss-gengzhe
	 * @date 2012-12-25 上午10:48:41
	 * @return
	 * @see
	 */
	public JTextFieldValidate getTxtCodRate() {
		return txtCodRate;
	}

	/**
	 * 
	 * 代收手续费get方法 
	 * @author foss-gengzhe
	 * @date 2012-12-25 上午10:48:41
	 * @return
	 * @see
	 */
	public JTextFieldValidate getTxtCodFee() {
		return txtCodFee;
	}

	/**
	 * 
	 * 送货费get方法 
	 * @author foss-gengzhe
	 * @date 2012-12-25 上午10:48:41
	 * @return
	 * @see
	 */
	public JTextFieldValidate getTxtDeliveryGoodsFee() {
		return txtDeliveryGoodsFee;
	}

	/**
	 * 
	 * 包装费get方法 
	 * @author foss-gengzhe
	 * @date 2012-12-25 上午10:48:41
	 * @return
	 * @see
	 */
	public JTextFieldValidate getTxtPackageFee() {
		return txtPackageFee;
	}

	/**
	 * 
	 * 其他费用合计get方法 
	 * @author foss-gengzhe
	 * @date 2012-12-25 上午10:48:41
	 * @return
	 * @see
	 */
	public JTextFieldValidate getTxtOtherFee() {
		return txtOtherFee;
	}

	/**
	 * 
	 * 优惠费用get方法
	 * @author foss-gengzhe
	 * @date 2012-12-25 上午10:48:35
	 * @return
	 * @see
	 */
	public JTextFieldValidate getTxtPromotionsFee() {
		return txtPromotionsFee;
	}

	/**
	 * 
	 * 费用合计get方法 
	 * @author foss-gengzhe
	 * @date 2012-12-25 上午10:48:31
	 * @return
	 * @see
	 */
	public JTextFieldValidate getTxtTotalFee() {
		return txtTotalFee;
	}

}