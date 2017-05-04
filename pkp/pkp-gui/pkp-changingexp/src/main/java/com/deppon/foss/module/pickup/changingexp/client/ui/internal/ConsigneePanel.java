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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/ui/internal/ConsigneePanel.java
 * 
 * FILE NAME        	: ConsigneePanel.java
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

import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument.Content;

import org.jdesktop.swingx.JXTextField;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.binding.validation.annotations.NotNull;
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
import com.deppon.foss.module.pickup.changingexp.client.action.QueryConsigneeAction;
import com.deppon.foss.module.pickup.changingexp.client.action.QueryConsigneeDeptAction;
import com.deppon.foss.module.pickup.common.client.ui.addressfield.JAddressFieldForExp;
import com.deppon.foss.module.pickup.common.client.utils.LengthDocument;
import com.deppon.foss.module.pickup.common.client.utils.NumberDocument;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * 收货客户信息面板
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:102246-foss-shaohongliang,date:2012-10-16
 * 上午9:25:44,content:
 * </p>
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-10-16 上午9:25:44
 * @since
 * @version
 */
@ContainerSeq(seq=4)
public class ConsigneePanel extends JPanel {

	/**
	 * default css
	 */
	private static final String DEFAULTGROW = "default:grow";
	
	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = -8823035208460096956L;
	
	/**
	 * 10
	 */
	private static final int TEN = 10;

	private static final int TWENTY = 20;

	private static final int ELEVEN = 11;

	private static final int TWELVE = 12;

	private static final int SIXTEEN = 16;

	private static final int EIGHT = 8;

	private static final int FOUR = 4;

	/**
	 * 国际化
	 */
	private static II18n i18n = I18nManager.getI18n(ConsigneePanel.class);

	/**
	 * 手机号
	 */
	@Bind("receiveCustomerMobilephone")
	@FocusSeq(seq=1)
	private JTextFieldValidate txtConsigneeMobile;

	/**
	 * 电话
	 */
	@Bind("receiveCustomerPhone")
	@FocusSeq(seq=2)
	private JTextFieldValidate txtConsigneePhone;

	/**
	 * 联系人
	 */
	@Bind("receiveCustomerContact")
	@NotNull
	@BindingArgs({ @BindingArg(name = "fieldName", value = "联系人") })
	@FocusSeq(seq=6)
	private JTextFieldValidate txtConsigneeLinkMan;

	/**
	 * 地址省市区
	 */
	@Bind("receiveCustomerArea")
	@FocusSeq(seq=7)
	private JAddressFieldForExp txtConsigneeArea;

	/**
	 * 详细地址
	 */
	@Bind("receiveCustomerAddress")
	@FocusSeq(seq=8)
	private JTextFieldValidate txtConsigneeAddress;

	/**
	 * 客户名称
	 */
	@Bind("receiveCustomerName")
	@BindingArgs({ @BindingArg(name = "fieldName", value = "客户名称") })
	@FocusSeq(seq=3)
	private JTextFieldValidate txtConsigeeName;

	/**
	 * 收货客户编码
	 */
	@Bind("receiveCustomerCode")
	@FocusSeq(seq=5)
	private JTextFieldValidate txtReceiveCustomerCode;
	private JLabel lblReceiveCustomerCode;

	/**
	 * 联系人
	 */
	private JLabel lblConsigneeLinkMan;
	
	private JPanel panel;

	
	/**
	 * 客户查询 
	 */
	@ButtonAction(icon = "query.png", shortcutKey = "F10", type = QueryConsigneeAction.class)
	@FocusSeq(seq=4)
	private JButton btnQuery;

	public JButton getBtnQuery() {
		return btnQuery;
	}

	
	public void setBtnQuery(JButton btnQuery) {
		this.btnQuery = btnQuery;
	}

	@ButtonAction(icon = "query.png", shortcutKey = "", type = QueryConsigneeDeptAction.class)
	private JButton btnConsigneeDept;
	/**
	 * 详细地址
	 */
	@Bind("receiveCustomerAddressNote")
	private JXTextField txtConsigneeAddressNote;

