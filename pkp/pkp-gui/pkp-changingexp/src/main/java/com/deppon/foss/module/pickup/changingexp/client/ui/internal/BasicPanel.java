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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/ui/internal/BasicPanel.java
 * 
 * FILE NAME        	: BasicPanel.java
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

import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.deppon.foss.framework.client.commons.binding.validation.annotations.Int;
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
import com.deppon.foss.module.pickup.changingexp.client.action.QuerySalesDepartmentAction;
import com.deppon.foss.module.pickup.common.client.utils.BZPartnersJudge;
import com.deppon.foss.module.pickup.common.client.utils.LengthDocument;
import com.deppon.foss.module.pickup.common.client.utils.NumberDocument;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 * 运单基础信息面板
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:102246-foss-shaohongliang,date:2012-10-16
 * 上午8:59:16,
 * </p>
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-10-16 上午8:59:16
 * @since
 * @version
 */
@ContainerSeq(seq=2)
public class BasicPanel extends JPanel {

	/**
	 * 20
	 */
	private static final int TWENTY = 20;

	/**
	 * 9
	 */
	private static final int NINE = 9;

	/**
	 * 10
	 */
	private static final int TEN = 10;

	

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 5563669636618712206L;

	/**
	 *  国际化
	 */
	private static II18n i18n = I18nManager.getI18n(BasicPanel.class);

	/**
	 * 新的运单号
	 */
	@Bind("newWaybillNo")
	@Int
	@FocusSeq(seq=4)
	private JTextFieldValidate txtRfcWaybillNo;

	
	/**
	 * @return the txtRfcWaybillNo
	 */
	public JTextFieldValidate getTxtRfcWaybillNo() {
		return txtRfcWaybillNo;
	}

	/**
	 * 收货部门
	 */
	@Bind("receiveOrgName")
	@NotNull
	@BindingArgs({ @BindingArg(name = "fieldName", value = "收入部门") })
	@FocusSeq(seq=1)
	private JTextFieldValidate txtConsigneeDept;

	/**
	 * 是否变更单号
	 */
	@FocusSeq(seq=3)
	private JCheckBox cboRfcWaybillNo;

	/**
	 * 是否上门接货
	 */
	@Bind("pickupToDoor")
	@FocusSeq(seq=5)
	private JCheckBox cboReceiveModel;
	
	/** 
	 * 内部发货
	 */
	private JLabel internalDelivery;
	/**
	 * 内部发货类型
	 */
	@Bind("internalDeliveryType")
	public JComboBox internalDeliveryType;
	/**
	 * 工号
	 */
	@Bind("employeeNo")
	private JTextFieldValidate txtStaffNumber;
	/**
	 * 内部发货类型model
	 * @return
	 */
	private DefaultComboBoxModel internalDeliveryTypeModel;
	/**
	 * 查询按键
	 */
	@ButtonAction(icon="query.png", shortcutKey="", type=QuerySalesDepartmentAction.class)
	@FocusSeq(seq=2)
	private JButton btnSearch;
	private JPanel panel;
	//伙伴开单
	@Bind("partnerBilling")
	private JCheckBox partnerCheckBox;
	//伙伴名称
	@Bind("partnerName")
	private JTextFieldValidate partnerName;
	private JLabel partnerLabel;
	private JLabel phomeLabel;
	//伙伴手机号
	@Bind("partnerPhone")
	private JTextFieldValidate partnerPhone;
//	@Bind("specialOffer")
//	private JComboBox cboSpecialOffer;
//	private JLabel label;
//	private DefaultComboBoxModel cboSpecialOfferModel;
	/**
	 * Create the panel.
	 */
	public BasicPanel() {
		init();
	}

