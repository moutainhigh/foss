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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/ui/editui/ConsignerPanel.java
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
package com.deppon.foss.module.pickup.creating.client.ui.editui;

import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

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
import com.deppon.foss.module.pickup.creating.client.action.QueryConsignerAction;
import com.deppon.foss.module.pickup.creating.client.action.QueryConsignerDeptAction;
import com.deppon.foss.module.pickup.waybill.shared.define.IconConstants;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;


/**
 * 
 * (发货客户端面板)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:Administrator,date:2012-10-17 上午11:18:01,content:
 * </p>
 * 
 * @author 025000-FOSS-helong
 * @date 2012-10-17 上午11:18:01
 * @since
 * @version
 */
@ContainerSeq(seq=2)
public class ConsignerPanel extends JPanel {

	private static final int TEN = 10;
	
	private static final int LEN100 = 100;


	private static final long serialVersionUID = 1L;

	private static final double NUM_1000 = 1000.0;
	
	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(ConsignerPanel.class);

	/**
	 * 手机号码
	 */
	@Bind("deliveryCustomerMobilephone")
	@FocusSeq(seq=1)
	private JTextFieldValidate txtConsignerMobile;

	/**
	 * 电话号码
	 */
	@Bind("deliveryCustomerPhone")
	@FocusSeq(seq=2)
	private JTextFieldValidate txtConsignerPhone;
	
	
	/**
	 * 发货客户名称
	 */
	@Bind("deliveryCustomerName")
	@FocusSeq(seq=3)
	private JTextFieldValidate txtConsigerName;
	
	/**
	 * 客户编码
	 */
	@Bind("deliveryCustomerCode")
	private JTextFieldValidate txtDeliveryCustomerCode;

	/**
	 * 发货人区域
	 */
	@Bind("deliveryCustomerArea")
	@FocusSeq(seq=5)
	JAddressField txtConsignerArea;
	
	/**
	 *  发货联系人
	 */
	JLabel lblLinkMan;
	
	/**
	 * 查询发货客户
	 */
	@ButtonAction(icon = IconConstants.QUERY, shortcutKey = "F9", type = QueryConsignerAction.class)
	private JButton btnQuery;

	private JLabel label2;
	private JPanel panel;
	
	/**
	 * 发货联系人
	 */
	@Bind("deliveryCustomerContact")
	@FocusSeq(seq=4)
	private JTextFieldValidate txtConsignerLinkMan;
	
	@ButtonAction(icon = IconConstants.QUERY, shortcutKey = "", type = QueryConsignerDeptAction.class)
	private JButton btnConsignerDept;
	
	/**
	 * 发货人地址
	 */
	@Bind("deliveryCustomerAddress")
	@FocusSeq(seq=6)
	private JTextField txtConsignerAddress;
	private JLabel lblNewLabel_1;
	 

	/**
	 * 发票标记
	 */
	@Bind("invoiceTab")
	private JTextField txtInvoice;
	//private JTextField txtInvoice;
	
	@Bind("invoiceMode")
	private JComboBox combInvoiceMode;// 发票标记
	
	/**
	 * 发货人地址
	 */
	@Bind("deliveryCustomerAddressNote")
	private JXTextField txtConsignerAddressNote;
	
	private JLabel lblcentesett;
	/**
	 * 发货客户【是否统一结算】；设置为不可编辑
	 */
	@Bind("startCentralizedSettlement")
	private JTextField txtStartCentralizedSettlement;
	

	private JLabel lblcontractdept;
	/**
	 * 发货客户【合同部门】设置为不可编辑
	 */
	@Bind("startContractOrgName")
	private JTextField txtStartContractOrgCode;
	/* zhangchengfu 20150530 FOSS系统新客户标签需求 begin */
	/**
	 * 客户分群 
	 */
	@Bind("flabelleavemonth")
	private JComboBox combFlabelleavemonth;	
	private JLabel lblFlabelleavemonth;
	/* zhangchengfu 20150530 FOSS系统新客户标签需求 end */

	/**
	 * @项目：智能开单项目
	 * @功能：保存手机号码的输入时间
	 * @author:218371-foss-zhaoyanjun
	 * @date:2016-05-19
	 */
	double consignerTelliphoneTime=0;
	WaybillPanelVo bean;
	
	/**
	 * Create the panel.
	 */
	public ConsignerPanel() {

		init();
	}

