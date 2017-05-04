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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changing/client/ui/internal/BasicPanel.java
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
package com.deppon.foss.module.pickup.changing.client.ui.internal;

import java.awt.Color;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.deppon.foss.base.util.define.NumberConstants;
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
import com.deppon.foss.module.pickup.changing.client.action.QuerySalesDepartmentAction;
import com.deppon.foss.module.pickup.common.client.utils.BZPartnersJudge;
import com.deppon.foss.module.pickup.common.client.utils.LengthDocument;
import com.deppon.foss.module.pickup.common.client.utils.WaybillNoDocument;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * 运单基础信息面板
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:102246-foss-shaohongliang,date:2012-10-16 上午8:59:16,
 * </p>
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-10-16 上午8:59:16
 * @since
 * @version
 */
@ContainerSeq(seq = 2)
public class BasicPanel extends JPanel {

	/**
	 * 20
	 */
	private static final int TWENTY = 20;

	/**
	 * 9
	 */
	private static final int NINE = 9;
	// 运单界面
//	private WaybillRFCUI waybillRFCUI;
	
	/**
	 * 10
	 */
	private static final int TEN = 10;

//	private static final int SIX = 6;

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 5563669636618712206L;

	/**
	 * 国际化
	 */
	private static II18n i18n = I18nManager.getI18n(BasicPanel.class);

	/**
	 * 新的运单号
	 */
	@Bind("newWaybillNo")
	@Int
	@FocusSeq(seq = 4)
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
	@BindingArgs({ @BindingArg(name = "fieldName", value = "收货部门") })
	@FocusSeq(seq = 1)
	private JTextFieldValidate txtConsigneeDept;

	/**
	 * 是否变更单号
	 */
	@FocusSeq(seq = 3)
	private JCheckBox cboRfcWaybillNo;

	/**
	 * 是否上门接货
	 */
	@Bind("pickupToDoor")
	@FocusSeq(seq = 5)
	private JCheckBox cboReceiveModel;

	/**
	 * 是否精准大票
	 */
	@Bind("isBigGoods")
	@FocusSeq(seq = 6)
	private JCheckBox cboBigGoodsModel; // zxy 20140626 JZDP 新增

	/**
	 * 查询按键
	 */
	@ButtonAction(icon = "query.png", shortcutKey = "", type = QuerySalesDepartmentAction.class)
	@FocusSeq(seq = 2)
	private JButton btnSearch;
	private JPanel panel;

	/**
	 * 经济自提件
	 */
	@Bind("isEconomyGoods")
	private JCheckBox chbEconomyGoods;
	/**
	 * 经济自提件类型
	 */
	@Bind("economyGoodsType")
	private JComboBox cobEconomyGoodsType;

	/**
	 * 自提件类型Model
	 */
	private DefaultComboBoxModel economyGoodsTypeModel;

	/**
	 * 是否展会货
	 */
	@Bind("isExhibitCargo")
	private JCheckBox chbExhibitCargo;

	/**
	 * 内部发货
	 */
	private JLabel internalDelivery;
	/**
	 * 内部发货类型
	 */
	@Bind("internalDeliveryType")
	private JComboBox internalDeliveryType;
	/**
	 * 工号
	 */
	@Bind("employeeNo")
	private JTextFieldValidate txtStaffNumber;
	
	/**
	 * 特殊增值服务
	 */
	private JLabel specialValueAddedService;
	/**
	 * 增值服务类型
	 */
	@Bind("specialValueAddedServiceType")
	private JComboBox specialValueAddedServiceType;

	/**
	 * 内部发货类型model
	 * 
	 * @return
	 */
	private DefaultComboBoxModel internalDeliveryTypeModel;
	/**
	 * 特殊增值服务类型model
	 * 
	 * @return
	 */
	private DefaultComboBoxModel specialValueAddedServiceTypeModel;
//	private JLabel label;
//	private JComboBox comboBox;
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
	public DefaultComboBoxModel getEconomyGoodsTypeModel() {
		return economyGoodsTypeModel;
	}
	
	/**
	 * 免费接货
	 */
	@Bind("freePickupGoods")
	// @FocusSeq(seq=4)
	private JCheckBox cboFreePickupGoods;
	

	public JCheckBox getCboFreePickupGoods() {
		return cboFreePickupGoods;
	}