	/**
	 * 
	 * 初始化布局
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午10:51:31
	 */
	private void init() {
		setBorder(new JDTitledBorder(i18n.get("pickup.changingexp.basicPanel")));
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(47dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(25dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(22dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(28dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));

		JLabel lblConsigneeDept = new JLabel(i18n.get("pickup.changingexp.consigneeDept"));
		add(lblConsigneeDept, "2, 1, right, default");

		cboRfcWaybillNo = new JCheckBox(i18n.get("pickup.changingexp.rfcWaybillNo"));
		
				
				panel = new JPanel();
				add(panel, "4, 1, fill, fill");
				panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
				txtConsigneeDept = new JTextFieldValidate();
				panel.add(txtConsigneeDept);
				txtConsigneeDept.setColumns(TEN);
		
		//查询
		btnSearch = new JButton();
		btnSearch.setMargin(new Insets(-2, 1, -2, 1));
		panel.add(btnSearch);
		//删除复选框“伙伴开单”选项及伙伴名称、手机号(直接删除)
		partnerCheckBox = new JCheckBox("伙伴开单");
		//add(partnerCheckBox, "10, 1, 6, 1");
		add(cboRfcWaybillNo, "2, 3");
		

		txtRfcWaybillNo = new JTextFieldValidate();
		add(txtRfcWaybillNo, "4, 3, fill, default");
		txtRfcWaybillNo.setColumns(TWENTY);
		txtRfcWaybillNo.setDocument(new NumberDocument(TEN));

		cboReceiveModel = new JCheckBox(i18n.get("pickup.changingexp.receiveModel"));
		add(cboReceiveModel, "2, 5");
		
		internalDelivery = new JLabel(
				i18n.get("foss.gui.creating.basicPanel.internalDelivery.label"));
		

		internalDeliveryType = new JComboBox();
		internalDeliveryTypeModel = (DefaultComboBoxModel) internalDeliveryType.getModel();
		
		// 工号
		JLabel staffNo = new JLabel(
				i18n.get("foss.gui.creating.basicPanel.number.label"));
		
		txtStaffNumber = new JTextFieldValidate();
		LengthDocument document = new LengthDocument(6);
		// 内部发货
		if(!BZPartnersJudge.IS_PARTENER || !BZPartnersJudge.INTERNAL_DELIVERY_EXP){		
		  add(internalDelivery, "2, 7, left, default");
		  add(internalDeliveryType, "3, 7, 3, 1, fill, default");
		  add(staffNo, "10, 7, left, default");
		  add(txtStaffNumber, "12, 7, 5, 1, fill, default");
		  txtStaffNumber.setDocument(document);
		}
			partnerLabel = new JLabel("伙伴名称:");	
			partnerName = new JTextFieldValidate();
			partnerName.setColumns(10);
			phomeLabel = new JLabel("伙伴电话:");
			partnerPhone = new JTextFieldValidate();
			partnerPhone.setColumns(10);
			
		
		 

		//	add(partnerLabel, "2, 9, left, default");
		//	add(partnerName, "4, 9, 2, 1, fill, default");
		//	add(phomeLabel, "8, 9, 4, 1");
		//	add(partnerPhone, "12, 9, 5, 1, fill, default");
		
		
	
		
//		label = new JLabel("优惠活动：");
//		add(label, "2, 7, left, default");
		
	//	cboSpecialOffer = new JComboBox();
//		cboSpecialOffer.setEnabled(false);
//		cboSpecialOfferModel=(DefaultComboBoxModel) cboSpecialOffer.getModel();
//		add(cboSpecialOffer, "4, 7, fill, default");

}

//	    public DefaultComboBoxModel getCboSpecialOfferModel() {
//		  return cboSpecialOfferModel;
//	}

	/**
	 * 
	 * 查询按键get方法 
	 * @author foss-gengzhe
	 * @date 2012-12-25 上午10:05:01
	 * @return
	 * @see
	 */
	public JButton getBtnSearch() {
		return btnSearch;
	}

	/**
	 * 
	 * 收货部门get方法
	 * @author foss-gengzhe
	 * @date 2012-12-25 上午10:05:06
	 * @return
	 * @see
	 */
	public JTextFieldValidate getTxtConsigneeDept() {
		return txtConsigneeDept;
	}

	/**
	 * 
	 * 
	 * @author foss-gengzhe
	 * @date 2012-12-25 上午10:05:11
	 * @return
	 * @see
	 */
	public JCheckBox getCboRfcWaybillNo() {
		return cboRfcWaybillNo;
	}

	/**
	 * 
	 * 
	 * @author foss-gengzhe
	 * @date 2012-12-25 上午10:05:16
	 * @return
	 * @see
	 */
	public JCheckBox getCboReceiveModel() {
		return cboReceiveModel;
	}
	
	public JLabel getInternalDelivery() {
		return internalDelivery;
	}

	public void setInternalDelivery(JLabel internalDelivery) {
		this.internalDelivery = internalDelivery;
	}

	public JComboBox getInternalDeliveryType() {
		return internalDeliveryType;
	}

	public void setInternalDeliveryType(JComboBox internalDeliveryType) {
		this.internalDeliveryType = internalDeliveryType;
	}

	public JTextFieldValidate getTxtStaffNumber() {
		return txtStaffNumber;
	}

	public void setTxtStaffNumber(JTextFieldValidate txtStaffNumber) {
		this.txtStaffNumber = txtStaffNumber;
	}
	public DefaultComboBoxModel getInternalDeliveryTypeModel() {
		return internalDeliveryTypeModel;
	}

	public void setInternalDeliveryTypeModel(
			DefaultComboBoxModel internalDeliveryTypeModel) {
		this.internalDeliveryTypeModel = internalDeliveryTypeModel;
	}

	public JCheckBox getPartnerCheckBox() {
		return partnerCheckBox;
	}

	public void setPartnerCheckBox(JCheckBox partnerCheckBox) {
		this.partnerCheckBox = partnerCheckBox;
	}

	public JLabel getPartnerLabel() {
		return partnerLabel;
	}

	public void setPartnerLabel(JLabel partnerLabel) {
		this.partnerLabel = partnerLabel;
	}

	public JTextFieldValidate getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(JTextFieldValidate partnerName) {
		this.partnerName = partnerName;
	}

	public JLabel getPhomeLabel() {
		return phomeLabel;
	}

	public void setPhomeLabel(JLabel phomeLabel) {
		this.phomeLabel = phomeLabel;
	}

	public JTextFieldValidate getPartnerPhone() {
		return partnerPhone;
	}

	public void setPartnerPhone(JTextFieldValidate partnerPhone) {
		this.partnerPhone = partnerPhone;
	}
	
}