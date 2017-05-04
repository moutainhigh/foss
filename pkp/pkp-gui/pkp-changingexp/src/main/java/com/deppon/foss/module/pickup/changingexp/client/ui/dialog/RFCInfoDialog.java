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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/ui/dialog/RFCInfoDialog.java
 * 
 * FILE NAME        	: RFCInfoDialog.java
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

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.math.BigDecimal;
import java.text.MessageFormat;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.annotation.ButtonAction;
import com.deppon.foss.framework.client.component.buttonaction.factory.ButtonActionFactory;
import com.deppon.foss.framework.client.core.context.ApplicationContext;
import com.deppon.foss.module.pickup.changingexp.client.action.WaybillRfcCommitAction;
import com.deppon.foss.module.pickup.changingexp.client.ui.WaybillRFCUI;
import com.deppon.foss.module.pickup.changingexp.client.ui.internal.MessagePanel;
import com.deppon.foss.module.pickup.changingexp.client.vo.WaybillInfoVo;
import com.deppon.foss.module.pickup.common.client.action.DialogCloseAction;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * 
 * 更改单确认信息对话框
 * @author 102246-foss-shaohongliang
 * @date 2012-12-18 下午8:00:06
 */
public class RFCInfoDialog extends JDialog {
	/**
	 * 国际化对象
	 */
	private static final II18n i18n = I18nManager.getI18n(RFCInfoDialog.class); 

	/**
	 * 8
	 */
	private static final int EIGHT = 8;

	/**
	 * 2
	 */
	private static final int TWO = 2;

	/**
	 * DEFAULT CSS STYLE
	 */
	private static final String DEFAULTGROW = "default:grow";

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = -3978295812120308605L;

	/**
	 * 更改单UI
	 */
	private WaybillRFCUI ui;

	/**
	 * 确认按钮
	 */
	@ButtonAction(icon="", shortcutKey="", type=WaybillRfcCommitAction.class)
	private JButton btnOk;

	/**
	 * 取消按钮
	 */
	@ButtonAction(icon="", shortcutKey="", type=DialogCloseAction.class)
	private JButton btnCancel;

	/**
	 * 给发货人发短信
	 */
	private JCheckBox cboConsigner;

	/**
	 * 给收货人发短信
	 */
	private JCheckBox cboConsignee;

	/**
	 * 费用差额Lbl
	 */
	private JLabel lblTotalLbl;
	
	/**
	 * 费用差额Txt
	 */
	private JLabel lblTotalTxt;

	/**
	 * 是否点击OK关闭
	 */
	private boolean isOk;
	
	/**
	 * 变更明细面板
	 */
	private MessagePanel messagePanel;

	/**
	 * 构造方法
	 * @param ui
	 */
	public RFCInfoDialog(WaybillRFCUI ui) {
		super(ApplicationContext.getApplication().getWorkbench().getFrame());
		this.ui = ui;
		init();
		bind();
		setDefaultValue();
	}

	/**
	 * 
	 * 设置默认值
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午10:15:35
	 */
	private void setDefaultValue() {
		messagePanel.getChangedInfoPanel().setChangeDetail(ui.getMessagePanel().getChangedInfoPanel().getTableData());
		messagePanel.getAbortInfoPanel().getLblOperateUser().setText(ui.getMessagePanel().getAbortInfoPanel().getLblOperateUser().getText());
		messagePanel.getAbortInfoPanel().getLblErrorResult().setText(ui.getMessagePanel().getAbortInfoPanel().getLblErrorResult().getText());
		messagePanel.getAbortInfoPanel().getTxtErrorCode().setText(ui.getMessagePanel().getAbortInfoPanel().getTxtErrorCode().getText());
		messagePanel.getAbortInfoPanel().getLblOperateTime().setText(ui.getMessagePanel().getAbortInfoPanel().getLblOperateTime().getText());
		messagePanel.getAbortInfoPanel().getTxtErrorCode().setEnabled(false);
		messagePanel.getInvalidInfoPanel().getLblOperateUser().setText(ui.getMessagePanel().getInvalidInfoPanel().getLblOperateUser().getText());
		String rfcTypeCode = ui.getBinderWaybill().getRfcType().getValueCode();
		if(WaybillRfcConstants.ABORT.equals(rfcTypeCode) || WaybillRfcConstants.INVALID.equals(rfcTypeCode)){
			lblTotalLbl.setVisible(false);
			lblTotalTxt.setVisible(false);
			messagePanel.getAbortInfoPanel().getBtnHandoverDetail().setVisible(false);
		}
	}

