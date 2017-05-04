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
 * PROJECT NAME	: pkp-creating
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/ui/editui/BillingPayPanel.java
 * 
 * FILE NAME        	: BillingPayPanel.java
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
package com.deppon.foss.module.pickup.creating.client.ui.editui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.apache.commons.lang.StringUtils;

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
import com.deppon.foss.module.pickup.common.client.utils.BZPartnersJudge;
import com.deppon.foss.module.pickup.common.client.utils.NumberDocument;
import com.deppon.foss.module.pickup.common.client.utils.NumberDocumentUtils;
import com.deppon.foss.module.pickup.common.client.utils.PackageNumberDocument;
import com.deppon.foss.module.pickup.creating.client.action.WaybillSubmitAction;
import com.deppon.foss.module.pickup.waybill.shared.define.IconConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;


/**
 * 
 * (付款信息面板)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:Administrator,date:2012-10-17 上午11:15:34,
 * </p>
 * 
 * @author 025000-FOSS-helong
 * @date 2012-10-17 上午11:15:34
 * @since
 * @version
 */
@ContainerSeq(seq = 8)
public class BillingPayPanel extends JPanel {

	/**
	 * 14
	 */
	private static final int FOURTEEN = 14;

	private static final int NIVE = 9;

	private static final int FIVE = 5;
	/**
	 * 10 for column count
	 */
	private static final int TEN = 10;
	
	private static final int SEVEN = 7;

	/**
	 * fieldname
	 */
	private static final String FIELDNAME = "fieldName";

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;

	private static final int NUM_213 = 213;

	private static final int FOUR = 4;

	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(BillingPayPanel.class);
	private String pictureWaybillType ;
	/**
	 * 包装费
	 */
	@Bind("packageFee")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "包装费") })
	@FocusSeq(seq = 1)
	private JTextFieldValidate txtPackCharge;

	/**
	 * 接货费
	 */
	@Bind("pickupFee")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "接货费") })
	@FocusSeq(seq = 2)
	private JTextFieldValidate txtPickUpCharge;

	/**
	 * 送货费
	 */
	@Bind("deliveryGoodsFee")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "送货费") })
	@FocusSeq(seq = 3)
	private JTextFieldValidate txtDeliveryCharge;

	/**
	 * 保价费
	 */
	@Bind("supportFee")
	@BindingArgs({@BindingArg(name = FIELDNAME, value = "保价费")})
	@FocusSeq(seq = 4)
	private JTextFieldValidate txtSupportFee ;
	
	@Bind("collectingFee")
	@BindingArgs({@BindingArg(name = FIELDNAME, value = "代收手续费")})
	@FocusSeq(seq = 5)
	private JTextFieldValidate txtCollectingFee ;
	

	/**
	 * 预付金额
	 */
	@Bind("prePayAmount")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "预付金额") })
	@FocusSeq(seq = 4)
	private JTextFieldValidate txtAdvancesMoney;

	/**
	 * 到付金额
	 */
	@Bind("toPayAmount")
	@FocusSeq(seq = 5)
	private JTextFieldValidate txtArrivePayment;

	/**
	 * 提交
	 */
	@ButtonAction(icon = IconConstants.SUBMIT, shortcutKey = "", type = WaybillSubmitAction.class)
	JButton btnSubmit;
	
	//提交并进行下一单	
	@ButtonAction(icon = IconConstants.SUBMIT, shortcutKey = "", type = WaybillSubmitAction.class)
	@FocusSeq(seq = 6)
	JButton btnSubmitAndNextSingle;
	/**
	 * 增值服务费
	 */
	@Bind("valueAddFee")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "增值服务费") })
	private JTextFieldValidate txtIncrementCharge;

	/**
	 * 优惠合计
	 */
	@Bind("promotionsFee")
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "优惠合计") })
	private JTextFieldValidate txtPromotionTotal;

	/**
	 * 总费用
	 */
	@Bind("totalFee")
	@NotNull()
	@BindingArgs({ @BindingArg(name = FIELDNAME, value = "总费用") })
	private JTextFieldValidate txtTotalCharge;

	/**
	 * 手写现付金额
	 */
	@Bind("handWriteMoney")
	private JTextFieldValidate txtHandWriteMoney;

	// 支付子面板
	public BillingPayBelongPanel billingPayBelongPanel;
	
	/**
	 *控制文本框只能输入数字
	 *@author:218371-foss-zhaoyanjun
	 *@date:2015-01-23上午10:25
	 */
	PackageNumberDocument number=new PackageNumberDocument(NumberConstants.NUMBER_200);
	
	/**
	 * 增加交易流水号文本框
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-01-22下午18:53
	 */
	@Bind("transactionSerialNumber")
	private JTextFieldValidate transactionSerialNumber;
	
	/**
	 * Create the panel.
	 */
	public BillingPayPanel(String pictureWaybillType) {
		this.pictureWaybillType = pictureWaybillType;
		init();

	}

	/**
	 * 
	 * <p>
	 * (初始化)
	 * </p>
	 * 
	 * @author helong
	 * @date 2012-10-16 上午09:40:13
	 * @see
	 */
	private void init() {
		setBorder(new CompoundBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "\u8D39\u7528\u660E\u7EC6", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, NumberConstants.NUMBER_70,
				NUM_213)), new EmptyBorder(0, 0, FOUR, 0)));
		setLayout(new FormLayout(new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(31dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(29dlu;default)"), FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(50dlu;default)"), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, },
				new RowSpec[] { FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, }));

		billingPayBelongPanel = new BillingPayBelongPanel();
		add(billingPayBelongPanel, "2, 1, 7, 1, fill, center");
		NumberDocument advancesMoney = new NumberDocument(TEN);
		NumberDocument packCharge = new NumberDocument(SEVEN);
		NumberDocument pickUpCharge = new NumberDocument(FIVE);
		NumberDocument deliverCharge = new NumberDocument(FIVE);
		NumberDocument supportCharge = new NumberDocument(FIVE);
		NumberDocument collectingCharge = new NumberDocument(FIVE);

		JLabel lblNewLabel = new JLabel(i18n.get("foss.gui.creating.billingPayPanel.lblNewLabel.label"));
		add(lblNewLabel, "12, 1, right, default");

		txtPackCharge = new JTextFieldValidate();
		add(txtPackCharge, "14, 1, fill, default");
		txtPackCharge.setColumns(TEN);		
		txtPackCharge.setDocument(packCharge);
		/**
		 * 将此文本框设置为不可编辑
		 * @author:218371-foss-zhaoyanjun
		 * @date:2014-12-2
		 */
		txtPackCharge.setEnabled(false);

		JLabel lblNewLabel1 = new JLabel(i18n.get("foss.gui.creating.billingPayPanel.lblNewLabel1.label"));
