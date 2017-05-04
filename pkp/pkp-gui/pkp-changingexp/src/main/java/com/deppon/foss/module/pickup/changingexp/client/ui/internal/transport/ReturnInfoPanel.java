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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/ui/internal/transport/ReturnInfoPanel.java
 * 
 * FILE NAME        	: ReturnInfoPanel.java
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
package com.deppon.foss.module.pickup.changingexp.client.ui.internal.transport;

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
import com.deppon.foss.module.pickup.changingexp.client.action.ShowPickupStationDialogAction;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * 
 * 返货信息面板
 * @author 102246-foss-shaohongliang
 * @date 2012-12-25 上午9:36:30
 */
public class ReturnInfoPanel extends JPanel {

	/**
	 *  国际化
	 */
	private static II18n i18n = I18nManager.getI18n(ReturnInfoPanel.class);

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
	 * 10 FOR COLUMN NUMBER
	 */
	private static final int TEN = 10;

	private static final int EIGHTEEN = 18;
	
	/**
	 * DEFAULT CSS
	 */
	private static final String DEFAULTGROW = "default:grow";

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = -3237841041309707273L;

	/**
	 * 返货目的站
	 */
	@Bind("rtnTargetOrgCode")
	private JTextFieldValidate txtReturnDestination;

	/**
	 * 返货提货网点
	 */
	@Bind("rtnCustomerPickupOrgName")
	private JTextFieldValidate txtReturnPickBranch;

	/**
	 * 返货费率
	 */
	@Bind("rtnUnitPrice")
	private JTextFieldValidate txtReturnUnitPrice;

	/**
	 * 返货费
	 */
	@Bind("rtnFee")
	private JTextFieldValidate txtReturnFee;

	/**
	 * 返货提货方式
	 */
	@Bind("rtnReceiveMethod")
	private JComboBox combReturnAllocationType;

	/**
	 * 返货运输性质
	 */
	@Bind("rtnProductCode")
	private JComboBox combReturnProductType;

	/**
	 * 返货计费类型
	 */
	@Bind("rtnBillingType")
	private JComboBox combReturnBillingType;

	/**
	 * 返货运输性质Model
	 */
	private DefaultComboBoxModel returnProductTypeModel;
	
	/**
	 * 返货提货方式Model
	 */
	private DefaultComboBoxModel pickModeModel;

	/**
	 * 返货计费类型Model
	 */
	private DefaultComboBoxModel returnBillingTypeModel;
	
	/**
	 * 查询目的站放大镜
	 */
	@ButtonAction(icon = "query.png", shortcutKey = "", type = ShowPickupStationDialogAction.class)
	private JButton btnQueryBranch;

	/**
	 * 
	 * 构造返货页面
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午9:38:32
	 */
	public ReturnInfoPanel() {
		init();
	}

