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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/ui/SubmitConfirmCompareImportDialog.java
 * 
 * FILE NAME        	: SubmitConfirmCompareImportDialog.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-creating
 * PACKAGE NAME: com.deppon.foss.module.pickup.creating.client.ui
 * FILE    NAME: SubmitConfirmCompareImportDialog.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.pickup.creating.client.ui;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.annotation.ButtonAction;
import com.deppon.foss.framework.client.component.buttonaction.factory.ButtonActionFactory;
import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.framework.client.core.context.ApplicationContext;
import com.deppon.foss.module.pickup.waybill.shared.dto.AddressFieldDto;
import com.deppon.foss.module.pickup.common.client.service.IAddressService;
import com.deppon.foss.module.pickup.common.client.service.impl.AddressService;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.action.DialogCloseAllAction;
import com.deppon.foss.module.pickup.creating.client.action.WaybillSubmitConfirmAction;
import com.deppon.foss.module.pickup.creating.client.vo.PrintTemplatesModel;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * “确认提交”弹出界面
 * 
 * 这个类的代码是从SubmitConfirmDialog 复制过来 并且作了比较大的修改 这个类的目的是提交的时候,如果这个单子是离线导入的
 * 需要在提交的时候 显示一些字段历史修改纪录
 * 
 * @author shixiaowei
 * 
 * @date 2013-01-17 下午4:02:59
 */
public class SubmitConfirmCompareImportDialog extends JDialog {


	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = 969L;

	// preview png
	private static final String PREVIEWPNG = "preview.png";

	// 10 for 宽度
	private static final int TEN = 10;

	private static final int NUM_900 = 900;

	private static final int NUM_700 = 700;

	private static final int NUM_725 = 725;

	private static final int NUM_820 = 820;

	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager
			.getI18n(SubmitConfirmCompareImportDialog.class);

	// 提货网点
	private JTextField txtLandingstation;

	// 付款方式
	private JTextField txtPaymentWay;

	// 货物名称
	private JTextField txtGoodsName;

	// 提货方式
	private JTextField txtPickUp;

	// 总运费
	private JTextField totalCost;

	// 重量/件数/体积
	private JTextField txtWeightPieceVolume;

	// 代收货款
	private JTextField txtAgentPayment;

	// 预付总运费
	private JTextField txtPrepTotalCost;

	// 包装
	private JTextField txtPackaging;

	// 保险价值
	private JTextField txtInsuredAmount;

	// 收货人名称
	private JTextField txtConsignee;

	// 收货人手机/电话
	private JTextField txtConsigneePhone;

	// 收货人地址
	private JTextField txtConsigneeAddress;

	// 运单号
	private JLabel lblWaybillNum;

	// 确定
	@ButtonAction(icon = PREVIEWPNG, shortcutKey = "", type = WaybillSubmitConfirmAction.class)
	private JButton btnConfirm;

	// 取 消
	@ButtonAction(icon = PREVIEWPNG, shortcutKey = "", type = DialogCloseAllAction.class)
	private JButton btnCancel;

	// 打印运单
	private JCheckBox chbPrintWaybill;

	// 打印标签
	private JCheckBox chbPrintLabel;
	
	//木标签
	private JCheckBox woodpackaging;

	// 确定后新增运单
	private JCheckBox chbNewAdd;

	// 面板
	private JPanel panel = new JPanel();

	// vo
	private WaybillPanelVo vo;

	// import vo
	private WaybillPanelVo importvo;

	// ui
	private WaybillEditUI waybillEditUI;

	// 离线提货网点
	private JTextField txtLandingstationOff;

	// 离线 运单号
	private JLabel lblWaybillNumOff;

	// 离线 付款方式
	private JTextField txtPaymentWayOff;

	// 离线 货物名称
	private JTextField txtGoodsNameOff;

	// 离线提货方式
	private JTextField txtPickUpOff;

	// 离线总运费
	private JTextField totalCostOff;

	// 离线 重量/件数/体积
	private JTextField txtWeightPieceVolumeOff;

	// 离线代收货款
	private JTextField txtAgentPaymentOff;

	// 离线 预付总运费
	private JTextField txtPrepTotalCostOff;

