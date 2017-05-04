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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/ui/dialog/RFCPrintChoiceDiaglogNew.java
 * 
 * FILE NAME        	: RFCPrintChoiceDiaglogNew.java
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
package com.deppon.foss.module.pickup.changingexp.client.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.annotation.ButtonAction;
import com.deppon.foss.framework.client.component.buttonaction.factory.ButtonActionFactory;
import com.deppon.foss.framework.client.core.context.ApplicationContext;
import com.deppon.foss.module.pickup.changingexp.client.action.CloseDialogAction;
import com.deppon.foss.module.pickup.changingexp.client.action.PrintModifyWaybillAction;
import com.deppon.foss.module.pickup.changingexp.client.ui.WaybillRFCUI;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * 打印类型选择对话框
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-jiangfei,date:2012-10-25 上午11:50:52,
 * </p>
 * 
 * @author foss-jiangfei
 * @date 2012-10-25 上午11:50:52
 * @since
 * @version
 */
public class RFCPrintChoiceDiaglogNew extends JDialog {
	/**
	 * 国际化对象
	 */
	private static final II18n i18n = I18nManager.getI18n(RFCPrintChoiceDiaglogNew.class); 

	/**
	 * 160
	 */
	private static final int ONESIXZERO = 160;

	/**
	 * 210
	 */
	private static final int TWOONEZERO = 210;

	/**
	 * 16
	 */
	private static final int SIXTEEN = 16;

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = -6263337942223777843L;

	/**
	 * 更改单UI
	 */
	private WaybillRFCUI waybillRFCUI;

	/**
	 * 取消按钮
	 */
	@ButtonAction(icon = "", shortcutKey = "", type = CloseDialogAction.class)
	private JButton btnNewButton1;

	/**
	 * 确定按钮
	 */
	@ButtonAction(icon = "", shortcutKey = "", type = PrintModifyWaybillAction.class)
	private JButton btnNewButton;
	
	public JList list;

	/**
	 * 
	 * 实例化变更单打印类型选择对话框
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午10:18:13
	 */
	public RFCPrintChoiceDiaglogNew(WaybillRFCUI waybillRFCUI2) {
		super(ApplicationContext.getApplication().getWorkbench().getFrame());
		waybillRFCUI = waybillRFCUI2;
		init();
		bind();

	}

	
	public WaybillRFCUI getWaybillRFCUI() {
		return waybillRFCUI;
	}

	
	/**
	 * <p>
	 * 控件绑定
	 * </p>
	 * 
	 * @author foss-jiangfei
	 * @date 2012-10-25 下午5:12:35
	 * @see
	 */
	private void bind() {
		ButtonActionFactory.getIntsance().bindButtonAction(this);
	}

	/**
	 * <p>
	 * 页面初始化
	 * </p>
	 * 
	 * @author foss-jiangfei
	 * @date 2012-10-25 下午5:12:33
	 * @see
	 */
	private void init() {
		setModal(true);
		//"模板选择"
		setTitle(i18n.get("foss.gui.changingexp.rfcPrintChoiceDialog.title"));
		setSize(TWOONEZERO, ONESIXZERO);
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);

		panel.setLayout(new FormLayout(new ColumnSpec[] { ColumnSpec.decode("center:default"), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(39dlu;pref)"), FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(39dlu;pref)"), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(35dlu;default)"), }, new RowSpec[] { FormFactory.LINE_GAP_ROWSPEC, RowSpec.decode("max(42dlu;default):grow"), FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("max(32dlu;default)"), }));

		list = new JList();
		list.setFont(new Font("宋体", Font.PLAIN, SIXTEEN));

		list.setModel(new AbstractListModel() {

			/**
			 * 序列化版本号
			 */
			private static final long serialVersionUID = 6763480777205626235L;
			
			//变更单表格打印\ 变更单套打
			String[] values = new String[] { i18n.get("foss.gui.changingexp.rfcPrintChoiceDialog.element.one"), 
					i18n.get("foss.gui.changingexp.rfcPrintChoiceDialog.element.two") };

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		});
		panel.add(list, "1, 2, 9, 1, fill, fill");

		btnNewButton = new JButton(i18n.get("foss.gui.changingexp.waybillRFCUI.common.confirm"));
		panel.add(btnNewButton, "3, 4");

		btnNewButton1 = new JButton(i18n.get("foss.gui.changingexp.waybillRFCUI.common.cancel"));
		panel.add(btnNewButton1, "7, 4");
		
	}

}