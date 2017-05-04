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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/ui/SubmitConfirmDialog.java
 * 
 * FILE NAME        	: SubmitConfirmDialog.java
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
 * FILE    NAME: SubmitConfirmDialog.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.pickup.creating.client.ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.annotation.ButtonAction;
import com.deppon.foss.framework.client.component.buttonaction.factory.ButtonActionFactory;
import com.deppon.foss.framework.client.component.focuspolicy.annotation.ContainerSeq;
import com.deppon.foss.framework.client.component.focuspolicy.annotation.FocusSeq;
import com.deppon.foss.framework.client.component.focuspolicy.factory.FocusPolicyFactory;
import com.deppon.foss.framework.client.core.context.ApplicationContext;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.action.DialogCloseAllAction;
import com.deppon.foss.module.pickup.creating.client.action.WaybillSubmitConfirmAction;
import com.deppon.foss.module.pickup.creating.client.common.Common;
import com.deppon.foss.module.pickup.creating.client.vo.PrintTemplatesModel;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.dto.AddressFieldDto;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * “确认提交”弹出界面
 * 
 * @author 026123-foss-lifengteng
 * @date 2012-10-29 下午3:04:23
 */
@ContainerSeq(seq=1)
public class SubmitConfirmDialog extends JDialog {

	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = -5468494371986344815L;

	//preview png
	private static final String PREVIEWPNG = "preview.png";

	private static final int HEIGHT450 = 450;

	private static final int WIDTH820 = 820;

	private static final int TEN = 10;

	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(SubmitConfirmDialog.class); 

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
	
	// 是否显示“标签显示德邦物流”
    private JCheckBox chckbxPrintCheckBox;

	// 收货人名称
	private JTextField txtConsignee;

	// 收货人手机/电话
	private JTextField txtConsigneePhone;

	// 收货人地址
	private JTextField txtConsigneeAddress;

	// 运单号
	private JLabel lblWaybillNum;
	
	@ButtonAction(icon = PREVIEWPNG, shortcutKey = "", type = WaybillSubmitConfirmAction.class)
	@FocusSeq(seq = 2)
	private JButton btnConfirm;

	@ButtonAction(icon = PREVIEWPNG, shortcutKey = "", type = DialogCloseAllAction.class)
	private JButton btnCancel;
	
	@FocusSeq(seq = 1)
	private JCheckBox chbPrintWaybill;
	
	private JCheckBox chbPrintLabel;
	
	private JCheckBox chbNewAdd;
	
	//木标签
	private JCheckBox woodpackaging;
	
	public JCheckBox getWoodpackaging() {
		return woodpackaging;
	}

	public void setWoodpackaging(JCheckBox woodpackaging) {
		this.woodpackaging = woodpackaging;
	}
	
	//运单打印模版 选择
	private JComboBox printTemplates;
	
	// 面板
	private JPanel panel = new JPanel();

	//vo
	private WaybillPanelVo vo;

	//ui
	private WaybillEditUI waybillEditUI;

	// 构造方法
	public SubmitConfirmDialog(WaybillPanelVo vo, WaybillEditUI waybillEditUI) {
		super(ApplicationContext.getApplication().getWorkbench().getFrame());
		this.waybillEditUI = waybillEditUI;
		this.vo = vo;
		init();
		buttonBind();
	}

