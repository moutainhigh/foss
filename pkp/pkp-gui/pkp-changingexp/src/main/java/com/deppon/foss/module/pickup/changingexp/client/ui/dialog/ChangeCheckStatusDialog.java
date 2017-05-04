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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/ui/dialog/ChangeCheckStatusDialog.java
 * 
 * FILE NAME        	: ChangeCheckStatusDialog.java
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
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.core.context.ApplicationContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.mainframe.client.utills.ExceptionHandler;
import com.deppon.foss.module.pickup.changingexp.client.service.IWaybillRfcVarificationService;
import com.deppon.foss.module.pickup.changingexp.client.service.WaybillRfcServiceFactory;
import com.deppon.foss.module.pickup.changingexp.client.ui.WaybillRFCCheckUI;
import com.deppon.foss.module.pickup.changingexp.client.ui.internal.message.ChangedInfoPanel;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcChangeDetailEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcChangeDetailDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcChangeDto;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * 更改审核弹出对话框
 * 
 * @author foss-jiangfei
 * @date 2012-11-29 上午9:30:15
 * 
 */
public class ChangeCheckStatusDialog extends JDialog {
	/**
	 * 国际化对象
	 */
	private static final II18n i18n = I18nManager.getI18n(ChangeCheckStatusDialog.class);

	/**
	 * DEFAULT CSS STYLE
	 */
	private static final String DEFAULTGROW = "default:grow";

	/**
	 * 10
	 */
	private static final int TEN = 10;

	/**
	 * 14
	 */
	private static final int FOURTEEN = 14;

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -3119400451628763599L;

	/**
	 * 同意/拒绝原因
	 */
	private JTextField textField;

	/**
	 * 同意按钮
	 */
	private JButton btnAgree;

	/**
	 * 拒绝按钮
	 */
	private JButton btnReject;

	/**
	 * 审核页面
	 */
	private WaybillRFCCheckUI waybillRFCCheckUI;

	/**
	 * 审核服务类
	 */
	private IWaybillRfcVarificationService waybillRfcVarificationService = WaybillRfcServiceFactory
			.getWaybillRfcVarificationService();

	/**
	 * 待审核DTO
	 */
	private WaybillRfcChangeDto waybillRfcChangeDto;

	/**
	 * 变更明细页面
	 */
	private ChangedInfoPanel changedInfoPanel;

	/**
	 * 审核页面选中行
	 */
	private int selectedRow;

	/**
	 * 
	 * 获取变更明细页面
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午9:22:24
	 */
	public ChangedInfoPanel getChangedInfoPanel() {
		return changedInfoPanel;
	}
	
	/**
	 * 
	 * 设置变更明细页面
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午9:22:24
	 */
	public void setChangedInfoPanel(ChangedInfoPanel changedInfoPanel) {
		this.changedInfoPanel = changedInfoPanel;
	}

	/**
	 * 
	 * 根据选中行，审核DTO初始化Dialog
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午9:47:07
	 */
	public ChangeCheckStatusDialog(WaybillRfcChangeDto changeDto,
			WaybillRFCCheckUI ui, int selectedRowNo) {
		super(ApplicationContext.getApplication().getWorkbench().getFrame());
		waybillRFCCheckUI = ui;
		waybillRfcChangeDto = changeDto;
		selectedRow = selectedRowNo;
		init();
		// 添加按钮监听
		addListener();
		setChangeDetails();
		setComponentState();
	}