	/**
	 * 
	 * <p>
	 * (初始化)
	 * </p>
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-16 上午10:37:48
	 * @see
	 */
	private void init() {
		setBorder(new JDTitledBorder(i18n.get("foss.gui.creating.consignerPanel.title")));
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(14dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
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

		//发货客户手机
		JLabel label1 = new JLabel(i18n.get("foss.gui.creating.consignerPanel.consignerMobile.label")+"：");
		add(label1, "2, 1, left, default");

		txtConsignerMobile = new JTextFieldValidate();
		add(txtConsignerMobile, "4, 1, fill, default");
		txtConsignerMobile.setColumns(TEN);
		// 限制手机号码只允许输入11位的数字
		MobileDocument numberDocument = new MobileDocument(NumberConstants.NUMBER_11);
		txtConsignerMobile.setDocument(numberDocument);
		
		//发货客户电话
		JLabel lblNewLabel = new JLabel(i18n.get("foss.gui.creating.consignerPanel.consignerPhone.label")+"：");
		add(lblNewLabel, "6, 1, left, default");

		txtConsignerPhone = new JTextFieldValidate();
		add(txtConsignerPhone, "8, 1, fill, default");
		txtConsignerPhone.setColumns(TEN);
		// 限制手机号码只允许输入20位的数字
		NumberDocument telePhoneDocument = new NumberDocument(NumberConstants.NUMBER_20);
		txtConsignerPhone.setDocument(telePhoneDocument);
		
		//发货客户姓名
		JLabel lblNewLabel1 = new JLabel(i18n.get("foss.gui.creating.consignerPanel.deliveryCustomerName.label")+"：");
		add(lblNewLabel1, "10, 1, right, default");
		
		txtConsigerName = new JTextFieldValidate();
		add(txtConsigerName, "12, 1, 7, 1, fill, default");
		txtConsigerName.setColumns(NumberConstants.NUMBER_10);
		
		//查询
		btnQuery = new JButton();
		btnQuery.setMargin(new Insets(-2, 1, -2, 1));
		add(btnQuery, "19, 1");
		
		//发货客户编码
		label2 = new JLabel(i18n.get("foss.gui.creating.consignerPanel.deliveryCustomerCode.label")+"：");
		add(label2, "21, 1, right, default");
		
		txtDeliveryCustomerCode = new JTextFieldValidate();
		add(txtDeliveryCustomerCode, "23, 1, fill, default");
		txtDeliveryCustomerCode.setColumns(TEN);
		txtDeliveryCustomerCode.setEditable(false);

		//发货客户联系人
		lblLinkMan = new JLabel("*"+i18n.get("foss.gui.creating.consignerPanel.consignerLinkMan.label")+"：");
		add(lblLinkMan, "2, 3, left, default");
		
		panel = new JPanel();
		add(panel, "4, 3, fill, fill");
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		txtConsignerLinkMan = new JTextFieldValidate();
		panel.add(txtConsignerLinkMan);
		txtConsignerLinkMan.setColumns(TEN);
		
		//查询
		btnConsignerDept = new JButton();
		btnConsignerDept.setVisible(false);
		btnConsignerDept.setMargin(new Insets(-2, 1, -2, 1));
		btnConsignerDept.setBorderPainted(false);
		panel.add(btnConsignerDept);

		//发货客户地址
		JLabel lblNewLabel2 = new JLabel("*"+i18n.get("foss.gui.creating.consignerPanel.consignerAddress.label")+"：");
		add(lblNewLabel2, "6, 3, left, default");

		txtConsignerArea = new JAddressField(i18n.get("foss.gui.creating.consigneePanel.consigneeArea.textFieldTip"));
		txtConsignerArea.setText("");
		add(txtConsignerArea, "8, 3, 2, 1, fill, default");
		LetterDocument letterDocument=new LetterDocument();
		txtConsignerArea.setDocument(letterDocument);
		
		txtConsignerAddress = new JTextField();
		add(txtConsignerAddress, "10, 3, 6, 1, fill, default");
		txtConsignerAddress.setColumns(TEN);
		LengthDocument consignerDocument = new LengthDocument(LEN100);
		txtConsignerAddress.setDocument(consignerDocument);
		
		txtConsignerAddressNote = new JXTextField(i18n.get("foss.gui.creating.consigneePanel.consigneeAddress.textFieldTip"));
		LengthDocument consigneeDocumentNote = new LengthDocument(NumberConstants.NUMBER_100);
		add(txtConsignerAddressNote, "16, 3, 5, 1, fill, default");
		txtConsignerAddressNote.setColumns(TEN);
		txtConsignerAddressNote.setDocument(consigneeDocumentNote);
		
		lblNewLabel_1 = new JLabel(i18n.get("foss.gui.creating.consignerPanel.invoice")+"：");
		add(lblNewLabel_1, "21, 3, right, default");
		
		txtInvoice = new JTextField();
		add(txtInvoice, "23, 3, fill, default");
		txtInvoice.setColumns(TEN);
		txtInvoice.setEditable(false);
		txtInvoice.setEnabled(true);
		
		combInvoiceMode = new JComboBox();
		add(combInvoiceMode, "23, 3, fill, default");
		//add(combInvoiceMode, "17, 3, fill, default");
		combInvoiceMode.setEnabled(false);
		combInvoiceMode.setEditable(false);
		
		lblcentesett = new JLabel(i18n.get("foss.gui.creating.consigneePanel.lblarrivecentralsett"));
		add(lblcentesett, "2, 5, right, default");
		
		txtStartCentralizedSettlement = new JTextField();
		txtStartCentralizedSettlement.setEnabled(false);
		txtStartCentralizedSettlement.setEditable(false);
		add(txtStartCentralizedSettlement, "4, 5, fill, default");
		txtStartCentralizedSettlement.setColumns(TEN);
		
		lblcontractdept = new JLabel(i18n.get("foss.gui.creating.consigneePanel.lblarrivecontractcode"));
		lblcontractdept.setHorizontalAlignment(SwingConstants.LEFT);
		add(lblcontractdept, "6, 5, right, default");
		
		txtStartContractOrgCode = new JTextField();
		txtStartContractOrgCode.setEnabled(false);
		txtStartContractOrgCode.setEditable(false);
		add(txtStartContractOrgCode, "8, 5, fill, default");
		txtStartContractOrgCode.setColumns(TEN);
		
		/* zhangchengfu 20150530 FOSS系统新客户标签需求 begin */
		lblFlabelleavemonth = new JLabel("客户分群：");
		add(lblFlabelleavemonth, "10, 5, right, default");
		combFlabelleavemonth = new JComboBox();
		add(combFlabelleavemonth, "12, 5, 10, 1, fill, default");
		combFlabelleavemonth.setEnabled(false);
		combFlabelleavemonth.setEditable(false);
		/* zhangchengfu 20150530 FOSS系统新客户标签需求 end */
	}
	

	public JLabel getLblLinkMan() {
		return lblLinkMan;
	}

	public JTextFieldValidate getTxtConsignerLinkMan() {
		return txtConsignerLinkMan;
	}
	
	
	public JAddressField getTxtConsignerArea() {
		return txtConsignerArea;
	}
	
	public JButton getBtnConsignerDept() {
		return btnConsignerDept;
	}
	
	public JTextField getTxtDeliveryCustomerCode() {
		return txtDeliveryCustomerCode;
	}
	
	/**
     * 获取发货人手机
	 */
	public JTextFieldValidate getTxtConsignerMobile(){
		return txtConsignerMobile;
	}
	
	/**
     * 获取发货人电话
	 */
	public JTextFieldValidate getTxtConsignerPhone(){
		return txtConsignerPhone;
	}
	
	/**
	 * 
	 * 查询发货客户
	 * @author 025000-FOSS-helong
	 * @date 2013-2-25 上午09:29:58
	 * @return
	 */
	public JButton getBtnQuery() {
		return btnQuery;
	}

	public JTextField getTxtInvoice() {
		return txtInvoice;
	}

	public JComboBox getCombInvoiceMode() {
		return combInvoiceMode;
	}
	public JLabel getLabel2() {
		return label2;
	}

	public void setLabel2(JLabel label2) {
		this.label2 = label2;
	}

	public JXTextField getTxtConsignerAddressNote() {
		return txtConsignerAddressNote;
	}

	public void setTxtConsignerAddressNote(JXTextField txtConsignerAddressNote) {
		this.txtConsignerAddressNote = txtConsignerAddressNote;
	}
	public JTextField getTxtConsignerAddress() {
		return txtConsignerAddress;
	}
	public void setTxtConsignerAddress(JTextField txtConsignerAddress) {
		this.txtConsignerAddress = txtConsignerAddress;
	}

	public JComboBox getCombFlabelleavemonth() {
		return combFlabelleavemonth;
	}

	public void setCombFlabelleavemonth(JComboBox combFlabelleavemonth) {
		this.combFlabelleavemonth = combFlabelleavemonth;
	}
	
	public WaybillPanelVo getBean() {
		return bean;
	}
	//===========================添加发货客户名称getter方法/linahe/2016年12月21日14:14:56/start===============
	/**
	 * 发货客户名称
	 */
	public JTextFieldValidate getTxtConsigerName() {
		return txtConsigerName;
	}
	//===========================添加发货客户名称getter方法/linahe/2016年12月21日14:14:56/end===============
	public void setBean(WaybillPanelVo vo) {
		consignerTelliphoneTime=0;
		this.bean = vo;
		/**
		 * @项目：智能开单项目
		 * @功能：保存手机号码的输入时间
		 * @author:218371-foss-zhaoyanjun
		 * @date:2016-05-19
		 */
		if(bean!=null&&bean.getIbtg()!=null){
			txtConsignerMobile.addFocusListener(new FocusListener() {
				Date startTime;
				Date endTime;
				
				@Override
				public void focusLost(FocusEvent e) {
					// TODO Auto-generated method stub
					try {
						endTime=new Date();
						consignerTelliphoneTime=(endTime.getTime()-startTime.getTime())/NUM_1000;
						consignerTelliphoneTime=consignerTelliphoneTime+bean.getIbtg().getConsignerTelliphoneTime();
						bean.getIbtg().setConsignerTelliphoneTime(consignerTelliphoneTime);
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