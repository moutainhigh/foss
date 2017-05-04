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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/ui/internal/BillingPayPanel.java
 * 
 * FILE NAME        	:java
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

import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
import com.deppon.foss.framework.client.widget.validatewidget.JTextFieldValidate;
import com.deppon.foss.module.mainframe.client.ui.JDTitledBorder;
import com.deppon.foss.module.pickup.changingexp.client.action.CalculateAction;
import com.deppon.foss.module.pickup.common.client.utils.FloatDocument;
import com.deppon.foss.module.pickup.common.client.utils.NumberDocument;
import com.deppon.foss.module.pickup.common.client.utils.PackageNumberDocument;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * 计费付款信息面板
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:102246-foss-shaohongliang,date:2012-10-17
 * 上午9:22:05,content:
 * </p>
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-10-17 上午9:22:05
 * @since
 * @version
 */
@ContainerSeq(seq=9)
public class BillingPayPanel extends JPanel {

	/**
	 * 10 for column count
	 */
	private static final int TEN = 10;

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = -6879340503489004042L;

	/**
	 * 国际化
	 */
	private static II18n i18n = I18nManager
			.getI18n(BillingPayPanel.class);

	/**
	 * 付款方式
	 */
	@Bind("paidMethod")
	@NotNull
	@BindingArgs({ @BindingArg(name = "fieldName", value = "付款方式") })
	@FocusSeq(seq=5)
	private JComboBox combPaymentMode;

	/**
	 * 到付金额
	 */
	@Bind("toPayAmount")
	@NotNull
	@BindingArgs({ @BindingArg(name = "fieldName", value = "到付金额") })
	@FocusSeq(seq=6)
	private JTextFieldValidate txtArrivePayment;

	/**
	 * 现付金额
	 */
	@Bind("prePayAmount")
	@NotNull
	@BindingArgs({ @BindingArg(name = "fieldName", value = "现付金额") })
	@FocusSeq(seq=7)
	private JTextFieldValidate txtCashpayment;

	/**
	 * 增值服务费
	 */
	@Bind("valueAddFee")
	@NotNull
	@BindingArgs({ @BindingArg(name = "fieldName", value = "增值服务费") })
	@FocusSeq(seq=2)
	private JTextFieldValidate txtIncrementCharge;

	/**
	 * 优惠费用
	 */
	@Bind("promotionsFee")
	@NotNull
	@BindingArgs({ @BindingArg(name = "fieldName", value = "优惠费用") })
	@FocusSeq(seq=3)
	private JTextFieldValidate txtPromotionCharge;
	

	/**
	 * 历史运单调整费用
	 */
	@Bind("adjustFee")
	private JTextFieldValidate txtAdjustCharge;

	/**
	 * 费用合计
	 */
	@Bind("totalFee")
	@NotNull
	@BindingArgs({ @BindingArg(name = "fieldName", value = "费用合计") })
	@FocusSeq(seq=4)
	private JTextFieldValidate txtTotalCharge;

	/**
	 * 预付费保密
	 */
	@Bind("secretPrepaid")
	@FocusSeq(seq=8)
	private JCheckBox chbSecrecy;

	/**
	 * 付款方式的Model
	 */
	private DefaultComboBoxModel paymentModeModel;

	private JPanel panel;

	/**
	 * 计算总运费
	 */
	@ButtonAction(icon = "", shortcutKey = "F11", type = CalculateAction.class)
	@FocusSeq(seq=9)
	JButton btnCalculate;

	private JLabel lblNewLabel;
	
	//支付子面板
	public BillingPayBelongPanel billingPayBelongPanel;

	private JLabel lblAdjustCharge;
	
	private FormLayout formLayout;
	
	/**
	 * 增加交易流水号标签
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-01-24
	 */
	private JLabel label;
	
	/**
	 * 增加交易流水号文本框
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-01-24
	 */
	@Bind("transactionSerialNumber")
	private JTextFieldValidate transactionSerialNumber;
	