	/**
	 * 
	 * 添加按钮监听
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午9:19:36
	 */
	private void addListener() {
		
		
		btnAgree.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String agreeOpinion =textField.getText().trim();
				if(agreeOpinion!=null){
					agreeOpinion = agreeOpinion.replace('<', '《');
					agreeOpinion = agreeOpinion.replace('>', '》');
					agreeOpinion = agreeOpinion.replace('.', '。');
					agreeOpinion = agreeOpinion.replace('/', '、');
					agreeOpinion = agreeOpinion.replace('\\', '、');
					
				}
				try {
					boolean isChangeOk = waybillRfcVarificationService
							.agreeWaybillRfcCheck(
									waybillRfcChangeDto.getChangeBillId(),
									agreeOpinion);
					if (isChangeOk) {
						MsgBox.showInfo(i18n.get("foss.gui.changingexp.changeCheckStatusDialog.msgBox.auditSuccess"));
						setVisible(false);
						waybillRFCCheckUI
								.waybillRFCCheckUITableRefresh(selectedRow);
					} else {
						MsgBox.showInfo(i18n.get("foss.gui.changingexp.changeCheckStatusDialog.msgBox.auditFailure"));
					}
				} catch (Exception e1) {
					ExceptionHandler.alert(e1);
				}

			}

		});
		btnReject.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			
				String agreeOpinion =textField.getText().trim();
				if(agreeOpinion!=null){
					agreeOpinion = agreeOpinion.replace('<', '《');
					agreeOpinion = agreeOpinion.replace('>', '》');
					agreeOpinion = agreeOpinion.replace('.', '。');
					agreeOpinion = agreeOpinion.replace('/', '、');
					agreeOpinion = agreeOpinion.replace('\\', '、');
					
				}
				
				try {
					boolean isChangeOk = waybillRfcVarificationService
							.refuseWaybillRfcCheck(
									waybillRfcChangeDto.getChangeBillId(),
									agreeOpinion);
					if (isChangeOk) {
						MsgBox.showInfo(i18n.get("foss.gui.changingexp.changeCheckStatusDialog.msgBox.refuseSuccess"));
						setVisible(false);
						waybillRFCCheckUI
								.waybillRFCCheckUITableRefresh(selectedRow);
					} else {
						MsgBox.showInfo(i18n.get("foss.gui.changingexp.changeCheckStatusDialog.msgBox.refuseFailure"));
						dispose();
					}
				} catch (Exception e1) {
					ExceptionHandler.alert(e1);
				}
			}
		});

		
		//增加一个esc 就关闭弹出窗口的功能
		this.rootPane.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE , 0), "close");
		this.rootPane.getActionMap().put("close", new AbstractAction() {

		    /**
			 * 序列化版本号
			 */
			private static final long serialVersionUID = 1L;

			//事件处理响应
			public void actionPerformed(ActionEvent e) {
				//关闭
		        dispose();
		    }
		 });
	}

	/**
	 * 
	 * 初始化布局
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午9:18:23
	 */
	private void init() {
		setTitle(i18n.get("foss.gui.changingexp.changeCheckStatusDialog.init.auditRFCBill.label"));

		setSize(NumberConstants.NUMBER_700, NumberConstants.NUMBER_350);
		this.setModal(true);

		changedInfoPanel = new ChangedInfoPanel();
		JPanel panel1 = new JPanel();
		panel1.setBorder(new LineBorder(Color.DARK_GRAY));
		getContentPane().add(panel1, BorderLayout.EAST);
		
		JLabel label = new JLabel();
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("宋体", Font.BOLD, NumberConstants.NUMBER_14));
		if(WaybillRfcConstants.INVALID.equals(waybillRfcChangeDto.getRfcType())){ 
            label.setText(i18n.get("foss.gui.changingexp.changeCheckStatusDialog.init.invalidWaybill.label"));
			getContentPane().add(label, BorderLayout.CENTER);
			setSize(NumberConstants.NUMBER_400, NumberConstants.NUMBER_200);
		}else if(WaybillRfcConstants.ABORT.equals(waybillRfcChangeDto.getRfcType())){
			label.setText(i18n.get("foss.gui.changingexp.changeCheckStatusDialog.init.abortWaybill.label"));
			getContentPane().add(label, BorderLayout.CENTER);
			setSize(NumberConstants.NUMBER_400, NumberConstants.NUMBER_200);
		}else{
			getContentPane().add(changedInfoPanel, BorderLayout.CENTER);
		}
		panel1.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("120dlu"),
				FormFactory.RELATED_GAP_COLSPEC, }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode(DEFAULTGROW),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode(DEFAULTGROW), }));

		JLabel lblNewLabel = new JLabel(i18n.get("foss.gui.changingexp.changeCheckStatusDialog.init.auditRemark.label"));
		lblNewLabel.setFont(new Font("宋体", Font.BOLD, FOURTEEN));
		panel1.add(lblNewLabel, "2, 2");

		textField = new JTextField();
		panel1.add(textField, "2, 6, fill, default");
		textField.setColumns(TEN);

		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setHgap(TEN);
		panel1.add(panel, "2, 8, fill, fill");

		btnAgree = new JButton(i18n.get("foss.gui.changingexp.waybillRFCCheckAndDeal.agree"));
		panel.add(btnAgree);

		btnReject = new JButton(i18n.get("foss.gui.changingexp.waybillRFCCheckAndDeal.deny"));
		panel.add(btnReject);

	}

	/**
	 * 填充变更明细
	 * 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-22
	 * @return void
	 * @see
	 */
	private void setChangeDetails() {
		String rfcId = waybillRfcChangeDto.getChangeBillId();
		// 获得选中的ITEM包含的各种更改项信息
		List<WaybillRfcChangeDetailEntity> changeItemInfo = waybillRfcVarificationService.getRfcChangeDetailList(rfcId);
		// 创建WaybillRfcChangeDetailDto列表集合
		List<WaybillRfcChangeDetailDto> changeDetailList = new ArrayList<WaybillRfcChangeDetailDto>();
		WaybillRfcChangeDetailDto changeDetail = null;
		for (WaybillRfcChangeDetailEntity itemDetail : changeItemInfo) {
			changeDetail = new WaybillRfcChangeDetailDto();
			changeDetail.setRfcItems(itemDetail.getRfcItemsName());
			changeDetail.setBeforeRfcInfo(itemDetail.getBeforeRfcInfo());
			changeDetail.setAfterRfcInfo(itemDetail.getAfterRfcInfo());
			changeDetailList.add(changeDetail);
		}
		changedInfoPanel.setChangeDetail(changeDetailList);
	}

	/**
	 * 设置按钮、备注可用
	 * 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-22
	 * @return void
	 * @see
	 */
	private void setComponentState() {
		// 如果已经同意 或者拒绝 就不能再看到 审核 同意 拒绝按钮, 只有在 待审核 状态看到下面按钮
		if (StringUtil.isEmpty(waybillRfcChangeDto.getDealStauts())) {

		} else {
			btnAgree.setVisible(false);
			btnReject.setVisible(false);
			textField.setText(waybillRfcChangeDto.getCheckNotes());
			textField.setEnabled(false);
		}
	}

}