	// 离线包装
	private JTextField txtPackagingOff;

	// 离线保险价值
	private JTextField txtInsuredAmountOff;

	// 离线收货人名称
	private JTextField txtConsigneeOff;

	// 离线收货人手机/电话
	private JTextField txtConsigneePhoneOff;

	// 离线收货人地址
	private JTextField txtConsigneeAddressOff;

	private JComboBox printTemplates;

	/**
	 * 构造方法
	 * 
	 * @param vo
	 * @param waybillEditUI
	 */
	public SubmitConfirmCompareImportDialog(WaybillPanelVo vo,
			WaybillEditUI waybillEditUI) {
		super(ApplicationContext.getApplication().getWorkbench().getFrame());
		this.waybillEditUI = waybillEditUI;
		this.vo = vo;
		this.importvo = waybillEditUI.getImportWaybillPanelVo();
		init();
		initData();
		buttonBind();
	}

	/**
	 * 初始化界面信息
	 */
	public void init() {

		setBounds(NumberConstants.NUMBER_100, NumberConstants.NUMBER_100, NUM_700, NUM_900);

		DefaultComboBoxModel model = new DefaultComboBoxModel();
		model.addElement(new PrintTemplatesModel(
				WaybillConstants.PRINTTEMPLATES_ONE,
				WaybillConstants.PRINTTEMPLATES_VALUE_ONE));

		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("left:10dlu"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("left:51dlu"),
				ColumnSpec.decode("19dlu:grow"),
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("left:50dlu"),
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("left:50dlu:grow"),
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("left:68dlu"), ColumnSpec.decode("10dlu"),
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("10dlu"), },
				new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("max(11dlu;default)"),
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("default:grow"),
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("max(12dlu;default)"),
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("fill:13dlu"),
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
						RowSpec.decode("max(12dlu;default)"),
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("max(13dlu;default)"),
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC, }));

		JLabel label2 = new JLabel(
				i18n.get("foss.gui.creating.submitConfirmDialog.warn.info"));
		panel.add(label2, "4, 2, 21, 1");

		JSeparator separatorOff1 = new JSeparator();
		panel.add(separatorOff1, "4, 4, 20, 1");

		// 离线信息 单号
		JLabel lblNewLabelOff = new JLabel(
				i18n.get("foss.gui.creating.submitConfirmDialog.offline.pendingWaybillNo.label"));
		lblNewLabelOff.setFont(new Font("宋体", Font.BOLD, NumberConstants.NUMBER_14));
		panel.add(lblNewLabelOff, "6, 6, 3, 1");

		lblWaybillNumOff = new JLabel("  ");
		lblWaybillNumOff.setFont(new Font("宋体", Font.BOLD, NumberConstants.NUMBER_14));
		panel.add(lblWaybillNumOff, "12, 6, 5, 1");

		// 提货网点
		JLabel lblNewLabelOff1 = new JLabel(
				i18n.get("foss.gui.creating.submitConfirmDialog.landingstation.label")
						+ "：");
		panel.add(lblNewLabelOff1, "4, 8, 3, 1");

		txtLandingstationOff = new JTextField();
		txtLandingstationOff.setEnabled(false);
		panel.add(txtLandingstationOff, "7, 8, 4, 1, fill, default");
		txtLandingstationOff.setColumns(TEN);

		// 付款方式
		JLabel lblNewLabelOff2 = new JLabel(
				i18n.get("foss.gui.creating.submitConfirmDialog.paymentWay.label")
						+ "：");
		panel.add(lblNewLabelOff2, "12, 8");

		txtPaymentWayOff = new JTextField();
		txtPaymentWayOff.setEnabled(false);
		panel.add(txtPaymentWayOff, "16, 8, fill, default");
		txtPaymentWayOff.setColumns(TEN);

		// 货物名称
		JLabel lblNewLabelOff3 = new JLabel(
				i18n.get("foss.gui.creating.submitConfirmDialog.goodsName.label")
						+ "：");
		panel.add(lblNewLabelOff3, "20, 8");

		txtGoodsNameOff = new JTextField();
		txtGoodsNameOff.setEnabled(false);
		panel.add(txtGoodsNameOff, "22, 8, fill, default");
		txtGoodsNameOff.setColumns(TEN);

		// 提货方式
		JLabel lblNewLabelOff4 = new JLabel(
				i18n.get("foss.gui.creating.submitConfirmDialog.pickUp.label")
						+ "：");
		panel.add(lblNewLabelOff4, "4, 10, 3, 1");

		txtPickUpOff = new JTextField();
		txtPickUpOff.setEnabled(false);
		panel.add(txtPickUpOff, "7, 10, 4, 1, fill, default");

		// 到付总运费
		JLabel lblNewLabelOff5 = new JLabel(
				i18n.get("foss.gui.creating.submitConfirmDialog.totalCost.label")
						+ "：");
		panel.add(lblNewLabelOff5, "12, 10");

		totalCostOff = new JTextField();
		totalCostOff.setEnabled(false);
		panel.add(totalCostOff, "16, 10, fill, default");
		totalCostOff.setColumns(TEN);

		// 重量/体积/件数
		JLabel lblNewLabelOff6 = new JLabel(
				i18n.get("foss.gui.creating.submitConfirmDialog.weightPieceVolume.label")
						+ "：");
		panel.add(lblNewLabelOff6, "20, 10");

		txtWeightPieceVolumeOff = new JTextField();
		txtWeightPieceVolumeOff.setEnabled(false);
		panel.add(txtWeightPieceVolumeOff, "22, 10, fill, default");
		txtWeightPieceVolumeOff.setColumns(TEN);

		// 代收货款
		JLabel lblNewLabelOff7 = new JLabel(
				i18n.get("foss.gui.creating.submitConfirmDialog.agentPayment.label")
						+ "：");
		panel.add(lblNewLabelOff7, "4, 12, 3, 1");

		txtAgentPaymentOff = new JTextField();
		txtAgentPaymentOff.setEnabled(false);
		panel.add(txtAgentPaymentOff, "7, 12, 4, 1, fill, default");
		txtAgentPaymentOff.setColumns(TEN);

		// 预付总运费
		JLabel lblNewLabelOff8 = new JLabel(
				i18n.get("foss.gui.creating.submitConfirmDialog.prepTotalCost.label")
						+ "：");
		panel.add(lblNewLabelOff8, "12, 12");

		txtPrepTotalCostOff = new JTextField();
		txtPrepTotalCostOff.setEnabled(false);
		panel.add(txtPrepTotalCostOff, "16, 12, fill, default");
		txtPrepTotalCostOff.setColumns(TEN);

		// 包装
		JLabel lblNewLabelOff9 = new JLabel(
				i18n.get("foss.gui.creating.submitConfirmDialog.packaging.label")
						+ "：");
		panel.add(lblNewLabelOff9, "20, 12");

		txtPackagingOff = new JTextField();
		txtPackagingOff.setEnabled(false);
		panel.add(txtPackagingOff, "22, 12, fill, default");
		txtPackagingOff.setColumns(TEN);

		// 保险价值
		JLabel lblNewLabelOff10 = new JLabel(
				i18n.get("foss.gui.creating.submitConfirmDialog.insuredAmount.label")
						+ "：");
		panel.add(lblNewLabelOff10, "4, 14, 3, 1");

		txtInsuredAmountOff = new JTextField();
		panel.add(txtInsuredAmountOff, "7, 14, 4, 1, fill, default");
		txtInsuredAmountOff.setEnabled(false);
		txtInsuredAmountOff.setColumns(TEN);

		JSeparator separatorOff2 = new JSeparator();
		panel.add(separatorOff2, "4, 16, 20, 1");
		// 收货人名称
		JLabel lblNewLabelOff11 = new JLabel(
				i18n.get("foss.gui.creating.submitConfirmDialog.consignee.label")
						+ "：");
		panel.add(lblNewLabelOff11, "4, 18, 3, 1");

		txtConsigneeOff = new JTextField();
		panel.add(txtConsigneeOff, "7, 18, 6, 1, fill, default");
		txtConsigneeOff.setEnabled(false);
		txtConsigneeOff.setColumns(TEN);

		// 收货人手机/电话
		JLabel lblNewLabelOff12 = new JLabel(
				i18n.get("foss.gui.creating.submitConfirmDialog.consigneePhone.label")
						+ "：");
		panel.add(lblNewLabelOff12, "4, 20, 3, 1");

		txtConsigneePhoneOff = new JTextField();
		panel.add(txtConsigneePhoneOff, "7, 20, 6, 1, fill, default");
		txtConsigneePhoneOff.setEnabled(false);
		txtConsigneePhoneOff.setColumns(TEN);

		// 收货人地址
		JLabel lblNewLabelOff13 = new JLabel(
				i18n.get("foss.gui.creating.submitConfirmDialog.consigneeAddress.label")
						+ "：");
		panel.add(lblNewLabelOff13, "4, 22, 3, 1");

		txtConsigneeAddressOff = new JTextField();
		panel.add(txtConsigneeAddressOff, "7, 22, 10, 1, fill, default");
		txtConsigneeAddressOff.setEnabled(false);
		txtConsigneeAddressOff.setColumns(TEN);

		JSeparator separator = new JSeparator();
		panel.add(separator, "4, 24, 20, 1, default, center");

		// 在线信息 单号
		JLabel label = new JLabel(
				i18n.get("foss.gui.creating.submitConfirmDialog.online.pendingWaybillNo.label"));
		label.setFont(new Font("宋体", Font.BOLD, NumberConstants.NUMBER_14));
		panel.add(label, "6, 26, 3, 1");

		lblWaybillNum = new JLabel("");
		lblWaybillNum.setFont(new Font("宋体", Font.BOLD, NumberConstants.NUMBER_14));
		panel.add(lblWaybillNum, "12, 26, 5, 1");

		// 提货网点
		JLabel label1 = new JLabel(
				i18n.get("foss.gui.creating.submitConfirmDialog.landingstation.label")
						+ "：");
		panel.add(label1, "4, 28, 3, 1, left, default");

		txtLandingstation = new JTextField();
		txtLandingstation.setEnabled(false);
		panel.add(txtLandingstation, "7, 28, 4, 1, fill, default");
		txtLandingstation.setColumns(TEN);

		// 付款方式
		JLabel lblNewLabel = new JLabel(
				i18n.get("foss.gui.creating.submitConfirmDialog.paymentWay.label")
						+ "：");
		panel.add(lblNewLabel, "12, 28, left, fill");

		txtPaymentWay = new JTextField();
		txtPaymentWay.setEnabled(false);
		panel.add(txtPaymentWay, "16, 28, fill, default");
		txtPaymentWay.setColumns(TEN);

		// 货物名称
		JLabel lblNewLabel1 = new JLabel(
				i18n.get("foss.gui.creating.submitConfirmDialog.goodsName.label")
						+ "：");
		panel.add(lblNewLabel1, "20, 28, left, default");

		txtGoodsName = new JTextField();
		txtGoodsName.setEnabled(false);
		panel.add(txtGoodsName, "22, 28, fill, default");
		txtGoodsName.setColumns(TEN);

		// 提货方式
		JLabel label3 = new JLabel(
				i18n.get("foss.gui.creating.submitConfirmDialog.pickUp.label")
						+ "：");
		panel.add(label3, "4, 30, 3, 1, left, default");

		txtPickUp = new JTextField();
		txtPickUp.setEnabled(false);
		panel.add(txtPickUp, "7, 30, 4, 1, fill, default");
		txtPickUp.setColumns(TEN);

		// 到付总运费
		JLabel label8 = new JLabel(
				i18n.get("foss.gui.creating.submitConfirmDialog.totalCost.label")
						+ "：");
		panel.add(label8, "12, 30, left, default");

		totalCost = new JTextField();
		totalCost.setEnabled(false);
		panel.add(totalCost, "16, 30, fill, default");
		totalCost.setColumns(TEN);

		// 重量/体积/件数
		JLabel label9 = new JLabel(
				i18n.get("foss.gui.creating.submitConfirmDialog.weightPieceVolume.label")
						+ "：");
		panel.add(label9, "20, 30, left, default");

		txtWeightPieceVolume = new JTextField();
		txtWeightPieceVolume.setEnabled(false);
		panel.add(txtWeightPieceVolume, "22, 30, fill, default");
		txtWeightPieceVolume.setColumns(TEN);

		// 代收货款
		JLabel label4 = new JLabel(
				i18n.get("foss.gui.creating.submitConfirmDialog.agentPayment.label")
						+ "：");
		panel.add(label4, "4, 32, 3, 1, left, default");

		txtAgentPayment = new JTextField();
		txtAgentPayment.setEnabled(false);
		panel.add(txtAgentPayment, "7, 32, 4, 1");
		txtAgentPayment.setColumns(TEN);

		// 预付总运费
		JLabel label10 = new JLabel(
				i18n.get("foss.gui.creating.submitConfirmDialog.prepTotalCost.label")
						+ "：");
		panel.add(label10, "12, 32, left, default");

		txtPrepTotalCost = new JTextField();
		txtPrepTotalCost.setEnabled(false);
		panel.add(txtPrepTotalCost, "16, 32, fill, default");
		txtPrepTotalCost.setColumns(TEN);

		// 包装
		JLabel label11 = new JLabel(
				i18n.get("foss.gui.creating.submitConfirmDialog.packaging.label")
						+ "：");
		panel.add(label11, "20, 32, left, default");

		txtPackaging = new JTextField();
		txtPackaging.setEnabled(false);
		panel.add(txtPackaging, "22, 32, fill, default");
		txtPackaging.setColumns(TEN);

		// 保险价值
		JLabel label5 = new JLabel(
				i18n.get("foss.gui.creating.submitConfirmDialog.insuredAmount.label")
						+ "：");
		panel.add(label5, "4, 34, 3, 1, left, default");

		txtInsuredAmount = new JTextField();
		txtInsuredAmount.setEnabled(false);
		panel.add(txtInsuredAmount, "7, 34, 4, 1, fill, default");
		txtInsuredAmount.setColumns(TEN);

		JSeparator separator1 = new JSeparator();
		panel.add(separator1, "4, 36, 20, 1");

		// 收货人名称
		JLabel lblNewLabel2 = new JLabel(
				i18n.get("foss.gui.creating.submitConfirmDialog.consignee.label")
						+ "：");
		panel.add(lblNewLabel2, "4, 38, 3, 1, left, default");

		txtConsignee = new JTextField();
		txtConsignee.setEnabled(false);
		panel.add(txtConsignee, "7, 38, 6, 1, fill, default");
		txtConsignee.setColumns(TEN);

		// 收货人手机/电话
		JLabel label6 = new JLabel(
				i18n.get("foss.gui.creating.submitConfirmDialog.consigneePhone.label")
						+ "：");
		panel.add(label6, "4, 40, 3, 1, left, default");

		txtConsigneePhone = new JTextField();
		txtConsigneePhone.setEnabled(false);
		panel.add(txtConsigneePhone, "7, 40, 6, 1, fill, default");
		txtConsigneePhone.setColumns(TEN);

		// 收货人地址
		JLabel label7 = new JLabel(
				i18n.get("foss.gui.creating.submitConfirmDialog.consigneeAddress.label")
						+ "：");
		panel.add(label7, "4, 42, 3, 1, left, default");

		txtConsigneeAddress = new JTextField();
		txtConsigneeAddress.setEnabled(false);
		panel.add(txtConsigneeAddress, "7, 42, 10, 1, fill, default");
		txtConsigneeAddress.setColumns(TEN);

		JSeparator separator2 = new JSeparator();
		panel.add(separator2, "4, 44, 20, 1");

		// 打印运单
		chbPrintWaybill = new JCheckBox(
				i18n.get("foss.gui.creating.submitConfirmDialog.printWaybill.label")
						+ "：");
		chbPrintWaybill.setSelected(true);
		panel.add(chbPrintWaybill, "6, 46, 2, 1, right, default");
		printTemplates = new JComboBox(model);
		printTemplates.setToolTipText("");
		panel.add(printTemplates, "8, 46, 5, 1, fill, default");

		// 打印标签
		chbPrintLabel = new JCheckBox(
				i18n.get("foss.gui.creating.submitConfirmDialog.printLabel.label"));
		if (vo.getIsWholeVehicle()) {
			chbPrintLabel.setSelected(false);
			chbPrintLabel.setEnabled(false);
		} else {
			chbPrintLabel.setSelected(true);
			chbPrintLabel.setEnabled(true);
		}
		panel.add(chbPrintLabel, "16, 46, left, default");

		/**
		 * 只有营业部开单和集中开单才会有打木标签勾选框
		 */
		if(waybillEditUI.getWaybillType().equals(WaybillConstants.WAYBILL_SALE_DEPARTMENT) || waybillEditUI.getWaybillType().equals(WaybillConstants.WAYBILL_FOCUS)){
			//打印木标签
			woodpackaging = new JCheckBox(i18n.get("foss.gui.creating.submitConfirmDialog.printLabel.labelwoodpackaging"));
			woodpackaging.setSelected(true);
			woodpackaging.setEnabled(true);
			panel.add(woodpackaging, "16, 48, 2, 1, left, default");
		}
		
		// 确定后新增运单
		chbNewAdd = new JCheckBox(
				i18n.get("foss.gui.creating.submitConfirmDialog.newAdd.label"));
		chbNewAdd.setSelected(true);
		panel.add(chbNewAdd, "20, 46, 3, 1, center, default");

		btnConfirm = new JButton(
				i18n.get("foss.gui.creating.waybillEditUI.common.confirm"));
		panel.add(btnConfirm, "7, 48, 6, 1, center, default");

		getContentPane().add(new JScrollPane(panel), BorderLayout.CENTER);
		// 增加“确定”按钮的监听事件

		btnCancel = new JButton(
				i18n.get("foss.gui.creating.waybillEditUI.common.cancel"));
		panel.add(btnCancel, "20, 48, 3, 1, center, default");

		setSize(NUM_820, NUM_725);

		setModal(true);
	}

	/**
	 * 
	 * 按钮绑定
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-5 上午11:43:46
	 */
	private void buttonBind() {
		ButtonActionFactory.getIntsance().bindButtonAction(this);
	}

	/**
	 * 
	 * 初始化控件的数据
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-2 上午10:44:27
	 */
	private void initData() {
		lblWaybillNum.setText(vo.getWaybillNo());// 运单号
		txtLandingstation.setText(vo.getCustomerPickupOrgName());// 提货网点
		txtPaymentWay.setText(vo.getPaidMethod().getValueName());// 付款方式
		txtGoodsName.setText(vo.getGoodsName());// 货物名称
		txtPickUp.setText(vo.getReceiveMethod().getValueName());// 提货方式
		totalCost.setText(vo.getToPayAmount().toString());// 到付总运费
		String str = vo.getGoodsWeightTotal() + "/" + vo.getGoodsVolumeTotal()
				+ "/" + vo.getGoodsQtyTotal();
		txtWeightPieceVolume.setText(str);// 重量/体积/件数：
		txtAgentPayment.setText(vo.getCodAmount().toString());// 代收货款
		txtPrepTotalCost.setText(vo.getPrePayAmount().toString());// 预付总运费
		txtPackaging.setText(getPackage(vo));// 包装
		txtInsuredAmount.setText(vo.getInsuranceAmount().toString());// 保险价值
		txtConsignee.setText(vo.getReceiveCustomerName());// 收货人名称
		String phone = vo.getReceiveCustomerMobilephone() + "/"
				+ vo.getReceiveCustomerPhone();
		txtConsigneePhone.setText(phone);// 收货人手机/电话
		AddressFieldDto consigneeAddress = waybillEditUI.consigneePanel
				.getTxtConsigneeArea().getAddressFieldDto();
		StringBuffer receiveCustomerAddress = new StringBuffer();
		
		//zxy 20140103 MANA-409 start 修改:增加consigneeAddress != null 判断(邮件所发bug,非此需求)
		// 国家名称
		if (consigneeAddress != null && StringUtils.isNotEmpty(consigneeAddress.getNationName()) && !WaybillConstants.CHINA.equals(consigneeAddress.getNationName())) {
			receiveCustomerAddress.append(consigneeAddress.getNationName());
			receiveCustomerAddress.append("-");
		}
		
		// 省份名称
		if (consigneeAddress != null && StringUtils.isNotEmpty(consigneeAddress.getProvinceName())) {
			receiveCustomerAddress.append(consigneeAddress.getProvinceName());
			receiveCustomerAddress.append("-");
		}

		if (consigneeAddress != null && StringUtils.isNotEmpty(consigneeAddress.getCityName())) {
			// 城市名称
			receiveCustomerAddress.append(consigneeAddress.getCityName());
			receiveCustomerAddress.append("-");
		}

		if (consigneeAddress != null && StringUtils.isNotEmpty(consigneeAddress.getCountyName())) {
			// 区域名称
			receiveCustomerAddress.append(consigneeAddress.getCountyName());
			receiveCustomerAddress.append("-");
		}
		//zxy 20140103 MANA-409 end 修改:增加consigneeAddress != null 判断(邮件所发bug,非此需求)
		
		if (StringUtils.isNotEmpty(vo.getReceiveCustomerAddress())) {
			receiveCustomerAddress.append(vo.getReceiveCustomerAddress());
		}
		txtConsigneeAddress.setText(receiveCustomerAddress.toString());// 收货人地址

		// off line ------------------------------------------------
		lblWaybillNumOff.setText(importvo.getWaybillNo());// 运单号
		txtLandingstationOff.setText(importvo.getCustomerPickupOrgName());// 提货网点
		if (importvo.getPaidMethod() != null) {
			txtPaymentWayOff.setText(importvo.getPaidMethod().getValueName());// 付款方式
		}
		txtGoodsNameOff.setText(importvo.getGoodsName());// 货物名称
		if (importvo.getReceiveMethod() != null) {
			txtPickUpOff.setText(importvo.getReceiveMethod().getValueName());// 提货方式
		}
		if (importvo.getToPayAmount() != null) {
			totalCostOff.setText(importvo.getToPayAmount().toString());// 到付总运费
		}
		String strOff = importvo.getGoodsWeightTotal() + "/"
				+ importvo.getGoodsVolumeTotal() + "/"
				+ importvo.getGoodsQtyTotal();
		txtWeightPieceVolumeOff.setText(strOff);// 重量/体积/件数：
		if (importvo.getCodAmount() != null) {
			txtAgentPaymentOff.setText(importvo.getCodAmount().toString());// 代收货款
		}
		if (importvo.getPrePayAmount() != null) {
			txtPrepTotalCostOff.setText(importvo.getPrePayAmount().toString());// 预付总运费
		}
		txtPackagingOff.setText(getPackage(importvo));// 包装
		if (importvo.getInsuranceAmount() != null) {
			txtInsuredAmountOff.setText(importvo.getInsuranceAmount()
					.toString());// 保险价值
		}
		txtConsigneeOff.setText(importvo.getReceiveCustomerName());// 收货人名称
		String phoneOff = importvo.getReceiveCustomerMobilephone() + "/"
				+ importvo.getReceiveCustomerPhone();
		txtConsigneePhoneOff.setText(phoneOff);// 收货人手机/电话
		StringBuffer consigneeAddressOff = new StringBuffer("");
		// 国家名称
		if (StringUtils.isNotEmpty(importvo.getReceiveCustomerNationCode())) {
			String name = getProvsNameFromAddressService(importvo
					.getReceiveCustomerNationCode());
			if(!WaybillConstants.CHINA.equals(name)){
				consigneeAddressOff.append(name);
				consigneeAddressOff.append("-");
			}
		}
		// 省份名称
		if (StringUtils.isNotEmpty(importvo.getReceiveCustomerProvCode())) {
			String name = getProvsNameFromAddressService(importvo
					.getReceiveCustomerProvCode());
			consigneeAddressOff.append(name);
			consigneeAddressOff.append("-");
		}

		if (StringUtils.isNotEmpty(importvo.getReceiveCustomerCityCode())) {
			// 城市名称
			String name = getAddressNameFromAddressService(importvo
					.getReceiveCustomerCityCode());
			consigneeAddressOff.append(name);
			consigneeAddressOff.append("-");
		}

		if (StringUtils.isNotEmpty(importvo.getReceiveCustomerDistCode())) {
			// 区域名称
			String name = getCountryNameFromAddressService(importvo
					.getReceiveCustomerDistCode());
			consigneeAddressOff.append(name);
			consigneeAddressOff.append("-");
		}

		if (StringUtils.isNotEmpty(vo.getReceiveCustomerAddress())) {
			consigneeAddressOff.append(vo.getReceiveCustomerAddress());
		}
		txtConsigneeAddressOff.setText(consigneeAddressOff.toString());// 收货人地址

	}

	/**
	 * get address name from service
	 * 
	 * @param dataService
	 * @param activeTmp
	 * @return
	 */
	private static String getCountryNameFromAddressService(String code) {

		IAddressService addService = GuiceContextFactroy.getInjector()
				.getInstance(AddressService.class);

		String name = addService.queryCountyByCode(code);

		if (name != null) {
			return name;
		} else {
			return "";
		}

	}

	/**
	 * get address name from service
	 * 
	 * @param dataService
	 * @param activeTmp
	 * @return
	 */
	private static String getProvsNameFromAddressService(String code) {

		IAddressService addService = GuiceContextFactroy.getInjector()
				.getInstance(AddressService.class);

		String name = addService.queryProviceByCode(code);

		if (name != null) {
			return name;
		} else {
			return "";
		}

	}

	/**
	 * get address name from service
	 * 
	 * @param dataService
	 * @param activeTmp
	 * @return
	 */
	private static String getAddressNameFromAddressService(String code) {

		IAddressService addService = GuiceContextFactroy.getInjector()
				.getInstance(AddressService.class);

		String name = addService.queryCityByCode(code);

		if (name != null) {
			return name;
		} else {
			return "";
		}

	}

	/**
	 * 
	 * 获取包装
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-29 上午11:05:50
	 */
	private String getPackage(WaybillPanelVo vo) {
		String pack = "";
		if (vo.getPaper() != null && vo.getPaper().intValue() != 0) {
			pack = pack + vo.getPaper()
					+ i18n.get("foss.gui.creating.cargoInfoPanel.paper.label");
		}

		if (vo.getWood() != null && vo.getWood().intValue() != 0) {
			pack = pack + vo.getWood()
					+ i18n.get("foss.gui.creating.cargoInfoPanel.wood.label");
		}

		if (vo.getFibre() != null && vo.getFibre().intValue() != 0) {
			pack = pack + vo.getFibre()
					+ i18n.get("foss.gui.creating.cargoInfoPanel.fiber.label");
		}

		if (vo.getSalver() != null && vo.getSalver().intValue() != 0) {
			pack = pack + vo.getSalver()
					+ i18n.get("foss.gui.creating.cargoInfoPanel.salver.label");
		}

		if (vo.getMembrane() != null && vo.getMembrane().intValue() != 0) {
			pack = pack
					+ vo.getMembrane()
					+ i18n.get("foss.gui.creating.cargoInfoPanel.membrane.label");
		}

		if (StringUtils.isNotEmpty(vo.getOtherPackage())) {
			pack = pack + vo.getOtherPackage();
		}

		vo.setGoodsPackage(pack);
		return vo.getGoodsPackage();
	}

	/**
	 * getVo
	 * 
	 * @return
	 */
	public WaybillPanelVo getVo() {
		return vo;
	}

	/**
	 * getWaybillEditUI
	 * 
	 * @return
	 */
	public WaybillEditUI getWaybillEditUI() {
		return waybillEditUI;
	}

	/**
	 * getChbPrintWaybill
	 * 
	 * @return
	 */
	public JCheckBox getChbPrintWaybill() {
		return chbPrintWaybill;
	}

	/**
	 * getChbPrintLabel
	 * 
	 * @return
	 */
	public JCheckBox getChbPrintLabel() {
		return chbPrintLabel;
	}

	/**
	 * getChbNewAdd
	 * 
	 * @return
	 */
	public JCheckBox getChbNewAdd() {
		return chbNewAdd;
	}

	public JComboBox getPrintTemplates() {
		return printTemplates;
	}

	public JCheckBox getWoodpackaging() {
		return woodpackaging;
	}

	public void setWoodpackaging(JCheckBox woodpackaging) {
		this.woodpackaging = woodpackaging;
	}

	

}