	public void init() {

		setBounds(NumberConstants.NUMBER_100, NumberConstants.NUMBER_100, NumberConstants.NUMBER_700, NumberConstants.NUMBER_900);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("left:10dlu"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("left:51dlu"),
				ColumnSpec.decode("19dlu"),
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("left:50dlu"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("left:50dlu:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("left:68dlu"),
				ColumnSpec.decode("10dlu"),
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("10dlu"),},
			new RowSpec[] {
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
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));

		//注意事项
		JLabel label2 = new JLabel(i18n.get("foss.gui.creating.submitConfirmDialog.warn.info"));
		panel.add(label2, "2, 2, 21, 1");

		JSeparator separator = new JSeparator();
		panel.add(separator, "2, 4, 20, 1, default, center");

		//单号
		JLabel label = new JLabel(i18n.get("foss.gui.creating.submitConfirmDialog.waybillNo.label")+"：");
		label.setFont(new Font("宋体", Font.BOLD, NumberConstants.NUMBER_14));
		panel.add(label, "4, 6");

		lblWaybillNum = new JLabel("");
		lblWaybillNum.setFont(new Font("宋体", Font.BOLD, NumberConstants.NUMBER_14));
		panel.add(lblWaybillNum, "5, 6, 6, 1");

		//提货网点
		JLabel label1 = new JLabel(i18n.get("foss.gui.creating.submitConfirmDialog.landingstation.label")+"：");
		panel.add(label1, "2, 8, 3, 1, left, default");

		txtLandingstation = new JTextField();
		txtLandingstation.setEnabled(false);
		panel.add(txtLandingstation, "5, 8, 4, 1, fill, default");
		txtLandingstation.setColumns(TEN);

		//付款方式
		JLabel lblNewLabel = new JLabel(i18n.get("foss.gui.creating.submitConfirmDialog.paymentWay.label")+"：");
		panel.add(lblNewLabel, "10, 8, left, fill");

		txtPaymentWay = new JTextField();
		txtPaymentWay.setEnabled(false);
		panel.add(txtPaymentWay, "14, 8, fill, default");
		txtPaymentWay.setColumns(TEN);

		//货物名称
		JLabel lblNewLabel1 = new JLabel(i18n.get("foss.gui.creating.submitConfirmDialog.goodsName.label")+"：");
		panel.add(lblNewLabel1, "18, 8, left, default");

		txtGoodsName = new JTextField();
		txtGoodsName.setEnabled(false);
		panel.add(txtGoodsName, "20, 8, fill, default");
		txtGoodsName.setColumns(TEN);

		//提货方式
		JLabel label3 = new JLabel(i18n.get("foss.gui.creating.submitConfirmDialog.pickUp.label")+"：");
		panel.add(label3, "2, 10, 3, 1, left, default");

		txtPickUp = new JTextField();
		txtPickUp.setEnabled(false);
		panel.add(txtPickUp, "5, 10, 4, 1, fill, default");
		txtPickUp.setColumns(TEN);

		//到付总运费
		JLabel label8 = new JLabel(i18n.get("foss.gui.creating.submitConfirmDialog.totalCost.label")+"：");
		panel.add(label8, "10, 10, left, default");

		totalCost = new JTextField();
		totalCost.setEnabled(false);
		panel.add(totalCost, "14, 10, fill, default");
		totalCost.setColumns(TEN);

		//重量/体积/件数
		JLabel label9 = new JLabel(i18n.get("foss.gui.creating.submitConfirmDialog.weightPieceVolume.label")+"：");
		panel.add(label9, "18, 10, left, default");

		txtWeightPieceVolume = new JTextField();
		txtWeightPieceVolume.setEnabled(false);
		panel.add(txtWeightPieceVolume, "20, 10, fill, default");
		txtWeightPieceVolume.setColumns(TEN);

		//代收货款
		JLabel label4 = new JLabel(i18n.get("foss.gui.creating.submitConfirmDialog.agentPayment.label")+"：");
		panel.add(label4, "2, 12, 3, 1, left, default");

		txtAgentPayment = new JTextField();
		txtAgentPayment.setEnabled(false);
		panel.add(txtAgentPayment, "5, 12, 4, 1");
		txtAgentPayment.setColumns(TEN);

		//预付总运费
		JLabel label10 = new JLabel(i18n.get("foss.gui.creating.submitConfirmDialog.prepTotalCost.label")+"：");
		panel.add(label10, "10, 12, left, default");

		txtPrepTotalCost = new JTextField();
		txtPrepTotalCost.setEnabled(false);
		panel.add(txtPrepTotalCost, "14, 12, fill, default");
		txtPrepTotalCost.setColumns(TEN);

		//包装
		JLabel label11 = new JLabel(i18n.get("foss.gui.creating.submitConfirmDialog.packaging.label")+"：");
		panel.add(label11, "18, 12, left, default");

		txtPackaging = new JTextField();
		txtPackaging.setEnabled(false);
		panel.add(txtPackaging, "20, 12, fill, default");
		txtPackaging.setColumns(TEN);

		//保险价值
		JLabel label5 = new JLabel(i18n.get("foss.gui.creating.submitConfirmDialog.insuredAmount.label")+"：");
		panel.add(label5, "2, 14, 3, 1, left, default");

		txtInsuredAmount = new JTextField();
		txtInsuredAmount.setEnabled(false);
		panel.add(txtInsuredAmount, "5, 14, 4, 1, fill, default");
		txtInsuredAmount.setColumns(TEN);

		JSeparator separator1 = new JSeparator();
		panel.add(separator1, "2, 16, 20, 1");

		//是否显示德邦
		chckbxPrintCheckBox = new JCheckBox(i18n.get("foss.gui.creating.printSignUI.allotype.isPrintLabelLogo"));
		chckbxPrintCheckBox.setSelected(true);
		panel.add(chckbxPrintCheckBox, "16, 26");

		//收货人名称
		JLabel lblNewLabel2 = new JLabel(i18n.get("foss.gui.creating.submitConfirmDialog.consignee.label")+"：");
		panel.add(lblNewLabel2, "2, 18, 3, 1, left, default");

		txtConsignee = new JTextField();
		txtConsignee.setEnabled(false);
		panel.add(txtConsignee, "5, 18, 6, 1, fill, default");
		txtConsignee.setColumns(TEN);

		//收货人手机/电话
		JLabel label6 = new JLabel(i18n.get("foss.gui.creating.submitConfirmDialog.consigneePhone.label")+"：");
		panel.add(label6, "2, 20, 3, 1, left, default");

		txtConsigneePhone = new JTextField();
		txtConsigneePhone.setEnabled(false);
		panel.add(txtConsigneePhone, "5, 20, 6, 1, fill, default");
		txtConsigneePhone.setColumns(TEN);

		//收货人地址
		JLabel label7 = new JLabel(i18n.get("foss.gui.creating.submitConfirmDialog.consigneeAddress.label")+"：");
		panel.add(label7, "2, 22, 3, 1, left, default");

		txtConsigneeAddress = new JTextField();
		txtConsigneeAddress.setEnabled(false);
		panel.add(txtConsigneeAddress, "5, 22, 10, 1, fill, default");
		txtConsigneeAddress.setColumns(TEN);

		JSeparator separator2 = new JSeparator();
		panel.add(separator2, "2, 24, 20, 1");

		//打印运单
		chbPrintWaybill = new JCheckBox(i18n.get("foss.gui.creating.submitConfirmDialog.printWaybill.label")+"：");
	
		panel.add(chbPrintWaybill, "4, 26, 2, 1, right, default");
		if(StringUtils.isNotBlank(waybillEditUI.getPictureWaybillType()) && 
				WaybillConstants.WAYBILL_PICTURE.equals(waybillEditUI.getPictureWaybillType())){
			chbPrintWaybill.setSelected(false);
		}else{
			chbPrintWaybill.setSelected(true);
		}

		Vector model = new Vector();
		model.addElement( new PrintTemplatesModel(WaybillConstants.PRINTTEMPLATES_ONE,WaybillConstants.PRINTTEMPLATES_VALUE_ONE) );
		model.addElement(new PrintTemplatesModel(WaybillConstants.PRINTTEMPLATES_HONGKONG_ONE, WaybillConstants.PRINTTEMPLATES_VALUE_HONGKONG_ONE));
		//ComboBoxModel model = new DefaultComboBoxModel(new Object[]{ 
		//new PrintTemplatesModel(WaybillConstants.PRINTTEMPLATES_ONE,WaybillConstants.PRINTTEMPLATES_VALUE_ONE)});
		//model.addElement(new PrintTemplatesModel(WaybillConstants.PRINTTEMPLATES_ONE,WaybillConstants.PRINTTEMPLATES_VALUE_ONE));
		printTemplates = new JComboBox(model);
		
		printTemplates.setToolTipText("");
		panel.add(printTemplates, "6, 26, 5, 1, fill, default");

		//打印标签
		chbPrintLabel = new JCheckBox(i18n.get("foss.gui.creating.submitConfirmDialog.printLabel.label"));
		if (vo.getIsWholeVehicle()) {
			chbPrintLabel.setSelected(false);
			chbPrintLabel.setEnabled(false);
		} else {
			chbPrintLabel.setSelected(true);
			chbPrintLabel.setEnabled(true);
		}		
		panel.add(chbPrintLabel, "14, 26, left, default");

		/**
		 * 只有营业部开单和集中开单才会有打木标签勾选框
		 */
		if(!WaybillConstants.WAYBILL_PICTURE.equals(waybillEditUI.getPictureWaybillType())){
			//打印木标签
			woodpackaging = new JCheckBox(i18n.get("foss.gui.creating.submitConfirmDialog.printLabel.labelwoodpackaging"));
			woodpackaging.setSelected(true);
			woodpackaging.setEnabled(true);
			panel.add(woodpackaging, "14, 28, 2, 1, left, default");
		}
				
		//确定后新增运单
		chbNewAdd = new JCheckBox(i18n.get("foss.gui.creating.submitConfirmDialog.newAdd.label"));
		if(StringUtils.isNotBlank(waybillEditUI.getPictureWaybillType()) && 
				WaybillConstants.WAYBILL_PICTURE.equals(waybillEditUI.getPictureWaybillType())){
			chbNewAdd.setSelected(false);
			chbNewAdd.setEnabled(false);
			chbPrintLabel.setSelected(false);
			chbPrintLabel.setEnabled(true);
		}else{
		    chbNewAdd.setSelected(true);
			chbPrintLabel.setSelected(true);
			chbPrintLabel.setEnabled(true);
		}
		panel.add(chbNewAdd, "18, 26, 3, 1, center, default");
		
		//确  定
		btnConfirm = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.confirm"));
		panel.add(btnConfirm, "5, 28, 6, 1, center, default");

		initData();

		getContentPane().add(panel, BorderLayout.NORTH);
		// 增加“确定”按钮的监听事件

		//btnConfirm.addActionListener(new ConfirmHandler());
		
		//取  消
		btnCancel = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.cancel"));
		panel.add(btnCancel, "18, 28, 3, 1, center, default");

		setSize(WIDTH820, HEIGHT450);
		
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
		FocusPolicyFactory.getIntsance().setFocusTraversalPolicy(this);
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
		txtPaymentWay.setText(vo.getPaidMethod().getValueName());//付款方式
		txtGoodsName.setText(vo.getGoodsName());// 货物名称
		txtPickUp.setText(vo.getReceiveMethod().getValueName());//提货方式
		totalCost.setText(vo.getToPayAmount().toString());// 到付总运费
		String str = vo.getGoodsWeightTotal() + "/" + vo.getGoodsVolumeTotal()
				+ "/" + vo.getGoodsQtyTotal();
		txtWeightPieceVolume.setText(str);// 重量/体积/件数：
		txtAgentPayment.setText(vo.getCodAmount().toString());// 代收货款
		txtPrepTotalCost.setText(vo.getPrePayAmount().toString());// 预付总运费
		txtPackaging.setText(Common.getPackage(vo));// 包装
		txtInsuredAmount.setText(vo.getInsuranceAmount().toString());// 保险价值
		txtConsignee.setText(vo.getReceiveCustomerContact());// 收货人名称
		StringBuffer phone = new StringBuffer();
		if(StringUtils.isNotEmpty(vo.getReceiveCustomerMobilephone()))
		{
			phone.append(vo.getReceiveCustomerMobilephone());
		}
		
		if(StringUtils.isNotEmpty(vo.getReceiveCustomerPhone()))
		{
			if(StringUtils.isEmpty(phone.toString()))
			{
				phone.append(vo.getReceiveCustomerPhone());
			}else
			{
				phone.append("/");
				phone.append(vo.getReceiveCustomerPhone());
			}
		}
		txtConsigneePhone.setText(phone.toString());// 收货人手机/电话
		AddressFieldDto consigneeAddress = waybillEditUI.consigneePanel.
		getTxtConsigneeArea().getAddressFieldDto();
		if(null != consigneeAddress){
			StringBuffer receiveCustomerAddress = new StringBuffer();
			//国家名称
			if(StringUtils.isNotEmpty(consigneeAddress.getNationName()) && !WaybillConstants.CHINA.equals(consigneeAddress.getNationName())){
				receiveCustomerAddress.append(consigneeAddress.getNationName());
				receiveCustomerAddress.append("-");
			}
			//省份名称
			if(StringUtils.isNotEmpty(consigneeAddress.getProvinceName()))
			{
				receiveCustomerAddress.append(consigneeAddress.getProvinceName());
				receiveCustomerAddress.append("-");
			}

			if(StringUtils.isNotEmpty(consigneeAddress.getCityName())){
				//城市名称
				receiveCustomerAddress.append(consigneeAddress.getCityName());
				receiveCustomerAddress.append("-");
			}

			if(StringUtils.isNotEmpty(consigneeAddress.getCountyName())){
				//区域名称
				receiveCustomerAddress.append(consigneeAddress.getCountyName());
				receiveCustomerAddress.append("-");
			}
			
			if(StringUtils.isNotEmpty(vo.getReceiveCustomerAddress())){
				receiveCustomerAddress.append(vo.getReceiveCustomerAddress());
			}
			txtConsigneeAddress.setText(receiveCustomerAddress.toString());// 收货人地址
		}
	}
	
	

	/**
	 * getVo
	 * @return
	 */
	public WaybillPanelVo getVo() {
		return vo;
	}

	/**
	 * getWaybillEditUI
	 * @return
	 */
	public WaybillEditUI getWaybillEditUI() {
		return waybillEditUI;
	}
	
	/**
	 * getChbPrintWaybill
	 * @return
	 */
	public JCheckBox getChbPrintWaybill() {
		return chbPrintWaybill;
	}
	//为了在界面获取加的方法
	public JCheckBox getChckbxPrintCheckBox() {
		return chckbxPrintCheckBox;
	}

	/**
	 * getChbPrintLabel
	 * @return
	 */
	public JCheckBox getChbPrintLabel() {
		return chbPrintLabel;
	}
	
	/**
	 * getChbNewAdd
	 * @return
	 */
	public JCheckBox getChbNewAdd() {
		return chbNewAdd;
	}

	public JComboBox getPrintTemplates() {
		return printTemplates;
	}
	
	/**
	 * Enter 回车事件
	 * @author 
	 *
	 */
	 /*private class ConfirmHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
				initData();
		}
	}*/
}