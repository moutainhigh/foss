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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/ui/editui/BasicPanel.java
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
package com.deppon.foss.module.pickup.creating.client.ui.editui;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.annotation.ButtonAction;
import com.deppon.foss.framework.client.component.focuspolicy.annotation.ContainerSeq;
import com.deppon.foss.framework.client.component.focuspolicy.annotation.FocusSeq;
import com.deppon.foss.framework.client.core.binding.annotation.Bind;
import com.deppon.foss.framework.client.widget.validatewidget.JTextFieldValidate;
import com.deppon.foss.module.mainframe.client.ui.JDTitledBorder;
import com.deppon.foss.module.pickup.common.client.utils.BZPartnersJudge;
import com.deppon.foss.module.pickup.common.client.utils.LengthDocument;
import com.deppon.foss.module.pickup.common.client.utils.MobileDocument;
import com.deppon.foss.module.pickup.common.client.utils.NumberDocument;
import com.deppon.foss.module.pickup.common.client.utils.WaybillNoDocument;
import com.deppon.foss.module.pickup.creating.client.action.ImportVehicleAction;
import com.deppon.foss.module.pickup.creating.client.action.QuerySalesDepartmentAction;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
/**
 * 
 * (基础信息面板)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:Administrator,date:2012-10-17 上午11:15:11,
 * </p>
 * 
 * @author 025000-FOSS-helong
 * @date 2012-10-17 上午11:15:11
 * @since
 * @version
 */
@ContainerSeq(seq=1)
public class BasicPanel extends JPanel {
	
	/**
	 * 国际化对象
	 */
	private static final II18n i18n = I18nManager.getI18n(BasicPanel.class); 

	private static final int SIX = 6;

	private static final int NIVE = 9;

	private static final int TEN = 10;

	private static final long serialVersionUID = 165299221767565361L;

	private static final int ELEVEN = 11;

	/**
	 * 运单号
	 */
	@Bind("waybillNo")
	@FocusSeq(seq=3)
	private JTextFieldValidate txtWaybillNO;

	/**
	 * 上门接货
	 */
	@Bind("pickupToDoor")
	@FocusSeq(seq=4)
	private JCheckBox cboReceiveModel;
	
	/**
	 * 整车
	 */
	@Bind("isWholeVehicle")
	@FocusSeq(seq=5)
	private JCheckBox chbWholeVehicle;
	
	/**
	 * 精准大票
	 */
	@Bind("isBigGoods")
	private JCheckBox chbBigGoods;
	
	/**
	 * 是否展会货
	 */
	@Bind("isExhibitCargo")
	private JCheckBox chbExhibitCargo;

	/**
	 * 经过到达部门
	 */
	@Bind("isPassDept")
	@FocusSeq(seq=6)
	private JCheckBox chbPassDept;
	
	/**
	 * 司机编号
	 */
	@Bind("driverCode")
	@FocusSeq(seq=7)
	private JTextFieldValidate txtDriverNumber;

	private JLabel lblVehicleNumber;

	/**
	 * 整车约车编号
	 */
	@Bind("vehicleNumber")
	private JTextFieldValidate txtVehicleNumber;	
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
	 * 特殊增值服务
	 */
	private JLabel specialValueAddedService;
	/**
	 * 增值服务类型
	 */
	@Bind("specialValueAddedServiceType")
	private JComboBox specialValueAddedServiceType;
	
	/**
	 * 导入整车约车编号
	 */
	@ButtonAction(icon = "preview.png", shortcutKey = "", type = ImportVehicleAction.class)
	private JButton btnImportVehicle;
	
	/**
	 * 集中开单或者营业部开单
	 */
	private String waybillType;
	private JLabel lblNewLabel;
	
	/**
	 * 收货部门
	 */
	@Bind("receiveOrgName")
	@FocusSeq(seq=1)
	private JTextFieldValidate txtSalesDepartment;
	
	public JTextFieldValidate getTxtSalesDepartment() {
		return txtSalesDepartment;
	}


	@ButtonAction(icon = "preview.png", shortcutKey = "", type = QuerySalesDepartmentAction.class)
	@FocusSeq(seq=2)
	private JButton btnQueryDept;
	
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
	//伙伴开单
	@Bind("partnerBilling")
	private JCheckBox partnerCheckBox;
	//伙伴名称
	@Bind("partnerName")
	@FocusSeq(seq=6)
	private JTextFieldValidate partnerName;
	private JLabel partnerLabel;
	private JLabel phoneLabel;
	//伙伴手机号
	@Bind("partnerPhone")
	@FocusSeq(seq=7)
	private JTextFieldValidate partnerPhone;
	