	public void setEconomyGoodsTypeModel(
			DefaultComboBoxModel economyGoodsTypeModel) {
		this.economyGoodsTypeModel = economyGoodsTypeModel;
	}

	public JCheckBox getChbEconomyGoods() {
		return chbEconomyGoods;
	}

	public void setChbEconomyGoods(JCheckBox chbEconomyGoods) {
		this.chbEconomyGoods = chbEconomyGoods;
	}

	public JComboBox getCobEconomyGoodsType() {
		return cobEconomyGoodsType;
	}

	public void setCobEconomyGoodsType(JComboBox cobEconomyGoodsType) {
		this.cobEconomyGoodsType = cobEconomyGoodsType;
	}

	/**
	 * Create the panel.
	 */
	public BasicPanel() {
		init();
	}

	/**
	 * 
	 * 初始化布局
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午10:51:31
	 */
	private void init() {
		setBorder(new JDTitledBorder(i18n.get("pickup.changing.basicPanel")));
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(47dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(25dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(22dlu;default):grow"),
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
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));

		JLabel lblConsigneeDept = new JLabel(
				i18n.get("pickup.changing.consigneeDept"));
		add(lblConsigneeDept, "2, 1, left, default");

		cboRfcWaybillNo = new JCheckBox(
				i18n.get("pickup.changing.rfcWaybillNo"));

		panel = new JPanel();
		add(panel, "4, 1, fill, fill");
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		txtConsigneeDept = new JTextFieldValidate();
		panel.add(txtConsigneeDept);
		txtConsigneeDept.setColumns(TEN);

		// 查询
		btnSearch = new JButton();
		btnSearch.setMargin(new Insets(-2, 1, -2, 1));
		panel.add(btnSearch);
		
		partnerCheckBox = new JCheckBox("伙伴开单");
//		add(partnerCheckBox, "12, 1, 7, 1");
		partnerCheckBox.setEnabled(false);
		
		add(cboRfcWaybillNo, "2, 3");

		txtRfcWaybillNo = new JTextFieldValidate();
		add(txtRfcWaybillNo, "4, 3, 3, 1, fill, default");
		txtRfcWaybillNo.setColumns(TWENTY);
		// zxy 20130909 修改：不能输入负号 start
		// NumberDocument 改成 WaybillNoDocument
		txtRfcWaybillNo.setDocument(new WaybillNoDocument(NINE));
		// zxy 20130909 修改：不能输入负号 end

		cboReceiveModel = new JCheckBox(
				i18n.get("pickup.changing.receiveModel"));
		add(cboReceiveModel, "2, 5");
		//免费接货
		cboFreePickupGoods = new JCheckBox(
				i18n.get("foss.gui.creating.basicPanel.freePickupGoods.label"));
		add(cboFreePickupGoods, "4, 5, left, default");
		cboFreePickupGoods.setEnabled(false);
		cboFreePickupGoods.setForeground(Color.RED);
		
		// zxy 20140626 JZDP start 新增:精准大票复选框
		cboBigGoodsModel = new JCheckBox(
				i18n.get("pickup.changing.bigGoodsModel"));
		if(!BZPartnersJudge.IS_PARTENER || !BZPartnersJudge.BIG_GOODS){
			add(cboBigGoodsModel, "2, 7");
		}
		// zxy 20140626 JZDP end 新增:精准大票复选框

		// 是否自提件
		chbEconomyGoods = new JCheckBox(
				i18n.get("foss.gui.changing.basicpanel.economygoods"));
		add(chbEconomyGoods, "2, 7");
		// 自提件取消上线，设置为不显示
		chbEconomyGoods.setVisible(false);

		cobEconomyGoodsType = new JComboBox();
		economyGoodsTypeModel = (DefaultComboBoxModel) cobEconomyGoodsType
				.getModel();
		add(cobEconomyGoodsType, "4, 7, fill, default");
		//自提件取消上线，设置为不显示
		// 特殊增值服务
		specialValueAddedService = new JLabel(
				i18n.get("foss.gui.changing.basicPanel.specialValueAddedService.label"));
		
		specialValueAddedServiceType = new JComboBox();
		specialValueAddedServiceTypeModel = (DefaultComboBoxModel) specialValueAddedServiceType
				.getModel();
		
		if(!BZPartnersJudge.IS_PARTENER){
			add(specialValueAddedService, "10, 7, left, default");
			add(specialValueAddedServiceType, "12, 7, 5, 1, fill, default");
		}
		
		chbExhibitCargo = new JCheckBox(i18n.get("pickup.changing.isExhibitCargo"));

		chbExhibitCargo = new JCheckBox(
				i18n.get("pickup.changing.isExhibitCargo"));
		add(chbExhibitCargo, "2, 9");
		// 内部发货
		internalDelivery = new JLabel(
				i18n.get("foss.gui.changing.basicPanel.internalDelivery.label"));

		internalDeliveryType = new JComboBox();
		internalDeliveryTypeModel = (DefaultComboBoxModel) internalDeliveryType
				.getModel();
		// 工号
		JLabel staffNo = new JLabel(
				i18n.get("foss.gui.changing.basicPanel.number.label"));

		txtStaffNumber = new JTextFieldValidate();
		if(!BZPartnersJudge.IS_PARTENER || !BZPartnersJudge.INTERNAL_DELIVERY){
//			add(internalDelivery, "2, 11, left, default");
//			add(internalDeliveryType, "3, 11, 5, 1, fill, default");
			add(staffNo, "2, 11, left, default");
			add(txtStaffNumber, "4, 11, 5, 1, fill, default");
		}
		LengthDocument document = new LengthDocument(NumberConstants.NUMBER_6);
		txtStaffNumber.setDocument(document);
		
		//合伙人和非合伙人已经区别开，所以合伙人名称不在显示 2016年2月22日 15:36:18 葛亮亮
		partnerLabel = new JLabel("伙伴名称:");
//		add(partnerLabel, "2, 13, left, default");
		
		partnerName = new JTextFieldValidate();
//		add(partnerName, "4, 13, 3, 1, fill, default");
		partnerName.setColumns(NumberConstants.NUMBER_10);
		partnerName.setEditable(false);
		partnerName.setEnabled(false);
		
		phomeLabel = new JLabel("伙伴电话:");
//		add(phomeLabel, "10, 13");
		
		partnerPhone = new JTextFieldValidate();
//		add(partnerPhone, "12, 13, 5, 1, fill, default");
		partnerPhone.setColumns(NumberConstants.NUMBER_10);
		partnerPhone.setEditable(false);
		partnerPhone.setEnabled(false);
		cobEconomyGoodsType.setVisible(false);
	}

