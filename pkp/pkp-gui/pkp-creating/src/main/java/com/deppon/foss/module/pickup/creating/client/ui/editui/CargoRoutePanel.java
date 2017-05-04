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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/ui/editui/CargoRoutePanel.java
 * 
 * FILE NAME        	: CargoRoutePanel.java
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

import java.awt.Font;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.annotation.ButtonAction;
import com.deppon.foss.framework.client.core.binding.annotation.Bind;
import com.deppon.foss.framework.client.widget.validatewidget.JTextFieldValidate;
import com.deppon.foss.module.pickup.creating.client.action.QueryAirDepartmentAction;
import com.deppon.foss.module.pickup.waybill.shared.define.IconConstants;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
/**
 * 
 * (走货线路面板)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:Administrator,date:2012-10-17 上午11:16:57,content:
 * </p>
 * 
 * @author 025000-FOSS-helong
 * @date 2012-10-17 上午11:16:57
 * @since
 * @version
 */
public class CargoRoutePanel extends JPanel {

	private static final int TEN = 10;

	private static final long serialVersionUID = 1L;
	
	
	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(CargoRoutePanel.class);

	//预计出发时间
	@Bind("preDepartureTime")
	private JTextFieldValidate txtPreDepartureTime;

	//预计派送-提货时间
	@Bind("preCustomerPickupTime")
	private JTextFieldValidate txtPreCustomerPickupTime;

	//配载线路
	@Bind("loadLineName")
	private JTextFieldValidate txtLoadLineCode;
	
	//最终配载部门
	@Bind("lastLoadOrgName")
	private JTextFieldValidate txtLastLoadOrgName;
	
	//配载部门
	@Bind("loadOrgName")
	private JTextFieldValidate txtLoadOrgName;
	
	/**
	 * 查询空运配载部门
	 */
	@ButtonAction(icon = IconConstants.QUERY, shortcutKey = "", type = QueryAirDepartmentAction.class)
	private JButton btnQueryAirDept;

	public CargoRoutePanel() {
		init();
	}

	/**
	 * 
	 * <p>
	 * (初始化)
	 * </p>
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-16 上午10:34:39
	 * @see
	 */
	private void init() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("left:max(51dlu;default)"),
				ColumnSpec.decode("default:grow"),},
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
				FormFactory.DEFAULT_ROWSPEC,}));

		//走货路线信息
		JLabel lblNewLabel = new JLabel(i18n.get("foss.gui.creating.cargoRoutePanel.title"));
		lblNewLabel.setFont(new Font("宋体", Font.BOLD, NumberConstants.NUMBER_12));
		add(lblNewLabel, "2, 2");

		//预配线路
		JLabel lblNewLabel1 = new JLabel(i18n.get("foss.gui.creating.cargoRoutePanel.loadLineCode.label")+"：");
		add(lblNewLabel1, "2, 4, left, default");

		txtLoadLineCode = new JTextFieldValidate();
		txtLoadLineCode.setEnabled(false);
		add(txtLoadLineCode, "4, 4, 2, 1, fill, default");
		txtLoadLineCode.setColumns(TEN);

		//配载部门
		JLabel label = new JLabel(i18n.get("foss.gui.creating.cargoRoutePanel.loadOrgName.label")+"：");
		add(label, "7, 4, right, default");
		
		txtLoadOrgName = new JTextFieldValidate();
		txtLoadOrgName.setEnabled(false);
		add(txtLoadOrgName, "8, 4, fill, default");
		txtLoadOrgName.setColumns(TEN);

		//最终配载部门
		JLabel label1 = new JLabel(i18n.get("foss.gui.creating.cargoRoutePanel.lastLoadOrgName.label")+"：");
		add(label1, "2, 6, left, default");
		
		txtLastLoadOrgName = new JTextFieldValidate();
		txtLastLoadOrgName.setEnabled(false);
		add(txtLastLoadOrgName, "4, 6, fill, default");
		txtLastLoadOrgName.setColumns(TEN);
		
		//查询
		btnQueryAirDept = new JButton();
		add(btnQueryAirDept, "5, 6");
		btnQueryAirDept.setEnabled(false);
		btnQueryAirDept.setMargin(new Insets(-2, 1, -2, 1));

		//预计出发时间
		JLabel lblNewLabel2 = new JLabel(i18n.get("foss.gui.creating.cargoRoutePanel.preDepartureTime.label")+"：");
		add(lblNewLabel2, "2, 8, left, default");

		txtPreDepartureTime = new JTextFieldValidate();
		txtPreDepartureTime.setEnabled(false);
		add(txtPreDepartureTime, "4, 8, 5, 1, fill, default");
		txtPreDepartureTime.setColumns(TEN);

		//预计派送/提货时间
		JLabel lblNewLabel3 = new JLabel(i18n.get("foss.gui.creating.cargoRoutePanel.preCustomerPickupTime.label")+"：");
		add(lblNewLabel3, "2, 10, right, default");

		txtPreCustomerPickupTime = new JTextFieldValidate();
		txtPreCustomerPickupTime.setEnabled(false);
		add(txtPreCustomerPickupTime, "4, 10, 5, 1, fill, default");
		txtPreCustomerPickupTime.setColumns(TEN);
	}

	/**
	 * 
	 * 查询空运配载部门
	 * @author 025000-FOSS-helong
	 * @date 2013-1-21 下午04:40:31
	 * @return
	 */
	public JButton getBtnQueryAirDept() {
		return btnQueryAirDept;
	}
}