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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changing/client/ui/internal/transport/TransferInfoPanel.java
 * 
 * FILE NAME        	: TransferInfoPanel.java
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
package com.deppon.foss.module.pickup.changing.client.ui.internal.transport;

import java.awt.Insets;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.annotation.ButtonAction;
import com.deppon.foss.framework.client.core.binding.annotation.Bind;
import com.deppon.foss.framework.client.widget.validatewidget.JTextFieldValidate;
import com.deppon.foss.module.pickup.changing.client.action.ShowPickupStationDialogAction;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.BoxLayout;

/**
 * 
 * 转运信息面板
 * @author 102246-foss-shaohongliang
 * @date 2012-12-25 上午9:42:57
 */
public class TransferInfoPanel extends JPanel {

	/**
	 *  国际化
	 */
	private static II18n i18n = I18nManager.getI18n(TransferInfoPanel.class);

	/**
	 * 10 FOR COLUMN NUMBER
	 */
	private static final int TEN = 10;

	/**
	 * 16
	 */
	private static final int SIXTEEN = 16;

	/**
	 * 12
	 */
	private static final int TWELVE = 12;

	/**
	 * 8
	 */
	private static final int EIGHT = 8;

	/**
	 * 4
	 */
	private static final int FOUR = 4;

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = -1352338064895282892L;

	/**
	 * 转运目的站
	 */
	@Bind("tfrTargetOrgCode")
	private JTextFieldValidate txtTransferDestination;

	/**
	 * 转运提货网点
	 */
	@Bind("tfrCustomerPickupOrgName")
	private JTextFieldValidate txtTransferPickBranch;

	/**
	 * 转运费率
	 */
	@Bind("tfrUnitPrice")
	private JTextFieldValidate txtTransferUnitPrice;

	/**
	 * 转运费
	 */
	@Bind("tfrFee")
	private JTextFieldValidate txtTransferFee;

	/**
	 * 转运提货方式
	 */
	@Bind("tfrReceiveMethod")
	private JComboBox combTransferAllocationType;

	/**
	 * 转运运输性质
	 */
	@Bind("tfrProductCode")
	private JComboBox combTransferProductType;

	/**
	 * 转运预配航班
	 */
	@Bind("tfrFlightNumberType")
	private JComboBox combTransferPredictFlight;

	/**
	 * 转运计费类型
	 */
	@Bind("tfrBillingType")
	private JComboBox combTransferBillingType;

	/**
	 * 转运运输性质Model
	 */
	private DefaultComboBoxModel transferProductTypeModel;

	/**
	 * 转运预配航班Model
	 */
	private DefaultComboBoxModel predictFlightModel;

	/**
	 * 转运提货方式Model
	 */
	private DefaultComboBoxModel pickModeModel;
	
	/**
	 * 转运计费类型Model
	 */
	private DefaultComboBoxModel transferBillingTypeModel;

	private DefaultComboBoxModel combFreightMethodModel;
	/**
	 * 查询目的站放大镜
	 */
	@ButtonAction(icon = "query.png", shortcutKey = "", type = ShowPickupStationDialogAction.class)
	private JButton btnQueryBranch;
	private JLabel label_1;
	//转货合票方式
	@Bind("tfrFreightMethod")
	private JComboBox combFreightMethod;
	private JLabel label_2;
	@Bind("tfrFlightShift")
	private JTextFieldValidate textFieldValidate;
	private JPanel panel;

	public JComboBox getCombFreightMethod() {
		return combFreightMethod;
	}

	/**
	 * 
	 * TransferInfoPanel
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午9:46:09
	 */
	public TransferInfoPanel() {
		init();
	}