//		add(lblNewLabel_1, "16, 1, right, default");

		txtPickUpCharge = new JTextFieldValidate();
//		add(txtPickUpCharge, "18, 1, fill, default");
		txtPickUpCharge.setDocument(pickUpCharge);
		txtPickUpCharge.setColumns(TEN);

		txtPickUpCharge.setColumns(NumberConstants.NUMBER_8);


		JLabel lblNewLabel2 = new JLabel(i18n.get("foss.gui.creating.billingPayPanel.lblNewLabel2.label"));
//		add(lblNewLabel_2, "20, 1, right, default");

		txtDeliveryCharge = new JTextFieldValidate();
		txtDeliveryCharge.setEnabled(false);

		add(txtDeliveryCharge, "22, 1, fill, default");
		txtDeliveryCharge.setColumns(TEN);

//		add(txtDeliveryCharge, "22, 1, fill, default");
		txtDeliveryCharge.setColumns(NumberConstants.NUMBER_8);
		txtDeliveryCharge.setDocument(deliverCharge);
		
		JPanel jPanel = new JPanel(new FlowLayout());
		jPanel.add(lblNewLabel1);
		jPanel.add(txtPickUpCharge);
		jPanel.add(lblNewLabel2);
		jPanel.add(txtDeliveryCharge);
		add(jPanel, "16, 1, 4, 1,right, default");
		
		//保价费
		JLabel lblNewLabel3 = new JLabel(i18n.get("foss.gui.creating.billingPayPanel.lblNewLabel3.label"));
//		add(lblNewLabel_3, "20, 1, right, default");
		
		txtSupportFee = new JTextFieldValidate();
		txtSupportFee.setColumns(NumberConstants.NUMBER_8);
		txtSupportFee.setDocument(supportCharge);