	/**
	 * 
	 * 初始化布局
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午9:38:45
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
				FormFactory.BUTTON_COLSPEC,
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
				FormFactory.RELATED_GAP_ROWSPEC,});
		formLayout.setColumnGroups(new int[][]{new int[]{FOUR, EIGHT, TWELVE, EIGHTEEN}});
		setLayout(formLayout);

		//返货运输性质：
		JLabel lblReturnProductType = new JLabel(i18n.get("foss.gui.changingexp.transportInfoPanel.returnProductType.label")+"：");
		add(lblReturnProductType, "2, 2, right, default");

		combReturnProductType = new JComboBox();
		returnProductTypeModel = (DefaultComboBoxModel)combReturnProductType.getModel();
		add(combReturnProductType, "4, 2, fill, default");
		
		//返货提货方式：
		JLabel lblNewLabel2 = new JLabel(i18n.get("foss.gui.changingexp.transportInfoPanel.returnPickMode.label")+"：");
		add(lblNewLabel2, "6, 2, right, default");

		combReturnAllocationType = new JComboBox();
		pickModeModel = (DefaultComboBoxModel)combReturnAllocationType.getModel();
		add(combReturnAllocationType, "8, 2, fill, default");

		//返货目的站：
		JLabel lblReturnDestination = new JLabel(i18n.get("foss.gui.changingexp.transportInfoPanel.returnDestination.label")+"：");
		add(lblReturnDestination, "10, 2, right, default");

		txtReturnDestination = new JTextFieldValidate();
		add(txtReturnDestination, "12, 2, fill, default");
		txtReturnDestination.setColumns(TEN);
		
		//匹配提货网点
		btnQueryBranch = new JButton();
		btnQueryBranch.setMargin(new Insets(-2, 1, -2, 1));
		add(btnQueryBranch, "14, 2");

		//返货提货网点：
		JLabel lblReturnPickBranch = new JLabel(i18n.get("foss.gui.changingexp.transportInfoPanel.returnPickBranch.label")+"：");
		add(lblReturnPickBranch, "16, 2, right, default");

		txtReturnPickBranch = new JTextFieldValidate();
		add(txtReturnPickBranch, "18, 2, fill, default");
		txtReturnPickBranch.setEnabled(false);
		txtReturnPickBranch.setColumns(TEN);

		//返货计费类型：
		JLabel lblNewLabel = new JLabel(i18n.get("foss.gui.changingexp.transportInfoPanel.returnBillingType.label")+"：");
		add(lblNewLabel, "2, 4, right, default");

		combReturnBillingType = new JComboBox();
		returnBillingTypeModel = (DefaultComboBoxModel)combReturnBillingType.getModel();
		combReturnBillingType.setEnabled(false);
		add(combReturnBillingType, "4, 4, fill, default");

		//返货费率：
		JLabel lblNewLabel1 = new JLabel(i18n.get("foss.gui.changingexp.transportInfoPanel.returnUnitPrice.label")+"：");
		add(lblNewLabel1, "6, 4, right, default");

		txtReturnUnitPrice = new JTextFieldValidate();
		add(txtReturnUnitPrice, "8, 4, fill, default");
		txtReturnUnitPrice.setEnabled(false);
		txtReturnUnitPrice.setColumns(TEN);

		//返货费：
		JLabel label = new JLabel(i18n.get("foss.gui.changingexp.transportInfoPanel.returnFee.label")+"：");
		add(label, "10, 4, right, default");

		txtReturnFee = new JTextFieldValidate();
		txtReturnFee.setEnabled(false);
		add(txtReturnFee, "12, 4, fill, default");
		txtReturnFee.setColumns(TEN);

	}

	
	/**
	 * @return the txtReturnDestination
	 */
	public JTextFieldValidate getTxtReturnDestination() {
		return txtReturnDestination;
	}

	
	/**
	 * @return the txtReturnPickBranch
	 */
	public JTextFieldValidate getTxtReturnPickBranch() {
		return txtReturnPickBranch;
	}

	
	/**
	 * @return the txtReturnUnitPrice
	 */
	public JTextFieldValidate getTxtReturnUnitPrice() {
		return txtReturnUnitPrice;
	}

	
	/**
	 * @return the txtReturnFee
	 */
	public JTextFieldValidate getTxtReturnFee() {
		return txtReturnFee;
	}

	
	/**
	 * @return the combReturnAllocationType
	 */
	public JComboBox getCombReturnAllocationType() {
		return combReturnAllocationType;
	}

	
	/**
	 * @return the combReturnProductType
	 */
	public JComboBox getCombReturnProductType() {
		return combReturnProductType;
	}

	
	/**
	 * @return the combReturnBillingType
	 */
	public JComboBox getCombReturnBillingType() {
		return combReturnBillingType;
	}

	
	/**
	 * @return the returnProductTypeModel
	 */
	public DefaultComboBoxModel getReturnProductTypeModel() {
		return returnProductTypeModel;
	}

	
	/**
	 * @return the pickModeModel
	 */
	public DefaultComboBoxModel getPickModeModel() {
		return pickModeModel;
	}

	
	/**
	 * @return the returnBillingTypeModel
	 */
	public DefaultComboBoxModel getReturnBillingTypeModel() {
		return returnBillingTypeModel;
	}


	/**
	 * @return the btnQueryBranch
	 */
	public JButton getBtnQueryBranch() {
		return btnQueryBranch;
	}
}