	/**
	 *控制文本框只能输入数字
	 *@author:218371-foss-zhaoyanjun
	 *@date:2015-01-23上午10:25
	 */
	PackageNumberDocument number=new PackageNumberDocument(NumberConstants.NUMBER_200);
	/**
	 * 
	 * BillingPayPanel
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午10:53:16
	 */
	public BillingPayPanel() {
		init();
	}

	/**
	 * 
	 * 布局初始化
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午10:53:24
	 */
	private void init() {
		setBorder(new JDTitledBorder(i18n.get("pickup.changingexp.billingPayPanel")));
		
		formLayout = new FormLayout(new ColumnSpec[] {
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
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,});
		setLayout(formLayout);
		
		

		billingPayBelongPanel = new BillingPayBelongPanel();
		add(billingPayBelongPanel, "2, 1, 3, 1, fill, default");

		JLabel lblIncrementCharge = new JLabel(
				i18n.get("pickup.changingexp.incrementCharge"));
		add(lblIncrementCharge, "6, 1, right, default");

		txtIncrementCharge = new JTextFieldValidate();
		add(txtIncrementCharge, "8, 1, fill, default");
		txtIncrementCharge.setDocument(new NumberDocument(TEN));
		txtIncrementCharge.setColumns(TEN);

		JLabel lblPromotionCharge = new JLabel(
				i18n.get("pickup.changingexp.promotionCharge"));
		add(lblPromotionCharge, "10, 1, right, default");

		txtPromotionCharge = new JTextFieldValidate();
		add(txtPromotionCharge, "12, 1, fill, default");
		//txtPromotionCharge.setDocument(new NumberDocument(TEN));
		txtPromotionCharge.setColumns(TEN);
		
		lblAdjustCharge = new JLabel(i18n.get("pickup.changingexp.adjustCharge")+"：");
		add(lblAdjustCharge, "14, 1, right, default");
		
		txtAdjustCharge = new JTextFieldValidate();
		add(txtAdjustCharge, "16, 1, fill, default");
		txtAdjustCharge.setColumns(TEN);

		JLabel lblTotalCharge = new JLabel(
				i18n.get("pickup.changingexp.totalCharge"));
		add(lblTotalCharge, "18, 1, right, default");

		txtTotalCharge = new JTextFieldValidate();
		add(txtTotalCharge, "20, 1, fill, default");
		txtTotalCharge.setDocument(new FloatDocument(TEN, 2));
		txtTotalCharge.setColumns(TEN);

		JLabel lblPaymentMode = new JLabel(
				i18n.get("pickup.changingexp.paymentMode"));
		add(lblPaymentMode, "2, 3, right, default");

		combPaymentMode = new JComboBox();
		paymentModeModel = (DefaultComboBoxModel) combPaymentMode.getModel();
		add(combPaymentMode, "4, 3, fill, default");
		
		/**
		 * 增加交易流水号标签
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-01-24
		 */
		label = new JLabel("交易流水号：");
		add(label, "6, 3, right, default");
		
		/**
		 * 增加交易流水号文本框
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-01-24
		 */
		transactionSerialNumber = new JTextFieldValidate();
		add(transactionSerialNumber, "8, 3, fill, default");
		transactionSerialNumber.setColumns(TEN);
		transactionSerialNumber.setEnabled(false);
		transactionSerialNumber.setDocument(number);

		JLabel lblArrivePayment = new JLabel(
				i18n.get("pickup.changingexp.arrivePayment"));
		add(lblArrivePayment, "10, 3, right, default");

		txtArrivePayment = new JTextFieldValidate();
		add(txtArrivePayment, "12, 3, fill, default");
		txtArrivePayment.setDocument(new FloatDocument(TEN, 2));
		txtArrivePayment.setColumns(TEN);

		JLabel lblCashPayment = new JLabel(
				i18n.get("pickup.changingexp.cashPayment"));
		add(lblCashPayment, "14, 3, right, default");

		txtCashpayment = new JTextFieldValidate();
		add(txtCashpayment, "16, 3, fill, default");
		txtCashpayment.setDocument(new FloatDocument(TEN, 2));
		txtCashpayment.setColumns(TEN);

		panel = new JPanel();
		add(panel, "18, 3, 3, 1, fill, fill");
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

		chbSecrecy = new JCheckBox(i18n.get("pickup.changingexp.secrecy"));
		panel.add(chbSecrecy);

		lblNewLabel = new JLabel("   ");
		panel.add(lblNewLabel);

		//计算总运费
		btnCalculate = new JButton(i18n.get("pickup.changingexp.calculateTotalFee"));
		panel.add(btnCalculate);
		
		clearComponent();
	}