	/**
	 * 免费接货
	 */
	@Bind("freePickupGoods")
	// @FocusSeq(seq=4)
	private JCheckBox cboFreePickupGoods;

	
	public JCheckBox getCboFreePickupGoods() {
		return cboFreePickupGoods;
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
	public BasicPanel(String waybillType) {
		this.waybillType = waybillType;
		init();
		//收货部门Enter键监控
		EnterKeyForReceiveOrg enterDepartment=new EnterKeyForReceiveOrg(btnQueryDept);		
		txtSalesDepartment.addKeyListener(enterDepartment);
		//整车编号Enter键监控
		EnterKeyForReceiveOrg enterVehicleNumber=new EnterKeyForReceiveOrg(btnImportVehicle);		
		txtVehicleNumber.addKeyListener(enterVehicleNumber);
	}

	/**
	 * 初始化界面信息
	 */
	private void init() {
		setBorder(new JDTitledBorder(i18n.get("foss.gui.creating.basicPanel.title")));
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
				ColumnSpec.decode("max(28dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
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
				FormFactory.DEFAULT_ROWSPEC,}));		
		/**
		 * 判断是集中开单还是营业部开单，根据不同属性，生成不同的界面
		 */
		if(WaybillConstants.WAYBILL_SALE_DEPARTMENT.equals(waybillType))
		{
			salesDepartment();
		} else if (WaybillConstants.WAYBILL_FOCUS.equals(waybillType)) {
			focusWaybill();		
		}
	}

	
	/**
	 * 
	 * 集中开单
	 * @author 025000-FOSS-helong
	 * @date 2012-12-11 上午11:32:49
	 */
	private void focusWaybill()
	{
		lblNewLabel = new JLabel("*"+i18n.get("foss.gui.creating.basicPanel.salesDepartment.label"));
		add(lblNewLabel, "2, 2, left, default");
		
		txtSalesDepartment = new JTextFieldValidate();
		add(txtSalesDepartment, "4, 2, 10, 1, fill, default");
		txtSalesDepartment.setColumns(TEN);
		txtSalesDepartment.setEditable(true);
		
		btnQueryDept = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.query"));
		add(btnQueryDept, "14, 2, 7, 1");
		
		JLabel label2 = new JLabel("*"+i18n.get("foss.gui.creating.waybillEditUI.common.waybillNo")+"：");
		add(label2, "2, 4, left, default");

		txtWaybillNO = new JTextFieldValidate();
		add(txtWaybillNO, "4, 4, 10, 1, fill, default");
		txtWaybillNO.setColumns(TEN);
		WaybillNoDocument numberDocument = new WaybillNoDocument(NIVE);
		txtWaybillNO.setDocument(numberDocument);

		cboReceiveModel = new JCheckBox(i18n.get("foss.gui.creating.basicPanel.receiveModel.label"));
		add(cboReceiveModel, "2, 6");
		cboReceiveModel.setVisible(true);
		
		chbWholeVehicle = new JCheckBox(i18n.get("foss.gui.creating.basicPanel.wholeVehicle.label"));
		add(chbWholeVehicle, "4, 6");
		chbWholeVehicle.setVisible(false);
		
		chbBigGoods = new JCheckBox(i18n.get("foss.gui.creating.basicPanel.BigGoods.label"));
		add(chbBigGoods,"2, 12");
		
		chbExhibitCargo = new JCheckBox(i18n.get("pickup.changing.isExhibitCargo"));
		add(chbExhibitCargo,"4, 12");
		// 内部发货
		internalDelivery = new JLabel(
				i18n.get("foss.gui.creating.basicPanel.internalDelivery.label"));
//		add(internalDelivery, "2, 16, left, default");

		internalDeliveryType = new JComboBox();
//		add(internalDeliveryType, "3, 16, 5, 1, fill, default");
		// 工号
		JLabel staffNo = new JLabel(
				i18n.get("foss.gui.creating.basicPanel.number.label"));
		add(staffNo, "2, 16, left, default");
		
		// 特殊增值服务
		specialValueAddedService = new JLabel(
				i18n.get("foss.gui.creating.basicPanel.specialValueAddedService.label"));
		add(specialValueAddedService, "2, 18, left, default");

		specialValueAddedServiceType = new JComboBox();
		add(specialValueAddedServiceType, "3, 18, 5, 1, fill, default");

		txtStaffNumber = new JTextFieldValidate();
		add(txtStaffNumber, "4, 16, 5, 1, fill, default");
		LengthDocument document  = new LengthDocument(SIX);
		txtStaffNumber.setDocument(document);
		
		chbPassDept = new JCheckBox(i18n.get("foss.gui.creating.basicPanel.passDept.label"));
		add(chbPassDept, "6, 6, 11, 1");
		chbPassDept.setVisible(false);
		
		chbEconomyGoods = new JCheckBox();
		add(chbEconomyGoods, "2, 8");
		chbEconomyGoods.setVisible(false);
		
		cobEconomyGoodsType = new JComboBox();
		add(cobEconomyGoodsType, "4, 8, 10, 1, fill, default");
		cobEconomyGoodsType.setVisible(false);

		JLabel label3 = new JLabel(i18n.get("foss.gui.creating.basicPanel.driverNumber.label")+"：");
		add(label3, "2, 10, left, default");

		txtDriverNumber = new JTextFieldValidate();
		add(txtDriverNumber, "4, 10, 10, 1, fill, default");
		txtDriverNumber.setColumns(TEN);
		NumberDocument driverNumber = new NumberDocument(SIX);
		txtDriverNumber.setDocument(driverNumber);

		
		lblVehicleNumber = new JLabel(i18n.get("foss.gui.creating.basicPanel.vehicleNumber.label")+"：");
		add(lblVehicleNumber, "2, 14, left, default");
		lblVehicleNumber.setVisible(false);
		
		txtVehicleNumber = new JTextFieldValidate();
		add(txtVehicleNumber, "4, 14, 10, 1, fill, default");
		txtVehicleNumber.setColumns(TEN);
		txtVehicleNumber.setVisible(false);
		
		btnImportVehicle = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.confirm"));
		add(btnImportVehicle, "14, 14, 7, 1");
		btnImportVehicle.setVisible(false);
		
		partnerCheckBox = new JCheckBox(i18n.get("foss.gui.creating.basicPanel.partnerCheckBox"));
		add(partnerCheckBox, "6, 12, 10, 1, default, default");
		partnerCheckBox.setVisible(false);
		
		partnerLabel = new JLabel(i18n.get("foss.gui.creating.basicPanel.partnerName")+":");
		add(partnerLabel, "2, 18, left, default");
		partnerLabel.setVisible(false);
		
		partnerName = new JTextFieldValidate();
		add(partnerName, "4, 18, fill, default");
		partnerName.setColumns(TEN);
		partnerName.setVisible(false);
		
		phoneLabel = new JLabel(i18n.get("foss.gui.creating.basicPanel.partnerPhone")+":");
		add(phoneLabel, "10, 18, left, default");
		phoneLabel.setVisible(false);
		
		partnerPhone = new JTextFieldValidate();
		add(partnerPhone, "12, 18, 5, 1, fill, default");
		partnerPhone.setColumns(TEN);
		partnerPhone.setVisible(false);
		
		// 集中开单免费接货
		cboFreePickupGoods = new JCheckBox(i18n.get("foss.gui.creating.basicPanel.freePickupGoods.label"));
		add(cboFreePickupGoods, "6, 6");
		cboFreePickupGoods.setEnabled(false);
		cboFreePickupGoods.setForeground(Color.RED);
				
		// 限制手机号码只允许输入11位的数字
		MobileDocument phoneNumberDocument = new MobileDocument(ELEVEN);
		partnerPhone.setDocument(phoneNumberDocument);
	}
	
	/**
	 * 
	 * 营业部开单
	 * @author 025000-FOSS-helong
	 * @date 2012-12-11 上午11:32:57
	 */
	private void salesDepartment()
	{
		JLabel label2 = new JLabel("*"+i18n.get("foss.gui.creating.waybillEditUI.common.waybillNo")+"：");
		add(label2, "2, 2, left, default");

		txtWaybillNO = new JTextFieldValidate();
		add(txtWaybillNO, "4, 2, 7, 1, fill, default");
		txtWaybillNO.setColumns(TEN);
		WaybillNoDocument numberDocument = new WaybillNoDocument(NIVE);
		txtWaybillNO.setDocument(numberDocument);
		
		lblNewLabel = new JLabel("*"+i18n.get("foss.gui.creating.basicPanel.salesDepartment.label"));
		add(lblNewLabel, "2, 4, left, default");
		lblNewLabel.setVisible(false);
		
		txtSalesDepartment = new JTextFieldValidate();
		add(txtSalesDepartment, "4, 4, 7, 1, fill, default");
		txtSalesDepartment.setColumns(TEN);
		txtSalesDepartment.setVisible(false);

		cboReceiveModel = new JCheckBox(i18n.get("foss.gui.creating.basicPanel.receiveModel.label"));
		add(cboReceiveModel, "2, 6");
		
		chbWholeVehicle = new JCheckBox(i18n.get("foss.gui.creating.basicPanel.wholeVehicle.label"));
		add(chbWholeVehicle, "4, 6");
		
		chbBigGoods = new JCheckBox(i18n.get("foss.gui.creating.basicPanel.BigGoods.label"));
		if(!BZPartnersJudge.IS_PARTENER || !BZPartnersJudge.BIG_GOODS){
			add(chbBigGoods,"2, 12");
		 }
		
		chbExhibitCargo = new JCheckBox(i18n.get("pickup.changing.isExhibitCargo"));
		add(chbExhibitCargo,"4, 12");
		
		// 特殊增值服务
		specialValueAddedService = new JLabel(
				i18n.get("foss.gui.creating.basicPanel.specialValueAddedService.label"));
		
		specialValueAddedServiceType = new JComboBox();
		
		if(!BZPartnersJudge.IS_PARTENER || !BZPartnersJudge.INTERNAL_DELIVERY){
			add(specialValueAddedService, "6, 12, 5, 1, left, default");
			add(specialValueAddedServiceType, "12, 12, 5, 1, fill, default");
		}

		// 内部发货
		internalDelivery = new JLabel(
				i18n.get("foss.gui.creating.basicPanel.internalDelivery.label"));

		internalDeliveryType = new JComboBox();
		if(!BZPartnersJudge.IS_PARTENER || !BZPartnersJudge.INTERNAL_DELIVERY){
			//=================取消内部发货按钮/lianhe/2016年12月19日11:16:13==================
//			add(internalDeliveryType, "3, 16, 5, 1, fill, default");
		}
		// 工号
		JLabel staffNo = new JLabel(
				i18n.get("foss.gui.creating.basicPanel.number.label"));

		txtStaffNumber = new JTextFieldValidate();
		//内部发货及工号的隐藏
		if(!BZPartnersJudge.IS_PARTENER || !BZPartnersJudge.INTERNAL_DELIVERY){
			//=================取消内部发货按钮,并修改工号布局在原内部发货栏位置/lianhe/2016年12月19日11:16:13==================
//			  add(internalDelivery, "2, 16, left, default");
			  add(staffNo, "2, 16, left, default");
			  add(txtStaffNumber, "4, 16, 5, 1, fill, default");
		}
		LengthDocument document  = new LengthDocument(SIX);
		txtStaffNumber.setDocument(document);
		
		chbPassDept = new JCheckBox(i18n.get("foss.gui.creating.basicPanel.passDept.label"));
		add(chbPassDept, "6, 6, 7, 1");
		chbPassDept.setEnabled(false);
		
		chbEconomyGoods = new JCheckBox(i18n.get("foss.gui.creating.basicpanel.economygoods"));
		add(chbEconomyGoods, "2, 8");
		//自提件取消上线，设置为不显示
		chbEconomyGoods.setVisible(false);
		
		cobEconomyGoodsType = new JComboBox();
		add(cobEconomyGoodsType, "4, 8, 7, 1, fill, default");
		//自提件取消上线，设置为不显示
		cobEconomyGoodsType.setVisible(false);

		JLabel label3 = new JLabel(i18n.get("foss.gui.creating.basicPanel.driverNumber.label")+"：");
		if(!BZPartnersJudge.IS_PARTENER || !BZPartnersJudge.DRIVER_CODE){
	    	add(label3, "2, 10, left, default");
		}

		txtDriverNumber = new JTextFieldValidate();
		if(!BZPartnersJudge.IS_PARTENER || !BZPartnersJudge.DRIVER_CODE){
		   add(txtDriverNumber, "4, 10, 7, 1, fill, default");
		}
		txtDriverNumber.setColumns(TEN);
		//现在司机工号不受是否接货的限制，根据ISSUE-3164修改
		txtDriverNumber.setEditable(true);
		NumberDocument driverNumber = new NumberDocument(SIX);
		txtDriverNumber.setDocument(driverNumber);

		
		lblVehicleNumber = new JLabel(i18n.get("foss.gui.creating.basicPanel.vehicleNumber.label")+"：");
		add(lblVehicleNumber, "2, 14, left, default");
		lblVehicleNumber.setVisible(false);
		
		txtVehicleNumber = new JTextFieldValidate();
		add(txtVehicleNumber, "4, 14, 7, 1, fill, default");
		txtVehicleNumber.setColumns(TEN);
		txtVehicleNumber.setVisible(false);
		
		btnImportVehicle = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.confirm"));
		add(btnImportVehicle, "12, 14, 5, 1");
		btnImportVehicle.setVisible(false);
		
		partnerCheckBox = new JCheckBox(i18n.get("foss.gui.creating.basicPanel.partnerCheckBox"));
//		add(partnerCheckBox, "13, 2, 4, 1");
		
		partnerLabel = new JLabel(i18n.get("foss.gui.creating.basicPanel.partnerName")+":");
		add(partnerLabel, "2, 18, left, default");
		partnerLabel.setVisible(false);
		
		partnerName = new JTextFieldValidate();
		add(partnerName, "4, 18, fill, default");
		partnerName.setColumns(TEN);
		partnerName.setVisible(false);
		
		phoneLabel = new JLabel(i18n.get("foss.gui.creating.basicPanel.partnerPhone")+":");
		add(phoneLabel, "10, 18, left, default");
		phoneLabel.setVisible(false);
		
		partnerPhone = new JTextFieldValidate();
		add(partnerPhone, "12, 18, 5, 1, fill, default");
		partnerPhone.setColumns(TEN);
		partnerPhone.setVisible(false);
		
		// 营业部开单免费接货
		cboFreePickupGoods = new JCheckBox(
				i18n.get("foss.gui.creating.basicPanel.freePickupGoods.label"));
		add(cboFreePickupGoods, "13, 6, 8, 1");
		cboFreePickupGoods.setEnabled(false);
		cboFreePickupGoods.setForeground(Color.RED);
		// 限制手机号码只允许输入11位的数字
		MobileDocument phoneNumberDocument = new MobileDocument(ELEVEN);
		partnerPhone.setDocument(phoneNumberDocument);
	}

	/**
	 * getCboReceiveModel
	 * @return
	 */
	public JCheckBox getCboReceiveModel() {
		return cboReceiveModel;
	}

	/**
	 * getTxtDriverNumber
	 * @return
	 */
	public JTextFieldValidate getTxtDriverNumber() {
		return txtDriverNumber;
	}
	
	/**
	 * getLblVehicleNumber
	 * @return
	 */
	public JLabel getLblVehicleNumber() {
		return lblVehicleNumber;
	}
	
	/**
	 * getTxtVehicleNumber
	 * @return
	 */
	public JTextFieldValidate getTxtVehicleNumber() {
		return txtVehicleNumber;
	}
	
	/**
	 * getBtnImportVehicle
	 * @return
	 */
	public JButton getBtnImportVehicle() {
		return btnImportVehicle;
	}
	
	public JCheckBox getChbWholeVehicle() {
		return chbWholeVehicle;
	}
	
	public JCheckBox getChbPassDept() {
		return chbPassDept;
	}

	/**
	 * @return the txtWaybillNO
	 */
	public JTextFieldValidate getTxtWaybillNO() {
		return txtWaybillNO;
	}

	/**
	 * @param txtWaybillNO the txtWaybillNO to set
	 */
	public void setTxtWaybillNO(JTextFieldValidate txtWaybillNO) {
		this.txtWaybillNO = txtWaybillNO;
	}

	public JCheckBox getChbBigGoods() {
		return chbBigGoods;
	}

	public void setChbBigGoods(JCheckBox chbBigGoods) {
		this.chbBigGoods = chbBigGoods;
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

	public JLabel getPhoneLabel() {
		return phoneLabel;
	}

	public void setPhoneLabel(JLabel phoneLabel) {
		this.phoneLabel = phoneLabel;
	}

	public JTextFieldValidate getPartnerPhone() {
		return partnerPhone;
	}

	public void setPartnerPhone(JTextFieldValidate partnerPhone) {
		this.partnerPhone = partnerPhone;
	}
}