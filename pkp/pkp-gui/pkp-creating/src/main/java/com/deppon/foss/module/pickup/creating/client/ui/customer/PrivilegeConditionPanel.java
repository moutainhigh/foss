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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/ui/customer/PrivilegeConditionPanel.java
 * 
 * FILE NAME        	: PrivilegeConditionPanel.java
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
package com.deppon.foss.module.pickup.creating.client.ui.customer;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;

import org.jdesktop.swingx.JXTable;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.module.pickup.common.client.utils.FrameWithCust;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * 优惠费用 panel
 * @author admin
 *
 */
public class PrivilegeConditionPanel extends JPanel {

	private static final int HORIZONWIDTH = (int) 0.5f;

	private static final int TWENTY = 20;

	private static final int TWO = 2;

	private static final int TEN = 10;

	/**
	 * 序列货标识
	 */
	private static final long serialVersionUID = 6490840989179119452L;
	
	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(PrivilegeConditionPanel.class); 

	// 优惠总计
	private JTextField txtPriAmount;

	/**
	 * 
	 * （创建一个新的实例 ）PrivilegeConditionUI
	 * 
	 * @author dp-lifengteng
	 * @date 2012-10-15 下午6:50:06
	 */
	public PrivilegeConditionPanel() {
		init();
	}

	/**
	 * 
	 * init（方法详细描述说明、方法参数的具体涵义）
	 * 
	 * @author dp-lifengteng
	 * @date 2012-10-15 下午6:50:11
	 */
	public void init() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("50dlu:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("69dlu:grow"),
				FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("1dlu"), },
				new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("30dlu:grow"),
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("6dlu"), }));

		//优惠费用
		JLabel lblAmount = new JLabel(i18n.get("foss.gui.creating.privilegeConditionPanel.priAmount.label")+"：");
		add(lblAmount, "2, 2, right, default");

		txtPriAmount = new JTextField();
		txtPriAmount.setEnabled(false);
		add(txtPriAmount, "4, 2, fill, default");
		txtPriAmount.setColumns(TEN);

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "2, 4, 3, 1, fill, fill");

		Object[][] tblData = {
				{ Boolean.FALSE, i18n.get("foss.gui.creating.privilegeConditionPanel.data.transFee"), 
					i18n.get("foss.gui.creating.privilegeConditionPanel.data.discount"), "80%", null },
				{ Boolean.FALSE, i18n.get("foss.gui.creating.privilegeConditionPanel.data.valueadd"), 
						i18n.get("foss.gui.creating.privilegeConditionPanel.data.alidiscount"), null,
						Integer.valueOf(TWO) },
				{ Boolean.FALSE, i18n.get("foss.gui.creating.privilegeConditionPanel.data.otherdiscount"), 
						i18n.get("foss.gui.creating.privilegeConditionPanel.data.coupon"), null,
						Integer.valueOf(TWENTY) } };
		String[] tblHead = { i18n.get("foss.gui.creating.privilegeConditionPanel.head.action"), 
				i18n.get("foss.gui.creating.privilegeConditionPanel.head.item"), 
				i18n.get("foss.gui.creating.privilegeConditionPanel.head.type"), 
				i18n.get("foss.gui.creating.privilegeConditionPanel.head.discount"), 
				i18n.get("foss.gui.creating.privilegeConditionPanel.head.amount") };
		JXTable tblOtherCharge = new JXTable(tblData, tblHead);
		tblOtherCharge.getColumnModel().getColumn(0)
				.setCellRenderer(new TableCellRenderer() {

					@Override
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						JCheckBox ck = new JCheckBox();
						ck.setSelected(isSelected);
						ck.setHorizontalAlignment(HORIZONWIDTH);
						return ck;
					}
				});
		scrollPane.setViewportView(tblOtherCharge);

	}

	/**
	 * main for test
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setLocation(0, 0);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new PrivilegeConditionPanel());
		FrameWithCust.flexFrame(frame);
		frame.setVisible(true);
		frame.pack();
	}

}