	/**
	 * @author 200945
	 */
	private JLabel lblNewLabel;
	@Bind("arriveCentralizedSettlement")
	private JTextField txtArriveCentralizedSettlement;
	private JLabel lblNewLabel_1;
	@Bind("arriveContractOrgName")
	private JTextField txtArriveContractOrgCode;

	/**
	 *  创建面板
	 */
	public ConsigneePanel() {
		init();
	}

	/**
	 * 
	 * 初始化界面
	 * @author foss-gengzhe
	 * @date 2012-12-25 上午10:22:53
	 * @see
	 */
	private void init() {
		setBorder(new JDTitledBorder(i18n.get("pickup.changingexp.consigneePanel")));
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
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,});
		formLayout.setColumnGroups(new int[][]{new int[]{TWELVE, EIGHT, FOUR, SIXTEEN}});
		setLayout(formLayout);

		JLabel lblConsigneeMobile = new JLabel(i18n.get("pickup.changingexp.mobile"));
		add(lblConsigneeMobile, "2, 1, right, default");

		txtConsigneeMobile = new JTextFieldValidate();
		add(txtConsigneeMobile, "4, 1, fill, default");
		txtConsigneeMobile.setColumns(TEN);
		/**
		 * 限制手机号码只允许输入11位的数字
		 */
		NumberDocument numberDocument = new NumberDocument(ELEVEN);
		txtConsigneeMobile.setDocument(numberDocument);

		JLabel lblConsigneePhone = new JLabel(i18n.get("pickup.changingexp.phone"));
		add(lblConsigneePhone, "6, 1, right, default");

		txtConsigneePhone = new JTextFieldValidate();
		add(txtConsigneePhone, "8, 1, fill, default");
		txtConsigneePhone.setColumns(TEN);
		// 限制手机号码只允许输入20位的数字
		NumberDocument telePhoneDocument = new NumberDocument(TWENTY);
		txtConsigneePhone.setDocument(telePhoneDocument);

		JLabel label = new JLabel(i18n.get("pickup.changingexp.consignerName"));
		add(label,"10, 1");
		
		panel = new JPanel();
		add(panel, "12, 1, fill, fill");
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode(DEFAULTGROW),
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,}));

		txtConsigeeName = new JTextFieldValidate();
		panel.add(txtConsigeeName,"1,1,fill,default");
		btnQuery = new JButton();
		btnQuery.setMargin(new Insets(-2, 1, -2, 1));
		panel.add(btnQuery, "2, 1");
		
		lblReceiveCustomerCode = new JLabel(i18n.get("pickup.changingexp.customerCode")+"：");
		add(lblReceiveCustomerCode, "14, 1, right, default");
		
		txtReceiveCustomerCode = new JTextFieldValidate();
		add(txtReceiveCustomerCode, "16, 1, fill, default");
		txtReceiveCustomerCode.setColumns(TEN);

		lblConsigneeLinkMan = new JLabel(i18n.get("pickup.changingexp.consigneeLinkMan"));
		add(lblConsigneeLinkMan, "2, 3, right, default");

		panel = new JPanel();
		add(panel, "4, 3, fill, default");
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		txtConsigneeLinkMan = new JTextFieldValidate();
		panel.add(txtConsigneeLinkMan);
		txtConsigneeLinkMan.setColumns(TEN);
		
		btnConsigneeDept = new JButton();
		panel.add(btnConsigneeDept);
		btnConsigneeDept.setVisible(false);
		btnConsigneeDept.setMargin(new Insets(-2, 1, -2, 1));

		JLabel lblConsigneeAddress = new JLabel(i18n.get("pickup.changingexp.consigneeAddress"));
		add(lblConsigneeAddress, "6, 3, right, default");

		txtConsigneeArea = new JAddressFieldForExp();
		add(txtConsigneeArea, "8, 3, fill, default");
		txtConsigneeArea.setColumns(TEN);

		txtConsigneeAddress = new JTextFieldValidate();
		add(txtConsigneeAddress, "10, 3, 3, 1, fill, default");
		txtConsigneeAddress.setColumns(TEN);
		
		txtConsigneeAddressNote = new JXTextField(i18n.get("foss.gui.creating.consigneePanel.consigneeAddress.textFieldTip"));
		LengthDocument consigneeDocumentNote = new LengthDocument(NumberConstants.NUMBER_100);
		add(txtConsigneeAddressNote, "13, 3, 3, 1, fill, default");
		txtConsigneeAddressNote.setColumns(TEN);
		txtConsigneeAddressNote.setDocument(consigneeDocumentNote);
		
		lblNewLabel = new JLabel(i18n.get("foss.gui.creating.consigneePanel.lblarrivecentralsett"));
		add(lblNewLabel, "2, 5, right, default");
		
		txtArriveCentralizedSettlement = new JTextField();
		txtArriveCentralizedSettlement.setEnabled(false);
		txtArriveCentralizedSettlement.setEditable(false);
		add(txtArriveCentralizedSettlement, "4, 5, fill, default");
		txtArriveCentralizedSettlement.setColumns(TEN);
		
		lblNewLabel_1 = new JLabel(i18n.get("foss.gui.creating.consigneePanel.lblarrivecontractcode"));
		add(lblNewLabel_1, "6, 5, right, default");
		
		txtArriveContractOrgCode = new JTextField();
		txtArriveContractOrgCode.setEnabled(false);
		txtArriveContractOrgCode.setEditable(false);
		add(txtArriveContractOrgCode, "8, 5, fill, default");
		txtArriveContractOrgCode.setColumns(TEN);

	}

	/**
	 * 
	 * 联系人get方法
	 * @author foss-gengzhe
	 * @date 2012-12-25 上午10:23:20
	 * @return
	 * @see
	 */
	public JTextFieldValidate getTxtConsigneeLinkMan() {
		return txtConsigneeLinkMan;
	}

	public JLabel getLblConsigneeLinkMan() {
		return lblConsigneeLinkMan;
	}

	/**
	 * 
	 * 客户
	 * @author foss-gengzhe
	 * @date 2012-12-25 上午10:23:35
	 * @return
	 * @see
	 */
	public JAddressFieldForExp getTxtConsigneeArea() {
		return txtConsigneeArea;
	}

	/**
	 * 
	 * 接收客户编码get方法
	 * @author foss-gengzhe
	 * @date 2012-12-25 上午10:23:40
	 * @return
	 * @see
	 */
	public JTextFieldValidate getTxtReceiveCustomerCode() {
		return txtReceiveCustomerCode;
	}

	
	/**
	 * @return the txtConsigneeAddress
	 */
	public JTextFieldValidate getTxtConsigneeAddress() {
		return txtConsigneeAddress;
	}

	public JButton getBtnConsigneeDept() {
		return btnConsigneeDept;
	}

	
	/**
	 * @return the txtConsigneePhone
	 */
	public JTextFieldValidate getTxtConsigneePhone() {
		return txtConsigneePhone;
	}

	
	/**
	 * @return the txtConsigeeName
	 */
	public JTextFieldValidate getTxtConsigeeName() {
		return txtConsigeeName;
	}

	/**
	 * @return the txtConsigneeMobile
	 */
	public JTextFieldValidate getTxtConsigneeMobile() {
		return txtConsigneeMobile;
	}


	public JLabel getLblReceiveCustomerCode() {
		return lblReceiveCustomerCode;
	}


	public void setLblReceiveCustomerCode(JLabel lblReceiveCustomerCode) {
		this.lblReceiveCustomerCode = lblReceiveCustomerCode;
	}


	public JXTextField getTxtConsigneeAddressNote() {
		return txtConsigneeAddressNote;
	}


	public void setTxtConsigneeAddressNote(JXTextField txtConsigneeAddressNote) {
		this.txtConsigneeAddressNote = txtConsigneeAddressNote;
	}
}