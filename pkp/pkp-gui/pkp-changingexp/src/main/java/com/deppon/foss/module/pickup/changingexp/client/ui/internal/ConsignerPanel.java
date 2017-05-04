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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/ui/internal/ConsignerPanel.java
 * 
 * FILE NAME        	: ConsignerPanel.java
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
import com.deppon.foss.module.pickup.changingexp.client.action.QueryConsignerAction;
import com.deppon.foss.module.pickup.changingexp.client.action.QueryConsignerDeptAction;
import com.deppon.foss.module.pickup.common.client.ui.addressfield.JAddressField;
import com.deppon.foss.module.pickup.common.client.utils.LengthDocument;
import com.deppon.foss.module.pickup.common.client.utils.NumberDocument;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * 发货客户信息面板
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:102246-foss-shaohongliang,date:2012-10-16
 * 上午9:21:52,content:
 * </p>
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-10-16 上午9:21:52
 * @since
 * @version
 */
@ContainerSeq(seq=3)
public class ConsignerPanel extends JPanel {


	/**
	 * 11
	 */
	private static final int ELEVEN = 11;

	/**
	 * 10
	 */
	private static final int TEN = 10;
	
	private static final int TWENTY = 20;

	private static final int TWELVE = 12;

	private static final int SIXTEEN = 16;

	private static final int EIGHT = 8;

	private static final int FOUR = 4;


	/**
	 * default css
	 */
	private static final String DEFAULTGROW = "default:grow";

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = -7683582356421409393L;

	/**
	 * 国际化
	 */
	private static II18n i18n = I18nManager.getI18n(ConsignerPanel.class);

	/**
	 * 联系人
	 */
	@Bind("deliveryCustomerContact")
	@NotNull
	@BindingArgs({ @BindingArg(name = "fieldName", value = "联系人") })
	@FocusSeq(seq=6)
	private JTextFieldValidate txtConsignerLinkMan;

	/**
	 * 客户电话
	 */
	@Bind("deliveryCustomerPhone")
	@FocusSeq(seq=2)
	private JTextFieldValidate txtConsignerPhone;
	
	/**
	 * 客户手机
	 */
	@Bind("deliveryCustomerMobilephone")
	@FocusSeq(seq=1)
	private JTextFieldValidate txtConsignerMobile;

	/**
	 * 客户名称
	 */
	@Bind("deliveryCustomerName")
	@FocusSeq(seq=3)
	@BindingArgs({ @BindingArg(name = "fieldName", value = "客户名称") })
	private JTextFieldValidate txtConsigerName;

	/**
	 * 客户地址
	 */
	@Bind("deliveryCustomerAddress")
	@FocusSeq(seq=8)
	private JTextFieldValidate txtConsignerAddress;

	/**
	 * 客户所在区域
	 */
	@Bind("deliveryCustomerArea")
	@FocusSeq(seq=7)
	private JAddressField txtConsignerArea;
	
	/**
	 * 客户编码
	 */
	@Bind("deliveryCustomerCode")
	@FocusSeq(seq=5)
	private JTextFieldValidate txtDeliveryCustomerCode;
	JLabel lblDeliveryCustomerCode;
	/**
	 * 联系人
	 */
	private JLabel lblConsignerLinkMan;
	
	private JPanel panel;
	
	/**
	 * 客户查询 
	 */
	@ButtonAction(icon = "query.png", shortcutKey = "F9", type = QueryConsignerAction.class)
	@FocusSeq(seq=4)
	private JButton btnQuery;
	
	@ButtonAction(icon = "query.png", shortcutKey = "", type = QueryConsignerDeptAction.class)
	private JButton btnConsignerDept;
	/**
	 * @author 200945 - wutao
	 */
	private JLabel lblNewLabel;
	@Bind("startCentralizedSettlement")
	private JTextField txtStartCentralizedSettlement;
	private JLabel lblNewLabel_1;
	@Bind("startContractOrgName")
	private JTextField txtStartContractOrgCode;
	
	/**
	 * 客户地址
	 */
	@Bind("deliveryCustomerAddressNote")
	private JXTextField txtConsignerAddressNote;

