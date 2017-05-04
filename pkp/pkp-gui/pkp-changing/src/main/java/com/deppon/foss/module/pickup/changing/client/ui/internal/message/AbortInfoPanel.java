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
 * PROJECT NAME	: pkp-changing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changing/client/ui/internal/message/AbortInfoPanel.java
 * 
 * FILE NAME        	: AbortInfoPanel.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.changing.client.ui.internal.message;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.annotation.ButtonAction;
import com.deppon.foss.framework.client.component.focuspolicy.annotation.ContainerSeq;
import com.deppon.foss.framework.client.component.focuspolicy.annotation.FocusSeq;
import com.deppon.foss.framework.client.core.binding.annotation.Bind;
import com.deppon.foss.framework.client.widget.validatewidget.JTextFieldValidate;
import com.deppon.foss.module.mainframe.client.ui.JDTitledBorder;
import com.deppon.foss.module.pickup.changing.client.action.HandoverDetailAction;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * 
 * 中止信息Panel
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-10-29 上午9:47:58
 */
@ContainerSeq(seq=12)
public class AbortInfoPanel extends JPanel {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 4784753913889155462L;
	
	/**
	 *  国际化
	 */
	private static final II18n i18n = I18nManager.getI18n(AbortInfoPanel.class);

	private static final int TWELVE = 12;
	
	/**
	 * 操作人
	 */
	private JLabel lblOperateUser;

	/**
	 * 操作时间
	 */
	private JLabel lblOperateTime;

	/**
	 * 差错代码
	 */
	@FocusSeq(seq=1)
	@Bind("errorHandlingCode")
	private JTextFieldValidate txtErrorCode;

	/**
	 * 差错结果
	 */
	private JLabel lblErrorResult;

	/**
	 * 交接明细按钮
	 */
	@FocusSeq(seq=2)
	@ButtonAction(icon="", shortcutKey="", type=HandoverDetailAction.class)
	private JButton btnHandoverDetail = new JButton(i18n.get("foss.gui.changing.abortInfoPanel.handoverDetail.button"));

	/**
	 * 
	 * 构造中止页面
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午9:31:31
	 */
	public AbortInfoPanel() {
		setBorder(new JDTitledBorder(i18n.get("foss.gui.changing.abortInfoPanel.title")));
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));

		//中止人
		JLabel lblNewLabel = new JLabel(i18n.get("foss.gui.changing.abortInfoPanel.operateUser.label")+"：");
		lblNewLabel.setFont(new Font("宋体", Font.BOLD, TWELVE));
		lblNewLabel.setForeground(Color.RED);
		add(lblNewLabel, "2, 1");

		lblOperateUser = new JLabel();
		lblOperateUser.setFont(new Font("宋体", Font.BOLD, TWELVE));
		add(lblOperateUser, "4, 1");

		//中止时间
		JLabel label1 = new JLabel(i18n.get("foss.gui.changing.abortInfoPanel.operateTime.label")+"：");
		label1.setFont(new Font("宋体", Font.BOLD, TWELVE));
		label1.setForeground(Color.RED);
		add(label1, "6, 1");

		lblOperateTime = new JLabel();
		lblOperateTime.setFont(new Font("宋体", Font.BOLD, TWELVE));
		add(lblOperateTime, "8, 1");

		//差错处理编号
		JLabel label2 = new JLabel(i18n.get("foss.gui.changing.abortInfoPanel.errorCode.label")+"：");
		label2.setFont(new Font("宋体", Font.BOLD, TWELVE));
		label2.setForeground(Color.RED);
		add(label2, "2, 3, right, default");

		txtErrorCode = new JTextFieldValidate();
		add(txtErrorCode, "4, 3, fill, default");
		txtErrorCode.setColumns(NumberConstants.NUMBER_10);

		//差错处理结果
		JLabel label3 = new JLabel(i18n.get("foss.gui.changing.abortInfoPanel.errorResult.label")+"：");
		label3.setFont(new Font("宋体", Font.BOLD, TWELVE));
		label3.setForeground(Color.RED);
		add(label3, "6, 3");

		lblErrorResult = new JLabel();
		lblErrorResult.setFont(new Font("宋体", Font.BOLD, TWELVE));
		add(lblErrorResult, "8, 3");

		add(btnHandoverDetail, "2, 5");
		
	}

	
	/**
	 * @return the lblOperateUser
	 */
	public JLabel getLblOperateUser() {
		return lblOperateUser;
	}

	
	/**
	 * @return the lblOperateTime
	 */
	public JLabel getLblOperateTime() {
		return lblOperateTime;
	}

	
	/**
	 * @return the txtErrorCode
	 */
	public JTextFieldValidate getTxtErrorCode() {
		return txtErrorCode;
	}

	
	/**
	 * @return the lblErrorResult
	 */
	public JLabel getLblErrorResult() {
		return lblErrorResult;
	}

	
	/**
	 * @return the btnHandoverDetail
	 */
	public JButton getBtnHandoverDetail() {
		return btnHandoverDetail;
	}

	

	
}