	/**
	 * 
	 * 初始化布局
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午9:46:02
	 */
	private void init() {

		FormLayout formLayout = new FormLayout(new ColumnSpec[] {
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
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,});
		formLayout.setColumnGroups(new int[][]{new int[]{FOUR, EIGHT, TWELVE, SIXTEEN}});
		setLayout(formLayout);

		//转运运输性质：
		JLabel lblTransferProductType = new JLabel(i18n.get("foss.gui.changing.transportInfoPanel.transferProductType.label")+"：");
		add(lblTransferProductType, "2, 2, right, default");

		combTransferProductType = new JComboBox();
		add(combTransferProductType, "4, 2, fill, default");
		transferProductTypeModel = (DefaultComboBoxModel) combTransferProductType.getModel();
		
		//转运提货方式：
		JLabel lblTransferAllocationType = new JLabel(i18n.get("foss.gui.changing.transportInfoPanel.transferPickMode.label")+"：");
		add(lblTransferAllocationType, "6, 2, right, default");

		combTransferAllocationType = new JComboBox();
		pickModeModel = (DefaultComboBoxModel)combTransferAllocationType.getModel();
		add(combTransferAllocationType, "8, 2, fill, default");

		//转运目的站：
		JLabel lblTransferDestination = new JLabel(i18n.get("foss.gui.changing.transportInfoPanel.transferDestination.label")+"：");
		add(lblTransferDestination, "10, 2, right, default");
		
		panel = new JPanel();
		add(panel, "12, 2, fill, fill");
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		txtTransferDestination = new JTextFieldValidate();
		panel.add(txtTransferDestination);
		txtTransferDestination.setColumns(TEN);
		
		//匹配提货网点
		btnQueryBranch = new JButton();
		btnQueryBranch.setMargin(new Insets(-2, 1, -2, 1));
		panel.add(btnQueryBranch);

		//转运提货网点：
		JLabel lblTransferPickBranch = new JLabel(i18n.get("foss.gui.changing.transportInfoPanel.transferPickBranch.label")+"：");
		add(lblTransferPickBranch, "14, 2, right, default");

		txtTransferPickBranch = new JTextFieldValidate();
		add(txtTransferPickBranch, "16, 2, fill, default");
		txtTransferPickBranch.setEnabled(false);
		txtTransferPickBranch.setColumns(TEN);

		//转运航班类型：
		JLabel lblTransferPredictFlight = new JLabel(i18n.get("foss.gui.changing.transportInfoPanel.transferPredictFlight.label")+"：");
		add(lblTransferPredictFlight, "2, 4, right, default");

		combTransferPredictFlight = new JComboBox();
		predictFlightModel = (DefaultComboBoxModel) combTransferPredictFlight.getModel();
		add(combTransferPredictFlight, "4, 4, fill, default");

		//转运计费类型：
		JLabel lblNewLabel = new JLabel(i18n.get("foss.gui.changing.transportInfoPanel.transferBillingType.label")+"：");
		add(lblNewLabel, "6, 4, right, default");

		combTransferBillingType = new JComboBox();
		transferBillingTypeModel = (DefaultComboBoxModel)combTransferBillingType.getModel();
		combTransferBillingType.setEnabled(false);
		add(combTransferBillingType, "8, 4, fill, default");

		//转运费率：
		JLabel lblNewLabel1 = new JLabel(i18n.get("foss.gui.changing.transportInfoPanel.transferUnitPrice.label")+"：");
		add(lblNewLabel1, "10, 4, right, default");

		txtTransferUnitPrice = new JTextFieldValidate();
		txtTransferUnitPrice.setEnabled(false);
		add(txtTransferUnitPrice, "12, 4, fill, default");
		txtTransferUnitPrice.setColumns(TEN);

		//转运费：
		JLabel label = new JLabel(i18n.get("foss.gui.changing.transportInfoPanel.transferFee.label")+"：");
		add(label, "14, 4, right, default");

		txtTransferFee = new JTextFieldValidate();
		txtTransferFee.setEnabled(false);
		add(txtTransferFee, "16, 4, fill, default");
		txtTransferFee.setColumns(TEN);
		
		//合票方式：
		label_1 = new JLabel(i18n.get("foss.gui.changing.transportInfoPanel.freightMethod.label")+"：");
		add(label_1, "2, 7, right, default");
		
		combFreightMethod = new JComboBox();
		combFreightMethodModel=(DefaultComboBoxModel) combFreightMethod.getModel();
		combFreightMethod.setEnabled(false);
		add(combFreightMethod, "4, 7, fill, default");
		
		//航班时间：
		label_2 = new JLabel(i18n.get("foss.gui.changing.transportInfoPanel.flightShift.label")+"：");
		add(label_2, "6, 7, right, default");
		
		textFieldValidate = new JTextFieldValidate();
		textFieldValidate.setEnabled(false);
		textFieldValidate.setColumns(TEN);
		add(textFieldValidate, "8, 7, fill, default");

	}

	
	/**
	 * @return the txtTransferDestination
	 */
	public JTextFieldValidate getTxtTransferDestination() {
		return txtTransferDestination;
	}

	
	/**
	 * @return the txtTransferPickBranch
	 */
	public JTextFieldValidate getTxtTransferPickBranch() {
		return txtTransferPickBranch;
	}

	
	/**
	 * @return the txtTransferUnitPrice
	 */
	public JTextFieldValidate getTxtTransferUnitPrice() {
		return txtTransferUnitPrice;
	}

	
	/**
	 * @return the txtTransferFee
	 */
	public JTextFieldValidate getTxtTransferFee() {
		return txtTransferFee;
	}

	
	/**
	 * @return the combTransferAllocationType
	 */
	public JComboBox getCombTransferAllocationType() {
		return combTransferAllocationType;
	}

	
	/**
	 * @return the combTransferProductType
	 */
	public JComboBox getCombTransferProductType() {
		return combTransferProductType;
	}

	
	/**
	 * @return the combTransferPredictFlight
	 */
	public JComboBox getCombTransferPredictFlight() {
		return combTransferPredictFlight;
	}

	
	/**
	 * @return the combTransferBillingType
	 */
	public JComboBox getCombTransferBillingType() {
		return combTransferBillingType;
	}

	
	/**
	 * @return the transferProductTypeModel
	 */
	public DefaultComboBoxModel getTransferProductTypeModel() {
		return transferProductTypeModel;
	}

	
	/**
	 * @return the predictFlightModel
	 */
	public DefaultComboBoxModel getPredictFlightModel() {
		return predictFlightModel;
	}

	
	/**
	 * @return the pickModeModel
	 */
	public DefaultComboBoxModel getPickModeModel() {
		return pickModeModel;
	}

	
	/**
	 * @return the transferBillingTypeModel
	 */
	public DefaultComboBoxModel getTransferBillingTypeModel() {
		return transferBillingTypeModel;
	}

	/**
	 * @return the combFreightMethodModel
	 */
	public DefaultComboBoxModel getCombFreightMethodModel() {
		return combFreightMethodModel;
	}

	/**
	 * @return the btnQueryBranch
	 */
	public JButton getBtnQueryBranch() {
		return btnQueryBranch;
	}
	


}