	/**
	 * 
	 * ConsignerPanel实例
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午11:03:27
	 */
	public ConsignerPanel() {
		init();
	}

	/**
	 * 
	 * 初始化布局
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午11:03:36
	 */
	private void init() {
		setBorder(new JDTitledBorder(i18n.get("pickup.changingexp.consignerPanel")));
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

		JLabel lblConsignerMobile = new JLabel(i18n.get("pickup.changingexp.mobile"));
		add(lblConsignerMobile, "2, 1, right, default");

		txtConsignerMobile = new JTextFieldValidate();
		add(txtConsignerMobile, "4, 1, fill, default");
		txtConsignerMobile.setColumns(TEN);

		/**
		 *  限制手机号码只允许输入11位的数字
		 */
		NumberDocument numberDocument = new NumberDocument(ELEVEN);
		txtConsignerMobile.setDocument(numberDocument);

		JLabel lblConsignerPhone = new JLabel(i18n.get("pickup.changingexp.phone"));
		add(lblConsignerPhone, "6, 1, right, default");

		txtConsignerPhone = new JTextFieldValidate();
		add(txtConsignerPhone, "8, 1, fill, default");
		txtConsignerPhone.setColumns(TEN);
		// 限制手机号码只允许输入20位的数字
		NumberDocument telePhoneDocument = new NumberDocument(TWENTY);
		txtConsignerPhone.setDocument(telePhoneDocument);

		JLabel lblConsignerName = new JLabel(i18n.get("pickup.changingexp.consignerName"));
		add(lblConsignerName, "10, 1");
		
		panel = new JPanel();
		add(panel, "12, 1, fill, fill");
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode(DEFAULTGROW),
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,}));

		txtConsigerName = new JTextFieldValidate();
		panel.add(txtConsigerName, "1, 1, fill, default");
		
		btnQuery = new JButton();
		btnQuery.setMargin(new Insets(-2, 1, -2, 1));
		panel.add(btnQuery, "2, 1");
		
		lblDeliveryCustomerCode = new JLabel(i18n.get("pickup.changingexp.customerCode")+"：");
		add(lblDeliveryCustomerCode, "14, 1, right, default");
		
		txtDeliveryCustomerCode = new JTextFieldValidate();
		add(txtDeliveryCustomerCode, "16, 1, fill, default");
		txtDeliveryCustomerCode.setColumns(TEN);

		lblConsignerLinkMan = new JLabel(i18n.get("pickup.changingexp.consignerLinkMan"));
		add(lblConsignerLinkMan, "2, 3, right, default");

		panel = new JPanel();
		add(panel, "4, 3, fill, default");
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		txtConsignerLinkMan = new JTextFieldValidate();
		panel.add(txtConsignerLinkMan);
		txtConsignerLinkMan.setColumns(TEN);
		
		btnConsignerDept = new JButton();
		btnConsignerDept.setMargin(new Insets(-2, 1, -2, 1));
		btnConsignerDept.setVisible(false);
		panel.add(btnConsignerDept);

		JLabel lblConsignerAddress = new JLabel(i18n.get("pickup.changingexp.consignerAddress"));
		add(lblConsignerAddress, "6, 3, right, default");

		txtConsignerArea = new JAddressField();
		add(txtConsignerArea, "8, 3, fill, default");
		txtConsignerArea.setColumns(TEN);

		txtConsignerAddress = new JTextFieldValidate();
		add(txtConsignerAddress, "10, 3, 3, 1, fill, default");
		txtConsignerAddress.setColumns(TEN);
		
		txtConsignerAddressNote = new JXTextField(i18n.get("foss.gui.creating.consigneePanel.consigneeAddress.textFieldTip"));
		LengthDocument consigneeDocumentNote = new LengthDocument(NumberConstants.NUMBER_100);
		add(txtConsignerAddressNote, "13, 3, 3, 1, fill, default");
		txtConsignerAddressNote.setColumns(TEN);
		txtConsignerAddressNote.setDocument(consigneeDocumentNote);
		
		lblNewLabel = new JLabel(i18n.get("foss.gui.creating.consigneePanel.lblarrivecentralsett"));
		add(lblNewLabel, "2, 5, right, default");
		
		txtStartCentralizedSettlement = new JTextField();
		txtStartCentralizedSettlement.setEnabled(false);
		txtStartCentralizedSettlement.setEditable(false);
		add(txtStartCentralizedSettlement, "4, 5, fill, default");
		txtStartCentralizedSettlement.setColumns(TEN);
		
		lblNewLabel_1 = new JLabel(i18n.get("foss.gui.creating.consigneePanel.lblarrivecontractcode"));
		add(lblNewLabel_1, "6, 5, right, default");
		
		txtStartContractOrgCode = new JTextField();
		txtStartContractOrgCode.setEnabled(false);
		txtStartContractOrgCode.setEditable(false);
		add(txtStartContractOrgCode, "8, 5, fill, default");
		txtStartContractOrgCode.setColumns(TEN);

	}

	/**
	 * 
	 * 联系人get方法 
	 * @author foss-gengzhe
	 * @date 2012-12-25 上午10:33:44
	 * @return
	 * @see
	 */
	public JTextFieldValidate getTxtConsignerLinkMan() {
		return txtConsignerLinkMan;
	}

	/**
	 * 
	 * 客户电话get方法
	 * @author foss-gengzhe
	 * @date 2012-12-25 上午10:33:48
	 * @return
	 * @see
	 */
	public JTextFieldValidate getTxtConsignerPhone() {
		return txtConsignerPhone;
	}

	/**
	 * 
	 * 客户手机get方法
	 * @author foss-gengzhe
	 * @date 2012-12-25 上午10:33:51
	 * @return
	 * @see
	 */
	public JTextFieldValidate getTxtConsignerMobile() {
		return txtConsignerMobile;
	}

	/**
	 * 
	 * 客户姓名get方法
	 * @author foss-gengzhe
	 * @date 2012-12-25 上午10:33:56
	 * @return
	 * @see
	 */
	public JTextFieldValidate getTxtConsigerName() {
		return txtConsigerName;
	}

	/**
	 * 
	 * 客户地址get方法
	 * @author foss-gengzhe
	 * @date 2012-12-25 上午10:33:59
	 * @return
	 * @see
	 */
	public JTextFieldValidate getTxtConsignerAddress() {
		return txtConsignerAddress;
	}

	/**
	 * 
	 * 客户所在区域get方法 
	 * @author foss-gengzhe
	 * @date 2012-12-25 上午10:34:03
	 * @return
	 * @see
	 */
	public JAddressField getTxtConsignerArea() {
		return txtConsignerArea;
	}

	public JLabel getLblConsignerLinkMan() {
		return lblConsignerLinkMan;
	}

	/**
	 * 
	 * 客户编码get方法
	 * @author foss-gengzhe
	 * @date 2012-12-25 上午10:34:11
	 * @return
	 * @see
	 */
	public JTextFieldValidate getTxtDeliveryCustomerCode() {
		return txtDeliveryCustomerCode;
	}
	/**
	 * 
	 * 查找按键get方法
	 * @author foss-gengzhe
	 * @date 2012-12-25 上午10:34:15
	 * @return
	 * @see
	 */
	public JButton getBtnQuery() {
		return btnQuery;
	}
	
	/**
	 * 
	 * 查找按键get方法
	 * @author foss-gengzhe
	 * @date 2012-12-25 上午10:34:15
	 * @return
	 * @see
	 */
	public JButton getBtnConsignerDept() {
		return btnConsignerDept;
	}

	public JLabel getLblDeliveryCustomerCode() {
		return lblDeliveryCustomerCode;
	}

	public void setLblDeliveryCustomerCode(JLabel lblDeliveryCustomerCode) {
		this.lblDeliveryCustomerCode = lblDeliveryCustomerCode;
	}

	public JXTextField getTxtConsignerAddressNote() {
		return txtConsignerAddressNote;
	}

	public void setTxtConsignerAddressNote(JXTextField txtConsignerAddressNote) {
		this.txtConsignerAddressNote = txtConsignerAddressNote;
	}
}