	/**
	 * 
	 * 初始化布局
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午9:18:23
	 */
	private void init() {
		setModal(true);
		setSize(new Dimension(NumberConstants.NUMBER_600, NumberConstants.NUMBER_400));
		setResizable(false);
		setTitle(i18n.get("foss.gui.changingexp.rfcInfoDialog.init.waybillRFC.label"));
		FormLayout formLayout = new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode(DEFAULTGROW),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode(DEFAULTGROW),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode(DEFAULTGROW),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,});
		formLayout.setColumnGroups(new int[][]{new int[]{TWO, EIGHT}});
		getContentPane().setLayout(formLayout);

		messagePanel = new MessagePanel();
		CardLayout cardLayout = (CardLayout) messagePanel.getLayout();
		cardLayout.show(messagePanel, ui.getMessagePanel().getCurrentLayoutName());
		getContentPane().add(messagePanel, "2, 2, 7, 1, fill, fill");
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.TRAILING);
		getContentPane().add(panel, "8, 4, fill, fill");
		
		lblTotalLbl = new JLabel(i18n.get("foss.gui.changingexp.rfcInfoDialog.init.totalFeeChange.label"));
		panel.add(lblTotalLbl);
		
		lblTotalTxt = new JLabel(i18n.get("foss.gui.changingexp.rfcInfoDialog.init.fiftyYuan.label"));
		panel.add(lblTotalTxt);

		JLabel label = new JLabel(i18n.get("foss.gui.changingexp.rfcInfoDialog.init.messageNotice.label"));
		label.setHorizontalAlignment(SwingConstants.TRAILING);
		getContentPane().add(label, "2, 6");

		btnOk = new JButton(i18n.get("foss.gui.changingexp.waybillRFCUI.common.confirm"));

		cboConsigner = new JCheckBox(i18n.get("foss.gui.changingexp.rfcInfoDialog.init.consigner.label"));
		getContentPane().add(cboConsigner, "4, 6");

		cboConsignee = new JCheckBox(i18n.get("foss.gui.changingexp.rfcInfoDialog.init.consignee.label"));
		getContentPane().add(cboConsignee, "6, 6");

		JSeparator separator = new JSeparator();
		getContentPane().add(separator, "2, 8, 7, 1");
		getContentPane().add(btnOk, "4, 10, fill, fill");

		btnCancel = new JButton(i18n.get("foss.gui.changingexp.waybillRFCUI.common.cancel"));
		getContentPane().add(btnCancel, "6, 10, fill, fill");
	}
	
	/**
	 * 
	 * UI与VO绑定
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-5 下午4:40:15
	 */
	private void bind() {
		// 按钮绑定
		ButtonActionFactory.getIntsance().bindButtonAction(this);
	}

	/**
	 * 
	 * 获取绑定更改单对象
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午10:15:49
	 */
	public WaybillInfoVo getWaybillInfoVo() {
		return ui.getBinderWaybill();
	}
	
	/**
	 * 
	 * 设置OK
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午10:16:03
	 */
	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}

	/**
	 * 
	 * 是否点击OK关闭
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午10:16:11
	 */
	public boolean isOk() {
		return isOk;
	}

	/**
	 * 
	 * 更改单UI
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午10:16:26
	 */
	public WaybillRFCUI getWaybillRFCUI() {
		return ui;
	}

	/**
	 * 
	 * 给发货人发短信
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午10:16:33
	 */
	public JCheckBox getCboConsigner() {
		return cboConsigner;
	}
	
	/**
	 * 给收货人发短信
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午10:16:55
	 */
	public JCheckBox getCboConsignee() {
		return cboConsignee;
	}

	/**
	 * 
	 * 设置总费用差额
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午10:16:59
	 */
	public void setTotalFeeChange(BigDecimal changedTotalFee) {
		String totalFeeStr = MessageFormat.format(i18n.get("foss.gui.changingexp.rfcInfoDialog.MessageFormat.totalFeeChange"), changedTotalFee);
		lblTotalTxt.setText(totalFeeStr);
	}
	
}