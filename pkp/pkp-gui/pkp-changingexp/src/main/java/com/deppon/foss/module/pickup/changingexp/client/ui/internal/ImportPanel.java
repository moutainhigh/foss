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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/ui/internal/ImportPanel.java
 * 
 * FILE NAME        	: ImportPanel.java
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
package com.deppon.foss.module.pickup.changingexp.client.ui.internal;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.text.AbstractDocument.Content;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.annotation.ButtonAction;
import com.deppon.foss.framework.client.component.focuspolicy.annotation.ContainerSeq;
import com.deppon.foss.framework.client.component.focuspolicy.annotation.FocusSeq;
import com.deppon.foss.framework.client.core.binding.annotation.Bind;
import com.deppon.foss.framework.client.core.binding.annotation.BindingArg;
import com.deppon.foss.framework.client.core.binding.annotation.BindingArgs;
import com.deppon.foss.framework.client.widget.validatewidget.JTextFieldValidate;
import com.deppon.foss.module.mainframe.client.ui.JDTitledBorder;
import com.deppon.foss.module.pickup.changingexp.client.action.WaybillImportAction;
import com.deppon.foss.module.pickup.common.client.utils.LengthDocument;
import com.deppon.foss.module.pickup.common.client.utils.NumberDocument;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * 更改单导入面板
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:102246-foss-shaohongliang,date:2012-10-15
 * 下午2:31:12,content:
 * </p>
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-10-15 下午2:31:12
 * @since
 * @version
 */
@ContainerSeq(seq=1)
public class ImportPanel extends JPanel {

	/**
	 * 
	 */
	private static final String DEFAULTGROW = "default:grow";

	/**
	 * 18
	 */
	private static final int EIGHTEEN = 18;

	/**
	 * 14
	 */
	private static final int FOURTEEN = 14;

	/**
	 * 4
	 */
	private static final int FOUR = 4;

	/**
	 * 10
	 */
	private static final int TEN = 10;
	
	private static final int NUM_66 = 66;
	
	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 4795479896737832241L;

	/**
	 * 国际化
	 */
	private static II18n i18n = I18nManager.getI18n(ImportPanel.class);

	/**
	 * 运单号文本框
	 */
	@Bind("importWaybillNo")
	@FocusSeq(seq=1)
	private JTextFieldValidate txtWaybillNo;

	private ButtonGroup btnGroupRequire = new ButtonGroup();

	/**
	 * 运单导入按钮
	 */
	@ButtonAction(icon = "", shortcutKey = "", type = WaybillImportAction.class)
	@FocusSeq(seq=2)
	private JButton btnImport = new JButton(i18n.get("pickup.changingexp.import"));;

	/**
	 * 客户变更单选框
	 */
	@Bind("rfcSource#CUSTOMER_REQUIRE")
	@FocusSeq(seq=3)
	private JRadioButton rdoCustomerRequire;

	/**
	 * 内部变更单选框
	 */
	@Bind("rfcSource#INSIDE_REQUIRE")
	@FocusSeq(seq=4)
	private JRadioButton rdoInsideRequire;

	/**
	 * 变更类型下拉框
	 */
	@Bind("rfcType")
	@FocusSeq(seq=5)
	private JComboBox cboRfcType;

	/**
	 * 变更原因输入框
	 */
	@Bind("rfcReason")
	@BindingArgs({ @BindingArg(name = "fieldName", value = "变更原因") })
	@FocusSeq(seq=6)
	private JTextFieldValidate txtRfcReason;

	private DefaultComboBoxModel rfcTypeComboBoxModel;

	/**
	 * Create the panel.
	 */
	public ImportPanel() {
		init();
	}

	/**
	 * 
	 * 页面布局初始化
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午8:46:15
	 */
	private void init() {
		setBorder(new JDTitledBorder(""));
		FormLayout formLayout = new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode(DEFAULTGROW),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.BUTTON_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode(DEFAULTGROW),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode(DEFAULTGROW),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,});
		formLayout.setColumnGroups(new int[][]{new int[]{FOUR, FOURTEEN, EIGHTEEN}});
		setLayout(formLayout);

		JLabel lblWaybillNo = new JLabel(i18n.get("pickup.changingexp.waybillNo"));
		add(lblWaybillNo, "2, 1, right, default");

		txtWaybillNo = new JTextFieldValidate();
		add(txtWaybillNo, "4, 1, fill, default");
		txtWaybillNo.setColumns(TEN);
		txtWaybillNo.setDocument(new NumberDocument(TEN));

		add(btnImport, "6, 1");

		rdoCustomerRequire = new JRadioButton(i18n.get("pickup.changingexp.customerRequire"));

		btnGroupRequire.add(rdoCustomerRequire);
		add(rdoCustomerRequire, "8, 1");

		rdoInsideRequire = new JRadioButton(i18n.get("pickup.changingexp.insideRequire"));

		btnGroupRequire.add(rdoInsideRequire);
		add(rdoInsideRequire, "10, 1");

		JLabel lblRfcType = new JLabel(i18n.get("pickup.changingexp.rfcType"));
		add(lblRfcType, "12, 1, right, default");

		cboRfcType = new JComboBox();
		rfcTypeComboBoxModel = (DefaultComboBoxModel) cboRfcType.getModel();
		add(cboRfcType, "14, 1, fill, default");

		JLabel lblRfcReason = new JLabel(i18n.get("pickup.changingexp.rfcReason"));
		add(lblRfcReason, "16, 1, right, default");

		txtRfcReason = new JTextFieldValidate();
		add(txtRfcReason, "18, 1, fill, default");
		//变更原因数据库中是200个字符，界面上只能输入66个中文字符，不然就会报错。
		txtRfcReason.setDocument(new LengthDocument(NUM_66));
		txtRfcReason.setColumns(TEN);
	}

	/**
	 * <p>
	 * 变更类型下拉框
	 * </p>
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-10-16 下午3:00:26
	 * @return
	 * @see
	 */
	public JComboBox getCboRfcType() {
		return cboRfcType;
	}

	/**
	 * @return the txtWaybillNo
	 */
	public JTextFieldValidate getTxtWaybillNo() {
		return txtWaybillNo;
	}

	/**
	 * 
	 * 变更类型ComboBoxModel
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午8:46:30
	 */
	public DefaultComboBoxModel getRfcTypeComboBoxModel() {
		return rfcTypeComboBoxModel;
	}

	/**
	 * 
	 * 客户变更单选框
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午8:46:57
	 */
	public JRadioButton getRdoCustomerRequire() {
		return rdoCustomerRequire;
	}

	/**
	 * 
	 * 内部变更单选框
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午8:47:08
	 */
	public JRadioButton getRdoInsideRequire() {
		return rdoInsideRequire;
	}

	/**
	 * 
	 * 变更原因输入框
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午8:47:16
	 */
	public JTextFieldValidate getTxtRfcReason() {
		return txtRfcReason;
	}

	/**
	 * 
	 * 变更来源单选组
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午8:47:27
	 */
	public ButtonGroup getBtnGroupRequire() {
		return btnGroupRequire;
	}
	
	public JButton getBtnImport() {
		return btnImport;
	}

	public void setBtnImport(JButton btnImport) {
		this.btnImport = btnImport;
	}
	

}