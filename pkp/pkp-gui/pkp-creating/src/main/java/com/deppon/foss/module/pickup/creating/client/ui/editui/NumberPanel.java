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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/ui/editui/NumberPanel.java
 * 
 * FILE NAME        	: NumberPanel.java
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

import javax.swing.JPanel;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.module.mainframe.client.ui.JDTitledBorder;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Color;

/**
 * 
 * (顶部运单号提示面板)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:Administrator,date:2012-10-17 上午11:19:04,content:
 * </p>
 * 
 * @author 025000-FOSS-helong
 * @date 2012-10-17 上午11:19:04
 * @since
 * @version
 */
public class NumberPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(NumberPanel.class);

	/**
	 * 运单号
	 */
	private JLabel lblNumber;
	
	/**
	 * 网单
	 */
	private JLabel lblOrderLabel;
	
	/**
	 * 网单号
	 */
	private JLabel lblOrderNumber;
	
	/**
	 * 客户黑名单提醒
	 */
	private JLabel lblBlackListLabel;
	
	

	/**
	 * Create the panel.
	 */
	public NumberPanel() {
		init();

	}

	/**
	 * 
	 * <p>
	 * (初始化)
	 * </p>
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-16 上午10:41:32
	 * @see
	 */
	private void init() {
		setBorder(new JDTitledBorder());
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(11dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(223dlu;default)"),},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,}));

		//运单号
		JLabel lblWaybillLabel = new JLabel(i18n.get("foss.gui.creating.numberPanel.waybillNo.label")+"：");
		lblWaybillLabel.setForeground(Color.RED);
		lblWaybillLabel.setFont(new Font("宋体", Font.BOLD, NumberConstants.NUMBER_33));
		add(lblWaybillLabel, "4, 1");

		lblNumber = new JLabel("");
		lblNumber.setForeground(Color.RED);
		lblNumber.setFont(new Font("宋体", Font.BOLD, NumberConstants.NUMBER_33));
		add(lblNumber, "6, 1");

		//订单号
		lblOrderLabel = new JLabel(i18n.get("foss.gui.creating.numberPanel.orderNo.label")+"：");
		lblOrderLabel.setFont(new Font("宋体", Font.BOLD, NumberConstants.NUMBER_18));
		lblOrderLabel.setVisible(false);
		add(lblOrderLabel, "10, 1");

		lblOrderNumber = new JLabel("");
		lblOrderNumber.setFont(new Font("宋体", Font.BOLD, NumberConstants.NUMBER_18));
		add(lblOrderNumber, "12, 1");

		//FOSS-开单黑名单客户提示
		lblBlackListLabel = new JLabel("");
		lblBlackListLabel.setForeground(Color.RED);
		lblBlackListLabel.setFont(new Font("宋体", Font.BOLD, NumberConstants.NUMBER_33));
		lblBlackListLabel.setVisible(false);
		add(lblBlackListLabel, "16, 1");
	}

	public JLabel getLblNumber() {
		return lblNumber;
	}

	
	/**
	 * @return  the lblOrderNumber
	 */
	public JLabel getLblOrderNumber() {
		return lblOrderNumber;
	}

	
	/**
	 * @return  the lblOrderLabel
	 */
	public JLabel getLblOrderLabel() {
		return lblOrderLabel;
	}

	
	/**
	 * @param lblOrderLabel the lblOrderLabel to set
	 */
	public void setLblOrderLabel(JLabel lblOrderLabel) {
		this.lblOrderLabel = lblOrderLabel;
	}

	public JLabel getLblBlackListLabel() {
		return lblBlackListLabel;
	}
	
}