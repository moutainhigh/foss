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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/ui/print/ChoosePrintTypeDialog.java
 * 
 * FILE NAME        	: ChoosePrintTypeDialog.java
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
package com.deppon.foss.module.pickup.creating.client.ui.print;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.jdesktop.swingx.JXTable;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.annotation.ButtonAction;
import com.deppon.foss.framework.client.component.buttonaction.factory.ButtonActionFactory;
import com.deppon.foss.module.pickup.creating.client.action.PrintWaybillAction;
import com.deppon.foss.module.pickup.creating.client.ui.WaybillEditUI;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * (打印、半打印选择框)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:jiangfei,date:2012-10-18 下午1:55:19,content:
 * </p>
 * 
 * @author jiangfei
 * @date 2012-10-18 下午1:55:19
 * @since
 * @version
 */
public class ChoosePrintTypeDialog extends JDialog {
	
    	//国际化
 	private static final II18n i18n = I18nManager.getI18n(ChoosePrintTypeDialog.class);
 	
	private static final String DEFAULTGROW = "default:grow";

	private static final int FOURHUNDRED = 400;

	private static final int FIVEHUNDRED = 500;

	private static final String NUMBER_ONE="1";
	
	private static final String NUMBER_TWO="2";
	
	private static final String NUMBER_THREE="3";
	
	private static final String NUMBER_FOUR="4";
	
	
	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = -7959544878097277415L;

	private static final int TEN = 10;
	/**
	 * 输入panel
	 */
	private JTextField textField;
	/**
	 * （标示 是打印 还是打印预览 true为打印 false为打印预览）
	 */
	private Boolean isPrintOrPrePrint;
	/**
	 * UI
	 */
	private WaybillEditUI waybillEditUI;
	
	@ButtonAction(icon = "", shortcutKey = "", type = PrintWaybillAction.class)
	private JButton btnNewButton;
	private JPanel panel;
	private JXTable tbltable;
	
	/**
	 * 构造方法
	 * @param isPrintOrPrePrint
	 * @param ui
	 */
	public ChoosePrintTypeDialog(Boolean isPrintOrPrePrint,WaybillEditUI ui) {
		this.isPrintOrPrePrint = isPrintOrPrePrint;
		waybillEditUI =ui;
		init();
		bind();
		addListener();	
		this.setVisible(true);	
	}

	/**
	 * getWaybillEditUI
	 * @return WaybillEditUI
	 */
	public WaybillEditUI getWaybillEditUI() {
		return waybillEditUI;
	}
	/**
	 * getTextField
	 * @return JTextField
	 */
	public JTextField getTextField() {
		return textField;
	}
	/**
	 * setTextField
	 * @param textField JTextField
	 */
	public void setTextField(JTextField textField) {
		this.textField = textField;
	}
	
	/**
	 * 绑定
	 */
	private void bind() {
		ButtonActionFactory.getIntsance().bindButtonAction(this);
	}

	/**
	 * 初始化界面信息
	 */
	private void init() {
		setTitle(i18n.get("foss.gui.creating.ChoosePrintTypeDialog.title"));
		setSize(FIVEHUNDRED, FOURHUNDRED);
		setLocationRelativeTo(null);
		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(262dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.BUTTON_COLSPEC, },
				new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode(DEFAULTGROW),
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode(DEFAULTGROW), }));

		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, "2, 2, 3, 1, fill, fill");

		tbltable = new JXTable();
		scrollPane.setViewportView(tbltable);

		Object[][] tableDetail = { { i18n.get("foss.gui.creating.printWaybillAction.printTemplate.inland.one"), i18n.get("foss.gui.creating.ui.print.ChoosePrintTypeDialog.chinese.defaultAllMsg"), NUMBER_ONE},
				{ i18n.get("foss.gui.creating.printWaybillAction.printTemplate.inland.two"), i18n.get("foss.gui.creating.ui.print.ChoosePrintTypeDialog.chinese.halfMsg"),NUMBER_TWO },
				{ i18n.get("foss.gui.creating.printWaybillAction.printTemplate.HongKong.one"), i18n.get("foss.gui.creating.ui.print.ChoosePrintTypeDialog.chinese.allMsg"),NUMBER_THREE },
				{ i18n.get("foss.gui.creating.printWaybillAction.printTemplate.HongKong.two"), i18n.get("foss.gui.creating.ui.print.ChoosePrintTypeDialog.chinese.halfMsg"),NUMBER_FOUR}};
		String[] tableHeader = { i18n.get("foss.gui.creating.ui.print.ChoosePrintTypeDialog.chinese.templateName"), i18n.get("foss.gui.creating.ui.print.ChoosePrintTypeDialog.chinese.remark"),"" };
		tbltable.setModel(new DefaultTableModel(tableDetail, tableHeader));
		tbltable.setEditable(false);
		tbltable.getColumn(2).setMaxWidth(0);
		
		
		TableColumn tc = tbltable.getTableHeader().getColumnModel().getColumn(2);
		tc.setMaxWidth(0);
	    tc.setPreferredWidth(0);
	    tc.setWidth(0);
	    tc.setMinWidth(0);
	    tbltable.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(0);
	    tbltable.getTableHeader().getColumnModel().getColumn(2).setMinWidth(0);

		
 
		textField = new JTextField();
		panel.add(textField, "2, 4, fill, default");
		textField.setColumns(TEN);
		btnNewButton = new JButton(i18n.get("foss.gui.creating.ui.print.PrintTimesDialog.chinese.ok"));
		panel.add(btnNewButton, "4, 4");
		setModal(true);
	}

	/**
	 * <p>
	 * (添加监听)
	 * </p>
	 * 
	 * @author jiangfei
	 * @date 2012-10-18 上午9:13:15
	 * @return
	 * @see
	 */
	private void addListener() {
		tbltable.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent e) {
						textField.setText((String) tbltable.getValueAt(
								tbltable.getSelectedRow(), 0));
						textField.setEditable(false);
					}
				});
		
		tbltable.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if(e.getClickCount()==2)
				{
					btnNewButton.doClick();
				}
				
			}
			
		});
		
	}
	/**
	 * getIsPrintOrPrePrint
	 * @return Boolean
	 */
	public Boolean getIsPrintOrPrePrint() {
		return isPrintOrPrePrint;
	}
	/**
	 * setIsPrintOrPrePrint
	 * @param isPrintOrPrePrint Boolean
	 */
	public void setIsPrintOrPrePrint(Boolean isPrintOrPrePrint) {
		this.isPrintOrPrePrint = isPrintOrPrePrint;
	}
	
	public JXTable getTable(){
		return tbltable;
	}

}