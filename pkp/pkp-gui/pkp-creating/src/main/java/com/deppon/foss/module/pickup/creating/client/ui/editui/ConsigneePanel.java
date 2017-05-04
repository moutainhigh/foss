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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/ui/editui/ConsigneePanel.java
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
package com.deppon.foss.module.pickup.creating.client.ui.editui;

import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdesktop.swingx.JXTextField;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.annotation.ButtonAction;
import com.deppon.foss.framework.client.component.focuspolicy.annotation.ContainerSeq;
import com.deppon.foss.framework.client.component.focuspolicy.annotation.FocusSeq;
import com.deppon.foss.framework.client.core.binding.annotation.Bind;
import com.deppon.foss.framework.client.widget.validatewidget.JTextFieldValidate;
import com.deppon.foss.module.mainframe.client.ui.JDTitledBorder;
import com.deppon.foss.module.pickup.common.client.ui.addressfield.JAddressField;
import com.deppon.foss.module.pickup.common.client.utils.LengthDocument;
import com.deppon.foss.module.pickup.common.client.utils.LetterDocument;
import com.deppon.foss.module.pickup.common.client.utils.MobileDocument;
import com.deppon.foss.module.pickup.common.client.utils.NumberDocument;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.action.QueryConsigneeAction;
import com.deppon.foss.module.pickup.creating.client.action.QueryConsigneeDeptAction;
import com.deppon.foss.module.pickup.creating.client.ui.WaybillEditUI;
import com.deppon.foss.module.pickup.waybill.shared.define.IconConstants;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * 
 * (收货客户面板)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:Administrator,date:2012-10-17 上午11:17:43,content:
 * </p>
 * 
 * @author 025000-FOSS-helong
 * @date 2012-10-17 上午11:17:43
 * @since
 * @version
 */
@ContainerSeq(seq=3)
public class ConsigneePanel extends JPanel {

	private static final int TEN = 10;
	
	private static final int LEN100 = 100;

	private static final long serialVersionUID = 1L;

	private static final double NUM_1000 = 1000.0;

	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(ConsigneePanel.class);
	
	/**
	 * 手机号码
	 */
	@Bind("receiveCustomerMobilephone")
	@FocusSeq(seq = 1)
	private JTextFieldValidate txtConsigneeMobile;
	
	/**
	 * 查询收货客户
	 */
	@ButtonAction(icon = IconConstants.QUERY, shortcutKey = "F10", type = QueryConsigneeAction.class)
	private JButton btnQuery;

	/**
	 * 电话号码
	 */
	@Bind("receiveCustomerPhone")
	@FocusSeq(seq = 2)
	private JTextFieldValidate txtConsigneePhone;

	/**
	 * 联系人
	 */
	@Bind("receiveCustomerContact")
	@FocusSeq(seq = 4)
	private JTextFieldValidate txtConsigneeLinkMan;

	/**
	 * 地址
	 */
	@Bind("receiveCustomerAddress")
	@FocusSeq(seq = 6)
	private JTextFieldValidate txtConsigneeAddress;

	/**
	 * 行政区域
	 */
	@Bind("receiveCustomerArea")  //加上绑定是为了实现地址匹配的联动
	@FocusSeq(seq = 5)
	private JAddressField txtConsigneeArea;

	/**
	 * 收货客户名称收货客户名称
	 */
	@Bind("receiveCustomerName")
	@FocusSeq(seq = 3)
	private JTextField txtReceiveCustomerName;
	
	/**
	 * 收货客户编码
	 */
	@Bind("receiveCustomerCode")
	private JTextField txtReceiveCustomerCode;
	private JLabel label2;
	

	private JLabel label5;

	private JLabel lblLinkMan;
	private JLabel lblNewLabel;
	private JPanel panel;
	
	@ButtonAction(icon = IconConstants.QUERY, shortcutKey = "", type = QueryConsigneeDeptAction.class)
	private JButton btnConsigneeDept;
	/**
	 * @author 200945 - wutao
	 * textArriveCentralizedSettlement:收货客户-是否统一结算
	 * txtArriveContractOrgCode:收货客户-合同部门
	 * 注释：都是不可编辑的
	 */
	private JLabel lblarrivecentralsett;
	@Bind("arriveCentralizedSettlement")
	private JTextField txtArriveCentralizedSettlement;
	private JLabel lblarrivecontractcode;
	@Bind("arriveContractOrgName")
	private JTextField txtArriveContractOrgCode;
	//wutao ==== end
	
	/**
	 * 地址
	 */
	@Bind("receiveCustomerAddressNote")
	@FocusSeq(seq = 7)
	private JXTextField txtConsigneeAddressNote;

	/**
	 * @项目：智能开单项目
	 * @功能：保存手机号码的输入时间
	 * @author:218371-foss-zhaoyanjun
	 * @date:2016-05-19
	 */
	double consigneeTelliphoneTime=0;
	WaybillPanelVo bean;
	