	/**
	 * 
	 * 查询按键get方法
	 * 
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
	 * 
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

	public JCheckBox getCboBigGoodsModel() {
		return cboBigGoodsModel;
	}

	public JCheckBox getChbExhibitCargo() {
		return chbExhibitCargo;
	}

	public void setChbExhibitCargo(JCheckBox chbExhibitCargo) {
		this.chbExhibitCargo = chbExhibitCargo;
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

	public JLabel getSpecialValueAddedService() {
		return specialValueAddedService;
	}

	public void setSpecialValueAddedService(JLabel specialValueAddedService) {
		this.specialValueAddedService = specialValueAddedService;
	}

	public JComboBox getSpecialValueAddedServiceType() {
		return specialValueAddedServiceType;
	}

	public void setSpecialValueAddedServiceType(
			JComboBox specialValueAddedServiceType) {
		this.specialValueAddedServiceType = specialValueAddedServiceType;
	}

	public DefaultComboBoxModel getSpecialValueAddedServiceTypeModel() {
		return specialValueAddedServiceTypeModel;
	}

	public void setSpecialValueAddedServiceTypeModel(
			DefaultComboBoxModel specialValueAddedServiceTypeModel) {
		this.specialValueAddedServiceTypeModel = specialValueAddedServiceTypeModel;
	}


	public JCheckBox getPartnerCheckBox() {
		return partnerCheckBox;
	}

	public void setPartnerCheckBox(JCheckBox partnerCheckBox) {
		this.partnerCheckBox = partnerCheckBox;
	}

	public JTextFieldValidate getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(JTextFieldValidate partnerName) {
		this.partnerName = partnerName;
	}

	public JLabel getPartnerLabel() {
		return partnerLabel;
	}

	public void setPartnerLabel(JLabel partnerLabel) {
		this.partnerLabel = partnerLabel;
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