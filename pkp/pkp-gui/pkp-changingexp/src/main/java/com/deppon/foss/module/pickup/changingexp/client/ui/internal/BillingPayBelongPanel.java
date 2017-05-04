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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/ui/internal/BillingPayBelongPanel.java
 * 
 * FILE NAME        	: BillingPayBelongPanel.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.changingexp.client.ui.internal;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.focuspolicy.annotation.ContainerSeq;
import com.deppon.foss.framework.client.component.focuspolicy.annotation.FocusSeq;
import com.deppon.foss.framework.client.core.binding.annotation.Bind;
import com.deppon.foss.framework.client.widget.validatewidget.JTextFieldValidate;
import com.deppon.foss.module.pickup.common.client.utils.FloatDocument;
import com.deppon.foss.module.pickup.common.client.utils.NumberDocument;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;
/**
 * 
 * 支付面板的子面板，针对整车业务
 * @author 025000-FOSS-helong
 * @date 2013-1-8 下午07:31:07
 */
@ContainerSeq(seq=8)
public class BillingPayBelongPanel extends JPanel {

	private static final long serialVersionUID = -7566811662764635962L;
	

	/**
	 * 国际化对象
	 */
	private static final II18n i18n = I18nManager.getI18n(BillingPayBelongPanel.class);
	
	/**
	 * 10 for column count
	 */
	private static final int TEN = 10;


	private static final int TWELVE = 12;
	
	
	/**
	 * 约车报价
	 */
	@Bind("wholeVehicleAppfee")
	private JTextFieldValidate txtWholeVehicleAppfee;
	/**
	 * 开单报价/公布价运费
	 */
	@Bind("transportFee")
	@FocusSeq(seq=1)
	private JTextFieldValidate txtPublicCharge;
	
	/**
	 * 整车约车报价标签
	 */
	private JLabel lblWholeVehicleAppfee;
	/**
	 * 开单报价标签
	 */
	private JLabel lblPublicCharge;
	
	/**
	 * 整车约车报价面板
	 */
	private JPanel wholeVehiclePanel;

	/**
	 * Create the panel.
	 */
	public BillingPayBelongPanel() {
		setLayout(new BorderLayout());
		
		//Box布局处理隐藏后自动填充问题
		Box box = Box.createHorizontalBox();
		add(box, BorderLayout.CENTER);
		
		box.add(Box.createHorizontalStrut(TWELVE));
		
		wholeVehiclePanel = new JPanel();
		wholeVehiclePanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,}));
		
		//约车报价
		lblWholeVehicleAppfee = new JLabel(i18n.get("foss.gui.changingexp.billingPayBelongPanel.wholeVehicleAppfee.label")+"：");
		wholeVehiclePanel.add(lblWholeVehicleAppfee, "2, 2");
		
		txtWholeVehicleAppfee = new JTextFieldValidate();
		wholeVehiclePanel.add(txtWholeVehicleAppfee, "4, 2");
		txtWholeVehicleAppfee.setColumns(TEN);
		txtWholeVehicleAppfee.setEnabled(false);
		box.add(wholeVehiclePanel);
		wholeVehiclePanel.setVisible(false);

		JPanel jPanel2 = new JPanel();
		jPanel2.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,}));

		//公布价运费
		lblPublicCharge = new JLabel(i18n.get("foss.gui.changingexp.billingPayBelongPanel.publicCharge.label")+"：");
		jPanel2.add(lblPublicCharge, "2, 2");
		
		txtPublicCharge = new JTextFieldValidate();
		jPanel2.add(txtPublicCharge, "4, 2");
		txtPublicCharge.setColumns(TEN);
		NumberDocument publicCharge = new NumberDocument(TEN);
		txtPublicCharge.setDocument(new FloatDocument(TEN, 2));
		txtPublicCharge.setEnabled(false);
		box.add(jPanel2);
	}

	public JTextFieldValidate getTxtWholeVehicleAppfee() {
		return txtWholeVehicleAppfee;
	}

	public JTextFieldValidate getTxtPublicCharge() {
		return txtPublicCharge;
	}

	public JLabel getLblWholeVehicleAppfee() {
		return lblWholeVehicleAppfee;
	}

	public JLabel getLblPublicCharge() {
		return lblPublicCharge;
	}

	public JPanel getWholeVehiclePanel() {
		return wholeVehiclePanel;
	}

	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		BillingPayBelongPanel p = new BillingPayBelongPanel();
		p.getWholeVehiclePanel().setVisible(false);
		frame.add(p);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}