//		add(txtSupportFee, "25, 1, fill, default");
		txtSupportFee.setEnabled(false);
		
		//代收手续费
		JLabel lblNewLabel4 = new JLabel(i18n.get("foss.gui.creating.billingPayPanel.lblNewLabel4.label"));
//		add(lblNewLabel_4, "27, 1, right, default");
		txtCollectingFee = new JTextFieldValidate();
		txtCollectingFee.setColumns(NumberConstants.NUMBER_8);
		txtCollectingFee.setDocument(collectingCharge);
//		add(txtCollectingFee, "29, 1, fill, default");
		txtCollectingFee.setEnabled(false);
		
		JPanel jPanel2 = new JPanel(new FlowLayout());
		jPanel2.add(lblNewLabel3);
		jPanel2.add(txtSupportFee);
		jPanel2.add(lblNewLabel4);
		jPanel2.add(txtCollectingFee);
		//合伙人项目 新增
		if(BZPartnersJudge.IS_PARTENER){	
			add(jPanel2, "20, 1, 4, 1, right, default");
		}
		
		// 增值服务费
		JLabel label3 = new JLabel(i18n.get("foss.gui.creating.billingPayPanel.incrementCharge.label") + "：");
		add(label3, "2, 3, right, default");

		txtIncrementCharge = new JTextFieldValidate();
		add(txtIncrementCharge, "3, 3, 2, 1, fill, default");
		txtIncrementCharge.setColumns(TEN);
		txtIncrementCharge.setEnabled(false);

		// 优惠合计
		JLabel label5 = new JLabel(i18n.get("foss.gui.creating.billingPayPanel.promotionTotal.label") + "：");

		txtPromotionTotal = new JTextFieldValidate();
		txtPromotionTotal.setColumns(TEN);
		txtPromotionTotal.setEnabled(false);
		
		if(!BZPartnersJudge.IS_PARTENER || !BZPartnersJudge.PRIVILEGE_TOTAL){
			add(label5, "6, 3, left, default");
			add(txtPromotionTotal, "7, 3, 2, 1, fill, default");
		}
 
		// 到付金额
		JLabel label4 = new JLabel(i18n.get("foss.gui.creating.billingPayPanel.advancesMoney.label") + "：");
		add(label4, "12, 3, right, default");

		txtAdvancesMoney = new JTextFieldValidate();
		add(txtAdvancesMoney, "14, 3, fill, default");
		txtAdvancesMoney.setDocument(advancesMoney);
		txtAdvancesMoney.setColumns(TEN);
		txtAdvancesMoney.setEnabled(false);
		

		// 到付金额
		JLabel label6 = new JLabel(i18n.get("foss.gui.creating.billingPayPanel.arrivePayment.label") + "：");
		add(label6, "16, 3, left, default");

		txtArrivePayment = new JTextFieldValidate();
		add(txtArrivePayment, "18, 3, fill, default");
		txtArrivePayment.setColumns(TEN);
		NumberDocumentUtils arrivePayment = new NumberDocumentUtils(NIVE);
//		NumberDocument arrivePayment = new NumberDocument(TEN);
		txtArrivePayment.setDocument(arrivePayment);
		txtArrivePayment.setEnabled(false);

		// ===========删除“手写现付金额”文本框/lianhe/2016年12月25日/start=======
		// 手写现付金额
		JLabel label9 = new JLabel(i18n.get("foss.gui.creating.billingPayPanel.handWriteMoney.label") + "：");
//		add(label9, "20, 3, right, default");
		txtHandWriteMoney = new JTextFieldValidate();
//		add(txtHandWriteMoney, "22, 3, fill, default");
		// ===========删除“手写现付金额”文本框/lianhe/2016年12月25日/end=======
		txtHandWriteMoney.setColumns(TEN);
		NumberDocumentUtils document = new NumberDocumentUtils(NIVE);