	/**
	 * 
	 * 付款方式get方法
	 * 
	 * @author foss-gengzhe
	 * @date 2012-12-25 上午10:10:24
	 * @return
	 * @see
	 */
	public JComboBox getCombPaymentMode() {
		return combPaymentMode;
	}

	/**
	 * 
	 * 到付金额 get方法
	 * 
	 * @author foss-gengzhe
	 * @date 2012-12-25 上午10:10:46
	 * @return
	 * @see
	 */
	public JTextFieldValidate getTxtArrivePayment() {
		return txtArrivePayment;
	}

	/**
	 * 
	 * 现付金额 get方法
	 * 
	 * @author foss-gengzhe
	 * @date 2012-12-25 上午10:10:56
	 * @return
	 * @see
	 */
	public JTextFieldValidate getTxtCashpayment() {
		return txtCashpayment;
	}


	/**
	 * 
	 * 增值服务费 get方法
	 * 
	 * @author foss-gengzhe
	 * @date 2012-12-25 上午10:11:12
	 * @return
	 * @see
	 */
	public JTextFieldValidate getTxtIncrementCharge() {
		return txtIncrementCharge;
	}

	/**
	 * 
	 * 优惠费用 get方法
	 * 
	 * @author foss-gengzhe
	 * @date 2012-12-25 上午10:11:17
	 * @return
	 * @see
	 */
	public JTextFieldValidate getTxtPromotionCharge() {
		return txtPromotionCharge;
	}

	/**
	 * 
	 * 费用合计get方法
	 * 
	 * @author foss-gengzhe
	 * @date 2012-12-25 上午10:11:22
	 * @return
	 * @see
	 */
	public JTextFieldValidate getTxtTotalCharge() {
		return txtTotalCharge;
	}

	/**
	 * @return the chbSecrecy
	 */
	public JCheckBox getChbSecrecy() {
		return chbSecrecy;
	}

	/**
	 * @return the paymentModeModel
	 */
	public DefaultComboBoxModel getPaymentModeModel() {
		return paymentModeModel;
	}

	/**
	 * @return the btnCalculate
	 */
	public JButton getBtnCalculate() {
		return btnCalculate;
	}

	
	/**
	 * @return the billingPayBelongPanel
	 */
	public BillingPayBelongPanel getBillingPayBelongPanel() {
		return billingPayBelongPanel;
	}


	/**
	 * @return the txtAdvancesMoney
	 */
	public JTextFieldValidate getTxtAdvancesMoney() {
		return txtCashpayment;
	}

	
	/**
	 * @return the txtAdjustCharge
	 */
	public JTextField getTxtAdjustCharge() {
		return txtAdjustCharge;
	}

	
	/**
	 * @return the formLayout
	 */
	public FormLayout getFormLayout() {
		return formLayout;
	}

	public void fitComponent() {
		lblAdjustCharge.setVisible(true);
		txtAdjustCharge.setVisible(true);
	}

	public void clearComponent() {
		lblAdjustCharge.setVisible(false);
		txtAdjustCharge.setVisible(false);
	}
	
	public JTextFieldValidate getTransactionSerialNumber() {
		return transactionSerialNumber;
	}

	public void setTransactionSerialNumber(
			JTextFieldValidate transactionSerialNumber) {
		this.transactionSerialNumber = transactionSerialNumber;
	}
}