	/**
	 * Create the panel.
	 */
	public ConsigneePanel(WaybillEditUI ui) {
		init();
	}

	/**
	 * 
	 * <p>
	 * (初始化)
	 * </p>
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-16 上午10:36:01
	 * @see
	 */
	private void init() {
		setBorder(new JDTitledBorder(i18n.get("foss.gui.creating.consigneePanel.title")));
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(57dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(51dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(25dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(59dlu;default):grow"),
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));

		//收货客户手机
		JLabel label1 = new JLabel(i18n.get("foss.gui.creating.consigneePanel.consigneeMobile.label")+"：");
		add(label1, "2, 1, left, default");

		txtConsigneeMobile = new JTextFieldValidate();
		add(txtConsigneeMobile, "4, 1, fill, default");
		txtConsigneeMobile.setColumns(TEN);
		// 限制手机号码只允许输入11位的数字
		MobileDocument numberDocument = new MobileDocument(NumberConstants.NUMBER_11);
		txtConsigneeMobile.setDocument(numberDocument);
		
		//收货客户电话
		JLabel label3 = new JLabel(i18n.get("foss.gui.creating.consigneePanel.consigneePhone.label")+"：");
		add(label3, "6, 1, left, default");

		txtConsigneePhone = new JTextFieldValidate();
		add(txtConsigneePhone, "8, 1, fill, default");
		txtConsigneePhone.setColumns(TEN);
		// 限制手机号码只允许输入11位的数字
		NumberDocument telePhoneDocument = new NumberDocument(NumberConstants.NUMBER_20);
		txtConsigneePhone.setDocument(telePhoneDocument);
		
		//收货客户名称
		label2 = new JLabel(i18n.get("foss.gui.creating.consigneePanel.receiveCustomerName.label")+"：");
		add(label2, "10, 1, right, default");
		
		txtReceiveCustomerName = new JTextField();
		add(txtReceiveCustomerName, "12, 1, fill, default");
		txtReceiveCustomerName.setColumns(TEN);
		
		//查询
		btnQuery = new JButton();
		btnQuery.setMargin(new Insets(-2, 1, -2, 1));
		add(btnQuery, "13, 1");
		
		//收货客户编码
		lblNewLabel = new JLabel(i18n.get("foss.gui.creating.consigneePanel.receiveCustomerCode.label")+"：");
		add(lblNewLabel, "15, 1, right, default");
		
		txtReceiveCustomerCode = new JTextField();
		add(txtReceiveCustomerCode, "17, 1, fill, default");
		txtReceiveCustomerCode.setColumns(TEN);
		txtReceiveCustomerCode.setEditable(false);

		//收货联系人
		lblLinkMan = new JLabel("*"+i18n.get("foss.gui.creating.consigneePanel.consigneeLinkMan.label")+"： ");
		add(lblLinkMan, "2, 3, left, default");
		
		panel = new JPanel();
		add(panel, "4, 3, fill, fill");
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		txtConsigneeLinkMan = new JTextFieldValidate();
		panel.add(txtConsigneeLinkMan);
		txtConsigneeLinkMan.setColumns(TEN);
		
		//查询
		btnConsigneeDept = new JButton();
		panel.add(btnConsigneeDept);
		btnConsigneeDept.setVisible(false);
		btnConsigneeDept.setMargin(new Insets(-2, 1, -2, 1));
		btnConsigneeDept.setBorderPainted(false);

		//收货人地址
		label5 = new JLabel("*"+i18n.get("foss.gui.creating.consigneePanel.consigneeAddress.label")+"：");
		add(label5, "6, 3, left, default");

		txtConsigneeArea = new JAddressField(i18n.get("foss.gui.creating.consigneePanel.consigneeArea.textFieldTip"));
		add(txtConsigneeArea, "8, 3, 4, 1, fill, default");
		LetterDocument letterDocument=new LetterDocument();
		txtConsigneeArea.setDocument(letterDocument);
		
		txtConsigneeAddress = new JTextFieldValidate();
		add(txtConsigneeAddress, "12, 3, 4, 1");
		txtConsigneeAddress.setColumns(TEN);
		LengthDocument consigneeDocument = new LengthDocument(LEN100);
		txtConsigneeAddress.setDocument(consigneeDocument);
		
		txtConsigneeAddressNote = new JXTextField(i18n.get("foss.gui.creating.consigneePanel.consigneeAddress.textFieldTip"));
		LengthDocument consigneeDocumentNote = new LengthDocument(NumberConstants.NUMBER_100);
		add(txtConsigneeAddressNote, "16, 3, 2, 1, fill, default");
		txtConsigneeAddressNote.setColumns(TEN);
		txtConsigneeAddressNote.setDocument(consigneeDocumentNote);
		
		lblarrivecentralsett = new JLabel(i18n.get("foss.gui.creating.consigneePanel.lblarrivecentralsett"));
		add(lblarrivecentralsett, "2, 5, right, default");
		
		txtArriveCentralizedSettlement = new JTextField();
		txtArriveCentralizedSettlement.setEditable(false);
		txtArriveCentralizedSettlement.setEnabled(false);
		add(txtArriveCentralizedSettlement, "4, 5, fill, default");
		txtArriveCentralizedSettlement.setColumns(TEN);
		
		lblarrivecontractcode = new JLabel(i18n.get("foss.gui.creating.consigneePanel.lblarrivecontractcode"));
		add(lblarrivecontractcode, "6, 5, right, default");
		
		txtArriveContractOrgCode = new JTextField();
		txtArriveContractOrgCode.setEnabled(false);
		txtArriveContractOrgCode.setEditable(false);
		add(txtArriveContractOrgCode, "8, 5, fill, default");
		txtArriveContractOrgCode.setColumns(TEN);
	}
	public JTextFieldValidate getTxtConsigneeLinkMan() {
		return txtConsigneeLinkMan;
	}

	public JLabel getLblLinkMan() {
		return lblLinkMan;
	}
	
	public JAddressField getTxtConsigneeArea() {
		return txtConsigneeArea;
	}
	
	public JButton getBtnConsigneeDept() {
		return btnConsigneeDept;
	}

	
	/**
	 * @return the txtConsigneeAddress .
	 */
	public JTextFieldValidate getTxtConsigneeAddress() {
		return txtConsigneeAddress;
	}
	
	public JTextField getTxtReceiveCustomerCode() {
		return txtReceiveCustomerCode;
	}

	
	/**
	 * @return the txtConsigneeMobile .
	 */
	public JTextFieldValidate getTxtConsigneeMobile() {
		return txtConsigneeMobile;
	}

	
	/**
	 * @return the txtConsigneePhone .
	 */
	public JTextFieldValidate getTxtConsigneePhone() {
		return txtConsigneePhone;
	}

	
	/**
	 * @return the txtReceiveCustomerName .
	 */
	public JTextField getTxtReceiveCustomerName() {
		return txtReceiveCustomerName;
	}
	
	/**
	 * 
	 * 查询收货客户
	 * @author 025000-FOSS-helong
	 * @date 2013-2-25 上午09:28:44
	 * @return
	 */
	public JButton getBtnQuery() {
		return btnQuery;
	}

	public JLabel getLblNewLabel() {
		return lblNewLabel;
	}

	public void setLblNewLabel(JLabel lblNewLabel) {
		this.lblNewLabel = lblNewLabel;
	}

	public JXTextField getTxtConsigneeAddressNote() {
		return txtConsigneeAddressNote;
	}

	public void setTxtConsigneeAddressNote(JXTextField txtConsigneeAddressNote) {
		this.txtConsigneeAddressNote = txtConsigneeAddressNote;
	}

	public JTextField getTxtArriveCentralizedSettlement() {
		return txtArriveCentralizedSettlement;
	}

	public void setTxtArriveCentralizedSettlement(
			JTextField txtArriveCentralizedSettlement) {
		this.txtArriveCentralizedSettlement = txtArriveCentralizedSettlement;
	}

	public JTextField getTxtArriveContractOrgCode() {
		return txtArriveContractOrgCode;
	}

	public void setTxtArriveContractOrgCode(JTextField txtArriveContractOrgCode) {
		this.txtArriveContractOrgCode = txtArriveContractOrgCode;
	}
	
	public WaybillPanelVo getBean() {
		return bean;
	}

	public void setBean(WaybillPanelVo vo) {
		consigneeTelliphoneTime=0;
		this.bean = vo;
		/**
		 * @项目：智能开单项目
		 * @功能：保存手机号码的输入时间
		 * @author:218371-foss-zhaoyanjun
		 * @date:2016-05-19
		 */
		if(bean!=null&&bean.getIbtg()!=null){
			txtConsigneeMobile.addFocusListener(new FocusListener() {
				Date startTime;
				Date endTime;
				
				@Override
				public void focusLost(FocusEvent e) {
					// TODO Auto-generated method stub
					try {
						endTime=new Date();
						consigneeTelliphoneTime=(endTime.getTime()-startTime.getTime())/NUM_1000;
						consigneeTelliphoneTime=consigneeTelliphoneTime+bean.getIbtg().getConsigneeTelliphoneTime();
						bean.getIbtg().setConsigneeTelliphoneTime(consigneeTelliphoneTime);
					} catch (Exception e2) {
						// TODO: handle exception
					}
				}
				
				@Override
				public void focusGained(FocusEvent e) {
					// TODO Auto-generated method stub
					startTime=new Date();
				}
			});
		}
	}
}