//		NumberDocument document = new NumberDocument(TEN);
		txtHandWriteMoney.setDocument(document);
		txtHandWriteMoney.setEnabled(false);

		JPanel panel = new JPanel();
		panel.setBorder(new JDTitledBorder());
		add(panel, "2, 5, 21, 1");
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,}));

		// 总运费
		JLabel label8 = new JLabel(i18n.get("foss.gui.creating.billingPayPanel.totalCharge.label") + "：");
		label8.setFont(new Font("宋体", Font.BOLD, FOURTEEN));
		panel.add(label8, "2, 2, right, default");

		txtTotalCharge = new JTextFieldValidate();
		panel.add(txtTotalCharge, "4, 2, 3, 1, fill, default");
		txtTotalCharge.setColumns(TEN);
		txtTotalCharge.setEnabled(false);
		
		/**
		 * 增加交易流水号标签
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-01-22下午18:53
		 */
		JLabel label10 = new JLabel("交易流水号：");
		label10.setFont(new Font("宋体", Font.BOLD, NumberConstants.NUMBER_12));
		panel.add(label10, "8, 2, right, default");
		
		/**
		 * 增加交易流水号文本框
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-01-22下午18:53
		 */
		transactionSerialNumber = new JTextFieldValidate();
		transactionSerialNumber.setEnabled(false);
		panel.add(transactionSerialNumber, "10, 2, 3, 1, fill, default");
		transactionSerialNumber.setColumns(TEN);
		transactionSerialNumber.setDocument(number);

		//提交  和  提交并进行下一单   互换位置 
		btnSubmit = new JButton(i18n.get("foss.gui.creating.buttonPanel.submit.label"));
		if(StringUtils.isNotBlank(this.pictureWaybillType) && 
				WaybillConstants.WAYBILL_PICTURE.equals(pictureWaybillType)){
			panel.add(btnSubmit, "20, 2");
			btnSubmit.setEnabled(true);
			btnSubmitAndNextSingle = new JButton(i18n.get("foss.gui.creating.buttonPanel.btnSubmitAndNextSingle.label"));
			panel.add(btnSubmitAndNextSingle,"16, 2");
			btnSubmitAndNextSingle.setEnabled(true);
			txtPickUpCharge.setEnabled(true);
		}else{
			panel.add(btnSubmit, "20, 2");
			btnSubmit.setEnabled(false);
			txtPickUpCharge.setEnabled(false);
		}		
	}

	/**
	 * 
	 * 提交
	 * @author 025000-FOSS-helong
	 * @date 2013-4-16 下午02:28:50
	 * @return
	 */
	public JButton getBtnSubmit() {
		return btnSubmit;
	}

	/**
	 * getTxtHandWriteMoney
	 * 
	 * @return JTextFieldValidate
	 */
	public JTextFieldValidate getTxtHandWriteMoney() {
		return txtHandWriteMoney;
	}

	/**
	 * 
	 * 获得预付金额控件对象
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-28 上午11:25:13
	 * @return
	 */
	public JTextFieldValidate getTxtAdvancesMoney() {
		return txtAdvancesMoney;
	}

	/**
	 * getTxtPickUpCharge
	 * 
	 * @return
	 */
	public JTextFieldValidate getTxtPickUpCharge() {
		return txtPickUpCharge;
	}

	/**
	 * getTxtDeliveryCharge
	 * 
	 * @return
	 */
	public JTextFieldValidate getTxtDeliveryCharge() {
		return txtDeliveryCharge;
	}

	/**
	 * getTxtPackCharge
	 * 
	 * @return
	 */
	public JTextFieldValidate getTxtPackCharge() {
		return txtPackCharge;
	}

	/**
	 * 
	 * 获得到付金额控件对象
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-28 上午11:25:30
	 * @return
	 */
	public JTextFieldValidate getTxtArrivePayment() {
		return txtArrivePayment;
	}

	public JTextFieldValidate getTxtIncrementCharge() {
		return txtIncrementCharge;
	}

	public JTextFieldValidate getTxtPromotionTotal() {
		return txtPromotionTotal;
	}

	public JTextFieldValidate getTxtTotalCharge() {
		return txtTotalCharge;
	}

	public JButton getBtnSubmitAndNextSingle() {
		return btnSubmitAndNextSingle;
	}
	public void setBtnSubmitAndNextSingle(JButton btnSubmitAndNextSingle) {
		this.btnSubmitAndNextSingle = btnSubmitAndNextSingle;
	}
	
	public JTextFieldValidate getTransactionSerialNumber() {
		return transactionSerialNumber;
	}

	public void setTransactionSerialNumber(
			JTextFieldValidate transactionSerialNumber) {
		this.transactionSerialNumber = transactionSerialNumber;
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
}